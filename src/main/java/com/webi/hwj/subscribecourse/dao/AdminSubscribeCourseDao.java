package com.webi.hwj.subscribecourse.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.course.entity.AdminCourse;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.FindNoShowCourseParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseAndStudentParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseAndTimeParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseCountParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseInfoParam;
import com.webi.hwj.subscribecourse.param.FindTuanxunCourseCommentParam;
import com.webi.hwj.subscribecourse.param.StatisticsTeacherSubscribeCourseParam;

@Repository
public class AdminSubscribeCourseDao extends BaseEntityDao<SubscribeCourse> {
  /**
   * 级联更新预约表信息
   */
  private static final String UPDATE_COURSE_INFO =
      " UPDATE t_subscribe_course SET course_title = :courseTitle, "
          + "course_courseware = :courseCourseware, course_pic = :coursePic "
          + " WHERE course_id IN (:courseIds) AND course_type=:courseType ";

  /**
   * 根据id查询上课数
   */
  private static final String FIND_SUBSCRIBE_COURSE_COUNT_BY_USERIDS =
      " SELECT create_date,user_id,course_type,COUNT(course_type) AS course_count "
          + " FROM  t_subscribe_course "
          + " WHERE user_id IN (:userIds) AND is_used = 1 AND subscribe_status = 1 AND start_time < :currentDate "
          + " GROUP BY user_id,course_type ";

  /**
   * 根据学员id和课程结束时间查询学员上课数
   */
  private static final String FIND_COUNT_BY_USERID_AND_ENDTIME =
      " SELECT course_type,COUNT(user_id) AS course_count,SUM(subscribe_status) AS show_course_count "
          + " FROM t_subscribe_course "
          + " WHERE user_id = :userId  AND is_used = 1 AND end_time <= :endTime AND order_id = :orderId "
          + " GROUP BY course_type ";

  /**
   * 查询学员当前级别有效预约课程结束时间小于等与当前时间的预约数 core
   */
  private static final String FIND_CORE_COUNT_BY_USERID_AND_LEVEL =
      " SELECT COUNT(DISTINCT tsc.course_id) "
          + " FROM  t_subscribe_course tsc "
          + " WHERE tsc.user_level = :userLevel AND tsc.end_time <= :endTime AND tsc.user_id=:userId AND tsc.course_type IN (:courseTypes) "
          + " AND tsc.is_used = 1 ";

  /**
   * 查询用户指定类型指定时间段内的有效预约数
   */
  private static final String FIND_COUNT_BY_USERID_STARTTIME_ENDTIME_AND_COURSETYPE =
      " SELECT COUNT(1) FROM t_subscribe_course "
          + " WHERE user_id = :userId AND start_time >= :startTime AND end_time <= :endTime AND order_id = :orderId AND course_type = :courseType AND is_used = 1 ";

  /**
   * 根据id查询上课数
   */
  private static final String FIND_SUBSCRIBE_COURSE_COUNT_BY_ORDERIDS =
      " SELECT user_id,course_type,COUNT(course_type) AS course_count "
          + " FROM  t_subscribe_course "
          + " WHERE order_id IN (:orderIds) AND is_used = 1 AND  subscribe_status = 1 "
          + " GROUP BY user_id,course_type ";

  /**
   * 查找noshow的课程相关信息 TODO
   */
  private static final String FIND_NOSHOW_COURSE_PAGE =
      " SELECT tsc.start_time,tsc.course_title,tu.user_name,tui.english_name,tu.user_code, "
          + " tu.phone,tbu.admin_user_name,tsc.course_type,tuf.last_follow_time "
          + " FROM t_subscribe_course tsc "
          + " LEFT JOIN t_user tu ON tsc.user_id = tu.key_id AND tu.is_used = 1 "
          + " LEFT JOIN t_user_info tui ON tsc.user_id = tui.key_id AND tui.is_used = 1 "
          + " LEFT JOIN t_badmin_user tbu ON tu.learning_coach_id = tbu.key_id AND tbu.is_used = 1 "
          + " LEFT JOIN "
          + " (SELECT user_id,MAX(create_date) last_follow_time FROM t_user_followup WHERE is_used = 1 "
          + " GROUP BY user_id) tuf ON tuf.user_id = tu.key_id "
          + " WHERE tsc.end_time <= :endTime AND tsc.is_used = 1 AND tsc.subscribe_status = 0 ";

