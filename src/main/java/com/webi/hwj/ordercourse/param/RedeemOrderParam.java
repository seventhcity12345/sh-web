/** 
 * File: UserForm.java<br> 
 * Project: hwj_svn<br> 
 * Package: com.webi.hwj.index.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月11日 下午1:22:45
 * @author yangmh
 */
package com.webi.hwj.ordercourse.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 一键兑换合同<br> 
 * Description: 一键兑换合同<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2016年9月26日 下午5:23:18 
 * @author komi.zsy
 */
public class RedeemOrderParam implements Serializable{
  /** 
  * 
  */ 
  private static final long serialVersionUID = 6897571035436336241L;

  @NotNull(message = "兑换码不能为空")
  @Length(min = 12, max = 12, message = "请输入正确的兑换码")
  private String redeemCode;

  @NotNull(message = "学员姓名不能为空")
  @Length(min = 2, max = 10, message = "姓名为2-10个汉字")
  private String userName;

  @NotNull(message = "手机号不能为空")
  @Length(min = 11, max = 11, message = "手机号必须为11位")
  private String userPhone;

  //身份证号
  private String idcard;

  public String getRedeemCode() {
    return redeemCode;
  }

  public void setRedeemCode(String redeemCode) {
    this.redeemCode = redeemCode;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }
}
