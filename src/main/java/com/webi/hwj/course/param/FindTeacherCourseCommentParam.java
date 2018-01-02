package com.webi.hwj.course.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

public class FindTeacherCourseCommentParam implements Serializable {
  private static final long serialVersionUID = 3713880789166216912L;
  // 课程名称
  private String courseTitle;
  // 课程开始时间
  private Date startTime;
  // 课程结束时间
  private Date endTime;
  // 老师来源
  private String thirdFrom;
  // 学生姓名
  private String userName;
  // 学生编号
  private String userCode;
  // 评论内容
  private String commentContent;
  // 老师名字
  private String teacherName;
  // 准备度分数
  private String preparationScore;
  // 专业度分数
  private String deliveryScore;
  // 互动性分数
  private String interactionScore;
  // 显示分数(平均分)
  private String showScore;
  //
  private List<String> subscribeIds;

  public String cons;

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

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

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
