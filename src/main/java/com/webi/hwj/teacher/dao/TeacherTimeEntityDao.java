package com.webi.hwj.teacher.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.param.FindTeacherCourseCenterCoreCourseParam;
import com.webi.hwj.teacher.param.FindTeacherCourseCenterExtraCourseParam;
import com.webi.hwj.teacher.param.FindTeacherCourseCenterTopGreenRedDateParam;
import com.webi.hwj.teacher.param.FindTeacherTimeAndTeacherParam;
import com.webi.hwj.teacher.param.FindTimesAndTeachersByDayParam;
import com.webi.hwj.teacher.param.TeacherTimeParam;

@Repository
public class TeacherTimeEntityDao extends BaseEntityDao<TeacherTime> {
  /**
   * 查询指定时间内已被排时间的老师 seven.gz
   */
  private static final String FIND_OVERLAP_TIME_TEACHERS = " SELECT teacher_id FROM t_teacher_time "
      + " WHERE is_used = 1 " + " AND teacher_id IN (:teacherIds) "
      + " AND ((:startTime >= start_time AND :startTime < end_time) "
      + " OR (:endTime > start_time AND :endTime <= end_time) "
      + " OR (:startTime <= start_time AND :endTime >= end_time)) ";

  /**
   * 查询1v1排课 seven.gz
   */
  private static final String FIND_ONE2ONE_SCHEDULING_EASYUI = " SELECT ttt.key_id,ttt.teacher_id, "
      + " ttt.course_type,tt.teacher_course_type, "
      + " ttt.start_time,ttt.end_time,ttt.is_subscribe, ttt.is_confirm,ttt.teacher_name,tt.`third_from` "
      // modify by seven 2016年9月22日14:52:20 修改1v1排课列表界面无法使用老师名称组合查询bug
      + " FROM t_teacher_time ttt  LEFT JOIN `t_teacher` tt ON tt.`key_id` = ttt.`teacher_id` "
      + " WHERE ttt.course_type='course_type1' AND ttt.is_used=1 ";

  private static final String FIND_TEACHER_TIME_BY_KEYIDS =
      " SELECT key_id,webex_room_host_id,is_attend FROM t_teacher_time WHERE is_used = 1 AND key_id IN (:teacherTimeIds) ";

  /**
   * 查询老师时间和老师信息
   */
  private static final String FIND_TEACHER_TIME_AND_TEACHER_INFO_LIST =
      // " SELECT
      // ttt.key_id,tt.third_from,tt.teacher_name,ttt.start_time,ttt.end_time,ttt.is_confirm,ttt.course_type
      // "
      // + " FROM t_teacher_time ttt " + " LEFT JOIN t_teacher tt " + " ON
      // ttt.teacher_id = tt.key_id "
      // + " WHERE ttt.is_used = 1 AND tt.is_used = 1 " + " AND ttt.start_time
      // >=
      // :startTime "
      // + " AND ttt.end_time <= :endTime " + " AND ttt.is_subscribe = 1 ";

  " SELECT  DISTINCT (ttt.key_id), tt.third_from,  tt.teacher_name,  ttt.start_time,  ttt.end_time,  "
      + " ttt.is_confirm,  tsc.course_type ,tcos.course_level"
      + " FROM  t_subscribe_course tsc   "
      + " LEFT JOIN t_teacher_time ttt    "
      + " ON ttt.key_id = tsc.teacher_time_id   "
      + " LEFT JOIN t_teacher tt   "
      + "  ON ttt.teacher_id = tt.key_id "
      + " LEFT JOIN t_course_one2many_scheduling tcos"
      + " ON tcos.key_id = tsc.course_id AND tcos.is_used = 1"
      + " WHERE ttt.is_used = 1   AND tt.is_used = 1  "
      + " AND ttt.start_time >= :startTime   AND ttt.end_time <= :endTime  "
      + " AND ttt.is_subscribe = 1   AND tsc.is_used = 1 ";
  /**
   * 查找老师排课时间，用于webex消费者
   */
  private static final String FIND_TEACHER_TIME_BY_KEY_ID_ON_WEBEX =
      " SELECT key_id,start_time,end_time,webex_room_host_id,webex_meeting_key,course_type"
          + " FROM t_teacher_time"
          + " WHERE key_id = :keyId ";

