package com.webi.hwj.course.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * Title: 1v1课程Dao<br>
 * Description: CourseOne2OneDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月23日 下午1:43:57
 * 
 * @author Woody
 */
@Repository
public class CourseOne2OneDao extends BaseMysqlDao {
  /**
   * 微信端加载排课数据
   */
  // modified by alex+komi+seven 2016年8月3日 14:19:47 增加webex房间查询
  private static final String FIND_WEIXIN_TIMES_AND_TEACHERS_BY_STARTTIME_AND_ENDTIME =
      "SELECT ttt.key_id teacher_time_id, ttt.start_time, ttt.end_time, "
          + " tt.key_id teacher_id, ttt.course_type, tt.teacher_name, tt.teacher_photo, tt.teacher_nationality "
          + " FROM t_teacher_time ttt "
          + " LEFT JOIN t_teacher tt "
          + " ON ttt.teacher_id = tt.key_id "
          + " AND ttt.start_time >= :start_time "
          + " AND ttt.end_time <= :end_time "
          + " WHERE ttt.is_used <> 0 "
          + " AND tt.is_used <> 0 "
          + " AND ttt.is_subscribe = 0 "
          + " AND ttt.course_type = 'course_type1' "
          // modify by seven 2016年12月27日15:31:16 过滤老师时间需要用老师的权限
          + " AND tt.teacher_course_type LIKE '%course_type1%' "
          + " AND ttt.webex_meeting_key <> '' "
          + " ORDER BY ttt.start_time ASC ";

  public CourseOne2OneDao() {
    super.setTableName("t_course_one2one");
  }

  /**
   * 
   * Title: 加载排课信息列表接口<br>
   * Description: findWeixinTimesAndTeachersByDay<br>
   * CreateDate: 2016年5月4日 下午4:11:37<br>
   * 
   * @category 加载排课信息列表接口
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findWeixinTimesAndTeachersByStartTimeAndEndTime(
      Map<String, Object> paramMap) throws Exception {
    return super.findList(CourseOne2OneDao.FIND_WEIXIN_TIMES_AND_TEACHERS_BY_STARTTIME_AND_ENDTIME,
        paramMap);
  }
}