package com.webi.hwj.ordercourse.entitydao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.param.CrmUpdateOrderMoneyParam;
import com.webi.hwj.ordercourse.param.FindOrderCourseCountParam;

@Repository
public class AdminOrderCourseEntityDao extends BaseEntityDao<OrderCourse> {
  private static Logger logger = Logger.getLogger(AdminOrderCourseEntityDao.class);
  private static final String DELETE_ORDER_COURSE_OPTION_BY_ORDER_ID =
      "delete from t_order_course_option where order_id = :orderId";

  /**
   * 查询合同的课程数量信息
   */
  private static final String FIND_ORDER_COURSE_COUNT_INFO =

  " SELECT toc.key_id as order_id,toc.user_id,toc.start_order_time,toc.end_order_time,toc.course_package_name, "

  + " toco.course_count,remain_course_count,toco.course_type,toc.order_original_type,toco.show_course_count,toco.order_id "
      + " FROM t_order_course toc LEFT JOIN t_order_course_option toco ON toc.key_id = toco.order_id "
      + " WHERE toc.order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " AND toc.user_id IN (:userIds) AND toc.category_type=:categoryType AND toc.is_used = 1 AND toco.is_used = 1 ";

  /**
   * 查询合同的课程数量信息
   */
  private static final String FIND_ORDER_COURSE_COUNT_INFO_BY_ORDERID =
      " SELECT toc.user_id,toc.start_order_time,toc.end_order_time,toc.course_package_name, "
          + " toco.course_count,remain_course_count,toco.course_type,toc.order_original_type,toco.show_course_count,toco.order_id "
          + " FROM t_order_course toc LEFT JOIN t_order_course_option toco ON toc.key_id = toco.order_id "
          + " WHERE toc.order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
          + " AND toc.key_id = :orderId AND toc.is_used = 1 AND toco.is_used = 1 ";

  /**
   * 查询合同的课程数量信息
   */
  private static final String FIND_ORDER_COURSE_COUNT_INFO_WITHOUT_CATEGORYTYPE =

  " SELECT toc.key_id as order_id,toc.user_id,toc.start_order_time,toc.end_order_time,toc.course_package_name, "
      + " toco.course_count,remain_course_count,toco.course_type,toc.order_original_type,toco.show_course_count,toco.order_id "
      + " FROM t_order_course toc LEFT JOIN t_order_course_option toco ON toc.key_id = toco.order_id "
      + " WHERE toc.order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " AND toc.user_id IN (:userIds) AND toc.is_used = 1 ";

  /**
   * 查询用户指定course_type的条数
   */
  private static final String FIND_COURSE_TYPE_COUNT_BY_USER_ID =
      "  SELECT COUNT(1)  "
          + " FROM t_order_course toc LEFT JOIN t_order_course_option toco ON toc.key_id = toco.order_id "
          + " WHERE toc.order_status =  " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
          + " AND toco.course_type = :courseType "
          + " AND toc.user_id = :userId AND toc.is_used = 1 AND toco.is_used =1 ";

  /**
   * 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的）
   * 
   * @author komi.zsy
   */
  public static final String FIND_NOT_ORDERS_BY_USERID = " SELECT COUNT(1) "
      + " FROM t_order_course "
      + " WHERE is_used <> 0 "
      + " AND user_id = :userId "
      + " AND (order_status != " + OrderStatusConstant.ORDER_STATUS_HAVE_EXPIRED
      + " AND order_status != " + OrderStatusConstant.ORDER_STATUS_HAVE_TERMINATED + " ) ";

  /**
   * @author komi.zsy crm专用，查询一段时间内，支付过的用户的支付总额
   */
  public static final String FIND_UPDATE_MONEY_WITH_DATE =
      "SELECT tu.phone,SUM(tocs.split_price) AS split_price"
          + " FROM t_order_course toc"
          + " LEFT JOIN t_order_course_split tocs"
          + " ON tocs.order_id = toc.key_id LEFT"
          + " JOIN t_user tu"
          + " ON tu.key_id = toc.user_id"
          + " WHERE tu.is_used = 1 AND tocs.is_used = 1 AND toc.is_used = 1"
          + " AND toc.order_status IN (" + OrderStatusConstant.ORDER_STATUS_PAYING + ","
          + OrderStatusConstant.ORDER_STATUS_HAVE_PAID + ")"
          + " AND tocs.split_status IN (" + OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_PAID_ONLINE
          + "," + OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_PAID_OFFLINE + ","
          + OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS + ")"
          + " AND tocs.update_date BETWEEN :startTime AND :endTime"
          + " AND tocs.split_price != 0"
          + " GROUP BY  toc.user_id";

