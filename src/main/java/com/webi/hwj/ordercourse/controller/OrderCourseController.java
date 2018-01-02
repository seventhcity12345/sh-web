package com.webi.hwj.ordercourse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.RequestUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.constant.SmsConstant;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.param.OrderCourseDetailParam;
import com.webi.hwj.ordercourse.param.RedeemOrderParam;
import com.webi.hwj.ordercourse.param.SaveOrderCourseParam;
import com.webi.hwj.ordercourse.service.AdminOrderCourseService;
import com.webi.hwj.ordercourse.service.OrderCourseOptionService;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.ordercourse.util.OrderContractStatusUtil;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category orderCourse控制类
 * @author mingyisoft代码生成工具
 */
@Controller
public class OrderCourseController {
  private static Logger logger = Logger.getLogger(OrderCourseController.class);

  @Resource
  private OrderCourseService orderCourseService;
  @Resource
  private OrderCourseOptionService orderCourseOptionService;

  @Resource
  private AdminOrderCourseService adminOrderCourseService;

  @Resource
  UserService userService;

  /**
   * Title: crm线下拟定合同<br>
   * Description: crm线下拟定合同<br>
   * CreateDate: 2017年3月21日 下午5:44:49<br>
   * 
   * @category crm线下拟定合同
   * @author komi.zsy
   * @param request
   * @param saveOrderCourseParam
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/orderCourse/insertContractWithCrm")
  public CommonJsonObject insertContractWithCrm(HttpServletRequest request,
      @RequestBody @Valid SaveOrderCourseParam saveOrderCourseParam, BindingResult result)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

//    // crm是url传参，所以要把中文转译一下
//    saveOrderCourseParam.setUserName(URLDecoder.decode(saveOrderCourseParam.getUserName(),
//        "utf-8"));
//    saveOrderCourseParam.setOrderRemark(URLDecoder.decode(saveOrderCourseParam.getOrderRemark(),
//        "utf-8"));
    
    // 表单校验框架
    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    // 拟定crm线下合同
    try {
      json = orderCourseService.insertContractWithCrm(saveOrderCourseParam);
    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(e.getMessage());
    }
    

    /**
     * 这个接口的作用是告诉crm合同已经开始执行。 但是现在crm过来的合同我们会自动开始执行，再调用这个接口，会导致crm自己的逻辑发生冲突。
     * 所以暂时先不调用，如果以后有合同延时开通的逻辑，在延时那里再调用。
     */
    // if(ErrorCodeConstant.SUCCESS == json.getCode() &&
    // !StringUtils.isEmpty(json.getData())){
    // //回调CRM确认开通合同接口
    // String orderId = saveOrderCourseParam.getCrmContractId();
    // CrmUtil.updateOrderStatusToCrm(orderId);
    // }

