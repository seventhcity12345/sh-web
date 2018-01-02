/** 
 * File: BaseSubscribeCourseTransactionService.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.subscribecourse.service<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年9月29日 下午12:20:14
 * @author yangmh
 */

package com.webi.hwj.subscribecourse.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;
import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.WebexConstant;
import com.webi.hwj.course.dao.CourseOne2ManySchedulingDao;
import com.webi.hwj.courseone2one.constant.CourseOneToOneSchedulingConstant;
import com.webi.hwj.huanxun.exception.HuanxunException;
import com.webi.hwj.huanxun.service.HuanxunService;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.teacher.dao.TeacherDao;
import com.webi.hwj.teacher.dao.TeacherTimeDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.webex.service.WebexSubscribeService;

/**
 * Title: 带事务控制的预约逻辑.<br>
 * Description: BaseSubscribeCourseTransactionService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年9月29日 下午12:20:14
 * 
 * @author yangmh
 */
@Service
public class BaseSubscribeCourseTransactionService {
  private static Logger logger = Logger.getLogger(BaseSubscribeCourseTransactionService.class);

  @Resource
  BaseSubscribeCourseCheckService baseSubscribeCourseCheckService;
  @Resource
  BaseSubscribeCourseService baseSubscribeCourseService;
  @Resource
  HuanxunService huanxunService;
  @Resource
  OrderCourseDao orderCourseDao;
  @Resource
  UserDao userDao;
  @Resource
  TeacherDao teacherDao;
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  TeacherTimeService teacherTimeService;
  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;
  @Resource
  CourseOne2ManySchedulingDao courseOne2ManySchedulingDao;
  @Resource
  SubscribeCourseDao subscribeCourseDao;
  @Resource
  TeacherTimeDao teacherTimeDao;
  @Resource
  UserEntityDao userEntityDao;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEnityDao;
  @Resource
  WebexSubscribeService webexSubscribeService;

