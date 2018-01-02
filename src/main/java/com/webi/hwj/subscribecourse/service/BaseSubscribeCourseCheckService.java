package com.webi.hwj.subscribecourse.service;

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
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.ErrorConstant;
import com.webi.hwj.course.dao.CourseOne2ManySchedulingDao;
import com.webi.hwj.course.dao.CourseOne2OneDao;
import com.webi.hwj.course.util.CommentUtil;
import com.webi.hwj.courseextension1v1.dao.CourseExtension1v1Dao;
import com.webi.hwj.courseextension1v1.entity.CourseExtension1v1;
import com.webi.hwj.courseone2one.dao.AdminCourseOne2oneDao;
import com.webi.hwj.courseone2one.entity.CourseOne2One;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.coursetype.param.CourseTypeInfo;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.subscribecourse.constant.AdminSubscribeCourseConstant;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.SubscribeCourseListParam;
import com.webi.hwj.teacher.constant.AdminTeacherConstant;
import com.webi.hwj.teacher.dao.TeacherDao;
import com.webi.hwj.teacher.dao.TeacherTimeDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.user.dao.UserInfoEntityDao;
import com.webi.hwj.user.entity.UserInfo;
import com.webi.hwj.webex.dao.WebexRoomDao;
import com.webi.hwj.webex.entity.WebexRoom;

/**
 * Title: 预约校验相关service.<br>
 * Description: 预约校验相关service<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月8日 上午9:42:46
 * 
 * @author yangmh
 */
@Service
public class BaseSubscribeCourseCheckService {
  private static Logger logger = Logger.getLogger(BaseSubscribeCourseCheckService.class);

  @Resource
  TeacherTimeDao teacherTimeDao;

  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;

  @Resource
  TeacherDao teacherDao;

  @Resource
  CourseOne2OneDao courseOne2OneDao;

  @Resource
  TellmemoreService tellmemoreService;

  @Resource
  SubscribeCourseDao subscribeCourseDao;

  @Resource
  UserDao userDao;

  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;

  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  CourseOne2ManySchedulingDao courseOne2ManySchedulingDao;

  @Resource
  CourseExtension1v1Dao courseExtension1v1Dao;

  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;

  @Resource
  AdminCourseOne2oneDao adminCourseOne2oneDao;

  @Resource
  UserInfoEntityDao userInfoEntityDao;

  @Resource
  WebexRoomDao webexRoomDao;

  /**
   * Title: core1v1不带事务校验.<br>
   * Description: checkSubscribeCourseType1WithoutTransaction<br>
   * CreateDate: 2016年9月22日 下午6:33:21<br>
   * 
   * @category core1v1不带事务校验
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   * @return CommonJsonObject对象
   */
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public CommonJsonObject checkSubscribeCourseType1WithoutTransaction(
      SubscribeCourse subscribeCourse) throws Exception {
    String userId = subscribeCourse.getUserId();
    String courseId = subscribeCourse.getCourseId();
    String courseType = subscribeCourse.getCourseType();

    CommonJsonObject json = new CommonJsonObject();
    // 判断course_id是否合法
    // 临时写法，为了以后把course表合并做准备

    if ("course_type9".equals(courseType)) {
      CourseExtension1v1 courseExtension1v1 = courseExtension1v1Dao.findOneByKeyId(courseId);
      if (courseExtension1v1 == null) {
        logger.error(ErrorConstant.COURSE_NOT_EXIST + "userId=" + userId);
        json.setCode(ErrorCodeEnum.COURSE_NOT_EXIST.getCode());
        return json;
      } else {
        subscribeCourse.setCategoryType(courseExtension1v1.getCategoryType());
        subscribeCourse.setCourseCourseware(courseExtension1v1.getCourseCourseware());
        subscribeCourse.setCourseId(courseExtension1v1.getKeyId());
        subscribeCourse.setCoursePic(courseExtension1v1.getCoursePic());
        subscribeCourse.setCourseTitle(courseExtension1v1.getCourseTitle());
      }
    } else {
      Map<String, Object> one2OneCourseMap = courseOne2OneDao.findOneByKeyId(courseId,
          "category_type,course_courseware,key_id,course_pic,course_title");
      if (one2OneCourseMap == null) {
        logger.error(ErrorConstant.COURSE_NOT_EXIST + "userId=" + userId);
        json.setCode(ErrorCodeEnum.COURSE_NOT_EXIST.getCode());
        return json;
      } else {
        subscribeCourse.setCategoryType(one2OneCourseMap.get("category_type").toString());
        subscribeCourse.setCourseCourseware(one2OneCourseMap.get("course_courseware").toString());
        subscribeCourse.setCourseId(one2OneCourseMap.get("key_id").toString());
        subscribeCourse.setCoursePic(one2OneCourseMap.get("course_pic").toString());
        subscribeCourse.setCourseTitle(one2OneCourseMap.get("course_title").toString());
      }

      // 1.判断tmm课件是否完成，修改了调用tmm的代码，减少代码耦合性。
      // 临时写法，为了保证老代码不出问题
      JsonMessage jsonMessage = tellmemoreService.judgeUserPercentComplete(courseId, userId);
      if (!jsonMessage.isSuccess()) {
        json.setCode(ErrorCodeEnum.RSA_NOT_COMPLETE_COURSE_PERCENT.getCode());
      }
    }

    return json;
  }

