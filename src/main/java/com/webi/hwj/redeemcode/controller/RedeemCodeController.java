package com.webi.hwj.redeemcode.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.redeemcode.constant.RedeemCodeConstant;
import com.webi.hwj.redeemcode.entity.RedeemCode;
import com.webi.hwj.redeemcode.service.RedeemCodeService;
import com.webi.hwj.util.SessionUtil;

/**
 * 
 * Title: 兑换码活动<br>
 * Description: RedeemcodeController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年2月23日 下午2:51:11
 * 
 * @author athrun.cw
 */

@Controller
public class RedeemCodeController {
  private static Logger logger = Logger.getLogger(RedeemCodeController.class);

  @Resource
  RedeemCodeService redeemcodeService;
  
  
  @InitBinder
  protected void initBinder(HttpServletRequest request,
      ServletRequestDataBinder binder) throws Exception {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    CustomDateEditor editor = new CustomDateEditor(df, false);
    binder.registerCustomEditor(Date.class, editor);
  }
  
  /**
   * Title: 生成兑换码<br>
   * Description: 生成兑换码<br>
   * CreateDate: 2016年7月19日 下午4:36:10<br>
   * 
   * @category 生成兑换码
   * @author seven.gz
   * @param request
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/admin/redeemcode/betchCreateRedeemcodeNew")
  public void betchCreateRedeemcodeNew(HttpServletRequest request,RedeemCode paramObj) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    // 对换码个数 20 的倍数
    int redeemcodeNum = Integer.valueOf((String) paramMap.get("redeemcodeNum"));

    //admin用户才能生产
    if (sessionAdminUser != null && "admin".equals(sessionAdminUser.getAccount())) {
      logger.info("开始批量生产兑换码...");
      String key_id = sessionAdminUser.getKeyId();
      paramObj.setUpdateUserId(key_id);
      paramObj.setCreateUserId(key_id);
      
      /**
       * 新版的兑换码活动
       */
      redeemcodeService.createRedeemcodeNew(paramObj,redeemcodeNum);
      
      logger.info("兑换码生成成功~~~");
    }
  }
  
  /**
   * Title: 批量生成兑换码<br>
   * Description: 后台有权限的管理员批量生成兑换码<br>
   * CreateDate: 2017年1月18日 上午10:28:12<br>
   * @category 批量生成兑换码 
   * @author komi.zsy
   * @param request
   * @param paramObj 兑换码生成相关信息
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/admin/redeemCode/betchCreateRedeemCodeBySent")
  public CommonJsonObject betchCreateRedeemCodeBySent(HttpServletRequest request,
      @Valid RedeemCode paramObj, BindingResult result) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 表单校验框架
    if (result.hasErrors()) {
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
        return json;
    }
    if(paramObj.getActivityStartTime().getTime() >= paramObj.getActivityEndTime().getTime()){
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("生效结束日期必须大于生效开始日期！");
      return json;
    }
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    //兑换码数量
    int redeemCodeNum = Integer.valueOf((String) paramMap.get("redeemCodeNum"));
    
    //有权限才能生产兑换码
    if (sessionAdminUser != null && sessionAdminUser.isHavePermisson("contract:createRedeemCode")) {
      logger.info("开始批量生产兑换码...");
      String key_id = sessionAdminUser.getKeyId();
      paramObj.setUpdateUserId(key_id);
      paramObj.setCreateUserId(key_id);
      paramObj.setIsSent(RedeemCodeConstant.REDEEM_CODE_SENT);
      //生成兑换码
      json.setData(redeemcodeService.betchCreateRedeemcodeBySent(paramObj,redeemCodeNum));
    }
    else{
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("管理员权限不足！");
      return json;
    }
    return json;
  }

  /**
   * 
   * Title: 提交兑换码信息接口:提交兑换码信息，用于兑换码页面<br>
   * Description: submitRedeemCode<br>
   * CreateDate: 2016年2月24日 下午2:27:21<br>
   * 
   * @category 提交兑换码信息接口:提交兑换码信息，用于兑换码页面
   * @author athrun.cw
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/ucenter/redeemCode/submitRedeemCode", method = RequestMethod.POST)
  public JsonMessage submitRedeemCode(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    JsonMessage json = new JsonMessage();

    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    // Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    if (sessionUser == null) {
      json.setSuccess(false);
      json.setMsg("用户还未登录，请先登录系统~~~");
      logger.error("用户还未登录，请先登录系统~~~");
      return json;
    }
    paramMap.put("redeem_user_id", sessionUser.getKeyId());
    paramMap.put("redeem_user_phone", sessionUser.getPhone());

    try {
      logger.info("用户id：[" + sessionUser.getKeyId() + "] 开始提交兑换码...");
      json = redeemcodeService.submitRedeemCode(paramMap);

    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("系统错误，请刷新页面重试~");
      logger.error("系统异常，error：" + e.getMessage());
    }
    return json;
  }

  /**
   * 
   * Title: 查询兑换码信息：查询session里的兑换码信息，用于非学员首页<br>
   * Description: findRedeemCode<br>
   * CreateDate: 2016年2月24日 上午10:34:16<br>
   * 
   * @category 查询兑换码信息：查询session里的兑换码信息，用于非学员首页
   * @author athrun.cw
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/ucenter/redeemCode/findRedeemCode", method = RequestMethod.POST)
  public JsonMessage findRedeemCode(HttpServletRequest request) {
    JsonMessage json = new JsonMessage();
    /**
     * 后端通过当前session的用户id去查询兑换表里的数据+需要加活动名称
     */
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    if (sessionUser == null) {
      json.setSuccess(false);
      json.setMsg("用户还未登录，请先登录系统~~~");
      logger.error("用户还未登录，请先登录系统~~~");
      return json;
    }

    paramMap.put("redeem_user_id", sessionUser.getKeyId());

    try {
      json = redeemcodeService.findRedeemCode(paramMap);
      logger.info("获取用户id：[" + sessionUser.getKeyId() + "] 兑换码信息成功~");
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("系统错误，请刷新页面重试~");
      logger.error("系统异常，error：" + e.getMessage());
    }
    return json;
  }

  /**
   * 
   * Title: 短信发送兑换码<br>
   * Description: 短信发送兑换码<br>
   * CreateDate: 2016年6月30日 下午2:26:48<br>
   * 
   * @category 短信发送兑换码
   * @author seven.gz
   * @param request
   * @param activityName
   * @param phone
   */
  @ResponseBody
  @RequestMapping("/redeemCode/sendSms")
  public JsonMessage sendSms(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    String activityName = (String) paramMap.get("activityName");
    String phone = (String) paramMap.get("phone");

    JsonMessage msg = new JsonMessage();
    if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(activityName)) {
      msg.setSuccess(false);
      msg.setMsg("参数不足");
    }
    try {
      msg = redeemcodeService.sendSms(activityName, phone);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("短信发送兑换码失败:" + e.getMessage(), e);
      msg.setSuccess(false);
      msg.setMsg("发送兑换码出现异常");
    }
    return msg;
  }
}
