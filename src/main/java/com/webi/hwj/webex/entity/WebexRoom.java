package com.webi.hwj.webex.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category webexRoom Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_webex_room")
public class WebexRoom implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -8742168469592979222L;
  // 主键
  private String keyId;
  // 房间host帐号
  private String webexRoomHostId;
  // 房间host密码
  private String webexRoomHostPassword;
  // 房间host邮箱
  private String webexRoomHostEmail;
  // 房间host邮箱
  private Integer webexRoomType;
  // webex的网站id(为了支持跨域)
  private String webexSiteId;
  // webex的合作者id(为了支持跨域)
  private String webexPartnerId;
  // webex的访问url
  private String webexRequestUrl;
  // 过滤时间标识
  private Integer roomTimeFilter;
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

  public String getWebexRoomHostId() {
    return webexRoomHostId;
  }

  public void setWebexRoomHostId(String webexRoomHostId) {
    this.webexRoomHostId = webexRoomHostId;
  }

  public String getWebexRoomHostPassword() {
    return webexRoomHostPassword;
  }

  public void setWebexRoomHostPassword(String webexRoomHostPassword) {
    this.webexRoomHostPassword = webexRoomHostPassword;
  }

  public String getWebexRoomHostEmail() {
    return webexRoomHostEmail;
  }

  public void setWebexRoomHostEmail(String webexRoomHostEmail) {
    this.webexRoomHostEmail = webexRoomHostEmail;
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

  public Integer getWebexRoomType() {
    return webexRoomType;
  }

  public void setWebexRoomType(Integer webexRoomType) {
    this.webexRoomType = webexRoomType;
  }

  public String getWebexSiteId() {
    return webexSiteId;
  }

  public void setWebexSiteId(String webexSiteId) {
    this.webexSiteId = webexSiteId;
  }

  public String getWebexPartnerId() {
    return webexPartnerId;
  }

  public void setWebexPartnerId(String webexPartnerId) {
    this.webexPartnerId = webexPartnerId;
  }

  public String getWebexRequestUrl() {
    return webexRequestUrl;
  }

  public void setWebexRequestUrl(String webexRequestUrl) {
    this.webexRequestUrl = webexRequestUrl;
  }

  public Integer getRoomTimeFilter() {
    return roomTimeFilter;
  }

  public void setRoomTimeFilter(Integer roomTimeFilter) {
    this.roomTimeFilter = roomTimeFilter;
  }

}