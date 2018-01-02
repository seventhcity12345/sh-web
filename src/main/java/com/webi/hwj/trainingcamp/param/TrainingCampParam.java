package com.webi.hwj.trainingcamp.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比列表<br>
 * Description: 训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比列表<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月8日 下午2:36:31
 * 
 * @author felix.yl
 */
@ApiModel(value = "TrainingCampParam(成人-口语训练营-基本信息模型)")
public class TrainingCampParam implements Serializable {

  private static final long serialVersionUID = 3743432727711234043L;

  // 主键
  @ApiModelProperty(value = "主键", example = "72dfe41853e64fef95dcf742de71cfd1")
  private String keyId;

  // 标题
  @ApiModelProperty(value = "训练营标题", example = "嗨跑训练营")
  private String trainingCampTitle;

  // 简介
  @ApiModelProperty(value = "训练营简介",
      example = "该怎么安排这漫长的暑假呢？跟着Felix学英语吧, 一个夏天就能让你大变样呢！没时间解释了,快上车！进入暑期口语训练营,顺畅沟通无障碍！")
  private String trainingCampDesc;

  // 封面
  @ApiModelProperty(value = "训练营封面图片",
      example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images%2Fuser%2Fdefault%2Fuser_default_photo.jpg")
  private String trainingCampPic;

  // 生效开始时间
  @ApiModelProperty(value = "生效开始时间", example = "2017-08-06 08:00:00")
  private Date trainingCampStartTime;

  // 生效结束时间
  @ApiModelProperty(value = "生效结束时间", example = "2017-08-27 08:00:00")
  private Date trainingCampEndTime;

  // 学员平均等级
  @ApiModelProperty(value = "学员平均等级", example = "3")
  private Integer trainingCampAverageLevel;

  // 昨日活跃人数
  @ApiModelProperty(value = "昨日活跃人数", example = "20")
  private Integer trainingCampYesterdayActivity;

  // 学员平均年龄
  @ApiModelProperty(value = "学员平均年龄", example = "22")
  private Integer trainingCampAverageAge;

  // 女性占比
  @ApiModelProperty(value = "女性占比", example = "86")
  private Integer trainingCampFemalePresent;

  // LC名称
  @ApiModelProperty(value = "LC名称", example = "Lolo")
  private String adminUserName;

  // LC头像
  @ApiModelProperty(value = "LC头像",
      example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images%2Fuser%2Fdefault%2Fuser_default_photo.jpg")
  private String adminUserPhoto;

  // 训练营id(作为查询条件,前端不用理睬)
  private String trainingCampId;

  // 当前时间(作为查询条件，前端不用理睬)
  private Date currentTime;

  // 当前训练营人数(前端不用理睬)
  private Integer trainingCampNum;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getTrainingCampTitle() {
    return trainingCampTitle;
  }

  public void setTrainingCampTitle(String trainingCampTitle) {
    this.trainingCampTitle = trainingCampTitle;
  }

  public String getTrainingCampDesc() {
    return trainingCampDesc;
  }

  public void setTrainingCampDesc(String trainingCampDesc) {
    this.trainingCampDesc = trainingCampDesc;
  }

  public String getTrainingCampPic() {
    return trainingCampPic;
  }

  public void setTrainingCampPic(String trainingCampPic) {
    this.trainingCampPic = trainingCampPic;
  }

  public Date getTrainingCampStartTime() {
    return trainingCampStartTime;
  }

  public void setTrainingCampStartTime(Date trainingCampStartTime) {
    this.trainingCampStartTime = trainingCampStartTime;
  }

  public Date getTrainingCampEndTime() {
    return trainingCampEndTime;
  }

  public void setTrainingCampEndTime(Date trainingCampEndTime) {
    this.trainingCampEndTime = trainingCampEndTime;
  }

  public Integer getTrainingCampAverageLevel() {
    return trainingCampAverageLevel;
  }

  public void setTrainingCampAverageLevel(Integer trainingCampAverageLevel) {
    this.trainingCampAverageLevel = trainingCampAverageLevel;
  }

  public Integer getTrainingCampYesterdayActivity() {
    return trainingCampYesterdayActivity;
  }

  public void setTrainingCampYesterdayActivity(Integer trainingCampYesterdayActivity) {
    this.trainingCampYesterdayActivity = trainingCampYesterdayActivity;
  }

  public Integer getTrainingCampAverageAge() {
    return trainingCampAverageAge;
  }

  public void setTrainingCampAverageAge(Integer trainingCampAverageAge) {
    this.trainingCampAverageAge = trainingCampAverageAge;
  }

  public Integer getTrainingCampFemalePresent() {
    return trainingCampFemalePresent;
  }

  public void setTrainingCampFemalePresent(Integer trainingCampFemalePresent) {
    this.trainingCampFemalePresent = trainingCampFemalePresent;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public String getAdminUserPhoto() {
    return adminUserPhoto;
  }

  public void setAdminUserPhoto(String adminUserPhoto) {
    this.adminUserPhoto = adminUserPhoto;
  }

  public String getTrainingCampId() {
    return trainingCampId;
  }

  public void setTrainingCampId(String trainingCampId) {
    this.trainingCampId = trainingCampId;
  }

  public Date getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(Date currentTime) {
    this.currentTime = currentTime;
  }

  public Integer getTrainingCampNum() {
    return trainingCampNum;
  }

  public void setTrainingCampNum(Integer trainingCampNum) {
    this.trainingCampNum = trainingCampNum;
  }

}