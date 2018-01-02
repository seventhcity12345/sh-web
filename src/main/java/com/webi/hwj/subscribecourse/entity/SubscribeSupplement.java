package com.webi.hwj.subscribecourse.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category subscribeSupplement Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_subscribe_supplement")
public class SubscribeSupplement implements Serializable {
  private static final long serialVersionUID = 7798106931124269990L;
  // 主键id
  private String keyId;
  // 学员id
  private String userId;
  // 学员名称
  private String userName;
  // 补课原因
  private String supplementReason;
  // 课程id
  private String courseId;
  // 课程标题
  private String courseTitle;
  // 生产预约id(为哪一条预约记录补课)
  private String fromSubscribeCourseId;
  // 消费预约id(哪一条预约记录补的课)
  private String toSubscribeCourseId;
  // 是否被消费(有学员补了这节课,0:没补课,1:补过课)
  private Boolean isSupplement;
  // 跟码表里的课程类别走
  private String courseType;
  // 创建日期
  private Date createDate;
  // 最后更新日期
  private Date updateDate;
  // 创建人id
  private String createUserId;
  // 创建人name
  private String createUserName;
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

  public String getSupplementReason() {
    return supplementReason;
  }

  public void setSupplementReason(String supplementReason) {
    this.supplementReason = supplementReason;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getFromSubscribeCourseId() {
    return fromSubscribeCourseId;
  }

  public void setFromSubscribeCourseId(String fromSubscribeCourseId) {
    this.fromSubscribeCourseId = fromSubscribeCourseId;
  }

  public String getToSubscribeCourseId() {
    return toSubscribeCourseId;
  }

  public void setToSubscribeCourseId(String toSubscribeCourseId) {
    this.toSubscribeCourseId = toSubscribeCourseId;
  }

  public Boolean getIsSupplement() {
    return isSupplement;
  }

  public void setIsSupplement(Boolean isSupplement) {
    this.isSupplement = isSupplement;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
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

  public String getCreateUserName() {
    return createUserName;
  }

  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
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