package com.webi.hwj.user.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.user.service.AdminKeyFollowupService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category 用户跟课信息controller
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/admin/keyFollowup")
public class AdminKeyFollowupController {
  private static Logger logger = Logger.getLogger(AdminKeyFollowupController.class);

  @Resource
  AdminKeyFollowupService adminKeyFollowupService;

  /**
   * @category 新生跟进页面
   * @author 新生跟进页面
   * @param model
   * @return
   */
  @RequestMapping("/newStudent")
  public String newStudent(Model model) {
    return "admin/keyfollowup/admin_new_student";
  }

  /**
   * 
   * Title: 本月follow次数少于两次<br>
   * Description: 本月follow次数少于两次<br>
   * CreateDate: 2016年7月6日 上午11:39:02<br>
   * 
   * @category 本月follow次数少于两次
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/lessFollowup")
  public String lessFollowup(Model model) {
    return "admin/keyfollowup/admin_less_followup_student";
  }

  /**
   * 
   * Title: 两个月内结束合同<br>
   * Description: 两个月内结束合同<br>
   * CreateDate: 2016年7月6日 上午11:39:02<br>
   * 
   * @category 本月follow次数少于两次
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/twoMonthEnd")
  public String twoMonthEnd(Model model) {
    return "admin/keyfollowup/admin_two_month_end";
  }

  /**
   * 
   * Title: 合同超过一个月但没有做过rsa<br>
   * Description: 合同超过一个月但没有做过rsa<br>
   * CreateDate: 2016年7月6日 上午11:39:02<br>
   * 
   * @category 合同超过一个月但没有做过ras
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/neverDoneRsa")
  public String neverDoneRsa(Model model) {
    return "admin/keyfollowup/admin_never_done_rsa";
  }

  /**
   * 
   * Title: 上过课但是一个月没做过课件<br>
   * Description: 上过课但是一个月没做过课件<br>
   * CreateDate: 2016年7月6日 上午11:39:02<br>
   * 
   * @category 上过课但是一个月没做过课件
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/oneMonthNotDoRsa")
  public String oneMonthNotDoRsa(Model model) {
    return "admin/keyfollowup/admin_one_month_not_do_rsa";
  }

  /**
   * 
   * Title: 做完课件一个月没预约课程<br>
   * Description: 做完课件一个月没预约课程<br>
   * CreateDate: 2016年7月13日 下午6:57:28<br>
   * 
   * @category 做完课件一个月没预约课程
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/notSubscribeClass")
  public String notSubscribeClass(Model model) {
    return "admin/keyfollowup/admin_not_subscribe_class";
  }

  /**
   * 
   * Title: 查询重点跟踪学员信息<br>
   * Description: 查询跟踪学员信息<br>
   * CreateDate: 2016年7月4日 下午2:43:37<br>
   * 
   * @category 查询跟踪学员信息
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/findKeyFollowupStudentPage")
  public Map<String, Object> findKeyFollowupStudentPage(HttpServletRequest request,
      boolean findAllFlag, String findType) {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询重点跟课学员信息...");
    Page p = null;
    try {
      String learningCoachId = null;
      if (!findAllFlag) {
        learningCoachId = sessionAdminUser.getKeyId();
      }
      p = adminKeyFollowupService.findKeyFollowupStudentPage(paramMap, learningCoachId, findType);
      if (p != null) {
        paramMap.put("total", p.getTotalCount());
        paramMap.put("rows", p.getDatas());
      }
    } catch (Exception e) {
      logger.error("查询重点跟课学员信息出错:" + e.getMessage(), e);
    }
    return paramMap;
  }

}
