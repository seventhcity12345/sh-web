/** 
 * File: TellMeMoreConstant.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.tellmemore.constant<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年10月10日 下午3:43:47
 * @author yangmh
 */
package com.webi.hwj.tellmemore.constant;

import com.mingyisoft.javabase.util.MemcachedUtil;

/**
 * Title: TellMeMoreConstant<br>
 * Description: TellMeMoreConstant<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年10月10日 下午3:43:47
 * 
 * @author yangmh
 */
public class TellmemoreConstant {
  /**
   * 命令：创建用户
   */
  public static final String PTF_CMD_USER_CREATION = "101";
  /**
   * 命令：修改用户
   */
  public static final String PTF_CMD_USER_MODIFICATION = "102";
  /**
   * 命令：删除用户
   */
  public static final String PTF_CMD_USER_REMOVAL = "103";
  /**
   * 命令：为用户分配组
   */
  public static final String PTF_CMD_GROUP_ASSIGNING = "301";
  /**
   * 命令：做课件入口
   */
  public static final String PTF_CMD_DIRECT_CONNECTION_TO_THE_PORTAL = "401";

  /**
   * 命令：通过用户账号获取用户的信息
   */
  public static final String PTF_CMD_PUPIL_XML_INFORMATION_BY_USER_PHONE = "504";
  /**
   * 命令：通过管理组获取所有用户的信息
   */
  public static final String PTF_CMD_PUPIL_XML_INFORMATION_BY_ADMINISTRATIVE_GROUP = "506";

  /**
   * 组类型：语言组
   */
  public static final String GROUP_TYPE_PEDAGOGICAL_INSTRUCTIONAL = "2";
  /**
   * 组类型：管理组
   */
  public static final String GROUP_TYPE_ADMINISTRATIVE = "1";

  /**
   * 组类型：管理组id
   */
  public static final String PTF_GROUP_NUMBER = MemcachedUtil.getConfigValue("tmm_ptf_admingroup");

  /**
   * 合作者id
   */
  public static final String PTF_PARTNER = MemcachedUtil.getConfigValue("tmm_ptf_partner");

  /**
   * 工作组id
   */
  public static final String PTF_HOST = MemcachedUtil.getConfigValue("tmm_ptf_host");

  /**
   * 校验方式
   */
  public static final String PTF_METHOD = "SHA512";

  /**
   * 用户角色：学生
   */
  public static final String PTF_ROLE = "1";

  /**
   * tmm的api请求地址
   */
  public static final String TMM_API_REQUEST_URL = MemcachedUtil
      .getConfigValue("tmm_api_request_url");

  /**
   * 购买tmm合同的名称
   */
  public static final String PTF_OFFERNAME = MemcachedUtil.getConfigValue("tmm_ptf_offername");
  /**
   * 教学语言，默认为英语
   */
  public static final String PTF_PUPILDISCIPLINE = MemcachedUtil
      .getConfigValue("tmm_ptf_pupildiscipline");
  /**
   * 相当于organization id,为用户组id（此用户组并不是管理组）
   */
  public static final String PTF_CLIENT = MemcachedUtil.getConfigValue("tmm_ptf_client");
  /**
   * 接口的私钥，用于验证码的生成
   */
  public static final String PTF_KEY = MemcachedUtil.getConfigValue("tmm_ptf_key");

  /**
   * 用户显示界面的语言
   */
  // public static final String PTF_INTERFACELANGUAGE = "en-US";
  public static final String PTF_INTERFACELANGUAGE = MemcachedUtil
      .getConfigValue("tmm_ptf_interfacelanguage");

}
