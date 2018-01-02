package com.webi.hwj.ordercourse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.service.OrderCourseSplitService;
import com.webi.hwj.util.CrmUtil;
import com.webi.hwj.util.SessionUtil;

/**
 * 
 * Title: 订单拆分类接口：（1.拆分订单预加载接口）<br>
 * Description: AdminOrderCourseSplitController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年1月12日 上午10:45:08
 * 
 * @author athrun.cw
 */
@Controller
@RequestMapping("/")
public class AdminOrderCourseSplitController {
  private static Logger logger = Logger.getLogger(AdminOrderCourseSplitController.class);

  @Resource
  private OrderCourseSplitService orderCourseSplitService;

  /**
   * @category 后台跳转到拆分合同jsp页面
   * @author alex.yang
   * @param model
   * @return
   * @throws Exception
   */
  @RequestMapping("/orderCourseSplit/preOrderCourseSplitJsp")
  public ModelAndView preOrderCourseSplitJsp(HttpServletRequest request) throws Exception {
    Map<String, Object> ccMap = CrmUtil.findBadminUser(request);
    return new ModelAndView("admin/ordercourse/admin_order_course_split", "cc", ccMap);
  }

  /**
   * 
   * Title: 管理员确认一笔线下支付为支付成功状态<br>
   * Description: confirmSuccessPay<br>
   * CreateDate: 2016年3月8日 下午4:53:27<br>
   * 
   * @category 管理员确认一笔线下支付为支付成功状态
   * @author athrun.cw
   * @param request
   *          split_order_id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/admin/orderCourseSplit/confirmSuccessPay", method = RequestMethod.POST)
  public Map<String, Object> confirmSuccessPay(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> orderCourseSplitMap = new HashMap<String, Object>();
    // if(paramMap == null || paramMap.get("split_order_id") == null ||
    // "".equals(paramMap.get("split_order_id"))){
    // ordeCourseSplitMap.put("code", "500");
    // orderCourseSplitMap.put("msg", "确认支付失败：参数为空！");
    // logger.error("确认支付成功接口错误：参数为空！");
    // return orderCourseSplitMap;
    // }
    /**
     * modify by athrun.cw 2016年3月12日14:56:37
     * 
     * 财务可以修改订单。因此所确认的订单 可以是1.原有或者修改的；2.新增的订单
     */
    if (paramMap == null || paramMap.get("order_id") == null
        || "".equals(paramMap.get("order_id"))) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "确认支付失败：参数为空！");
      logger.error("确认支付成功接口错误：参数为空！");
      return orderCourseSplitMap;
    }

    // 更新update_user_id字段
    paramMap.put("admin_user_id", sessionAdminUser.getKeyId());
    // 支付来源
    paramMap.put("pay_from", "财务确认");
    // 参数不为空
    try {
      if (paramMap.get("split_order_id") == null || "".equals(paramMap.get("split_order_id"))) {
        logger.info("开始调用确认支付成功接口：确认支付的合同order_id：" + paramMap.get("order_id")
            + "，该订单split_order_id为空，是财务新拟定的订单...");
      } else {
        logger.info("开始调用确认支付成功接口：确认支付的合同order_id：" + paramMap.get("order_id")
            + "，该订单split_order_id：" + paramMap.get("split_order_id") + "...");
      }

      paramMap.put("split_status", OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE);
      orderCourseSplitMap = orderCourseSplitService.confirmSuccessPay(paramMap);
    } catch (Exception e) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "确认支付成功接口错误：error=" + e.getMessage());
      logger.error("确认支付成功接口错误:拆分订单split_order_id：" + paramMap.get("split_order_id") + "错误，系统异常~"
          + e.getMessage());
    }

    return orderCourseSplitMap;
  }

  /**
   * Title: 管理员确认一笔线下支付为支付成功状态（CRM专用）<br>
   * Description: crmConfirmSuccessPay<br>
   * CreateDate: 2016年4月28日 下午1:50:29<br>
   * 
   * @category 管理员确认一笔线下支付为支付成功状态（CRM专用）
   * @author ivan.mgh
   * @param request
   * @param paramMap
   * @see AdminOrderCourseSplitController#confirmSuccessPay(HttpServletRequest,
   *      Map)
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/admin/orderCourseSplit/confirmSuccessPay/crm",
      method = RequestMethod.POST)
  public Map<String, Object> crmConfirmSuccessPay(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    // SessionAdminUser sessionAdminUser =
    // SessionUtil.getSessionAdminUser(request);

    SessionAdminUser sessionAdminUser = CrmUtil.findAdminUserFromCache(request);

    Map<String, Object> orderCourseSplitMap = new HashMap<String, Object>();
    // if(paramMap == null || paramMap.get("split_order_id") == null ||
    // "".equals(paramMap.get("split_order_id"))){
    // ordeCourseSplitMap.put("code", "500");
    // orderCourseSplitMap.put("msg", "确认支付失败：参数为空！");
    // logger.error("确认支付成功接口错误：参数为空！");
    // return orderCourseSplitMap;
    // }
    /**
     * modify by athrun.cw 2016年3月12日14:56:37
     * 
     * 财务可以修改订单。因此所确认的订单 可以是1.原有或者修改的；2.新增的订单
     */
    if (paramMap == null || paramMap.get("order_id") == null
        || "".equals(paramMap.get("order_id"))) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "确认支付失败：参数为空！");
      logger.error("确认支付成功接口错误：参数为空！");
      return orderCourseSplitMap;
    }

    // 更新update_user_id字段
    paramMap.put("admin_user_id", sessionAdminUser.getKeyId());
    // 支付来源
    paramMap.put("pay_from", "财务确认");
    // 参数不为空
    try {
      if (paramMap.get("split_order_id") == null || "".equals(paramMap.get("split_order_id"))) {
        logger.info("开始调用确认支付成功接口：确认支付的合同order_id：" + paramMap.get("order_id")
            + "，该订单split_order_id为空，是财务新拟定的订单...");
      } else {
        logger.info("开始调用确认支付成功接口：确认支付的合同order_id：" + paramMap.get("order_id")
            + "，该订单split_order_id：" + paramMap.get("split_order_id") + "...");
      }
      paramMap.put("split_status", OrderStatusConstant.ORDER_SPLIT_STATUS_HAVE_NOT_PAID_OFFLINE);
      orderCourseSplitMap = orderCourseSplitService.confirmSuccessPay(paramMap);
    } catch (Exception e) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "确认支付成功接口错误：error=" + e.getMessage());
      logger.error("确认支付成功接口错误:拆分订单split_order_id：" + paramMap.get("split_order_id") + "错误，系统异常~"
          + e.getMessage());
    }

    return orderCourseSplitMap;
  }

  /**
   * 
   * Title: 拆分订单预加载接口:销售拟定好的合同，有可能被拆分成多个订单。<br>
   * Description: preLoadOrderCourseSplit<br>
   * CreateDate: 2016年1月12日 上午10:52:17<br>
   * 
   * @category 拆分订单预加载接口:销售拟定好的合同，有可能被拆分成多个订单。
   * @author athrun.cw
   * @param request
   *          : order_id
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/orderCourseSplit/preLoadOrderCourseSplit", method = RequestMethod.POST)
  public Map<String, Object> preLoadOrderCourseSplit(@RequestBody Map<String, Object> paramMap)
      throws Exception {
    Map<String, Object> orderCourseSplitMap = new HashMap<String, Object>();
    // 页面传送的是订单order_id
    if (paramMap == null || paramMap.get("order_id") == null
        || "".equals(paramMap.get("order_id"))) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "订单order_id无效或者为空！");
      logger.error("拆分订单预加载接口错误：参数订单order_id无效或者为空！");
      throw new RuntimeException("开始调用拆分订单预加载接口：参数订单order_id无效或者为空！");
    }

    try {
      logger.info("开始调用拆分订单预加载接口：订单id：" + paramMap.get("order_id") + "...");
      orderCourseSplitMap = orderCourseSplitService.preLoadOrderCourseSplitByOrderId(paramMap);
      // orderCourseSplitMap.put("code", "200");
      // orderCourseSplitMap.put("msg", "拆分订单预加载接口成功~");
    } catch (Exception e) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "系统异常！" + e.getMessage());
      logger.error("预加载订单id：" + paramMap.get("order_id") + "错误，系统异常~" + e.getMessage());
    }
    return orderCourseSplitMap;
  }

  /**
   * 
   * Title: 订单拆分提交:将拆分好的订单提交至服务器<br>
   * Description: saveOrderCourseSplit<br>
   * CreateDate: 2016年1月12日 下午3:05:47<br>
   * 
   * @category 订单拆分提交:将拆分好的订单提交至服务器
   * @author athrun.cw
   * @param request
   * @param model
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/admin/orderCourseSplit/saveOrderCourseSplit",
      method = RequestMethod.POST)
  public Map<String, Object> saveOrderCourseSplit(@RequestBody Map<String, Object> paramMap)
      throws Exception {
    Map<String, Object> orderCourseSplitMap = new HashMap<String, Object>();
    if (paramMap == null || paramMap.get("order_id") == null
        || "".equals(paramMap.get("order_id"))) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "拆分合同失败：参数为空！");
      logger.error("订单拆分提交接口错误：参数为空！");
      return orderCourseSplitMap;
    }
    // 参数不为空
    try {
      logger.info("开始调用订单拆分提交接口：订单id：" + paramMap.get("order_id") + "...");
      orderCourseSplitMap = orderCourseSplitService.saveOrderCourseSplit(paramMap);
    } catch (Exception e) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "订单拆分提交接口错误：error=" + e.getMessage());
      logger.error("订单拆分提交接口错误:订单id：" + paramMap.get("order_id") + "错误，系统异常~" + e.getMessage());
    }

    return orderCourseSplitMap;
  }

  /**
   * Title: 订单拆分提交:将拆分好的订单提交至服务器（CRM专用）<br>
   * Description: crmSaveOrderCourseSplit<br>
   * CreateDate: 2016年4月28日 下午1:48:06<br>
   * 
   * @category 订单拆分提交:将拆分好的订单提交至服务器 （CRM专用）
   * @author ivan.mgh
   * @param request
   * @param paramMap
   * @see AdminOrderCourseSplitController#saveOrderCourseSplit(Map)
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/admin/orderCourseSplit/saveOrderCourseSplit/crm",
      method = RequestMethod.POST)
  public Map<String, Object> crmSaveOrderCourseSplit(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    Map<String, Object> orderCourseSplitMap = new HashMap<String, Object>();
    if (paramMap == null || paramMap.get("order_id") == null
        || "".equals(paramMap.get("order_id"))) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "拆分合同失败：参数为空！");
      logger.error("订单拆分提交接口错误：参数为空！");
      return orderCourseSplitMap;
    }
    // 参数不为空
    try {
      logger.info("开始调用订单拆分提交接口：订单id：" + paramMap.get("order_id") + "...");

      // 获得凭证
      String authParam = request.getParameter("auth");
      if (StringUtils.isNotBlank(authParam)) {
        // Base64解码
        String leadId = new String(Base64.decodeBase64(authParam), "UTF-8");
        orderCourseSplitMap = orderCourseSplitService.crmSaveOrderCourseSplit(paramMap, leadId);
      } else {
        orderCourseSplitMap.put("code", "500");
        orderCourseSplitMap.put("msg", "找不到学员全局GUID！");
      }
    } catch (Exception e) {
      orderCourseSplitMap.put("code", "500");
      orderCourseSplitMap.put("msg", "订单拆分提交接口错误：error=" + e.getMessage());
      logger.error("订单拆分提交接口错误:订单id：" + paramMap.get("order_id") + "错误，系统异常~" + e.getMessage());
    }

    return orderCourseSplitMap;
  }

}
