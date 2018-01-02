package com.webi.hwj.course.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.course.entity.CourseComment;
import com.webi.hwj.course.param.CourseCommentBySubscribeIdsParam;
import com.webi.hwj.course.param.FindTeacherCourseCommentParam;
import com.webi.hwj.course.param.FindToTeacherAverageScroreParam;
import com.webi.hwj.subscribecourse.param.FinishedCourseParam;

@Repository
public class CourseCommentEnityDao extends BaseEntityDao<CourseComment> {
  private static Logger logger = Logger.getLogger(CourseCommentEnityDao.class);

  /**
   * 查询对老师的评论信息
   */
  public static final String FIND_TEACHER_COURSE_COMMENT = ""
      + " SELECT tsc.course_title,tsc.start_time,tsc.teacher_name,tt.third_from,tu.user_name,tu.user_code, "
      + " tcc.preparation_score,tcc.delivery_score,tcc.interaction_score,tcc.show_score,tcc.comment_content "
      + " FROM t_course_comment tcc "
      + " LEFT JOIN t_teacher tt ON tcc.to_user_id = tt.key_id "
      + " LEFT JOIN t_subscribe_course tsc ON tsc.key_id = tcc.subscribe_course_id "
      + " left join t_user tu on tcc.from_user_id=tu.key_id "
      + " WHERE tcc.pronouncation_score = '' "
      + " AND tsc.start_time >= :startTime AND tsc.end_time <= :endTime AND tcc.is_used = 1 ";

  /**
   * 查询老师评论平均分
   */
  private static final String FIND_TO_TEACHER_AVERAGE_SCROREPARAM =
      " SELECT tt.teacher_name,tt.third_from,COUNT(to_user_id) AS comment_count,AVG(tcc.preparation_score) AS preparation_score, "
          + " AVG(tcc.delivery_score) AS delivery_score, "
          + " AVG(tcc.interaction_score) AS interaction_score,AVG(tcc.show_score) AS show_score "
          + " FROM t_teacher tt "
          + " LEFT JOIN "
          + " t_course_comment tcc "
          + " ON to_user_id = tt.key_id AND tcc.listening_score= '' AND tcc.is_used = 1 AND tcc.subscribe_course_id IN(:subscribeIds) "
          + " WHERE 1=1 AND tt.is_used = 1 GROUP BY tt.key_id HAVING 1=1 ";

  /**
   * 根据预约id查找评论信息
   * 
   * @author komi.zsy
   */
  private static final String FIND_COMMENT_BY_SUBSCRIBEID =
      "SELECT from_user_id,pronouncation_score,"
          + "vocabulary_score,grammer_score,listening_score,preparation_score,"
          + "delivery_score,interaction_score,show_score,comment_content"
          + " FROM `t_course_comment`"
          + " WHERE is_used = 1"
          + " AND subscribe_course_id = :subscribeCourseId";

  /**
   * 查询课程预约表里的上过课的数据（如果noshow的数据不查出来）， 关联评论表，默认只查老师对我的评论，查to_user_id为当前用户的id。
   * modified by alex 2016年12月26日 17:57:49 老师不出席也算未出席
   * 
   * @author athrun.cw
   */
  private static final String FIND_COURSES_AND_COMMENTS_BY_USERID =
      " SELECT tsc.key_id, tsc.teacher_time_id, "
          + " tsc.course_type, tsc.course_title,  tsc.course_pic, tsc.teacher_id, tsc.start_time, "
          + " tcc.pronouncation_score, tcc.vocabulary_score, tcc.grammer_score, tcc.listening_score, tcc.show_score, "
          + " tcc.comment_content, tcc.update_date, "
          + " tt.teacher_name, tt.teacher_photo,course_courseware "
          + " FROM t_subscribe_course tsc "
          + " LEFT JOIN t_teacher tt "
          + " ON tt.key_id = tsc.teacher_id "
          + " LEFT JOIN t_teacher_time ttt "
          + " ON ttt.key_id = tsc.teacher_time_id "
          + " LEFT JOIN t_course_comment tcc "
          + " ON tsc.key_id = tcc.subscribe_course_id AND tcc.to_user_id =:toUserId AND tcc.is_used <> 0 "
          // 修改bug 456 老师因离职等原因被删除后，已完成课程无法显示数据 ， 去掉tt.is_used = 1
          + " WHERE tsc.is_used <> 0  AND tsc.subscribe_status = 1 AND ttt.is_attend = 1 AND tsc.user_id = :toUserId"
          + " ORDER BY tsc.start_time DESC ";

