package com.webi.hwj.teacher.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * Title: 学生登陆后，看课程回顾，可以看到老师的评价.<br>
 * Description: CourseCommentDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月1日 上午10:29:26
 * 
 * @author athrun.cw
 */
@Repository
public class TeacherCourseCommentDao extends BaseMysqlDao {
  private static Logger logger = Logger
      .getLogger(TeacherCourseCommentDao.class);

  /**
   * 查询课程预约表里的上过课的数据（如果noshow的数据不查出来），
   * 关联评论表&&用户表，默认只查老师对我的评论，查to_user_id为当前用户的id。
   * 
   * @author athrun.cw
   * 
   *         modified by seven 2016年6月27日13:06:22 增加学生对老师的几个维度的评分; modified by
   *         alex 2016年12月26日 16:09:53 老师不出席也查询不出来。
   * 
   */
  private static final String FIND_COURSES_AND_COMMENTS_BY_TEACHERID = " SELECT tsc.key_id, tsc.user_id, "
      + " tsc.teacher_time_id, tsc.course_type, tsc.course_title,  tsc.course_pic, tsc.teacher_id, tsc.subscribe_status, "
      + " tsc.start_time, tsc.end_time,  tcc.key_id comment_id, tcc.subscribe_course_id, tcc.from_user_id, "
      + " tcc.to_user_id, tcc.pronouncation_score,  tcc.vocabulary_score, tcc.grammer_score, "
      + " tcc.listening_score,tcc.preparation_score,tcc.delivery_score,tcc.interaction_score,tcc.show_score, "
      + " tcc.comment_content, tcc.update_date, "
      + " tcc.is_used comment_is_used, tu.user_name, tu.user_photo "
      + " FROM t_subscribe_course tsc "
      + " LEFT JOIN t_user tu "
      + " ON tu.key_id = tsc.user_id "
      + " LEFT JOIN t_course_comment tcc "
      + " ON tsc.key_id = tcc.subscribe_course_id AND tcc.to_user_id =:teacher_id AND tcc.is_used <> 0 "
      + " LEFT JOIN t_teacher_time ttt "
      + " ON ttt.key_id = tsc.teacher_time_id "
      + " WHERE tsc.is_used <> 0 "
      + " AND tu.is_used <> 0  "
      + " AND (tsc.subscribe_status = 1 AND ttt.is_attend = 1 )  "
      + " AND tsc.teacher_id = :teacher_id "
      + " AND (tsc.course_type IN ('course_type1','course_type9') OR (tcc.to_user_id =:teacher_id AND tcc.is_used <> 0 )) "
      + " AND tsc.course_type IN (:courseTypeCheckBoxList)"
      + " ORDER BY tsc.start_time DESC ";

  /**
   * 老师查询自己给学生的评论
   * 
   * @author athrun.cw
   */
  private static final String FIND_TEACHER_COMMENT_BY_COURSEID_AND_USERID_AND_TEACHERID = " SELECT tcc.key_id, tcc.subscribe_course_id, tcc.from_user_id, "
      + " tcc.to_user_id, tcc.pronouncation_score, tcc.vocabulary_score, tcc.grammer_score, tcc.listening_score, tcc.show_score, "
      + " tcc.comment_content, tcc.update_date "
      + " FROM t_course_comment tcc "
      + " WHERE tcc.is_used <>0 "
      + " AND tcc.subscribe_course_id = :subscribe_course_id "
      + " AND tcc.from_user_id = :from_user_id "
      + " AND tcc.to_user_id = :to_user_id ";

  /**
   * Title: 老师中心的评价页面数据加载<br>
   * Description: findCommentByTeacherId<br>
   * CreateDate: 2015年9月24日 上午10:45:17<br>
   * 
   * @category 老师中心的评价页面数据加载
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findCommentByTeacherId(Map<String, Object> paramMap)
      throws Exception {
    return super.findPage(FIND_COURSES_AND_COMMENTS_BY_TEACHERID, paramMap);
  }

  /**
   * 
   * Title: 老师查询自己给学生的评论<br>
   * Description: findTeacherCommentByCourseIdAndUserIdAndTeacherId<br>
   * CreateDate: 2015年9月24日 下午2:16:29<br>
   * 
   * @category 老师查询自己给学生的评论
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findTeacherCommentByCourseIdAndUserIdAndTeacherId(
      Map<String, Object> paramMap) throws Exception {
    return super.findOne(
        FIND_TEACHER_COMMENT_BY_COURSEID_AND_USERID_AND_TEACHERID,
        paramMap);
  }
}