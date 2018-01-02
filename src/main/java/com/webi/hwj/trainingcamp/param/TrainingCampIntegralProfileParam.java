package com.webi.hwj.trainingcamp.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 积分概况信息<br>
 * Description: 积分概况信息<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月8日 下午7:38:31
 * 
 * @author felix.yl
 */
@ApiModel(value = "TrainingCampIntegralProfileParm(成人-口语训练营-积分概况模型)")
public class TrainingCampIntegralProfileParam implements Serializable {

  private static final long serialVersionUID = 2094213360035486431L;

  @ApiModelProperty(value = "keyId(训练表的主键,前端无需理睬)", example = "72dfe41853e64fef95dcf742de71cfd1")
  private String keyId;// 训练营表的主键

  @ApiModelProperty(value = "个人总积分", example = "180")
  private Integer trainingMemberTotalScore;// 个人总积分

  @ApiModelProperty(value = "RSA课件学习分", example = "60")
  private Integer trainingMemberRsaScore;// RSA课件学习分

  @ApiModelProperty(value = "预约上课分", example = "60")
  private Integer trainingMemberCourseScore;// 预约上课分

  @ApiModelProperty(value = "战队活动分(加分总分)", example = "15")
  private Integer trainingMemberOptionScore;// 战队活动分(加分总分)

  @ApiModelProperty(value = "团队奖励分", example = "5")
  private Integer trainingMemberBonusScore;// 团队奖励分

  @ApiModelProperty(value = "今日学习时间(单位分钟)", example = "120")
  private String todayStudyTime;// 今日学习时间

  @ApiModelProperty(value = "学员id(查询用,前端无需理睬)", example = "72dfe41853e64fef95dcf742de71cfd1")
  private String trainingMemberUserId;

  @ApiModelProperty(value = "当前时间(查询用,前端无需理睬)", example = "2017-08-08")
  private Date currentTime;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
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

  public Integer getTrainingMemberOptionScore() {
    return trainingMemberOptionScore;
  }

  public void setTrainingMemberOptionScore(Integer trainingMemberOptionScore) {
    this.trainingMemberOptionScore = trainingMemberOptionScore;
  }

  public Integer getTrainingMemberBonusScore() {
    return trainingMemberBonusScore;
  }

  public void setTrainingMemberBonusScore(Integer trainingMemberBonusScore) {
    this.trainingMemberBonusScore = trainingMemberBonusScore;
  }

  public String getTodayStudyTime() {
    return todayStudyTime;
  }

  public void setTodayStudyTime(String todayStudyTime) {
    this.todayStudyTime = todayStudyTime;
  }

  public String getTrainingMemberUserId() {
    return trainingMemberUserId;
  }

  public void setTrainingMemberUserId(String trainingMemberUserId) {
    this.trainingMemberUserId = trainingMemberUserId;
  }

  public Date getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(Date currentTime) {
    this.currentTime = currentTime;
  }

}
