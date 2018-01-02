package com.webi.hwj.teacher.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.entity.TeacherTimeSign;
import com.webi.hwj.teacher.param.TeacherTimeSignParam;

@Repository
public class TeacherTimeSignDao extends BaseEntityDao<TeacherTimeSign> {
  // private static Logger logger = Logger.getLogger(TeacherTimeSignDao.class);

  /**
   * 根据教师id查询当日以及之后的教师签课信息
   * 
   * @author komi.zsy
   */
  private static final String SELECT_TEACHER_TIME_SIGN_INFO_BY_TEACHER_ID_AFTER_TODAY = "SELECT start_time,end_time "
      + " FROM t_teacher_time_sign"
      + " WHERE teacher_id = :teacherId AND is_used <> 0 AND start_time >= :startTime";

  /**
   * 根据教师id修改教师相关信息
   * 
   * @author komi.zsy
   */
  private final static String UPDATE_TEACHER_INFO_BY_TEACHERID = "UPDATE t_teacher_time_sign "
      + " SET teacher_name =:teacherName,teacher_course_type = :teacherCourseType "
      + " WHERE teacher_id =:teacherId ";

  /**
   * 查找要添加的签课时间，是否已存在（或相交，重复，包含）
   * 
   * @author komi.zsy
   */
  private final static String SELECT_TEACHER_TIME_SIGN_IS_EXIST_BY_ADD_TIME = "SELECT COUNT(1)"
      + " FROM t_teacher_time_sign"
      + " WHERE teacher_id = :teacherId AND is_used <> 0"
      + " AND ((:startTime >= start_time AND :startTime < end_time)"
      + " OR (:endTime > start_time AND :endTime <= end_time)"
      + " OR (:startTime <= start_time AND :endTime >= end_time))";

  /**
   * 查找要删除的时间段是否为已存在时间段
   * 
   * @author komi.zsy
   */
  private final static String SELECT_TEACHER_TIME_SIGN_BY_DELETE_TIME = "SELECT key_id,teacher_id,teacher_name,"
      + " start_time,end_time,teacher_course_type"
      + " FROM t_teacher_time_sign"
      + " WHERE is_used <> 0 AND teacher_id = :teacherId"
      + " AND start_time <= :startTime  AND  end_time >= :endTime ";

  /**
   * 查找删除时间段内所有排课信息
   * 
   * @author komi.zsy
   */
  private final static String SELECT_TEACHER_TIME_BY_TEACHER_TIME_SIGN = "SELECT key_id,start_time,end_time,is_subscribe,course_type"
      + " FROM t_teacher_time "
      + " WHERE teacher_id = :teacherId AND is_used <> 0 "
      + " AND ((:startTime >= start_time AND :startTime < end_time) "
      + " OR (:endTime > start_time AND :endTime <= end_time) "
      + " OR (:startTime <= start_time AND :endTime >= end_time))";

  /**
   * 查询签课时间包含参数时间的老师
   * 
   * @author seven.gz
   */
  private final static String FIND_TEACHER_SIGN_BY_TIME = " SELECT tts.teacher_id,tts.teacher_name,tt.account,tt.teacher_course_type "
      + " FROM t_teacher_time_sign tts "
      + " LEFT JOIN t_teacher tt "
      + " ON tts.teacher_id = tt.key_id "
      + " WHERE tts.is_used = 1 "
      + " AND :startTime >= tts.start_time "
      + " AND :endTime <= tts.end_time "
      // modify by seven 2016年12月29日10:01:09
      // core1v1，ext1v1，demo1v1的类型单独出来，都可以在1v1排课的下拉框中过滤出来。
      // + " AND tts.teacher_course_type LIKE :teacherCourseType "
      + " ORDER BY tt.account ASC";

  /**
   * Title: 根据教师id查询当日以及之后的教师签课信息<br>
   * Description: 根据教师id查询当日以及之后的教师签课信息<br>
   * CreateDate: 2016年4月27日 上午11:02:18<br>
   * 
   * @category 根据教师id查询当日以及之后的教师签课信息
   * @author komi.zsy
   * @param paramObj
   * @param sort
   * @param order
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  public Page findPage(TeacherTimeSign paramObj, String sort, String order, Integer page,
      Integer rows) throws Exception {
    return super.findPageEasyui(SELECT_TEACHER_TIME_SIGN_INFO_BY_TEACHER_ID_AFTER_TODAY, paramObj,
        sort, order, page, rows);
  }

  /**
   * Title: 更新教师相关信息<br>
   * Description: 更新教师相关信息<br>
   * CreateDate: 2016年4月27日 下午1:45:45<br>
   * 
   * @category 更新教师相关信息
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateTeacherInfoByTeacherId(TeacherTimeSign paramObj) throws Exception {
    return super.update(UPDATE_TEACHER_INFO_BY_TEACHERID, paramObj);
  }

  /**
   * Title: 查询教师签课时间是否已存在<br>
   * Description: 查询教师签课时间是否已存在<br>
   * CreateDate: 2016年4月27日 下午4:46:30<br>
   * 
   * @category 查询教师签课时间是否已存在
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int findTeacherTimeSignByAddTime(TeacherTimeSign paramObj) throws Exception {
    return super.findCount(SELECT_TEACHER_TIME_SIGN_IS_EXIST_BY_ADD_TIME, paramObj);
  }

  /**
   * Title: 查找要删除时间段是否在已签课时间段内<br>
   * Description: 查找要删除时间段是否在已签课时间段内<br>
   * CreateDate: 2016年4月28日 上午10:12:00<br>
   * 
   * @category 查找要删除时间段是否在已签课时间段内
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public TeacherTimeSign findTeacherTimeSignByDeleteTime(TeacherTimeSign paramObj)
      throws Exception {
    return super.findOne(SELECT_TEACHER_TIME_SIGN_BY_DELETE_TIME, paramObj);
  }

  /**
   * Title: 根据删除时间段查找所有排课信息<br>
   * Description: 根据签课时间段查找所有排课信息<br>
   * CreateDate: 2016年4月28日 上午10:51:00<br>
   * 
   * @category 根据签课时间段查找所有排课信息
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<TeacherTime> findTeacherTimeByTeacherTimeSign(TeacherTime paramObj) throws Exception {
    return super.findList(SELECT_TEACHER_TIME_BY_TEACHER_TIME_SIGN, paramObj);
  }

  /**
   * 
   * Title: 查询签课时间包含参数时间的老师<br>
   * Description: 查询签课时间包含参数时间的老师<br>
   * CreateDate: 2016年4月28日 上午11:03:21<br>
   * 
   * @category 查询签课时间包含参数时间的老师
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public List<TeacherTimeSignParam> findTeacherSignByTime(TeacherTimeSignParam paramObj)
      throws Exception {
    return super.findList(FIND_TEACHER_SIGN_BY_TIME, paramObj);
  }
}