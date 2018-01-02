package com.webi.hwj.coursepackageprice.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category coursePackagePrice Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_course_package_price")
public class CoursePackagePrice implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -5778509928991435259L;
  // 主键id
  private String keyId;
  // 价格政策名称
  private String packagePriceName;
  // 价格执行开始时间
  private Date packagePriceStartTime;
  // 价格执行结束时间
  private Date packagePriceEndTime;
  //线上线下是否显示（0线上显示，1线下显示），逗号分隔
  private String packagePriceOnlineType;
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

  public String getPackagePriceName() {
    return packagePriceName;
  }

  public void setPackagePriceName(String packagePriceName) {
    this.packagePriceName = packagePriceName;
  }

  public Date getPackagePriceStartTime() {
    return packagePriceStartTime;
  }

  public void setPackagePriceStartTime(Date packagePriceStartTime) {
    this.packagePriceStartTime = packagePriceStartTime;
  }

  public Date getPackagePriceEndTime() {
    return packagePriceEndTime;
  }

  public void setPackagePriceEndTime(Date packagePriceEndTime) {
    this.packagePriceEndTime = packagePriceEndTime;
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

  public String getPackagePriceOnlineType() {
    return packagePriceOnlineType;
  }

  public void setPackagePriceOnlineType(String packagePriceOnlineType) {
    this.packagePriceOnlineType = packagePriceOnlineType;
  }

}