  /**
   * 参数：传入预约id(list集合)<br>
   * 返回：根据预约id查询评论记录
   * 
   * @author felix.yl
   */
  private static final String FIND_COMMENT_LIST_BY_SUBSCRIBE_IDS = " SELECT"
      + " subscribe_course_id,"
      + " from_user_id,"
      + " to_user_id,"
      + " show_score "
      + " FROM t_course_comment "
      + " WHERE is_used = 1 "
      + " AND subscribe_course_id IN (:subscribeCourseIds) ";

  /**
   * 参数：传入预约id<br>
   * 返回：根据预约id查询评论记录
   * 
   * @author felix.yl
   */
  private static final String FIND_COMMENT_LIST_BY_SUBSCRIBE_ID = " SELECT"
      + " key_id,"
      + " from_user_id,"
      + " to_user_id,"
      + " pronouncation_score,"
      + " vocabulary_score,"
      + " grammer_score,"
      + " listening_score,"
      + " preparation_score,"
      + " delivery_score,"
      + " interaction_score,"
      + " show_score,"
      + " comment_content"
      + " FROM t_course_comment"
      + " WHERE is_used = 1"
      + " AND subscribe_course_id = :subscribeCourseId";

  /**
   * 参数：传入预约课程id、评论人id<br>
   * 返回：查询评论记录(findOne-因为一个评论人对一个预约id只能保存一次评论)
   * 
   * @author felix.yl
   */
  private static final String FIND_COMMENT_INFO_BY_SUBSCRIBE_COURSE_ID_AND_FROM_USER_ID = " SELECT"
      + " key_id,"
      + " from_user_id,"
      + " to_user_id,"
      + " pronouncation_score,"
      + " vocabulary_score,"
      + " grammer_score,"
      + " listening_score,"
      + " preparation_score,"
      + " delivery_score,"
      + " interaction_score,"
      + " show_score,"
      + " comment_content "
      + " FROM t_course_comment"
      + " WHERE subscribe_course_id=:subscribeCourseId"
      + " AND from_user_id=:fromUserId"
      + " AND is_used=1";

  /**
   * Title: 根据预约id查找评论信息<br>
   * Description: 根据预约id查找评论信息<br>
   * CreateDate: 2016年10月14日 下午4:17:17<br>
   * 
   * @category 根据预约id查找评论信息
   * @author komi.zsy
   * @param subscribeId
   * @return
   * @throws Exception
   */
  public List<CourseComment> findCommentBySubscribeid(String subscribeId) throws Exception {
    CourseComment paramObj = new CourseComment();
    paramObj.setSubscribeCourseId(subscribeId);
    return super.findList(FIND_COMMENT_BY_SUBSCRIBEID, paramObj);
  }

