package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 课程表数据<br> 
 * Description: 课程表数据<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2016年9月21日 上午10:13:19 
 * @author komi.zsy
 */
public class SubscribeCourseParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = 1871747613519539902L;
  // 主键id
  private String keyId;
  // 用户id
  private String userId;
  // 老师上课时间id
  private String teacherTimeId;
  // 老师id
  private String teacherId;
  // 老师名称
  private String teacherName;
  // 老师图片
  private String teacherPhoto;
  // 课程id（小课存的是1v1课程表的主键,大课存的是t_teacher_time_scheduling表的主键）
  private String courseId;
  // 体系类别
  private String categoryType;
  // 跟码表里的课程类别走
  private String courseType;
  // 跟码表里的课程类别走
  private String courseTypeChineseName;
  // 课程标题
  private String courseTitle;
  // 预约状态(0:未上课,1:已上课)
  private Integer subscribeStatus;
  // 按钮状态  1：可取消预约  2：倒计时  3：进入教室
  private Integer status;
  // 课件ppt地址
  private String courseCourseware;
  // 课程图片
  private String coursePic;
  // 上课开始时间
  private Date startTime;
  // 上课结束时间
  private Date endTime;
  // 学生上课地址
  private String inviteUrl;
  // 增加倒计时时间
  private Long beforeLessonCountdown;
  // 提前进入教室的时间
  private Long beforeLessonTime;

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

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
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

  public String getTeacherPhoto() {
    return teacherPhoto;
  }

  public void setTeacherPhoto(String teacherPhoto) {
    this.teacherPhoto = teacherPhoto;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
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

  public String getCourseTypeChineseName() {
    return courseTypeChineseName;
  }

  public void setCourseTypeChineseName(String courseTypeChineseName) {
    this.courseTypeChineseName = courseTypeChineseName;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
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

  public String getCourseCourseware() {
    return courseCourseware;
  }

  public void setCourseCourseware(String courseCourseware) {
    this.courseCourseware = courseCourseware;
  }

  public String getCoursePic() {
    return coursePic;
  }

  public void setCoursePic(String coursePic) {
    this.coursePic = coursePic;
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

  public String getInviteUrl() {
    return inviteUrl;
  }

  public void setInviteUrl(String inviteUrl) {
    this.inviteUrl = inviteUrl;
  }

  public Long getBeforeLessonCountdown() {
    return beforeLessonCountdown;
  }

  public void setBeforeLessonCountdown(Long beforeLessonCountdown) {
    this.beforeLessonCountdown = beforeLessonCountdown;
  }

  public Long getBeforeLessonTime() {
    return beforeLessonTime;
  }

  public void setBeforeLessonTime(Long beforeLessonTime) {
    this.beforeLessonTime = beforeLessonTime;
  }

 }