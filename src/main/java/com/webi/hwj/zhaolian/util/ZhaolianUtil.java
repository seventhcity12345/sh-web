package com.webi.hwj.zhaolian.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mucfc.fep.base.AppConfig;
import com.mucfc.fep.base.ServiceExecutor;
import com.mucfc.fep.base.ServiceRequest;
import com.mucfc.fep.base.ServiceResponse;
import com.mucfc.fep.sdk.security.AsymmetricKeyManager;
import com.mucfc.fep.sdk.util.Base64;
import com.mucfc.fep.sdk.util.RSAUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.param.OrderAndOrderSplitParam;
import com.webi.hwj.zhaolian.constant.ZhaolianConstant;

public class ZhaolianUtil {

  /**
   * Title: 根据招联返回的状态码格式化为我们系统的订单状态码<br>
   * Description: 根据招联返回的状态码格式化为我们系统的订单状态码<br>
   * CreateDate: 2017年3月31日 上午10:58:47<br>
   * 
   * @category 根据招联返回的状态码格式化为我们系统的订单状态码
   * @author komi.zsy
   * @param status
   *          招联返回的状态码
   * @return
   */
  public static final String formatStatusByZhaolian(String status) {
    switch (status) {
      case "1":
        return OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS;
      case "0":
      case "3":
      case "4":
        return OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL;
      default:
        break;
    }
    return OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING;
  }
  
  /**
   * Title: 返回招联下单接口<br>
   * Description: 返回招联下单接口<br>
   * CreateDate: 2017年4月24日 下午4:41:39<br>
   * @category 返回招联下单接口 
   * @author komi.zsy
   * @param orderCourseSplit 订单信息
   * @param sessionUser 用户信息
   * @return
   * @throws Exception
   */
  public static String returnZhaolianUrl(OrderAndOrderSplitParam orderCourseSplit,SessionUser sessionUser) throws Exception{
 // 2.为招联订单参数赋值
    //////////////////////////////////// 请求参数//////////////////////////////////////
    String encoding = "UTF-8";
    // 业务参数BizContent组装，具体业务参数可能不同
    JSONObject jsonObj = new JSONObject();
    // 商户id
    jsonObj.put("merchantId", ZhaolianConstant.MERCHANTID);
    // 商户名字
    jsonObj.put("merchantName", "韦博英语");
    // 订单参考号（我们系统的订单号）
    jsonObj.put("orderRefNo", orderCourseSplit.getKeyId());
    // 订单金额
    jsonObj.put("orderAmt", orderCourseSplit.getSplitPrice());
    // 订单描述
    jsonObj.put("orderDesc", "线上支付订单");
    // 订单超时时间
    jsonObj.put("timeout", ZhaolianConstant.TIMEOUTTIME);
    // 回调地址
    jsonObj.put("callbackPC", MemcachedUtil.getConfigValue(ConfigConstant.ZHAOLIAN_RETURNURL));
    // 异步回调地址
    jsonObj.put("asyncCallback", MemcachedUtil.getConfigValue(ConfigConstant.ZHAOLIAN_NOTIFYURL));

    // 商品信息
    JSONObject jsonProductInfo = new JSONObject();
    // 商品类型
    jsonProductInfo.put("productType", "教育产品");
    // 订单商品名称
    jsonProductInfo.put("productName", orderCourseSplit.getCoursePackageName());
    // 订单商品描述
    jsonProductInfo.put("productDesc", orderCourseSplit.getCoursePackageName());
    jsonObj.put("productInfo", jsonProductInfo);

    // 商品信息
    JSONObject jsonCustomerInfo = new JSONObject();
    // 商品类型
    jsonCustomerInfo.put("customerName", sessionUser.getRealName());
    // 订单商品名称
    jsonCustomerInfo.put("idNumber", sessionUser.getIdcard());
    // 订单商品描述
    jsonCustomerInfo.put("mobileNo", sessionUser.getPhone());
    jsonObj.put("customerInfo", jsonCustomerInfo);

    // 原始外部订单号(如果同一笔订单每次支付请求过来的orderRefNo不一样，请保持这些订单的原订单参考号一致。此字段为空时默认等于orderRefNo)
    // jsonObj.put("originalOrderRefNo","");

    // 接口固定参数组装
    String appId = ZhaolianConstant.APPID;
    // 请求流水号(商户端生成的以appId开头的19位流水号，每次请求需要提供唯一的流水号)
    String flowNo = appId + System.currentTimeMillis();
    // 当前请求时间，格式为yyyy-MM-dd HH:mm:ss
    String reqDateTime = com.mingyisoft.javabase.util.DateUtil.dateToStrYYMMDDHHMMSS(new Date());
    // 组装的业务参数Map
    String bizContent = jsonObj.toJSONString();
    Map<String, String> params = new HashMap<String, String>();
    params.put("appId", appId);
    params.put("flowNo", flowNo);
    params.put("encoding", encoding);
    params.put("reqDateTime", reqDateTime);
    params.put("bizContent", bizContent);

    // 字典排序需要签名内容
    Set<String> keySet = params.keySet();
    String[] keysArr = keySet.toArray(new String[0]);
    Arrays.sort(keysArr);
    StringBuilder signedContent = new StringBuilder();
    for (int i = 0; i < keysArr.length; i++) {
      signedContent.append(keysArr[i]).append("=").append(params.get(keysArr[i])).append("&");
    }

    // 数据签名
    String signedContentStr = signedContent.toString();
    if (signedContentStr.endsWith("&"))
      signedContentStr = signedContentStr.substring(0,
          signedContentStr.length() - 1);
    System.out.println(signedContentStr);

    AsymmetricKeyManager asymmetricKeyManager = AsymmetricKeyManager.getInstance();
    PrivateKey privateKey = asymmetricKeyManager.getPrivateKey(AppConfig.getInstance()
        .getAppKeyName());
    String sign = Base64.byteArrayToBase64(RSAUtil.genSignature(signedContentStr.getBytes("UTF-8"),
        privateKey));
    // 签名(这个和招联的sdk不是一套，对方开发单独写了签名方法)
    // String sign = Verify.verify(params);

    params.put("sign", sign);
    params.put("bizContent", java.net.URLEncoder.encode(bizContent, encoding));

    // 建立请求
    String sHtmlText = ZhaolianUtil.buildRequest(params, "post", "确认");
    return sHtmlText;
  }

