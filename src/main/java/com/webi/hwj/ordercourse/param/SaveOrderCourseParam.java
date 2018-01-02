package com.webi.hwj.ordercourse.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 拟定合同时使用的参数bean<br>
 * Description: SaveOrderCourseParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月5日 下午3:04:13
 * 
 * @author athrun.cw
 */
public class SaveOrderCourseParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 4745071007403547042L;

  private String orderId;

  private String userId;
  // 价格策略id
  private String coursePackagePriceOptionId;
  
  @NotNull(message = "真实姓名不能为空")
  @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,15}$", message = "真实姓名只能是中文")
  @Length(min = 1, max = 15, message = "真实姓名的范围是1-15位")
  private String userName;
  
  //用户英语名字
  @NotNull(message = "英文名不能为空")
  @Pattern(regexp = "^([A-Za-z]+\\s?)*[A-Za-z]$", message = "英文名只能是英文")
  @Length(min = 1, max = 20, message = "英文名的范围是1-20位")
  private String englishName;
  //用户手机号
  private String userPhone;
  
  @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|\\d{17,17}(x|X))$|^\\s{0}$", message = "身份证号是15-18位")
  @Length(min = 15, max = 18, message = "身份证号的范围是15-18位")
  private String idcard;

  private String categoryType;

  private Integer totalShowPrice;

  private Integer totalRealPrice;

  private Integer totalFinalPrice;

  private String renewalOrderCourseKeyId;

  private String fromPath;

  private String coursePackageId;

  private String coursePackageName;

  private Integer limitShowTime;
  // 时效单位
  private Integer limitShowTimeUnit;
  // 课程包类型0:standard,1:premium,2:basic
  private Integer coursePackageType;

  private Integer userFromType;

  private String currentLevel;

  private Boolean isCrm;

  private Integer giftTime;

  private String learningCoachId;
  
  //更新人id
  private String createUserId;
  
  //合同备注
  private String orderRemark;
  
  //小唯独价格策略版本
  private Integer coursePriceVersion;
  //crm合同Guid
  private String crmContractId;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Integer getTotalRealPrice() {
    return totalRealPrice;
  }

  public void setTotalRealPrice(Integer totalRealPrice) {
    this.totalRealPrice = totalRealPrice;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
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

  public String getRenewalOrderCourseKeyId() {
    return renewalOrderCourseKeyId;
  }

  public void setRenewalOrderCourseKeyId(String renewalOrderCourseKeyId) {
    this.renewalOrderCourseKeyId = renewalOrderCourseKeyId;
  }

  public String getFromPath() {
    return fromPath;
  }

  public void setFromPath(String fromPath) {
    this.fromPath = fromPath;
  }

  public String getCoursePackageId() {
    return coursePackageId;
  }

  public void setCoursePackageId(String coursePackageId) {
    this.coursePackageId = coursePackageId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getCoursePackageName() {
    return coursePackageName;
  }

  public void setCoursePackageName(String coursePackageName) {
    this.coursePackageName = coursePackageName;
  }

  public Integer getLimitShowTime() {
    return limitShowTime;
  }

  public void setLimitShowTime(Integer limitShowTime) {
    this.limitShowTime = limitShowTime;
  }

  public Integer getUserFromType() {
    return userFromType;
  }

  public void setUserFromType(Integer userFromType) {
    this.userFromType = userFromType;
  }

  public String getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(String currentLevel) {
    this.currentLevel = currentLevel;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
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

  public Integer getLimitShowTimeUnit() {
    return limitShowTimeUnit;
  }

  public void setLimitShowTimeUnit(Integer limitShowTimeUnit) {
    this.limitShowTimeUnit = limitShowTimeUnit;
  }

  public String getCoursePackagePriceOptionId() {
    return coursePackagePriceOptionId;
  }

  public void setCoursePackagePriceOptionId(String coursePackagePriceOptionId) {
    this.coursePackagePriceOptionId = coursePackagePriceOptionId;
  }

  public Integer getCoursePackageType() {
    return coursePackageType;
  }

  public void setCoursePackageType(Integer coursePackageType) {
    this.coursePackageType = coursePackageType;
  }

  public String getLearningCoachId() {
    return learningCoachId;
  }

  public void setLearningCoachId(String learningCoachId) {
    this.learningCoachId = learningCoachId;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public Integer getCoursePriceVersion() {
    return coursePriceVersion;
  }

  public void setCoursePriceVersion(Integer coursePriceVersion) {
    this.coursePriceVersion = coursePriceVersion;
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

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

}