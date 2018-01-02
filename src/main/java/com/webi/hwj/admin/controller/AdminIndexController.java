package com.webi.hwj.admin.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.webi.hwj.admin.dto.BadminUserDto;
import com.webi.hwj.admin.service.AdminBdminUserService;
import com.webi.hwj.admin.service.AdminConfigService;
import com.webi.hwj.admin.service.AdminResourceService;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.index.service.IndexService;
import com.webi.hwj.user.service.AdminUserService;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.CrmUtil;
import com.webi.hwj.util.SessionUtil;

/**
 * @category 后台管理首页控制类
 *
 */
@Controller
@RequestMapping("/")
public class AdminIndexController {

  private Logger logger = Logger.getLogger(AdminIndexController.class);

  @Resource
  private AdminResourceService adminResourceService;

  @Resource
  private AdminBdminUserService adminBdminUserService;

  @Resource
  private AdminConfigService adminConfigService;

  @Resource
  private AdminUserService adminUserService;

  @Resource
  private IndexService indexService;
  @Resource
  private UserService userService;

  /**
   * @category 跳转到管理后台主页面(入口)
   * @param model
   * @return
   */

  @RequestMapping("/bgmanagementadmin")
  public String adminMain(HttpServletRequest request, ModelMap model) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

    if (sessionAdminUser != null) {
      model.addAttribute("welcomeObj", MemcachedUtil.getConfigValue("config_admin_welcome"));
      return "admin/base/admin_main";
    } else {
      return "admin/base/admin_login";
    }
  }

  /**
   * @category 左侧菜单
   * @param model
   * @return
   */
  @RequestMapping("/adminLeftMain")
  @ResponseBody
  public List<Map<String, Object>> adminLeftMain(HttpServletRequest request, String keyId)
      throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, List<Map<String, Object>>> leftMenuTreeMap = sessionAdminUser.getLeftMenuTreeMap();
    return leftMenuTreeMap.get(keyId);
  }

  /**
   * Title: 管理员登录<br>
   * Description: bLogin<br>
   * CreateDate: 2016年2月14日 上午11:30:19<br>
   * 
   * @category 管理员登陆
   * @author yangmh
   * @param session
   * @param account
   *          账号
   * @param pwd
   *          密码
   * @return
   * @throws Exception
   */
  @RequestMapping("/bLogin")
  @ResponseBody
  public JsonMessage bLogin(HttpSession session, String account, String pwd) throws Exception {
    Map<String, Object> adminUserMap = adminBdminUserService.findOne("account", account,
        "key_id,account,email,pwd,create_date,admin_user_name,admin_user_type,role_id,role_name");

    // modify by seven 2017年6月9日10:20:35 离职人员不允许登录
    // 对比加密密码
    if (adminUserMap != null
        && SHAUtil.encodeByDate(adminUserMap.get("pwd").toString()).equals(pwd) && !"离职人员".equals(
            adminUserMap.get("role_name"))) {
      // 1.初始化user对象
      SessionAdminUser sessionAdminUser = SessionUtil.initSessionAdminUser(adminUserMap, session);
      // 2.初始化左侧菜单

      // 查询出一级菜单
      List<Map<String, Object>> firstLevelMenuList = adminResourceService.leftMenuTree(null,
          sessionAdminUser);
      if (firstLevelMenuList != null && firstLevelMenuList.size() > 0) {
        Map<String, List<Map<String, Object>>> leftMenuTreeMap =
            new HashMap<String, List<Map<String, Object>>>();
        // 遍历一级菜单，将二级菜单数据扔到map里
        leftMenuTreeMap.put("0", firstLevelMenuList);
        for (Map<String, Object> firstLevelMenuMap : firstLevelMenuList) {
          leftMenuTreeMap.put(firstLevelMenuMap.get("key_id").toString(),
              adminResourceService.leftMenuTree(firstLevelMenuMap.get("key_id").toString(),
                  sessionAdminUser));
        }
        sessionAdminUser.setLeftMenuTreeMap(leftMenuTreeMap);
      }

      // 3.初始化权限
      sessionAdminUser.setPermissionMap(adminResourceService.findPermissionMap(sessionAdminUser));

      session.setAttribute(SessionUtil.SESSION_ADMIN_USER_NAME, sessionAdminUser);
    } else {
      return new JsonMessage(false, "账号不可用，请重新输入!");
    }
    return new JsonMessage(true, "登陆成功!");
  }

  /**
   * @category 管理员登出
   * @param request
   * @param reponse
   * @return
   */
  @RequestMapping("/adminChagnePwd")
  @ResponseBody
  public JsonMessage adminChagnePwd(HttpServletRequest request, HttpServletResponse reponse)
      throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    Map<String, Object> newMap = new HashMap<String, Object>();

    if (!paramMap.get("oldPwd").toString().equals(sessionAdminUser.getPwd())) {
      return new JsonMessage("旧密码错误，请重新输入");
    }

    newMap.put("key_id", sessionAdminUser.getKeyId());
    newMap.put("pwd", paramMap.get("pwd"));
    adminBdminUserService.update(newMap);
    return new JsonMessage();
  }

  /**
   * @category 管理员登出
   * @param request
   * @param reponse
   * @return
   */
  @RequestMapping("/adminLogout")
  @ResponseBody
  public JsonMessage adminLogout(HttpServletRequest request, HttpServletResponse reponse)
      throws Exception {
    HttpSession session = request.getSession();
    session.removeAttribute(SessionUtil.SESSION_ADMIN_USER_NAME);
    return new JsonMessage();
  }

  /**
   * 
   * Title: 密码加密(登录)<br>
   * Description: 密码加密<br>
   * CreateDate: 2015年8月29日 上午9:59:53<br>
   * 
   * @category 密码加密
   * @author yangmh
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/encodeLogin")
  public JsonMessage encodeLogin(String code) throws Exception {
    JsonMessage json = new JsonMessage();
    // 1.先对明文加密,形成密文
    String tempCode = SHAUtil.encode(code);
    // 2.再对密文+日期加密
    json.setMsg(SHAUtil.encodeByDate(tempCode));
    return json;
  }

  /**
   * 
   * Title: 密码加密(注册)<br>
   * Description: 密码加密<br>
   * CreateDate: 2015年8月29日 上午9:59:53<br>
   * 
   * @category 密码加密
   * @author yangmh
   * @param request
   * @return
   * @throws Exception
   */
  /**
   * Title: encodeRegister<br>
   * Description: encodeRegister<br>
   * CreateDate: 2017年4月27日 下午3:47:51<br>
   * 
   * @category encodeRegister
   * @author seven.gz
   * @param code
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/encodeRegister")
  public JsonMessage encodeRegister(String code) throws Exception {
    JsonMessage json = new JsonMessage();
    // modify by seven 2017年4月27日15:46:24 增加非空判断
    if (code != null) {
      json.setMsg(SHAUtil.encode(code));
    } else {
      json.setSuccess(false);
      json.setMsg("code不可为空！！");
    }

    return json;
  }

  /**
   * Title: 拟定合同接口<br>
   * Description: 拟定合同接口，对登录用户进行认证，返回拟定合同页面URL<br>
   * CreateDate: 2016年3月25日 下午3:21:35<br>
   * 
   * @category 拟定合同接口
   * @author ivan.mgh
   * @param badminUserDto
   *          拟定合同接口数据传输对象
   * @return
   */
  @RequestMapping(value = "crm/login", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public JsonMessage loginFromCrm(HttpServletRequest request, HttpServletResponse response,
      @RequestBody @Valid BadminUserDto badminUserDto, BindingResult bindingResult) {
    JsonMessage jsonMessage = new JsonMessage();

    try {
      // 校验传入参数
      // 传入参数有误
      if (bindingResult.hasErrors()) {
        processFieldErrorsMessage(bindingResult, jsonMessage);

      } else { // 传入参数校验成功
        // 如果CC的账号在SpeakHi系统中已经录入，则以该CC的名义登录
        Map<String, Object> ccMap = findCc(badminUserDto);
        if (null == ccMap) { // CC不存在，通过通用账号登录
          // 获取登录用户
          Map<String, Object> badminUserMap = adminBdminUserService.findOne("account",
              badminUserDto.getAccount(), "key_id,account,pwd,admin_user_name,admin_user_type");

          loginAsCrmAdmin(badminUserMap, badminUserDto, jsonMessage);
          afterCrmAdminLogin(request, badminUserDto, jsonMessage, badminUserMap);
        } else { // CC已录入，通过CC登录，创建学员
          Map<String, Object> userMap = adminUserService.createUserIfNotExist(badminUserDto,
              (String) ccMap.get("key_id"));

          cacheCrmAuth(badminUserDto, userMap, ccMap);

          // 生成拟定合同页面URL
          String url = createCrmAddOrderCoursePageUrl(request, badminUserDto);
          // 构造响应jsonMessage
          jsonMessage.setData(url);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.toString(), e);
      jsonMessage.setSuccess(false);
      jsonMessage.setMsg(e.toString());
    }

    // 返回响应
    return jsonMessage;
  }

  /**
   * Title: 通用账号登录后处理<br>
   * Description: 生成拟定合同页面URL，若学员不存在创建学员，缓存数据<br>
   * CreateDate: 2016年4月28日 上午11:41:16<br>
   * 
   * @category 通用账号登录后处理
   * @author ivan.mgh
   * @param request
   * @param badminUserDto
   * @param jsonMessage
   * @param badminUserMap
   * @throws UnsupportedEncodingException
   * @throws Exception
   */
  private void afterCrmAdminLogin(HttpServletRequest request, BadminUserDto badminUserDto,
      JsonMessage jsonMessage,
      Map<String, Object> badminUserMap) throws UnsupportedEncodingException, Exception {
    // 登录成功
    if (jsonMessage.isSuccess()) {
      // 生成拟定合同页面URL
      String url = createCrmAddOrderCoursePageUrl(request, badminUserDto);
      // 构造响应jsonMessage
      jsonMessage.setData(url);

      // 创建学员
      Map<String, Object> userMap = adminUserService.createUserIfNotExist(badminUserDto,
          (String) badminUserMap.get("key_id"));

      cacheCrmAuth(badminUserDto, userMap, badminUserMap);
    }
  }

  /**
   * Title: 缓存CRM登录后相关信息<br>
   * Description: cacheCrmAuth<br>
   * CreateDate: 2016年4月28日 上午11:34:35<br>
   * 
   * @category 缓存CRM登录后相关信息
   * @author ivan.mgh
   * @param badminUserDto
   * @param userMap
   * @param badminUserMap
   * @throws Exception
   */
  private void cacheCrmAuth(BadminUserDto badminUserDto, Map<String, Object> userMap,
      Map<String, Object> badminUserMap) throws Exception {
    Map<String, Object> authMap = new HashMap<>();

    // 构造SessionAdminUser
    Map<String, Object> adminUserObj = adminBdminUserService.findOne("key_id",
        badminUserMap.get("key_id").toString(), "*");
    SessionAdminUser sessionAdminUser = CrmUtil.initSessionAdminUser(adminUserObj);

    Map<String, Object> permissionMap = adminResourceService.findPermissionMap(sessionAdminUser);

    // Base64进行编码
    String authBase64 = Base64.encodeBase64String(badminUserDto.getLeadId().getBytes("UTF-8"));
    authMap.put("CRM_AUTH_KEY", authBase64);

    badminUserMap.put("ccName", badminUserDto.getCcName());
    badminUserMap.put("ccBelongCenter", badminUserDto.getCcBelongCenter());
    badminUserMap.put("ccBelongCity", badminUserDto.getCcBelongCity());

    authMap.put("CRM_AUTH_USER", userMap);
    authMap.put("CRM_AUTH_BADMIN_USER", badminUserMap);
    authMap.put("CRM_AUTH_AUTHORITY", permissionMap);
    authMap.put("SESSION_ADMIN_USER", sessionAdminUser);

    // 将凭证缓存
    MemcachedUtil.setValue("CRM_AUTH_" + badminUserDto.getLeadId(), authMap, 60 * 60);
  }

  /**
   * Title: 处理入参校验错误<br>
   * Description: processFieldErrorsMessage<br>
   * CreateDate: 2016年4月28日 上午11:19:34<br>
   * 
   * @category 处理入参校验错误
   * @author ivan.mgh
   * @param bindingResult
   * @param jsonMessage
   */
  private void processFieldErrorsMessage(BindingResult bindingResult, JsonMessage jsonMessage) {
    StringBuilder sb = new StringBuilder();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    for (FieldError fieldError : fieldErrors) {
      sb.append(fieldError.getField());
      sb.append(fieldError.getDefaultMessage());
      sb.append(";");
    }

    jsonMessage.setSuccess(false);
    jsonMessage.setMsg(sb.toString());
  }

  /**
   * Title: 使用CRM通用账号“crm_admin”登录<br>
   * Description: 校验用户是否存在，校验密码是否正确<br>
   * CreateDate: 2016年4月28日 上午11:17:26<br>
   * 
   * @category loginAsCrmAdmin
   * @author ivan.mgh
   * @param badminUserMap
   * @param badminUserDto
   * @param jsonMessage
   * @throws Exception
   * @throws UnsupportedEncodingException
   */
  private void loginAsCrmAdmin(Map<String, Object> badminUserMap, BadminUserDto badminUserDto,
      JsonMessage jsonMessage) throws Exception, UnsupportedEncodingException {
    if (null == badminUserMap) {
      jsonMessage.setSuccess(false);
      jsonMessage.setMsg("用户不存在");
    } else {
      String badminUserPwd = badminUserMap.get("pwd").toString();
      // 密码不匹配
      if (!badminUserDto.getPwd().equals(badminUserPwd)) {
        jsonMessage.setSuccess(false);
        jsonMessage.setMsg("密码不正确");
      }
    }
  }

  /**
   * Title: 通过account获得CC<br>
   * Description: 通过account获得CC，若account为空，或者数据库中找不到对应的管理员用户则返回null<br>
   * CreateDate: 2016年4月28日 上午11:15:25<br>
   * 
   * @category 通过account获得CC
   * @author ivan.mgh
   * @param badminUserDto
   * @return
   * @throws Exception
   */
  private Map<String, Object> findCc(BadminUserDto badminUserDto) throws Exception {
    if (StringUtils.isNotBlank(badminUserDto.getCcAccount())) {
      Map<String, Object> ccMap = adminBdminUserService.findOne("account",
          badminUserDto.getCcAccount(),
          "key_id,admin_user_name,admin_user_type");
      return ccMap;
    }
    return null;
  }

  /**
   * Title: 生成拟定合同页面URL<br>
   * Description: 生成拟定合同页面URL<br>
   * CreateDate: 2016年3月31日 下午1:34:20<br>
   * 
   * @category 生成拟定合同页面URL
   * @author ivan.mgh
   * @param request
   *          请求
   * @param authBase64
   *          经过Base64编码的凭证
   * @return URL
   * @throws UnsupportedEncodingException
   */
  private String createCrmAddOrderCoursePageUrl(HttpServletRequest request,
      BadminUserDto badminUserDto)
          throws UnsupportedEncodingException {
    String auth = badminUserDto.getLeadId();
    // Base64进行编码
    String authBase64 = Base64.encodeBase64String(auth.getBytes("UTF-8"));

    // 协议
    String scheme = request.getScheme();
    // 主机
    String serverName = request.getServerName();
    // 端口
    String serverPort = Integer.valueOf(request.getServerPort()).toString();
    // 应用名
    String contextPath = request.getContextPath();

    StringBuffer url = new StringBuffer(scheme + "://" + serverName);

    if (StringUtils.isNotBlank(serverPort)) {
      url.append(":" + serverPort);
    }
    url.append(contextPath);
    url.append("/admin/orderCourse/crm/add?auth=" + authBase64);
    return url.toString();
  }
}
