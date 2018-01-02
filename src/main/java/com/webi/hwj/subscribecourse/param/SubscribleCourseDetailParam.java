package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title:课程预约详情 <br>
 * Description: 课程预约详情<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年7月20日 上午9:27:05
 * 
 * @author felix.yl
 */
@ApiModel(value = "SubscribleCourseDetailParam(成人-我的课表-某天所有已预约课程详情模型)")
public class SubscribleCourseDetailParam implements Serializable {

  private static final long serialVersionUID = 23781715811402115L;

  @ApiModelProperty(value = "预约id", example = "00530f8153bb4e0fb514d7bd9f490e48")
  private String keyId;// 课程预约id

  @ApiModelProperty(value = "老师上课时间id", example = "00530f8153bb4e0fb514d7bd9f490e48")
  private String teacherTimeId;// 老师上课时间id

  @ApiModelProperty(value = "用户的userId(查询参数)", example = "7fa43cc0e4ae43408247d6df825a84df")
  private String userId;// 用户的userId

  @ApiModelProperty(value = "当天开始时间(查询参数)", example = "2017-07-20 00:00:00")
  private Date currentStartTime;// 当天开始时间(查询参数)

  @ApiModelProperty(value = "当天结束时间(查询参数)", example = "2017-07-20 23:59:59")
  private Date currentEndTime;// 当天结束时间(查询参数)

  @ApiModelProperty(value = "课程类型", example = "course_type8")
  private String courseType;// 课程类型

  @ApiModelProperty(value = "课程类型中文名", example = "OC课")
  private String courseTypeChineseName;// 课程类型

  @ApiModelProperty(value = "课程图片",
      example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images%2Fcourse%2Fone2one%2FGeneral_Level_1%2FAge.jpg")
  private String coursePic;// 课程图片

  @ApiModelProperty(value = "课程标题", example = "Age")
  private String courseTitle;// 课程标题

  @ApiModelProperty(value = "课程开始时间", example = "2017-06-07 18:00:00")
  private Date startTime;// 课程开始时间

  @ApiModelProperty(value = "课程结束时间", example = "2017-06-07 18:25:00")
  private Date endTime;// 课程结束时间

  @ApiModelProperty(value = "课程课件地址",
      example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/courseware%2Fone2one%2FGeneral_Level_1%2FMain%20Course%20Level%201_Lesson%204_Age.pptx")
  private String courseCourseware;// 课程课件地址

  @ApiModelProperty(value = "课程简介(只有大课才有课程简介)", example = "study hard, improve every day")
  private String courseDesc;// 课程简介(只有大课才有课程简介)

  @ApiModelProperty(value = "老师头像",
      example = "http://webi-hwj-test.oss-cn-hangzhou.aliyuncs.com/images/teacher/2f473b5087e24a3cbf73a995768a5bc1.jpg")
  private String teacherPhoto;// 老师头像

  @ApiModelProperty(value = "老师姓名", example = "Matt")
  private String teacherName;// 老师姓名

  @ApiModelProperty(value = "老师国籍", example = "中国")
  private String teacherNationality;// 老师国籍

  @ApiModelProperty(value = "上课平台((上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放)))", example = "0")
  private Integer teacherTimePlatform;// 上课平台(上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放))

  @ApiModelProperty(value = "课程限制人数(若返回为0,表示该课程类型不限制人数)", example = "6")
  private Integer courseTypeLimitNumber;// 课程限制人数

  @ApiModelProperty(value = "提前上课时间(分钟)", example = "15")
  private Integer courseTypeBeforeGoclassTime;// 提前上课时间(分钟)

  @ApiModelProperty(value = "提前取消预约时间(分钟)", example = "480")
  private Integer courseTypeCancelSubscribeTime;// 提前取消预约时间(分钟)

  @ApiModelProperty(value = "出席状态(0:未出席,1:已出席)", example = "0")
  private Integer subscribeStatus;// 出席状态(0:未出席,1:已出席)

  @ApiModelProperty(value = "状态(1-可取消预约;2-倒计时;3-可进入教室)", example = "1")
  private Integer status;// 状态(1-可取消预约;2-倒计时;3-可进入教室)

  @ApiModelProperty(value = "当前时间是否已过了课程结束时间(0:是;1:否)", example = "1")
  private Integer courseEndTimestatus;// 是否已过课程结束时间(0:当前时间>课程结束时间(过了);1:当前时间<课程结束时间(还没过))

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getCurrentStartTime() {
    return currentStartTime;
  }

  public void setCurrentStartTime(Date currentStartTime) {
    this.currentStartTime = currentStartTime;
  }

  public Date getCurrentEndTime() {
    return currentEndTime;
  }

  public void setCurrentEndTime(Date currentEndTime) {
    this.currentEndTime = currentEndTime;
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

  public String getCoursePic() {
    return coursePic;
  }

  public void setCoursePic(String coursePic) {
    this.coursePic = coursePic;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
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

  public String getCourseCourseware() {
    return courseCourseware;
  }

  public void setCourseCourseware(String courseCourseware) {
    this.courseCourseware = courseCourseware;
  }

  public String getCourseDesc() {
    return courseDesc;
  }

  public void setCourseDesc(String courseDesc) {
    this.courseDesc = courseDesc;
  }

  public String getTeacherPhoto() {
    return teacherPhoto;
  }

  public void setTeacherPhoto(String teacherPhoto) {
    this.teacherPhoto = teacherPhoto;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getTeacherNationality() {
    return teacherNationality;
  }

  public void setTeacherNationality(String teacherNationality) {
    this.teacherNationality = teacherNationality;
  }

  public Integer getTeacherTimePlatform() {
    return teacherTimePlatform;
  }

  public void setTeacherTimePlatform(Integer teacherTimePlatform) {
    this.teacherTimePlatform = teacherTimePlatform;
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

  public Integer getCourseTypeCancelSubscribeTime() {
    return courseTypeCancelSubscribeTime;
  }

  public void setCourseTypeCancelSubscribeTime(Integer courseTypeCancelSubscribeTime) {
    this.courseTypeCancelSubscribeTime = courseTypeCancelSubscribeTime;
  }

  public Integer getSubscribeStatus() {
    return subscribeStatus;
  }

  public void setSubscribeStatus(Integer subscribeStatus) {
    this.subscribeStatus = subscribeStatus;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getCourseEndTimestatus() {
    return courseEndTimestatus;
  }

  public void setCourseEndTimestatus(Integer courseEndTimestatus) {
    this.courseEndTimestatus = courseEndTimestatus;
  }

}
