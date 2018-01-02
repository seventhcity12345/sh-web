/** 
 * File: AlipayController.java<br> 
 * Project: javabase<br> 
 * Package: com.alipay.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年12月2日 下午2:46:51
 * @author yangmh
 */
package com.webi.hwj.alipay.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.ordercourse.service.OrderCourseSplitService;
import com.webi.hwj.util.SessionUtil;

/**
 * Title: AlipayController<br>
 * Description: AlipayController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月2日 下午2:46:51
 * 
 * @author yangmh
 */
@Controller
@RequestMapping("/")
public class AlipayController {
  private static Logger logger = Logger.getLogger(AlipayController.class);

  @Resource
  private OrderCourseService orderCourseService;

  @Resource
  private OrderCourseSplitService orderCourseSplitService;

  /**
   * @category 支付宝订单状态监听(异步)
   * @author yangmh
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @RequestMapping("/alipay/notifyUrl")
  public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    logger.info("支付宝回执监听------>开始");
    // 1.阿里自己的代码

    // 获取支付宝POST过来反馈信息
    Map<String, String> params = new HashMap<String, String>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
      String name = (String) iter.next();
      String[] values = (String[]) requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? valueStr + values[i]
            : valueStr + values[i] + ",";
      }
      // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
      // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
      params.put(name, valueStr);
    }

    // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
    // 商户订单号
    String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),
        "UTF-8");

    // 支付宝交易号
    String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

    // 交易状态
    String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),
        "UTF-8");

    // 交易金额
    String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),
        "UTF-8");

    // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

    logger.info("支付宝回执监听------>商户订单号(我们)=" + out_trade_no);
    logger.info("支付宝回执监听------>支付宝交易号=" + trade_no);
    logger.info("支付宝回执监听------>交易状态" + trade_status);
    logger.info("支付宝回执监听------>交易金额" + total_fee);

    // 日志描述

    if (AlipayNotify.verify(params)) {// 验证成功
      logger.info("支付宝回执监听------>验证成功");
      //////////////////////////////////////////////////////////////////////////////////////////
      // 请在这里加上商户的业务逻辑程序代码

      // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

      if (trade_status.equals("TRADE_FINISHED")) {
        // 判断该笔订单是否在商户网站中已经做过处理
        // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
        // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
        // 如果有做过处理，不执行商户的业务程序

        // 注意：
        // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
        logger.info("退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知");
        logger.info("speakhi不作处理!");
      } else if (trade_status.equals("TRADE_SUCCESS")) {
        // 判断该笔订单是否在商户网站中已经做过处理
        // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
        // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
        // 如果有做过处理，不执行商户的业务程序
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("trade_status", trade_status);
        paramMap.put("trade_no", trade_no);

        String[] money = total_fee.split("\\.");
        orderCourseService.afterPaySuccessLogic(out_trade_no, money[0], "支付宝", paramMap);

        // 注意：
        // 付款完成后，支付宝系统发送该交易状态通知
      }

      // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

      response.getWriter().write("success");
      // out.println("success"); //请不要修改或删除

      //////////////////////////////////////////////////////////////////////////////////////////
    } else {// 验证失败
      logger.error("支付宝回执监听------>验证失败");
      response.getWriter().write("fail");
      // out.println("fail");
    }
    // 将合同的状态设置
    // 判断该笔订单是否在商户网站中已经做过处理
    logger.info("支付宝回执监听------>结束");
  }

  @RequestMapping("/alipay/returnUrl")
  public String returnUrl(HttpServletRequest request) throws Exception {
    logger.info("支付宝跳转------>开始");

    // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
    // 商户订单号

    String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),
        "UTF-8");

    // 支付宝交易号

    String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

    // 交易状态
    String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),
        "UTF-8");

    // 交易金额
    String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),
        "UTF-8");

    logger.info("支付宝跳转------>商户订单号(我们)=" + out_trade_no);
    logger.info("支付宝跳转------>支付宝交易号=" + trade_no);
    logger.info("支付宝跳转------>交易状态" + trade_status);
    logger.info("支付宝跳转------>交易金额" + total_fee);

    String returnUrl = "forward:/kuaiqian/showResult?msg=success&order_id=" + out_trade_no;

    // 获取支付宝GET过来反馈信息
    Map<String, String> params = new HashMap<String, String>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
      String name = (String) iter.next();
      String[] values = (String[]) requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? valueStr + values[i]
            : valueStr + values[i] + ",";
      }
      // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
      valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
      params.put(name, valueStr);
    }

    // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

    // 计算得出通知验证结果
    boolean verify_result = AlipayNotify.verify(params);

    if (verify_result) {// 验证成功
      logger.info("支付宝跳转------>验证成功");
      // 请在这里加上商户的业务逻辑程序代码

      // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
      if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
        // 判断该笔订单是否在商户网站中已经做过处理
        // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
        // 如果有做过处理，不执行商户的业务程序

        String[] money = total_fee.split("\\.");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        orderCourseService.afterPaySuccessLogic(out_trade_no, money[0], "支付宝1", paramMap);
      }
    } else {
      logger.error("支付宝跳转------>验证失败");
      returnUrl = "redirect:/ucenter/index";
    }

    logger.info("支付宝跳转------>结束");
    return returnUrl;
  }

  /**
   * Title: 支付宝订单提交<br>
   * Description: submitAlipayForm<br>
   * CreateDate: 2015年12月10日 上午11:45:12<br>
   * 
   * @category 支付宝订单提交
   * @author yangmh
   */
  @RequestMapping("/ucenter/alipay/submitAlipayForm")
  public void submitAlipayForm(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String splitOrderId = request.getParameter("split_order_id");
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    logger.info("支付宝订单提交------>开始,phone=" + sessionUser.getPhone() + ",split订单id=" + splitOrderId);
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter pw = response.getWriter();

    // 订单ID

    // 1.查询订单相关信息
    Map<String, Object> orderCourseSplit = orderCourseSplitService.findOneByKeyId(splitOrderId,
        "key_id,split_price");
    if (orderCourseSplit == null) {
      logger.error("合同号不存在!phone=" + sessionUser.getPhone());
      pw.write("<script>alert('合同号不存在!');history.back();</script>");
      return;
    }

    // 2.为支付宝订单参数赋值

    //////////////////////////////////// 请求参数//////////////////////////////////////

    // 支付类型
    String payment_type = "1";
    // 必填，不能修改
    // 服务器异步通知页面路径
    String notify_url = MemcachedUtil.getConfigValue("alipay_notify_url");
    // 需http://格式的完整路径，不能加?id=123这类自定义参数

    // 页面跳转同步通知页面路径
    String return_url = MemcachedUtil.getConfigValue("alipay_return_url");
    // 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

    // 商户订单号
    String out_trade_no = orderCourseSplit.get("key_id").toString();
    // 商户网站订单系统中唯一订单号，必填

    // 订单名称
    String subject = "speakhi";
    // 必填

    // 付款金额
    // String total_fee = "0.01";
    String total_fee = orderCourseSplit.get("split_price").toString();
    // 必填

    // 订单描述

    String body = "speakhi.com";
    // 商品展示地址
    String show_url = "http://speakhi.com";
    // 需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

    // 防钓鱼时间戳
    String anti_phishing_key = "";
    // 若要使用请调用类文件submit中的query_timestamp函数

    // 客户端的IP地址
    String exter_invoke_ip = "";
    // 非局域网的外网IP地址，如：221.0.0.1

    // 把请求参数打包成数组
    Map<String, String> sParaTemp = new HashMap<String, String>();
    sParaTemp.put("service", "create_direct_pay_by_user");
    sParaTemp.put("partner", AlipayConfig.partner);
    sParaTemp.put("seller_email", AlipayConfig.seller_email);
    sParaTemp.put("_input_charset", AlipayConfig.input_charset);
    sParaTemp.put("payment_type", payment_type);
    sParaTemp.put("notify_url", notify_url);
    sParaTemp.put("return_url", return_url);
    sParaTemp.put("out_trade_no", out_trade_no);
    sParaTemp.put("subject", subject);
    sParaTemp.put("total_fee", total_fee);
    sParaTemp.put("body", body);
    sParaTemp.put("show_url", show_url);
    sParaTemp.put("anti_phishing_key", anti_phishing_key);
    sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

    // 建立请求
    String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
    response.getWriter().println(sHtmlText);
    logger.info("支付宝订单提交------>结束,phone=" + sessionUser.getPhone() + ",订单id=" + splitOrderId);
  }

}
