package com.webi.hwj.courseextension1v1.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.courseextension1v1.service.CourseExtension1v1Service;
import com.webi.hwj.util.SessionUtil;

/**
 * @category courseExtension1v1控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
// @RequestMapping("/courseExtension1v1")
public class CourseExtension1v1Controller {
  private static Logger logger = Logger.getLogger(CourseExtension1v1Controller.class);
  @Resource
  private CourseExtension1v1Service courseExtension1v1Service;

  /**
   * Title: extension1v1课程列表接口<br>
   * Description: extension1v1课程列表接口<br>
   * CreateDate: 2016年9月6日 下午2:49:18<br>
   * 
   * @category extension1v1课程列表接口
   * @author komi.zsy
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("ucenter/course/findCourseType9List")
  public CommonJsonObject findCourseType1List(HttpServletRequest request) {
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    try {
      logger.info("用户 [" + sessionUser.getKeyId() + "开始查询CourseType9列表...");

      Map<String, Object> returnMap = new HashMap<String, Object>();

      // 查询CourseType1课程列表以及rsa课程进度
      returnMap.put("courseType9List", courseExtension1v1Service.findCourseType9List(sessionUser));

      json.setData(returnMap);

    } catch (Exception e) {
      logger.error("选择CourseType9课程出错", e);
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }
}