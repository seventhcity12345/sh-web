package com.webi.hwj.ordercourse.entitydao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.param.ContractLearningProgressParam;
import com.webi.hwj.ordercourse.param.OrderCourseAndOptionParam;

@Repository
public class OrderCourseEntityDao extends BaseEntityDao<OrderCourse> {
  private static Logger logger = Logger.getLogger(OrderCourseEntityDao.class);

  /**
   * 查找用户第一份合同的起始日期
   * 
   * @author komi.zsy
   */
  private static final String SELECT_FIRST_ORDER_BY_USER_ID = "SELECT start_order_time "
      + " FROM t_order_course "
      + " WHERE user_id = :userId AND start_order_time IS NOT NULL"
      + " ORDER BY start_order_time ASC "
      + " LIMIT 1";

  /**
   * 已经开始上课的合同(当前时间 >= 合同上课时间) 添加续约合同表示字段（在合同进度计算用）
   */
  public static final String FIND_STARTING_CONTRACT_LIST_BY_USERID = " SELECT key_id, "
      + " user_id, category_type, start_order_time, end_order_time "
      + " FROM t_order_course "
      + " WHERE is_used <> 0 "
      + " AND user_id = :userId "
      + " AND order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " AND start_order_time <= :startOrderTime "
      + " ORDER BY create_date desc ";

  /**
   * 在非学员状态的用户中心允许快速找到未付款的订单入口 在非学员状态的用户中心允许快速找到CC已发送状态的合同入口。 查找CC已经发送的&&
   * 用户经济确认但是没有付款的订单 ++ 用户正在支付中的订单 modify by athrun.cw
   */
  public static final String FIND_ORDERS_BY_USERID = " SELECT key_id, order_status "
      + " FROM t_order_course "
      + " WHERE is_used <> 0 "
      + " AND user_id = :userId "
      + " AND (order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_SENT
      + " OR order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_CONFIRMED
      + " OR order_status = " + OrderStatusConstant.ORDER_STATUS_PAYING + " ) "
      + " ORDER BY create_date DESC ";

  /**
   * 查找学员执行中和已过期合同
   * 
   * @author komi.zsy
   */
  public static final String FIND_COMPLETE_CONTRACT_LIST_BY_USERID = "SELECT key_id,user_name,"
      + " start_order_time,end_order_time,course_package_name,order_status"
      + " FROM `t_order_course`"
      + " WHERE user_id = :userId"
      + " AND is_used = 1"
      + " AND (order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " OR order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_EXPIRED + ")"
      + " ORDER BY end_order_time DESC";

  /**
   * 查找学员执行中合同
   * 
   * @author alex
   */
  public static final String FIND_ONLY_COMPLETE_CONTRACT_LIST_BY_USERID = "SELECT key_id,user_name,"
      + " start_order_time,end_order_time,course_package_name,order_status"
      + " FROM `t_order_course`"
      + " WHERE user_id = :userId"
      + " AND is_used = 1"
      + " AND order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " ORDER BY end_order_time DESC";

  /**
   * 根据学员的keyId作为t_order_course表的user_id去查询对应CC的id
   * 
   * @author felix.yl
   */
  private static final String FIND_CRMCON_TRACT_ID_BY_KEY_ID =
      "SELECT create_user_id"
          + " FROM t_order_course"
          + " WHERE user_id = :userId"
          + " AND is_used <> 0"
          + " ORDER BY create_date DESC"
          + " LIMIT 1";

  /**
   * Title: 查找学员执行中和已过期合同<br>
   * Description: 查找学员执行中和已过期合同<br>
   * CreateDate: 2016年10月13日 下午2:40:28<br>
   * 
   * @category 查找学员执行中和已过期合同
   * @author komi.zsy
   * @param userId
   * @return
   * @throws Exception
   */
  public List<OrderCourseAndOptionParam> findCompleteContractListByUserId(String userId)
      throws Exception {
    OrderCourseAndOptionParam paramObj = new OrderCourseAndOptionParam();
    paramObj.setUserId(userId);
    return super.findList(FIND_COMPLETE_CONTRACT_LIST_BY_USERID, paramObj);
  }

