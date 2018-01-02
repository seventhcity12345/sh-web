/** 
 * File: WebiHwjParameterUtil.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月14日 下午6:51:40
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.bean;

import com.mingyisoft.javabase.util.MemcachedUtil;

/**
 * Title: WebiHwjParameterUtil<br>
 * webi公司 HWJ网站传递给kuqian公司的参数value Description: GatewayMerchantUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月14日 下午6:51:40
 * 
 * @author athrun.cw
 */
public class WebiHwjParameterConstant {

  // 商品名称
  public static final String PRODUCT_NAME = "speakhi";

  // 商品数量
  public static final String PRODUCT_NUMBER = "1";

  // 商品描述1
  public static final String EXT1 = "";

  // 商品描述2
  public static final String EXT2 = "";

  // 商户生成证书时设置的密码 测试用密码
  // public static final String MERCHANT_PASSWORD_TEST = "123456";
  // 生成自己的私钥后，私钥密码
  public static final String MERCHANT_WEBI_HWJ_PASSWORD = "xzN-5up-FM9-qxX";

  // 商户私钥名称
  public static final String PRIVATE_WEBI_RSA_KEY = "../kuaiqiankey/99bill-rsa.pfx";

  // 快钱公钥名称
  public static final String PUBLIC_KUAIQIAN_RSA_KEY = "../kuaiqiankey/99bill.cert.rsa.20340630.cer";

  // receive 页面接收到快钱支付结果后， 会自动将参数传递给 show 页面并跳转到 show 页面
  public static final String RETURN_SHOW_URL_SUCCESS = MemcachedUtil
      .getConfigValue("contract_owner_url") + "/kuaiqian/showResult?msg=success";
  public static final String RETURN_SHOW_URL_FALSE = MemcachedUtil
      .getConfigValue("contract_owner_url") + "/kuaiqian/showResult?msg=false";
  public static final String RETURN_SHOW_URL_ERROR = MemcachedUtil
      .getConfigValue("contract_owner_url") + "/kuaiqian/showResult?msg=error";

  // public static final String RETURN_SHOW_URL_SUCCESS =
  // "http://test.hwj.webiedu.cn/kuaiqian/showResult?msg=success";
  // public static final String RETURN_SHOW_URL_FALSE =
  // "http://test.hwj.webiedu.cn/kuaiqian/showResult?msg=false";
  // public static final String RETURN_SHOW_URL_ERROR =
  // "http://test.hwj.webiedu.cn/kuaiqian/showResult?msg=error";

  // 通知快钱接收到支付结果 1：商户已经收到通知 0：商户未接到通知
  public static final int HAVE_SUCCESS_RECEIVE = 1;
  public static final int HAVE_FAILED_RECEIVE = 0;

  /**
   * 标识使用快钱支付的领域：用于购买speakhi合同订单签约，，，...等等 目前只有speakhi合同订单
   * 
   * 以后可能会有其他用快钱支付的种类
   */
  public static final String PAY_FROM_TYPE_HWJ_ORDER = "hwj.contract.order";

  // 参数为空时，使用该常量
  // public static final String PARAMETER_NULL = "";

  // 人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填
  // public static final String MERCHANT_ACCOUNT_ID_RMB = "1001213884201";
  // 韦博真实的商户编编号
  public static final String MERCHANT_WEBI_ACCOUNT_ID_RMB = "1002396119101";

  // pid 商户编号 快钱合作伙伴的帐户号，即商户编号，可为空
  public static final String PARTNER_ID = "";

  /**
   * 编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填
   */
  public static final String INPUT_CHARSET_UTF8 = "1";
  // public static final String INPUT_CHARSET_GBK = "2";
  // public static final String INPUT_CHARSET_GB2312 = "3";

  // 接收支付结果的页面地址，该参数一般置为空即可
  public static final String PAGE_RECEIVE_RESULT_URL = "";

  // 服务器接收支付结果的后台地址，该参数务必填写，用于处理业务逻辑
  // public static final String BACKGROUND_SERVICE_RECEIVE_RESULT_URL =
  // MemcachedUtil.getConfigValue("contract_owner_url")+"/kuaiqian/receiveOrderCourseResult";
  public static final String BACKGROUND_SERVICE_RECEIVE_RESULT_URL = MemcachedUtil
      .getConfigValue("contract_owner_url") + "/kuaiqian/receiveOrderCourseResult";
  // public static final String BACKGROUND_SERVICE_RECEIVE_RESULT_URL =
  // "http://test.hwj.webiedu.cn/kuaiqian/receiveOrderCourseResult";

