package com.webi.hwj.user.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * 
 * Title: 团训学员报表定时发送<br>
 * Description: 团训学员报表定时发送<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年11月15日 下午2:04:37
 * 
 * @author seven.gz
 */
public class FindUserOrderInfoParam implements Serializable {
  private static final long serialVersionUID = 1255199629671993155L;
  // 主键id
  private String keyId;
  //学员id
  private String userId;
  // 初始级别
  private String initLevel;
  // 当前级别
  private String currentLevel;
  // 放置的为user_info里的英文名称
  private String userName;
  // 合同开始时间
  private Date startOrderTime;
  // 合同截止时间
  private Date endOrderTime;
  // 1v1 消耗课程数
  private int consumeCourseType1Count;
  // 1vN 消耗课程数
  private int consumeCourseType2Count;
  //1v1 消耗课程数
  private int consumeCourseType8Count;
  // rsa 学习时长
  private String totalTmmWorkingTime;
  // 学员来源
  private Integer userFromType;
  // 学员手机号
  private String phone;
  // 创建日期
  private Date createDate;
  //对学生的评论
  private StringBuffer commentContent; 
  //学员code
  private String userCode;
  
  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public StringBuffer getCommentContent() {
    return commentContent;
  }
  

  public void setCommentContent(StringBuffer commentContent) {
    this.commentContent = commentContent;
  }

  public void appendCommentContent(String commentContent) {
    if (this.commentContent==null) {
      this.commentContent=new StringBuffer();
    }
      this.commentContent.append(commentContent);
  }

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

  public String getInitLevel() {
    return initLevel;
  }

  public void setInitLevel(String initLevel) {
    this.initLevel = initLevel;
  }

  public String getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(String currentLevel) {
    this.currentLevel = currentLevel;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getConsumeCourseType1Count() {
    return consumeCourseType1Count;
  }

  public void setConsumeCourseType1Count(int consumeCourseType1Count) {
    this.consumeCourseType1Count = consumeCourseType1Count;
  }

  public int getConsumeCourseType2Count() {
    return consumeCourseType2Count;
  }

  public void setConsumeCourseType2Count(int consumeCourseType2Count) {
    this.consumeCourseType2Count = consumeCourseType2Count;
  }

  public int getConsumeCourseType8Count() {
    return consumeCourseType8Count;
  }

  public void setConsumeCourseType8Count(int consumeCourseType8Count) {
    this.consumeCourseType8Count = consumeCourseType8Count;
  }

  public String getTotalTmmWorkingTime() {
    return totalTmmWorkingTime;
  }

  public void setTotalTmmWorkingTime(String totalTmmWorkingTime) {
    this.totalTmmWorkingTime = totalTmmWorkingTime;
  }

  public Integer getUserFromType() {
    return userFromType;
  }

  public void setUserFromType(Integer userFromType) {
    this.userFromType = userFromType;
  }

  public Date getStartOrderTime() {
    return startOrderTime;
  }

  public void setStartOrderTime(Date startOrderTime) {
    this.startOrderTime = startOrderTime;
  }

  public Date getEndOrderTime() {
    return endOrderTime;
  }

  public void setEndOrderTime(Date endOrderTime) {
    this.endOrderTime = endOrderTime;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

}