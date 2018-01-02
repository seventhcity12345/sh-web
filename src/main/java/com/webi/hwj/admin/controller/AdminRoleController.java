package com.webi.hwj.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.admin.service.AdminRoleService;

/**
 * @category tRole控制类
 * @author 自动生成
 *
 */
@Controller
@RequestMapping("/admin/role")
public class AdminRoleController {
  @Resource
  private AdminRoleService roleService;

  /**
   * @category tRole后台管理主页面
   * @param model
   * @return
   */
  @RequestMapping("index")
  public String index(Model model) {
    return "admin/base/admin_role";
  }

  /**
   * @category tRole保存
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/save")
  public JsonMessage save(HttpServletRequest request, ModelMap model) throws Exception {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    if (paramMap.get("key_id") != null && !"".equals(paramMap.get("key_id"))) {// 修改
      roleService.update(paramMap);
    } else {// 新增
      roleService.insert(paramMap);
    }

    return new JsonMessage();
  }

  /**
   * @category tRole查询数据列表(带分页)
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/pagelist")
  public Map<String, Object> pagelist(HttpServletRequest request,
      @RequestParam(value = "page", defaultValue = "1") Integer page,
      @RequestParam(value = "rows", defaultValue = "20") Integer rows) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();

    Page p = roleService.findPageEasyui(RequestUtil.getParameterMap(request));
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    return responseMap;
  }

  /**
   * @category tRole查询数据列表(不带分页)
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/list")
  public Map<String, Object> list(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    responseMap.put("data", roleService.findList(RequestUtil.getParameterMap(request), "*"));
    return responseMap;
  }

  /**
   * @category tRole查询数据列表(全部,不带分页)
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/all")
  public Map<String, Object> all(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    responseMap.put("data", roleService.findList(new HashMap(), "*"));
    return responseMap;
  }

  /**
   * @category 通过ID查询数据
   * @param id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/get")
  public Map<String, Object> get(HttpServletRequest request) throws Exception {
    return roleService.findOne(RequestUtil.getParameterMap(request), "*");
  }

  /**
   * @category 通过ID删除数据
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public JsonMessage delete(String ids) throws Exception {
    roleService.delete(ids);
    return new JsonMessage();
  }

  /**
   * @category 查询中间表
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findRoleResources")
  public JsonMessage findRoleResources(HttpServletRequest request, ModelMap model)
      throws Exception {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    return new JsonMessage(roleService.findRoleResources(paramMap.get("roleId").toString()));
  }

  /**
   * @category roleResources保存
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/submitRoleResources")
  public JsonMessage submitRoleResources(HttpServletRequest request, ModelMap model)
      throws Exception {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    roleService.submitRoleResources(paramMap);
    return new JsonMessage();
  }
}
