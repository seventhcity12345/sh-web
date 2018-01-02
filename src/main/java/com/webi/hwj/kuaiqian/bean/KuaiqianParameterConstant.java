/** 
 * File: KuaiqianParameterUtil.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian.bean<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月14日 下午6:47:17
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.bean;

/**
 * Title: kuaiqian公司接收 用于标识 传递给快钱的参数ID Description: KuaiqianParameterUtil<br>
 * 需要提交给快钱的参数bean Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月14日 下午6:47:17
 * 
 * @author athrun.cw
 */
public class KuaiqianParameterConstant {

  // 测试环境下，快钱支付URL
  public static final String KUAIQIAN_PAY_URL_TEST = "https://sandbox.99bill.com/gateway/recvMerchantInfoAction.htm";

  // 正式环境下，快钱支付URL
  public static final String KUAIQIAN_PAY_URL_PRO = "https://www.99bill.com/gateway/recvMerchantInfoAction.htm";

  // 人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填。
  public static final String MERCHANTACCTID = "merchantAcctId";

  // 编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填。
  public static final String INPUTCHARSET = "inputCharset";

  // 接收支付结果的页面地址，该参数一般置为空即可。
  public static final String PAGEURL = "pageUrl";

  // 服务器接收支付结果的后台地址，该参数务必填写，不能为空。
  public static final String BGURL = "bgUrl";

  // 网关版本，固定值：v2.0,该参数必填。
  public static final String VERSION = "version";

  // 语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
  public static final String LANGUAGE = "language";

  // 签名类型,该值为4，代表PKI加密方式,该参数必填。
  public static final String SIGNTYPE = "signType";

  // 支付人姓名,可以为空。
  public static final String PAYERNAME = "payerName";

  // 支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空。
  public static final String PAYERCONTACTTYPE = "payerContactType";

  // 支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空。
  public static final String PAYERCONTACT = "payerContact";

  // 商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空。
  public static final String ORDERID = "orderId";

  // 订单金额，金额以“分”为单位，商户测试以1分测试(测试时代码改为100分)即可，切勿以大金额测试。该参数必填。
  public static final String ORDERAMOUNT = "orderAmount";

  // 订单提交时间，格式：yyyy-MM-dd HH:mm:ss，如：20071117020101，不能为空。
  public static final String ORDERTIME = "orderTime";

  // 商品名称，可以为空。
  public static final String PRODUCTNAME = "productName";

  // 商品数量，可以为空。
  public static final String PRODUCTNUM = "productNum";

  // 商品代码，可以为空。
  public static final String PRODUCTID = "productId";

  // 商品描述，可以为空。
  public static final String PRODUCTDESC = "productDesc";

  // 扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
  public static final String EXT1 = "ext1";

  // 扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
  public static final String EXT2 = "ext2";

  // 支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10，必填。
  public static final String PAYTYPE = "payType";

  // 银行代码，如果payType为00，该值可以为空；如果payType为10，该值必须填写，具体请参考银行列表。
  public static final String BANKID = "bankId";

  // 同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
  public static final String REDOFLAG = "redoFlag";

  // 快钱合作伙伴的帐户号，即商户编号，可为空。
  public static final String PID = "pid";

  /*
   * // signMsg 签名字符串 不可空，生成加密签名串 public static final String SIGNMSGVAL =
   * "signMsgVal";
   */

}
