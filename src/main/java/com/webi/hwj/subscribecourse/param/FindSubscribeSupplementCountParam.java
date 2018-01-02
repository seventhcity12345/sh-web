package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Title: 查询补课数参数类<br>
 * Description: 查询补课数参数类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年6月2日 上午11:03:28
 * 
 * @author seven.gz
 */
public class FindSubscribeSupplementCountParam implements Serializable {
  private static final long serialVersionUID = -8048207634912782219L;
  private String userId;
  private String courseType;
  private int courseCount;
  private String orderId;
  private List<String> orderIds;

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

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public List<String> getOrderIds() {
    return orderIds;
  }

  public void setOrderIds(List<String> orderIds) {
    this.orderIds = orderIds;
  }
}
