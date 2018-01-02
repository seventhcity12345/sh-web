package com.webi.hwj.trainingcamp.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category trainingMember Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_training_member")
public class TrainingMember implements Serializable {
  // 主键id
  private String keyId;
  // 训练营id
  private String trainingCampId;
  // 学员id
  private String trainingMemberUserId;
  // 用户编码
  private Integer trainingMemberUserCode;
  // 英文名
  private String trainingMemberEnglishName;
  // 真实姓名
  private String trainingMemberRealName;
  // 0:女;1:男;2:还没选
  private Integer trainingMemberGender;
  // 年龄
  private Integer trainingMemberAge;
  // 用户照片
  private String trainingMemberPic;
  // 手机号
  private String trainingMemberPhone;
  // 当前级别
  private String trainingMemberCurrentLevel;
  // 个人总积分
  private Integer trainingMemberTotalScore;
  // rsa学习积分
  private Integer trainingMemberRsaScore;
  // 上课积分
  private Integer trainingMemberCourseScore;
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
  // 数据描述
  private String dataDesc;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getTrainingCampId() {
    return trainingCampId;
  }

  public void setTrainingCampId(String trainingCampId) {
    this.trainingCampId = trainingCampId;
  }

  public String getTrainingMemberUserId() {
    return trainingMemberUserId;
  }

  public void setTrainingMemberUserId(String trainingMemberUserId) {
    this.trainingMemberUserId = trainingMemberUserId;
  }

  public Integer getTrainingMemberUserCode() {
    return trainingMemberUserCode;
  }

  public void setTrainingMemberUserCode(Integer trainingMemberUserCode) {
    this.trainingMemberUserCode = trainingMemberUserCode;
  }

  public String getTrainingMemberEnglishName() {
    return trainingMemberEnglishName;
  }

  public void setTrainingMemberEnglishName(String trainingMemberEnglishName) {
    this.trainingMemberEnglishName = trainingMemberEnglishName;
  }

  public String getTrainingMemberRealName() {
    return trainingMemberRealName;
  }

  public void setTrainingMemberRealName(String trainingMemberRealName) {
    this.trainingMemberRealName = trainingMemberRealName;
  }

  public Integer getTrainingMemberGender() {
    return trainingMemberGender;
  }

  public void setTrainingMemberGender(Integer trainingMemberGender) {
    this.trainingMemberGender = trainingMemberGender;
  }

  public String getTrainingMemberPic() {
    return trainingMemberPic;
  }

  public void setTrainingMemberPic(String trainingMemberPic) {
    this.trainingMemberPic = trainingMemberPic;
  }

  public String getTrainingMemberPhone() {
    return trainingMemberPhone;
  }

  public void setTrainingMemberPhone(String trainingMemberPhone) {
    this.trainingMemberPhone = trainingMemberPhone;
  }

  public String getTrainingMemberCurrentLevel() {
    return trainingMemberCurrentLevel;
  }

  public void setTrainingMemberCurrentLevel(String trainingMemberCurrentLevel) {
    this.trainingMemberCurrentLevel = trainingMemberCurrentLevel;
  }

  public Integer getTrainingMemberTotalScore() {
    return trainingMemberTotalScore;
  }

  public void setTrainingMemberTotalScore(Integer trainingMemberTotalScore) {
    this.trainingMemberTotalScore = trainingMemberTotalScore;
  }

  public Integer getTrainingMemberRsaScore() {
    return trainingMemberRsaScore;
  }

  public void setTrainingMemberRsaScore(Integer trainingMemberRsaScore) {
    this.trainingMemberRsaScore = trainingMemberRsaScore;
  }

  public Integer getTrainingMemberCourseScore() {
    return trainingMemberCourseScore;
  }

  public void setTrainingMemberCourseScore(Integer trainingMemberCourseScore) {
    this.trainingMemberCourseScore = trainingMemberCourseScore;
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

  public String getDataDesc() {
    return dataDesc;
  }

  public void setDataDesc(String dataDesc) {
    this.dataDesc = dataDesc;
  }

  public Integer getTrainingMemberAge() {
    return trainingMemberAge;
  }

  public void setTrainingMemberAge(Integer trainingMemberAge) {
    this.trainingMemberAge = trainingMemberAge;
  }

}