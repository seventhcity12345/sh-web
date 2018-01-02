/** 
 * File: AlipayController.java<br> 
 * Project: javabase<br> 
 * Package: com.alipay.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年12月2日 下午2:46:51
 * @author yangmh
 */
package com.webi.hwj.zhaolian.controller;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mucfc.fep.base.ServiceResponse;
import com.mucfc.fep.sdk.util.ParamUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.ordercourse.entity.OrderCourseSplit;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.ordercourse.service.OrderCourseSplitService;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.SessionUtil;
import com.webi.hwj.zhaolian.service.ZhaolianService;

@Controller
public class ZhaolianController {
  private static Logger logger = Logger.getLogger(ZhaolianController.class);

  @Resource
  private OrderCourseService orderCourseService;

  @Resource
  private OrderCourseSplitService orderCourseSplitService;

  @Resource
  private UserService userService;

  @Resource
  private ZhaolianService zhaolianService;
  
  /**
   * Title: 招联订单状态监听(异步)<br>
   * Description: 招联订单状态监听(异步)<br>
   * CreateDate: 2017年4月18日 上午11:55:05<br>
   * 
   * @category 招联订单状态监听(异步)
   * @author komi.zsy
   * @param request
   * @param response
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/zhaolian/notifyUrl")
  public void notifyUrl(HttpServletRequest request) throws Exception {
    logger.info("招联回执监听------>开始");

    System.out.println(request);
    String success = request.getParameter("success");
    System.out.println("签名数据是" + success);

    logger.info("招联返回的数据：" + request.getParameterMap());
    ServiceResponse serviceResponse = new ServiceResponse();
    serviceResponse.fromHttpResponseParams(ParamUtil.requestMap2Map(request.getParameterMap()));
    logger.info(serviceResponse.toSignContent());

    //处理招联订单状态
    zhaolianService.afterZhaolianPaySuccessLogic(serviceResponse);
    
    logger.info("招联回执监听------>结束");
  }

  /**
   * Title: 招联订单同步回调<br>
   * Description: 招联订单同步回调<br>
   * CreateDate: 2017年4月18日 下午1:45:52<br>
   * 
   * @category 招联订单同步回调
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/api/speakhi/v1/zhaolian/returnUrl")
  public String returnUrl(HttpServletRequest request) throws Exception {
    logger.info("招联跳转------>开始");
    // 招联的开发说这边同步回调数据不准确，只做跳转就可以了不要同步数据
    logger.info("招联跳转------>结束");
    return "redirect:/ucenter/index";
  }

  /**
   * Title: 招联订单提交<br>
   * Description: 招联订单提交<br>
   * CreateDate: 2017年1月19日 上午11:05:07<br>
   * 
   * @category 招联订单提交
   * @author komi.zsy
   * @param request
   * @param response
   * @throws Exception
   */
  @RequestMapping("/api/speakhi/v1/zhaolian/ucenter/submitZhaolianForm")
  public void submitZhaolianForm(HttpServletRequest request, HttpServletResponse response,
      @RequestBody OrderCourseSplit orderCourseSplit)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String splitOrderId = orderCourseSplit.getKeyId();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    // 因为拟合同增加的名字身份证号，用户不更新session的话，可能会拿不到名字
    sessionUser = userService.initSessionUser(sessionUser.getKeyId(), null);
    logger.info("招联订单提交------>开始,phone=" + sessionUser.getPhone() + ",split订单id=" + splitOrderId);
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter pw = response.getWriter();

    // 招联订单提交
    json = zhaolianService.submitZhaolianForm(splitOrderId, sessionUser);
    if (ErrorCodeEnum.SUCCESS.getCode() != json.getCode()) {
      pw.write("<script>alert('" + json.getMsg() + "');history.back();</script>");
    }
    
    response.getWriter().println(json.getData());
    logger.info("招联订单提交------>结束,phone=" + sessionUser.getPhone() + ",订单id=" + splitOrderId);
  }
}
