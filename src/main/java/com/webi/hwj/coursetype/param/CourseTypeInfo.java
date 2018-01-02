package com.webi.hwj.coursetype.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * 
 * Title: 向session中放的courseType信息<br>
 * Description: CourseTypeInfo<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年8月30日 下午1:48:56
 * 
 * @author seven.gz
 */
public class CourseTypeInfo implements Serializable {
  private static final long serialVersionUID = -6011626449212982792L;
  // 课程类型码
  private String courseType;
  // 课程类型的限制日期
  private Date limitTime;
  // 课程单位类型 是否是节
  private Boolean courseUnitHaveClass;
  // 课程类型的名称
  private String courseTypeChineseName;
  // 合同id
  private String orderId;
  // 合同字表id
  private String orderOptionId;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public Date getLimitTime() {
    return limitTime;
  }

  public void setLimitTime(Date limitTime) {
    this.limitTime = limitTime;
  }

  public Boolean getCourseUnitHaveClass() {
    return courseUnitHaveClass;
  }

  public void setCourseUnitHaveClass(Boolean courseUnitHaveClass) {
    this.courseUnitHaveClass = courseUnitHaveClass;
  }

  public String getCourseTypeChineseName() {
    return courseTypeChineseName;
  }

  public void setCourseTypeChineseName(String courseTypeChineseName) {
    this.courseTypeChineseName = courseTypeChineseName;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderOptionId() {
    return orderOptionId;
  }

  public void setOrderOptionId(String orderOptionId) {
    this.orderOptionId = orderOptionId;
  }
}