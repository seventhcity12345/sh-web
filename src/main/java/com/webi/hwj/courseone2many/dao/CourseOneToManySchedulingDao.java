package com.webi.hwj.courseone2many.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.courseone2many.param.CourseOne2ManySchedulingParam;
import com.webi.hwj.courseone2many.param.CourseSchedulingParam;
import com.webi.hwj.teacher.constant.TeacherTimeConstant;

@Repository
public class CourseOneToManySchedulingDao extends BaseEntityDao<CourseOne2ManyScheduling> {
  // private static Logger logger =
  // Logger.getLogger(CourseOneToManySchedulingDao.class);

  /**
   * 根据时间和课程类型查询课程和老师信息
   * 
   * @author komi.zsy
   */
  private final static String SELECT_COURSE_INFO_BY_DATE =
      "SELECT tcos.key_id,tcos.course_title,tcos.course_type,"
          + "tcos.teacher_name,tcos.start_time,tcos.end_time,tcos.teacher_time_id,"
          + "tcos.course_desc,tcos.course_pic,tcos.teacher_photo,tt.teacher_nationality"
          + " FROM t_course_one2many_scheduling tcos"
          + " LEFT JOIN t_teacher tt"
          + " ON tt.key_id = tcos.teacher_id"
          + " WHERE tcos.course_type = :courseType"
          + " AND  tcos.end_time < :endTime AND tcos.start_time >= :startTime"
          + " AND tcos.is_used <> 0"
          + " ORDER BY tcos.start_time ASC";

  /**
   * 根据时间查询课程和老师信息
   * 
   * @author seven.gz
   */
  private final static String SELECT_COURSE_AND_TEACHER_INFO =
      " SELECT tcos.key_id,tcos.course_title,tcos.course_type"
          + ",tcos.course_desc,tcos.start_time,tcos.end_time,tcos.course_pic,tcos.course_id, "
          + " tcos.teacher_name,tcos.teacher_photo,tcos.student_url,tt.teacher_nationality,tcos.teacher_time_id"
          + " FROM t_course_one2many_scheduling tcos LEFT JOIN t_teacher tt ON  tcos.teacher_id = tt.key_id "
          + " WHERE course_type = :courseType AND end_time >= :endTime AND tcos.is_used = 1 ORDER BY tcos.start_time ASC ";

  /**
   * 根据课程类型查找课程列表信息
   * 
   * @author komi.zsy
   */
  private final static String FIND_COURSE_LIST_BY_COURSE_TYPE_AND_USER_ID =
      " SELECT tcos.key_id,tcos.course_title,tcos.course_type"
          + ",tcos.course_desc,tcos.start_time,tcos.end_time,tcos.course_pic,tcos.course_id, "
          + " tcos.teacher_name,tcos.teacher_photo,"
          + " tsc.key_id AS subscribe_id"
          + " FROM t_course_one2many_scheduling tcos"
          + " LEFT JOIN t_subscribe_course tsc"
          + " ON tsc.course_id = tcos.key_id"
          + " AND tsc.user_id = :userId  AND tsc.is_used = 1"
          + " LEFT JOIN t_teacher_time ttt"
          + " ON ttt.key_id = tcos.teacher_time_id"
          + " WHERE tcos.course_type = :courseType AND tcos.end_time >= :endTime"
          + " AND tcos.is_used = 1 "
          + " AND ttt.is_used = 1 AND ttt.teacher_time_platform = " + TeacherTimeConstant.TEACHER_TIME_PLATFORM_CLASSIN
          + " ORDER BY tcos.start_time ASC ";

  /**
   * 查询排课信息
   */
  private final static String FIND_COURSE_INFO_LIST =
      " SELECT tcos.key_id,tcos.course_type,tcos.limit_number,"
          + " tcos.teacher_name,tcos.start_time,tcos.end_time,tcos.course_title, "
          + " tcos.teacher_time_id,tcos.already_person_count FROM  t_course_one2many_scheduling tcos "
          + " WHERE DATE(tcos.start_time) = DATE(:endTime) "
          + " AND tcos.start_time > :startTime AND tcos.course_type = :courseType "
          + " AND tcos.course_level REGEXP :courseLevel  AND tcos.is_used = 1  "
          + " ORDER BY tcos.start_time ASC  ";

