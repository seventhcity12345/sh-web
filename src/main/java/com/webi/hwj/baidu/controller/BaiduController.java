/** 
 * File: AlipayController.java<br> 
 * Project: javabase<br> 
 * Package: com.alipay.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年12月2日 下午2:46:51
 * @author yangmh
 */
package com.webi.hwj.baidu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.baidu.constant.BaiduConstant;
import com.webi.hwj.baidu.service.BaiduService;
import com.webi.hwj.baidu.util.BaiduUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.ordercourse.entity.OrderCourseSplit;
import com.webi.hwj.ordercourse.param.OrderAndOrderSplitParam;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.ordercourse.service.OrderCourseSplitService;
import com.webi.hwj.util.SessionUtil;

/**
 * Title: 百度分期<br>
 * Description: BaiduController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年3月30日 上午11:00:42
 * 
 * @author komi.zsy
 */
@Controller
public class BaiduController {
  private static Logger logger = Logger.getLogger(BaiduController.class);

  @Resource
  private BaiduService baiduService;
  @Resource
  private OrderCourseService orderCourseService;

  @Resource
  private OrderCourseSplitService orderCourseSplitService;

  /**
   * Title: 百度回调监听<br>
   * Description: 百度回调监听<br>
   * CreateDate: 2017年3月30日 上午11:06:18<br>
   * 
   * @category 百度回调监听
   * @author komi.zsy
   * @param request
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/baidu/notifyUrl")
  public Map<String, Object> notifyUrl(HttpServletRequest request) throws Exception {
    logger.info("百度回调监听----->开始");
    // 获取百度POST过来反馈信息
    Map<String, Object> params = RequestUtil.getParameterMap(request);
    // 商户订单号
    String out_trade_no = params.get("orderid").toString();
    // 交易状态
    String trade_status = params.get("status").toString();
    //签名
    String sign = params.get("sign").toString();
    
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("orderid", out_trade_no);
    paramMap.put("status", trade_status);
    //分期状态：只有在推送放款状态时，才可能会推送该参数。机构在计算签名时，注意把接收到的参数算在签名里，没有该参数则不能计入签名。
    if(params.get("period")!=null){
      String period = params.get("period").toString();
      paramMap.put("period", period);
    }
    //我们自己拿收到的明文参数加密一下
    String signStr = BaiduUtil.formatSign(paramMap);
    
    //返回的参数，百度有自己要求的格式，详见文档2.0
    Map<String, Object> returnMap = new HashMap<String, Object>();
    returnMap.put("status", BaiduConstant.BAIDU_STATUS_SUCCESS);
    returnMap.put("msg", "success");
    
    if(sign.equals(signStr)){
      //校验，参数是否正确
      // 百度支付回调逻辑
      try {
        orderCourseService.afterBaiduPaySuccessLogic(out_trade_no, "百度分期",
            BaiduUtil.formatStatusByBaidu(trade_status), null,null,null);
      } catch (Exception e) {
        returnMap.put("status", BaiduConstant.BAIDU_STATUS_ORDER_NOT_EXIST);
        returnMap.put("msg", e.getMessage());
      }
      
    }
    else{
      //参数不正确
      returnMap.put("status", BaiduConstant.BAIDU_STATUS_PARAM_ERROR);
      returnMap.put("msg", "参数校验错误！");
    }
    
    logger.info("百度回调监听------>结束");
    return returnMap;
  }

  /**
   * Title: 百度订单提交<br>
   * Description: 百度订单提交<br>
   * CreateDate: 2017年3月31日 上午11:56:58<br>
   * 
   * @category 百度订单提交
   * @author komi.zsy
   * @param request
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/baidu/ucenter/submitBaiduForm")
  public CommonJsonObject submitBaiduForm(HttpServletRequest request,@RequestBody OrderCourseSplit orderCourseSplit)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 订单ID
    String splitOrderId = orderCourseSplit.getKeyId();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    logger.info("百度订单提交------>开始,phone=" + sessionUser.getPhone() + ",split订单id=" + splitOrderId);

    // 1.查询订单相关信息
    OrderAndOrderSplitParam orderCourseSplitObj = orderCourseSplitService.findOneAndOrderInfoByKeyId(splitOrderId);
    if (orderCourseSplitObj == null) {
      logger.error("合同订单号不存在!phone=" + sessionUser.getPhone());
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("合同订单不存在！");
      return json;
    }
    
    // 2.同步订单数据到百度，并返回百度的页面地址
    String url = baiduService.syncOrderInfo(sessionUser.getKeyId(), sessionUser.getUserName(),
        sessionUser.getPhone(), orderCourseSplitObj.getKeyId(),
        orderCourseSplitObj.getSplitPrice().toString(),orderCourseSplitObj.getCoursePackageName());
    
    json.setData(url);
    
    return json;

  }

}
