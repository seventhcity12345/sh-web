package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

public class FindSubscribeCourseCountParam implements Serializable {
  private static final long serialVersionUID = 9112952756773539221L;
  // 学员id
  private String userId;
  // 课程了类型
  private String courseType;
  // 课程数
  private int courseCount;
  // 要查询的学员id
  private List<String> userIds;
  // 课程开始时间
  private Date startTime;
  // 课程结束时间
  private Date endTime;
  // 课程级别
  private String courseLevel;
  // 合同id
  private String orderId;
  // 合同id
  private String userLevel;
  // 出席课程数
  private int showCourseCount; // 要查询的学员id
  // 课程了类型
  private List<String> courseTypes;

  private List<String> orderIds;
  //截至到前一天的日期
  private Date currentDate;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
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

  public String getCourseLevel() {
    return courseLevel;
  }

  public void setCourseLevel(String courseLevel) {
    this.courseLevel = courseLevel;
  }

  public List<String> getOrderIds() {
    return orderIds;
  }

  public void setOrderIds(List<String> orderIds) {
    this.orderIds = orderIds;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public int getShowCourseCount() {
    return showCourseCount;
  }

  public void setShowCourseCount(int showCourseCount) {
    this.showCourseCount = showCourseCount;
  }

  public String getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
  }

  public List<String> getCourseTypes() {
    return courseTypes;
  }

  public void setCourseTypes(List<String> courseTypes) {
    this.courseTypes = courseTypes;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public Date getCurrentDate() {
    return currentDate;
  }

  public void setCurrentDate(Date currentDate) {
    this.currentDate = currentDate;
  }
}
