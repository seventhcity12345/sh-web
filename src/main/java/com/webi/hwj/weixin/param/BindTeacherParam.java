/** 
 * File: BingUserParam.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.weixin.param<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年10月14日 下午3:08:43
 * @author yangmh
 */
package com.webi.hwj.weixin.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 用于微信绑定教师<br> 
 * Description: 用于微信绑定教师<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年6月15日 下午4:38:20 
 * @author komi.zsy
 */
public class BindTeacherParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = -8272190359751669097L;
  private String keyId;
  @NotNull(message = "账号不能为空")
  private String account;
  @NotNull(message = "openid不能为空")
  private String weixinOpenId;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getWeixinOpenId() {
    return weixinOpenId;
  }

  public void setWeixinOpenId(String weixinOpenId) {
    this.weixinOpenId = weixinOpenId;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }
  
}
