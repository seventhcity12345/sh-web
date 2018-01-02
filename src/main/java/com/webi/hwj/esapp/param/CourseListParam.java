
package com.webi.hwj.esapp.param;

import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 课程列表<br> 
 * Description: CourseListParam<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年8月24日 下午4:48:57 
 * @author komi.zsy
 */
@ApiModel(value = "CourseListParam(课程类型信息)")
public class CourseListParam {
  @ApiModelProperty(value = "大课排课id", required = true, example = "0143bf569ab6455f8beec20dd728131d")
  private String id;
  @ApiModelProperty(value = "课程id", required = true, example = "0143bf569ab6455f8beec20dd728131d")
  private String video_id;
  @ApiModelProperty(value = "直播Id(这里其实是我们的学员预约id)", required = true, example = "0143bf569ab6455f8beec20dd728131d")
  private String live_id;
  @ApiModelProperty(value = "是否预约", required = true, example = "true")
  private Boolean subscribeStatus;
  @ApiModelProperty(value = "课程名称 ", required = true, example = "课程名称")
  private String name;
  @ApiModelProperty(value = "老师姓名", required = true, example = "老师姓名")
  private String teacher_name;
  @ApiModelProperty(value = "封面图", required = true, example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images%2Fuser%2Fdefault%2Fuser_default_photo_large.jpg")
  private String banner;
  @ApiModelProperty(value = "开始时间", required = true, example = "1483631400000")
  private Date start_time;
  @ApiModelProperty(value = "结束时间", required = true, example = "1483631400000")
  private Date end_time;
  @ApiModelProperty(value = "提前上课时间(分钟)", required = true, example = "5")
  private Integer courseTypeBeforeGoclassTime;
  @ApiModelProperty(value = "服务器时间", required = true, example = "1483631400000")
  private Date serverTime;
  @ApiModelProperty(value = "老师头像", required = true, example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images%2Fuser%2Fdefault%2Fuser_default_photo_large.jpg")
  private String teacher_img;
  @ApiModelProperty(value = "标准播放地址", required = true, example = "rtmp://3786.liveplay.myqcloud.com/eeolive/3786113d119e91db?txSecret=3708fcefb6829ab9a3ff6b6e11ea6378&txTime=7d8d37cd")
  private String[] stream_play_url;
  @ApiModelProperty(value = "hls播放地址", required = true, example = "http://3786.liveplay.myqcloud.com/eeolive/3786113d119e91db.m3u8?txSecret=3708fcefb6829ab9a3ff6b6e11ea6378&txTime=7d8d37cd")
  private String[] stream_play_hls;
  @ApiModelProperty(value = "sn", required = true, example = "MjdWJwAAOpw1")
  private String sn;
  @ApiModelProperty(value = "chatToken", required = true, example = "M4dm8WsrNJnHbvFug8cHlNT8B2BtmEtdK6L9sJtzUNjht17XTks7ms zJ1vWsKM0ZBlLXZ9eGMCXBz6Ax2MS1A==")
  private String chatToken;
  @ApiModelProperty(value = "直播房间id", required = true, example = "100024")
  private String chatRoomId;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }


  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public String getVideo_id() {
    return video_id;
  }


  public void setVideo_id(String video_id) {
    this.video_id = video_id;
  }


  public String getLive_id() {
    return live_id;
  }


  public void setLive_id(String live_id) {
    this.live_id = live_id;
  }


  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public String getTeacher_name() {
    return teacher_name;
  }


  public void setTeacher_name(String teacher_name) {
    this.teacher_name = teacher_name;
  }


  public String getBanner() {
    return banner;
  }


  public void setBanner(String banner) {
    this.banner = banner;
  }


  public Date getStart_time() {
    return start_time;
  }


  public void setStart_time(Date start_time) {
    this.start_time = start_time;
  }


  public Date getEnd_time() {
    return end_time;
  }


  public void setEnd_time(Date end_time) {
    this.end_time = end_time;
  }


  public String getTeacher_img() {
    return teacher_img;
  }


  public void setTeacher_img(String teacher_img) {
    this.teacher_img = teacher_img;
  }


  public String[] getStream_play_url() {
    return stream_play_url;
  }


  public void setStream_play_url(String[] stream_play_url) {
    this.stream_play_url = stream_play_url;
  }


  public String[] getStream_play_hls() {
    return stream_play_hls;
  }


  public void setStream_play_hls(String[] stream_play_hls) {
    this.stream_play_hls = stream_play_hls;
  }


  public String getSn() {
    return sn;
  }


  public void setSn(String sn) {
    this.sn = sn;
  }


  public String getChatToken() {
    return chatToken;
  }


  public void setChatToken(String chatToken) {
    this.chatToken = chatToken;
  }


  public Boolean getSubscribeStatus() {
    return subscribeStatus;
  }


  public void setSubscribeStatus(Boolean subscribeStatus) {
    this.subscribeStatus = subscribeStatus;
  }


  public Integer getCourseTypeBeforeGoclassTime() {
    return courseTypeBeforeGoclassTime;
  }


  public void setCourseTypeBeforeGoclassTime(Integer courseTypeBeforeGoclassTime) {
    this.courseTypeBeforeGoclassTime = courseTypeBeforeGoclassTime;
  }

  public Date getServerTime() {
    return serverTime;
  }

  public void setServerTime(Date serverTime) {
    this.serverTime = serverTime;
  }


  public String getChatRoomId() {
    return chatRoomId;
  }

  public void setChatRoomId(String chatRoomId) {
    this.chatRoomId = chatRoomId;
  }
  
}