package com.webi.hwj.course.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @category courseComment Entity
 * @author mingyisoft代码生成工具
 */
@ApiModel(value = "CourseCommentDetailInfoParam(成人-已完成课程-课程评价详细信息)")
public class CourseCommentDetailInfoParam implements Serializable {

  private static final long serialVersionUID = 7584042259341719133L;

  private String keyId;// 主键id

  @ApiModelProperty(value = "预约id(查询传参用:需要前端传递该参数)")
  private String subscribeCourseId; // 预约id

  @ApiModelProperty(value = "老师名称")
  private String teacherName;// 老师名称

  @ApiModelProperty(value = "老师照片")
  private String teacherPhoto;// 老师照片

  @ApiModelProperty(value = "发音分数(老师对学员的评价)")
  private String pronouncationScore;// 发音分数

  @ApiModelProperty(value = "语法分数(老师对学员的评价)")
  private String grammerScore;// 语法分数

  @ApiModelProperty(value = "词汇量分数(老师对学员的评价)")
  private String vocabularyScore;// 词汇量分数

  @ApiModelProperty(value = "听力分数(老师对学员的评价)")
  private String listeningScore;// 听力分数

  @ApiModelProperty(value = "评语(老师对学员的评语)")
  private String teacherCommentContent;// 评语(老师对学员的评语)

  @ApiModelProperty(value = "老师对学员的打分(平均分)")
  private String teacherShowScore;// 老师对学员的打分(平均分)

  @ApiModelProperty(value = "准备度分数(学员对老师的评价)")
  private String preparationScore;// 准备度分数

  @ApiModelProperty(value = "专业度分数(学员对老师的评价)")
  private String deliveryScore;// 专业度分数

  @ApiModelProperty(value = "互动性分数(学员对老师的评价)")
  private String interactionScore;// 互动性分数

  @ApiModelProperty(value = "评语(学员对老师的评语)")
  private String studentCommentContent;// 评语(学员对老师的评语)

  @ApiModelProperty(value = "学员对老师的打分(平均分)")
  private String studentShowScore;// 学员对老师的打分(平均分)

  @ApiModelProperty(value = "老师时间id")
  private String teacherTimeId;// 老师时间id

  @ApiModelProperty(value = "课件地址")
  private String courseCourseware;// 课件地址

  @ApiModelProperty(value = "上课平台(上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放);2:classin(有课程回放))")
  private Integer teacherTimePlatform;// 上课平台(上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放))

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getTeacherPhoto() {
    return teacherPhoto;
  }

  public void setTeacherPhoto(String teacherPhoto) {
    this.teacherPhoto = teacherPhoto;
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getSubscribeCourseId() {
    return subscribeCourseId;
  }

  public void setSubscribeCourseId(String subscribeCourseId) {
    this.subscribeCourseId = subscribeCourseId;
  }

  public String getPronouncationScore() {
    return pronouncationScore;
  }

  public void setPronouncationScore(String pronouncationScore) {
    this.pronouncationScore = pronouncationScore;
  }

  public String getVocabularyScore() {
    return vocabularyScore;
  }

  public void setVocabularyScore(String vocabularyScore) {
    this.vocabularyScore = vocabularyScore;
  }

  public String getGrammerScore() {
    return grammerScore;
  }

  public void setGrammerScore(String grammerScore) {
    this.grammerScore = grammerScore;
  }

  public String getListeningScore() {
    return listeningScore;
  }

  public void setListeningScore(String listeningScore) {
    this.listeningScore = listeningScore;
  }

  public String getStudentCommentContent() {
    return studentCommentContent;
  }

  public void setStudentCommentContent(String studentCommentContent) {
    this.studentCommentContent = studentCommentContent;
  }

  public String getStudentShowScore() {
    return studentShowScore;
  }

  public void setStudentShowScore(String studentShowScore) {
    this.studentShowScore = studentShowScore;
  }

  public String getPreparationScore() {
    return preparationScore;
  }

  public void setPreparationScore(String preparationScore) {
    this.preparationScore = preparationScore;
  }

  public String getDeliveryScore() {
    return deliveryScore;
  }

  public void setDeliveryScore(String deliveryScore) {
    this.deliveryScore = deliveryScore;
  }

  public String getInteractionScore() {
    return interactionScore;
  }

  public void setInteractionScore(String interactionScore) {
    this.interactionScore = interactionScore;
  }

  public String getTeacherCommentContent() {
    return teacherCommentContent;
  }

  public void setTeacherCommentContent(String teacherCommentContent) {
    this.teacherCommentContent = teacherCommentContent;
  }

  public String getTeacherShowScore() {
    return teacherShowScore;
  }

  public void setTeacherShowScore(String teacherShowScore) {
    this.teacherShowScore = teacherShowScore;
  }

  public String getTeacherTimeId() {
    return teacherTimeId;
  }

  public void setTeacherTimeId(String teacherTimeId) {
    this.teacherTimeId = teacherTimeId;
  }

  public String getCourseCourseware() {
    return courseCourseware;
  }

  public void setCourseCourseware(String courseCourseware) {
    this.courseCourseware = courseCourseware;
  }

  public Integer getTeacherTimePlatform() {
    return teacherTimePlatform;
  }

  public void setTeacherTimePlatform(Integer teacherTimePlatform) {
    this.teacherTimePlatform = teacherTimePlatform;
  }

}