  // 网关版本，固定值：v2.0,该参数必填。
  public static final String GATEWAY_VERSION = "v2.0";

  /**
   * 语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
   */
  public static final String DEFAULT_LANGUAGE_CHINESE = "1";
  // public static final String LANGUAGE_ENGLISH = "2";

  // 签名类型,该值为4，代表PKI加密方式,该参数必填。
  public static final String SIGN_TYPE = "4";

  /**
   * 支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空。
   */
  // public static final String PAYER_CONTRACT_TYPE_EMAIL = "1";
  public static final String PAYER_CONTRACT_TYPE_PHONE = "2";

  // orderTime订单提交时间的格式
  public static final String ORDER_TIME_FORMART = "yyyyMMddhhmmss";

  /**
   * 支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10，必填。
   */
  public static final String PAY_TYPE_ALL_CHOSE = "00";
  // public static final String PAY_TYPE_DIRECT_BANK = "10";

  // bankid 银行代码
  public static final String BANK_ID = "";

  /**
   * 同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
   */
  // public static final String GOODS_TYPE_ACTUAL = "1";
  public static final String GOODS_TYPE_VIRTUAL = "0";

  /**
   * 
   * Title: 用于显示页面提交给kuaiqian的参数传递 Description: initKuaiqianPayParameter<br>
   * CreateDate: 2015年8月15日 下午12:20:23<br>
   * 
   * @category initKuaiqianPayParameter
   * @author athrun.cw
   * @param sessionUser
   * @param order
   * @param signMsgVal
   * @return
   *//*
     * public static WebiHwjOrderCourse initWebiHwjOrderCourse(SessionUser
     * sessionUser, Map<String, Object> order, String signMsgVal){
     * WebiHwjOrderCourse kuaiqian = new WebiHwjOrderCourse();
     * kuaiqian.setPayFrom(PAY_FROM_TYPE_HWJ_ORDER);
     * kuaiqian.setMerchantAcctId(MERCHANT_ACCOUNT_ID_RMB);
     * kuaiqian.setInputCharset(INPUT_CHARSET_UTF8); // 页面接收的URL
     * kuaiqian.setPageUrl(PAGE_RECEIVE_RESULT_URL);
     * kuaiqian.setBgUrl(BACKGROUND_SERVICE_RECEIVE_RESULT_URL);
     * kuaiqian.setVersion(GATEWAY_VERSION);
     * kuaiqian.setLanguage(DEFAULT_LANGUAGE_CHINESE);
     * kuaiqian.setSignType(SIGN_TYPE); //payerName 保存userName if(sessionUser !=
     * null && sessionUser.getUserName() != null){
     * kuaiqian.setPayerName(sessionUser.getUserName()); } //联系方式为电话
     * kuaiqian.setPayerContactType(PAYER_CONTRACT_TYPE_PHONE); //电话号码
     * if(sessionUser != null && sessionUser.getPhone() != null){
     * kuaiqian.setPayerName(sessionUser.getPhone()); }
     * kuaiqian.setOrderId(order.get("key_id").toString()); //将 元 ---> 转换为 分
     * if(order != null && order.get("total_real_price") != null){
     * kuaiqian.setOrderAmount(String.valueOf(Integer.parseInt(order.get(
     * "total_real_price").toString()) * 100)); } kuaiqian.setOrderTime(new
     * SimpleDateFormat(ORDER_TIME_FORMART).format(new Date())); if(order !=
     * null && order.get("course_package_name") != null){
     * kuaiqian.setProductName(order.get("course_package_name").toString()); }
     * 
     * // 需要计算optionList.size() //是option 用于保存包含的服务条目
     * kuaiqian.setProductNum(order.get("option_service_count").toString()); //
     * log_key_id kuaiqian.setProductId(order.get("log_key_id").toString());
     * //同course_package_name && athrun || alex共同确定
     * kuaiqian.setProductDesc(order.get("course_package_name")+" pay for "+
     * WebiHwjParameterUtil.PAY_FROM_TYPE_HWJ_ORDER);
     * kuaiqian.setExt1(order.get("extends_description1").toString());
     * kuaiqian.setExt2(order.get("extends_description2").toString());
     * kuaiqian.setPayType(PAY_TYPE_ALL_CHOSE); kuaiqian.setBankId(BANK_ID);
     * kuaiqian.setRedoFlag(GOODS_TYPE_VIRTUAL); kuaiqian.setPid(PARTNER_ID);
     * kuaiqian.setSignMsgVal(signMsgVal);
     * 
     * return kuaiqian; }
     */

}
