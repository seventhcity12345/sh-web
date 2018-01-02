/** 
 * File: WebexUtil.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.webex.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年7月28日 上午11:02:36
 * @author yangmh
 */
package com.webi.hwj.coperation.util;

import org.apache.log4j.Logger;

import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.coperation.constant.MeibangConstant;

import net.sf.json.JSONObject;

public class MeibangUtil {
  private static Logger logger = Logger.getLogger(MeibangUtil.class);

  /**
   * 
   * Title: 美邦券码校验<br>
   * Description: 美邦券码校验<br>
   * CreateDate: 2016年11月8日 下午3:44:13<br>
   * 
   * @category 美邦券码校验
   * @author seven.gz
   * @param cardNo
   * @return
   * @throws Exception
   */
  public static boolean checkCardNo(String cardNo)
      throws Exception {
    boolean resultFlag = false;

    String url = MemcachedUtil.getConfigValue(ConfigConstant.MEIBANG_SERVER_ADDRESS)
        + MeibangConstant.API_ADDRESS_CHECK_CARD_NO;

    String paramJsonStr = "{\"providerCode\":\"" + MeibangConstant.API_PARAM_PROVIDERCODE + "\","
        + "\"cardNo\":\"" + cardNo + "\""
        + "}";

    String returnStr = HttpClientUtil.doPostByJson(url, paramJsonStr, null);

    JSONObject returnJson = JSONObject.fromObject(returnStr);

    // 接口里拿哪个值还不确定需要问他们要
    if (returnJson.getBoolean("success")) {
      resultFlag = true;
    } else {
      resultFlag = false;
      logger.error(
          "美邦兑换码校验失败" + returnJson.getString("errorcode") + ":" + returnJson.getString("errormsg"));
    }
    return resultFlag;
  }

  /**
   * 
   * Title: 核销结果接收<br>
   * Description: 核销结果接收<br>
   * CreateDate: 2016年11月8日 下午4:09:27<br>
   * 
   * @category 核销结果接收
   * @author seven.gz
   * @param cardNo
   * @param mobile
   * @return
   * @throws Exception
   */
  public static boolean recExchangeResult(String cardNo, String mobile)
      throws Exception {
    boolean resultFlag = false;

    String url = MemcachedUtil.getConfigValue(ConfigConstant.MEIBANG_SERVER_ADDRESS)
        + MeibangConstant.API_ADDRESS_REC_EXCHANGE_RESULT;

    String paramJsonStr = "{\"providerCode\":\"" + MeibangConstant.API_PARAM_PROVIDERCODE + "\","
        + "\"cardNo\":\"" + cardNo + "\","
        + "\"mobile\":\"" + mobile + "\""
        + "}";

    String returnStr = HttpClientUtil.doPostByJson(url, paramJsonStr, null);

    JSONObject returnJson = JSONObject.fromObject(returnStr);

    // 接口里拿哪个值还不确定需要问他们要
    if (returnJson.getBoolean("success")) {
      resultFlag = true;
    } else {
      resultFlag = false;
      logger.error(
          "美邦核销兑换码失败" + returnJson.getString("errorcode") + ":" + returnJson.getString("errormsg"));
    }
    return resultFlag;
  }

}
