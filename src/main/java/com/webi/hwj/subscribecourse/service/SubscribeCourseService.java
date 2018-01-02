package com.webi.hwj.subscribecourse.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.classin.param.ClassinAppUrlParam;
import com.webi.hwj.classin.service.ClassinService;
import com.webi.hwj.classin.util.ClassinUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.dao.CourseOne2ManySchedulingDao;
import com.webi.hwj.course.dao.CourseOne2OneDao;
import com.webi.hwj.course.util.CourseUtil;
import com.webi.hwj.courseextension1v1.dao.CourseExtension1v1Dao;
import com.webi.hwj.courseone2many.dao.CourseOneToManySchedulingDao;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.courseone2many.param.CourseOne2ManySchedulingParam;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.esapp.param.AppJsonObject;
import com.webi.hwj.esapp.param.CourseListParam;
import com.webi.hwj.gensee.constant.GenseeConstant;
import com.webi.hwj.gensee.service.GenseeService;
import com.webi.hwj.huanxun.service.HuanxunService;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.dao.OrderCourseOptionDao;
import com.webi.hwj.subscribecourse.constant.SubscribeCourseConstant;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.dao.SubscribeSupplementDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.FindListfinishLevelReturnParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeInfoByUserIdAndCourseTypeParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseDetailParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseParam;
import com.webi.hwj.teacher.constant.TeacherTimeConstant;
import com.webi.hwj.teacher.dao.TeacherDao;
import com.webi.hwj.teacher.dao.TeacherTimeDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.user.dao.UserInfoEntityDao;
import com.webi.hwj.user.param.UserInfoForOrderDetailParam;
import com.webi.hwj.util.CalendarUtil;
import com.webi.hwj.webex.service.WebexSubscribeService;

/**
 * Title: 预约相关service<br>
 * Description: 预约相关service<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月8日 上午9:42:46
 * 
 * @author yangmh
 */
@Service
public class SubscribeCourseService {
  private static Logger logger = Logger.getLogger(SubscribeCourseService.class);
  @Resource
  SubscribeCourseDao subscribeCourseDao;
  @Resource
  OrderCourseDao orderCourseDao;
  @Resource
  UserDao userDao;
  @Resource
  OrderCourseOptionDao orderCourseOptionDao;
  @Resource
  TeacherTimeDao teacherTimeDao;
  @Resource
  TeacherDao teacherDao;
  @Resource
  CourseOne2ManySchedulingDao courseOne2ManySchedulingDao;
  @Resource
  CourseOne2OneDao courseOne2OneDao;
  @Resource
  TellmemoreService tellmemoreService;
  @Resource
  SubscribeSupplementDao subscribeSupplementDao;
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
  TeacherTimeEntityDao teacherTimeEntityDao;
  @Resource
  CourseExtension1v1Dao courseExtension1v1Dao;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;
  @Resource
  BaseSubscribeCourseService baseSubscribeCourseService;
  @Resource
  ClassinService classinService;
  @Resource
  UserInfoEntityDao userInfoEntityDao;

  /**
   * Title: 按日期查询所有预约信息<br>
   * Description: 按日期查询所有预约信息<br>
   * CreateDate: 2016年12月20日 下午4:24:07<br>
   * 
   * @category 按日期查询所有预约信息
   * @author komi.zsy
   * @param startTime
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findSubscribeCourseByDate(Date startTime) throws Exception {
    return subscribeCourseEntityDao.findSubscribeCourseByDate(startTime);
  }

  /**
   * Title: 根据预约id获取备注信息<br>
   * Description: 根据预约id获取备注信息<br>
   * CreateDate: 2017年6月7日 下午5:01:35<br>
   * 
   * @category 根据预约id获取备注信息
   * @author komi.zsy
   * @param keyId
   *          预约id
   * @return
   * @throws Exception
   */
  public SubscribeCourse getSubscribeCourseRemark(String keyId) throws Exception {
    return subscribeCourseEntityDao.findRemarkByKeyId(keyId);
  }

