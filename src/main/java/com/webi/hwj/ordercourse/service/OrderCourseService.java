package com.webi.hwj.ordercourse.service;

import java.util.ArrayList;
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

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.admin.dao.BadminUserDao;
import com.webi.hwj.admin.entity.BadminUser;
import com.webi.hwj.alipay.dao.PayLogDao;
import com.webi.hwj.constant.BadminConstant;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.constant.SmsConstant;
import com.webi.hwj.coursepackage.dao.CoursePackageEntityDao;
import com.webi.hwj.coursepackage.dao.CoursePackageOptionEntityDao;
import com.webi.hwj.coursepackage.param.CoursePackageAndPriceParam;
import com.webi.hwj.coursepackage.param.CoursePackageOptionAndPriceParam;
import com.webi.hwj.coursepackagepriceoption.dao.CoursePackagePriceOptionDao;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.index.param.UserRegisterParam;
import com.webi.hwj.index.service.IndexService;
import com.webi.hwj.kuaiqian.dao.PayOrderCourseDao;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseSplitDao;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.dao.OrderCourseOptionDao;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.entitydao.OrderCourseEntityDao;
import com.webi.hwj.ordercourse.entitydao.OrderCourseOptionEntityDao;
import com.webi.hwj.ordercourse.param.OrderCourseDetailOptionParam;
import com.webi.hwj.ordercourse.param.OrderCourseDetailParam;
import com.webi.hwj.ordercourse.param.SaveOrderCourseParam;
import com.webi.hwj.redeemcode.dao.RedeemCodeDao;
import com.webi.hwj.redeemcode.entity.RedeemCode;
import com.webi.hwj.redeemcode.service.RedeemCodeService;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.dao.UserInfoEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;
import com.webi.hwj.user.param.UserInfoForOrderDetailParam;

@Service
public class OrderCourseService {
  private static Logger logger = Logger.getLogger(OrderCourseService.class);

  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  AdminOrderCourseSplitDao adminOrderCourseSplitDao;

  @Resource
  UserDao userDao;

  @Resource
  PayOrderCourseDao payOrderCourseDao;

  @Resource
  private PayLogDao payLogDao;

  @Resource
  private OrderCourseOptionDao orderCourseOptionDao;

  @Resource
  private SubscribeCourseDao subscribeCourseDao;

  @Resource
  private OrderCourseEntityDao orderCourseEntityDao;

  @Resource
  private AdminOrderCourseService adminOrderCourseService;

  @Resource
  AfterPayService afterPayService;

  @Resource
  RedeemCodeDao redeemcodeDao;
  @Resource
  RedeemCodeService redeemcodeService;
  @Resource
  CoursePackageOptionEntityDao coursePackageOptionEntityDao;
  @Resource
  OrderCourseSplitService orderCourseSplitService;
  @Resource
  CoursePackagePriceOptionDao coursePackagePriceOptionDao;
  @Resource
  AdminOrderCourseSaveService adminOrderCourseSaveService;
  @Resource
  private AdminUserEntityDao adminUserEntityDao;
  @Resource
  private IndexService indexService;
  @Resource
  private CoursePackageEntityDao coursePackageEntityDao;

  @Resource
  UserInfoEntityDao userInfoEntityDao;
  @Resource
  BadminUserDao badminUserDao;
  @Resource
  OrderCourseOptionEntityDao orderCourseOptionEntityDao;

  @Resource
  UserEntityDao userEntityDao;

