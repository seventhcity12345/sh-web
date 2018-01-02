package com.webi.hwj.convertlab.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.convertlab.util.ConvertlabUtil;
import com.webi.hwj.util.CrmUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ConvertlabService {
  private static Logger logger = Logger.getLogger(ConvertlabService.class);

  /**
   * 
   * Title: converlab数据推送到crm<br>
   * Description: pushUserDateToCrm<br>
   * CreateDate: 2017年8月11日 下午4:44:18<br>
   * 
   * @category converlab数据推送到crm
   * @author seven.gz
   * @param selectDate
   */
  public void pushUserDateToCrm(Date selectDate) throws Exception {
    String lastUpdatedFrom = DateUtil.dateToStr(selectDate,
        "yyyy-MM-dd'T'hh:mm:ss'Z'");

    JSONArray userDateArray = null;

    // 循环三次获取数据 感觉循环三次直接写在调用获取的地方更好
    for (int i = 0; i < 3; i++) {
      try {
        // 如果第二次循环则等待5秒钟
        if (i > 0) {
          Thread.sleep(5000);
        }
        userDateArray = ConvertlabUtil.obtainCustomers(lastUpdatedFrom);
        break;
      } catch (Exception e) {
        logger.error("error:" + e.getMessage(), e);
        // 如果第三次还是失败就报警
        if (i == 2) {
          SmsUtil.sendAlarmSms(
              "获取converlab数据失败");
          throw e;
        }
      }
    }

    // 生产才发送开发和测试就不要推送了,推送的数据一样也不好
    if ("pro".equals(MemcachedUtil.getConfigValue("env"))) {
      // 将数据推送crm 这里数据量很小 看了下一天就几条数据，等量大了时间用太多了再用多线程
      if (userDateArray != null && userDateArray.size() > 0) {
        JSONObject jsonObject = null;

        // 记录失败数量
        int failCount = 0;
        for (int j = 0; j < userDateArray.size(); j++) {
          jsonObject = userDateArray.getJSONObject(j);
          // 手机号不为空才推送crm, 这个接口获取不到年龄
          if (!StringUtils.isEmpty(jsonObject.getString("mobile")) && !"null".equals(jsonObject
              .getString("mobile"))) {

            // 如果没有成功失败数量加一
            if (!CrmUtil.crmPushUser(jsonObject.getString("mobile"),
                jsonObject.getString("name"),
                null)) {
              failCount++;
            }
          }
        }
        // 如果有失败的则报警
        if (failCount > 0) {
          SmsUtil.sendAlarmSms(
              "获取converlab数据推送crm异常,失败数量:" + failCount);
        }
      }
    }
  }
}
