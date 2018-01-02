/** 
 * File: WeixinConstant.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.weixin.constant<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年10月17日 上午11:16:04
 * @author yangmh
 */
package com.webi.hwj.weixin.constant;

import com.mingyisoft.javabase.util.MemcachedUtil;

/**
 * Title: 微信常量类.<br>
 * Description: WeixinConstant<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月17日 上午11:16:04
 * 
 * @author yangmh
 */
public class WeixinConstant {
  /**
   * 用于缓存微信的session对象.
   */
  public static final String WEIXIN_MEMCACHED_SESSION_USER = "weixin_memcached_session_user";

  /**
   * 用于缓存的获取其他API使用的ACCESS_TOKEN的key.
   */
  public static final String WEIXIN_MEMCACHED_NORMAL_ACCESS_TOKEN =
      "weixin_memcached_normal_access_token";

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

  /**
   * 微信模板消息推送时创建多线程的个数
   */
  public static final Integer THREAD_SUMNUM_BY_WEIXIN = 10;

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

  // 上课提醒
  public static String getWeixinMsgTemplateIdClassWarn() {
    return MemcachedUtil.getConfigValue("weixin_msg_template_id_class_warn");
  }

  // 课程评价通知
  public static String getWeixinMsgTemplateIdCommentWarn() {
    return MemcachedUtil.getConfigValue("weixin_msg_template_id_comment_warn");
  }

  /**
   * 绑定微信服务号提醒
   * 
   * @author felix.yl
   */
  public static String getWeixinMsgTemplateIdBindWarn() {
    return MemcachedUtil.getConfigValue("weixin_msg_template_id_bind_warn");
  }
}
