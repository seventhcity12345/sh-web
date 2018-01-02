package com.webi.hwj.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.admin.service.AdminConfigService;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.coursetype.service.CourseTypeService;
import com.webi.hwj.notice.constant.NoticeConstant;
import com.webi.hwj.notice.service.NoticeService;

/**
 * @category config控制类.
 * @author 自动生成
 *
 */
@Controller
@RequestMapping("/admin/config")
public class AdminConfigController {
  @Resource
  private AdminConfigService adminConfigService;
  @Resource
  CourseTypeService courseTypeService;
  @Resource
  NoticeService noticeService;

  /**
   * @category config后台管理主页面
   * @param model
   * @return
   */
  @RequestMapping("/index")
  public String index(Model model) {
    return "admin/base/admin_config";
  }

  /**
   * @category config保存
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @SuppressWarnings("rawtypes")
  @RequestMapping("/save")
  public JsonMessage save(HttpServletRequest request, ModelMap model) throws Exception {
    Map paramMap = RequestUtil.getParameterMap(request);
    adminConfigService.update(paramMap);

    // 放置到缓存中
    Map obj = adminConfigService.findOneByKeyId(paramMap.get("key_id"), "*");
    MemcachedUtil.setValue(obj.get("config_name") + "", paramMap.get("config_value"));
    return new JsonMessage();
  }

  /**
   * @category config查询数据列表(带分页)
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/pagelist")
  public Map<String, Object> pagelist(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();

    Page p = adminConfigService.findPageEasyui(RequestUtil.getParameterMap(request));
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

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
    return adminConfigService.findOne(RequestUtil.getParameterMap(request), "*");
  }

  /**
   * 
   * Title: 查看详情页面<br>
   * Description: 查看详情页面<br>
   * CreateDate: 2015年8月14日 下午1:43:40<br>
   * 
   * @category detail
   * @author yangmh
   * @param model
   * @param keyId
   * @return
   * @throws Exception
   */
  @RequestMapping("/detail/{keyId}")
  public String detail(Model model, @PathVariable(value = "keyId") String keyId) throws Exception {
    model.addAttribute("obj", adminConfigService.findOneByKeyId(keyId, "*"));
    // 访问地址为 localhost:8080/hwj/demo/detail/主键
    // jsp路径为 hwj\src\main\webapp\WEB-INF\jsp\demo\demo_detail.jsp
    return "admin/base/admin_config_detail";
  }

  /**
   * Title: 初始化码表缓存<br>
   * Description: initAdminConfigMemcache<br>
   * CreateDate: 2015年11月19日 下午8:55:38<br>
   * 
   * @category 初始化码表缓存
   * @author yangmh
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/initAdminConfigMemcache")
  public JsonMessage initAdminConfigMemcache() throws Exception {
    JsonMessage json = new JsonMessage();
    List<Map<String, Object>> adminConfigList = adminConfigService.findList(null, "*");
    for (Map<String, Object> configMap : adminConfigList) {
      MemcachedUtil.setValue(configMap.get("config_name").toString(),
          adminConfigService.findOne("config_name",
              configMap.get("config_name").toString(), "config_value").get("config_value"));
    }

    /**
     * modified by komi 2016年8月24日11:28:09 courseType从码表中移除，修改至从course_type表放入缓存
     */
    List<CourseType> courseTypes = courseTypeService.findList();
    if (courseTypes != null && courseTypes.size() != 0) {
      for (CourseType CourseType : courseTypes) {
        MemcachedUtil.setValue(CourseType.getCourseType(), CourseType);
      }
    } else {
      throw new Exception("没有有效的课程类型！");
    }

    /**
     * modified by komi 2016年12月19日17:15:27 增加banner缓存
     */
    noticeService.setMemcached(NoticeConstant.NOTICE_TYPE_BANNER);

    noticeService.setMemcached(NoticeConstant.NOTICE_TYPE_NOTICE);

    return json;
  }

  /**
   * 
   * Title: 查询码表中的数据<br>
   * Description: findlist<br>
   * CreateDate: 2016年4月26日 下午6:08:32<br>
   * 
   * @category 查询码表中的数据
   * @author seven.gz
   * @param 查询码表中的数据
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findCourseType")
  public List<Map<String, Object>> findCourseType(HttpServletRequest request) throws Exception {
    Map<String, Object> paraMap = RequestUtil.getParameterMap(request);
    List<Map<String, Object>> list = adminConfigService.findList(paraMap,
        " config_name,config_value ");
    return list;
  }

  /**
   * Title: 查找老师来源<br>
   * Description: 根据码表type查找老师来源<br>
   * CreateDate: 2016年12月14日 下午4:17:54<br>
   * 
   * @category 查找老师来源
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findTeacherThirdFrom")
  public List<Map<String, Object>> findTeacherThirdFrom(HttpServletRequest request)
      throws Exception {
    return adminConfigService.findTeacherThirdFrom();
  }

  /**
   * 
   * Title: 查询码表中的数据<br>
   * Description: findlist<br>
   * CreateDate: 2016年4月26日 下午6:08:32<br>
   * 
   * @category 查询码表中的数据
   * @author seven.gz
   * @param 查询码表中的数据
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findConfigByParam")
  public List<Map<String, Object>> findConfigUsingMemcached(HttpServletRequest request)
      throws Exception {
    Map<String, Object> paraMap = RequestUtil.getParameterMap(request);
    List<Map<String, Object>> list = adminConfigService.findList(paraMap,
        " config_name,config_value ");
    return list;
  }
}
