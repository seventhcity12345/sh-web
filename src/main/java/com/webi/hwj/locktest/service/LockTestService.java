package com.webi.hwj.locktest.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.ordercourse.dao.AdminOrderCourseDao;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeSupplementDao;
import com.webi.hwj.teacher.dao.TeacherTimeDao;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class LockTestService {

  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  SubscribeCourseDao subscribeCourseDao;

  @Resource
  AdminOrderCourseDao adminOrderCourseDao;

  @Resource
  TeacherTimeDao teacherTimeDao;

  @Resource
  SubscribeSupplementDao subscribeSupplementDao;

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void aaa() throws Exception {
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void bbb() throws Exception {
    Map<String, Object> orderCourseObj = new HashMap<String, Object>();
    orderCourseObj.put("key_id", "1");
    orderCourseObj.put("user_id", "1");
    orderCourseObj.put("start_order_time", new Date());
    orderCourseObj.put("end_order_time", new Date());
    orderCourseObj.put("limit_show_time", 1);
    adminOrderCourseDao.insert(orderCourseObj);
  }

  public Map<String, Object> findSmallpackMaxPositionSubscribedByUserIdAndCourseType(
      Map<String, Object> paramMap) throws Exception {
    return null;
  }
}