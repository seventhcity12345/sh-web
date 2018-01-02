package com.webi.hwj.course.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursetype.entity.CourseType;

/**
 * 
 * Title: 微信端orderCourse工具类<br>
 * Description: WeixinCourseUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月3日 上午10:39:27
 * 
 * @author athrun.cw
 */
public class WeixinCourseUtil {
  /**
   * 
   * Title: 学员在PC端预约过的1v1课程在微信端课程列表应该消失<br>
   * Description: filterHaveSubscribedCourse<br>
   * CreateDate: 2016年5月26日 下午3:52:25<br>
   * 
   * @category 学员在PC端预约过的1v1课程在微信端课程列表应该消失
   * @author athrun.cw
   * @param courseMap
   * @return
   */
  public static List<Map<String, Object>> filterHaveSubscribedCourse(
      List<Map<String, Object>> courseMap) {
    if (courseMap == null || courseMap.size() == 0) {
      return null;
    }
    List<Map<String, Object>> returnMapList = new ArrayList<Map<String, Object>>();
    for (Map<String, Object> infoMap : courseMap) {
      // 没被预约的 才在微信端显示
      if (infoMap.get("subscribe_status") == null) {
        returnMapList.add(infoMap);
      }
    }
    return returnMapList;
  }

  /**
   * Title: 累加赠送课时format<br>
   * Description: formatCourseType<br>
   * CreateDate: 2016年6月28日 下午8:47:50<br>
   * 
   * @category 累加赠送课时format
   * @author caowei
   * @param orderCourseOptionMapList
   * @return
   */
  public static List<Map<String, Object>> formatCourseType(
      List<Map<String, Object>> orderCourseOptionMapList) {
    if (orderCourseOptionMapList == null || orderCourseOptionMapList.size() == 0) {
      return null;
    }
    List<Map<String, Object>> formatCourseTypeList = new ArrayList<Map<String, Object>>();
    Map<String, Object> reCourseType1Map = new HashMap<String, Object>();
    reCourseType1Map.put("course_type", "course_type1");
    reCourseType1Map.put("course_type_name",
        ((CourseType) MemcachedUtil.getValue("course_type1")).getCourseTypeChineseName());

    Map<String, Object> reCourseType2Map = new HashMap<String, Object>();
    reCourseType2Map.put("course_type", "course_type2");
    reCourseType2Map.put("course_type_name",
        ((CourseType) MemcachedUtil.getValue("course_type2")).getCourseTypeChineseName());

    int totalCourseType1Count = 0;
    int totalCourseType2Count = 0;
    for (Map<String, Object> orderCourseOptionMap : orderCourseOptionMapList) {
      Object course_type = orderCourseOptionMap.get("course_type");
      if (course_type == null) {
        return null;
      }
      if ("course_type1".equals(course_type.toString())) {
        totalCourseType1Count += (int) orderCourseOptionMap.get("remain_course_count");

        reCourseType1Map.put("course_count", totalCourseType1Count);
      }
      if ("course_type2".equals(course_type.toString())) {
        totalCourseType2Count += (int) orderCourseOptionMap.get("remain_course_count");

        reCourseType2Map.put("course_count", totalCourseType2Count);
      }
    }
    formatCourseTypeList.add(reCourseType1Map);
    formatCourseTypeList.add(reCourseType2Map);
    return formatCourseTypeList;

  }
}
