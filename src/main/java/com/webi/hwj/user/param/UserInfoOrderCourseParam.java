package com.webi.hwj.user.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 用于查询合同中生效的课程的信息<br>
 * Description: UserInfoOrderCourseParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月11日 下午4:40:49
 * 
 * @author komi.zsy
 */
@TableName("t_user_info")
public class UserInfoOrderCourseParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 231728888978955311L;
  // 主键id
  private String keyId;
  // 英文名
  private String englishName;
  // 真实姓名
  private String realName;
  // 0:女;1:男;2:还没选
  private Integer gender;
  // 身份证号
  private String idcard;

  // 手机号
  private String phone;
  // 当前级别
  private String currentLevel;
  // 用户编码
  private Integer userCode;
  // 用户照片
  private String userPhoto;

  // 合同开始时间
  private Date startOrderTime;
  // 合同截止时间
  private Date endOrderTime;

  // 跟码表里的课程类别走
  private String courseType;
  // 课程数
  private Integer courseCount;
  // 课程单位类型（0:节，1:月，2：天）
  private Integer courseUnitType;

  
  // 教务id
  private String learningCoachId;
  
  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(String currentLevel) {
    this.currentLevel = currentLevel;
  }

  public Integer getUserCode() {
    return userCode;
  }

  public void setUserCode(Integer userCode) {
    this.userCode = userCode;
  }

  public String getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
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

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public Integer getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(Integer courseCount) {
    this.courseCount = courseCount;
  }

  public Integer getCourseUnitType() {
    return courseUnitType;
  }

  public void setCourseUnitType(Integer courseUnitType) {
    this.courseUnitType = courseUnitType;
  }

  public String getLearningCoachId() {
    return learningCoachId;
  }

  public void setLearningCoachId(String learningCoachId) {
    this.learningCoachId = learningCoachId;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

}