package com.webi.hwj.ordercourse.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SqlUtil;
import com.webi.hwj.admin.dao.BadminUserDao;
import com.webi.hwj.admin.entity.BadminUser;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.coursepackage.dao.AdminCoursePackageDao;
import com.webi.hwj.mail.MailUtil;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseDao;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseEntityDao;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseOptionEntityDao;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseSplitEntityDao;
import com.webi.hwj.ordercourse.param.SaveOrderCourseParam;
import com.webi.hwj.ordercourse.util.OrderContractStatusUtil;
import com.webi.hwj.trainingcamp.entity.TrainingMember;
import com.webi.hwj.trainingcamp.service.TrainingMemberService;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entity.UserInfo;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;
import com.webi.hwj.user.entitydao.AdminUserInfoEntityDao;
import com.webi.hwj.user.service.AdminUserInfoService;

/**
 * Title: 创建/修改合同相关逻辑<br>
 * Description: 因为创建/修改合同的逻辑太过复杂，因此抽出来一个service方法<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月1日 下午8:21:05
 * 
 * @author alex.yang
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminOrderCourseSaveService {
  private static Logger logger = Logger.getLogger(AdminOrderCourseSaveService.class);
  @Resource
  AdminOrderCourseEntityDao adminOrderCourseEntityDao;
  @Resource
  AdminOrderCourseOptionEntityDao adminOrderCourseOptionEntityDao;
  @Resource
  AdminOrderCourseSplitEntityDao adminOrderCourseSplitEntityDao;
  @Resource
  AdminUserEntityDao adminUserEntityDao;
  @Resource
  UserDao userDao;
  @Resource
  AdminUserInfoEntityDao adminUserInfoEntityDao;
  @Resource
  TrainingMemberService trainingMemberService;

  @Resource
  AdminCoursePackageDao adminCoursePackageDao;
  @Resource
  AdminOrderCourseDao adminOrderCourseDao;
  @Resource
  AdminOrderCourseService adminOrderCourseService;
  @Resource
  AdminUserInfoService adminUserInfoService;
  @Resource
  OrderCourseSplitService orderCourseSplitService;
  @Resource
  BadminUserDao badminUserDao;

  /**
   * 
   * Title: 销售拟订合同&修改合同：状态为1：已拟定<br>
   * Description: saveOrderCourseAndOption<br>
   * CreateDate: 2016年1月8日 上午11:19:38<br>
   * 
   * @category 销售拟订合同&修改合同：状态为1：已拟定
   * @author athrun.cw
   * @param paramMap
   * @param adminUserMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public String saveOrderCourseAndOption(SaveOrderCourseParam saveOrderCourseParam,
      String createUserId, String updateUserId,
      List<OrderCourseOption> giftOptionsMergeList, List<OrderCourseOption> normalOptionsMergeList)
          throws Exception {
    /**
     * modify by athrun.cw 2016年1月22日16:25:44 如果是修改合同，则要判断状态
     */
    // ----------------校验拟定合同 ----------------
    this.checkSaveOrderCourse(saveOrderCourseParam, updateUserId);

    // ----------------新增合同主表 ----------------
    OrderCourse orderCourse = this.insertOrderCourse(saveOrderCourseParam, createUserId,
        updateUserId);

    // ----------------新增合同子表----------------
    this.insertOrderCourseOption(orderCourse.getKeyId(),
        saveOrderCourseParam.getRenewalOrderCourseKeyId(),
        giftOptionsMergeList,
        normalOptionsMergeList, createUserId);
    // ----------------用户数据修改----------------
    this.updateUserInfos(saveOrderCourseParam, updateUserId);

    /**
     * modified by komi 2016年8月15日17:43:00 需求325，折后价为0时，合同自动生效
     */
    if (saveOrderCourseParam.getTotalRealPrice() == 0) {
      this.confirmSuccessPayByAuto(orderCourse.getKeyId(), updateUserId, "折后价0元", 0);
    }

    return orderCourse.getKeyId();
  }

  /**
   * Title: crm保存合同<br>
   * Description: crm保存合同<br>
   * CreateDate: 2016年7月21日 上午9:46:03<br>
   * 
   * @category crm保存合同
   * @author ivan.mgh
   * @param saveOrderCourseParam
   * @param updateUserId
   * @param giftOptionsList
   * @param normalOptionsList
   * @param authParam
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public String crmSaveOrderCourseAndOption(SaveOrderCourseParam saveOrderCourseParam,
      String updateUserId,
      List<OrderCourseOption> giftOptionsMergeMap, List<OrderCourseOption> normalOptionsMergeMap,
      String authParam)
          throws Exception {

    String orderId = saveOrderCourseAndOption(saveOrderCourseParam, updateUserId, updateUserId,
        giftOptionsMergeMap,
        normalOptionsMergeMap);
    Map<String, Object> orderCourseMap = adminOrderCourseDao.findOneByKeyId(orderId,
        "key_id,order_status,total_show_price,total_real_price,total_final_price");
    // Base64解码
    String leadId = new String(Base64.decodeBase64(authParam), "UTF-8");
    orderCourseMap.put("lead_id", leadId);

    // 添加到消息队列
    adminOrderCourseService.sendSynchronizeContractInfoMessage(orderCourseMap);

    return orderId;
  }

  /**
   * 
   * Title: 新增合同主表<br>
   * Description: insertOrderCourse<br>
   * CreateDate: 2016年4月13日 下午2:04:27<br>
   * 
   * @category insertOrderCourse
   * @author athrun.cw
   * @param saveOrderCourseParam
   * @param sessionAdminUser
   * @return
   * @throws Exception
   */
  public OrderCourse insertOrderCourse(SaveOrderCourseParam saveOrderCourseParam,
      String createUserId, String updateUserId)
          throws Exception {
    OrderCourse orderCourse = new OrderCourse();

    logger.info("拟定合同开始保存合同");

    // modified by ivan.mgh,2016年5月16日20:37:34
    orderCourse.setIsCrm(saveOrderCourseParam.getIsCrm());

    String userId = saveOrderCourseParam.getUserId();
    String userName = saveOrderCourseParam.getUserName();

    String keyId = SqlUtil.createUUID();
    orderCourse.setKeyId(keyId);
    // 价格策略子表id
    orderCourse.setCoursePackagePriceOptionId(saveOrderCourseParam.getCoursePackagePriceOptionId());
    // 总展示价
    orderCourse.setTotalShowPrice(saveOrderCourseParam.getTotalShowPrice());
    // modified by ivan.mgh，2016年5月18日17:32:59
    // 赠送时间
    orderCourse.setGiftTime(saveOrderCourseParam.getGiftTime());
    // 所选课程包id，跟联动没关系，只是记录一下而已。
    orderCourse.setCoursePackageId(saveOrderCourseParam.getCoursePackageId());
    // 所选用户ID
    orderCourse.setUserId(userId);
    // 所选用户名称
    orderCourse.setUserName(userName);
    // 时效
    orderCourse.setLimitShowTime(saveOrderCourseParam.getLimitShowTime());
    // 时效单位
    orderCourse.setLimitShowTimeUnit(saveOrderCourseParam.getLimitShowTimeUnit());
    // 课程包类型0:standard,1:premium,2:basic
    orderCourse.setCoursePackageType(saveOrderCourseParam.getCoursePackageType());
    // 总真实价
    orderCourse.setTotalRealPrice(saveOrderCourseParam.getTotalRealPrice());
    // 类别id
    orderCourse.setCategoryType(saveOrderCourseParam.getCategoryType());
    // 学生来源
    orderCourse.setUserFromType(saveOrderCourseParam.getUserFromType());
    // crm合同Guid
    orderCourse.setCrmContractId(saveOrderCourseParam.getCrmContractId());
    // 合同备注
    orderCourse.setOrderRemark(saveOrderCourseParam.getOrderRemark());
    // 创建人id
    orderCourse.setCreateUserId(createUserId);
    // 更新人id
    orderCourse.setUpdateUserId(updateUserId);
    // modify by seven 2017年4月5日11:39:58 增加合同版本号
    orderCourse.setOrderVersion(Integer.valueOf(MemcachedUtil.getConfigValue(
        ConfigConstant.ORDER_VERSION)));

    /**
     * modify by athrun.cw 2016年4月1日13:33:37 续约逻辑
     */
    String renewalOrderCourseKeyId = saveOrderCourseParam.getRenewalOrderCourseKeyId();

    if (!StringUtils.isEmpty(renewalOrderCourseKeyId)) {
      // 续约逻辑
      // 在拟订续约合同中，源合同的id，是经过处理的以","分隔的字符串
      String resourceKeyId = renewalOrderCourseKeyId.toString().split(",")[0];

      // 1.找到源合同的数据
      OrderCourse orderCourseObj = adminOrderCourseEntityDao.findOneByKeyId(resourceKeyId);
      if (orderCourseObj == null) {
        logger.error("id [" + updateUserId + "] 为用户id [" + userId + "] 续约合同失败，找不到续约的源合同~");
        throw new RuntimeException("续约合同失败，找不到续约的源合同~");
      }

      /**
       * 需求486 modified by komi 2017年1月22日14:05:08 续约后的课程包名称显示为：原课程包名/续约课程包名
       */
      // 课程包名
      orderCourse.setCoursePackageName(orderCourseObj.getCoursePackageName() + "/"
          + saveOrderCourseParam.getCoursePackageName());
      orderCourse.setFromPath(orderCourseObj.getFromPath() + "," + keyId);
      orderCourse.setTotalFinalPrice(saveOrderCourseParam.getTotalFinalPrice());
      orderCourse
          .setOrderOriginalType(Integer.parseInt(OrderStatusConstant.ORDER_ORIGINAL_TYPE_RENEWAL));

    } else {
      // 课程包名
      orderCourse.setCoursePackageName(saveOrderCourseParam.getCoursePackageName());
      orderCourse.setFromPath(keyId);
      orderCourse.setTotalFinalPrice(saveOrderCourseParam.getTotalRealPrice());
      orderCourse
          .setOrderOriginalType(Integer.parseInt(OrderStatusConstant.ORDER_ORIGINAL_TYPE_NORMAL));
    }

    // 更新用户级别
    String currentLevel = saveOrderCourseParam.getCurrentLevel();
    if (!StringUtils.isEmpty(currentLevel)) {
      User userParam = new User();
      userParam.setKeyId(userId);
      userParam.setInitLevel(currentLevel);
      userParam.setCurrentLevel(currentLevel);
      adminUserEntityDao.update(userParam);

      // modified by komi 2017年8月11日10:54:00 级联更新训练营表用户当前级别
      TrainingMember paramObj = new TrainingMember();
      paramObj.setTrainingMemberUserId(userId);
      paramObj.setTrainingMemberCurrentLevel(currentLevel);
      trainingMemberService.updateMemberUserCurrentLevel(paramObj);
    }

    adminOrderCourseEntityDao.insert(orderCourse);
    return orderCourse;
  }

  /**
   * Title: 增加合同子表option数据<br>
   * Description: insertOrderCourseOption<br>
   * CreateDate: 2016年6月20日 下午4:38:20<br>
   * 
   * @category insertOrderCourseOption
   * @author athrun.cw
   * @param orderId
   * @param renewalOrderCourseKeyId
   *          需要解析的源合同的数据，后面会被改成源合同的keyId（如果是续约才有，如果不是续约则为null）
   * @param giftOptionsList
   *          赠送课程项集合
   * @param normalOptionsList
   *          课程包下的课程项集合
   * @throws Exception
   */
  public void insertOrderCourseOption(String orderId, String renewalOrderCourseKeyId,
      List<OrderCourseOption> giftOptionsMergeList,
      List<OrderCourseOption> normalOptionsMergeList, String createUserId)
          throws Exception { // 插入普通子表数据
    logger.info("拟定合同开始插入合同子表数据");

    if (normalOptionsMergeList != null && normalOptionsMergeList.size() > 0) {
      for (OrderCourseOption orderCourseOptionParam : normalOptionsMergeList) {
        orderCourseOptionParam.setOrderId(orderId);
      }
    }
    // 插入赠课子表数据
    if (giftOptionsMergeList != null && giftOptionsMergeList.size() > 0) {
      for (OrderCourseOption orderCourseOptionParam : giftOptionsMergeList) {
        orderCourseOptionParam.setOrderId(orderId);
      }
    }
    // 批量插入
    adminOrderCourseOptionEntityDao.batchInsert(giftOptionsMergeList);
    adminOrderCourseOptionEntityDao.batchInsert(normalOptionsMergeList);
  }

  /**
   * Title: 更新用户相关数据<br>
   * Description: updateUserInfos<br>
   * CreateDate: 2016年4月5日 下午8:59:45<br>
   * 
   * @category 更新用户相关数据
   * @author athrun.cw
   * @param saveOrderCourseParam
   * @throws Exception
   */
  public void updateUserInfos(SaveOrderCourseParam saveOrderCourseParam, String updateUserId)
      throws Exception {
    String userName = saveOrderCourseParam.getUserName();
    String userId = saveOrderCourseParam.getUserId();
    String lcId = saveOrderCourseParam.getLearningCoachId();

    logger.info("拟定合同开始保存用户信息：" + userId);

    User user = adminUserEntityDao.findOneByKeyId(userId);
    /**
     * modified by komi 2016年7月19日16:15:28 可以修改用户中文名字
     */
    if (!StringUtils.isEmpty(userName) && !userName.equals(user.getUserName())) {
      user.setUserName(userName);
      adminUserEntityDao.update(user);
    }

    /**
     * modify by felix.yl 兑换码兑换合同,如果当前学员当前没有指定的LC，就将当前学员的LC默认指定为lc.lc
     */
    if (!StringUtils.isEmpty(lcId) && StringUtils.isEmpty(user.getLearningCoachId())) {
      user.setLearningCoachId(lcId);
      adminUserEntityDao.update(user);
    }

    // 需要判断用户的real_name是否为空,如果不为空则需要赋值
    UserInfo userInfo = adminUserInfoEntityDao.findOneRealName(userId);

    if (!StringUtils.isEmpty(userName) && !userName.equals(userInfo.getRealName())) {

      userInfo.setRealName(userName);
      adminUserInfoEntityDao.update(userInfo);

      // 更新t_user表中的info_complete_percent
      adminUserEntityDao.updateCompletePercent(userInfo);

      // modified by komi 2017年8月11日10:54:00 级联更新训练营表用户信息
      TrainingMember paramObj = new TrainingMember();
      paramObj.setTrainingMemberUserId(userInfo.getKeyId());
      paramObj.setTrainingMemberRealName(userName);
      trainingMemberService.updateMemberUserRealName(paramObj);
    }

    // 更新身份证信息
    if (!StringUtils.isEmpty(saveOrderCourseParam.getIdcard())
        && !saveOrderCourseParam.getIdcard().equals(userInfo.getIdcard())) {
      userInfo.setIdcard(saveOrderCourseParam.getIdcard());
      adminUserInfoEntityDao.update(userInfo);

      // 更新t_user表中的info_complete_percent
      adminUserEntityDao.updateCompletePercent(userInfo);
    }

    // 更新英语名字
    if (!StringUtils.isEmpty(saveOrderCourseParam.getEnglishName())
        && !saveOrderCourseParam.getEnglishName().equals(userInfo.getEnglishName())) {
      userInfo.setEnglishName(saveOrderCourseParam.getEnglishName());
      adminUserInfoEntityDao.update(userInfo);

      // 更新t_user表中的info_complete_percent
      adminUserEntityDao.updateCompletePercent(userInfo);
    }

    /**
     * modify by seven 2017年1月18日10:26:46 开通合同时指定lc
     */
    if (!StringUtils.isEmpty(saveOrderCourseParam.getLearningCoachId())
        && !saveOrderCourseParam.getLearningCoachId().equals(user.getLearningCoachId())) {
      user.setLearningCoachId(saveOrderCourseParam.getLearningCoachId());
      adminUserEntityDao.update(user);
      // 发送邮件
      // 查询lc cc name
      String lcName = null;
      String ccName = null;
      BadminUser lcEnity = badminUserDao.findOneBykeyId(user.getLearningCoachId());
      BadminUser ccEnity = badminUserDao.findOneBykeyId(updateUserId);
      if (lcEnity != null) {
        lcName = lcEnity.getAdminUserName();
      }
      if (ccEnity != null) {
        ccName = ccEnity.getAdminUserName();
      }
      // 生成邮件内容
      String mailbody = OrderContractStatusUtil.createChangeLcMail(user, userInfo, ccName, lcName);
      // 发送邮件
      String mailAddress = (String) MemcachedUtil.getValue(ConfigConstant.MAIL_LC_CHANGE_ADDRESS);
      MailUtil.sendMail(mailAddress, "有新学员合同生成/老学员合同变更，请LC团队尽快联系", mailbody);
    }

  }

  /**
   * Title: 需要赋值参数的确认支付成功接口<br>
   * Description: 需要赋值参数的确认支付成功接口<br>
   * CreateDate: 2017年3月21日 上午10:44:07<br>
   * 
   * @category 需要赋值参数的确认支付成功接口
   * @author komi.zsy
   * @param orderId
   *          合同id
   * @param updateUserId
   *          更新人id
   * @param payFrom
   *          支付来源
   * @param splitPrice
   *          订单价格
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void confirmSuccessPayByAuto(String orderId, String updateUserId, String payFrom,
      int splitPrice) throws Exception {
    logger.info("0元合同开始拆分订单！！！！！！！");
    Map<String, Object> splitParamMap = new HashMap<String, Object>();
    // 支付来源
    splitParamMap.put("pay_from", payFrom);
    // 合同id
    splitParamMap.put("order_id", orderId);
    // 订单价格
    splitParamMap.put("split_price", splitPrice);
    // 订单状态
    splitParamMap.put("split_status", OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE);
    // 支付来源
    splitParamMap.put("split_pay_type", OrderCourseConstant.SPLIT_PAY_TYPE_TRANSFER);
    // 更新人id
    splitParamMap.put("admin_user_id", updateUserId);
    // cc名字
    splitParamMap.put("pay_cc_name", "");
    // cc信息，全部为""
    splitParamMap.put("pay_bank", "");
    splitParamMap.put("pay_success_sequence", "");
    splitParamMap.put("pay_center_name", "");
    splitParamMap.put("pay_city_name", "");

    // 拆分一笔订单（支付价）并管理员确认线下支付
    Map<String, Object> confirmSuccessPayMap = orderCourseSplitService
        .confirmSuccessPay(splitParamMap);

    if (!"200".equals((String) confirmSuccessPayMap.get("code"))) {
      logger.error(splitPrice + "元合同拆分订单失败！！！");
      throw new RuntimeException(splitPrice + "元合同拆分订单失败！");
    }
  }

  /**
   * Title: 拟定合同时的相关校验<br>
   * Description: 如果是编辑合同则需要物理删除原来合同的信息<br>
   * CreateDate: 2016年4月5日 下午9:11:01<br>
   * 
   * @category 拟定合同时的相关校验
   * @author athrun.cw
   * @param orderId
   * @param saveOrderCourseParam
   * @throws Exception
   */
  public void checkSaveOrderCourse(SaveOrderCourseParam saveOrderCourseParam, String updateUserId)
      throws Exception {
    logger.info("拟定合同开始合同校验");
    String orderId = saveOrderCourseParam.getOrderId();
    String userId = saveOrderCourseParam.getUserId();
    String idcard = saveOrderCourseParam.getIdcard();

    if (idcard != null && !"null".equals(idcard) && !"".equals(idcard)) {
      // 判读用户的身份证号是否重复
      Map<String, Object> idcardParamMap = new HashMap<String, Object>();
      idcardParamMap.put("idcard", idcard);
      idcardParamMap.put("key_id", "notequal" + userId);
      int idcardCount = adminUserInfoService.findCount(idcardParamMap);
      if (idcardCount > 0) {
        throw new RuntimeException("身份证不能重复");
      }
    }

    /**
     * 添加用户名称 midify by athrun.cw 2015年12月28日15:31:14
     */
    // 用户姓名非空校验
    if (StringUtils.isEmpty(userId)) {
      logger.error("id [" + updateUserId + "] 为用户拟订合同失败，请填写用户姓名~");
      throw new RuntimeException(" id [" + updateUserId + "] 为用户拟订合同失败，请填写用户姓名~");
    }

    // 修改合同时的判断
    if (!StringUtils.isEmpty(orderId)) {
      // 找到该修改的合同信息
      OrderCourse orderCourse = adminOrderCourseEntityDao.findOneByKeyIdForStatus(orderId);
      if (orderCourse == null) {
        logger.error("想要修改的合同id：" + orderId + "不存在！");
        throw new RuntimeException("想要修改的合同id：" + orderId + "不存在！");
      }

      // 查找合同拆分订单申请中的数据（招联百度专用！！！）为0则代表没有申请中的数据，可以修改合同
      int isApplySplitNum = adminOrderCourseSplitEntityDao.findOneByOrderIdAndStatus(orderId);

      // 一些状态是不可以修改合同的
      String orderStatus = orderCourse.getOrderStatus().toString();
      if (isApplySplitNum != 0
          || OrderStatusConstant.ORDER_STATUS_PAYING.equals(orderStatus)
          || OrderStatusConstant.ORDER_STATUS_HAVE_PAID.equals(orderStatus)
          || OrderStatusConstant.ORDER_STATUS_HAVE_EXPIRED.equals(orderStatus)
          || OrderStatusConstant.ORDER_STATUS_HAVE_TERMINATED.equals(orderStatus)) {
        // 禁止修改
        logger.error("想要修改的合同id：" + orderId + "已经开始付款，不能修改合同！");
        throw new RuntimeException("想要修改的合同id：" + orderId + "已经开始付款，不能修改合同！");
      }

      logger.info("拟定合同：发现是编辑合同，开始删除原有合同：" + orderId);

      // 删除之前的数据(如果有)
      adminOrderCourseEntityDao.deleteForReal(orderId);
      adminOrderCourseEntityDao.deleteForRealOption(orderId);
      adminOrderCourseSplitEntityDao.deleteForRealByOrderId(orderId);
    }

    /**
     * modify by athrun.cw 2016年1月8日11:33:55 添加 total_real_price 为可编辑时候，必须非空判断
     * 
     * modify by athrun.cw 2016年1月21日18:04:18 调价 折后价为>0 的数字
     */
    if (saveOrderCourseParam.getTotalRealPrice() == null
        || saveOrderCourseParam.getTotalRealPrice() < 0) {
      logger.error(
          " id [" + updateUserId + "] 为用户id [" + saveOrderCourseParam.getUserId()
              + "] 拟定合同失败，折后价必须为大于等于0的数字！");
      throw new RuntimeException("拟定合同失败，折后价必须为大于等于0的数字！");
    }

    /**
     * 差价必须为大于等于0的数字
     * 
     * @author komi.zsy
     */
    if (saveOrderCourseParam.getTotalFinalPrice() != null
        && saveOrderCourseParam.getTotalFinalPrice() < 0) {
      logger.error(" id [" + updateUserId + "] 为用户id [" + saveOrderCourseParam.getUserId()
          + "] 拟定合同失败，差价必须为大于等于0的数字！");
      throw new RuntimeException("拟定合同失败，差价必须为大于等于0的数字！");
    }
  }
}