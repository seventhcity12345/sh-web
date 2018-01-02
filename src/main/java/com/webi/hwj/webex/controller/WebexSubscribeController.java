package com.webi.hwj.webex.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.webex.util.WebexUtil;

/**
 * Title: webex相关<br> 
 * Description: WebexSubscribeController<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2016年10月11日 下午5:57:04 
 * @author komi.zsy
 */
@Controller
public class WebexSubscribeController {
  /**
   * Title: 获取webex测试joinUrl.<br>
   * Description: 获取webex测试joinUrl<br>
   * CreateDate: 2016年10月11日 下午6:08:24<br>
   * 
   * @category 获取webex测试joinUrl
   * @author seven
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/webexTestRoomUrl", method = RequestMethod.GET)
  public CommonJsonObject enterWebexTestRoom(HttpServletRequest request) throws Exception {
    return WebexUtil.enterTestWebexRoom();
  }

}
