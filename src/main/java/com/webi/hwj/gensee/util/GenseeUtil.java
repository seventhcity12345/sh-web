/** 
 * File: GenseeUtil.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.gensee.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年7月6日 下午1:57:55
 * @author yangmh
 */
package com.webi.hwj.gensee.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;

import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SqlUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.gensee.constant.GenseeConstant;
import com.webi.hwj.gensee.entity.Gensee;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: GenseeUtil<br>
 * Description: GenseeUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月6日 下午1:57:55
 * 
 * @author yangmh
 */
public class GenseeUtil {
  private static Logger logger = Logger.getLogger(GenseeUtil.class);

  /**
   * Title: 创建房间<br>
   * Description: createRoom<br>
   * CreateDate: 2016年7月6日 下午2:55:49<br>
   * 
   * @category 创建房间
   * @author yangmh
   * @param roomType
   * @param subject
   * @param startTime
   * @param endTime
   * @throws 会把展示互动的code转换成中文抛出来，抓住e.toString即可。
   */
  public static Gensee createRoom(int roomType,String courseType, String subject, Date startTime, Date endTime,
      String teacherDesc,String teacherName,String courseDesc)
      throws Exception {
    logger.info("创建展示互动房间开始------>roomType=" + roomType + ",subject=" + subject + ",startTime="
        + startTime + ",endTime=" + endTime);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("subject", subject + "(" + SqlUtil.createUUID() + ")");
    params.put("teacherToken", GenseeConstant.GENSEE_TEACHER_TOKEN);
    params.put("assistantToken", GenseeConstant.GENSEE_ASSISTANT_TOKEN);
    params.put("studentClientToken", GenseeConstant.GENSEE_STUDENT_CLIENT_TOKEN);
    /**
     * modified by komi 2017年1月4日14:21:53
     * 创建房间是增加课程、教师简介信息，需求472
     */
    params.put("speakerInfo", teacherName+"\r\n"+teacherDesc);
    params.put("scheduleInfo", DateUtil.dateToStr(startTime, "MM月dd日HH:mm")
        +" - "+DateUtil.dateToStr(endTime, "MM月dd日HH:mm"));
    params.put("description", courseDesc);
    
    // 提前开始x分钟开启房间
    Calendar c = Calendar.getInstance();
    c.setTime(startTime);
    c.add(Calendar.MINUTE,
        -((CourseType) MemcachedUtil.getValue(courseType)).getCourseTypeBeforeGoclassTime());

    params.put("startDate", c.getTimeInMillis());
    params.put("invalidDate", endTime.getTime());
    params.put("loginName", MemcachedUtil.getConfigValue("gensee_login_name"));
    params.put("password", DigestUtils.md5Hex(MemcachedUtil.getConfigValue("gensee_password")));

    // Web 端学生界面设置(1 是三分屏，2是 文档/视频为主，3是两分屏，4：互动增加)
    params.put("uiMode", GenseeConstant.GENSEE_UI_MODE_THREE_PART);

    // 房间类型：
    // 0：大讲堂（目前都用这个，greg说的，已经邮件报备2016年7月6日）
    // 1：小班课
    params.put("scene", roomType);

    // 是否为实时数据
    params.put("realtime", "true");
    // 密码是进行加密过的（在控制台设置成MD5加密）
    params.put("sec", "true");

    String result = HttpClientUtil.doPost(
        MemcachedUtil.getConfigValue("gensee_api_url") + "/integration/site/training/room/created",
        params);

    logger.info("result------>" + result);
    JSONObject returnJsonObj = JSONObject.fromObject(result);

    checkGenseeReturnCode(returnJsonObj);

    Gensee gensee = new Gensee();
    gensee.setRoomId(returnJsonObj.getString("id"));
    gensee.setStudentJoinUrl(returnJsonObj.getString("studentJoinUrl"));
    gensee.setTeacherJoinUrl(returnJsonObj.getString("teacherJoinUrl"));

    logger.info("创建展示互动房间结束------>gensee=" + gensee);
    return gensee;
  }

