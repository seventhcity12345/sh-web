package com.webi.hwj.ordercourse.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class OrderCourseOptionDao extends BaseMysqlDao {

  /**
   * 查看订单下的 所有具体的服务 modified by alex.yangmh 2015年12月25日17:18:23 删掉老的字段
   */
  private static final String FIND_ORDER_DETAILS_BY_ORDERID = " SELECT key_id, order_id, "
      + " course_type, real_price, show_course_count,course_count, remain_course_count,is_gift,course_unit_type "
      + " FROM t_order_course_option "
      + " WHERE is_used <> 0 "
      + " AND order_id = :order_id ";

  public OrderCourseOptionDao() {
    super.setTableName("t_order_course_option");
  }

  /**
   * 
   * Title: 查看订单下的 所有具体的服务 Description: findDetailOrderByOrderId<br>
   * CreateDate: 2015年8月14日 上午10:50:20<br>
   * 
   * @category findDetailOrderByOrderId 查看订单下的 所有具体的服务
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findDetailsOptionByOrderId(Map<String, Object> paramMap)
      throws Exception {
    return findList(FIND_ORDER_DETAILS_BY_ORDERID, paramMap);
  }

}