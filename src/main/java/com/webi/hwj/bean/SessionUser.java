package com.webi.hwj.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.mingyisoft.javabase.util.ReflectUtil;

public class SessionUser implements Serializable {

  /** 
  * 
  */
  private static final long serialVersionUID = 7239962754827516904L;
  
  private String token;
  // 主键
  private String keyId;
  // 手机号
  private String phone;

  // 密码（数据库中加密过的）
  private String pwd;

  // 用户名
  private String userName;
  // 用户编码
  private String userCode;

  // 用户图片
  private String userPhoto;

  // 用户图片
  private String userPhotoLarge;

  // 是否为学员状态(0:否，1:是)
  private Boolean isStudent;

  // 出来混是要还的，因为前端页面太多引用了，所以。。。
  // private boolean isStudent;

  // 当前用户级别
  private String currentLevel;

  // 最后登陆IP
  private String lastLoginIp;

  // 最后登陆时间
  private String lastLoginTime;

  // 用户资料完成度
  private String infoCompletePercent;

  // 身份证号
  private String idcard;
  // 英文名
  private String englishName;
  // 真实姓名
  private String realName;
  // 0:女;1:男;2:还没选
  private Integer gender;
  // 省
  private String province;
  // 市
  private String city;
  // 区/县
  private String district;
  // 通讯地址
  private String address;
  // 学习工具
  private String learnTool;
  // 个性签名
  private String personalSign;
  // 邮箱
  private String email;
  // 联系方式
  private String contractFunc;
  // 当前合同开始时间
  private Date currentOrderStartTime;

  // 当前合同结束时间
  private Date currentOrderEndTime;

  // 当前合同合同包类型
  private String coursePackageType;

  // 当前合同结束时间

  // 用户拥有的课程类型
  private Map<String, Object> courseTypes;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }

  public String getUserPhotoLarge() {
    return userPhotoLarge;
  }

  public void setUserPhotoLarge(String userPhotoLarge) {
    this.userPhotoLarge = userPhotoLarge;
  }

  public String getLastLoginIp() {
    return lastLoginIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  public String getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(String lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public Map<String, Object> getCourseTypes() {
    return courseTypes;
  }

  public void setCourseTypes(Map<String, Object> courseTypes) {
    this.courseTypes = courseTypes;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
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

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLearnTool() {
    return learnTool;
  }

  public void setLearnTool(String learnTool) {
    this.learnTool = learnTool;
  }

  public String getPersonalSign() {
    return personalSign;
  }

  public void setPersonalSign(String personalSign) {
    this.personalSign = personalSign;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContractFunc() {
    return contractFunc;
  }

  public void setContractFunc(String contractFunc) {
    this.contractFunc = contractFunc;
  }

  public boolean isStudent() {
    return isStudent;
  }

  public void setStudent(boolean isStudent) {
    this.isStudent = isStudent;
  }

  public Boolean getIsStudent() {
    return isStudent;
  }

  public void setIsStudent(Boolean isStudent) {
    this.isStudent = isStudent;
  }

  public Date getCurrentOrderStartTime() {
    return currentOrderStartTime;
  }

  public void setCurrentOrderStartTime(Date currentOrderStartTime) {
    this.currentOrderStartTime = currentOrderStartTime;
  }

  public Date getCurrentOrderEndTime() {
    return currentOrderEndTime;
  }

  public void setCurrentOrderEndTime(Date currentOrderEndTime) {
    this.currentOrderEndTime = currentOrderEndTime;
  }

  public String getCoursePackageType() {
    return coursePackageType;
  }

  public void setCoursePackageType(String coursePackageType) {
    this.coursePackageType = coursePackageType;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
  
  public String getCurrentLevel() {
    return currentLevel;
  }
 
  public void setCurrentLevel(String currentLevel) {
    this.currentLevel = currentLevel;
  }

  public String getInfoCompletePercent() {
    return infoCompletePercent;
  }

  public void setInfoCompletePercent(String infoCompletePercent) {
    this.infoCompletePercent = infoCompletePercent;
  }
}
