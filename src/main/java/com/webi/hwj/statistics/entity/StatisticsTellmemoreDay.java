package com.webi.hwj.statistics.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * @category statisticsTellmemoreDay Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_statistics_tellmemore_day")
public class StatisticsTellmemoreDay implements Serializable {
  // 主键id
  private String keyId;
  // 用户的id
  private String userId;
  // 每个用户的总学习进度
  private Integer totalTmmPercent;
  // 每个用户的总正确率
  private Integer totalTmmCorrect;
  // 每个用户的总学习时间
  private String totalTmmWorkingtime;
  // 每个用户的学习进度差值
  private Integer changeTmmPercent;
  // 每个用户的正确率差值
  private Integer changeTmmCorrect;
  // 每个用户的学习时间差值
  private String changeTmmWorkingtime;
  // 创建日期
  private Date createDate;
  // 更新日期
  private Date updateDate;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getTotalTmmPercent() {
    return totalTmmPercent;
  }

  public void setTotalTmmPercent(Integer totalTmmPercent) {
    this.totalTmmPercent = totalTmmPercent;
  }

  public Integer getTotalTmmCorrect() {
    return totalTmmCorrect;
  }

  public void setTotalTmmCorrect(Integer totalTmmCorrect) {
    this.totalTmmCorrect = totalTmmCorrect;
  }

  public String getTotalTmmWorkingtime() {
    return totalTmmWorkingtime;
  }

  public void setTotalTmmWorkingtime(String totalTmmWorkingtime) {
    this.totalTmmWorkingtime = totalTmmWorkingtime;
  }

  public Integer getChangeTmmPercent() {
    return changeTmmPercent;
  }

  public void setChangeTmmPercent(Integer changeTmmPercent) {
    this.changeTmmPercent = changeTmmPercent;
  }

  public Integer getChangeTmmCorrect() {
    return changeTmmCorrect;
  }

  public void setChangeTmmCorrect(Integer changeTmmCorrect) {
    this.changeTmmCorrect = changeTmmCorrect;
  }

  public String getChangeTmmWorkingtime() {
    return changeTmmWorkingtime;
  }

  public void setChangeTmmWorkingtime(String changeTmmWorkingtime) {
    this.changeTmmWorkingtime = changeTmmWorkingtime;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Boolean getIsUsed() {
    return isUsed;
  }

  public void setIsUsed(Boolean isUsed) {
    this.isUsed = isUsed;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

}