  /**
   * Title: 查看展示互动录像<br>
   * Description: watchVideo<br>
   * CreateDate: 2016年7月10日 上午11:29:57<br>
   * 
   * @category 查看展示互动录像
   * @author yangmh
   * @param roomId
   * @return
   * @throws Exception
   *           会把展示互动的code转换成中文抛出来，抓住e.toString即可。
   */
  public static String watchVideo(String roomId, String nickName) throws Exception {
    logger.info("查看展示互动录像开始------>roomId=" + roomId);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("roomId", roomId);
    params.put("loginName", MemcachedUtil.getConfigValue("gensee_login_name"));
    params.put("password", DigestUtils.md5Hex(MemcachedUtil.getConfigValue("gensee_password")));
    // 密码是进行加密过的（在控制台设置成MD5加密）
    params.put("sec", "true");

    String result = HttpClientUtil.doPost(
        MemcachedUtil.getConfigValue("gensee_api_url")
            + "/integration/site/training/courseware/list",
        params);

    logger.info("result------>" + result);
    JSONObject returnJsonObj = JSONObject.fromObject(result);

    checkGenseeReturnCode(returnJsonObj);

    String videoUrl = null;
    
    /**
     * modified by komi 2017年5月23日14:16:22
     * 如果返回里的没有coursewares，就不要读取了，会报错
     */
    if(returnJsonObj.containsKey("coursewares")){
      JSONArray coursewares = returnJsonObj.getJSONArray("coursewares");

      JSONObject courseware = coursewares.getJSONObject(0);

      videoUrl = courseware.getString("url") + "?nickname=" + nickName;
    }
    
    logger.info("查看展示互动录像结束------>videoUrl=" + videoUrl);
   
    return videoUrl;
  }

  /**
   * Title: 校验展示互动的返回码，抛异常<br>
   * Description: checkGenseeReturnCode<br>
   * CreateDate: 2016年7月7日 下午2:28:58<br>
   * 
   * @category 校验展示互动的返回码，抛异常
   * @author yangmh
   * @param jsonObject
   * @throws Exception
   */
  public static void checkGenseeReturnCode(JSONObject jsonObject) throws Exception {
    String code = jsonObject.getString("code");
    String message = null;

    if (!"0".equals(code)) {
      message = jsonObject.getString("message");
    }

    switch (code) {
    case "0":
      // 成功
      break;
    case "-1":
      // 失败
      throw new RuntimeException("调用展示互动接口返回失败" + message);
    case "101":
      throw new RuntimeException("参数错误" + message);
    case "102":
      throw new RuntimeException("参数转换错误" + message);
    case "200":
      throw new RuntimeException("认证失败" + message);
    case "201":
      throw new RuntimeException("口令过期" + message);
    case "300":
      throw new RuntimeException("系统错误" + message);
    case "500":
      throw new RuntimeException("业务错误" + message);
    case "501":
      throw new RuntimeException("业务错误-数据不存在" + message);
    case "502":
      throw new RuntimeException("业务错误，重复数据" + message);
    case "600":
      throw new RuntimeException("接口被禁用，请联系管理员" + message);
    }
  }

  /**
   * Title: 上传课件<br>
   * Description: uploadCourseware<br>
   * CreateDate: 2016年7月11日 上午11:16:11<br>
   * 
   * @category 上传课件
   * @author yangmh
   * @throws Exception
   */
  public static String uploadCourseware(String name, String resourceUrl) throws Exception {
    logger.info("展示互动文件上传开始------>name=" + name + ",resourceUrl=" + resourceUrl);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("resourceUrl", resourceUrl);
    params.put("name", name);
    params.put("loginName", MemcachedUtil.getConfigValue("gensee_login_name"));
    params.put("password", DigestUtils.md5Hex(MemcachedUtil.getConfigValue("gensee_password")));
    // 密码是进行加密过的（在控制台设置成MD5加密）
    params.put("sec", "true");

    String result = HttpClientUtil.doPost(
        MemcachedUtil.getConfigValue("gensee_api_url")
            + "/integration/site/training/courseware/transcode",
        params);

    logger.info("result------>" + result);
    JSONObject returnJsonObj = JSONObject.fromObject(result);

    checkGenseeReturnCode(returnJsonObj);

    String documentId = returnJsonObj.getString("docId");

    logger.info("展示互动文件上传结束------>documentId=" + documentId);
    return documentId;
  }

  /**
   * Title: 绑定课件到房间<br>
   * Description: 绑定课件到房间<br>
   * CreateDate: 2016年7月11日 下午3:19:32<br>
   * 
   * @category 绑定课件到房间
   * @author yangmh
   */
  public static void attachFile(String roomId, String docId) throws Exception {
    if (StringUtils.isEmpty(docId)) {
      return;
    }
    logger.info("展示互动绑定文件开始------>roomId=" + roomId + ",docId=" + docId);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("roomId", roomId);
    params.put("docId", docId);
    params.put("loginName", MemcachedUtil.getConfigValue("gensee_login_name"));
    params.put("password", DigestUtils.md5Hex(MemcachedUtil.getConfigValue("gensee_password")));
    // 密码是进行加密过的（在控制台设置成MD5加密）
    params.put("sec", "true");

    String result = HttpClientUtil.doPost(
        MemcachedUtil.getConfigValue("gensee_api_url") + "/integration/site/training/doc/attach",
        params);

    logger.info("result------>" + result);
    JSONObject returnJsonObj = JSONObject.fromObject(result);

    checkGenseeReturnCode(returnJsonObj);

    String code = returnJsonObj.getString("code");

    logger.info("展示互动绑定文件结束------>code=" + code);
  }
}
