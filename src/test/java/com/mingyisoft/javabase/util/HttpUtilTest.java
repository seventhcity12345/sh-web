package com.mingyisoft.javabase.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class HttpUtilTest {
  @Test
  public void demo() {

  }

  // @Test
  /**
   * Title: setValue.<br>
   * Description: setValue<br>
   * CreateDate: 2016年9月14日 下午2:04:55<br>
   * 
   * @category setValue
   * @author yangmh
   * @throws Exception
   *           去死
   */
  public void setValue() throws Exception {
    Map<String, Object> ipMap = new HashMap<String, Object>();
    String ip = "116.228.62.78";
    ipMap.put("ip", ip);
    StringBuffer crmUrl = new StringBuffer(MemcachedUtil.getConfigValue("crm_register_url"));
    crmUrl.append("&Phone=10000000006&Citys=%E4%B8%8A%E6%B5%B7&ADID=QGHQFN06NI");
    String returnStr = HttpClientUtil.doPost(crmUrl.toString(), new HashMap<String, Object>());// "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json",ipMap);
    String resultStr = StringUtils.substringBetween(returnStr, "(", ")");
    JSONObject resultJsonObject = JSONObject.fromObject(resultStr);
    String aa = resultJsonObject.get("State").toString();
    System.out.println(resultJsonObject.get("State").toString());
    System.out.println("0".equals(resultJsonObject.get("State").toString()));

    String cityValue = StringUtils.substringBetween(returnStr.toString(), "\"" + "city" + "\":\"",
        "\",");
    String city = StringEscapeUtils.unescapeJava(cityValue);
    // System.out.println(URLEncoder.encode(city, "utf-8"));

    String cityValue2 = StringUtils.substringBetween(returnStr.toString(), "\"" + "ret" + "\":",
        ",");
    System.out.println("1".equals(cityValue2));
  }
}
