package com.webi.hwj.ordercourse.util;

import java.util.HashMap;
import java.util.Map;

import com.mingyisoft.javabase.util.MemcachedUtil;

/**
 * 
 * Title: 续约<br>
 * Description: RenewalOrderCourseUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月30日 下午3:45:25
 * 
 * @author athrun.cw
 */
public class RenewalOrderCourseUtil {

  /**
   * 
   * Title: 处理<br>
   * Description: transOrderAndContractStatus<br>
   * CreateDate: 2016年3月30日 下午3:49:17<br>
   * 
   * @category transOrderAndContractStatus
   * @author athrun.cw
   * @param paramMap
   * @return
   */
  public static Map<String, Object> formatRenewalOrderCourse(Map<String, Object> paramMap) {
    if (paramMap == null) {
      return null;
    }
    // key_id, category_type, course_package_name, limit_show_time
    StringBuffer strBuf = new StringBuffer();
    strBuf.append(paramMap.get("key_id") + ",");
    strBuf.append(paramMap.get("category_type") + ",");
    strBuf.append(paramMap.get("limit_show_time") + ",");
    strBuf.append(MemcachedUtil.getConfigValue(paramMap.get("category_type").toString()) + ",");
    strBuf.append(paramMap.get("total_final_price") + ",");
    // modify by komi 2016年6月1日17:38:22 使用原合同的真实价格
    strBuf.append(paramMap.get("total_real_price") + ",");
    strBuf.append(paramMap.get("limit_show_time_unit"));

    paramMap.put("strBuf", strBuf);

    return paramMap;
  }

  public static void main(String[] args) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("key_id", "11111");
    paramMap.put("category_type", "category_type1");
    paramMap.put("course_package_name", "1n1");
    paramMap.put("limit_show_time", "3");
    System.out.println(formatRenewalOrderCourse(paramMap));
  }

}
