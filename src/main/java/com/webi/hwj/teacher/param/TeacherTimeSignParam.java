package com.webi.hwj.teacher.param;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * Title: TeacherTimeSignParam<br>
 * Description: 一些业务逻辑的通用属性<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年8月2日 上午10:49:12
 * 
 * @author yangmh
 */
@TableName("t_teacher_time_sign")
public class TeacherTimeSignParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 3062452386424866171L;
  // 主键id
  private String keyId;
  // 老师id(逻辑外键)
  private String teacherId;
  // 老师名称
  private String teacherName;
  // 允许上课开始时间
  private Date startTime;
  // 允许上课结束时间
  private Date endTime;
  // 老师可以上课的类型
  private String teacherCourseType;
  // 创建日期
  private Date createDate;
  // 最后更新日期
  private Date updateDate;
  // 创建人id
  private String createUserId;
  // 最后更新人id
  private String updateUserId;
  // 是否使用(1:使用,0:不使用)
  private Boolean isUsed;

  // 一些使用的属性

  // 教师账号（用于前台显示）
  private String account;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
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

  public String getTeacherCourseType() {
    return teacherCourseType;
  }

  public void setTeacherCourseType(String teacherCourseType) {
    this.teacherCourseType = teacherCourseType;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }

  public Boolean getIsUsed() {
    return isUsed;
  }

  public void setIsUsed(Boolean isUsed) {
    this.isUsed = isUsed;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

}