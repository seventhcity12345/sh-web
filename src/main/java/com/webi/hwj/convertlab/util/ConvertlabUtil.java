package com.webi.hwj.convertlab.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.convertlab.constant.ConvertlabConstant;
import com.webi.hwj.gensee.util.GenseeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * Title: ConvertlabUtil<br>
 * Description: ConvertlabUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月8日 下午5:18:58
 * 
 * @author seven.gz
 */
public class ConvertlabUtil {
  private static Logger logger = Logger.getLogger(GenseeUtil.class);

  /**
   * 
   * Title: 获取身份权限<br>
   * Description: obtainAccessToken<br>
   * CreateDate: 2017年8月8日 下午6:09:09<br>
   * 
   * @category 获取身份权限
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public static String obtainAccessToken()
      throws Exception {
    String result = HttpClientUtil.doGetReturnString(
        "https://api.convertlab.com/security/accesstoken?grant_type=client_credentials&appid="
            + MemcachedUtil.getConfigValue(ConfigConstant.CONVERTLAB_APPID) + "&secret="
            + MemcachedUtil.getConfigValue(ConfigConstant.CONVERTLAB_SECRET));

    JSONObject returnJsonObj = JSONObject.fromObject(result);
    if (returnJsonObj != null) {
      return returnJsonObj.getString("access_token");
    }
    return null;
  }

  /**
   * 
   * Title: 获取客户数量<br>
   * Description: 这里查询条件和查询客户的条件一样只用最后更新时间过滤,最后更新时间的格式为yyyy-MM-ddThh:mm:ssZ<br>
   * CreateDate: 2017年8月8日 下午6:09:09<br>
   * 
   * @category 获取客户数量
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public static Integer obtainCustomersCount(String accessToken, String lastUpdatedFrom)
      throws Exception {
    String result = HttpClientUtil.doGetReturnString(
        "https://api.convertlab.com/v1/customers/count?access_token="
            + accessToken + "&lastUpdatedFrom=" + lastUpdatedFrom);
    if (!StringUtils.isEmpty(result)) {
      return Integer.valueOf(result);
    }
    return 0;
  }

  /**
   * 
   * Title: 获取客户信息<br>
   * Description: 这里查询条件和查询客户的条件一样只用最后更新时间过滤,最后更新时间的格式为yyyy-MM-ddThh:mm:ssZ<br>
   * CreateDate: 2017年8月8日 下午6:09:09<br>
   * 
   * @category 获取客户信息
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public static JSONArray obtainCustomers(String lastUpdatedFrom)
      throws Exception {
    // 获取身份认证 认证信息只能有效两天，所以这里每次用的时候都要重新获取
    String accessToken = obtainAccessToken();

    // 获取要查询的客户总数量
    int customerCount = obtainCustomersCount(accessToken, lastUpdatedFrom);

    JSONArray returnArray = new JSONArray();

    // 获取用户信息的接口最多就能查100 个人 所以需要循环调用
    if (customerCount > 0) {
      String result;
      JSONObject returnJsonObj;

      for (int i = 0; i * ConvertlabConstant.OBTAIN_CUSTOMERS_ROWS <= customerCount; i++) {
        result = HttpClientUtil.doGetReturnString(
            "https://api.convertlab.com/v1/customers?orderby=lastUpdated:desc&access_token="
                + accessToken
                + "&rows=" + ConvertlabConstant.OBTAIN_CUSTOMERS_ROWS + "&page=" + (i + 1) +
                "&lastUpdatedFrom=" + lastUpdatedFrom);
        returnJsonObj = JSONObject.fromObject(result);
        JSONArray resultArray = returnJsonObj.getJSONArray("rows");
        if (resultArray != null && resultArray.size() > 0) {
          returnArray.addAll(resultArray);
        }
      }
    }

    return returnArray;
  }
}