  /**
   * Title: 根据时间查询所有课程类型的未评价预约信息<br>
   * Description: 根据时间查询所有课程类型的未评价预约信息<br>
   * CreateDate: 2016年12月20日 下午6:33:54<br>
   * 
   * @category 根据时间查询所有课程类型的未评价预约信息
   * @author komi.zsy
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findAllSubscribeCourseByNotComment(Date endTime) throws Exception {
    return subscribeCourseEntityDao.findAllSubscribeCourseByNotComment(endTime);
  }

  /**
   * @category teacherTime 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return subscribeCourseDao.insert(fields);
  }

  /**
   * 
   * Title: findList<br>
   * Description: findList<br>
   * CreateDate: 2016年1月20日 下午4:10:52<br>
   * 
   * @category findList
   * @author athrun.cw
   * @param paramMap
   * @param columnName
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return subscribeCourseDao.findList(paramMap, columnName);
  }

  /**
   * Title: 课程表<br>
   * Description: 查找即将上的课程<br>
   * CreateDate: 2016年9月21日 上午11:17:18<br>
   * 
   * @category 课程表
   * @author komi.zsy
   * @param userId
   * @return
   * @throws Exception
   */
  public List<SubscribeCourseParam> findClassTablesByUserId(String userId)
      throws Exception {
    // 查找用户预约的&& 还未上课的课程列表
    List<SubscribeCourseParam> subscribeCourseList = subscribeCourseEntityDao
        .findSubscribeCourseByUserIdAndCurrentDate(userId, new Date());

    if (subscribeCourseList != null && subscribeCourseList.size() != 0) {
      logger.debug("用户 [" + userId + "] 没有预约课程：" + subscribeCourseList);
      // 遍历处理各个预约的课程
      for (SubscribeCourseParam subscribeCourse : subscribeCourseList) {
        subscribeCourse = CourseUtil.formatClassTable(subscribeCourse);
      }
    } else {
      logger.debug("用户 [" + userId + "] 没有预约过任何课程，课程表为空...");
    }
    return subscribeCourseList;
  }

  /**
   * Title: 查询课程预约表里的上过课的数据<br>
   * Description: 查询课程预约表里的上过课的数据（如果noshow的数据不查出来,老师已删除的课程也查出来）<br>
   * CreateDate: 2016年9月21日 上午11:09:40<br>
   * 
   * @category 查询课程预约表里的上过课的数据
   * @author komi.zsy
   * @param userId
   *          用户id
   * @param page
   *          页数
   * @param rows
   *          行数
   * @return
   * @throws Exception
   */
  public List<SubscribeCourseParam> findCompleteSubscribeCourseByUserId(String userId, Integer page,
      Integer rows)
          throws Exception {
    // 查找用户预约的&& 已上课的课程列表
    List<SubscribeCourseParam> subscribeCourseList = subscribeCourseEntityDao
        .findCompleteSubscribeCourseByUserId(userId, page, rows).getDatas();
    if (subscribeCourseList != null && subscribeCourseList.size() != 0) {
      logger.debug("用户 [" + userId + "] 课程回顾：" + subscribeCourseList);
      // 遍历处理课程类型名字
      for (SubscribeCourseParam subscribeCourse : subscribeCourseList) {
        subscribeCourse.setCourseTypeChineseName(
            ((CourseType) MemcachedUtil.getValue(subscribeCourse.getCourseType()))
                .getCourseTypeChineseName());
      }
    } else {
      logger.debug("用户 [" + userId + "] 没有出席过任何课程，课程回顾为空...");
    }
    return subscribeCourseList;
  }

  /**
   * Title: 校验课程的开始时间与结束时间是否合法<br>
   * Description: checkStartTimeAndEndTime<br>
   * CreateDate: 2016年8月5日 下午2:22:57<br>
   * 
   * @category 校验课程的开始时间与结束时间是否合法
   * @author yangmh
   * @param startTime
   *          课程开始时间
   * @param endTime
   *          课程结束时间
   * @return
   */
  private boolean checkStartTimeAndEndTime(Date startTime, Date endTime, String courseType) {
    // 微立方房间的向前预留时间(码表)
    int beforeTime = ((CourseType) MemcachedUtil.getValue(courseType))
        .getCourseTypeBeforeGoclassTime();
    // 微立方房间的向后预留时间(码表)
    int afterTime = ((CourseType) MemcachedUtil.getValue(courseType))
        .getCourseTypeAfterGoclassTime();

    // 真正的微立方房间预约开始时间
    long startTimeLong = (Long) startTime.getTime() - beforeTime * 60 * 1000;
    // 真正的微立方房间预约结束时间
    long endTimeLong = (Long) endTime.getTime() + afterTime * 60 * 1000;

    return checkTimeLong(startTimeLong, endTimeLong);
  }

