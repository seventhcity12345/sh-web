package com.webi.hwj.user.constant;

public class UserConstant {
  /**
   * 新生天数（小于这个天数就是新生）
   */
  public static final int NEW_STUDENT_DAYS = 15;
  /**
   * 新生进度默认差值
   */
  public static final int NEW_STUDENT_PERCENTAGE = -200;

  /**
   * 查询follow次数（follow小于这个次数需要查询出来）
   */
  public static final int FOLLOWUP_COUNT = 2;

  /**
   * 一个月的天数
   */
  public static final int DAYS_OF_MONTH = 31;

  /**
   * 一个星期的天数
   */
  public static final int DAYS_OF_WEEK = 7;
  
  /**
   * 0:女;
   */
  public static final int USER_GENDER_FEMALE = 0;
  
  /**
   * 1:男;
   */
  public static final int USER_GENDER_MALE = 1;
  
  /**
   * 2:还没选
   */
  public static final int USER_GENDER_NOT = 2;
}
