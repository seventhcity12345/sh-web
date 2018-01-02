package com.webi.hwj.user.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mingyisoft.javabase.util.DateUtil;

public class AdminUserDateUtil {

  /**
   * 
   * Title: 获取某天中：最小时刻 和 最大时刻<br>
   * Description: addThisDayMinAdnMaxTime<br>
   * CreateDate: 2015年11月26日 下午3:43:17<br>
   * 
   * @category 获取某天中：最小时刻 和 最大时刻
   * @author athrun.cw
   * @param date_time
   * @return
   */
  public static Map<String, Object> addThisDayMinAdnMaxTime(long date_time) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(date_time);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    // int hour = calendar.get(Calendar.HOUR);//得到小时
    // int minute = calendar.get(Calendar.MINUTE);//得到分钟
    // int second = calendar.get(Calendar.SECOND);//得到秒
    String thisDayMinTime = year + "-" + month + "-" + day + " 00:00:00";
    /**
     * modified by komi 2017年4月24日11:01:17
     * 修改成当前时间之后的才要查询
     */
    if(new Date().getTime() > DateUtil.strToDateYYYYMMDDHHMMSS(thisDayMinTime).getTime()){
      thisDayMinTime = DateUtil.dateToStrYYMMDDHHMMSS(new Date());
    }
    String thisDayMaxTime = year + "-" + month + "-" + day + " 23:59:59";
    Map<String, Object> dayMinAndMaxTimeMap = new HashMap<String, Object>();
    dayMinAndMaxTimeMap.put("thisDayMinTime", thisDayMinTime);
    dayMinAndMaxTimeMap.put("thisDayMaxTime", thisDayMaxTime);

    return dayMinAndMaxTimeMap;
  }

  /**
   * 
   * Title: 获取当月中起点时间 <br>
   * Description: getCurrentMonthMinTime<br>
   * CreateDate: 2015年11月19日 下午8:41:13<br>
   * 
   * @category 获取当月中起点时间
   * @author athrun.cw
   * @throws Exception
   */
  public static Date getCurrentMonthMinTime() throws Exception {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    String currentMonthMinTime = year + "-" + month + "-01" + " 00:00:00";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return simpleDateFormat.parse(currentMonthMinTime);
  }

  /**
   * 
   * Title: 获取当月中起点时间(String)<br>
   * Description: getCurrentMonthMinTime2String<br>
   * CreateDate: 2015年11月20日 上午11:54:06<br>
   * 
   * @category 获取当月中起点时间(String)
   * @author athrun.cw
   * @return
   * @throws Exception
   */
  public static String getCurrentMonthMinTime2String() throws Exception {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    String currentMonthMinTime = year + "-" + month + "-01" + " 00:00:00";
    return currentMonthMinTime;
  }

  public static void main(String[] args) throws Exception {
    // System.out.println(getCurrentMonthMinTime());
    // double rundom = Math.random();
    // System.out.println(rundom * 9 + 1);
    // System.out.println((rundom * 9 + 1) * 100000);

    String randomNumber = String.valueOf((Math.random() * 9 + 1) * 100000);
    System.out.println(randomNumber);
    System.out.println(randomNumber.substring(0, 6));
  }

  /**
   * 
   * Title: 获得两个日期的天数差值<br>
   * Description: 获得两个日期的天数差值<br>
   * CreateDate: 2016年3月31日 上午11:10:17<br>
   * 
   * @category 获得两个日期的天数差值
   * @author seven.gz
   * @param cla1
   *          小日期
   * @param cla2
   *          大日期
   * @return
   */
  public static int calculationDaysBetweenTwoDate(Date date1, Date date2) {
    long time1 = date1.getTime();
    long time2 = date2.getTime();
    long between_days = (time2 - time1) / (1000 * 3600 * 24);
    return Integer.parseInt(String.valueOf(between_days));
  }
}
