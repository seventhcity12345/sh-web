package com.webi.hwj.redeemcode.constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RedeemCodeConstant {
  /**
   * modify by athrun.cw 2016年4月13日14:31:58 以A开头的 兑换码体验活动
   */
  public static final int REDEEMCODE_A_NUM = 2000;

  // 活动A开始时间(无期限)
  public static final String ACTIVITY_START_TIME_A = "1970-07-01 00:00:00";

  // 活动A结束时间(无期限)
  public static final String ACTIVITY_END_TIME_A = "3000-00-00 00:00:00";

  // 兑换码体验活动名称 activity_name
  public static final String ACTIVITY_NAME_A = "MONTH_EXPERIENCE1";

  public static final int REDEEMCODE_EFFECTIVE_TIME = 1;

  // 开启的线程数
  public static final int THREAD_NUM = 20;

  // 一套以M开头，表示是M引来的流量
  public static final int REDEEMCODE_M_NUM = 100000;

  // 一套以W开头，表示是W引来的流量
  public static final int REDEEMCODE_W_NUM = 1000;

  // 兑换码的长度12
  public static final int REDEEMCODE_LENGTH = 12;

  // 活动M开始时间
  public static final String ACTIVITY_START_TIME_M = "2016-03-16 00:00:00";

  // 活动M结束时间
  public static final String ACTIVITY_END_TIME_M = "2016-04-20 00:00:00";

  // 活动W开始时间
  public static final String ACTIVITY_START_TIME_W = "2016-03-01 00:00:00";

  // 活动W结束时间
  public static final String ACTIVITY_END_TIME_W = "2016-03-16 00:00:00";

  // 活动名称activity_name
  public static final String ACTIVITY_NAME = "MCDONLD1";

  // 对换码 1 已发送
  public static final int REDEEM_CODE_SENT = 1;
  // 对换码 0 未发送
  public static final int REDEEM_CODE_UNSENT = 0;

  public static void main(String[] args) throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd mm:HH:ss");
    System.out.println(format.parse(ACTIVITY_START_TIME_M));
    System.out.println(format.parse(ACTIVITY_END_TIME_M));

    System.out.println(format.parse("2019-1-2 23:59:59"));
  }

}
