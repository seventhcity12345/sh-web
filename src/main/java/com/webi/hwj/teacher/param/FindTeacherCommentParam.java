package com.webi.hwj.teacher.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category 用于教师端-评论中心-评论列表.
 * @author mingyisoft代码生成工具
 */
public class FindTeacherCommentParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -8561927269760613741L;
  private String userId;
  private String teacherTimeId;
  private String courseType;
  private String courseTypeEnglishName;
  private String courseTitle;
  private String coursePic;
  private String teacherId;
  private Date startTime;
  private Date endTime;
  private String commentId;
  private String subscribeCourseId;
  private String preparationScore;
  private String deliveryScore;
  private String interactionScore;
  private String showScore;
  private String commentContent;
  private Boolean isTeacherComment;
  private Date updateDate;
  private String userName;
  private String userPhoto;
  private String courseTypeCheckBox;
  private Date curDate;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getCoursePic() {
    return coursePic;
  }

  public void setCoursePic(String coursePic) {
    this.coursePic = coursePic;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
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

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

  public String getSubscribeCourseId() {
    return subscribeCourseId;
  }

  public void setSubscribeCourseId(String subscribeCourseId) {
    this.subscribeCourseId = subscribeCourseId;
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

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }

  public String getCourseTypeEnglishName() {
    return courseTypeEnglishName;
  }

  public void setCourseTypeEnglishName(String courseTypeEnglishName) {
    this.courseTypeEnglishName = courseTypeEnglishName;
  }

  public String getCourseTypeCheckBox() {
    return courseTypeCheckBox;
  }

  public void setCourseTypeCheckBox(String courseTypeCheckBox) {
    this.courseTypeCheckBox = courseTypeCheckBox;
  }

  public Boolean getIsTeacherComment() {
    return isTeacherComment;
  }

  public void setIsTeacherComment(Boolean isTeacherComment) {
    this.isTeacherComment = isTeacherComment;
  }

  public Date getCurDate() {
    return curDate;
  }

  public void setCurDate(Date curDate) {
    this.curDate = curDate;
  }

}