package com.webi.hwj.course.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.course.constant.CourseCommentConstant;
import com.webi.hwj.course.dao.CourseCommentDao;
import com.webi.hwj.course.dao.CourseCommentEnityDao;
import com.webi.hwj.course.entity.CourseComment;
import com.webi.hwj.course.param.CourseCommentBySubscribeIdsParam;
import com.webi.hwj.course.param.CourseCommentDetailInfoParam;
import com.webi.hwj.course.param.StudentCommentToTeacherParam;
import com.webi.hwj.course.util.CommentUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.subscribecourse.constant.SubscribeCourseConstant;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.CourseSubscribeAndTeacherInfoParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentCountParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentListParam;
import com.webi.hwj.subscribecourse.param.FinishedCourseParam;
import com.webi.hwj.subscribecourse.param.PageRowNumCourseTypeInfoParam;
import com.webi.hwj.util.PageUtil;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class CourseCommentService {
  private static Logger logger = Logger.getLogger(CourseCommentService.class);
  @Resource
  CourseCommentDao courseCommentDao;
  @Resource
  CourseCommentEnityDao courseCommentEnityDao;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;

  /**
   * Title: 加入评论<br>
   * Description: insertComment<br>
   * CreateDate: 2015年9月2日 下午2:41:02<br>
   * 
   * @category insertComment
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> insertComment(Map<String, Object> paramMap) throws Exception {
    // 查询之前是否有过评论
    Map<String, Object> findParamMap = new HashMap<String, Object>();
    findParamMap.put("subscribe_course_id", paramMap.get("subscribe_course_id"));
    findParamMap.put("from_user_id", paramMap.get("from_user_id"));
    findParamMap.put("to_user_id", paramMap.get("to_user_id"));
    Map<String, Object> oldComment = courseCommentDao.findOne(findParamMap,
        "key_id,subscribe_course_id,from_user_id,to_user_id,pronouncation_score,vocabulary_score"
            + ",grammer_score,listening_score,show_score,comment_content");
    // 之前评论过就更新没有则新增
    if (oldComment == null) {
      // 先评论
      return courseCommentDao.insert(CommentUtil.showScore2DoubleType(paramMap));
    } else {
      oldComment.put("pronouncation_score", paramMap.get("pronouncation_score"));
      oldComment.put("vocabulary_score", paramMap.get("vocabulary_score"));
      oldComment.put("grammer_score", paramMap.get("grammer_score"));
      oldComment.put("listening_score", paramMap.get("listening_score"));
      oldComment.put("show_score", paramMap.get("show_score"));
      oldComment.put("comment_content", paramMap.get("comment_content"));
      courseCommentDao.update(oldComment);
      return oldComment;
    }

  }

  /**
   * 
   * Title: 查找用户自己给当前课程（老师）的评论<br>
   * Description: findCommentByCourseIdAndUserId<br>
   * CreateDate: 2015年9月1日 下午4:18:39<br>
   * 
   * @category findCommentByCourseIdAndUserId
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE) 仅对一致性不高的查询， 无须事物
  public Map<String, Object> findUserCommentByCourseIdAndUserIdAndTeacherId(
      Map<String, Object> paramMap)
          throws Exception {
    return CommentUtil
        .formatDate4Comment(
            courseCommentDao.findUserCommentByCourseIdAndUserIdAndTeacherId(paramMap));
  }

  /**
   * Title:查询课程预约表里的上过课的数据<br>
   * Description: （如果noshow的数据不查出来）， 关联评论表，默认只查老师对我的评论，查to_user_id为当前用户的id<br>
   * CreateDate: 2015年9月1日 上午10:48:04<br>
   * 
   * @category 查询课程预约表里的上过课的数据
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findCoursesAndCommentsByUserId(String toUserId, Integer page, Integer rows)
      throws Exception {
    if (rows == null) {
      rows = PageUtil.PAGE_LIMIT_ROWS;
    }
    Page courseCommentPage = courseCommentEnityDao.findCoursesAndCommentsByUserId(toUserId, page,
        rows);
    // 遍历放入课程类型中文名
    List<FinishedCourseParam> finishedCourseList = courseCommentPage.getDatas();
    if (finishedCourseList != null && finishedCourseList.size() > 0) {
      for (FinishedCourseParam finishedCourseParam : finishedCourseList) {
        finishedCourseParam.setCourseTypeChineseName(
            ((CourseType) MemcachedUtil.getValue(finishedCourseParam.getCourseType()))
                .getCourseTypeChineseName());
      }
    }
    return courseCommentPage;
  }

  /**
   * 
   * Title: 查询已完成课程总数<br>
   * Description: 查询已完成课程总数<br>
   * CreateDate: 2017年7月21日 下午4:23:34<br>
   * 
   * @category 查询已完成课程总数
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  public List<CourseTypeCommentCountParam> findCourseCountCompleted(SessionUser sessionUser)
      throws Exception {

    // 获取学员的userId
    String keyId = sessionUser.getKeyId();

    // 调用Dao层
    List<CourseTypeCommentCountParam> courseCountCompleted = subscribeCourseEntityDao
        .findCourseCountCompleted(keyId, new Date());

    // 查询course_type中文名并赋值
    if (courseCountCompleted != null && courseCountCompleted.size() > 0) {
      for (CourseTypeCommentCountParam courseTypeCommentCountParam : courseCountCompleted) {
        CourseType courseType = (CourseType) MemcachedUtil.getValue(courseTypeCommentCountParam
            .getCourseType());
        if (courseType != null) {
          courseTypeCommentCountParam.setCourseTypeChineseName(courseType
              .getCourseTypeChineseName());
        }
      }
    }
    return courseCountCompleted;
  }

  /**
   * 
   * Title: 返回指定课程类型的评价信息列表<br>
   * Description: 返回指定课程类型的评价信息列表;<br>
   * 传入参数：用户的userId、课程类型、页码(想要查询第几页)、行数(每页想要展示的行数)<br>
   * CreateDate: 2017年7月21日 下午6:28:06<br>
   * 
   * @category 返回指定课程类型的评价信息列表
   * @author felix.yl
   * @param userId
   * @param courseType
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  public List<CourseTypeCommentListParam> findCourseListCompleted(String userId, String courseType,
      int page, int rows) throws Exception {

    // 调用Dao层(分页查询课程预约表相关的信息)
    Page findPage = subscribeCourseEntityDao.findCourseListCompleted(userId, courseType, new Date(),
        page, rows);

    List<CourseTypeCommentListParam> courseTypeCommentList = null;// 构建list,存储课程类型的评价信息列表

    if (findPage != null) {
      // Page类中的datas对象就是一个list集合(courseTypeCommentList集合中存储的是CourseTypeCommentListParam对象)
      courseTypeCommentList = findPage.getDatas();

      if (courseTypeCommentList != null && courseTypeCommentList.size() > 0) {
        List<String> keyIdList = new ArrayList<String>();// 新建list
        Map<String, CourseTypeCommentListParam> resultMap =
            new HashMap<String, CourseTypeCommentListParam>();// 新建map

        // 将分页查询出的预约表信息循环遍历,初步更新每条记录的课程状态
        for (CourseTypeCommentListParam returnObj : courseTypeCommentList) {
          keyIdList.add(returnObj.getKeyId());// 将查询课程预表返回的主键id(即预约id)迭代存储在新建的list集合中
          resultMap.put(returnObj.getKeyId(), returnObj);// 新建map追加对象(其中key为预约Id,value为对应的CourseTypeCommentListParam对象)

          // 根据查询课程预约表返回的"预约状态(0:未上课,1:已上课)-课程预约表中查出来的",设置"课程状态(0:未出席,1:待评价,2:已出席已评价)-课程状态,自定义需要返回展示的";
          if (SubscribeCourseConstant.SUBSCRIBE_STATUS_NO_SHOW == returnObj.getSubscribeStatus()) {// 未上课
            returnObj.setStatus(CourseCommentConstant.COURSE_COMMENT_LIST_STATUS_NO_SHOW);// 课程状态：0-未出席
          } else {// 已上课
            returnObj.setStatus(CourseCommentConstant.COURSE_COMMENT_LIST_STATUS_SHOW);// 课程状态：1-待评价
          }
        }

        // 调用Dao层-传入评论id(list集合),查询返回评论表信息
        List<CourseCommentBySubscribeIdsParam> commentList = courseCommentEnityDao
            .findListBySubscribeIds(keyIdList);

        if (commentList != null && commentList.size() > 0) {
          CourseTypeCommentListParam courseTypeCommentListParamTempObj = null;
          // 更新学员或老师的评论信息
          for (CourseCommentBySubscribeIdsParam courseCommentBySubscribeIdsParam : commentList) {
            courseTypeCommentListParamTempObj = resultMap.get(courseCommentBySubscribeIdsParam
                .getSubscribeCourseId());
            if (courseTypeCommentListParamTempObj != null) {// 课程预约id在评论表中有对应的记录(即该课程预约id已产生过评论)
              // 判断是老师评论还是学员评论
              if (courseTypeCommentListParamTempObj.getUserId().equals(
                  courseCommentBySubscribeIdsParam.getFromUserId())) {
                // 学员评论
                courseTypeCommentListParamTempObj.setStatus(
                    CourseCommentConstant.COURSE_COMMENT_LIST_STATUS_HAVE_COMMENT);// 课程状态更新为：2-已出席已评价
              } else {
                // 老师评论
                courseTypeCommentListParamTempObj.setShowScore(courseCommentBySubscribeIdsParam
                    .getShowScore());// 更新老师评论的分数
              }
            }
          }
        }

      }
    }
    return courseTypeCommentList;
  }

  /**
   * 
   * Title: 查询已完成课程评论的详细信息<br>
   * Description: 查询已完成课程评论的详细信息<br>
   * CreateDate: 2017年7月24日 下午5:18:29<br>
   * 
   * @category 查询已完成课程评论的详细信息
   * @author felix.yl
   * @param subscribeCourseId
   * @param userId
   * @return
   * @throws Exception
   */
  public CourseCommentDetailInfoParam findCourseCommentDetailCompleted(String subscribeCourseId,
      String userId)
          throws Exception {
    // 创建对象(因为是一个对象直接返回给前端)
    CourseCommentDetailInfoParam courseCommentDetailInfoParam = new CourseCommentDetailInfoParam();

    // 根据预约id查询老师、上课平台等相关信息
    CourseSubscribeAndTeacherInfoParam teacherInfo =
        subscribeCourseEntityDao.findSubscribeTeacherInfoBySubscribeId(subscribeCourseId);

    if (teacherInfo != null) {
      // 设置老师信息
      courseCommentDetailInfoParam.setTeacherTimeId(teacherInfo.getTeacherTimeId());
      courseCommentDetailInfoParam.setTeacherName(teacherInfo.getTeacherName());
      courseCommentDetailInfoParam.setTeacherPhoto(teacherInfo.getTeacherPhoto());

      // 设置PPT课件地址
      courseCommentDetailInfoParam.setCourseCourseware(teacherInfo.getCourseCourseware());

      // 设置课程类型上课平台
      courseCommentDetailInfoParam.setTeacherTimePlatform(teacherInfo.getTeacherTimePlatform());

      // 根据预约id查询评价表相关的信息
      List<CourseComment> courseCommentList = courseCommentEnityDao
          .findListBySubscribeId(subscribeCourseId);

      if (courseCommentList != null && courseCommentList.size() > 0) {
        for (CourseComment courseComment : courseCommentList) {
          // 判断是学员发的评论还是教师发的评论
          if (userId.equals(courseComment.getFromUserId())) {
            // 学员给老师的评论
            courseCommentDetailInfoParam.setStudentCommentContent(courseComment
                .getCommentContent());// 学生给老师的评语
            courseCommentDetailInfoParam.setStudentShowScore(courseComment.getShowScore());// 学生给老师的打分(平均分)

            courseCommentDetailInfoParam.setDeliveryScore(courseComment.getDeliveryScore());// 专业度
            courseCommentDetailInfoParam.setInteractionScore(courseComment.getInteractionScore());// 互动性
            courseCommentDetailInfoParam.setPreparationScore(courseComment.getPreparationScore());// 准备度

          } else {
            // 老师给学员的评论
            courseCommentDetailInfoParam.setTeacherCommentContent(courseComment
                .getCommentContent());// 老师给学生的评语
            courseCommentDetailInfoParam.setTeacherShowScore(courseComment.getShowScore());// 老师给学生的打分(平均分)

            courseCommentDetailInfoParam.setPronouncationScore(courseComment
                .getPronouncationScore());// 发音
            courseCommentDetailInfoParam.setGrammerScore(courseComment.getGrammerScore());// 语法
            courseCommentDetailInfoParam.setVocabularyScore(courseComment.getVocabularyScore());// 词汇量
            courseCommentDetailInfoParam.setListeningScore(courseComment.getListeningScore());// 听力
          }
        }
      }
    }
    return courseCommentDetailInfoParam;
  }

  /**
   * 
   * Title: 已完成课程模块-学员新增或修改对老师的评论<br>
   * Description: 已完成课程模块-学员新增或修改对老师的评论<br>
   * CreateDate: 2017年7月25日 上午10:22:32<br>
   * 
   * @category 已完成课程模块-学员新增或修改对老师的评论
   * @author felix.yl
   * @param studentCommentToTeacherParam
   *          学员提交的评论信息封装为bean
   * @param userId
   *          评论人id(此处即表示学员id)
   * @throws Exception
   */
  public void updateCommentToTeacher(StudentCommentToTeacherParam studentCommentToTeacherParam,
      String userId)
          throws Exception {

    // 根据课程预约id和评论人id,查询评论表,看是否有评论信息
    CourseComment courseComment = courseCommentEnityDao.findOneBySubscribeIdAndUserId(
        studentCommentToTeacherParam.getSubscribeCourseId(), userId);

    if (courseComment == null) {
      // 如果之前没有评论过则新增
      courseComment = new CourseComment();// 新建CourseComment对象
      BeanUtils.copyProperties(studentCommentToTeacherParam, courseComment);// 将前端传进来的封装bean(封装bean里包含提交的评论信息)复制到我们新建的CourseComment对象
      courseComment.setFromUserId(userId);// 评论人id
      courseComment.setCreateUserId(userId);// 创建者id
      courseComment.setUpdateUserId(userId);// 修改者id
      courseCommentEnityDao.insert(courseComment);

    } else {
      // 如果之前有评论过的话则执行更新操作
      BeanUtils.copyProperties(studentCommentToTeacherParam, courseComment);// 将前端传进来的封装bean(封装bean里包含提交的评论信息)复制到我们新建的CourseComment对象
      courseCommentEnityDao.update(courseComment);
    }
  }

  /**
   * 
   * Title: 查询指定的课程在第几页的第几行<br>
   * Description: 查询指定的课程在第几页的第几行<br>
   * CreateDate: 2017年7月27日 上午10:45:14<br>
   * 
   * @category 查询指定的课程在第几页的第几行
   * @author felix.yl
   * @param rows
   * @param subscribeId
   * @param userId
   * @return
   * @throws Exception
   */
  public PageRowNumCourseTypeInfoParam findPageAndRowsBySubscribeId(int rows, String subscribeId,
      String userId) throws Exception {
    // 当前页数
    int currentPage = 1;
    // 当前页第几条
    int currentRowNum = 1;
    // 课程类型
    String courseType = "course_type1";

    SubscribeCourse subscribeCourse = null;
    // 如果预约id为空,最近一条未查看的数据
    if (StringUtils.isEmpty(subscribeId)) {
      subscribeCourse = subscribeCourseEntityDao.findOneStudentNoLookComment(userId);
    } else {
      subscribeCourse = subscribeCourseEntityDao.findSubscribeCourseByKeyId(subscribeId);
    }

    if (subscribeCourse != null) {
      courseType = subscribeCourse.getCourseType();
      int order = subscribeCourseEntityDao.findOrderNumber(subscribeCourse.getStartTime(), userId,
          courseType);
      if (rows > 0) {
        currentPage = order / rows + 1;
        currentRowNum = order % rows;
        if (currentRowNum == 0) {
          currentPage -= 1;
          currentRowNum = rows;
        }
      }
    }

    PageRowNumCourseTypeInfoParam pageRowNum = new PageRowNumCourseTypeInfoParam();// 构建对象
    pageRowNum.setPageNum(currentPage);
    pageRowNum.setRowNum(currentRowNum);
    pageRowNum.setCourseType(courseType);

    return pageRowNum;
  }

}