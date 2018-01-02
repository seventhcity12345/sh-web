package com.webi.hwj.admin.controller;

import java.util.HashMap;
import java.util.List;
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
import com.webi.hwj.admin.service.AdminResourceService;

/**
 * @category tResource控制类
 * @author 自动生成
 *
 */
@Controller
@RequestMapping("/admin/resource")
public class AdminResourceController {
  @Resource
  private AdminResourceService resourceService;

  /**
   * @category tResource后台管理主页面
   * @param model
   * @return
   */
  @RequestMapping("index")
  public String index(Model model) {
    return "admin/base/admin_resource";
  }

  /**
   * @category tResource保存
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/save")
  public JsonMessage save(HttpServletRequest request, ModelMap model) throws Exception {
    Map paramMap = RequestUtil.getParameterMap(request);

    paramMap.remove("pname");

    if (paramMap.get("key_id") != null && !"".equals(paramMap.get("key_id"))) {// 修改
      resourceService.update(paramMap);
    } else {// 新增
      resourceService.insert(paramMap);
    }

    return new JsonMessage();
  }

  /**
   * @category tResource查询数据列表(带分页)
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

    Page p = resourceService.findPageEasyui(RequestUtil.getParameterMap(request));
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    return responseMap;
  }

  /**
   * @category tResource查询数据列表(不带分页)
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/list")
  public Map<String, Object> list(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    responseMap.put("data", resourceService.findList(RequestUtil.getParameterMap(request), "*"));
    return responseMap;
  }

  /**
   * @category tResource查询数据列表(全部,不带分页)
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/all")
  public Map<String, Object> all(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    responseMap.put("data", resourceService.findList(new HashMap(), "*"));
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
    return resourceService.findOne(RequestUtil.getParameterMap(request), "*");
  }

  /**
   * @category 通过ID删除数据
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public JsonMessage delete(String ids) throws Exception {
    resourceService.delete(ids);
    return new JsonMessage();
  }

  /**
   * @category 资源tree
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping("tree")
  public List<Map<String, Object>> tree(String key_id) throws Exception {
    return resourceService.tree(key_id);
  }

  /**
   * @category 排序
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/sort")
  public JsonMessage sort(HttpServletRequest request, ModelMap model) throws Exception {
    resourceService.sort(RequestUtil.getParameterMap(request));
    return new JsonMessage();
  }
}
