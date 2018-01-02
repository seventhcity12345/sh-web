package com.webi.hwj.user.param;

import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

public class FindRsaEffectiveWorkTimeParam {
  private String rsaEffectiveExerciseTime;
  private List<String> effectiveLearningDays;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getRsaEffectiveExerciseTime() {
    return rsaEffectiveExerciseTime;
  }

  public void setRsaEffectiveExerciseTime(String rsaEffectiveExerciseTime) {
    this.rsaEffectiveExerciseTime = rsaEffectiveExerciseTime;
  }

  public List<String> getEffectiveLearningDays() {
    return effectiveLearningDays;
  }

  public void setEffectiveLearningDays(List<String> effectiveLearningDays) {
    this.effectiveLearningDays = effectiveLearningDays;
  }

}
