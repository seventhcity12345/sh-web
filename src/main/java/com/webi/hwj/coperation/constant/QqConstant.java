package com.webi.hwj.coperation.constant;

public class QqConstant {
  /**
   * qq 核销兑换码接口成功返回值
   */
  public static final int CONSUMECPP_RESPONESE_CODE_SUCCESS = 0;
  
  /**
   * qq 核销兑换码接口成功返回值
   */
  public static final String CONSUMECPP_URL = "http://iyouxi.vip.qq.com/open.php?_f=consumecpp&";
  
  /**
   * qq 兑换码注册 是已经注册的学员 给项目人员发送的短信
   */
  public static final String QQ_ISUSER_SMS = "学员,QQ兑换码注册,请为其开通合同,兑换码:";
  
  
  /**
   * qq 兑换码注册 是已经注册的学员 给项目人员发送的短信
   */
  public static final String QQ_ISUSER_MSG = "您已是嗨英语的用户，我们的客服将在3个工作日内联系您并为您提供课程，请留意接听。";
  
  
  /**
   * qq 消息队列消费类型 0:核销兑换码
   */
  public static final String QQ_SMS_CONSUME_REDEEMCODE = "0";
  
  /**
   * qq合作方代码
   */
  public static final Integer QQ_COPERATION_TYPE = 0;
  
}
