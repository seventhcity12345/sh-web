package com.webi.hwj.coperation.constant;

/**
 * 
 * Title: 美邦常量类<br>
 * Description: 美邦常量类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年11月8日 下午2:10:59
 * 
 * @author seven.gz
 */
public class MeibangConstant {
  /**
   * 调用美邦api公司标识参数
   */
  public static final String API_PARAM_PROVIDERCODE = "WEIBO";

  /**
   * 调用美邦api券码校验接口地址
   */
  public static final String API_ADDRESS_CHECK_CARD_NO = "/mb-pstore-thirdservice/mb/checkCardNo.action";

  /**
   * 调用美邦api券码核销结果接收接口地址
   */
  public static final String API_ADDRESS_REC_EXCHANGE_RESULT = "/mb-pstore-thirdservice/mb/recExchangeResult.action";

  /**
   * 美邦合作方代码
   */
  public static final Integer MEIBANG_COPERATION_TYPE = 1;

  /**
   * 美邦兑换码课程包
   */
  public static final String MEIBANG_COURSE_PACKAGE_ID = "6a75fa2cdf054df1a6a565f287f02001";

  /**
   * 美邦兑换码价格策略
   */
  public static final String MEIBANG_COURSE_PACKAGE_PRICE_ID = "4eb73582e31b4f7680e2c1808dddddd1";

  /**
   * 美邦消息队列消费类型 0:核销兑换码
   */
  public static final String MEIABNG_SMS_CONSUME_REDEEMCODE = "0";
  
  /**
   *  兑换码注册 是已经注册的学员 给项目人员发送的短信
   */
  public static final String MEIABNG_ISUSER_SMS = "学员,美邦兑换码注册,请为其开通合同,兑换码:";
  
  /**
   * 兑换码注册 是已经注册的学员 给项目人员发送的短信
   */
  public static final String MEIABNG_ISUSER_MSG = "您已是嗨英语的用户，我们的客服将在3个工作日内联系您并为您提供课程，请留意接听。";

}
