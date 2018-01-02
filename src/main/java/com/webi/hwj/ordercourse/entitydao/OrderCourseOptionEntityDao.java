package com.webi.hwj.ordercourse.entitydao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.param.OrderCourseOptionParam;

@Repository
public class OrderCourseOptionEntityDao extends BaseEntityDao<OrderCourseOption> {
  private static Logger logger = Logger.getLogger(OrderCourseOptionEntityDao.class);

  /**
   * 根据合同id查看课时数据
   * 
   * @author komi.zsy
   */
  private static final String FIND_ORDER_DETAILS_BY_ORDERID = " SELECT key_id, "
      + " course_type,show_course_count,remain_course_count,is_gift,course_unit_type "
      + " FROM t_order_course_option "
      + " WHERE is_used <> 0 "
      + " AND order_id = :orderId ";

  /**
   * Title: 根据合同id查看课时数据<br>
   * Description: 根据合同id查看课时数据<br>
   * CreateDate: 2016年9月21日 下午3:22:29<br>
   * 
   * @category 根据合同id查看课时数据
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<OrderCourseOption> findDetailsOptionByOrderId(String orderId)
      throws Exception {
    OrderCourseOption paramObj = new OrderCourseOption();
    paramObj.setOrderId(orderId);
    return findList(FIND_ORDER_DETAILS_BY_ORDERID, paramObj);
  }

  /**
   * Title: 根据合同id查看课时数据<br>
   * Description: 根据合同id查看课时数据<br>
   * CreateDate: 2016年9月21日 下午3:22:29<br>
   * 
   * @category 根据合同id查看课时数据
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<OrderCourseOptionParam> findDetailsOptionParamByOrderId(String orderId)
      throws Exception {
    OrderCourseOptionParam paramObj = new OrderCourseOptionParam();
    paramObj.setOrderId(orderId);
    return findList(FIND_ORDER_DETAILS_BY_ORDERID, paramObj);
  }

  /**
   * Title: 通过合同id来查询合同子表集合<br>
   * Description: findList<br>
   * CreateDate: 2016年10月31日 下午9:06:12<br>
   * 
   * @category 通过合同id来查询合同子表集合
   * @author yangmh
   * @param orderId
   *          合同id
   * @return
   * @throws Exception
   */
  public List<OrderCourseOption> findListByOrderIdReturnCourseType(String orderId)
      throws Exception {
    OrderCourseOption orderCourseOption = new OrderCourseOption();
    orderCourseOption.setOrderId(orderId);
    return super.findList(orderCourseOption,
        "key_id,course_type,course_unit_type,show_course_count,course_count,is_gift,real_price");
  }
}
