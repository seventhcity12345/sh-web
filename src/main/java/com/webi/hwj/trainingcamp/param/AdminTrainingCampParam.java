package com.webi.hwj.trainingcamp.param;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * 
 * Title: 训练营参数类，为组合查询<br>
 * Description: 训练营参数类，为组合查询<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月8日 下午4:36:34
 * 
 * @author komi.zsy
 */
@TableName("t_training_camp")
public class AdminTrainingCampParam implements Serializable {
  // 主键
  private String keyId;
  // 标题
  private String trainingCampTitle;
  // 简介
  private String trainingCampDesc;
  // 当前训练营人数
  private Integer trainingCampNum;
  // 封面
  private String trainingCampPic;
  // 生效开始时间
  private Date trainingCampStartTime;
  // 生效结束时间
  private Date trainingCampEndTime;
  // 训练营平均分
  private Integer trainingCampAverageScore;
  // lc id
  private String trainingCampLearningCoachId;
  // 学员平均等级
  private Integer trainingCampAverageLevel;
  // 昨日活跃人数
  private Integer trainingCampYesterdayActivity;
  // 学员平均年龄
  private Integer trainingCampAverageAge;
  // 女性占比
  private Integer trainingCampFemalePresent;
  // 创建时间
  private Date createDate;
  // 修改时间
  private Date updateDate;
  // 创建人id
  private String createUserId;
  // 最后更新人id
  private String updateUserId;
  // 是否使用(1:使用,0:不使用)
  private Boolean isUsed;
  
  // lc用户名
  private String account;

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

  public String getTrainingCampTitle() {
    return trainingCampTitle;
  }

  public void setTrainingCampTitle(String trainingCampTitle) {
    this.trainingCampTitle = trainingCampTitle;
  }

  public String getTrainingCampDesc() {
    return trainingCampDesc;
  }

  public void setTrainingCampDesc(String trainingCampDesc) {
    this.trainingCampDesc = trainingCampDesc;
  }

  public Integer getTrainingCampNum() {
    return trainingCampNum;
  }

  public void setTrainingCampNum(Integer trainingCampNum) {
    this.trainingCampNum = trainingCampNum;
  }

  public String getTrainingCampPic() {
    return trainingCampPic;
  }

  public void setTrainingCampPic(String trainingCampPic) {
    this.trainingCampPic = trainingCampPic;
  }

  public Date getTrainingCampStartTime() {
    return trainingCampStartTime;
  }

  public void setTrainingCampStartTime(Date trainingCampStartTime) {
    this.trainingCampStartTime = trainingCampStartTime;
  }

  public Date getTrainingCampEndTime() {
    return trainingCampEndTime;
  }

  public void setTrainingCampEndTime(Date trainingCampEndTime) {
    this.trainingCampEndTime = trainingCampEndTime;
  }

  public Integer getTrainingCampAverageScore() {
    return trainingCampAverageScore;
  }

  public void setTrainingCampAverageScore(Integer trainingCampAverageScore) {
    this.trainingCampAverageScore = trainingCampAverageScore;
  }

  public String getTrainingCampLearningCoachId() {
    return trainingCampLearningCoachId;
  }

  public void setTrainingCampLearningCoachId(String trainingCampLearningCoachId) {
    this.trainingCampLearningCoachId = trainingCampLearningCoachId;
  }

  public Integer getTrainingCampAverageLevel() {
    return trainingCampAverageLevel;
  }

  public void setTrainingCampAverageLevel(Integer trainingCampAverageLevel) {
    this.trainingCampAverageLevel = trainingCampAverageLevel;
  }

  public Integer getTrainingCampYesterdayActivity() {
    return trainingCampYesterdayActivity;
  }

  public void setTrainingCampYesterdayActivity(Integer trainingCampYesterdayActivity) {
    this.trainingCampYesterdayActivity = trainingCampYesterdayActivity;
  }

  public Integer getTrainingCampAverageAge() {
    return trainingCampAverageAge;
  }

  public void setTrainingCampAverageAge(Integer trainingCampAverageAge) {
    this.trainingCampAverageAge = trainingCampAverageAge;
  }

  public Integer getTrainingCampFemalePresent() {
    return trainingCampFemalePresent;
  }

  public void setTrainingCampFemalePresent(Integer trainingCampFemalePresent) {
    this.trainingCampFemalePresent = trainingCampFemalePresent;
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

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

}