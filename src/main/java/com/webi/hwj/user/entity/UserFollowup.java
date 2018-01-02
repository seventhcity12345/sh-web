package com.webi.hwj.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category userFollowup Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_user_followup")
public class UserFollowup implements Serializable {
  private static final long serialVersionUID = -5502507103577575764L;
  // 主键
  private String keyId;
  // 学生id
  private String userId;
  // 管理员ID
  private String learningCoachId;
  // 管理员名称
  private String learningCoachName;
  // 主题
  private String followupTitle;
  // 内容
  private String followupContent;
  // 创建时间
  private Date createDate;
  // 修改时间
  private Date updateDate;
  // 创建人
  private String createUserId;
  // 修改人
  private String updateUserId;
  // 是否启用
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

  public String getLearningCoachId() {
    return learningCoachId;
  }

  public void setLearningCoachId(String learningCoachId) {
    this.learningCoachId = learningCoachId;
  }

  public String getLearningCoachName() {
    return learningCoachName;
  }

  public void setLearningCoachName(String learningCoachName) {
    this.learningCoachName = learningCoachName;
  }

  public String getFollowupTitle() {
    return followupTitle;
  }

  public void setFollowupTitle(String followupTitle) {
    this.followupTitle = followupTitle;
  }

  public String getFollowupContent() {
    return followupContent;
  }

  public void setFollowupContent(String followupContent) {
    this.followupContent = followupContent;
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