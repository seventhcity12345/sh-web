package com.webi.hwj.statistics.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FindLastWorkTimeParam implements Serializable {
  private static final long serialVersionUID = -8885195377552984261L;
  // 用户id
  private String userId;
  // 最后上课件时间
  private Date lastWorkTime;
  // 学员ids
  private List<String> userIds;
  // 更新时间
  private Date updateDate;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getLastWorkTime() {
    return lastWorkTime;
  }

  public void setLastWorkTime(Date lastWorkTime) {
    this.lastWorkTime = lastWorkTime;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

}
