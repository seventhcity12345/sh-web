package com.webi.hwj.statistics.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 最近7天每天学员平均学习课件时长(list)<br>
 * Description: 最近7天每天学员平均学习课件时长(list)<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月10日 上午9:56:00
 * 
 * @author felix.yl
 */
@ApiModel(value = "CoursewareLearningTimeApiParam(成人-口语训练营-课件学习时长模型(list对象)))")
public class CoursewareLearningTimeApiParam implements Serializable {

  private static final long serialVersionUID = -1303755965012264999L;

  @ApiModelProperty(value = "所有人平均学习时长(list对象)")
  private List<CoursewareLearningTimeParam> coursewareLearningTimeAvgList;

  @ApiModelProperty(value = "当前登录学员平均学习时长(list对象)")
  private List<CoursewareLearningTimeParam> coursewareLearningTimeMyList;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<CoursewareLearningTimeParam> getCoursewareLearningTimeAvgList() {
    return coursewareLearningTimeAvgList;
  }

  public void setCoursewareLearningTimeAvgList(
      List<CoursewareLearningTimeParam> coursewareLearningTimeAvgList) {
    this.coursewareLearningTimeAvgList = coursewareLearningTimeAvgList;
  }

  public List<CoursewareLearningTimeParam> getCoursewareLearningTimeMyList() {
    return coursewareLearningTimeMyList;
  }

  public void setCoursewareLearningTimeMyList(
      List<CoursewareLearningTimeParam> coursewareLearningTimeMyList) {
    this.coursewareLearningTimeMyList = coursewareLearningTimeMyList;
  }

}
