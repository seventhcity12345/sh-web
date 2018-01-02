/** 
 * File: SessionAdminUser.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.bean<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年2月14日 上午11:20:37
 * @author yangmh
 */
package com.webi.hwj.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Title: SessionAdminUser<br>
 * Description: SessionAdminUser<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年2月14日 上午11:20:37
 * 
 * @author yangmh
 */
public class SessionAdminUser implements Serializable {
  // 序列化ID
  private static final long serialVersionUID = 839482893919203818L;
  // 主键
  private String keyId;
  // 账号
  private String account;
  // 邮箱
  private String email;
  // 手机号
  private String phone;
  // 密码
  private String pwd;
  // 创建时间
  private Date createDate;
  // 管理员名称
  private String adminUserName;
  // 用户类型(0:普通管理员；2:超级管理员)
  private String adminUserType;
  // 角色ID
  private String roleId;
  // 角色名称
  private String roleName;
  // 左侧菜单集合
  private Map<String, List<Map<String, Object>>> leftMenuTreeMap;

  // 功能权限集合
  private Map<String, Object> permissionMap;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
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

  public Map<String, List<Map<String, Object>>> getLeftMenuTreeMap() {
    return leftMenuTreeMap;
  }

  public void setLeftMenuTreeMap(Map<String, List<Map<String, Object>>> leftMenuTreeMap) {
    this.leftMenuTreeMap = leftMenuTreeMap;
  }

  public Map<String, Object> getPermissionMap() {
    return permissionMap;
  }

  public void setPermissionMap(Map<String, Object> permissionMap) {
    this.permissionMap = permissionMap;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public String getAdminUserType() {
    return adminUserType;
  }

  public void setAdminUserType(String adminUserType) {
    this.adminUserType = adminUserType;
  }

  /**
   * Title: 是否有权限<br>
   * Description: isHavePermisson<br>
   * CreateDate: 2016年2月14日 下午1:58:39<br>
   * 
   * @category isHavePermisson
   * @author yangmh
   * @param permission
   * @return
   */
  public boolean isHavePermisson(String permission) {
    if (permissionMap.get(permission) != null) {
      return true;
    } else {
      return false;
    }
  }
}
