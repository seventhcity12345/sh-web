/** 
 * File: WebexConstant.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.constant<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年8月3日 下午5:24:38
 * @author yangmh
 */
package com.webi.hwj.constant;

/**
 * Title: WebexConstant<br>
 * Description: WebexConstant<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年8月3日 下午5:24:38
 * 
 * @author yangmh
 */
public class WebexConstant {
  /**
   * webex消费者类型：创建会议
   */
  public static final String WEBEX_CONSUMER_TYPE_CREATE_MEETING = "0";
  /**
   * webex消费者类型：删除会议
   */
  public static final String WEBEX_CONSUMER_TYPE_DELETE_MEETING = "2";

  /**
   * webex消费者类型：创建会议join_url
   */
  public static final String WEBEX_CONSUMER_TYPE_CREATE_MEETING_JOIN_URL = "3";

  /**
   * webex房间类型，1v1
   */
  public static final int WEBEX_ROOM_TYPE_ONE2ONE = 0;

  /**
   * webex房间类型，1vn
   */
  public static final int WEBEX_ROOM_TYPE_ONE2MANY = 1;

  /**
   * webex房间类型，demo
   */
  public static final int WEBEX_ROOM_TYPE_DEMO = 2;
}
