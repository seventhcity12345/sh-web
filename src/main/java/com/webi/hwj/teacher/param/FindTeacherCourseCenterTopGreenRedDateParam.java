package com.webi.hwj.teacher.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 用于查询老师端-课程中心的头部红绿色小图标列表.<br>
 * Description: FindTeacherCourseCenterTopGreenRedDateParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年2月10日 下午5:36:12
 * 
 * @author yangmh
 */
public class FindTeacherCourseCenterTopGreenRedDateParam implements Serializable {

  /** 
  * 
  */
  private static final long serialVersionUID = -1695365972644903661L;

  // 查询日期
  private String curDate;

  // 查询老师id
  private String teacherId;

  // 返回日期
  private Date startTime;
  // 符合查询条件数据的数量
  private Integer ct;

  private Integer redCount;
  private Integer greenCount;

  // 老师是否已确认
  private Boolean isConfirm;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getCurDate() {
    return curDate;
  }

  public void setCurDate(String curDate) {
    this.curDate = curDate;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Integer getCt() {
    return ct;
  }

  public void setCt(Integer ct) {
    this.ct = ct;
  }

  public Boolean getIsConfirm() {
    return isConfirm;
  }

  public void setIsConfirm(Boolean isConfirm) {
    this.isConfirm = isConfirm;
  }

  public String getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(String teacherId) {
    this.teacherId = teacherId;
  }

  public Integer getRedCount() {
    return redCount;
  }

  public void setRedCount(Integer redCount) {
    this.redCount = redCount;
  }

  public Integer getGreenCount() {
    return greenCount;
  }

  public void setGreenCount(Integer greenCount) {
    this.greenCount = greenCount;
  }

}