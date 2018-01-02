/** 
 * File: TellMeMoreUtil.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.tellmemore.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年10月11日 下午12:49:42
 * @author yangmh
 */
package com.webi.hwj.tellmemore.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.HttpUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.mingyisoft.javabase.util.XmlUtil;
import com.webi.hwj.tellmemore.constant.TellmemoreConstant;

/**
 * Title: TellMeMoreUtil<br>
 * Description: TellMeMoreUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年10月11日 下午12:49:42
 * 
 * @author yangmh
 */
public class TellmemoreUtil {
  private static Logger logger = Logger.getLogger(TellmemoreUtil.class);

  /**
   * Title: 生成tmm加密串<br>
   * Description: generateCheckStr<br>
   * CreateDate: 2015年10月11日 下午12:51:55<br>
   * 
   * @category generateCheckStr
   * @author yangmh
   * @param login
   * @param curentGMTDateStr
   * @return
   */
  public static String generateCheckStr(String login, String curentGMTDateStr) {
    return SHAUtil.encode(
        login + TellmemoreConstant.PTF_PARTNER + curentGMTDateStr + TellmemoreConstant.PTF_KEY);
  }

  /**
   * Title: 生成通用接口Map<br>
   * Description: 生成通用接口Map<br>
   * CreateDate: 2015年10月11日 下午1:23:35<br>
   * 
   * @category 生成通用接口Map
   * @author yangmh
   * @param login
   *          登录名
   * @param cmd
   *          接口的命令符
   * @return
   */
  public static Map<String, String> createCommonRequestMap(String login, String cmd) {
    String curentGMTDateStr = DateUtil.getCurrentGMTDate();
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("ptf_cmd", cmd);
    paramMap.put("ptf_login", login);
    paramMap.put("ptf_host", TellmemoreConstant.PTF_HOST);
    paramMap.put("ptf_partner", TellmemoreConstant.PTF_PARTNER);
    System.out.println("dwiouhduoiwqdoi+:::" + paramMap.get("ptf_partner"));
    paramMap.put("ptf_timer", curentGMTDateStr);
    paramMap.put("ptf_method", TellmemoreConstant.PTF_METHOD);
    paramMap.put("ptf_check", TellmemoreUtil.generateCheckStr(login, curentGMTDateStr));
    return paramMap;
  }

  /**
   * Title: 生成通用接口Map（不要延迟）<br>
   * Description: 生成通用接口Map<br>
   * CreateDate: 2015年10月11日 下午1:23:35<br>
   * 
   * @category 生成通用接口Map
   * @author yangmh
   * @param login
   *          登录名
   * @param cmd
   *          接口的命令符
   * @return
   */
  public static Map<String, Object> createCommonRequestNoDelayMap(String login, String cmd) {
    String curentGMTDateStr = DateUtil.getCurrentGMTDate();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("ptf_cmd", cmd);
    paramMap.put("ptf_login", login);
    paramMap.put("ptf_host", TellmemoreConstant.PTF_HOST);
    paramMap.put("ptf_partner", TellmemoreConstant.PTF_PARTNER);
    paramMap.put("ptf_timer", curentGMTDateStr);
    paramMap.put("ptf_method", TellmemoreConstant.PTF_METHOD);
    paramMap.put("ptf_check", TellmemoreUtil.generateCheckStr(login, curentGMTDateStr));
    paramMap.put("ptf_live", "true");// 不要延迟4小时
    paramMap.put("ptf_discipline", "ENGLISH");// 不要延迟4小时
    return paramMap;
  }

  /**
   * Title: 生成通用接口Map<br>
   * Description: 生成通用接口Map<br>
   * CreateDate: 2015年10月11日 下午1:23:35<br>
   * 
   * @category 生成通用接口Map
   * @author yangmh
   * @param login
   *          登录名
   * @param cmd
   *          接口的命令符
   * @return
   */
  public static Map<String, String> createCommonRequestMap(String cmd) {
    String curentGMTDateStr = DateUtil.getCurrentGMTDate();
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("ptf_cmd", cmd);
    paramMap.put("ptf_partner", TellmemoreConstant.PTF_PARTNER);
    paramMap.put("ptf_timer", curentGMTDateStr);
    paramMap.put("ptf_method", TellmemoreConstant.PTF_METHOD);
    paramMap.put("ptf_check", TellmemoreUtil.generateCheckStr("", curentGMTDateStr));
    logger.debug("paramMap:" + paramMap);
    return paramMap;
  }

