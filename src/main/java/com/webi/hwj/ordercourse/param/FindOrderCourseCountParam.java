package com.webi.hwj.ordercourse.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * Title: 查询合同课程数参数类<br>
 * Description: 查询合同课程数参数类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月31日 上午10:48:46
 * 
 * @author seven.gz
 */
public class FindOrderCourseCountParam implements Serializable {
  private static final long serialVersionUID = 1452209996607722989L;
  // 用户id
  private String userId;
  // 课程类型
  private String courseType;
  // 课程数
  private int courseCount;
  // 课程数(用于续约的合同展示)
  private int showCourseCount;
  // 剩余课程数量
  private int remainCourseCount;
  // 合同开始时间
  private Date startOrderTime;
  // 合同结束时间
  private Date endOrderTime;
  // 合同包名称
  private String coursePackageName;
  // 体系类别
  private String categoryType;
  // 合同类型(0:普通合同,1:续约合同)
  private String orderOriginalType;

  // 用户id（用于传参）
  private List<String> userIds;
  // 合同id
  private String orderId;

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

  public int getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(int courseCount) {
    this.courseCount = courseCount;
  }

  public int getShowCourseCount() {
    return showCourseCount;
  }

  public void setShowCourseCount(int showCourseCount) {
    this.showCourseCount = showCourseCount;
  }

  public int getRemainCourseCount() {
    return remainCourseCount;
  }

  public void setRemainCourseCount(int remainCourseCount) {
    this.remainCourseCount = remainCourseCount;
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

  public String getCoursePackageName() {
    return coursePackageName;
  }

  public void setCoursePackageName(String coursePackageName) {
    this.coursePackageName = coursePackageName;
  }

  public String getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }

  public String getOrderOriginalType() {
    return orderOriginalType;
  }

  public void setOrderOriginalType(String orderOriginalType) {
    this.orderOriginalType = orderOriginalType;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

}
