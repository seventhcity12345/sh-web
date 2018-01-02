package com.webi.hwj.courseone2many.param;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 排课参数实体，增加预约id<br>
 * Description: CourseOne2ManySchedulingParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月11日 下午3:20:51
 * 
 * @author komi.zsy
 */
@ApiModel(value = "CourseOne2ManySchedulingParam(成人ES课程信息模型)")
public class CourseOne2ManySchedulingParam implements Serializable {
  private static final long serialVersionUID = 4934895840513613580L;

  private String keyId;// 主键id

  @NotNull(message = "体系类别不能为空")
  private String categoryType;// 体系类别

  @NotNull(message = "课程类别不能为空")
  @ApiModelProperty(value = "课程类型", example = "course_type8")
  private String courseType;// 课程类型

  @NotNull(message = "所属课程id不能为空")
  private String courseId;// 所属课程id

  @ApiModelProperty(value = "课程标题", example = "eat food")
  private String courseTitle;// 课程标题

  @NotNull(message = "课程级别不能为空")
  private String courseLevel;// 课程级别(多个用逗号分隔)

  @ApiModelProperty(value = "课程图片",
      example = "https://teenagertestcdn.speakhi.com/images/teacher/33e51155701d4ad383b92cbd9fe05fed.png")
  private String coursePic;// 课程图片

  @NotNull(message = "老师id不能为空")
  private String teacherId;// 老师id

  @ApiModelProperty(value = "老师名称", example = "Steven")
  private String teacherName;// 老师名称

  private String teacherTimeId;// 老师时间id

  private String courseCourseware;// ppt课件地址

  private String documentId;// 微立方课件id

  @NotNull(message = "上课时间不能为空")
  @ApiModelProperty(value = "上课开始时间", example = "2017-06-02 05:00:00")
  private Date startTime;// 上课开始时间

  @ApiModelProperty(value = "上课结束时间", example = "2017-06-02 05:00:00")
  private Date endTime;// 上课结束时间

  @ApiModelProperty(value = "直播id", example = "aaaaa")
  private String liveId;
  @ApiModelProperty(value = "上课平台类型", example = "上课平台(0:webex;1:展示互动;2:classin)")
  private String teacherTimePlatform;

  private String userId;

  private String courseDesc;// 课程简介

  private String teacherUrl; // 教师url

  private String studentUrl; // 学生url

  @ApiModelProperty(value = "教师图片",
      example = "https://teenagertestcdn.speakhi.com/images/teacher/33e51155701d4ad383b92cbd9fe05fed.png")
  private String teacherPhoto;// 教师图片

  @ApiModelProperty(value = "课程已预约人数", example = "120")
  private Integer alreadyPersonCount;// 已预约人数

  private Integer maxCount;// 最大预约人数

  private Date createDate;// 创建日期

  private Date updateDate;// 最后更新日期

  private String createUserId;// 创建人id

  private String updateUserId;// 最后更新人id

  private Boolean isUsed;// 是否使用(1:使用,0:不使用)

  private Boolean isSubscribe;// 是否预约(1:使用,0:不使用)

  private Boolean isConfirm;// 是否确认(1:使用,0:不使用)

  @ApiModelProperty(value = "老师类型", example = "外教")
  private String teacherNationality;// 老师国籍

  private String teacherDesc;// 教师简介

  private Integer limitNumber;// 上课限制人数

  private String courseTypeName;

  private String thirdFrom;// 老师第三方来源

  private String subscribeId;// 预约id

  private Integer status;

  @ApiModelProperty(value = "可提前进入教室时间(分钟)", example = "10")
  private Integer courseTypeBeforeGoclassTime;// 上课前多少分钟可以进入教室

  @ApiModelProperty(value = "可提前取消时间(分钟)", example = "480")
  private Integer courseTypeCancelSubscribeTime;// 提前多少分钟可以取消预约

  // 房间id
  private String roomId;// 预约id

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

  public String getSubscribeId() {
    return subscribeId;
  }

  public void setSubscribeId(String subscribeId) {
    this.subscribeId = subscribeId;
  }

  public String getCourseTypeName() {
    return courseTypeName;
  }

  public void setCourseTypeName(String courseTypeName) {
    this.courseTypeName = courseTypeName;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getCourseTypeBeforeGoclassTime() {
    return courseTypeBeforeGoclassTime;
  }

  public void setCourseTypeBeforeGoclassTime(Integer courseTypeBeforeGoclassTime) {
    this.courseTypeBeforeGoclassTime = courseTypeBeforeGoclassTime;
  }

  public Integer getCourseTypeCancelSubscribeTime() {
    return courseTypeCancelSubscribeTime;
  }

  public void setCourseTypeCancelSubscribeTime(Integer courseTypeCancelSubscribeTime) {
    this.courseTypeCancelSubscribeTime = courseTypeCancelSubscribeTime;
  }

  public Integer getMaxCount() {
    return maxCount;
  }

  public void setMaxCount(Integer maxCount) {
    this.maxCount = maxCount;
  }

  public String getTeacherDesc() {
    return teacherDesc;
  }

  public void setTeacherDesc(String teacherDesc) {
    this.teacherDesc = teacherDesc;
  }

  public String getLiveId() {
    return liveId;
  }

  public void setLiveId(String liveId) {
    this.liveId = liveId;
  }

  public String getTeacherTimePlatform() {
    return teacherTimePlatform;
  }

  public void setTeacherTimePlatform(String teacherTimePlatform) {
    this.teacherTimePlatform = teacherTimePlatform;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

}
