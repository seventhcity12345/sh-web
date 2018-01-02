package com.webi.hwj.subscribecourse.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * @category subscribeCourse Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_subscribe_course")
public class SubscribeCourse implements Serializable {
  private static final long serialVersionUID = 1540906203022641036L;
  // 主键id
  @ApiModelProperty(value = "预约id", required = true, example = "0199fc74402b4b98a867e7cc5ead0d9b")
  private String keyId;
  // 用户id
  private String userId;
  // 用户手机号
  private String userPhone;
  // 学生姓名
  private String userName;
  // 学生当前级别
  private String userLevel;
  // 老师上课时间id
  @ApiModelProperty(value = "老师上课时间id", required = true,
      example = "00c11d97e4694c469f10dfc1bcf37cb8")
  private String teacherTimeId;
  // 老师id
  private String teacherId;
  // 老师名称
  private String teacherName;
  // 课程id（小课存的是1v1课程表的主键,大课存的是t_teacher_time_scheduling表的主键）
  private String courseId;
  // 体系类别
  private String categoryType;
  // 跟码表里的课程类别走
  private String courseType;
  // 课程标题
  @ApiModelProperty(value = "课程标题", required = true, example = "heiheihei")
  private String courseTitle;
  // 预约状态(0:未上课,1:已上课)
  private Integer subscribeStatus;
  // 课件ppt地址
  private String courseCourseware;
  // 课程图片
  private String coursePic;
  // 合同子表id
  private String orderOptionId;
  // 合同主表id
  private String orderId;
  // 上课开始时间
  @ApiModelProperty(value = "上课开始时间", required = true, example = "1465820000000")
  private Date startTime;
  // 上课结束时间
  @ApiModelProperty(value = "上课结束时间", required = true, example = "1465830000000")
  private Date endTime;
  // 学生上课地址
  private String inviteUrl;
  // 预约来源
  private String subscribeFrom;
  // 学员show
  private Boolean isFirst;
  // 预约备注
  private String subscribeRemark;
  // 预约类型目前只用于demo课（0成人，1青少）
  private Integer subscribeType;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
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

  public String getOrderOptionId() {
    return orderOptionId;
  }

  public void setOrderOptionId(String orderOptionId) {
    this.orderOptionId = orderOptionId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
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

  public String getSubscribeFrom() {
    return subscribeFrom;
  }

  public void setSubscribeFrom(String subscribeFrom) {
    this.subscribeFrom = subscribeFrom;
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

  public Boolean getIsFirst() {
    return isFirst;
  }

  public void setIsFirst(Boolean isFirst) {
    this.isFirst = isFirst;
  }

  public String getSubscribeRemark() {
    return subscribeRemark;
  }

  public void setSubscribeRemark(String subscribeRemark) {
    this.subscribeRemark = subscribeRemark;
  }

  public Integer getSubscribeType() {
    return subscribeType;
  }

  public void setSubscribeType(Integer subscribeType) {
    this.subscribeType = subscribeType;
  }
}