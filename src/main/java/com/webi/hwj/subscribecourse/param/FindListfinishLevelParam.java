package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

public class FindListfinishLevelParam implements Serializable {
  private static final long serialVersionUID = 7252395775240955843L;
  // 用户id
  private String userId;
  // 结束时间
  @ApiModelProperty(value = "结束时间", example = "1546987202000")
  private Date endTime;
  // 完成级别
  @ApiModelProperty(value = "完成级别", example = "General Level 18")
  private String userLevel;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
  }

}