  /**
   * Title: crm线下拟定合同<br>
   * Description: crm线下拟定合同<br>
   * CreateDate: 2017年3月21日 上午11:15:14<br>
   * 
   * @category crm线下拟定合同
   * @author komi.zsy
   * @param saveOrderCourseParam
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public CommonJsonObject insertContractWithCrm(
      SaveOrderCourseParam saveOrderCourseParam)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 判断手机号是否已存在
    String phone = saveOrderCourseParam.getUserPhone();
    User returnUser = adminUserEntityDao.findOneByPhone(phone);
    // 用户id
    String userId = null;
    if (returnUser == null) {
      // 手机号不存在，需要先注册该学员
      UserRegisterParam userRegisterParam = new UserRegisterParam();
      userRegisterParam.setPhone(phone);
      // 生成6位随机数密码
      String random6BitNumber = String.valueOf((Math.random() * 9 + 1) * 100000).substring(0, 6);
      logger.info("随机生成的6位有效密码是：" + random6BitNumber);
      userRegisterParam.setPwd(SHAUtil.encode(random6BitNumber));
      userRegisterParam.setCreateUserId(saveOrderCourseParam.getCreateUserId());
      // 保存用户信息
      Map<String, Object> insertUser = indexService.saveUser(userRegisterParam);
      // 2.发送给学员短信，提示账号密码
      SmsUtil.sendSmsToQueue(phone,
          SmsConstant.PREFIX_SPEAKHI + random6BitNumber + SmsConstant.REGISTER_NEW_USER);

      userId = insertUser.get("key_id").toString();

    } else {
      // 用户已存在
      userId = returnUser.getKeyId();
      // 检查是否已有合同，已有合同则不能拟定crm新合同
      int num = adminOrderCourseService.findNotOrderCourseByUserId(userId);
      if (num > 0) {
        // 已有合同，不能拟定
        json.setCode(ErrorCodeEnum.ORDER_IS_EXIST.getCode());
        return json;
      }
    }

    // 开始拟定合同
    String coursePackagePriceOptionId = saveOrderCourseParam.getCoursePackagePriceOptionId();
    saveOrderCourseParam.setUserId(userId);
    saveOrderCourseParam.setIsCrm(true);
    saveOrderCourseParam.setCreateUserId(MemcachedUtil.getConfigValue("crm_admin_account"));

    // 获取课程包信息及其价格
    SaveOrderCourseParam coursePackageInfo =
        coursePackageEntityDao.findCoursePackageByCoursePackagePriceOptionId(
            coursePackagePriceOptionId);

    saveOrderCourseParam.setLimitShowTime(coursePackageInfo.getLimitShowTime());
    saveOrderCourseParam.setLimitShowTimeUnit(coursePackageInfo.getLimitShowTimeUnit());
    saveOrderCourseParam.setCategoryType(coursePackageInfo.getCategoryType());
    saveOrderCourseParam.setCoursePackageId(coursePackageInfo.getCoursePackageId());
    saveOrderCourseParam.setCoursePackageName(coursePackageInfo.getCoursePackageName());
    saveOrderCourseParam.setCoursePackageType(coursePackageInfo.getCoursePackageType());
    saveOrderCourseParam.setTotalShowPrice(coursePackageInfo.getTotalShowPrice());
    // crm的id
    saveOrderCourseParam.setCreateUserId(MemcachedUtil.getConfigValue(
        ConfigConstant.CRM_ADMIN_ACCOUNT));

    // 获取课程包子表数据
    List<CoursePackageOptionAndPriceParam> optionAndPirceList =
        coursePackageOptionEntityDao.findOptionAndPirceList(
            coursePackageInfo.getCoursePackageId(), coursePackageInfo.getCoursePriceVersion());
    List<OrderCourseOption> normalOptionsMergeList = new ArrayList<OrderCourseOption>();
    if (optionAndPirceList != null && optionAndPirceList.size() != 0) {
      for (CoursePackageOptionAndPriceParam optionAndPirce : optionAndPirceList) {
        OrderCourseOption orderCourseOption = new OrderCourseOption();
        orderCourseOption.setCourseCount(optionAndPirce.getCourseCount());
        orderCourseOption.setShowCourseCount(orderCourseOption.getCourseCount());
        orderCourseOption.setRemainCourseCount(orderCourseOption.getCourseCount());
        orderCourseOption.setCourseType(optionAndPirce.getCourseType());
        orderCourseOption.setCourseUnitType(optionAndPirce.getCourseUnitType());
        orderCourseOption.setRealPrice(optionAndPirce.getCoursePriceUnitPrice());
        normalOptionsMergeList.add(orderCourseOption);
      }
    }
    // 赠送课程数据（CRM没有赠送课程功能）
    List<OrderCourseOption> giftOptionsMergeList = new ArrayList<OrderCourseOption>();

    String createUserId = saveOrderCourseParam.getCreateUserId();
    // 拟定合同
    String orderId = adminOrderCourseSaveService.saveOrderCourseAndOption(saveOrderCourseParam,
        createUserId, createUserId, giftOptionsMergeList,
        normalOptionsMergeList);
    // 拆分订单并确认
    adminOrderCourseSaveService.confirmSuccessPayByAuto(orderId, createUserId, "CRM线下",
        saveOrderCourseParam.getTotalRealPrice());

    json.setData(orderId);

    return json;
  }

  /**
   * Title: 找到未付款 && CC已发送状态 的订单 Description: findOrdersByUserIdAndStatus<br>
   * CreateDate: 2015年8月14日 上午10:18:25<br>
   * 
   * @category findOrdersByUserIdAndStatus
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<OrderCourse> findOrdersByUserId(String userId)
      throws Exception {
    logger.debug("开始找未付款 && CC已发送状态 的订单");
    return orderCourseEntityDao.findOrdersByUserId(userId);
  }

  /**
   * @category orderCourse 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return orderCourseDao.insert(fields);
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
    return orderCourseDao.findList(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @return
   * @throws Exception
   */
  public Page findPage(Map<String, Object> paramMap) throws Exception {
    return orderCourseDao.findPage(paramMap);
  }

