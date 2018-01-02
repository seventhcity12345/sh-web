package com.webi.hwj.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.index.controller.validateForm.UpdatePasswordValidationForm;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.user.controller.validateForm.UserInfoValidationForm;
import com.webi.hwj.user.service.AdminUserInfoService;
import com.webi.hwj.user.service.AdminUserService;
import com.webi.hwj.user.service.SutdentLearningProgressService;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.MapUtil;
import com.webi.hwj.util.SessionUtil;

/**
 * @category user控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
public class UserController {
  private static Logger logger = Logger.getLogger(UserController.class);
  @Resource
  private UserService userService;
  @Resource
  private OrderCourseService orderCourseService;
  @Resource
  private SutdentLearningProgressService sutdentLearningProgressService;
  @Resource
  AdminUserService adminUserService;
  @Resource
  AdminUserInfoService adminUserInfoService;
  
  
  /**
   * Title: updateUserPhoto<br>
   * Description: 上传个人图像<br>
   * CreateDate: 2015年11月3日 下午4:40:14<br>
   * 
   * @category 上传个人图像
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/user/updateUserPhoto")
  public CommonJsonObject<SessionUser> updateUserPhoto(HttpServletRequest request) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    return userService.updateUserPhoto(request, sessionUser);
  }

  /**
   * 
   * Title: initUserTestLevel<br>
   * Description: 初始化用户的级别<br>
   * CreateDate: 2015年11月4日 下午4:15:30<br>
   * 
   * @category 初始化用户的级别
   * @author athrun.cw
   * @param request
   *          test_level
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/user/initUserTestLevel")
  public JsonMessage initUserTestLevel(HttpServletRequest request, Model model) throws Exception {
    JsonMessage json = new JsonMessage();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("user_id", sessionUser.getKeyId());
    try {
      logger.info("用户id [" + paramMap.get("user_id") + "] 级别初始化开始...");
      json = userService.initUserTestLevel(paramMap);
      logger.info("用户id [" + paramMap.get("user_id") + "] 级别初始化成功！");
    } catch (Exception e) {
      logger.info("用户id [" + paramMap.get("user_id") + "] 级别初始化失败！");
      logger.error("error:" + e.getMessage());
      json.setMsg("用户级别初始化失败！");
      json.setSuccess(false);
    }
    return json;
  }

  /**
   * Title: 保存用户当前级别<br>
   * Description: 保存用户当前级别<br>
   * CreateDate: 2016年7月21日 上午11:47:58<br>
   * 
   * @category 保存用户当前级别
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/user/saveCurrentLevel")
  public JsonMessage saveCurrentLevel(HttpServletRequest request) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    logger.info("学员修改等级------>学员id：" + sessionUser.getKeyId() + ",修改为:" + paramMap);

    String currentLevel = paramMap.get("current_level").toString();

    // TODO 这里暂时写死通用英语等级，不知道等级机制怎么区分，以后有别的体系的级别需要修改！！！
    if (currentLevel != null && !"".equals(currentLevel) && !"null".equals(currentLevel)) {
      paramMap.put("current_level", "General Level " + currentLevel);
    } else {
      return new JsonMessage(false, "请先选择级别！");
    }

    return adminUserService.saveCurrentLevel(paramMap, sessionUser.getKeyId());
  }

  /**
   * Title: 保存用户的英文名<br>
   * Description: saveEnglishName<br>
   * CreateDate: 2016年8月8日 下午5:44:06<br>
   * 
   * @category 保存用户的英文名
   * @author yangmh
   * @param request
   * @param session
   * @param englishName
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/user/saveEnglishName")
  public JsonMessage saveEnglishName(HttpServletRequest request, HttpSession session,
      String englishName) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    JsonMessage json = new JsonMessage();
    logger.info("学员修改英文名------>学员id：" + sessionUser.getKeyId() + ",修改为:" + englishName);

    // TODO 这里暂时写死通用英语等级，不知道等级机制怎么区分，以后有别的体系的级别需要修改！！！
    if (!StringUtils.isEmpty(englishName)) {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("english_name", englishName);
      paramMap.put("key_id", sessionUser.getKeyId());
      adminUserInfoService.update(paramMap);
      userService.initSessionUser(sessionUser.getKeyId(), null);
    } else {
      json.setSuccess(false);
      json.setMsg("请输入英文名！");
    }
    return json;
  }

  /**
   * 
   * Title: 编辑个人资料<br>
   * Description: editUserInfo<br>
   * CreateDate: 2015年10月16日 上午10:25:13<br>
   * 
   * @category 编辑个人资料
   * @author athrun.cw
   * @param request
   * @param userInfoValidationForm
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/user/updateUserInfo")
  public JsonMessage updateUserInfo(HttpServletRequest request,
      @Valid UserInfoValidationForm userInfoValidationForm,
      BindingResult result) throws Exception {
    JsonMessage json = new JsonMessage();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    // Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    @SuppressWarnings("unchecked")
    Map<String, Object> paramMap = MapUtil.convertBean(userInfoValidationForm);

    // 从sessionUser添加user_id && info_complete_percent
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    paramMap.put("key_id", sessionUser.getKeyId());
    paramMap.put("update_user_id", sessionUser.getKeyId());

    try {
      // 预约subcribe_id上课，课程预约subscribe
      logger.info("用户 [" + sessionUser.getPhone() + "] 开始更新资料...");
      json = userService.updateUserInfoByUserOrAdminUser(paramMap, request);
      logger.info("用户 [" + sessionUser.getPhone() + "] 更新资料成功！");
    } catch (Exception e) {
      logger.info("用户 [" + sessionUser.getPhone() + "] 更新资料失败！");
      logger.error("error:" + e.getMessage());
      json.setMsg(e.getMessage());
      json.setSuccess(false);
    }

    return json;
  }

  /**
   * 
   * Title: 学员修改密码<br>
   * Description: updateUserPassword<br>
   * CreateDate: 2015年9月18日 上午11:20:46<br>
   * 
   * @category 学员修改密码
   * @author athrun.cw
   * @param request
   * @param updatePasswordValidationForm
   * @param result
   * @return
   */
  @ResponseBody
  @RequestMapping("/ucenter/user/updateUserPassword")
  public JsonMessage updateUserPassword(HttpServletRequest request,
      @Valid UpdatePasswordValidationForm updatePasswordValidationForm, BindingResult result) {
    JsonMessage json = new JsonMessage();
    // 表单校验
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }
    // 1.业务逻辑处理：验证登陆
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    if (sessionUser == null) {
      json.setSuccess(false);
      json.setMsg("请登录后在操作！");
      return json;
    }
    logger.info("用户 [" + sessionUser.getPhone() + "] 正在进行修改学员密码...");
    // 2.业务逻辑处理：已经登陆的用户-验证原始密码正确
    try {
      Map<String, Object> userObj = userService.findOneByKeyId(sessionUser.getKeyId(), "*");
      if (userObj != null
          && userObj.get("pwd").toString()
              .equals(updatePasswordValidationForm.getUpdate_oldPassword())) {
        logger.info("用户 [" + sessionUser.getPhone() + "] 原始密码验证成功，开始更新新密码...");
        Map<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("key_id", sessionUser.getKeyId());
        updateMap.put("pwd", updatePasswordValidationForm.getUpdate_newPassword());
        // 更新新密码
        userService.update(updateMap);
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
    if (json.isSuccess()) {
      /**
       * 新需求：即时修改了密码，仍然是登录状态不变 modify by athrun.cw 2015年12月22日16:19:01
       */
      // 3.业务逻辑处理：销毁原登陆的session
      // request.getSession().setAttribute(SessionUtil.SESSION_USER_NAME,
      // null);
      // logger.info("用户【 " + sessionUser.getPhone() + "
      // 】已经完成新密码的修改，将使用新密码重新登录系统...");
    } else {
      logger.info("用户 [" + sessionUser.getPhone() + "] 修改密码失败...");
    }
    return json;
  }

}