  /**
   * 级联更新老师名称，用于修改老师信息
   */
  private static final String UPDATE_TEACHER_NAME_BY_TEACHER_ID =
      " UPDATE t_teacher_time SET teacher_name = :teacherName WHERE teacher_id = :teacherId ";

  private static final String FIND_TIMES_AND_TEACHERS_BY_DAY =
      " SELECT  ttt.key_id,ttt.start_time,ttt.end_time,tt.key_id teacher_id,tt.teacher_name,tt.teacher_photo "
          + " FROM  t_teacher_time ttt   LEFT JOIN t_teacher tt   ON ttt.teacher_id = tt.key_id "
          + " WHERE ttt.is_used <> 0  AND tt.is_used <> 0  AND ttt.is_subscribe = 0  AND ttt.course_type = 'course_type1' "
          + " AND ttt.webex_meeting_key <> '' AND end_time < :endTime  AND ttt.start_time >= :startTime "
          // modify by seven 2016年12月27日15:31:16 过滤老师时间需要用老师的权限 这里只有course_type1
          // 用了其他地方没有
          + " AND tt.teacher_course_type LIKE '%course_type1%' "
          + " ORDER BY ttt.start_time ASC ";

  /**
   * 根据时间查询已经预约的core和extension课程数
   */
  private static final String FIND_COUNT_BY_STARTTIME_AND_ENDTIME =
      " SELECT COUNT(1) FROM t_teacher_time "
          + " WHERE start_time >= :startTime "
          + " AND end_time <= :endTime "
          + " AND (course_type = 'course_type1' OR course_type = 'course_type2' "
          + "  OR course_type = 'course_type9' OR course_type = 'course_type11' ) "
          + " AND is_used = 1  AND is_subscribe = 1 ";

  /**
   * 根据courseType查询日期列表.
   */
  private static final String FIND_FOURTEEN_EACHER_TIME_LIST =
      "SELECT date(ttt.start_time) start_time "
          + " FROM t_teacher_time ttt LEFT JOIN t_teacher tt ON ttt.teacher_id = tt.key_id "
          + " WHERE ttt.is_used = 1 "
          + " AND ttt.is_subscribe = 0 "
          + " AND ttt.start_time >= :startTime "
          + " AND ttt.start_time < :endTime "
          + " AND ttt.course_type = :courseType "
          // modify by seven 2016年12月27日15:31:16 过滤老师时间需要用老师的权限
          + " AND tt.teacher_course_type LIKE '%course_type1%' "
          + " GROUP BY date(ttt.start_time)";

  /**
   * 查询某一天拥有某些权限的老师时间
   */
  private static final String FIND_TIMES_AND_TEACHERS_BY_DAY_AND_COURSETYPE =
      " SELECT  ttt.key_id,ttt.start_time,ttt.end_time,tt.key_id teacher_id,tt.teacher_name, "
          + " tt.teacher_photo,tt.third_from,tt.teacher_nationality,tt.teacher_job_type "
          + " FROM  t_teacher_time ttt   LEFT JOIN t_teacher tt   ON ttt.teacher_id = tt.key_id "
          + " WHERE ttt.is_used <> 0  AND tt.is_used <> 0  AND ttt.is_subscribe = 0  AND ttt.course_type = 'course_type1' "
          + " AND ttt.start_time > :startTime "
          + " AND ttt.webex_meeting_key <> '' AND DATE(ttt.start_time) = DATE(:day) "
          + " AND tt.teacher_course_type LIKE :teacherCourseType "
          + " ORDER BY ttt.start_time ASC ";

