package com.webi.hwj.notice.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 公告信息组合查询参数bean<br>
 * Description: NoticeParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月28日 下午5:05:08
 * 
 * @author komi.zsy
 */
public class NoticeParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 2167622895579597881L;
  // 主键
  @ApiModelProperty(value = "公告id", required = true, example = "316c6718b67747b59244c3edb3bb6a20")
  private String keyId;
  // 标题
  @ApiModelProperty(value = "noticeTitle", required = true,
      example = "今天上课啦")
  private String noticeTitle;
  // 类型
  private Integer noticeType;
  // 内容
  @ApiModelProperty(value = "内容", required = true,
      example = "今天上天气很高啊啊啊啊啊啊啊啊")
  private String noticeContent;
  // 创建人名字
  @ApiModelProperty(value = "创建人名字", required = true,
      example = "小明")
  private String adminUserName;
  // 生效开始时间
  @ApiModelProperty(value = "生效开始时间", required = true,
      example = "1469721600000")
  private Date noticeStartTime;
  // 生效结束时间
  @ApiModelProperty(value = "生效结束时间", required = true,
      example = "1471276800000")
  private Date noticeEndTime;
  // 创建时间
  @ApiModelProperty(value = "创建时间", required = true,
      example = "1469721600000")
  private Date createDate;
  // 修改时间
  private Date updateDate;
  // 创建人id
  private String createUserId;
  // 最后更新人id
  private String updateUserId;
  // 是否使用(1:使用,0:不使用)
  private Boolean isUsed;
  // 组合查询条件
  public String cons;
  // 查询多少条数据
  private Integer num;

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

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Integer getNoticeType() {
    return noticeType;
  }

  public void setNoticeType(Integer noticeType) {
    this.noticeType = noticeType;
  }
}