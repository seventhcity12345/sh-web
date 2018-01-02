package com.webi.hwj.teacher.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 用于查询老师端-课程中心的头部红绿色小图标列表.<br>
 * Description: FindTeacherCourseCenterTopGreenRedDateParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年2月10日 下午5:36:12
 * 
 * @author seven
 */
public class FindTeacherCourseCenterCourseListParam implements Serializable {
  private static final long serialVersionUID = -4116969483120352626L;

  @ApiModelProperty(value = "core课程list", required = false)
  private List<FindTeacherCourseCenterCoreCourseParam> teacherCourseCenterCoreCourseList;

  @ApiModelProperty(value = "extra课程list", required = false)
  private List<FindTeacherCourseCenterExtraCourseParam> teacherCourseCenterExtraCourseList;

  @ApiModelProperty(value = "courseType1提前进入教室时间", required = false, example = "5")
  private Integer courseType1BeforeGoclassTime;

  @ApiModelProperty(value = "courseType2提前进入教室时间", required = false, example = "5")
  private Integer courseType2BeforeGoclassTime;

  @ApiModelProperty(value = "courseType4提前进入教室时间", required = false, example = "5")
  private Integer courseType4BeforeGoclassTime;

  @ApiModelProperty(value = "courseType5提前进入教室时间", required = false, example = "5")
  private Integer courseType5BeforeGoclassTime;

  @ApiModelProperty(value = "courseType8提前进入教室时间", required = false, example = "5")
  private Integer courseType8BeforeGoclassTime;

  @ApiModelProperty(value = "courseType9提前进入教室时间", required = false, example = "5")
  private Integer courseType9BeforeGoclassTime;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<FindTeacherCourseCenterCoreCourseParam> getTeacherCourseCenterCoreCourseList() {
    return teacherCourseCenterCoreCourseList;
  }

  public void setTeacherCourseCenterCoreCourseList(
      List<FindTeacherCourseCenterCoreCourseParam> teacherCourseCenterCoreCourseList) {
    this.teacherCourseCenterCoreCourseList = teacherCourseCenterCoreCourseList;
  }

  public List<FindTeacherCourseCenterExtraCourseParam> getTeacherCourseCenterExtraCourseList() {
    return teacherCourseCenterExtraCourseList;
  }

  public void setTeacherCourseCenterExtraCourseList(
      List<FindTeacherCourseCenterExtraCourseParam> teacherCourseCenterExtraCourseList) {
    this.teacherCourseCenterExtraCourseList = teacherCourseCenterExtraCourseList;
  }

  public Integer getCourseType1BeforeGoclassTime() {
    return courseType1BeforeGoclassTime;
  }

  public void setCourseType1BeforeGoclassTime(Integer courseType1BeforeGoclassTime) {
    this.courseType1BeforeGoclassTime = courseType1BeforeGoclassTime;
  }

  public Integer getCourseType2BeforeGoclassTime() {
    return courseType2BeforeGoclassTime;
  }

  public void setCourseType2BeforeGoclassTime(Integer courseType2BeforeGoclassTime) {
    this.courseType2BeforeGoclassTime = courseType2BeforeGoclassTime;
  }

  public Integer getCourseType4BeforeGoclassTime() {
    return courseType4BeforeGoclassTime;
  }

  public void setCourseType4BeforeGoclassTime(Integer courseType4BeforeGoclassTime) {
    this.courseType4BeforeGoclassTime = courseType4BeforeGoclassTime;
  }

  public Integer getCourseType5BeforeGoclassTime() {
    return courseType5BeforeGoclassTime;
  }

  public void setCourseType5BeforeGoclassTime(Integer courseType5BeforeGoclassTime) {
    this.courseType5BeforeGoclassTime = courseType5BeforeGoclassTime;
  }

  public Integer getCourseType8BeforeGoclassTime() {
    return courseType8BeforeGoclassTime;
  }

  public void setCourseType8BeforeGoclassTime(Integer courseType8BeforeGoclassTime) {
    this.courseType8BeforeGoclassTime = courseType8BeforeGoclassTime;
  }

  public Integer getCourseType9BeforeGoclassTime() {
    return courseType9BeforeGoclassTime;
  }

  public void setCourseType9BeforeGoclassTime(Integer courseType9BeforeGoclassTime) {
    this.courseType9BeforeGoclassTime = courseType9BeforeGoclassTime;
  }
}