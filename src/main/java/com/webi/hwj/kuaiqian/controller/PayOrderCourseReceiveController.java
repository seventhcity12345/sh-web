/** 
 * File: PayOrderCourseController.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月14日 下午6:38:23
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.kuaiqian.bean.WebiHwjParameterConstant;
import com.webi.hwj.kuaiqian.service.PayOrderCourseLogService;
import com.webi.hwj.kuaiqian.service.PayOrderCourseService;
import com.webi.hwj.kuaiqian.util.PkiEncryptSecurityUtil;
import com.webi.hwj.kuaiqian.util.SignMessageUtil;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.ordercourse.service.OrderCourseSplitService;
import com.webi.hwj.user.service.UserService;

/**
 * 
 * Title: PayOrderCourseReceiveController<br>
 * Description: 系统接收快钱的respnose<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年11月13日 下午5:12:04
 * 
 * @author athrun.cw
 */
@Controller
@RequestMapping("/")
public class PayOrderCourseReceiveController {
  private static Logger logger = Logger.getLogger(PayOrderCourseReceiveController.class);

  @Resource
  private UserService userService;

  @Resource
  private PayOrderCourseLogService payOrderCourseLogService;

  @Resource
  private PayOrderCourseService payOrderCourseService;

  @Resource
  OrderCourseService orderCourseService;

  @Resource
  private OrderCourseSplitService orderCourseSplitService;

  /**
   * 
   * Title: 处理款前返回的信息，并对相关数据 进行更新处理： 1.把t_user表中的is_student字段给弄成1
   * 2.把当前session里的用户对象的student设置成1 3.把t_order_course里的order_status设置成已支付
   * Description: receiveOrderCourseResult<br>
   * CreateDate: 2015年8月17日 下午3:45:21<br>
   * 
   * @category receiveOrderCourseResult
   * @author athrun.cw
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @RequestMapping("/kuaiqian/receiveOrderCourseResult")
  public void receiveOrderCourseResult(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    logger.info("将要接受快钱返回的支付结果信息...");
    // 支付钱数

    /**
     * 天杀的快钱，支付单位是分
     */
    String orderAmount = Integer
        .toString(Integer.parseInt(request.getParameter("orderAmount")) / 100);
    logger.debug("快钱返回的支付金额orderAmount是------------------------>:" + orderAmount);

    String kuaiqianPayLogerId = request.getParameter("orderId");

    // 签名字符串
    String signMsg = request.getParameter("signMsg");

    // 按照快钱加密字符串连接规则 解析，获取快钱返回给商户的交易明文字符串
    String merchantSignMsgVal = SignMessageUtil.receiveSignMessageValue(request);

