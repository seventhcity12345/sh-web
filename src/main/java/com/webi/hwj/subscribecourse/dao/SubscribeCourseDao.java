package com.webi.hwj.subscribecourse.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class SubscribeCourseDao extends BaseMysqlDao {
  public SubscribeCourseDao() {
    super.setTableName("t_subscribe_course");
  }

  /**
   * 查询已经预约了 但是还没有还未上过的课程
   */
  public static final String FIND_HAVE_NOTATTEND_SUBSCRIBE_COURSE_COUNT = " SELECT count(1)"
      + " FROM t_subscribe_course "
      + " WHERE is_used <> 0 "
      + " AND end_time > :currentDate "
      + " AND user_id = :user_id ";

  /**
   * 老师端查询预约数据 modified by alex 2016年12月5日 17:30:59 加入了查询一些用户的信息. modified by
   * alex 2016年12月26日 18:39:48 多查询了一下老师是否出席
   */
  private final static String FIND_SUBSCRIBE_BY_TEACHERID = " SELECT "
      + " scs.teacher_id,scs.course_title,scs.course_id,"
      + " scs.course_type,scs.course_pic, "
      + " scs.course_courseware,scs.user_id,scs.teacher_time_id AS time_id ,"
      + " scs.key_id AS lesson_id,scs.start_time,scs.subscribe_status, "
      + " scs.end_time,tt.is_confirm,u.user_photo,u.current_level,tt.is_attend,"
      + " tui.english_name,tui.gender,tui.province "
      + " FROM t_subscribe_course scs "
      + " LEFT JOIN t_teacher_time tt "
      + " ON tt.key_id = scs.teacher_time_id "
      + " AND scs.teacher_id = :teacher_id "
      + " AND tt.is_used = 1 "
      + " LEFT JOIN t_user u "
      + " ON u.key_id = scs.user_id "
      + " AND u.is_used = 1 "
      + " LEFT JOIN t_user_info tui "
      + " ON tui.key_id = u.key_id "
      + " WHERE DATE(tt.start_time)= :dt "
      + " AND tt.is_subscribe = 1 "
      // modify by seven 2017年4月12日11:37:55 教师端不显示纠音1v1
      + " AND scs.course_type != 'course_type13' "
      + " AND scs.is_used = 1 "
      + " ORDER BY tt.start_time ";

  /**
   * 查看课程tmm完成度
   */
  public static final String FIND_TELLMEMORE_PERCENT_BY_USERID_AND_COURSEID =
      " SELECT key_id, course_id, user_id, "
          + " course_title, tmm_percent "
          + " FROM t_tellmemore_percent "
          + " WHERE is_used <> 0 "
          + " AND course_id = :course_id "
          + " AND user_id = :user_id ";

  public static final String FIND_ALLTYPE_USERCOURSE_BY_USERID_ANDSTARTTIME_ANDENDTIME = " SELECT 1"
      + " FROM t_subscribe_course "
      + " WHERE is_used =1 "
      + " AND user_id = :user_id AND ( "
      + "(:start_time >= start_time AND :start_time <= end_time) "
      + "OR (:end_time >= start_time AND :end_time <= end_time) "
      + "OR (:start_time <= start_time AND :end_time >= end_time) ) ";

  /**
   * 通过用户和课程id，查找相应的预约信息
   */
  public static final String FIND_SUBSCRIBE_ONE2MANY_BY_USERID_AND_COURSEID = " SELECT key_id "
      + " FROM t_subscribe_course "
      + " WHERE is_used <> 0 "
      + " AND user_id = :user_id "
      + " AND course_id = :course_id ";

  /**
   * 取得指定用户的指定课程的最大位置(用于正常逻辑的主题课预约校验)
   * 
   * @author yangmh
   */
  private static final String FIND_SMALLPACK_MAXPOSITION_SUBSCRIBED_BY_USERID_AND_COURSE_ID =
      " SELECT cs.course_position "
          + " FROM t_subscribe_course sc "
          + " LEFT JOIN t_course_smallpack cs ON sc.course_id = cs.key_id "
          + " WHERE sc.is_used <> 0 "
          + " AND cs.is_used <> 0 "
          + " AND sc.user_id = :user_id "
          + " ORDER BY cs.course_position DESC ";
  /**
   * 按时间查询预约相关数据(目前用于定时任务报警)
   * 
   * @author yangmh
   */
  private static final String FIND_SUBSCRIBE_BY_TIME = " SELECT "
      + " sc.key_id, sc.user_id,sc.course_title, sc.teacher_id, sc.start_time, sc.end_time, sc.course_type,sc.subscribe_remark, "
      + " t.teacher_name, u.user_name, u.phone, tbu.account "
      + " FROM t_subscribe_course sc "
      + " LEFT JOIN t_user u ON u.key_id = sc.user_id "
      + " LEFT JOIN t_teacher_time tt ON sc.teacher_time_id = tt.key_id "
      + " LEFT JOIN t_teacher t ON tt.teacher_id = t.key_id "
      + " LEFT JOIN t_badmin_user tbu ON tbu.key_id = sc.create_user_id "
      + " WHERE sc.is_used = 1 AND u.is_used = 1 AND tt.is_used = 1 AND sc.start_time BETWEEN :startDate AND :endDate ";

  /**
   * 根据课程类型和结束时间和用户id来筛选预约信息
   * 
   * @author komi.zsy
   */
  private static final String FIND_SUBSCRIBE_LIST_BY_USERID_AND_COURSETYPE_AND_DATE =
      "SELECT key_id,course_id "
          + " FROM t_subscribe_course "
          + " WHERE user_id = :userId and course_type = :courseType"
          + " AND end_time >= :endTime AND is_used = 1";

  /**
   * 根据教师id修改教师相关信息（教师名字）
   * 
   * @author komi.zsy
   */
  private final static String UPDATE_TEACHERNAME_BY_TEACHERID = "UPDATE t_subscribe_course "
      + " SET teacher_name =:teacher_name "
      + " WHERE teacher_id =:teacher_id ";

  private final static String UPDATE_SUBSCRIBEUSERID_BY_USERID = "UPDATE t_subscribe_course "
      + " SET update_user_id =:update_user_id , is_used = :is_used"
      + " WHERE key_id =:key_id ";

  /**
   * 查找noshow的课程相关信息
   */
  private static final String FIND_NOSHOW_COURSE_PAGE =
      " SELECT tsc.user_id,tsc.start_time,tsc.course_title,tu.user_name AS 'tu.user_name',tui.english_name,tu.user_code, "
          + " tu.phone,tbu.admin_user_name,tsc.course_type,tuf.last_follow_time "
          + " FROM t_subscribe_course tsc "
          + " LEFT JOIN t_user tu ON tsc.user_id = tu.key_id AND tu.is_used = 1 "
          + " LEFT JOIN t_user_info tui ON tsc.user_id = tui.key_id AND tui.is_used = 1 "
          + " LEFT JOIN t_badmin_user tbu ON tu.learning_coach_id = tbu.key_id AND tbu.is_used = 1 "
          + " LEFT JOIN "
          + " (SELECT user_id,MAX(create_date) last_follow_time FROM t_user_followup WHERE is_used = 1 "
          + " GROUP BY user_id) tuf ON tuf.user_id = tu.key_id "
          + " WHERE tsc.end_time <= :endTime AND tsc.is_used = 1 AND tsc.subscribe_status = 0 AND tsc.course_type != 'course_type8' "
          + " AND tsc.course_type != 'course_type5'";

  /**
   * Title: 按时间查询预约相关数据(目前用于定时任务报警)<br>
   * Description: findSubscribeListByTime<br>
   * CreateDate: 2016年2月27日 下午2:59:50<br>
   * 
   * @category 按时间查询预约相关数据(目前用于定时任务报警)
   * @author yangmh
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findSubscribeListByTime(Map<String, Object> paramMap)
      throws Exception {
    return super.findList(FIND_SUBSCRIBE_BY_TIME, paramMap);
  }

  /**
   * Title: 老师端查询预约数据<br>
   * Description: findSubscribeListByTeacherId<br>
   * CreateDate: 2015年12月19日 下午1:25:49<br>
   * 
   * @category 老师端查询预约数据
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findSubscribeListByTeacherId(Map<String, Object> paramMap)
      throws Exception {
    return super.findList(FIND_SUBSCRIBE_BY_TEACHERID, paramMap);
  }

  /**
   * Title: 取得指定用户的指定课程的最大位置(用于正常逻辑的主题课预约校验)<br>
   * Description: findSmallpackMaxPositionSubscribedByUserIdAndCourseType<br>
   * CreateDate: 2015年11月27日 下午5:58:14<br>
   * 
   * @category 取得指定用户的指定课程的最大位置(用于正常逻辑的主题课预约校验)
   * @author alex.ymh
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int findSmallpackMaxPositionSubscribedByUserIdAndCourseId(Map<String, Object> paramMap)
      throws Exception {
    Map<String, Object> returnMap = findOne(
        FIND_SMALLPACK_MAXPOSITION_SUBSCRIBED_BY_USERID_AND_COURSE_ID, paramMap);
    if (returnMap != null) {
      return (int) returnMap.get("course_position");
    } else {
      // 还没有约过主题课
      return 0;
    }
  }

  /**
   * Title: 通过用户和课程id，查找相应的预约信息<br>
   * Description: findSubscribeOne2ManyCourse<br>
   * CreateDate: 2015年9月24日 下午4:58:21<br>
   * 
   * @category 通过用户和课程id，查找相应的预约信息
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findSubscribeOne2ManyCourse(Map<String, Object> paramMap)
      throws Exception {
    return super.findOne(FIND_SUBSCRIBE_ONE2MANY_BY_USERID_AND_COURSEID, paramMap);
  }

  /**
   * 
   * Title: 查询用户user_id在相同时间段start_time && end_time 内，不能重复预约 （对1v1和小包课有效）<br>
   * Description: findAllTypeUserCourseByUserIdAndStartTimeAndEndTime<br>
   * CreateDate: 2015年9月25日 上午11:26:24<br>
   * 
   * @category 查询用户user_id在相同时间段start_time && end_time 内，不能重复预约
   * @author athrun.cw
   * @param userId
   *          当前用户id
   * @param available_start_time
   *          开始时间
   * @param available_end_time
   *          结束时间
   * @return
   * @throws Exception
   */
  public Map<String, Object> findAllTypeUserCourseByUserIdAndStartTimeAndEndTime(
      Map<String, Object> paramMap) throws Exception {
    return super.findOne(FIND_ALLTYPE_USERCOURSE_BY_USERID_ANDSTARTTIME_ANDENDTIME, paramMap);
  }

  /**
   * Title: 查找用户结束时间大于等于当前时间的所有预约信息<br>
   * Description: 查找用户结束时间大于等于当前时间的所有预约信息<br>
   * CreateDate: 2016年7月6日 上午10:42:48<br>
   * 
   * @category 查找用户结束时间大于等于当前时间的所有预约信息
   * @author komi.zsy
   * @param userId
   * @param endTime
   * @param courseType
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findSubscribeListByUserIdAndCourseTypeAndDate(String userId,
      Date endTime, String courseType) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("userId", userId);
    paramMap.put("endTime", endTime);
    paramMap.put("courseType", courseType);

    return super.findList(FIND_SUBSCRIBE_LIST_BY_USERID_AND_COURSETYPE_AND_DATE, paramMap);
  }

  /**
   * Title: 根据教师id修改教师相关信息（教师姓名）<br>
   * Description: 根据教师id修改教师相关信息（教师姓名）<br>
   * CreateDate: 2016年4月21日 下午3:06:06<br>
   * 
   * @category 根据教师id修改教师相关信息（教师姓名）
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int updateTeacherNameByTeacherId(Map<String, Object> paramMap) throws Exception {
    return super.update(UPDATE_TEACHERNAME_BY_TEACHERID, paramMap);

  }

  /**
   * Title: 查询当前teacherTime数据对应的预约数量<br>
   * Description: 用于lecture取消预约<br>
   * CreateDate: 2016年4月27日 下午6:08:51<br>
   * 
   * @category 查询当前teacherTime数据对应的预约数量
   * @author yangmh
   * @param teacherTimeId
   * @return
   * @throws Exception
   */
  public int findCountSubscribeByTeacherTimeId(String teacherTimeId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("teacher_time_id", teacherTimeId);
    return super.findCount(paramMap);

  }

  /**
   * 
   * Title: 更新人<br>
   * Description: updateSubscribeUserIdByUserId<br>
   * CreateDate: 2016年5月25日 下午6:04:09<br>
   * 
   * @category updateSubscribeUserIdByUserId
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int updateSubscribeUserIdByUserId(Map<String, Object> paramMap) throws Exception {
    return super.update(UPDATE_SUBSCRIBEUSERID_BY_USERID, paramMap);

  }

  /**
   * 
   * Title: 查询已经预约了 但是还没有还未上过的课程<br>
   * Description: haveNotAttendSubscribeCourseCount<br>
   * CreateDate: 2016年5月31日 上午10:37:12<br>
   * 
   * @category 查询已经预约了 但是还没有还未上过的课程
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int findHaveNotAttendSubscribeCourseCount(Map<String, Object> paramMap) throws Exception {
    return super.findCount(FIND_HAVE_NOTATTEND_SUBSCRIBE_COURSE_COUNT, paramMap);
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
      paramMap.put("sort", sort);
      paramMap.put("order", order);
    }
    paramMap.put("endTime", currentTime);
    String sql = FIND_NOSHOW_COURSE_PAGE;
    if (!StringUtils.isEmpty(learningCoachId)) {
      sql += " and tu.learning_coach_id = :learningCoachId ";
      paramMap.put("learningCoachId", learningCoachId);
    }

    return super.findPageEasyui(paramMap, sql);
  }

}