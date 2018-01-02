package com.webi.hwj.statistics.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category tellmemorePercent Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_tellmemore_percent")
public class TellmemorePercent implements Serializable {
  private static final long serialVersionUID = -8018041359098726663L;
  // 主键id
  private String keyId;
  // 1v1课程id
  private String courseId;
  // 用户id
  private String userId;
  // 课程标题
  private String courseTitle;
  // 课程级别
  private String courseLevel;
  // tmm课件进度百分比
  private Integer tmmPercent;
  // tmm课件正确率百分比
  private Integer tmmCorrect;
  // tmm课件学习时间
  private String tmmWorkingtime;
  // 第一次课件达标时间（可以预约1v1课程）
  private Date firstCompleteTime;
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

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getCourseLevel() {
    return courseLevel;
  }

  public void setCourseLevel(String courseLevel) {
    this.courseLevel = courseLevel;
  }

  public Integer getTmmPercent() {
    return tmmPercent;
  }

  public void setTmmPercent(Integer tmmPercent) {
    this.tmmPercent = tmmPercent;
  }

  public Integer getTmmCorrect() {
    return tmmCorrect;
  }

  public void setTmmCorrect(Integer tmmCorrect) {
    this.tmmCorrect = tmmCorrect;
  }

  public String getTmmWorkingtime() {
    return tmmWorkingtime;
  }

  public void setTmmWorkingtime(String tmmWorkingtime) {
    this.tmmWorkingtime = tmmWorkingtime;
  }

  public Date getFirstCompleteTime() {
    return firstCompleteTime;
  }

  public void setFirstCompleteTime(Date firstCompleteTime) {
    this.firstCompleteTime = firstCompleteTime;
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

}