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
public class FindTeacherTimeAndTeacherParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = 4459931407049870432L;
  // 主键id
  private String keyId;
  // 老师来源
  private String thirdFrom;
  // 老师姓名
  private String teacherName;
  // 老师id(逻辑外键)
  private String teacherId;
  // 上课开始时间
  private Date startTime;
  // 上课结束时间
  private Date endTime;
  // 上课的类型
  private String courseType;
  // 老师是否确认
  private Boolean isConfirm;
  // 课程标题
  private String courseTitle;
  // 预约人数
  private int alreadyPersonCount;
  // 用户等级（1v1课程等级）
  private String userLevel;
  // 课程等级（大课等级，如果这个为空，则用用户等级）
  private String courseLevel;
  // 用于组合查询条件
  public String cons;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
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

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public Boolean getIsConfirm() {
    return isConfirm;
  }

  public void setIsConfirm(Boolean isConfirm) {
    this.isConfirm = isConfirm;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public int getAlreadyPersonCount() {
    return alreadyPersonCount;
  }

  public void setAlreadyPersonCount(int alreadyPersonCount) {
    this.alreadyPersonCount = alreadyPersonCount;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
  }

  public String getCourseLevel() {
    return courseLevel;
  }

  public void setCourseLevel(String courseLevel) {
    this.courseLevel = courseLevel;
  }


}