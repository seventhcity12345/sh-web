package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value = "CourseSubscribleParam(成人-我的课表-奖杯/闹钟图标展示-信息模型)")
public class SubscribleCourseParam implements Serializable {

  private static final long serialVersionUID = 3009922707488077585L;

  @ApiModelProperty(value = "用户的userId", example = "72dfe41853e64fef95dcf742de71cfd1")
  private String userId;

  @ApiModelProperty(value = "本月第一天")
  private Date startTime;

  @ApiModelProperty(value = "本月最后一天")
  private Date endTime;

  @ApiModelProperty(value = "已预约课程那天的日期(Date型)")
  private Date subscribleDate;

  @ApiModelProperty(value = "预约课程数", example = "2")
  private Integer count;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Date getSubscribleDate() {
    return subscribleDate;
  }

  public void setSubscribleDate(Date subscribleDate) {
    this.subscribleDate = subscribleDate;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

}