  /**
   * Title: 生成通用接口Map(不要延迟)<br>
   * Description: 生成通用接口Map<br>
   * CreateDate: 2015年10月11日 下午1:23:35<br>
   * 
   * @category 生成通用接口Map
   * @author yangmh
   * @param login
   *          登录名
   * @param cmd
   *          接口的命令符
   * @return
   */
  public static Map<String, String> createCommonRequestNoDelayMap(String cmd) {
    String curentGMTDateStr = DateUtil.getCurrentGMTDate();
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("ptf_cmd", cmd);
    paramMap.put("ptf_partner", TellmemoreConstant.PTF_PARTNER);
    paramMap.put("ptf_timer", curentGMTDateStr);
    paramMap.put("ptf_method", TellmemoreConstant.PTF_METHOD);
    paramMap.put("ptf_check", TellmemoreUtil.generateCheckStr("", curentGMTDateStr));
    paramMap.put("ptf_live", "true");// 不要延迟4小时
    paramMap.put("ptf_discipline", "ENGLISH");// 不要延迟4小时
    return paramMap;
  }

  /**
   * Title: 更新tmm用户账号<br>
   * Description: 更新tmm用户账号<br>
   * CreateDate: 2016年8月10日 下午3:35:03<br>
   * 
   * @category 更新tmm用户账号
   * @author komi.zsy
   * @param phone
   *          原账号
   * @param newPhone
   *          新账号
   * @return
   * @throws Exception
   */
  public static Map<String, Object> updateTmmUserAccount(String phone, String newPhone)
      throws Exception {
    logger.info("调用tmm接口------>修改tmm用户开始,phone=" + phone);
    Map<String, String> paramMap = TellmemoreUtil.createCommonRequestMap(phone,
        TellmemoreConstant.PTF_CMD_USER_MODIFICATION);

    // 新账号
    paramMap.put("ptf_newlogin", newPhone);
    // 新密码
    paramMap.put("ptf_password", newPhone);

    // 发送http post请求
    String returnStr = "";
    try {
      returnStr = HttpUtil.doPost(TellmemoreConstant.TMM_API_REQUEST_URL, paramMap);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      throw new RuntimeException("error:" + e.getMessage(), e);
    }
    logger.info("调用tmm接口------>修改tmm用户,returnStr" + returnStr);

    Map<String, Object> returnMap = XmlUtil.doXMLParse(returnStr.toString());// 解析微信返回的信息，以Map形式存储便于取值
    String errorMsg = returnMap.get("errortext") + "";
    if (!"null".equals(errorMsg)) {
      logger.error("修改tmm用户失败" + phone + "=====" + returnMap.get("errortext"));
      if (errorMsg.indexOf("exist") != -1) {
        // 用户已重复，不往下走
        return null;
      } else {
        // 其他异常，需要事务回滚，抛异常
        throw new RuntimeException(returnMap.get("errortext") + "");
      }
    } else {
      logger.info("调用tmm接口------>修改tmm用户成功,newPhone=" + newPhone);
    }

    for (String key : returnMap.keySet()) {
      logger.debug("key= " + key + " and value= " + returnMap.get(key));
    }

    logger.info("调用tmm接口------>修改tmm用户结束,phone=" + phone);
    return returnMap;
  }

