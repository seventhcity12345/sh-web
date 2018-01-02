package com.webi.hwj.teacher.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * 
 * Title: 查询老师上课表参数类<br>
 * Description: 查询老师上课表参数类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月25日 上午11:56:43
 * 
 * @author seven.gz
 */
@TableName("t_teacher_time")
public class FindTimesAndTeachersByDayParam implements Serializable {
  private static final long serialVersionUID = -3703891017449402870L;
  // 主键id
  private String keyId;
  // 老师姓名
  private String teacherName;
  // 老师id(逻辑外键)
  private String teacherId;
  // 上课开始时间
  private Date startTime;
  // 上课结束时间
  private Date endTime;
  // 老师头像
  private String teacherPhoto;
  // 用于查询哪天数数据
  private String day;
  // 老师权限
  private String teacherCourseType;
  // 第三方来源
  private String thirdFrom;
  // 老师国籍
  private String teacherNationality;
  // 老师工作性质
  private Integer teacherJobType;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
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

  public String getTeacherPhoto() {
    return teacherPhoto;
  }

  public void setTeacherPhoto(String teacherPhoto) {
    this.teacherPhoto = teacherPhoto;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getTeacherCourseType() {
    return teacherCourseType;
  }

  public void setTeacherCourseType(String teacherCourseType) {
    this.teacherCourseType = teacherCourseType;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

  public String getTeacherNationality() {
    return teacherNationality;
  }

  public void setTeacherNationality(String teacherNationality) {
    this.teacherNationality = teacherNationality;
  }

  public Integer getTeacherJobType() {
    return teacherJobType;
  }

  public void setTeacherJobType(Integer teacherJobType) {
    this.teacherJobType = teacherJobType;
  }

}