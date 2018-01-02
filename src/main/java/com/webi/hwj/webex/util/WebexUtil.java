/** 
 * File: WebexUtil.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.webex.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年7月28日 上午11:02:36
 * @author yangmh
 */
package com.webi.hwj.webex.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.HttpClientUtil;
import com.webi.hwj.constant.ErrorCodeEnum;

import net.sf.json.JSONObject;

/**
 * Title: WebexUtil<br>
 * Description: WebexUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月28日 上午11:02:36
 * 
 * @author yangmh
 */
public class WebexUtil {
  private static Logger logger = Logger.getLogger(WebexUtil.class);

  /**
   * Title: 生成Webex通用头部<br>
   * Description: genernateWebexCommonHeader<br>
   * CreateDate: 2016年7月28日 下午2:50:50<br>
   * 
   * @category 生成Webex通用头部
   * @author yangmh
   * @param webExID
   *          房间host帐号
   * @param password
   *          房间host密码
   * @param email
   *          房间host邮箱
   * @param webexSiteId
   *          webex的网站id(为了支持跨域)
   * @param webexPartnerId
   *          webex的合作者id(为了支持跨域)
   * @return
   */
  private static String genernateWebexCommonHeader(String webExID, String password, String email,
      String webexSiteId, String webexPartnerId) {
    String returnXml = "    <header>"
        + "        <securityContext>"
        + "            <webExID>" + webExID + "</webExID>"
        + "            <password>" + password + "</password>"
        + "            <siteID>" + webexSiteId + "</siteID>"
        + "            <partnerID>" + webexPartnerId + "</partnerID>"
        + "            <email>" + email + "</email>"
        + "        </securityContext>"
        + "    </header>";
    return returnXml;
  }

  /**
   * Title: 创建Webex会议<br>
   * Description: createWebexMeeting<br>
   * CreateDate: 2016年7月28日 下午2:51:43<br>
   * 
   * @category 创建Webex会议
   * @author yangmh
   * @param webExID
   *          房间host帐号
   * @param password
   *          房间host密码
   * @param email
   *          房间host邮箱
   * @param webexSiteId
   *          webex的网站id(为了支持跨域)
   * @param webexPartnerId
   *          webex的合作者id(为了支持跨域)
   * @param confName
   *          会议名称
   * @param startDate
   *          开始时间
   * @param openTime
   *          会议开始前多久可以进入房间，单位：秒
   * @param duration
   *          会议持续时间，单位：分钟
   * @throws Exception
   * @return meetingKey 会议号
   */
  public static String createWebexMeeting(String url, String webExID, String password, String email,
      String webexSiteId, String webexPartnerId,
      String confName, Date startDate, int openTime, int duration) throws Exception {
    String paramXmlStr = "<?xml version=\"1.0\" ?>"
        + "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + genernateWebexCommonHeader(webExID, password, email, webexSiteId, webexPartnerId)
        + "    <body>"
        + "        <bodyContent"
        + "            xsi:type=\"java:com.webex.service.binding.meeting.CreateMeeting\">"
        + "            <metaData>"
        + "                <confName>" + confName + "</confName>"
        + "            </metaData>"
        + "            <enableOptions>"
        + "                <chat>true</chat>"
        + "                <poll>true</poll>"
        + "                <audioVideo>true</audioVideo>"
        // modify by seven+komi 去除学员操作ppt的权限
        // + " <annotation>true</annotation>"
        // + " <viewAnyDoc>true</viewAnyDoc>"
        // + " <viewAnyPage>true</viewAnyPage>"
        + "                <attendeeList>true</attendeeList>"
        + "                <thumbnail>true</thumbnail>"
        + "            </enableOptions>"
        + "            <schedule>"
        // 07/28/2016 13:30:00
        + "                <startDate>" + DateUtil.dateToStr(startDate, "MM/dd/YYYY HH:mm:ss")
        + "</startDate>"
        // 900
        + "                <openTime>" + openTime + "</openTime>"
        // 20
        + "                <duration>" + duration + "</duration>"
        + "                <timeZoneID>45</timeZoneID>"
        + "            </schedule>"
        + "        </bodyContent>"
        + "    </body>"
        + "</serv:message>";

    String reutrnXml = HttpClientUtil.doPostByXml(url, paramXmlStr, null);

    if (reutrnXml.indexOf("<serv:result>SUCCESS</serv:result>") != -1) {
      String meetingkey = reutrnXml.substring(reutrnXml.indexOf("<meet:meetingkey>") + 17,
          reutrnXml.indexOf("</meet:meetingkey>"));
      logger.info("meetingkey=" + meetingkey);
      return meetingkey;
    } else {
      throw new RuntimeException("webex创建会议失败:" + reutrnXml);
    }
  }