  /**
   * 根据courseType查询日期列表.
   */
  private static final String FIND_FOURTEEN_EACHER_TIME_LIST = "SELECT date(start_time) start_time "
      + " FROM t_course_one2many_scheduling "
      + " WHERE is_used = 1 "
      + " AND start_time >= :startTime "
      + " AND start_time < :endTime "
      + " AND course_type = :courseType "
      + " AND course_level REGEXP :courseLevel "
      + " GROUP BY date(start_time)";

  /**
   * 查询排课信息
   */
  private final static String FIND_COURSE_INFO_LIST_PC =
      " SELECT tcos.key_id,tcos.course_type,tcos.limit_number,"
          + " tcos.teacher_name,tcos.start_time,tcos.end_time,tcos.course_title, "
          + " tcos.teacher_time_id,tcos.already_person_count,tcos.teacher_photo, "
          + " tcos.teacher_id,tt.teacher_nationality,tcos.course_pic "
          + " FROM  t_course_one2many_scheduling tcos "
          + " LEFT JOIN t_teacher tt ON tcos.teacher_id = tt.key_id "
          + " WHERE DATE(tcos.start_time) = DATE(:endTime) "
          + " AND tcos.end_time > :startTime AND tcos.course_type = :courseType "
          + " AND tcos.course_level REGEXP :courseLevel  AND tcos.is_used = 1  "
          + " ORDER BY tcos.start_time ASC  ";

  /**
   * 根据keyid查询相关课程信息
   * 
   * @author komi.zsy
   */
  private final static String FIND_COURSE_INFO_BY_KEY_ID = "SELECT tcos.key_id,tcos.course_title,"
      + "tcos.course_desc,tcos.start_time,tcos.end_time,tcos.course_pic,tcos.course_id,"
      + "tcos.teacher_name, tcos.teacher_photo,tt.teacher_nationality,tt.teacher_desc,tcos.teacher_time_id"
      + " FROM t_course_one2many_scheduling tcos"
      + " LEFT JOIN t_teacher tt"
      + " ON tcos.teacher_id = tt.key_id"
      + " WHERE tcos.key_id = :keyId AND tcos.is_used = 1 AND tt.is_used = 1";

  /**
   * 趣味大课堂-根据时间查询课程和老师信息(最近的5节课程)
   * 
   * @author felix.yl
   */
  private final static String SELECT_COURSE_AND_TEACHER_INFO_ON_FUNBIGCLASS = " SELECT tcos.key_id,"
      + " tcos.course_title,"
      + " tcos.course_pic,"
      + " tcos.start_time,"
      + " tcos.end_time,"
      + " tcos.already_person_count,"
      + " tcos.teacher_photo,"
      + " tcos.teacher_name,"
      + " tt.teacher_nationality,"
      + " tcos.course_id,"
      + " tcos.course_type,"
      + " tcos.student_url,"
      + " tcos.teacher_time_id"
      + " FROM t_course_one2many_scheduling tcos"
      + " LEFT JOIN t_teacher tt"
      + " ON tcos.teacher_id = tt.key_id"
      + " WHERE course_type = :courseType"
      + " AND end_time >= :endTime"
      + " AND tcos.is_used = 1"
      + " ORDER BY tcos.start_time ASC"
      + " LIMIT 5";

  /**
   * 查询指定时间范围指定课程类型的课
   */
  public final static String FIND_LIST_BY_COURSE_TYPE_AND_END_TIME =
      " SELECT course_type,course_title,start_time,end_time "
          + " FROM t_course_one2many_scheduling "
          + " WHERE end_time > :startTime AND end_time<:endTime AND course_type = :courseType AND is_used =1 ORDER BY start_time ASC ";
  /**
   * 
   * 传入参数：课程类型、当前时间 .<br>
   * 查询课程结束时间>=当前时间的某种课程类型的课程及老师相关信息.<br>
   * 
   * @author felix.yl
   */
  private final static String FIND_COURSE_AND_TEACHER_INFO_BY_COURSETYPE = " SELECT"
      + " tcos.key_id,"
      + " tcos.course_type,"
      + " tcos.course_desc,"
      + " tcos.course_pic,"
      + " tcos.teacher_time_id,"
      + " tcos.course_title,"
      + " tcos.course_courseware,"
      + " tcos.start_time,"
      + " tcos.end_time,"
      + " tcos.limit_number,"
      + " tcos.already_person_count,"
      + " tcos.teacher_photo,"
      + " tcos.teacher_name,"
      + " tt.teacher_nationality,"
      + " ttt.teacher_time_platform"
      + " FROM t_course_one2many_scheduling tcos"
      + " LEFT JOIN t_teacher tt"
      + " ON  tcos.teacher_id = tt.key_id"
      + " LEFT JOIN t_teacher_time ttt"
      + " ON  tcos.teacher_time_id = ttt.key_id"
      + " WHERE tcos.course_type = :courseType"
      + " AND tcos.end_time >= :paramTime"
      + " AND tcos.is_used = 1"
      + " ORDER BY tcos.start_time ASC";