  /**
   * Title: 创建tmm用户<br>
   * Description: 创建tmm用户<br>
   * CreateDate: 2015年10月11日 下午1:28:42<br>
   * 
   * @category 创建tmm用户
   * @author yangmh
   * @param login
   * @return
   * @throws Exception
   */
  public static Map<String, Object> createTmmUser(String phone, String userId) throws Exception {
    logger.info("调用tmm接口------>创建tmm用户开始,phone=" + phone);
    Map<String, String> paramMap = TellmemoreUtil.createCommonRequestMap(phone,
        TellmemoreConstant.PTF_CMD_USER_CREATION);

    paramMap.put("ptf_role", TellmemoreConstant.PTF_ROLE);
    // 单独登录用户名/密码都是手机号,例如:
    paramMap.put("ptf_password", phone);
    paramMap.put("ptf_interfacelanguage", TellmemoreConstant.PTF_INTERFACELANGUAGE);
    paramMap.put("ptf_firstname", phone);
    paramMap.put("ptf_lastname", userId);
    paramMap.put("ptf_client", TellmemoreConstant.PTF_CLIENT);
    paramMap.put("ptf_pupildiscipline", TellmemoreConstant.PTF_PUPILDISCIPLINE);
    paramMap.put("ptf_offername", TellmemoreConstant.PTF_OFFERNAME);

    // StringBuffer paramStrBuffer = new StringBuffer(1024);
    // for (String key : paramMap.keySet()) {
    // paramStrBuffer.append("&"+key+"="+paramMap.get(key));
    // }
    //
    // String finalParamStr = paramStrBuffer.toString().replaceFirst("&", "?");

    // 发送http post请求
    String returnStr = "";
    try {
      // https
      // String xxx = HttpsTest.doPost(TellmemoreConstant.TMM_API_REQUEST_URL,
      // finalParamStr, "utf-8", 8000, 8000);
      // System.out.println(xxx);//
      returnStr = HttpUtil.doPost(TellmemoreConstant.TMM_API_REQUEST_URL, paramMap);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      throw new RuntimeException("error:" + e.getMessage(), e);
    }
    logger.info("调用tmm接口------>创建tmm用户,returnStr" + returnStr);

    Map<String, Object> returnMap = XmlUtil.doXMLParse(returnStr.toString());// 解析微信返回的信息，以Map形式存储便于取值
    String errorMsg = returnMap.get("errortext") + "";
    if (!"null".equals(errorMsg)) {
      logger.error("创建tmm用户失败" + userId + "=====" + returnMap.get("errortext"));
      if (errorMsg.indexOf("exist") != -1) {
        // 用户已重复，不往下走
        return null;
      } else {
        // 其他异常，需要事务回滚，抛异常
        throw new RuntimeException(returnMap.get("errortext") + "");
      }
    } else {
      logger.info("调用tmm接口------>创建tmm用户成功");
    }

    for (String key : returnMap.keySet()) {
      logger.debug("key= " + key + " and value= " + returnMap.get(key));
    }

    logger.info("调用tmm接口------>创建tmm用户结束,phone=" + phone);
    return returnMap;
  }

  /**
   * Title: 为tmm用户分配组<br>
   * Description: 为tmm用户分配组<br>
   * CreateDate: 2015年10月11日 下午4:22:46<br>
   * 
   * @category 为tmm用户分配组
   * @author yangmh
   * @param login
   *          用户名
   * @param groupNumber
   *          tmm的组id
   * @param type
   *          组类别
   * @return
   * @throws Exception
   */
  public static Map<String, Object> assignGroup(String login, String groupNumber, String groupType)
      throws Exception {
    logger.info("调用tmm接口------>为tmm用户分配组开始,phone=" + login);
    Map<String, String> paramMap = TellmemoreUtil.createCommonRequestMap(login,
        TellmemoreConstant.PTF_CMD_GROUP_ASSIGNING);

    // 组类别（1:管理组,2:语言组）
    paramMap.put("ptf_assigntype", groupType);
    paramMap.put("ptf_groupnumber", groupNumber);

    // 发送http post请求
    String returnStr = "";

    try {
      returnStr = HttpUtil.doPost(TellmemoreConstant.TMM_API_REQUEST_URL, paramMap);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      throw new RuntimeException("error:" + e.getMessage(), e);
    }
    logger.info("调用tmm接口------>为tmm用户分配组,returnStr" + returnStr);

    Map<String, Object> returnMap = XmlUtil.doXMLParse(returnStr.toString());// 解析微信返回的信息，以Map形式存储便于取值
    if (returnMap.get("errorcode") != null) {
      logger.error("为tmm用户分配语言组失败" + login + "=====" + returnMap.get("errortext"));
      throw new RuntimeException(returnMap.get("errortext") + "");
    } else {
      logger.info("为tmm用户分配组成功");
    }

    for (String key : returnMap.keySet()) {
      logger.debug("key= " + key + " and value= " + returnMap.get(key));
    }

    logger.info("调用tmm接口------>为tmm用户分配组结束,phone=" + login);
    return returnMap;
  }

