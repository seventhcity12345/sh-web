package com.webi.hwj.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category userInfo Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_user_info")
public class UserInfo implements Serializable {
  private static final long serialVersionUID = -9131110492069157294L;
  // 主键id
  private String keyId;
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