  /**
   * Title: courseType2的取消事务入口.<br>
   * Description: cancelSubscribeCourseType2WithTransaction<br>
   * CreateDate: 2016年9月29日 下午1:13:05<br>
   * 
   * @category courseType2的取消事务入口
   * @author yangmh
   * @param subscribeCourse
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject cancelSubscribeCourseType2WithTransaction(SubscribeCourse subscribeCourse)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 3.通用带事务校验

    // 4.个体带事务校验

    // 5.个体提交带事务
    submitCancelSubscribeCourseType2WithTransaction(subscribeCourse);

    // 6.通用提交带事务
    submitCancelSubscribeCourseCommonWithTransaction(subscribeCourse);

    return json;
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject cancelSubscribeCourseType1WithTransaction(SubscribeCourse subscribeCourse)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 3.通用带事务校验

    // 4.个体带事务校验

    // 5.个体提交带事务
    submitCancelSubscribeCourseType1WithTransaction(subscribeCourse);

    // 6.通用提交带事务
    submitCancelSubscribeCourseCommonWithTransaction(subscribeCourse);
    return json;

  }

  /**
   * Title: 取消预约通用提交带事务.<br>
   * Description: submitCancelSubscribeCourseCommonWithTransaction<br>
   * CreateDate: 2016年9月22日 下午6:27:48<br>
   * 
   * @category 取消预约通用提交带事务.
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,
      rollbackFor = { Exception.class })
  public void submitCancelSubscribeCourseCommonWithTransaction(SubscribeCourse subscribeCourse)
      throws Exception {
    Date startTime = subscribeCourse.getStartTime();
    Date endTime = subscribeCourse.getEndTime();

    // 1.删除预约表数据+更新操作人
    Map<String, Object> updateSubscribeMap = new HashMap<String, Object>();
    updateSubscribeMap.put("key_id", subscribeCourse.getKeyId());
    updateSubscribeMap.put("update_user_id", subscribeCourse.getUpdateUserId());
    updateSubscribeMap.put("is_used", 0);
    subscribeCourseDao.updateSubscribeUserIdByUserId(updateSubscribeMap);

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("order_course_option_id", subscribeCourse.getOrderOptionId());

    // 2.给学员的课程数+1
    orderCourseDao.updateCourseCount(paramMap, true);

    // modified by alex.yang 2015年12月28日08:22:48
    Map<String, Object> teacherMap = teacherDao.findOneByKeyId(subscribeCourse.getTeacherId(),
        "third_from");

    String courseType = subscribeCourse.getCourseType();

    if (teacherMap != null && "huanxun".equals(teacherMap.get("third_from"))
    // modify by seven 2016年8月26日18:14:11 取消预约大课环迅老师不用调用
        && !"course_type2".equals(courseType)
        && !"course_type5".equals(courseType)
        && !"course_type8".equals(courseType)) {
      // 如果是环迅的老师，需要调用环迅的接口
      paramMap.put("start_time", startTime);
      paramMap.put("end_time", endTime);
      paramMap.put("user_id", subscribeCourse.getUserId());
      String returnCode = huanxunService.huanxunCancel(paramMap);
      if (!"200".equals(returnCode)) {
        throw new HuanxunException("取消预约环迅老师出错!");
      }
    }

    // 3.需要判断这节课是否已经全部被取消预约完毕，如果是的话，则需要把teacherTime表里的is_subscribe,is_confirm设置为0
    // modified by alex 2016年4月27日 17:55:05
    String teacherTimeId = subscribeCourse.getTeacherTimeId();
    int subscribeCourseCount = subscribeCourseDao.findCountSubscribeByTeacherTimeId(teacherTimeId);
    if (subscribeCourseCount == 0) {
      teacherTimeDao.cancelSubscribeTeacherTime(teacherTimeId);
    }

    // 注意！目前有2个上课平台，gensee和webex，gensee都是ES,OC这种大课，不需要去调用异步取消接口。而webex更是不需要取消，因此取消预约不用异步。
  }

  /**
   * Title: 取消预约ext1v6提交带事务.<br>
   * Description: submitCancelSubscribeCourseType2WithTransaction<br>
   * CreateDate: 2016年9月22日 下午5:38:33<br>
   * 
   * @category 取消预约core1v1提交带事务
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public void submitCancelSubscribeCourseType2WithTransaction(SubscribeCourse subscribeCourse)
      throws Exception {
    // 1.多课程表t_course_one2many_scheduling中 already_person_count减少1
    courseOne2ManySchedulingDao
        .updateOne2ManyAlreadyPersonCountByCourseId(subscribeCourse.getCourseId());
  }

  /**
   * Title: 取消预约core1v1提交带事务.<br>
   * Description: submitCancelSubscribeCourseType1WithTransaction<br>
   * CreateDate: 2016年9月22日 下午5:34:31<br>
   * 
   * @category 取消预约core1v1提交带事务
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public void submitCancelSubscribeCourseType1WithTransaction(SubscribeCourse subscribeCourse)
      throws Exception {
    // 1.将老师时间表的状态设置为可预约状态(只有1v1和主题课的取消预约才用重置老师的is_subscribe,is_confirm)
    // modified by alex.yang 2015年10月23日21:13:19
    teacherTimeDao.cancelSubscribeTeacherTime(subscribeCourse.getTeacherTimeId());
  }

  /**
   * Title: courseType2的预约事务入口<br>
   * Description: subscribeCourseType2WithTransaction<br>
   * CreateDate: 2016年9月29日 下午1:14:10<br>
   * 
   * @category courseType2的预约事务入口
   * @author yangmh
   * @param subscribeCourse
   * @param sessionUser
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject subscribeCourseType2WithTransaction(SubscribeCourse subscribeCourse,
      SessionUser sessionUser)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // 3.个体带事务校验
    json = baseSubscribeCourseCheckService
        .checkSubscribeCourseType2WithTransaction(subscribeCourse);
    if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
      return json;
    }

    // 4.通用带事务校验
    json = baseSubscribeCourseCheckService
        .checkSubscribeCourseCommonWithTransaction(subscribeCourse, sessionUser.getCourseTypes());

    if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
      return json;
    }

    // 5.个体提交带事务
    submitSubscribeCourseType2WithTransaction(subscribeCourse);

    // 6.通用提交带事务
    String subscribeId = submitSubscribeCourseCommonWithTransaction(subscribeCourse);

    json.setData(subscribeId);

    return json;
  }

  /**
   * Title: courseType8的预约事务入口.<br>
   * Description: subscribeCourseType8WithTransaction<br>
   * CreateDate: 2016年9月29日 下午1:14:44<br>
   * 
   * @category courseType8的预约事务入口
   * @author yangmh
   * @param subscribeCourse
   * @param sessionUser
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject subscribeCourseType8WithTransaction(SubscribeCourse subscribeCourse,
      SessionUser sessionUser)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // 3.通用带事务校验
    json = baseSubscribeCourseCheckService
        .checkSubscribeCourseCommonWithTransaction(subscribeCourse, sessionUser.getCourseTypes());

    if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
      return json;
    }

    // 4.个体带事务校验

    // 5.个体提交带事务,注意这里目前和ext1v6是一样的，如果以后不一样请再拆分方法
    submitSubscribeCourseType2WithTransaction(subscribeCourse);

    // 6.通用提交带事务
    String subscribeId = submitSubscribeCourseCommonWithTransaction(subscribeCourse);

    json.setData(subscribeId);

    return json;
  }

  /**
   * Title: ext1v6预约提交带事务.<br>
   * Description: submitSubscribeCourseType2WithTransaction<br>
   * CreateDate: 2016年9月22日 下午6:38:25<br>
   * 
   * @category ext1v6预约提交带事务
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public void submitSubscribeCourseType2WithTransaction(SubscribeCourse subscribeCourse)
      throws Exception {
    courseOne2ManySchedulingDao
        .updateAlreadyPersonCount(subscribeCourse.getCourseId());
  }

  /**
   * Title: 带事务的course_type1的入口.<br>
   * Description: subscribeCourseType1WithTransaction<br>
   * CreateDate: 2016年9月29日 下午12:21:39<br>
   * 
   * @category subscribeCourseType1WithTransaction
   * @author yangmh
   * @param subscribeCourse
   * @param sessionUser
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject subscribeCourseType1WithTransaction(SubscribeCourse subscribeCourse,
      SessionUser sessionUser)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // 3.个体带事务校验
    json = baseSubscribeCourseCheckService
        .checkSubscribeCourseType1WithTransaction(subscribeCourse.getTeacherTimeId());

    if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
      return json;
    }

    // 4.通用带事务校验
    json = baseSubscribeCourseCheckService
        .checkSubscribeCourseCommonWithTransaction(subscribeCourse, sessionUser.getCourseTypes());

    if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
      return json;
    }

    // 5.个体提交带事务

    // 6.通用提交带事务
    String subscribeId = submitSubscribeCourseCommonWithTransaction(subscribeCourse);

    json.setData(subscribeId);

    return json;
  }

  /**
   * Title: 预约通用提交带事务.<br>
   * Description: submitSubscribeCourseCommonWithTransaction<br>
   * CreateDate: 2016年9月22日 下午6:46:09<br>
   * 
   * @category 预约通用提交带事务
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   * @return 预约id
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,
      rollbackFor = { Exception.class })
  public String submitSubscribeCourseCommonWithTransaction(SubscribeCourse subscribeCourse)
      throws Exception {

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("order_course_option_id", subscribeCourse.getOrderOptionId());

    // 1.减少合同里的数量 （都需要判断合同里是否有可用的合同子表count. ）
    orderCourseDao.updateCourseCount(paramMap, false);

    // 2.插入预约数据
    String userId = subscribeCourse.getUserId();
    Map<String, Object> userMap = userDao.findOneByKeyId(userId,
        "phone,user_code");
    subscribeCourse.setUserPhone(userMap.get("phone").toString());

    // modify by seven 2016年10月26日20:22:51 判断是否是首课
    SubscribeCourse subscribeCountParam = new SubscribeCourse();
    subscribeCountParam.setUserId(userId);
    int subscribeCount = adminSubscribeCourseDao.findCount(subscribeCountParam);
    if (subscribeCount == 0) {
      subscribeCourse.setIsFirst(true);
    }

    adminSubscribeCourseDao.insert(subscribeCourse);

    /**
     * modify by athrun.cw 2016年5月23日16:47:02 bug344 学员前台预约课程和LC后台预约课程的日志输出不一致
     * 添加了公共的预约id日志输出
     */
    logger.info("学员user_id [" + userId + "] 预约课程成功，预约subscribe_id ["
        + subscribeCourse.getKeyId() + "]...");