  /**
   * 查询上过课的学员（为出席也算）
   */
  private static final String FIND_WENT_CLASS_STUDENTS =
      " SELECT user_id,MAX(start_time) AS start_time FROM t_subscribe_course "
          + "	WHERE course_type = :courseType "
          + " AND end_time <= :endTime AND is_used = 1 "
          + "	GROUP BY user_id ";

  /**
   * 查询预约的人和课程
   */
  private static final String FIND_SUBSCRIBE_USER_AND_COURSE =
      " SELECT user_id,course_id FROM t_subscribe_course "
          + " WHERE course_type = :courseType AND is_used =1 GROUP BY user_id,course_id ";

  /**
   * 查询已出席的课程key_id
   */
  private static final String FIND_SHOW_SUBSCRIBES =
      " SELECT key_id FROM t_subscribe_course WHERE start_time >= :startTime AND end_time <= :endTime "
          + " AND is_used = 1 AND subscribe_status = 1 ";

  /**
   * 根据teacherTimeIds 查询预约信息
   */
  private static final String FIND_SUBSCRIBE_COURSE_BY_TEACHER_TIME_IDS =
      " SELECT key_id,teacher_time_id,course_title,user_level "
          + " FROM t_subscribe_course WHERE is_used=1 AND teacher_time_id IN (:teacherTimeIds) ";

  /**
   * 根据时间查询预约信息
   */
  public static final String FIND_SUBSCRIBE_COURSE_BY_TIME =
      " SELECT teacher_time_id,course_type,start_time "
          + " FROM t_subscribe_course "
          + " WHERE start_time >= :startTime AND end_time <= :endTime "
          + " AND is_used = 1 ";

  /**
   * 查询预约和老师信息
   */
  public static final String FIND_SUBSCRIBE_COURSE_AND_TEACHER_PAGE =
      " SELECT tsc.teacher_id,tsc.teacher_time_id,tsc.start_time,tsc.end_time,tt.third_from,tsc.teacher_name,tsc.course_type "
          + " FROM  t_subscribe_course tsc LEFT JOIN t_teacher tt "
          + " ON  tsc.teacher_id = tt.key_id "
          + " WHERE start_time >= :startTime AND end_time <= :endTime AND tsc.is_used = 1 ";

  /**
   * 查询学生 预约信息.
   */
  public static final String FIND_SUBSCRIBE_COURSE_AND_STUDENT_PAGE =
      " SELECT tsc.key_id,tsc.start_time,tsc.course_title,tsc.course_type,tsc.teacher_name, "
          + " tsc.user_name,tsc.user_id,tu.user_code,tu.phone,tsc.subscribe_status,tsc.invite_url,tsc.is_first,  "
          + " tsc.teacher_time_id,tsc.teacher_id,tu.learning_coach_id,tscn.subscribe_note,tbu.admin_user_name, "
          + " tscn.subscribe_note_taker,tscn.update_date AS subscribe_note_date,tscn.subscribe_note_type,tt.third_from "
          + " FROM t_subscribe_course tsc "
          + " LEFT JOIN t_user tu "
          + " ON tsc.user_id = tu.key_id "
          + " LEFT JOIN t_badmin_user tbu "
          + " ON tbu.key_id = tu.learning_coach_id "
          + " LEFT JOIN t_subscribe_course_note tscn "
          + " ON tscn.key_id = tsc.key_id "
          + " LEFT JOIN t_teacher tt ON tsc.teacher_id = tt.key_id "
          + " WHERE tsc.is_used = 1 AND tsc.start_time >= :startTime AND tsc.end_time <= :endTime AND "
          + " (course_type = 'course_type1' OR course_type = 'course_type2' OR "
          + " course_type = 'course_type9' OR course_type = 'course_type11' OR course_type = 'course_type4' ) ";
  /**
   * 查询还未开始的预约课程数
   * 
   * @author seven.gz
   */
  public static final String FIND_SUBSCRIBE_COURSE_COUNT_BEFORE_START =
      " SELECT  COUNT(key_id) FROM t_subscribe_course "
          + " WHERE user_id = :userId AND start_time > :startTime AND is_used = 1 "
          + " AND (course_type = 'course_type1' OR course_type = 'course_type2' OR course_type = 'course_type9') ";

