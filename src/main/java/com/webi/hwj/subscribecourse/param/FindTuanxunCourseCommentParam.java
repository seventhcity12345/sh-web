package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * Title: FindTuanxunCourseCommentParam<br> 
 * Description: FindTuanxunCourseCommentParam<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年1月16日 下午9:18:15 
 * @author Administrator
 */
public class FindTuanxunCourseCommentParam implements Serializable {
  private static final long serialVersionUID = 2386548943850929179L;
  //学员id
  private String userId;
//教师姓名
  private String teacherName;
  //上课开始时间
  private Date startTime;
  //对学生的评论
  private String commentContent;
  //需要查询的学员ids
  private List<String> userIds;
  
  
  
  public List<String> getUserIds() {
    return userIds;
  }
  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getTeacherName() {
    return teacherName;
  }
  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }
 
  public Date getStartTime() {
    return startTime;
  }
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }
  public String getCommentContent() {
    return commentContent;
  }
  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }
  
}
