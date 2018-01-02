package com.webi.hwj.coursepackageprice.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursepackageprice.constant.CoursePackagePriceConstant;
import com.webi.hwj.coursepackageprice.entity.CoursePackagePrice;
import com.webi.hwj.coursepackageprice.service.CoursePackagePriceService;

/**
 * @category coursePackagePrice控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
public class CoursePackagePriceController {
  private static Logger logger = Logger.getLogger(CoursePackagePriceController.class);
  @Resource
  private CoursePackagePriceService coursePackagePriceService;

  /**
   * Title: 增加价格策略 <br>
   * Description: 增加价格策略 <br>
   * CreateDate: 2017年8月16日 下午4:33:50<br>
   * 
   * @category 增加价格策略
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/coursePackagePrice/addCoursePackagePrice")
  public CommonJsonObject<Object> addCoursePackagePrice(HttpServletRequest request)
      throws Exception {
    CommonJsonObject<Object> json = new CommonJsonObject<Object>();

    String loadAddress = "E:\\CoursePackagePriceData.txt";
    // 如果是生产环境
    if ("test".equals(MemcachedUtil.getConfigValue("env")) || "pro".equals(MemcachedUtil
        .getConfigValue("env"))) {
      loadAddress = File.separator + "usr" + File.separator + "CoursePackagePriceData.txt";
    }

    coursePackagePriceService.addCoursePackagePrice(loadAddress);
    return json;
  }

  /**
   * Title: 查找当前时间生效的价格政策<br>
   * Description: 查找当前时间生效的价格政策(crm专用)<br>
   * CreateDate: 2016年8月29日 下午4:31:57<br>
   * 
   * @category 查找当前时间生效的价格政策
   * @author komi.zsy
   * @return
   */
  @ResponseBody
  @RequestMapping("/coursePackagePrice/findListByTime")
  public List<CoursePackagePrice> findListByTime(HttpServletRequest request) throws Exception {
    return coursePackagePriceService.findListByTime(new Date(), "%" + CoursePackagePriceConstant.PACKAGE_PRICE_LINE_TYPE + "%");
  }

  /**
   * Title: 查找当前时间生效的价格政策<br>
   * Description: 查找当前时间生效的价格政策<br>
   * CreateDate: 2016年8月29日 下午4:31:57<br>
   * 
   * @category 查找当前时间生效的价格政策
   * @author komi.zsy
   * @return
   */
  @ResponseBody
  @RequestMapping("/admin/coursePackagePrice/findListByTime")
  public List<CoursePackagePrice> findListByTimeAndAdmin(HttpServletRequest request)
      throws Exception {
    return coursePackagePriceService.findListByTime(new Date(), "%" + CoursePackagePriceConstant.PACKAGE_PRICE_ONLINE_TYPE + "%");
  }
}