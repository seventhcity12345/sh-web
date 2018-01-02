package com.webi.hwj.courseone2one.constant;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursetype.entity.CourseType;

/**
 * Title: 1v1排课常量<br>
 * Description: 1v1排课常量<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月4日 下午3:19:58
 * 
 * @author komi.zsy
 */
public class CourseOneToOneSchedulingConstant {

  /**
   * 1V1自动排课开始时间，6点开始上课
   * 
   * @author komi.zsy
   */
  public static final String CLASS_START_TIME_BY_DAY = "06:00:00";

  /**
   * 1v1排课一天内排课几小时：小时（6点排到24点）
   * 
   * @author komi.zsy
   */
  public static final int CLASS_HOUR_BY_DAY = 24 - 6;

  /**
   * 课前预留时间：分钟（目前是5分钟）
   */
  public static int getVcubeRoomClassFrontTime(String courseType) throws Exception {
    return ((CourseType) MemcachedUtil.getValue(courseType)).getCourseTypeBeforeGoclassTime();
  }

  /**
   * 1v1上课时间:分钟（每节课配置上课时间（目前是25分钟））
   * 
   * @author komi.zsy
   */
  public static int getVcubeRoomClassTime() throws Exception {
    return ((CourseType) MemcachedUtil.getValue("course_type1")).getCourseTypeDuration();
  }

  /**
   * 课后预留时间：分钟（目前是0分钟）
   */
  public static int getVcubeRoomClassPostTime(String courseType) throws Exception {
    return ((CourseType) MemcachedUtil.getValue(courseType)).getCourseTypeAfterGoclassTime();
  }

  /**
   * 1v1排课时间力度:分钟（每隔30分钟排一次课）
   * 
   * @author komi.zsy
   */
  public static int getClassMinuteByClass() throws Exception {
    return Integer
        .parseInt(MemcachedUtil.getConfigValue("course_one2one_scheduling_time_interval"));
  }
}
