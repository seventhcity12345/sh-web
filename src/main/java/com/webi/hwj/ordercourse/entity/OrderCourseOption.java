package com.webi.hwj.ordercourse.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category orderCourseOption Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_order_course_option")
public class OrderCourseOption implements Serializable {
  private static final long serialVersionUID = -5072915914086321252L;
  // 主键id
  private String keyId;
  // 订单/合同ID
  private String orderId;
  // 跟码表里的课程类别走
  private String courseType;
  // 真实价(用户支付的价格)（就是原价）
  private Integer realPrice;
  // 课程数(用于续约的合同展示)
  private Integer showCourseCount;
  // 课程数
  private Integer courseCount;
  // 剩余课程数量(每次上完课之后会减1，初始值与remain_course_count一致)
  private Integer remainCourseCount;
  // 是否赠送(1:是，0:否)
  private Boolean isGift;
  // 课程单位类型（0:节，1:月，2：天）
  private Integer courseUnitType;
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
  //续约课时数无法计算，临时字段，（0：删除，1：使用中）
  private Boolean isDelete;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public Integer getRealPrice() {
    return realPrice;
  }

  public void setRealPrice(Integer realPrice) {
    this.realPrice = realPrice;
  }

  public Integer getShowCourseCount() {
    return showCourseCount;
  }

  public void setShowCourseCount(Integer showCourseCount) {
    this.showCourseCount = showCourseCount;
  }

  public Integer getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(Integer courseCount) {
    this.courseCount = courseCount;
  }

  public Integer getRemainCourseCount() {
    return remainCourseCount;
  }

  public void setRemainCourseCount(Integer remainCourseCount) {
    this.remainCourseCount = remainCourseCount;
  }

  public Boolean getIsGift() {
    return isGift;
  }

  public void setIsGift(Boolean isGift) {
    this.isGift = isGift;
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

  public Integer getCourseUnitType() {
    return courseUnitType;
  }

  public void setCourseUnitType(Integer courseUnitType) {
    this.courseUnitType = courseUnitType;
  }

  public Boolean getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Boolean isDelete) {
    this.isDelete = isDelete;
  }

}