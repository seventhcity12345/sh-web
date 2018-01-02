package com.webi.hwj.teacher.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 用于查询老师端-课程中心的头部红绿色小图标列表.<br>
 * Description: FindTeacherCourseCenterTopGreenRedDateParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年2月10日 下午5:36:12
 * 
 * @author yangmh
 */
public class FindTeacherCourseCenterExtraCourseParam implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 5587223744351571952L;
  @ApiModelProperty(value = "课程标题", required = true, example = "hello word")
  private String courseTitle;
  @ApiModelProperty(value = "参与人数", required = true, example = "10")
  private Integer attendeeCount;
  @ApiModelProperty(value = "开始时间", required = true, example = "1486951200000")
  private Date startTime;
  @ApiModelProperty(value = "结束时间", required = true, example = "1486954500000")
  private Date endTime;
  @ApiModelProperty(value = "课程图片", required = true,
      example = "http://speakhi-teenager-test.oss-cn-hangzhou.aliyuncs.com/images/course/one2many/course_type2/057f089a8a314256a0c3209dda067819.jpg")
  private String coursePic;
  @ApiModelProperty(value = "课件地址", required = true,
      example = "http://speakhi-teenager-test.oss-cn-hangzhou.aliyuncs.com/courseware/one2many/course_type2/a9a9af036dbc4edfb0697e3be39f2ee3.pptx")
  private String courseCourseware;
  @ApiModelProperty(value = "课程类型", required = true,
      example = "course_type8")
  private String courseType;
  @ApiModelProperty(value = "老师时间id", required = true,
      example = "f9121b379c04482fbbe0640871e9b4d4")
  private String teacherTimeId;
  private String teacherId;
  private String queryDate;
  @ApiModelProperty(value = "课程类型英文名", required = true,
      example = "ES")
  private String courseTypeEnglishName;
  @ApiModelProperty(value = "老师是否确认（用于绿色对号显示）", required = true,
      example = "true")
  private Boolean isConfirm;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public Integer getAttendeeCount() {
    return attendeeCount;
  }

  public void setAttendeeCount(Integer attendeeCount) {
    this.attendeeCount = attendeeCount;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getCoursePic() {
    return coursePic;
  }

  public void setCoursePic(String coursePic) {
    this.coursePic = coursePic;
  }

  public String getCourseCourseware() {
    return courseCourseware;
  }

  public void setCourseCourseware(String courseCourseware) {
    this.courseCourseware = courseCourseware;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getQueryDate() {
    return queryDate;
  }

  public void setQueryDate(String queryDate) {
    this.queryDate = queryDate;
  }

  public String getCourseTypeEnglishName() {
    return courseTypeEnglishName;
  }

  public void setCourseTypeEnglishName(String courseTypeEnglishName) {
    this.courseTypeEnglishName = courseTypeEnglishName;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public Boolean getIsConfirm() {
    return isConfirm;
  }

  public void setIsConfirm(Boolean isConfirm) {
    this.isConfirm = isConfirm;
  }

}