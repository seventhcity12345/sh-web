package com.webi.hwj.ordercourse.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category orderCourse Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_order_course")
public class OrderCourse implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 5755077080802350257L;
  // 主键id
  private String keyId;
  // 用户id
  private String userId;
  // 合同中的用户姓名
  private String userName;
  // 体系类别
  private String categoryType;
  // 学生来源(0:常规,1:线下售卖,2:线下转线上)
  private Integer userFromType;
  // 合同类型(0:普通合同,1:续约合同)
  private Integer orderOriginalType;
  // 所属课程包id(用于管理平台取值)
  private String coursePackageId;
  // 课程包名称
  private String coursePackageName;
  // 价格策略id
  private String coursePackagePriceOptionId;
  // 续约合同路径(逗号分隔)
  private String fromPath;
  // 已累计支付的金额
  private Integer havePaidPrice;
  // 总共折后价(用户支付的价格)
  private Integer totalRealPrice;
  // 总共原价
  private Integer totalShowPrice;
  // 最后价格(如果是普通合同的话就是等同于total_real_price，如果是续约就是存一个差价)
  private Integer totalFinalPrice;
  // 合同开始时间
  private Date startOrderTime;
  // 合同截止时间
  private Date endOrderTime;
  // 时效(只给用户看,不参与任何计算)
  private Integer limitShowTime;
  // （时效单位：默认为0，0是月，1：天）
  private Integer limitShowTimeUnit;
  // 课程包类型0:standard,1:premium,2:basic
  private Integer coursePackageType;
  // 订单状态（1.已拟定,2.已发送,3.已确认,4.支付中,5.已支付,6.已过期,7.已终止）
  private Integer orderStatus;
  // 创建日期
  private Date createDate;
  // 最后更新日期
  private Date updateDate;
  // 创建人id(销售id)
  private String createUserId;
  // 最后更新人id
  private String updateUserId;
  // 是否使用(1:使用,0:不使用)
  private Boolean isUsed;
  // 数据描述
  private String dataDesc;

  // 是否是crm中创建的合同(1:是,0:否)
  private Boolean isCrm;

  // 合同版本
  private Integer orderVersion;

  // 赠送时间（单位：月）
  private Integer giftTime;

  // crm合同Guid
  private String crmContractId;
  // 合同备注
  private String orderRemark;

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

  public String getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }

  public String getCoursePackagePriceOptionId() {
    return coursePackagePriceOptionId;
  }

  public void setCoursePackagePriceOptionId(String coursePackagePriceOptionId) {
    this.coursePackagePriceOptionId = coursePackagePriceOptionId;
  }

  public Integer getLimitShowTimeUnit() {
    return limitShowTimeUnit;
  }

  public void setLimitShowTimeUnit(Integer limitShowTimeUnit) {
    this.limitShowTimeUnit = limitShowTimeUnit;
  }

  public Integer getUserFromType() {
    return userFromType;
  }

  public void setUserFromType(Integer userFromType) {
    this.userFromType = userFromType;
  }

  public Integer getOrderOriginalType() {
    return orderOriginalType;
  }

  public void setOrderOriginalType(Integer orderOriginalType) {
    this.orderOriginalType = orderOriginalType;
  }

  public String getCoursePackageId() {
    return coursePackageId;
  }

  public void setCoursePackageId(String coursePackageId) {
    this.coursePackageId = coursePackageId;
  }

  public String getCoursePackageName() {
    return coursePackageName;
  }

  public void setCoursePackageName(String coursePackageName) {
    this.coursePackageName = coursePackageName;
  }

  public String getFromPath() {
    return fromPath;
  }

  public void setFromPath(String fromPath) {
    this.fromPath = fromPath;
  }

  public Integer getHavePaidPrice() {
    return havePaidPrice;
  }

  public void setHavePaidPrice(Integer havePaidPrice) {
    this.havePaidPrice = havePaidPrice;
  }

  public Integer getTotalRealPrice() {
    return totalRealPrice;
  }

  public void setTotalRealPrice(Integer totalRealPrice) {
    this.totalRealPrice = totalRealPrice;
  }

  public Integer getTotalShowPrice() {
    return totalShowPrice;
  }

  public void setTotalShowPrice(Integer totalShowPrice) {
    this.totalShowPrice = totalShowPrice;
  }

  public Integer getTotalFinalPrice() {
    return totalFinalPrice;
  }

  public void setTotalFinalPrice(Integer totalFinalPrice) {
    this.totalFinalPrice = totalFinalPrice;
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

  public Integer getLimitShowTime() {
    return limitShowTime;
  }

  public void setLimitShowTime(Integer limitShowTime) {
    this.limitShowTime = limitShowTime;
  }

  public Integer getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(Integer orderStatus) {
    this.orderStatus = orderStatus;
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

  public Boolean getIsCrm() {
    return isCrm;
  }

  public void setIsCrm(Boolean isCrm) {
    this.isCrm = isCrm;
  }

  public Integer getGiftTime() {
    return giftTime;
  }

  public void setGiftTime(Integer giftTime) {
    this.giftTime = giftTime;
  }

  public Integer getCoursePackageType() {
    return coursePackageType;
  }

  public void setCoursePackageType(Integer coursePackageType) {
    this.coursePackageType = coursePackageType;
  }

  public String getDataDesc() {
    return dataDesc;
  }

  public void setDataDesc(String dataDesc) {
    this.dataDesc = dataDesc;
  }

  public Integer getOrderVersion() {
    return orderVersion;
  }

  public void setOrderVersion(Integer orderVersion) {
    this.orderVersion = orderVersion;
  }

  public String getCrmContractId() {
    return crmContractId;
  }

  public void setCrmContractId(String crmContractId) {
    this.crmContractId = crmContractId;
  }

  public String getOrderRemark() {
    return orderRemark;
  }

  public void setOrderRemark(String orderRemark) {
    this.orderRemark = orderRemark;
  }
}