  /**
   * Title: ext1v6预约带事务校验.<br>
   * Description: checkSubscribeCourseType2WithTransaction<br>
   * CreateDate: 2016年9月22日 下午6:37:01<br>
   * 
   * @category ext1v6预约带事务校验
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   * @return CommonJsonObject对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject checkSubscribeCourseType2WithTransaction(SubscribeCourse subscribeCourse)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String userId = subscribeCourse.getUserId();
    String courseId = subscribeCourse.getCourseId();

    // 1.判断course_id是否合法
    // Map<String, Object> one2ManyCourseMap =
    // courseOne2ManySchedulingDao.findOneByKeyId(courseId,
    // "key_id,category_type,course_courseware,course_pic,course_title,already_person_count,limit_number,course_type");
    Map<String, Object> one2ManyCourseMap = courseOne2ManySchedulingDao
        .findOneByKeyIdLock(courseId);
    if (one2ManyCourseMap == null) {
      logger.error(ErrorConstant.COURSE_NOT_EXIST + "userId=" + userId);
      json.setCode(ErrorCodeEnum.COURSE_NOT_EXIST.getCode());
      return json;
    } else {
      subscribeCourse.setCategoryType(one2ManyCourseMap.get("category_type").toString());
      subscribeCourse.setCourseCourseware(one2ManyCourseMap.get("course_courseware").toString());
      subscribeCourse.setCourseId(one2ManyCourseMap.get("key_id").toString());
      subscribeCourse.setCoursePic(one2ManyCourseMap.get("course_pic").toString());
      subscribeCourse.setCourseTitle(one2ManyCourseMap.get("course_title").toString());
    }

    // 2.最大人数限制在排课中的limit_number内
    int already_person_count = Integer
        .parseInt(one2ManyCourseMap.get("already_person_count").toString());
    int limit_max_count = Integer.parseInt(one2ManyCourseMap.get("limit_number").toString());

    if (limit_max_count <= already_person_count) {
      // modified by seven 2016年8月6日12:08:16 修改日志级别
      logger.info(ErrorConstant.SUBSCRIBE_ONE2MANY_MAX_PERSONCOUNT_LIMIT + ",userId=" + userId);
      json.setCode(ErrorCodeEnum.SUBSCRIBE_ONE2MANY_MAX_PERSONCOUNT_LIMIT.getCode());
      return json;
    }
    return json;
  }

  /**
   * Title: es预约不带事务校验.<br>
   * Description: checkSubscribeCourseType8WithoutTransaction<br>
   * CreateDate: 2016年9月22日 下午6:44:34<br>
   * 
   * @category es预约不带事务校验
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   * @return CommonJsonObject对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject checkSubscribeCourseType8WithoutTransaction(
      SubscribeCourse subscribeCourse)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String userId = subscribeCourse.getUserId();
    String courseId = subscribeCourse.getCourseId();

    // 1.判断course_id是否合法
    Map<String, Object> one2ManyCourseMap = courseOne2ManySchedulingDao.findOneByKeyId(courseId,
        "key_id,category_type,course_courseware,course_pic,course_title,already_person_count,limit_number,course_type");
    if (one2ManyCourseMap == null) {
      logger.error(ErrorConstant.COURSE_NOT_EXIST + "userId=" + userId);
      json.setCode(ErrorCodeEnum.COURSE_NOT_EXIST.getCode());
      return json;
    } else {
      subscribeCourse.setCategoryType(one2ManyCourseMap.get("category_type").toString());
      subscribeCourse.setCourseCourseware(one2ManyCourseMap.get("course_courseware").toString());
      subscribeCourse.setCourseId(one2ManyCourseMap.get("key_id").toString());
      subscribeCourse.setCoursePic(one2ManyCourseMap.get("course_pic").toString());
      subscribeCourse.setCourseTitle(one2ManyCourseMap.get("course_title").toString());
    }
    return json;
  }

  /**
   * Title: core1v1带事务校验.<br>
   * Description: checkSubscribeCourseType1WithTransaction<br>
   * CreateDate: 2016年9月22日 下午6:34:34<br>
   * 
   * @category core1v1带事务校验
   * @author yangmh
   * @param teacherTimeId
   *          老师时间id
   * @return CommonJsonObject对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject checkSubscribeCourseType1WithTransaction(String teacherTimeId)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    TeacherTime teacherTime = teacherTimeEntityDao.findOneByKeyIdForUpdate(teacherTimeId);

    // 0.判断当前的teacher_time_id 是否瞬间被别人预约掉了。
    if (teacherTime.getIsSubscribe()) {
      // 已经被人预约了（该时间段已经被其他人预约了！）
      // modified by seven 2016年8月6日12:08:16 修改日志级别
      json.setCode(ErrorCodeEnum.TEACHER_TIME_ALREADY_SUBSCRIBE.getCode());
      return json;
    }

    return json;
  }

  /**
   * Title: 通用预约不带事务校验.<br>
   * Description: checkSubscribeCourseCommonWithoutTransaction<br>
   * CreateDate: 2016年9月22日 下午6:32:42<br>
   * 
   * @category 通用预约不带事务校验
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   * @return CommonJsonObject对象
   */
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public CommonJsonObject checkSubscribeCourseCommonWithoutTransaction(
      SubscribeCourse subscribeCourse) throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    String userId = subscribeCourse.getUserId();

