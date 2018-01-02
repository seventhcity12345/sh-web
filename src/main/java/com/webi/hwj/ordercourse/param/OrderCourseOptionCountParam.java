package com.webi.hwj.ordercourse.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 合同子表课程类型<br>
 * Description: 合同子表课程类型<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年9月20日 上午11:00:34
 * 
 * @author seven.gz
 */
public class OrderCourseOptionCountParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 3487805149247433875L;
  // 主键id
  private String keyId;
  // 用户id
  private String userId;
  // 合同id
  private String orderId;
  // 跟码表里的课程类别走
  private String courseType;
  // 跟码表里的课程类别走
  private String courseTypeChineseName;
  // 课程数(用于续约的合同展示)
  private Integer showCourseCount;
  // 剩余课程数量(每次上完课之后会减1，初始值与remain_course_count一致)
  private Integer remainCourseCount;
  // 课程截止日期
  private Date courseEndTime;
  // 课程单位类型（0:节，1:月，2：天）
  private Integer courseUnitType;
  // 合同开始时间
  private Date startOrderTime;
  // 合同结束时间
  private Date endOrderTime;
  // 合同数
  private Integer orderCount;
  // 合同ids
  private List<String> orderIds;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getCourseTypeChineseName() {
    return courseTypeChineseName;
  }

  public void setCourseTypeChineseName(String courseTypeChineseName) {
    this.courseTypeChineseName = courseTypeChineseName;
  }

  public Integer getShowCourseCount() {
    return showCourseCount;
  }

  public void setShowCourseCount(Integer showCourseCount) {
    this.showCourseCount = showCourseCount;
  }

  public Integer getRemainCourseCount() {
    return remainCourseCount;
  }

  public void setRemainCourseCount(Integer remainCourseCount) {
    this.remainCourseCount = remainCourseCount;
  }

  public Integer getCourseUnitType() {
    return courseUnitType;
  }

  public void setCourseUnitType(Integer courseUnitType) {
    this.courseUnitType = courseUnitType;
  }

  public Date getStartOrderTime() {
    return startOrderTime;
  }

  public void setStartOrderTime(Date startOrderTime) {
    this.startOrderTime = startOrderTime;
  }

  public Date getEndOrderTime() {
    return endOrderTime;
  }

  public void setEndOrderTime(Date endOrderTime) {
    this.endOrderTime = endOrderTime;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Integer getOrderCount() {
    return orderCount;
  }

  public void setOrderCount(Integer orderCount) {
    this.orderCount = orderCount;
  }

  public Date getCourseEndTime() {
    return courseEndTime;
  }

  public void setCourseEndTime(Date courseEndTime) {
    this.courseEndTime = courseEndTime;
  }

  public List<String> getOrderIds() {
    return orderIds;
  }

  public void setOrderIds(List<String> orderIds) {
    this.orderIds = orderIds;
  }
}