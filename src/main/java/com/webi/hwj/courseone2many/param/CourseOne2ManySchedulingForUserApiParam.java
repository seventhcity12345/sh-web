package com.webi.hwj.courseone2many.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 排课参数实体，增加预约id<br>
 * Description: CourseOne2ManySchedulingParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月11日 下午3:20:51
 * 
 * @author komi.zsy
 */
@ApiModel(value = "CourseOne2ManySchedulingForUserApiParam(成人ES课程以及教师(最终返回给前端的data)信息模型)")
public class CourseOne2ManySchedulingForUserApiParam implements Serializable {
  private static final long serialVersionUID = 4934895840513613580L;

  @ApiModelProperty(value = "课程以及教师信息list")
  private List<CourseOne2ManySchedulingParam> esInfoList;// 课程以及教师相关信息

  @ApiModelProperty(value = "提前上课时间")
  private Integer beforeGoclassTime;// 提前上课时间

  @ApiModelProperty(value = "提前取消时间")
  private Integer cancelSubscribeTime;// 提前取消时间

  @ApiModelProperty(value = "提前预约时间")
  private Integer subscribeTime;// 提前预约时间

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<CourseOne2ManySchedulingParam> getEsInfoList() {
    return esInfoList;
  }

  public void setEsInfoList(List<CourseOne2ManySchedulingParam> esInfoList) {
    this.esInfoList = esInfoList;
  }

  public Integer getBeforeGoclassTime() {
    return beforeGoclassTime;
  }

  public void setBeforeGoclassTime(Integer beforeGoclassTime) {
    this.beforeGoclassTime = beforeGoclassTime;
  }

  public Integer getCancelSubscribeTime() {
    return cancelSubscribeTime;
  }

  public void setCancelSubscribeTime(Integer cancelSubscribeTime) {
    this.cancelSubscribeTime = cancelSubscribeTime;
  }

  public Integer getSubscribeTime() {
    return subscribeTime;
  }

  public void setSubscribeTime(Integer subscribeTime) {
    this.subscribeTime = subscribeTime;
  }

}
