package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 查询订课记录列表<br>
 * Description: 查询订课记录列表<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月14日 上午11:25:20
 * 
 * @author komi.zsy
 */
public class SubscribeCourseListParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 1982109176347059607L;
  // 主键id
  private String keyId;
  // 用户id
  private String userId;
  // 准备度分数（用于判断学生是否已评价）
  private String preparationScore;
  // 跟码表里的课程类别走
  private String courseType;
  // 跟码表里的课程类别走
  private String courseTypeChineseName;
  // 课程标题
  private String courseTitle;
  // 课程类型限制人数，如果是0则页面显示为N。用于上课形式
  private Integer courseTypeLimitNumber;
  // 课程状态
  // 0.已完成已评价、1.已完成未评价（显示待评价标签）、2.已预约且当天开课（显示即将开课标签（当前时间到当天24点内））、3.已预约且非当天开课
  private Integer courseStatus;
  // 上课开始时间
  private Date startTime;
  // 上课结束时间
  private Date endTime;
  // 老师名称
  private String teacherName;
  // 预约状态(0:未上课,1:已上课)
  private Integer subscribeStatus;

  // modified by alex 2016年12月26日 19:52:24 添加老师是否已出席
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

  public Integer getSubscribeStatus() {
    return subscribeStatus;
  }

  public void setSubscribeStatus(Integer subscribeStatus) {
    this.subscribeStatus = subscribeStatus;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public Integer getIsAttend() {
    return isAttend;
  }

  public void setIsAttend(Integer isAttend) {
    this.isAttend = isAttend;
  }

}