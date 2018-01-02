package com.webi.hwj.course.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @category courseComment Entity
 * @author mingyisoft代码生成工具
 */
@ApiModel(value = "StudentCommentToTeacherParam(成人-已完成课程-学员评价老师信息)")
public class StudentCommentToTeacherParam implements Serializable {

  private static final long serialVersionUID = 4330529767516734552L;

  @ApiModelProperty(value = "预约id(需要前端传)", required = true,
      example = "55ff7c23e42143c7ba5d7a2905cd5cf7")
  @NotNull(message = "预约课程id不能为空")
  private String subscribeCourseId;// 预约课程id

  @ApiModelProperty(value = "被评论人id(老师id;需要前端传)", required = true,
      example = "7fa43cc0e4ae43408247d6df825a84df")
  @NotNull(message = "被评论人id不能为空")
  private String toUserId;// 被评论人id

  @ApiModelProperty(value = "准备度分数(需要前端传)", required = true, example = "5")
  @NotNull(message = "准备度分数不能为空")
  private String preparationScore;// 准备度分数

  @ApiModelProperty(value = "专业度分数(需要前端传)", required = true, example = "3")
  @NotNull(message = "专业度分数不能为空")
  private String deliveryScore;// 专业度分数

  @ApiModelProperty(value = "互动性分数(需要前端传)", required = true, example = "3")
  @NotNull(message = "互动性分数不能为空")
  private String interactionScore;// 互动性分数

  @ApiModelProperty(value = "显示分数(平均分;需要前端传)", required = true, example = "4")
  @NotNull(message = "显示分数(平均分)不能为空")
  private String showScore;// 显示分数(平均分)

  @ApiModelProperty(value = "评论内容(需要前端传)", required = true, example = "这个老师非常好！")
  @NotNull(message = "评论内容不能为空")
  private String commentContent;// 评论内容

  @ApiModelProperty(value = "评论人id(查询用;该字段不用前端传)", example = "166f92ec49974597ab652d003753af54")
  private String fromUserId;// 被评论人id

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getSubscribeCourseId() {
    return subscribeCourseId;
  }

  public void setSubscribeCourseId(String subscribeCourseId) {
    this.subscribeCourseId = subscribeCourseId;
  }

  public String getToUserId() {
    return toUserId;
  }

  public void setToUserId(String toUserId) {
    this.toUserId = toUserId;
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

  public String getShowScore() {
    return showScore;
  }

  public void setShowScore(String showScore) {
    this.showScore = showScore;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  public String getFromUserId() {
    return fromUserId;
  }

  public void setFromUserId(String fromUserId) {
    this.fromUserId = fromUserId;
  }

}