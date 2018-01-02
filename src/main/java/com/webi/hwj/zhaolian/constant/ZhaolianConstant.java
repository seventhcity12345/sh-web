/** 
 * File: WeixinConstant.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.weixin.constant<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年10月17日 上午11:16:04
 * @author yangmh
 */
package com.webi.hwj.zhaolian.constant;

import com.mingyisoft.javabase.util.MemcachedUtil;

/**
 * Title: 微信常量类.<br>
 * Description: WeixinConstant<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月17日 上午11:16:04
 * 
 * @author yangmh
 */
public class ZhaolianConstant {
  
  //因为测试生产都是这个，就不配在码表了（app_private_key.pem这个里也是我们的这个私钥）
  public static final String PRIKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ5WNr+cR7aM20yOJOWkWZ6G9TNpZUMZregtxIQmd/XQiWx5RJwxTeoUa2sgujWi32KjiHU4eIcG0yhpSw0DnC4gFiS0jWKjsIMOLFisH2Nyos21ctGTtnmHNU0qsi+2KoLsaNZ7TZQJB9/OJMsyWV8rIfl1qOiWxLzfQaYIAWshAgMBAAECgYAPVPXUNAPVr2oEzbbQ7csnyJWxtsDxcGkglF31FeyDxv+nm06DeVq8EeIc63xceTr8SC2MfD1fhc+omBmGVxUmUuTCMI6AESz5D1Zy4oT8IQKxkm6+99891urqIiBSraUzbqkEcGbKLDD746lZ13tK/yhdEOCERTkoQTFyREEPgQJBAOcJ+9esnP3zXY6ivan94FF5nhKWX2GU6Vk6/IeExdNu1OozNOh8gTeruqKf/ZmSIfUw3R8cSGs4l5Fq4rvZX/kCQQCvcXNwfvY69M6DBtAjS8NFWnVhLJYS5jGdh2IaZ7adRZmuxHjvEN5qm1ChgTFI28scJlnY8jyzhICKMz6roP5pAkABGZMRW8kDjH0NiMjOXl8LBEE0ZXLezA6wVg+NtZwbfZOV6dh+otkaor6ot4pFiOSQfkMPOts9Z2+RSaj6q/ZBAkA3DX2w/lYb1/f5i6jCeqKy35jXvlr1YUCOcw4oxQS7wzxfaA2ezwdMIqKhm12wxC+vDKXC+vhrSJfhRgVTa/iJAkEAndl3sJuo3NQoRhLG24vBz1AD2RwFHGcZJPjIY6dnS3OMQpQKZGbSCQFCi452TlYA/gQwiGzzQwrhbknnV6qDjg==";
  
  //接入商户在信用付支付网关申请的应用ID, 韦伯英语为10040
  public static final String APPID = "10040";
  
  //商户ID
  public static final String MERCHANTID = "20200001";
  
  //订单超时时间
  public static final int TIMEOUTTIME = 86400;
  
  /**
   * 用于缓存微信的session对象.
   */
  public static final String WEIXIN_MEMCACHED_SESSION_USER = "weixin_memcached_session_user";

  /**
   * 用于缓存的获取其他API使用的ACCESS_TOKEN的key.
   */
  public static final String WEIXIN_MEMCACHED_NORMAL_ACCESS_TOKEN = "weixin_memcached_normal_access_token";

  /**
   * 用于缓存的微信扫码的uid和openid的key.
   */
  public static final String WEIXIN_MEMCACHED_SCAN_UID = "weixin_memcached_scan_uid";

  /**
   * 微信的应用id.
   */
  private static final String WEIXIN_APPID = null;
  /**
   * 微信的密钥.
   */
  private static final String WEIXIN_SECRET = null;
  /**
   * 微信预约成功下发消息模板id.
   */
  private static final String WEIXIN_MSG_TEMPLATE_ID_SUBSCRIBE = null;
  /**
   * 微信取消预约成功下发消息模板id
   */
  private static final String WEIXIN_MSG_TEMPLATE_ID_CANCEL_SUBSCRIBE = null;
  /**
   * 微信缓存用户名称
   */
  public static final String WEIXIN_MEMCACHED_NICK_NAME = "weixin_memcached_nick_name";
  /**
   * 微信缓存用户头像
   */
  public static final String WEIXIN_MEMCACHED_USER_PHOTO = "weixin_memcached_user_photo";

  public static String getWeixinAppid() {
    return MemcachedUtil.getConfigValue("weixin_appid");
  }

  public static String getWeixinSecret() {
    return MemcachedUtil.getConfigValue("weixin_secret");
  }

  public static String getWeixinMsgTemplateIdSubscribe() {
    return MemcachedUtil.getConfigValue("weixin_msg_template_id_subscribe");
  }

  public static String getWeixinMsgTemplateIdCancelSubscribe() {
    return MemcachedUtil.getConfigValue("weixin_msg_template_id_cancel_subscribe");
  }
  
  //上课提醒
  public static String getWeixinMsgTemplateIdClassWarn() {
    return MemcachedUtil.getConfigValue("weixin_msg_template_id_class_warn");
  }
  
  //课程评价通知
  public static String getWeixinMsgTemplateIdCommentWarn() {
    return MemcachedUtil.getConfigValue("weixin_msg_template_id_comment_warn");
  }

}
