
package com.webi.hwj.esapp.param;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 查找课程信息参数类<br> 
 * Description: 查找课程信息参数类<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年8月24日 下午3:45:44 
 * @author komi.zsy
 */
@ApiModel(value = "FindCourseListParam(查找课程信息入参)")
public class FindCourseListParam{
  @ApiModelProperty(value = "第几页", required = true, example = "1")
  private Integer current_page;
  @ApiModelProperty(value = "一页几行", required = true, example = "10")
  private Integer page_size;
  @ApiModelProperty(value = "课程类型", required = true, example = "5")
  private String courseType;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public Integer getCurrent_page() {
    return current_page;
  }

  public void setCurrent_page(Integer current_page) {
    this.current_page = current_page;
  }

  public Integer getPage_size() {
    return page_size;
  }

  public void setPage_size(Integer page_size) {
    this.page_size = page_size;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

}