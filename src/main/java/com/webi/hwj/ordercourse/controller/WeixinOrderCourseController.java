package com.webi.hwj.ordercourse.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.ordercourse.service.AdminOrderCourseService;
import com.webi.hwj.util.SessionUtil;

/**
 * 
 * Title: 微信端合同controller<br>
 * Description: WeixinOrderCourseController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月29日 上午11:07:26
 * 
 * @author athrun.cw
 */
@Controller
@RequestMapping("/weixin/ordercourse/")
public class WeixinOrderCourseController {
  private static Logger logger = Logger.getLogger(WeixinOrderCourseController.class);

  @Resource
  private AdminOrderCourseService adminOrderCourseService;

  /**
   * 
   * Title: 加载正在执行中的合同列表接口<br>
   * Description: usingOrderList<br>
   * CreateDate: 2016年4月29日 上午11:10:51<br>
   * 
   * @category 加载正在执行中的合同列表接口
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/usingOrderList", method = RequestMethod.POST)
  public List<Map<String, Object>> usingOrderList(HttpServletRequest request) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("user_id", sessionUser.getKeyId());
    // 使用续约时候，找能够续约的合同一样的逻辑
    List<Map<String, Object>> usingOrderMapList = adminOrderCourseService
        .findRenewalOrderCourseListByUserId(paramMap);
    return usingOrderMapList;
  }

}
