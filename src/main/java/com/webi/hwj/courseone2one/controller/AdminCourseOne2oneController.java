package com.webi.hwj.courseone2one.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webi.hwj.courseone2one.service.AdminCourseOne2oneService;

/**
 * @category courseOne2one控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/courseOne2one")
public class AdminCourseOne2oneController {
  private static Logger logger = Logger.getLogger(AdminCourseOne2oneController.class);
  @Resource
  private AdminCourseOne2oneService adminCourseOne2oneService;

}