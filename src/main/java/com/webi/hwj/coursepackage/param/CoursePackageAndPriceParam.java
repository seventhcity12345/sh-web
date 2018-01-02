package com.webi.hwj.coursepackage.param;

import java.io.Serializable;

/**
 * Title: 课程包数据<br>
 * Description: 课程包数据<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年8月29日 下午3:11:40
 * 
 * @author komi.zsy
 */
public class CoursePackageAndPriceParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -1122267194992038368L;
  // 课程包id
  private String keyId;
  // 价格策略主表id
  private String coursePackagePriceId;
  // 价格策略子表id
  private String coursePackagePriceOptionId;
  // 课程包id
  private String coursePackageId;
  // 体系类别
  private String categoryType;
  // 课程包名称
  private String packageName;
  // 时效性(月,只用于展示)
  private Integer limitShowTime;
  // 时效性单位0:月,1:天
  private Integer limitShowTimeUnit;
  // 课程包类型0:standard,1:premium,2:basic
  private Integer coursePackageType;
  // 跟码表里的课程类别走
  private String courseType;
  // 课节数
  private Integer courseCount;
  // 课程单位类型（0:节，1:月）
  private Integer courseUnitType;

  // 原价
  private Integer coursePackageShowPrice;
  // 优惠价
  private Integer coursePackageRealPrice;
  // 单价版本号
  private Integer coursePriceVersion;

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

  public String getCoursePackagePriceId() {
    return coursePackagePriceId;
  }

  public void setCoursePackagePriceId(String coursePackagePriceId) {
    this.coursePackagePriceId = coursePackagePriceId;
  }

  public String getCoursePackagePriceOptionId() {
    return coursePackagePriceOptionId;
  }

  public void setCoursePackagePriceOptionId(String coursePackagePriceOptionId) {
    this.coursePackagePriceOptionId = coursePackagePriceOptionId;
  }

  public Integer getCoursePackageShowPrice() {
    return coursePackageShowPrice;
  }

  public void setCoursePackageShowPrice(Integer coursePackageShowPrice) {
    this.coursePackageShowPrice = coursePackageShowPrice;
  }

  public Integer getCoursePackageRealPrice() {
    return coursePackageRealPrice;
  }

  public void setCoursePackageRealPrice(Integer coursePackageRealPrice) {
    this.coursePackageRealPrice = coursePackageRealPrice;
  }

  public Integer getCoursePriceVersion() {
    return coursePriceVersion;
  }

  public void setCoursePriceVersion(Integer coursePriceVersion) {
    this.coursePriceVersion = coursePriceVersion;
  }

  public Integer getLimitShowTimeUnit() {
    return limitShowTimeUnit;
  }

  public void setLimitShowTimeUnit(Integer limitShowTimeUnit) {
    this.limitShowTimeUnit = limitShowTimeUnit;
  }

  public String getCoursePackageId() {
    return coursePackageId;
  }

  public void setCoursePackageId(String coursePackageId) {
    this.coursePackageId = coursePackageId;
  }

  public Integer getCoursePackageType() {
    return coursePackageType;
  }

  public void setCoursePackageType(Integer coursePackageType) {
    this.coursePackageType = coursePackageType;
  }
}