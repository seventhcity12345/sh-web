package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CourseTypeCommentListParam(成人-已完成课程-指定课程类型的评价信息列表)")
public class CourseTypeCommentListParam implements Serializable {

  private static final long serialVersionUID = -1465446673411366622L;

  @ApiModelProperty(value = "预约表主键(预约id)")
  private String keyId;// 预约表主键(预约id)

  @ApiModelProperty(value = "用户id")
  private String userId;// 用户id

  @ApiModelProperty(value = "当前时间")
  private Date currentTime;// 当前时间

  @ApiModelProperty(value = "课程类型")
  private String courseType;// 课程类型

  @ApiModelProperty(value = "课程类型中文名称")
  private String courseTypeChineseName;// 课程类型中文名称

  @ApiModelProperty(value = "预约状态(0:未上课,1:已上课)-课程预约表中查出来的")
  private Integer subscribeStatus;// 预约状态(0:未上课,1:已上课)

  @ApiModelProperty(value = "上课开始时间")
  private Date startTime;// 上课开始时间

  @ApiModelProperty(value = "上课结束时间")
  private Date endTime;// 上课结束时间

  @ApiModelProperty(value = "课程标题")
  private String courseTitle;// 课程标题

  @ApiModelProperty(value = "显示分数(平均分)")
  private String showScore;// 显示分数(平均分)

  @ApiModelProperty(value = "老师名称")
  private String teacherName;// 老师名称

  @ApiModelProperty(value = "老师id")
  private String teacherId;// 老师id

  @ApiModelProperty(value = "课程状态(0:未出席,1:待评价,2:已出席已评价)-课程状态,返回给前端的,前端需要关注;")
  private Integer status;// 课程状态(0:未出席,1:待评价,2:已出席已评价)

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

  public Date getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(Date currentTime) {
    this.currentTime = currentTime;
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getShowScore() {
    return showScore;
  }

  public void setShowScore(String showScore) {
    this.showScore = showScore;
  }

}
