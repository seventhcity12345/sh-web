package com.webi.hwj.subscribecourse.constant;

public class SubscribeCourseConstant {
  /**
   * 微信订课列表状态(0.已完成已评价)
   */
  public static final int WEIXIN_SUBSCRIBE_COMMENT = 0;

  /**
   * 微信订课列表状态(1.已完成未评价（显示待评价标签）)
   */
  public static final int WEIXIN_SUBSCRIBE_NOT_COMMENT = 1;

  /**
   * 微信订课列表状态(2.已预约且当天开课（显示即将开课标签（当前时间到当天24点内））)
   */
  public static final int WEIXIN_SUBSCRIBE_TODAY = 2;

  /**
   * 微信订课列表状态(3.已预约且非当天开课)
   */
  public static final int WEIXIN_SUBSCRIBE_AFTER_TODAY = 3;
  /**
   * 微信订课列表状态(4.已预约且非当天开课)
   */
  public static final int WEIXIN_SUBSCRIBE_NO_SHOW = 4;

  /**
   * 微信预约课程详情状态 0: 已完成未出席
   */
  public static final int WEIXIN_SUBSCRIBE_DETAIL_NOT_SHOW = 0;

  /**
   * 微信预约课程详情状态 1: 已完成已出席（只有这个状态才有课程回顾按钮）
   */
  public static final int WEIXIN_SUBSCRIBE_DETAIL_SHOW = 1;

  /**
   * 微信预约课程详情状态 2: 已预约未完成
   */
  public static final int WEIXIN_SUBSCRIBE_DETAIL_NOT_START = 2;

  /**
   * 课程预约 上课状态(表示是否已上课-0:未上课,1:已上课)
   */
  public static final int SUBSCRIBE_STATUS_NO_SHOW = 0;

  /**
   * 状态(1-可取消预约;2-倒计时;3-可进入教室)
   */
  public static final int CAN_CANCEL_SUBSCRIBE = 1;

  /**
   * 状态(1-可取消预约;2-倒计时;3-可进入教室)
   */
  public static final int CAN_COUNT_DOWN = 2;
  /**
   * 状态(1-可取消预约;2-倒计时;3-可进入教室)
   */
  public static final int CAN_INTO_CLASSROOM = 3;

  /**
   * 当前时间是否大于课程结束时间(0:是;1:否)
   */
  public static final int COURSE_END_TIME_STATUS_YES = 0;
  /**
   * 当前时间是否大于课程结束时间(0:是;1:否)
   */
  public static final int COURSE_END_TIME_STATUS_NO = 1;

}
