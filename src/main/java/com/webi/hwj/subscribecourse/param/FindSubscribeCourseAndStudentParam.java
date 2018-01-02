package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

public class FindSubscribeCourseAndStudentParam implements Serializable {
  private static final long serialVersionUID = -3287382946610544442L;
  //
  private String keyId;
  // 课程开始时间
  private Date startTime;
  // 课程结束时间
  private Date endTime;
  // 课程标题
  private String courseTitle;
  // 课程类型
  private String courseType;
  // 课程类型
  private String courseTypeChineseName;
  // 老师名字
  private String teacherName;
  // 老师来源
  private String thirdFrom;
  // 学员名字
  private String userName;
  // 学员id
  private String userId;
  // 学员编号
  private String userCode;
  // 学员手机号
  private String phone;
  // 学员show
  private Boolean subscribeStatus;
  // 房间id
  private String roomId;
  // 老师时间id
  private String teacherTimeId;
  // 老师id
  private String teacherId;
  // 教务id
  private String learningCoachId;
  // 教务名称
  private String adminUserName;
  // 老师是否出席(0:否,1:是)
  private Boolean isAttend;
  // lc记录
  private String subscribeNote;
  // lc记录人
  private String subscribeNoteTaker;
  // lc记录时间
  private Date subscribeNoteDate;
  // 学员访问链接
  private String inviteUrl;
  // 学员show
  private Boolean isFirst;
  // 记录类型
  private Integer subscribeNoteType;
  // 评论内容
  private String commentContent;
  // 房间url
  private String webexRequestUrl;
  // 会议号id
  private String webexMeetingKey;
  // 房间号
  private String webexRoomHostId;

  private Integer transactionAmount;

  public String cons;

  private String reportUrl;

  private String commentUrl;

  // 创建时间
  private Date createDate;

  private Integer subscribeType;

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
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

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
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

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Boolean getSubscribeStatus() {
    return subscribeStatus;
  }

  public void setSubscribeStatus(Boolean subscribeStatus) {
    this.subscribeStatus = subscribeStatus;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public String getLearningCoachId() {
    return learningCoachId;
  }

  public void setLearningCoachId(String learningCoachId) {
    this.learningCoachId = learningCoachId;
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

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public Boolean getIsAttend() {
    return isAttend;
  }

  public void setIsAttend(Boolean isAttend) {
    this.isAttend = isAttend;
  }

  public String getSubscribeNote() {
    return subscribeNote;
  }

  public void setSubscribeNote(String subscribeNote) {
    this.subscribeNote = subscribeNote;
  }

  public String getSubscribeNoteTaker() {
    return subscribeNoteTaker;
  }

  public void setSubscribeNoteTaker(String subscribeNoteTaker) {
    this.subscribeNoteTaker = subscribeNoteTaker;
  }

  public Date getSubscribeNoteDate() {
    return subscribeNoteDate;
  }

  public void setSubscribeNoteDate(Date subscribeNoteDate) {
    this.subscribeNoteDate = subscribeNoteDate;
  }

  public String getInviteUrl() {
    return inviteUrl;
  }

  public void setInviteUrl(String inviteUrl) {
    this.inviteUrl = inviteUrl;
  }

  public Boolean getIsFirst() {
    return isFirst;
  }

  public void setIsFirst(Boolean isFirst) {
    this.isFirst = isFirst;
  }

  public Integer getSubscribeNoteType() {
    return subscribeNoteType;
  }

  public void setSubscribeNoteType(Integer subscribeNoteType) {
    this.subscribeNoteType = subscribeNoteType;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
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

  public Integer getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(Integer transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  public String getReportUrl() {
    return reportUrl;
  }

  public void setReportUrl(String reportUrl) {
    this.reportUrl = reportUrl;
  }

  public String getCommentUrl() {
    return commentUrl;
  }

  public void setCommentUrl(String commentUrl) {
    this.commentUrl = commentUrl;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getWebexRoomHostId() {
    return webexRoomHostId;
  }

  public void setWebexRoomHostId(String webexRoomHostId) {
    this.webexRoomHostId = webexRoomHostId;
  }

  public Integer getSubscribeType() {
    return subscribeType;
  }

  public void setSubscribeType(Integer subscribeType) {
    this.subscribeType = subscribeType;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
