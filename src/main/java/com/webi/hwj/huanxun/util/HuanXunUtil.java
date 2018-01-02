package com.webi.hwj.huanxun.util;

import java.util.Date;

public class HuanXunUtil {

  /**
   * 
   * Title: 判断两个时间段端是否有交集<br>
   * Description: 判断两个时间段端是否有交集<br>
   * CreateDate: 2016年8月10日 下午5:37:23<br>
   * 
   * @category 判断两个时间段端是否有交集
   * @author seven.gz
   * @param firstStartTime
   * @param firstEndTime
   * @param secondStartTime
   * @param secondEndTime
   * @return
   */
  public static boolean timeHaveIntersection(Date firstStartTime, Date firstEndTime,
      Date secondStartTime, Date secondEndTime) {
    long firstStartTimeLong = firstStartTime.getTime();
    long firstEndTimeLong = firstEndTime.getTime();

    long secondStartTimeLong = secondStartTime.getTime();
    long secondEndTimeLong = secondEndTime.getTime();

    boolean returnFlag = false;
    // 第一个时间段的开始时间在，在第二个时间段内
    if (firstStartTimeLong > secondStartTimeLong && firstStartTimeLong < secondEndTimeLong) {
      returnFlag = true;
    }
    // 第一个时间段的结束时间在，在第二个时间段内
    if (firstEndTimeLong > secondStartTimeLong && firstEndTimeLong < secondEndTimeLong) {
      returnFlag = true;
    }
    // 第二个时间段在，在第一个时间段内
    if (firstStartTimeLong <= secondStartTimeLong && firstEndTimeLong >= secondEndTimeLong) {
      returnFlag = true;
    }

    return returnFlag;
  }
}