  /**
   * 
   * Title: 查询老师评论平均分<br>
   * Description: 查询老师评论平均分<br>
   * CreateDate: 2016年7月21日 下午5:58:54<br>
   * 
   * @category 查询老师评论平均分
   * @author seven.gz
   * @param param
   * @param subscribeIds
   * @return
   * @throws Exception
   */
  public Page findTeacherAverageScrore(Map<String, Object> param, List<String> subscribeIds)
      throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      // 同时用两个排序条件 只能用小写
      sort = " showScore ";
      order = " DESC ";
    }
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    FindToTeacherAverageScroreParam findToTeacherAverageScroreParam =
        new FindToTeacherAverageScroreParam();
    findToTeacherAverageScroreParam.setCons((String) param.get("cons"));
    findToTeacherAverageScroreParam.setSubscribeIds(subscribeIds);
    return findPageEasyui(FIND_TO_TEACHER_AVERAGE_SCROREPARAM, findToTeacherAverageScroreParam,
        sort, order, page, rows);
  }

  /**
   * 
   * Title: 查询对老师的评论<br>
   * Description: 查询对老师的评论<br>
   * CreateDate: 2016年7月23日 上午10:09:40<br>
   * 
   * @category 查询对老师的评论
   * @author seven.gz
   * @param param
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public Page findTeacherCourseCommentList(Map<String, Object> param, Date startTime, Date endTime)
      throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      // 同时用两个排序条件 只能用小写
      sort = " startTime ";
      order = " DESC ";
    }
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    FindTeacherCourseCommentParam findTeacherCourseCommentParam =
        new FindTeacherCourseCommentParam();
    findTeacherCourseCommentParam.setCons((String) param.get("cons"));
    findTeacherCourseCommentParam.setStartTime(startTime);
    findTeacherCourseCommentParam.setEndTime(endTime);

    return findPageEasyui(FIND_TEACHER_COURSE_COMMENT, findTeacherCourseCommentParam, sort, order,
        page, rows);
  }

  /**
   * Title:查询已完成课程以及评论.<br>
   * Description: （如果noshow的数据不查出来）， 关联评论表，默认只查老师对我的评论，查to_user_id为当前用户的id<br>
   * CreateDate: 2015年9月1日 上午10:47:38<br>
   * 
   * @category 查询已完成课程以及评论
   * @author alex+seven
   * @param toUserId
   *          当前用户id
   * @param page
   *          当前页数
   * @param rows
   *          查询多少行
   */
  public Page findCoursesAndCommentsByUserId(String toUserId, Integer page, Integer rows)
      throws Exception {
    FinishedCourseParam finishedCourseParam = new FinishedCourseParam();
    finishedCourseParam.setToUserId(toUserId);
    return super.findPage(FIND_COURSES_AND_COMMENTS_BY_USERID, finishedCourseParam, page, rows);
  }

  /**
   * 
   * Title: 根据预约id查询评论信息<br>
   * Description: 根据预约id查询评论信息<br>
   * CreateDate: 2017年7月24日 上午10:34:34<br>
   * 
   * @category 根据预约id查询评论信息
   * @author felix.yl
   * @param subscribeCourseIds
   * @return
   * @throws Exception
   */
  public List<CourseCommentBySubscribeIdsParam> findListBySubscribeIds(
      List<String> subscribeCourseIds) throws Exception {
    CourseCommentBySubscribeIdsParam paramObj = new CourseCommentBySubscribeIdsParam();
    paramObj.setSubscribeCourseIds(subscribeCourseIds);
    return super.findList(FIND_COMMENT_LIST_BY_SUBSCRIBE_IDS, paramObj);
  }

  /**
   * 
   * Title: 根据预约id查询评论信息<br>
   * Description: 根据预约id查询评论信息<br>
   * CreateDate: 2017年7月24日 上午10:34:34<br>
   * 
   * @category 根据预约id查询评论信息
   * @author felix.yl
   * @param subscribeCourseId
   * @return
   * @throws Exception
   */
  public List<CourseComment> findListBySubscribeId(
      String subscribeCourseId) throws Exception {
    CourseComment paramObj = new CourseComment();
    paramObj.setSubscribeCourseId(subscribeCourseId);
    return super.findList(FIND_COMMENT_LIST_BY_SUBSCRIBE_ID, paramObj);
  }

  /**
   * 
   * Title: 根据课程预约id和评论人id查询评论信息<br>
   * Description: 根据 课程预约id和评论人id查询评论信息<br>
   * CreateDate: 2017年7月25日 上午10:11:03<br>
   * 
   * @category 根据课程预约id和评论人id查询评论信息
   * @author felix.yl
   * @param subscribeCourseId
   * @param userId
   * @return
   * @throws Exception
   */
  public CourseComment findOneBySubscribeIdAndUserId(String subscribeCourseId, String fromUserId)
      throws Exception {
    CourseComment paramObj = new CourseComment();
    paramObj.setSubscribeCourseId(subscribeCourseId);
    paramObj.setFromUserId(fromUserId);
    return super.findOne(FIND_COMMENT_INFO_BY_SUBSCRIBE_COURSE_ID_AND_FROM_USER_ID, paramObj);
  }

}