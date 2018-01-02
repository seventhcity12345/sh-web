package com.webi.hwj.classin.param;

import com.mingyisoft.javabase.util.ReflectUtil;

public class ClassinAppUrlParam {
  private String id;
  private String sn;

  private String chatToken;

  private String[] streamPlayUrl;

  private String[] streamPlayHls;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getChatToken() {
    return chatToken;
  }

  public void setChatToken(String chatToken) {
    this.chatToken = chatToken;
  }

  public String[] getStreamPlayUrl() {
    return streamPlayUrl;
  }

  public void setStreamPlayUrl(String[] streamPlayUrl) {
    this.streamPlayUrl = streamPlayUrl;
  }

  public String[] getStreamPlayHls() {
    return streamPlayHls;
  }

  public void setStreamPlayHls(String[] streamPlayHls) {
    this.streamPlayHls = streamPlayHls;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
