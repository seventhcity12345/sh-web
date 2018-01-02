/** 
 * File: UserForm.java<br> 
 * Project: hwj_svn<br> 
 * Package: com.webi.hwj.index.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月11日 下午1:22:45
 * @author yangmh
 */
package com.webi.hwj.coperation.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * 
 * Title: qq兑换码使用注册参数<br>
 * Description: qq兑换码使用注册参数<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年9月26日 下午3:48:31
 * 
 * @author seven.gz
 */
public class RedeemCodeRegisterParam {
  private String userId;

  @NotNull(message = "兑换码不能为空")
  private String redeemCode;

  @NotNull(message = "学员姓名不能为空")
  private String userName;

  @NotNull(message = "手机号不能为空")
  @Length(min = 11, max = 11, message = "手机号必须为11位")
  private String phone;

  private String pwd;

  @NotNull(message = "验证码不能为空")
  @Length(min = 4, max = 4, message = "验证码必须为4位")
  private String code;

  @NotNull(message = "省不能为空")
  private String province;

  @NotNull(message = "市不能为空")
  private String city;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