    // 通知快钱接收到支付结果 1：商户已经收到通知 0：商户未接到通知
    int returnOK = WebiHwjParameterConstant.HAVE_FAILED_RECEIVE;
    String returnURL = "";
    // receive 页面接收到快钱支付结果后， 会自动将参数传递给 show 页面并跳转到 show 页面
    Map<String, Object> paramMap = new HashMap<String, Object>();
    // 通过签名验证
    logger.info("开始验证快钱私钥...");
    if (PkiEncryptSecurityUtil.enCodeByCer(merchantSignMsgVal, signMsg)) {
      logger.info("快钱私钥验证通过，开始根据返回的信息处理speakhi业务逻辑...");
      switch (Integer.parseInt(request.getParameter("payResult"))) {
        // 支付成功
        case 10:
          // 查询该日志信息
          logger.info("通过快钱支付成功，开始后台处理自己成功的业务逻辑...");
          paramMap.put("key_id", request.getParameter("orderId"));
          Map<String, Object> payOrderCourseLog = payOrderCourseLogService
              .findOneByKeyId(kuaiqianPayLogerId, "key_id,user_id,order_id");
          // 如果没有找到后台合同日志数据，error
          if (payOrderCourseLog == null) {
            throw new RuntimeException("合同数据出现错误，没有找到相应的后台合同数据！");
          }
          logger.info("原初始化的日志为：payOrderCourseLog ------------------->" + payOrderCourseLog);
          // 初始化处理参数
          // 1.把t_user表中的is_student字段给弄成1
          // paramMap.put("is_student", 1);
          paramMap.put("user_id", payOrderCourseLog.get("user_id"));
          // 1.把t_user表中的is_student字段给弄成1

          // 2.把t_order_course里的order_status设置成已支付
          // paramMap.put("order_id", payOrderCourseLog.get("order_id"));
          // paramMap.put("order_status",
          // OrderStatusConstant.ORDER_STATUS_HAVE_PAID);
          // 2.把t_order_course里的order_status设置成已支付

          // 3.更新日志记录表，将trade_status 更新为返回的 payResult
          paramMap.put("trade_status", request.getParameter("payResult"));
          // 快钱交易号 快钱交易号，商户每一笔交易都会在快钱生成一个交易号。
          paramMap.put("deal_id", request.getParameter("dealId"));
          // 3.更新日志记录表，将trade_status 更新为返回的 payResult

          // trade_status,deal_id,key_id

          logger.info("开始处理用户id为 [" + paramMap.get("user_id") + " ]支付成功后的相关后台数据处理...");
          orderCourseService.afterPaySuccessLogic(payOrderCourseLog.get("order_id").toString(),
              orderAmount, "快钱", paramMap);
          logger.info("开始处理用户id为 [" + paramMap.get("user_id") + " ]支付成功后的相关后台数据处理成功！");

          /*
           * // 4.把当前session里的用户对象的student设置成1 (true)
           * logger.info("重新设置当前sessionUser绑定对象");
           * 
           * Map<String, Object> userObj =
           * userService.findOneByKeyId(payOrderCourseLog.get("user_id"),"*");
           * // 清空原有的session绑定对象
           * request.getSession().setAttribute(SessionUtil.SESSION_USER_NAME,
           * null); // 重置userSession，重新绑定登录用户对象
           * SessionUtil.initSessionUser(userObj, request.getSession());
           * logger.info("重新设置用户id为 [" + paramMap.get("user_id") +
           * " ]的sessionUser绑定对象成功！");
           */

          // 返回给快钱的标识 ：1 ：已经收到
          returnOK = WebiHwjParameterConstant.HAVE_SUCCESS_RECEIVE;
          logger.info("成功支付后，返回给快钱的标识 returnOK = " + returnOK);
          // 支付成功后页面显示URL 前端自定义
          returnURL = WebiHwjParameterConstant.RETURN_SHOW_URL_SUCCESS
              + "&order_id=" + payOrderCourseLog.get("order_id");
          logger.info("成功支付后，将要跳转页面的 returnURL = " + returnURL);
          break;
        default:
          // 交易失败后，log日志处理
          logger.info("通过快钱支付失败，开始后台处理自己失败的业务逻辑...");
          paramMap.put("key_id", request.getParameter("orderId"));
          paramMap.put("deal_id", request.getParameter("dealId"));
          logger.info("开始处理订单orderId为 [" + paramMap.get("key_id") + " ]支付失败后的日志记录...");
          payOrderCourseLogService.updateKuaiqianLogByKeyId(paramMap);
          logger.info("开始处理订单orderId为 [" + paramMap.get("key_id") + " ]支付失败后的日志记录处理成功！");
          // 返回给快钱的标识 ：0 ：没有收到
          returnOK = WebiHwjParameterConstant.HAVE_FAILED_RECEIVE;
          logger.info("成功失败后，返回给快钱的标识 returnOK = " + returnOK);
          // 支付失败后页面显示URL 前端自定义
          returnURL = WebiHwjParameterConstant.RETURN_SHOW_URL_FALSE;
          logger.info("成功失败后，将要跳转页面的 returnURL = " + returnURL);
          break;
      }
    } else {// 没有通过验证
      // 返回给快钱的标识 ：0 ：没有收到
      logger.info("快钱私钥验证不通过，直接提示给快钱 & 本地跳转...");
      returnOK = WebiHwjParameterConstant.HAVE_FAILED_RECEIVE;
      logger.info("无法通过验证，重新返回给快钱的标识 returnOK = " + returnOK);
      // 错误页面显示URL 前端自定义
      returnURL = WebiHwjParameterConstant.RETURN_SHOW_URL_ERROR;
      logger.info("无法通过验证，将要跳转页面的 returnOK = " + returnOK);
    }
    // model.addAttribute("returnOK", returnOK);
    // model.addAttribute("returnURL", returnURL);

