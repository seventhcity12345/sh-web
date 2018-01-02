package com.webi.hwj.user.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.user.param.GeneralStudentInfoParam;
import com.webi.hwj.user.service.AdminGeneralStudentService;
import com.webi.hwj.util.SessionUtil;

@Controller
@RequestMapping("/admin/generalStudent")
public class AdminGeneralStudentController {
  private static Logger logger = Logger.getLogger(AdminGeneralStudentController.class);

  @Resource
  AdminGeneralStudentService adminGeneralStudentService;

  /**
   * 
   * Title: 跳转后台通用英语学员管理界面<br>
   * Description: 跳转后台通用英语学员管理界面<br>
   * CreateDate: 2016年5月31日 下午4:17:22<br>
   * 
   * @category index
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/index")
  public String index(Model model) {
    return "admin/user/admin_general_student";
  }

  /**
   * 
   * Title: 查询通用学员详细上课信息<br>
   * Description: 查询通用学员详细上课信息<br>
   * CreateDate: 2016年6月2日 上午11:56:03<br>
   * 
   * @category 查询通用学员详细上课信息
   * @author seven.gz
   * @param request
   * @param userId
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findGeneralStudentDetailInfo")
  public GeneralStudentInfoParam findGeneralStudentDetailInfo(HttpServletRequest request,
      String userId) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询通用用户详细信息...");
    return adminGeneralStudentService.findGeneralStudentDetailInfo(userId);
  }

}