  /**
   * 根据学员id，修改学员手机号
   * 
   * @author komi.zsy
   */
  private static final String UPDATE_USER_PHONE_BY_USERID = " UPDATE `t_subscribe_course`"
      + " SET user_phone = :userPhone"
      + " WHERE user_id = :userId AND is_used <> 0";

  /**
   * 查询学员预约数据
   */
  private static final String FIND_USER_LAST_SUBSCRIBE_INFO = " SELECT tsc.key_id, "
      + " tsc.user_id,ttt.start_time,ttt.end_time,ttt.webex_meeting_key, "
      + " twr.webex_request_url,twr.webex_room_host_id,tsc.course_type "
      + " FROM t_subscribe_course tsc LEFT JOIN t_teacher_time ttt "
      + " ON tsc.teacher_time_id = ttt.key_id "
      + " LEFT JOIN t_webex_room twr ON ttt.webex_room_host_id = twr.webex_room_host_id "
      + " WHERE tsc.is_used = 1  "
      + " AND tsc.user_id IN (:userIds) ";

  /**
   * 查询团训新增字段的查询：user_id,teacher_name、t_course_comment、start_time
   */
  private static final String FIND_TUANXUN_SUBSCRIBR_INFO =
      " SELECT tsc.user_id,tsc.teacher_name,tsc.start_time,tcc.comment_content "
          + " FROM t_subscribe_course tsc LEFT JOIN  t_course_comment tcc "
          + " ON tsc.key_id=tcc.subscribe_course_id "
          + " WHERE tsc.is_used <> 0 AND tcc.is_used <> 0 AND course_type='course_type1' AND start_time < :startTime AND tcc.to_user_id IN (:userIds) ";

  /**
   * 查询学生 预约信息.
   */
  public static final String FIND_SUBSCRIBE_COURSE_AND_STUDENT_PAGE_BY_COURSE_TYPE =
      " SELECT tsc.key_id,tsc.start_time,tsc.end_time,tsc.course_title,tsc.course_type,tsc.teacher_name, "
          + " tsc.user_name,tsc.user_id,tu.user_code,tu.phone, "
          + " tsc.subscribe_status,tsc.invite_url,tsc.is_first,tsc.create_date, "
          + " tsc.teacher_time_id,tsc.teacher_id,tu.learning_coach_id,tbu.admin_user_name,tsc.subscribe_type "
          + " FROM t_subscribe_course tsc "
          + " LEFT JOIN t_user tu "
          + " ON tsc.user_id = tu.key_id "
          + " LEFT JOIN t_badmin_user tbu "
          + " ON tsc.create_user_id = tbu.key_id  "
          + " WHERE tsc.is_used = 1 AND tsc.start_time >= :startTime AND tsc.end_time <= :endTime AND "
          + " course_type = :courseType ";

  /**
   * 跟新学员名称
   */
  public static final String UPDATE_NAME_BY_ENGLISH_NAME =
      " UPDATE t_subscribe_course SET user_name = :userName WHERE user_id = :userId AND is_used = 1 ";