  /**
   * Title: crm专用，查询一段时间内，支付过的用户的支付总额<br>
   * Description: crm专用，查询一段时间内，支付过的用户的支付总额<br>
   * CreateDate: 2017年6月28日 下午6:04:02<br>
   * 
   * @category crm专用，查询一段时间内，支付过的用户的支付总额
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<CrmUpdateOrderMoneyParam> findUpdateMoneyToCrm(Date startTime, Date endTime)
      throws Exception {
    CrmUpdateOrderMoneyParam crmUpdateOrderMoneyParam = new CrmUpdateOrderMoneyParam();
    crmUpdateOrderMoneyParam.setStartTime(startTime);
    crmUpdateOrderMoneyParam.setEndTime(endTime);
    return findList(FIND_UPDATE_MONEY_WITH_DATE, crmUpdateOrderMoneyParam);
  }

  /**
   * Title: 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的）<br>
   * Description: 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的）<br>
   * CreateDate: 2017年3月20日 下午3:45:34<br>
   * 
   * @category 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的）
   * @author komi.zsy
   * @param userId
   *          用户id
   * @return
   * @throws Exception
   */
  public int findNotOrderCourseByUserId(String userId)
      throws Exception {
    OrderCourse paramObj = new OrderCourse();
    paramObj.setUserId(userId);
    return findCount(FIND_NOT_ORDERS_BY_USERID, paramObj);
  }

  public OrderCourse findOneByKeyIdForStatus(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "order_status");
  }

  /**
   * Title: 根据id查找合同相关数据<br>
   * Description: 延期用<br>
   * CreateDate: 2016年10月31日 下午8:28:09<br>
   * 
   * @category 根据id查找合同相关数据
   * @author komi.zsy
   * @param keyId
   * @return
   * @throws Exception
   */
  public OrderCourse findOneByKeyIdWithExtension(String keyId) throws Exception {
    return super.findOneByKeyId(keyId,
        "key_id,order_status,start_order_time,end_order_time,data_desc");
  }

  /**
   * Title: 根据keyid查找合同数据<br>
   * Description: 根据keyid查找合同数据<br>
   * CreateDate: 2017年1月22日 下午2:06:57<br>
   * 
   * @category 根据keyid查找合同数据
   * @author komi.zsy
   * @param keyId
   *          合同主表id
   * @return
   * @throws Exception
   */
  public OrderCourse findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "from_path,course_package_name");
  }

  public int deleteForRealOption(String orderId) throws Exception {
    Map paramMap = new HashMap();
    paramMap.put("orderId", orderId);
    return namedParameterJdbcTemplate.update(DELETE_ORDER_COURSE_OPTION_BY_ORDER_ID, paramMap);
  }

  /**
   * 
   * Title: 查询合同的课程数量信息<br>
   * Description: 查询合同的课程数量信息<br>
   * CreateDate: 2016年5月31日 上午10:55:13<br>
   * 
   * @category 查询合同的课程数量信息
   * @author seven.gz
   * @param userIds
   * @return
   * @throws Exception
   */
  public List<FindOrderCourseCountParam> findOrderCourseCount(List<String> userIds,
      String categoryType) throws Exception {
    FindOrderCourseCountParam findCourseCountInfoParam = new FindOrderCourseCountParam();
    findCourseCountInfoParam.setUserIds(userIds);
    findCourseCountInfoParam.setCategoryType(categoryType);
    return super.findList(FIND_ORDER_COURSE_COUNT_INFO, findCourseCountInfoParam);
  }

  /**
   * 
   * Title: 查询合同的课程数量信息<br>
   * Description: 查询合同的课程数量信息<br>
   * CreateDate: 2016年5月31日 上午10:55:13<br>
   * 
   * @category 查询合同的课程数量信息
   * @author seven.gz
   * @param userIds
   * @return
   * @throws Exception
   */
  public List<FindOrderCourseCountParam> findOrderCourseCount(List<String> userIds)
      throws Exception {
    FindOrderCourseCountParam findCourseCountInfoParam = new FindOrderCourseCountParam();
    findCourseCountInfoParam.setUserIds(userIds);
    return super.findList(FIND_ORDER_COURSE_COUNT_INFO_WITHOUT_CATEGORYTYPE,
        findCourseCountInfoParam);
  }

  /**
   * 
   * Title: 查询合同的课程数量信息<br>
   * Description: 查询合同的课程数量信息<br>
   * CreateDate: 2016年5月31日 上午10:55:13<br>
   * 
   * @category 查询合同的课程数量信息
   * @author seven.gz
   * @param userIds
   * @return
   * @throws Exception
   */
  public List<FindOrderCourseCountParam> findOrderCourseCount(String orderId) throws Exception {
    FindOrderCourseCountParam findCourseCountInfoParam = new FindOrderCourseCountParam();
    findCourseCountInfoParam.setOrderId(orderId);
    return super.findList(FIND_ORDER_COURSE_COUNT_INFO_BY_ORDERID, findCourseCountInfoParam);
  }

  /**
   * 
   * Title: 查询合同字表中用户指定课程类型的条数<br>
   * Description: findCountUserCourseType<br>
   * CreateDate: 2017年3月10日 上午10:12:26<br>
   * 
   * @category 查询合同字表中用户指定课程类型的条数
   * @author seven.gz
   * @param userId
   * @param courseType
   * @return
   * @throws Exception
   */
  public int findCountUserCourseType(String userId, String courseType) throws Exception {
    FindOrderCourseCountParam paramObj = new FindOrderCourseCountParam();
    paramObj.setUserId(userId);
    paramObj.setCourseType(courseType);
    return super.findCount(FIND_COURSE_TYPE_COUNT_BY_USER_ID, paramObj);
  }
}