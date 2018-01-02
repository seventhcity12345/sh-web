package com.webi.hwj.ordercourse.entitydao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.param.OrderCourseOptionCountParam;
import com.webi.hwj.ordercourse.param.OrderCourseOptionParam;

@Repository
public class AdminOrderCourseOptionEntityDao extends BaseEntityDao<OrderCourseOption> {
  private static Logger logger = Logger.getLogger(AdminOrderCourseOptionEntityDao.class);

  /**
   * 查询用户当前执行中的合同拥有的课程
   * 
   * @author komi.zsy
   */
  private static final String FIND_ORDER_COURSE_INFO =
      "SELECT toc.start_order_time,toc.end_order_time,toc.course_package_name,"
          + "toco.`course_type`,toco.`show_course_count`,"
          + "toco.`remain_course_count`,toco.`is_gift`,toco.`course_unit_type`"
          + " FROM `t_order_course_option` toco"
          + " LEFT JOIN  `t_order_course` toc"
          + " ON toco.order_id = toc.key_id AND toc.order_status = 5"
          + " WHERE toc.user_id = :userId"
          + " AND toc.is_used <> 0 AND toco.is_used <> 0 "
          + " ORDER BY toco.`is_gift` ASC";

  /**
   * 更新合同子表课程数量（延期操作）
   * 
   * @author komi.zsy
   */
  private static final String UPDATE_ORDER_COURSE_OPTION_BY_EXTENSION =
      "UPDATE t_order_course_option"
          + " SET remain_course_count = remain_course_count + :courseCount,"
          + " show_course_count = show_course_count + :courseCount,"
          + " data_desc = '执行延期操作，增加合同课程数量'"
          + " WHERE order_id = :orderId"
          + " AND course_unit_type = " + OrderCourseConstant.COURSE_UNIT_TYPE_MONTH
          + " AND is_gift = " + OrderCourseConstant.ORDER_COURSE_OPTION_IS_NOT_GIFT
          + " AND is_used = 1 ";

  /**
   * 根据合同id查找数据
   * 
   * @author komi.zsy
   */
  private static final String FIND_LIST_BY_ORDER_ID = "  SELECT toco.order_id,toco.course_type,"
      + "toco.show_course_count,toco.remain_course_count,toco.course_unit_type,"
      + "toc.start_order_time"
      + " FROM t_order_course_option toco"
      + " LEFT JOIN `t_order_course` toc"
      + " ON toco.order_id = toc.key_id"
      + " WHERE toco.order_id IN (:orderIds)"
      // modify by seven 2017年4月21日09:49:25 为了过期合同也能显示
      + " AND toc.is_used = 1 AND toco.is_delete = 1 "
      + " ORDER BY toco.order_id";

  /**
   * Title: 更新合同子表课程数量<br>
   * Description: 延期操作<br>
   * CreateDate: 2017年3月9日 下午4:59:14<br>
   * 
   * @category 更新合同子表课程数量
   * @author komi.zsy
   * @param courseCount
   *          增加课时数
   * @param orderId
   *          合同id
   * @return
   * @throws Exception
   */
  public int updateOrderCourseOptionByExtension(int courseCount, String orderId) throws Exception {
    OrderCourseOption paramObj = new OrderCourseOption();
    paramObj.setCourseCount(courseCount);
    paramObj.setOrderId(orderId);
    return super.update(UPDATE_ORDER_COURSE_OPTION_BY_EXTENSION, paramObj);
  }

  /**
   * Title: 为了给新续约合同中合同子表的计算剩余课时数做准备而查询的源合同子表项集合<br>
   * Description: 为了给新续约合同中合同子表的计算剩余课时数做准备而查询的源合同子表项集合<br>
   * CreateDate: 2017年1月22日 下午2:11:43<br>
   * 
   * @category 为了给新续约合同中合同子表的计算剩余课时数做准备而查询的源合同子表项集合
   * @author athrun.cw
   * @param orderId
   *          合同id
   * @return
   * @throws Exception
   */
  public List<OrderCourseOption> findListAll(String orderId) throws Exception {
    OrderCourseOption orderCourseOption = new OrderCourseOption();
    orderCourseOption.setOrderId(orderId);
    return super.findList(orderCourseOption,
        "key_id,order_id,course_type,course_unit_type,real_price,show_course_count,course_count,remain_course_count,is_gift,create_user_id,update_user_id");
  }

  /**
   * Title: 查询用户当前执行中的合同拥有的课程<br>
   * Description: 查询用户当前执行中的合同拥有的课程<br>
   * CreateDate: 2016年9月20日 上午10:47:03<br>
   * 
   * @category 查询用户当前执行中的合同拥有的课程
   * @author komi.zsy
   * @param userId
   * @return
   * @throws Exception
   */
  public List<OrderCourseOptionParam> findOrderCourseInfoList(String userId) throws Exception {
    OrderCourseOptionParam paramObj = new OrderCourseOptionParam();
    paramObj.setUserId(userId);
    return super.findList(FIND_ORDER_COURSE_INFO, paramObj);
  }

  /**
   * Title: 根据合同id查找数据<br>
   * Description: 根据合同id查找数据<br>
   * CreateDate: 2017年2月15日 下午2:34:56<br>
   * 
   * @category 根据合同id查找数据
   * @author seven.gz
   * @param orderIds
   *          合同id
   */
  public List<OrderCourseOptionCountParam> findListByOrderId(List<String> orderIds)
      throws Exception {
    OrderCourseOptionCountParam paramObj = new OrderCourseOptionCountParam();
    paramObj.setOrderIds(orderIds);
    return super.findList(FIND_LIST_BY_ORDER_ID, paramObj);
  }
}
