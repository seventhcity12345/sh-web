package com.webi.hwj.ordercourse.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 拟定合同时使用的参数bean<br>
 * Description: SaveOrderCourseParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月5日 下午3:04:13
 * 
 * @author athrun.cw
 */
public class OrderCourseDetailOptionParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 2161937304830467118L;
  // 课程单位类型
  private Integer courseUnitType;
  // 订课规则
  private String orderRule;
  // 课程类型名字
  private String courseType;
  // 课程类型
  private String courseTypeId;
  // 是否赠送课程
  private Boolean isGift;
  // 课时数
  private Integer showCourseCount;
  // 价格
  private Integer realPrice;
  // 订课规则
  private String subscribeRules;
  // 取消订课规则
  private String cancelSubscribeRules;
  // 课程时长规则
  private String durationRules;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public Integer getCourseUnitType() {
    return courseUnitType;
  }

  public void setCourseUnitType(Integer courseUnitType) {
    this.courseUnitType = courseUnitType;
  }

  public String getOrderRule() {
    return orderRule;
  }

  public void setOrderRule(String orderRule) {
    this.orderRule = orderRule;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public Boolean getIsGift() {
    return isGift;
  }

  public void setIsGift(Boolean isGift) {
    this.isGift = isGift;
  }

  public Integer getShowCourseCount() {
    return showCourseCount;
  }

  public void setShowCourseCount(Integer showCourseCount) {
    this.showCourseCount = showCourseCount;
  }

  public Integer getRealPrice() {
    return realPrice;
  }

  public void setRealPrice(Integer realPrice) {
    this.realPrice = realPrice;
  }

  public String getSubscribeRules() {
    return subscribeRules;
  }

  public void setSubscribeRules(String subscribeRules) {
    this.subscribeRules = subscribeRules;
  }

  public String getCancelSubscribeRules() {
    return cancelSubscribeRules;
  }

  public void setCancelSubscribeRules(String cancelSubscribeRules) {
    this.cancelSubscribeRules = cancelSubscribeRules;
  }

  public String getDurationRules() {
    return durationRules;
  }

  public void setDurationRules(String durationRules) {
    this.durationRules = durationRules;
  }

  public String getCourseTypeId() {
    return courseTypeId;
  }

  public void setCourseTypeId(String courseTypeId) {
    this.courseTypeId = courseTypeId;
  }

}