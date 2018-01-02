package com.webi.hwj.ordercourse.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.param.SaveOrderCourseParam;
import com.webi.hwj.ordercourse.service.AdminOrderCourseSaveService;
import com.webi.hwj.ordercourse.service.AdminOrderCourseService;
import com.webi.hwj.ordercourse.util.RenewalOrderCourseUtil;
import com.webi.hwj.user.service.AdminUserInfoService;
import com.webi.hwj.util.CrmUtil;
import com.webi.hwj.util.SessionUtil;

import net.sf.json.JSONObject;

/**
 * @category orderCourse控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/admin/orderCourse")
public class AdminOrderCourseController {
  private static Logger logger = Logger.getLogger(AdminOrderCourseController.class);

  @Resource
  private AdminOrderCourseService adminOrderCourseService;

  @Resource
  private AdminOrderCourseSaveService adminOrderCourseSaveService;

  @Resource
  private AdminUserInfoService adminUserInfoService;

  /**
   * @category orderCourse后台管理主页面
   * @author mingyisoft代码生成工具
   * @param model
   * @return
   */
  @RequestMapping("/index")
  public String index(HttpServletRequest request, Model model) {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

    // modified by ivan.mgh,2016年4月25日14:26:16
    model.addAttribute("isHavaChangeOrderCoursePricePermission",
        sessionAdminUser.isHavePermisson("contract:changePrice"));

    return "admin/ordercourse/admin_ordercourse";
  }

  /**
   * 
   * Title: 找到是否存在 未付款的合同<br>
   * Description: whetherCanMakeNewOrder<br>
   * CreateDate: 2016年1月11日 下午4:41:31<br>
   * 
   * @category 找到是否存在 未付款的合同
   * @author athrun.cw
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping("/whetherCanMakeNewOrder")
  public JsonMessage whetherCanMakeNewOrder(HttpServletRequest request, Model model) {
    JsonMessage json = new JsonMessage();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    doWhetherCanMakeNewOrder(request, json, sessionAdminUser);
    return json;

  }

  /**
   * Title: 找是否有未支付的合同<br>
   * Description: （拟定合同的时候需要判断当前用户是否有没支付完成的订单，如果有的话，不能让管理员拟定合同。） <br>
   * CreateDate: 2016年5月xx日 下午3:23:14<br>
   * 
   * @category 找是否有未支付的合同
   * @author ivan
   * @param request
   * @param json
   * @param sessionAdminUser
   */
  private void doWhetherCanMakeNewOrder(HttpServletRequest request, JsonMessage json,
      SessionAdminUser sessionAdminUser) {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    try {
      logger.info(
          "后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始判定学员id [" + paramMap.get("user_id")
              + "] 是否能拟定合同...");
      Map<String, Object> notPaidOrderCourse = adminOrderCourseService
          .findNotPaidOrderCourseByUserId(paramMap);
      if (notPaidOrderCourse == null) {
        json.setSuccess(true);
        json.setMsg("可以拟定新合同~");
        logger.info(
            "后台管理用户id [" + sessionAdminUser.getKeyId() + "] 判定学员id [" + paramMap.get("user_id")
                + "] 可以拟定合同！");
      } else {
        json.setSuccess(false);
        json.setMsg("还有未支付的其他合同，无法拟定新合同~");
        logger.info(
            "后台管理用户id [" + sessionAdminUser.getKeyId() + "] 判定学员id [" + paramMap.get("user_id")
                + "] 还有未支付的其他合同，无法拟定新合同！");
      }
    } catch (Exception e) {
      logger.error("拟定合同失败，error:" + e.getMessage());
      json.setSuccess(false);
      json.setMsg("拟定合同失败， error:" + e.getMessage());
    }
  }

  /**
   * 
   * Title: 删除订单（order_id）<br>
   * Description: deleteOrderCourse<br>
   * CreateDate: 2016年1月20日 下午3:21:19<br>
   * 
   * @category 删除订单（order_id）
   * @author athrun.cw
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping("/deleteOrderCourse")
  public CommonJsonObject deleteOrderCourse(HttpServletRequest request, Model model) {
    CommonJsonObject json = new CommonJsonObject();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    // 添加权限控制
    // modified by alex.ymh 2016年2月14日17:56:48
    if (sessionAdminUser.isHavePermisson("contract:deleteOrder")) {
      Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
      try {
        logger.info(
            "后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始删除合同id [" + paramMap.get("order_id")
                + "] ...");
        json = adminOrderCourseService.deleteOrderCourse(paramMap, sessionAdminUser.getKeyId());
      } catch (Exception e) {
        logger.error("删除合同失败，error:" + e.getMessage());
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg("删除合同失败， error:" + e.getMessage());
      }
    } else {
      logger.error("没有权限，adminUserId = :" + sessionAdminUser.getKeyId());
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("没有权限!");
    }

    return json;

  }

  /**
   * 
   * Title: 撤回合同<br>
   * Description: withdrawOrderCourse<br>
   * CreateDate: 2016年1月22日 下午3:21:25<br>
   * 
   * @category 撤回合同
   * @author athrun.cw
   * @param request
   * @param model
   * @return
   */
  @ResponseBody
  @RequestMapping("/withdrawOrderCourse")
  public JsonMessage withdrawOrderCourse(HttpServletRequest request, Model model) {
    JsonMessage json = new JsonMessage();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    try {
      logger.info(
          "后台管理用户id [" + sessionAdminUser.getKeyId() + "] 开始撤回合同id [" + paramMap.get("order_id")
              + "] ...");
      json = adminOrderCourseService.withdrawOrderCourse(paramMap);
    } catch (Exception e) {
      logger.error("撤回合同失败，error:" + e.getMessage());
      json.setSuccess(false);
      json.setMsg("撤回合同失败， error:" + e.getMessage());
    }
    return json;

  }

  /**
   * @category orderCourseOption保存
   * @author mingyisoft代码生成工具
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/save")
  public JsonMessage save(HttpServletRequest request, ModelMap model) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    if (paramMap.get("key_id") != null && !"".equals(paramMap.get("key_id"))) {// 修改
      paramMap.put("update_date", new Date());
      paramMap.put("update_user_id", sessionAdminUser.getKeyId());
      adminOrderCourseService.update(paramMap);
    } else {// 新增
      paramMap.put("create_date", new Date());
      paramMap.put("create_user_id", sessionAdminUser.getKeyId());
      adminOrderCourseService.insert(paramMap);
    }

    return new JsonMessage();
  }

  /**
   * @category orderCourse查询数据列表(带分页)
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

    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    // modify by seven 2017年4月20日14:55:52 cc只能看自己开通的合同
    if (sessionAdminUser.getRoleId() != null && sessionAdminUser.getRoleId().equals(MemcachedUtil
        .getConfigValue(ConfigConstant.CC_ROLE_ID))) {
      paramMap.put("ccAdminUserId", sessionAdminUser.getKeyId());
    }

    Page p = adminOrderCourseService.findOrderCourseList(paramMap);
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    return responseMap;
  }

  /**
   * @category orderCourse查询数据列表(不带分页)
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
    responseMap.put("data",
        adminOrderCourseService.findList(RequestUtil.getParameterMap(request), "*"));
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
    logger.info("123");
    return adminOrderCourseService.findList(RequestUtil.getParameterMap(request), "*");
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
    return adminOrderCourseService.findOne(RequestUtil.getParameterMap(request), "*");
  }

  /**
   * @category 通过ID查询数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/getJsonObj")
  public JsonMessage getJsonObj(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();

    try {
      json.setData(adminOrderCourseService.findOne(RequestUtil.getParameterMap(request), "*"));
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("系统出现异常!");
    }

    return json;
  }

  @ResponseBody
  @RequestMapping("/getJsonObj/crm")
  public JsonMessage crmGetJsonObj(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();

    try {
      Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
      paramMap.remove("auth");

      json.setData(adminOrderCourseService.findOne(paramMap, "*"));
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setSuccess(false);
      json.setMsg("系统出现异常!");
    }

    return json;
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
    adminOrderCourseService.delete(ids);
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
    model.addAttribute("obj", adminOrderCourseService.findOneByKeyId(keyId, "*"));
    // 访问地址为 localhost:8080/hwj/demo/detail/主键
    // jsp路径为 hwj\src\main\webapp\WEB-INF\jsp\demo\demo_detail.jsp
    return "admin/admin_ordercourse_detail";
  }

  /**
   * 
   * Title: 合同续约时，拟定好合同后管理员可以点击续约合同按钮调用接口 将续约合同的父类（根）迭代 全部查询出来 <br>
   * Description: findRenewalOrderCourseDetail<br>
   * CreateDate: 2016年3月28日 下午5:12:28<br>
   * 
   * @category 合同续约时，拟定好合同后管理员可以点击续约合同按钮调用接口 将续约合同的父类（根）迭代 全部查询出来
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/findRenewalOrderCourseDetail", method = RequestMethod.POST)
  public Map<String, Object> findRenewalOrderCourseDetail(@RequestBody Map<String, Object> paramMap)
      throws Exception {
    Map<String, Object> renewalOrderCourse = new HashMap<String, Object>();
    if (paramMap == null || paramMap.get("order_id") == null
        || "".equals(paramMap.get("order_id"))) {
      renewalOrderCourse.put("code", "500");
      renewalOrderCourse.put("msg", "合同续约明细接口调用失败：参数为空！");
      logger.error("合同续约明细接口调用失败：参数为空！");
      return renewalOrderCourse;
    }
    // 参数不为空
    try {
      logger.info("开始调用合同续约明细接口：订单order_id：" + paramMap.get("order_id") + "...");
      renewalOrderCourse = adminOrderCourseService.findRenewalOrderCourseDetailByOrderId(paramMap);
    } catch (Exception e) {
      renewalOrderCourse.put("code", "500");
      renewalOrderCourse.put("msg", "订单拆分提交接口错误：error=" + e.getMessage());
      logger.error("订单拆分提交接口错误:订单id：" + paramMap.get("order_id") + "错误，系统异常~" + e.getMessage());
    }

    return renewalOrderCourse;
  }

  /**
   * 
   * Title: 加载后台选择 可续约的合同<br>
   * Description: findNeedRenewalOrderCourseListByUserId<br>
   * CreateDate: 2016年3月29日 下午3:42:05<br>
   * 
   * @category 加载后台选择 可续约的合同
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findNeedRenewalOrderCourseListByUserId/{userId}")
  public List<Map<String, Object>> findNeedRenewalOrderCourseListByUserId(
      HttpServletRequest request,
      @PathVariable(value = "userId") String userId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", userId);
    List<Map<String, Object>> renewalOrderCourseMapList = adminOrderCourseService
        .findRenewalOrderCourseListByUserId(paramMap);
    for (Map<String, Object> renewalOrderCourseMap : renewalOrderCourseMapList) {
      renewalOrderCourseMap = RenewalOrderCourseUtil
          .formatRenewalOrderCourse(renewalOrderCourseMap);
    }

    return renewalOrderCourseMapList;
  }

  /**
   * 
   * Title: 拟定（续约）保存新合同<br>
   * Description: saveOrderCourse<br>
   * CreateDate: 2016年4月13日 下午2:03:24<br>
   * 
   * @category 拟定（续约）保存新合同
   * @author athrun.cw
   * @param request
   * @param saveOrderCourseParam
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/saveOrderCourse")
  public JsonMessage saveOrderCourse(HttpServletRequest request,
      SaveOrderCourseParam saveOrderCourseParam)
          throws Exception {
    JsonMessage json = new JsonMessage();

    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    // modified by komi 2016年11月9日11:10:10 有学员续约的时候，课时数不对，所以打出来看看，bug480
    logger.info("合同课时详情------->" + paramMap);

    try {

      // 合并赠送相同课程
      List<OrderCourseOption> giftOptionsMergeList = new ArrayList<OrderCourseOption>();
      // 合并相同课程
      List<OrderCourseOption> normalOptionsMergeList = new ArrayList<OrderCourseOption>();

      // 处理合同子表数据（转换、合并）
      this.conversionGiftAndNormalOptionsList(paramMap, sessionAdminUser.getKeyId(),
          giftOptionsMergeList,
          normalOptionsMergeList);

      String orderId = adminOrderCourseSaveService.saveOrderCourseAndOption(saveOrderCourseParam,
          sessionAdminUser.getKeyId(), sessionAdminUser.getKeyId(), giftOptionsMergeList,
          normalOptionsMergeList);
      json.setData(orderId);
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg(e.getMessage());
      e.printStackTrace();
      logger.error("系统出错! error:" + e.toString(), e);
    }

    return json;
  }

  /**
   * Title: 保存新合同（CRM专用）<br>
   * Description: crmSaveOrderCourse<br>
   * CreateDate: 2016年4月28日 下午1:39:10<br>
   * 
   * @category 保存新合同（CRM专用）
   * @author ivan.mgh
   * @param request
   * @param saveOrderCourseParam
   * @param result
   * @see AdminOrderCourseController#saveOrderCourse(HttpServletRequest,
   *      SaveOrderCourseParam, BindingResult)
   * @return
   * @throws Exception
   */
  @ResponseBody
  @SuppressWarnings("all")
  @RequestMapping(value = "/saveOrderCourse/crm", method = RequestMethod.POST,
      produces = "text/html;charset=UTF-8")
  public String crmSaveOrderCourse(HttpServletRequest request,
      @Valid SaveOrderCourseParam saveOrderCourseParam,
      BindingResult result) throws Exception {
    JsonMessage json = new JsonMessage();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");

      return JSONObject.fromObject(json).toString();
    }

    SessionAdminUser sessionAdminUser = CrmUtil.findAdminUserFromCache(request);

    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    String authParam = paramMap.get("auth").toString();
    // auth用于校验授权，移除掉
    paramMap.remove("auth");

    try {

      // 合并赠送相同课程
      List<OrderCourseOption> giftOptionsMergeList = new ArrayList<OrderCourseOption>();
      // 合并相同课程
      List<OrderCourseOption> normalOptionsMergeList = new ArrayList<OrderCourseOption>();

      // 处理合同子表数据（转换、合并）
      this.conversionGiftAndNormalOptionsList(paramMap, sessionAdminUser.getKeyId(),
          giftOptionsMergeList,
          normalOptionsMergeList);

      saveOrderCourseParam.setIsCrm(true);
      String orderId = adminOrderCourseSaveService.crmSaveOrderCourseAndOption(saveOrderCourseParam,
          sessionAdminUser.getKeyId(), giftOptionsMergeList, normalOptionsMergeList, authParam);
      json.setData(orderId);
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg(e.toString());
      e.printStackTrace();
      logger.error("系统出错! error:" + e.getMessage(), e);
    }

    return JSONObject.fromObject(json).toString();
  }

  /**
   * Title: 合并合同子表数据<br>
   * Description: 合并合同子表数据<br>
   * CreateDate: 2016年8月22日 下午12:53:40<br>
   * 
   * @category 合并合同子表数据
   * @author komi.zsy
   * @param paramMap
   * @param updateUserId
   * @param giftOptionsList
   * @param normalOptionsList
   * @throws Exception
   */
  public void conversionGiftAndNormalOptionsList(Map<String, Object> paramMap, String updateUserId,
      List<OrderCourseOption> giftOptionsMergeList, List<OrderCourseOption> normalOptionsMergeList)
          throws Exception { // 普通子表数据
    int normalOptions = 0;
    // 赠送子表数据
    int giftOptions = 0;

    // 合并赠送相同课程后的map
    Map<String, OrderCourseOption> giftOptionsMergeMap = new HashMap<String, OrderCourseOption>();
    // 合并相同课程后的map
    Map<String, OrderCourseOption> normalOptionsMergeMap = new HashMap<String, OrderCourseOption>();

    // 遍历参数
    for (String key : paramMap.keySet()) {
      if (key.indexOf("courseType_") != -1) {//
        normalOptions++;
      } else if (key.indexOf("courseTypeGift_") != -1) {
        giftOptions++;
      }
    }

    // 组装数据，赠送合同子表集合
    if (giftOptions > 0) {
      for (int i = 1; i <= giftOptions; i++) {
        if (paramMap.get("courseTypeGift_" + i) != null
            && !"".equals(paramMap.get("courseTypeGift_" + i))
            && paramMap.get("realPriceGift_" + i) != null
            && !"".equals(paramMap.get("realPriceGift_" + i))
            && paramMap.get("courseCountGift_" + i) != null
            && !"".equals(paramMap.get("courseCountGift_" + i))
            && !StringUtils.isEmpty((String) paramMap.get("courseUnitTypeGift_" + i))) {

          /**
           * modified by komi 2016年8月22日11:48:46 合并赠送相同课程信息
           */
          if (giftOptionsMergeMap.get(paramMap.get("courseTypeGift_" + i) + ","
              + paramMap.get("courseUnitTypeGift_" + i)) == null) {
            // 没有相同课程
            OrderCourseOption giftOption = new OrderCourseOption();
            giftOption.setCourseType(paramMap.get("courseTypeGift_" + i) + "");
            giftOption.setRealPrice(Integer.valueOf(paramMap.get("realPriceGift_" + i) + ""));
            giftOption.setCourseCount(Integer.valueOf(paramMap.get("courseCountGift_" + i) + ""));
            /**
             * modify by athrun.cw 2016年4月12日17:01:54 续约的 合同，课程数(用于续约的合同展示)
             */
            giftOption.setShowCourseCount(giftOption.getCourseCount());
            giftOption
                .setRemainCourseCount(Integer.valueOf(paramMap.get("courseCountGift_" + i) + ""));
            /**
             * modified by komi 2016年8月22日11:48:46 增加赠送合同 更新人id
             */
            giftOption.setCreateUserId(updateUserId);
            giftOption.setUpdateUserId(updateUserId);
            giftOption.setIsGift(true);
            /**
             * modified by komi 2016年7月6日14:58:43 增加课程单位类型
             */
            giftOption
                .setCourseUnitType(Integer.valueOf(paramMap.get("courseUnitTypeGift_" + i) + ""));

            giftOptionsMergeMap.put(
                paramMap.get("courseTypeGift_" + i) + "," + paramMap.get("courseUnitTypeGift_" + i),
                giftOption);
          } else {
            // 有相同课程，合并数量
            OrderCourseOption giftOptionTemp = (OrderCourseOption) giftOptionsMergeMap.get(
                paramMap.get("courseTypeGift_" + i) + ","
                    + paramMap.get("courseUnitTypeGift_" + i));
            giftOptionTemp.setCourseCount(giftOptionTemp.getCourseCount()
                + Integer.valueOf(paramMap.get("courseCountGift_" + i) + ""));
            giftOptionTemp.setShowCourseCount(giftOptionTemp.getCourseCount());
            giftOptionTemp.setRemainCourseCount(giftOptionTemp.getCourseCount());
          }
        }
      }
    }

    // 组装数据，合同子表集合
    if (normalOptions > 0) {
      for (int i = 0; i < normalOptions; i++) {
        if (StringUtils.isNotEmpty((String) paramMap.get("courseType_" + i))
            && StringUtils.isNotEmpty((String) paramMap.get("realPrice_" + i))
            && StringUtils.isNotEmpty((String) paramMap.get("courseCount_" + i))
            && StringUtils.isNotEmpty((String) paramMap.get("courseUnitType_" + i))) {

          if (normalOptionsMergeMap.get(paramMap.get("courseType_" + i)
              + "," + paramMap.get("courseUnitType_" + i)) == null) {
            OrderCourseOption orderCourseOption = new OrderCourseOption();
            orderCourseOption.setCourseType(paramMap.get("courseType_" + i) + "");
            orderCourseOption.setRealPrice(Integer.valueOf(paramMap.get("realPrice_" + i) + ""));
            orderCourseOption.setCourseCount(Integer.valueOf(paramMap.get("courseCount_" + i)
                + ""));
            /**
             * modify by athrun.cw 2016年4月12日17:01:54 续约的 合同，课程数(用于续约的合同展示)
             */
            orderCourseOption.setShowCourseCount(orderCourseOption.getCourseCount());
            orderCourseOption
                .setRemainCourseCount(orderCourseOption.getCourseCount());
            orderCourseOption.setCreateUserId(updateUserId);
            orderCourseOption.setUpdateUserId(updateUserId);
            /**
             * modified by komi 2016年7月6日14:58:43 增加课程单位类型
             */
            orderCourseOption
                .setCourseUnitType(Integer.valueOf(paramMap.get("courseUnitType_" + i) + ""));

            normalOptionsMergeMap.put(paramMap.get("courseType_" + i)
                + "," + paramMap.get("courseUnitType_" + i), orderCourseOption);
          } else {
            // 有相同课程，合并数量
            OrderCourseOption orderCourseOption = (OrderCourseOption) normalOptionsMergeMap.get(
                paramMap.get("courseType_" + i)
                    + "," + paramMap.get("courseUnitType_" + i));
            orderCourseOption.setCourseCount(orderCourseOption.getCourseCount()
                + Integer.valueOf(paramMap.get("courseCount_" + i) + ""));
            orderCourseOption.setShowCourseCount(orderCourseOption.getCourseCount());
            orderCourseOption.setRemainCourseCount(orderCourseOption.getCourseCount());
          }
        }

      }
    }

    giftOptionsMergeList.addAll(giftOptionsMergeMap.values());
    normalOptionsMergeList.addAll(normalOptionsMergeMap.values());
  }

  /**
   * Title: 转到拟定合同页面<br>
   * Description: 拟定合同页面<br>
   * CreateDate: 2016年3月31日 下午1:42:18<br>
   * 
   * @category 拟定合同页面
   * @author ivan.mgh
   * @param request
   *          请求
   * @return
   * @throws UnsupportedEncodingException
   */
  @RequestMapping(value = "/crm/add")
  public ModelAndView crmAddOrderCourse(HttpServletRequest request)
      throws UnsupportedEncodingException {
    // 缓存的授权凭证
    Map<String, Object> map = CrmUtil.findCachedCrmAuthMap(request);

    Map<String, Object> authMap = createAuthMap(map, request);
    ModelAndView mv = new ModelAndView(
        "admin/ordercourse/ordercoursesave/admin_ordercourse_save_insert", "authMap",
        authMap);
    return mv;
  }

  /**
   * Title: 创建数据模型<br>
   * Description: 从缓存中获取登录数据，创建数据模型，用于传到前端页面<br>
   * CreateDate: 2016年4月28日 上午11:47:46<br>
   * 
   * @category 创建数据模型
   * @author ivan.mgh
   * @param map
   * @return
   * @throws UnsupportedEncodingException
   */
  @SuppressWarnings("unchecked")
  private Map<String, Object> createAuthMap(Map<String, Object> map, HttpServletRequest request)
      throws UnsupportedEncodingException {
    Map<String, Object> authMap = new HashMap<>();
    authMap.put("crmflag", Boolean.TRUE);

    Map<String, Object> userMap = (Map<String, Object>) map.get("CRM_AUTH_USER");
    Map<String, Object> badminUserMap = (Map<String, Object>) map.get("CRM_AUTH_BADMIN_USER");

    authMap.put("auth", map.get("CRM_AUTH_KEY"));
    authMap.put("seller", badminUserMap.get("admin_user_name"));
    authMap.put("user_id", userMap.get("key_id"));
    authMap.put("user_phone", userMap.get("phone"));
    authMap.put("user_name", userMap.get("user_name"));
    authMap.put("current_level", userMap.get("current_level"));
    authMap.put("isCrmHavaChangeOrderCoursePricePermission",
        CrmUtil.isAdminUserHavePermisson(map, "contract:changePrice"));
    authMap.put("isHaveGiveLesson", CrmUtil.isAdminUserHavePermisson(map, "contract:giveLesson"));
    authMap.put("isHaveGiveTime", CrmUtil.isAdminUserHavePermisson(map, "contract:giveTime"));

    return authMap;
  }

  /**
   * Title: 校验是否可拟新合同<br>
   * Description: CRM专用<br>
   * CreateDate: 2016年4月28日 上午11:51:41<br>
   * 
   * @category 校验是否可拟新合同
   * @author ivan.mgh
   * @param request
   * @param model
   * @see AdminOrderCourseController#whetherCanMakeNewOrder
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/crm/whetherCanMakeNewOrder")
  public JsonMessage crmWhetherCanMakeNewOrder(HttpServletRequest request, Model model)
      throws Exception {
    JsonMessage json = new JsonMessage();
    SessionAdminUser sessionAdminUser = CrmUtil.findAdminUserFromCache(request);

    doWhetherCanMakeNewOrder(request, json, sessionAdminUser);
    return json;

  }

  /**
   * Title: 合同延期<br>
   * Description: 合同延期<br>
   * CreateDate: 2017年3月9日 上午10:28:44<br>
   * 
   * @category 合同延期
   * @author komi.zsy
   * @param request
   * @param orderCourse
   *          合同数据
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/updateContractExtension")
  public CommonJsonObject updateContractExtension(HttpServletRequest request,
      OrderCourse orderCourse)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    if (sessionAdminUser.isHavePermisson("contract:contractExtension")) {
      // 合同延期
      json = adminOrderCourseService.updateContractExtension(orderCourse.getKeyId(), orderCourse
          .getLimitShowTime(), orderCourse.getLimitShowTimeUnit(), sessionAdminUser.getKeyId());
    } else {
      logger.error("没有权限，adminUserId = :" + sessionAdminUser.getKeyId());
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("没有权限!");
    }
    return json;

  }

  /**
   * Title: 更换cc<br>
   * Description: 更换cc<br>
   * CreateDate: 2017年3月28日 下午5:47:10<br>
   * 
   * @category 更换cc
   * @author seven.gz
   * @param orderId
   *          合同id
   * @param ccId
   *          ccId
   */
  @ResponseBody
  @RequestMapping("/changeCc")
  public CommonJsonObject changeCc(HttpServletRequest request, String orderId, String ccId)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    if (sessionAdminUser.isHavePermisson("contract:changeCc")) {
      if (orderId == null && "".equals(orderId)) {
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg("合同id错误！请选中至少一条数据！");
        return json;
      }
      // 更换cc
      json = adminOrderCourseService.changeCc(orderId, ccId);
    } else {
      logger.error("没有权限，adminUserId = :" + sessionAdminUser.getKeyId());
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("没有权限!");
    }
    return json;

  }
  
  /**
   * Title: 向crm推送变更合同金额<br>
   * Description: 第一次一次性向crm推送全部合同相关数据，以后可以删除<br>
   * CreateDate: 2017年7月4日 下午6:11:29<br>
   * @category 向crm推送变更合同金额 
   * @author komi.zsy
   * @param startTime 开始时间
   * @param endTime 结束时间
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/updateMoneyToCrm")
  public void updateMoneyToCrm() throws Exception {
    Date endTime = new Date();
    Date startTime = DateUtil.strToDateYYYYMMDD("2015-01-01");
    adminOrderCourseService.updateMoneyToCrm(startTime, endTime);
  }

}
