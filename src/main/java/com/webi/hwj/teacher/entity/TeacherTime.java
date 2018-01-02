package com.webi.hwj.teacher.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category teacherTime Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_teacher_time")
public class TeacherTime implements Serializable {
  private static final long serialVersionUID = 544713458742730889L;
  // 主键id
  private String keyId;
  // 老师id(逻辑外键)
  private String teacherId;
  // 老师名称
  private String teacherName;
  // 跟码表里的课程类别走
  private String courseType;
  // 微立方房间ID
  private String roomId;
  // webex房间ID
  private String webexRoomHostId;
  // 会议号
  private String webexMeetingKey;
  // 允许上课开始时间
  private Date startTime;
  // 允许上课结束时间
  private Date endTime;
  // 是否被学生预约(0:否,1:是)
  private Boolean isSubscribe;
  // 老师是否确认
  private Boolean isConfirm;
  // 老师是否出席(0:否,1:是)
  private Boolean isAttend;
  // 上课平台(0:webex;1:展示互动;2:classin)
  private Integer teacherTimePlatform;
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

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
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

  public String getWebexRoomHostId() {
    return webexRoomHostId;
  }

  public void setWebexRoomHostId(String webexRoomHostId) {
    this.webexRoomHostId = webexRoomHostId;
  }

  public String getWebexMeetingKey() {
    return webexMeetingKey;
  }

  public void setWebexMeetingKey(String webexMeetingKey) {
    this.webexMeetingKey = webexMeetingKey;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public Boolean getIsAttend() {
    return isAttend;
  }

  public void setIsAttend(Boolean isAttend) {
    this.isAttend = isAttend;
  }

  public Integer getTeacherTimePlatform() {
    return teacherTimePlatform;
  }

  public void setTeacherTimePlatform(Integer teacherTimePlatform) {
    this.teacherTimePlatform = teacherTimePlatform;
  }
}