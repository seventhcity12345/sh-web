package com.webi.hwj.course.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.entity.AdminCourse;
import com.webi.hwj.course.service.AdminCourseService;
import com.webi.hwj.courseone2many.param.SchedulingChangeTeacherParam;
import com.webi.hwj.courseone2one.entity.CourseOne2One;
import com.webi.hwj.courseone2one.service.AdminCourseOne2oneService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category 后台管理-课程管理
 * @author seven.gz
 *
 */
@Controller
@RequestMapping("/admin/course")
public class AdminCourseController {
  private static Logger logger = Logger.getLogger(AdminCourseController.class);
  @Resource
  private AdminCourseService adminCourseAllService;
  @Resource
  AdminCourseOne2oneService adminCourseOne2oneService;

  @InitBinder
  protected void initBinder(HttpServletRequest request,
      ServletRequestDataBinder binder) throws Exception {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    CustomDateEditor editor = new CustomDateEditor(df, false);
    binder.registerCustomEditor(Date.class, editor);
  }

  /**
   * 
   * Title: 查询课程页面<br>
   * Description: 查询课程页面<br>
   * CreateDate: 2016年4月12日 上午10:01:06<br>
   * 
   * @category 查询课程页面
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/index")
  public String index(Model model) {
    return "admin/admincourse/admin_course";
  }

  /**
   * Title: 排课后更换老师<br>
   * Description: 排课后更换老师<br>
   * CreateDate: 2016年10月27日 上午11:32:12<br>
   * 
   * @category 排课后更换老师
   * @author komi.zsy
   * @param request
   * @param schedulingChangeTeacherParam
   * @param result
   * @return
   */
  @ResponseBody
  @RequestMapping("/changeTeacher")
  public CommonJsonObject changeTeacher(HttpServletRequest request,
      @Valid SchedulingChangeTeacherParam schedulingChangeTeacherParam, BindingResult result) {
    CommonJsonObject json = new CommonJsonObject();

    // 表单校验框架
    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    try {
      // 更换老师
      json = adminCourseAllService.changeSchedulingTeacher(schedulingChangeTeacherParam);

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * 
   * Title: 查询所有课程信息<br>
   * Description: 查询所有课程信息<br>
   * CreateDate: 2016年4月12日 上午9:40:04<br>
   * 
   * @category 查询所有课程信息
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/findAllCourseList")
  public Map<String, Object> findAllCourseList(HttpServletRequest request) {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询所有课程信息...");
    Page p;
    try {
      p = adminCourseAllService.findAllCourse(paramMap);
      if (p != null) {
        paramMap.put("total", p.getTotalCount());
        paramMap.put("rows", p.getDatas());
      }
    } catch (Exception e) {
      logger.error("查询课程信息列表出错:" + e.getMessage(), e);
    }
    return paramMap;
  }

  /**
   * 
   * Title: 删除课程<br>
   * Description: 删除课程<br>
   * CreateDate: 2016年4月13日 上午9:34:42<br>
   * 
   * @category 删除课程
   * @author seven.gz
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping("/deleteCourse")
  public JsonMessage deleteCourse(HttpServletRequest request, String keyId, String courseType) {
    JsonMessage json = new JsonMessage();

    if (!StringUtils.isEmpty(keyId) && !StringUtils.isEmpty(courseType)) {
      SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
      try {
        logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始删除课程id [" + keyId + "] ...");
        json = adminCourseAllService.deleteCourseByKeyIdAndType(keyId, courseType,
            sessionAdminUser.getKeyId());
      } catch (Exception e) {
        logger.error("删除课程失败，error:" + e.getMessage());
        json.setSuccess(false);
        json.setMsg("删除课程失败， error:" + e.getMessage());
      }
    } else {
      json.setMsg("参数不合法!!!");
      json.setSuccess(false);
    }

    return json;

  }

  /**
   * 
   * Title: 添加课程<br>
   * Description: 添加课程<br>
   * CreateDate: 2016年4月13日 下午5:19:31<br>
   * 
   * @category 添加课程
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/addCourse")
  public JsonMessage addCourse(HttpServletRequest request, @Valid AdminCourse course,
      BindingResult result)
          throws Exception {
    JsonMessage json = new JsonMessage();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    Map<String, MultipartFile> fileMap = handleRequestToFile(request);
    MultipartFile courseCoursewareFile = fileMap.get("courseCoursewareFile");
    MultipartFile coursePicFile = fileMap.get("coursePicFile");

    if (coursePicFile == null) {
      json.setSuccess(false);
      json.setMsg("请上传图片!");
    } else {
      // 控制图片不能大于5M
      if (coursePicFile.getSize() > 5 * 1024 * 1024) {
        json.setSuccess(false);
        json.setMsg("图片不能大于5M!");
        return json;
      }
    }

    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始添加课程 ...");
    course.setUpdateUserId(sessionAdminUser.getKeyId());
    course.setCreateUserId(sessionAdminUser.getKeyId());

    try {
      json = adminCourseAllService.addCourse(course, courseCoursewareFile, coursePicFile);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("图片上传失败");
    }
    return json;
  }

  /**
   * 
   * Title: 修改课程<br>
   * Description: 修改课程<br>
   * CreateDate: 2016年4月13日 下午5:19:31<br>
   * 
   * @category 添加课程
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/modifyCourse")
  public JsonMessage modifyCourse(HttpServletRequest request, @Valid AdminCourse course,
      BindingResult result)
          throws Exception {
    JsonMessage json = new JsonMessage();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    Map<String, MultipartFile> fileMap = handleRequestToFile(request);
    MultipartFile courseCoursewareFile = fileMap.get("courseCoursewareFile");
    MultipartFile coursePicFile = fileMap.get("coursePicFile");

    if (coursePicFile != null) {
      // 控制图片不能大于5M
      if (coursePicFile.getSize() > 5 * 1024 * 1024) {
        json.setSuccess(false);
        json.setMsg("图片不能大于5M");
        return json;
      }
    }

    try {
      SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
      logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始修改课程 ...");
      course.setUpdateUserId(sessionAdminUser.getKeyId());
      adminCourseAllService.modifyCourse(course, courseCoursewareFile, coursePicFile);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg(e.toString());
    }

    return json;
  }

  /**
   * Title: 封装新增/修改课件时的文件判断部分<br>
   * Description: handleRequestToFile<br>
   * CreateDate: 2016年5月24日 下午11:06:51<br>
   * 
   * @category 封装新增/修改课件时的文件判断部分
   * @author seven.gz
   * @param request
   * @return
   */
  private Map<String, MultipartFile> handleRequestToFile(HttpServletRequest request) {
    Map<String, MultipartFile> returnMap = new HashMap<String, MultipartFile>();
    MultipartFile courseCoursewareFile = null;
    MultipartFile coursePicFile = null;

    if ((request instanceof MultipartHttpServletRequest)) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      courseCoursewareFile = (MultipartFile) multipartRequest.getFiles("courseCoursewareFile")
          .get(0);
      coursePicFile = (MultipartFile) multipartRequest.getFiles("coursePicFile").get(0);

      if (courseCoursewareFile != null && courseCoursewareFile.getSize() == 0) {
        courseCoursewareFile = null;
      }

      if (coursePicFile != null && coursePicFile.getSize() == 0) {
        coursePicFile = null;
      }

      returnMap.put("courseCoursewareFile", courseCoursewareFile);
      returnMap.put("coursePicFile", coursePicFile);
    }
    return returnMap;
  }

  /**
   * 
   * Title: NoShow管理页面<br>
   * Description: NoShow管理页面<br>
   * CreateDate: 2016年7月4日 下午1:34:50<br>
   * 
   * @category NoShow管理页面
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/noShowIndex")
  public String noShowIndex(Model model) {
    return "admin/admincourse/admin_noshow_course";
  }

  /**
   * 
   * Title: 查询noshow课程信息<br>
   * Description: 查询noshow课程信息<br>
   * CreateDate: 2016年7月4日 下午2:43:37<br>
   * 
   * @category 查询noshow课程信息
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/findNoShowCoursePage")
  public Map<String, Object> findNoShowCoursePage(HttpServletRequest request, boolean findAllFlag) {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询noshow课程信息...");
    Page p = null;
    try {
      String learningCoachId = null;
      if (!findAllFlag) {
        learningCoachId = sessionAdminUser.getKeyId();
      }
      p = adminCourseAllService.findNoShowCoursePage(paramMap, learningCoachId);
      if (p != null) {
        paramMap.put("total", p.getTotalCount());
        paramMap.put("rows", p.getDatas());
      }
    } catch (Exception e) {
      logger.error("查询课程信息列表出错:" + e.getMessage(), e);
    }
    return paramMap;
  }

  /**
   * 
   * Title: 查找demo课程<br>
   * Description: findListDemo<br>
   * CreateDate: 2016年12月22日 下午8:01:19<br>
   * 
   * @category 查找demo课程
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findListDemo")
  public List<CourseOne2One> findListDemo(HttpServletRequest request, String courseType)
      throws Exception {
    List<CourseOne2One> courseList = adminCourseOne2oneService
        .findListCourseByCourseType(courseType);
    return courseList;
  }
}