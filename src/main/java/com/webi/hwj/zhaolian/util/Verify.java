package com.webi.hwj.zhaolian.util;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.webi.hwj.zhaolian.constant.ZhaolianConstant;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * Title: 招联开发写的验签demo，但是试验直接调sdk的方法也行，就先保留一下<br> 
 * Description: Verify<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年4月21日 上午10:47:32 
 * @author komi.zsy
 */
public class Verify {


  public static void main(String[] args) throws Exception {

    // 需要传递的参数
    String encoding = "UTF-8";
    String appId = "10040";
    // String merchantId = "20200001";
    String flowNo = "123456"; // 根据实际情况传输
    String reqDateTime = "2017-04-13 12:00:00";
    
    // 业务参数BizContent组装，具体业务参数可能不同
    JSONObject jsonObj = new JSONObject();
    // 商户id
    jsonObj.put("merchantId", "20200001");
    // 商户名字
    jsonObj.put("merchantName", "韦博英语");
    // 订单参考号（我们系统的订单号）
    jsonObj.put("orderRefNo", "scsccscsscscegerg");
    // 订单金额
    jsonObj.put("orderAmt", 200);
    // 订单描述
    jsonObj.put("orderDesc", "线上支付订单");
    // 订单超时时间
    jsonObj.put("timeout", 1800);
    // 回调地址
    jsonObj.put("callbackPC", "komi.speakhi.com");
    // 异步回调地址
    jsonObj.put("asyncCallback", "komi.speakhi.com");

    // 商品信息
    JSONObject jsonProductInfo = new JSONObject();
    // 商品类型
    jsonProductInfo.put("productType", "教育产品");
    // 订单商品名称
    jsonProductInfo.put("productName", "24个月课程表");
    // 订单商品描述
    jsonProductInfo.put("productDesc", "24个月课程表");
    jsonObj.put("productInfo", jsonProductInfo);

    // 商品信息
    JSONObject jsonCustomerInfo = new JSONObject();
    // 商品类型
    jsonCustomerInfo.put("customerName", "都是");
    // 订单商品名称
    jsonCustomerInfo.put("idNumber", "123456789011111");
    // 订单商品描述
    jsonCustomerInfo.put("mobileNo", "12345678901");
    jsonObj.put("customerInfo", jsonCustomerInfo);
    String bizContent = jsonObj.toJSONString();
        //"{\"orderRefNo\":\"2345646153\",\"merchantId\":\"20200001\",\"orderAmt\":\"1600\",\"timeout\":\"1800\",\"callbackAPP\":\"a.com\",\"callbackPC\":\"b.com\",\"asyncCallback\":\"http://order-st1.api.cfcmu.cn/pay/getApplyCode.json\",\"productInfo\":{\"productName\":\"韦博订单\"}}";

    Map<String, String> map = new HashMap<String, String>();
    map.put("encoding", encoding);
    map.put("appId", appId);
    // map.put("merchantId",merchantId);
    map.put("flowNo", flowNo);
    map.put("reqDateTime", reqDateTime);
    map.put("bizContent", bizContent);

    // 字典排序并生成字符串
    String strParams = MaptoString(map);

    String privateKey = ZhaolianConstant.PRIKEY;// 获取私钥
    String sign = getRSASign(strParams, privateKey);

    String result = "http://order-st1.api.cfcmu.cn/pay/verifySign.json?" + strParams + "&sign="
        + java.net.URLEncoder.encode(sign, encoding);

    String result2 = "http://order-st1.api.cfcmu.cn/pay/verifySign.json?" + "appId="
        + java.net.URLEncoder.encode(appId, encoding) + "&encoding="
        + java.net.URLEncoder.encode(encoding, encoding) + "&bizContent="
        + java.net.URLEncoder.encode(bizContent, encoding) + "&flowNo="
        + java.net.URLEncoder.encode(flowNo, encoding) + "&reqDateTime="
        + java.net.URLEncoder.encode(reqDateTime, encoding) + "&sign="
        + java.net.URLEncoder.encode(sign, encoding);
    
    System.out.println(result);
    
    
    System.out.println(result2);

    // result 和 result2 都是正确的URL, 区别在于 result中的参数是没有经过URLENCODE
    // 本样例中使用的 base64方法依赖于 jre8, 如果不是用java8的话, 可以使用其他的base64.encode/decode方法.
    // 替换掉即可运行.

    // int i = 2;

  }

  public static String verify(Map<String, String> params) throws Exception {
    // 字典排序并生成字符串
    String strParams = MaptoString(params);
    String sign = getRSASign(strParams, ZhaolianConstant.PRIKEY);
    return sign;
  }

  private static String getRSASign(String signData, String privateKey) throws Exception {

    byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
    java.security.spec.PKCS8EncodedKeySpec bobPriKeySpec =
        new java.security.spec.PKCS8EncodedKeySpec(keyBytes);
    java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
    PrivateKey pKey = keyFactory.generatePrivate(bobPriKeySpec);

    java.security.Signature sig = java.security.Signature.getInstance("SHA256withRSA");
    sig.initSign(pKey);
    sig.update(signData.getBytes("UTF-8"));

    byte[] signature = sig.sign();
    String strSign = new BASE64Encoder().encode(signature);
    return strSign;

  }

  private static String MaptoString(Map<String, String> params) {
    // 字典排序需要签名内容
    Set<String> keySet = params.keySet();
    String[] keysArr = keySet.toArray(new String[0]);
    Arrays.sort(keysArr);
    StringBuilder signedContent = new StringBuilder();
    for (int i = 0; i < keysArr.length; i++) {
      signedContent.append(keysArr[i]).append("=").append(params.get(keysArr[i])).append("&");
    }
    String signedContentStr = signedContent.toString();
    if (signedContentStr.endsWith("&"))
      signedContentStr = signedContentStr.substring(0, signedContentStr.length() - 1);
    return signedContentStr;
  }
}
