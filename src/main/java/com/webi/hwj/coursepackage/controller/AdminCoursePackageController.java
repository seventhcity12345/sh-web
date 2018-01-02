package com.webi.hwj.coursepackage.controller;

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
import com.webi.hwj.coursepackage.param.CoursePackageAndPriceParam;
import com.webi.hwj.coursepackage.service.AdminCoursePackageService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category coursePackage控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
public class AdminCoursePackageController {
  private static Logger logger = Logger.getLogger(AdminCoursePackageController.class);

  @Resource
  private AdminCoursePackageService adminCoursePackageService;

  /**
   * @category coursePackage后台管理主页面
   * @author mingyisoft代码生成工具
   * @param model
   * @return
   */
  @RequestMapping("/admin/coursePackage/index")
  public String index(Model model) {
    return "admin/coursePackage/admin_coursepackage";
  }

  /**
   * @category coursePackage保存
   * @author mingyisoft代码生成工具
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/admin/coursePackage/save")
  public JsonMessage save(HttpServletRequest request, ModelMap model) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    if (paramMap.get("key_id") != null && !"".equals(paramMap.get("key_id"))) {
      // 修改
      paramMap.put("update_date", new Date());
      paramMap.put("update_user_id", sessionAdminUser.getKeyId());
      adminCoursePackageService.update(paramMap);
    } else {
      // 新增
      paramMap.put("create_date", new Date());
      paramMap.put("create_user_id", sessionAdminUser.getKeyId());
      adminCoursePackageService.insert(paramMap);
    }

    return new JsonMessage();
  }

  /**
   * @category coursePackage查询数据列表(带分页)
   * @author mingyisoft代码生成工具
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/admin/coursePackage/pagelist")
  public Map<String, Object> pagelist(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();

    Page p = adminCoursePackageService.findPageEasyui(RequestUtil.getParameterMap(request));
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    return responseMap;
  }

  /**
   * @category 适用于easyui下拉框
   * @author mingyisoft代码生成工具
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/coursePackage/selectlist")
  public List<CoursePackageAndPriceParam> selectlist(HttpServletRequest request, String keyId)
      throws Exception {
    return adminCoursePackageService.findList(keyId);
  }

  /**
   * Title: 课程包下拉列表<br>
   * Description: 课程包下拉列表，仅crm拟合同时调用<br>
   * CreateDate: 2016年4月25日 下午1:44:15<br>
   * 
   * @category 课程包下拉列表
   * @author ivan.mgh
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/coursePackage/selectlist/crm")
  public List<CoursePackageAndPriceParam> crmSelectlist(HttpServletRequest request, String keyId)
      throws Exception {
    // modified by ivan.mgh,2016年4月25日14:51:23
    // 为了获取授权，传入了参数auth，url:'<%=request.getContextPath()%>/admin/coursePackage/crm/selectlist?auth=${authMap.auth
    // }'，此处将auth参数清除掉

    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.clear();

    return adminCoursePackageService.findList(keyId);
  }

  /**
   * @category 通过ID查询数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/admin/coursePackage/get")
  public JsonMessage get(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();

    try {
      json.setData(adminCoursePackageService.findOne(RequestUtil.getParameterMap(request), "*"));
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("系统出现异常!");
    }

    return json;
  }

  @ResponseBody
  @RequestMapping("/admin/coursePackage/get/crm")
  public JsonMessage crmGet(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();

    try {
      Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
      paramMap.remove("auth");

      json.setData(adminCoursePackageService.findOne(paramMap, "*"));
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
  @RequestMapping("/admin/coursePackage/delete")
  @ResponseBody
  public JsonMessage delete(String ids) throws Exception {
    adminCoursePackageService.delete(ids);
    return new JsonMessage();
  }

  /**
   * @category 查看详细信息
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  @RequestMapping("/admin/coursePackage/detail/{keyId}")
  public String detail(Model model, @PathVariable(value = "keyId") String keyId) throws Exception {
    model.addAttribute("obj", adminCoursePackageService.findOneByKeyId(keyId, "*"));
    // 访问地址为 localhost:8080/hwj/demo/detail/主键
    // jsp路径为 hwj\src\main\webapp\WEB-INF\jsp\demo\demo_detail.jsp
    // return "admin/admin_coursepackage_detail";
    return "/ucenter/ordercourse/user_contract";
  }
}
