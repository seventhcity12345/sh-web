/** 
 * File: PayOrderCourseService.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian.service<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月21日 下午4:23:41
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.kuaiqian.dao.PayOrderCourseDao;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.user.dao.UserDao;

/**
 * Title: 快钱返回响应后，需要做的业务处理
 * 
 * 1.把t_user表中的is_student字段给弄成1 2.把当前session里的用户对象的student设置成1 (true)
 * 3.把t_order_course里的order_status设置成已支付 4.更新日志记录表，将trade_status 更新为返回的
 * payResult
 * 
 * Description: PayOrderCourseService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月21日 下午4:23:41
 * 
 * @author athrun.cw
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PayOrderCourseService {
  private static Logger logger = Logger.getLogger(PayOrderCourseService.class);
  @Resource
  UserDao userDao;

  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  PayOrderCourseDao payOrderCourseDao;

  @Resource
  OrderCourseService orderCourseService;

}
