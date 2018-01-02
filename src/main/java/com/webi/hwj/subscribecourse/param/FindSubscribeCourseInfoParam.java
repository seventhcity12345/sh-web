package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

public class FindSubscribeCourseInfoParam implements Serializable {
  private static final long serialVersionUID = 4348751696381909182L;
  //
  private String keyId;
  // 老师时间id
  private String teacherTimeId;
  // 课程标题
  private String courseTitle;
  // 用户等级
  private String userLevel;
  // 要查询的学员id
  private List<String> teacherTimeIds;

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public List<String> getTeacherTimeIds() {
    return teacherTimeIds;
  }

  public void setTeacherTimeIds(List<String> teacherTimeIds) {
    this.teacherTimeIds = teacherTimeIds;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(String userLevel) {
    this.userLevel = userLevel;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
