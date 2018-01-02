package com.webi.hwj.ordercourse.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;
import com.webi.hwj.constant.OrderStatusConstant;

@Repository
public class AdminOrderCourseSplitDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(AdminOrderCourseSplitDao.class);

  public AdminOrderCourseSplitDao() {
    super.setTableName("t_order_course_split");
  }

  /**
   * 删除split
   */
  private static final String DALETE_ORDERCOURSE_SPLIT_BY_ORDERID = " DELETE FROM t_order_course_split "
      + " WHERE is_used <> 0 "
      + " AND order_id = :order_id ";

  /**
   * 销售拟定好的合同，有可能被拆分成多个订单:"key_id, split_price, split_status, update_date,
   * pay_from"
   * 
   * modify by athrun.cw 2016年3月8日16:17:00
   * 
   * 以下字段 都是需要数据库中添加字段的 添加新的字段：pay_bank, pay_success_sequence, pay_cc_name,
   * pay_center_name, pay_city_name, split_pay_type, create_date
   * 
   */
  private static final String FIND_ORDERCOURSE_SPLIT_BY_ORDERID = " SELECT key_id, split_price, "
      + " split_status, update_date, pay_from, pay_bank, pay_success_sequence, pay_cc_name, "
      + " pay_center_name, pay_city_name, split_pay_type, create_date "
      + " FROM t_order_course_split "
      + " WHERE is_used <> 0 "
      + " AND order_id = :order_id ";
  
  /**
   * 物理删除拆分订单数据
   */
  private static final String DELETE_ORDER_SPLIT_BY_NOT_PAID = " DELETE FROM t_order_course_split "
      + " WHERE order_id = :orderId"
      + " AND split_status IN ("+OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_ONLINE+
      ","+OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE
      +","+OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY
      +","+OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL+") ";

  /**
   * 
   * Title: 由合同id，删除split<br>
   * Description: deleteOrderCourseSplitByOrderId<br>
   * CreateDate: 2016年1月20日 下午3:48:48<br>
   * 
   * @category 由合同id，删除split
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int deleteOrderCourseSplitByOrderId(Map<String, Object> paramMap) throws Exception {
    return update(DALETE_ORDERCOURSE_SPLIT_BY_ORDERID, paramMap);
  }

  /**
   * 
   * Title: 拆分订单预加载接口:销售拟定好的合同，有可能被拆分成多个订单<br>
   * Description: findOrderCourseSplitByOrderId<br>
   * CreateDate: 2016年1月12日 上午11:26:01<br>
   * 
   * @category 拆分订单预加载接口:销售拟定好的合同，有可能被拆分成多个订单
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findOrderCourseSplitByOrderId(Map<String, Object> paramMap)
      throws Exception {
    return findList(FIND_ORDERCOURSE_SPLIT_BY_ORDERID, paramMap);
  }

  /**
   * 
   * Title: 真实删除掉 （未支付的）拆分订单表<br>
   * Description: deleteForRealOrderCourseSplit<br>
   * CreateDate: 2016年1月12日 下午4:35:07<br>
   * 
   * @category 真实删除掉（未支付的）拆分订单表
   * @author athrun.cw
   * @param orderId 合同id
   * @return
   * @throws Exception
   */
  public int deleteForRealOrderCourseSplit(String orderId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("orderId", orderId);
    return super.update(DELETE_ORDER_SPLIT_BY_NOT_PAID, paramMap);
  }

}