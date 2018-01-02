package com.webi.hwj.ordercourse.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.webi.hwj.ordercourse.service.AdminOrderCourseOptionService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category orderCourseOption控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/admin/orderCourseOption")
public class AdminOrderCourseOptionController {
  private static Logger logger = Logger.getLogger(AdminOrderCourseOptionController.class);

  @Resource
  private AdminOrderCourseOptionService adminOrderCourseOptionService;

  /**
   * @category orderCourseOption后台管理主页面
   * @author mingyisoft代码生成工具
   * @param model
   * @return
   */
  @RequestMapping("/index")
  public String index(Model model) {
    return "admin/orderCourseOption/admin_ordercourseoption";
  }

  /**
   * @category orderCourseOption保存
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

    if (paramMap.get("key_id") != null && !"".equals(paramMap.get("key_id"))) {// 修改
      paramMap.put("update_date", new Date());
      paramMap.put("update_user_id", sessionAdminUser.getKeyId());
      adminOrderCourseOptionService.update(paramMap);
    } else {// 新增
      paramMap.put("create_date", new Date());
      paramMap.put("create_user_id", sessionAdminUser.getKeyId());
      adminOrderCourseOptionService.insert(paramMap);
    }

    return new JsonMessage();
  }

  /**
   * @category orderCourseOption查询数据列表(带分页)
   * @author mingyisoft代码生成工具
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/pagelist")
  public Map<String, Object> pagelist(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();

    Page p = adminOrderCourseOptionService.findPageEasyui(RequestUtil.getParameterMap(request));
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    return responseMap;
  }

  /**
   * @category orderCourseOption查询数据列表(不带分页)
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
      json.setData(
          adminOrderCourseOptionService.findList(RequestUtil.getParameterMap(request), "*"));
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("系统出现异常!");
    }
    return json;
  }

  @ResponseBody
  @RequestMapping("/list/crm")
  public JsonMessage crmList(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();
    try {
      Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
      paramMap.remove("auth");

      json.setData(adminOrderCourseOptionService.findList(paramMap, "*"));
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
    return adminOrderCourseOptionService.findList(RequestUtil.getParameterMap(request), "*");
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
    return adminOrderCourseOptionService.findOne(RequestUtil.getParameterMap(request), "*");
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
    adminOrderCourseOptionService.delete(ids);
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
    model.addAttribute("obj", adminOrderCourseOptionService.findOneByKeyId(keyId, "*"));
    // 访问地址为 localhost:8080/hwj/demo/detail/主键
    // jsp路径为 hwj\src\main\webapp\WEB-INF\jsp\demo\demo_detail.jsp
    return "admin/admin_ordercourseoption_detail";
  }
}
