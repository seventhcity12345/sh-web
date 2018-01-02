package com.webi.hwj.weixin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.util.SessionUtil;
import com.webi.hwj.weixin.service.UserWeixinService;

/**
 * @category userWeixin控制类.
 * @author mingyisoft代码生成工具
 *
 */
@Controller
public class AdminWeixinController {
  private static Logger logger = Logger.getLogger(AdminWeixinController.class);

  @Resource
  UserWeixinService userWeixinService;

  /**
   * Title: 进入黄海的webex测试教室.<br>
   * Description: enterTestWebexRoom<br>
   * CreateDate: 2016年12月21日 上午10:46:29<br>
   * 
   * @category 进入黄海的webex测试教室
   * @author yangmh
   */
  @ResponseBody
  @RequestMapping("/sendweixinMsgAll")
  public CommonJsonObject sendweixinMsgAll(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    // admin用户才能生产
    if (sessionAdminUser != null && "admin".equals(sessionAdminUser.getAccount())) {
      logger.info("准备批量下发微信消息");
      userWeixinService.sendWeixinMsgAll();
    }
    logger.info("结束批量下发微信消息");
    return new CommonJsonObject();
  }
}