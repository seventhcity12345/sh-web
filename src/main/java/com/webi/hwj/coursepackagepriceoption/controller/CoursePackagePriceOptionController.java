package com.webi.hwj.coursepackagepriceoption.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.webi.hwj.coursepackagepriceoption.service.CoursePackagePriceOptionService;
import org.apache.log4j.Logger;

/**
 * @category coursePackagePriceOption控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/coursePackagePriceOption")
public class CoursePackagePriceOptionController {
  private static Logger logger = Logger.getLogger(CoursePackagePriceOptionController.class);
  @Resource
  private CoursePackagePriceOptionService coursePackagePriceOptionService;

}