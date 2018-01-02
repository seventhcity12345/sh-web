package com.webi.hwj.ordercourse.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.baidu.service.BaiduService;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseDao;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseSplitDao;
import com.webi.hwj.ordercourse.entitydao.OrderCourseSplitEntityDao;
import com.webi.hwj.ordercourse.param.OrderAndOrderSplitParam;
import com.webi.hwj.zhaolian.service.ZhaolianService;

@Service
public class OrderCourseSplitService {
  private static Logger logger = Logger.getLogger(OrderCourseSplitService.class);

  @Resource
  private AdminOrderCourseSplitDao adminOrderCourseSplitDao;

  @Resource
  private AdminOrderCourseDao adminOrderCourseDao;

  @Resource
  OrderCourseService orderCourseService;

  @Resource
  AfterPayService afterPayService;

  @Resource
  AdminOrderCourseService adminOrderCourseService;

  @Resource
  private BaiduService baiduService;
  @Resource
  private OrderCourseSplitEntityDao orderCourseSplitEntityDao;

  @Resource
  private ZhaolianService zhaolianService;

  /**
   * 
   * Title: 确认支付成功接口<br>
   * Description: confirmSuccessPay<br>
   * CreateDate: 2016年3月12日 下午3:20:09<br>
   * 
   * @category 确认支付成功接口
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public Map<String, Object> confirmSuccessPay(Map<String, Object> paramMap) throws Exception {
    // 返回code & msg（500 200）
    Map<String, Object> confirmSuccessPayMap = new HashMap<String, Object>();

    /**
     * modify by athrun.cw 2016年3月12日15:20:51
     * 
     * 财务可以修改订单。因此所确认的订单 可以是1.原有或者修改的；2.新增的订单
     */
    Map<String, Object> orderCourseMap = adminOrderCourseDao.findOneByKeyId(
        paramMap.get("order_id"),
        "key_id, have_paid_price, total_real_price, user_id, user_name, limit_show_time, limit_show_time_unit,"
            + "total_final_price, order_original_type,from_path,gift_time,order_status");
    if (orderCourseMap == null) {
      logger.error("数据错误，找不到order_id：" + paramMap.get("order_id") + "的合同信息！");
      confirmSuccessPayMap.put("code", "500");
      confirmSuccessPayMap.put("msg", "确认支付失败，没有找到该合同信息！");
      return confirmSuccessPayMap;
    }

    /**
     * 更新该订单的split_status， 1.需要把split表中的状态改为3:已支付,已确认 2.同时调用通用逻辑：该订单支付后，是否合同已经完成
     */

