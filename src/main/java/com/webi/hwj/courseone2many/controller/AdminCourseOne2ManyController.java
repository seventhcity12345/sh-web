package com.webi.hwj.courseone2many.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.courseone2many.entity.CourseOne2Many;
import com.webi.hwj.courseone2many.service.AdminCourseOne2ManyService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category courseOne2many控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("admin/adminCourseOne2many")
public class AdminCourseOne2ManyController {
  private static Logger logger = Logger.getLogger(AdminCourseOne2ManyController.class);
  @Resource
  private AdminCourseOne2ManyService adminCourseOne2ManyService;

  /**
   * 
   * Title: 按条件查询大课课程<br>
   * Description: 按条件查询大课课程<br>
   * CreateDate: 2016年4月27日 下午5:35:40<br>
   * 
   * @category findOne2ManyCourse
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findOne2ManyCourse")
  public List<CourseOne2Many> findOne2ManyCourse(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询大课排课信息...");
    return adminCourseOne2ManyService.findOne2ManyCourseList(paramMap);
  }
}