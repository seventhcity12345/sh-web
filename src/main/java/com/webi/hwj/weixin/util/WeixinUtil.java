/** 
 * File: 微信工具类.<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.weixin.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年10月17日 下午3:19:53
 * @author yangmh
 */

package com.webi.hwj.weixin.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.weixin.constant.WeixinConstant;
import com.webi.hwj.weixin.entity.WeixinMsg;

import net.sf.json.JSONObject;

/**
 * Title: 微信工具类.<br>
 * Description: WeixinUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月17日 下午3:19:53
 * 
 * @author yangmh
 */
public class WeixinUtil {
  private static Logger logger = Logger.getLogger(WeixinUtil.class);

  /**
   * Title: 获取openid和特殊的accessToken.<br>
   * Description: findOpenIdAndSpecialAccessToken<br>
   * CreateDate: 2016年10月19日 上午11:02:22<br>
   * 
   * @category 获取openid和特殊的accessToken
   * @author yangmh
   * @param code
   *          微信的code码，用于获取微信的openid
   * @return 微信返回的json对象
   */
  public static JSONObject findOpenIdAndSpecialAccessToken(String code) throws Exception {
    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
        + WeixinConstant.getWeixinAppid()
        + "&secret=" + WeixinConstant.getWeixinSecret() + "&code=" + code
        + "&grant_type=authorization_code";
    String jsonStr = HttpClientUtil.doPost(url, new HashMap());
    return JSONObject.fromObject(jsonStr);
  }

  /**
   * Title: 查询微信用户信息.<br>
   * Description: findUserInfo<br>
   * CreateDate: 2016年10月19日 下午2:24:14<br>
   * 
   * @category 查询微信用户信息.
   * @author yangmh
   * @param openId
   *          微信的openid
   * @param accessToken
   *          微信的accessToken
   * @return returnArrayStr [0]为昵称,[1]为头像
   */
  public static String[] findUserInfo(String openId, String accessToken) throws Exception {
    String[] returnArrayStr = new String[2];

    String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken
        + "&openid=" + openId + "&lang=zh_CN";

    String jsonStrTemp = HttpClientUtil.doGetReturnString(url);// (url, new
    // HashMap());

    // 坑爹的微信返回过来是乱码，必须要转码!
    String jsonStr = new String(jsonStrTemp.getBytes("ISO-8859-1"), "UTF-8");

    JSONObject jsonObj = JSONObject.fromObject(jsonStr);

    if (jsonObj.get("nickname") != null) {
      // 微信昵称
      String nickname = jsonObj.get("nickname").toString();
      // 微信头像
      String headImgUrl = jsonObj.get("headimgurl").toString();

      returnArrayStr[0] = nickname;
      returnArrayStr[1] = headImgUrl;

    }
    return returnArrayStr;
  }

  /**
   * Title: 获取用于API的accessToken.<br>
   * Description: findNormalAccessToken<br>
   * CreateDate: 2016年10月17日 下午4:28:04<br>
   * 
   * @category 获取用于API的accessToken
   * @author yangmh
   */
  public static String findNormalAccessToken() throws Exception {
    // 正儿八经的access_token，与前面用作登录授权的access_token是有区别的
    // 从缓存中获取accessToken
    String accessToken = MemcachedUtil
        .getConfigValue(WeixinConstant.WEIXIN_MEMCACHED_NORMAL_ACCESS_TOKEN);

    if (StringUtils.isEmpty(accessToken)) {
      // 如果accessToken为空，则去重新获取
      String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
          + WeixinConstant.getWeixinAppid() + "&secret=" + WeixinConstant.getWeixinSecret();

      String tokenReturnJsonStr = HttpClientUtil.doGetReturnString(tokenUrl);

      JSONObject jsonObj = JSONObject.fromObject(tokenReturnJsonStr);

      if (jsonObj.get("access_token") != null) {
        accessToken = jsonObj.get("access_token").toString();
        // 30分钟重拿一次缓存accessToken
        MemcachedUtil.setValue(WeixinConstant.WEIXIN_MEMCACHED_NORMAL_ACCESS_TOKEN, accessToken,
            30 * 60);
      } else {
        throw new Exception("获取access_token失败");
      }
    }
    return accessToken;
  }

  /**
   * Title: 下发微信消息.<br>
   * Description: sendWeixinMsg<br>
   * CreateDate: 2016年10月18日 下午4:27:18<br>
   * 
   * @category sendWeixinMsg
   * @author yangmh
   * @param openId
   *          微信的openid
   * @param templateId
   *          模板id
   * @param dataMap
   *          消息体,参见模板
   * @param url
   *          如果需要让消息允许点击，则此处不为空
   * @return 错误码，如果是0则表示没有错误
   */
  public static String sendWeixinMsg(String openId, String templateId, String url,
      Map<String, Object> dataMap)
      throws Exception {
    logger.info("微信消息下发------>start" + dataMap);

    WeixinMsg weixinMsg = new WeixinMsg();
    weixinMsg.setTouser(openId);
    weixinMsg.setTemplate_id(templateId);
    if (!StringUtils.isEmpty(url)) {
      weixinMsg.setUrl(url);
    }

    weixinMsg.setData(dataMap);

    JSONObject jsonObject = JSONObject.fromObject(weixinMsg);

    String accessToken = findNormalAccessToken();

    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
        + accessToken;
    String returnJsonStr = HttpClientUtil.doPostByJson(requestUrl, jsonObject.toString(),
        new HashMap());

    JSONObject returnJsonObject = JSONObject.fromObject(returnJsonStr);
    String errorCode = returnJsonObject.getString("errcode");

    logger.info("微信消息下发------>end" + errorCode);

    return errorCode;

  }

  /**
   * Title: 获取微信的sessionUser<br>
   * Description: getWeixinSessionUser<br>
   * CreateDate: 2016年11月2日 上午10:07:36<br>
   * 
   * @category 获取微信的sessionUser
   * @author alex
   * @param weixinOpenId
   *          微信的openid
   * @return
   */
  public static SessionUser getWeixinSessionUser(String weixinOpenId) {
    return (SessionUser) MemcachedUtil.getValue(
        WeixinConstant.WEIXIN_MEMCACHED_SESSION_USER + weixinOpenId);
  }

}
