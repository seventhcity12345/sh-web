package com.webi.hwj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mingyisoft.javabase.util.DateUtil;

public class CalendarUtil {

  /**
   * Title: 格式化教师微信推送课程时间<br>
   * Description: 按tom要求，一定要弄个特别复杂的时间格式<br>
   * CreateDate: 2017年6月16日 下午4:37:57<br>
   * 
   * @category formatTeacherWechatCourseTime
   * @author komi.zsy
   * @param startTime
   *          课程开始时间
   * @param endTime
   *          课程结束时间
   * @return
   */
  public static String formatTeacherWechatCourseTime(Date startTime, Date endTime) {
    String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
        "Saturday" };
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startTime);
    String time = DateUtil.dateToStr(startTime, "MM/dd") + "(" + days[calendar.get(
        Calendar.DAY_OF_WEEK) - 1] + ")"
        + DateUtil.dateToStr(startTime, "HH:mm") + "-" + DateUtil.dateToStr(endTime, "HH:mm");
    return time;
  }

  /**
   * Title: 字符串转Calendar格式<br>
   * Description: 字符串转Calendar格式<br>
   * CreateDate: 2016年5月4日 下午3:13:40<br>
   * 
   * @category 字符串转Calendar格式
   * @author komi.zsy
   * @param dateStr
   * @return
   */
  public static Calendar parseCalendar(String dateStr) {
    // yyyy-MM-dd HH:mm:ss
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtil.strToDateYYYYMMDDHHMMSS(dateStr));

    return calendar;
  }

  /**
   * Title: 计算两个日期相差的天数(向上取整)<br>
   * Description: 计算两个日期相差的天数<br>
   * CreateDate: 2016年9月20日 上午11:42:42<br>
   * 
   * @category 计算两个日期相差的天数
   * @author komi.zsy
   * @param endCal
   *          结束日期
   * @param startCal
   *          开始日期
   * @return 相差的天数
   */
  public static int getMinusDay(Date endCal, Date startCal) {
    return (int) Math
        .ceil((endCal.getTime() - startCal.getTime()) / (double) (1000 * 60 * 60 * 24));
  }

  /**
   * 
   * Title: 获取当月第一天<br>
   * Description: 获取当月第一天<br>
   * CreateDate: 2016年6月1日 下午9:48:33<br>
   * 
   * @category 获取当月第一天
   * @author seven.gz
   * @return
   */
  public static Date getFirstDayOfMonth() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    // 将小时至0
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    // 将分钟至0
    calendar.set(Calendar.MINUTE, 0);
    // 将秒至0
    calendar.set(Calendar.SECOND, 0);
    // 将毫秒至0
    calendar.set(Calendar.MILLISECOND, 0);
    // 获得当前月第一天
    Date sdate = calendar.getTime();
    return sdate;
  }

  /**
   * 
   * Title: 获取当月第一天<br>
   * Description: 获取当月第一天<br>
   * CreateDate: 2016年6月1日 下午9:48:33<br>
   * 
   * @category 获取当月第一天
   * @author seven.gz
   * @return
   */
  public static Date getFirstDayOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    // 将小时至0
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    // 将分钟至0
    calendar.set(Calendar.MINUTE, 0);
    // 将秒至0
    calendar.set(Calendar.SECOND, 0);
    // 将毫秒至0
    calendar.set(Calendar.MILLISECOND, 0);
    // 获得当前月第一天
    Date sdate = calendar.getTime();
    return sdate;
  }

  /**
   * 
   * Title: 获取当月第一天<br>
   * Description: 获取当月第一天<br>
   * CreateDate: 2016年6月1日 下午9:48:33<br>
   * 
   * @category 获取当月第一天
   * @author seven.gz
   * @return
   */
  public static Date getFirstDayOfNextMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    // 将小时至0
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    // 将分钟至0
    calendar.set(Calendar.MINUTE, 0);
    // 将秒至0
    calendar.set(Calendar.SECOND, 0);
    // 将毫秒至0
    calendar.set(Calendar.MILLISECOND, 0);
    // 获得当前月第一天
    Date sdate = calendar.getTime();
    return sdate;
  }

  /**
   * 
   * Title: 得到给定时间下n个月的时间<br>
   * Description: 得到给定时间下n个月的时间<br>
   * CreateDate: 2016年8月30日 下午2:58:44<br>
   * 
   * @category 得到给定时间下n个月的时间
   * @author seven.gz
   * @param date
   * @param n
   * @return
   */
  public static Date getNextNMonth(Date date, int n) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + n);
    return calendar.getTime();
  }

  /**
   * 
   * Title: 得到给定时间下n天的时间<br>
   * Description: 得到给定时间下n天的时间<br>
   * CreateDate: 2016年8月30日 下午2:58:44<br>
   * 
   * @category 得到给定时间下n天的时间
   * @author seven.gz
   * @param date
   * @param n
   * @return
   */
  public static Date getNextNDay(Date date, int n) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + n);
    return calendar.getTime();
  }

  /**
   * 
   * Title: 获取所给时间分钟数<br>
   * Description: getMinute<br>
   * CreateDate: 2017年6月26日 下午8:37:14<br>
   * 
   * @category 获取所给时间分钟数
   * @author seven.gz
   * @param date
   * @return
   */
  public static int getMinute(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.MINUTE);
  }

  /**
   * 
   * Title: 获取某月的最后一天<br>
   * Description: 获取某月的最后一天<br>
   * CreateDate: 2017年7月19日 下午3:08:35<br>
   * 
   * @category 获取某月的最后一天(返回String为了便于拼接)
   * @author felix.yl
   * @param year
   * @param month
   * @return
   * @throws ParseException
   */
  public static String getLastDayOfMonth(int year, int month) {

    Calendar cal = Calendar.getInstance();
    // 设置年份
    cal.set(Calendar.YEAR, year);
    // 设置月份
    cal.set(Calendar.MONTH, month - 1);
    // 获取某月最大天数
    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    // 设置日历中月份的最大天数
    cal.set(Calendar.DAY_OF_MONTH, lastDay);
    // 格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String lastDayOfMonth = sdf.format(cal.getTime());

    return lastDayOfMonth;
  }

  /**
   * 
   * Title: 获取某月的第一天<br>
   * Description: 获取某月的第一天<br>
   * CreateDate: 2017年7月19日 下午3:08:35<br>
   * 
   * @category 获取某月的第一天(返回String为了便于拼接)
   * @author felix.yl
   * @param year
   * @param month
   * @return
   * @throws ParseException
   */
  public static String getFirstDayOfMonth(int year, int month) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month - 1);
    cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
    // 格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String firstDayOfMonth = sdf.format(cal.getTime());
    return firstDayOfMonth;
  }

}
