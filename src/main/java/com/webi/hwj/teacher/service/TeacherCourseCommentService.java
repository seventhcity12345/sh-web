package com.webi.hwj.teacher.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.course.entity.CourseComment;
import com.webi.hwj.course.param.CourseCommentParam;
import com.webi.hwj.course.util.CourseUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.subscribecourse.constant.AdminSubscribeCourseConstant;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.teacher.dao.TeacherCourseCommentDao;
import com.webi.hwj.teacher.dao.TeacherCourseCommentEntityDao;
import com.webi.hwj.teacher.param.FindTeacherCommentParam;
import com.webi.hwj.util.PageUtil;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class TeacherCourseCommentService {
  private static Logger logger = Logger.getLogger(TeacherCourseCommentService.class);
  @Resource
  TeacherCourseCommentDao teacherCourseCommentDao;
  @Resource
  private TeacherCourseCommentEntityDao teacherCourseCommentEntityDao;
  @Resource
  private SubscribeCourseEntityDao subscribeCourseEntityDao;
  
  /**
   * Title: 保存评论信息.<br>
   * Description: 有keyId就是修改，否则为新增<br>
   * CreateDate: 2017年6月7日 下午7:43:02<br>
   * @category 保存评论信息 
   * @author komi.zsy
   * @param courseCommentParam
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public int saveComment(CourseCommentParam courseCommentParam) throws Exception {
    int flag = 0;
    //是否需要更新评论，因为demo课如果选了未出席就不需要更新评论了
    boolean isNeedUpdateComment = true;
    if(courseCommentParam.getIsDemo()!=null && courseCommentParam.getIsDemo()){
      //demo课，还要更新一下学员是否出席
      SubscribeCourse subscribeCourse = new SubscribeCourse();
      subscribeCourse.setKeyId(courseCommentParam.getSubscribeCourseId());
      subscribeCourse.setSubscribeStatus(courseCommentParam.getStudentIsShow());
      subscribeCourseEntityDao.update(subscribeCourse);
      if(AdminSubscribeCourseConstant.SUBSCRIBE_STATUS_NO_SHOW == courseCommentParam.getStudentIsShow()){
        //学生未出席，不需要更新评论
        isNeedUpdateComment = false;
      }
    }
   
    if(isNeedUpdateComment){
      //更新评论
      CourseComment courseComment = new CourseComment();
      BeanUtils.copyProperties(courseComment, courseCommentParam);
      if (StringUtils.isEmpty(courseComment.getKeyId())) {
        flag = teacherCourseCommentEntityDao.insert(courseComment);
      } else {
        flag = teacherCourseCommentEntityDao.update(courseComment);
      }
    }
    
    return flag;
  }
  
  /**
   * Title: 老师查看自己给学生的评论.<br>
   * Description: findTeacherComment<br>
   * CreateDate: 2017年2月14日 下午9:16:19<br>
   * 
   * @category 老师查看自己给学生的评论
   * @author yangmh
   * @param teacherId
   *          老师id
   * @param subscribeCourseId
   *          预约id
   */
  public CourseCommentParam findTeacherComment(String teacherId,
      String subscribeCourseId)
      throws Exception {
    return teacherCourseCommentEntityDao.findTeacherComment(teacherId, subscribeCourseId);
  }

  /**
   * Title: 老师中心的评价页面数据加载.<br>
   * Description: findTeacherCommentPage<br>
   * CreateDate: 2017年3月8日 下午6:26:30<br>
   * 
   * @category 老师中心的评价页面数据加载
   * @author yangmh
   * @param teacherId
   *          老师id
   * @param courseTypeCheckBox
   *          课程类型
   * @param page
   *          第几页
   * @param rows
   *          每页多少行
   */
  public Page findTeacherCommentPage(String teacherId, String courseTypeCheckBox,
      Integer page, Integer rows) throws Exception {
    if (rows == null) {
      // 默认显示5条
      rows = PageUtil.PAGE_LIMIT_ROWS;
    }

    CourseType courseType = (CourseType) MemcachedUtil.getValue(courseTypeCheckBox);
    Page courseCommentPage = null;
    if (courseType.getCourseTypeIsTeacherComment()) {
      // 允许老师评论
      courseCommentPage = teacherCourseCommentEntityDao.findTeacherCommentPageAllowTeacherComment(teacherId,
          courseTypeCheckBox, page, rows);
    } else {
      // 不允许老师评论
      courseCommentPage = teacherCourseCommentEntityDao.findTeacherCommentPage(teacherId,
          courseTypeCheckBox, page, rows);
    }

    if (courseCommentPage != null) {
      // 获得course&&comment
      List<FindTeacherCommentParam> comments = courseCommentPage.getDatas();
      for (FindTeacherCommentParam findTeacherCommentParam : comments) {

        // 老师是否允许评论
        findTeacherCommentParam.setIsTeacherComment(courseType.getCourseTypeIsTeacherComment());
        // 课程类型英文名
        findTeacherCommentParam.setCourseTypeEnglishName(courseType
            .getCourseTypeEnglishName());
      }
    }
    return courseCommentPage;
  }
}