  private static final String FIND_TEACHER_COURSE_CENTER_TOP_AVAILABLE_DATE = ""
      + "SELECT DISTINCT DATE(start_time) start_time "
      + "FROM t_teacher_time "
      + "WHERE teacher_id = :teacherId "
      + "AND is_used =1 AND DATE(start_time) >= :queryDate";

  private static final String FIND_TEACHER_COURSE_CENTER_TOP_GREEN_RED_DATE_SMALL = ""
      + " SELECT DATE(tt.start_time) start_time, COUNT(DISTINCT tt.key_id) ct, tt.is_confirm "
      + " FROM t_teacher_time tt "
      + " WHERE tt.teacher_id = :teacherId "
      + " AND tt.start_time >= :curDate "
      + " AND tt.is_subscribe = 1 "
      + " AND tt.is_used = 1 "
      + " AND tt.course_type = 'course_type1' "
      + " GROUP BY DATE(tt.start_time) ,tt.is_confirm ";

  private static final String FIND_TEACHER_COURSE_CENTER_TOP_GREEN_RED_DATE_BIG = ""
      + " SELECT DATE(tt.start_time) start_time, COUNT(DISTINCT tt.key_id) ct, tt.is_confirm "
      + " FROM t_teacher_time tt "
      + " WHERE tt.teacher_id = :teacherId "
      + " AND tt.start_time >= :curDate "
      + " AND tt.is_used = 1 "
      + " AND tt.course_type IN ('course_type2', 'course_type5', 'course_type8') "
      + " GROUP BY DATE(tt.start_time) ,tt.is_confirm ";

  private static final String FIND_TEACHER_COURSE_CENTER_CORE_COURSE = "SELECT "
      + "tsc.course_title,tsc.course_id,tsc.course_type,tsc.course_pic,"
      + "tsc.course_courseware,tsc.user_id,tsc.teacher_time_id,"
      + "ttt.start_time,tsc.subscribe_status,tsc.key_id AS subscribe_id,"
      + "ttt.end_time,ttt.is_confirm,ttt.teacher_id,tu.user_photo,tu.current_level,"
      + "tui.english_name,tui.gender,tui.province,tbu.admin_user_name,tsc.subscribe_type "
      + "FROM t_teacher_time ttt "
      + "RIGHT JOIN t_subscribe_course tsc ON ttt.key_id = tsc.teacher_time_id "
      + "LEFT JOIN t_user tu ON tu.key_id = tsc.user_id "
      + "LEFT JOIN t_user_info tui ON tu.key_id = tui.key_id "
      + "LEFT JOIN t_badmin_user tbu ON tbu.key_id = tsc.create_user_id "
      + "WHERE tsc.teacher_id = :teacherId "
      + "AND ttt.course_type = 'course_type1' "
      + "AND DATE(ttt.start_time)=:queryDate "
      + "AND ttt.is_subscribe = 1 "
      + "AND tsc.is_used = 1 "
      + "ORDER BY ttt.start_time ";

  private static final String FIND_TEACHER_COURSE_CENTER_EXTRA_COURSE = "SELECT "
      + "tcos.course_title,COUNT(tsc.key_id) AS attendeeCount,"
      + "tcos.start_time,tcos.end_time,tcos.course_pic,tcos.course_courseware,"
      + "tcos.course_type,tcos.teacher_time_id,ttt.is_confirm "
      + "FROM t_course_one2many_scheduling tcos "
      + "LEFT JOIN t_subscribe_course tsc ON tsc.course_id = tcos.key_id AND tsc.is_used = 1 "
      + "LEFT JOIN t_teacher_time ttt ON tcos.teacher_time_id = ttt.key_id "
      + "WHERE tcos.is_used = 1 AND tcos.teacher_id = :teacherId "
      + "AND DATE(tcos.start_time) = :queryDate "
      + "GROUP BY tcos.key_id "
      + "ORDER BY tcos.start_time ";

  /**
   * 根据房间id查询
   */
  private static final String FIND_BY_DAY_AND_WEBEX_ROOM_HOST_ID =
      " SELECT start_time,end_time FROM t_teacher_time WHERE is_used = 1 "
          + " AND  start_time > :startTime AND webex_room_host_id = :webexRoomHostId "
          + " AND DATE(start_time) = :day ";

