package com.webi.hwj.baidu.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.alipay.dao.PayLogDao;
import com.webi.hwj.baidu.constant.BaiduConstant;
import com.webi.hwj.baidu.util.BaiduUtil;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.entity.OrderCourseSplit;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseSplitEntityDao;
import com.webi.hwj.ordercourse.service.OrderCourseService;

import net.sf.json.JSONObject;

/**
 * Title: 百度分期<br>
 * Description: BaiduService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年4月1日 上午9:41:41
 * 
 * @author komi.zsy
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BaiduService {
  private static Logger logger = Logger.getLogger(BaiduService.class);

  @Resource
  private AdminOrderCourseSplitEntityDao adminOrderCourseSplitEntityDao;
  @Resource
  private OrderCourseService orderCourseService;
  @Resource
  private PayLogDao payLogDao;

  /**
   * Title: 同步订单<br>
   * Description: 提交订单至百度并获得百度返回的页面地址，更新订单为申请中，锁定状态<br>
   * CreateDate: 2017年3月31日 下午2:22:16<br>
   * 
   * @category syncOrderInfo
   * @author komi.zsy
   * @param userId
   *          用户id
   * @param userName
   *          用户名字
   * @param userPhone
   *          用户手机号
   * @param orderSplitId
   *          订单id
   * @param splitPrice
   *          订单价格
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = { Exception.class })
  public String syncOrderInfo(String userId, String userName, String userPhone,
      String orderSplitId, String splitPrice,String coursePackageName) throws Exception {
    String url = "";
    try {
      url = BaiduUtil.syncOrderInfo(userId, userName, userPhone, orderSplitId, splitPrice,coursePackageName);
    } catch (Exception e) {
      logger.error("百度提交订单接口失败！！！");
    }

    // 没有url的话说明百度出问题了，不能更新订单状态为申请中，会被锁定不能再次提交
    if (!StringUtils.isEmpty(url)) {
      // 有url才更新状态
      // 一旦点击我要付款，订单状态就更改为申请中
      OrderCourseSplit paramObj = new OrderCourseSplit();
      paramObj.setKeyId(orderSplitId);
      paramObj.setSplitStatus(Integer.parseInt(
          OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_IS_APPLYING));
      adminOrderCourseSplitEntityDao.update(paramObj);
    }

    return url;
  }

  /**
   * Title: 获得百度订单状态<br>
   * Description: 获得百度订单状态<br>
   * CreateDate: 2017年3月30日 下午6:09:04<br>
   * 
   * @category 获得百度订单状态
   * @author komi.zsy
   * @param orderSplitId
   *          订单id
   * @param splitStatus
   *          订单原状态
   * @return
   * @throws Exception
   */
  public CommonJsonObject getOrderStatus(String orderSplitId, String splitStatus) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    json.setData(splitStatus);
    // 订单如果原来已经成功了或还未申请，不需要处理。
    if (!OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_APPLY_SUCCESS.equals(splitStatus
        .toString())
        && !OrderStatusConstant.ORDER_SPLIT_STATUS_BAIDU_NOT_APPLY.equals(splitStatus
            .toString())) {
      // 调用百度 查询订单状态 接口
      JSONObject jsonObject = BaiduUtil.getOrderStatus(orderSplitId);

      if (!BaiduConstant.BAIDU_STATUS_SUCCESS.equals(jsonObject.getString("status").toString())) {
        // 返回不成功时，返回错误信息
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        String msg = jsonObject.getString("msg").toString();
        json.setMsg(msg);

        // 记录log
        Map<String, Object> logMap = new HashMap<String, Object>();
        // 如果百度返回不成功，这里存的不是订单状态，是错误码
        if(jsonObject.containsKey("result")){
          logMap.put("trade_status", JSONObject.fromObject(jsonObject.getString("result")).getString("tstatus").toString());
        }
        logMap.put("order_id", orderSplitId);
        logMap.put("pay_log_desc", msg);
        logMap.put("pay_type", "百度分期报错");
        payLogDao.insert(logMap);
      } else {
        // 返回成功时
        if(jsonObject.containsKey("result")){
          String returnStatus = BaiduUtil.formatStatusByBaidu(
              JSONObject.fromObject(jsonObject.getString("result")).getString("tstatus").toString());
          // 百度支付回调逻辑
          orderCourseService.afterBaiduPaySuccessLogic(orderSplitId, "百度分期", returnStatus, null,null,null);
          json.setData(returnStatus);
        }
      }
    }
    return json;
  }

}
