package com.webi.hwj.admin.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category badminUser Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_badmin_user")
public class BadminUserParam implements Serializable {
  private static final long serialVersionUID = 7654249335647891543L;
  // 主键
  private String keyId;
  // 用户名
  private String account;
  // 密码(密文)
  private String pwd;
  // 管理员email
  private String email;
  // 创建时间
  private Date createDate;
  // 真实名称
  private String adminUserName;
  // 用户类型(0:普通管理员；2:超级管理员)
  private Integer adminUserType;
  // 角色ID
  private String roleId;
  // 角色名称
  private String roleName;
  // AO系统工号
  private String employeeNumber;
  // 修改时间
  private Date updateDate;
  // 创建人
  private Integer createUserId;
  // 修改人
  private Integer updateUserId;
  // 是否启用
  private Integer isUsed;
  // 管理员手机号
  private String telphone;
  // 管理员微信号
  private String weixin;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public Integer getAdminUserType() {
    return adminUserType;
  }

  public void setAdminUserType(Integer adminUserType) {
    this.adminUserType = adminUserType;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public Integer getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Integer createUserId) {
    this.createUserId = createUserId;
  }

  public Integer getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(Integer updateUserId) {
    this.updateUserId = updateUserId;
  }

  public Integer getIsUsed() {
    return isUsed;
  }

  public void setIsUsed(Integer isUsed) {
    this.isUsed = isUsed;
  }

  public String getEmployeeNumber() {
    return employeeNumber;
  }

  public void setEmployeeNumber(String employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public String getTelphone() {
    return telphone;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  public String getWeixin() {
    return weixin;
  }

  public void setWeixin(String weixin) {
    this.weixin = weixin;
  }

}