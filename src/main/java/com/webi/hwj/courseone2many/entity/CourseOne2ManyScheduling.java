package com.webi.hwj.courseone2many.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category courseOne2manyScheduling Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_course_one2many_scheduling")
public class CourseOne2ManyScheduling implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -4921740071831623736L;
  // 主键id
  private String keyId;
  // 体系类别
  @NotNull(message = "体系类别不能为空")
  private String categoryType;
  // 跟码表里的课程类别走
  @NotNull(message = "课程类别不能为空")
  private String courseType;
  // 所属课程id
  @NotNull(message = "所属课程id不能为空")
  private String courseId;
  // 课程标题
  private String courseTitle;
  // 课程级别(多个用逗号分隔)
  @NotNull(message = "课程级别不能为空")
  private String courseLevel;
  // 课程图片
  private String coursePic;
  // 老师id
  @NotNull(message = "老师id不能为空")
  private String teacherId;
  // 老师名称
  private String teacherName;
  // 老师时间id
  private String teacherTimeId;
  // ppt课件
  private String courseCourseware;
  // 微立方课件id
  private String documentId;
  // 上课时间
  @NotNull(message = "上课时间不能为空")
  private Date startTime;
  // 下课时间
  @NotNull(message = "下课时间不能为空")
  private Date endTime;
  // 课程简介
  private String courseDesc;
  // 教师url
  private String teacherUrl;
  // 学生url
  private String studentUrl;
  // 教师图片
  private String teacherPhoto;
  // 已预约人数
  private Integer alreadyPersonCount;
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
  // 是否预约(1:使用,0:不使用)
  private Boolean isSubscribe;
  // 是否确认(1:使用,0:不使用)
  private Boolean isConfirm;
  // 老师国籍
  private String teacherNationality;
  // 上课限制人数
  private Integer limitNumber;

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

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
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

  public Integer getAlreadyPersonCount() {
    return alreadyPersonCount;
  }

  public void setAlreadyPersonCount(Integer alreadyPersonCount) {
    this.alreadyPersonCount = alreadyPersonCount;
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

  public String getCourseDesc() {
    return courseDesc;
  }

  public void setCourseDesc(String courseDesc) {
    this.courseDesc = courseDesc;
  }

  public String getTeacherUrl() {
    return teacherUrl;
  }

  public void setTeacherUrl(String teacherUrl) {
    this.teacherUrl = teacherUrl;
  }

  public String getStudentUrl() {
    return studentUrl;
  }

  public void setStudentUrl(String studentUrl) {
    this.studentUrl = studentUrl;
  }

  public String getTeacherPhoto() {
    return teacherPhoto;
  }

  public void setTeacherPhoto(String teacherPhoto) {
    this.teacherPhoto = teacherPhoto;
  }

  public Boolean getIsSubscribe() {
    return isSubscribe;
  }

  public void setIsSubscribe(Boolean isSubscribe) {
    this.isSubscribe = isSubscribe;
  }

  public Boolean getIsConfirm() {
    return isConfirm;
  }

  public void setIsConfirm(Boolean isConfirm) {
    this.isConfirm = isConfirm;
  }

  public String getTeacherNationality() {
    return teacherNationality;
  }

  public void setTeacherNationality(String teacherNationality) {
    this.teacherNationality = teacherNationality;
  }

  public Integer getLimitNumber() {
    return limitNumber;
  }

  public void setLimitNumber(Integer limitNumber) {
    this.limitNumber = limitNumber;
  }
}