    // 1.必须要先设置英文名才能预约课程
    Map<String, Object> userInfoMap = userDao.findOneByKeyId(userId, "t_user_info",
        "english_name");

    if (StringUtils.isEmpty(userInfoMap.get("english_name"))) {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_NO_ENGLISH_NAME.getCode());
      return json;
    } else {
      subscribeCourse.setUserName(userInfoMap.get("english_name").toString());
    }

    // modify by seven 增加对预约1v1+lecture课程数的限制
    // 2.如果预约1v1+lecture课程数 >= 限制的课程数 则不允许再预约
    if (!"course_type8".equals(subscribeCourse.getCourseType())
        && !"course_type5".equals(subscribeCourse.getCourseType())) {
      if (!checkSubscribeCourseCount(userId)) {
        logger.info(ErrorConstant.EXCEED_MAX_SUBSCRIBE_COURSE_COUNT + "userId=" + userId);
        json.setCode(ErrorCodeEnum.SUBSCRIBE_COUNT_NOT_ENOUGH.getCode());
        return json;
      }
    }

    return json;
  }

  /**
   * 
   * Title: 校验学员预约的课程数<br>
   * Description: 校验学员预约的课程数 未开始的1v1+lecture课程数 < 定好的最大课程数(暂定为4)<br>
   * CreateDate: 2016年8月11日 下午3:40:32<br>
   * 
   * @category 校验学员预约的课程数
   * @author seven.gz
   * @param userId
   * @return
   * @throws Exception
   */
  public boolean checkSubscribeCourseCount(String userId) throws Exception {
    // 码表中获取最大的1v1+lecture数
    int maxSubscribeCourseCount = Integer
        .valueOf(MemcachedUtil.getConfigValue("max_subscribe_course_count"));
    // 查询学员的未开始的预约数
    int subscribeCourseCount = adminSubscribeCourseDao.findSubscribeCourseCountBeforeStart(userId,
        new Date());
    return subscribeCourseCount < maxSubscribeCourseCount;
  }

  /**
   * Title: 预约通用带事务校验.<br>
   * Description: checkSubscribeCourseCommonWithTransaction<br>
   * CreateDate: 2016年9月19日 下午5:37:19<br>
   * 
   * @category 预约通用带事务校验
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   * @param courseTypes
   *          课程类型集合
   * @return CommonJsonObject对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,
      rollbackFor = { Exception.class })
  public CommonJsonObject checkSubscribeCourseCommonWithTransaction(
      SubscribeCourse subscribeCourse, Map<String, Object> courseTypes)
          throws Exception {

    CommonJsonObject json = new CommonJsonObject();

    String userId = subscribeCourse.getUserId();
    String courseType = subscribeCourse.getCourseType();

    Map<String, Object> paramMap = new HashMap<String, Object>();
    // 当前时间作为参数
    paramMap.put("curDate", new Date());
    paramMap.put("category_type", subscribeCourse.getCategoryType());
    paramMap.put("course_type", courseType);
    paramMap.put("user_id", subscribeCourse.getUserId());

    // 查询合同子表中的一个子表主键，为增加合同子表的课程数做准备，有多个合同也不怕，目前是拿最老的那条合同子表数据减课时。
    List<Map<String, Object>> orderCourseOptionList = orderCourseDao
        .findList(OrderCourseDao.FIND_NEED_SUBSCRIBE_ORDER_COURSE_OPTION_BY_USERID_FOR_UPDATE,
            paramMap);

    // 1.需要判断当前这个时间段，是否已经被自己预约。
    // 由当前的user_id 和 start_time && end_time查询数据库是否已经存在相同的预约时间段
    Map<String, Object> TimeparamMap = new HashMap<String, Object>();
    TimeparamMap.put("user_id", userId);
    TimeparamMap.put("start_time", subscribeCourse.getStartTime());
    TimeparamMap.put("end_time", subscribeCourse.getEndTime());

    Map<String, Object> allTypeSubscribeUserCourse = subscribeCourseDao
        .findAllTypeUserCourseByUserIdAndStartTimeAndEndTime(TimeparamMap);
    if (allTypeSubscribeUserCourse != null) {
      /*
       * modify by seven 2016年7月19日14:07:54 修改日志级别为info
       */
      logger.info(
          ErrorConstant.HAVE_SUBSCRIBE_COURSE_AT_THIS_TIME + "user_id=" + userId);
      json.setCode(ErrorCodeEnum.SUBSCRIBE_ALREADY_EXSIT.getCode());
      return json;
    }

    // modify by seven 根据用户的课程类型的单位，判断该用户是否可以订课
    CourseTypeInfo courseTypeInfo = null;
    if (courseTypes != null) {
      courseTypeInfo = (CourseTypeInfo) courseTypes.get(courseType);
    }
    // 如果通过时间判断就不用判断课时数了
    if (!CommentUtil.checkCourseTypeLimitTime(courseTypeInfo)) {
      // 1.判断是否有足够用的课时数
      if (orderCourseOptionList == null || orderCourseOptionList.size() == 0) {
        /**
         * modify by seven 2016年7月19日14:06:31 修改日志级别error 为info
         */
        logger.info(ErrorConstant.ORDERCOURSE_NOT_LEGAL_OR_OPTINES_COUNT_HAVE_DONE + "user_id="
            + userId);
        json.setCode(ErrorCodeEnum.ORDER_COURSE_COUNT_NOT_ENOUGH.getCode());
        return json;
      } else {
        Map<String, Object> orderCourseOption = orderCourseOptionList.get(0);
        subscribeCourse.setOrderOptionId(orderCourseOption.get("order_option_id").toString());
        subscribeCourse.setOrderId(orderCourseOption.get("order_id").toString());
      }
    } else {
      subscribeCourse.setOrderOptionId(courseTypeInfo.getOrderOptionId());
      subscribeCourse.setOrderId(courseTypeInfo.getOrderId());
    }

    /**
     * modified by komi 2016年7月15日11:25:40 增加后台管理员标识，预约不需要提前时间
     */
    if (!"admin".equals(subscribeCourse.getSubscribeFrom())) {
      /**
       * modify by athrun.cw 2016年5月27日11:25:49 预约时间的校验
       */
      Date startTime = subscribeCourse.getStartTime();
      // 上课前多久可以预约/取消课程(小时)
      long beforeLessionCanSubscribe = System.currentTimeMillis()
          + ((CourseType) MemcachedUtil.getValue(courseType)).getCourseTypeSubscribeTime() * 60
              * 1000;
      /**
       * modified by komi 2016年7月5日15:10:21 System.currentTimeMillis() +
       * beforeLessionCanSubscribe * 60 * 1000 改为直接传入限制时间（在外面计算）
       */
      if (startTime.getTime() < beforeLessionCanSubscribe) {
        logger.error("预约失败,必须在预约时间范围内才能预约~!" + "user_id=" + userId);
        json.setCode(ErrorCodeEnum.SUBSCRIBE_ERROR_TIME.getCode());
        return json;
      }
    }

    return json;
  }

  /**
   * Title: 取消预约通用不带事务校验.<br>
   * Description: checkCancelSubscribeCourseCommonWithoutTransaction<br>
   * CreateDate: 2016年9月22日 下午5:32:50<br>
   * 
   * @category 取消预约通用不带事务校验
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   * @return CommonJsonObject对象
   */
  public CommonJsonObject checkCancelSubscribeCourseCommonWithoutTransaction(
      SubscribeCourse subscribeCourse) {
    CommonJsonObject json = new CommonJsonObject();
    if (subscribeCourse == null) {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_COURSE_NOT_EXIST.getCode());
      return json;
    }

    // 0.如果要取消的课程已经超过了取消范围则不能被取消，要求当前时间<上课时间-x小时
    Date startTime = subscribeCourse.getStartTime();

    // 上课前多久可以预约/取消课程(小时)
    int beforeLessionCanSubscribe = ((CourseType) MemcachedUtil
        .getValue(subscribeCourse.getCourseType()))
            .getCourseTypeCancelSubscribeTime();

    /**
     * modified by komi 2016年7月15日11:23:57 增加后台管理员标识，取消预约不需要提前时间
     */
    if ("admin".equals(subscribeCourse.getSubscribeFrom())) {
      beforeLessionCanSubscribe = 0;
    }

    if (System
        .currentTimeMillis() >= (startTime.getTime() - beforeLessionCanSubscribe * 60 * 1000)) {
      // modify by seven 2017年5月23日13:56:48 修改日志级别
      logger.info("非法取消预约,取消预约时间不正确!" + "user_id=" + subscribeCourse.getUserId());
      json.setCode(ErrorCodeEnum.SUBSCRIBE_COURSE_CANCEL_WRONG_TIME.getCode());
    }

    return json;
  }

  /**
   * Title: 待评价列表<br>
   * Description: 待评价列表<br>
   * CreateDate: 2016年12月12日 下午3:30:01<br>
   * 
   * @category 待评价列表
   * @author komi.zsy
   * @param userId
   *          用户id
   * @return
   * @throws Exception
   */
  public List<SubscribeCourseListParam> findNotCommentList(String userId)
      throws Exception {
    // 查找用户已上课、未评价的1v1类型的课程列表
    List<SubscribeCourseListParam> subscribeCourseList = subscribeCourseEntityDao
        .findNotCommentList(userId);

    if (subscribeCourseList != null && subscribeCourseList.size() != 0) {
      logger.debug("用户 [" + userId + "] 的未评价课程：" + subscribeCourseList);
      // 遍历处理课程;
      for (SubscribeCourseListParam subscribeCourse : subscribeCourseList) {
        CourseType courseTypeObj = (CourseType) MemcachedUtil
            .getValue(subscribeCourse.getCourseType());
        subscribeCourse.setCourseTypeLimitNumber(courseTypeObj.getCourseTypeLimitNumber());
        subscribeCourse.setCourseTypeChineseName(courseTypeObj.getCourseTypeChineseName());
      }
    } else {
      return null;
    }

    return subscribeCourseList;
  }

  /**
   * 
   * Title: 不带事物的demo课校验<br>
   * Description: checkSubscribeCourseType4WithoutTransaction<br>
   * CreateDate: 2016年12月23日 下午5:01:10<br>
   * 
   * @category 不带事物的demo课校验
   * @author seven.gz
   * @param subscribeCourse
   * @param userId
   * @param teacher
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public CommonJsonObject checkSubscribeCourseType4WithoutTransaction(
      SubscribeCourse subscribeCourse, Teacher teacher, String webexRoomHostId)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String userId = subscribeCourse.getUserId();
    // 查询是否有英文名
    UserInfo userInfo = userInfoEntityDao.findOneByKeyId(userId);
    if (userInfo != null) {
      if (StringUtils.isEmpty(userInfo.getEnglishName())) {
        // 如果没有英文名
        subscribeCourse.setUserName("demoStudent");
      } else {
        subscribeCourse.setUserName(userInfo.getEnglishName());
      }
    } else {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_NO_ENGLISH_NAME.getCode());
      return json;
    }

    // 查询课程是否存在
    String courseId = subscribeCourse.getCourseId();
    CourseOne2One courseOne2One = adminCourseOne2oneDao.findOneByKeyId(courseId);
    if (courseOne2One == null) {
      logger.error(ErrorConstant.COURSE_NOT_EXIST + "userId=" + userId);
      json.setCode(ErrorCodeEnum.COURSE_NOT_EXIST.getCode());
      return json;
    } else {
      subscribeCourse.setCategoryType(courseOne2One.getCategoryType());
      subscribeCourse.setCourseCourseware(courseOne2One.getCourseCourseware());
      subscribeCourse.setCourseId(courseOne2One.getKeyId());
      subscribeCourse.setCoursePic(courseOne2One.getCoursePic());
      subscribeCourse.setCourseTitle(courseOne2One.getCourseTitle());
    }

    // modify by seven 2017年4月11日19:05:54 如果是demo课需要判断房间是否存在
    if ("course_type4".equals(subscribeCourse.getCourseType())) {
      WebexRoom webexRoom = webexRoomDao.findWebexRoomByHostIdWithNoIsUsed(webexRoomHostId);
      if (webexRoom == null) {
        json.setCode(ErrorCodeEnum.SUBSCRIBE_WEBEX_ROOM_NOT_EXIST.getCode());
        return json;
      }
    }

    // 环迅老师不需要判断时间，有环迅的报错判断
    if (!"huanxun".equals(teacher.getThirdFrom())) {

      Date startTime = subscribeCourse.getStartTime();
      CourseType courseTypeEnity = (CourseType) MemcachedUtil
          .getValue(subscribeCourse.getCourseType());
      // 上课前多久可以预约/取消课程(小时)
      int beforeLessionCanSubscribe =
          +courseTypeEnity
              .getCourseTypeSubscribeTime();

      // modify by seven 2017年4月21日11:05:49 demo预约时如果 来源是speakhi 工作性质是全职的老师
      // 提前预约时间为2小时（做特殊处理），其他按照数据库配置来
      // modify by seven 2017年7月17日 修改为 全职和买断都是两小时其他按数据库来走
      if (AdminTeacherConstant.TEACHER_TEACHER_JOB_TYPE_FULL_TIME == teacher
          .getTeacherJobType() || AdminTeacherConstant.TEACHER_TEACHER_JOB_TYPE_BUYOUT == teacher
              .getTeacherJobType()) {
        beforeLessionCanSubscribe =
            AdminSubscribeCourseConstant.DEMO_SUBSCRIBE_TIME_SPEAKHI_FULL_TIME;
      }

      if (startTime.getTime() < System.currentTimeMillis() + beforeLessionCanSubscribe * 60
          * 1000) {
        logger.error("预约失败,必须在预约时间范围内才能预约~!" + "user_id=" + userId);
        json.setCode(ErrorCodeEnum.SUBSCRIBE_ERROR_TIME.getCode());
        json.setData(beforeLessionCanSubscribe);
        return json;
      }
    }
    return json;
  }

  /**
   * 
   * Title: 带事物的demo课校验<br>
   * Description: checkSubscribeCourseType4WithTransaction<br>
   * CreateDate: 2016年12月23日 下午5:07:16<br>
   * 
   * @category 带事物的demo课校验
   * @author seven.gz
   * @param subscribeCourse
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject checkSubscribeCourseType4WithTransaction(SubscribeCourse subscribeCourse)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 判断老师时间是否合法
    TeacherTime teacherTime = teacherTimeEntityDao
        .findOneByKeyIdForUpdate(subscribeCourse.getTeacherTimeId());
    // 0.判断当前的teacher_time_id 是否瞬间被别人预约掉了。
    if (teacherTime.getIsSubscribe()) {
      // 已经被人预约了（该时间段已经被其他人预约了！）
      // modified by seven 2016年8月6日12:08:16 修改日志级别
      json.setCode(ErrorCodeEnum.TEACHER_TIME_ALREADY_SUBSCRIBE.getCode());
      return json;
    }

    // 判断这段时间是否有其他预约
    SubscribeCourse allTypeSubscribeUserCourse = subscribeCourseEntityDao
        .findAllTypeUserCourseByUserIdAndStartTimeAndEndTime(subscribeCourse.getUserId(),
            subscribeCourse.getStartTime(), subscribeCourse.getEndTime());
    if (allTypeSubscribeUserCourse != null) {
      logger.info(
          ErrorConstant.HAVE_SUBSCRIBE_COURSE_AT_THIS_TIME + "user_id="
              + subscribeCourse.getUserId());
      json.setCode(ErrorCodeEnum.SUBSCRIBE_ALREADY_EXSIT.getCode());
      return json;
    }
    return json;
  }

  /**
   * Title: 取消预约通用不带事务校验.<br>
   * Description: checkCancelSubscribeCourseCommonWithoutTransaction<br>
   * CreateDate: 2016年9月22日 下午5:32:50<br>
   * 
   * @category 取消预约通用不带事务校验
   * @author yangmh
   * @param subscribeCourse
   *          预约对象
   * @return CommonJsonObject对象
   */
  public CommonJsonObject checkCancelSubscribeCourseType4WithoutTransaction(
      SubscribeCourse subscribeCourse, Teacher teacher) {
    CommonJsonObject json = new CommonJsonObject();
    if (subscribeCourse == null) {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_COURSE_NOT_EXIST.getCode());
      return json;
    }

    // 不是环迅的才校验时间
    if (!"huanxun".equals(teacher.getThirdFrom())) {
      // 0.如果要取消的课程已经超过了取消范围则不能被取消，要求当前时间<上课时间-x小时
      Date startTime = subscribeCourse.getStartTime();

      CourseType courseTypeEnity = (CourseType) MemcachedUtil
          .getValue(subscribeCourse.getCourseType());

      // 上课前多久可以预约/取消课程(小时)
      long beforeLessionCanSubscribe = courseTypeEnity
          .getCourseTypeCancelSubscribeTime();

      // modify by seven 2017年8月8日14:26:53 如果是全职或买断按照2小时，其他按数据库配置
      if (AdminTeacherConstant.TEACHER_TEACHER_JOB_TYPE_FULL_TIME == teacher
          .getTeacherJobType() || AdminTeacherConstant.TEACHER_TEACHER_JOB_TYPE_BUYOUT == teacher
              .getTeacherJobType()) {
        beforeLessionCanSubscribe =
            AdminSubscribeCourseConstant.DEMO_SUBSCRIBE_TIME_SPEAKHI_FULL_TIME;
      }

      if (System
          .currentTimeMillis() >= (startTime.getTime() - beforeLessionCanSubscribe * 60 * 1000)) {
        // modify by seven 2017年5月23日13:57:27 修改日志级别
        logger.info("非法取消预约,取消预约时间不正确!" + "user_id=" + subscribeCourse.getUserId());
        json.setCode(ErrorCodeEnum.SUBSCRIBE_COURSE_CANCEL_WRONG_TIME.getCode());
        Map<String, Object> returDataMap = new HashMap<String, Object>();
        returDataMap.put("thirdFrom", teacher.getThirdFrom());
        returDataMap.put("courseTypeSubscribeTime", beforeLessionCanSubscribe);
        json.setData(returDataMap);
      }
    }

    return json;
  }

}