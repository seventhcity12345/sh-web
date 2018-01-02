package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 订课详情及其评论<br>
 * Description: 订课详情及其评论<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月14日 上午11:25:20
 * 
 * @author komi.zsy
 */
public class SubscribeCourseAndCommentParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 192462260899977531L;
  // 主键id
  private String keyId;
  // 用户id
  private String userId;
  // 跟码表里的课程类别走
  private String courseType;
  // 跟码表里的课程类别走
  private String courseTypeChineseName;
  // 课程标题
  private String courseTitle;
  // 课程类型限制人数，如果是0则页面显示为N。用于上课形式
  private Integer courseTypeLimitNumber;
  // 课程状态 0:已完成未出席、1:已完成未出席已完成已出席（只有这个状态才有课程回顾按钮）、2:已预约未完成
  private Integer courseStatus;
  // 上课开始时间
  private Date startTime;
  // 上课结束时间
  private Date endTime;
  // 课程图片
  private String coursePic;
  // 课件ppt地址
  private String courseCourseware;
  // 老师时间ID（用于查看录像）
  private String teacherTimeId;
  // 老师ID
  private String teacherId;
  // 老师名称
  private String teacherName;
  // 老师头像
  private String teacherPhoto;
  // 老师国籍
  private String teacherNationality;
  /**
   * 老师给学生的评价
   */
  // 老师给学生评论平均分
  private String teacherShowScore;
  // 发音分数
  private String pronouncationScore;
  // 语法分数
  private String grammerScore;
  // 词汇量分数
  private String vocabularyScore;
  // 听力分数
  private String listeningScore;
  // 老师的评论
  private String teacherCommentContent;
  /**
   * 学生给老师的评价
   */
  // 学生给老师评论平均分
  private String studentShowScore;
  // 专业度分数
  private String deliveryScore;
  // 互动性分数
  private String interactionScore;
  // 准备度分数
  private String preparationScore;
  // 学生的评论
  private String studentCommentContent;

  // 老师是否已出席 modified by alex 2016年12月26日 20:14:50
  private Integer isAttend;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getCourseTypeChineseName() {
    return courseTypeChineseName;
  }

  public void setCourseTypeChineseName(String courseTypeChineseName) {
    this.courseTypeChineseName = courseTypeChineseName;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public Integer getCourseTypeLimitNumber() {
    return courseTypeLimitNumber;
  }

  public void setCourseTypeLimitNumber(Integer courseTypeLimitNumber) {
    this.courseTypeLimitNumber = courseTypeLimitNumber;
  }

  public Integer getCourseStatus() {
    return courseStatus;
  }

  public void setCourseStatus(Integer courseStatus) {
    this.courseStatus = courseStatus;
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

  public String getPreparationScore() {
    return preparationScore;
  }

  public void setPreparationScore(String preparationScore) {
    this.preparationScore = preparationScore;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCoursePic() {
    return coursePic;
  }

  public void setCoursePic(String coursePic) {
    this.coursePic = coursePic;
  }

  public String getCourseCourseware() {
    return courseCourseware;
  }

  public void setCourseCourseware(String courseCourseware) {
    this.courseCourseware = courseCourseware;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getTeacherPhoto() {
    return teacherPhoto;
  }

  public void setTeacherPhoto(String teacherPhoto) {
    this.teacherPhoto = teacherPhoto;
  }

  public String getTeacherNationality() {
    return teacherNationality;
  }

  public void setTeacherNationality(String teacherNationality) {
    this.teacherNationality = teacherNationality;
  }

  public String getTeacherShowScore() {
    return teacherShowScore;
  }

  public void setTeacherShowScore(String teacherShowScore) {
    this.teacherShowScore = teacherShowScore;
  }

  public String getPronouncationScore() {
    return pronouncationScore;
  }

  public void setPronouncationScore(String pronouncationScore) {
    this.pronouncationScore = pronouncationScore;
  }

  public String getGrammerScore() {
    return grammerScore;
  }

  public void setGrammerScore(String grammerScore) {
    this.grammerScore = grammerScore;
  }

  public String getVocabularyScore() {
    return vocabularyScore;
  }

  public void setVocabularyScore(String vocabularyScore) {
    this.vocabularyScore = vocabularyScore;
  }

  public String getListeningScore() {
    return listeningScore;
  }

  public void setListeningScore(String listeningScore) {
    this.listeningScore = listeningScore;
  }

  public String getTeacherCommentContent() {
    return teacherCommentContent;
  }

  public void setTeacherCommentContent(String teacherCommentContent) {
    this.teacherCommentContent = teacherCommentContent;
  }

  public String getStudentShowScore() {
    return studentShowScore;
  }

  public void setStudentShowScore(String studentShowScore) {
    this.studentShowScore = studentShowScore;
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

  public String getStudentCommentContent() {
    return studentCommentContent;
  }

  public void setStudentCommentContent(String studentCommentContent) {
    this.studentCommentContent = studentCommentContent;
  }

  public Integer getIsAttend() {
    return isAttend;
  }

  public void setIsAttend(Integer isAttend) {
    this.isAttend = isAttend;
  }

}