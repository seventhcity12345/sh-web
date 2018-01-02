package com.webi.hwj.courseone2one.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.courseone2one.service.AdminCourseOne2oneService;
import com.webi.hwj.teacher.param.TeacherTimeParam;
import com.webi.hwj.util.SessionUtil;

/**
 * @category courseOne2one控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("admin/courseOne2oneScheduling")
public class AdminCourseOne2oneSchedulingController {
  private static Logger logger = Logger.getLogger(AdminCourseOne2oneSchedulingController.class);
  @Resource
  private AdminCourseOne2oneService adminCourseOne2oneService;

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
    return "admin/admincourse/admin_one2one_scheduling";
  }

  /**
   * 
   * Title: 查询1v1排课信息<br>
   * Description: 查询1v1排课信息<br>
   * CreateDate: 2016年5月4日 下午4:54:18<br>
   * 
   * @category 查询1v1排课信息
   * @author seven.gz
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findSchedulingList")
  public Map<String, Object> findCourseOne2oneSchedulingList(HttpServletRequest request)
      throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询1v1排课信息...");
    Page p = adminCourseOne2oneService.findCourseOne2oneSchedulingList(paramMap);
    paramMap.put("total", p.getTotalCount());
    paramMap.put("rows", p.getDatas());
    return paramMap;
  }

  /**
   * Title: 获取次日起14天日期<br>
   * Description: 获取次日起14天日期<br>
   * CreateDate: 2016年5月5日 下午2:04:06<br>
   * 
   * @category 获取次日起14天日期
   * @author komi.zsy
   * @return
   */
  @ResponseBody
  @RequestMapping("/getDateList")
  public List<Map<String, Object>> getDateList() {
    List<Map<String, Object>> dateMapList = new ArrayList<Map<String, Object>>();
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, 1);
    for (int i = 0; i < 14; i++) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("dateStr", DateUtil.formatDate(cal.getTimeInMillis(), "yyyy-MM-dd"));
      dateMapList.add(paramMap);
      cal.add(Calendar.DATE, 1);
    }
    logger.debug("开始拉取1v1一键排课日期----->" + dateMapList);
    return dateMapList;
  }

  /**
   * Title: 1v1一键排课<br>
   * Description: 1v1一键排课<br>
   * CreateDate: 2016年5月6日 下午2:31:48<br>
   * 
   * @category 1v1一键排课
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/addOnekeyCourseOne2oneScheduling")
  public JsonMessage addOnekeyCourseOne2oneScheduling(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    JsonMessage json = new JsonMessage();
    String selectDate = request.getParameter("selectDate");
    logger.info("管理员：" + sessionAdminUser.getKeyId() + "开始1v1一键排课------->" + selectDate);

    try {
      if (adminCourseOne2oneService.insertOnekeyCourseOne2oneScheduling(selectDate,
          sessionAdminUser.getKeyId())) {
        json.setSuccess(true);
        json.setMsg("批量排课成功！");
      } else {
        json.setSuccess(true);
        json.setMsg("批量排课成功！（当前排课量已超出可用房间，请联系技术部要求增加房间）");
      }

    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("批量排课失败！");
    }

    logger.info("1v1一键排课完毕------->" + json);
    return json;
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
   * Title: 添加1v1排课<br>
   * Description: 添加1v1排课<br>
   * CreateDate: 2016年5月5日 下午4:20:30<br>
   * 
   * @category 添加1v1排课
   * @author seven.gz
   * @param request
   * @param teacherTimeParam
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/addAdminCourseOne2oneScheduling")
  public JsonMessage addAdminCourseOne2oneScheduling(HttpServletRequest request,
      @Valid TeacherTimeParam teacherTimeParam, BindingResult result) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始添加1v1排课 ...");
    teacherTimeParam.setUpdateUserId(sessionAdminUser.getKeyId());
    teacherTimeParam.setCreateUserId(sessionAdminUser.getKeyId());
    JsonMessage json = null;

    try {
      json = adminCourseOne2oneService.addCourseOne2OneScheduling(teacherTimeParam);
    } catch (Exception e) {
      logger.error("1v1排课出错：" + e.toString());
      json = new JsonMessage(false, "添加失败");
    }
    return json;
  }
}