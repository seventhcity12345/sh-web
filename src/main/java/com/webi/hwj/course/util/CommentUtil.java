/** 
 * File: CommentUtil.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.course.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年9月1日 下午3:59:30
 * @author athrun.cw
 */
package com.webi.hwj.course.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.coursetype.param.CourseTypeInfo;

/**
 * Title: CommentUtil<br>
 * Description: CommentUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月1日 下午3:59:30
 * 
 * @author athrun.cw
 */
public class CommentUtil {

  private static final double ONE_YEAR_MILLIS = 31104000000D;
  private static final double ONE_DAY_MILLIS = 86400000D;
  private static final double ONE_HOUR_MILLIS = 3600000D;
  private static final double ONE_MINUTE_MILLIS = 60000D;

  /**
   * 
   * Title: 根据预约数据中的course_type字段，区分 1:1v1;2:1vn;3:主题课（老师端：英文显示）<br>
   * Description: transCategoryType4Teacher<br>
   * CreateDate: 2015年9月28日 下午5:48:56<br>
   * 
   * @category 根据预约数据中的course_type字段，区分1:1v1;2:1vn;3:主题课
   * @author athrun.cw
   * @param paramMap
   * @return
   */
  public static Map<String, Object> transCategoryType4Teacher(
      Map<String, Object> paramMap) {
    String course_type = paramMap.get("course_type").toString();
    // modify by seven 2016年12月7日14:52:19 前端台复杂 不敢乱改 先增加个属性自己用
    paramMap.put("course_type_code", course_type);
    paramMap.put("course_type",
        ((CourseType) MemcachedUtil.getValue(course_type)).getCourseTypeEnglishName());
    return paramMap;
  }

  /**
   * 
   * Title: 根据预约数据中的course_type字段，区分 1:1v1;2:1vn;3:主题课(学生端：中文显示)<br>
   * Description: transCategoryType4User<br>
   * CreateDate: 2015年9月28日 下午5:48:56<br>
   * 
   * @category 根据预约数据中的course_type字段，区分1:1v1;2:1vn;3:主题课
   * @author athrun.cw
   * @param paramMap
   * @return
   */
  public static Map<String, Object> transCategoryType4User(
      Map<String, Object> paramMap) {
    String course_type = paramMap.get("course_type").toString();

    /**
     * modified by komi 2016年7月5日14:00:54 修改课程名字直接读取数据库缓存
     * 这里有一个问题：如果是缓存里没有的课程类型怎么办？是否需要先判断一下缓存是否为空
     */
    paramMap.put("course_type", course_type);
    paramMap.put("course_type_chinese_name",
        ((CourseType) MemcachedUtil.getValue(course_type)).getCourseTypeChineseName());
    return paramMap;
  }

  /**
   * 
   * Title: 将分数转换为Double类型展示 && 保存<br>
   * Description: ShowScore2DoubleType<br>
   * CreateDate: 2015年9月2日 下午2:39:35<br>
   * 
   * @category ShowScore2DoubleType
   * @author athrun.cw
   * @param paramMap
   * @return
   */
  public static Map<String, Object> showScore2DoubleType(Map<String, Object> paramMap) {
    paramMap.put("show_score", Double.parseDouble(paramMap.get("show_score").toString()) + "");
    return paramMap;
  }

