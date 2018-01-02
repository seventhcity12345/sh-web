package com.webi.hwj.index.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.SmsConstant;
import com.webi.hwj.index.controller.validateForm.UserFindPasswordValidationForm;
import com.webi.hwj.index.controller.validateForm.UserResetPasswordValidationForm;
import com.webi.hwj.index.param.UserLoginValidationForm;
import com.webi.hwj.index.param.UserRegisterParam;
import com.webi.hwj.index.service.IndexService;
import com.webi.hwj.mail.MailUtil;
import com.webi.hwj.teacher.service.TeacherService;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.SessionUtil;
import com.webi.hwj.util.TimeZoneUtil;
import com.webi.hwj.weixin.service.UserWeixinService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * Title: 首页/头部控制类<br>
 * Description: 首页/头部控制类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月29日 上午9:59:06
 * 
 * @author yangmh
 */
@Api(description = "首页&头部相关接口")
@Controller
@RequestMapping("/")
public class IndexController {
  private static Logger logger = Logger.getLogger(IndexController.class);

  @Resource
  private IndexService indexService;

  @Resource
  private UserService userService;

  @Resource
  private TeacherService teacherService;

  @Resource
  private UserWeixinService userWeixinService;

  /**
   * 
   * Title: 获取时区<br>
   * Description: 获取时区<br>
   * CreateDate: 2016年5月10日 上午11:54:33<br>
   * 
   * @category getDefaultTimeZone
   * @author athrun.cw
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/timeZone"/* , method=RequestMethod.POST */)
  public JsonMessage getDefaultTimeZone() throws Exception {
    JsonMessage json = new JsonMessage();
    json.setMsg("获取时区成功");
    json.setData(TimeZoneUtil.getDefaultTimeZone());
    return json;
  }

  /**
   * 
   * Title: 找回密码：重置密码<br>
   * Description: resetPassword<br>
   * CreateDate: 2015年12月22日 下午3:46:32<br>
   * 
   * @category 找回密码：重置密码
   * @author athrun.cw
   * @param request
   * @param session
   * @param findPasswordValidationForm
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/resetPassword")
  public JsonMessage resetPassword(HttpServletRequest request, HttpSession session,
      @Valid UserResetPasswordValidationForm userResetPasswordValidationForm, BindingResult result)
      throws Exception {
    JsonMessage json = new JsonMessage();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    // 0.判断是否进行了第一步的号码校验 && 合法用户 的重置密码操作
    Object phone = session.getAttribute("phone");
    if (phone == null || !phone.equals(userResetPasswordValidationForm.getPhone())) {
      json.setSuccess(false);
      json.setMsg("请先进行手机号码校验！");
      logger.error("用户：" + userResetPasswordValidationForm.getPhone() + "非法重置密码，没有通过号码校验，企图重置密码~");
      return json;
    }

    // 1.判断两次输入的密码是否一致
    if (!userResetPasswordValidationForm.getNewPassword()
        .equals(userResetPasswordValidationForm.getConfirmPassword())) {
      json.setSuccess(false);
      json.setMsg("请输入相同的新密码！");
      logger.error("用户：" + userResetPasswordValidationForm.getPhone() + "输入的两次密码不一致，无法重置密码...");
      return json;
    }

    // 2.判断手机号是存在的，才可以重置该手机号的密码
    Map<String, Object> userMap = userService.findOne("phone",
        userResetPasswordValidationForm.getPhone(),
        "key_id");
    if (userMap == null) {
      json.setSuccess(false);
      json.setMsg("该手机号还没有注册，找回密码失败！");
      logger.error("用户：" + userResetPasswordValidationForm.getPhone() + "重置密码权限校验不通过，无法重置密码...");
      return json;
    } else {
      logger.info("用户：" + userResetPasswordValidationForm.getPhone() + "重置密码权限校验通过，将开始重置密码...");
      // 3.所有校验全部通过，开始修改密码
      try {
        Map<String, Object> resetMap = new HashMap<String, Object>();
        resetMap.put("key_id", userMap.get("key_id"));
        resetMap.put("pwd", userResetPasswordValidationForm.getConfirmPassword());
        // 4.更新新的密码
        userService.update(resetMap);
        json.setMsg("恭喜，修改密码成功~");
        logger.info("用户：" + userResetPasswordValidationForm.getPhone() + "更新新密码成功！将直接登录系统...");

      } catch (Exception e) {
        json.setSuccess(false);
        json.setMsg("系统异常，重置密码失败！");
        logger.error("用户：" + userResetPasswordValidationForm.getPhone() + "重置密码失败，系统出现异常...");
      }
      if (json.isSuccess()) {
        // 5.销毁原有的session中的phone
        // session.setAttribute("phone", null);

        // 4.该用户重置密码成功后，将直接登录系统
        userService.initSessionUser(userMap.get("key_id") + "", null);
      }
    }
    return json;
  }

  /**
   * 
   * Title: 找回密码：用户合法性校验和跳转到重置密码页面<br>
   * Description: validateUser4FindPassword<br>
   * CreateDate: 2015年12月22日 下午4:22:24<br>
   * 
   * @category 找回密码：用户合法性校验和跳转到重置密码页面
   * @author athrun.cw
   * @param request
   * @param session
   * @param userFindPasswordValidationForm
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/validateUser4FindPassword")
  public JsonMessage validateUser4FindPassword(HttpServletRequest request, HttpSession session,
      Model model,
      @Valid UserFindPasswordValidationForm userFindPasswordValidationForm, BindingResult result)
      throws Exception {
    JsonMessage json = new JsonMessage();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    // 1.判断手机号是存在的，才能重置该手机号的密码
    Map<String, Object> userMap = userService.findOne("phone",
        userFindPasswordValidationForm.getPhone(), "key_id");
    if (userMap == null) {
      // 没有注册过，直接返回提示
      json.setSuccess(false);
      json.setMsg("该手机号还没有注册，找回密码失败！");
      // modified by seven 2016年8月6日12:08:16 修改日志级别
      logger.info("用户：" + userFindPasswordValidationForm.getPhone() + "重置密码失败，该手机号没有注册过！");
      return json;
    } else {
      // 2.手机验证码校验
      Object ccode = session.getAttribute("ccode");
      if (ccode == null) {
        // 2.1没有通过手机验证码：为空
        json.setSuccess(false);
        json.setMsg("请输入正确的手机验证码！");
        logger.error("用户：" + userFindPasswordValidationForm.getPhone() + "重置密码失败，手机验证码错误！");
        return json;
      } else {
        if (!ccode.toString().equals(userFindPasswordValidationForm.getValidateCode())) {
          // 2.2没有通过手机验证码：验证码错误
          json.setSuccess(false);
          json.setMsg("请输入正确的手机验证码！");
          logger.error("用户：" + userFindPasswordValidationForm.getPhone() + "重置密码失败，手机验证码错误！");
          return json;
        } else {
          // 2.3验证成功，手机验证码已经无用，销毁
          session.setAttribute("ccode", null);

          // 2.4添加新的session phone，便于在重置时候，校验用户合法性
          session.setAttribute("phone", userFindPasswordValidationForm.getPhone());
          json.setSuccess(true);
          json.setMsg("手机号校验通过，可以重置密码了~");
          logger.info("用户：" + userFindPasswordValidationForm.getPhone() + "可以重置密码了~");
          return json;
        }
      }
    }
  }

  /**
   * 
   * Title: 注册<br>
   * Description: 注册<br>
   * CreateDate: 2015年8月29日 上午10:01:09<br>
   * 
   * @category 注册
   * @author yangmh
   * @param request
   * @param session
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "用户(学生)注册", notes = "")
  @RequestMapping(value = "/api/speakhi/v1/user/register", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<User> userRegister(HttpServletRequest request,
      @ApiParam(value = "用户注册参数",
          required = true) @Valid @RequestBody UserRegisterParam userRegisterParam,
      BindingResult result)
      throws Exception {
    CommonJsonObject<User> json = new CommonJsonObject<User>();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    // 校验验证码
    String ccode = request.getSession().getAttribute("ccode") + "";
    if (!ccode.equals(userRegisterParam.getCode())) {
      json.setCode(ErrorCodeEnum.CCODE_NOT_CORRECT.getCode());
      return json;
    }

    // 请求者ip地址
    String requestIp = request.getRemoteAddr();

    return indexService.studentRegister(userRegisterParam, requestIp);
  }

  /**
   * 
   * Title: 学员登录<br>
   * Description: 学员登录<br>
   * CreateDate: 2015年8月29日 上午10:00:59<br>
   * 
   * @category 学员登录
   * @author yangmh
   * @param request
   * @param session
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "用户(学生)登录", notes = "")
  @RequestMapping(value = "/api/speakhi/v1/user/login", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<SessionUser> userLogin(HttpServletRequest request,
      @Valid @ApiParam(value = "用户登录参数",
          required = true) @RequestBody UserLoginValidationForm userLoginValidationForm,
      BindingResult result)
      throws Exception {
    CommonJsonObject<SessionUser> json = new CommonJsonObject<SessionUser>();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    SessionUser sessionUser = userService.userLogin(userLoginValidationForm,
        RequestUtil.getAddr(request));

    if (sessionUser != null) {
      // 登录成功
      json.setData(sessionUser);
    } else {
      // 登录失败
      json.setCode(ErrorCodeEnum.SOURCE_NOT_EXIST.getCode());
    }

    return json;
  }

  /**
   * Title: 查找缓存中的数据（支持多个缓存值同时查询）.<br>
   * Description: findMemcachedValue<br>
   * CreateDate: 2016年10月28日 下午3:11:34<br>
   * 
   * @category 查找缓存中的数据（支持多个缓存值同时查询）
   * @author yangmh
   * @param json格式paramStr,如果有多个请使用逗号分割
   * @return 返回跟参数对应的缓存内的值
   */
  @ResponseBody
  @RequestMapping(value = "/findMemcachedValue", method = RequestMethod.POST)
  public CommonJsonObject findMemcachedValue(@RequestBody Map<String, Object> paramMap)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String paramStr = paramMap.get("paramStr").toString();
    if (StringUtils.isEmpty(paramStr)) {
      // 如果参数为空
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      return json;
    }

    String[] paramStrArray = paramStr.split(",");
    Map<String, Object> returnMap = new HashMap<String, Object>();
    for (String tempParmaStr : paramStrArray) {
      returnMap.put(tempParmaStr, MemcachedUtil.getValue(tempParmaStr));
    }

    json.setData(returnMap);

    return json;
  }

  /**
   * Title: 学生登出.<br>
   * Description: logout<br>
   * CreateDate: 2017年2月20日 下午3:58:44<br>
   * 
   * @category 学生登出
   * @author yangmh
   */
  @ResponseBody
  @ApiOperation(value = "用户(学生)退出登录", notes = "")
  @RequestMapping(value = "/api/speakhi/v1/user/logout", method = RequestMethod.DELETE)
  public CommonJsonObject<Object> userLogout(HttpServletRequest request)
      throws Exception {
    MemcachedUtil.deleteValue(request.getHeader("token"));
    return new CommonJsonObject<Object>();
  }

  /**
   * Title: 发送验证码.<br>
   * Description: 目前有缺陷，可以被无限制利用，session存储验证码的方式<br>
   * CreateDate: 2015年8月29日 上午10:00:30<br>
   * 
   * @category 发送验证码
   * @author yangmh
   * @param mobile
   *          手机号
   */
  @ResponseBody
  @ApiOperation(value = "发送验证码", notes = "session保存验证码")
  @RequestMapping(value = "/api/speakhi/v1/index/sms", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<Object> sendSms(HttpSession session, @ApiParam(name = "mobile",
      required = true, value = "手机号") @RequestParam("mobile") String mobile) throws Exception {
    int ckno = (int) ((Math.random() * 9 + 1) * 1000);
    System.out.println("ckno=================lalalala==================>" + ckno);

    // session保存验证码
    session.setAttribute("ccode", ckno);
    // 向消息队列里推发短信消息

    CommonJsonObject<Object> json = new CommonJsonObject<Object>();
    // SmsUtil.sendSmsToQueueRocketMq(mobile, "您注册的手机验证码为" + ckno +
    // "，请在注册页面填写验证码完成注册");
    SmsUtil.sendSmsToQueue(mobile, "您注册的手机验证码为" + ckno + "，请在注册页面填写验证码完成注册");
    return json;
  }

  /**
   * Title: lp页面发送短信<br>
   * Description: lp页面发送短信,验证码返回前端<br>
   * CreateDate: 2016年9月19日 下午4:08:49<br>
   * 
   * @category lp页面发送短信
   * @author komi.zsy
   * @param mobile
   *          手机号
   * @return 验证码
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "发送验证码", notes = "验证码返回客户端")
  @RequestMapping(value = "/api/speakhi/v1/index/codesms", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<Integer> sendCodeSms(@ApiParam(name = "mobile",
      required = true, value = "手机号") @RequestParam("mobile") String mobile) throws Exception {
    CommonJsonObject<Integer> json = new CommonJsonObject<Integer>();
    int ckno = (int) ((Math.random() * 9 + 1) * 1000);
    System.out.println("Lp,ckno===================================>" + ckno);
    // 向消息队列里推发短信消息
    SmsUtil.sendSmsToQueue(mobile, "您注册的手机验证码为" + ckno + "，请在注册页面填写验证码完成注册");
    json.setData(ckno);
    return json;
  }

  /**
   * Title: 邮箱发送验证码<br>
   * Description: 邮箱发送验证码<br>
   * CreateDate: 2016年12月12日 下午5:02:21<br>
   * 
   * @category 邮箱发送验证码
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/userInfo/mail/sending")
  public CommonJsonObject sendCodeEmail(@RequestBody Map<String, Object> paramMap)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 邮箱地址
    String email = paramMap.get("email").toString();
    int ckno = (int) ((Math.random() * 9 + 1) * 1000);
    System.out.println("邮箱,ckno===================================>" + ckno);
    // 发送邮件
    MailUtil.sendMail(email, "SpeakHi邮箱验证提醒",
        "<p>尊敬的SpeakHi用户：<br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本次邮箱验证码为：<font color='#FF0000'>"
            + ckno
            + "</font> <font color='#666666'>请填入个人资料页面对应输入框</font>"
            + "<br><br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预祝您在SpeakHi学习愉快！</p>");
    // 返回前端验证码
    Map<String, Object> returnMap = new HashMap<String, Object>();
    returnMap.put("emailCode", ckno);
    json.setData(returnMap);

    return json;
  }

  /**
   * 
   * Title: 校验预约手机是否重复<br>
   * Description: 校验预约手机是否重复<br>
   * CreateDate: 2015年8月29日 上午9:59:53<br>
   * 
   * @category 校验预约手机是否重复
   * @author yangmh
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/checkRepeatSubscribe")
  public JsonMessage checkRepeatSubscribe(HttpServletRequest request) throws Exception {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    JsonMessage json = new JsonMessage();
    try {
      List<Map<String, Object>> userList = indexService.findSubscribeUserList(paramMap);
      if (userList != null && userList.size() > 0) {
        json.setSuccess(false);
        json.setMsg("该手机号已经注册！");
      }
    } catch (Exception e) {
      logger.error("error:" + e.getMessage(), e);
      e.printStackTrace();
      json.setSuccess(false);
      json.setMsg("网络不稳定");
    }
    return json;
  }

  /**
   * 校验手机是否重复
   * 
   * @category 校验手机是否重复
   * @param request
   * @author yangmh
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "检查重复注册手机号", notes = "不是json的参数！！！！！！！")
  @RequestMapping(value = "/api/speakhi/v1/user/repeat", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<Object> checkRepeat(@ApiParam(name = "phone", required = true,
      value = "手机号", defaultValue = "18600609747") @RequestParam("phone") String phone)
      throws Exception {
    CommonJsonObject<Object> json = new CommonJsonObject<Object>();

    User user = userService.findOneByPhoneReturnPwd(phone);

    if (user != null && !StringUtils.isEmpty(user.getKeyId())) {
      // 该手机号已被注册
      json.setCode(ErrorCodeEnum.USER_PHONE_EXISTS.getCode());
    }
    return json;
  }

  /**
   * 
   * Title: remandUserHavePaid<br>
   * Description: 用户成功支付后，发送短信，提醒用户已经购买成功<br>
   * CreateDate: 2015年10月27日 下午3:10:58<br>
   * 
   * @category 用户成功支付后，发送短信，提醒用户已经购买成功
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/remindUserHavePaid")
  public void remindUserHavePaid(HttpServletRequest request) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    // 调用发送短信的util类，参数分别为：电话 & 短信内容模板类型
    /**
     * TODO 待修改
     * 
     * SpeakHi嗨英语 欢迎成为SpeakHi的一员！ 您的学习开课时间为：2016年X月X号。请先登录网站完善个人资料，
     * 让外教老师更好地认识你吧！speakhi.com 韦博集团
     */
    SmsUtil.sendSmsToQueue(sessionUser.getPhone(), SmsConstant.BACK_REGISTER_SUCCESS);
  }

  @ResponseBody
  @RequestMapping("/findServerTime")
  public CommonJsonObject findServerTime() throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 调用发送短信的util类，参数分别为：电话 & 短信内容模板类型
    json.setData(new Date());
    return json;
  }

}