  /**
   * Title: 根据学员id，修改学员手机号<br>
   * Description: 根据学员id，修改学员手机号<br>
   * CreateDate: 2016年8月1日 下午8:43:07<br>
   * 
   * @category 根据学员id，修改学员手机号
   * @author komi.zsy
   * @param subscribeCourse
   * @return
   * @throws Exception
   */
  public int updateUserPhoneByUserId(SubscribeCourse subscribeCourse) throws Exception {
    return super.update(UPDATE_USER_PHONE_BY_USERID, subscribeCourse);
  }

  /**
   * * Title: 通过课程id级联更新预约表中的课程相关信息<br>
   * Description: 无论是大课还是小课都通用，用于管理平台中的修改课程<br>
   * CreateDate: 2016年5月23日 下午4:34:10<br>
   * 
   * @category 通过课程id级联更新预约表中的课程相关信息
   * @author seven.gz
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateCourseInfo(AdminCourse paramObj) throws Exception {
    return super.update(UPDATE_COURSE_INFO, paramObj);
  }

  /**
   * 
   * Title: 查询学员上课数<br>
   * Description: 查询学员上课数<br>
   * CreateDate: 2016年5月31日 下午1:32:28<br>
   * 
   * @category 查询学员上课数
   * @author seven.gz
   * @param userIds
   * @return
   * @throws Exception
   */
  public List<FindSubscribeCourseCountParam> findSubscribeCourseCount(List<String> userIds,
      Date currentDate)
          throws Exception {
    FindSubscribeCourseCountParam findSubscribeCourseCountParam =
        new FindSubscribeCourseCountParam();
    findSubscribeCourseCountParam.setUserIds(userIds);
    findSubscribeCourseCountParam.setCurrentDate(currentDate);
    return super.findList(FIND_SUBSCRIBE_COURSE_COUNT_BY_USERIDS, findSubscribeCourseCountParam);
  }

  /**
   * 
   * Title: 根据学员id和课程结束时间查询学员上课数<br>
   * Description: 查询学员上课数<br>
   * CreateDate: 2016年5月31日 下午1:32:28<br>
   * 
   * @category 根据学员id和课程结束时间查询学员上课数
   * @author seven.gz
   * @param userIds
   * @return
   * @throws Exception
   */
  public List<FindSubscribeCourseCountParam> findCountByUserIdAndEndTime(String userId,
      Date currentTime, String orderId) throws Exception {
    FindSubscribeCourseCountParam findSubscribeCourseCountParam =
        new FindSubscribeCourseCountParam();
    findSubscribeCourseCountParam.setUserId(userId);
    findSubscribeCourseCountParam.setEndTime(currentTime);
    findSubscribeCourseCountParam.setOrderId(orderId);
    return super.findList(FIND_COUNT_BY_USERID_AND_ENDTIME, findSubscribeCourseCountParam);
  }

  /**
   * 
   * Title: findCountByUserIdAndLevel<br>
   * Description: findCountByUserIdAndLevel<br>
   * CreateDate: 2016年9月13日 下午3:56:14<br>
   * 
   * @category findCountByUserIdAndLevel
   * @author seven.gz
   * @param userId
   * @param userLevel
   * @param currentTime
   * @param orderId
   * @param isCore
   *          true查core 课程， false 查extension课程
   * @return
   * @throws Exception
   */
  public int findCountByUserIdAndLevel(String userId, String userLevel, Date currentTime,
      String orderId, boolean isCore) throws Exception {
    FindSubscribeCourseCountParam findSubscribeCourseCountParam =
        new FindSubscribeCourseCountParam();
    findSubscribeCourseCountParam.setUserId(userId);
    findSubscribeCourseCountParam.setEndTime(currentTime);
    findSubscribeCourseCountParam.setOrderId(orderId);
    findSubscribeCourseCountParam.setUserLevel(userLevel);
    List<String> courseTypes = new ArrayList<String>();
    if (isCore) {
      courseTypes.add("course_type1");
      courseTypes.add("course_type11");
    } else {
      courseTypes.add("course_type2"); // 不需要去重因为 t_subscribe_course
                                       // 表中保存的是scheduling 的数据所以去重也不会有影响
      courseTypes.add("course_type9");
    }
    findSubscribeCourseCountParam.setCourseTypes(courseTypes);
    String sql = FIND_CORE_COUNT_BY_USERID_AND_LEVEL;

    // 有order就带orderid查 没有则不带
    if (!StringUtils.isEmpty(orderId)) {
      sql += " AND order_id = :orderId ";
    }

    return super.findCount(sql, findSubscribeCourseCountParam);
  }

