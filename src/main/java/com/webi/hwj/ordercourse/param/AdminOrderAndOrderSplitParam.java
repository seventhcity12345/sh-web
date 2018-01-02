package com.webi.hwj.ordercourse.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 拆分订单表和合同表信息<br>
 * Description: 用于招联参数<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年1月19日 上午10:58:52
 * 
 * @author komi.zsy
 */
public class AdminOrderAndOrderSplitParam implements Serializable {
  private static final long serialVersionUID = 3494955808245343269L;

  private String userId;
  // 拆分金额
  private Integer splitPrice;

  private Date updateDate;

  private List<String> userIds;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getSplitPrice() {
    return splitPrice;
  }

  public void setSplitPrice(Integer splitPrice) {
    this.splitPrice = splitPrice;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

}