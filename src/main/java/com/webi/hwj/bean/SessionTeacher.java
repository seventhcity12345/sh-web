package com.webi.hwj.bean;

import java.io.Serializable;
import java.util.List;

import com.webi.hwj.coursetype.entity.CourseType;

public class SessionTeacher implements Serializable {
  // 序列化ID
  private static final long serialVersionUID = 839482893919203812L;
  
  private String token;
  
  private String keyId;

  private String account;

  private String teacherName;

  private String teacherPhoto;

  private String teacherDesc;

  // 老师第三方来源
  private String third_from;
  // 老师可以上课的类型
  private String teacher_course_type;
  //老师上课列表（用于前端老师评论多选框绘制）
  private List<CourseType> teacherCourseTypeList;

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getTeacherDesc() {
    return teacherDesc;
  }

  public void setTeacherDesc(String teacherDesc) {
    this.teacherDesc = teacherDesc;
  }

  public String getTeacherPhoto() {
    return teacherPhoto;
  }

  public void setTeacherPhoto(String teacherPhoto) {
    this.teacherPhoto = teacherPhoto;
  }

  public String getTeacherCourseType() {
    return teacher_course_type;
  }

  public void setTeacherCourseType(String teacher_course_type) {
    this.teacher_course_type = teacher_course_type;
  }

  public String getThird_from() {
    return third_from;
  }

  public void setThird_from(String third_from) {
    this.third_from = third_from;
  }

  public List<CourseType> getTeacherCourseTypeList() {
    return teacherCourseTypeList;
  }

  public void setTeacherCourseTypeList(List<CourseType> teacherCourseTypeList) {
    this.teacherCourseTypeList = teacherCourseTypeList;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
  
}
