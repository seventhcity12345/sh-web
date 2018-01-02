package com.webi.hwj.ordercourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 合同学习进度<br> 
 * Description: 合同学习进度<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2016年9月21日 下午2:59:00 
 * @author komi.zsy
 */
public class ContractLearningProgressParam implements Serializable {
  // 主键id
  private String keyId;
  // 用户id
  private String userId;
  // 剩余课程数量(每次上完课之后会减1，初始值与remain_course_count一致)
  private Integer remainCourseCount;
  // 体系类别
  private String categoryType;
  // 体系类别(中文名)
  private String categoryTypeChineseName;
  // 合同开始时间
  private Date startOrderTime;
  // 合同结束时间
  private Date endOrderTime;
  //期望进度
  private Integer expectCourseProgress;
  //实际进度
  private Integer courseProgress;
  
  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public Integer getRemainCourseCount() {
    return remainCourseCount;
  }

  public void setRemainCourseCount(Integer remainCourseCount) {
    this.remainCourseCount = remainCourseCount;
  }

  public String getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }

  public String getCategoryTypeChineseName() {
    return categoryTypeChineseName;
  }

  public void setCategoryTypeChineseName(String categoryTypeChineseName) {
    this.categoryTypeChineseName = categoryTypeChineseName;
  }

  public Date getStartOrderTime() {
    return startOrderTime;
  }

  public void setStartOrderTime(Date startOrderTime) {
    this.startOrderTime = startOrderTime;
  }

  public Date getEndOrderTime() {
    return endOrderTime;
  }

  public void setEndOrderTime(Date endOrderTime) {
    this.endOrderTime = endOrderTime;
  }

  public Integer getExpectCourseProgress() {
    return expectCourseProgress;
  }

  public void setExpectCourseProgress(Integer expectCourseProgress) {
    this.expectCourseProgress = expectCourseProgress;
  }

  public Integer getCourseProgress() {
    return courseProgress;
  }

  public void setCourseProgress(Integer courseProgress) {
    this.courseProgress = courseProgress;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
  
}