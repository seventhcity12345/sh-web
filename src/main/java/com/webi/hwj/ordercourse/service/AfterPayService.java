package com.webi.hwj.ordercourse.service;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.constant.QueueTagConstant;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseOptionDao;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseSplitDao;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.dao.OrderCourseOptionDao;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseOptionEntityDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.user.dao.UserDao;

/**
 * Title: 支付成功通用逻辑<br>
 * Description: AfterPayService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年6月20日 下午7:55:53
 * 
 * @author yangmh
 */
@Service
public class AfterPayService {
  private static Logger logger = Logger.getLogger(AfterPayService.class);

  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  AdminOrderCourseSplitDao adminOrderCourseSplitDao;

  @Resource
  UserDao userDao;

  @Resource
  private OrderCourseOptionDao orderCourseOptionDao;

  @Resource
  private SubscribeCourseDao subscribeCourseDao;

  @Resource
  private AdminOrderCourseService adminOrderCourseService;

  @Resource
  private AdminOrderCourseOptionEntityDao adminOrderCourseOptionEntityDao;
  @Resource
  private AdminOrderCourseOptionDao adminOrderCourseOptionDao;

  /**
   * Title: 支付成功正常业务逻辑（带事务）<br>
   * Description: 注意：此处需要有事务传播级别为REQUIRES_NEW，因为需要这里出异常之后，把调用者的log插入数据库。<br>
   * CreateDate: 2016年1月13日 下午8:52:41<br>
   * 
   * @category afterPaySuccessLogicCommon
   * @author alex.yang
   * @param orderSplitId
   * @param money
   * @param payFrom
   * @param paramMap
   * @param orderCourseSplitMap
   * @param orderCourseMap
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = { Exception.class })
  public void afterPaySuccessLogicCommon(String orderSplitId, String money, String payFrom,
      Map<String, Object> orderCourseSplitMap, Map<String, Object> orderCourseMap)
      throws Exception {

    // 如果合同的状态为已支付，已过期，已终止
    if (!StringUtils.isEmpty(orderCourseMap.get("order_status"))
        && (OrderStatusConstant.ORDER_STATUS_HAVE_PAID.equals(orderCourseMap.get("order_status"))
            || OrderStatusConstant.ORDER_STATUS_HAVE_EXPIRED
                .equals(orderCourseMap.get("order_status"))
            || OrderStatusConstant.ORDER_STATUS_HAVE_TERMINATED
                .equals(orderCourseMap.get("order_status")))) {
      logger
          .error("afterPaySuccessLogicCommon======>合同状态不对!!!" + orderCourseMap.get("order_status"));
      // 报警
      SmsUtil.send("@|$~合同支付逻辑报警," + MemcachedUtil.getConfigValue("env") + ",orderSplitId="
          + orderSplitId + "~$|2|"
          + MemcachedUtil.getConfigValue("project_leader_phones") + "|1000|@");
      return;
    }

    // 合同状态下面还要根据逻辑赋值，这里要清空原合同状态
    orderCourseMap.remove("order_status");

    // 合同状态
    String splitStatus = orderCourseSplitMap.get("split_status").toString();
    /**
     * modify by athrun.cw 2016年3月8日18:27:27
     * 
     * 拆分订单状态(线上支付的状态0:未支付,1:已支付,非线上支付的状态2.已支付未确认3.已支付已确认
     * ,4:未申请,5:申请中,6:申请成功,7:申请失败)
     * 
     * 需要兼容财务确认这种状况 modified by komi 2017年3月30日14:52:23 百度分期只要没有成功都可以进入支付流程
     */
    if (OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_ONLINE.equals(splitStatus)
        || OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE.equals(splitStatus)
        || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY.equals(splitStatus)
        || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING.equals(splitStatus)
        || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL.equals(splitStatus)) {
      // 校验无错，走正常逻辑

      // 1.设置合同主表里的have_paid_price，需要累加
      orderCourseMap.put("have_paid_price", Integer.valueOf(money)
          + Integer.valueOf(orderCourseMap.get("have_paid_price").toString()));

      /**
       * 财务确认支付中，已经更新了 split_status 3
       * 
       */
      // 2.设置合同split表里的split_status为1(或者3)：已支付,update_date也需要设置
      if (OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_ONLINE
          .equals(orderCourseSplitMap.get("split_status").toString())) {
        logger.info("此次支付是线上支付，原来的订单状态是split_status：" + splitStatus);
        orderCourseSplitMap.put("split_status",
            OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_PAID_ONLINE);
      } else if (OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE
          .equals(orderCourseSplitMap.get("split_status").toString())) {
        logger.info("此次支付是非线上支付，原来的订单状态是split_status：" + splitStatus);
        orderCourseSplitMap.put("split_status",
            OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_PAID_OFFLINE);
      } else if (OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY.equals(splitStatus)
          || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING.equals(splitStatus)
          || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL.equals(splitStatus)) {
        logger.info("此次支付是百度分期支付，原来的订单状态是split_status：" + splitStatus);
        orderCourseSplitMap.put("split_status",
            OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS);
      } else {
        logger.error("订单状态不对！！！！！！");
        throw new RuntimeException("订单状态不对！！！！！！");
      }

      // 3.需要设置split表里的pay_from
      orderCourseSplitMap.put("pay_from", payFrom);
      orderCourseSplitMap.put("update_date", new Date());
      // orderCourseSplitMap.put("update_user_id",
      // paramMap.get("admin_user_id"));
      adminOrderCourseSplitDao.update(orderCourseSplitMap);

      // 5.合同主表的order_status 设置成 5:已支付，并且t_user表的is_student设置成1
      // 需要判断是否全部支付完毕(比对合同主表中的have_paid_price和price 相同的时候)
      // 如果全部支付完毕，需要将订单主表状态改成:5.已支付,且将支付时间设置为当前时间,并生产初始化tmm消息队列（消息队列部分alex来）
      // 如果没有全部支付完毕，则需要将订单主表状态设置为：4.支付中。

      Map<String, Object> orderSplitParamMap = new HashMap<String, Object>();
      orderSplitParamMap.put("order_id", orderCourseSplitMap.get("order_id"));

      // 查询出当前合同中所有的订单集合
      List<Map<String, Object>> courseOrderSplitList = adminOrderCourseSplitDao
          .findList(orderSplitParamMap, "key_id,split_status,split_price");
      if (courseOrderSplitList != null && courseOrderSplitList.size() > 0) {
        boolean isAllPaidFlag = true;
        // 真实支付过的总价（累加）
        int toalPrice = 0;

        // 第一次判断:遍历split表做判断
        int i = 1;
        for (Map<String, Object> courseOrderSplitMap : courseOrderSplitList) {
          String split_status = courseOrderSplitMap.get("split_status").toString();

          logger.info("本次（第" + i + "）次，支付split订单id：[" + courseOrderSplitMap.get("key_id")
              + "] 该订单一共支付了split_price——————————————————>：" + courseOrderSplitMap.get("split_price")
              + "状态split_status ：" + split_status);

          logger.info("本次（第" + i + "）次累计toalPrice支付了——————————————————>：" + toalPrice);
          // 未支付
          /**
           * modify by athryun.cw 2016年3月8日18:47:05
           * 
           * 拆分订单状态(线上支付的状态0:未支付,1:已支付,非线上支付的状态2.已支付未确认3.已支付已确认,4:未申请,5:申请中,6:申请成功,7:申请失败)
           */
          if (OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_ONLINE.equals(split_status)
              || OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE.equals(split_status)
              || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY.equals(split_status)
              || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING.equals(split_status)
              || OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL.equals(split_status)) {
            isAllPaidFlag = false;
            break;
          }

          toalPrice += Integer.valueOf(courseOrderSplitMap.get("split_price").toString());
          logger.info("本次（第" + i + "）次累计toalPrice支付了——————————————————>：" + toalPrice);

          i++;
        }

        logger.info("订单支付后，该订单一共支付了——————————————————>：" + toalPrice);

        // 第二次判断：双保险,已经全部支付
        /**
         * modify by athrun.cw 2016年4月1日15:47:23 续约
         */
        boolean ifPriceOk = true;
        String order_original_type = orderCourseMap.get("order_original_type").toString();

        if (toalPrice != Integer.valueOf(orderCourseMap.get("total_final_price").toString())
            .intValue()) {
          ifPriceOk = false;
        }

        logger.info("开始判断是否全部支付...");
        /**
         * modify by athrun.cw 2016年4月1日15:31:04
         * 
         * 续约合同，需要判断的字段是
         */
        if (isAllPaidFlag && ifPriceOk) {
          logger.debug("全部支付成功...orderCourseMap=" + orderCourseMap);

          // 把t_order_course里的order_status设置成已支付
          // orderCourseMap.put("order_status",
          // OrderStatusConstant.ORDER_STATUS_HAVE_PAID);
          orderCourseMap.put("have_paid_price", toalPrice);
          Date now_date = new Date();

          /**
           * modify by athrun.cw 2016年3月9日15:38:16
           * 
           * 生效日期：支付完成的时间即生效日期 同时 这时候 也要更新合同截止日期了，所以必须要limit_show_time
           */
          int limit_show_time = Integer.parseInt(orderCourseMap.get("limit_show_time").toString());
          int limit_show_time_unit = Integer
              .parseInt(orderCourseMap.get("limit_show_time_unit").toString());

          // 正常合同的时间
          orderCourseMap.put("start_order_time", now_date);
          orderCourseMap.put("end_order_time", now_date);

          /**
           * modify by athrun.cw 2016年4月6日13:49:33 续约支付成功
           */
          if (OrderStatusConstant.ORDER_ORIGINAL_TYPE_RENEWAL.equals(order_original_type)) {
            logger.info("1.1该合同是续约合同支付成功，合同id: " + orderCourseMap.get("key_id")
                + "，开始初始化源合同的预约数据为新合同相关联...");
            /**
             * 1.将与合同的所有预约数据记录， 更新为新合同的order_id和option_id
             */
            String[] fromPaths = orderCourseMap.get("from_path").toString().split(",");
            // 源合同id
            String resourceOrderId = fromPaths[fromPaths.length - 2];

            // 1.1 找到新的 续约合同的option数据 和 源合同的option数据并叠加
            List<OrderCourseOption> optionsMergeList = insertRenewalOrderCourseOption(
                orderCourseMap.get("key_id").toString(), resourceOrderId);

            // 1.2 找到 源合同的subscribe数据
            Map<String, Object> findSubecribeCourseMap = new HashMap<String, Object>();
            findSubecribeCourseMap.put("order_id", resourceOrderId);
            List<Map<String, Object>> resourceSubecribeCourseMapList = subscribeCourseDao
                .findList(findSubecribeCourseMap, "key_id, course_type");

            // 1.3 将subscribe中的order_id和option_id 更改为新的order_id 和 option_id
            if (resourceSubecribeCourseMapList != null
                && resourceSubecribeCourseMapList.size() > 0) {
              for (Map<String, Object> resourceSubecribeCourseMap : resourceSubecribeCourseMapList) {
                for (OrderCourseOption orderCourseOptionObj : optionsMergeList) {
                  if (resourceSubecribeCourseMap.get("course_type")
                      .equals(orderCourseOptionObj.getCourseType())) {
                    Map<String, Object> updateSubecribeCourseMap = new HashMap<String, Object>();
                    updateSubecribeCourseMap.put("key_id",
                        resourceSubecribeCourseMap.get("key_id"));
                    updateSubecribeCourseMap.put("order_id", orderCourseMap.get("key_id"));
                    updateSubecribeCourseMap.put("order_option_id",
                        orderCourseOptionObj.getKeyId());
                    subscribeCourseDao.update(updateSubecribeCourseMap);
                  }
                }
              }
            }

            logger
                .info("1.2该合同是续约合同支付成功，合同id: " + orderCourseMap.get("key_id") + "，开始将源合同置为已终止...");

            // 1.4 将原合同 置为
            // modified by ivan.mgh,2016年5月24日10:55:01
            adminOrderCourseService.updateOrderStatus(resourceOrderId,
                OrderStatusConstant.ORDER_STATUS_HAVE_TERMINATED);

            logger
                .info("1.3该合同是续约合同支付成功，合同id: " + orderCourseMap.get("key_id") + "，开始找源合同的开始时间...");
            Map<String, Object> resourceOrderCourseMap = orderCourseDao
                .findOneByKeyId(resourceOrderId, "start_order_time,end_order_time");

            // 将原来的开始时间 作为新续约合同的开始时间
            logger.info("1.3.1续约的合同id: " + orderCourseMap.get("key_id") + "的开始时间就是源合同的开始时间");
            orderCourseMap.put("start_order_time", resourceOrderCourseMap.get("start_order_time"));
            orderCourseMap.put("end_order_time", resourceOrderCourseMap.get("end_order_time"));
          }

          logger.info("2.1该合同支付成功，合同id: " + orderCourseMap.get("key_id") + "，开始更新订单的支付状态...");

          // 合同开课时间+时效
          Calendar c = Calendar.getInstance();
          c.setTime((Date) orderCourseMap.get("end_order_time"));

          /**
           * modified by komi 2016年8月31日16:34:06 合同有效时间，要根据合同时效单位来加
           */
          if (limit_show_time_unit == 0) {
            // 月
            c.add(Calendar.MONTH, limit_show_time);
          } else if (limit_show_time_unit == 1) {
            // 天
            c.add(Calendar.DATE, limit_show_time);
          } else {
            logger.error("合同时效单位不对！！！！！！");
            throw new RuntimeException("合同时效单位不对！！！！！！");
          }

          // modified by ivan.mgh，2016年5月18日19:17:22
          // ---------------start-----------------
          Integer giftTime = (Integer) orderCourseMap.get("gift_time");
          if (null != giftTime) {
            c.add(Calendar.MONTH, giftTime);
          }
          // ---------------end-----------------
          // 合同结束时间
          orderCourseMap.put("end_order_time", c.getTime());

          // 把t_user表中的is_student字段给弄成1
          logger.info("开始将该用户更新为学员...");
          Map<String, Object> updateUserMap = new HashMap<String, Object>();
          updateUserMap.put("is_student", "1");
          updateUserMap.put("key_id", orderCourseMap.get("user_id"));
          userDao.update(updateUserMap);

          // modified by ivan.mgh,2016年5月24日10:56:28
          adminOrderCourseService.updateOrderStatus(orderCourseMap.get("key_id").toString(),
              OrderStatusConstant.ORDER_STATUS_HAVE_PAID);

          /**
           * modified by komi 2017年1月10日17:26:08 生产消息，初始化rsa账号
           */
          OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue(
              ConfigConstant.ALIYUN_ONS_PRODUCERID),
              MemcachedUtil.getConfigValue(ConfigConstant.ALIYUN_ONS_TOPICID),
              ConfigConstant.CID_SPEAKHI_PUBLIC_ + MemcachedUtil.getConfigValue("env")
                  .toUpperCase(),
              QueueTagConstant.CREATE_RSA_ACCOUNT + "," + orderCourseMap.get("user_id"), 60 * 3 * 1000);
        } else {
          logger.info("没有全部支付成功...");
          // 支付中
          adminOrderCourseService.updateOrderStatus(orderCourseMap.get("key_id").toString(),
              OrderStatusConstant.ORDER_STATUS_PAYING);
        }

      } else {
        throw new Exception("split数据不正确，应该至少有一条split数据");
      }