  /**
   * 
   * Title: 查询用户指定类型指定时间段内的有效预约数<br>
   * Description: 查询用户指定类型指定时间段内的有效预约数<br>
   * CreateDate: 2016年6月1日 下午9:04:24<br>
   * 
   * @category 查询用户指定类型指定时间段内的有效预约数
   * @author seven.gz
   * @param userId
   * @param startTime
   * @param endTime
   * @param courseType
   * @return
   * @throws Exception
   */
  public int findCountByUserIdStartTimeEndTimeAndCourseType(String userId, Date startTime,
      Date endTime, String courseType, String orderId) throws Exception {
    FindSubscribeCourseCountParam findSubscribeCourseCountParam =
        new FindSubscribeCourseCountParam();
    findSubscribeCourseCountParam.setUserId(userId);
    findSubscribeCourseCountParam.setStartTime(startTime);
    findSubscribeCourseCountParam.setEndTime(endTime);
    findSubscribeCourseCountParam.setCourseType(courseType);
    findSubscribeCourseCountParam.setOrderId(orderId);
    return super.findCount(FIND_COUNT_BY_USERID_STARTTIME_ENDTIME_AND_COURSETYPE,
        findSubscribeCourseCountParam);
  }

  /**
   * 
   * Title: 查询学员上课数<br>
   * Description: 查询学员上课数<br>
   * CreateDate: 2016年5月31日 下午1:32:28<br>
   * 
   * @category 查询学员上课数
   * @author seven.gz
   * @param userIds
   * @return
   * @throws Exception
   */
  public List<FindSubscribeCourseCountParam> findSubscribeCourseCountByOrderIds(
      List<String> orderIds) throws Exception {
    FindSubscribeCourseCountParam findSubscribeCourseCountParam =
        new FindSubscribeCourseCountParam();
    findSubscribeCourseCountParam.setOrderIds(orderIds);
    return super.findList(FIND_SUBSCRIBE_COURSE_COUNT_BY_ORDERIDS, findSubscribeCourseCountParam);
  }