  /**
   * Title: 查找学员执行中合同<br>
   * Description: 查找学员执行中和已过期合同<br>
   * CreateDate: 2016年10月13日 下午2:40:28<br>
   * 
   * @category 查找学员执行中合同
   * @author alex
   * @param userId
   * @return
   * @throws Exception
   */
  public List<OrderCourseAndOptionParam> findOnlyCompleteContractListByUserId(String userId)
      throws Exception {
    OrderCourseAndOptionParam paramObj = new OrderCourseAndOptionParam();
    paramObj.setUserId(userId);
    return super.findList(FIND_ONLY_COMPLETE_CONTRACT_LIST_BY_USERID, paramObj);
  }

  /**
   * Title: 找到未付款 && CC已发送状态 的订单 Description: findOrdersByUserIdAndStatus<br>
   * CreateDate: 2015年8月14日 上午10:15:12<br>
   * 
   * @category 找到未付款 && CC已发送状态 的订单
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<OrderCourse> findOrdersByUserId(String userId)
      throws Exception {
    OrderCourse paramObj = new OrderCourse();
    paramObj.setUserId(userId);
    return findList(FIND_ORDERS_BY_USERID, paramObj);
  }

  /**
   * Title: 查询执行中的合同<br>
   * Description: 查询执行中的合同<br>
   * CreateDate: 2016年9月21日 下午2:55:25<br>
   * 
   * @category 查询执行中的合同
   * @author komi.zsy
   * @param userId
   *          用户id
   * @param startOrderTime
   *          合同开始时间
   * @return
   * @throws Exception
   */
  public List<ContractLearningProgressParam> findStartingContractListByUserId(String userId,
      Date startOrderTime)
          throws Exception {
    ContractLearningProgressParam paramObj = new ContractLearningProgressParam();
    paramObj.setUserId(userId);
    paramObj.setStartOrderTime(startOrderTime);
    return super.findList(FIND_STARTING_CONTRACT_LIST_BY_USERID, paramObj);

  }

  /**
   * Title: 查找用户第一份合同的起始日期<br>
   * Description: 查找用户第一份合同的起始日期<br>
   * CreateDate: 2016年4月26日 下午2:05:03<br>
   * 
   * @category 查找用户第一份合同的起始日期
   * @author komi.zsy
   * @param userId
   * @return
   * @throws Exception
   */
  public OrderCourse findFirstOrderByUserId(String userId) throws Exception {
    OrderCourse orderCourse = new OrderCourse();
    orderCourse.setUserId(userId);
    return super.findOne(SELECT_FIRST_ORDER_BY_USER_ID, orderCourse);
  }

  /**
   * Title: 通过合同id查询合同信息<br>
   * Description: findOneByOrderIdReturnAll<br>
   * CreateDate: 2016年11月11日 下午3:16:56<br>
   * 
   * @category 通过合同id查询合同信息
   * @author komi.zsy
   * @param orderId
   *          合同id
   * @return
   * @throws Exception
   */
  public OrderCourse findOneByOrderIdReturnAll(String orderId) throws Exception {
    OrderCourse orderCourse = new OrderCourse();
    orderCourse.setKeyId(orderId);
    return super.findOne(orderCourse,
        "key_id,user_id,course_package_name,total_real_price,total_show_price,"
            + "create_user_id,gift_time,start_order_time,order_original_type,"
            + "total_final_price,have_paid_price,user_from_type,order_version");
  }

  /**
   * 
   * Title: 根据登录学员的keyId作为user_id去查询CC的id<br>
   * Description: 根据登录学员的keyId作为user_id去查询CC的id<br>
   * CreateDate: 2017年7月5日 下午12:02:31<br>
   * 
   * @category 根据登录学员的keyId作为user_id去查询CC的id
   * @author felix.yl
   * @param userId
   * @return
   * @throws Exception
   */
  public List<OrderCourse> findCcId(String userId)
      throws Exception {
    OrderCourse paramObj = new OrderCourse();
    paramObj.setUserId(userId);
    return findList(FIND_CRMCON_TRACT_ID_BY_KEY_ID, paramObj);
  }

  /**
   * 
   * Title: 查询学员执行中的合同数量<br>
   * Description: findCountExecutoryOrder<br>
   * CreateDate: 2017年8月14日 下午3:12:51<br>
   * 
   * @category 查询学员执行中的合同数量
   * @author seven.gz
   * @param userId
   * @return
   * @throws Exception
   */
  public int findCountExecutoryOrder(String userId) throws Exception {
    OrderCourse paramObj = new OrderCourse();
    paramObj.setUserId(userId);
    paramObj.setOrderStatus(Integer.valueOf(OrderStatusConstant.ORDER_STATUS_HAVE_PAID));
    return super.findCount(paramObj);
  }
}