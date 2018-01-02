package com.webi.hwj.ordercourse.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category orderCourseSplit Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_order_course_split")
public class OrderCourseSplit implements Serializable {
  private static final long serialVersionUID = -4851652953612240491L;
  // 主键id
  private String keyId;
  // 合同ID
  private String orderId;
  // 拆分金额
  private Integer splitPrice;
  // 支付状态(0:未支付-线上,1:已支付-线上,2:已支付,待确认-线下,3:已支付,已确认-线下,4:未申请,5:申请中,6:申请成功,7:申请失败)
  private Integer splitStatus;
  // 支付方式(0:线上支付,1:pos机支付,2:现金,3:个人转账,5:百度分期)
  private Integer splitPayType;
  // 支付来源(支付宝,快钱)
  private String payFrom;
  // 支付银行
  private String payBank;
  // 支付成功流水号
  private String paySuccessSequence;
  // 线下销售姓名
  private String payCcName;
  // 线下中心
  private String payCenterName;
  // 线下城市
  private String payCityName;
  // 创建日期
  private Date createDate;
  // 最后更新日期
  private Date updateDate;
  // 创建人id
  private String createUserId;
  // 最后更新人id
  private String updateUserId;
  // 是否使用(1:使用,0:不使用)
  private Boolean isUsed;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Integer getSplitPrice() {
    return splitPrice;
  }

  public void setSplitPrice(Integer splitPrice) {
    this.splitPrice = splitPrice;
  }

  public Integer getSplitStatus() {
    return splitStatus;
  }

  public void setSplitStatus(Integer splitStatus) {
    this.splitStatus = splitStatus;
  }

  public Integer getSplitPayType() {
    return splitPayType;
  }

  public void setSplitPayType(Integer splitPayType) {
    this.splitPayType = splitPayType;
  }

  public String getPayFrom() {
    return payFrom;
  }

  public void setPayFrom(String payFrom) {
    this.payFrom = payFrom;
  }

  public String getPayBank() {
    return payBank;
  }

  public void setPayBank(String payBank) {
    this.payBank = payBank;
  }

  public String getPaySuccessSequence() {
    return paySuccessSequence;
  }

  public void setPaySuccessSequence(String paySuccessSequence) {
    this.paySuccessSequence = paySuccessSequence;
  }

  public String getPayCcName() {
    return payCcName;
  }

  public void setPayCcName(String payCcName) {
    this.payCcName = payCcName;
  }

  public String getPayCenterName() {
    return payCenterName;
  }

  public void setPayCenterName(String payCenterName) {
    this.payCenterName = payCenterName;
  }

  public String getPayCityName() {
    return payCityName;
  }

  public void setPayCityName(String payCityName) {
    this.payCityName = payCityName;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }

  public Boolean getIsUsed() {
    return isUsed;
  }

  public void setIsUsed(Boolean isUsed) {
    this.isUsed = isUsed;
  }

}