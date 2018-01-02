/** 
 * File: PayOrderCourseController.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月14日 下午6:38:23
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.kuaiqian.service.PayOrderCourseLogService;
import com.webi.hwj.kuaiqian.service.PayOrderCourseService;
import com.webi.hwj.kuaiqian.util.PkiEncryptSecurityUtil;
import com.webi.hwj.kuaiqian.util.SignMessageUtil;
import com.webi.hwj.kuaiqian.util.SubmitPostUtil;
import com.webi.hwj.ordercourse.service.OrderCourseOptionService;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.ordercourse.service.OrderCourseSplitService;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.SessionUtil;

/**
 * Title: PayOrderCourseController<br>
 * 使用快钱的支付接口，进行订单支付 Description: PayOrderCourseController<br>
 * 使用快钱的支付接口，进行订单支付 Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月14日 下午6:38:23
 * 
 * @author athrun.cw
 */
@Controller
@RequestMapping("/ucenter/kuaiqian/pay")
public class PayOrderCourseController {
  private static Logger logger = Logger.getLogger(PayOrderCourseController.class);

  @Resource
  private OrderCourseService orderCourseService;

  @Resource
  private OrderCourseSplitService orderCourseSplitService;

  @Resource
  private OrderCourseOptionService orderCourseOptionService;

  @Resource
  private UserService userService;

  @Resource
  private PayOrderCourseLogService payOrderCourseLogService;

  @Resource
  private PayOrderCourseService payOrderCourseService;

  /**
   * 
   * Title: 快钱提交<br>
   * Description: submitKuaiqianForm<br>
   * CreateDate: 2016年1月25日 下午3:26:02<br>
   * 
   * @category 快钱提交
   * @author athrun.cw
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping("/submitKuaiqianForm")
  public void submitKuaiqianForm(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    // 获取需支付的split_id
    String splitOrderId = request.getParameter("split_order_id_kuaiqian");
    logger.info("用户 [" + sessionUser.getPhone() + "] 进入快钱提交/submitKuaiqianForm" + "，split订单id ["
        + splitOrderId + "]，将加载快钱请求参数...");
    // 1.查询订单相关信息
    Map<String, Object> orderCourseSplitMap = orderCourseSplitService.findOneByKeyId(splitOrderId,
        "*");
    if (orderCourseSplitMap == null) {
      logger.error("用户 [" + sessionUser.getPhone() + "]拆分订单不存在，无法支付~");
      throw new Exception("用户 [" + sessionUser.getPhone() + "]拆分订单不存在，无法支付~");
    }

    // 开始添加支付 交易记录日志
    Map<String, Object> logParamMap = new HashMap<String, Object>();

    logParamMap.put("trade_status", orderCourseSplitMap.get("split_status"));
    logParamMap.put("order_id", splitOrderId);
    logParamMap.put("user_id", sessionUser.getKeyId());
    logParamMap.put("money", orderCourseSplitMap.get("split_price"));
    // 再点击支付时候，就开始初始化该交易记录
    payOrderCourseLogService.insertKuaiqianLogByParamMap(logParamMap);
    logger.info("用户 [" + sessionUser.getPhone() + "] 的支付日志初始化数据为：" + logParamMap);

    // 将刚才初始化的 建议日志信息，赋值给即将传递给快钱的 log_key_id 参数：orderId
    orderCourseSplitMap.put("log_key_id", logParamMap.get("key_id"));

    /**
     * map.put("signMsgVal", signMsgVal);
     */
    logger.info("用户 [" + sessionUser.getPhone() + "] 使用快钱支付，开始签证...");

    Map<String, Object> map = SignMessageUtil.sendSignMessageValue(sessionUser,
        orderCourseSplitMap);
    logger.info("用户 [" + sessionUser.getPhone() + "] 签证用字符串连接完成...");
    // 订单信息明文
    String signMsgVal = map.get("signMsgVal").toString();

    logger.info("用户 [" + sessionUser.getPhone() + "] 的签证明文为：" + signMsgVal);
    // 订单信息密文
    String signSecurityMsgVal = PkiEncryptSecurityUtil.signSecurityMessage(signMsgVal);
    logger.info("用户 [" + sessionUser.getPhone() + "] 的签证密文为：" + signSecurityMsgVal);

    logger.info("用户 [" + sessionUser.getPhone() + "] 快钱签证完成...");

    Map<String, String> splitOrderMap = (Map<String, String>) map.get("splitOrderMap");
    splitOrderMap.put("signMsg", signSecurityMsgVal);

    // 建立请求
    String sHtmlText = SubmitPostUtil.buildPostRequestForm(splitOrderMap, "post", "submit");

    PrintWriter out = null;
    try {
      // 转码
      response.setContentType("text/html;charset=UTF-8");
      out = response.getWriter();
      logger.debug("快钱返回------>" + sHtmlText);
      out.write(sHtmlText);
      // out.write(HttpUtil.doPost(KuaiqianParameterConstant.KUAIQIAN_PAY_URL_PRO,
      // splitOrderMap));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        out.close();
      }
    }
    /*
     * return "redirect:" + KuaiqianParameterUtil.KUAIQIAN_PAY_URL_PRO + "?" +
     * signMsgVal + "&signMsgVal=" + signSecurityMsgVal;
     */

  }

}