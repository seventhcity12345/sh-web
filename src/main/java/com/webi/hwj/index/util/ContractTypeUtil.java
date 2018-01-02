package com.webi.hwj.index.util;

import java.util.Map;

/**
 * 
 * Title: ContractTypeUtil<br>
 * Description: ContractTypeUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月11日 下午3:11:45
 * 
 * @author athrun.cw
 */
public class ContractTypeUtil {

  /**
   * 
   * Title: formatContractType<br>
   * Description: 更具合同的category_type，获取相应的类别<br>
   * CreateDate: 2015年12月11日 下午3:14:14<br>
   * 
   * @category 更具合同的category_type，获取相应的类别
   * @author athrun.cw
   * @param paramMap
   * @return
   */
  public static Map<String, Object> formatContractType(Map<String, Object> paramMap) {
    // 防止出现null异常
    if (paramMap.get("category_type") == null) {
      return paramMap;
    }
    String categoryType = paramMap.get("category_type").toString();
    switch (categoryType) {
    // category_type1 商务英语
    case "category_type1":
      paramMap.put("order_type", "商务英语");
      break;
    // category_type2 通用英语
    case "category_type2":
      paramMap.put("order_type", "通用英语");
      break;
    // category_type3 主题英语job_interview
    case "category_type3":
      paramMap.put("order_type", "job_interview");
      break;
    // category_type4 主题英语travel
    case "category_type4":
      paramMap.put("order_type", "travel");
      break;
    default:
      break;
    }
    return paramMap;
  }
}