  /**
   * Title: 查询教师端-课程中心-core课程列表.<br>
   * Description: 没有约过的课程是不查询出来的<br>
   * CreateDate: 2017年2月13日 下午2:26:50<br>
   * 
   * @category 查询教师端-课程中心-core课程列表
   * @author yangmh
   * @param queryDate
   *          查询日期
   */
  public List<FindTeacherCourseCenterCoreCourseParam> findTeacherCourseCenterCoreCourseList(
      String queryDate, String teacherId) throws Exception {
    FindTeacherCourseCenterCoreCourseParam param = new FindTeacherCourseCenterCoreCourseParam();
    param.setQueryDate(queryDate);
    param.setTeacherId(teacherId);
    param.setCurDate(new Date());
    return super.findList(FIND_TEACHER_COURSE_CENTER_CORE_COURSE, param);
  }

  /**
   * Title: 查询教师端-课程中心-extra课程列表.<br>
   * Description: 没有约过的课程也可以查询出来,包括新概念课程<br>
   * CreateDate: 2017年2月13日 下午2:26:50<br>
   * 
   * @category 查询教师端-课程中心-core课程列表
   * @author yangmh
   * @param queryDate
   *          查询日期
   * @param teacherId
   *          老师id
   */
  public List<FindTeacherCourseCenterExtraCourseParam> findTeacherCourseCenterExtraCourseList(
      String queryDate, String teacherId) throws Exception {
    FindTeacherCourseCenterExtraCourseParam param = new FindTeacherCourseCenterExtraCourseParam();
    param.setQueryDate(queryDate);
    param.setTeacherId(teacherId);
    return super.findList(FIND_TEACHER_COURSE_CENTER_EXTRA_COURSE, param);
  }

  /**
   * Title: 查询教师端-课程中心头部红绿图标数据(大课).<br>
   * Description: findTeacherCourseCenterTopGreenRedDateList<br>
   * CreateDate: 2017年2月10日 下午5:45:14<br>
   * 
   * @category 查询教师端-课程中心头部红绿图标数据(大课)
   * @author yangmh
   * @param teacherId
   *          老师id
   * @param curDate
   *          查询日期
   */
  public List<FindTeacherCourseCenterTopGreenRedDateParam>
      findTeacherCourseCenterTopGreenRedDateBigList(
          String teacherId,
          String curDate) throws Exception {
    FindTeacherCourseCenterTopGreenRedDateParam findTeacherCourseCenterTopGreenRedDateParam =
        new FindTeacherCourseCenterTopGreenRedDateParam();
    findTeacherCourseCenterTopGreenRedDateParam.setCurDate(curDate);
    findTeacherCourseCenterTopGreenRedDateParam.setTeacherId(teacherId);

    return super.findList(FIND_TEACHER_COURSE_CENTER_TOP_GREEN_RED_DATE_BIG,
        findTeacherCourseCenterTopGreenRedDateParam);
  }

  /**
   * Title: 查询教师端-课程中心头部红绿图标数据(小课).<br>
   * Description: findTeacherCourseCenterTopGreenRedDateList<br>
   * CreateDate: 2017年2月10日 下午5:45:14<br>
   * 
   * @category 查询教师端-课程中心头部红绿图标数据(小课)
   * @author yangmh
   * @param teacherId
   *          老师id
   * @param curDate
   *          查询日期
   */
  public List<FindTeacherCourseCenterTopGreenRedDateParam>
      findTeacherCourseCenterTopGreenRedDateSmallList(
          String teacherId,
          String curDate) throws Exception {
    FindTeacherCourseCenterTopGreenRedDateParam findTeacherCourseCenterTopGreenRedDateParam =
        new FindTeacherCourseCenterTopGreenRedDateParam();
    findTeacherCourseCenterTopGreenRedDateParam.setCurDate(curDate);
    findTeacherCourseCenterTopGreenRedDateParam.setTeacherId(teacherId);

    return super.findList(FIND_TEACHER_COURSE_CENTER_TOP_GREEN_RED_DATE_SMALL,
        findTeacherCourseCenterTopGreenRedDateParam);
  }

