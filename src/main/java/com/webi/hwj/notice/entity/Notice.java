package com.webi.hwj.notice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category notice Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_notice")
public class Notice implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = -6762625033510431017L;
  // 主键
  private String keyId;
  // 标题
  @Length(min = 0, max = 16, message = "标题不能超过16位")
  private String noticeTitle;
  // 类型
  @NotNull(message="类型不能为空")
  private Integer noticeType;
  // 内容
  private String noticeContent;
  // 创建人名字
  private String adminUserName;
  // 生效开始时间
  @NotNull(message="生效开始时间不能为空")
  private Date noticeStartTime;
  // 生效结束时间
  @NotNull(message="生效结束时间不能为空")
  private Date noticeEndTime;
  // 创建时间
  private Date createDate;
  // 修改时间
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

  public String getNoticeTitle() {
    return noticeTitle;
  }

  public void setNoticeTitle(String noticeTitle) {
    this.noticeTitle = noticeTitle;
  }

  public String getNoticeContent() {
    return noticeContent;
  }

  public void setNoticeContent(String noticeContent) {
    this.noticeContent = noticeContent;
  }

  public Date getNoticeStartTime() {
    return noticeStartTime;
  }

  public void setNoticeStartTime(Date noticeStartTime) {
    this.noticeStartTime = noticeStartTime;
  }

  public Date getNoticeEndTime() {
    return noticeEndTime;
  }

  public void setNoticeEndTime(Date noticeEndTime) {
    this.noticeEndTime = noticeEndTime;
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

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public Integer getNoticeType() {
    return noticeType;
  }

  public void setNoticeType(Integer noticeType) {
    this.noticeType = noticeType;
  }
}