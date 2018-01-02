package com.webi.hwj.user.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursetype.entity.CourseType;

/**
 * 
 * Title: 将用户 合同中保存的体系类别，转换为后台管理 页面中需要显示的合同类别<br>
 * Description: UserCategoryTypeUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年11月24日 下午2:56:33
 * 
 * @author athrun.cw
 */
public class UserCategoryTypeUtil {
  private static Logger logger = Logger.getLogger(UserCategoryTypeUtil.class);

  /**
   * 
   * Title: 课程体系&& 课程类别 && 状态 转换为 页面显示的格式内容 <br>
   * Description: formatCategoryTypeAndCourseTypeAndSubscribeStatus<br>
   * CreateDate: 2015年11月29日 下午4:49:53<br>
   * 
   * @category 课程体系&& 课程类别 && 状态 转换为 页面显示的格式内容
   * @author athrun.cw
   * @param userInformationsList
   * @return
   */
  public static List<Map<String, Object>> formatCategoryTypeAndCourseTypeAndSubscribeStatus(
      List<Map<String, Object>> userInformationsList) {
    if (userInformationsList == null || userInformationsList.size() == 0) {
      return userInformationsList;
    }
    for (Map<String, Object> userInfo : userInformationsList) {
      String subscribeStatus = userInfo.get("subscribe_status").toString();
      switch (subscribeStatus) {
      case "0":
        userInfo.put("format_subscribe_status", "未出席");
        break;
      case "1":
        userInfo.put("format_subscribe_status", "已出席");
        break;
      default:
        break;
      }
    }
    return formatCategoryTypeAndCourseType(userInformationsList);
  }

  /**
   * 
   * Title: 课程体系&& 课程类别 转换为 页面显示的格式内容 <br>
   * Description: formatCategoryTypeAndCourseType<br>
   * CreateDate: 2015年11月29日 下午4:49:37<br>
   * 
   * @category 课程体系&& 课程类别 转换为 页面显示的格式内容
   * @author athrun.cw
   * @param userInformationsList
   * @return
   */
  public static List<Map<String, Object>> formatCategoryTypeAndCourseType(
      List<Map<String, Object>> userInformationsList) {
    if (userInformationsList == null || userInformationsList.size() == 0) {
      return userInformationsList;
    }

    // 课程类别 转换为 页面显示的格式内容
    for (Map<String, Object> userInfo : userInformationsList) {
      String courseType = userInfo.get("course_type").toString();
      switch (courseType) {
      /**
       * course_type1 课程类别：1v1 && course_type3 课程类别：主题课 1对1 和 小包课 都显示：一对一
       */
      case "course_type1":
      case "course_type3":
        userInfo.put("format_course_type",
            ((CourseType) MemcachedUtil.getValue("course_type1")).getCourseTypeChineseName());
        break;
      default:
        userInfo.put("format_course_type",
            ((CourseType) MemcachedUtil.getValue(courseType)).getCourseTypeChineseName());
        break;
      }
    }
    // 课程体系 转换为 页面显示的格式内容
    return formatCategoryType2OrderType(userInformationsList);
    // return userInformationsList;
  }

  /**
   * 
   * Title: 将用户 合同中保存的体系类别，转换为后台管理页面中需要显示的合同类别<br>
   * Description: formatCategoryType2OrderType<br>
   * CreateDate: 2015年11月24日 下午3:00:03<br>
   * 
   * @category 将用户 合同中保存的体系类别，转换为后台管理页面中需要显示的合同类别
   * @author athrun.cw
   * @param userInformationsList
   * @return
   */
  public static List<Map<String, Object>> formatCategoryType2OrderType(
      List<Map<String, Object>> userInformationsList) {
    if (userInformationsList == null || userInformationsList.size() == 0) {
      return userInformationsList;
    }

    try {
      for (Map<String, Object> userInfo : userInformationsList) {
        String categoryType = userInfo.get("category_type") + "";
        userInfo.put("order_type", MemcachedUtil.getValue(categoryType));
      }
    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
    }
    return userInformationsList;
  }
}