  /**
   * 
   * Title: 判断是否是合法时间<br>
   * Description: 判断是否是合法时间<br>
   * CreateDate: 2016年11月5日 下午1:15:57<br>
   * 
   * @category checkTimeLong
   * @author seven.gz
   * @param startTimeLong
   * @param endTimeLong
   * @return
   */
  private boolean checkTimeLong(long startTimeLong, long endTimeLong) {
    Date curDate = new Date();
    if (curDate.getTime() >= startTimeLong && curDate.getTime() < endTimeLong) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Title: app学生进入教室<br>
   * Description: app学生进入教室<br>
   * CreateDate: 2017年8月25日 下午6:29:12<br>
   * 
   * @category app学生进入教室
   * @author komi.zsy
   * @param subscribeId
   *          预约id
   * @param userId
   *          用户id
   * @param userName
   *          用户名字
   * @param userCode
   *          用户编码
   * @param userPhoto
   *          用户头像
   * @return
   * @throws Exception
   */
  public AppJsonObject<CourseListParam> goToClassAppStudent(String subscribeId, String userId,
      String userName, String userCode, String userPhoto) throws Exception {
    AppJsonObject<CourseListParam> json = new AppJsonObject<CourseListParam>();
    // 根据预约id查询课程和老师相关信息
    CourseOne2ManySchedulingParam courseOne2ManySchedulingParam =
        subscribeCourseEntityDao.findCourseAndTeacherInfoBySubscribeId(subscribeId);
    if (courseOne2ManySchedulingParam == null) {
      // 返回没有已经预约好并且可以去上课的课程
      json.setError("该课程未预约");
      json.setState(false);
      json.setError_key(ErrorCodeEnum.SUBSCRIBE_COURSE_NOT_EXIST.getCode()+"");
      return json;
    }

    // 判断用户是否合法
    if (userId.equals(courseOne2ManySchedulingParam.getUserId())) {

      Date startTime = courseOne2ManySchedulingParam.getStartTime();
      Date endTime = courseOne2ManySchedulingParam.getEndTime();
      String courseType = courseOne2ManySchedulingParam.getCourseType();
      String teacherTimeId = courseOne2ManySchedulingParam.getTeacherTimeId();
      String inviteUrl = courseOne2ManySchedulingParam.getStudentUrl();

      // 判断学员是否能进入教室并做处理
      JsonMessage jsonMessage = checkAndReturnGoToClassStudent(subscribeId, startTime, endTime,
          courseType, teacherTimeId, userId, userName, userCode, inviteUrl);
      json.setState(jsonMessage.isSuccess());
      json.setError(jsonMessage.getMsg());

      // 为了app转换一下数据结构
      CourseListParam courseListParam = new CourseListParam();
      courseListParam.setId(courseOne2ManySchedulingParam.getKeyId());
      courseListParam.setLive_id(courseOne2ManySchedulingParam.getSubscribeId());
      courseListParam.setName(courseOne2ManySchedulingParam.getCourseTitle());
      courseListParam.setTeacher_name(courseOne2ManySchedulingParam.getTeacherName());
      courseListParam.setBanner(courseOne2ManySchedulingParam.getCoursePic());
      courseListParam.setStart_time(courseOne2ManySchedulingParam.getStartTime());
      courseListParam.setEnd_time(courseOne2ManySchedulingParam.getEndTime());
      courseListParam.setTeacher_img(courseOne2ManySchedulingParam.getTeacherPhoto());
      // 调用classin的接口获取直播链接
      ClassinAppUrlParam classinAppUrlParam = ClassinUtil.findAppUrl(courseOne2ManySchedulingParam
          .getRoomId(),
          userId, userName, userPhoto);
      courseListParam.setStream_play_url(classinAppUrlParam.getStreamPlayUrl());
      courseListParam.setStream_play_hls(classinAppUrlParam.getStreamPlayHls());
      courseListParam.setSn(classinAppUrlParam.getSn());
      courseListParam.setChatToken(classinAppUrlParam.getChatToken());
      courseListParam.setChatRoomId(classinAppUrlParam.getId());
      json.setData(courseListParam);
    } else {
      // 非法用户逻辑
      json.setError("该课程已结束！");
      json.setState(false);
    }

    return json;
  }

  /**
   * Title: 检查是否可以进入教室并处理数据<br>
   * Description: 检查是否可以进入教室并处理数据<br>
   * CreateDate: 2017年8月25日 下午1:57:46<br>
   * 
   * @category 检查是否可以进入教室并处理数据
   * @author komi.zsy
   * @param subscribeId
   *          预约id
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @param courseType
   *          课程类型
   * @param teacherTimeId
   *          老师时间id
   * @param userId
   *          用户id
   * @param userName
   *          用户名字
   * @param userCode
   *          用户code
   * @param inviteUrl
   *          进入教室链接（webex才有）
   * @return
   * @throws Exception
   */
  public JsonMessage checkAndReturnGoToClassStudent(String subscribeId, Date startTime,
      Date endTime,
      String courseType,
      String teacherTimeId, String userId, String userName, String userCode, String inviteUrl)
          throws Exception {
    JsonMessage json = new JsonMessage(true);

    // 只有在上课时间范围内才能进入教室
    // 需要判断进入教室时间是否合法
    int beforeTime = ((CourseType) MemcachedUtil.getValue(courseType))
        .getCourseTypeBeforeGoclassTime();
    int afterTime = ((CourseType) MemcachedUtil.getValue(courseType))
        .getCourseTypeAfterGoclassTime();

    long startTimeLong = (Long) startTime.getTime() - beforeTime * 60 * 1000;
    long endTimeLong = (Long) endTime.getTime() + afterTime * 60 * 1000;

    boolean isCheckTime = checkTimeLong(startTimeLong, endTimeLong);

    if (!isCheckTime) {
      json.setSuccess(false);
      Date startClassTime = new Date(startTimeLong);
      Date endClassTime = new Date(endTimeLong);
      Date currentTime = new Date();
      // modify by seven 2017年7月27日15:26:00 修改进入教室提示
      json.setMsg("请提前" + beforeTime + "分钟进入教室！");
      logger.info("您必须在合法时间内进入教室!which is " + startClassTime + "-" + endClassTime
          + ",当前服务器时间为:"
          + currentTime);
      return json;
    }

    // 用于判断是否更新最后的是否进入房间标识
    boolean returnFlag = true;

    // 展示互动进入
    if ("course_type8".equals(courseType) || "course_type5".equals(courseType)) {
      TeacherTime teacherTime = teacherTimeEntityDao.findOneByKeyId(teacherTimeId);

      if (TeacherTimeConstant.TEACHER_TIME_PLATFORM_GENSEE.equals(teacherTime
          .getTeacherTimePlatform())) {
        try {
          json.setMsg(genseeService.goToGenseeClass(teacherTimeId,
              GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_STUDENT, userName,
              Integer.valueOf(userCode)));
        } catch (Exception e) {
          json.setMsg(e.toString());
          json.setSuccess(false);
          returnFlag = false;
          logger.error("展示互动错误----->>>" + e.toString());
          SmsUtil.sendAlarmSms("学生无法进入展示互动教室报警,teacher_time_id=" + teacherTimeId);
        }
      } else {
        // 查询学员信息
        UserInfoForOrderDetailParam user = userInfoEntityDao.findUserInfoByKeyId(userId);
        json.setMsg(ClassinService.goToClassinClassStudentShort(teacherTime.getRoomId(), userId,
            user
                .getEnglishName(),
            user.getUserPhoto()));
      }
    } else {
      // 走webex
      json.setMsg(inviteUrl);
    }

    if (returnFlag) {
      logger.info("学员 [" + userId + "成功进入课堂，开始更新本地预约状态subscribe_status=1...");
      Map<String, Object> updateSubscribeCourseMap = new HashMap<String, Object>();
      updateSubscribeCourseMap.put("key_id", subscribeId);
      updateSubscribeCourseMap.put("subscribe_status", 1);
      subscribeCourseDao.update(updateSubscribeCourseMap);
      logger.info("学员 [" + userId + "成功进入课堂，更新本地预约状态subscribe_status=1成功！");
    }
    return json;
  }

  /**
   * Title: 学生进入教室<br>
   * Description: goToClassStudent<br>
   * CreateDate: 2016年8月4日 上午11:44:40<br>
   * 
   * @category 学生进入教室
   * @author yangmh
   * @param paramMap
   * @return
   * @throws Exception
   */
  public JsonMessage goToClassStudent(Map<String, Object> paramMap) throws Exception {
    JsonMessage json = new JsonMessage(true);
    Map<String, Object> subscribeCourseMap = subscribeCourseDao.findOneByKeyId(
        paramMap.get("key_id"),
        "key_id,user_id, teacher_time_id,course_type,invite_url,start_time,end_time");

    if (subscribeCourseMap == null) {
      // 返回没有已经预约好并且可以去上课的课程
      json.setMsg("该课程未预约");
      json.setSuccess(false);
      return json;
    }

    /**
     * modified by komi 2017年8月25日14:17:28
     * 为了app也能用这个方法，把下面通用逻辑封装一下，因为app需要一些奇怪的参数
     */
    // 判断用户是否合法
    String userId = paramMap.get("user_id").toString();
    if (userId.equals(subscribeCourseMap.get("user_id"))) {

      Date startTime = (Date) subscribeCourseMap.get("start_time");
      Date endTime = (Date) subscribeCourseMap.get("end_time");
      String courseType = (String) subscribeCourseMap.get("course_type");
      String teacherTimeId = (String) subscribeCourseMap.get("teacher_time_id");
      String userName = (String) paramMap.get("user_name");
      String userCode = (String) paramMap.get("user_code");
      String subscribeId = (String) paramMap.get("key_id");
      String inviteUrl = (String) subscribeCourseMap.get("invite_url");

      // 判断学员是否能进入教室并做处理
      json = checkAndReturnGoToClassStudent(subscribeId, startTime, endTime, courseType,
          teacherTimeId,
          userId, userName, userCode, inviteUrl);
    } else {
      // 非法用户逻辑
      json.setMsg("该课程已结束！");
      json.setSuccess(false);
    }

    return json;
  }

  /**
   * Title: 老师进入教室<br>
   * Description: goToClassStudent<br>
   * CreateDate: 2016年8月4日 上午11:44:40<br>
   * 
   * @category 老师进入教室
   * @author yangmh
   * @param paramMap
   * @return
   * @throws Exception
   */
  public JsonMessage goToClassTeacher(String teacherTimeId, String teacherId, String teacherName,
      boolean isFront)
          throws Exception {
    JsonMessage json = new JsonMessage();
    // 威力方进入
    // modified by alex+komi+seven 2016年8月3日 14:19:47
    // 因为不要一刀切,临时解决方案,回头记得删除
    // TODO
    Map<String, Object> teacherTimeMap = teacherTimeDao.findOneByKeyId(teacherTimeId,
        "key_id,webex_room_host_id,course_type,start_time,end_time,teacher_time_platform,room_id");

    if (teacherTimeMap == null) {
      throw new RuntimeException("teacherTime不存在，数据结构有问题!");
    }

    // 只有在上课时间范围内才能进入教室
    Date startTime = (Date) teacherTimeMap.get("start_time");
    Date endTime = (Date) teacherTimeMap.get("end_time");

    // 需要判断进入教室时间是否合法
    boolean isCheckTime = checkStartTimeAndEndTime(startTime, endTime,
        teacherTimeMap.get("course_type").toString());

    if (!isCheckTime) {
      json.setSuccess(false);
      json.setMsg(
          "您必须在合法时间内进入教室!which is " + startTime + "-" + endTime + ",当前服务器时间为:" + new Date());
      return json;
    }

    // modify by seven 2016年9月21日16:02:21 将老师出席状态设置为已出席
    // 前台请求才会设置，后台获得链接不会设置
    if (isFront) {
      TeacherTime teacherTimeAttend = new TeacherTime();
      teacherTimeAttend.setKeyId(teacherTimeId);
      teacherTimeAttend.setIsAttend(true);
      teacherTimeEntityDao.update(teacherTimeAttend);
    }

    /**
     * modified by komi 2016年7月7日17:51:20 ES\oc课程通过展示互动进入教室
     */
    String courseType = teacherTimeMap.get("course_type").toString();
    if ("course_type8".equals(courseType) || "course_type5".equals(courseType)) {
      // 判断是哪个平台
      if (TeacherTimeConstant.TEACHER_TIME_PLATFORM_GENSEE.equals(teacherTimeMap.get(
          "teacher_time_platform"))) {
        try {
          json.setMsg(genseeService.goToGenseeClass(teacherTimeId,
              GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_TEACHER,
              teacherName, null));
        } catch (Exception ex) {
          logger.error(ex.toString());
          SmsUtil.sendAlarmSms(
              "老师无法进入展示互动教室报警,teacherTimeId="
                  + teacherTimeId);
          json = new JsonMessage(false, ex.toString());
        }
      } else {
        // 因为老师需要打开两个链接，这里有一个中间页面来
        String teacherUrl = URLEncoder.encode(classinService.goToClassinClassTeacher(
            (String) teacherTimeMap.get(
                "room_id")), "utf-8");
        String teacherChatUrl = URLEncoder.encode(ClassinUtil.goToClassinClassChatTeacher(
            (String) teacherTimeMap.get(
                "room_id")), "utf-8");

        json.setMsg(MemcachedUtil
            .getConfigValue(ConfigConstant.CONTRACT_OWNER_URL)
            + "/web/teacher/startClassIn.html?teacherUrl=" + teacherUrl
            + "&teacherChatUrl=" + teacherChatUrl);
      }
    } else {
      // 走webex
      String webexHostUrl = webexSubscribeService.createWebexMeetingHostUrl(teacherTimeId);

      if (StringUtils.isEmpty(webexHostUrl)) {
        throw new RuntimeException("webex的host_url为空！！！");
      }
      json.setMsg(webexHostUrl);
    }

    return json;
  }

  /**
   * Title: 视频回顾<br>
   * Description: 视频回顾<br>
   * CreateDate: 2016年10月14日 下午5:09:12<br>
   * 
   * @category 视频回顾
   * @author komi.zsy
   * @param teacherTimeId
   * @param userName
   * @return
   * @throws Exception
   */
  public CommonJsonObject watchCourseVideo(String userId, String teacherTimeId, String userName)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    TeacherTime teacherTimeObj = teacherTimeEntityDao.findOneByKeyId(teacherTimeId);
    if (teacherTimeObj == null) {
      json.setCode(ErrorCodeEnum.TEACHER_TIME_NOT_EXIST.getCode());
    } else {
      String courseType = teacherTimeObj.getCourseType();
      if ("course_type8".equals(courseType) || "course_type5".equals(courseType)) {
        if (TeacherTimeConstant.TEACHER_TIME_PLATFORM_GENSEE.equals(teacherTimeObj
            .getTeacherTimePlatform())) {
          // 展示互动
          String url = genseeService.watchGenseeVideo(teacherTimeId, userName);
          if (!StringUtils.isEmpty(url)) {
            json.setData(url);
          } else {
            json.setCode(ErrorCodeEnum.COURSE_VIDEO_NOT_EXIST.getCode());
          }
        } else {
          // classin
          // 查询学员信息
          UserInfoForOrderDetailParam user = userInfoEntityDao.findUserInfoByKeyId(userId);
          json.setData(ClassinUtil.goToClassinClassStudent(teacherTimeObj.getRoomId(), userId,
              user
                  .getEnglishName(),
              user.getUserPhoto()));
        }

      } else {
        // webex不支持录像
        json.setCode(ErrorCodeEnum.COURSE_VIDEO_WEBEX_NOT_EXIST.getCode());
      }
    }
    return json;
  }

