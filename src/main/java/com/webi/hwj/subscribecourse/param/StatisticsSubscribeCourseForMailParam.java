package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

public class StatisticsSubscribeCourseForMailParam implements Serializable {
  private static final long serialVersionUID = 6597305711191077677L;
  // 课程类型
  private String courseType;
  // 总课数
  private int totalCount;
  // 异常课时数
  private int abnormalCount;
  // 正常课时数
  private int normalCount;

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getAbnormalCount() {
    return abnormalCount;
  }

  public void setAbnormalCount(int abnormalCount) {
    this.abnormalCount = abnormalCount;
  }

  public int getNormalCount() {
    return normalCount;
  }

  public void setNormalCount(int normalCount) {
    this.normalCount = normalCount;
  }

  public void addTotalCount() {
    this.totalCount++;
  }

  public void addAbnormalCount() {
    this.abnormalCount++;
  }

  public void addNormalCount() {
    this.normalCount++;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
