package com.webi.hwj.user.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Title: CompletePercentUtil<br>
 * Description: 资料完成度计算<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年10月16日 下午2:02:02
 * 
 * @author athrun.cw
 */
public class CompletePercentUtil {
  private static String[] unUsedFieldNameArray = { "serialVersionUID", "keyId", "createDate",
      "updateDate", "createUserId", "updateUserId", "isUsed" };

  public static int getCompletePercent(Object obj) throws Exception {
    Class clazz = obj.getClass();
    Field[] fieldArray = clazz.getDeclaredFields();

    double alreadyComplete = 0;
    double total = 0;

    for (Field field : fieldArray) {
      field.setAccessible(true);
      String fieldName = field.getName();

      if (Arrays.asList(unUsedFieldNameArray).contains(fieldName)) {
        continue;
      }

      if (field.get(obj) != null) {
        alreadyComplete++;
        total++;
      } else {
        total++;
      }
    }
    return (int) Math.floor((alreadyComplete / total) * 100);
  }

  /**
   * 
   * Title: completePercent<br>
   * Description: 计算资料完成度<br>
   * CreateDate: 2015年10月16日 下午3:13:51<br>
   * 
   * @category 计算资料完成度
   * @author athrun.cw
   * @param paramMap
   * @return
   */
  public static int getCompletePercent(Map<String, Object> paramMap) {
    Map<String, Double> resultMap = new HashMap<String, Double>();
    // 已经完成的
    resultMap.put("alreadyComplete", 0.0);
    // 总共
    resultMap.put("total", 0.0);

    /**
     * 获取资料完成度有效map（不是所有查出来的字段都是依据条件）
     */
    // for(Object element : getUsefulInfoMap(paramMap).values()){
    // //计算资料完成度
    // resultMap = notNullEelementPencent(element, resultMap);
    // }
    //
    // return (int) Math.floor((resultMap.get("alreadyComplete") /
    // resultMap.get("total")) * 100 );

    double alreadyComplete = 0;
    double total = 0;

    // idcard
    if (paramMap.get("idcard") != null && !"".equals(paramMap.get("idcard"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // english_name
    if (paramMap.get("english_name") != null && !"".equals(paramMap.get("english_name"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // real_name
    if (paramMap.get("real_name") != null && !"".equals(paramMap.get("real_name"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // gender
    if (paramMap.get("gender") != null && !"".equals(paramMap.get("gender"))
        && (Integer.parseInt(paramMap.get("gender").toString()) == 0 || Integer
            .parseInt(paramMap.get("gender").toString()) == 1)) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // province
    if (paramMap.get("province") != null && !"".equals(paramMap.get("province"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // city
    if (paramMap.get("city") != null && !"".equals(paramMap.get("city"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // district
    if (paramMap.get("district") != null && !"".equals(paramMap.get("district"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // address
    if (paramMap.get("address") != null && !"".equals(paramMap.get("address"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // learn_tool
    if (paramMap.get("learn_tool") != null && !"".equals(paramMap.get("learn_tool"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // sign
    if (paramMap.get("personal_sign") != null && !"".equals(paramMap.get("personal_sign"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // email
    if (paramMap.get("email") != null && !"".equals(paramMap.get("email"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    // contract_func
    if (paramMap.get("contract_func") != null && !"".equals(paramMap.get("contract_func"))) {
      alreadyComplete++;
      total++;
    } else {
      total++;
    }
    return (int) Math.floor((alreadyComplete / total) * 100);
  }

  /**
   * 
   * Title: getUsefulInfoMap<br>
   * Description: 获取资料完成度的判断属性map<br>
   * CreateDate: 2015年10月20日 下午3:38:56<br>
   * 
   * @category 获取资料完成度的判断属性map
   * @author athrun.cw
   * @param userInfoMap
   * @return
   */
  public static Map<String, Object> getUsefulInfoMap(Map<String, Object> userInfoMap) {
    Map<String, Object> usefulMap = new HashMap<String, Object>();
    usefulMap.put("idcard", userInfoMap.get("idcard"));
    usefulMap.put("english_name", userInfoMap.get("english_name"));
    usefulMap.put("real_name", userInfoMap.get("real_name"));
    usefulMap.put("gender", userInfoMap.get("gender"));
    usefulMap.put("province", userInfoMap.get("province"));
    usefulMap.put("city", userInfoMap.get("city"));
    usefulMap.put("district", userInfoMap.get("district"));
    usefulMap.put("address", userInfoMap.get("address"));
    usefulMap.put("learn_tool", userInfoMap.get("learn_tool"));
    usefulMap.put("personal_sign", userInfoMap.get("personal_sign"));
    usefulMap.put("email", userInfoMap.get("email"));
    usefulMap.put("contract_func", userInfoMap.get("contract_func"));
    return usefulMap;
  }

  /**
   * 
   * Title: notNullEelementPencent<br>
   * Description: 非空元素占总元素的比例 <br>
   * CreateDate: 2015年10月20日 下午4:07:35<br>
   * 
   * @category 非空元素占总元素的比例
   * @author athrun.cw
   * @param element
   * @param resultMap
   * @return
   */
  private static Map<String, Double> notNullEelementPencent(Object element,
      Map<String, Double> resultMap) {
    if (element != null && !"".equals(element)) {
      // gender单独处理，因为默认是2
      if ("2".equals(element.toString())) {
        if (Integer.parseInt("gender") == 2) {
          resultMap.put("total", resultMap.get("total") + 1);
        }
        if (Integer.parseInt("gender") == 0 || Integer.parseInt("gender") == 1) {
          resultMap.put("alreadyComplete", resultMap.get("alreadyComplete") + 1);
          resultMap.put("total", resultMap.get("total") + 1);
        }
      } else {
        resultMap.put("alreadyComplete", resultMap.get("alreadyComplete") + 1);
        resultMap.put("total", resultMap.get("total") + 1);
      }
    } else {
      resultMap.put("total", resultMap.get("total") + 1);
    }
    return resultMap;
  }

  public static void main(String[] args) {

    double alreadyComplete = 1;
    double total = 12;
    System.out.println(alreadyComplete / total);
    System.out.println("round" + (int) Math.round((alreadyComplete / total) * 100));
    System.out.println("floor" + (int) Math.floor((alreadyComplete / total) * 100));
    System.out.println("ceil" + (int) Math.ceil((alreadyComplete / total) * 100));

    Map<String, Double> resultMap = new HashMap<String, Double>();
    resultMap.put("alreadyComplete", 0.0);
    resultMap.put("total", 0.0);

    System.out.println("--------------------" + resultMap.get("caowei"));

    Map<String, Object> map = new LinkedHashMap<String, Object>();
    map.put("key_id", "123456");
    map.put("idcard", "4203215555555");
    map.put("english_name", "athrun.cw");
    map.put("real_name", "caowei");
    map.put("gender", 1);
    for (Object test : map.values()) {
      System.out.println(notNullEelementPencent(test, resultMap));
      System.out.println(test);
    }
    System.out.println("-------------------");
    for (Object test : resultMap.values()) {
      System.out.println(test);
    }
  }

  /**
   * 
   * Title: 将时长转化为分钟<br>
   * Description: 将HH:mm:dd 的个是转化为分钟<br>
   * CreateDate: 2017年9月13日 下午1:39:01<br>
   * 
   * @category 将时长转化为分钟
   * @author seven.gz
   * @param str
   * @return
   */
  public static Integer translateMinute(String str) {
    if (!StringUtils.isEmpty(str)) {
      String[] strArr = str.split(":");
      if (strArr.length == 3) {
        return Integer.valueOf(strArr[0]) * 60 + Integer.valueOf(strArr[1]) + Integer.valueOf(
            strArr[2]) / 60;
      } else {
        return 0;
      }
    } else {
      return 0;
    }
  }

  /**
   * 
   * Title: 将时长转化为分钟<br>
   * Description: list的0,1,2分别放的时分秒<br>
   * CreateDate: 2017年9月13日 下午1:39:01<br>
   * 
   * @category 将时长转化为分钟
   * @author seven.gz
   * @param timeList
   * @return
   */
  public static Integer translateMinute(List<Integer> timeList) {
    if (timeList != null && timeList.size() == 3) {
      // 计算当天的分钟数
      return timeList.get(0) * 60 + timeList.get(1) + timeList.get(2) / 60;
    } else {
      return 0;
    }
  }
}