  /**
   * 
   * Title: 查询noshow课程信息<br>
   * Description: 查询noshow课程信息<br>
   * CreateDate: 2016年7月4日 下午2:26:13<br>
   * 
   * @category 查询noshow课程信息
   * @author seven.gz
   * @param currentTime
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findNoShowCoursePage(Date currentTime, Map<String, Object> paramMap,
      String learningCoachId) throws Exception {
    String sort = (String) paramMap.get("sort");
    String order = (String) paramMap.get("order");
    if (sort == null) {
      sort = " start_time ";
      order = " DESC ";
    }

    Integer page = Integer.valueOf((String) paramMap.get("page"));
    Integer rows = Integer.valueOf((String) paramMap.get("rows"));

    FindNoShowCourseParam findNoShowCourseParam = new FindNoShowCourseParam();
    findNoShowCourseParam.setEndTime(currentTime);
    findNoShowCourseParam.setCons((String) paramMap.get("cons"));

    String sql = FIND_NOSHOW_COURSE_PAGE;
    if (!StringUtils.isEmpty(learningCoachId)) {
      sql += " and tu.learning_coach_id = :learningCoachId ";
      findNoShowCourseParam.setLearningCoachId(learningCoachId);
    }

    return super.findPageEasyui(sql, findNoShowCourseParam, sort, order, page, rows);
  }

  /**
   * 
   * Title: 查询上过课的学员（没出席也算）<br>
   * Description: 查询上过课的学员（没出席也算）<br>
   * CreateDate: 2016年7月12日 下午3:26:37<br>
   * 
   * @category 查询上过课的学员（没出席也算）
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findWentClassStudents(String courseType, Date nowDate)
      throws Exception {
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setCourseType(courseType);
    subscribeCourse.setEndTime(nowDate);
    return super.findList(FIND_WENT_CLASS_STUDENTS, subscribeCourse);
  }

  /**
   * 
   * Title: 查询预约的人和课程<br>
   * Description: 查询预约的人和课程<br>
   * CreateDate: 2016年7月13日 下午3:58:20<br>
   * 
   * @category 查询预约的人和课程
   * @author seven.gz
   * @param courseType
   * @param nowDate
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findUserAndCourse(String courseType) throws Exception {
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setCourseType(courseType);
    return super.findList(FIND_SUBSCRIBE_USER_AND_COURSE, subscribeCourse);
  }

  /**
   * 
   * Title: 查询出席的预约信息<br>
   * Description: 查询出席的预约信息<br>
   * CreateDate: 2016年7月21日 下午4:47:48<br>
   * 
   * @category 查询出席的预约信息
   * @author seven.gz
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findShowSubscribe(Date startTime, Date endTime) throws Exception {
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setStartTime(startTime);
    subscribeCourse.setEndTime(endTime);
    return super.findList(FIND_SHOW_SUBSCRIBES, subscribeCourse);
  }

  /**
   * 
   * Title: 根据老师时间id查询预约信息<br>
   * Description: 根据老师时间id查询预约信息<br>
   * CreateDate: 2016年7月25日 下午2:22:22<br>
   * 
   * @category 根据老师时间id查询预约信息
   * @author seven.gz
   * @param teacherTimeIds
   * @return
   * @throws Exception
   */
  public List<FindSubscribeCourseInfoParam> findSubscribeCourseInfoParam(
      List<String> teacherTimeIds) throws Exception {
    if (teacherTimeIds == null || teacherTimeIds.size() == 0) {
      return null;
    }
    FindSubscribeCourseInfoParam findSubscribeCourseInfoParam = new FindSubscribeCourseInfoParam();
    findSubscribeCourseInfoParam.setTeacherTimeIds(teacherTimeIds);
    return super.findList(FIND_SUBSCRIBE_COURSE_BY_TEACHER_TIME_IDS, findSubscribeCourseInfoParam);
  }

