package com.webi.hwj.user.param;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Title: 用户统计信息参数类<br>
 * Description: 用户统计信息参数类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月31日 上午10:58:46
 * 
 * @author seven.gz
 */
public class FindUserCourseStatisticsParam implements Serializable {
  private static final long serialVersionUID = 7949112589383220145L;
  // 主键id
  private String userId;
  // 手机号
  private String phone;
  // 英文名
  private String englishName;
  // 真实姓名
  private String realName;
  // 用户编码
  private int userCode;
  // 教务id
  private String learningCoachId;
  // 教务名称
  private String adminUserName;

  // 1v1总课数
  private int one2OneTotalCount;
  // 1v1标准上课数
  private int one2OneStandardCount;
  // 1v1上课数
  private int one2OneShowCount;
  // 1vn总课数
  private int one2ManyTotalCount;
  // 1vn标准上课数
  private int one2ManyStandardCount;
  // 1vn上课数
  private int one2ManyShowCount;
  // isActivity
  private boolean activity;
  // 用于组合查询
  public String cons;
  // 通过级别区分通用还是商务英语
  private String currentLevel;
  // 合同开始时间
  private Date startOrderTime;
  // 合同结束时间
  private Date endOrderTime;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

  public int getUserCode() {
    return userCode;
  }

  public void setUserCode(int userCode) {
    this.userCode = userCode;
  }

  public int getOne2OneStandardCount() {
    return one2OneStandardCount;
  }

  public void setOne2OneStandardCount(int one2OneStandardCount) {
    this.one2OneStandardCount = one2OneStandardCount;
  }

  public int getOne2OneShowCount() {
    return one2OneShowCount;
  }

  public void setOne2OneShowCount(int one2OneShowCount) {
    this.one2OneShowCount = one2OneShowCount;
  }

  public int getOne2ManyStandardCount() {
    return one2ManyStandardCount;
  }

  public void setOne2ManyStandardCount(int one2ManyStandardCount) {
    this.one2ManyStandardCount = one2ManyStandardCount;
  }

  public int getOne2ManyShowCount() {
    return one2ManyShowCount;
  }

  public void setOne2ManyShowCount(int one2ManyShowCount) {
    this.one2ManyShowCount = one2ManyShowCount;
  }

  public boolean isActivity() {
    return activity;
  }

  public void setActivity(boolean activity) {
    this.activity = activity;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(String currentLevel) {
    this.currentLevel = currentLevel;
  }

  public String getLearningCoachId() {
    return learningCoachId;
  }

  public void setLearningCoachId(String learningCoachId) {
    this.learningCoachId = learningCoachId;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public int getOne2OneTotalCount() {
    return one2OneTotalCount;
  }

  public void setOne2OneTotalCount(int one2OneTotalCount) {
    this.one2OneTotalCount = one2OneTotalCount;
  }

  public int getOne2ManyTotalCount() {
    return one2ManyTotalCount;
  }

  public void setOne2ManyTotalCount(int one2ManyTotalCount) {
    this.one2ManyTotalCount = one2ManyTotalCount;
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
}
