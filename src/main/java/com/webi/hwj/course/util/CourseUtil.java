package com.webi.hwj.course.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.course.constant.CourseConstant;
import com.webi.hwj.courseone2one.param.CourseOne2OneParam;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.SubscribeCourseParam;

/**
 * 
 * @ClassName: ClassUtil
 * @Description: 课程在12小时之内
 * @author athrun.cw
 * @date 2015年8月11日 上午10:48:10
 */

public class CourseUtil {
  // 最后可以取消时间：12h = 12*60*60*1000=43200000L
  public static final long CLASSES_MIN_CAN_CANCAL = 43200000L;

  // 上课时间
  public static final long CLASSES_MAX_GOTO_CLASS = 1800000L;

  public final static String[] WEEK = new String[] { "", "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

  public final static String[] EN_WEEK = new String[] { "", "Sun.", "Mon.", "Tues.", "Wed.",
      "Thur.", "Fri.", "Sat." };

  // 页面的课程天数是 14天
  public final static int LESSONS_CYCLE = 14;

  // 可以评论前2天的课程
  public final static int FORWARD_COMMENT_DAYS = 2;

  // 提前2天的时间
  public final static long FORWARD_COMMENT_TIME = 1000L * 3600 * 24 * 2;

  // 预约确认状态值
  public static final int CONFIRM_VAL = 1;

  // 学生预约上课
  public static final int BOOK_VAL = 1;

  // 小包课预约成功的状态值
  public static final int SMALLPACK_SUBSCRIBE = 1;

  public static final int TOPIC_1V1 = 1;
  public static final int TOPIC_SMALLPACK = 2;
  
  /**
   * 
   * Title: 将上课人数转化为上课形式<br>
   * Description: 将上课人数转化为上课形式<br>
   * CreateDate: 2017年2月13日 下午10:02:31<br>
   * 
   * @category 将上课人数转化为上课形式
   * @author seven.gz
   * @param courseTypeLimitNumber
   * @return
   */
  public static String transformLimitNumberToStr(Integer courseTypeLimitNumber) {
    String courseTypeLimitStr = "1v";
    if (courseTypeLimitNumber != null && courseTypeLimitNumber > 0) {
      courseTypeLimitStr += courseTypeLimitNumber;
    } else {
      courseTypeLimitStr += "n";
    }
    return courseTypeLimitStr;
  }

  /**
   * 
   * Title: 在主题课 预约界面，不显示"主题课"<br>
   * Description: formatCourseTypeName<br>
   * CreateDate: 2015年12月19日 下午12:22:14<br>
   * 
   * @category 在主题课 预约界面，不显示"主题课"
   * @author athrun.cw
   * @param category_type_name
   * @return
   */
  public static String formatCourseTypeName(String category_type_name) {
    if (category_type_name == null || "".equals(category_type_name)) {
      return category_type_name;
    }
    switch (category_type_name) {
    case "主题英语job_interview":
      category_type_name = "job_interview";
      break;
    case "主题英语travel":
      category_type_name = "travel";
      break;
    default:
      break;
    }
    return category_type_name;
  }

  /**
   * Title: formatClassTable<br>
   * Description: 首页显示课程表信息 所需要的各种字段util<br>
   * CreateDate: 2015年10月19日 上午10:58:07<br>
   * 
   * @category 首页显示课程表信息 所需要的各种字段util
   * @author athrun.cw
   * @param paramMap
   * @return
   */
  public static SubscribeCourseParam formatClassTable(SubscribeCourseParam paramObj) {

    String course_type = paramObj.getCourseType();
    long beforeLessonCountdown = 12;

    /**
     * modified by komi 2016年7月5日14:00:54 修改课程名字直接读取数据库缓存
     * 这里有一个问题：如果是缓存里没有的课程类型怎么办？是否需要先判断一下缓存是否为空
     */
    paramObj.setCourseTypeChineseName(((CourseType) MemcachedUtil.getValue(course_type)).getCourseTypeChineseName());

    /**
     * 各个时间段显示的状态
     */
    // 右下角图标和订课界面图标规则一致
    long start_time = paramObj.getStartTime().getTime();
    long end_time = paramObj.getEndTime().getTime();

    // 上课前x分钟 可取消预约
    beforeLessonCountdown = ((CourseType) MemcachedUtil.getValue(course_type))
        .getCourseTypeCancelSubscribeTime() * 60 * 1000;
    // 上课开始前x分钟一直到课程结束 都能 进入教室
    long beforeLessonTime = ((CourseType) MemcachedUtil.getValue(course_type))
        .getCourseTypeBeforeGoclassTime() * 60 * 1000;

    /**
     * modified by komi 2016年7月14日14:35:18 增加倒计时时间，提前进入教室的时间
     */
    paramObj.setBeforeLessonCountdown(beforeLessonCountdown);
    paramObj.setBeforeLessonTime(beforeLessonTime);

    long currentTime = System.currentTimeMillis();

    // 现在时间 距离 上课时间间隔
    long time = start_time - currentTime;

    // 状态：可取消预约 && 倒计时 && 进入教室
    if (time > beforeLessonCountdown) {
      // 大于12个小时 可取消预约
      paramObj.setStatus(1);
      // 倒计时时间
//      paramMap.put("countdown", time);
    } else if (time <= beforeLessonCountdown && time > beforeLessonTime) {
      paramObj.setStatus(2);
      // 倒计时时间
//      paramMap.put("countdown", time);
    }
    if (time <= beforeLessonTime && currentTime < end_time) {
      paramObj.setStatus(3);
      // 上课倒计时
//      paramMap.put("go2Class", end_time - currentTime);
    }

    return paramObj;
  }

  /**
   * @Title: isStartTimeWithIn12Hours @Description: 课程是否是12小时之内 @param @param
   * classCourse1v1 @param @return 设定文件 @return boolean 返回类型 @throws
   */
  public static boolean isStartTimeWithIn12Hours(Map<String, Object> classCourse1v1,
      long limitTime) {
    if (classCourse1v1 != null) {
      // 不在12小时之内的，干掉
      long start_time = ((Date) classCourse1v1.get("start_time")).getTime();
      return timeWithIn12Hours(start_time, limitTime);
    }
    return false;
  }

  /**
   * 
   * @Title: timeWithIn12Hours @Description: 在12小时内，true @param @param
   * start_time @param @return 设定文件 @return boolean 返回类型 @throws
   */
  public static boolean timeWithIn12Hours(long start_time, long limitTime) {
    if (start_time - System.currentTimeMillis() <= limitTime) {
      return true;
    }
    return false;
  }

  
  /**
   * 
   * Title: 统计课程对应的状态<br>
   * Description: 统计课程对应的状态<br>
   * CreateDate: 2016年10月14日 下午2:16:57<br>
   * @category 统计课程对应的状态 
   * @author seven.gz
   * @param lastSubscribeCourseList
   * @param one2OneCourseList
   * @param courseType
   * @param rates
   */
  public static void statisticalStatus(List<SubscribeCourse> lastSubscribeCourseList, List<CourseOne2OneParam> one2OneCourseList, 
      CourseType courseType, Map<String, Object> rates){
    Map<String, SubscribeCourse> lastSubscribeCourseTotalMap = new HashMap<String, SubscribeCourse>();
    
    int beforeGoclassTime = courseType.getCourseTypeBeforeGoclassTime(); // 课程开始时间
    int ratesLimit = Integer.parseInt(MemcachedUtil.getConfigValue("tmm_limit_percent")); // 课件进度临界点
    int cancelSubscribeTime = courseType.getCourseTypeCancelSubscribeTime(); // 可取消预约时间
    
    // 遍历预约数据集合，进行行列转换
    for (SubscribeCourse lastSubscribeCourseObj : lastSubscribeCourseList) {
      // 预约id为key
      lastSubscribeCourseTotalMap.put(lastSubscribeCourseObj.getCourseId(),
          lastSubscribeCourseObj);
    }

    // 遍历1v1课集合
    for (CourseOne2OneParam one2OneCourseObj : one2OneCourseList) {
      // 存入课程类型相关信息
      one2OneCourseObj.setCourseTypeChineseName(courseType.getCourseTypeChineseName());
      one2OneCourseObj
          .setCourseTypeCancelSubscribeTime(courseType.getCourseTypeCancelSubscribeTime());
      one2OneCourseObj
          .setCourseTypeBeforeGoclassTime(courseType.getCourseTypeBeforeGoclassTime());

      // 预约处理
      String courseKeyId = one2OneCourseObj.getKeyId();
      SubscribeCourse tempObj = lastSubscribeCourseTotalMap.get(courseKeyId);
      // 该主题课有对应的预约数据，需要赋值
      if (tempObj != null) {
        one2OneCourseObj.setSubscribeStartTime(tempObj.getStartTime());
        one2OneCourseObj.setSubscribeEndTime(tempObj.getEndTime());
        one2OneCourseObj.setSubscribeId(tempObj.getKeyId());
        one2OneCourseObj.setCourseStatus(tempObj.getSubscribeStatus());
      }

      // RSA进度处理
      Map<String, Object> percentMap = (Map<String, Object>) rates
          .get(one2OneCourseObj.getCourseRsaTitle() + "," + one2OneCourseObj.getCourseLevel());

      // 当前课程使用title匹配rsa，如果没有数据，则直接返回不能预约
      if (percentMap == null) {
        one2OneCourseObj.setRsaRate(0);
        one2OneCourseObj.setCourseStatus(CourseConstant.COURSE_STATUS_CAN_NOT_SUBSCRIBE);
        continue;
      }

      // 获取该课程RSA进度
      String percent = percentMap.get("tmm_percent") + "";
      int courseRate = "null".equals(percent) ? 0 : Integer.parseInt(percent);
      one2OneCourseObj.setRsaRate(courseRate);

      // 处理各个课程的展示状态
      // 预约状态
      Object statusObj = one2OneCourseObj.getCourseStatus();
      // 预约表中 没有有效的预约记录，说明该课程是没有预约过的，一旦适合tmm完成进度，就可以预约
      if (statusObj == null) {
        // 没被预约
        // tmm进度 >= 系统限制最小可预约进度，可以正常的预约
        if (courseRate >= ratesLimit) {
          // 完成进度大于等于标准值 可预约
          one2OneCourseObj.setCourseStatus(CourseConstant.COURSE_STATUS_CAN_SUBSCRIBE);
        } else {
          // 完成进度小于标准值 可预约
          one2OneCourseObj.setCourseStatus(CourseConstant.COURSE_STATUS_CAN_NOT_SUBSCRIBE);
        }
      } else {
        // 课程已经有该学员的预约记录
        Date start = one2OneCourseObj.getSubscribeStartTime();
        Date end = one2OneCourseObj.getSubscribeEndTime();

        long startTime = start.getTime();
        long time = startTime - System.currentTimeMillis();
        if (time > cancelSubscribeTime * 60 * 1000) {
          // 2.1 大于cancelSubscribeTime个小时 可取消预约
          one2OneCourseObj.setCourseStatus(CourseConstant.COURSE_STATUS_CAN_CANCEL_SUBSCRIBE);
        } else if (time <= cancelSubscribeTime * 60 * 1000
            && time > beforeGoclassTime * 60 * 1000) {
          // 2.2 倒计时
          one2OneCourseObj.setCourseStatus(CourseConstant.COURSE_STATUS_COUNT_DOWN);
        } else if (time < beforeGoclassTime * 60 * 1000
            && System.currentTimeMillis() < end.getTime()) {
          // 2.3 上课开始前beforeGoclassTime分钟一直到课程结束 都能 进入教室
          one2OneCourseObj.setCourseStatus(CourseConstant.COURSE_STATUS_GO_TO_CLASS);
        } else if (System.currentTimeMillis() >= end.getTime()) { // 课程结束
          // 2.4 已上课,未出席,(需要微立方的回执),1:已上课,已出席,(需要微立方的回执)
          int status = Integer.parseInt(statusObj.toString());
          if (status == 1) {
            // 2.4.1 已出席
            one2OneCourseObj.setCourseStatus(CourseConstant.COURSE_STATUS_SHOW);
          } else if (status == 0) {
            // 2.4.2 未出席
            one2OneCourseObj.setCourseStatus(CourseConstant.COURSE_STATUS_NO_SHOW);
          }
        }
      }
    }
  }
  
}