  /**
   * Title: 获取所有用户tmm信息<br>
   * Description: 获取所有用户tmm信息<br>
   * CreateDate: 2015年10月12日 上午7:45:18<br>
   * 
   * @category 获取所有用户tmm信息
   * @author yangmh
   * @return
   * @throws Exception
   */
  public static List<Map<String, Object>> fetchUserInformation(int pageNumber, int pageSize)
      throws Exception {
    logger.info("获取用户tmm信息开始======");
    Map<String, String> paramMap = TellmemoreUtil.createCommonRequestNoDelayMap(
        TellmemoreConstant.PTF_CMD_PUPIL_XML_INFORMATION_BY_ADMINISTRATIVE_GROUP);
    // 获取的tmm管理组的id
    paramMap.put("ptf_groupnumber", TellmemoreConstant.PTF_GROUP_NUMBER);

    // 分页
    paramMap.put("ptf_pagenumber", pageNumber + "");
    paramMap.put("ptf_itemsperpage", pageSize + "");

    // 发送http post请求
    String returnStr = HttpUtil.doPost(TellmemoreConstant.TMM_API_REQUEST_URL, paramMap);
    logger.debug("returnXml------>" + returnStr);
    List<Map<String, Object>> returnList = doXmlParseTellMeMoreFetchUser(returnStr);// .substring(38,
                                                                                    // returnStr.length())
    // logger.info("returnList------>"+returnList);
    logger.info("获取所有用户tmm信息结束======" + returnList.size());

    return returnList;
  }

  /**
   * Title: 生成开始做课件url<br>
   * Description: 生成开始做课件url<br>
   * CreateDate: 2015年10月12日 上午7:44:53<br>
   * 
   * @category 生成开始做课件url
   * @author yangmh
   * @param login
   * @return
   * @throws Exception
   */
  public static String generateConnectionPortalUrl(String login) throws Exception {
    logger.info("调用tmm接口------>生成开始做课件url开始," + login);
    Map<String, String> paramMap = TellmemoreUtil.createCommonRequestMap(login,
        TellmemoreConstant.PTF_CMD_DIRECT_CONNECTION_TO_THE_PORTAL);

    StringBuffer sb = new StringBuffer(
        TellmemoreConstant.TMM_API_REQUEST_URL + "?ptf_endingurl=http%3a%2f%2fwww.speakhi.com");

    for (String key : paramMap.keySet()) {
      sb.append("&" + key + "=" + paramMap.get(key));
    }
    logger.info("调用tmm接口------>" + login + ",课件地址为" + sb.toString());
    logger.info("调用tmm接口------>生成开始做课件url结束," + login);
    return sb.toString();
  }

  /**
   * 
   * Title: 根据用户手机号（RSA账号）获取单个用户tmm信息并计算三维总属性<br>
   * Description: 根据用户手机号（RSA账号）获取单个用户tmm信息<br>
   * CreateDate: 2016年3月24日 上午11:32:37<br>
   * 
   * @category 根据用户手机号（RSA账号）获取单个用户tmm信息
   * @author komi.zsy
   * @param phone
   *          rsa账号
   * @return
   * @throws Exception
   */
  public static Map<String, Object> fetchUserInformationAndSumByUserPhone(String phone)
      throws Exception {
    logger.info("调用tmm接口------>开始抓取单个用户信息," + phone);
    Map<String, Object> paramMap = TellmemoreUtil.createCommonRequestNoDelayMap(phone,
        TellmemoreConstant.PTF_CMD_PUPIL_XML_INFORMATION_BY_USER_PHONE);

    logger.debug("returnParamMap------>" + paramMap);

    // 发送http post请求
    String returnStr = HttpClientUtil.doPost(TellmemoreConstant.TMM_API_REQUEST_URL, paramMap);
    logger.debug("returnXml------>" + returnStr);
    Map<String, Object> pupilMap = doXmlParseTellMeMoreFetchUserByUserPhone(returnStr);
    logger.info("returnPupilMap------>" + pupilMap);

    if (pupilMap == null || pupilMap.size() == 0) {
      return null;
    }

    // 取出学员所有课程信息，并进行sum计算三个维度的数据（完成率，正确率，学习时间）
    List<Map<String, Object>> sequenceList = (List) pupilMap.get("sequenceList");

    int percentdoneSum = 0;
    int percentcorrectSum = 0;
    List<Integer> timeSum = new ArrayList<Integer>();
    for (int i = 0; i < 3; i++) {
      timeSum.add(0);
    }

    // 没上过课，课程为空，则不进行计算
    if (sequenceList != null) {
      for (Map<String, Object> sequence : sequenceList) {
        // logger.debug("用户id(韦博)===>"+pupilMap.get("lastname"));
        // logger.debug("课程名称===>"+sequence.get("name"));
        // logger.debug("完成百分比===>"+sequence.get("percentdone"));
        // logger.debug("准确率===>"+sequence.get("percentcorrect"));
        // logger.debug("学习时间===>"+sequence.get("workingTime"));
        // logger.debug("sequence===>"+sequence.toString());
        // System.out.println(pupilMap);

        String percentdone = (String) sequence.get("percentdone");
        String percentcorrect = (String) sequence.get("percentcorrect");
        String workingTime = (String) sequence.get("workingTime");

        int numTemp = Integer.parseInt(percentdone);
        percentdoneSum += numTemp;

        if ("N/A".equals(percentcorrect) || "n/a".equals(percentcorrect)) {
          numTemp = 100;
        } else {
          numTemp = Integer.parseInt(percentcorrect);
        }

        percentcorrectSum += numTemp;

        List<Integer> timeTemp = TellmemoreUtil.getTimeInInt(workingTime);

        for (int i = 0; i < 3; i++) {
          timeSum.set(i, timeSum.get(i) + timeTemp.get(i));
        }

      }
    }

    Map<String, Object> returnMap = new HashMap<String, Object>();
    returnMap.put("total_tmm_percent", percentdoneSum);
    returnMap.put("total_tmm_correct", percentcorrectSum);
    returnMap.put("total_tmm_workingtime", TellmemoreUtil.getTimeInString(timeSum));
    returnMap.put("workingTimeList", timeSum);

    logger.info("获取单个用户tmm信息结束======");

    return returnMap;
  }

