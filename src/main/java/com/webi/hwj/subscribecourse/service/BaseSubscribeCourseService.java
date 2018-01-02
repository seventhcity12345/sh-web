package com.webi.hwj.subscribecourse.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.ErrorConstant;
import com.webi.hwj.course.dao.CourseOne2ManySchedulingDao;
import com.webi.hwj.course.dao.CourseOne2OneDao;
import com.webi.hwj.courseextension1v1.dao.CourseExtension1v1Dao;
import com.webi.hwj.courseone2many.dao.CourseOneToManySchedulingDao;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.gensee.service.GenseeService;
import com.webi.hwj.huanxun.exception.HuanxunException;
import com.webi.hwj.huanxun.service.HuanxunService;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.dao.OrderCourseOptionDao;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.teacher.dao.TeacherDao;
import com.webi.hwj.teacher.dao.TeacherEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.util.CalendarUtil;
import com.webi.hwj.webex.service.WebexSubscribeService;
import com.webi.hwj.weixin.constant.WeixinConstant;
import com.webi.hwj.weixin.entity.WeixinMsgOption;
import com.webi.hwj.weixin.service.TeacherWeixinService;
import com.webi.hwj.weixin.service.UserWeixinService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: 预约相关service.<br>
 * Description: 预约相关service<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月8日 上午9:42:46
 * 
 * @author yangmh
 */
@Service
public class BaseSubscribeCourseService {
  private static Logger logger = Logger.getLogger(BaseSubscribeCourseService.class);
  @Resource
  SubscribeCourseDao subscribeCourseDao;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;
  @Resource
  OrderCourseDao orderCourseDao;
  @Resource
  UserDao userDao;
  @Resource
  OrderCourseOptionDao orderCourseOptionDao;
  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;
  @Resource
  TeacherDao teacherDao;
  @Resource
  TeacherTimeDao teacherTimeDao;
  @Resource
  CourseOne2ManySchedulingDao courseOne2ManySchedulingDao;
  @Resource
  CourseOne2OneDao courseOne2OneDao;
  @Resource
  HuanxunService huanxunService;
  @Resource
  GenseeService genseeService;
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  CourseOneToManySchedulingDao courseOneToManySchedulingDao;
  @Resource
  WebexSubscribeService webexSubscribeService;
  @Resource
  CourseExtension1v1Dao courseExtension1v1Dao;
  @Resource
  BaseSubscribeCourseCheckService baseSubscribeCourseCheckService;
  @Resource
  TeacherTimeService teacherTimeService;
  @Resource
  BaseSubscribeCourseTransactionService baseSubscribeCourseTransactionService;
  @Resource
  UserWeixinService userWeixinService;
  @Resource
  TeacherEntityDao teacherEntityDao;
  @Resource
  private TeacherWeixinService teacherWeixinService;

  /**
   * Title: 预约总入口.<br>
   * Description: subscribeEntry<br>
   * CreateDate: 2016年9月22日 下午6:30:27<br>
   * 
   * @category 预约总入口
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   * @param sessionUser
   *          sessionUser
   * @return CommonJsonObject对象
   */
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public CommonJsonObject subscribeEntry(SubscribeCourse subscribeCourse, SessionUser sessionUser)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    try {
      // 预约总入口,分为带事务和不带事务两部分，考虑到事务的隔离级别与传播级别。

      // 为了提升性能，尽量减少往外抛异常，这种写法待优化，先解决眼前的性能问题再重构代码

      // 这段也可以封起来，先通过teacherTimeId查询出相关信息保存至subscribeCourse对象中
      TeacherTime teacherTime = teacherTimeEntityDao.findOneByKeyId(
          subscribeCourse.getTeacherTimeId());
      if (teacherTime == null) {
        // modified by seven 2016年8月6日12:08:16 修改日志级别
        logger.info(
            ErrorConstant.TEACHERTIMEID_NOT_EXIST + "userId=" + subscribeCourse.getUserId());
        json.setCode(ErrorCodeEnum.TEACHER_TIME_NOT_EXIST.getCode());
        return json;
      } else {
        subscribeCourse.setStartTime(teacherTime.getStartTime());
        subscribeCourse.setEndTime(teacherTime.getEndTime());
        subscribeCourse.setTeacherId(teacherTime.getTeacherId());
        subscribeCourse.setTeacherName(teacherTime.getTeacherName());
      }

      // 预约逻辑的步骤：
      // 1.通用不带事务校验
      // 2.个体不带事务校验
      // 3.通用带事务校验
      // 4.个体带事务校验
      // 5.个体提交带事务
      // 6.通用提交带事务

      // 1.通用不带事务校验
      json = baseSubscribeCourseCheckService
          .checkSubscribeCourseCommonWithoutTransaction(subscribeCourse);

      if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
        return json;
      }

