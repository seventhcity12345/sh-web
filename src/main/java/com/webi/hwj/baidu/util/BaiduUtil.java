package com.webi.hwj.baidu.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.baidu.constant.BaiduConstant;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.OrderStatusConstant;

import net.sf.json.JSONObject;

public class BaiduUtil {
  
  /**
   * Title: 根据百度返回的状态码格式化为我们系统的订单状态码<br>
   * Description: 根据百度返回的状态码格式化为我们系统的订单状态码<br>
   * CreateDate: 2017年3月31日 上午10:58:47<br>
   * @category 根据百度返回的状态码格式化为我们系统的订单状态码 
   * @author komi.zsy
   * @param status 百度返回的状态码
   * @return
   */
  public static final String formatStatusByBaidu(String status){
    switch (status){
      case "2":
      case "16":
        return OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING;
      case "8":
        return OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS;
      case "3":
      case "24":
        return OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL;
      default:
        break;
    }
    return OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING;
  }

  /**
   * Title: 同步百度分期订单状态<br>
   * Description: 同步百度分期订单状态<br>
   * CreateDate: 2017年3月30日 下午4:44:19<br>
   * 
   * @category 同步百度分期订单状态
   * @author komi.zsy
   * @param orderSplitId
   *          订单id
   * @throws Exception
   */
  public static JSONObject getOrderStatus(String orderSplitId) throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    map.put("action", BaiduConstant.GET_ORDER_STATUS);
    map.put("corpid", BaiduConstant.getCorpid());
    map.put("orderid", orderSplitId);
    map.put("tpl", BaiduConstant.getCorpid());

    // 获取sign
    String sign = Sign.createBaseSign(map, BaiduConstant.getSignKey());
    map.put("sign", sign);

    // post 请求
    Set<Entry<String, String>> entries = map.entrySet();
    String postStr = "";
    for (Entry<String, String> entry : entries) {
      postStr += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8") + "&";
    }
    System.out.println("postStr:  " + postStr);

    String url = BaiduConstant.getUrl();// 联调地址

    String result = HttpRequest.sendPost(url, postStr);
    JSONObject json = JSONObject.fromObject(result);
    System.out.println(json);

    return json;
  }

  /**
   * Title: 提交订单<br>
   * Description: 同步订单数据<br>
   * CreateDate: 2017年3月31日 下午2:22:24<br>
   * 
   * @category syncOrderInfo
   * @author komi.zsy
   * @param userId
   *          用户id
   * @param userName
   *          用户名字
   * @param userPhone
   *          用户手机号
   * @param orderSplitId
   *          订单id
   * @param splitPrice
   *          订单价格
   * @return
   * @throws Exception
   */
  public static String syncOrderInfo(String userId, String userName, String userPhone,
      String orderSplitId, String splitPrice,String coursePackageName) throws Exception {
    Map<String, String> userMap = new HashMap<String, String>();
    userMap.put("username", userName);
    userMap.put("mobile", userPhone);
    // userMap.put("email", "789hasn@qq.com");
    String jsonStr = JSON.toJSONString(userMap);
    String rsaStr = Rsa.encrypt(Rsa.getPublicKey(BaiduConstant.getPublicKey()), jsonStr);

    // System.out.println("rsaStr: " + rsaStr);

    Map<String, String> map = new HashMap<String, String>();
    map.put("action", BaiduConstant.SYNC_ORDER_INFO);
    map.put("corpid", BaiduConstant.getCorpid());
    map.put("orderid", orderSplitId);
    // 贷款金额，单位为：分（我们是元的，所以加两个0）
    map.put("money", splitPrice + "00");
    // map.put("period", "12");
    map.put("oauthid", userId);
    map.put("courseid", BaiduConstant.getCourseId());
    map.put("coursename", coursePackageName);
    map.put("tpl", BaiduConstant.getCorpid());
    //百度分期：渠道，channel1是成人，channel2是青少
    map.put("channel", MemcachedUtil.getConfigValue(ConfigConstant.BAIDU_CHANNEL));
    map.put("data", rsaStr);

    // 获取sign
    String sign = Sign.createBaseSign(map, BaiduConstant.getSignKey());
    map.put("sign", sign);

    // post 请求
    Set<Entry<String, String>> entries = map.entrySet();
    String postStr = "";
    for (Entry<String, String> entry : entries) {
      postStr += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8") + "&";
    }
    System.out.println("postStr:  " + postStr);

    String url = BaiduConstant.getUrl();// 联调地址
    // String url = "https://umoney.baidu.com/edu/openapi/post";// 线上地址

    String result = HttpRequest.sendPost(url, postStr);

    JSONObject jsonObject = JSONObject.fromObject(result);
    System.out.println(JSONObject.fromObject(result));

    return jsonObject.getString("result");
  }
  
  /**
   * Title: 处理签名加密<br>
   * Description: 处理签名加密<br>
   * CreateDate: 2017年4月7日 上午11:16:18<br>
   * @category formatSign 
   * @author komi.zsy
   * @param map 需要处理的参数
   * @return
   */
  public static String formatSign(Map<String, String> map) {
    return Sign.createBaseSign(map, BaiduConstant.getSignKey());
  }
}
