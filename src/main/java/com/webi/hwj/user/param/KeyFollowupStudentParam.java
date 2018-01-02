package com.webi.hwj.user.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * 
 * Title: 查询重点跟踪学员类<br>
 * Description: 查询重点跟踪学员类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月5日 下午5:55:23
 * 
 * @author seven.gz
 */
public class KeyFollowupStudentParam implements Serializable {
  private static final long serialVersionUID = -5413921260894924768L;
  //
  private String userId;
  // 学员编号
  private String userCode;
  // 学员姓名
  private String realName;
  // 英文名
  private String englishName;
  // LC 名字
  private String adminUserName;
  // 手机号
  private String phone;
  // 合同开始时间
  private Date startOrderTime;
  // 课程结束时间
  private Date endOrderTime;
  // 跟课次数
  private Integer followupCount;
  // 最后一次follow 时间
  private Date lastFollowTime;
  // 组合查询条件
  public String cons;
  // 教务id
  private String learningCoachId;
  // 过滤时间
  private Date filterTime;
  // 学员id
  private List<String> userIds;
  // 最后一次做课件的时间
  private Date lastCoursewareDate;
  // 最后一次上课时间
  private Date lastClassDate;
  //
  private List<String> keyIds;
  // 课件完成率
  private String tmmPercent;
  // 课程名称
  private String courseTitle;
  // 第一次课件达标时间
  private String firstCompleteTime;
  // cc名称
  private String ccName;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

  public Integer getFollowupCount() {
    return followupCount;
  }

  public void setFollowupCount(Integer followupCount) {
    this.followupCount = followupCount;
  }

  public Date getLastFollowTime() {
    return lastFollowTime;
  }

  public void setLastFollowTime(Date lastFollowTime) {
    this.lastFollowTime = lastFollowTime;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getLearningCoachId() {
    return learningCoachId;
  }

  public void setLearningCoachId(String learningCoachId) {
    this.learningCoachId = learningCoachId;
  }

  public Date getFilterTime() {
    return filterTime;
  }

  public void setFilterTime(Date filterTime) {
    this.filterTime = filterTime;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public Date getLastCoursewareDate() {
    return lastCoursewareDate;
  }

  public void setLastCoursewareDate(Date lastCoursewareDate) {
    this.lastCoursewareDate = lastCoursewareDate;
  }

  public Date getLastClassDate() {
    return lastClassDate;
  }

  public void setLastClassDate(Date lastClassDate) {
    this.lastClassDate = lastClassDate;
  }

  public List<String> getKeyIds() {
    return keyIds;
  }

  public void setKeyIds(List<String> keyIds) {
    this.keyIds = keyIds;
  }

  public String getTmmPercent() {
    return tmmPercent;
  }

  public void setTmmPercent(String tmmPercent) {
    this.tmmPercent = tmmPercent;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public String getFirstCompleteTime() {
    return firstCompleteTime;
  }

  public void setFirstCompleteTime(String firstCompleteTime) {
    this.firstCompleteTime = firstCompleteTime;
  }

  public String getCcName() {
    return ccName;
  }

  public void setCcName(String ccName) {
    this.ccName = ccName;
  }

}