  /**
   * 
   * Title: 将评论的时间，按照一定规则显示（老师端）<br>
   * Description: formatDate4Comment2Teacher<br>
   * CreateDate: 2015年11月27日 下午5:35:14<br>
   * 
   * @category 将评论的时间，按照一定规则显示（老师端）
   * @author athrun.cw
   * @param comment
   * @return
   */
  public static Map<String, Object> formatDate4Comment2Teacher(Map<String, Object> comment) {
    if (comment == null) {
      return comment;
    }
    // 最后评论时间(超过一年的)
    Date update_time = (Date) comment.get("update_date");
    double now_time = System.currentTimeMillis();
    double differ = now_time - update_time.getTime();
    // 小于一年的
    if (differ <= ONE_YEAR_MILLIS) {
      // 一天之内的
      if (differ <= ONE_DAY_MILLIS) {
        // 24小时之内
        if (differ <= ONE_HOUR_MILLIS) {
          if (differ < ONE_MINUTE_MILLIS) {
            comment.put("lastAccessTime", "just now");
          } else {
            comment.put("lastAccessTime", (int) (differ / 1000 / 60 % 60) + "minutes ago");
          }
        } else {
          comment.put("lastAccessTime", (int) (differ / 1000 / 60 / 60 % 24) + "hours ago");
        }
      } else {
        comment.put("lastAccessTime", new SimpleDateFormat("MM-dd HH:mm").format(update_time));
      }
    } else {
      // Calendar.getInstance().setTime(update_time)CommentUtil.
      comment.put("lastAccessTime", "years ago");
    }
    // 将show_score变为double类型
    showScore2DoubleType(comment);
    return comment;
  }

  /**
   * 
   * Title: 将评论的时间，按照一定规则显示(学生端)<br>
   * Description: formatDate2Comment<br>
   * CreateDate: 2015年9月1日 下午4:19:10<br>
   * 
   * @category formatDate2Comment
   * @author athrun.cw
   * @param update_date
   * @return
   */
  public static Map<String, Object> formatDate4Comment(Map<String, Object> comment) {
    if (comment == null) {
      return comment;
    }
    // 最后评论时间(超过一年的)
    Date update_time = (Date) comment.get("update_date");
    double now_time = System.currentTimeMillis();
    double differ = now_time - update_time.getTime();
    // 小于一年的
    if (differ <= ONE_YEAR_MILLIS) {
      // 一天之内的
      if (differ <= ONE_DAY_MILLIS) {
        // 24小时之内
        if (differ <= ONE_HOUR_MILLIS) {
          if (differ < ONE_MINUTE_MILLIS) {
            comment.put("lastAccessTime", "刚刚");
          } else {
            comment.put("lastAccessTime", (int) (differ / 1000 / 60 % 60) + "分钟前");
          }
        } else {
          comment.put("lastAccessTime", (int) (differ / 1000 / 60 / 60 % 24) + "小时前");
        }
      } else {
        comment.put("lastAccessTime", new SimpleDateFormat("MM月dd日 HH:mm").format(update_time));
      }
    } else {
      // Calendar.getInstance().setTime(update_time)CommentUtil.
      comment.put("lastAccessTime", "一年前");
    }
    // 将show_score变为double类型
    showScore2DoubleType(comment);
    return comment;
  }

  public static void main(String[] args) {

    // System.out.println(syso / 1000 / 60 / 60 % 24);

    System.out.println(60 * 60 * 1000);
    System.out.println(24 * 60 * 60 * 1000);
    System.out.println(12 * 30 * 24 * 6 * 6);

  }

  /**
   * 
   * Title: 判断用户此种类型的courseType是否在合法的时间内<br>
   * Description: checkCourseTypeLimitTime<br>
   * CreateDate: 2016年8月31日 上午11:47:08<br>
   * 
   * @category checkCourseTypeLimitTime
   * @author seven.gz
   * @param courseTypeInfo
   * @return
   */
  public static boolean checkCourseTypeLimitTime(CourseTypeInfo courseTypeInfo) {
    // 判断用户的这种类型是按什么上的
    // 如果按时间上，并且没有按节上的
    // 查看是否允许在允许的
    boolean checkFlag = true;
    if (courseTypeInfo != null) {
      if (courseTypeInfo.getLimitTime() != null) {
        if (new Date().getTime() > courseTypeInfo.getLimitTime().getTime()) {
          checkFlag = false;
        }
      } else {
        checkFlag = false;
      }
    } else {
      checkFlag = false;
    }
    return checkFlag;
  }
}
