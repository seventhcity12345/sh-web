package com.webi.hwj.course.param;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category courseCommentDemo Entity
 * @author mingyisoft代码生成工具
 */
public class CourseCommentDemoParam implements Serializable {
  private static final long serialVersionUID = -3025393568354830687L;
  // 主键id
  private String keyId;
  // 预约课程id
  @NotNull(message = "预约id不能为空")
  private String subscribeCourseId;
  // 评论人id
  private String fromUserId;
  // 被评论人id
  private String toUserId;
  // 老师名称
  private String teacherName;
  // 学生姓名
  private String userName;
  // 听力理解等级
  private Integer comprehensionLevel;
  // 发音等级
  private Integer pronunciationLevel;
  // 词汇等级
  private Integer vocabularyLevel;
  // 语法等级
  private Integer grammerLevel;
  // 流利度等级
  private Integer fluencyLevel;
  // 优点
  private String goodPoints;
  // 需要提高
  private String needToImprove;
  // 进度计划
  private String progressPlan;
  // 最后更新日期
  private Date updateDate;

  // 预约状态(0:未上课,1:已上课)
  @NotNull(message = "subscribeStatus不能为空")
  private Integer subscribeStatus;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getSubscribeCourseId() {
    return subscribeCourseId;
  }

  public void setSubscribeCourseId(String subscribeCourseId) {
    this.subscribeCourseId = subscribeCourseId;
  }

  public String getFromUserId() {
    return fromUserId;
  }

  public void setFromUserId(String fromUserId) {
    this.fromUserId = fromUserId;
  }

  public String getToUserId() {
    return toUserId;
  }

  public void setToUserId(String toUserId) {
    this.toUserId = toUserId;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getComprehensionLevel() {
    return comprehensionLevel;
  }

  public void setComprehensionLevel(Integer comprehensionLevel) {
    this.comprehensionLevel = comprehensionLevel;
  }

  public Integer getPronunciationLevel() {
    return pronunciationLevel;
  }

  public void setPronunciationLevel(Integer pronunciationLevel) {
    this.pronunciationLevel = pronunciationLevel;
  }

  public Integer getVocabularyLevel() {
    return vocabularyLevel;
  }

  public void setVocabularyLevel(Integer vocabularyLevel) {
    this.vocabularyLevel = vocabularyLevel;
  }

  public Integer getGrammerLevel() {
    return grammerLevel;
  }

  public void setGrammerLevel(Integer grammerLevel) {
    this.grammerLevel = grammerLevel;
  }

  public Integer getFluencyLevel() {
    return fluencyLevel;
  }

  public void setFluencyLevel(Integer fluencyLevel) {
    this.fluencyLevel = fluencyLevel;
  }

  public String getGoodPoints() {
    return goodPoints;
  }

  public void setGoodPoints(String goodPoints) {
    this.goodPoints = goodPoints;
  }

  public String getNeedToImprove() {
    return needToImprove;
  }

  public void setNeedToImprove(String needToImprove) {
    this.needToImprove = needToImprove;
  }

  public String getProgressPlan() {
    return progressPlan;
  }

  public void setProgressPlan(String progressPlan) {
    this.progressPlan = progressPlan;
  }

  public Integer getSubscribeStatus() {
    return subscribeStatus;
  }

  public void setSubscribeStatus(Integer subscribeStatus) {
    this.subscribeStatus = subscribeStatus;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
}