package com.webi.hwj.teacher.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.course.entity.CourseComment;
import com.webi.hwj.course.param.CourseCommentParam;
import com.webi.hwj.teacher.param.FindTeacherCommentParam;

@Repository
public class TeacherCourseCommentEntityDao extends BaseEntityDao<CourseComment> {
  private static Logger logger = Logger.getLogger(TeacherCourseCommentEntityDao.class);

  /**
   * 老师查询自己给学生的评论
   */
  private static final String FIND_TEACHER_COMMENT = " SELECT "
      + " tcc.key_id, tcc.subscribe_course_id, tcc.from_user_id, "
      + " tcc.to_user_id, tcc.pronouncation_score, tcc.vocabulary_score, "
      + " tcc.grammer_score, tcc.listening_score, tcc.show_score, "
      + " tcc.comment_content, tcc.update_date,tsc.subscribe_status AS student_is_show "
      + " FROM t_course_comment tcc "
      + "  LEFT JOIN t_subscribe_course tsc"
      + " ON tsc.key_id = tcc.subscribe_course_id"
      + " WHERE tcc.is_used <> 0 "
      + " AND tcc.subscribe_course_id = :subscribeCourseId "
      + " AND tcc.from_user_id = :fromUserId "
      + " AND tsc.is_used = 1";

  private static final String FIND_TEACHER_COMMENT_LIST = " SELECT "
      + " tsc.user_id,tsc.teacher_time_id, tsc.course_type, "
      + " tsc.course_title,tsc.course_pic,tsc.teacher_id, "
      + " tsc.start_time, tsc.end_time,  tcc.key_id comment_id,"
      + " tsc.key_id as subscribe_course_id, "
      + " tcc.preparation_score,tcc.delivery_score,"
      + " tcc.interaction_score,tcc.show_score, tcc.comment_content, tcc.update_date, "
      + " tsc.user_name, tu.user_photo "
      + " FROM t_subscribe_course tsc "
      + " LEFT JOIN t_user tu "
      + " ON tu.key_id = tsc.user_id "
      + " LEFT JOIN t_course_comment tcc "
      + " ON tsc.key_id = tcc.subscribe_course_id "
      + " AND tcc.to_user_id =:teacherId AND tcc.is_used <> 0 "
      + " WHERE tsc.is_used <> 0  AND tsc.subscribe_status = 1  "
      + " AND tsc.teacher_id = :teacherId "
      + " AND (tsc.course_type = 'course_type1' "
      + " OR (tcc.to_user_id =:teacherId AND tcc.is_used <> 0 )) "
      + " AND tsc.course_type = :courseTypeCheckBox"
      + " ORDER BY tsc.start_time DESC ";

  private static final String FIND_TEACHER_COMMENT_LIST_ALLOW_TEACHER_COMMENT = " SELECT "
      + " tsc.user_id,tsc.teacher_time_id, tsc.course_type, "
      + " tsc.course_title,tsc.course_pic,tsc.teacher_id, "
      + " tsc.start_time, tsc.end_time,  tcc.key_id comment_id,"
      + " tsc.key_id as subscribe_course_id, "
      + " tcc.preparation_score,tcc.delivery_score,"
      + " tcc.interaction_score,tcc.show_score, tcc.comment_content, tcc.update_date, "
      + " tsc.user_name, tu.user_photo "
      + " FROM t_subscribe_course tsc "
      + " LEFT JOIN t_user tu "
      + " ON tu.key_id = tsc.user_id "
      + " LEFT JOIN t_course_comment tcc "
      + " ON tsc.key_id = tcc.subscribe_course_id "
      + " AND tcc.to_user_id =:teacherId AND tcc.is_used <> 0 "
      + " WHERE tsc.is_used <> 0  "
      + " AND tsc.teacher_id = :teacherId "
      + " AND tsc.end_time <= :curDate "
      + " AND tsc.course_type = :courseTypeCheckBox"
      + " ORDER BY tsc.start_time DESC ";

  /**
   * 
   * Title: 根据预约id查询评论信息<br>
   * Description: 根据预约id查询评论信息<br>
   * CreateDate: 2016年11月21日 下午6:42:50<br>
   * 
   * @category 根据预约id查询评论信息
   * @author seven.gz
   * @param subscribeCourseIds
   * @return
   * @throws Exception
   */
  public List<CourseComment> findListBySubscribeId(
      String subscribeCourseId) throws Exception {
    CourseComment paramObj = new CourseComment();
    paramObj.setSubscribeCourseId(subscribeCourseId);
    return super.findList(paramObj,
        "key_id,from_user_id,to_user_id,pronouncation_score,vocabulary_score,grammer_score,"
            + "listening_score,preparation_score,delivery_score,interaction_score,"
            + "show_score,comment_content");
  }

