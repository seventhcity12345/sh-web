/** 
 * File: SignMessageUtil.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月15日 上午10:48:26
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.kuaiqian.bean.KuaiqianParameterConstant;
import com.webi.hwj.kuaiqian.bean.WebiHwjParameterConstant;

/**
 * Title: 获取加密前的签证明文 Description: SignMessageUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月15日 上午10:48:26
 * 
 * @author athrun.cw
 */
public class SignMessageUtil {
  private static Logger logger = Logger.getLogger(SignMessageUtil.class);

  /**
   * 
   * Title: 获取快钱 返回给商户的交易信息 Description: receiveSignMessageValue<br>
   * CreateDate: 2015年8月17日 下午3:30:19<br>
   * 
   * @category receiveSignMessageValue
   * @author athrun.cw
   * @param request
   * @return
   */
  public static String receiveSignMessageValue(HttpServletRequest request) {
    logger.info("开始按照一定规则，解析快钱返回给商户的交易信息...");

    // 人民币网关账号，该账号为11位人民币网关商户编号+01,该值与提交时相同。
    String merchantAcctId = request.getParameter("merchantAcctId");
    // 网关版本，固定值：v2.0,该值与提交时相同。
    String version = request.getParameter("version");
    // 语言种类，1代表中文显示，2代表英文显示。默认为1,该值与提交时相同。
    String language = request.getParameter("language");
    // 签名类型,该值为4，代表PKI加密方式,该值与提交时相同。
    String signType = request.getParameter("signType");
    // 支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10,该值与提交时相同。
    String payType = request.getParameter("payType");
    // 银行代码，如果payType为00，该值为空；如果payType为10,该值与提交时相同。
    String bankId = request.getParameter("bankId");
    // 商户订单号，该值与提交时相同。
    String orderId = request.getParameter("orderId");
    // 订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101,该值与提交时相同。
    String orderTime = request.getParameter("orderTime");
    // 订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试,该值与支付时相同。
    String orderAmount = request.getParameter("orderAmount");
    //银行返回的卡的短号（如：6225889301）
    String bindCard = request.getParameter("bindCard");
    //手机的短号（如：1591918）
    String bindMobile = request.getParameter("bindMobile");
    // 快钱交易号，商户每一笔交易都会在快钱生成一个交易号。
    String dealId = request.getParameter("dealId");
    // 银行交易号 ，快钱交易在银行支付时对应的交易号，如果不是通过银行卡支付，则为空
    String bankDealId = request.getParameter("bankDealId");
    // 快钱交易时间，快钱对交易进行处理的时间,格式：yyyyMMddHHmmss，如：20071117020101
    String dealTime = request.getParameter("dealTime");
    // 商户实际支付金额 以分为单位。比方10元，提交时金额应为1000。该金额代表商户快钱账户最终收到的金额。
    String payAmount = request.getParameter("payAmount");
    // 费用，快钱收取商户的手续费，单位为分。
    String fee = request.getParameter("fee");
    // 扩展字段1，该值与提交时相同。
    String ext1 = request.getParameter("ext1");
    // 扩展字段2，该值与提交时相同。
    String ext2 = request.getParameter("ext2");
    // 处理结果， 10支付成功，11 支付失败，00订单申请成功，01 订单申请失败
    String payResult = request.getParameter("payResult");
    // 错误代码 ，请参照《人民币网关接口文档》最后部分的详细解释。
    String errCode = request.getParameter("errCode");

    String merchantSignMsgVal = "";
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", merchantAcctId);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", version);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", language);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", signType);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", payType);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", bankId);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", orderId);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", orderTime);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", orderAmount);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindCard", bindCard);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindMobile", bindMobile);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", dealId);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", bankDealId);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", dealTime);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", payAmount);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", payResult);
    merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", errCode);

    logger.info("按照快钱加密字符串连接规则解析，获取快钱返回给商户的交易明文字符串为：" + merchantSignMsgVal);
    return merchantSignMsgVal;
  }

  /**
   * Title: 将所有的订单信息&公司信息 与快钱的参数ID进行绑定，并且组合成为一条字符串 明文 Description: 将所有的订单信息&公司信息
   * 与快钱的参数ID进行绑定，并且组合成为一条字符串 明文，并且组合成为一条字符串 明文 CreateDate: 2015年8月15日
   * 上午11:58:55<br>
   * 
   * @category sendSignMessageValue
   * @author athrun.cw
   * @param sessionUser
   * @param order
   * @return
   */
  public static Map<String, Object> sendSignMessageValue(SessionUser sessionUser,
      Map<String, Object> orderCourseSplitMap) {
    logger.info("开始按照一定规则，对将要提交到快钱的订单信息进行字符串拼接...");
    Map<String, Object> map = new HashMap<String, Object>();
    // 返回的加密签证 明文
    String signMsgVal = "";
    // WebiHwjOrderCourse hwjOrderCourse = new WebiHwjOrderCourse();
    Map<String, String> splitOrderMap = new HashMap<String, String>();

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.INPUTCHARSET,
        WebiHwjParameterConstant.INPUT_CHARSET_UTF8);
    // hwjOrderCourse.setInputCharset(WebiHwjParameterConstant.INPUT_CHARSET_UTF8);
    splitOrderMap.put(KuaiqianParameterConstant.INPUTCHARSET,
        WebiHwjParameterConstant.INPUT_CHARSET_UTF8);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.PAGEURL,
        WebiHwjParameterConstant.PAGE_RECEIVE_RESULT_URL);
    // hwjOrderCourse.setPageUrl(WebiHwjParameterConstant.PAGE_RECEIVE_RESULT_URL);

    /***
     * pageUrl为''
     */
    // splitOrderMap.put(KuaiqianParameterConstant.PAGEURL,
    // WebiHwjParameterConstant.PAGE_RECEIVE_RESULT_URL);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.BGURL,
        WebiHwjParameterConstant.BACKGROUND_SERVICE_RECEIVE_RESULT_URL);
    // hwjOrderCourse.setBgUrl(WebiHwjParameterConstant.BACKGROUND_SERVICE_RECEIVE_RESULT_URL);
    splitOrderMap.put(KuaiqianParameterConstant.BGURL,
        WebiHwjParameterConstant.BACKGROUND_SERVICE_RECEIVE_RESULT_URL);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.VERSION,
        WebiHwjParameterConstant.GATEWAY_VERSION);
    // hwjOrderCourse.setVersion(WebiHwjParameterConstant.GATEWAY_VERSION);
    splitOrderMap.put(KuaiqianParameterConstant.VERSION, WebiHwjParameterConstant.GATEWAY_VERSION);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.LANGUAGE,
        WebiHwjParameterConstant.DEFAULT_LANGUAGE_CHINESE);
    // hwjOrderCourse.setLanguage(WebiHwjParameterConstant.DEFAULT_LANGUAGE_CHINESE);
    splitOrderMap.put(KuaiqianParameterConstant.LANGUAGE,
        WebiHwjParameterConstant.DEFAULT_LANGUAGE_CHINESE);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.SIGNTYPE,
        WebiHwjParameterConstant.SIGN_TYPE);
    // hwjOrderCourse.setSignType(WebiHwjParameterConstant.SIGN_TYPE);
    splitOrderMap.put(KuaiqianParameterConstant.SIGNTYPE, WebiHwjParameterConstant.SIGN_TYPE);

    // 测试账号去除
    // signMsgVal = appendParam(signMsgVal,
    // KuaiqianParameterUtil.MERCHANTACCTID,
    // WebiHwjParameterUtil.MERCHANT_ACCOUNT_ID_RMB);
    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.MERCHANTACCTID,
        WebiHwjParameterConstant.MERCHANT_WEBI_ACCOUNT_ID_RMB);
    // hwjOrderCourse.setMerchantAcctId(WebiHwjParameterConstant.MERCHANT_WEBI_ACCOUNT_ID_RMB);
    splitOrderMap.put(KuaiqianParameterConstant.MERCHANTACCTID,
        WebiHwjParameterConstant.MERCHANT_WEBI_ACCOUNT_ID_RMB);

    if (sessionUser != null && sessionUser.getUserName() != null) {
      String userName = sessionUser.getUserName();
      signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.PAYERNAME, userName);
      // hwjOrderCourse.setPayerName(userName);
      splitOrderMap.put(KuaiqianParameterConstant.PAYERNAME, userName);
    }

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.PAYERCONTACTTYPE,
        WebiHwjParameterConstant.PAYER_CONTRACT_TYPE_PHONE);
    // hwjOrderCourse.setPayerContactType(WebiHwjParameterConstant.PAYER_CONTRACT_TYPE_PHONE);
    splitOrderMap.put(KuaiqianParameterConstant.PAYERCONTACTTYPE,
        WebiHwjParameterConstant.PAYER_CONTRACT_TYPE_PHONE);

    if (sessionUser != null && sessionUser.getPhone() != null) {
      String phone = sessionUser.getPhone();
      signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.PAYERCONTACT, phone);
      // hwjOrderCourse.setPayerContact(phone);
      splitOrderMap.put(KuaiqianParameterConstant.PAYERCONTACT, phone);
    }

    /**
     * alex && athrun：
     * 
     * 因为快钱orderId是30位限制，所以此处不再存储order_id字段， 存储的是log日志表的id
     * 
     * 因为此原因，log表的Id，单独设置成为30位uuid
     * 
     * key_id
     */
    if (orderCourseSplitMap != null && orderCourseSplitMap.get("log_key_id") != null) {
      // 快钱ORDERID 说明书中30位
      String log_key_id = orderCourseSplitMap.get("log_key_id").toString();
      signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.ORDERID, log_key_id);
      // hwjOrderCourse.setOrderId(log_key_id);
      splitOrderMap.put(KuaiqianParameterConstant.ORDERID, log_key_id);
    }

    // split_price
    /**
     * 因为 kuaiqian 使用分为计数单位，需要转换为分 modify by athrun.cw 2015年12月23日11:17:13
     * 上线正式测试，将100分去除
     */
    if (orderCourseSplitMap != null && orderCourseSplitMap.get("split_price") != null) {
      String split_price = String
          .valueOf(Integer.parseInt(orderCourseSplitMap.get("split_price").toString()) * 100);
      // split_price = "100";//快钱至少要100分才做处理，否则会操作失败，测试时需要改成100
      signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.ORDERAMOUNT, split_price);
      // hwjOrderCourse.setOrderAmount(split_price);
      splitOrderMap.put(KuaiqianParameterConstant.ORDERAMOUNT, split_price);
    }

    String orderTime = new SimpleDateFormat(WebiHwjParameterConstant.ORDER_TIME_FORMART)
        .format(new Date());
    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.ORDERTIME, orderTime);
    // hwjOrderCourse.setOrderTime(orderTime);
    splitOrderMap.put(KuaiqianParameterConstant.ORDERTIME, orderTime);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.PRODUCTNAME,
        WebiHwjParameterConstant.PRODUCT_NAME);
    // hwjOrderCourse.setProductName(WebiHwjParameterConstant.PRODUCT_NAME);
    splitOrderMap.put(KuaiqianParameterConstant.PRODUCTNAME, WebiHwjParameterConstant.PRODUCT_NAME);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.PRODUCTNUM,
        WebiHwjParameterConstant.PRODUCT_NUMBER);
    // hwjOrderCourse.setProductNum(WebiHwjParameterConstant.PRODUCT_NUMBER);
    splitOrderMap.put(KuaiqianParameterConstant.PRODUCTNUM,
        WebiHwjParameterConstant.PRODUCT_NUMBER);

    /**
     * alex && athrun：
     * 
     * PRODUCTID因为存储的事20位，无法与数据库的设计32位进行关联，所以设置为空
     * 
     * log_key_id
     *//*
       * if(orderCourseSplitMap != null && orderCourseSplitMap.get("log_key_id")
       * != null){ //接口文档说明书中，PRODUCTID为20位 String log_key_id =
       * orderCourseSplitMap.get("log_key_id").toString().substring(0, 19);
       * signMsgVal = appendParam(signMsgVal, KuaiqianParameterUtil.PRODUCTID,
       * log_key_id); hwjOrderCourse.setProductId(log_key_id); }
       */

    /**
     * 同ProductName && athrun || alex共同确定
     */
    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.PRODUCTDESC,
        WebiHwjParameterConstant.PRODUCT_NAME);
    // hwjOrderCourse.setProductDesc(WebiHwjParameterConstant.PRODUCT_NAME);
    splitOrderMap.put(KuaiqianParameterConstant.PRODUCTDESC, WebiHwjParameterConstant.PRODUCT_NAME);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.PAYTYPE,
        WebiHwjParameterConstant.PAY_TYPE_ALL_CHOSE);
    // hwjOrderCourse.setPayType(WebiHwjParameterConstant.PAY_TYPE_ALL_CHOSE);
    splitOrderMap.put(KuaiqianParameterConstant.PAYTYPE,
        WebiHwjParameterConstant.PAY_TYPE_ALL_CHOSE);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.BANKID,
        WebiHwjParameterConstant.BANK_ID);
    // hwjOrderCourse.setBankId(WebiHwjParameterConstant.BANK_ID);
    /**
     * bankId为''
     */
    // splitOrderMap.put(KuaiqianParameterConstant.BANKID,
    // WebiHwjParameterConstant.BANK_ID);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.REDOFLAG,
        WebiHwjParameterConstant.GOODS_TYPE_VIRTUAL);
    // hwjOrderCourse.setRedoFlag(WebiHwjParameterConstant.GOODS_TYPE_VIRTUAL);
    splitOrderMap.put(KuaiqianParameterConstant.REDOFLAG,
        WebiHwjParameterConstant.GOODS_TYPE_VIRTUAL);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.PID,
        WebiHwjParameterConstant.PARTNER_ID);
    // hwjOrderCourse.setPid(WebiHwjParameterConstant.PARTNER_ID);

    /**
     * pid为''
     */
    // splitOrderMap.put(KuaiqianParameterConstant.PID,
    // WebiHwjParameterConstant.PARTNER_ID);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.EXT1,
        WebiHwjParameterConstant.EXT1);

    /**
     * ext1为''
     */
    // splitOrderMap.put(KuaiqianParameterConstant.EXT1,
    // WebiHwjParameterConstant.EXT1);

    signMsgVal = appendParam(signMsgVal, KuaiqianParameterConstant.EXT2,
        WebiHwjParameterConstant.EXT2);

    /**
     * ext2为''
     */
    // splitOrderMap.put(KuaiqianParameterConstant.EXT2,
    // WebiHwjParameterConstant.EXT2);

    logger.info("经过拼接的订单信息字符串：signMsgVal---> " + signMsgVal);
    logger.debug("提交到快钱的订单详细信息：splitOrderMap---> " + splitOrderMap);

    map.put("signMsgVal", signMsgVal);
    map.put("splitOrderMap", splitOrderMap);

    return map;
  }

  /**
   * 
   * Title: 将所有的订单信息&公司信息 与快钱的参数ID进行绑定，并且组合成为一条字符串 明文 Description:
   * appendParam<br>
   * 将所有的订单信息&公司信息 与快钱的参数ID进行绑定，并且组合成为一条字符串 明文 CreateDate: 2015年8月15日
   * 上午11:57:42<br>
   * 
   * @category appendParam
   * @author athrun.cw
   * @param returnMessage
   * @param kuaiqianParametersId
   * @param webiParametersValue
   * @return
   */
  private static String appendParam(String returnMessage, String kuaiqianParametersId,
      String webiParametersValue) {
    System.out.println(webiParametersValue.length());
    if (!"".equals(returnMessage)) {
      if (!"".equals(webiParametersValue)) {
        returnMessage += "&" + kuaiqianParametersId + "=" + webiParametersValue;
      }
    } else {
      if (!"".equals(webiParametersValue)) {
        returnMessage = kuaiqianParametersId + "=" + webiParametersValue;
      }
    }
    return returnMessage;
  }

}
