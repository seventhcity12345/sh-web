package com.webi.hwj.coursepackage.param;

import java.io.Serializable;

/**
 * Title: 课程包数据<br>
 * Description: 课程包数据<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月20日 下午3:11:40
 * 
 * @author komi.zsy
 */
public class CoursePackageParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 8258119185577355885L;
  // 课程包id
  private String keyId;
  // 体系类别
  private String categoryType;
  // 课程包名称
  private String packageName;
  // 时效性(月,只用于展示)
  private Integer limitShowTime;
  // 时效性单位0:月,1:天
  private Integer limitShowTimeUnit;
  // 跟码表里的课程类别走
  private String courseType;
  // 课节数
  private Integer courseCount;
  // 课程单位类型（0:节，1:月）
  private Integer courseUnitType;

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public Integer getLimitShowTime() {
    return limitShowTime;
  }

  public void setLimitShowTime(Integer limitShowTime) {
    this.limitShowTime = limitShowTime;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public Integer getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(Integer courseCount) {
    this.courseCount = courseCount;
  }

  public Integer getCourseUnitType() {
    return courseUnitType;
  }

  public void setCourseUnitType(Integer courseUnitType) {
    this.courseUnitType = courseUnitType;
  }

  public Integer getLimitShowTimeUnit() {
    return limitShowTimeUnit;
  }

  public void setLimitShowTimeUnit(Integer limitShowTimeUnit) {
    this.limitShowTimeUnit = limitShowTimeUnit;
  }
}