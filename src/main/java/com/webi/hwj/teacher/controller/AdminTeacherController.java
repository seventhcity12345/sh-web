package com.webi.hwj.teacher.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.teacher.constant.AdminTeacherConstant;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.service.AdminTeacherService;

/**
 * @category teacher控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/admin/teacher")
public class AdminTeacherController {
  private static Logger logger = Logger.getLogger(AdminTeacherController.class);
  @Resource
  private AdminTeacherService adminTeacherService;

  /**
   * Title: 后台教师管理主页<br>
   * Description: 后台教师管理主页<br>
   * CreateDate: 2016年4月11日 下午5:13:05<br>
   * 
   * @category index
   * @author komi.zsy
   * @return
   */
  @RequestMapping("/index")
  public String index() {
    return "admin/teacher/admin_teacher";
  }

  /**
   * Title: 查询教师信息<br>
   * Description: 查询教师信息<br>
   * CreateDate: 2016年4月11日 下午6:24:15<br>
   * 
   * @category 查询教师信息
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/pageList")
  public Map<String, Object> pageList(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();

    logger.info("开始抓取所有教师信息------->");

    String sort = request.getParameter("sort");
    String order = request.getParameter("order");
    String pageStr = request.getParameter("page");
    String rowsStr = request.getParameter("rows");
    Integer page = null;
    Integer rows = null;
    if (!StringUtils.isEmpty(pageStr)) {
      page = Integer.parseInt(pageStr);
    }

    if (!StringUtils.isEmpty(rowsStr)) {
      rows = Integer.parseInt(rowsStr);
    }

    Page p = adminTeacherService.findPageEasyuiTeachers(sort, order, page, rows);
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    logger.info("抓取所有教师信息完毕------->");

    return responseMap;
  }

  /**
   * Title: 编辑教师信息<br>
   * Description: 编辑教师信息<br>
   * CreateDate: 2016年4月11日 下午6:24:48<br>
   * 
   * @category 编辑教师信息
   * @author komi.zsy
   * @param request
   * @param teacher
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/editTeacher")
  public JsonMessage editTeacher(HttpServletRequest request, @Valid Teacher teacher,
      BindingResult result)
          throws Exception {
    logger.info("开始编辑教师信息------->" + teacher);
    JsonMessage json = new JsonMessage();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    try {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      MultipartFile teacherPic = (MultipartFile) multipartRequest
          .getFiles(AdminTeacherConstant.UPLOAD_FIELD_NAME)
          .get(0);

      json = adminTeacherService.editTeacher(teacher, teacherPic);
      logger.debug("修改逻辑成功！");

    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("修改失败");
      logger.error("error:" + e.getMessage(), e);
    }
    logger.info("编辑教师信息完毕------->");
    return json;
  }

  /**
   * Title: （批量）删除教师信息<br>
   * Description: （批量）删除教师信息<br>
   * CreateDate: 2016年4月11日 下午6:41:52<br>
   * 
   * @category （批量）删除教师信息
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/deleteTeacher")
  public JsonMessage batchDeleteTeacher(HttpServletRequest request) throws Exception {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    JsonMessage json = new JsonMessage();
    try {
      int count = adminTeacherService.batchDeleteTeacher(paramMap);
      json.setSuccess(true);
      json.setMsg("成功删除" + count + "条教师信息！");
      logger.debug("批量删除成功！");
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("批量删除失败");
      logger.error("批量删除失败！");
    }
    return json;
  }

  /**
   * Title: 获取教师微信绑定页面url<br>
   * Description: 获取教师微信绑定页面url<br>
   * CreateDate: 2017年6月16日 上午11:28:37<br>
   * 
   * @category 获取教师微信绑定页面url
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findTeacherWechatBindUrl")
  public CommonJsonObject findTeacherWechatBindUrl(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // 拼接教师绑定页面url
    json.setData(MemcachedUtil.getConfigValue(ConfigConstant.SPEAKHI_WEBSITE_URL)
        + MemcachedUtil.getConfigValue(ConfigConstant.TEACHER_WECHAT_BIND_URL));

    return json;
  }

  /**
   * Title: 新增教师信息<br>
   * Description: 新增教师信息<br>
   * CreateDate: 2016年4月11日 下午6:45:17<br>
   * 
   * @category 新增教师信息
   * @author komi.zsy
   * @param request
   * @param teacher
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/addTeacher")
  public JsonMessage addTeacher(HttpServletRequest request, @Valid Teacher teacher,
      BindingResult result)
          throws Exception {
    logger.info("开始新增教师信息------->" + request.getParameterMap());
    JsonMessage json = new JsonMessage();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    try {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      MultipartFile teacherPic = (MultipartFile) multipartRequest
          .getFiles(AdminTeacherConstant.UPLOAD_FIELD_NAME)
          .get(0);

      json = adminTeacherService.insert(teacher, teacherPic);
      logger.debug("新增逻辑成功！");
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("新增失败");
      logger.error("新增失败！" + e.getMessage());
    }
    logger.info("新增教师信息完毕------->" + teacher);
    return json;
  }

  /**
   * 
   * Title: 根据老师来源查找老师<br>
   * Description: 根据老师来源查找老师<br>
   * CreateDate: 2016年8月25日 下午6:56:26<br>
   * 
   * @category 根据老师来源查找老师
   * @author seven.gz
   * @param request
   * @param thirdFrom
   * @return
   */
  @ResponseBody
  @RequestMapping("/findTeacherListByThirdFrom/{thirdFrom}")
  public List<Teacher> findTeacherListByThirdFrom(HttpServletRequest request,
      @PathVariable(value = "thirdFrom") String thirdFrom) {
    List<Teacher> teacherList = null;
    try {
      teacherList = adminTeacherService.findTeacherListByThirdFrom(thirdFrom);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("根据老师来源查找老师出错 error:" + e.getMessage(), e);
    }
    return teacherList;
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
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/login")
  public CommonJsonObject<String> adminLogin(String teacherId) throws Exception {
    return adminTeacherService.adminLogin(teacherId);
  }
}