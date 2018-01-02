package com.webi.hwj.ordercourse.entitydao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.entity.OrderCourseSplit;
import com.webi.hwj.ordercourse.param.AdminOrderAndOrderSplitParam;

@Repository
public class AdminOrderCourseSplitEntityDao extends BaseEntityDao<OrderCourseSplit> {
  private static Logger logger = Logger.getLogger(AdminOrderCourseSplitEntityDao.class);

  private static final String DELETE_ORDER_COURSE_SPLIT_BY_ORDER_ID =
      "delete from t_order_course_split where order_id = :orderId ";

  /**
   * 根据用户id查询已支付订单拆分信息.
   */
  private static final String FIND_LIST_PAID_BY_USER_IDS =
      " SELECT toc.user_id,tocs.split_price,tocs.update_date FROM t_order_course_split tocs  "
          + " LEFT JOIN t_order_course toc ON tocs.order_id = toc.key_id WHERE tocs.is_used = 1 "
          + " AND tocs.split_status IN ('" + OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_PAID_ONLINE
          + "','" + OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_PAID_OFFLINE
          + "','" + OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS
          + "') AND toc.user_id IN (:userIds) ";

  /**
   * Title: 查找合同拆分订单申请中的数据<br>
   * Description: 查找合同拆分订单申请中的数据（招联百度专用！！！）<br>
   * CreateDate: 2017年4月25日 下午7:02:10<br>
   * 
   * @category 查找合同拆分订单数据
   * @author komi.zsy
   * @param orderId
   *          合同id
   * @return
   * @throws Exception
   */
  public int findOneByOrderIdAndStatus(String orderId) throws Exception {
    OrderCourseSplit paramObj = new OrderCourseSplit();
    paramObj.setOrderId(orderId);
    paramObj.setSplitStatus(Integer.parseInt(
        OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING));;
    return super.findCount(paramObj);
  }

  /**
   * 
   * Title: 删除split表数据<br>
   * Description: deleteForRealSplit<br>
   * CreateDate: 2016年1月21日 下午6:29:41<br>
   * 
   * @category deleteForRealSplit
   * @author athrun.cw
   * @param id
   * @return
   * @throws Exception
   */
  public int deleteForRealByOrderId(String orderId) throws Exception {
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("orderId", orderId);
    return namedParameterJdbcTemplate.update(DELETE_ORDER_COURSE_SPLIT_BY_ORDER_ID, paramMap);
  }

  /**
   * Title: 根据用户ID查询合同和拆分订单信息<br>
   * Description: findListOrderAndOrderSplit<br>
   * CreateDate: 2017年6月8日 上午11:14:20<br>
   * 
   * @category 根据用户ID查询合同和拆分订单信息
   * @author seven.gz
   * @param userIds
   *          用户id
   */
  public List<AdminOrderAndOrderSplitParam> findListOrderAndOrderSplit(List<String> userIds)
      throws Exception {
    AdminOrderAndOrderSplitParam paramObj = new AdminOrderAndOrderSplitParam();
    paramObj.setUserIds(userIds);
    return super.findList(FIND_LIST_PAID_BY_USER_IDS, paramObj);
  }
}