      // 更新主表数据
      orderCourseDao.update(orderCourseMap);
    }
  }

  /**
   * Title: 续约逻辑(option叠加)<br>
   * Description: 续约逻辑(option叠加)<br>
   * CreateDate: 2017年1月23日 上午11:44:15<br>
   * 
   * @category 续约逻辑(option叠加)
   * @author komi.zsy
   * @param newOrderId
   *          续约合同id
   * @param resourceOrderId
   *          源合同id
   * @param createUserId
   *          更新人id
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = { Exception.class })
  public List<OrderCourseOption> insertRenewalOrderCourseOption(String newOrderId,
      String resourceOrderId) throws Exception {
    // 遍历续约合同的课时数，行列转换
    Map<String, OrderCourseOption> optionsMergeMap = new HashMap<String, OrderCourseOption>();
    // 续约的新合同的课时数
    List<OrderCourseOption> newOrderCourseOptionList = adminOrderCourseOptionEntityDao.findListAll(
        newOrderId);
    String createUserId = "";
    if (newOrderCourseOptionList != null && newOrderCourseOptionList.size() != 0) {
      createUserId = newOrderCourseOptionList.get(0).getCreateUserId();
      // 要删除的续约合同的课时数的keyid
      List<String> newOrderKeyIds = new ArrayList<String>();
      for (OrderCourseOption orderCourseOptionObj : newOrderCourseOptionList) {
        newOrderKeyIds.add(orderCourseOptionObj.getKeyId());
        orderCourseOptionObj.setKeyId(null);
        optionsMergeMap.put(orderCourseOptionObj.getCourseType()
            + "," + orderCourseOptionObj.getCourseUnitType()
            + "," + orderCourseOptionObj.getIsGift(), orderCourseOptionObj);
      }
      // 删除之前显示的续约的合同的课时数（为了和源合同课时数整合后重新插入）
      adminOrderCourseOptionDao.deleteOrderCourseOptionByRenew(createUserId, newOrderKeyIds);
    }

    // 为了给新续约合同中合同子表的计算剩余课时数做准备而查询的源合同子表项集合
    // 源合同option子表数据
    List<OrderCourseOption> resourceOrderCourseOptionList = adminOrderCourseOptionEntityDao
        .findListAll(resourceOrderId);

    if (resourceOrderCourseOptionList != null && resourceOrderCourseOptionList.size() != 0) {
      /**
       * 需求486：新续约合同的总课时course_count=新续约合同的课时数course_count + 源合同剩余的课时（
       * remain_course_count）
       */
      for (OrderCourseOption orderCourseOptionTemp : resourceOrderCourseOptionList) {
        if (optionsMergeMap.get(orderCourseOptionTemp.getCourseType()
            + "," + orderCourseOptionTemp.getCourseUnitType()
            + "," + orderCourseOptionTemp.getIsGift()) == null) {
          // 如果续约合同没有源合同的课程类型，则放入源合同课程课时数
          orderCourseOptionTemp.setKeyId(null);
          orderCourseOptionTemp.setOrderId(newOrderId);
          orderCourseOptionTemp.setUpdateUserId(createUserId);
          orderCourseOptionTemp.setCreateUserId(createUserId);
          optionsMergeMap.put(orderCourseOptionTemp.getCourseType()
              + "," + orderCourseOptionTemp.getCourseUnitType()
              + "," + orderCourseOptionTemp.getIsGift(), orderCourseOptionTemp);
        } else {
          OrderCourseOption optionsMergeObj = optionsMergeMap.get(orderCourseOptionTemp
              .getCourseType()
              + "," + orderCourseOptionTemp.getCourseUnitType()
              + "," + orderCourseOptionTemp.getIsGift());
          // 如果续约合同有和源合同同样的课程类型，则累加源合同剩余课时数
          optionsMergeObj.setShowCourseCount(optionsMergeObj.getShowCourseCount()
              + orderCourseOptionTemp.getRemainCourseCount());
          optionsMergeObj.setRemainCourseCount(optionsMergeObj.getShowCourseCount());
        }
      }
    }

    // 批量插入
    List<OrderCourseOption> optionsMergeList = new ArrayList<OrderCourseOption>(optionsMergeMap
        .values());
    adminOrderCourseOptionEntityDao.batchInsert(optionsMergeList);
    return optionsMergeList;
  }
}