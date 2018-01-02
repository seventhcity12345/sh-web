package com.webi.hwj.index.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 课程表数据<br>
 * Description: 课程表数据<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年9月21日 上午10:13:19
 * 
 * @author komi.zsy
 */
public class FindLearningProgressParam implements Serializable {
  private static final long serialVersionUID = 2439788296930938109L;
  // 实际学习进度
  @ApiModelProperty(value = "实际学习进度", required = true, example = "50")
  private int actualLearningProgress;
  // 当前级别
  @ApiModelProperty(value = "实际学习进度", required = true, example = "General Level 2")
  private String currentLevel;
  // 本级已上1v1课程数
  @ApiModelProperty(value = "本级已上1v1课程数", required = true, example = "8")
  private Integer currentLevelOne2OneCourseCount;
  // 本级已上1vN课程数
  @ApiModelProperty(value = "本级已上1vN课程数", required = true, example = "7")
  private Integer currentLevelOne2ManyCourseCount;
  // 升级需上课程数
  @ApiModelProperty(value = "升级需上课程数", required = true, example = "18")
  private Integer levelupTotalCourseCount;
  // 达到下一级别需要完成的1v1课程数
  @ApiModelProperty(value = "达到下一级别需要完成的1v1课程数", required = true, example = "9")
  private Integer toNextLevelOne2OneCourseCount;
  // 达到下一级别需要完成的1vN课程数
  @ApiModelProperty(value = "达到下一级别需要完成的1v1课程数", required = true, example = "9")
  private Integer toNextLevelOne2ManyCourseCount;
  // 累计完成课时
  @ApiModelProperty(value = "累计完成课时", required = true, example = "20")
  private Integer userFinishCourseCount;
  // 累计说英语时长
  @ApiModelProperty(value = "累计说英语时", required = true, example = "16.5")
  private Double userTalkTime;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public int getActualLearningProgress() {
    return actualLearningProgress;
  }

  public void setActualLearningProgress(int actualLearningProgress) {
    this.actualLearningProgress = actualLearningProgress;
  }

  public String getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(String currentLevel) {
    this.currentLevel = currentLevel;
  }

  public Integer getCurrentLevelOne2OneCourseCount() {
    return currentLevelOne2OneCourseCount;
  }

  public void setCurrentLevelOne2OneCourseCount(Integer currentLevelOne2OneCourseCount) {
    this.currentLevelOne2OneCourseCount = currentLevelOne2OneCourseCount;
  }

  public Integer getCurrentLevelOne2ManyCourseCount() {
    return currentLevelOne2ManyCourseCount;
  }

  public void setCurrentLevelOne2ManyCourseCount(Integer currentLevelOne2ManyCourseCount) {
    this.currentLevelOne2ManyCourseCount = currentLevelOne2ManyCourseCount;
  }

  public Integer getLevelupTotalCourseCount() {
    return levelupTotalCourseCount;
  }

  public void setLevelupTotalCourseCount(Integer levelupTotalCourseCount) {
    this.levelupTotalCourseCount = levelupTotalCourseCount;
  }

  public Integer getToNextLevelOne2OneCourseCount() {
    return toNextLevelOne2OneCourseCount;
  }

  public void setToNextLevelOne2OneCourseCount(Integer toNextLevelOne2OneCourseCount) {
    this.toNextLevelOne2OneCourseCount = toNextLevelOne2OneCourseCount;
  }

  public Integer getToNextLevelOne2ManyCourseCount() {
    return toNextLevelOne2ManyCourseCount;
  }

  public void setToNextLevelOne2ManyCourseCount(Integer toNextLevelOne2ManyCourseCount) {
    this.toNextLevelOne2ManyCourseCount = toNextLevelOne2ManyCourseCount;
  }

  public Integer getUserFinishCourseCount() {
    return userFinishCourseCount;
  }

  public void setUserFinishCourseCount(Integer userFinishCourseCount) {
    this.userFinishCourseCount = userFinishCourseCount;
  }

  public Double getUserTalkTime() {
    return userTalkTime;
  }

  public void setUserTalkTime(Double userTalkTime) {
    this.userTalkTime = userTalkTime;
  }
}