package com.webi.hwj.courseone2many.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 会员中心改版-ES课程相关信息模型(返回给前端)<br>
 * Description: 会员中心改版-ES课程相关信息模型(返回给前端)<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年7月20日 下午3:48:51
 * 
 * @author felix.yl
 */
@ApiModel(value = "CourseSchedulingApiParam(会员中心改版-ES/OE课程相关信息模型(返回给前端))")
public class CourseSchedulingApiParam implements Serializable {

  private static final long serialVersionUID = -4434138099060284188L;

  @ApiModelProperty(value = "课程及教师信息list")
  private List<CourseSchedulingParam> courseInfoList;// 课程以及老师相关信息

  /**
   * 以下4个字段的内容值是从缓存中获取的
   */
  @ApiModelProperty(value = "上课平台((上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放);2:classin(有课程回放)))",
      example = "0")
  private Integer teacherTimePlatform;// 上课平台(上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放))

  @ApiModelProperty(value = "提前预约时间(分钟)", example = "480")
  private Integer courseTypeSubscribeTime;// 提前预约时间(分钟)

  @ApiModelProperty(value = "提前取消预约时间(分钟)", example = "480")
  private Integer courseTypeCancelSubscribeTime;// 提前取消预约时间(分钟)

  @ApiModelProperty(value = "提前上课时间(分钟)", example = "15")
  private Integer courseTypeBeforeGoclassTime;// 提前上课时间(分钟)

  @ApiModelProperty(value = "课程类型中文名", example = "English-Studio")
  private String courseTypeChineseName;// 课程类型中文名

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<CourseSchedulingParam> getCourseInfoList() {
    return courseInfoList;
  }

  public void setCourseInfoList(List<CourseSchedulingParam> courseInfoList) {
    this.courseInfoList = courseInfoList;
  }

  public Integer getTeacherTimePlatform() {
    return teacherTimePlatform;
  }

  public void setTeacherTimePlatform(Integer teacherTimePlatform) {
    this.teacherTimePlatform = teacherTimePlatform;
  }

  public Integer getCourseTypeSubscribeTime() {
    return courseTypeSubscribeTime;
  }

  public void setCourseTypeSubscribeTime(Integer courseTypeSubscribeTime) {
    this.courseTypeSubscribeTime = courseTypeSubscribeTime;
  }

  public Integer getCourseTypeCancelSubscribeTime() {
    return courseTypeCancelSubscribeTime;
  }

  public void setCourseTypeCancelSubscribeTime(Integer courseTypeCancelSubscribeTime) {
    this.courseTypeCancelSubscribeTime = courseTypeCancelSubscribeTime;
  }

  public Integer getCourseTypeBeforeGoclassTime() {
    return courseTypeBeforeGoclassTime;
  }

  public void setCourseTypeBeforeGoclassTime(Integer courseTypeBeforeGoclassTime) {
    this.courseTypeBeforeGoclassTime = courseTypeBeforeGoclassTime;
  }

  public String getCourseTypeChineseName() {
    return courseTypeChineseName;
  }

  public void setCourseTypeChineseName(String courseTypeChineseName) {
    this.courseTypeChineseName = courseTypeChineseName;
  }

}
