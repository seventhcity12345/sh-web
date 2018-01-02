package com.webi.hwj.course.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.course.service.AdminSubscribeSupplementService;
import com.webi.hwj.user.service.AdminUserService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category subscribeSupplement控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/admin/subscribeSupplement")
public class AdminSubscribeSupplementController {
  private static Logger logger = Logger.getLogger(AdminSubscribeSupplementController.class);

  @Resource
  private AdminSubscribeSupplementService adminSubscribeSupplementService;

  @Resource
  private AdminUserService adminUserService;

  /**
   * Title: 补课<br>
   * Description: subscribeCourseSupplement<br>
   * CreateDate: 2015年12月1日 下午5:46:45<br>
   * 
   * @category 补课
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/supplyCourse")
  public JsonMessage supply(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("admin_user_id", sessionAdminUser.getKeyId());
    paramMap.put("admin_user_name", sessionAdminUser.getAdminUserName());
    try {
      logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始帮助学员id ["
          + paramMap.get("supplement_user_id")
          + "] 补课，课程id [" + paramMap.get("supplement_course_id") + "] ...");
      json = adminSubscribeSupplementService.subscribeCourseSupplement(paramMap);
      logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 帮助学员补课成功！");
      json.setMsg("补课成功！");
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("补课失败！" + e.getMessage());
      logger.error("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 补课失败，错误error:" + e.getMessage());
    }
    return json;
  }
}
