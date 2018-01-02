package com.webi.hwj.courseone2many.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.courseone2many.param.HuanxunCourseOne2ManySchedulingParam;
import com.webi.hwj.courseone2many.service.AdminCourseOne2ManySchedulingService;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.teacher.param.TeacherTimeSignParam;
import com.webi.hwj.util.SessionUtil;

/**
 * @category courseOne2manyScheduling控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("admin/courseOne2manyScheduling")
public class AdminCourseOne2ManySchedulingController {
  private static Logger logger = Logger.getLogger(AdminCourseOne2ManySchedulingController.class);
  @Resource
  private AdminCourseOne2ManySchedulingService adminCourseOne2ManySchedulingService;

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
  @RequestMapping("/findAll")
  public String findAll(Model model) {
    return "admin/admincourse/admin_one2many_scheduling";
  }

  /**
   * 
   * Title: 查询大课排课信息列表<br>
   * Description: 查询大课排课信息列表<br>
   * CreateDate: 2016年4月26日 上午9:26:30<br>
   * 
   * @category 查询大课排课信息列表
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findSchedulingList")
  public Map<String, Object> findSchedulingList(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询大课排课信息...");
    Page p = adminCourseOne2ManySchedulingService.findSchedulingList(paramMap);
    paramMap.put("total", p.getTotalCount());
    paramMap.put("rows", p.getDatas());
    return paramMap;
  }

  /**
   * 
   * Title: 删除排课<br>
   * Description: 删除排课<br>
   * CreateDate: 2016年4月27日 下午5:33:17<br>
   * 
   * @category 删除排课
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/deleteScheduling")
  public JsonMessage deleteCourse(HttpServletRequest request, String keyId, String teacherTimeId,
      String courseType) {
    JsonMessage json = new JsonMessage();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

    try {
      logger.info(
          "后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始删除大课排课id [" + keyId + "] ...");
      json = adminCourseOne2ManySchedulingService.deleteScheduling(keyId, teacherTimeId,
          courseType);
    } catch (Exception e) {
      logger.error("删除大课排课失败，error:" + e.getMessage());
      json.setSuccess(false);
      json.setMsg("删除大课排课失败， error:" + e.getMessage());
    }
    return json;
  }

  /**
   * 
   * Title: 根据时间课程类型查询可用的老师<br>
   * Description: 根据时间课程类型查询可用的老师<br>
   * CreateDate: 2016年4月28日 下午4:00:07<br>
   * 
   * @category 根据时间课程类型查询可用的老师
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findAvailableTeacher")
  public List<TeacherTimeSignParam> findAvailableTeacher(HttpServletRequest request)
      throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询可用老师参数 [" + paramMap + "] ...");
    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS((String) paramMap.get("startTime"));
    String courseType = (String) paramMap.get("courseType");
    int subscribeTime = ((CourseType) MemcachedUtil.getValue(courseType))
        .getCourseTypeDuration(); // 课程持续时间
    Date endTime = new Date(startTime.getTime() + subscribeTime * 60 * 1000);

    return adminCourseOne2ManySchedulingService.findAvailableTeacherByTime(
        startTime, endTime, courseType);
  }

  @InitBinder
  protected void initBinder(HttpServletRequest request,
      ServletRequestDataBinder binder) throws Exception {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    CustomDateEditor editor = new CustomDateEditor(df, false);
    binder.registerCustomEditor(Date.class, editor);
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
  @RequestMapping("/addAdminCourseOne2ManyScheduling")
  public JsonMessage addAdminCourseOne2ManyScheduling(HttpServletRequest request,
      @Valid CourseOne2ManyScheduling courseOne2ManyScheduling, BindingResult result,
      String platform)
          throws Exception {
    JsonMessage json = new JsonMessage();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始添加排课 ...");
    courseOne2ManyScheduling.setUpdateUserId(sessionAdminUser.getKeyId());
    courseOne2ManyScheduling.setCreateUserId(sessionAdminUser.getKeyId());

    try {
      json = adminCourseOne2ManySchedulingService
          .addCourseOne2ManyScheduling(courseOne2ManyScheduling, false, platform);
    } catch (Exception e) {
      logger.error("添加1vn排课失败" + e.toString());
      json = new JsonMessage(false, "添加失败");
    }
    return json;
  }

  /**
   * 
   * Title: 添加环迅排课<br>
   * Description: 添加环迅排课<br>
   * CreateDate: 2016年8月25日 下午7:57:49<br>
   * 
   * @category 添加环迅排课
   * @author seven.gz
   * @param request
   * @param huanxunCourseOne2ManySchedulingParam
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/addHuanxunCourseOne2ManyScheduling")
  public JsonMessage addHuanxunCourseOne2ManyScheduling(HttpServletRequest request,
      @Valid HuanxunCourseOne2ManySchedulingParam huanxunCourseOne2ManySchedulingParam,
      BindingResult result, String huanxunPlatform) throws Exception {
    JsonMessage json = new JsonMessage();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始添加环迅排课 ...");
    CourseOne2ManyScheduling courseOne2ManyScheduling = new CourseOne2ManyScheduling();

    // 页面逻辑比较复杂在写一套参数，在这里转化一下
    courseOne2ManyScheduling.setCourseId(huanxunCourseOne2ManySchedulingParam.getHuanxunCourseId());
    courseOne2ManyScheduling
        .setCategoryType(huanxunCourseOne2ManySchedulingParam.getHuanxunCategoryType());
    courseOne2ManyScheduling
        .setCourseLevel(huanxunCourseOne2ManySchedulingParam.getHuanxunCourseLevel());
    courseOne2ManyScheduling
        .setTeacherId(huanxunCourseOne2ManySchedulingParam.getHuanxunTeacherId());
    courseOne2ManyScheduling
        .setCourseType(huanxunCourseOne2ManySchedulingParam.getHuanxunCourseType());
    courseOne2ManyScheduling.setStartTime(huanxunCourseOne2ManySchedulingParam.getStartTime());
    courseOne2ManyScheduling.setEndTime(huanxunCourseOne2ManySchedulingParam.getEndTime());

    courseOne2ManyScheduling.setUpdateUserId(sessionAdminUser.getKeyId());
    courseOne2ManyScheduling.setCreateUserId(sessionAdminUser.getKeyId());
    try {
      json = adminCourseOne2ManySchedulingService
          .addCourseOne2ManyScheduling(courseOne2ManyScheduling, true, huanxunPlatform);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("添加环迅1vn排课失败" + e.toString());
      json = new JsonMessage(false, "添加失败");
    }
    return json;
  }

  /**
   * 
   * Title: 根据时间课程类型查询可用的老师<br>
   * Description: 根据时间课程类型查询可用的老师<br>
   * CreateDate: 2016年4月28日 下午4:00:07<br>
   * 
   * @category 根据时间课程类型查询可用的老师
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findAvailableTeacherByTeacherTime")
  public List<TeacherTimeSignParam> findAvailableTeacherByTeacherTime(HttpServletRequest request)
      throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询可用老师参数 [" + paramMap + "] ...");
    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS((String) paramMap.get("startTime"));
    String courseType = (String) paramMap.get("courseType");
    String teacherTimeId = (String) paramMap.get("teacherTimeId");
    return adminCourseOne2ManySchedulingService.findAvailableTeacherByTeacherTime(startTime,
        courseType, teacherTimeId);
  }

}
