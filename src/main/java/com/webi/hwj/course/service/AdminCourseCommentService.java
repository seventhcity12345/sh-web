package com.webi.hwj.course.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.course.dao.CourseCommentEnityDao;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;

@Service
public class AdminCourseCommentService {
  private static Logger logger = Logger.getLogger(AdminCourseCommentService.class);
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  CourseCommentEnityDao courseCommentEnityDao;

  /**
   * 
   * Title: 查询老师评论平均分<br>
   * Description: 查询老师评论平均分<br>
   * CreateDate: 2016年7月21日 下午6:14:58<br>
   * 
   * @category 查询老师评论平均分
   * @author seven.gz
   * @param param
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public Page findTeacherAverageScrore(Map<String, Object> param, Date startTime, Date endTime)
      throws Exception {
    // 1. 根据课程开始结束时间查询预约表
    List<SubscribeCourse> subscribeCourseList = adminSubscribeCourseDao.findShowSubscribe(startTime,
        endTime);

    List<String> subScribeKeyIds = new ArrayList<String>();
    if (subscribeCourseList != null && subscribeCourseList.size() > 0) {
      for (SubscribeCourse subscribeCourse : subscribeCourseList) {
        subScribeKeyIds.add(subscribeCourse.getKeyId());
      }
    }
    // 2. 查询这些预约信息所对应的评论信息
    if (subScribeKeyIds == null || subScribeKeyIds.size() == 0) {
      return new Page();
    }
    return courseCommentEnityDao.findTeacherAverageScrore(param, subScribeKeyIds);
  }

  /**
   * 
   * Title: 查询对老师的评论<br>
   * Description: 查询对老师的评论<br>
   * CreateDate: 2016年7月23日 上午10:11:06<br>
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
    return courseCommentEnityDao.findTeacherCourseCommentList(param, startTime, endTime);
  }
}