    /**
     * modify by athrun.cw 2016年3月12日15:22:43
     * 
     * 现在 财务可以修改订单。因此所确认的订单 可以是1.原有或者修改的；2.新增的订单 split_order_id可以为空
     */
    Object split_order_id = paramMap.get("split_order_id");
    Map<String, Object> orderCourseSplitMap = null;
    /**
     * 2.新增的订单
     */
    if (split_order_id == null || "".equals(split_order_id) || "null".equals(split_order_id)) {
      Map<String, Object> insertOrderCourseSplitMap = new HashMap<String, Object>();
      insertOrderCourseSplitMap.put("order_id", paramMap.get("order_id"));
      insertOrderCourseSplitMap.put("split_price", paramMap.get("split_price"));
      insertOrderCourseSplitMap.put("split_status", paramMap.get("split_status"));
      insertOrderCourseSplitMap.put("split_pay_type", paramMap.get("split_pay_type"));
      insertOrderCourseSplitMap.put("pay_bank", paramMap.get("pay_bank"));
      insertOrderCourseSplitMap.put("pay_success_sequence", paramMap.get("pay_success_sequence"));
      insertOrderCourseSplitMap.put("pay_cc_name", paramMap.get("pay_cc_name"));
      insertOrderCourseSplitMap.put("pay_center_name", paramMap.get("pay_center_name"));
      insertOrderCourseSplitMap.put("pay_city_name", paramMap.get("pay_city_name"));
      insertOrderCourseSplitMap.put("create_user_id", paramMap.get("admin_user_id"));
      insertOrderCourseSplitMap.put("update_user_id", paramMap.get("admin_user_id"));

      adminOrderCourseSplitDao.insert(insertOrderCourseSplitMap);

      orderCourseSplitMap = insertOrderCourseSplitMap;

    } else {
      /**
       * 1.原有或者修改的,全部都是update一边
       */
      Map<String, Object> updateOrderCourseSplitMap = new HashMap<String, Object>();
      updateOrderCourseSplitMap.put("key_id", paramMap.get("split_order_id"));
      updateOrderCourseSplitMap.put("order_id", paramMap.get("order_id"));
      updateOrderCourseSplitMap.put("split_price", paramMap.get("split_price"));
      updateOrderCourseSplitMap.put("split_status", paramMap.get("split_status"));
      updateOrderCourseSplitMap.put("split_pay_type", paramMap.get("split_pay_type"));
      updateOrderCourseSplitMap.put("pay_bank", paramMap.get("pay_bank"));
      updateOrderCourseSplitMap.put("pay_success_sequence", paramMap.get("pay_success_sequence"));
      updateOrderCourseSplitMap.put("pay_cc_name", paramMap.get("pay_cc_name"));
      updateOrderCourseSplitMap.put("pay_center_name", paramMap.get("pay_center_name"));
      updateOrderCourseSplitMap.put("pay_city_name", paramMap.get("pay_city_name"));
      updateOrderCourseSplitMap.put("update_user_id", paramMap.get("admin_user_id"));
      updateOrderCourseSplitMap.put("update_date", new Date());

      adminOrderCourseSplitDao.update(updateOrderCourseSplitMap);

      orderCourseSplitMap = updateOrderCourseSplitMap;
    }

    // 2.同时调用通用逻辑：该订单支付后，是否合同已经完成
    afterPayService.afterPaySuccessLogicCommon(orderCourseSplitMap.get("key_id").toString(),
        orderCourseSplitMap.get("split_price").toString(),
        paramMap.get("pay_from").toString(),
        orderCourseSplitMap,
        /**
         * modify by athrun.cw 2016年3月9日15:38:16
         * 
         * 生效日期：支付完成的时间即生效日期 同时 这时候 也要更新合同截止日期了，所以必须要limit_show_time
         */
        orderCourseMap);

