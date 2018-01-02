package com.webi.hwj.course.param;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * Title: 评价表+微信openid<br> 
 * Description: CourseCommentWeixinParam<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2016年10月17日 上午11:03:12 
 * @author komi.zsy
 */
public class CourseCommentWeixinParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = -6555738037421546710L;
  // 主键id
  private String keyId;
  // 微信openid
  private String weixinOpenId;
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

  public String getWeixinOpenId() {
    return weixinOpenId;
  }

  public void setWeixinOpenId(String weixinOpenId) {
    this.weixinOpenId = weixinOpenId;
  }

}