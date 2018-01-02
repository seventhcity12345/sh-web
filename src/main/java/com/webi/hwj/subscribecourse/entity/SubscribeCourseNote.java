package com.webi.hwj.subscribecourse.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category subscribeCourseNote Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_subscribe_course_note")
public class SubscribeCourseNote implements Serializable {
  private static final long serialVersionUID = -1214361416102191753L;
  // 主键 为t_subscribe_course的key_id
  @NotNull(message = "主键不能为空")
  private String keyId;
  // 记录
  private String subscribeNote;
  // 记录类型
  private Integer subscribeNoteType;
  // 记录人名称
  private String subscribeNoteTaker;
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

  public String getSubscribeNote() {
    return subscribeNote;
  }

  public void setSubscribeNote(String subscribeNote) {
    this.subscribeNote = subscribeNote;
  }

  public Integer getSubscribeNoteType() {
    return subscribeNoteType;
  }

  public void setSubscribeNoteType(Integer subscribeNoteType) {
    this.subscribeNoteType = subscribeNoteType;
  }

  public String getSubscribeNoteTaker() {
    return subscribeNoteTaker;
  }

  public void setSubscribeNoteTaker(String subscribeNoteTaker) {
    this.subscribeNoteTaker = subscribeNoteTaker;
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