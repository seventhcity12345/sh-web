package com.webi.hwj.course.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

public class FindToTeacherAverageScroreParam implements Serializable {
  private static final long serialVersionUID = -1986393024290483761L;
  // 老师名字
  private String teacherName;
  // 老师来源
  private String thirdFrom;
  // 准备度分数
  private String preparationScore;
  // 专业度分数
  private String deliveryScore;
  // 互动性分数
  private String interactionScore;
  // 显示分数(平均分)
  private String showScore;
  // 评价数
  private String commentCount;
  //
  private List<String> subscribeIds;

  public String cons;

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
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

  public String getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(String commentCount) {
    this.commentCount = commentCount;
  }

  public List<String> getSubscribeIds() {
    return subscribeIds;
  }

  public void setSubscribeIds(List<String> subscribeIds) {
    this.subscribeIds = subscribeIds;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
