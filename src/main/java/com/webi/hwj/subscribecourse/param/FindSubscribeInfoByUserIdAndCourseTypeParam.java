package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;

import io.swagger.annotations.ApiModelProperty;

public class FindSubscribeInfoByUserIdAndCourseTypeParam implements Serializable {
  private static final long serialVersionUID = 7868147032521850163L;
  // 提前上课时间(分钟)
  @ApiModelProperty(value = "提前上课时间(分钟)", required = true, example = "15")
  private Integer courseTypeBeforeGoclassTime;
  // 预约列表
  @ApiModelProperty(value = "预约列表", required = true)
  private List<SubscribeCourse> teacherTimeIds;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public Integer getCourseTypeBeforeGoclassTime() {
    return courseTypeBeforeGoclassTime;
  }

  public void setCourseTypeBeforeGoclassTime(Integer courseTypeBeforeGoclassTime) {
    this.courseTypeBeforeGoclassTime = courseTypeBeforeGoclassTime;
  }

  public List<SubscribeCourse> getTeacherTimeIds() {
    return teacherTimeIds;
  }

  public void setTeacherTimeIds(List<SubscribeCourse> teacherTimeIds) {
    this.teacherTimeIds = teacherTimeIds;
  }
}
