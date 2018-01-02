package com.webi.hwj.teacher.dao;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class TeacherDao extends BaseMysqlDao {
  /**
   * 某种类型课程的排课的日期数据
   */
  public final static String PAIKE_DAYS = "SELECT DISTINCT DATE(start_time) dt "
      + "FROM t_teacher_time "
      + "WHERE teacher_id = :teacher_id "
      + "AND is_used =1 AND " + "start_time > :curDate";

  /**
   * 小包课预约状态数据(红标+绿标)
   */
  public final static String CONFIRM_DAYS_SMALLPACK_RED_GREEN =
      " SELECT DATE(tt.start_time) dt, COUNT(DISTINCT tt.key_id) ct, tt.is_confirm "
          + " FROM t_teacher_time tt "
          + " RIGHT JOIN t_subscribe_course tsc "
          + " ON tsc.teacher_time_id = tt.key_id AND tsc.is_used = 1 "
          + " WHERE tt.teacher_id = :teacher_id "
          + " AND tt.start_time >= :curDay "
          + " AND tt.is_subscribe = 1 "
          + " AND tt.is_used = 1 "
          // modify by seven 2017年4月12日11:35:20 教师端不显示纠音1v1
          + " AND tsc.course_type != 'course_type13' "
          + " GROUP BY DATE(tt.start_time) ,tt.is_confirm ";

  /**
   * 小包课确认预约的信息
   * 
   * modify by athrun.cw 2015年10月30日17:45:10 无用代码，已经删除不用
   */
  public final static String SMALLPACK_CONFIRMS =
      "SELECT tt.key_id AS time_id,scs.key_id AS lesson_id,tt.start_time,"
          + "tt.end_time,tt.is_confirm, tu.phone,tu.user_photo,cs.course_title,cs.course_pic,cs.course_courseware "
          + "FROM t_teacher_time tt "
          + "LEFT JOIN t_subscribe_course scs ON scs.teacher_time_id=tt.key_id "
          + "LEFT JOIN t_user tu ON tu.key_id=scs.user_id	"
          + "LEFT JOIN t_course_smallpack cs ON cs.key_id=scs.course_id "
          + "WHERE tt.teacher_id = :teacher_id AND tt.is_used=1 AND scs.is_used=1 AND cs.is_used=1 AND tt.is_confirm = :is_confirm AND DATE(tt.start_time)= :dt AND scs.user_id IS NOT NULL "
          + "ORDER BY tt.start_time";

  public TeacherDao() {
    super.setTableName("t_teacher");
  }

}