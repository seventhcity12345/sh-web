package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

public class StatisticsMonthSubscribeCountParam implements Serializable {
  private static final long serialVersionUID = -696669967173874407L;
  // 上课日期
  private String classDate;
  // 1v1课程数
  private int one2OneCount;
  // 1vn课程数
  private int one2ManyCount;
  // es课程数
  private int englishStudioCount;
  // extension1v1课程数
  private int extensionOne2OneCount;
  // oc课程数
  private int ocCount;
  // extension1v1课程数
  private int demoOne2OneCount;

  // 合计
  private int total;

  public String getClassDate() {
    return classDate;
  }

  public void setClassDate(String classDate) {
    this.classDate = classDate;
  }

  public int getOne2OneCount() {
    return one2OneCount;
  }

  public void setOne2OneCount(int one2OneCount) {
    this.one2OneCount = one2OneCount;
  }

  public int getOne2ManyCount() {
    return one2ManyCount;
  }

  public void setOne2ManyCount(int one2ManyCount) {
    this.one2ManyCount = one2ManyCount;
  }

  public int getEnglishStudioCount() {
    return englishStudioCount;
  }

  public void setEnglishStudioCount(int englishStudioCount) {
    this.englishStudioCount = englishStudioCount;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getExtensionOne2OneCount() {
    return extensionOne2OneCount;
  }

  public void setExtensionOne2OneCount(int extensionOne2OneCount) {
    this.extensionOne2OneCount = extensionOne2OneCount;
  }

  public int getOcCount() {
    return ocCount;
  }

  public void setOcCount(int ocCount) {
    this.ocCount = ocCount;
  }

  public int getDemoOne2OneCount() {
    return demoOne2OneCount;
  }

  public void setDemoOne2OneCount(int demoOne2OneCount) {
    this.demoOne2OneCount = demoOne2OneCount;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
