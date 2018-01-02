package com.webi.hwj.ordercourse.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 合同信息以及其子表list信息<br> 
 * Description: OrderCourseAndOptionParam<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2016年10月13日 下午3:15:58 
 * @author komi.zsy
 */
public class OrderCourseAndOptionParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = 225703063440994266L;
  // 主键id
  private String keyId;
  // 用户id
  private String userId;
  // 合同中的用户姓名
  private String userName;
  //手机号
  private String userPhone;
  // 课程包名称
  private String coursePackageName;
  // 合同开始时间
  private Date startOrderTime;
  // 合同截止时间
  private Date endOrderTime;
  // 订单状态（1.已拟定,2.已发送,3.已确认,4.支付中,5.已支付,6.已过期,7.已终止）
  private Integer orderStatus;
  
  //合同子表list
  private List<OrderCourseOptionParam> orderCourseOptionList;

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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public Integer getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(Integer orderStatus) {
    this.orderStatus = orderStatus;
  }

  public List<OrderCourseOptionParam> getOrderCourseOptionList() {
    return orderCourseOptionList;
  }

  public void setOrderCourseOptionList(List<OrderCourseOptionParam> orderCourseOptionList) {
    this.orderCourseOptionList = orderCourseOptionList;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getCoursePackageName() {
    return coursePackageName;
  }

  public void setCoursePackageName(String coursePackageName) {
    this.coursePackageName = coursePackageName;
  }
}