package com.webi.hwj.coursepackage.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.coursepackage.service.AdminCoursePackageOptionService;
import com.webi.hwj.coursepackagepriceoption.entity.CoursePackagePriceOption;

/**
 * @category coursePackageOption控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
public class AdminCoursePackageOptionController {
  private static Logger logger = Logger.getLogger(AdminCoursePackageOptionController.class);

  @Resource
  private AdminCoursePackageOptionService adminCoursePackageOptionService;

  /**
   * Title: 查找课程包子表以及相对于的小维度价格<br>
   * Description: 查找课程包子表以及相对于的小维度价格<br>
   * CreateDate: 2016年8月30日 上午11:03:14<br>
   * 
   * @category 查找课程包子表以及相对于的小维度价格
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/coursePackageOption/findOptionAndPirceList")
  public JsonMessage findOptionAndPirceList(HttpServletRequest request,
      CoursePackagePriceOption coursePackagePriceOption) throws Exception {
    JsonMessage json = new JsonMessage();
    try {
      json.setData(adminCoursePackageOptionService.findOptionAndPirceList(
          coursePackagePriceOption.getCoursePackageId(),
          coursePackagePriceOption.getCoursePriceVersion()));
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.toString(), e);
      json.setSuccess(false);
      json.setMsg("系统出现异常!");
    }
    return json;
  }

  /**
   * Title: (crm)查找课程包子表以及相对于的小维度价格<br>
   * Description: (crm)查找课程包子表以及相对于的小维度价格<br>
   * CreateDate: 2016年8月30日 上午11:03:14<br>
   * 
   * @category (crm)查找课程包子表以及相对于的小维度价格
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
//  @ResponseBody
//  @RequestMapping("/coursePackageOption/findOptionAndPirceList/crm")
//  public JsonMessage findOptionAndPirceListCrm(HttpServletRequest request, String coursePackageId,
//      Integer coursePriceVersion) throws Exception {
//    return findOptionAndPirceList(request, coursePackageId, coursePriceVersion);
//  }

  /**
   * Title: English Studio & RSA & 微课，单价之和<br>
   * Description: English Studio & RSA & 微课，计算单价之和<br>
   * CreateDate: 2016年5月20日 上午10:21:00<br>
   * 
   * @category English Studio & RSA & 微课，单价之和
   * @author ivan.mgh
   * @return
   */
  @RequestMapping("/admin/coursePackageOption/sumEnglishStudioRsaWeikeUnitPrice")
  @ResponseBody
  public JsonMessage sumEnglishStudioRsaWeikeUnitPrice() {
    return doSumEnglishStudioRsaWeikeUnitPrice();
  }

  /**
   * Title: English Studio & RSA & 微课，单价之和（crm专用）<br>
   * Description: English Studio & RSA & 微课，计算单价之和<br>
   * CreateDate: 2016年5月20日 上午10:21:00<br>
   * 
   * @category English Studio & RSA & 微课，单价之和
   * @author ivan.mgh
   * @return
   */
  @RequestMapping("/admin/coursePackageOption/sumEnglishStudioRsaWeikeUnitPrice/crm")
  @ResponseBody
  public JsonMessage crmSumEnglishStudioRsaWeikeUnitPrice() {
    return doSumEnglishStudioRsaWeikeUnitPrice();
  }

  /**
   * Title: 公用方法，计算English Studio & RSA & 微课，单价之和<br>
   * Description: doSumEnglishStudioRsaWeikeUnitPrice<br>
   * CreateDate: 2016年5月20日 上午10:23:42<br>
   * 
   * @category 公用方法，计算English Studio & RSA & 微课，单价之和
   * @author ivan.mgh
   * @return
   */
  private JsonMessage doSumEnglishStudioRsaWeikeUnitPrice() {
    JsonMessage jsonMessage = new JsonMessage();

    try {
      Integer sumUnitPrice = adminCoursePackageOptionService.sumEnglishStudioRsaWeikeUnitPrice();
      jsonMessage.setData(sumUnitPrice);

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      jsonMessage.setSuccess(false);
      jsonMessage.setMsg(e.toString());
    }

    return jsonMessage;
  }

}
