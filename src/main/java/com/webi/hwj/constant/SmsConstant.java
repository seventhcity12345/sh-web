package com.webi.hwj.constant;

/**
 * 
 * Title: 发送短信的内容<br>
 * Description: SmsConstant<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年11月24日 下午2:14:41
 * 
 * @author athrun.cw
 */
public class SmsConstant {

  /**
   * 发送短信网站&&前缀
   */
  public static final String PREFIX_SPEAKHI = "SpeakHi嗨英语";

  /**
   * 注册成功后，发送的短信内容
   */
  public static final String REGISTER_NEW_USER = "（号码随机，6位）为您的原始注册密码，请尽快登陆网站重新设定密码并完善个人资料 http://www.speakhi.com/";

  /**
   * 后台代注册，提示用户成为学员
   */
  public static final String BACK_REGISTER_SUCCESS = "欢迎成为SpeakHi的一员！ 请先登录网站完善个人资料，让外教老师更好地认识你吧！speakhi.com";

  /**
   * 兑换码一键兑换拟定合同成功提示
   */
  public static final String SAVE_ORDERCOURSE_BY_REDEEM_CODE_SUCCESS = "您的课程已兑换成功，感谢成为嗨英语的一员！系统默认开课等级为Level3，为了保障学习体验，请尽快在当前页面完成水平测试以自动调整您的学习级别——来自嗨英语官网speakhi.com";

  /**
   * 领取es课程 兑换码发送的短信头
   */
  public static final String SEND_REDEEM_CODE_HEAD = "恭喜您领取成功！您的课程兑换码是:";
  /**
   * 领取es课程 兑换码发送的短信尾
   */
  public static final String SEND_REDEEM_CODE_TAIL = " ,兑换登录网址是:";
}
