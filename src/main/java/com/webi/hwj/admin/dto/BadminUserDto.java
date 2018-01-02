package com.webi.hwj.admin.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class BadminUserDto implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -918862559375321127L;
  @Length(min = 1, max = 50, message = "必须是1-50位")
  @NotBlank(message = "不能为空")
  private String account;
  @Pattern(regexp = "\\w{128}", message = "必须是128位字母数字的组合")
  @NotBlank(message = "不能为空")
  private String pwd;

  @Pattern(regexp = "[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}",
      message = "格式不合法")
  @NotBlank(message = "不能为空")
  private String leadId;
  // 学员手机号
  @NotBlank(message = "不能为空")
  @Pattern(regexp = "^1[0-9]{10}$", message = "格式不合法")
  private String studentMobile;

  // CC账号
  private String ccAccount;
  // CC名字
  @NotBlank(message = "不能为空")
  @Length(max = 50, message = "长度不能超过50位")
  private String ccName;
  // CC所在中心
  @Length(min = 2, max = 10, message = "必须是2-10位")
  @NotBlank(message = "不能为空")
  private String ccBelongCenter;
  // CC所在城市
  @Length(min = 2, max = 10, message = "必须是2-10位")
  @NotBlank(message = "不能为空")
  private String ccBelongCity;
  // 推广来源（crm）
  private String adid;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getLeadId() {
    return leadId;
  }

  public void setLeadId(String leadId) {
    this.leadId = leadId;
  }

  public String getStudentMobile() {
    return studentMobile;
  }

  public void setStudentMobile(String studentMobile) {
    this.studentMobile = studentMobile;
  }

  public String getCcAccount() {
    return ccAccount;
  }

  public void setCcAccount(String ccAccount) {
    this.ccAccount = ccAccount;
  }

  public String getCcName() {
    return ccName;
  }

  public void setCcName(String ccName) {
    this.ccName = ccName;
  }

  public String getCcBelongCenter() {
    return ccBelongCenter;
  }

  public void setCcBelongCenter(String ccBelongCenter) {
    this.ccBelongCenter = ccBelongCenter;
  }

  public String getCcBelongCity() {
    return ccBelongCity;
  }

  public void setCcBelongCity(String ccBelongCity) {
    this.ccBelongCity = ccBelongCity;
  }

  public String getAdid() {
    return adid;
  }

  public void setAdid(String adid) {
    this.adid = adid;
  }
}