  /**
   * Title: 删除用户<br>
   * Description: 删除用户<br>
   * CreateDate: 2016年8月16日 上午10:38:07<br>
   * 
   * @category 删除用户
   * @author komi.zsy
   * @param login
   * @param groupNumber
   * @param groupType
   * @return
   * @throws Exception
   */
  public static Map<String, Object> userRemoval(String login) throws Exception {
    logger.info("调用tmm接口------>删除tmm用户开始,phone=" + login);
    Map<String, String> paramMap = TellmemoreUtil.createCommonRequestMap(login,
        TellmemoreConstant.PTF_CMD_USER_REMOVAL);

    // 用户角色：学生
    paramMap.put("ptf_role", TellmemoreConstant.PTF_ROLE);

    // 发送http post请求
    String returnStr = "";

    try {
      returnStr = HttpUtil.doPost(TellmemoreConstant.TMM_API_REQUEST_URL, paramMap);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      throw new RuntimeException("error:" + e.getMessage(), e);
    }
    logger.info("调用tmm接口------>删除tmm用户,returnStr" + returnStr);

    Map<String, Object> returnMap = XmlUtil.doXMLParse(returnStr.toString());// 解析微信返回的信息，以Map形式存储便于取值
    if (returnMap.get("errorcode") != null) {
      if ("17".equals(returnMap.get("errorcode"))) {
        logger.info("用户账户不存在，‘删除’tmm用户成功");
      } else {
        logger.error("删除tmm用户失败" + login + "=====" + returnMap.get("errortext"));
        throw new RuntimeException(returnMap.get("errortext") + "");
      }
    } else {
      logger.info("删除tmm用户成功");
    }

    for (String key : returnMap.keySet()) {
      logger.debug("key= " + key + " and value= " + returnMap.get(key));
    }

    logger.info("调用tmm接口------>删除tmm用户结束,phone=" + login);
    return returnMap;
  }

  /**
   * 
   * Title: 0:0:0时间转换为三个int数据<br>
   * Description: 0:0:0时间转换为三个int数据<br>
   * CreateDate: 2016年3月25日 上午11:07:15<br>
   * 
   * @category 0:0:0时间转换为三个int数据
   * @author komi.zsy
   * @param workingTime
   * @return
   */
  public static List<Integer> getTimeInInt(String workingTime) {
    String[] timeArr = workingTime.split(":");
    int numTemp = 0;
    List<Integer> timeReturnList = new ArrayList<Integer>();

    for (int i = 0; i < 3; i++) {
      numTemp = Integer.parseInt(timeArr[i]);
      timeReturnList.add(numTemp);
      // System.out.println("1111111:"+timeArr[i]);
    }
    return timeReturnList;
  }

