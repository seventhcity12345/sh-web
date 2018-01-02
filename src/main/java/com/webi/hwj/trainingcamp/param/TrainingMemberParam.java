package com.webi.hwj.trainingcamp.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 训练营学员管理列表<br> 
 * Description: TrainingMemberParam<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年8月9日 上午10:54:15 
 * @author komi.zsy
 */
@TableName("t_training_member")
public class TrainingMemberParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = 2970878287206280269L;
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
  
  // 变更分数事由（理由）
  private String trainingMemberOptionReason;
  // 变更分数（直接存正负）
  private Integer trainingMemberOptionScore;
  // 判断1加分，0减分
  private Boolean trainingMemberOptionType;
  
  //加分内容
  private String addScore;
  //扣分内容
  private String reductionScore;
  
  // 组合查询条件
  public String cons;

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

  public String getAddScore() {
    return addScore;
  }

  public void setAddScore(String addScore) {
    this.addScore = addScore;
  }

  public String getReductionScore() {
    return reductionScore;
  }

  public void setReductionScore(String reductionScore) {
    this.reductionScore = reductionScore;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
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
}