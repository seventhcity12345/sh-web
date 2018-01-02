package com.webi.hwj.constant;

/**
 * Title: 合同状态常量类<br>
 * Description: OrderStatusConstant<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年1月8日 下午3:53:32
 * 
 * @author athrun.cw
 */
public class OrderStatusConstant {
  /**
   * 合同原始状态：正常合同
   */
  public static final String ORDER_ORIGINAL_TYPE_NORMAL = "0";
  /**
   * 合同原始状态：续约合同
   */
  public static final String ORDER_ORIGINAL_TYPE_RENEWAL = "1";

  /**
   * 已拟定
   */
  public static final String ORDER_STATUS_HAVE_PLANNED = "1";

  /**
   * 已发送
   */
  public static final String ORDER_STATUS_HAVE_SENT = "2";

  /**
   * 已确认
   */
  public static final String ORDER_STATUS_HAVE_CONFIRMED = "3";

  /**
   * 支付中
   */
  public static final String ORDER_STATUS_PAYING = "4";

  /**
   * 已支付
   */
  public static final String ORDER_STATUS_HAVE_PAID = "5";

  /**
   * 已过期
   */
  public static final String ORDER_STATUS_HAVE_EXPIRED = "6";

  /**
   * 已终止
   */
  public static final String ORDER_STATUS_HAVE_TERMINATED = "7";

  // modified by ivan.mgh，2016年5月11日17:22:41
  /**
   * 已删除（该状态仅用于通知CRM系统合同已被删除，SPEAKHI中无此状态）
   */
  public static final String ORDER_STATUS_HAVE_DELETED = "8";

  /**
   * split表 线上支付：未支付 0
   */
  public static final String ORDER_SPLIT_STATUS_HAVE_NOT_PAID_ONLINE = "0";

  /**
   * split表 线上支付：已支付 1
   */
  public static final String ORDER_SPLIT_STATUS_HAVE_PAID_ONLINE = "1";

  /**
   * split表 非线上支付：2:已支付,未确认
   */
  public static final String ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE = "2";

  /**
   * split表未支付3:已支付,已确认
   */
  public static final String ORDER_SPLIT_STATUS_HAVE_PAID_OFFLINE = "3";
  
  /**
   * split表 分期：4:未申请
   */
  public static final String ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY = "4";

  /**
   * split表 分期：5:申请中
   */
  public static final String ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING = "5";

  /**
   * split表 分期：6:申请成功
   */
  public static final String ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS = "6";

  /**
   * split表  分期： 7:申请失败
   */
  public static final String ORDER_SPLIT_STATUS_BAIDU_APPLY_FAIL = "7";
  
  /**
   * split表  支付方式(5:百度分期)
   */
  public static final int ORDER_SPLIT_PAY_TYPE_BAIDU  = 5;
  
  /**
   * split表  支付方式(6:招联分期)
   */
  public static final int ORDER_SPLIT_PAY_TYPE_ZHAOLIAN  = 6;
}
