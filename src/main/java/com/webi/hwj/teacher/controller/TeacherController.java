package com.webi.hwj.teacher.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.index.controller.validateForm.TeacherLoginValidationForm;
import com.webi.hwj.index.controller.validateForm.UpdatePasswordValidationForm;
import com.webi.hwj.teacher.service.TeacherService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @category teacher控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@Api(description = "老师相关接口")
public class TeacherController {
  private static Logger logger = Logger.getLogger(TeacherController.class);
  @Resource
  private TeacherService teacherService;

  /**
   * Title: 第三方老师登录.<br>
   * Description: 第三方老师登录<br>
   * CreateDate: 2015年8月29日 上午9:59:30<br>
   * 
   * @category 老师登录
   * @author yangmh
   * @param third
   *          第三方来源码(老师id)
   */
  @ResponseBody
  @ApiOperation(value = "环讯老师登录,后台老师登录", notes = "暴露给环讯方的接口,实现自动登录,在环讯调用老师评论接口时设置老师信息到缓存,老师id是key")
  @RequestMapping(method = RequestMethod.POST, value = "/api/speakhi/v1/teacher/thirdLogin",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<SessionTeacher> teacherThirdLogin(@ApiParam(name = "third",
      required = true, value = "第三方来源码") @RequestParam("third") String third)
          throws Exception {
    CommonJsonObject<SessionTeacher> json = new CommonJsonObject<SessionTeacher>();

    json.setCode(ErrorCodeEnum.SOURCE_NOT_EXIST.getCode());
    // 如果是第三方的老师进来
    // 从缓存里拿标识
    // Map<String, Object> teacherObj = (Map<String, Object>)
    // MemcachedUtil.getValue(
    // "thirdTeacherLogin_" + third);
    /**
     * modify by seven 2017年9月12日15:55:16 这里只需要id就好了，不知道为什么会放一个对象
     */
    String teacherId = MemcachedUtil.getConfigValue(
        "thirdTeacherLogin_" + third);

    if (!StringUtils.isEmpty(teacherId)) {
      // 帮第三方老师登录
      SessionTeacher sessionTeacher = teacherService.initSessionTeacher(teacherId
          + "");
      if (sessionTeacher != null) {
        // 登录成功
        json.setData(sessionTeacher);
        json.setCode(ErrorCodeEnum.SUCCESS.getCode());
      }
    }
    return json;
  }

  /**
   * Title: 老师登录.<br>
   * Description: 老师登录<br>
   * CreateDate: 2015年8月29日 上午9:59:30<br>
   * 
   * @category 老师登录
   * @author yangmh
   */
  @ResponseBody
  @ApiOperation(value = "老师登录", notes = "")
  @RequestMapping(method = RequestMethod.POST, value = "/api/speakhi/v1/teacher/login",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<SessionTeacher> teacherLogin(
      @Valid @ApiParam(value = "老师登录参数",
          required = true) @RequestBody TeacherLoginValidationForm teacherLoginValidationForm,
      BindingResult result)
          throws Exception {

    CommonJsonObject<SessionTeacher> json = new CommonJsonObject<SessionTeacher>();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    SessionTeacher sessionTeacher = teacherService.teacherLogin(teacherLoginValidationForm);

    if (sessionTeacher != null) {
      // 登录成功
      json.setData(sessionTeacher);
    } else {
      // 登录失败
      json.setCode(ErrorCodeEnum.SOURCE_NOT_EXIST.getCode());
    }

    return json;
  }

  /**
   * 
   * Title: 老师登出<br>
   * Description: 老师登出<br>
   * CreateDate: 2015年8月29日 上午10:00:39<br>
   * 
   * @category 老师登出
   * @author yangmh
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/tcenter/teacherLogout")
  public CommonJsonObject teacherLogout(HttpServletRequest request) throws Exception {
    SessionUtil.deleteSessionUser(request);
    return new CommonJsonObject();
  }

  /**
   * 
   * Title: 老师修改密码<br>
   * Description: updateTeacherPassword<br>
   * CreateDate: 2015年9月18日 下午3:14:36<br>
   * 
   * @category 老师修改密码
   * @author athrun.cw
   * @param request
   * @param updatePasswordValidationForm
   * @param result
   * @return
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/tcenter/teacherPassword")
  public JsonMessage updateTeacherPassword(HttpServletRequest request,
      @RequestBody @Valid UpdatePasswordValidationForm updatePasswordValidationForm,
      BindingResult result) {
    JsonMessage json = new JsonMessage();
    // 表单校验
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }
    // 1.业务逻辑处理：验证登陆
    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);
    if (sessionTeacher == null) {
      json.setSuccess(false);
      json.setMsg("请登录后在操作！");
      return json;
    }
    logger.info("用户 [" + sessionTeacher.getTeacherName() + "] 正在进行修改老师密码...");
    // 2.业务逻辑处理：已经登陆的用户-验证原始密码正确
    try {
      Map<String, Object> userObj = teacherService.findOneByKeyId(sessionTeacher.getKeyId(), "*");
      if (userObj != null
          && userObj.get("pwd").toString()
              .equals(updatePasswordValidationForm.getUpdate_oldPassword())) {
        logger.info("用户 [" + sessionTeacher.getTeacherName() + "] 原始密码验证成功，开始更新新密码...");
        Map<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("key_id", sessionTeacher.getKeyId());
        updateMap.put("pwd", updatePasswordValidationForm.getUpdate_newPassword());
        // 更新新密码
        teacherService.update(updateMap);
      } else {
        // 验证失败
        json.setSuccess(false);
        json.setMsg("请输入正确的账号密码！");
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("系统异常！");
    }
    logger.info("用户 [" + sessionTeacher.getTeacherName() + "] 已经完成新密码的修改...");

    // 3.业务逻辑处理：销毁原登陆的session
    SessionUtil.deleteSessionUser(request);
    logger.info("用户 [" + sessionTeacher.getTeacherName() + "] 将重新使用新密码登陆首页...");

    return json;
  }

  /**
   * Title: 获取教师session<br>
   * Description: 获取教师session<br>
   * CreateDate: 2017年6月7日 下午8:22:09<br>
   * 
   * @category 获取教师session
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/tcenter/teacherSession", method = RequestMethod.GET)
  public CommonJsonObject findSessionTeacher(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);
    json.setData(sessionTeacher);
    return json;
  }

}