      switch (subscribeCourse.getCourseType()) {
        case "course_type1":// core1v1
        case "course_type9":// ext1v1

          // 2.个体带不带事务校验
          json = baseSubscribeCourseCheckService
              .checkSubscribeCourseType1WithoutTransaction(subscribeCourse);
          if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
            return json;
          }

          // courseType1的预约事务入口
          json = baseSubscribeCourseTransactionService.subscribeCourseType1WithTransaction(
              subscribeCourse,
              sessionUser);

          if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
            return json;
          }

          break;
        case "course_type2":// ext1v6

          // 2.个体带不带事务校验

          // courseType2的预约事务入口
          json = baseSubscribeCourseTransactionService.subscribeCourseType2WithTransaction(
              subscribeCourse,
              sessionUser);

          if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
            return json;
          }

          break;
        case "course_type8":// es
        case "course_type5":// oc

          // 2.个体不带事务校验
          json = baseSubscribeCourseCheckService
              .checkSubscribeCourseType8WithoutTransaction(subscribeCourse);

          if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
            return json;
          }

          // courseType8的预约事务入口
          json = baseSubscribeCourseTransactionService.subscribeCourseType8WithTransaction(
              subscribeCourse,
              sessionUser);

          if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
            return json;
          }

          break;
        default:
          json.setCode(ErrorCodeEnum.SUBSCRIBE_ERROR_COURSE_TYPE.getCode());
          return json;
      }
    } catch (HuanxunException ex) {
      // modify by seven 这样会发生死锁
      // 将删除的方法放到预约事物外面,如果觉的不好就在接收到303返回值得时候跑个消息队列之类的来做这件事情
      teacherTimeService.deleteOnlyUseInSubscribe(subscribeCourse.getTeacherTimeId());
      throw ex;
    }

    logger.info("预约成功------>subscribeId=" + json.getData());

    return json;

  }

  /**
   * Title: 取消预约总入口.<br>
   * Description: 取消预约一开始查预约表id时就需要带事物，如果查的时候不带，会导致可以重复取消预约同一节课。<br>
   * CreateDate: 2016年9月22日 下午5:31:16<br>
   * 
   * @category 取消预约总入口
   * @author yangmh
   * @param subscribeCourseParam
   *          预约参数对象
   * @return CommonJsonObject对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject cancelSubscribeEntry(SubscribeCourse subscribeCourseParam)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // 取消预约逻辑的步骤：
    // 1.通用不带事务校验
    // 2.个体不带事务校验
    // 3.通用带事务校验
    // 4.个体带事务校验
    // 5.个体提交带事务
    // 6.通用提交带事务

    SubscribeCourse subscribeCourse = subscribeCourseEntityDao
        .findOneByKeyIdForUpdate(subscribeCourseParam.getKeyId());

    if (subscribeCourse == null) {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_COURSE_NOT_EXIST.getCode());
      return json;
    }

    subscribeCourse.setUpdateUserId(subscribeCourseParam.getUpdateUserId());
    subscribeCourse.setSubscribeFrom(subscribeCourseParam.getSubscribeFrom());
    subscribeCourseParam.setCourseType(subscribeCourse.getCourseType());

    // 1.通用不带事务校验
    json = baseSubscribeCourseCheckService
        .checkCancelSubscribeCourseCommonWithoutTransaction(subscribeCourse);

    if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
      return json;
    }

    switch (subscribeCourse.getCourseType()) {
      case "course_type1":// core1v1
      case "course_type9":// ext1v1

        // 2.个体不带事务校验

        // courseType1的取消事务入口
        json = baseSubscribeCourseTransactionService
            .cancelSubscribeCourseType1WithTransaction(subscribeCourse);

        if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
          return json;
        }

        break;
      case "course_type2":// core1v6
      case "course_type5":// oc
      case "course_type8":// es

        // 2.个体不带事务校验

        // courseType2的取消事务入口
        json = baseSubscribeCourseTransactionService
            .cancelSubscribeCourseType2WithTransaction(subscribeCourse);

        if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
          return json;
        }

        break;
      default:
        json.setCode(ErrorCodeEnum.SUBSCRIBE_ERROR_COURSE_TYPE.getCode());
        return json;
    }

    String courseType = subscribeCourseParam.getCourseType();

    /**
     * 下发短信业务逻辑
     */
    // oc和es不发送短信
    if (!"course_type5".equals(courseType) && !"course_type8".equals(courseType)) {
      // 4.发送取消预约成功短信
      CourseType courseTypeObj = ((CourseType) MemcachedUtil.getValue(courseType));
      // 发送短信{0}同学,您取消了开课时间为{1}的外教{2},祝您生活愉快
      SmsUtil.sendSmsToQueue(subscribeCourseParam.getUserPhone(),
          MessageFormat.format(MemcachedUtil.getConfigValue("cancel_subscribe_success_message"),
              new String[] { subscribeCourse.getUserName(),
                  DateUtil.dateToStrYYMMDDHHMM((Date) subscribeCourse.getStartTime()),
                  courseTypeObj.getCourseTypeChineseName() }));
    }

    /**
     * 下发微信消息业务逻辑
     */
    sendCancelSubscribeWeixinMsg(subscribeCourse);

    return json;
  }

  /**
   * Title: 下发取消预约微信消息.<br>
   * Description: sendCancelSubscribeMsg<br>
   * CreateDate: 2016年10月18日 下午7:44:18<br>
   * 
   * @category sendCancelSubscribeMsg
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   */
  public void sendCancelSubscribeWeixinMsg(SubscribeCourse subscribeCourse) {
    try {
      Map<String, Object> dataMap = new HashMap<String, Object>();
      // 下发消息的消息体，根据不同的模板，不一样的写法
      dataMap.put("first",
          new WeixinMsgOption("亲爱的学员，已为您取消以下课程。"));
      // 课程名称
      dataMap.put("keyword1", new WeixinMsgOption(subscribeCourse.getCourseTitle()));

      // 时间
      dataMap.put("keyword2",
          new WeixinMsgOption(
              DateUtil.dateToStr(subscribeCourse.getStartTime(), "yyyy-MM-dd HH:mm")));

      dataMap.put("remark", new WeixinMsgOption("记得下次来重新订课啊，我们等你哦~"));
      /**
       * modified by komi 2016年12月20日15:20:24 微信推送，代码复用
       */
      userWeixinService.sendWeixinMsg(dataMap, subscribeCourse.getUserId(),
          WeixinConstant.getWeixinMsgTemplateIdCancelSubscribe(), subscribeCourse.getKeyId());

    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("error:" + ex.toString(), ex);
      try {
        SmsUtil.sendAlarmSms("下发取消预约微信消息报警,subscribeId=" + subscribeCourse.getKeyId());
      } catch (Exception exx) {
        exx.printStackTrace();
        logger.error("error:" + ex.toString(), ex);
      }

    }
  }

  /**
   * Title: 下发预约微信消息.<br>
   * Description: sendSubscribeWeixinMsg<br>
   * CreateDate: 2016年10月18日 下午7:44:18<br>
   * 
   * @category 下发预约微信消息
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   */
  public void sendSubscribeWeixinMsg(SubscribeCourse subscribeCourse) {
    try {
      logger.info("预约成功--->微信下发消息");
      Map<String, Object> dataMap = new HashMap<String, Object>();
      // 下发消息的消息体，根据不同的模板，不一样的写法
      dataMap.put("first",
          new WeixinMsgOption("亲爱的学员，您已成功预订以下课程，我们不见不散哦~"));
      // 课程名称
      dataMap.put("keyword1", new WeixinMsgOption(subscribeCourse.getCourseTitle()));

      // 时间
      dataMap.put("keyword2",
          new WeixinMsgOption(
              DateUtil.dateToStr(subscribeCourse.getStartTime(), "yyyy-MM-dd HH:mm")));

      dataMap.put("remark", new WeixinMsgOption("点击详情，查看课程详细情况"));

      /**
       * modified by komi 2016年12月20日15:20:24 微信推送，代码复用
       */
      userWeixinService.sendWeixinMsg(dataMap, subscribeCourse.getUserId(),
          WeixinConstant.getWeixinMsgTemplateIdSubscribe(), subscribeCourse.getKeyId());

    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("error:" + ex.getMessage(), ex);
      try {
        SmsUtil.sendAlarmSms("下发预约微信消息报警,subscribeId=" + subscribeCourse.getKeyId());
      } catch (Exception exx) {
        exx.printStackTrace();
        logger.error("error:" + exx.getMessage(), exx);
      }

    }
  }

  /**
   * 
   * Title: 预约demo课<br>
   * Description: demoSubscribe<br>
   * CreateDate: 2016年12月23日 下午5:24:09<br>
   * 
   * @category 预约demo课
   * @author seven.gz
   * @param userId
   * @param subscribeCourse
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public CommonJsonObject subscribeDemoEntry(SubscribeCourse subscribeCourse,
      String webexRoomHostId, String adminUserName)
          throws Exception {
    // demo 预约统一预约有很多重复的地方这里没有偶抽出来因为时间不太够，也怕对之前的预约有影响，先copy下
    CommonJsonObject json = new CommonJsonObject();

    try {

      TeacherTime teacherTime = teacherTimeEntityDao.findOneByKeyId(
          subscribeCourse.getTeacherTimeId());

      // 根据配置获取房间对应时间的会议号
      String webexMeetingKey = findWebexMeetingKey(webexRoomHostId, teacherTime);
      if (StringUtils.isEmpty(webexMeetingKey)) {
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg("预约时间段没有对应的会议号");
        return json;
      }

      if (teacherTime == null) {
        // modified by seven 2016年8月6日12:08:16 修改日志级别
        logger.info(
            ErrorConstant.TEACHERTIMEID_NOT_EXIST + "userId=" + subscribeCourse.getUserId());
        json.setCode(ErrorCodeEnum.TEACHER_TIME_NOT_EXIST.getCode());
        return json;
      } else {
        subscribeCourse.setStartTime(teacherTime.getStartTime());
        subscribeCourse.setEndTime(teacherTime.getEndTime());
        subscribeCourse.setTeacherId(teacherTime.getTeacherId());
        subscribeCourse.setTeacherName(teacherTime.getTeacherName());
      }

      /**
       * modified by komi 2017年4月21日14:31:16 查询这个时间和这个房间号是不是已经有预约房间了
       */
      TeacherTime paramObj = new TeacherTime();
      paramObj.setStartTime(subscribeCourse.getStartTime());
      paramObj.setEndTime(subscribeCourse.getEndTime());
      paramObj.setWebexRoomHostId(webexRoomHostId);
      int hasRoomNum = teacherTimeEntityDao.findCount(paramObj);
      if (hasRoomNum != 0) {
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg(webexRoomHostId + "房间的" + subscribeCourse.getStartTime() + "到" + subscribeCourse
            .getEndTime() + "已被其他CC预约！");
        return json;
      }

      // 查询老师
      Teacher teacher = teacherEntityDao.findOneByKeyId(subscribeCourse.getTeacherId());

      // 不带事务的demo课校验
      json = baseSubscribeCourseCheckService
          .checkSubscribeCourseType4WithoutTransaction(subscribeCourse,
              teacher, webexRoomHostId);
      if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
        return json;
      }

      // 带事物的demo课预约
      json = baseSubscribeCourseTransactionService
          .subscribeCourseType4WithTransaction(subscribeCourse, teacher.getThirdFrom(),
              webexRoomHostId, webexMeetingKey);
    } catch (HuanxunException ex) {
      // modify by seven 这样会发生死锁
      // 将删除的方法放到预约事物外面,如果觉的不好就在接收到303返回值得时候跑个消息队列之类的来做这件事情
      teacherTimeService.deleteOnlyUseInSubscribe(subscribeCourse.getTeacherTimeId());
      throw ex;
    }

    // 发送微信推送给老师
    Map<String, Object> dataMap = new HashMap<String, Object>();
    // 下发消息的消息体，根据不同的模板，不一样的写法
    dataMap.put("first",
        new WeixinMsgOption("Dear teacher,the lesson below is booked.Please noted."));
    // 课程名称
    dataMap.put("keyword1", new WeixinMsgOption(subscribeCourse.getCourseTitle() + "("
        + ((CourseType) MemcachedUtil.getValue(subscribeCourse.getCourseType()))
            .getCourseTypeChineseName()
        + ")[Booking By " + adminUserName + "]"));
    // 时间
    dataMap.put("keyword2",
        new WeixinMsgOption(CalendarUtil.formatTeacherWechatCourseTime(subscribeCourse
            .getStartTime(), subscribeCourse.getEndTime())));
    dataMap.put("remark", new WeixinMsgOption("About the student:" + subscribeCourse
        .getSubscribeRemark()));

    teacherWeixinService.sendWeixinMsgToTeacher(dataMap, subscribeCourse.getTeacherId(),
        WeixinConstant.getWeixinMsgTemplateIdSubscribe());

    return json;
  }

  /**
   * 
   * Title: demo课取消预约<br>
   * Description: demo课取消预约<br>
   * CreateDate: 2016年12月27日 上午11:43:04<br>
   * 
   * @category demo课取消预约
   * @author seven.gz
   * @param subscribeCourseParam
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject cancelDemoSubscribeEntry(SubscribeCourse subscribeCourseParam)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // 查询预约对象
    SubscribeCourse subscribeCourse = subscribeCourseEntityDao
        .findOneByKeyIdForUpdate(subscribeCourseParam.getKeyId());

    if (subscribeCourse == null) {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_COURSE_NOT_EXIST.getCode());
      return json;
    }

    // 设置参数
    subscribeCourse.setUpdateUserId(subscribeCourseParam.getUpdateUserId());
    subscribeCourse.setSubscribeFrom(subscribeCourseParam.getSubscribeFrom());
    subscribeCourseParam.setCourseType(subscribeCourse.getCourseType());

    // 查询老师
    Teacher teacher = teacherEntityDao.findOneByKeyId(subscribeCourse.getTeacherId());

    // 不带事务校验
    json = baseSubscribeCourseCheckService
        .checkCancelSubscribeCourseType4WithoutTransaction(subscribeCourse, teacher);

    if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
      return json;
    }

    // courseType4的取消事务入口
    json = baseSubscribeCourseTransactionService
        .cancelSubscribeCourseType4WithTransaction(subscribeCourse, teacher.getThirdFrom());

    String courseType = subscribeCourseParam.getCourseType();

    /**
     * 下发短信业务逻辑
     */
    // 4.发送取消预约成功短信
    CourseType courseTypeObj = ((CourseType) MemcachedUtil.getValue(courseType));
    // 发送短信{0}同学,您取消了开课时间为{1}的外教{2},祝您生活愉快
    SmsUtil.sendSmsToQueue(subscribeCourseParam.getUserPhone(),
        MessageFormat.format(MemcachedUtil.getConfigValue("cancel_subscribe_success_message"),
            new String[] { subscribeCourse.getUserName(),
                DateUtil.dateToStrYYMMDDHHMM((Date) subscribeCourse.getStartTime()),
                courseTypeObj.getCourseTypeChineseName() }));

    /**
     * 下发微信消息业务逻辑
     */
    sendCancelSubscribeWeixinMsg(subscribeCourse);

    return json;
  }

  /**
   * 
   * Title: 获取会议号<br>
   * Description: findWebexMeetingKey<br>
   * CreateDate: 2017年7月26日 下午6:12:50<br>
   * 
   * @category 获取会议号
   * @author seven.gz
   * @param webexRoomHostId
   *          房间号
   * @param teacherTime
   *          老师时间
   */
  public static String findWebexMeetingKey(String webexRoomHostId, TeacherTime teacherTime) {
    // 判断是否有配置可用的房间会议
    JSONObject returnJson = null;
    // 获取json配置
    String roomSet = MemcachedUtil.getConfigValue(webexRoomHostId);
    if (!StringUtils.isEmpty(roomSet)) {
      returnJson = JSONObject.fromObject(roomSet);
      JSONArray returnJsonArray = returnJson.getJSONArray("timeRangeList");
      // 根据时间判断是哪个会议号
      if (returnJsonArray != null && returnJsonArray.size() > 0) {
        JSONObject arrayJson = null;
        for (int i = 0; i < returnJsonArray.size(); i++) {
          arrayJson = returnJsonArray.getJSONObject(i);
          String timeRangeStart = arrayJson.getString("timeRangeStart");
          String timeRangeEnd = arrayJson.getString("timeRangeEnd");

          // 如果预约的时间在配置的时间段内就用此会议号
          if (DateUtil.strToDateYYYYMMDDHHMMSS(DateUtil.dateToStrYYMMDD(teacherTime.getStartTime())
              + " " + timeRangeStart + ":00").getTime() <= teacherTime.getStartTime().getTime()
              && DateUtil
                  .strToDateYYYYMMDDHHMMSS(DateUtil.dateToStrYYMMDD(teacherTime.getStartTime())
                      + " " + timeRangeEnd + ":00").getTime() >= teacherTime.getEndTime()
                          .getTime()) {
            return arrayJson.getString("webexMeetingKey");
          }
        }
      }
    }
    return null;
  }

}