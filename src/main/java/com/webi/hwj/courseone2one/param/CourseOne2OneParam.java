package com.webi.hwj.courseone2one.param;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * Title: 1v1课程列表<br>
 * Description: CourseOne2OneParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年9月6日 下午3:19:34
 * 
 * @author komi.zsy
 */
public class CourseOne2OneParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -6886679048738873476L;
  // 主键id
  private String keyId;
  // 体系类别
  private String categoryType;
  // 课程标题
  private String courseTitle;
  // 课程标题(用于和tmm进度比对)
  private String courseRsaTitle;
  // 课程图片
  private String coursePic;
  // 课程排序
  private Integer courseOrder;
  // ppt课件
  private String courseCourseware;
  // 微立方课件id
  private String documentId;
  // 课程级别
  private String courseLevel;
  // 课程类型
  private String courseType;
  // 课程类型中文名字
  private String courseTypeChineseName;
  // 上课前多少分钟可以进入教室
  private Integer courseTypeBeforeGoclassTime;
  // 提前多少分钟可以取消预约
  private Integer courseTypeCancelSubscribeTime;
  // Rsa课程进度
  private Integer rsaRate;

  // 预约id
  private String subscribeId;
  // 上课开始时间
  private Date subscribeStartTime;
  // 上课结束时间
  private Date subscribeEndTime;
  // 课程预约状态
  private Integer courseStatus;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getCoursePic() {
    return coursePic;
  }

  public void setCoursePic(String coursePic) {
    this.coursePic = coursePic;
  }

  public String getCourseCourseware() {
    return courseCourseware;
  }

  public void setCourseCourseware(String courseCourseware) {
    this.courseCourseware = courseCourseware;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getCourseLevel() {
    return courseLevel;
  }

  public void setCourseLevel(String courseLevel) {
    this.courseLevel = courseLevel;
  }

  public String getCourseTypeChineseName() {
    return courseTypeChineseName;
  }

  public void setCourseTypeChineseName(String courseTypeChineseName) {
    this.courseTypeChineseName = courseTypeChineseName;
  }

  public Integer getCourseTypeBeforeGoclassTime() {
    return courseTypeBeforeGoclassTime;
  }

  public void setCourseTypeBeforeGoclassTime(Integer courseTypeBeforeGoclassTime) {
    this.courseTypeBeforeGoclassTime = courseTypeBeforeGoclassTime;
  }

  public Integer getCourseTypeCancelSubscribeTime() {
    return courseTypeCancelSubscribeTime;
  }

  public void setCourseTypeCancelSubscribeTime(Integer courseTypeCancelSubscribeTime) {
    this.courseTypeCancelSubscribeTime = courseTypeCancelSubscribeTime;
  }

  public Integer getRsaRate() {
    return rsaRate;
  }

  public void setRsaRate(Integer rsaRate) {
    this.rsaRate = rsaRate;
  }

  public String getSubscribeId() {
    return subscribeId;
  }

  public void setSubscribeId(String subscribeId) {
    this.subscribeId = subscribeId;
  }

  public Date getSubscribeStartTime() {
    return subscribeStartTime;
  }

  public void setSubscribeStartTime(Date subscribeStartTime) {
    this.subscribeStartTime = subscribeStartTime;
  }

  public Date getSubscribeEndTime() {
    return subscribeEndTime;
  }

  public void setSubscribeEndTime(Date subscribeEndTime) {
    this.subscribeEndTime = subscribeEndTime;
  }

  public Integer getCourseStatus() {
    return courseStatus;
  }

  public void setCourseStatus(Integer courseStatus) {
    this.courseStatus = courseStatus;
  }

  public Integer getCourseOrder() {
    return courseOrder;
  }

  public void setCourseOrder(Integer courseOrder) {
    this.courseOrder = courseOrder;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getCourseRsaTitle() {
    return courseRsaTitle;
  }

  public void setCourseRsaTitle(String courseRsaTitle) {
    this.courseRsaTitle = courseRsaTitle;
  }

}