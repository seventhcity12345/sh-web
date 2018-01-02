package com.webi.hwj.weixin.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

public class LearningPathTypeInfoParm implements Serializable {
  private static final long serialVersionUID = -5890563937139438834L;
  // 课程类型
  private String courseType;
  // 完成课时数
  private Integer completeCourseCount;
  // 升级需要课时数
  private Integer levelUpCourseCount;
  // 课程单元的列表
  private List<Integer> courseUnitList;

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public Integer getCompleteCourseCount() {
    return completeCourseCount;
  }

  public void setCompleteCourseCount(Integer completeCourseCount) {
    this.completeCourseCount = completeCourseCount;
  }

  public Integer getLevelUpCourseCount() {
    return levelUpCourseCount;
  }

  public void setLevelUpCourseCount(Integer levelUpCourseCount) {
    this.levelUpCourseCount = levelUpCourseCount;
  }

  public List<Integer> getCourseUnitList() {
    return courseUnitList;
  }

  public void setCourseUnitList(List<Integer> courseUnitList) {
    this.courseUnitList = courseUnitList;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
