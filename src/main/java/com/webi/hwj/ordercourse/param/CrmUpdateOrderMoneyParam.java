package com.webi.hwj.ordercourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: crm专用，查询一段时间内，支付过的用户的支付总额<br>
 * Description: CrmUpdateOrderMoneyParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年6月28日 下午4:49:11
 * 
 * @author komi.zsy
 */
public class CrmUpdateOrderMoneyParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = 2061669094069999091L;
  // 用户手机号
  private String phone;
  // 拆分金额
  private Integer splitPrice;

  private Date startTime;

  private Date endTime;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getSplitPrice() {
    return splitPrice;
  }

  public void setSplitPrice(Integer splitPrice) {
    this.splitPrice = splitPrice;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}