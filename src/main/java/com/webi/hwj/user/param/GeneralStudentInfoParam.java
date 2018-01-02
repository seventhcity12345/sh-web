package com.webi.hwj.user.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

public class GeneralStudentInfoParam implements Serializable {
  private static final long serialVersionUID = 4457006480743305517L;
  // 课程包名称
  private String coursePackageName;
  // core 课程总数
  private int coreCourseCount;
  // core 剩余课程数
  private int coreRemainCourseCount;
  // extension 课程数
  private int extensionCourseCount;
  // extension剩余课程数
  private int extensionRemainCourseCount;
  // 本级已上core课程
  private int currentLevelCoreCourseCount;
  // 本级已上extension课程
  private int currentLevelExtensionCourseCount;
  // 本月RSA课件学习时长
  private String currentMonthTmmWorkingTime;
  // core出席数
  private int coreShowCourseCount;
  // extension出席数
  private int extensionShowCourseCount;
  // 合同id
  private String orderId;

  public String getCoursePackageName() {
    return coursePackageName;
  }

  public int getCoreCourseCount() {
    return coreCourseCount;
  }

  public void setCoreCourseCount(int coreCourseCount) {
    this.coreCourseCount = coreCourseCount;
  }

  public int getCoreRemainCourseCount() {
    return coreRemainCourseCount;
  }

  public void setCoreRemainCourseCount(int coreRemainCourseCount) {
    this.coreRemainCourseCount = coreRemainCourseCount;
  }

  public int getExtensionCourseCount() {
    return extensionCourseCount;
  }

  public void setExtensionCourseCount(int extensionCourseCount) {
    this.extensionCourseCount = extensionCourseCount;
  }

  public int getExtensionRemainCourseCount() {
    return extensionRemainCourseCount;
  }

  public void setExtensionRemainCourseCount(int extensionRemainCourseCount) {
    this.extensionRemainCourseCount = extensionRemainCourseCount;
  }

  public void setCoursePackageName(String coursePackageName) {
    this.coursePackageName = coursePackageName;
  }

  public int getCurrentLevelCoreCourseCount() {
    return currentLevelCoreCourseCount;
  }

  public void setCurrentLevelCoreCourseCount(int currentLevelCoreCourseCount) {
    this.currentLevelCoreCourseCount = currentLevelCoreCourseCount;
  }

  public int getCurrentLevelExtensionCourseCount() {
    return currentLevelExtensionCourseCount;
  }

  public void setCurrentLevelExtensionCourseCount(int currentLevelExtensionCourseCount) {
    this.currentLevelExtensionCourseCount = currentLevelExtensionCourseCount;
  }

  public String getCurrentMonthTmmWorkingTime() {
    return currentMonthTmmWorkingTime;
  }

  public void setCurrentMonthTmmWorkingTime(String currentMonthTmmWorkingTime) {
    this.currentMonthTmmWorkingTime = currentMonthTmmWorkingTime;
  }

  public int getCoreShowCourseCount() {
    return coreShowCourseCount;
  }

  public void setCoreShowCourseCount(int coreShowCourseCount) {
    this.coreShowCourseCount = coreShowCourseCount;
  }

  public int getExtensionShowCourseCount() {
    return extensionShowCourseCount;
  }

  public void setExtensionShowCourseCount(int extensionShowCourseCount) {
    this.extensionShowCourseCount = extensionShowCourseCount;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
