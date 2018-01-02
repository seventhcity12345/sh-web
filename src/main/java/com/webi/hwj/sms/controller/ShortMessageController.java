package com.webi.hwj.sms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.SmsUtil;

/**
 * @category subscribeSupplement控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/shortMessage")
public class ShortMessageController {
  private static Logger logger = Logger.getLogger(ShortMessageController.class);

  @ResponseBody
  @RequestMapping("/send")
  public JsonMessage send(HttpServletRequest request, @RequestBody Map<String, Object> paramMap)
      throws Exception {
    // 手机号
    String mobile = (String) paramMap.get("mobile");
    // 短信内容
    String msg = (String) paramMap.get("msg");
    if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(msg)) {
      return new JsonMessage(false, "参数错误");
    }
    SmsUtil.sendSmsToQueue(mobile, msg);
    return new JsonMessage(true);
  }
}
