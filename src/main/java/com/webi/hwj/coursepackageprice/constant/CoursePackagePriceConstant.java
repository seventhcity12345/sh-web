package com.webi.hwj.coursepackageprice.constant;

import com.mingyisoft.javabase.util.MemcachedUtil;

/**
 * Title: 价格政策<br>
 * Description: 价格政策<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年8月31日 下午3:01:30
 * 
 * @author komi.zsy
 */
public class CoursePackagePriceConstant {

  // 兑换码一键兑换合同专用价格政策id
  public static String getRedeemCodeCoursePackagePriceId() throws Exception {
    return MemcachedUtil.getConfigValue("redeem_course_package_price_id");
  }
  
  
  /**
   * 线上线下是否显示（0线上显示）
   */
  public static final String PACKAGE_PRICE_ONLINE_TYPE = "0";
  
  /**
   * 线上线下是否显示（1线下显示）
   */
  public static final String PACKAGE_PRICE_LINE_TYPE = "1";
}
