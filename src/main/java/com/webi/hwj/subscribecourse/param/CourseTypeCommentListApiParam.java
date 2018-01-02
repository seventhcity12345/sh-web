package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "CourseTypeCommentListApiParam(成人-已完成课程-指定课程类型的评价信息列表(List对象：传给前端))")
public class CourseTypeCommentListApiParam implements Serializable {

  private static final long serialVersionUID = -6333109845466168304L;

  List<CourseTypeCommentListParam> courseTypeCommentListParamList;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<CourseTypeCommentListParam> getCourseTypeCommentListParamList() {
    return courseTypeCommentListParamList;
  }

  public void setCourseTypeCommentListParamList(
      List<CourseTypeCommentListParam> courseTypeCommentListParamList) {
    this.courseTypeCommentListParamList = courseTypeCommentListParamList;
  }

}