  /**
   * 
   * Title: 三个int数据转换成时间字符串<br>
   * Description: 三个lint数据转换成时间字符串<br>
   * CreateDate: 2016年3月25日 上午11:07:50<br>
   * 
   * @category 三个int数据转换成时间字符串
   * @author komi.zsy
   * @param workingTime
   * @return
   */
  public static String getTimeInString(List<Integer> arrTime) {
    // 计算秒
    // 当秒数大于60时，向分钟进位，当秒数小于0时（因为会做加减法，可能会出现负数），向分钟进位负数，当剩余负数还有余数时，再向分钟-1，秒数+60
    if (arrTime.get(2) >= 60) {
      arrTime.set(1, arrTime.get(1) + arrTime.get(2) / 60);
      arrTime.set(2, arrTime.get(2) % 60);
    } else if (arrTime.get(2) < 0) {
      arrTime.set(1, arrTime.get(1) + arrTime.get(2) / 60);

      if (arrTime.get(2) % 60 == 0) {
        arrTime.set(2, 0);
      } else {
        arrTime.set(1, arrTime.get(1) - 1);
        arrTime.set(2, arrTime.get(2) % 60 + 60);
      }
    }

    // 计算分钟
    // 当分钟大于60时，向小时进位，当分钟小于0时（因为会做加减法，可能会出现负数），向小时进位负数，当剩余负数还有余数时，再向小时-1，分钟+60
    if (arrTime.get(1) >= 60) {
      arrTime.set(0, arrTime.get(0) + arrTime.get(1) / 60);
      arrTime.set(1, arrTime.get(1) % 60);
    } else if (arrTime.get(1) < 0) {
      arrTime.set(0, arrTime.get(0) + arrTime.get(1) / 60);

      if (arrTime.get(1) % 60 == 0) {
        arrTime.set(1, 0);
      } else {
        arrTime.set(0, arrTime.get(0) - 1);
        arrTime.set(1, arrTime.get(1) % 60 + 60);
      }
    }

    StringBuffer sb = new StringBuffer();

    sb.append(arrTime.get(0) + ":");

    if (arrTime.get(1) < 10) {
      sb.append("0" + arrTime.get(1) + ":");
    } else {
      sb.append(arrTime.get(1) + ":");
    }

    if (arrTime.get(2) < 10) {
      sb.append("0" + arrTime.get(2));
    } else {
      sb.append(arrTime.get(2));
    }

    return sb.toString();
    // return arrTime.get(0)+":"+arrTime.get(1)+":"+arrTime.get(2);
  }