  /**
   * 
   * Title: 查询一个时间段内的预约数据<br>
   * Description: 查询一个时间段内的预约数据<br>
   * CreateDate: 2016年7月27日 下午1:58:34<br>
   * 
   * @category 查询一个时间段内的预约数据
   * @author seven.gz
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findSubscribeCourseByTime(Date startTime, Date endTime,
      String studentShow)
          throws Exception {
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setStartTime(startTime);
    subscribeCourse.setEndTime(endTime);
    String sql = FIND_SUBSCRIBE_COURSE_BY_TIME;
    if (!StringUtils.isEmpty(studentShow)) {
      sql += " AND subscribe_status = 1 ";
    }
    sql += " ORDER BY start_time ASC ";
    return super.findList(sql, subscribeCourse);
  }

  /**
   * 
   * Title: 查询预约和老师信息<br>
   * Description: 查询预约和老师信息<br>
   * CreateDate: 2016年7月28日 下午5:56:37<br>
   * 
   * @category 查询预约和老师信息
   * @author seven.gz
   * @param paramMap
   * @param startTime
   * @param endTime
   * @param studentShow
   * @return
   * @throws Exception
   */
  public Page findSubscribeCourseAndTeacherPage(Map<String, Object> paramMap, Date startTime,
      Date endTime,
      String studentShow) throws Exception {
    String sort = (String) paramMap.get("sort");
    String order = (String) paramMap.get("order");
    if (sort == null) {
      sort = " teacherId,courseType,startTime ";
      order = " ASC ";
    }

    StatisticsTeacherSubscribeCourseParam statisticsTeacherSubscribeCourseParam =
        new StatisticsTeacherSubscribeCourseParam();
    statisticsTeacherSubscribeCourseParam.setStartTime(startTime);
    statisticsTeacherSubscribeCourseParam.setEndTime(endTime);
    String cons = (String) paramMap.get("cons");
    if (cons != null) {
      statisticsTeacherSubscribeCourseParam
          .setCons(cons.replaceAll("teacherName", "tsc.teacherName"));
    }

    String sql = FIND_SUBSCRIBE_COURSE_AND_TEACHER_PAGE;
    if (!StringUtils.isEmpty(studentShow)) {
      sql += " AND subscribe_status = 1 ";
    }
    // 应为有null就设置 page 1，rows 20 的限制，这里先写99999999 等数据量大道这个程度再说 TODO
    return super.findPageEasyui(sql, statisticsTeacherSubscribeCourseParam, sort,
        order, 1, Integer.MAX_VALUE);
  }

  /**
   * Title: 查询预约和学员信息<br>
   * Description: 查询预约和学员信息<br>
   * CreateDate: 2016年7月29日 下午6:09:23<br>
   * 
   * @category 查询预约和学员信息
   * @author seven.gz
   * @param paramMap
   *          easyui传入的参数
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @return page
   * @throws Exception
   *           异常
   */
  public Page findSubscribeCourseAndStudentPage(Map<String, Object> paramMap, Date startTime,
      Date endTime)
          throws Exception {
    String sort = (String) paramMap.get("sort");
    String order = (String) paramMap.get("order");
    if (sort == null) {
      sort = " startTime asc,courseId ";
      order = " ASC ";
    }

    FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam =
        new FindSubscribeCourseAndStudentParam();
    findSubscribeCourseAndStudentParam.setStartTime(startTime);
    findSubscribeCourseAndStudentParam.setEndTime(endTime);
    String cons = (String) paramMap.get("cons");
    findSubscribeCourseAndStudentParam.setCons(cons);

    Integer page = Integer.valueOf((String) paramMap.get("page"));
    Integer rows = Integer.valueOf((String) paramMap.get("rows"));

    return super.findPageEasyui(FIND_SUBSCRIBE_COURSE_AND_STUDENT_PAGE,
        findSubscribeCourseAndStudentParam, sort,
        order, page, rows);
  }

  /**
   * 
   * Title: 根据userid查找还未开始上课的预约id<br>
   * Description: 根据userid查找还未开始上课的预约id<br>
   * CreateDate: 2016年8月11日 下午3:29:29<br>
   * 
   * @category 根据userid查找还未开始上课的预约id
   * @author seven.gz
   * @param userId
   * @param currentTime
   * @return
   * @throws Exception
   */
  public int findSubscribeCourseCountBeforeStart(String userId, Date currentTime) throws Exception {
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setUserId(userId);
    subscribeCourse.setStartTime(currentTime);
    return super.findCount(FIND_SUBSCRIBE_COURSE_COUNT_BEFORE_START, subscribeCourse);
  }

