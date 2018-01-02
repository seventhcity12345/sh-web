package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CourseTypeCommentCountParam(成人-已完成课程-已完成课程总数模型)")
public class CourseTypeCommentCountParam implements Serializable {

  private static final long serialVersionUID = -5684973887146410323L;

  @ApiModelProperty(value = "学员userId(查询参数)")
  private String userId;// 学员userId(查询参数)

  @ApiModelProperty(value = "当前时间(查询参数)")
  private Date currentTime;// 当前时间(查询参数)

  @ApiModelProperty(value = "课程类型", example = "course_type5")
  private String courseType;// 课程类型

  @ApiModelProperty(value = "已完成课程总数(节数)", example = "12")
  private int commentCount;// 已完成课程总数

  @ApiModelProperty(value = "课程类型中文名", example = "oc课")
  private String courseTypeChineseName;// 课程类型中文名

  public String toString() {
    return ReflectUtil.reflectToString(this);
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

  public int getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(int commentCount) {
    this.commentCount = commentCount;
  }

  public String getCourseTypeChineseName() {
    return courseTypeChineseName;
  }

  public void setCourseTypeChineseName(String courseTypeChineseName) {
    this.courseTypeChineseName = courseTypeChineseName;
  }

}