  /**
   * Title: 根据keyid查询相关课程信息<br>
   * Description: 根据keyid查询相关课程信息<br>
   * CreateDate: 2017年4月10日 下午4:12:33<br>
   * 
   * @category 根据keyid查询相关课程信息
   * @author komi.zsy
   * @param keyId
   *          大课排课表id
   * @return
   * @throws Exception
   */
  public CourseOne2ManySchedulingParam findCourseInfoByKeyId(String keyId) throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    paramObj.setKeyId(keyId);
    return super.findOne(FIND_COURSE_INFO_BY_KEY_ID, paramObj);
  }

  /**
   * Title: 根据课程类型和上课时间范围查询课程信息<br>
   * Description: 根据课程类型和上课时间范围查询课程信息<br>
   * CreateDate: 2016年4月25日 下午6:34:10<br>
   * 
   * @category 根据课程类型和上课时间范围查询课程信息
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findSchedulingByCourseTypeAndDate(
      CourseOne2ManySchedulingParam paramObj) throws Exception {
    return super.findList(SELECT_COURSE_INFO_BY_DATE, paramObj);
  }

  /**
   * 
   * Title: findSchedulingAndTeacherInfo<br>
   * Description: findSchedulingAndTeacherInfo<br>
   * CreateDate: 2016年5月16日 上午11:49:24<br>
   * 
   * @category findSchedulingAndTeacherInfo
   * @author seven.gz
   * @param courseType
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findSchedulingAndTeacherInfo(String courseType,
      Date endTime) throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    // 这里课程类型是EnglishStdio课
    paramObj.setCourseType(courseType);
    // 设置为当前时间
    paramObj.setEndTime(endTime);
    return super.findList(SELECT_COURSE_AND_TEACHER_INFO, paramObj);
  }

  /**
   * Title: 根据课程类型查找课程列表信息<br>
   * Description: 根据课程类型查找课程列表信息<br>
   * CreateDate: 2017年8月24日 下午4:30:16<br>
   * 
   * @category 根据课程类型查找课程列表信息
   * @author komi.zsy
   * @param courseType
   *          课程类型
   * @param userId
   *          用户id
   * @param endTime
   *          结束时间
   * @return
   * @throws Exception
   */
  public Page findCourseListByCourseTypeAndUserId(String courseType, String userId,
      Date endTime, Integer page, Integer rows) throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    // 课程类型
    paramObj.setCourseType(courseType);
    // 设置为当前时间
    paramObj.setEndTime(endTime);
    paramObj.setUserId(userId);
    return super.findPage(FIND_COURSE_LIST_BY_COURSE_TYPE_AND_USER_ID, paramObj, page, rows);
  }

  /**
   * Title: 根据课程类型查找最近一节课程信息<br>
   * Description: 根据课程类型查找最近一节课程信息<br>
   * CreateDate: 2017年8月24日 下午4:30:16<br>
   * 
   * @category 根据课程类型查找最近一节课程信息
   * @author komi.zsy
   * @param courseType
   *          课程类型
   * @param userId
   *          用户id
   * @param endTime
   *          结束时间
   * @return
   * @throws Exception
   */
  public CourseOne2ManySchedulingParam findCourseHeadByCourseTypeAndUserId(String courseType,
      String userId,
      Date endTime) throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    // 课程类型
    paramObj.setCourseType(courseType);
    // 设置为当前时间
    paramObj.setEndTime(endTime);
    paramObj.setUserId(userId);
    return super.findOne(FIND_COURSE_LIST_BY_COURSE_TYPE_AND_USER_ID + " LIMIT 1", paramObj);
  }

  /**
   * * Title: 根据keyid查找排课数据<br>
   * Description: 根据keyid查找排课数据<br>
   * CreateDate: 2016年8月16日 下午4:44:37<br>
   * 
   * @category 根据keyid查找排课数据
   * @author komi.zsy
   * @param keyId
   * @param columnName
   * @return
   * @throws Exception
   */
  public CourseOne2ManyScheduling findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "key_id,course_title,student_url");
  }

  /**
   * 
   * Title: 查询课程列表<br>
   * Description: 查询课程列表<br>
   * CreateDate: 2016年10月17日 下午4:55:37<br>
   * 
   * @category 查询课程列表
   * @author seven.gz
   * @param teacherTimeDate
   * @param startTime
   * @param courseType
   * @param courseLevel
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findCourseInfoList(Date teacherTimeDate,
      Date startTime,
      String courseType, String courseLevel) throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    paramObj.setCourseLevel(courseLevel);
    paramObj.setStartTime(startTime);
    paramObj.setEndTime(teacherTimeDate);
    paramObj.setCourseType(courseType);
    return super.findList(FIND_COURSE_INFO_LIST, paramObj);
  }

  /**
   * 
   * Title: pc1v6根据日期查询列表<br>
   * Description: pc1v6根据日期查询列表<br>
   * CreateDate: 2016年12月15日 上午11:41:27<br>
   * 
   * @category pc1v6根据日期查询列表
   * @author seven.gz
   * @param teacherTimeDate
   * @param startTime
   * @param courseType
   * @param courseLevel
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findCourseInfoListPc(Date teacherTimeDate,
      Date startTime,
      String courseType, String courseLevel) throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    paramObj.setCourseLevel(courseLevel);
    paramObj.setStartTime(startTime);
    paramObj.setEndTime(teacherTimeDate);
    paramObj.setCourseType(courseType);
    return super.findList(FIND_COURSE_INFO_LIST_PC, paramObj);
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
  public List<CourseOne2ManySchedulingParam> findTimeDateList(Date startTime, Date endTime,
      String courseType, String courseLevel)
          throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    paramObj.setStartTime(startTime);
    paramObj.setEndTime(endTime);
    paramObj.setCourseType(courseType);
    paramObj.setCourseLevel(courseLevel);
    return super.findList(FIND_FOURTEEN_EACHER_TIME_LIST, paramObj);
  }

  /**
   * 
   * Title: 趣味大讲堂<br>
   * Description: findSchedulingAndTeacherInfo<br>
   * CreateDate: 2017年7月5日 下午6:24:59<br>
   * 
   * @category 趣味大讲堂-查询课程相关信息
   * @author felix.yl
   * @param courseType
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findEnglishStidoOnFunBigClassAndTeacherInfo(
      String courseType, Date endTime) throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    paramObj.setCourseType(courseType);// 类型是EnglishStdio课
    paramObj.setEndTime(endTime);// 当前时间
    return super.findList(SELECT_COURSE_AND_TEACHER_INFO_ON_FUNBIGCLASS, paramObj);
  }

  /**
   * 
   * Title: 查询指定时间范围指定课程类型的课<br>
   * Description: findListByCourseTypeAndEndTime<br>
   * CreateDate: 2017年7月20日 下午4:07:12<br>
   * 
   * @category 查询指定时间范围指定课程类型的课
   * @author seven.gz
   * @param courseType
   *          课程类型
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   */
  public List<CourseOne2ManyScheduling> findListByCourseTypeAndEndTime(String courseType,
      Date startTime, Date endTime) throws Exception {
    CourseOne2ManyScheduling paramObj = new CourseOne2ManyScheduling();
    paramObj.setCourseType(courseType);
    paramObj.setStartTime(startTime);
    paramObj.setEndTime(endTime);
    return super.findList(FIND_LIST_BY_COURSE_TYPE_AND_END_TIME, paramObj);
  }

  /**
   * 
   * Title: 会员中心改版-查询课程预约相关信息<br>
   * Description: 会员中心改版-传入课程类型和当前时间,查询对应课程类型的、课程结束时间大于等于当前时间的课程以及相对应的老师信息<br>
   * CreateDate: 2017年7月20日 下午4:25:24<br>
   * 
   * @category 会员中心改版-传入课程类型和当前时间,查询对应课程类型的、课程结束时间大于等于当前时间的课程以及相对应的老师信息
   * @author felix.yl
   * @param courseType
   * @param currentTime
   * @return
   * @throws Exception
   */
  public List<CourseSchedulingParam> findCourseAndTeacherInfoList(String courseType,
      Date paramTime) throws Exception {
    CourseSchedulingParam paramObj = new CourseSchedulingParam();
    paramObj.setCourseType(courseType);
    paramObj.setParamTime(paramTime);
    return super.findList(FIND_COURSE_AND_TEACHER_INFO_BY_COURSETYPE, paramObj);
  }

}