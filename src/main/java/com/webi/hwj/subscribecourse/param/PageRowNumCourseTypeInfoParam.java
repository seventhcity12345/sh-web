package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PageRowNumCourseTypeInfoParam(成人-已完成课程-页码行号信息)")
public class PageRowNumCourseTypeInfoParam implements Serializable {

  private static final long serialVersionUID = -7790989411568266219L;

  @ApiModelProperty(value = "页码(第几页)", example = "2")
  private Integer pageNum;// 页码

  @ApiModelProperty(value = "行号(第几行)", example = "3")
  private Integer rowNum;// 页码

  @ApiModelProperty(value = "课程类型", example = "course_type1")
  private String courseType;// 课程类型

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public Integer getPageNum() {
    return pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public Integer getRowNum() {
    return rowNum;
  }

  public void setRowNum(Integer rowNum) {
    this.rowNum = rowNum;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

}