  /**
   * Title: 预约1vn课 重复上课判断<br>
   * Description: 预约1vn课 重复上课判断<br>
   * CreateDate: 2016年8月16日 下午4:30:53<br>
   * 
   * @category 预约1vn课 重复上课判断
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage checkSubscribeOne2ManyCoursePremise(Map<String, Object> paramMap)
      throws Exception {
    String courseId = paramMap.get("course_id").toString();
    String userId = paramMap.get("user_id").toString();
    JsonMessage json = new JsonMessage();

    // 查找该课程的课程名字
    CourseOne2ManyScheduling courseOne2ManyScheduling = courseOneToManySchedulingDao
        .findOneByKeyId(courseId);
    // 根据userid和课程名字和已出席的课程 的条件，查处是否有数据
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setUserId(userId);
    subscribeCourse.setCourseTitle(courseOne2ManyScheduling.getCourseTitle());
    subscribeCourse.setSubscribeStatus(1);
    int num = adminSubscribeCourseDao.findCount(subscribeCourse);

    // 有数据，则弹出提示
    if (num > 0) {
      json.setSuccess(false);
      json.setMsg(ErrorCodeEnum.SUBSCRIBE_LECTURE_REPEAT.getCode() + "");
    }

    return json;
  }

  /**
   * 
   * Title: 查询本月有预约(过)课程的日期以及当天预约的课程数<br>
   * Description: 查询本月有预约(过)课程的日期以及当天预约的课程数<br>
   * CreateDate: 2017年7月19日 下午4:33:40<br>
   * 
   * @category 查询本月有预约(过)课程的日期以及当天预约的课程数
   * @author felix.yl
   * @param sessionUser
   * @param yearMonth
   * @return
   * @throws Exception
   */
  public List<SubscribleCourseParam> findSubscribeCourseDateAndNumber(SessionUser sessionUser,
      String yearMonth) throws Exception {

    // 获取当前登录用户的userId
    String userId = sessionUser.getKeyId();// 参数一

    // 字符串拆分
    String year = yearMonth.substring(0, 4);
    String month = yearMonth.substring(4, 6);

    // 根据传过来的年月,获取当月的第一天
    String start = CalendarUtil.getFirstDayOfMonth(Integer.parseInt(year), Integer.parseInt(
        month)) + " 00:00:00";
    // 根据传过来的年月,获取当月的最后一天
    String end = CalendarUtil.getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(
        month)) + " 23:59:59";

