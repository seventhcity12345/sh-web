package com.webi.hwj.course.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category courseComment Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_course_comment")
public class CourseComment implements Serializable {
  private static final long serialVersionUID = 6053179883850088889L;
  // 主键id
  private String keyId;
  // 预约课程id
  private String subscribeCourseId;
  // 评论人id
  private String fromUserId;
  // 被评论人id
  private String toUserId;
  // 发音分数
  private String pronouncationScore;
  // 词汇量分数
  private String vocabularyScore;
  // 语法分数
  private String grammerScore;
  // 听力分数
  private String listeningScore;
  // 准备度分数
  private String preparationScore;
  // 专业度分数
  private String deliveryScore;
  // 互动性分数
  private String interactionScore;
  // 显示分数(平均分)
  private String showScore;
  // 评论内容
  private String commentContent;
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

  public String getPronouncationScore() {
    return pronouncationScore;
  }

  public void setPronouncationScore(String pronouncationScore) {
    this.pronouncationScore = pronouncationScore;
  }

  public String getVocabularyScore() {
    return vocabularyScore;
  }

  public void setVocabularyScore(String vocabularyScore) {
    this.vocabularyScore = vocabularyScore;
  }

  public String getGrammerScore() {
    return grammerScore;
  }

  public void setGrammerScore(String grammerScore) {
    this.grammerScore = grammerScore;
  }

  public String getListeningScore() {
    return listeningScore;
  }

  public void setListeningScore(String listeningScore) {
    this.listeningScore = listeningScore;
  }

  public String getPreparationScore() {
    return preparationScore;
  }

  public void setPreparationScore(String preparationScore) {
    this.preparationScore = preparationScore;
  }

  public String getDeliveryScore() {
    return deliveryScore;
  }

  public void setDeliveryScore(String deliveryScore) {
    this.deliveryScore = deliveryScore;
  }

  public String getInteractionScore() {
    return interactionScore;
  }

  public void setInteractionScore(String interactionScore) {
    this.interactionScore = interactionScore;
  }

  public String getShowScore() {
    return showScore;
  }

  public void setShowScore(String showScore) {
    this.showScore = showScore;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
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