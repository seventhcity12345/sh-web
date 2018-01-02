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
import java.util.Map;

/**
 * Title: 微信下发消息实体.<br>
 * Description: WeixinMsg<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月17日 下午4:34:24
 * 
 * @author yangmh
 */
public class WeixinMsg implements Serializable {
  // 下发用户的openid
  private String touser;
  // 模板id
  private String template_id;
  // 点击消息内的地址
  private String url;
  // 发送内容
  private Map<String, Object> data;

  public String getTouser() {
    return touser;
  }

  public void setTouser(String touser) {
    this.touser = touser;
  }

  public String getTemplate_id() {
    return template_id;
  }

  public void setTemplate_id(String template_id) {
    this.template_id = template_id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

}