  /**
   * @category 修改数据
   * @author mingyisoft代码生成工具
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return orderCourseDao.update(fields);
  }

  /**
   * @category 查询单条数据(通过参数MAP)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(Map<String, Object> paramMap, String columnName)
      throws Exception {
    logger.debug("查询订单信息");
    return orderCourseDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return orderCourseDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return orderCourseDao.delete(ids);
  }

  /**
   * @category 查询数量
   * @author mingyisoft代码生成工具
   * @param sql
   * @param map
   * @return
   * @throws Exception
   */
  public int findCount(Map<String, Object> map) throws Exception {
    return orderCourseDao.findCount(map);
  }

  /**
   * @category 查询总数
   * @author mingyisoft代码生成工具
   * @param map
   * @param sumField
   *          sum的字段
   * @return
   * @throws Exception
   */
  public int findSum(Map<String, Object> map, String sumField) throws Exception {
    return orderCourseDao.findSum(map, sumField);
  }

  /**
   * @category 关闭用户未付款的订单
   * @throws Exception
   * @author vector.mjp
   */
  public void closeOrder() throws Exception {
    Map<String, Object> pms = new HashMap<String, Object>();
    pms.put("old_status", 3);
    pms.put("new_status", 9);

    orderCourseDao.closeOrder(pms);
  }

