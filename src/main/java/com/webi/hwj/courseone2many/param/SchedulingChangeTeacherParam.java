package com.webi.hwj.courseone2many.param;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title:排课更换老师<br> 
 * Description: 排课更换老师<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2016年10月26日 下午6:21:32 
 * @author komi.zsy
 */
public class SchedulingChangeTeacherParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = -5174962826906144835L;
  // 大课排课id（1v1时为空）
  private String courseId;
  // 老师id
  @NotNull(message = "老师id不能为空")
  private String teacherId;
  // 老师时间id
  @NotNull(message = "老师时间id不能为空")
  private String teacherTimeId;
  // 上课时间
  @NotNull(message = "上课时间不能为空")
  private Date startTime;
  // 下课时间
  @NotNull(message = "下课时间不能为空")
  private Date endTime;
  //老师来源
  private String thirdFrom;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
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

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

}