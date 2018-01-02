package com.webi.hwj.coursepackage.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category coursePackage Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_course_package")
public class CoursePackage implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 2008193333390655417L;
  // 主键id
  private String keyId;
  // 体系类别
  private String categoryType;
  // 课程包名称
  private String packageName;
  // 时效性(只用于展示)
  private Integer limitShowTime;
  // 时效性单位0:月,1:天
  private Integer limitShowTimeUnit;
  // 课程包类型0:standard,1:premium,2:basic
  private Integer coursePackageType;
  // 创建日期
  private Date createDate;
  // 最后更新日期
  private Date updateDate;
  // 创建人id
  private String createUserId;
  // 最后更新人id
  private String updateUserId;
  // 是否使用(1:使用,0:不使用)
  private Boolean isUsed;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

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

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }

  public Boolean getIsUsed() {
    return isUsed;
  }

  public void setIsUsed(Boolean isUsed) {
    this.isUsed = isUsed;
  }

}