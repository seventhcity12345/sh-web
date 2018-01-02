package com.webi.hwj.statistics.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 最近7天每天学员平均学习课件时长<br>
 * Description: 最近7天每天学员平均学习课件时长<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月10日 上午9:56:00
 * 
 * @author felix.yl
 */
@ApiModel(value = "CoursewareLearningTimeParam(成人-口语训练营-课件学习时长模型)")
public class CoursewareLearningTimeParam implements Serializable {

  private static final long serialVersionUID = 4450382002187597540L;

  @ApiModelProperty(value = "日期", example = "2017-08-10")
  private Date learningDate;// 日期

  @ApiModelProperty(value = "学习时长", example = "35")
  private Integer learningTime;// 学习时长

  @ApiModelProperty(value = "开始日期(查询用,前端不用理睬)", example = "2017-08-02")
  private Date startDate;// 开始日期

  @ApiModelProperty(value = "结束日期(查询用,前端不用理睬)", example = "2017-08-09")
  private Date endDate;// 开始日期

  @ApiModelProperty(value = "学员id(查询用,前端不用理睬)", example = "72dfe41853e64fef95dcf742de71cfd1")
  private String userId;// 开始日期

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public Date getLearningDate() {
    return learningDate;
  }

  public void setLearningDate(Date learningDate) {
    this.learningDate = learningDate;
  }

  public Integer getLearningTime() {
    return learningTime;
  }

  public void setLearningTime(Integer learningTime) {
    this.learningTime = learningTime;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
