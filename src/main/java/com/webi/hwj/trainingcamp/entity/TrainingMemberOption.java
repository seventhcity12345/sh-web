package com.webi.hwj.trainingcamp.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category trainingMemberOption Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_training_member_option")
public class TrainingMemberOption implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = -7425126730315238663L;
  // 主键id
  private String keyId;
  // 训练营主表id
  private String trainingCampId;
  //学员id
  private String trainingMemberUserId;
  // 变更分数事由（理由）
  private String trainingMemberOptionReason;
  // 变更分数（直接存正负）
  private Integer trainingMemberOptionScore;
  // 判断1加分，0减分
  private Boolean trainingMemberOptionType;
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

  public String getTrainingMemberOptionReason() {
    return trainingMemberOptionReason;
  }

  public void setTrainingMemberOptionReason(String trainingMemberOptionReason) {
    this.trainingMemberOptionReason = trainingMemberOptionReason;
  }

  public Integer getTrainingMemberOptionScore() {
    return trainingMemberOptionScore;
  }

  public void setTrainingMemberOptionScore(Integer trainingMemberOptionScore) {
    this.trainingMemberOptionScore = trainingMemberOptionScore;
  }

  public Boolean getTrainingMemberOptionType() {
    return trainingMemberOptionType;
  }

  public void setTrainingMemberOptionType(Boolean trainingMemberOptionType) {
    this.trainingMemberOptionType = trainingMemberOptionType;
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

}