  /**
   * 
   * Title: 解析抓取单个用户信息的xml<br>
   * Description: doXmlParseTellMeMoreFetchUser<br>
   * CreateDate: 2016年3月24日 上午11:45:05<br>
   * 
   * @category 解析抓取单个用户信息的xml
   * @author komi.zsy
   * @param strxml
   * @return
   * @throws Exception
   */
  public static Map<String, Object> doXmlParseTellMeMoreFetchUserByUserPhone(String strxml)
      throws Exception {
    if (null == strxml || "".equals(strxml)) {
      return null;
    }
    InputStream in = null;
    Map<String, Object> pupilMap = null;

    try {
      in = new ByteArrayInputStream(strxml.getBytes("utf-8"));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(in);
      // 获取根元素
      Element root = doc.getRootElement();

      if (root.getChildren("pupil") == null || root.getChildren("pupil").size() == 0) {
        return null;
      }

      if (root.getChildren("pupil").get(0) == null) {
        return null;
      }

      // 获取pupil
      Element pupilElement = (Element) root.getChildren("pupil").get(0);

      pupilMap = doXmlParseTellMeMoreFetchUserByOne(pupilElement);
      // modified by alex.yang 防止空指针异常 2016年5月6日 19:23:50
      logger.debug("tmm接口---->本次抓取pupilMap='" + pupilMap + "'学员的信息");
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.toString(), e);

    } finally {
      if (in != null) {
        in.close();
      }
    }
    return pupilMap;
  }

  /**
   * Title: 解析抓取用户数据的返回xml<br>
   * Description: 因为tmm的xml特别灵活因此不能使用固定的递归写法<br>
   * CreateDate: 2015年10月19日 下午6:42:09<br>
   * 
   * @category 解析抓取用户数据的返回xml
   * @author yangmh
   * @param strxml
   * @return
   * @throws Exception
   */
  public static List<Map<String, Object>> doXmlParseTellMeMoreFetchUser(String strxml)
      throws Exception {
    if (null == strxml || "".equals(strxml)) {
      return null;
    }
    InputStream in = null;
    List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

    try {
      in = new ByteArrayInputStream(strxml.getBytes("utf-8"));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(in);
      // 获取根元素
      Element root = doc.getRootElement();

      /**
       * modified by komi 2016年6月23日17:43:51 如果为空则直接跳出
       */
      if (root.getChildren("summary") == null || root.getChildren("summary").size() == 0) {
        return null;
      }
      Element summaryElement = (Element) root.getChildren("summary").get(0);

      // list返回的第一条是summary数据
      returnList.add(XmlUtil.getMapFromElement(summaryElement));

      /**
       * modified by komi 2016年6月23日17:43:51 如果为空则直接跳出
       */
      if (root.getChildren("pupils") == null || root.getChildren("pupils").size() == 0) {
        return null;
      }
      // 获取子元素
      Element pupilsElement = (Element) root.getChildren("pupils").get(0);

      List<Element> pupilList = pupilsElement.getChildren();
      logger.info("tmm接口---->本次抓取" + pupilList.size() + "个学员的信息");

      for (Element pupil : pupilList) {

        Map<String, Object> pupilMap = null;
        // 一个学员出问题，继续操作其他学员
        try {
          // modified by komi at 2016年3月29日17:12:47
          // 此处代码块因有重复，移植到doXmlParseTellMeMoreFetchUserByOne(pupil)
          pupilMap = doXmlParseTellMeMoreFetchUserByOne(pupil);
        } catch (Exception e) {
          e.printStackTrace();
          logger.error("error:" + e.toString(), e);
          continue;
        }

        if (pupilMap == null) {
          continue;
        }

        returnList.add(pupilMap);

      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.toString(), e);
    } finally {
      if (in != null) {
        in.close();
      }
    }
    return returnList;
  }

  /**
   * 
   * Title: 解析一个RSA学员数据所有课程信息,从Xml解析成Map<br>
   * Description: 因alex的拉取所有学员信息和komi的拉取单个学员信息都需用到此处解析，所以此块代码独立出来<br>
   * CreateDate: 2016年3月24日 下午1:33:17<br>
   * 
   * @category 解析一个RSA学员数据所有课程信息,从Xml解析成Map
   * @author komi.zsy
   * @param pupil
   * @return
   * @throws Exception
   */
  public static Map<String, Object> doXmlParseTellMeMoreFetchUserByOne(Element pupil)
      throws Exception {
    Map<String, Object> pupilMap = new HashMap<String, Object>();
    List<Map<String, Object>> sequenceList = new ArrayList<Map<String, Object>>();
    pupilMap = XmlUtil.getMapFromElement(pupil);
    pupilMap.put("sequenceList", sequenceList);

    // <disciplines>
    Element disciplinesElement = (Element) pupil.getChildren().get(0);

    // <discipline disciplineid="ENGLISH" disciplinename="English"
    // trainingstartdate="2015-10-11"
    List<Element> disciplinesElementList = disciplinesElement.getChildren();

    // komi+alex:如果当前用户的语言没有设置，那么无法更新这个用户的信息
    if (disciplinesElementList == null || disciplinesElementList.size() == 0) {
      return null;
    }
    Element disciplineElement = (Element) disciplinesElementList.get(0);

    // <catalogues>
    List<Element> cataloguesElementList = disciplineElement.getChildren("catalogues");
    Element catalogueElement = cataloguesElementList.get(0);

    // 有的学员可能压根就没有进行学习，那么就别往下走啦。
    if (catalogueElement.getChildren().isEmpty()) {
      return null;
    }

    // <catalogue name="Training Catalogs Assigned by the Administrator"
    // percentdone="11"
    // modified by komi at 2016年5月20日12:10:01
    // rsa分组可能有好几个，不能只取第一个
    // Element secondCataloguesElement = (Element)
    // catalogueElement.getChildren().get(0);
    List<Element> secondCataloguesElementsList = catalogueElement.getChildren();
    if (secondCataloguesElementsList != null && secondCataloguesElementsList.size() != 0) {
      for (Element secondCataloguesElement : secondCataloguesElementsList) {
        List<Element> secondCatalogueElementList = secondCataloguesElement.getChildren();

        // <catalogues>
        Element secondcataloguesElement = secondCatalogueElementList.get(0);

        // 获得学习等级列表 <catalogue name="Beginner A1" percentdone="0"
        List<Element> secondcatalogueElementList = secondcataloguesElement.getChildren();

        for (Element catelogueElement : secondcatalogueElementList) {
          // <catalogue name="2016-09-08" percentdone="1" percentcorrect="100"
          // workingTime="0:01:06">
          List<Element> cateloguesElementList = catelogueElement.getChildren();
          // <catalogues>
          Element thirdCataloguesElement = cateloguesElementList.get(0);

          List<Element> thirdCataloguesElementList = thirdCataloguesElement.getChildren();

          if (thirdCataloguesElementList == null || thirdCataloguesElementList.size() == 0) {
            continue;
          }

          // <catalogue name="General Level 1" percentdone="1"
          // percentcorrect="100" workingTime="0:01:06">
          Element thirdCatalogueElement = thirdCataloguesElementList.get(0);

          // 获取课程列表等级（用于判断相同名字的课程）
          Map<String, String> levelMap = XmlUtil.getMapFromElement(thirdCatalogueElement);
          String courseLevel = levelMap.get("name");

          // <sequences>
          List<Element> sequencesElementList = thirdCatalogueElement.getChildren("sequences");
          Element sequencesElement = sequencesElementList.get(0);

          List<Element> sequenceElementList = sequencesElement.getChildren();

          for (Element sequenceElement : sequenceElementList) {
            /**
             * modified by komi 2016年9月9日15:21:04 增加课程等级（用于判断相同名字的课程）
             */
            // <sequence name="Installation Guide" percentdone="34"
            // percentcorrect="50"
            Map<String, Object> courseMap = XmlUtil.getMapFromElement(sequenceElement);
            /**
             * modified by komi 2016年9月23日14:06:57
             * rsa有个错误的目录，名字错误的“Generallevel 18”，在这里强行改成我们系统中正确的 General Level 18
             */
            if("Generallevel 18".equals(courseLevel)){
              courseLevel = "General Level 18";
            }
            courseMap.put("courseLevel", courseLevel);
            sequenceList.add(courseMap);
          }
        }
      }
    }

    return pupilMap;
  }

  /**
   * Title: 通过手机号查询用户信息<br>
   * Description: 用于1v1课程列表同步更新课程进度<br>
   * CreateDate: 2016年5月4日 下午4:39:26<br>
   * 
   * @category 通过手机号查询用户信息
   * @author yangmh
   * @param phone
   * @return
   * @throws Exception
   */
  public static List<Map<String, Object>> fetchUserInformationByUserPhone(String phone)
      throws Exception {
    logger.info("调用tmm接口------>开始抓取单个用户信息(alex)," + phone);
    Map<String, Object> paramMap = TellmemoreUtil.createCommonRequestNoDelayMap(phone,
        TellmemoreConstant.PTF_CMD_PUPIL_XML_INFORMATION_BY_USER_PHONE);

    logger.debug("returnParamMap------>" + paramMap);

    // 发送http post请求
    String returnStr = HttpClientUtil.doPost(TellmemoreConstant.TMM_API_REQUEST_URL, paramMap);
    logger.debug("returnXml------>" + returnStr);
    Map<String, Object> pupilMap = doXmlParseTellMeMoreFetchUserByUserPhone(returnStr);
    logger.info("returnPupilMap------>" + pupilMap);

    if (pupilMap == null || pupilMap.size() == 0) {
      return null;
    }

    // 取出学员所有课程信息，并进行sum计算三个维度的数据（完成率，正确率，学习时间）
    List<Map<String, Object>> sequenceList = (List) pupilMap.get("sequenceList");

    logger.info("获取单个用户tmm信息结束(alex)======");
    return sequenceList;
  }

  /**
   * 
   * Title: 比较RSA统计表里的学习时间和给的分钟数<br>
   * Description: 比较表里的学习时间和给的分钟数，表里的时间长则返回true<br>
   * CreateDate: 2016年6月12日 下午5:55:16<br>
   * 
   * @category 比较RSA统计表里的学习时间和给的分钟数
   * @author seven.gz
   * @param rsaStringTime
   * @param workingMinute
   * @return
   */
  public static boolean compareWithMinute(String rsaStringTime, int workingMinute) {
    List<Integer> timeList = getTimeInInt(rsaStringTime);
    if (timeList != null && timeList.size() > 0) {
      if (timeList.get(0) > 0) {
        return true;
      }
      if (timeList.get(1) >= workingMinute) {
        return true;
      }
    }
    return false;
  }
}
