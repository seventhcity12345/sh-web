package com.webi.hwj.subscribecourse.constant;

public class AdminSubscribeCourseConstant {
  // 1v1 课程所占课时数
  public static final double COURSE_1V1_TIME = 0.5;
  // lecture 课程所占课时数
  public static final double COURSE_LECTURE_TIME = 1;
  /**
   * 预约状态(0:未上课,1:已上课)
   */
  public static final int SUBSCRIBE_STATUS_SHOW = 1;
  /**
   * 预约状态(0:未上课,1:已上课)
   */
  public static final int SUBSCRIBE_STATUS_NO_SHOW = 0;

  /**
   * 发送课程跟踪信息邮件标题.
   */
  public static final String ABNORMAL_SUBSCRIBE_MAIL_SUBJECT = "课程跟踪异常信息";

  /**
   * 发送课程跟踪信息邮件附件名.
   */
  public static final String ABNORMAL_SUBSCRIBE_MAIL_ATTACHMENT_NAME = "课程跟踪异常信息";

  /**
   * 发团训学员报表定时发送信息邮件标题.
   */
  public static final String TUANXUN_USER_INFO_MAIL_SUBJECT = "团训学员报表信息";

  /**
   * demo课speakhi全职老师的提前预约时间 单位分钟.
   */
  public static final int DEMO_SUBSCRIBE_TIME_SPEAKHI_FULL_TIME = 120;

  /**
   * demo课speakhi全职老师的提前取消预约时间 单位分钟.
   */
  public static final int DEMO_CANCEL_SUBSCRIBE_TIME_SPEAKHI_FULL_TIME = 120;
}
