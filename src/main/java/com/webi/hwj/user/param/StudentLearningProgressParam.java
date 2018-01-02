package com.webi.hwj.user.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

public class StudentLearningProgressParam implements Serializable {
  private static final long serialVersionUID = 8121800322485662071L;
  // 合同进行天数
  private int consumeOrderDays;
  // 剩余合同天数
  private int remainOrderDays;
  // 总课程数
  private int totalCourseCount;
  // 已消耗课时数
  private int consumeCourseCount;
  // 剩余课时数
  private int remainCourseCount;
  // 出席课时数
  private int noShowCourseCount;
  // 应有学习进度
  private int expectedLearningProgress;
  // 实际学习进度
  private int actualLearningProgress;
  // 当前级别
  private String currentLevel;
  // 本级已上1v1课程数
  private int currentLevelOne2OneCourseCount;
  // 本级已上1vN课程数
  private int currentLevelOne2ManyCourseCount;
  // 升级需上课程数
  private int levelupTotalCourseCount;
  // 达到下一级别需要完成的1v1课程数
  private int toNextLevelOne2OneCourseCount;
  // 达到下一级别需要完成的1vN课程数
  private int toNextLevelOne2ManyCourseCount;
  // 课件学习记录（学习N分钟以上算有效，一个月内有效的）
  private List<String> effectiveLearningDays;
  // 课件有效练习时间
  private int effectiveExerciseTime;
  // 小嗨要说的话
  private String encouragementWords;

  public int getConsumeOrderDays() {
    return consumeOrderDays;
  }

  public void setConsumeOrderDays(int consumeOrderDays) {
    this.consumeOrderDays = consumeOrderDays;
  }

  public int getRemainOrderDays() {
    return remainOrderDays;
  }

  public void setRemainOrderDays(int remainOrderDays) {
    this.remainOrderDays = remainOrderDays;
  }

  public int getConsumeCourseCount() {
    return consumeCourseCount;
  }

  public void setConsumeCourseCount(int consumeCourseCount) {
    this.consumeCourseCount = consumeCourseCount;
  }

  public int getRemainCourseCount() {
    return remainCourseCount;
  }

  public void setRemainCourseCount(int remainCourseCount) {
    this.remainCourseCount = remainCourseCount;
  }

  public int getExpectedLearningProgress() {
    return expectedLearningProgress;
  }

  public void setExpectedLearningProgress(int expectedLearningProgress) {
    this.expectedLearningProgress = expectedLearningProgress;
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

  public int getCurrentLevelOne2OneCourseCount() {
    return currentLevelOne2OneCourseCount;
  }

  public void setCurrentLevelOne2OneCourseCount(int currentLevelOne2OneCourseCount) {
    this.currentLevelOne2OneCourseCount = currentLevelOne2OneCourseCount;
  }

  public int getCurrentLevelOne2ManyCourseCount() {
    return currentLevelOne2ManyCourseCount;
  }

  public void setCurrentLevelOne2ManyCourseCount(int currentLevelOne2ManyCourseCount) {
    this.currentLevelOne2ManyCourseCount = currentLevelOne2ManyCourseCount;
  }

  public int getLevelupTotalCourseCount() {
    return levelupTotalCourseCount;
  }

  public void setLevelupTotalCourseCount(int levelupTotalCourseCount) {
    this.levelupTotalCourseCount = levelupTotalCourseCount;
  }

  public int getToNextLevelOne2OneCourseCount() {
    return toNextLevelOne2OneCourseCount;
  }

  public void setToNextLevelOne2OneCourseCount(int toNextLevelOne2OneCourseCount) {
    this.toNextLevelOne2OneCourseCount = toNextLevelOne2OneCourseCount;
  }

  public int getToNextLevelOne2ManyCourseCount() {
    return toNextLevelOne2ManyCourseCount;
  }

  public void setToNextLevelOne2ManyCourseCount(int toNextLevelOne2ManyCourseCount) {
    this.toNextLevelOne2ManyCourseCount = toNextLevelOne2ManyCourseCount;
  }

  public List<String> getEffectiveLearningDays() {
    return effectiveLearningDays;
  }

  public void setEffectiveLearningDays(List<String> effectiveLearningDays) {
    this.effectiveLearningDays = effectiveLearningDays;
  }

  public int getEffectiveExerciseTime() {
    return effectiveExerciseTime;
  }

  public void setEffectiveExerciseTime(int effectiveExerciseTime) {
    this.effectiveExerciseTime = effectiveExerciseTime;
  }

  public String getEncouragementWords() {
    return encouragementWords;
  }

  public void setEncouragementWords(String encouragementWords) {
    this.encouragementWords = encouragementWords;
  }

  public int getTotalCourseCount() {
    return totalCourseCount;
  }

  public void setTotalCourseCount(int totalCourseCount) {
    this.totalCourseCount = totalCourseCount;
  }

  public int getNoShowCourseCount() {
    return noShowCourseCount;
  }

  public void setNoShowCourseCount(int noShowCourseCount) {
    this.noShowCourseCount = noShowCourseCount;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
