package com.webi.hwj.course.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.course.dao.CourseOne2ManySchedulingDao;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;

/**
 * Title: 一对多课程的业务逻辑<br>
 * Description: CourseOne2ManyService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月24日 上午11:12:33
 * 
 * @author Woody
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CourseOne2ManyService {
  private final Logger logger = Logger.getLogger(CourseOne2ManyService.class);

  @Resource
  private CourseOne2ManySchedulingDao courseOne2ManySchedulingDao;
  @Resource
  private SubscribeCourseDao subscribeCourseDao;
  @Resource
  OrderCourseDao orderCourseDao;
}