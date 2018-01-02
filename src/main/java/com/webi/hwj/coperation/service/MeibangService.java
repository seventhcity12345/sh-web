package com.webi.hwj.coperation.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;
import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.coperation.constant.MeibangConstant;
import com.webi.hwj.coperation.dao.CoperationDao;
import com.webi.hwj.coperation.entity.Coperation;
import com.webi.hwj.coperation.param.RedeemCodeRegisterParam;
import com.webi.hwj.coperation.util.MeibangUtil;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.entity.User;

/**
 * @category qqCoperation控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class MeibangService {
  private static Logger logger = Logger.getLogger(MeibangService.class);
  @Resource
  CoperationDao coperationDao;
  @Resource
  OrderCourseService orderCourseService;
  @Resource
  UserEntityDao userEntityDao;
  @Resource
  CoperationService coperationService;

  /**
   * Title: 美邦会员使用兑换码注册<br>
   * Description: 美邦会员使用兑换码注册<br>
   * CreateDate: 2016年9月26日 下午4:32:59<br>
   * 
   * @category 美邦会员使用兑换码注册
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
    // 兑换码
    String redeemCode = redeemCodeRegisterParam.getRedeemCode();

    // 兑换码不能重复使用
    Coperation coperation = coperationDao.findOneByRedeemCode(redeemCode);
    if (coperation != null) {
      // 兑换码已经使用过，返回错误
      json.setCode(ErrorCodeEnum.REDEEM_CODE_IS_USED.getCode());
      json.setMsg("兑换码已使用");
      return json;
    }

    // 手机号不允许重复判断
    User user = userEntityDao.findUserByUserIdOrPhone(phone);

    if (user != null) {
      // 插入兑换信息
      coperationService.saveCoperation(user.getKeyId(), redeemCodeRegisterParam,
          MeibangConstant.MEIBANG_COPERATION_TYPE);
      
      // 已经在我们系统注册了
      if (user.getIsStudent()) {
        // 是学员，不直接开通合同，给管理员发送开通课程短信
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg(MeibangConstant.MEIABNG_ISUSER_MSG);

        // 向开发人员发送短信
        SmsUtil.sendSmsToQueue(MemcachedUtil.getConfigValue("project_leader_phones"),
            phone + MeibangConstant.MEIABNG_ISUSER_SMS + redeemCode);
      } else {
        // 不是学员，直接开通合同
        // 开通合同
        JsonMessage jm = orderCourseService.oneKeyCreateOrder(
            MeibangConstant.MEIBANG_COURSE_PACKAGE_ID,
            MeibangConstant.MEIBANG_COURSE_PACKAGE_PRICE_ID, redeemCode,
            redeemCodeRegisterParam.getPhone(),
            redeemCodeRegisterParam.getUserName(), OrderCourseConstant.USER_FROM_TYPE_MBVIP,
            user.getKeyId());
        if (!jm.isSuccess()) {
          throw new RuntimeException(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode() + "");
        }
      }
    } else {
      // 没有在我们系统注册过，需要先注册
      // 保存用户信息
      redeemCodeRegisterParam
          .setPwd(SHAUtil.encode(phone.substring(phone.length() - 6, phone.length())));
      coperationService.saveUser(redeemCodeRegisterParam, MeibangConstant.MEIBANG_COPERATION_TYPE);

      // 开通合同
      JsonMessage jm = orderCourseService.oneKeyCreateOrder(
          MeibangConstant.MEIBANG_COURSE_PACKAGE_ID,
          MeibangConstant.MEIBANG_COURSE_PACKAGE_PRICE_ID, redeemCode,
          redeemCodeRegisterParam.getPhone(),
          redeemCodeRegisterParam.getUserName(), OrderCourseConstant.USER_FROM_TYPE_MBVIP,
          redeemCodeRegisterParam.getUserId());
      if (!jm.isSuccess()) {
        throw new RuntimeException(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode() + "");
      }
    }

    // 美邦异步核销
    String onsBody = MeibangConstant.MEIABNG_SMS_CONSUME_REDEEMCODE +
        "," + redeemCode + "," + phone;
    OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
        MemcachedUtil.getConfigValue("aliyun_ons_topicid"),
        ConfigConstant.ALIYUN_ONS_CONSUMERID_MEIBANG,
        onsBody);

    return json;
  }

  /**
   * 
   * Title: 检验兑换码是否合法<br>
   * Description: 检验兑换码是否合法<br>
   * CreateDate: 2016年11月8日 下午4:30:30<br>
   * 
   * @category 检验兑换码是否合法
   * @author seven.gz
   * @param redeemCode
   * @return
   * @throws Exception
   */
  public CommonJsonObject cehckRedeemCode(String redeemCode) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 调用接口校验兑换码
    if (MeibangUtil.checkCardNo(redeemCode)) {
      // 兑换码不能重复使用
      Coperation coperation = coperationDao.findOneByRedeemCode(redeemCode);
      if (coperation != null) {
        json.setCode(ErrorCodeEnum.REDEEM_CODE_IS_USED.getCode());
        json.setMsg("兑换码已使用");
      }
    } else {
      json.setCode(ErrorCodeEnum.REDEEM_CODE_ILLEGAL.getCode());
      json.setMsg("兑换码已使用");
    }
    return json;
  }

}