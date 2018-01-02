package com.webi.hwj.teacher.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.teacher.param.FindTimesAndTeachersByDayParam;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category teacherTime控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/admin/teacherTime")
public class AdminTeacherTimeController {
  private static Logger logger = Logger.getLogger(AdminTeacherTimeController.class);
  @Resource
  private TeacherTimeService teacherTimeService;

  /**
   * @category 对老师评论页面
   * @author 对老师评论页面
   * @param model
   * @return
   */
  @RequestMapping("/subscribeTeacherTime")
  public String subscribeTeacherTime(Model model) {
    return "admin/teacher/admin_subscribe_teacher_time";
  }

  /**
   * 
   * Title: 查询老师上课表信息<br>
   * Description: 查询老师上课表信息<br>
   * CreateDate: 2016年7月25日 下午3:06:48<br>
   * 
   * @category 查询老师上课表信息
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/findSubscribeTeacherTimeList")
  public Map<String, Object> findSubscribeTeacherTimeList(HttpServletRequest request) {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询对老师上课信息...");
    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS((String) paramMap.get("startTime"));
    Date endTime = DateUtil.strToDateYYYYMMDDHHMMSS((String) paramMap.get("endTime"));
    Page page = null;
    try {
      page = teacherTimeService.findSubscribeTeacherTimeList(paramMap, startTime, endTime);
      if (page != null) {
        paramMap.put("total", page.getTotalCount());
        paramMap.put("rows", page.getDatas());
      }
    } catch (Exception e) {
      logger.error("查询老师老师上课信息出错：" + e.getMessage(), e);
    }
    return paramMap;
  }

  /**
   * 
   * Title: 查询拥有某些权限的老师时间,demo课程查询<br>
   * Description: 用于demo订课<br>
   * CreateDate: 2016年12月22日 下午9:16:34<br>
   * 
   * @category 查询拥有某些权限的老师时间,demo课程查询
   * @author seven.gz
   * @param request
   * @param courseType
   * @param selectDay
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findTimesAndTeachersByDayAndCourseType")
  public List<FindTimesAndTeachersByDayParam> findTimesAndTeachersByDayAndCourseType(
      HttpServletRequest request, String courseType,
      String selectDay, String webexRoomHostId) throws Exception {
    return teacherTimeService.findTimesAndTeachersByDayAndCourseType(selectDay, courseType,
        webexRoomHostId);
  }
}
