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
 * Title: BindUserParam.<br>
 * Description: 用于微信绑定用户<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月14日 下午3:08:43
 * 
 * @author yangmh
 */
public class BindUserParam implements Serializable {
  private static final long serialVersionUID = 5869183770833213887L;
  private String keyId;
  private String userPhoto;
  private String phone;
  private String idcard;
  @NotNull(message = "openid不能为空")
  private String weixinOpenId;
  @NotNull(message = "微信昵称不能为空")
  private String weixinNickName;
  @NotNull(message = "微信头像不能为空")
  private String weixinUserPhoto;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public String getWeixinOpenId() {
    return weixinOpenId;
  }

  public void setWeixinOpenId(String weixinOpenId) {
    this.weixinOpenId = weixinOpenId;
  }

  public String getWeixinNickName() {
    return weixinNickName;
  }

  public void setWeixinNickName(String weixinNickName) {
    this.weixinNickName = weixinNickName;
  }

  public String getWeixinUserPhoto() {
    return weixinUserPhoto;
  }

  public void setWeixinUserPhoto(String weixinUserPhoto) {
    this.weixinUserPhoto = weixinUserPhoto;
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }

}
