package com.webi.hwj.webex.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category webexRoom Entity
 * @author mingyisoft代码生成工具
 */
public class DemoRoomParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -6813777229190467751L;
  // 房间host帐号
  private String webexRoomHostId;
  // 房间host帐号
  private String webexRoomName;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getWebexRoomHostId() {
    return webexRoomHostId;
  }

  public void setWebexRoomHostId(String webexRoomHostId) {
    this.webexRoomHostId = webexRoomHostId;
  }

  public String getWebexRoomName() {
    return webexRoomName;
  }

  public void setWebexRoomName(String webexRoomName) {
    this.webexRoomName = webexRoomName;
  }
}