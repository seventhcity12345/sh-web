package com.webi.hwj.teacher.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 用于查询老师端-课程中心的头部红绿色小图标列表.<br>
 * Description: FindTeacherCourseCenterTopGreenRedDateParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年2月10日 下午5:36:12
 * 
 * @author yangmh
 */
public class FindTeacherCourseCenterCoreCourseParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 1512950108424235058L;
  @ApiModelProperty(value = "课程标题", required = true, example = "hello word")
  private String courseTitle;
  @ApiModelProperty(value = "课程id", required = true, example = "4832eb13ea884f92b6c9a9e26c599d01")
  private String courseId;
  @ApiModelProperty(value = "课程类型", required = true, example = "course_type1")
  private String courseType;
  @ApiModelProperty(value = "课程图片", required = false,
      example = "http://speakhi-teenager-test.oss-cn-hangzhou.aliyuncs.com/images/course/one2many/course_type2/057f089a8a314256a0c3209dda067819.jpg")
  private String coursePic;
  @ApiModelProperty(value = "课程课件", required = false,
      example = "http://speakhi-teenager-test.oss-cn-hangzhou.aliyuncs.com/courseware/one2many/course_type2/a9a9af036dbc4edfb0697e3be39f2ee3.pptx")
  private String courseCourseware;
  @ApiModelProperty(value = "用户id", required = false,
      example = "89ac3f952e7145a38ca3ea2faa70f91a")
  private String userId;
  @ApiModelProperty(value = "老师时间id（用于老师确认&进入教室）", required = false,
      example = "f9121b379c04482fbbe0640871e9b4d4")
  private String teacherTimeId;
  private String teacherId;
  @ApiModelProperty(value = "预约id", required = false,
      example = "025411316c184295b1bd22330a963a71")
  private String subscribeId;
  @ApiModelProperty(value = "开始时间", required = false,
      example = "1486951200000")
  private Date startTime;
  @ApiModelProperty(value = "结束时间", required = false,
      example = "1486954500000")
  private Date endTime;
  @ApiModelProperty(value = "学生是否已出席（0:未出席,1:已出席）", required = false,
      example = "1")
  private Integer subscribeStatus;
  @ApiModelProperty(value = "老师是否确认（用于绿色对号显示）", required = false,
      example = "true")
  private Boolean isConfirm;
  @ApiModelProperty(value = "用户图片", required = false,
      example = "http://speakhi-teenager-test.oss-cn-hangzhou.aliyuncs.com/images/course/one2many/course_type2/057f089a8a314256a0c3209dda067819.jpg")
  private String userPhoto;
  @ApiModelProperty(value = "用户级别", required = false,
      example = "General Level 7")
  private String currentLevel;
  @ApiModelProperty(value = "用户英文名", required = false,
      example = "xiaoming")
  private String englishName;
  @ApiModelProperty(value = "用户性别（0:女,1:男）", required = false,
      example = "1")
  private Integer gender;
  @ApiModelProperty(value = "省", required = false,
      example = "上海")
  private String province;
  private String userGrade;
  @ApiModelProperty(value = "年龄", required = false,
      example = "99")
  private Integer age;
  private String queryDate;
  private Date curDate;
  private String lastSubscribeCourseTitle;
  private String lastSubscribeCourseType;
  @ApiModelProperty(value = "课程类型英文名", required = false,
      example = "ES")
  private String courseTypeEnglishName;
  private String adminUserName;
  @ApiModelProperty(value = "预约类型目前只用于demo课（0成人，1青少）", required = false,
      example = "1")
  private Integer subscribeType;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getSubscribeId() {
    return subscribeId;
  }

  public void setSubscribeId(String subscribeId) {
    this.subscribeId = subscribeId;
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

  public Integer getSubscribeStatus() {
    return subscribeStatus;
  }

  public void setSubscribeStatus(Integer subscribeStatus) {
    this.subscribeStatus = subscribeStatus;
  }

  public Boolean getIsConfirm() {
    return isConfirm;
  }

  public void setIsConfirm(Boolean isConfirm) {
    this.isConfirm = isConfirm;
  }

  public String getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }

  public String getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(String currentLevel) {
    this.currentLevel = currentLevel;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getUserGrade() {
    return userGrade;
  }

  public void setUserGrade(String userGrade) {
    this.userGrade = userGrade;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getQueryDate() {
    return queryDate;
  }

  public void setQueryDate(String queryDate) {
    this.queryDate = queryDate;
  }

  public Date getCurDate() {
    return curDate;
  }

  public void setCurDate(Date curDate) {
    this.curDate = curDate;
  }

  public String getLastSubscribeCourseTitle() {
    return lastSubscribeCourseTitle;
  }

  public void setLastSubscribeCourseTitle(String lastSubscribeCourseTitle) {
    this.lastSubscribeCourseTitle = lastSubscribeCourseTitle;
  }

  public String getLastSubscribeCourseType() {
    return lastSubscribeCourseType;
  }

  public void setLastSubscribeCourseType(String lastSubscribeCourseType) {
    this.lastSubscribeCourseType = lastSubscribeCourseType;
  }

  public String getCourseTypeEnglishName() {
    return courseTypeEnglishName;
  }

  public void setCourseTypeEnglishName(String courseTypeEnglishName) {
    this.courseTypeEnglishName = courseTypeEnglishName;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public Integer getSubscribeType() {
    return subscribeType;
  }

  public void setSubscribeType(Integer subscribeType) {
    this.subscribeType = subscribeType;
  }

}