    // 3.更新老师状态
    TeacherTime teacherTimeParam = new TeacherTime();
    teacherTimeParam.setKeyId(subscribeCourse.getTeacherTimeId());
    teacherTimeParam.setIsSubscribe(true);
    teacherTimeEntityDao.update(teacherTimeParam);

    // modified by alex.yang 2015年12月28日08:22:48
    Map<String, Object> teacherMap = teacherDao.findOneByKeyId(subscribeCourse.getTeacherId(),
        "third_from");
    String courseType = subscribeCourse.getCourseType();
    if (teacherMap != null && "huanxun".equals(teacherMap.get("third_from"))
        && ("course_type1".equals(courseType) || "course_type9".equals(courseType))) {
      // modify by seven 2016年8月26日18:14:11 预约大课环迅老师不用调用
      // 如果是环迅的老师，需要调用环迅的接口
      Map<String, Object> huanxunParamMap = new HashMap<String, Object>();
      huanxunParamMap.put("course_type", courseType);
      huanxunParamMap.put("teacher_id", subscribeCourse.getTeacherId());
      huanxunParamMap.put("start_time", subscribeCourse.getStartTime());
      huanxunParamMap.put("end_time", subscribeCourse.getEndTime());
      huanxunParamMap.put("course_courseware", subscribeCourse.getCourseCourseware());
      huanxunParamMap.put("user_id", subscribeCourse.getUserId());
      huanxunParamMap.put("user_name", subscribeCourse.getUserName());

      String returnCode = huanxunService.huanxunBook(huanxunParamMap);

      // 如果是老师已被占用，则把韦博侧的老师时间等相关排课数据逻辑删除。
      if ("303".equals(returnCode)) {
        throw new HuanxunException("老师已经被占用!");
      } else if (!"200".equals(returnCode)) {
        throw new RuntimeException("预约环迅老师出错!");
      }
    }

