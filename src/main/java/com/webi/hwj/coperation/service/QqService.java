package com.webi.hwj.coperation.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;
import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.coperation.constant.QqConstant;
import com.webi.hwj.coperation.dao.CoperationDao;
import com.webi.hwj.coperation.param.RedeemCodeRegisterParam;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.service.AdminOrderCourseSaveService;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.redeemcode.dao.RedeemCodeDao;
import com.webi.hwj.redeemcode.dao.RedeemCodeEntityDao;
import com.webi.hwj.redeemcode.entity.RedeemCode;
import com.webi.hwj.redeemcode.service.RedeemCodeService;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;

/**
 * 
 * Title: 兑换码活动<br>
 * Description: RedeemcodeService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年2月23日 下午2:54:38
 * 
 * @author athrun.cw
 */

@Service
public class QqService {
  private static Logger logger = Logger.getLogger(QqService.class);

  @Resource
  RedeemCodeDao redeemcodeDao;

  @Resource
  AdminUserEntityDao adminUserEntityDao;
  
  @Resource
  UserEntityDao userEntityDao;

  @Resource
  CoperationDao coperationDao;

  @Resource
  AdminOrderCourseSaveService adminOrderCourseSaveService;
  @Resource
  RedeemCodeEntityDao redeemCodeEntityDao;
  
  @Resource
  RedeemCodeService redeemcodeService;
  
  @Resource
  CoperationService coperationService;
  
  @Resource
  OrderCourseService orderCourseService;

  /**
   * Title: qq会员使用兑换码注册<br>
   * Description: qq会员使用兑换码注册<br>
   * CreateDate: 2016年9月26日 下午4:32:59<br>
   * 
   * @category qq会员使用兑换码注册
   * @author seven.gz
   * @param redeemCodeRegisterParam
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      timeout = 15)
  public CommonJsonObject redeemCodeRegister(RedeemCodeRegisterParam redeemCodeRegisterParam)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 密码为手机号后六位
    String phone = redeemCodeRegisterParam.getPhone();
    // 手机号不允许重复判断
    User user = userEntityDao.findUserByUserIdOrPhone(phone);

    if (user != null) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(QqConstant.QQ_ISUSER_MSG);
      // modify by seven 根据需求修改 已注册学员，核销兑换码，需要发送短信给开发人员联系销售联系学员给他卡合同 或 延期，
      // 查询兑换码
      RedeemCode redeemCodeObj = redeemCodeEntityDao.findRedeemCodeByCode(redeemCodeRegisterParam.getRedeemCode());
      if(redeemCodeObj != null && StringUtils.isEmpty(redeemCodeObj.getRedeemUserId())){
        // 更新兑换码
        redeemcodeService.updateRedeemCode(user.getKeyId(), redeemCodeRegisterParam.getPhone(),
            redeemCodeRegisterParam.getUserName(), redeemCodeObj, 0, new Date());
        
        // 插入兑换信息
        coperationService.saveCoperation(user.getKeyId(), redeemCodeRegisterParam,QqConstant.QQ_COPERATION_TYPE);
        
        // 异步调用美邦核销兑换码
        
        // 向开发人员发送短信
        SmsUtil.sendSmsToQueue(MemcachedUtil.getConfigValue("project_leader_phones"), 
            phone + QqConstant.QQ_ISUSER_SMS + redeemCodeObj.getRedeemCode());
      } else {
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg("兑换码错误");
      }
    } else {
      // 保存用户信息
      redeemCodeRegisterParam
          .setPwd(SHAUtil.encode(phone.substring(phone.length() - 6, phone.length())));
      coperationService.saveUser(redeemCodeRegisterParam,QqConstant.QQ_COPERATION_TYPE);

      // 开通合同
      JsonMessage jm = orderCourseService.saveOrderCourseAndOptionByRedeemCode(
          redeemCodeRegisterParam.getRedeemCode(), phone,
          redeemCodeRegisterParam.getUserName(),
          OrderCourseConstant.USER_FROM_TYPE_QQVIP, redeemCodeRegisterParam.getUserId());
      if (!jm.isSuccess()) {
        throw new RuntimeException(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode() + "");
      }

      RedeemCode redeemCodeObj = (RedeemCode) jm.getData();

      // 异步调用qq核销兑换码
      String onsBody = QqConstant.QQ_SMS_CONSUME_REDEEMCODE +
          "," + redeemCodeObj.getCpid() + "," + redeemCodeObj.getRedeemCode();
      OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
          MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_qq",
          onsBody);
      
    }
    return json;
  }

}
