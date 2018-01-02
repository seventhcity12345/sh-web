package com.webi.hwj.course.controller;

import java.util.Date;
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
import com.webi.hwj.course.service.AdminCourseCommentService;
import com.webi.hwj.util.SessionUtil;

/**
 * 
 * Title: 后台查看老师评论相关内容<br>
 * Description: 后台查看老师评论相关内容<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月21日 下午3:55:43
 * 
 * @author seven.gz
 */
@Controller
@RequestMapping("/admin/courseComment")
public class AdminCourseCommentController {
  private static Logger logger = Logger.getLogger(AdminCourseCommentController.class);

  @Resource
  AdminCourseCommentService adminCourseCommentService;

  /**
   * @category 老师平均分页面
   * @author 老师平均分页面
   * @param model
   * @return
   */
  @RequestMapping("/teacherAverageScrore")
  public String teacherAverageScrore(Model model) {
    return "admin/coursecomment/admin_teacher_average_score";
  }

  /**
   * @category 对老师评论页面
   * @author 对老师评论页面
   * @param model
   * @return
   */
  @RequestMapping("/teacherCourseComment")
  public String teacherCourseComment(Model model) {
    return "admin/coursecomment/admin_teacher_course_comment";
  }

  /**
   * 
   * Title: 查询老师评论平均分信息<br>
   * Description: 查询老师评论平均分信息<br>
   * CreateDate: 2016年7月21日 下午7:40:36<br>
   * 
   * @category 查询老师评论平均分信息
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/findTeacherAverageScrorePage")
  public Map<String, Object> findTeacherAverageScrorePage(HttpServletRequest request) {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询老师平均分信息...");
    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS((String) paramMap.get("startTime"));
    Date endTime = DateUtil.strToDateYYYYMMDDHHMMSS((String) paramMap.get("endTime"));
    Page page = null;
    try {
      page = adminCourseCommentService.findTeacherAverageScrore(paramMap, startTime, endTime);
      if (page != null) {
        paramMap.put("total", page.getTotalCount());
        paramMap.put("rows", page.getDatas());
      }
    } catch (Exception e) {
      logger.error("查询老师平均分信息出错：" + e.getMessage(), e);
    }
    return paramMap;
  }

  /**
   * 
   * Title: 查询对老师的评论<br>
   * Description: 查询对老师的评论<br>
   * CreateDate: 2016年7月23日 上午10:12:32<br>
   * 
   * @category 查询对老师的评论
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/findTeacherCourseCommentList")
  public Map<String, Object> findTeacherCourseCommentList(HttpServletRequest request) {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询对老师评论信息...");
    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS((String) paramMap.get("startTime"));
    Date endTime = DateUtil.strToDateYYYYMMDDHHMMSS((String) paramMap.get("endTime"));
    Page page = null;
    try {
      page = adminCourseCommentService.findTeacherCourseCommentList(paramMap, startTime, endTime);
      if (page != null) {
        paramMap.put("total", page.getTotalCount());
        paramMap.put("rows", page.getDatas());
      }
    } catch (Exception e) {
      logger.error("查询老师平均分信息出错：" + e.getMessage(), e);
    }
    return paramMap;
  }

}
