package com.webi.hwj.classin.util;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.classin.param.ClassinAppUrlParam;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.util.Md5Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * Title: ClassinUtil<br>
 * Description: ClassinUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月24日 下午2:04:54
 * 
 * @author seven.gz
 */
public class ClassinUtil {
  private static Logger logger = Logger.getLogger(ClassinUtil.class);

  /**
   * 
   * Title: 创建房间<br>
   * Description: createRoom<br>
   * CreateDate: 2017年8月24日 下午2:05:06<br>
   * 
   * @category 创建房间
   * @author seven.gz
   */
  public static String createRoom(String courseTitle, String coursePic, String schedulingId,
      String teacherName, String teacherPhoto,
      Date startTime, int durationMinutes, String courseId)
          throws Exception {
    logger.info("创建classin房间开始");

    String paramJsonStr = "{\"ClassTitle\":\"" + courseTitle + "\","
        + "\"ClassCover\":\"" + coursePic + "\","
        + "\"RelatedId\":\"" + schedulingId + "\","
        + "\"RelatedType\":\"" + MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_RELATED_TYPE)
        + "\","
        + "\"TeacherName\":\"" + teacherName + "\","
        + "\"TeacherPhoto\":\"" + teacherPhoto + "\","
        + "\"StartTime\":\"" + DateUtil.dateToStrYYMMDDHHMMSS(startTime) + "\","
        + "\"DurationMinutes\":\"" + durationMinutes + "\","
        + "\"UserName\":\"" + "speakhi" + "\","
        + "\"Resource\":\"" + courseId + "-" + courseTitle + "\""
        + "}";
    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("ApiKey", MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_API_KEY));
    String returnStr = HttpClientUtil.doPostByJson(
        MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_URL) + "/api/Video",
        paramJsonStr, headerMap);

    JSONObject returnJson = JSONObject.fromObject(returnStr);

    checkGenseeReturnCode(returnJson);

    String roomId = returnJson.getJSONObject("Data").getString("Id");

    logger.info("创建classin房间结束");
    return roomId;
  }

  /**
   * 
   * Title: 检查是否返回成功<br>
   * Description: checkGenseeReturnCode<br>
   * CreateDate: 2017年8月24日 下午3:46:17<br>
   * 
   * @category 检查是否返回成功
   * @author seven.gz
   */
  public static void checkGenseeReturnCode(JSONObject jsonObject) throws Exception {
    // 删除成功不会返回内容
    if (jsonObject == null || "null".equals(jsonObject.toString())) {
      return;
    }

    String code = jsonObject.getString("Code");
    String message = null;

    if (!"0".equals(code)) {
      message = jsonObject.getString("Message");
      throw new RuntimeException("调用classin接口返回失败" + message);
    }
  }

  /**
   * 
   * Title: 获取老师链接<br>
   * Description: findTeacherUrl<br>
   * CreateDate: 2017年8月25日 下午5:38:21<br>
   * 
   * @category 获取老师链接
   * @author seven.gz
   * @param roomId
   *          房间id
   */
  public static String findTeacherUrl(String roomId)
      throws Exception {
    logger.info("classin获取老师url开始");

    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("ApiKey", MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_API_KEY));
    String returnStr = HttpClientUtil.doGet(
        MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_URL) + "/api/Video/" + roomId,
        headerMap);

    JSONObject returnJson = JSONObject.fromObject(returnStr);

    checkGenseeReturnCode(returnJson);

    String teacherUrl = returnJson.getJSONObject("Data").getString("StreamPushUrl");

    logger.info("classin获取老师url结束");
    return teacherUrl;
  }

  /**
   * 
   * Title: 删除房间<br>
   * Description: deleteRoom<br>
   * CreateDate: 2017年8月25日 下午5:38:59<br>
   * 
   * @category 删除房间
   * @author seven.gz
   * @param roomId
   *          房间id
   */
  public static void deleteRoom(String roomId)
      throws Exception {
    logger.info("classin删除教室开始");

    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("ApiKey", MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_API_KEY));
    String returnStr = HttpClientUtil.doDelete(
        MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_URL) + "/api/Video/" + roomId,
        headerMap);

    JSONObject returnJson = JSONObject.fromObject(returnStr);

    checkGenseeReturnCode(returnJson);

    logger.info("classin删除教室结束");
  }

  /**
   * 
   * Title: 获取app调用信息<br>
   * Description: findAppUrl<br>
   * CreateDate: 2017年8月25日 下午8:17:54<br>
   * 
   * @category 获取app调用信息
   * @author seven.gz
   * @param roomId
   *          房间id
   * @param userId
   *          学员id
   * @param userName
   *          学员姓名
   */
  public static ClassinAppUrlParam findAppUrl(String roomId, String userId, String userName,
      String userPhoto)
          throws Exception {
    logger.info("classin获取app调用信息开始");

    String paramJsonStr = "{\"VideoClassId\":\"" + roomId + "\","
        + "\"UserId\":\"" + userId + "\","
        + "\"UserName\":\"" + userName + "\","
        + "\"Portrait\":\"" + userPhoto + "\""
        + "}";
    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("ApiKey", MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_API_KEY));
    String returnStr = HttpClientUtil.doPostByJson(
        MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_URL) + "/api/Video/join",
        paramJsonStr, headerMap);

    JSONObject returnJson = JSONObject.fromObject(returnStr);

    checkGenseeReturnCode(returnJson);
    ClassinAppUrlParam classinAppUrlParam = new ClassinAppUrlParam();

    JSONObject dataObject = returnJson.getJSONObject("Data");

    classinAppUrlParam.setSn(dataObject.getJSONObject("ClientToken").getString("sn"));
    classinAppUrlParam.setChatToken(dataObject.getJSONObject("ClientToken").getString("chatToken"));

    classinAppUrlParam.setStreamPlayUrl(jsonStringArrayToArray(dataObject.getJSONObject("ClassData")
        .getJSONArray("StreamPlayUrl")));
    classinAppUrlParam.setStreamPlayHls(
        jsonStringArrayToArray(dataObject.getJSONObject("ClassData")
            .getJSONArray("StreamPlayHls")));
    classinAppUrlParam.setId(dataObject.getJSONObject("ClassData").getString("Id"));

    logger.info("classin获取app调用信息结束");

    return classinAppUrlParam;
  }

  /**
   * 
   * Title: 将json array 转化为数组<br>
   * Description: jsonStringArrayToArray<br>
   * CreateDate: 2017年9月7日 上午11:52:27<br>
   * 
   * @category 将json array 转化为数组
   * @author seven.gz
   * @param jsonArray
   * @return
   */
  private static String[] jsonStringArrayToArray(JSONArray jsonArray) {
    String[] returnArray = null;
    if (jsonArray != null && jsonArray.size() > 0) {
      returnArray = new String[jsonArray.size()];
      for (int i = 0; i < jsonArray.size(); i++) {
        returnArray[i] = jsonArray.get(i).toString();
      }
    }
    return returnArray;
  }

  /**
   * 
   * Title: 学员进入教室<br>
   * Description: 不需要调用接口但是链接里有学员头像，比较长<br>
   * CreateDate: 2017年8月24日 下午7:50:51<br>
   * 
   * @category 学员进入教室
   * @author seven.gz
   * @param teacherTimeId
   *          老师时间id
   * @param userId
   *          学员id
   * @param userName
   *          学员名称
   * @param userPhoto
   *          学员头像
   */
  public static String goToClassinClassStudent(String roomId, String userId, String userName,
      String userPhoto)
          throws Exception {
    String md5str = Md5Util.getMd5Str(MemcachedUtil.getConfigValue(
        ConfigConstant.CLASSIN_ROOM_JOIN_HASH_KEY) + "," + roomId + "," + userId
        + ",0");

    String url = MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_URL)
        + "/room/join?VideoClassId=" + roomId + "&UserId=" + userId
        + "&UserName=" + userName + "&Portrait=" + URLEncoder.encode(userPhoto, "utf-8")
        + "&IsAdmin=0" + "&signature=" + md5str;
    return url;
  }

  /**
   * 
   * Title: 学员进入教室<br>
   * Description: 不需要调用接口但是链接里有学员头像，比较长<br>
   * CreateDate: 2017年8月24日 下午7:50:51<br>
   * 
   * @category 学员进入教室
   * @author seven.gz
   * @param teacherTimeId
   *          老师时间id
   * @param userId
   *          学员id
   * @param userName
   *          学员名称
   * @param userPhoto
   *          学员头像
   */
  public static String goToClassinClassChatTeacher(String roomId)
      throws Exception {
    String md5str = Md5Util.getMd5Str(MemcachedUtil.getConfigValue(
        ConfigConstant.CLASSIN_ROOM_JOIN_HASH_KEY) + "," + roomId + ",teacher,1");

    String url = MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_URL)
        + "/room/teacher?VideoClassId=" + roomId + "&signature=" + md5str;
    return url;
  }
}
