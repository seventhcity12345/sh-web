package com.webi.hwj.coursepackage.param;

import java.io.Serializable;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category coursePackageOption Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_course_package_option")
public class CoursePackageOptionAndPriceParam implements Serializable {

  /** 
  * 
  */
  private static final long serialVersionUID = 8980102668205100922L;
  // 主键id
  private String keyId;
  // 课程包id
  private String coursePackageId;
  // 跟码表里的课程类别走
  private String courseType;
  // 前端使用的原始id（类似course_type1）
  private String courseTypeId;
  // 课节数
  private Integer courseCount;
  // 课程单位类型（0:节,1:月,2:天）
  private Integer courseUnitType;
  // 价格版本号
  private Integer coursePriceVersion;
  // 课程包类型0:standard,1:premium,2:basic
  private Integer coursePackageType;
  // 单价
  private Integer coursePriceUnitPrice;

  // 体系类别
  private String categoryType;
  // 课程包名称
  private String packageName;
  // 时效性(只用于展示)
  private Integer limitShowTime;
  // 时效性单位0:月,1:天
  private Integer limitShowTimeUnit;

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

  public Integer getCoursePriceUnitPrice() {
    return coursePriceUnitPrice;
  }

  public void setCoursePriceUnitPrice(Integer coursePriceUnitPrice) {
    this.coursePriceUnitPrice = coursePriceUnitPrice;
  }

  public Integer getCoursePriceVersion() {
    return coursePriceVersion;
  }

  public void setCoursePriceVersion(Integer coursePriceVersion) {
    this.coursePriceVersion = coursePriceVersion;
  }

  public String getCoursePackageId() {
    return coursePackageId;
  }

  public void setCoursePackageId(String coursePackageId) {
    this.coursePackageId = coursePackageId;
  }

  public String getCourseTypeId() {
    return courseTypeId;
  }

  public void setCourseTypeId(String courseTypeId) {
    this.courseTypeId = courseTypeId;
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

  public Integer getLimitShowTimeUnit() {
    return limitShowTimeUnit;
  }

  public void setLimitShowTimeUnit(Integer limitShowTimeUnit) {
    this.limitShowTimeUnit = limitShowTimeUnit;
  }

  public Integer getCoursePackageType() {
    return coursePackageType;
  }

  public void setCoursePackageType(Integer coursePackageType) {
    this.coursePackageType = coursePackageType;
  }
}