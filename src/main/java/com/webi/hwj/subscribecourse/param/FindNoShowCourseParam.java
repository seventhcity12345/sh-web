package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

public class FindNoShowCourseParam implements Serializable {
  private static final long serialVersionUID = 4782038927353828658L;
  // 上课时间
  private Date startTime;
  // NoShow课程名称
  private String courseTitle;
  // 学员姓名
  private String userName;
  // 英文名
  private String englishName;
  // 学员编号
  private String userCode;
  // 手机号
  private String phone;
  // LC 名字
  private String adminUserName;
  // 课程类型
  private String courseType;
  // 最后一次follow 时间
  private Date lastFollowTime;
  // 课程结束时间
  private Date endTime;
  // 组合查询条件
  public String cons;
  // 教务id
  private String learningCoachId;

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public Date getLastFollowTime() {
    return lastFollowTime;
  }

  public void setLastFollowTime(Date lastFollowTime) {
    this.lastFollowTime = lastFollowTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getLearningCoachId() {
    return learningCoachId;
  }

  public void setLearningCoachId(String learningCoachId) {
    this.learningCoachId = learningCoachId;
  }
}