    confirmSuccessPayMap.put("code", "200");
    confirmSuccessPayMap.put("msg", "确认支付成功！");
    confirmSuccessPayMap.put("data", orderCourseSplitMap.get("key_id"));
    return confirmSuccessPayMap;
  }

  /**
   * 
   * Title: 拆分订单预加载接口:销售拟定好的合同，有可能被拆分成多个订单。<br>
   * Description: preLoadOrderCourseSplitByOrderId<br>
   * CreateDate: 2016年1月12日 上午11:03:34<br>
   * 
   * @category 拆分订单预加载接口:销售拟定好的合同，有可能被拆分成多个订单。
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> preLoadOrderCourseSplitByOrderId(Map<String, Object> paramMap)
      throws Exception {
    /**
     * 1.查询合同主表 2.查询合同拆分表
     */
    /**
     * Modify by athrun.cw 2016年3月8日15:57:14
     * 
     * 新的拆分合同中，需要额外的数据 1.验证订单是否完成，添加了：user_from_type来源类型（0:常规，1:线下售卖，2:线下转线上）
     */
    Map<String, Object> orderCourseMap = adminOrderCourseDao.findOneByKeyId(
        paramMap.get("order_id"),
        "order_original_type,total_final_price, have_paid_price, total_real_price, user_from_type ");
    if (orderCourseMap == null) {
      logger.error("数据错误，找不到订单id：" + paramMap.get("order_id") + "的订单信息！");
      throw new RuntimeException("数据错误，找不到订单id：" + paramMap.get("order_id") + "的订单信息！");
    }
    int should_pay_price = Integer.parseInt(orderCourseMap.get("total_real_price").toString())
        - Integer.parseInt(orderCourseMap.get("have_paid_price").toString());
    /**
     * modify by athrun.cw 2016年4月12日15:50:48 续约拆分
     */
    if (OrderStatusConstant.ORDER_ORIGINAL_TYPE_RENEWAL
        .equals(orderCourseMap.get("order_original_type").toString())) {
      should_pay_price = Integer.parseInt(orderCourseMap.get("total_final_price").toString())
          - Integer.parseInt(orderCourseMap.get("have_paid_price").toString());
    }

    /**
     * modify by athrun.cw 2016年3月21日14:17:33
     * 前台显示订单详情的话，不管是should_pay_price=0否，都需要返回到前台
     */

    /**
     * Modify by athrun.cw 2016年3月8日15:57:14
     * 
     * 2.拆分的订单
     */
    // 找拆分订单记录
    List<Map<String, Object>> orderCourseSplitMapList = adminOrderCourseSplitDao
        .findOrderCourseSplitByOrderId(paramMap);

    if (orderCourseSplitMapList != null && orderCourseSplitMapList.size() != 0) {
      for (Map<String, Object> orderCourseSplitMap : orderCourseSplitMapList) {
        // 如果是百度，要去更新一下订单状态
        if ((OrderStatusConstant.ORDER_SPLIT_PAY_TYPE_BAIDU + "")
            .equals(orderCourseSplitMap.get("split_pay_type").toString())) {
          CommonJsonObject json = baiduService.getOrderStatus(orderCourseSplitMap.get("key_id")
              .toString(),
              orderCourseSplitMap.get("split_status").toString());
          // 百度返回的状态码赋值到这次的订单列表中
          orderCourseSplitMap.put("split_status", json.getData());
        } else if ((OrderStatusConstant.ORDER_SPLIT_PAY_TYPE_ZHAOLIAN + "")
            .equals(orderCourseSplitMap.get("split_pay_type").toString())) {
          // 招联更新状态
          String splitStatus = zhaolianService.findOrderSplitStatus(orderCourseSplitMap.get(
              "key_id")
              .toString(), orderCourseSplitMap.get("split_status").toString());
          // 返回的状态码赋值到这次的订单列表中
          orderCourseSplitMap.put("split_status", splitStatus);
        }
      }
    }

    Map<String, Object> preLoadOrderCourseMap = new HashMap<String, Object>();
    preLoadOrderCourseMap.put("should_pay_price", should_pay_price);
    preLoadOrderCourseMap.put("have_paid_price", orderCourseMap.get("have_paid_price"));

    /**
     * modify by athrun.cw 2016年3月8日16:02:17
     */
    preLoadOrderCourseMap.put("user_from_type", orderCourseMap.get("user_from_type"));
    preLoadOrderCourseMap.put("split_list", orderCourseSplitMapList);

    return preLoadOrderCourseMap;
  }

  /**
   * 
   * Title: 订单拆分提交:将拆分好的订单提交至服务器 <br>
   * Description: saveOrderCourseSplit<br>
   * CreateDate: 2016年1月12日 下午3:17:30<br>
   * 
   * @category 订单拆分提交:将拆分好的订单提交至服务器
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public Map<String, Object> saveOrderCourseSplit(Map<String, Object> paramMap) throws Exception {
    // 返回code & msg
    Map<String, Object> orderCourseSplitMap = new HashMap<String, Object>();

    String orderId = paramMap.get("order_id").toString();

    // 获取到订单id下的合同详细信息
    /**
     * modify by athrun.cw 2016年3月8日16:25:36 将查询字段修改为要使用的字段，替代*
     */
    Map<String, Object> orderCourseMap = adminOrderCourseDao.findOneByKeyId(
        paramMap.get("order_id"),
        "order_original_type,total_final_price,order_status, total_real_price, have_paid_price ");
    if (orderCourseMap == null) {
      logger.error("数据错误，拆分合同失败，找不到订单id：" + orderId + "的订单信息！");
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "拆分合同失败，没有该合同信息！");
      return orderCourseSplitMap;
    }

    if (OrderStatusConstant.ORDER_STATUS_HAVE_PAID
        .equals(orderCourseMap.get("order_status").toString())) {
      // modified by seven 2016年8月6日15:31:09 修改日志级别
      logger.info("数据错误，合同id：" + orderId + "的订单已支付完成，不能再拆分~~~");
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "拆分合同失败，合同已支付成功！");
      return orderCourseSplitMap;
    }

    // modified
    String order_status = orderCourseMap.get("order_status").toString();
    if (!OrderStatusConstant.ORDER_STATUS_HAVE_PLANNED.equals(order_status) &&
        !OrderStatusConstant.ORDER_STATUS_HAVE_SENT.equals(order_status) &&
        !OrderStatusConstant.ORDER_STATUS_HAVE_CONFIRMED.equals(order_status) &&
        !OrderStatusConstant.ORDER_STATUS_PAYING.equals(order_status)) {
      logger.error("有人盗链，拆分合同失败，非法状态：" + orderId + "的订单信息！");
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "拆分合同失败，非法状态！");
      return orderCourseSplitMap;
    }

    // 本次拆分的 应付金额
    int should_pay_price = Integer.parseInt(orderCourseMap.get("total_real_price").toString())
        - Integer.parseInt(orderCourseMap.get("have_paid_price").toString());
    /**
     * modify by athrun.cw 2016年4月12日17:45:48 续约合同中，判断依据是total_final_price
     */
    if (OrderStatusConstant.ORDER_ORIGINAL_TYPE_RENEWAL
        .equals(orderCourseMap.get("order_original_type").toString())) {
      should_pay_price = Integer.parseInt(orderCourseMap.get("total_final_price").toString())
          - Integer.parseInt(orderCourseMap.get("have_paid_price").toString());
    }

    List<Map<String, Object>> orderCourseSplitMapList = (List<Map<String, Object>>) paramMap
        .get("order_split_list");
    int total_split_pay_price = 0;
    for (Map<String, Object> splitMap : orderCourseSplitMapList) {
      // 拆分订单状态(1,0)(已支付1、未支付0)
      Object split_status = splitMap.get("split_status");
      /**
       * modify by athrun.cw 2016年3月8日18:02:34 拆分订单状态 (线上支付的状态0:未支付,1:已支付,
       * 非线上支付的状态2.已支付未确认3.已支付已确认,4:未申请,5:申请中,6:申请成功,7:申请失败)
       */
      if (OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_ONLINE.equals(split_status)
          || OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE.equals(split_status)
          || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY.equals(split_status)
          || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING.equals(split_status)
          || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL.equals(split_status)) {
        // 未支付状态的
        total_split_pay_price += Integer.parseInt(splitMap.get("split_price").toString());
      }
    }
    if (should_pay_price != total_split_pay_price) {
      logger.error("数据错误，拆分合同失败，拆分订单id：" + orderId + "的金额错误！");
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "拆分合同失败，金额错误！");
      return orderCourseSplitMap;
    }

    /**
     * 通过了验证，将删除之前的拆分订单信息，重新插入新的拆分订单
     */
    // modified by alex.yang 2016年1月16日15:05:22
    /*
     * 如果合同状态是 “支付中：4”，那么无论拆多少次订单，合同主表的状态都是“支付中：4”。
     * 否则是其他状态，那么拆订单提交后，合同主表的状态都是“已发送：2”，需要用户重新发送 -cyndi 2016年1月12日16:15:48
     * 合同状态（1.已拟定,2.已发送,3.已确认,4.支付中,5.已支付,6.已过期,7.已终止）
     */
    if (!OrderStatusConstant.ORDER_STATUS_PAYING.equals(order_status)) {
      // modified by ivan.mgh，2016年5月24日10:40:52
      adminOrderCourseService.updateOrderStatus(orderId,
          OrderStatusConstant.ORDER_STATUS_HAVE_SENT);
    }

    // 2.删除所有的未支付的订单拆分表
    adminOrderCourseSplitDao.deleteForRealOrderCourseSplit(orderId);

    // 3.插入所有新的 未支付的订单拆分表
    for (Map<String, Object> splitMap : orderCourseSplitMapList) {
      // 拆分订单状态
      Object split_status = splitMap.get("split_status");
      /**
       * modify by athrun.cw 2016年3月8日18:04:17 拆分订单状态 (线上支付的状态0:未支付,1:已支付,
       * 非线上支付的状态2.已支付未确认3.已支付已确认,4:未申请,5:申请中,6:申请成功,7:申请失败) modified by komi
       * 2017年3月30日14:36:00 百度只有未申请和申请失败时可编辑订单
       */
      if (OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_ONLINE.equals(split_status)
          || OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE.equals(split_status)
          || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY.equals(split_status)
          || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL.equals(split_status)) {
        // 支付状态的
        Map<String, Object> insertSplitMap = new HashMap<String, Object>();
        insertSplitMap.put("order_id", orderId);
        insertSplitMap.put("split_price", splitMap.get("split_price"));
        insertSplitMap.put("split_status", split_status);
        insertSplitMap.put("pay_bank", splitMap.get("pay_bank"));

        /**
         * modify by athrun.cw 2016年3月8日16:47:38
         * 
         * 新版拆分合同中，
         * 新增了字段：pay_type，pay_success_sequence，pay_cc_name，pay_center_name，
         * pay_city_name
         */
        insertSplitMap.put("split_pay_type", splitMap.get("split_pay_type"));
        insertSplitMap.put("pay_success_sequence", splitMap.get("pay_success_sequence"));
        insertSplitMap.put("pay_cc_name", splitMap.get("pay_cc_name"));
        insertSplitMap.put("pay_center_name", splitMap.get("pay_center_name"));
        insertSplitMap.put("pay_city_name", splitMap.get("pay_city_name"));

        adminOrderCourseSplitDao.insert(insertSplitMap);
      }
    }
    orderCourseSplitMap.put("code", "200");
    orderCourseSplitMap.put("msg", "拆分订单成功！");
    return orderCourseSplitMap;
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return adminOrderCourseSplitDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminOrderCourseSplitDao.findList(paramMap, columnName);
  }

  /**
   * Title: 保存拆分的合同（CRM专用）<br>
   * Description: crmSaveOrderCourseSplit<br>
   * CreateDate: 2016年4月28日 下午2:00:43<br>
   * 
   * @category 保存拆分的合同（CRM专用）
   * @author ivan.mgh
   * @param paramMap
   * @param leadId
   * @see OrderCourseSplitService#saveOrderCourseSplit(Map)
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public Map<String, Object> crmSaveOrderCourseSplit(Map<String, Object> paramMap, String leadId)
      throws Exception {

    Map<String, Object> map = saveOrderCourseSplit(paramMap);

    // String orderId = paramMap.get("order_id").toString();

    // 同步合同状态
    // adminOrderCourseService.sendSynchronizeContractStatusMessage(orderId);

    // 清除缓存
    MemcachedUtil.deleteValue("CRM_AUTH_" + leadId);

    return map;
  }

  /**
   * Title: 查询未支付的订单及其所属合同的课程包名字<br>
   * Description: 查询未支付的订单及其所属合同的课程包名字(分期查询使用，因为和正常的状态码不一样)<br>
   * CreateDate: 2017年1月19日 上午11:01:35<br>
   * 
   * @category 查询未支付的订单及其所属合同的课程包名字
   * @author komi.zsy
   * @param splitOrderId
   *          订单id
   * @return
   * @throws Exception
   */
  public OrderAndOrderSplitParam findOneAndOrderInfoByKeyId(String splitOrderId) throws Exception {
    return orderCourseSplitEntityDao.findOneAndOrderInfoByKeyId(splitOrderId);
  }
}