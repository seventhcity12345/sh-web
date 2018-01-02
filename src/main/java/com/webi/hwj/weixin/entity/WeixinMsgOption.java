/** 
 * File: WeixinMsg.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.weixin.entity<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年10月17日 下午4:34:24
 * @author yangmh
 */
package com.webi.hwj.weixin.entity;

import java.io.Serializable;

/**
 * Title: 微信下发消息实体.<br>
 * Description: WeixinMsg<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月17日 下午4:34:24
 * 
 * @author yangmh
 */
public class WeixinMsgOption implements Serializable {
  public WeixinMsgOption(String value) {
    this.value = value;
  }

  // 微信消息的子项值
  private String value;
  // 微信消息的子项颜色
  private String color = "#173177";

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}
