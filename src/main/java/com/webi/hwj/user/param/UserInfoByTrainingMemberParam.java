package com.webi.hwj.user.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

public class UserInfoByTrainingMemberParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = 3222680625949993937L;
  // 主键id
  private String keyId;
  // 手机号
  private String phone;
  // 当前级别
  private String currentLevel;
  // 用户编码
  private Integer userCode;
  // 用户照片
  private String userPhoto;
  // 身份证号
  private String idcard;
  // 英文名
  private String englishName;
  // 真实姓名
  private String realName;
  // 0:女;1:男;2:还没选
  private Integer gender;
  
  private List<String> keyIds;

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

  public String getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(String currentLevel) {
    this.currentLevel = currentLevel;
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

  public List<String> getKeyIds() {
    return keyIds;
  }

  public void setKeyIds(List<String> keyIds) {
    this.keyIds = keyIds;
  }
}