  /**
   * 
   * Title: 根据预约id和评论人id查询评论信息<br>
   * Description: 根据预约id和评论人id查询评论信息<br>
   * CreateDate: 2016年11月21日 下午6:42:50<br>
   * 
   * @category 根据预约id和评论人id查询评论信息
   * @author seven.gz
   * @param subscribeCourseIds
   * @return
   * @throws Exception
   */
  public CourseComment findOneBySubscribeIdAndUserId(
      String subscribeCourseId, String userId) throws Exception {
    CourseComment paramObj = new CourseComment();
    paramObj.setSubscribeCourseId(subscribeCourseId);
    paramObj.setFromUserId(userId);
    return super.findOne(paramObj,
        "key_id,from_user_id,to_user_id,pronouncation_score,vocabulary_score,grammer_score,"
            + "listening_score,preparation_score,delivery_score,interaction_score,"
            + "show_score,comment_content");
  }

  /**
   * Title: 查询老师评论信息.<br>
   * Description: findTeacherComment<br>
   * CreateDate: 2017年2月14日 下午9:10:37<br>
   * 
   * @category 查询老师评论信息
   * @author yangmh
   * @param teacherId
   *          老师id
   * @param subscribeCourseId
   *          预约id
   */
  public CourseCommentParam findTeacherComment(String teacherId, String subscribeCourseId)
      throws Exception {
    CourseCommentParam param = new CourseCommentParam();
    param.setFromUserId(teacherId);
    param.setSubscribeCourseId(subscribeCourseId);

    return super.findOne(FIND_TEACHER_COMMENT, param);
  }

  /**
   * Title: 教师端-评论中心-评论分页查询.<br>
   * Description: 查询学生给老师的评论信息列表，必须是上过课的
   * 关联评论表&&用户表，默认只查老师对我的评论，查to_user_id为当前老师的id.
   * core课程的评论都查(包括老师和学生都没有互评的数据)，大课的评论必须是学生已经给老师评过的才查出来。 <br>
   * 大课情况下，假如有6个人上课，但只有2个人给老师评论了，那么这里2条数据查询出来 CreateDate: 2017年2月15日
   * 下午9:49:02<br>
   * 
   * @category 教师端-评论中心-评论分页查询
   * @author yangmh
   * @param teacherId
   *          老师id
   * @param courseTypeCheckBox
   *          课程类型
   * @param page
   *          第几页
   * @param rows
   *          每页显示多少条
   */
  public Page findTeacherCommentPage(String teacherId, String courseTypeCheckBox,
      Integer page, Integer rows)
      throws Exception {
    FindTeacherCommentParam param = new FindTeacherCommentParam();
    param.setTeacherId(teacherId);
    param.setCourseTypeCheckBox(courseTypeCheckBox);
    return super.findPage(FIND_TEACHER_COMMENT_LIST, param, page, rows);
  }

  /**
   * Title: 教师端-评论中心-评论分页查询（允许老师评论）.<br>
   * Description: 大课情况下，假如有6个人上课，但只有2个人给老师评论了，那么这里6条数据全部查询出来<br>
   * CreateDate: 2017年3月8日 下午6:21:00<br>
   * 
   * @category 教师端-评论中心-评论分页查询（允许老师评论）
   * @author yangmh
   * @param teacherId
   *          老师id
   * @param courseTypeCheckBox
   *          课程类型
   * @param page
   *          第几页
   * @param rows
   *          每页显示多少条
   */
  public Page findTeacherCommentPageAllowTeacherComment(String teacherId, String courseTypeCheckBox,
      Integer page, Integer rows)
      throws Exception {
    FindTeacherCommentParam param = new FindTeacherCommentParam();
    param.setTeacherId(teacherId);
    param.setCourseTypeCheckBox(courseTypeCheckBox);
    param.setCurDate(new Date());
    return super.findPage(FIND_TEACHER_COMMENT_LIST_ALLOW_TEACHER_COMMENT, param, page, rows);
  }
  
  
  

}