    return json;
  }

  /**
   * 
   * Title: 续约详情<br>
   * Description: renewalOrderCourseDetailJsp<br>
   * CreateDate: 2016年4月8日 下午5:58:51<br>
   * 
   * @category renewalOrderCourseDetailJsp
   * @author athrun.cw
   * @param request
   * @param model
   * @param orderId
   * @return
   */
  @RequestMapping("/orderCourse/renewalOrderCourseDetailJsp/{orderId}")
  public String renewalOrderCourseDetailJsp(HttpServletRequest request, Model model,
      @PathVariable(value = "orderId") String orderId) {
    model.addAttribute("order_id", orderId);
    return "admin/ordercourse/renewal_ordercourse_detail";
  }

  /**
   * 
   * Title: 校验当前用户和合同是否合法<br>
   * Description: checkLegalContractAndUser<br>
   * CreateDate: 2015年9月6日 下午8:33:43<br>
   * 
   * @category 校验当前用户和合同是否合法
   * @author yangmh
   * @param paramMap
   * @param sessionUser
   * @return
   * @throws Exception
   */
  private Boolean checkLegalContractAndUser(Map<String, Object> paramMap, SessionUser sessionUser)
      throws Exception {
    Map<String, Object> orderCourse = orderCourseService.findOneByKeyId(paramMap.get("key_id"),
        "*");
    return orderCourse != null && sessionUser.getKeyId().equals(orderCourse.get("user_id"));
  }

  /**
   * Title: agree<br>
   * Description: 同意课程包合同<br>
   * CreateDate: 2015年8月24日 下午2:03:29<br>
   * 
   * @category 同意课程包合同
   * @author vector.mjp
   * @param m
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/ucenter/ordercourse/agree")
  public String agree(Model m, HttpServletRequest request) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    if (!checkLegalContractAndUser(paramMap, sessionUser)) {
      return "redirect:/ucenter/index";
    }

    Map<String, Object> saveMap = new HashMap<String, Object>();
    saveMap.put("key_id", paramMap.get("key_id"));

    logger.info("学员同意合同[user_id:" + sessionUser.getKeyId() + ", contract_id:"
        + paramMap.get("key_id") + "]");

    // modified by ivan.mgh,2016年5月24日10:23:37
    // order_status=已确认，表示用户同意签订购买合同
    adminOrderCourseService.updateOrderStatus(paramMap.get("key_id").toString(),
        OrderStatusConstant.ORDER_STATUS_HAVE_CONFIRMED);

    // 订单支付页面！
    return "redirect:/ucenter/ordercourse/detailsBeforePay/" + request.getParameter("key_id");
  }

  /**
   * Title: disagree<br>
   * Description: 不同意课程包合同<br>
   * CreateDate: 2015年8月24日 下午2:03:18<br>
   * 
   * @category 不同意课程包合同
   * @author vector.mjp
   * @param m
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/ucenter/ordercourse/disagree")
  public String disagree(Model m, HttpServletRequest request) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    if (!checkLegalContractAndUser(paramMap, sessionUser)) {
      return "redirect:/ucenter/index";
    }

    Map<String, Object> saveMap = new HashMap<String, Object>();
    saveMap.put("key_id", paramMap.get("key_id"));
    // order_status=已拟定，表示用户不同意签订购买合同
    saveMap.put("order_status", OrderStatusConstant.ORDER_STATUS_HAVE_PLANNED);

    logger.info("学员不同意合同[user_id:" + sessionUser.getKeyId() + ", contract_id:"
        + paramMap.get("key_id") + "]");
    orderCourseService.update(saveMap);

    return "redirect:/ucenter/index";
  }

  /**
   * 
   * Title: 查看当前用户的所有合同列表<br>
   * Description: userOrderList<br>
   * CreateDate: 2016年3月9日 上午11:20:46<br>
   * 
   * @category 查看当前用户的所有合同列表
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/ucenter/ordercourse/userOrderList", method = RequestMethod.POST)
  public Map<String, Object> userOrderList(HttpServletRequest request) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", sessionUser.getKeyId());
    paramMap.put("order_status", "notequal" + OrderStatusConstant.ORDER_STATUS_HAVE_PLANNED);

    /**
     * modify athrun.cw 2016年3月29日14:14:33
     * 
     * 新版续约合同，添加了新的字段，所以需要修改返回参数
     */
    List<Map<String, Object>> returnList = orderCourseService.findList(paramMap,
        "key_id,course_package_name,create_date,order_status,start_order_time,"
            + "end_order_time,total_show_price,total_real_price,total_final_price,order_original_type");

    /**
     * modify by athrun.cw 2016年3月9日11:14:36
     * 
     * 处理 合同集合returnList，添加合同状态
     */
    for (Map<String, Object> orderCourseMap : returnList) {
      orderCourseMap = OrderContractStatusUtil.transOrderAndContractStatus(orderCourseMap);
    }

    logger.info(returnList);
    Map<String, Object> returnMap = new HashMap<String, Object>();
    returnMap.put("order_list", returnList);
    return returnMap;
  }

  /**
   * Title: 兑换码一键生成合同<br>
   * Description: 兑换码一键生成合同<br>
   * CreateDate: 2016年7月20日 上午11:19:44<br>
   * 
   * @category 兑换码一键生成合同
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/ordercourse/saveOrderCourseByRedeemCode")
  public JsonMessage saveOrderCourseByRedeemCode(HttpServletRequest request, HttpSession session,
      @Valid RedeemOrderParam paramObj, BindingResult result)
          throws Exception {
    JsonMessage json = new JsonMessage(true, "兑换成功！");
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    logger.info("开始兑换码兑换合同！！！！！！！");

    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    String userPhone = paramObj.getUserPhone();
    String userName = paramObj.getUserName();
    String redeemCode = paramObj.getRedeemCode();

    try {
      // 兑换码兑换合同
      json = orderCourseService.saveOrderCourseAndOptionByRedeemCode(
          redeemCode, userPhone, userName,
          OrderCourseConstant.USER_FROM_TYPE_NORMAL, sessionUser.getKeyId());
      
      if(json.isSuccess()){
        // 初始化学员属性
        userService.initSessionUser(sessionUser.getKeyId(), null);

        // 成功后给学员发送提醒短信
        SmsUtil.sendSmsToQueue(userPhone, "学员" + userName + ","
            + SmsConstant.SAVE_ORDERCOURSE_BY_REDEEM_CODE_SUCCESS);

      }
     
      logger.info("兑换码兑换合同完毕！！！！！！！orderId：" + json);
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg(e.getMessage());
      e.printStackTrace();
      logger.error("系统出错! error:" + e.toString(), e);
    }
    return json;
  }

  /**
   * Title: 查看合同详情.<br>
   * Description: detail<br>
   * CreateDate: 2017年2月17日 下午3:23:45<br>
   * 
   * @category 查看合同详情
   * @author yangmh
   * @param keyId
   *          合同id
   */
  @ResponseBody
  @RequestMapping(method = RequestMethod.GET, value = "/api/speakhi/v1/orderCourse/{keyId}")
  public CommonJsonObject findOrderCourseDetail(HttpServletRequest request,
      @PathVariable(value = "keyId") String keyId)
          throws Exception {

    CommonJsonObject json = new CommonJsonObject();
    OrderCourseDetailParam orderCourseDetailParam = orderCourseService.findOrderCourseDetail(keyId);

    if (orderCourseDetailParam == null) {
      json.setCode(ErrorCodeEnum.ORDER_NOT_EXIST.getCode());
    } else {
      json.setData(orderCourseDetailParam);
    }

    return json;
  }
}
