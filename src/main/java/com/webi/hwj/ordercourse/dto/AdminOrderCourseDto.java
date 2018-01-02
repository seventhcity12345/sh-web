/** 
 * File: OrderCourseDto.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.ordercourse.cto<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年3月25日 上午11:19:37
 * @author ivan.mgh
 */
package com.webi.hwj.ordercourse.dto;

import java.io.Serializable;

/**
 * Title: 合同信息补充数据传输类<br>
 * Description: 合同信息补充数据传输类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月25日 上午11:19:37
 * 
 * @author ivan.mgh
 */
public class AdminOrderCourseDto implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 7034940084386711709L;
  // Speakhi合同号
  private String contractGuid;
  // 合同总金额
  private String contractMoney;
  // 优惠金额
  private String discountMoney;
  // 实收金额
  private String actualMoney;
  // 合同状态
  private String contractState;
  // CRM客户唯一GUID
  private String leadid;

  public String getContractGuid() {
    return contractGuid;
  }

  public void setContractGuid(String contractGuid) {
    this.contractGuid = contractGuid;
  }

  public String getContractMoney() {
    return contractMoney;
  }

  public void setContractMoney(String contractMoney) {
    this.contractMoney = contractMoney;
  }

  public String getDiscountMoney() {
    return discountMoney;
  }

  public void setDiscountMoney(String discountMoney) {
    this.discountMoney = discountMoney;
  }

  public String getActualMoney() {
    return actualMoney;
  }

  public void setActualMoney(String actualMoney) {
    this.actualMoney = actualMoney;
  }

  public String getContractState() {
    return contractState;
  }

  public void setContractState(String contractState) {
    this.contractState = contractState;
  }

  public String getLeadid() {
    return leadid;
  }

  public void setLeadid(String leadid) {
    this.leadid = leadid;
  }

}