  /**
   * 
   * Title: 根据老师时间id查询预约信息<br>
   * Description: findOneByTeacherTimeId<br>
   * CreateDate: 2016年12月21日 上午11:12:08<br>
   * 
   * @category 根据老师时间id查询预约信息
   * @author seven.gz
   * @param teacherTimeId
   * @return
   * @throws Exception
   */
  public SubscribeCourse findOneByTeacherTimeId(String teacherTimeId) throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setTeacherTimeId(teacherTimeId);
    return super.findOne(paramObj, "teacher_id,course_type");
  }

  /**
   * 
   * Title: 根据学员id号查询预约信息<br>
   * Description: 根据学员id号查询预约信息<br>
   * CreateDate: 2016年12月26日 下午2:31:57<br>
   * 
   * @category 根据学员id号查询预约信息
   * @author seven.gz
   * @param userIds
   * @return
   * @throws Exception
   */
  public List<FindSubscribeCourseAndTimeParam> findListSubscribeByUserIds(List<String> userIds)
      throws Exception {
    FindSubscribeCourseAndTimeParam paramObj = new FindSubscribeCourseAndTimeParam();
    paramObj.setUserIds(userIds);
    return super.findList(FIND_USER_LAST_SUBSCRIBE_INFO, paramObj);
  }

  /**
   * 
   * Title: 查询团训学员已上约课信息的教师名称,开始上课时间,教师评论<br>
   * Description: findTuanxunCourseComment<br>
   * CreateDate: 2017年1月16日 下午9:37:27<br>
   * 
   * @category 查询团训学员已上约课信息的教师名称,开始上课时间,教师评论
   * @author allen.chang
   * @param userIds
   * @return
   */
  public List<FindTuanxunCourseCommentParam> findTuanxunCourseComment(List<String> userIds,
      Date currentDate) throws Exception {
    FindTuanxunCourseCommentParam paramObj = new FindTuanxunCourseCommentParam();
    paramObj.setUserIds(userIds);
    paramObj.setStartTime(currentDate);
    return super.findList(FIND_TUANXUN_SUBSCRIBR_INFO, paramObj);
  }

  /**
   * Title: 根据课程类型查询预约信息<br>
   * Description: findSubscribeCourseAndStudentPageByCourseType<br>
   * CreateDate: 2017年4月30日 下午4:40:37<br>
   * 
   * @category 根据课程类型查询预约信息
   * @author seven.gz
   * @param paramMap
   *          条件
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @param courseType
   *          课程类型
   */
  public Page findSubscribeCourseAndStudentPageByCourseType(Map<String, Object> paramMap,
      Date startTime,
      Date endTime, String courseType)
          throws Exception {
    String sort = (String) paramMap.get("sort");
    String order = (String) paramMap.get("order");
    if (sort == null) {
      sort = " startTime asc,courseId ";
      order = " ASC ";
    }

    FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam =
        new FindSubscribeCourseAndStudentParam();
    findSubscribeCourseAndStudentParam.setStartTime(startTime);
    findSubscribeCourseAndStudentParam.setEndTime(endTime);
    findSubscribeCourseAndStudentParam.setCourseType(courseType);
    String cons = (String) paramMap.get("cons");
    findSubscribeCourseAndStudentParam.setCons(cons);

    Integer page = Integer.valueOf((String) paramMap.get("page"));
    Integer rows = Integer.valueOf((String) paramMap.get("rows"));

    return super.findPageEasyui(FIND_SUBSCRIBE_COURSE_AND_STUDENT_PAGE_BY_COURSE_TYPE,
        findSubscribeCourseAndStudentParam, sort,
        order, page, rows);
  }

  /**
   * 
   * Title: 根据用户id更新用户名称<br>
   * Description: updateUserNameByUserId<br>
   * CreateDate: 2017年7月4日 下午2:38:06<br>
   * 
   * @category 根据用户id更新用户名称
   * @author seven.gz
   * @param userId
   * @param userName
   * @return
   * @throws Exception
   */
  public int updateUserNameByUserId(String userId, String userName) throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setUserId(userId);
    paramObj.setUserName(userName);
    return super.update(UPDATE_NAME_BY_ENGLISH_NAME, paramObj);
  }
}