    response.getWriter()
        .write("<result>" + returnOK + "</result> <redirecturl>" + returnURL + "</redirecturl>");

    logger.info("完成对快钱返回的支付结果的业务处理，将进入---> 回执快钱是否重新发送 & 提示用户支付结果界面... ");
    /**
     * <result><%=rtnOK%></result> <redirecturl><%=rtnUrl%></redirecturl>
     * 
     * 不知道 该标签 怎么用代码写
     */
    // return "/kuaiqian/pay/ordercourse/ordercourse_receive";
  }

  /**
   * 
   * Title: 显示支付处理结果 Description: showResult<br>
   * CreateDate: 2015年8月17日 下午4:22:17<br>
   * 
   * @category showResult
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/kuaiqian/showResult")
  public String showResult(HttpServletRequest request, Model model) throws Exception {
    logger.info("通过正常支付，开始提示用户支付结果...");

    // 通过参数msg 判断是否已经支付成功！
    if ("success".equals(request.getParameter("msg"))) {
      logger.info("通过正常支付，支付成功，将进入result结果引导页...");

      String splitOrderId = request.getParameter("order_id");
      model.addAttribute("order_id", splitOrderId);
      /**
       * 根据支付状态，决定跳转
       */
      // 1.查询split相关信息
      Map<String, Object> orderCourseSplitMap = orderCourseSplitService.findOneByKeyId(splitOrderId,
          "*");
      if (orderCourseSplitMap == null) {
        logger.error("拆分订单split_id" + splitOrderId + "不存在，无法支付~");
        return "common/500";
      }
      logger.debug("本次支付的split_id拆分订单详情：orderCourseSplitMap = " + orderCourseSplitMap);

      // 2.找到合同
      Map<String, Object> orderCourseMap = orderCourseService
          .findOneByKeyId(orderCourseSplitMap.get("order_id"), "key_id, order_status,user_id");
      if (orderCourseMap == null) {
        logger.error("拆分订单所对应的父订单不存在，无法支付~");
        return "common/500";
      }

      String order_status = orderCourseMap.get("order_status").toString();
      logger.debug(
          "本次支付的订单详情：orderCourseMap = " + orderCourseMap + "，状态order_status为：" + order_status);

      // 3.支付中
      if (OrderStatusConstant.ORDER_STATUS_PAYING.equals(order_status)) {
        logger.info("本次支付后，整个订单状态为支付中，将进入支付页面~~~");
        return "redirect:/web/views/member/#/payCenter/" + orderCourseMap.get("key_id");
      }

      // 4.支付完成
      if (OrderStatusConstant.ORDER_STATUS_HAVE_PAID.equals(order_status)) {
        logger.info("本次支付后，整个订单状态为已支付，将进入支付完成页面，初始化temmmemore 和发送通知短信！！！");

        // 把当前session里的用户对象的student设置成1 (true)
        logger.debug("重新设置当前用户id" + orderCourseMap.get("user_id") + "的sessionUser绑定对象");

        // 重置userSession，重新绑定登录用户对象
        userService.initSessionUser(orderCourseMap.get("user_id").toString(), null);
        logger.info(
            "重新设置用户id为 [" + orderCourseMap.get("user_id").toString() + " ]的sessionUser绑定对象成功！");

        // order_id需要传递到页面中
        // model.addAttribute("order_id", orderId);
//        return "kuaiqian/pay/ordercourse/ordercourse_result";
      }
      return "redirect:/ucenter/index";
    } else {
      logger.info("通过正常支付，支付失败，将重定向到支付预览界面...");
      return "redirect:/ucenter/index";
    }
  }
}