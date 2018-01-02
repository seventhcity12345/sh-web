/** 
 * File: JsonCodeMessage.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.bean<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年3月28日 上午11:13:19
 * @author ivan.mgh
 */
package com.webi.hwj.bean;

/**
 * Title: JsonCodeMessage<br>
 * Description: JsonCodeMessage<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月28日 上午11:13:19
 * 
 * @author ivan.mgh
 */
public class JsonCodeMessage {
  private String code;
  private String msg;
  private Object data;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "JsonCodeMessage [code=" + code + ", msg=" + msg + ", data=" + data + "]";
  }
}
