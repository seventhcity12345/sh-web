package com.webi.hwj.courseprice.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category coursePrice Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_course_price")
public class CoursePrice implements Serializable {
  // 主键id
  private String keyId;
  // 价格版本号
  private Integer coursePriceVersion;
  // 所选课程类型
  private String courseType;
  // 单价
  private Integer coursePriceUnitPrice;
  // 单位（0:每节;1:每月;2:每天）
  private Integer coursePriceUnitType;
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

  public Integer getCoursePriceVersion() {
    return coursePriceVersion;
  }

  public void setCoursePriceVersion(Integer coursePriceVersion) {
    this.coursePriceVersion = coursePriceVersion;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public Integer getCoursePriceUnitPrice() {
    return coursePriceUnitPrice;
  }

  public void setCoursePriceUnitPrice(Integer coursePriceUnitPrice) {
    this.coursePriceUnitPrice = coursePriceUnitPrice;
  }

  public Integer getCoursePriceUnitType() {
    return coursePriceUnitType;
  }

  public void setCoursePriceUnitType(Integer coursePriceUnitType) {
    this.coursePriceUnitType = coursePriceUnitType;
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