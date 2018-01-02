package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * 
 * Title: 已预约且出席的课程节数<br>
 * Description: SubscribeCourseCountParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月8日 下午8:14:29
 * 
 * @author felix.yl
 */
public class SubscribeCourseCountParam implements Serializable {

  private static final long serialVersionUID = 2026043442633830378L;

  // 1v1课程上课总数
  private Integer oneToOneCountCourse;

  // 1vn课程上课总数
  private Integer oneToManyCountCourse;

  // 当天开始时间
  private Date currentStartTime;

  // 当天结束时间
  private Date currentEndTime;

  // 学员id
  private String userId;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public Integer getOneToOneCountCourse() {
    return oneToOneCountCourse;
  }

  public void setOneToOneCountCourse(Integer oneToOneCountCourse) {
    this.oneToOneCountCourse = oneToOneCountCourse;
  }

  public Integer getOneToManyCountCourse() {
    return oneToManyCountCourse;
  }

  public void setOneToManyCountCourse(Integer oneToManyCountCourse) {
    this.oneToManyCountCourse = oneToManyCountCourse;
  }

  public Date getCurrentStartTime() {
    return currentStartTime;
  }

  public void setCurrentStartTime(Date currentStartTime) {
    this.currentStartTime = currentStartTime;
  }

  public Date getCurrentEndTime() {
    return currentEndTime;
  }

  public void setCurrentEndTime(Date currentEndTime) {
    this.currentEndTime = currentEndTime;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}