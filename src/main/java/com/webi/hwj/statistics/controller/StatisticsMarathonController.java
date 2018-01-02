package com.webi.hwj.statistics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.statistics.service.StatisticsMarathonService;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.util.SessionUtil;

/**
 * 
 * Title:马拉松系统数据统计<br>
 * Description: 马拉松系统数据统计<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月7日 上午11:02:41
 * 
 * @author komi.zsy
 */
@Controller
@RequestMapping("/statistics/test")
public class StatisticsMarathonController {
  private static Logger logger = Logger.getLogger(StatisticsMarathonController.class);

  @Resource
  private TellmemoreService tellmemoreService;

  @Resource
  private StatisticsMarathonService statisticsMarathonService;

  /**
   * Title: 测试马拉松学员数据统计(每小时变化)<br>
   * Description: 测试马拉松学员数据统计(每小时变化)<br>
   * CreateDate: 2016年4月6日 下午2:52:38<br>
   * 
   * @category 测试马拉松学员数据统计 (每小时变化)
   * @author komi.zsy
   * @param request
   * @throws Exception
   */
  @RequestMapping("/testStatisticsMarathon")
  public void testStatisticsMarathonController(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    // logger.info("shengyi------>"+MemcachedUtil.getConfigValue("env"));
    // logger.info("shengyi------>"+sessionAdminUser.getAccount());

    if ("pro".equals(MemcachedUtil.getConfigValue("env")) && sessionAdminUser != null
        && "admin".equals(sessionAdminUser.getAccount())) {
      logger.info("生产环境马拉松学员信息统计,开始生产");
      tellmemoreService.fetchTmmUserPercentAndInsertByUserPhone();
      logger.info("生产环境马拉松学员信息统计,结束生产");
    }
  }

  /**
   * Title: 测试马拉松学员数据统计(每日变化)<br>
   * Description: 测试马拉松学员数据统计(每日变化)<br>
   * CreateDate: 2016年4月6日 下午2:52:38<br>
   * 
   * @category 测试马拉松学员数据统计 (每日变化)
   * @author komi.zsy
   * @param request
   * @throws Exception
   */
  @RequestMapping("/testStatisticsMarathonDay")
  public void testStatisticsMarathonDayController(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    // logger.info("shengyi------>"+MemcachedUtil.getConfigValue("env"));
    // logger.info("shengyi------>"+sessionAdminUser.getAccount());

    if ("pro".equals(MemcachedUtil.getConfigValue("env")) && sessionAdminUser != null
        && "admin".equals(sessionAdminUser.getAccount())) {
      logger.info("生产环境马拉松学员信息统计(每日信息),开始生产");
      statisticsMarathonService.statisticsMarathonChangeEveryday();
      logger.info("生产环境马拉松学员信息统计(每日信息),结束生产");
    }
  }

}
