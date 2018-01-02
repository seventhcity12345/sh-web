package com.webi.hwj.courseone2many.param;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 排课参数实体，增加预约id<br>
 * Description: CourseOne2ManySchedulingParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月11日 下午3:20:51
 * 
 * @author komi.zsy
 */
@TableName("t_course_one2many_scheduling")
public class HuanxunCourseOne2ManySchedulingParam implements Serializable {
  private static final long serialVersionUID = 9201245268559349458L;
  // 主键id
  private String keyId;
  // 体系类别
  @NotNull(message = "体系类别不能为空")
  private String huanxunCategoryType;
  // 跟码表里的课程类别走
  @NotNull(message = "课程类别不能为空")
  private String huanxunCourseType;
  // 所属课程id
  @NotNull(message = "所属课程id不能为空")
  private String huanxunCourseId;
  // 课程级别(多个用逗号分隔)
  @NotNull(message = "课程级别不能为空")
  private String huanxunCourseLevel;
  // 老师id
  @NotNull(message = "老师id不能为空")
  private String huanxunTeacherId;
  // 上课时间
  @NotNull(message = "上课时间不能为空")
  private Date startTime;
  // 下课时间
  private Date endTime;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getHuanxunCategoryType() {
    return huanxunCategoryType;
  }

  public void setHuanxunCategoryType(String huanxunCategoryType) {
    this.huanxunCategoryType = huanxunCategoryType;
  }

  public String getHuanxunCourseType() {
    return huanxunCourseType;
  }

  public void setHuanxunCourseType(String huanxunCourseType) {
    this.huanxunCourseType = huanxunCourseType;
  }

  public String getHuanxunCourseId() {
    return huanxunCourseId;
  }

  public void setHuanxunCourseId(String huanxunCourseId) {
    this.huanxunCourseId = huanxunCourseId;
  }

  public String getHuanxunCourseLevel() {
    return huanxunCourseLevel;
  }

  public void setHuanxunCourseLevel(String huanxunCourseLevel) {
    this.huanxunCourseLevel = huanxunCourseLevel;
  }

  public String getHuanxunTeacherId() {
    return huanxunTeacherId;
  }

  public void setHuanxunTeacherId(String huanxunTeacherId) {
    this.huanxunTeacherId = huanxunTeacherId;
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

}
