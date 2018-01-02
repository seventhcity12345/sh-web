package com.webi.hwj.courseprice.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webi.hwj.courseprice.entity.CoursePrice;
import com.webi.hwj.courseprice.param.CoursePriceParam;
import com.webi.hwj.courseprice.service.CoursePriceService;

/**
 * @category coursePrice控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/coursePrice")
public class CoursePriceController {
  private static Logger logger = Logger.getLogger(CoursePriceController.class);
  @Resource
  private CoursePriceService coursePriceService;

  /**
   * Title: 根据课程类型和价格版本号查找课程类型类型和课程单价<br>
   * Description: 根据课程类型和价格版本号查找课程类型类型和课程单价<br>
   * CreateDate: 2016年8月30日 下午3:15:40<br>
   * 
   * @category 根据课程类型和价格版本号查找课程类型类型和课程单价
   * @author komi.zsy
   * @param request
   * @param paramObj
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findCourseUnitAndPriceListByVersion")
  public List<CoursePriceParam> findCourseUnitAndPriceListByVersion(HttpServletRequest request,
      CoursePriceParam paramObj) throws Exception {
    return coursePriceService.findCourseUnitAndPriceListByVersion(paramObj);
  }
}