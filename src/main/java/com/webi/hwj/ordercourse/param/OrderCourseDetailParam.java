package com.webi.hwj.ordercourse.param;

import java.io.Serializable;
import java.util.List;

import com.webi.hwj.admin.entity.BadminUser;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.user.param.UserInfoForOrderDetailParam;

/**
 * Title: 拟定合同时使用的参数bean<br>
 * Description: SaveOrderCourseParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月5日 下午3:04:13
 * 
 * @author athrun.cw
 */
public class OrderCourseDetailParam implements Serializable {
  private OrderCourse orderCourse;
  private UserInfoForOrderDetailParam userInfo;
  private BadminUser badminUser;
  private String giftTimeHtml;
  // 服务方
  private String contractOwnerName;

  private List<OrderCourseDetailOptionParam> orderCourseOptionEntityList;

  public OrderCourse getOrderCourse() {
    return orderCourse;
  }

  public void setOrderCourse(OrderCourse orderCourse) {
    this.orderCourse = orderCourse;
  }

  public UserInfoForOrderDetailParam getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfoForOrderDetailParam userInfo) {
    this.userInfo = userInfo;
  }

  public BadminUser getBadminUser() {
    return badminUser;
  }

  public void setBadminUser(BadminUser badminUser) {
    this.badminUser = badminUser;
  }

  public String getGiftTimeHtml() {
    return giftTimeHtml;
  }

  public void setGiftTimeHtml(String giftTimeHtml) {
    this.giftTimeHtml = giftTimeHtml;
  }

  public List<OrderCourseDetailOptionParam> getOrderCourseOptionEntityList() {
    return orderCourseOptionEntityList;
  }

  public void setOrderCourseOptionEntityList(
      List<OrderCourseDetailOptionParam> orderCourseOptionEntityList) {
    this.orderCourseOptionEntityList = orderCourseOptionEntityList;
  }

  public String getContractOwnerName() {
    return contractOwnerName;
  }

  public void setContractOwnerName(String contractOwnerName) {
    this.contractOwnerName = contractOwnerName;
  }

}