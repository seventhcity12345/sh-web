package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

public class FindSubscribeCourseAndTimeParam implements Serializable {
  private static final long serialVersionUID = -755980224389223011L;
  //
  private String keyId;
  // 学员id
  private String userId;
  // 课程开始时间
  private Date startTime;
  // 课程结束时间
  private Date endTime;
  // 会议号
  private String webexMeetingKey;
  // 评论内容
  private String commentContent;
  // 房间地址
  private String webexRequestUrl;
  // 房间号
  private String webexRoomHostId;
  // 课程类型
  private String courseType;

  // 用于ids
  private List<String> userIds;

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

  public String getWebexMeetingKey() {
    return webexMeetingKey;
  }

  public void setWebexMeetingKey(String webexMeetingKey) {
    this.webexMeetingKey = webexMeetingKey;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

  public String getWebexRequestUrl() {
    return webexRequestUrl;
  }

  public void setWebexRequestUrl(String webexRequestUrl) {
    this.webexRequestUrl = webexRequestUrl;
  }

  public String getWebexRoomHostId() {
    return webexRoomHostId;
  }

  public void setWebexRoomHostId(String webexRoomHostId) {
    this.webexRoomHostId = webexRoomHostId;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

}
