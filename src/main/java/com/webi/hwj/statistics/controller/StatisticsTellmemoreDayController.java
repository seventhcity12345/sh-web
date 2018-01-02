package com.webi.hwj.statistics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.statistics.service.StatisticsTellmemoreDayService;
import com.webi.hwj.util.SessionUtil;

/**
 * Title: Tellmemore每日学员三维数据统计，变化量统计<br>
 * Description: Tellmemore每日学员三维数据统计，变化量统计<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月15日 下午1:13:21
 * 
 * @author komi.zsy
 */
@Controller
@RequestMapping("/statistics/test")
public class StatisticsTellmemoreDayController {
  private static Logger logger = Logger.getLogger(StatisticsTellmemoreDayController.class);
  @Resource
  private StatisticsTellmemoreDayService statisticsTellmemoreDayService;

  @RequestMapping("/testStatisticsTellmemoreDay")
  public void testStatisticsTellmemoreDayController(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    // logger.info("shengyi------>"+MemcachedUtil.getConfigValue("env"));

    if ("pro".equals(MemcachedUtil.getConfigValue("env")) && sessionAdminUser != null
        && "admin".equals(sessionAdminUser.getAccount())) {
      logger.info("生产环境speakhi学员rsa数据每日信息统计,开始生产");
      String paramDate = (String) request.getParameter("paramDate");
      statisticsTellmemoreDayService.statisticsTellmemoreChangeEveryday(paramDate);
      logger.info("生产环境speakhi学员rsa数据每日信息统计,结束生产");
    }
  }

}