  /**
   * Title: 支付成功后的业务逻辑(通用,轻易不要改!!!)<br>
   * Description: afterPaySuccessLogic<br>
   * CreateDate: 2015年12月10日 下午4:26:01<br>
   * 
   * @category 支付成功后的业务逻辑<
   * @author yangmh
   * @param orderSplitId
   *          split表主键
   * @param money
   *          支付成功的价格
   * @param payFrom
   *          支付来源，例如支付宝or快钱
   * @param paramMap
   *          快钱需要使用的log里的参数(trade_status,deal_id,key_id),支付宝的参数为（trade_status,
   *          trade_no）
   * @throws Exception
   */
  // 支付失败插入消费日志方法不回滚
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE,rollbackFor={Exception.class})
  public void afterPaySuccessLogic(String orderSplitId, String money, String payFrom,
      Map<String, Object> paramMap) throws Exception {
    // 查询合同split数据
    Map<String, Object> orderCourseSplitMap = null;
    // 查询合同主表数据
    Map<String, Object> orderCourseMap = null;

    String logDesc = null;

    logger.info(
        "--------------------->afterPaySuccessLogic调用的参数：orderSplitId=" + orderSplitId + ", money="
            + money + ", payFrom=" + payFrom + ", paramMap" + paramMap);

    // 各种校验
    try {
      // 非空校验
      if (payFrom == null || "".equals(payFrom) || "null".equals(payFrom)) {
        throw new Exception("payFrom不能为空!");
      }

      orderCourseSplitMap = adminOrderCourseSplitDao.findOneByKeyId(orderSplitId,
          "key_id,order_id,split_price,split_status");
      logger.info("--------------------->合同拆分表orderCourseSplitMap = " + orderCourseSplitMap);
      if (orderCourseSplitMap == null) {
        throw new Exception("合同split数据不存在");
      }

      /**
       * modify by athrun.cw 2016年3月9日15:38:16
       * 
       * 生效日期：支付完成的时间即生效日期 同时 这时候 也要更新合同截止日期了，所以必须要limit_show_time &&
       * start_order_time
       */
      orderCourseMap = orderCourseDao.findOneByKeyId(orderCourseSplitMap.get("order_id"),
          "key_id, have_paid_price, total_real_price, user_id, user_name, limit_show_time,limit_show_time_unit, "
              + "total_final_price, order_original_type,from_path,gift_time,order_status ");
      logger.info("--------------------->合同表orderCourseMap = " + orderCourseMap);
      if (orderCourseMap == null) {
        throw new Exception("合同主表数据不存在");
      }

      // 除了线上支付以外的其他所有状态
      /**
       * modify by athrun.cw 2016年1月28日16:57:18 快钱返回的money单位是分
       * 
       */

      if (money == null || "".equals(money) || "null".equals(money)) {
        // money = orderCourseSplitMap.get("split_price").toString();
        money = orderCourseSplitMap.get("split_price").toString();
      }

      logger.info("--------------------->合同拆分表orderCourseSplitMap中 split_price= "
          + orderCourseSplitMap.get("split_price").toString()
          + ", 快钱返回的money= " + money);
      if (!orderCourseSplitMap.get("split_price").toString().equals(money)) {
        throw new Exception("价格不对!");
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("数据异常， error:" + e.getMessage(), e);
      logDesc = e.toString();
    }

    // 如果校验不出错，则走业务逻辑，两种情况都需要记录log
    // 抛异常之后也不回滚代码。
    if (logDesc == null) {
      try {
        afterPayService.afterPaySuccessLogicCommon(orderSplitId, money, payFrom,
            orderCourseSplitMap, orderCourseMap);
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("error:" + e.toString(), e);
        logDesc = e.toString();
      }
    }

    // 4.支付log表里存的order_id改成了split的key_id
    switch (payFrom) {
      case "快钱":
        // 更新日志记录表，将trade_status 更新为返回的 payResult
        logger.debug("开始更新快钱支付日志记录...");
        Map<String, Object> updateKuaiqianLogMap = new HashMap<String, Object>();
        updateKuaiqianLogMap.put("trade_status", paramMap.get("trade_status"));
        updateKuaiqianLogMap.put("deal_id", paramMap.get("deal_id"));
        updateKuaiqianLogMap.put("key_id", paramMap.get("key_id"));
        updateKuaiqianLogMap.put("pay_log_desc", logDesc);
        payOrderCourseDao.updateKuaiqianLogByKeyId(updateKuaiqianLogMap);
        break;
      case "支付宝":
        // 如果该订单没有被执行过，则更新订单支付状态
        logger.info("支付宝回执监听------>订单支付状态设置为付款成功");

        // 记录log
        Map<String, Object> logMap = new HashMap<String, Object>();
        logMap.put("trade_status", paramMap.get("trade_status"));
        logMap.put("trade_no", paramMap.get("trade_no"));
        logMap.put("order_id", orderSplitId);
        logMap.put("pay_log_desc", logDesc);
        logMap.put("money", money);

        if (orderCourseMap.get("key_id") != null) {
          logMap.put("user_id", orderCourseMap.get("user_id"));
          logMap.put("user_name", orderCourseMap.get("user_name"));
        }

        logMap.put("pay_type", "支付宝");
        payLogDao.insert(logMap);
        break;
    }
  }

  /**
   * Title: 百度/招联同步状态的业务逻辑<br>
   * Description: 百度/招联同步状态的业务逻辑<br>
   * CreateDate: 2015年12月10日 下午4:26:01<br>
   * 
   * @category 百度/招联同步状态的业务逻辑
   * @author komi.zsy
   * @param orderSplitId
   *          split表主键
   * @param payFrom
   *          支付来源
   * @param status
   *          订单状态
   * @param logDesc
   *          错误信息
   * @param money
   *          钱
   * @param tradeNo
   *          第三方id
   * @throws Exception
   */
  // 支付失败插入消费日志方法不回滚
  public void afterBaiduPaySuccessLogic(
      String orderSplitId, String payFrom, String status, String logDesc, String money,
      String tradeNo)
          throws Exception {
    // 查询合同split数据
    Map<String, Object> orderCourseSplitMap = null;
    // 查询合同主表数据
    Map<String, Object> orderCourseMap = null;

    // 各种校验
    try {
      // 非空校验
      if (StringUtils.isEmpty(payFrom)) {
        throw new Exception("payFrom不能为空!");
      }
      orderCourseSplitMap = adminOrderCourseSplitDao.findOneByKeyId(orderSplitId,
          "key_id,order_id,split_price,split_status");
      logger.info("--------------------->合同拆分表orderCourseSplitMap = " + orderCourseSplitMap);
      if (orderCourseSplitMap == null) {
        throw new Exception("合同split数据不存在");
      } else if (money != null && !(Integer.parseInt(orderCourseSplitMap.get("split_price")
          .toString()) == Double.parseDouble(money))) {
        // 这边比money，百度是没有回传的，招联是小数点后两位
        throw new Exception("价格不对!");
      }

      // 如果此笔订单已经是申请成功状态，则不需要再更新了
      if (OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS
          .equals(orderCourseSplitMap.get("split_status"))) {
        logger.info(payFrom + "订单：" + orderSplitId + "已经申请成功了，可能是重复推送");
        return;
      }

      /**
       * modify by athrun.cw 2016年3月9日15:38:16
       * 
       * 生效日期：支付完成的时间即生效日期 同时 这时候 也要更新合同截止日期了，所以必须要limit_show_time &&
       * start_order_time
       */
      orderCourseMap = orderCourseDao.findOneByKeyId(orderCourseSplitMap.get("order_id"),
          "key_id, have_paid_price, total_real_price, user_id, user_name, limit_show_time,limit_show_time_unit, "
              + "total_final_price, order_original_type,from_path,gift_time,order_status ");
      logger.info("--------------------->合同表orderCourseMap = " + orderCourseMap);
      if (orderCourseMap == null) {
        throw new Exception("合同主表数据不存在");
      }

      logger.info("--------------------->合同拆分表orderCourseSplitMap中 split_price= "
          + orderCourseSplitMap.get("split_price").toString());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("数据异常， error:" + e.getMessage(), e);
      logDesc = e.toString();
    }

    // 如果校验不出错，则走业务逻辑，两种情况都需要记录log
    // 抛异常之后也不回滚代码。
    if (logDesc == null) {
      try {
        // 百度分期状态为成功时才走业务逻辑，否则只更新一下订单状态
        if (OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS.equals(status)) {
          afterPayService.afterPaySuccessLogicCommon(orderSplitId,
              orderCourseSplitMap.get("split_price").toString(), payFrom,
              orderCourseSplitMap, orderCourseMap);
        } else {
          orderCourseSplitMap.put("pay_from", payFrom);
          orderCourseSplitMap.put("split_status", status);
          adminOrderCourseSplitDao.update(orderCourseSplitMap);
        }
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("error:" + e.toString(), e);
        logDesc = e.toString();
      }
    }

    // 4.支付log表里存的order_id改成了split的key_id
    logger.info(payFrom + "回执监听------>记录log");
    switch (payFrom) {
      case "招联分期": {
        // 记录log
        Map<String, Object> logMap = new HashMap<String, Object>();
        logMap.put("trade_no", orderSplitId);
        logMap.put("trade_status", status);
        logMap.put("order_id", orderSplitId);
        logMap.put("pay_log_desc", logDesc);

        if (orderCourseMap.get("key_id") != null) {
          logMap.put("user_id", orderCourseMap.get("user_id"));
          logMap.put("user_name", orderCourseMap.get("user_name"));
        }

        logMap.put("pay_type", payFrom);
        payLogDao.insert(logMap);
      }
        break;
      case "百度分期": {
        // 记录log
        Map<String, Object> logMap = new HashMap<String, Object>();
        logMap.put("trade_status", status);
        logMap.put("order_id", orderSplitId);
        logMap.put("pay_log_desc", logDesc);

        if (orderCourseMap.get("key_id") != null) {
          logMap.put("user_id", orderCourseMap.get("user_id"));
          logMap.put("user_name", orderCourseMap.get("user_name"));
        }

        logMap.put("pay_type", payFrom);
        payLogDao.insert(logMap);
      }
        break;
    }
  }

  /**
   * Title: 用户第一份合同起始日期是否在n天内<br>
   * Description: 用户第一份合同起始日期是否在n天内<br>
   * CreateDate: 2016年4月26日 下午1:57:05<br>
   * 
   * @category 用户第一份合同起始日期是否在n天内
   * @author komi.zsy
   * @param numDay
   *          在n天内
   * @param userId
   * @return
   * @throws Exception
   */
  public boolean findUserFirstOrderIsInNDay(int numDay, String userId) throws Exception {
    // 用户第一份合同起始日期是否在numDay天内，true是在numDay天内，false超过numDay天
    boolean isInN = true;

    OrderCourse orderCourse = orderCourseEntityDao.findFirstOrderByUserId(userId);
    if (orderCourse == null) {
      return true;
    } else {
      if (orderCourse.getStartOrderTime() == null) {
        return true;
      }
    }
    // 求出当前时间和第一份合同的起始日期的差额
    long intervalMilli = new Date().getTime() - orderCourse.getStartOrderTime().getTime();

    // 大于numDay天，则返回false
    if ((intervalMilli / (24 * 60 * 60 * 1000)) > numDay) {
      isInN = false;
    }

    logger.debug("用户第一份合同至今已过--->" + (intervalMilli / (24 * 60 * 60 * 1000)) + "天");

    return isInN;
  }

  /**
   * Title: 兑换码兑换合同<br>
   * Description: 兑换码兑换合同<br>
   * CreateDate: 2016年7月21日 上午9:48:59<br>
   * 
   * @category 兑换码兑换合同
   * @author komi.zsy
   * @param paramMap
   * @param updateUserId
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage saveOrderCourseAndOptionByRedeemCode(String redeemCode, String userPhone,
      String userName,
      Integer userFromType, String updateUserId) throws Exception {
    JsonMessage json = new JsonMessage();
    Date currentDate = new Date();

    // 校验是否可以使用兑换码
    json = redeemcodeService.checkRedeemCode(redeemCode, updateUserId, currentDate);
    if (!json.isSuccess()) {
      logger.info("兑换码校验失败！！！兑换码：" + redeemCode);
      return json;
    }

    RedeemCode redeemcodeObj = (RedeemCode) json.getData();

    // 课程包id
    String coursePackageId = redeemcodeObj.getCoursePackageId();
    // 价格政策id
    String coursePackagePriceId = redeemcodeObj.getCoursePackagePriceId();

    if (coursePackageId == null || coursePackagePriceId == null) {
      logger.info("兑换码校验失败！！！兑换码：" + redeemCode);
      json.setSuccess(false);
      json.setMsg("兑换码不属于本次活动，请检查兑换网址，谢谢");
      return json;
    }

    // 处理一键生产合同
    json = oneKeyCreateOrder(coursePackageId, coursePackagePriceId, redeemCode,
        userPhone, userName, userFromType, updateUserId);

    logger.info("兑换码兑换合同开始确认提交兑换码！！！！！！！");
    // 全部验证通过，正常情况下，开始提交兑换码
    redeemcodeService.updateRedeemCode(updateUserId, userPhone,
        userName, redeemcodeObj, 0, currentDate);
    json.setData(redeemcodeObj);

    return json;
  }

  /**
   * Title: 一键生产合同<br>
   * Description: 一键生产合同<br>
   * CreateDate: 2016年11月8日 下午2:24:10<br>
   * 
   * @category 一键生产合同
   * @author komi.zsy
   * @param coursePackageId
   *          课程包id
   * @param coursePackagePriceId
   *          价格策略id
   * @param redeemCode
   *          兑换码
   * @param userPhone
   *          学员手机号
   * @param userName
   *          学员名字
   * @param userFromType
   *          学生来源
   * @param updateUserId
   *          操作者id(这里只能是用户id)
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage oneKeyCreateOrder(
      String coursePackageId, String coursePackagePriceId,
      String redeemCode, String userPhone, String userName,
      Integer userFromType, String updateUserId) throws Exception {
    JsonMessage json = new JsonMessage();
    logger.info("兑换码兑换合同开始保存合同！！！！！！！");
    // 初始化参数，默认合同等级为3
    SaveOrderCourseParam saveOrderCourseParam = new SaveOrderCourseParam();

    /**
     * modify by felix.yl 根据当前学员的keyId,去查t_user表,查看该学员当前是否已经有LC;
     * 如果当前学员之前没有指定的LC,使用兑换码生成的合同,就默认LC为lc.lc(将默认LC的userId配置在了码表中,缓存获取);
     */
    User user = userEntityDao.findOneLcId(updateUserId);
    if (user != null) {
      String learningCoachId = user.getLearningCoachId();
      if (learningCoachId == null || "".equals(learningCoachId)) {
        String defaultLcId = MemcachedUtil.getConfigValue("redeemCode_contract_default_lc_user_id");
        saveOrderCourseParam.setLearningCoachId(defaultLcId);
      }
    }

    // 合同等级
    saveOrderCourseParam.setCurrentLevel("General Level 3");
    // 用户id
    saveOrderCourseParam.setUserId(updateUserId);
    // 用户中文名字
    saveOrderCourseParam.setUserName(userName);
    // 用户身份证号 paramMap.get("idcard") + ""
    // 332需求迭代，不需要身份证号
    saveOrderCourseParam.setIdcard(null);

    // 查询价格政策
    CoursePackageAndPriceParam coursePackageAndPriceParam = coursePackagePriceOptionDao
        .findOneByCoursePackageIdAndCoursePackagePriceId(coursePackagePriceId, coursePackageId);
    if (coursePackageAndPriceParam == null) {
      logger.error("兑换码兑换合同失败！！！没有价格政策！！！兑换码：" + redeemCode);
      json.setSuccess(false);
      json.setMsg("合同拟定失败！");
      return json;
    }

    // 学生来源
    saveOrderCourseParam.setUserFromType(userFromType);
    // 课程体系
    saveOrderCourseParam.setCategoryType(coursePackageAndPriceParam.getCategoryType());
    // 课程包id
    saveOrderCourseParam.setCoursePackageId(coursePackageAndPriceParam.getKeyId());
    // 课程包名字
    saveOrderCourseParam.setCoursePackageName(coursePackageAndPriceParam.getPackageName());
    // 时效性(只用于展示)
    saveOrderCourseParam.setLimitShowTime(coursePackageAndPriceParam.getLimitShowTime());
    // 时效性(单位)
    saveOrderCourseParam.setLimitShowTimeUnit(coursePackageAndPriceParam.getLimitShowTimeUnit());
    // 优惠价
    saveOrderCourseParam.setTotalRealPrice(coursePackageAndPriceParam.getCoursePackageRealPrice());
    // 支付价（这里等于优惠价）
    saveOrderCourseParam.setTotalFinalPrice(coursePackageAndPriceParam.getCoursePackageRealPrice());
    // 价格政策子表id
    saveOrderCourseParam
        .setCoursePackagePriceOptionId(coursePackageAndPriceParam.getCoursePackagePriceOptionId());
    // 课程包类型
    saveOrderCourseParam.setCoursePackageType(coursePackageAndPriceParam.getCoursePackageType());

    // 课程包中的所有课程列表
    List<CoursePackageOptionAndPriceParam> coursePackageOptionList = coursePackageOptionEntityDao
        .findCoursePackageList(coursePackageId, coursePackageAndPriceParam.getCoursePriceVersion());

    // 课程包和课程包子表都有数据
    if (coursePackageOptionList != null && coursePackageOptionList.size() != 0
        && coursePackageOptionList.get(0).getCourseType() != null) {
      // 课程包中的课程列表
      Map<String, OrderCourseOption> normalOptionsMap = new HashMap<String, OrderCourseOption>();

      for (CoursePackageOptionAndPriceParam coursePackageParam : coursePackageOptionList) {
        // 课程列表赋值
        OrderCourseOption orderCourseOption = new OrderCourseOption();
        // 课程类型
        orderCourseOption.setCourseType(coursePackageParam.getCourseType());
        // 单价
        orderCourseOption.setRealPrice(coursePackageParam.getCoursePriceUnitPrice());
        // 课时数
        orderCourseOption.setCourseCount(coursePackageParam.getCourseCount());
        // 续约的 合同，课时数(用于续约的合同展示)
        orderCourseOption.setShowCourseCount(orderCourseOption.getCourseCount());
        // 可用课时数
        orderCourseOption.setRemainCourseCount(orderCourseOption.getCourseCount());
        // 创建人id,默认为e-commerce的id
        orderCourseOption.setCreateUserId(BadminConstant.LCID_REDEEM_ORDER_BY_USER);
        // 更新人id，为学员id
        orderCourseOption.setUpdateUserId(updateUserId);
        // 课程单位类型
        orderCourseOption.setCourseUnitType(coursePackageParam.getCourseUnitType());

        normalOptionsMap.put(orderCourseOption.getCourseType()
            + "," + orderCourseOption.getCourseUnitType(), orderCourseOption);
      }
      // 课程包原价
      saveOrderCourseParam
          .setTotalShowPrice(coursePackageAndPriceParam.getCoursePackageShowPrice());

      List<OrderCourseOption> normalOptionsList = new ArrayList<OrderCourseOption>(normalOptionsMap
          .values());
      List<OrderCourseOption> giftOptionsList = new ArrayList<OrderCourseOption>();
      // 保存合同
      String orderId = adminOrderCourseSaveService.saveOrderCourseAndOption(saveOrderCourseParam,
          BadminConstant.LCID_REDEEM_ORDER_BY_USER,
          updateUserId, giftOptionsList,
          normalOptionsList);

      if (orderId == null) {
        logger.error("兑换码兑换保存合同失败！！！兑换码：" + redeemCode);
        throw new RuntimeException("合同拟定失败！");
      }

      logger.info("兑换码兑换合同开始拆分订单！！！！！！！");
      Map<String, Object> splitParamMap = new HashMap<String, Object>();
      // 支付来源
      splitParamMap.put("pay_from", "兑换码");
      // 合同id
      splitParamMap.put("order_id", orderId);
      // 订单价格
      splitParamMap.put("split_price", saveOrderCourseParam.getTotalFinalPrice());
      // 订单状态
      splitParamMap.put("split_status",
          OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_ONLINE);
      // 支付来源
      splitParamMap.put("split_pay_type", OrderCourseConstant.SPLIT_PAY_TYPE_ONLINE_OTHER);
      // 更新人id
      splitParamMap.put("admin_user_id", updateUserId);
      // cc名字
      splitParamMap.put("pay_cc_name", "e-commerce");
      // cc信息，全部为""
      splitParamMap.put("pay_bank", "");
      splitParamMap.put("pay_success_sequence", "");
      splitParamMap.put("pay_center_name", "");
      splitParamMap.put("pay_city_name", "");

      // 拆分一笔订单（支付价）并管理员确认线下支付
      Map<String, Object> confirmSuccessPayMap = orderCourseSplitService
          .confirmSuccessPay(splitParamMap);

      if (!"200".equals((String) confirmSuccessPayMap.get("code"))) {
        logger.error("兑换码兑换拆分订单确认失败！！！兑换码：" + redeemCode);
        throw new RuntimeException("合同拟定失败！");
      }
    }

    return json;
  }

  /**
   * 
   * Title: 查询合同信息<br>
   * Description: findOrderCourseDetail<br>
   * CreateDate: 2017年4月5日 下午4:35:27<br>
   * 
   * @category 查询合同信息
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public OrderCourseDetailParam findOrderCourseDetail(String keyId) throws Exception {
    // modify by yang.mh 2015年9月6日20:07:50

    // 1.通过主键去查询合同主表
    OrderCourse orderCourse = orderCourseEntityDao.findOneByOrderIdReturnAll(keyId);

    if (orderCourse == null) {
      logger.error("合同不存在，无法查看" + keyId);
      return null;
    }

    // 2.查询当前用户的信息(因为cc在管理平台修改过用户信息，因此不能通过session来查询)
    UserInfoForOrderDetailParam userInfo = userInfoEntityDao
        .findUserInfoByKeyId(orderCourse.getUserId());

    // 3.查询销售的信息
    BadminUser badminUser = badminUserDao.findOneBykeyId(orderCourse.getCreateUserId());

    // 4.查询合同子表的信息
    List<OrderCourseOption> orderCourseOptionList = orderCourseOptionEntityDao
        .findListByOrderIdReturnCourseType(orderCourse.getKeyId());

    List<OrderCourseDetailOptionParam> orderCourseOptionEntityList =
        new ArrayList<OrderCourseDetailOptionParam>();

    // 遍历码表中的课程类型名称,临时写法
    for (OrderCourseOption orderCourseOption : orderCourseOptionList) {
      OrderCourseDetailOptionParam orderCourseDetailOptionParam =
          new OrderCourseDetailOptionParam();

      CourseType courseType = (CourseType) MemcachedUtil
          .getValue(orderCourseOption.getCourseType());

      // 课程单位类型
      orderCourseDetailOptionParam.setCourseUnitType(orderCourseOption.getCourseUnitType());
      // 课程类型
      orderCourseDetailOptionParam.setCourseTypeId(orderCourseOption.getCourseType());
      orderCourseDetailOptionParam.setCourseType(courseType.getCourseTypeChineseName());
      // 是否已赠送
      orderCourseDetailOptionParam.setIsGift(orderCourseOption.getIsGift());
      // 课时数
      orderCourseDetailOptionParam.setShowCourseCount(orderCourseOption.getShowCourseCount());
      // 价格
      orderCourseDetailOptionParam.setRealPrice(orderCourseOption.getRealPrice());
      // 订课规则
      orderCourseDetailOptionParam.setSubscribeRules(courseType.getCourseTypeSubscribeRules());
      // 取消订课规则
      orderCourseDetailOptionParam
          .setCancelSubscribeRules(courseType.getCourseTypeCancelSubscribeRules());
      // modify by seven 2017年4月5日15:33:05 增加课程时长返回
      orderCourseDetailOptionParam
          .setDurationRules(courseType.getCourseTypeDurationRules());

      orderCourseOptionEntityList.add(orderCourseDetailOptionParam);
    }

    OrderCourseDetailParam returnObj = new OrderCourseDetailParam();
    // 封装数据，返回给前台
    returnObj.setOrderCourse(orderCourse);
    returnObj.setUserInfo(userInfo);
    returnObj.setBadminUser(badminUser);
    returnObj.setOrderCourseOptionEntityList(orderCourseOptionEntityList);
    returnObj.setGiftTimeHtml(createGiftTimeInnerHtml(orderCourse.getGiftTime()));
    returnObj.setContractOwnerName(MemcachedUtil.getConfigValue("contract_owner_name"));
    return returnObj;
  }

  /**
   * Title: 生成赠送时间HTML内容<br>
   * Description: 生成赠送时间HTML内容，预览合同页面 和 学员前端 -> 我的合同 -> 购买课时数，两个页面展示<br>
   * CreateDate: 2016年5月24日 下午6:30:10<br>
   * 
   * @category 生产赠送时间HTML
   * @author ivan.mgh
   * @param orderCourseMap
   * @return
   */
  private String createGiftTimeInnerHtml(Integer giftTime) {
    StringBuilder sb = new StringBuilder("");

    if (null != giftTime && giftTime.intValue() > 0) {
      sb.append(giftTime + "月上课时长（赠）");
      sb.append("<br>");

      /**
       * modified by komi 2016年8月31日09:54:37 不再赠送以下课程
       */
      // sb.append(giftTime + "月RSA(赠）");
      // sb.append("<br>");
      //
      // sb.append(giftTime + "月微课（赠）");
      // sb.append("<br>");
      //
      // sb.append(giftTime + "月English Studio（赠）");
    }

    return sb.toString();
  }
}