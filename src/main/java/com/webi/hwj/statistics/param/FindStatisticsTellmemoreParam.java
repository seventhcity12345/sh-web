package com.webi.hwj.statistics.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FindStatisticsTellmemoreParam implements Serializable {
  private static final long serialVersionUID = 436532340746746635L;
  // 用户id
  private String userId;
  // 开始时间
  private Date startTime;
  // 结束时间
  private Date endTime;
  // 每个用户的总学习时间
  private String totalTmmWorkingtime;
  // 每个用户的总学习时间
  private String changeTmmWorkingtime;
  // 创建时间
  private Date createDate;
  // 学员ids
  private List<String> userIds;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public String getTotalTmmWorkingtime() {
    return totalTmmWorkingtime;
  }

  public void setTotalTmmWorkingtime(String totalTmmWorkingtime) {
    this.totalTmmWorkingtime = totalTmmWorkingtime;
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

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

}
