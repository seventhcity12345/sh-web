package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 成人会员首页我的课表<br>
 * Description: 成人会员首页我的课表<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年7月19日 下午1:41:40
 * 
 * @author felix.yl
 */
@ApiModel(value = "CourseTypeCommentCountApiParam(成人-已完成课程-已完成课程总节数模型(list对象))")
public class CourseTypeCommentCountApiParam implements Serializable {

  private static final long serialVersionUID = 6978550264163959596L;

  @ApiModelProperty(value = "已完成课程总节数模型(list对象)")
  private List<CourseTypeCommentCountParam> courseTypeCommentCountParamList;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<CourseTypeCommentCountParam> getCourseTypeCommentCountParamList() {
    return courseTypeCommentCountParamList;
  }

  public void setCourseTypeCommentCountParamList(
      List<CourseTypeCommentCountParam> courseTypeCommentCountParamList) {
    this.courseTypeCommentCountParamList = courseTypeCommentCountParamList;
  }

}
