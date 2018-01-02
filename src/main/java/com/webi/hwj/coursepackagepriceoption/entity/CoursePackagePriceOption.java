package com.webi.hwj.coursepackagepriceoption.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category coursePackagePriceOption Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_course_package_price_option")
public class CoursePackagePriceOption implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -753066284050204826L;
  // 主键id
  private String keyId;
  // 主表id
  private String coursePackagePriceId;
  // 课程包id
  private String coursePackageId;
  // 原价
  private Integer coursePackageShowPrice;
  // 优惠价
  private Integer coursePackageRealPrice;
  // 单价版本号
  private Integer coursePriceVersion;
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

  public String getCoursePackagePriceId() {
    return coursePackagePriceId;
  }

  public void setCoursePackagePriceId(String coursePackagePriceId) {
    this.coursePackagePriceId = coursePackagePriceId;
  }

  public String getCoursePackageId() {
    return coursePackageId;
  }

  public void setCoursePackageId(String coursePackageId) {
    this.coursePackageId = coursePackageId;
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