package com.webi.hwj.course.service;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.dao.CourseCommentDemoDao;
import com.webi.hwj.course.entity.CourseCommentDemo;
import com.webi.hwj.course.param.CourseCommentDemoParam;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;

/**
 * @category courseCommentDemo控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
public class CourseCommentDemoService {
  private static Logger logger = Logger.getLogger(CourseCommentDemoService.class);
  @Resource
  CourseCommentDemoDao courseCommentDemoDao;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;

  /**
   * 
   * Title: demo保存评论<br>
   * Description: saveTeacherCommentDemo<br>
   * CreateDate: 2017年6月27日 下午4:13:56<br>
   * 
   * @category demo保存评论
   * @author seven.gz
   * @param courseCommentDemoParam
   * 
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public CommonJsonObject saveCommentDemo(CourseCommentDemoParam courseCommentDemoParam)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 查询预约信息
    SubscribeCourse subscribeCourse = subscribeCourseEntityDao.findOneByKeyIdOnSubscribe(
        courseCommentDemoParam
            .getSubscribeCourseId());

    // 预约信息不存在
    if (subscribeCourse == null) {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_COURSE_NOT_EXIST.getCode());
      return json;
    }

    // demo课，还要更新一下学员是否出席
    subscribeCourse.setKeyId(courseCommentDemoParam.getSubscribeCourseId());
    subscribeCourse.setSubscribeStatus(courseCommentDemoParam.getSubscribeStatus());
    subscribeCourseEntityDao.update(subscribeCourse);

    // 查询是否评论过
    CourseCommentDemo courseCommentDemo = courseCommentDemoDao.findOneBySubscribeIdAndFromUserId(
        courseCommentDemoParam
            .getSubscribeCourseId(), subscribeCourse.getTeacherId());

    // 之前没有评论过,新增
    if (courseCommentDemo == null) {
      courseCommentDemo = new CourseCommentDemo();
      PropertyUtils.copyProperties(courseCommentDemo, courseCommentDemoParam);
      // 这里固定携程了老师评论学员，以后有需求再加判断
      courseCommentDemo.setToUserId(subscribeCourse.getUserId());
      courseCommentDemo.setFromUserId(subscribeCourse.getTeacherId());
      courseCommentDemo.setSubscribeStatus(courseCommentDemoParam.getSubscribeStatus());
      courseCommentDemoDao.insert(courseCommentDemo);
      // 之前评论过,更新
    } else {
      // 下面的属性复制会把keyid设置为null
      courseCommentDemoParam.setKeyId(courseCommentDemo.getKeyId());
      PropertyUtils.copyProperties(courseCommentDemo, courseCommentDemoParam);
      courseCommentDemoDao.update(courseCommentDemo);
    }

    return json;
  }

  /**
   * Title: 查询demo评论<br>
   * Description: 查询demo评论<br>
   * CreateDate: 2017年6月27日 下午6:26:52<br>
   * 
   * @category 查询demo评论
   * @author seven.gz
   * @param subscribeCourseId
   *          预约id
   */
  public CourseCommentDemoParam findCommentDemo(String subscribeCourseId)
      throws Exception {
    CourseCommentDemoParam courseCommentDemoParam = new CourseCommentDemoParam();
    // 查询预约信息
    SubscribeCourse subscribeCourse = subscribeCourseEntityDao.findOneByKeyIdOnSubscribe(
        subscribeCourseId);

    // 预约信息不存在
    if (subscribeCourse == null) {
      return null;
    }

    courseCommentDemoParam.setTeacherName(subscribeCourse.getTeacherName());
    courseCommentDemoParam.setUserName(subscribeCourse.getUserName());

    // 查询评论
    CourseCommentDemo courseCommentDemo = courseCommentDemoDao.findOneBySubscribeIdAndFromUserId(
        subscribeCourseId, subscribeCourse.getTeacherId());

    if (courseCommentDemo == null) {
      return courseCommentDemoParam;
    }

    PropertyUtils.copyProperties(courseCommentDemoParam, courseCommentDemo);

    return courseCommentDemoParam;
  }
}