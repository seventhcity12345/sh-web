package com.webi.hwj.demo.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category demo Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_demo")
public class Demo implements Serializable {
  // 主键id
  private String keyId;
  // 手机号
  private String phone;
  // 密码(密文)
  private String pwd;
  // 初始级别
  private String initLevel;
  // 当前级别
  private String currentLevel;
  // 定级测试级别
  private String testLevel;
  // 用户资料完成度
  private Integer infoCompletePercent;
  // 放置的为user_info里的英文名称
  private String userName;
  // 用户编码
  private Integer userCode;
  // 用户照片
  private String userPhoto;
  // 用户照片(大图标)
  private String userPhotoLarge;
  // 教务id
  private String learningCoachId;
  // 是否为学员1：是。0:不是
  private Boolean isStudent;
  // 推广来源(crm)
  private String adid;
  // 最后登陆时间
  private Date lastLoginTime;
  // 最后登陆IP
  private String lastLoginIp;
  // 创建日期
  private Date createDate;
  // 最后更新日期
  private Date updateDate;
  // 创建人id
  private String createUserId;
  // 最后更新人id
  private String updateUserId;
  // 是否使用(1:使用,0:不使用)
  private Boolean isUsed;

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

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getInitLevel() {
    return initLevel;
  }

  public void setInitLevel(String initLevel) {
    this.initLevel = initLevel;
  }

  public String getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(String currentLevel) {
    this.currentLevel = currentLevel;
  }

  public String getTestLevel() {
    return testLevel;
  }

  public void setTestLevel(String testLevel) {
    this.testLevel = testLevel;
  }

  public Integer getInfoCompletePercent() {
    return infoCompletePercent;
  }

  public void setInfoCompletePercent(Integer infoCompletePercent) {
    this.infoCompletePercent = infoCompletePercent;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getUserCode() {
    return userCode;
  }

  public void setUserCode(Integer userCode) {
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

  public String getLearningCoachId() {
    return learningCoachId;
  }

  public void setLearningCoachId(String learningCoachId) {
    this.learningCoachId = learningCoachId;
  }

  public Boolean getIsStudent() {
    return isStudent;
  }

  public void setIsStudent(Boolean isStudent) {
    this.isStudent = isStudent;
  }

  public String getAdid() {
    return adid;
  }

  public void setAdid(String adid) {
    this.adid = adid;
  }

  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getLastLoginIp() {
    return lastLoginIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }

  public Boolean getIsUsed() {
    return isUsed;
  }

  public void setIsUsed(Boolean isUsed) {
    this.isUsed = isUsed;
  }

}