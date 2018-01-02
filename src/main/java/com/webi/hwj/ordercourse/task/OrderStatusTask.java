package com.webi.hwj.ordercourse.task;

import javax.annotation.Resource;

import com.webi.hwj.ordercourse.service.OrderCourseService;

/**
 * 订单状态定时任务: 在每天的0点，走系统任务把订单状态为3的订单update成9.
 * 
 * @author vector.mjp
 */
public class OrderStatusTask {
  @Resource
  private OrderCourseService orderCourseService;

  /**
   * @category 关闭课程包订单
   */
  public void closeOrder() {

    try {
      orderCourseService.closeOrder();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("关闭课程包订单发生异常！消息如下：" + e.getMessage());
    }

  }

}
