package com.webi.hwj.course.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category courseCommentDemo Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_course_comment_demo")
public class CourseCommentDemo implements Serializable {
  private static final long serialVersionUID = 7972813958689761575L;
  // 主键id
  private String keyId;
  // 预约课程id
  private String subscribeCourseId;
  // 评论人id
  private String fromUserId;
  // 被评论人id
  private String toUserId;
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
  // 出席状态
  private Integer subscribeStatus;
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

  public Integer getSubscribeStatus() {
    return subscribeStatus;
  }

  public void setSubscribeStatus(Integer subscribeStatus) {
    this.subscribeStatus = subscribeStatus;
  }

}