    if (!"course_type8".equals(courseType) && !"course_type5".equals(courseType)) {
      try {

        // 5.生产消息------>房间预约
        logger.info("create msg queue------>begin");

        // webex房间预约逻辑,在预约时，去webex获取join url
        // 异步调用生产者,生产webex会议join_url
        // 延迟1分钟消费
        String createWebexMeetingJoinUrlBodyStr =
            WebexConstant.WEBEX_CONSUMER_TYPE_CREATE_MEETING_JOIN_URL
                + "," + subscribeCourse.getKeyId() + "," + subscribeCourse.getUserName()
                + userMap.get("user_code");
        OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
            MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_webex",
            createWebexMeetingJoinUrlBodyStr, 1 * 60 * 1000);

        logger.info("create msg queue------>end");

      } catch (Exception ex) {
        SmsUtil.sendAlarmSms(
            "webex消息队列报警,预约时无法生产消息,subscribe,"
                + subscribeCourse.getKeyId());

        ex.printStackTrace();
        logger.error("error:" + ex.toString());
        throw new RuntimeException("webex消息队列报警,预约时无法生产消息");
      }
    } else {
      // 下发预约成功微信消息
      baseSubscribeCourseService.sendSubscribeWeixinMsg(subscribeCourse);
    }

    return subscribeCourse.getKeyId();
  }

  /**
   * 
   * Title: demo课预约提交<br>
   * Description: 这里和上面有些重复的地方时间不太够 就没有抽出来先时间功能 <br>
   * CreateDate: 2016年12月23日 下午5:15:44<br>
   * 
   * @category demo课预约提交
   * @author seven.gz
   * @param subscribeCourse
   * @param thirdFrom
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,
      rollbackFor = { Exception.class })
  public CommonJsonObject submitSubscribeCourseCourseType4WithTransaction(
      SubscribeCourse subscribeCourse,
      String thirdFrom, String webexRoomHostId, String webexMeetingKey) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String userId = subscribeCourse.getUserId();
    // 插入预约数据
    User user = userEntityDao.findOneByKeyId(userId);
    subscribeCourse.setUserPhone(user.getPhone());
    subscribeCourse.setUserLevel(user.getCurrentLevel());

    SubscribeCourse subscribeCountParam = new SubscribeCourse();
    subscribeCountParam.setUserId(userId);
    adminSubscribeCourseDao.insert(subscribeCourse);

    // 更新老师状态
    TeacherTime teacherTimeParam = new TeacherTime();
    teacherTimeParam.setKeyId(subscribeCourse.getTeacherTimeId());
    teacherTimeParam.setIsSubscribe(true);

    // modify by seven 2017年4月11日18:49:44 demo课需要输入cc安排的房间号和会议号
    teacherTimeParam.setWebexRoomHostId(webexRoomHostId);
    teacherTimeParam.setWebexMeetingKey(webexMeetingKey);
    teacherTimeEntityDao.update(teacherTimeParam);

    // 环迅老师预约
    String courseType = subscribeCourse.getCourseType();
    if ("huanxun".equals(thirdFrom)) {
      // 如果是环迅的老师，需要调用环迅的接口
      Map<String, Object> huanxunParamMap = new HashMap<String, Object>();
      huanxunParamMap.put("course_type", courseType);
      huanxunParamMap.put("teacher_id", subscribeCourse.getTeacherId());
      huanxunParamMap.put("start_time",
          DateUtil.dateToStrYYMMDDHHMMSS(subscribeCourse.getStartTime()));
      huanxunParamMap.put("end_time", DateUtil.dateToStrYYMMDDHHMMSS(subscribeCourse.getEndTime()));
      huanxunParamMap.put("course_courseware", subscribeCourse.getCourseCourseware());
      huanxunParamMap.put("user_id", subscribeCourse.getUserId());
      huanxunParamMap.put("user_name", subscribeCourse.getUserName());

      String returnCode = huanxunService.huanxunBook(huanxunParamMap);

      // 如果是老师已被占用，则把韦博侧的老师时间等相关排课数据逻辑删除。
      if ("303".equals(returnCode)) {
        throw new HuanxunException("老师已经被占用!");
      } else if (!"200".equals(returnCode)) {
        throw new RuntimeException("预约环迅老师出错!");
      }
    }

    // modify by seven 2017年4月11日18:41:28 demo课预约不需要join_url
    try {

      // 5.生产消息------>房间预约
      logger.info("create msg queue------>begin");

      // webex房间预约逻辑,在预约时，去webex获取join url
      // 异步调用生产者,生产webex会议join_url
      // 延迟1分钟消费
      String createWebexMeetingJoinUrlBodyStr =
          WebexConstant.WEBEX_CONSUMER_TYPE_CREATE_MEETING_JOIN_URL
              + "," + subscribeCourse.getKeyId() + "," + subscribeCourse.getUserName()
              + user.getUserCode();
      OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
          MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_webex",
          createWebexMeetingJoinUrlBodyStr, 1 * 60 * 1000);

      logger.info("create msg queue------>end");

    } catch (Exception ex) {
      SmsUtil.sendAlarmSms(
          "webex消息队列报警,预约时无法生产消息,subscribe,"
              + subscribeCourse.getKeyId());

      ex.printStackTrace();
      logger.error("error:" + ex.toString());
      throw new RuntimeException("webex消息队列报警,预约时无法生产消息");
    }

    json.setData(subscribeCourse.getKeyId());
    return json;
  }

  /**
   * 
   * Title: 带事物的demo预约<br>
   * Description: subscribeCourseType4WithTransaction<br>
   * CreateDate: 2016年12月23日 下午6:37:40<br>
   * 
   * @category 带事物的demo预约
   * @author seven.gz
   * @param subscribeCourse
   * @param thirdFrom
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject subscribeCourseType4WithTransaction(SubscribeCourse subscribeCourse,
      String thirdFrom, String webexRoomHostId, String webexMeetingKey)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // 带事务的demo课校验
    json = baseSubscribeCourseCheckService
        .checkSubscribeCourseType4WithTransaction(subscribeCourse);
    if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
      return json;
    }
    // 预约提交
    // courseType4的预约事务入口
    json = submitSubscribeCourseCourseType4WithTransaction(subscribeCourse, thirdFrom,
        webexRoomHostId, webexMeetingKey);

    return json;
  }

  /**
   * 
   * Title: demo课取消预约<br>
   * Description: demo课取消预约<br>
   * CreateDate: 2016年12月27日 上午11:27:49<br>
   * 
   * @category demo课取消预约
   * @author seven.gz
   * @param subscribeCourse
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject cancelSubscribeCourseType4WithTransaction(SubscribeCourse subscribeCourse,
      String thirdFrom)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // 1.删除预约表数据+更新操作人
    subscribeCourseEnityDao.updateSubscribeUserIdByUserId(subscribeCourse.getKeyId(),
        subscribeCourse.getUpdateUserId(), false);

    if ("huanxun".equals(thirdFrom)) {
      // 如果是环迅的老师，需要调用环迅的接口
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("start_time", DateUtil.dateToStrYYMMDDHHMMSS(subscribeCourse.getStartTime()));
      paramMap.put("end_time", DateUtil.dateToStrYYMMDDHHMMSS(subscribeCourse.getEndTime()));
      paramMap.put("user_id", subscribeCourse.getUserId());
      String returnCode = huanxunService.huanxunCancel(paramMap);
      if (!"200".equals(returnCode)) {
        throw new HuanxunException("取消预约环迅老师出错!");
      }
    }

    // modify by seven 2017年4月11日19:55:54 取消demo课需要重新排入房间
    cancelSubscribeTeacherTime(subscribeCourse.getTeacherTimeId());

    return json;

  }

  /**
   * 
   * Title: 取消demo课需要重新排入房间<br>
   * Description: cancelSubscribeTeacherTime<br>
   * CreateDate: 2017年4月11日 下午7:54:36<br>
   * 
   * @category 取消demo课需要重新排入房间
   * @author seven.gz
   * @param teacherTimeId
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  private void cancelSubscribeTeacherTime(String teacherTimeId) throws Exception {
    TeacherTime teacherTime = teacherTimeEntityDao.findOneByKeyId(teacherTimeId);

    teacherTime.setIsConfirm(false);
    teacherTime.setIsSubscribe(false);

    // 因为排课房间要有预留时间所以查询时要再课程时间基础上留出这个时间
    Calendar calStartClassTimeCalendar = Calendar.getInstance();
    calStartClassTimeCalendar.setTime(teacherTime.getStartTime());
    calStartClassTimeCalendar.add(Calendar.MINUTE,
        -CourseOneToOneSchedulingConstant.getVcubeRoomClassFrontTime(teacherTime.getCourseType()));

    Calendar calEndClassTimeCalendar = Calendar.getInstance();
    calEndClassTimeCalendar.setTime(teacherTime.getEndTime());
    calEndClassTimeCalendar.add(Calendar.MINUTE,
        CourseOneToOneSchedulingConstant.getVcubeRoomClassPostTime(teacherTime.getCourseType()));

    // 获得老师时间实体
    List<String> roomIdList = teacherTimeService.findWebexAvailableRoomList(
        WebexConstant.WEBEX_ROOM_TYPE_ONE2ONE,
        calStartClassTimeCalendar.getTime(), calEndClassTimeCalendar.getTime());
    if (roomIdList != null && roomIdList.size() > 0) {
      String roomId = roomIdList.get(0);
      teacherTime.setWebexRoomHostId(roomId);

      // 更新老师时间表
      teacherTimeEntityDao.update(teacherTime);

      // 同步生产webex会议
      webexSubscribeService.createWebexMeeting(teacherTime.getKeyId(),
          teacherTime.getTeacherName() + " "
              + DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getStartTime()) + " "
              + DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getEndTime()) + " ");
    } else {
      // 更新老师时间表
      teacherTime.setWebexRoomHostId("");
      teacherTime.setWebexMeetingKey("");
      teacherTimeEntityDao.update(teacherTime);
    }
  }
}
