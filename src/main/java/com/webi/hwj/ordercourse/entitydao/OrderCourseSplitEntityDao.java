package com.webi.hwj.ordercourse.entitydao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.entity.OrderCourseSplit;
import com.webi.hwj.ordercourse.param.OrderAndOrderSplitParam;

@Repository
public class OrderCourseSplitEntityDao extends BaseEntityDao<OrderCourseSplit> {
  private static Logger logger = Logger.getLogger(OrderCourseSplitEntityDao.class);

  /**
   * 查询未支付的订单及其所属合同的课程包名字
   * (分期查询使用，因为和正常的状态码不一样)
   * @author komi.zsy
   */
  private static final String FIND_ONE_AND_ORDER_INFO_BY_KEYID = "SELECT tosc.key_id,tosc.split_price,toc.course_package_name"
      + " FROM t_order_course_split tosc"
      + " LEFT JOIN t_order_course toc ON toc.key_id = tosc.order_id"
      + " WHERE toc.is_used = 1 AND tosc.is_used = 1"
      + " AND (tosc.split_status = " + OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY 
      + " OR tosc.split_status = " +  OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL +")"
      + " AND  tosc.key_id = :keyId ";
  
  /**
   * Title: 查询未支付的订单及其所属合同的课程包名字<br>
   * Description: 查询未支付的订单及其所属合同的课程包名字(分期查询使用，因为和正常的状态码不一样)<br>
   * CreateDate: 2017年1月19日 上午11:01:35<br>
   * @category 查询未支付的订单及其所属合同的课程包名字 
   * @author komi.zsy
   * @param splitOrderId 订单id
   * @return
   * @throws Exception
   */
  public OrderAndOrderSplitParam findOneAndOrderInfoByKeyId(String splitOrderId) throws Exception {
    OrderAndOrderSplitParam paramObj = new OrderAndOrderSplitParam();
    paramObj.setKeyId(splitOrderId);
    return super.findOne(FIND_ONE_AND_ORDER_INFO_BY_KEYID, paramObj);
  }
}