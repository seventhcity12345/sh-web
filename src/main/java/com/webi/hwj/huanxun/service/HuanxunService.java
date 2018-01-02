/** 
 * File: HuanxunService.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.huanxun.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年12月28日 上午8:27:03
 * @author yangmh
 */
package com.webi.hwj.huanxun.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;

import net.sf.json.JSONObject;

/**
 * Title: HuanxunService<br>
 * Description: HuanxunService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月28日 上午8:27:03
 * 
 * @author yangmh
 */
@Service
public class HuanxunService {
  private static Logger logger = Logger.getLogger(HuanxunService.class);

  /**
   * Title: 预约环迅老师时间<br>
   * Description: huanxunBook<br>
   * CreateDate: 2015年12月28日 上午8:46:39<br>
   * 
   * @category huanxunBook
   * @author yangmh
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = { Exception.class })
  public String huanxunBook(Map<String, Object> paramMap) throws Exception {
    logger.info("预约环迅老师时间------>start");

    // String courseType = paramMap.get("course_type").toString();
    // if("course_type1".equals(courseType)||"course_type3".equals(courseType)){
    // courseType = "1v1";
    // }else if("course_type2".equals(courseType)){
    // courseType = "1vn";
    // }
    // modify by seven 2016年9月8日10:48:11 与环迅统一course_type 这里写死成course_type1
    // 只有1v1的才有这个接口，不论core1v1还是extension1v1 都传course_type1
    String courseType = "course_type1";
    String paramJsonStr = "{\"course_type\":\"" + courseType + "\","
        + "\"timestamp\":\"" + (System.currentTimeMillis() + "").substring(0, 11) + "\","
        + "\"partner\":\"" + MemcachedUtil.getConfigValue("huanxun_partner") + "\","
        + "\"teacher_id\":\"" + paramMap.get("teacher_id") + "\","
        + "\"start_time\":\"" + paramMap.get("start_time").toString().substring(0, 19) + "\","
        + "\"end_time\":\"" + paramMap.get("end_time").toString().substring(0, 19) + "\","
        + "\"course_courseware\":\"" + paramMap.get("course_courseware") + "\","
        + "\"user_id\":\"" + paramMap.get("user_id") + "\","
        + "\"nick_name\":\"" + paramMap.get("user_name") + "\""
        + "}";
    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("Authorization", "Token " + MemcachedUtil.getConfigValue("huanxun_token"));
    String returnStr = HttpClientUtil.doPostByJson(
        MemcachedUtil.getConfigValue("huanxun_url") + "/api/v1/wb/book", paramJsonStr, headerMap);

    JSONObject returnJson = JSONObject.fromObject(returnStr);
    logger.info("约环迅老师时间------>msg=" + returnJson.getString("msg") + ",code="
        + returnJson.getString("code"));
    return returnJson.getString("code");
  }

  /**
   * Title: 取消预约环迅老师时间<br>
   * Description: huanxunCancel<br>
   * CreateDate: 2015年12月28日 上午8:56:01<br>
   * 
   * @category huanxunCancel
   * @author yangmh
   * @param paramMap
   * @return
   * @throws Exception
   */
  public String huanxunCancel(Map<String, Object> paramMap) throws Exception {
    logger.info("取消预约环迅老师时间------>start");
    String paramJsonStr = "{\"timestamp\":\"" + (System.currentTimeMillis() + "").substring(0, 11)
        + "\","
        + "\"partner\":\"" + MemcachedUtil.getConfigValue("huanxun_partner") + "\","
        + "\"start_time\":\"" + paramMap.get("start_time").toString().substring(0, 19) + "\","
        + "\"end_time\":\"" + paramMap.get("end_time").toString().substring(0, 19) + "\","
        + "\"user_id\":\"" + paramMap.get("user_id") + "\""
        + "}";
    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("Authorization", "Token " + MemcachedUtil.getConfigValue("huanxun_token"));
    String returnStr = HttpClientUtil.doPostByJson(
        MemcachedUtil.getConfigValue("huanxun_url") + "/api/v1/classes/partner/wb/cancel",
        paramJsonStr, headerMap);
    JSONObject returnJson = JSONObject.fromObject(returnStr);
    logger.info("取消约环迅老师时间------>msg=" + returnJson.getString("msg") + ",code="
        + returnJson.getString("code"));
    return returnJson.getString("code");
  }

  /**
   * Title: 预约环迅老师时间<br>
   * Description: huanxunBook<br>
   * CreateDate: 2015年12月28日 上午8:46:39<br>
   * 
   * @category huanxunBook
   * @author yangmh
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = { Exception.class })
  public String huanxunBookOne2Many(Map<String, Object> paramMap) throws Exception {
    logger.info("预约环迅老师1vn时间------>start");

    String paramJsonStr = "{\"course_type\":\"" + paramMap.get("course_type").toString() + "\","
        + "\"partner\":\"" + MemcachedUtil.getConfigValue("huanxun_partner") + "\","
        + "\"teacher_id\":\"" + paramMap.get("teacher_id") + "\","
        + "\"start_time\":\"" + paramMap.get("start_time").toString().substring(0, 19) + "\","
        + "\"end_time\":\"" + paramMap.get("end_time").toString().substring(0, 19) + "\","
        + "\"course_courseware\":\"" + paramMap.get("course_courseware") + "\""
        + "}";
    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("Authorization", "Token " + MemcachedUtil.getConfigValue("huanxun_token"));
    String returnStr = HttpClientUtil.doPostByJson(
        MemcachedUtil.getConfigValue("huanxun_url") + "/api/v1/wb/book/onetomany/class",
        paramJsonStr, headerMap);

    JSONObject returnJson = JSONObject.fromObject(returnStr);
    logger.info("约环迅老师1vn时间------>msg=" + returnJson.getString("msg") + ",code="
        + returnJson.getString("code"));
    return returnJson.getString("code");
  }

  /**
   * Title: 取消预约环迅老师时间<br>
   * Description: huanxunCancel<br>
   * CreateDate: 2015年12月28日 上午8:56:01<br>
   * 
   * @category huanxunCancel
   * @author yangmh
   * @param paramMap
   * @return
   * @throws Exception
   */
  public String huanxunCancelOne2Many(Map<String, Object> paramMap) throws Exception {
    logger.info("取消预约环迅老师1vn时间------>start");
    String paramJsonStr = "{\"partner\":\"" + MemcachedUtil.getConfigValue("huanxun_partner")
        + "\","
        + "\"start_time\":\"" + paramMap.get("start_time").toString().substring(0, 19) + "\","
        + "\"end_time\":\"" + paramMap.get("end_time").toString().substring(0, 19) + "\","
        + "\"teacher_id\":\"" + paramMap.get("teacher_id") + "\""
        + "}";
    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("Authorization", "Token " + MemcachedUtil.getConfigValue("huanxun_token"));
    String returnStr = HttpClientUtil.doPostByJson(
        MemcachedUtil.getConfigValue("huanxun_url") + "/api/v1/onetomany/classes/partner/wb/cancel",
        paramJsonStr, headerMap);
    JSONObject returnJson = JSONObject.fromObject(returnStr);
    logger.info("取消约环迅老师1vn时间------>msg=" + returnJson.getString("msg") + ",code="
        + returnJson.getString("code"));
    return returnJson.getString("code");
  }
}
