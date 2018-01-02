/** 
 * File: OrderCourseConstant.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.ordercourse.constant<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年3月25日 上午11:11:31
 * @author ivan.mgh
 */
package com.webi.hwj.ordercourse.constant;

/**
 * Title: 合同相关常量<br>
 * Description: 合同相关常量<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月25日 上午11:11:31
 * 
 * @author ivan.mgh
 */
public class OrderCourseConstant {
  // 已拟定
  public static final String ORDER_HAS_BEEN_PROPOSED = "1";
  // 已发送
  public static final String ORDER_HAS_BEEN_SENT = "2";
  // 已确认
  public static final String ORDER_HAS_BEEN_CONFIRMED = "3";
  // 支付中
  public static final String ORDER_IS_BEING_PAID_IN = "4";
  // 已支付
  public static final String ORDER_HAS_BEEN_PAID = "5";
  // 已过期
  public static final String ORDER_IS_EXPIRED = "6";
  // 已终止
  public static final String ORDER_IS_TERMINATED = "7";

  // 支付来源：个人转账
  public static final String SPLIT_PAY_TYPE_TRANSFER = "3";
  // 支付来源：线上支付（其他）
  public static final String SPLIT_PAY_TYPE_ONLINE_OTHER = "4";

  /**
   * 课程单位类型（0:节，1:月，2：天）
   */
  public static final String COURSE_UNIT_TYPE_MONTH = "1";

  /**
   * 课程单位类型（0:节，1:月，2：天）
   */
  public static final String COURSE_UNIT_TYPE_DAY = "2";

  /**
   * 课程单位类型（0:节，1:月，2：天）
   */
  public static final String COURSE_UNIT_TYPE_CLASS = "0";
  
  
  /**
   * 合同时效单位：0:月
   */
  public static final int LIMIT_SHOW_TIME_UNIT_MONTH = 0;

  /**
   * 合同时效单位：1：天
   */
  public static final int LIMIT_SHOW_TIME_UNIT_DAY = 1;
  
  
  
  /**
   * 课程是否赠送(1:是)
   */
  public static final int ORDER_COURSE_OPTION_IS_GIFT = 1;

  /**
   * 课程是否赠送(0:否)
   */
  public static final int ORDER_COURSE_OPTION_IS_NOT_GIFT = 0;
  
  // 学生来源：0:常规
  public static final Integer USER_FROM_TYPE_NORMAL = 0;
  //学生来源：1:线下售卖
  public static final Integer USER_FROM_TYPE_OFFLINE = 1;
  //学生来源：2:线下转线上
  public static final Integer USER_FROM_TYPE_OFFLINE_TO_ONLINE = 2;
  //学生来源：3:团训
  public static final Integer USER_FROM_TYPE_GROUP_TRAINING = 3;
  //学生来源：4:学员推荐
  public static final Integer USER_FROM_TYPE_STUDENT = 4;
  //学生来源：5:内部员工
  public static final Integer USER_FROM_TYPE_SELF = 5;
  //学生来源：6:测试
  public static final Integer USER_FROM_TYPE_TEST = 6;
  //学生来源：7:VIP学员
  public static final Integer USER_FROM_TYPE_VIP = 7;
  //学生来源：8:QQ会员活动
  public static final Integer USER_FROM_TYPE_QQVIP = 8;
  //学生来源：9:美邦会员活动
  public static final Integer USER_FROM_TYPE_MBVIP = 9;
}
