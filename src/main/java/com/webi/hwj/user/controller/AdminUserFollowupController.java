package com.webi.hwj.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.user.service.AdminUserFollowupService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category 用户跟课信息controller
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/admin/userFollowup")
public class AdminUserFollowupController {
  private static Logger logger = Logger.getLogger(AdminUserFollowupController.class);

  @Resource
  private AdminUserFollowupService adminUserFollowupService;

  /**
   * @category userFollowup后台管理主页面
   * @author mingyisoft代码生成工具
   * @param model
   * @return
   */
  @RequestMapping("/index")
  public String index(Model model) {
    return "admin/user/admin_userfollowup";
  }

  /**
   * @category userFollowup保存
   * @author mingyisoft代码生成工具
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/save")
  public JsonMessage save(HttpServletRequest request, ModelMap model) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    paramMap.put("user_id", paramMap.get("followup_user_id"));
    paramMap.remove("followup_user_id");
    paramMap.remove("followup_key_id");

    paramMap.put("learning_coach_name", sessionAdminUser.getAdminUserName());
    paramMap.put("learning_coach_id", sessionAdminUser.getKeyId());
    paramMap.put("create_user_id", sessionAdminUser.getKeyId());
    adminUserFollowupService.insert(paramMap);
    return new JsonMessage();
  }

  /**
   * @category 当前管理员名下的跟课列表
   * @author mingyisoft代码生成工具
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/pagelist")
  public Map<String, Object> pagelist(HttpServletRequest request, HttpSession session)
      throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    Page p = adminUserFollowupService.findPage(paramMap, "*");
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    return responseMap;
  }

  /**
   * @category userFollowup查询数据列表(不带分页)
   * @author mingyisoft代码生成工具
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/list")
  public JsonMessage list(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();
    try {
      json.setData(adminUserFollowupService.findList(RequestUtil.getParameterMap(request), "*"));
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("系统出现异常!");
    }
    return json;
  }

  /**
   * @category 适用于easyui下拉框
   * @author mingyisoft代码生成工具
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/selectlist")
  public List<Map<String, Object>> selectlist(HttpServletRequest request) throws Exception {
    return adminUserFollowupService.findList(RequestUtil.getParameterMap(request), "*");
  }

  /**
   * @category 通过ID查询数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/get")
  public Map<String, Object> get(HttpServletRequest request) throws Exception {
    return adminUserFollowupService.findOne(RequestUtil.getParameterMap(request), "*");
  }

  /**
   * @category 通过ID查询数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/getJsonObj")
  public JsonMessage getJsonObj(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();

    try {
      json.setData(adminUserFollowupService.findOne(RequestUtil.getParameterMap(request), "*"));
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("系统出现异常!");
    }

    return json;
  }

  /**
   * @category 通过ID删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public JsonMessage delete(String ids) throws Exception {
    adminUserFollowupService.delete(ids);
    return new JsonMessage();
  }

  /**
   * @category 查看详细信息
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  @RequestMapping("/detail/{keyId}")
  public String detail(Model model, @PathVariable(value = "keyId") String keyId) throws Exception {
    model.addAttribute("obj", adminUserFollowupService.findOneByKeyId(keyId, "*"));
    // 访问地址为 localhost:8080/hwj/demo/detail/主键
    // jsp路径为 hwj\src\main\webapp\WEB-INF\jsp\demo\demo_detail.jsp
    return "admin/admin_userfollowup_detail";
  }
}
