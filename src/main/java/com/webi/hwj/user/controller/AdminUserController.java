package com.webi.hwj.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.RequestUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.admin.entity.BadminUser;
import com.webi.hwj.admin.service.AdminBdminUserService;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.constant.SmsConstant;
import com.webi.hwj.index.param.UserRegisterParam;
import com.webi.hwj.index.service.IndexService;
import com.webi.hwj.ordercourse.service.AdminOrderCourseOptionService;
import com.webi.hwj.ordercourse.service.AdminOrderCourseService;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.SubscribeParam;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseService;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.user.controller.validateForm.UserInfoValidationForm;
import com.webi.hwj.user.controller.validateForm.UserPhoneValidationForm;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.service.AdminGeneralStudentService;
import com.webi.hwj.user.service.AdminUserInfoService;
import com.webi.hwj.user.service.AdminUserService;
import com.webi.hwj.user.service.SutdentLearningProgressService;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.user.util.UserCategoryTypeUtil;
import com.webi.hwj.util.SessionUtil;

/**
 * @category user控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
  private static Logger logger = Logger.getLogger(AdminUserController.class);

  @Resource
  private AdminUserService adminUserService;

  @Resource
  private AdminOrderCourseService adminOrderCourseService;

  @Resource
  private IndexService indexService;

  @Resource
  TellmemoreService tellmemoreService;

  @Resource
  private UserService userService;

  @Autowired
  private OrderCourseService orderCourseService;

  @Resource
  private SubscribeCourseService subscribeCourseService;

  @Resource
  private AdminUserInfoService adminUserInfoService;
  @Resource
  AdminGeneralStudentService adminGeneralStudentService;
  @Resource
  AdminOrderCourseOptionService adminOrderCourseOptionService;

  @Resource
  BaseSubscribeCourseService baseSubscribeCourseService;
  @Resource
  UserEntityDao userEntityDao;

  @Resource
  AdminBdminUserService adminBdminUserService;

  @Resource
  SutdentLearningProgressService sutdentLearningProgressService;

  /**
   * @category user后台管理主页面
   * @author mingyisoft代码生成工具
   * @param model
   * @return
   */
  @RequestMapping("/index")
  public String index(Model model) {
    return "admin/user/admin_user";
  }

  /**
   * Title: 管理员重置用户密码界面<br>
   * Description: 管理员重置用户密码界面<br>
   * CreateDate: 2016年3月30日 下午3:31:39<br>
   * 
   * @category 管理员重置用户密码界面
   * @author komi.zsy
   * @return
   */
  @RequestMapping("/userResetPasswordIndex")
  public String userResetPasswordIndex() {
    return "admin/user/admin_reset_password";
  }

  /**
   * Title: 根据手机号判定是否是学员<br>
   * Description: 根据手机号判定是否是学员<br>
   * CreateDate: 2016年3月30日 下午6:19:31<br>
   * 
   * @category 根据手机号判定是否是学员
   * @author komi.zsy
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/fingStudentIsExist")
  public JsonMessage fingStudentIsExistByPhone(HttpServletRequest request) {
    JsonMessage json = new JsonMessage();
    // 检查学员是否存在
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("is_student", 1);
    paramMap.put("is_used", 1);
    try {
      if (adminUserService.fingStudentIsExistByPhone(paramMap) > 0) {
        json.setSuccess(true);
        json.setMsg("该学员存在");
      } else {
        json.setSuccess(false);
        json.setMsg("学员不存在！请输入正确的手机号！");
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("系统异常！");
    }
    return json;
  }

  /**
   * 
   * Title: 根据用户手机号将密码重置为123456<br>
   * Description: 根据用户手机号将密码重置为123456<br>
   * CreateDate: 2016年3月30日 下午5:17:16<br>
   * 
   * @category 根据用户手机号将密码重置为123456
   * @author komi.zsy
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/resetUserPasswordByAdmin")
  public JsonMessage resetUserPasswordByAdmin(HttpServletRequest request) {
    JsonMessage json = new JsonMessage();
    // 业务逻辑处理：验证登陆
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    if (sessionAdminUser == null) {
      json.setSuccess(false);
      json.setMsg("请登录后再操作！");
      return json;
    }
    logger.info("管理员 [" + sessionAdminUser.getAccount() + "] 正在进行修改学员密码...");
    // 业务逻辑处理：重置密码
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    try {
      logger.info("用户 [" + paramMap.get("phone") + "] 开始重置新密码123456");
      Map<String, Object> updateMap = new HashMap<String, Object>();
      updateMap.put("phone", paramMap.get("phone"));
      updateMap.put("pwd", SHAUtil.encode("123456"));
      // 更新新密码
      adminUserService.resetUserPasswordByAdmin(updateMap);

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("系统异常！");
    }
    if (json.isSuccess()) {
      logger.info("用户 [" + paramMap.get("phone") + "] 重置密码123456");
    } else {
      logger.info("用户 [" + paramMap.get("phone") + "] 重置密码失败");
    }
    return json;
  }

  /**
   * Title: 学员管理index<br>
   * Description: index<br>
   * CreateDate: 2015年11月19日 下午6:41:27<br>
   * 
   * @category 学员管理index
   * @author athrun.cw
   * @param model
   * @return
   */
  @RequestMapping("/studentIndex")
  public String studentIndex(Model model) {
    logger.debug("学员管理页面admin_studentIndex...");
    return "admin/user/admin_studentIndex";
  }

  /**
   * 
   * Title: 学员管理 查看所有学员时，查询出所有的学员，没有LC限制<br>
   * Description: findAllStudent<br>
   * CreateDate: 2015年11月25日 上午10:18:10<br>
   * 
   * @category 学员管理 查看所有学员时，查询出所有的学员，没有LC限制
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findAllStudent")
  public Map<String, Object> findAllStudent(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("learning_coach_id", sessionAdminUser.getKeyId());
    paramMap.put("findAllStudent", "findAllStudent");
    try {
      logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始查看学员信息...");
      Page p = adminUserService.findStudentPageEasyui(paramMap);
      paramMap.put("total", p.getTotalCount());
      paramMap.put("rows", p.getDatas());
    } catch (Exception e) {
      logger.error("后台管理用户id [" + sessionAdminUser.getKeyId() + "查看学员信息，错误error:" + e.getMessage(),
          e);
    }
    return paramMap;
  }

  /**
   * 
   * Title: 批量修改用户的LC<br>
   * Description: batchUpdateUserLC<br>
   * CreateDate: 2015年11月20日 下午6:43:38<br>
   * 
   * @category 批量修改用户的LC
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/batchUpdateUserLC")
  public JsonMessage batchUpdateUserLC(HttpServletRequest request) throws Exception {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    JsonMessage json = new JsonMessage();
    try {
      json = adminUserService.batchUpdateUserLC(paramMap);
      json.setMsg("批量修改用户LC成功！");
      logger.debug("批量修改用户LC成功！");
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("批量修改用户LC失败");
      logger.error("批量修改用户LC失败！" + e.getMessage());
    }
    return json;
  }

  /**
   * 
   * Title: 获取所有有效的教务<br>
   * Description: findAllAdminUserList<br>
   * CreateDate: 2015年11月20日 下午6:38:03<br>
   * 
   * @category 获取所有有效的教务
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findAdminUserListByLc")
  public List<BadminUser> findAdminUserListByLc(HttpServletRequest request)
      throws Exception {
    return adminUserService.findAdminUserListByLc();
  }

  /**
   * 
   * Title: 查询学员管理页面中显示的数据<br>
   * Description: studentPageList<br>
   * CreateDate: 2015年11月19日 下午6:50:09<br>
   * 
   * @category 查询学员管理页面中显示的数据
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/studentPageList")
  public Map<String, Object> studentPageList(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("learning_coach_id", sessionAdminUser.getKeyId());
    paramMap.put("studentPageList", "studentPageList");
    try {
      logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始查看学员列表信息...");
      Page p = adminUserService.findStudentPageEasyui(paramMap);
      paramMap.put("total", p.getTotalCount());
      paramMap.put("rows", p.getDatas());
    } catch (Exception e) {
      logger.error(
          "后台管理用户id [" + sessionAdminUser.getKeyId() + "查看学员列表信息，错误error:" + e.getMessage(), e);
    }
    return paramMap;
  }

  /**
   * @category 保存用户当前级别
   * @author mingyisoft代码生成工具
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/saveCurrentLevel")
  public JsonMessage saveCurrentLevel(HttpServletRequest request, ModelMap model) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    return adminUserService.saveCurrentLevel(paramMap, sessionAdminUser.getKeyId());
  }

  /**
   * @category user保存
   * @author mingyisoft代码生成工具
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @SuppressWarnings("rawtypes")
  @RequestMapping("/save")
  public JsonMessage save(HttpServletRequest request, ModelMap model) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    if (paramMap.get("key_id") != null && !"".equals(paramMap.get("key_id"))) {// 修改
      paramMap.put("update_date", new Date());
      paramMap.put("update_user_id", sessionAdminUser.getKeyId());
      adminUserService.update(paramMap);
    } else {// 新增
      paramMap.put("create_date", new Date());
      paramMap.put("create_user_id", sessionAdminUser.getKeyId());
      adminUserService.insert(paramMap);
    }

    return new JsonMessage();
  }

  /**
   * @category user查询数据列表(带分页)
   * @author mingyisoft代码生成工具
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/pagelist")
  public Map<String, Object> pagelist(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    Page p = adminUserService.findPageEasyuiUser(RequestUtil.getParameterMap(request));
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    return responseMap;
  }

  /**
   * @category user查询数据列表(不带分页)
   * @author mingyisoft代码生成工具
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/list")
  public Map<String, Object> list(HttpServletRequest request) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    responseMap.put("data", adminUserService.findList(RequestUtil.getParameterMap(request), "*"));
    return responseMap;
  }

  /**
   * @category 适用于easyui下拉框
   * @author mingyisoft代码生成工具
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/selectlist")
  public List<Map<String, Object>> selectlist(HttpServletRequest request) throws Exception {
    return adminUserService.findList(RequestUtil.getParameterMap(request), "*");
  }

  /**
   * @category 通过ID查询数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/get")
  public Map<String, Object> get(HttpServletRequest request) throws Exception {
    return adminUserService.findOne(RequestUtil.getParameterMap(request), "*");
  }

  /**
   * @category 通过ID删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public JsonMessage delete(String ids) throws Exception {
    adminUserService.delete(ids);
    return new JsonMessage();
  }

  /**
   * @category 查看详细信息
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  @RequestMapping("/detail/{keyId}")
  public String detail(Model model, @PathVariable(value = "keyId") String keyId) throws Exception {
    model.addAttribute("obj", adminUserService.findOneByKeyId(keyId, "*"));
    // 访问地址为 localhost:8080/hwj/demo/detail/主键
    // jsp路径为 hwj\src\main\webapp\WEB-INF\jsp\demo\demo_detail.jsp
    return "admin/admin_user_detail";
  }

  /**
   * 
   * Title: 潜客管理中的 添加新用户<br>
   * Description: addNewUser<br>
   * CreateDate: 2015年11月23日 下午5:02:04<br>
   * 
   * @category 潜客管理中的 添加新用户
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/addNewUser")
  public JsonMessage addNewUser(HttpServletRequest request, HttpSession session,
      @Valid UserPhoneValidationForm userPhoneValidationForm, BindingResult result)
          throws Exception {
    JsonMessage json = new JsonMessage(false, "新用户注册成功失败!");
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    // 手机号不允许重复判断
    String phone = userPhoneValidationForm.getPhone();
    Map<String, Object> returnMap = userService.findOne("phone", phone, "key_id");
    if (returnMap != null) {
      json.setSuccess(false);
      json.setMsg("手机号已被使用，请重新输入！");
    } else {
      // 正常情况下的 注册逻辑
      try {
        // 生成6位随机数
        String random6BitNumber = String.valueOf((Math.random() * 9 + 1) * 100000).substring(0, 6);
        logger.info("随机生成的6位有效密码是：" + random6BitNumber);

        UserRegisterParam userRegisterParam = new UserRegisterParam();
        userRegisterParam.setPhone(phone);
        userRegisterParam.setPwd(SHAUtil.encode(random6BitNumber));

        /**
         * modify by athrun.cw 2016年5月23日15:38:52 bug 342
         * LC后台帮潜客注册，`t_user`表中create_user_id为空
         */
        userRegisterParam
            .setCreateUserId(SessionUtil.getSessionAdminUser(request).getKeyId());
        Map<String, Object> insertUser = indexService.saveUser(userRegisterParam);
        // modify by seven terry 需求增加userid
        json.setData(insertUser.get("key_id"));

        // 2.发送短信SmsUtil.addMsgToQueue(sessionUser.getPhone(), 1000,
        // "你已经购买成功！");
        SmsUtil.sendSmsToQueue(phone,
            SmsConstant.PREFIX_SPEAKHI + random6BitNumber + SmsConstant.REGISTER_NEW_USER);

        json.setSuccess(true);
        json.setMsg("新用户 [" + phone + "] 注册成功！随机生成的密码是：" + random6BitNumber);

        logger.debug("新用户注册成功成功！随机生成的密码是：" + json.getData());
      } catch (Exception e) {
        json.setSuccess(false);
        json.setMsg("新用户注册失败!");
        logger.error("新用户注册失败!" + e.getMessage());
      }
    }
    return json;
  }

  /**
   * 
   * Title: 后台双击行元素，查看学员详情<br>
   * Description: lookUserDetail<br>
   * CreateDate: 2015年11月24日 上午11:10:40<br>
   * 
   * @category 后台双击行元素，查看学员详情
   * @author athrun.cw
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @RequestMapping("/lookUserDetail/{userId}")
  public String lookUserDetail(HttpServletRequest request, @PathVariable("userId") String userId,
      Model model) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    model.addAttribute("userId", userId);
    // modified by ivan.mgh,2016年4月25日14:26:16
    model.addAttribute("isHavaChangeOrderCoursePricePermission",
        sessionAdminUser.isHavePermisson("contract:changePrice"));
    // modified by seven,2016年6月1日13:09:16
    return "/admin/user/userinfo_detail";
  }

  /**
   * 
   * Title: 后天个人详情数据<br>
   * Description: lookUserDetailInfo<br>
   * CreateDate: 2017年6月20日 上午10:33:19<br>
   * 
   * @category 后天个人详情数据
   * @author seven.gz
   * @param request
   * @param userId
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/lookUserDetailInfo/{userId}")
  public Map<String, Object> lookUserDetailInfo(HttpServletRequest request,
      @PathVariable("userId") String userId) throws Exception {
    // 不需要从request获取参数，因为easyui会额外传递"_"的参数
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", userId);
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> returnMap = new HashMap<String, Object>();
    try {
      logger.info("后台管理员 [" + sessionAdminUser.getKeyId() + "] 开始查看学员详情...");

      // modify by seven 2017年3月28日13:53:19 学员详情显示lc 之前写了个“*”
      Map<String, Object> user = userService.findOneByKeyId(userId,
          "key_id,phone,init_level,current_level,test_level,user_name,user_code,learning_coach_id,is_student");
      returnMap.put("user", user);
      returnMap.put("userInfo", userService.findUserInfoByUserId(paramMap));

      if (user != null) {
        returnMap.put("badminUser", adminBdminUserService.findOne("key_id", (String) user.get(
            "learning_coach_id"),
            "admin_user_name,email,telphone,weixin"));
      }

      // modified by ivan.mgh,2016年4月25日14:26:16
      returnMap.put("isHavaChangeOrderCoursePricePermission",
          sessionAdminUser.isHavePermisson("contract:changePrice"));
      // modified by seven,2016年6月1日13:09:16
      returnMap.put("generalStudentInfo",
          adminGeneralStudentService.findGeneralStudentDetailInfo(userId));
      logger.info("后台管理员 [" + sessionAdminUser.getKeyId() + "] 查看学员详情成功...");
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("后台管理员 [" + sessionAdminUser.getKeyId() + "] 查看学员详情失败...");
      logger.error(e.getMessage());
    }

    return returnMap;
  }

  /**
   * 
   * Title: 获取用户合同中包含的所有的CategoryType<br>
   * Description: findUserCategoryTypeList<br>
   * CreateDate: 2015年11月24日 下午3:49:18<br>
   * 
   * @category 获取用户合同中包含的所有的CategoryType
   * @author athrun.cw
   * @param request
   * @param userId
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findUserCategoryTypeList/{userId}")
  public List<Map<String, Object>> findUserCategoryTypeList(HttpServletRequest request,
      @PathVariable("userId") String userId) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    // 获取用户购买的体系类型列表（查合同表）
    paramMap.put("user_id", userId);
    paramMap.put("order_status", OrderStatusConstant.ORDER_STATUS_HAVE_PAID);
    List<Map<String, Object>> categoryTypeList = new ArrayList<Map<String, Object>>();
    try {
      logger.info("后台管理员 [" + sessionAdminUser.getKeyId() + "] 开始查看用户合同中包含的所有的CategoryType...");
      categoryTypeList = orderCourseService.findList(paramMap, "category_type");
      logger.info("后台管理员 [" + sessionAdminUser.getKeyId() + "] 查看用户合同中包含的所有的CategoryType成功...");
    } catch (Exception e) {
      logger.error("后台管理员 [" + sessionAdminUser.getKeyId() + "] 查看用户合同中包含的所有的CategoryType失败...");
      logger.error(e.getMessage());
    }
    // 返回的体系类别 需要按照要求显示name
    return UserCategoryTypeUtil.formatCategoryType2OrderType(categoryTypeList);
  }

  /**
   * 
   * Title: 根据条件，查询学员能够预约的课程列表<br>
   * Description: findUserCourseList<br>
   * CreateDate: 2015年11月25日 下午5:06:07<br>
   * 
   * @category 根据条件，查询学员能够预约的课程列表
   * @author athrun.cw
   * @param request:
   *          category_type & course_type & user_id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findUserCourseList")
  public List<Map<String, Object>> findUserCourseList(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    // 根据传入的user_id 查询当前学员能够预约课程的级别current_level
    Map<String, Object> userMap = userService.findOneByKeyId(paramMap.get("user_id"),
        "current_level");
    if (userMap == null) {
      logger.error("后台管理员 [" + sessionAdminUser.getKeyId() + "] 查看学员能够预约的课程列表失败，找不到当前学院信息...");
      throw new RuntimeException("数据错误，当前学员不存在~");
    }
    paramMap.put("current_level", userMap.get("current_level"));

    List<Map<String, Object>> courseList = new ArrayList<Map<String, Object>>();
    try {
      logger.info("后台管理员 [" + sessionAdminUser.getKeyId() + "] 开始查看学员能够预约的课程列表...");
      courseList = adminUserService.findUserCourseList(paramMap);
      logger.info("后台管理员 [" + sessionAdminUser.getKeyId() + "] 查看学员能够预约的课程列表...");
    } catch (Exception e) {
      logger.error("后台管理员 [" + sessionAdminUser.getKeyId() + "] 查看学员能够预约的课程列表失败...");
      logger.error(e.getMessage());
    }
    return courseList;
  }

  /**
   * 
   * Title: 管理员代订课：查询符合条件的能够预约课程 <br>
   * Description: subscribeCoursePageList<br>
   * CreateDate: 2015年11月26日 上午11:16:56<br>
   * 
   * @category 管理员代订课：查询符合条件的能够预约课程
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/caowei123")
  public Map<String, Object> findTeacherPage(HttpServletRequest request) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    try {
      logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始查询符合条件的能够预约课程...");

      Page p = adminUserService.findTeacherTimePageEasyui(paramMap);
      paramMap.put("total", p.getTotalCount());
      paramMap.put("rows", p.getDatas());
    } catch (Exception e) {
      logger.error("后台管理用户id [" + sessionAdminUser.getKeyId() + "，错误error:" + e.getMessage());
    }
    return paramMap;
  }

  /**
   * 
   * Title: 待订课<br>
   * Description: SubscribeCourseByAdmin<br>
   * CreateDate: 2015年11月26日 下午6:28:28<br>
   * 
   * @category 待订课
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/subscribeCourseByAdmin")
  public CommonJsonObject subscribeCourseByAdmin(HttpServletRequest request,
      @Valid SubscribeParam subscribeParam, BindingResult result) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    /**
     * modified by komi 2016年12月14日11:21:30 改为通用预约
     */
    if (StringUtils.isEmpty(subscribeParam.getUserId())) {
      json.setMsg("请选择一个用户！");
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      return json;
    }

    SessionUser sessionUser = userService.initSessionUser(subscribeParam.getUserId(), null);
    // 组装subscribeCourse对象
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setUserId(sessionUser.getKeyId());
    subscribeCourse.setUserPhone(sessionUser.getPhone());
    subscribeCourse.setCourseType(subscribeParam.getCourseType());
    subscribeCourse.setUserLevel(sessionUser.getCurrentLevel());
    subscribeCourse.setCourseId(subscribeParam.getCourseId());
    subscribeCourse.setTeacherTimeId(subscribeParam.getTeacherTimeId());
    subscribeCourse.setCreateUserId(sessionAdminUser.getKeyId());
    subscribeCourse.setUpdateUserId(sessionAdminUser.getKeyId());
    subscribeCourse.setSubscribeFrom("admin");

    json = baseSubscribeCourseService.subscribeEntry(subscribeCourse, sessionUser);

    return json;
  }

  /**
   * 
   * Title: 学员管理中，双击详情页面dialog中的 订课详情<br>
   * Description: findSubscribedCourseByUserId<br>
   * CreateDate: 2015年11月29日 下午2:53:13<br>
   * 
   * @category 学员管理中，双击详情页面dialog中的 订课详情
   * @author athrun.cw
   * @param request
   * @param userId
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findSubscribedCourseByUserId/{userId}")
  public Map<String, Object> findSubscribedCourseByUserId(HttpServletRequest request,
      @PathVariable("userId") String userId) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("user_id", userId);
    try {
      logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始在学员详情dialog中，查看订课详情...");
      Page p = adminUserService.findSubscribedCourseByUserIdEasyui(paramMap);
      paramMap.put("total", p.getTotalCount());
      paramMap.put("rows", p.getDatas());
    } catch (Exception e) {
      logger.error("后台管理用户id [" + sessionAdminUser.getKeyId() + "在学员详情dialog中，查看订课详情，错误error:"
          + e.getMessage(), e);
    }
    return paramMap;
  }

  /**
   * 
   * Title: 代学员取消课程<br>
   * Description: cancelSubscribeCourseByAdmin<br>
   * CreateDate: 2015年11月29日 下午4:52:57<br>
   * 
   * @category 代学员取消课程
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/cancelSubscribeCourseByAdmin")
  public CommonJsonObject cancelSubscribeCourseByAdmin(HttpServletRequest request)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    /**
     * modified by komi 2016年12月14日11:21:30 改为通用取消预约
     */
    // 组装subscribeCourse对象
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setKeyId(paramMap.get("key_id").toString());
    subscribeCourse.setUpdateUserId(sessionAdminUser.getKeyId());
    User user = userEntityDao.findUserByUserIdOrPhone(paramMap.get("user_id").toString());
    if (user == null) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("请选择一名用户");
      return json;
    }
    subscribeCourse.setUserPhone(user.getPhone());
    subscribeCourse.setSubscribeFrom("admin");

    json = baseSubscribeCourseService.cancelSubscribeEntry(subscribeCourse);

    return json;
  }

  /**
   * 
   * Title:修改用户详细信息<br>
   * Description: editUserInfo<br>
   * CreateDate: 2016年3月18日 上午10:18:45<br>
   * 
   * @category 修改用户详细信息
   * @author komi.zsy
   * @param request
   * @param userInfoValidationForm
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/editUserInfo")
  public JsonMessage editUserInfo(HttpServletRequest request,
      @Valid UserInfoValidationForm userInfoValidationForm, BindingResult result) throws Exception {
    JsonMessage json = new JsonMessage();

    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    SessionAdminUser sessionAdmin = SessionUtil.getSessionAdminUser(request);

    // 用这种方式，可以获取页面传来的用户key_id，自身session的是admin的key_id
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    // @SuppressWarnings("unchecked")
    // Map<String, Object> paramMap =
    // MapUtil.convertBean(userInfoValidationForm);

    paramMap.put("update_user_id", sessionAdmin.getKeyId());

    try {
      // 预约subcribe_id上课，课程预约subscribe
      logger.info("管理员[" + sessionAdmin.getAdminUserName() + "] 开始更新资料...");
      json = userService.updateUserInfoByUserOrAdminUser(paramMap, request);
      logger.info("管理员 [" + sessionAdmin.getAdminUserName() + "] 更新资料成功！");
      json.setSuccess(true);
    } catch (Exception e) {
      logger.info("管理员 [" + sessionAdmin.getAdminUserName() + "] 更新资料失败！");
      logger.error("error:" + e.getMessage());
      json.setMsg(e.getMessage());
      json.setSuccess(false);
    }
    return json;
  }

  /**
   * 
   * Title: findOrderCourseList<br>
   * Description: findOrderCourseList<br>
   * CreateDate: 2016年3月28日 下午3:01:26<br>
   * 
   * @category findOrderCourseList
   * @author athrun.cw
   * @param request
   * @param userId
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findOrderCourseListByUserId/{userId}")
  public Map<String, Object> findOrderCourseListByUserId(HttpServletRequest request,
      @PathVariable("userId") String userId) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("user_id", userId);
    try {
      logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始查看合同详情...");
      Page p = adminOrderCourseService.findOrderCourseByUserIdEasyui(paramMap);
      paramMap.put("total", p.getTotalCount());
      paramMap.put("rows", p.getDatas());
    } catch (Exception e) {
      logger.error("后台管理用户id [" + sessionAdminUser.getKeyId() + "查看合同详情，错误error:" + e.getMessage(),
          e);
    }
    return paramMap;
  }

  /**
   * Title: 查询学员rsa学习进度<br>
   * Description: findRsaLearningProgress<br>
   * CreateDate: 2017年9月13日 上午11:44:28<br>
   * 
   * @category 查询学员rsa学习进度
   * @author seven.gz
   * @param request
   * @param userId
   * @param page
   * @param rows
   */
  @ResponseBody
  @RequestMapping("/findRsaLearningProgress/{userId}")
  public Map<String, Object> findRsaLearningProgress(HttpServletRequest request,
      @PathVariable("userId") String userId, Integer page, Integer rows) throws Exception {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    Page p = sutdentLearningProgressService.findRsaLearningProgress(page, rows, userId);
    paramMap.put("total", p.getTotalCount());
    paramMap.put("rows", p.getDatas());

    return paramMap;
  }
}