    // 转换为Date型
    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS(start);
    Date endTime = DateUtil.strToDateYYYYMMDDHHMMSS(end);

    // 调用Dao层
    List<SubscribleCourseParam> subscribeCourseList = subscribeCourseEntityDao
        .findSubscribeCourseDateAndNumber(userId, startTime, endTime);

    return subscribeCourseList;
  }

  /**
   * 
   * Title: 查询展示学员在某天已预约的所有课程(包括大课、小课)<br>
   * Description: 查询展示学员在某天已预约的所有课程(包括大课、小课)<br>
   * CreateDate: 2017年7月20日 上午11:18:18<br>
   * 
   * @category 查询展示学员在某天已预约的所有课程(包括大课、小课)
   * @author felix.yl
   * @param sessionUser
   * @param yearMonthDay
   * @return
   * @throws Exception
   */
  public List<SubscribleCourseDetailParam> findSubscribeCourseDetail(SessionUser sessionUser,
      String yearMonthDay) throws Exception {

    // 获取当前登录用户的userId
    String userId = sessionUser.getKeyId();// 参数一

    // 构建当天的开始时间和结束时间
    String startTime = yearMonthDay + " 00:00:00";
    String endTime = yearMonthDay + " 23:59:59";

    // 转换为Date型
    Date currentStartTime = DateUtil.strToDateYYYYMMDDHHMMSS(startTime);
    Date currentEndTime = DateUtil.strToDateYYYYMMDDHHMMSS(endTime);

    // 调用Dao层(大课)
    List<SubscribleCourseDetailParam> subscribeCourseDetailOneToMany = subscribeCourseEntityDao
        .findSubscribeCourseDetailOneToMany(userId, currentStartTime, currentEndTime);

    // 1-可取消预约;2-倒计时;3-可进入教室
    for (SubscribleCourseDetailParam returnObj : subscribeCourseDetailOneToMany) {
      Long courseStartTime = returnObj.getStartTime().getTime();// 课程开始时间
      Long courseEndTime = returnObj.getEndTime().getTime();// 课程结束时间
      Integer beforeGoclassTime = returnObj.getCourseTypeBeforeGoclassTime() * 60 * 1000;// 提前进入教室时间(分钟)
      Integer cancelSubscribeTime = returnObj.getCourseTypeCancelSubscribeTime() * 60 * 1000;// 提前取消预约的时间(分钟)
      Long currentTime = System.currentTimeMillis();// 当前时间
      Long time = courseStartTime - currentTime;// 现在时间距离上课时间间隔

      // 刷新状态
      if (time > cancelSubscribeTime) {
        returnObj.setStatus(SubscribeCourseConstant.CAN_CANCEL_SUBSCRIBE);// 1-可取消预约
      }
      if (time <= cancelSubscribeTime && time > beforeGoclassTime) {
        returnObj.setStatus(SubscribeCourseConstant.CAN_COUNT_DOWN);// 2-倒计时
      }
      if (time <= beforeGoclassTime && currentTime < courseEndTime) {
        returnObj.setStatus(SubscribeCourseConstant.CAN_INTO_CLASSROOM);// 3-可进入教室
      }

      // 刷新当前时间是否大于课程结束时间标志
      if (currentTime >= courseEndTime) {
        returnObj.setCourseEndTimestatus(SubscribeCourseConstant.COURSE_END_TIME_STATUS_YES);
      } else {
        returnObj.setCourseEndTimestatus(SubscribeCourseConstant.COURSE_END_TIME_STATUS_NO);
      }
    }

    // 调用Dao层(小课)
    List<SubscribleCourseDetailParam> subscribeCourseDetailOneToOne = subscribeCourseEntityDao
        .findSubscribeCourseDetailOneToOne(userId, currentStartTime, currentEndTime);

    // 1-可取消预约;2-倒计时;3-可进入教室
    for (SubscribleCourseDetailParam returnObj : subscribeCourseDetailOneToOne) {
      Long courseStartTime = returnObj.getStartTime().getTime();// 课程开始时间
      Long courseEndTime = returnObj.getEndTime().getTime();// 课程结束时间
      Integer beforeGoclassTime = returnObj.getCourseTypeBeforeGoclassTime() * 60 * 1000;// 提前进入教室时间(分钟)
      Integer cancelSubscribeTime = returnObj.getCourseTypeCancelSubscribeTime() * 60 * 1000;// 提前取消预约的时间(分钟)
      Long currentTime = System.currentTimeMillis();// 当前时间
      Long time = courseStartTime - currentTime;// 现在时间距离上课时间间隔

      // 刷新状态
      if (time > cancelSubscribeTime) {
        returnObj.setStatus(SubscribeCourseConstant.CAN_CANCEL_SUBSCRIBE);// 1-可取消预约
      }
      if (time <= cancelSubscribeTime && time > beforeGoclassTime) {
        returnObj.setStatus(SubscribeCourseConstant.CAN_COUNT_DOWN);// 2-倒计时
      }
      if (time <= beforeGoclassTime && currentTime < courseEndTime) {
        returnObj.setStatus(SubscribeCourseConstant.CAN_INTO_CLASSROOM);// 3-可进入教室
      }

      // 刷新当前时间是否大于课程结束时间标志
      if (currentTime >= courseEndTime) {
        returnObj.setCourseEndTimestatus(SubscribeCourseConstant.COURSE_END_TIME_STATUS_YES);
      } else {
        returnObj.setCourseEndTimestatus(SubscribeCourseConstant.COURSE_END_TIME_STATUS_NO);
      }
    }

    // 整合两个list集合
    List<SubscribleCourseDetailParam> subscribeCourseDetailAll =
        new ArrayList<SubscribleCourseDetailParam>();
    if (subscribeCourseDetailOneToOne.size() == 0 || subscribeCourseDetailOneToMany.size() == 0) {
      // 直接整合;不考虑排序
      subscribeCourseDetailAll.addAll(subscribeCourseDetailOneToOne);
      subscribeCourseDetailAll.addAll(subscribeCourseDetailOneToMany);
    } else {
      // 整合且排序
      subscribeCourseDetailAll.addAll(subscribeCourseDetailOneToOne);
      subscribeCourseDetailAll.addAll(subscribeCourseDetailOneToMany);

      // 将整合后的list集合排序
      Collections.sort(subscribeCourseDetailAll, new Comparator<SubscribleCourseDetailParam>() {
        public int compare(SubscribleCourseDetailParam o1, SubscribleCourseDetailParam o2) {
          long i = o1.getStartTime().getTime() - o2.getStartTime().getTime();
          if (i > 0) {
            return 1;
          } else {
            return -1;
          }
        }
      });
    }
    return subscribeCourseDetailAll;
  }

  /**
   * 
   * Title: 查询预约列表<br>
   * Description: findSubscribeInfoByUserIdAndCourseType<br>
   * CreateDate: 2017年7月21日 下午6:18:38<br>
   * 
   * @category 查询预约列表
   * @author seven.gz
   * @param userId
   * @param courseType
   * @return
   * @throws Exception
   */
  public FindSubscribeInfoByUserIdAndCourseTypeParam findSubscribeInfoByUserIdAndCourseType(
      String userId,
      String courseType, Date endTime) throws Exception {
    FindSubscribeInfoByUserIdAndCourseTypeParam findSubscribeInfoByUserIdAndCourseTypeParam =
        new FindSubscribeInfoByUserIdAndCourseTypeParam();

    // 查询预约信息
    findSubscribeInfoByUserIdAndCourseTypeParam.setTeacherTimeIds(subscribeCourseEntityDao
        .findListByUserIdAndCourseType(userId, courseType, endTime));

    // 设置提前进入教室时间
    findSubscribeInfoByUserIdAndCourseTypeParam.setCourseTypeBeforeGoclassTime(
        ((CourseType) MemcachedUtil.getValue(courseType)).getCourseTypeBeforeGoclassTime());
    return findSubscribeInfoByUserIdAndCourseTypeParam;
  }

  /**
   * 
   * Title: 查询该学员某个级别中含有9个已完成已出席的不同的Core课程<br>
   * Description: findListfinishLevel<br>
   * CreateDate: 2017年7月24日 下午5:08:29<br>
   * 
   * @category 查询该学员某个级别中含有9个已完成已出席的不同的Core课程
   * @author seven.gz
   * @param userId
   * @param endTime
   * @return
   * @throws Exception
   */
  public FindListfinishLevelReturnParam findListfinishLevel(
      String userId, Date endTime) throws Exception {
    FindListfinishLevelReturnParam findListfinishLevelReturnParam =
        new FindListfinishLevelReturnParam();

    // 查询该学员某个级别中含有9个已完成已出席的不同的Core课程
    findListfinishLevelReturnParam.setFinishLevelList(subscribeCourseEntityDao
        .findListFinishLevel(userId, endTime));

    return findListfinishLevelReturnParam;
  }
}