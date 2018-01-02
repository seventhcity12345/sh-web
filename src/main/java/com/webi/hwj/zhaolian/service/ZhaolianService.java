package com.webi.hwj.zhaolian.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mucfc.fep.base.AppConfig;
import com.mucfc.fep.base.ServiceResponse;
import com.mucfc.fep.sdk.security.AsymmetricKeyManager;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.entity.OrderCourseSplit;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseSplitEntityDao;
import com.webi.hwj.ordercourse.param.OrderAndOrderSplitParam;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.ordercourse.service.OrderCourseSplitService;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.zhaolian.util.ZhaolianUtil;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ZhaolianService {
  private static Logger logger = Logger.getLogger(ZhaolianService.class);
  @Resource
  private UserService userService;

  @Resource
  private OrderCourseSplitService orderCourseSplitService;

  @Resource
  private OrderCourseService orderCourseService;

  @Resource
  private AdminOrderCourseSplitEntityDao adminOrderCourseSplitEntityDao;

  /**
   * Title: 获取招联订单状态<br>
   * Description: 获取招联订单状态<br>
   * CreateDate: 2017年4月20日 下午4:57:47<br>
   * 
   * @category 获取招联订单状态
   * @author komi.zsy
   * @param orderSplitId
   *          拆分订单id
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = { Exception.class })
  public String findOrderSplitStatus(String orderSplitId, String splitStatus) throws Exception {
    String tradeStatus = splitStatus;
    // 订单如果原来已经成功了或还未申请，不需要处理。
    if (!OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS.equals(splitStatus
        .toString())
        && !OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY.equals(splitStatus
            .toString())) {
      //招联老是网络不稳定，有时候接不上，所以如果报错就不处理
      try {
        String token = ZhaolianUtil.getToken();
        ServiceResponse serviceResponse = ZhaolianUtil.findOrderSplitStatus(orderSplitId, token);
        JSONObject jsonObject = JSONObject.parseObject(serviceResponse.getBizContent());
        // 交易状态
        tradeStatus = ZhaolianUtil.formatStatusByZhaolian(jsonObject.getString("payResult"));
        this.afterZhaolianPaySuccessLogic(serviceResponse);
      } catch (Exception e) {
        logger.error("招联同步订单出错："+e.toString());
      }
    }

    return tradeStatus;
  }

  /**
   * Title: 处理招联订单状态<br>
   * Description: 处理招联订单状态<br>
   * CreateDate: 2017年4月20日 下午4:52:10<br>
   * 
   * @category 处理招联订单状态
   * @author komi.zsy
   * @param serviceResponse
   * @param jsonObject
   * @throws Exception
   */
  public void afterZhaolianPaySuccessLogic(ServiceResponse serviceResponse)
      throws Exception {
    JSONObject jsonObject = JSONObject.parseObject(serviceResponse.getBizContent());
    System.out.println(jsonObject);
    // 获取招联的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
    // 商户订单号
    String orderId = jsonObject.getString("orderRefNo");

    // 招联交易号
    String tradeNo = jsonObject.getString("orderNo");

    // 交易状态
    String tradeStatus = jsonObject.getString("payResult");

    // 交易金额
    String totalMoney = jsonObject.getString("orderAmt");

    // 逻辑处理
    if (serviceResponse.verify(AsymmetricKeyManager.getInstance().getPublicKey(AppConfig
        .getInstance().getPlatformKeyName()))) {
      // 验证成功
      logger.info("招联回执监听------>验证成功");
      // 调用支付业务逻辑
      orderCourseService.afterBaiduPaySuccessLogic(orderId, "招联分期",
          ZhaolianUtil.formatStatusByZhaolian(tradeStatus), null, totalMoney, tradeNo);

    } else {// 验证失败
      logger.error("招联回执监听------>验证失败");
    }
  }

  /**
   * Title: 提交招联订单<br>
   * Description: 提交招联订单<br>
   * CreateDate: 2017年4月19日 下午3:30:46<br>
   * 
   * @category 提交招联订单
   * @author komi.zsy
   * @param splitOrderId
   *          合同订单id
   * @param sessionUser
   *          用户信息
   * @return
   * @throws Exception
   */
  public CommonJsonObject submitZhaolianForm(String splitOrderId, SessionUser sessionUser)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 1.查询订单相关信息
    OrderAndOrderSplitParam orderCourseSplit = orderCourseSplitService.findOneAndOrderInfoByKeyId(
        splitOrderId);
    if (orderCourseSplit == null) {
      json.setCode(ErrorCodeEnum.ORDER_NOT_EXIST.getCode());
      json.setMsg("订单不存在");
      logger.error("订单不存在!phone=" + sessionUser.getPhone());
      return json;
    }

    // 建立请求
    String sHtmlText = ZhaolianUtil.returnZhaolianUrl(orderCourseSplit, sessionUser);
    json.setData(sHtmlText);

    // 一旦点击我要付款，订单状态就更改为申请中
    OrderCourseSplit paramObj = new OrderCourseSplit();
    paramObj.setKeyId(splitOrderId);
    paramObj.setSplitStatus(Integer.parseInt(
        OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING));
    adminOrderCourseSplitEntityDao.update(paramObj);

    return json;
  }

}