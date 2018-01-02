package com.webi.hwj.teacher.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category teacher Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_teacher")
public class Teacher implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -2576148205679192661L;
  // 主键id
  private String keyId;
  // 老师名称
  @NotNull(message = "老师姓名不能为空")
  @Length(min = 1, max = 200, message = "老师姓名不能超过200位")
  private String teacherName;
  // 老师账号
  @Length(min = 0, max = 20, message = "老师账号不能超过20位")
  private String account;
  // 老师密码
  private String pwd;
  // 老师国籍
  @NotNull(message = "老师国籍不能为空")
  @Length(min = 1, max = 20, message = "老师国籍不能超过20位")
  private String teacherNationality;
  // 老师性别
  @NotNull(message = "老师性别不能为空")
  private Integer teacherGender;
  // 老师头像
  private String teacherPhoto;
  // 老师简介
  private String teacherDesc;
  // 老师可以上课的类型
  @NotNull(message = "老师上课权限不能为空")
  @Length(min = 1, max = 200, message = "老师上课权限不能超过200位")
  private String teacherCourseType;
  // 联系方式描述
  @Length(min = 0, max = 200, message = "老师联系方式不能超过200位")
  private String teacherContactContent;
  // 老师工作性质(0兼职,1全职,默认为0)
  @NotNull(message = "老师工作性质不能为空")
  private Integer teacherJobType;
  // 第三方来源
  @NotNull(message = "老师来源渠道不能为空")
  @Length(min = 1, max = 10, message = "老师来源渠道不能超过10位")
  private String thirdFrom;
  // 创建日期
  private Date createDate;
  // 最后更新日期
  private Date updateDate;
  // 创建人id
  private String createUserId;
  // 最后更新人id
  private String updateUserId;
  // 是否使用(1:使用,0:不使用)
  private Boolean isUsed;
  //老师是否绑定微信(1:绑定，0：未绑定)
  private Boolean isBindWechat;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getTeacherNationality() {
    return teacherNationality;
  }

  public void setTeacherNationality(String teacherNationality) {
    this.teacherNationality = teacherNationality;
  }

  public Integer getTeacherGender() {
    return teacherGender;
  }

  public void setTeacherGender(Integer teacherGender) {
    this.teacherGender = teacherGender;
  }

  public String getTeacherPhoto() {
    return teacherPhoto;
  }

  public void setTeacherPhoto(String teacherPhoto) {
    this.teacherPhoto = teacherPhoto;
  }

  public String getTeacherDesc() {
    return teacherDesc;
  }

  public void setTeacherDesc(String teacherDesc) {
    this.teacherDesc = teacherDesc;
  }

  public String getTeacherCourseType() {
    return teacherCourseType;
  }

  public void setTeacherCourseType(String teacherCourseType) {
    this.teacherCourseType = teacherCourseType;
  }

  public String getTeacherContactContent() {
    return teacherContactContent;
  }

  public void setTeacherContactContent(String teacherContactContent) {
    this.teacherContactContent = teacherContactContent;
  }

  public Integer getTeacherJobType() {
    return teacherJobType;
  }

  public void setTeacherJobType(Integer teacherJobType) {
    this.teacherJobType = teacherJobType;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }

  public Boolean getIsUsed() {
    return isUsed;
  }

  public void setIsUsed(Boolean isUsed) {
    this.isUsed = isUsed;
  }

  public Boolean getIsBindWechat() {
    return isBindWechat;
  }

  public void setIsBindWechat(Boolean isBindWechat) {
    this.isBindWechat = isBindWechat;
  }
}