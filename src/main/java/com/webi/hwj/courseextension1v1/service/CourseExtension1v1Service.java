package com.webi.hwj.courseextension1v1.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.course.service.CourseService;
import com.webi.hwj.courseextension1v1.dao.CourseExtension1v1Dao;
import com.webi.hwj.courseone2one.param.CourseOne2OneParam;

/**
 * @category courseExtension1v1控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class CourseExtension1v1Service {
  private static Logger logger = Logger.getLogger(CourseExtension1v1Service.class);
  @Resource
  CourseExtension1v1Dao courseExtension1v1Dao;
  @Resource
  CourseService courseService;

  /**
   * Title: 查询course_type9列表<br>
   * Description: 查询course_type1列表 <br>
   * CreateDate: 2016年9月6日14:56:46<br>
   * 
   * @category 查询course_type9列表
   * @author komi.zsy
   * @param sessionUser
   * @return
   * @throws Exception
   */
  public List<CourseOne2OneParam> findCourseType9List(SessionUser sessionUser) throws Exception {
    // 查询学员当前等级的课程列表
    List<CourseOne2OneParam> one2OneCourseList = courseExtension1v1Dao
        .findCourseType9List(sessionUser.getCurrentLevel());

    // 根据rsa进度和预约情况，组装1v1类型课程数据

    courseService.findCourseOne2OneList(sessionUser, one2OneCourseList);
    return one2OneCourseList;
  }
}