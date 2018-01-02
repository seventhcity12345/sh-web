package com.webi.hwj.courseone2many.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 会员中心改版-ES课程预约信息模型<br>
 * Description: 会员中心改版-ES课程预约信息模型<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年7月20日 下午3:34:34
 * 
 * @author felix.yl
 */
@ApiModel(value = "CourseSchedulingParam(会员中心改版-ES课程预约信息模型)")
public class CourseSchedulingParam implements Serializable {

  private static final long serialVersionUID = 349594573054099011L;

  private String keyId;// 主键Id

  private Date paramTime;// 参数时间(作为查询的参数)

  @ApiModelProperty(value = "课程类型", example = "course_type8")
  private String courseType;// 课程类型

  @ApiModelProperty(value = "课程简介",
      example = "Love your parents. We are too busy growing up yet we forget that they are already growing old")
  private String courseDesc;// 课程简介

  @ApiModelProperty(value = "课程图片",
      example = "https://teenagertestcdn.speakhi.com/images/teacher/33e51155701d4ad383b92cbd9fe05fed.png")
  private String coursePic;// 课程图片

  @ApiModelProperty(value = "课程标题", example = "Love")
  private String courseTitle;// 课程标题

  @ApiModelProperty(value = "课件地址",
      example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/courseware%2Fone2one%2FGeneral_Level_6%2FMain%20Course%20Level%206_Lesson%202_Work%20Review.pptx")
  private String courseCourseware;// 课件地址

  @ApiModelProperty(value = "课程开始时间", example = "2017-07-20 08:00:00")
  private Date startTime;// 课程开始时间

  @ApiModelProperty(value = "课程结束时间", example = "2017-07-20 08:25:00")
  private Date endTime;// 课程结束时间

  @ApiModelProperty(value = "课程限制人数(若返回为0,表示该课程类型不限制人数)", example = "6")
  private Integer limitNumber;// 课程限制人数

  @ApiModelProperty(value = "课程已预约人数", example = "10")
  private Integer alreadyPersonCount;// 课程已预约人数

  @ApiModelProperty(value = "老师图片",
      example = "https://teenagertestcdn.speakhi.com/images/teacher/33e51155701d4ad383b92cbd9fe05fed.png")
  private String teacherPhoto;// 教师图片

  @ApiModelProperty(value = "老师姓名", example = "Steven")
  private String teacherName;// 老师姓名

  @ApiModelProperty(value = "老师国籍", example = "中国")
  private String teacherNationality;// 老师国籍

  @ApiModelProperty(value = "是否已预约标志(0:未预约;1:已预约)", example = "1")
  private String subscribeFlag;// 是否已预约标志

  @ApiModelProperty(value = "预约id", example = "00530f8153bb4e0fb514d7bd9f490e48")
  private String subscribeId;// 预约id

  @ApiModelProperty(value = "老师上课时间id", example = "00530f8153bb4e0fb514d7bd9f490e48")
  private String teacherTimeId;// 老师上课时间id

  @ApiModelProperty(value = "状态(1-可取消预约;2-倒计时;3-可进入教室)", example = "1")
  private Integer status;// 状态(1-可取消预约;2-倒计时;3-可进入教室)

  @ApiModelProperty(value = "上课平台(上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放);2:classin(有课程回放))")
  // 上课平台(上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放))
  private Integer teacherTimePlatform;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public Date getParamTime() {
    return paramTime;
  }

  public void setParamTime(Date paramTime) {
    this.paramTime = paramTime;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getCourseDesc() {
    return courseDesc;
  }

  public void setCourseDesc(String courseDesc) {
    this.courseDesc = courseDesc;
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

  public String getCourseCourseware() {
    return courseCourseware;
  }

  public void setCourseCourseware(String courseCourseware) {
    this.courseCourseware = courseCourseware;
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

  public Integer getLimitNumber() {
    return limitNumber;
  }

  public void setLimitNumber(Integer limitNumber) {
    this.limitNumber = limitNumber;
  }

  public Integer getAlreadyPersonCount() {
    return alreadyPersonCount;
  }

  public void setAlreadyPersonCount(Integer alreadyPersonCount) {
    this.alreadyPersonCount = alreadyPersonCount;
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

  public String getSubscribeFlag() {
    return subscribeFlag;
  }

  public void setSubscribeFlag(String subscribeFlag) {
    this.subscribeFlag = subscribeFlag;
  }

  public String getSubscribeId() {
    return subscribeId;
  }

  public void setSubscribeId(String subscribeId) {
    this.subscribeId = subscribeId;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getTeacherTimePlatform() {
    return teacherTimePlatform;
  }

  public void setTeacherTimePlatform(Integer teacherTimePlatform) {
    this.teacherTimePlatform = teacherTimePlatform;
  }

}
