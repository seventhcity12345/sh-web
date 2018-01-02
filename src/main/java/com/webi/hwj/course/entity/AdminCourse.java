package com.webi.hwj.course.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category courseOne2many Entity
 * @author mingyisoft代码生成工具
 */
public class AdminCourse implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 2505448920405683772L;
  // 主键id
  private String keyId;
  // 体系类别
  @NotNull(message = "体系类别不能为空")
  private String categoryType;
  // 体系类别名称
  private String categoryTypeId;
  // 跟码表里的课程类别走
  @NotNull(message = "课程类别不能为空")
  private String courseType;
  // 跟码表里的课程类别走
  private String courseTypeId;
  // 课程标题
  @NotNull(message = "课程标题不能为空")
  @Length(min = 1, max = 200, message = "课程标题必须为1-200位")
  private String courseTitle;
  // 课程级别(多个用逗号分隔)
  private String courseLevel;
  // 课程图片
  @NotNull(message = "课程图片不能为空")
  private String coursePic;
  // ppt课件
  private String courseCourseware;
  // 微立方课件id
  private String documentId;
  // 课程简介
  private String courseDesc;
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
  // 用于根据courseid更新课程信息的In条件
  private List<String> courseIds;
  // 用于组合查询
  public String cons;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getCourseLevel() {
    return courseLevel;
  }

  public void setCourseLevel(String courseLevel) {
    this.courseLevel = courseLevel;
  }

  public String getCoursePic() {
    return coursePic;
  }

  public void setCoursePic(String coursePic) {
    this.coursePic = coursePic;
  }

  public String getCourseCourseware() {
    return courseCourseware;
  }

  public void setCourseCourseware(String courseCourseware) {
    this.courseCourseware = courseCourseware;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getCourseDesc() {
    return courseDesc;
  }

  public void setCourseDesc(String courseDesc) {
    this.courseDesc = courseDesc;
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

  public List<String> getCourseIds() {
    return courseIds;
  }

  public void setCourseIds(List<String> courseIds) {
    this.courseIds = courseIds;
  }

  public String getCategoryTypeId() {
    return categoryTypeId;
  }

  public void setCategoryTypeId(String categoryTypeId) {
    this.categoryTypeId = categoryTypeId;
  }

  public String getCourseTypeId() {
    return courseTypeId;
  }

  public void setCourseTypeId(String courseTypeId) {
    this.courseTypeId = courseTypeId;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }
}