package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

public class StatisticsTeacherSubscribeCourseParam implements Serializable {
  private static final long serialVersionUID = -5060953992628662467L;
  // 上课日期
  private String classDate;
  // 上课时间
  private Date startTime;
  // 课程结束时间
  private Date endTime;
  // 课程名称
  private String courseTitle;
  // 老师id
  private String teacherId;
  // 老师时间id
  private String teacherTimeId;
  // 老师来源
  private String thirdFrom;
  // 老师名称
  private String teacherName;
  // 课程类型
  private String courseType;
  // 课程数
  private int courseCount;
  // 课时数
  private double courseTime;
  // 组合查询条件
  public String cons;

  public String getClassDate() {
    return classDate;
  }

  public void setClassDate(String classDate) {
    this.classDate = classDate;
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

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public int getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(int courseCount) {
    this.courseCount = courseCount;
  }

  public double getCourseTime() {
    return courseTime;
  }

  public void setCourseTime(double courseTime) {
    this.courseTime = courseTime;
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
