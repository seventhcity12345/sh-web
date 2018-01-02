package com.webi.hwj.user.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.annotation.TableName;

/**
 * @category 根据给的学员id 查找followup信息
 * @author seven
 */
@TableName("t_user_followup")
public class FindUserFollowupParam implements Serializable {
  private static final long serialVersionUID = 5188095333707879414L;
  // 学生id
  private String userId;
  // 创建人
  private Date createDate;
  // userIds
  private List<String> userIds;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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