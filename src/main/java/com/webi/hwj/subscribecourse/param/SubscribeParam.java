package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Title: 预约时的传递参数使用.<br>
 * Description: SubscribeParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年9月23日 上午10:15:31
 * 
 * @author yangmh
 */
public class SubscribeParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 3058719937264151694L;

  private String weixinOpenId;

  @NotNull(message = "老师时间不能为空")
  private String teacherTimeId;

  // 课程id（小课存的是1v1课程表的主键,大课存的是t_teacher_time_scheduling表的主键）
  @NotNull(message = "课程ID不能为空")
  private String courseId;

  @NotNull(message = "课程类型不能为空")
  private String courseType;

  // 用户id
  private String userId;

  // modify by seven 2017年4月11日18:30:56 demo课增加属性
  // 会议号
  private String webexMeetingKey;

  // 房间号
  private String webexRoomHostId;

  // 预约备注
  private String subscribeRemark;

  private Integer subscribeType;

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getWeixinOpenId() {
    return weixinOpenId;
  }

  public void setWeixinOpenId(String weixinOpenId) {
    this.weixinOpenId = weixinOpenId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getWebexMeetingKey() {
    return webexMeetingKey;
  }

  public void setWebexMeetingKey(String webexMeetingKey) {
    this.webexMeetingKey = webexMeetingKey;
  }

  public String getWebexRoomHostId() {
    return webexRoomHostId;
  }

  public void setWebexRoomHostId(String webexRoomHostId) {
    this.webexRoomHostId = webexRoomHostId;
  }

  public String getSubscribeRemark() {
    return subscribeRemark;
  }

  public void setSubscribeRemark(String subscribeRemark) {
    this.subscribeRemark = subscribeRemark;
  }

  public Integer getSubscribeType() {
    return subscribeType;
  }

  public void setSubscribeType(Integer subscribeType) {
    this.subscribeType = subscribeType;
  }

}