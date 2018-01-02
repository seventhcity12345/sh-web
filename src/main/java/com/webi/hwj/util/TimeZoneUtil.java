package com.webi.hwj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * Title: 时区转换util<br>
 * Description: TimeZoneUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月3日 下午2:52:14
 * 
 * @author athrun.cw
 */
public class TimeZoneUtil {

  private static long MILLIS_2_HOUR = 1000 * 60 * 60;

  public static void main(String[] args) {
    System.out.println("当前您所在的时区：" + getDefaultTimeZone());
    System.out.println("当前您所在的时区与UTC的时差：" + getDefaultTimeZoneRawOffset() / MILLIS_2_HOUR);
    System.out.println("Europe/Madrid: " + getTimeZoneRawOffset("Europe/Madrid") / MILLIS_2_HOUR);
    System.out.println("MIT: " + getTimeZoneRawOffset("MIT") / MILLIS_2_HOUR);
    int i = 1;
    for (String timeZone : getAllTimeZone()) {
      System.out.println("时区" + i + ": " + timeZone);
      i++;
    }
  }

  /**
   * 
   * Title: 获取所有的时区id<br>
   * Description: getAllTimeZone<br>
   * CreateDate: 2016年3月3日 下午4:40:40<br>
   * 
   * @category 获取所有的时区id
   * @author athrun.cw
   * @return
   */
  static String[] getAllTimeZone() {
    return TimeZone.getAvailableIDs();
  }

  /**
   * 
   * Title: 获取默认的时区<br>
   * Description: getDefaultTimeZone<br>
   * CreateDate: 2016年3月3日 下午3:02:05<br>
   * 
   * @category 获取默认的时区
   * @author athrun.cw
   * @return
   */
  public static String getDefaultTimeZone() {
    return TimeZone.getDefault().getID();
  }

  /**
   * 
   * Title: 获取当前默认时区与UTC的时间差（毫秒）<br>
   * Description: getDefaultTimeZoneRawOffset<br>
   * CreateDate: 2016年3月3日 下午2:52:41<br>
   * 
   * @category 获取当前默认时区与UTC的时间差 （毫秒）
   * @author athrun.cw
   * @return
   */
  static int getDefaultTimeZoneRawOffset() {
    return TimeZone.getDefault().getRawOffset();
  }

  /**
   * 
   * Title: 获取指定时区与UTC的时间差（毫秒）<br>
   * Description: getTimeZoneRawOffset<br>
   * CreateDate: 2016年3月3日 下午2:57:03<br>
   * 
   * @category 获取指定时区与UTC的时间差 （毫秒）
   * @author athrun.cw
   * @param timeZoneId
   * @return
   */
  private static int getTimeZoneRawOffset(String timeZoneId) {
    return TimeZone.getTimeZone(timeZoneId).getRawOffset();
  }

  /**
   * 
   * Title: 获取当前<br>
   * Description: getMinusTimeZoneRawOffset<br>
   * CreateDate: 2016年3月3日 下午3:01:20<br>
   * 
   * @category getMinusTimeZoneRawOffset
   * @author athrun.cw
   * @param timeZoneId
   * @return
   */
  public static int getMinusTimeZoneRawOffset(String timeZoneId) {
    return TimeZone.getDefault().getRawOffset() - TimeZone.getTimeZone(timeZoneId).getRawOffset();
  }

  public static String string2TimezoneDefault(String srcDateTime, String dstTimeZoneId)
      throws ParseException {
    return string2Timezone("yyyy-MM-dd HH:mm:ss", srcDateTime, "yyyy-MM-dd HH:mm:ss",
        dstTimeZoneId);
  }

  public static String string2Timezone(String srcFormater, String srcDateTime, String dstFormater,
      String dstTimeZoneId) throws ParseException {
    if (srcFormater == null || "".equals(srcFormater))
      return null;
    if (srcDateTime == null || "".equals(srcDateTime))
      return null;
    if (dstFormater == null || "".equals(dstFormater))
      return null;
    if (dstTimeZoneId == null || "".equals(dstTimeZoneId))
      return null;
    SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
    int diffTime = getMinusTimeZoneRawOffset(dstTimeZoneId);
    Date d = sdf.parse(srcDateTime);
    long nowTime = d.getTime();
    long newNowTime = nowTime - diffTime;
    d = new Date(newNowTime);
    return sdf.format(d);
  }
}
