package com.webi.hwj.course.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * 
 * Title: 学生登陆后，看课程回顾，可以看到老师的评价<br>
 * Description: CourseCommentDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月1日 上午10:29:26
 * 
 * @author athrun.cw
 */
@Repository
public class CourseCommentDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(CourseCommentDao.class);

  private static final String FIND_USER_COMMENT_BY_COURSEID_AND_USERID_AND_TEACHERID =
      " SELECT tcc.key_id, tcc.subscribe_course_id, tcc.from_user_id, "
          + " tcc.to_user_id, tcc.pronouncation_score, tcc.vocabulary_score, tcc.grammer_score, tcc.listening_score,tcc.preparation_score,"
          + " tcc.delivery_score,tcc.interaction_score,tcc.show_score, "
          + " tcc.comment_content, tcc.update_date, tu.user_photo "
          + " FROM t_course_comment tcc "
          + " LEFT JOIN t_user tu "
          + " ON tcc. from_user_id = tu.key_id "
          + " WHERE tcc.is_used <>0 "
          + " AND tu.is_used <>0 "
          + " AND tcc.subscribe_course_id = :subscribe_course_id "
          + " AND tcc.from_user_id = :from_user_id "
          + " AND tcc.to_user_id = :to_user_id ";

  private static final String FIND_TEACHER_COMMENT_BY_COURSEID_AND_USERID_AND_TEACHERID =
      " SELECT tcc.key_id, tcc.subscribe_course_id, tcc.from_user_id, "
          + " tcc.to_user_id, tcc.pronouncation_score, tcc.vocabulary_score, tcc.grammer_score, tcc.listening_score, tcc.show_score, "
          + " tcc.comment_content, tcc.update_date, tt.teacher_photo "
          + " FROM t_course_comment tcc "
          + " LEFT JOIN t_teacher tt "
          + " ON tcc. from_user_id = tt.key_id "
          + " WHERE tcc.is_used <>0 "
          + " AND tt.is_used <>0 "
          + " AND tcc.subscribe_course_id = :subscribe_course_id "
          + " AND tcc.from_user_id = :from_user_id "
          + " AND tcc.to_user_id = :to_user_id ";

  public CourseCommentDao() {
    super.setTableName("t_course_comment");
  }

  /**
   * 
   * Title: 查找用户自己给当前课程（老师）的评论<br>
   * Description: findCommentByCourseIdAndUserId<br>
   * CreateDate: 2015年9月1日 下午4:17:12<br>
   * 
   * @category findCommentByCourseIdAndUserId
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findUserCommentByCourseIdAndUserIdAndTeacherId(
      Map<String, Object> paramMap) throws Exception {
    return super.findOne(
        FIND_USER_COMMENT_BY_COURSEID_AND_USERID_AND_TEACHERID,
        paramMap);
  }

  /**
   * 
   * Title: 查找用户自己给当前课程（老师）的评论<br>
   * Description: findCommentByCourseIdAndUserId<br>
   * CreateDate: 2015年9月1日 下午4:17:12<br>
   * 
   * @category findCommentByCourseIdAndUserId
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