  /**
   * Title: 查询教师端-课程中心头部可点击数据.<br>
   * Description: findTeacherCourseCenterTopAvailableDateList<br>
   * CreateDate: 2017年2月10日 下午5:24:00<br>
   * 
   * @category 查询教师端-课程中心头部可点击数据
   * @author yangmh
   * @param teacherId
   *          老师id
   * @param queryDate
   *          查询日期
   */
  public List<TeacherTime> findTeacherCourseCenterTopAvailableDateList(String teacherId,
      String queryDate)
          throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("teacherId", teacherId);
    paramMap.put("queryDate", queryDate);
    return super.findList(FIND_TEACHER_COURSE_CENTER_TOP_AVAILABLE_DATE, paramMap);
  }

  /**
   * 查询老师时间和房间信息
   */
  private static final String FIND_TEACHER_TIME_AND_ROOM_BY_KEYIDS =
      " SELECT ttt.key_id,ttt.webex_room_host_id,ttt.is_attend,twr.webex_request_url,ttt.webex_meeting_key "
          + " FROM t_teacher_time ttt LEFT JOIN t_webex_room twr "
          + " ON ttt.webex_room_host_id = twr.webex_room_host_id "
          + " WHERE ttt.is_used = 1 AND ttt.key_id IN (:teacherTimeIds) ";

  /**
   * 
   * Title: 查询指定时间内已被排时间的老师<br>
   * Description: 查询指定时间内已被排时间的老师<br>
   * CreateDate: 2016年4月28日 上午11:20:52<br>
   * 
   * @category 查询指定时间内已被排时间的老师
   * @author seven.gz
   * @param TeacherTimeParam
   *          teacher_id字段设置要查询的老师集合用','分割
   * @return
   * @throws Exception
   */
  public List<TeacherTimeParam> findOverlapTimeTeachers(TeacherTimeParam paramObj)
      throws Exception {
    return super.findList(FIND_OVERLAP_TIME_TEACHERS, paramObj);
  }

  /**
   * 
   * Title: 查询排课信息<br>
   * Description: 通过不同的courseType来定义不同的排课信息<br>
   * CreateDate: 2016年5月4日 下午5:17:23<br>
   * 
   * @category 查询排课信息
   * @author seven.gz
   * @param paramObj
   * @return
   * @throws Exception
   */
  public Page findOne2OneSchedulingEasyui(TeacherTimeParam paramObj, int page, int rows)
      throws Exception {
    return super.findPageEasyui(FIND_ONE2ONE_SCHEDULING_EASYUI, paramObj, "start_time", "desc",
        page, rows);
  }

  /**
   * Title: 查找teacherTime的webex相关信息<br>
   * Description: <br>
   * CreateDate: 2016年7月28日 下午3:56:05<br>
   * 
   * @category 查找teacherTime的webex相关信息
   * @author yangmh
   * @param teacherTimeId
   *          老师时间ID
   * @return
   * @throws Exception
   */
  public TeacherTime findTeacherTimeByKeyIdReturnWebex(String teacherTimeId) throws Exception {
    TeacherTime teacherTime = new TeacherTime();
    teacherTime.setKeyId(teacherTimeId);
    return super.findOne(FIND_TEACHER_TIME_BY_KEY_ID_ON_WEBEX, teacherTime);
  }

  /**
   * 
   * Title: 查询老师时间和老师信息<br>
   * Description: 查询老师时间和老师信息<br>
   * CreateDate: 2016年7月25日 上午11:59:43<br>
   * 
   * @category findTeacherTimeAndTeacherInfoList
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public Page findTeacherTimeAndTeacherInfo(Map<String, Object> param, Date startTime, Date endTime)
      throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      // 同时用两个排序条件 只能用小写
      sort = " startTime ";
      order = " ASC ";
    }
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    FindTeacherTimeAndTeacherParam findTeacherTimeAndTeacherParam =
        new FindTeacherTimeAndTeacherParam();
    findTeacherTimeAndTeacherParam.setCons((String) param.get("cons"));
    findTeacherTimeAndTeacherParam.setStartTime(startTime);
    findTeacherTimeAndTeacherParam.setEndTime(endTime);

    return findPageEasyui(FIND_TEACHER_TIME_AND_TEACHER_INFO_LIST, findTeacherTimeAndTeacherParam,
        sort, order,
        page, rows);
  }

  /**
   * 
   * Title: 根据keyId查询老师时间<br>
   * Description: 根据keyId查询老师时间<br>
   * CreateDate: 2016年8月1日 上午10:38:42<br>
   * 
   * @category 根据keyId查询老师时间
   * @author seven.gz
   * @param teacherTimeIds
   * @return
   * @throws Exception
   */
  public List<TeacherTimeParam> findTeacherTimeByKeyids(List<String> teacherTimeIds)
      throws Exception {
    TeacherTimeParam paramObj = new TeacherTimeParam();
    paramObj.setTeacherTimeIds(teacherTimeIds);
    return findList(FIND_TEACHER_TIME_BY_KEYIDS, paramObj);
  }

  /**
   * 
   * Title: 级联更新老师名称，用于修改老师信息<br>
   * Description: updateTeacherNameByTeacherId<br>
   * CreateDate: 2016年8月6日 下午6:22:40<br>
   * 
   * @category 级联更新老师名称，用于修改老师信息
   * @author yangmh
   * @param teacherTime
   */
  public int updateTeacherNameByTeacherId(TeacherTime teacherTime) throws Exception {
    return super.update(UPDATE_TEACHER_NAME_BY_TEACHER_ID, teacherTime);
  }

  /**
   * 
   * Title: 按天查询预约时的老师时间列表<br>
   * Description: 按天查询预约时的老师时间列表,这里为修改时区问题获取三天的数据<br>
   * CreateDate: 2016年9月6日 下午4:20:49<br>
   * 
   * @category 按天查询预约时的老师时间列表
   * @author seven.gz
   * @param day
   * @param startTime
   * @return
   * @throws Exception
   */
  public List<FindTimesAndTeachersByDayParam> findTimesAndTeachersByDay(Date startTime,
      Date endTime)
          throws Exception {
    FindTimesAndTeachersByDayParam paramObj = new FindTimesAndTeachersByDayParam();
    paramObj.setStartTime(startTime);
    paramObj.setEndTime(endTime);
    return super.findList(FIND_TIMES_AND_TEACHERS_BY_DAY, paramObj);
  }

  /**
   * Title: 根据keyid查找teacherTime<br>
   * Description: 根据keyid查找teacherTime<br>
   * CreateDate: 2016年9月7日 下午1:53:37<br>
   * 
   * @category 根据keyid查找teacherTime
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public TeacherTime findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId,
        "key_id,start_time,end_time,teacher_id,teacher_name,is_subscribe,course_type,teacher_time_platform,room_id");
  }

  public TeacherTime findOneByKeyIdForUpdate(String keyId)
      throws Exception {
    System.out.println(111);
    String querySql =
        "SELECT key_id,start_time,end_time,teacher_id,teacher_name,is_subscribe,course_type "
            + "FROM t_teacher_time where key_id = :keyId AND is_used = 1 FOR UPDATE ";
    /* 346 */ Map paramMap = new HashMap();
    /* 347 */ paramMap.put("keyId", keyId);
    /*     */
    /* 349 */ List<TeacherTime> returnList = this.namedParameterJdbcTemplate.query(querySql,
        paramMap, new BeanPropertyRowMapper(TeacherTime.class));
    /*     */
    TeacherTime aa = returnList.get(0);
    return aa;
    /*     */ }

  /**
   * 
   * Title: 根据时间查询已经预约的core和extension课程数<br>
   * Description: 根据时间查询已经预约的core和extension课程数<br>
   * CreateDate: 2016年9月21日 下午4:57:26<br>
   * 
   * @category 根据时间查询已经预约的core和extension课程数
   * @author seven.gz
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @return int
   * @throws Exception
   *           通用异常
   */
  public int findCountByStartTimeAndEndTime(Date startTime, Date endTime) throws Exception {
    TeacherTime teacherTime = new TeacherTime();
    teacherTime.setStartTime(startTime);
    teacherTime.setEndTime(endTime);
    return super.findCount(FIND_COUNT_BY_STARTTIME_AND_ENDTIME, teacherTime);
  }

  /**
   * 
   * Title: 根据courseType查询日期列表<br>
   * Description: 根据courseType查询日期列表<br>
   * CreateDate: 2016年10月17日 下午1:46:38<br>
   * 
   * @category 根据courseType查询日期列表
   * @author seven.gz
   * @param startTime
   * @param endTime
   * @param courseType
   * @return
   * @throws Exception
   */
  public List<TeacherTimeParam> findTimeDateList(Date startTime, Date endTime, String courseType)
      throws Exception {
    TeacherTimeParam paramObj = new TeacherTimeParam();
    paramObj.setStartTime(startTime);
    paramObj.setEndTime(endTime);
    paramObj.setCourseType(courseType);
    paramObj.setTeacherCourseType("%" + courseType + "%");
    return super.findList(FIND_FOURTEEN_EACHER_TIME_LIST, paramObj);
  }

  /**
   * 
   * Title: 查询拥有某些权限的老师时间<br>
   * Description: findTimesAndTeachersByDayAndCourseType<br>
   * CreateDate: 2016年12月22日 下午9:09:07<br>
   * 
   * @category 查询拥有某些权限的老师时间
   * @author seven.gz
   * @param day
   * @param startTime
   * @param courseType
   * @return
   * @throws Exception
   */
  public List<FindTimesAndTeachersByDayParam> findTimesAndTeachersByDayAndCourseType(String day,
      String courseType, Date currentDate)
          throws Exception {
    FindTimesAndTeachersByDayParam paramObj = new FindTimesAndTeachersByDayParam();
    paramObj.setDay(day);
    paramObj.setTeacherCourseType("%" + courseType + "%");
    paramObj.setStartTime(currentDate);
    return super.findList(FIND_TIMES_AND_TEACHERS_BY_DAY_AND_COURSETYPE, paramObj);
  }

  /**
   * Title: 查询老师时间和房间信息<br>
   * Description: findTeacherTimeAndRoomByKeyIds<br>
   * CreateDate: 2017年4月30日 下午4:58:01<br>
   * 
   * @category 查询老师时间和房间信息
   * @author seven.gz
   * @param teacherTimeIds
   *          老师时间ids
   */
  public List<TeacherTimeParam> findTeacherTimeAndRoomByKeyIds(List<String> teacherTimeIds)
      throws Exception {
    TeacherTimeParam paramObj = new TeacherTimeParam();
    paramObj.setTeacherTimeIds(teacherTimeIds);
    return findList(FIND_TEACHER_TIME_AND_ROOM_BY_KEYIDS, paramObj);
  }

  /**
   * 
   * Title: 按房间查询老师时间<br>
   * Description: findTeacherTimeRoomUsed<br>
   * CreateDate: 2017年6月23日 上午11:00:03<br>
   * 
   * @category 按房间查询老师时间
   * @author seven.gz
   * @param webexRoomHostId
   *          房间号
   * @param day
   *          查询的天数据
   * @param startTime
   *          过滤的开始时间
   */
  public List<TeacherTime> findListTeacherTimeRoomUsed(String webexRoomHostId, String day,
      Date startTime) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("webexRoomHostId", webexRoomHostId);
    paramMap.put("startTime", startTime);
    paramMap.put("day", day);
    return super.findList(FIND_BY_DAY_AND_WEBEX_ROOM_HOST_ID, paramMap);

  }

}