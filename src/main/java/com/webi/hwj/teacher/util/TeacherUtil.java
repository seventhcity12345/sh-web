package com.webi.hwj.teacher.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.mingyisoft.javabase.util.DateUtil;

/**
 * Title: TeacherUtil<br>
 * Description: TeacherUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月27日 下午4:43:35
 * 
 * @author komi.zsy
 */
public class TeacherUtil {
  /**
   * Title: 查看教师选择的签课时间是否合法<br>
   * Description: checkTimeIsInvalid<br>
   * CreateDate: 2016年4月27日 下午4:45:24<br>
   * 
   * @category checkTimeIsInvalid
   * @author komi.zsy
   * @param startTime
   * @param endTime
   * @return
   * @throws ParseException
   */
  public static boolean checkTimeIsInvalid(String startTime, String endTime) throws ParseException {
    // 时间不能为空
    if (endTime != null && startTime != null) {
      Date endDate = DateUtil.strToDateYYYYMMDDHHMMSS(endTime);
      Date startDate = DateUtil.strToDateYYYYMMDDHHMMSS(startTime);
      long endTimeLong = endDate.getTime();
      long startTimeLong = startDate.getTime();
      // 结束时间大于开始时间
      if (endTimeLong > startTimeLong) {
        Calendar cal = Calendar.getInstance();
        // 结束时间不能在0-6点间
        cal.setTime(endDate);
        if (cal.get(Calendar.HOUR_OF_DAY) >= 6
            || (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0)) {
          // 开始时间不能小于6点
          cal.setTime(startDate);
          if (cal.get(Calendar.HOUR_OF_DAY) >= 6) {
            // 时间效验成功，可以查询是否可以签课
            return true;
          }
        }
      }
    }
    return false;
  }
}
