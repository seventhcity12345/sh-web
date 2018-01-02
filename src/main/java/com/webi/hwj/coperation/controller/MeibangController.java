package com.webi.hwj.coperation.controller;

import java.text.MessageFormat;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.coperation.param.RedeemCodeRegisterParam;
import com.webi.hwj.coperation.service.MeibangService;

@Controller
public class MeibangController {
  private static Logger logger = Logger.getLogger(MeibangController.class);

  @Resource
  private MeibangService meibangService;

  /**
   * Title: 美邦兑换码提交<br>
   * Description: 美邦兑换码提交<br>
   * CreateDate: 2016年11月8日 下午1:35:33<br>
   * 
   * @category 美邦兑换码提交
   * @author komi.zsy
   * @param request
   * @param session
   * @param redeemCodeRegisterParam
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/mb/submitCode")
  public CommonJsonObject submitCode(HttpServletRequest request, HttpSession session,
      @RequestBody @Valid RedeemCodeRegisterParam redeemCodeRegisterParam, BindingResult result)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }
    // 校验验证码
    String ccode = session.getAttribute("ccode") + "";
    if (!ccode.equals(redeemCodeRegisterParam.getCode())) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("请输入正确的验证码！");
      return json;
    }

    try {
      json = meibangService.redeemCodeRegister(redeemCodeRegisterParam);

      // 兑换成功后发送短信
      if (ErrorCodeEnum.SUCCESS.getCode() == json.getCode()) {
        // 发送短信 暂时共同先用这个
        String msgContent = MessageFormat.format(
            MemcachedUtil.getConfigValue("qq_redeem_code_regist_sussess_message"),
            new String[] { redeemCodeRegisterParam.getPhone() });
        SmsUtil.sendSmsToQueue(redeemCodeRegisterParam.getPhone(), msgContent);
      }

    } catch (Exception e) {
      if ((ErrorCodeEnum.PARAM_CHECK_ERROR.getCode() + "").equals(e.getMessage())) {
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg("兑换码错误!");
      } else {
        e.printStackTrace();
        logger.error("美邦兑换兑换码发生异常 error:" + e.getMessage(), e);
        json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());

        SmsUtil.sendAlarmSms("美邦兑换码注册出错,RedeemCode="
            + redeemCodeRegisterParam.getRedeemCode() + ",phone="
            + redeemCodeRegisterParam.getPhone() + ",error" + e.toString());
      }
    }

    return json;
  }

  /**
   * 
   * Title: 美邦校验兑换码<br>
   * Description: 美邦校验兑换码<br>
   * CreateDate: 2016年11月8日 下午4:12:55<br>
   * 
   * @category 美邦校验兑换码
   * @author seven.gz
   * @param request
   * @param session
   * @param redeemCodeRegisterParam
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/mb/checkRedeemCode")
  public CommonJsonObject cehckRedeemCode(HttpServletRequest request, HttpSession session,
      @RequestBody Map<String, String> paramMap) {
    CommonJsonObject json = null;
    // 表单校验框架
    String redeemCode = paramMap.get("redeemCode");
    try {
      json = meibangService.cehckRedeemCode(redeemCode);
    } catch (Exception e) {
      json = new CommonJsonObject();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      e.printStackTrace();
      logger.error("美邦兑换码校验失败:" + e.getMessage(), e);
    }
    return json;
  }
}