  /**
   * Title: 创建Webex的HostUrl（老师链接）<br>
   * Description: createWebexHostUrl<br>
   * CreateDate: 2016年7月28日 下午8:14:22<br>
   * 
   * @category 创建Webex的HostUrl（老师链接）
   * @author yangmh
   * @param webExID
   *          房间host帐号
   * @param password
   *          房间host密码
   * @param email
   *          房间host邮箱
   * @param webexSiteId
   *          webex的网站id(为了支持跨域)
   * @param webexPartnerId
   *          webex的合作者id(为了支持跨域)
   * @param meetingKey
   *          会议号
   * @return
   * @throws Exception
   */
  public static String createWebexHostUrl(String url, String webExID, String password, String email,
      String webexSiteId, String webexPartnerId,
      String meetingKey) throws Exception {
    String paramXmlStr = "<?xml version=\"1.0\" ?>"
        + "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + genernateWebexCommonHeader(webExID, password, email, webexSiteId, webexPartnerId)
        + "    <body>"
        + "        <bodyContent"
        + "            xsi:type=\"java:com.webex.service.binding.meeting.GethosturlMeeting\">"
        + "            <meetingKey>" + meetingKey + "</meetingKey>"
        + "        </bodyContent>"
        + "    </body>"
        + "</serv:message>";

    String reutrnXml = HttpClientUtil.doPostByXml(url, paramXmlStr, null);

    if (reutrnXml.indexOf("<serv:result>SUCCESS</serv:result>") != -1) {
      String hostUrl = reutrnXml.substring(reutrnXml.indexOf("<meet:hostMeetingURL>") + 21,
          reutrnXml.indexOf("</meet:hostMeetingURL>"));
      hostUrl = hostUrl.replaceAll("&amp;", "&");
      logger.info("hostUrl=" + hostUrl + "&PW=" + password);
      return hostUrl + "&PW=" + password;
    } else {
      throw new RuntimeException("webex创建会议失败:" + reutrnXml);
    }
  }

  /**
   * Title: 创建学生进入url<br>
   * Description: createWebexJoinUrl<br>
   * CreateDate: 2016年7月28日 下午8:21:28<br>
   * 
   * @category 创建学生进入url
   * @author yangmh
   * @param webExID
   *          房间host帐号
   * @param password
   *          房间host密码
   * @param email
   *          房间host邮箱
   * @param webexSiteId
   *          webex的网站id(为了支持跨域)
   * @param webexPartnerId
   *          webex的合作者id(为了支持跨域)
   * @param meetingKey
   *          会议号
   * @param attendeeName
   *          必须传入学生名称+"（学号）"的形式
   * @return
   * @throws Exception
   */
  public static String createWebexJoinUrl(String url, String webExID, String password, String email,
      String webexSiteId, String webexPartnerId,
      String meetingKey, String attendeeName) throws Exception {
    String paramXmlStr = "<?xml version=\"1.0\" ?>"
        + "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + genernateWebexCommonHeader(webExID, password, email, webexSiteId, webexPartnerId)
        + "    <body>"
        + "        <bodyContent"
        + "            xsi:type=\"java:com.webex.service.binding.meeting.GetjoinurlMeeting\">"
        + "            <meetingKey>" + meetingKey + "</meetingKey>"
        + "            <attendeeName>" + attendeeName + "</attendeeName>"
        + "            <attendeeEmail>alexyang@webi.com.cn</attendeeEmail>"
        + "        </bodyContent>"
        + "    </body>"
        + "</serv:message>";

    String reutrnXml = HttpClientUtil.doPostByXml(url, paramXmlStr, null);

    if (reutrnXml.indexOf("<serv:result>SUCCESS</serv:result>") != -1) {
      String joinUrl = reutrnXml.substring(reutrnXml.indexOf("<meet:joinMeetingURL>") + 21,
          reutrnXml.indexOf("</meet:joinMeetingURL>"));
      // String inviteUrl =
      // reutrnXml.substring(reutrnXml.indexOf("<meet:inviteMeetingURL>")+23,reutrnXml.indexOf("</meet:inviteMeetingURL>"));

      logger.info("joinUrl=" + joinUrl + "&AT=JM");
      return joinUrl + "&AT=JM";
    } else {
      throw new RuntimeException("webex创建会议失败:" + reutrnXml);
    }
  }