  /**
   * Title: 招联表单提交组装方法<br>
   * Description: 招联表单提交组装方法<br>
   * CreateDate: 2017年4月18日 下午1:49:51<br>
   * 
   * @category 招联表单提交组装方法
   * @author komi.zsy
   * @param sParaTemp
   * @param strMethod
   * @param strButtonName
   * @return
   */
  public static String buildRequest(Map<String, String> sParaTemp, String strMethod,
      String strButtonName) {
    // 待请求参数数组
    List<String> keys = new ArrayList<String>(sParaTemp.keySet());

    StringBuffer sbHtml = new StringBuffer();

    sbHtml.append("<form id=\"zhaolianSubmit\" name=\"zhaolianSubmit\""
        + " action=\"" + MemcachedUtil.getConfigValue(ConfigConstant.ZHAOLIAN_URL)
        + "\">");

    for (int i = 0; i < keys.size(); i++) {
      String name = (String) keys.get(i);
      String value = (String) sParaTemp.get(name);
      System.out.println("111111:<input type=\"hidden\" name=\"" + name + "\" value=\"" + value
          + "\"/>");
      sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    }

    // submit按钮控件请不要含有name属性
    sbHtml.append("<input type=\"submit\" value=\"" + strButtonName
        + "\" style=\"display:none;\"></form>");
    sbHtml.append("<script>document.forms['zhaolianSubmit'].submit();</script>");

    return sbHtml.toString();
  }

  /**
   * Title: 招联接口固定数据组装<br>
   * Description: 招联接口固定数据组装<br>
   * CreateDate: 2017年4月20日 下午4:45:16<br>
   * @category 招联接口固定数据组装 
   * @author komi.zsy
   * @param request
   * @param bizContent 数据
   */
  public static void formatParam(ServiceRequest request,String bizContent) {
    // 接口固定参数组装
    String encoding = "UTF-8";
    String appId = ZhaolianConstant.APPID;
    // 请求流水号(商户端生成的以appId开头的19位流水号，每次请求需要提供唯一的流水号)
    String flowNo = appId + System.currentTimeMillis();
    // 当前请求时间，格式为yyyy-MM-dd HH:mm:ss
    Date reqDateTime = new Date();
    // 组装的业务参数
    request.setFlowNo(flowNo);
    request.setAppId(appId);
    request.setEncoding(encoding);
    request.setReqDateTime(reqDateTime);
    request.setBizContent(bizContent);
  }
  
  /**
   * Title: Token获取<br>
   * Description: Token获取<br>
   * CreateDate: 2017年4月20日 下午4:46:44<br>
   * @category getToken 
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public static String getToken() throws Exception {
    ServiceRequest request = new ServiceRequest();
    request.setReqServiceId("api.token");
    ZhaolianUtil.formatParam(request,"");
    
    ServiceResponse resp = ServiceExecutor.execute(request);
    System.out.println(resp);
    JSONObject tokenObj = JSONObject.parseObject(resp.getBizContent());
    System.out.println("token : " + tokenObj.getString("key"));
    String token = tokenObj.getString("key");
    System.out.println(token);
    
    return token;
  }

  /**
   * Title: 获取订单状态<br>
   * Description: 获取订单状态<br>
   * CreateDate: 2017年4月26日 下午1:05:08<br>
   * @category 获取订单状态 
   * @author komi.zsy
   * @param orderSplitId 订单id
   * @param token 招联token
   * @return
   * @throws Exception
   */
  public static ServiceResponse findOrderSplitStatus(String orderSplitId,String token) throws Exception {
    ServiceRequest request = new ServiceRequest();
    request.setReqServiceId("api.service.pay.query");
    request.setToken(token);
    // 业务参数
    JSONObject bizContent = new JSONObject();
    bizContent.put("merchantId", ZhaolianConstant.MERCHANTID);
    bizContent.put("orderRefNo", orderSplitId);
    
    ZhaolianUtil.formatParam(request,bizContent.toJSONString());

    ServiceResponse resp = ServiceExecutor.execute(request);
    System.out.println(resp);
    
    return resp;
  }

}
