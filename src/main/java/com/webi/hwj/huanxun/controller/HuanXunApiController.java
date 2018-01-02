/** 
 * File: HuanXunApiController.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.api.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年12月16日 下午4:43:35
 * @author yangmh
 */
package com.webi.hwj.huanxun.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.webi.hwj.huanxun.service.HuanXunApiService;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.teacher.service.TeacherService;
import com.webi.hwj.teacher.service.TeacherTimeService;

import net.sf.json.JSONObject;

/**
 * Title: 环迅接口对接<br>
 * Description: HuanXunApiController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月16日 下午4:43:35
 * 
 * @author yangmh
 */
@Controller
@RequestMapping("/api/huanxun")
public class HuanXunApiController {
  private static Logger logger = Logger.getLogger(HuanXunApiController.class);

  @Resource
  HuanXunApiService huanXunApiService;

  @Resource
  TeacherTimeService teacherTimeService;

  @Resource
  TeacherService teacherService;

  @Resource
  SubscribeCourseService subscribeCourseService;

  /**
   * 环迅与韦博通讯使用的私钥
   */
  private static final String PRIVATE_KEY = "d066fbcd244e4f888481a50ed74ad842";

  /**
   * Title: 推送老师数据接口<br>
   * Description: pullTeacherData<br>
   * CreateDate: 2015年12月21日 上午5:12:01<br>
   * 
   * @category 推送老师数据接口
   * @author yangmh
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/pullTeacherData", method = RequestMethod.POST)
  public Map<String, String> pullTeacherData(String json, String webicheck) {
    logger.info("推送老师数据接口------>start,json=" + json + ",webicheck=" + webicheck);
    Map<String, String> returnMap;

    // 通用校验
    try {
      returnMap = commonCheck(json, webicheck);
      if (returnMap != null) {
        return returnMap;
      }

      // 正式业务逻辑
      try {
        returnMap = huanXunApiService.pullTeacherData(json);
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("error:" + e.getMessage(), e);
        returnMap.put("code", "500");
        returnMap.put("msg", e.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      returnMap = new HashMap<String, String>();
      returnMap.put("code", "500");
      returnMap.put("msg", e.getMessage());
    }
    logger.info("推送老师数据接口------>end" + returnMap);
    return returnMap;
  }

  /**
   * Title: 推送老师时间数据接口<br>
   * Description: pullTeacherTimeData<br>
   * CreateDate: 2015年12月21日 上午11:20:43<br>
   * 
   * @category 推送老师时间数据接口
   * @author yangmh
   * @param json
   * @param webicheck
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/pullTeacherTimeData", method = RequestMethod.POST)
  public Map<String, String> pullTeacherTimeData(String json, String webicheck) {
    logger.info("推送老师时间数据接口------>start,json=" + json + ",webicheck=" + webicheck);
    Map<String, String> returnMap;

    try {
      // 通用校验
      returnMap = commonCheck(json, webicheck);
      if (returnMap != null) {
        return returnMap;
      }
      // 正式业务逻辑
      returnMap = huanXunApiService.pullTeacherTimeData(json);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      returnMap = new HashMap<String, String>();

      String err = e.getMessage();
      if (err.indexOf(",") != -1) {
        returnMap.put("code", err.substring(0, 3));
        returnMap.put("msg", err.substring(4));
      } else {
        returnMap.put("code", "500");
        returnMap.put("msg", err);
      }
    }
    logger.info("推送老师数据接口------>end" + returnMap);
    return returnMap;
  }

  /**
   * Title: 老师上课接口<br>
   * Description: teacherGoClass<br>
   * CreateDate: 2015年12月22日 上午10:53:07<br>
   * 
   * @category 老师上课接口
   * @author yangmh
   * @param json
   * @param webicheck
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/teacherGoClass", method = RequestMethod.POST)
  public Map<String, String> teacherGoClass(String json, String webicheck) {
    logger.info("老师上课接口new------>start,json=" + json + ",webicheck=" + webicheck);
    Map<String, String> returnMap;
    try {
      // 通用校验
      returnMap = commonCheck(json, webicheck);
      if (returnMap != null) {
        return returnMap;
      } else {
        returnMap = new HashMap<String, String>();
      }

      JSONObject ja = JSONObject.fromObject(json);
      String teacherId = ja.getString("teacher_id");
      String startTime = ja.getString("start_time");
      String endTime = ja.getString("end_time");
      String teacherName = ja.getString("teacher_name");

      if (teacherId == null || startTime == null || endTime == null || teacherName == null) {
        returnMap.put("code", "304");
        returnMap.put("msg", "参数不合法!");
        return returnMap;
      }

      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("teacher_id", teacherId);
      paramMap.put("start_time", startTime);
      paramMap.put("end_time", endTime);
      Map<String, Object> teacherTimeMap = teacherTimeService.findOne(paramMap, "key_id");
      if (teacherTimeMap == null) {
        returnMap.put("code", "500");
        returnMap.put("msg", "老师时间数据不存在！");
        return returnMap;
      }

      JsonMessage jsonMessage = subscribeCourseService
          .goToClassTeacher(teacherTimeMap.get("key_id").toString(), teacherId, teacherName, true);

      if (jsonMessage.isSuccess()) {
        logger.info("vcube进入房间返回------>" + jsonMessage.getMsg());
        returnMap.put("code", "200");
        returnMap.put("msg", jsonMessage.getMsg());
      } else {
        // modify by seven 2017年5月23日14:02:40 修改日志级别
        logger.info("vcube进入房间返回------>" + jsonMessage.getMsg());
        returnMap.put("code", "500");
        returnMap.put("msg", "房间进入错误！");
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      returnMap = new HashMap<String, String>();
      returnMap.put("code", "500");
      returnMap.put("msg", e.getMessage());
    }

    logger.info("老师上课接口------>end" + returnMap);
    return returnMap;
  }

  /**
   * Title: 老师评论接口<br>
   * Description: teacherGoClass<br>
   * CreateDate: 2015年12月22日 上午10:53:07<br>
   * 
   * @category 老师评论接口
   * @author yangmh
   * @param json
   * @param webicheck
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/teacherGoComment", method = RequestMethod.POST)
  public Map<String, String> teacherGoComment(String json, String webicheck, HttpSession session) {
    logger.info("老师评论接口------>start,json=" + json + ",webicheck=" + webicheck);
    Map<String, String> returnMap;
    try {
      // 通用校验
      returnMap = commonCheck(json, webicheck);
      if (returnMap != null) {
        return returnMap;
      } else {
        returnMap = new HashMap<String, String>();
      }

      JSONObject ja = JSONObject.fromObject(json);
      String teacherId = ja.getString("teacher_id");

      if (teacherId == null) {
        returnMap.put("code", "304");
        returnMap.put("msg", "参数不合法!");
        return returnMap;
      }

      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("key_id", teacherId);
      paramMap.put("third_from", "huanxun");

      Map<String, Object> teacherMap = teacherService.findOne(paramMap,
          "key_id,account,teacher_name,teacher_photo,teacher_desc,third_from,teacher_course_type");
      if (teacherMap == null) {
        returnMap.put("code", "302");
        returnMap.put("msg", "");
        return returnMap;
      }

      // 环境
      String env = MemcachedUtil.getConfigValue("env");
      logger.info("老师评论接口------->环境" + env);

      if ("pro".equals(env)) {
        // 生产环境
        returnMap.put("msg",
            "http://speakhi.com/web/teacher/course_comment.html?third=" + teacherId);
      } else {
        // 测试环境
        returnMap.put("msg",
            "http://test.speakhi.com/web/teacher/course_comment.html?third="
                + teacherId);
        // returnMap.put("msg",
        // "http://localhost:8899/tcenter/teacherCourseComment/commentCenterPage?third="+teacherId);
      }

      returnMap.put("code", "200");
      // session超时时间
      /**
       * modify by seven 2017年9月12日15:55:16 这里只需要id就好了，不知道为什么会放一个对象
       */
      MemcachedUtil.setValue("thirdTeacherLogin_" + teacherId, teacherId, 60 * 60);

      logger.info("返回值------>返回值：" + returnMap);

      return returnMap;
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      returnMap = new HashMap<String, String>();
      returnMap.put("code", "500");
      returnMap.put("msg", e.getMessage());
    }

    logger.info("老师评论接口------>end" + returnMap);
    return returnMap;
  }

  /**
   * Title: 通用校验<br>
   * Description: commonCheck<br>
   * CreateDate: 2015年12月22日 上午10:56:23<br>
   * 
   * @category commonCheck
   * @author yangmh
   * @param json
   * @param webicheck
   * @return
   */
  private Map<String, String> commonCheck(String json, String webicheck) {
    Map<String, String> returnMap = new HashMap<String, String>();
    // 判断参数是否非空
    if (json == null || webicheck == null) {
      returnMap.put("code", "304");
      returnMap.put("msg", "");
      return returnMap;
    }

    // 判断数据是否被黑客修改过
    if (!SHAUtil.encode(json + PRIVATE_KEY).equals(webicheck)) {
      returnMap.put("code", "301");
      returnMap.put("msg", "");
      return returnMap;
    }
    return null;
  }

}
