/** 
 * File: WebexUtil.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.webex.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年7月28日 上午11:02:36
 * @author yangmh
 */
package com.webi.hwj.coperation.util;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coperation.constant.QqConstant;

import net.sf.json.JSONObject;

public class QqUtil {
  private static Logger logger = Logger.getLogger(QqUtil.class);

  /**
   * 
   * Title: qq核销兑换码接口<br>
   * Description: qq核销兑换码接口<br>
   * CreateDate: 2016年9月22日 下午2:08:06<br>
   * 
   * @category qq核销兑换码接口
   * @author seven.gz
   * @param appid
   * @param cpid
   * @param coupon
   * @param ts
   * @param appKey
   * @throws Exception
   */
  public static void consumeRedeemCode(String cpid, String coupon)
      throws Exception {

    String url = QqConstant.CONSUMECPP_URL;

    String paramStr = "appid=" + MemcachedUtil.getConfigValue("qq_redeem_code_appid") + "&coupon=" + coupon + "&cpid=" + cpid + "&ts="
        + new Date().getTime() / 1000;
    String signature = DigestUtils.md5Hex(paramStr + "&appkey=" + MemcachedUtil.getConfigValue("qq_redeem_code_appkey"));
    url = url + paramStr + "&signature=" + signature;

    String returnStr = HttpClientUtil.doPost(url, new HashMap());

    JSONObject returnJson = JSONObject.fromObject(returnStr);

    if (returnJson.getInt("ret") == QqConstant.CONSUMECPP_RESPONESE_CODE_SUCCESS) {

    } else {
      throw new RuntimeException("qq核销兑换码失败:" + returnJson.getString("msg"));
    }
  }
}