  /**
   * Title: 删除webex会议<br>
   * Description: deleteWebexMeeting<br>
   * CreateDate: 2016年8月1日 上午10:48:34<br>
   * 
   * @category 删除webex会议
   * @author yangmh
   * @param webExID
   *          房间host帐号
   * @param password
   *          房间host密码
   * @param email
   *          房间host邮箱
   * @param webexSiteId
   *          webex的网站id(为了支持跨域)
   * @param webexPartnerId
   *          webex的合作者id(为了支持跨域)
   * @param meetingKey
   *          会议号
   * @throws Exception
   */
  public static void deleteWebexMeeting(String url, String webExID, String password, String email,
      String webexSiteId, String webexPartnerId,
      String meetingKey) throws Exception {
    String paramXmlStr = "<?xml version=\"1.0\" ?>"
        + "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + genernateWebexCommonHeader(webExID, password, email, webexSiteId, webexPartnerId)
        + "    <body>"
        + "        <bodyContent"
        + "            xsi:type=\"java:com.webex.service.binding.meeting.DelMeeting\">"
        + "            <meetingKey>" + meetingKey + "</meetingKey>"
        + "        </bodyContent>"
        + "    </body>"
        + "</serv:message>";

    String reutrnXml = HttpClientUtil.doPostByXml(url, paramXmlStr, null);

    if (reutrnXml.indexOf("<serv:result>SUCCESS</serv:result>") != -1) {

    } else {
      throw new RuntimeException("webex删除会议失败:" + reutrnXml);
    }
  }

  /**
   * Title: 进入黄海的测试教室.<br>
   * Description: enterTestWebexRoom<br>
   * CreateDate: 2016年12月21日 上午10:33:11<br>
   * 
   * @category 进入黄海的测试教室
   * @author yangmh
   * @return
   * @throws Exception
   */
  public static CommonJsonObject enterTestWebexRoom() throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String currentLong = System.currentTimeMillis() + "";
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("contract_guid", "speakhi");
    paramMap.put("enname", "testSpeakhiStudent");
    paramMap.put("appid", "olsdc04a482d9914d0");
    paramMap.put("time_stamp", currentLong);

    // 数字签名
    // appid和私钥是对应的，黄海配置的。
    // 对称加密的密钥：00190e7e1a4b43cea0fa2783d1c5ea587531a4f8760a4d2d8e009def73c6ce0a
    // 拼装密钥
    String secret = DigestUtils
        .md5Hex(
            "00190e7e1a4b43cea0fa2783d1c5ea587531a4f8760a4d2d8e009def73c6ce0a" + currentLong);
    // 取得后六位
    String echoStr = secret.substring(secret.length() - 6);

    paramMap.put("echo_str", echoStr);
    // 测试地址：http://olstest.webi.com.cn/api/webex/test_code
    // 原来地址：http://olsapi.webi.com.cn/api/webex/test_code
    // 加https的地址：https://olsapi.webi.com.cn
    String returnJsonStr = HttpClientUtil.doPost("https://olsapi.webi.com.cn/api/webex/test_code",
        paramMap);
    System.out.println(returnJsonStr);

    JSONObject returnJsonObj = JSONObject.fromObject(returnJsonStr);
    Boolean state = (Boolean) returnJsonObj.get("state");

    if (state) {
      json.setCode(ErrorCodeEnum.SUCCESS.getCode());
      String dataStr = returnJsonObj.getString("data");
      JSONObject dataObj = JSONObject.fromObject(dataStr);

      json.setData(dataObj.getString("url"));
    } else {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      json.setData(returnJsonObj.getString("error"));
    }
    return json;
  }
}
