package com.webi.hwj.coursetype.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category courseType Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_course_type")
public class CourseType implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -5160914957798750722L;
  // 主键id
  private String keyId;
  // 课程类型码
  private String courseType;
  // 课程类型名称(中文)
  private String courseTypeChineseName;
  // 课程类型名称(英文)
  private String courseTypeEnglishName;
  // 上课持续时间(分钟)
  private Integer courseTypeDuration;
  // 限制人数
  private Integer courseTypeLimitNumber;
  // 提前上课时间(分钟)
  private Integer courseTypeBeforeGoclassTime;
  // 下课延长时间(分钟)
  private Integer courseTypeAfterGoclassTime;
  // 提前预约时间(分钟)
  private Integer courseTypeSubscribeTime;
  // 提前取消预约时间(分钟)
  private Integer courseTypeCancelSubscribeTime;
  // 课程类型性质（小课，大课等）
  private Integer courseTypeFlag;
  // 订课规则
  private String courseTypeSubscribeRules;
  // 取消订课规则
  private String courseTypeCancelSubscribeRules;
  // 课程时长规则
  private String courseTypeDurationRules;
  // 是否允许老师评论(0:不可以,1:可以)
  private Boolean courseTypeIsTeacherComment;
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

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getCourseTypeChineseName() {
    return courseTypeChineseName;
  }

  public void setCourseTypeChineseName(String courseTypeChineseName) {
    this.courseTypeChineseName = courseTypeChineseName;
  }

  public String getCourseTypeEnglishName() {
    return courseTypeEnglishName;
  }

  public void setCourseTypeEnglishName(String courseTypeEnglishName) {
    this.courseTypeEnglishName = courseTypeEnglishName;
  }

  public Integer getCourseTypeDuration() {
    return courseTypeDuration;
  }

  public void setCourseTypeDuration(Integer courseTypeDuration) {
    this.courseTypeDuration = courseTypeDuration;
  }

  public Integer getCourseTypeLimitNumber() {
    return courseTypeLimitNumber;
  }

  public void setCourseTypeLimitNumber(Integer courseTypeLimitNumber) {
    this.courseTypeLimitNumber = courseTypeLimitNumber;
  }

  public Integer getCourseTypeBeforeGoclassTime() {
    return courseTypeBeforeGoclassTime;
  }

  public void setCourseTypeBeforeGoclassTime(Integer courseTypeBeforeGoclassTime) {
    this.courseTypeBeforeGoclassTime = courseTypeBeforeGoclassTime;
  }

  public Integer getCourseTypeAfterGoclassTime() {
    return courseTypeAfterGoclassTime;
  }

  public void setCourseTypeAfterGoclassTime(Integer courseTypeAfterGoclassTime) {
    this.courseTypeAfterGoclassTime = courseTypeAfterGoclassTime;
  }

  public Integer getCourseTypeSubscribeTime() {
    return courseTypeSubscribeTime;
  }

  public void setCourseTypeSubscribeTime(Integer courseTypeSubscribeTime) {
    this.courseTypeSubscribeTime = courseTypeSubscribeTime;
  }

  public Integer getCourseTypeCancelSubscribeTime() {
    return courseTypeCancelSubscribeTime;
  }

  public void setCourseTypeCancelSubscribeTime(Integer courseTypeCancelSubscribeTime) {
    this.courseTypeCancelSubscribeTime = courseTypeCancelSubscribeTime;
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

  public Integer getCourseTypeFlag() {
    return courseTypeFlag;
  }

  public void setCourseTypeFlag(Integer courseTypeFlag) {
    this.courseTypeFlag = courseTypeFlag;
  }

  public String getCourseTypeSubscribeRules() {
    return courseTypeSubscribeRules;
  }

  public void setCourseTypeSubscribeRules(String courseTypeSubscribeRules) {
    this.courseTypeSubscribeRules = courseTypeSubscribeRules;
  }

  public String getCourseTypeCancelSubscribeRules() {
    return courseTypeCancelSubscribeRules;
  }

  public void setCourseTypeCancelSubscribeRules(String courseTypeCancelSubscribeRules) {
    this.courseTypeCancelSubscribeRules = courseTypeCancelSubscribeRules;
  }

  public String getCourseTypeDurationRules() {
    return courseTypeDurationRules;
  }

  public void setCourseTypeDurationRules(String courseTypeDurationRules) {
    this.courseTypeDurationRules = courseTypeDurationRules;
  }

  public Boolean getCourseTypeIsTeacherComment() {
    return courseTypeIsTeacherComment;
  }

  public void setCourseTypeIsTeacherComment(Boolean courseTypeIsTeacherComment) {
    this.courseTypeIsTeacherComment = courseTypeIsTeacherComment;
  }
}