package com.webi.hwj.teacher.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: TeacherTimeParam<br>
 * Description: 一些业务逻辑的通用属性<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年8月2日 上午10:45:20
 * 
 * @author yangmh
 */
@TableName("t_teacher_time")
public class TeacherTimeParam implements Serializable {
  private static final long serialVersionUID = 5539170325151900604L;
  // 主键id
  private String keyId;
  // 老师id(逻辑外键)
  private String teacherId;
  // 跟码表里的课程类别走
  private String courseType;
  // 微立方房间ID
  private String roomId;
  // webex房间ID
  private String webexRoomHostId;
  // 允许上课开始时间
  private Date startTime;
  // 允许上课结束时间
  private Date endTime;
  // 是否被学生预约(0:否,1:是)
  private Boolean isSubscribe;
  // 老师是否确认
  private Boolean isConfirm;
  // 老师是否出席
  private Boolean isAttend;
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
  // 老师第三方来源
  private String thirdFrom;

  // 一些使用的属性
  private String teacherCourseType;

  // 老师名称
  private String teacherName;
  // 用于组合查询条件
  public String cons;
  // 用于in查询
  private List<String> teacherIds;
  // 用于in查询
  private List<String> teacherTimeIds;
  // 房间url
  private String webexRequestUrl;
  // 会议号
  private String webexMeetingKey;

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

  public String getWebexRoomHostId() {
    return webexRoomHostId;
  }

  public void setWebexRoomHostId(String webexRoomHostId) {
    this.webexRoomHostId = webexRoomHostId;
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

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public List<String> getTeacherIds() {
    return teacherIds;
  }

  public void setTeacherIds(List<String> teacherIds) {
    this.teacherIds = teacherIds;
  }

  public List<String> getTeacherTimeIds() {
    return teacherTimeIds;
  }

  public void setTeacherTimeIds(List<String> teacherTimeIds) {
    this.teacherTimeIds = teacherTimeIds;
  }

  public Boolean getIsAttend() {
    return isAttend;
  }

  public void setIsAttend(Boolean isAttend) {
    this.isAttend = isAttend;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

  public String getTeacherCourseType() {
    return teacherCourseType;
  }

  public void setTeacherCourseType(String teacherCourseType) {
    this.teacherCourseType = teacherCourseType;
  }

  public String getWebexRequestUrl() {
    return webexRequestUrl;
  }

  public void setWebexRequestUrl(String webexRequestUrl) {
    this.webexRequestUrl = webexRequestUrl;
  }

  public String getWebexMeetingKey() {
    return webexMeetingKey;
  }

  public void setWebexMeetingKey(String webexMeetingKey) {
    this.webexMeetingKey = webexMeetingKey;
  }

}