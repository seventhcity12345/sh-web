package com.webi.hwj.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mingyisoft.javabase.aliyun.ocs.OSSClientUtil;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.mingyisoft.javabase.util.SqlUtil;
import com.webi.hwj.admin.entity.BadminUser;
import com.webi.hwj.admin.service.AdminBdminUserService;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.teacher.constant.AdminTeacherConstant;
import com.webi.hwj.teacher.controller.AdminTeacherController;
import com.webi.hwj.util.CrmUtil;
import com.webi.hwj.util.SessionUtil;

/**
 * @category tUser控制类.
 * @author 自动生成
 *
 */
@Controller
@RequestMapping("/admin/adminUser")
public class AdminBadminUserController {
  private static Logger logger = Logger.getLogger(AdminBadminUserController.class);
  @Resource
  private AdminBdminUserService adminBdminUserService;

  /**
   * @category tUser主页面
   * @param model
   * @return
   */
  @RequestMapping("index")
  public String index(Model model) {
    return "admin/base/admin_badmin_user";
  }

  /**
   * @category tUser保存
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/save")
  public JsonMessage save(HttpServletRequest request, ModelMap model) throws Exception {
    JsonMessage json = new JsonMessage();

    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultipartFile bAdminPic = (MultipartFile) multipartRequest
        .getFiles(AdminTeacherConstant.UPLOAD_FIELD_NAME)
        .get(0);
    
    json = adminBdminUserService.saveAdmin(paramMap, bAdminPic);
    
    return json;
  }

  /**
   * @category tUser查询数据列表(带分页)
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/pagelist")
  public Map<String, Object> pagelist(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();

    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("admin_user_type", "notequal" + 2);

    /**
     * modified by komi 2016年8月22日15:30:33 修改为可以组合查询的方法
     */
    Page p = adminBdminUserService.findPageEasyui(paramMap);
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    return responseMap;
  }

  /**
   * @category tUser查询数据列表(不带分页)
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/list")
  public Map<String, Object> list(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    responseMap.put("data",
        adminBdminUserService.findSQLCommon(RequestUtil.getParameterMap(request), "*"));
    return responseMap;
  }

  /**
   * @category tUser查询数据列表(全部,不带分页)
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/all")
  public Map<String, Object> all(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    responseMap.put("data", adminBdminUserService.findList(new HashMap(), "*"));
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
    Map<String, Object> userMap = adminBdminUserService
        .findOne(RequestUtil.getParameterMap(request), "*");
    userMap.put("pwd", "");
    return userMap;
  }

  /**
   * @category 通过ID删除数据
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public JsonMessage delete(String ids) throws Exception {
    adminBdminUserService.delete(ids);
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
  @RequestMapping("/findUserRoles")
  public JsonMessage findUserRoles(HttpServletRequest request) throws Exception {
    Map paramMap = RequestUtil.getParameterMap(request);
    Map userMap = adminBdminUserService.findOne(paramMap, "*");
    return new JsonMessage(userMap.get("role_id"));
  }

  /**
   * @category roleResources保存
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/submitUserRoles")
  public JsonMessage submitUserRoles(HttpServletRequest request) throws Exception {
    Map paramMap = RequestUtil.getParameterMap(request);
    adminBdminUserService.submitUserRoles(paramMap);
    return new JsonMessage();
  }

  /**
   * Title: 判断当前用户是否有权限<br>
   * Description: checkAdminUserPermission<br>
   * CreateDate: 2016年3月10日 下午2:42:45<br>
   * 
   * @category 判断当前用户是否有权限
   * @author athrun.cw
   * @param request
   * @param paramMap
   * @return
   */
  @ResponseBody
  @RequestMapping("/checkAdminUserPermission")
  public JsonMessage checkAdminUserPermission(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    String permission = paramMap.get("permission") + "";
    JsonMessage json = new JsonMessage();
    json.setMsg(sessionAdminUser.isHavePermisson(permission) + "");
    return json;
  }

  /**
   * Title: 判断当前用户是否有权限，CRM专用<br>
   * Description: 判断当前用户是否有权限，CRM专用<br>
   * CreateDate: 2016年4月28日 下午12:07:57<br>
   * 
   * @category 判断当前用户是否有权限，CRM专用
   * @author ivan.mgh
   * @param request
   * @param paramMap
   * @see checkAdminUserPermission
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/checkAdminUserPermission/crm")
  public JsonMessage crmCheckAdminUserPermission(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    String permission = paramMap.get("permission").toString();

    JsonMessage json = new JsonMessage();
    json.setMsg(CrmUtil.isAdminUserHavePermisson(request, permission).toString());
    return json;
  }

  /**
   * 
   * Title: 查询值班中的lc <br>
   * Description: FindLcRota<br>
   * CreateDate: 2017年1月17日 下午1:32:00<br>
   * 
   * @category 查询值班中的lc
   * @author seven.gz
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findLcRota")
  public List<BadminUser> findLcRota(HttpServletRequest request) throws Exception {
    return adminBdminUserService.findLcRota();
  }

  /**
   * Title: 查询cc<br>
   * Description: 查询cc<br>
   * CreateDate: 2017年3月28日 下午4:53:34<br>
   * 
   * @category findAdminUserListCc
   * @author seven.gz
   */
  @ResponseBody
  @RequestMapping("/findAdminUserListCc")
  public List<BadminUser> findAdminUserListCc(HttpServletRequest request)
      throws Exception {
    return adminBdminUserService.findListCc();
  }
}
