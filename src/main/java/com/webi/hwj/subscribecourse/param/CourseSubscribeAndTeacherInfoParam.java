package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category courseComment Entity
 * @author mingyisoft代码生成工具
 */
public class CourseSubscribeAndTeacherInfoParam implements Serializable {

  private static final long serialVersionUID = 3298115575911413709L;

  private String subscribeCourseId;// 预约id(预约表的主键-查询参数)

  private String teacherTimeId;// 老师时间id

  private String courseCourseware;// 课件地址

  private Integer teacherTimePlatform;// 上课平台

  private String teacherName;// 老师姓名

  private String teacherPhoto;// 老师头像

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getSubscribeCourseId() {
    return subscribeCourseId;
  }

  public void setSubscribeCourseId(String subscribeCourseId) {
    this.subscribeCourseId = subscribeCourseId;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getCourseCourseware() {
    return courseCourseware;
  }

  public void setCourseCourseware(String courseCourseware) {
    this.courseCourseware = courseCourseware;
  }

  public Integer getTeacherTimePlatform() {
    return teacherTimePlatform;
  }

  public void setTeacherTimePlatform(Integer teacherTimePlatform) {
    this.teacherTimePlatform = teacherTimePlatform;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getTeacherPhoto() {
    return teacherPhoto;
  }

  public void setTeacherPhoto(String teacherPhoto) {
    this.teacherPhoto = teacherPhoto;
  }

}