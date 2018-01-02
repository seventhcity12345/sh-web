/** 
 * File: TellMeMoreController.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.tellmemore.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年10月10日 下午4:19:16
 * @author yangmh
 */
package com.webi.hwj.tellmemore.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.tellmemore.util.TellmemoreUtil;
import com.webi.hwj.user.service.SutdentLearningProgressService;
import com.webi.hwj.util.SessionUtil;

/**
 * Title: AdminTellmemoreController<br>
 * Description: AdminTellmemoreController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年10月10日 下午4:19:16
 * 
 * @author seven.gz
 */
@Controller
@RequestMapping("/admin/tellmemore")
public class AdminTellmemoreController {
  private static Logger logger = Logger.getLogger(AdminTellmemoreController.class);
  @Resource
  private TellmemoreService tellmemoreService;
  @Resource
  private SutdentLearningProgressService sutdentLearningProgressService;

  /**
   * Title: 去做课件.<br>
   * Description: 去做课件<br>
   * CreateDate: 2015年10月23日 上午11:06:47<br>
   * 
   * @category goTmm
   * @author seven.gz
   */
  @RequestMapping("/gotoRsa")
  @ResponseBody
  public JsonMessage goTmm(HttpServletRequest request, String phone) throws Exception {
    JsonMessage json = new JsonMessage();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    if (sessionAdminUser == null) {
      json.setMsg("请登录管理员账号!");
      json.setSuccess(false);
    }
    if (StringUtils.isEmpty(phone)) {
      json.setMsg("请输入手机号!");
      json.setSuccess(false);
    }
    try {
      // modify by seven 2016年8月31日13:58:59 判断课件是否到期
      json.setMsg(TellmemoreUtil.generateConnectionPortalUrl(phone));
      json.setSuccess(true);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setMsg("系统内部出现异常!");
      json.setSuccess(false);
    }
    return json;
  }
}
