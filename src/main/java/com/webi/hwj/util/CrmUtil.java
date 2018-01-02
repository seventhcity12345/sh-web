/** 
 * File: CrmUtil.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年4月26日 下午2:50:20
 * @author ivan.mgh
 */
package com.webi.hwj.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.ordercourse.param.CrmUpdateOrderMoneyParam;

import net.sf.json.JSONObject;

/**
 * Title: CRM拟合同工具类<br>
 * Description: CrmUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月26日 下午2:50:20
 * 
 * @author ivan.mgh
 */
public class CrmUtil {

  private static Logger logger = Logger.getLogger(CrmUtil.class);

  /**
   * Title: 调用crm同步合同信息接口<br>
   * Description: 调用crm同步合同信息接口<br>
   * CreateDate: 2017年4月5日 下午4:13:17<br>
   * 
   * @category 调用crm同步合同信息接口
   * @author komi.zsy
   * @param crmContractId
   *          crm合同id
   * @return
   * @throws Exception
   */
  public static Boolean updateOrderStatusToCrm(String crmContractId) throws Exception {
    String url = "http://10.0.0.83:9977/UpdateContract.asmx/UpdateStudentContract";
    String paramJsonStr = "{\"contractId\":\"" + crmContractId + "\"}";
    String jsonStr = HttpClientUtil.doPostByJson(url, paramJsonStr, null);
    JSONObject returnJson = JSONObject.fromObject(jsonStr);
    logger.info(returnJson);
    if ("true".equals(returnJson.getString("d").toString())) {
      return true;
    } else {
      logger.error("crm合同同步接口错误：" + returnJson.getString("d").toString());
    }
    return false;
  }

  /**
   * Title: 向crm推送变更合同金额<br>
   * Description: 向crm推送变更合同金额<br>
   * CreateDate: 2017年7月4日 下午8:40:43<br>
   * 
   * @category 向crm推送变更合同金额
   * @author komi.zsy
   * @param crmUpdateOrderMoneyParamList
   *          变更金额列表
   * @return
   * @throws Exception
   */
  public static Boolean updateMoneyToCrm(
      List<CrmUpdateOrderMoneyParam> crmUpdateOrderMoneyParamList) throws Exception {

    String url = MemcachedUtil.getConfigValue(ConfigConstant.CRM_UPDATE_MONEY_URL);

    // 拼接json字符串
    StringBuffer paramJsonStr = new StringBuffer();
    paramJsonStr.append("[");

    if (crmUpdateOrderMoneyParamList != null && crmUpdateOrderMoneyParamList.size() != 0) {
      for (CrmUpdateOrderMoneyParam crmUpdateOrderMoneyParam : crmUpdateOrderMoneyParamList) {
        paramJsonStr.append("{\"PhoneNumber\":\"" + crmUpdateOrderMoneyParam.getPhone()
            + "\",\"Money\":\"" + crmUpdateOrderMoneyParam.getSplitPrice() + "\"},");
      }
    } else {
      // 因为循环里最后会多一个逗号，在最外面去除逗号，这边还要再判断觉得没什么意思，就干脆多加一个逗号，在外面去除一下就ok了
      paramJsonStr.append("{},");
    }
    paramJsonStr.deleteCharAt(paramJsonStr.length() - 1);
    paramJsonStr.append("]");
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("json", paramJsonStr);
    String jsonStr = HttpClientUtil.doPost(url, paramMap);
    if (jsonStr.contains("<result>true</result>")) {
      return true;
    } else {
      logger.error("crm合同同步金额接口错误");
      SmsUtil.sendAlarmSms("crm合同同步金额接口错误");
    }
    return false;
  }

  /**
   * Title: 构造SessionAdminUser对象<br>
   * Description: initSessionAdminUser<br>
   * CreateDate: 2016年4月28日 下午12:28:45<br>
   * 
   * @category 构造SessionAdminUser对象
   * @author ivan.mgh
   * @param adminUserObj
   *          管理员对象，包含所有字段
   * @return
   * @throws Exception
   */
  public static SessionAdminUser initSessionAdminUser(Map<String, Object> adminUserObj)
      throws Exception {
    SessionAdminUser sessionAdminUser = new SessionAdminUser();
    sessionAdminUser.setAccount(adminUserObj.get("account") + "");
    sessionAdminUser.setCreateDate((Date) adminUserObj.get("create_date"));
    sessionAdminUser.setEmail(adminUserObj.get("email") + "");
    sessionAdminUser.setKeyId(adminUserObj.get("key_id") + "");
    sessionAdminUser.setPhone(adminUserObj.get("phone") + "");
    sessionAdminUser.setRoleId(adminUserObj.get("role_id") + "");
    sessionAdminUser.setRoleName(adminUserObj.get("role_name") + "");
    sessionAdminUser.setAdminUserName(adminUserObj.get("admin_user_name") + "");
    sessionAdminUser.setAdminUserType(adminUserObj.get("admin_user_type") + "");
    sessionAdminUser.setPwd(adminUserObj.get("pwd") + "");

    return sessionAdminUser;
  }

  /**
   * Title: 从缓存中拿到SessionAdminUser<br>
   * Description: findAdminUserFromCache<br>
   * CreateDate: 2016年4月28日 下午12:36:51<br>
   * 
   * @category 从缓存中拿到SessionAdminUser
   * @author ivan.mgh
   * @param request
   * @return
   * @throws Exception
   */
  @SuppressWarnings("all")
  public static SessionAdminUser findAdminUserFromCache(HttpServletRequest request)
      throws Exception {
    SessionAdminUser sessionAdminUser = null;
    // 缓存的授权凭证
    Map<String, Object> map = findCachedCrmAuthMap(request);
    if (null != map) {
      sessionAdminUser = (SessionAdminUser) map.get("SESSION_ADMIN_USER");
    }
    return sessionAdminUser;
  }

  /**
   * Title: 获取缓存对象（包含所有数据）<br>
   * Description: findCachedCrmAuthMap<br>
   * CreateDate: 2016年4月28日 下午12:37:28<br>
   * 
   * @category 获取缓存对象（包含所有数据）
   * @author ivan.mgh
   * @param request
   * @return
   * @throws UnsupportedEncodingException
   */
  @SuppressWarnings("all")
  public static Map<String, Object> findCachedCrmAuthMap(HttpServletRequest request)
      throws UnsupportedEncodingException {
    Map<String, Object> map = null;
    // 获得凭证
    String authParam = request.getParameter("auth");

    if (StringUtils.isNotBlank(authParam)) {
      // Base64解码
      String auth = new String(Base64.decodeBase64(authParam), "UTF-8");
      // 缓存的授权凭证
      map = (Map<String, Object>) MemcachedUtil.getValue("CRM_AUTH_" + auth);
    }
    return map;
  }

  /**
   * Title: 获取管理员<br>
   * Description: findBadminUser<br>
   * CreateDate: 2016年4月28日 下午12:38:26<br>
   * 
   * @category 获取管理员
   * @author ivan.mgh
   * @param request
   * @return
   * @throws Exception
   */
  @SuppressWarnings("all")
  public static Map<String, Object> findBadminUser(HttpServletRequest request) throws Exception {
    Map<String, Object> ccMap = null;
    // 缓存的授权凭证
    Map<String, Object> map = findCachedCrmAuthMap(request);
    if (null != map) {
      ccMap = (Map<String, Object>) map.get("CRM_AUTH_BADMIN_USER");
    }
    return ccMap;
  }

  /**
   * Title: 校验是否有某权限<br>
   * Description: isAdminUserHavePermisson<br>
   * CreateDate: 2016年4月28日 下午12:41:00<br>
   * 
   * @category 校验是否有某权限
   * @author ivan.mgh
   * @param request
   * @param permission
   * @return
   * @throws UnsupportedEncodingException
   */
  @SuppressWarnings("all")
  public static Boolean isAdminUserHavePermisson(HttpServletRequest request, String permission)
      throws UnsupportedEncodingException {
    Map<String, Object> map = findCachedCrmAuthMap(request);
    Map<String, Object> permissionMap = null;

    if (null != map) {
      permissionMap = (Map<String, Object>) map.get("CRM_AUTH_AUTHORITY");
      if (null != permissionMap) {
        if (permissionMap.get(permission) != null) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Title: 校验是否有某权限<br>
   * Description: isAdminUserHavePermisson<br>
   * CreateDate: 2016年4月29日 下午6:04:52<br>
   * 
   * @category 校验是否有某权限
   * @author ivan.mgh
   * @param map
   *          缓存的数据map
   * @param permission
   *          权限字符串
   * @return
   * @throws UnsupportedEncodingException
   */
  @SuppressWarnings("all")
  public static Boolean isAdminUserHavePermisson(Map<String, Object> map, String permission)
      throws UnsupportedEncodingException {
    Map<String, Object> permissionMap = null;

    if (null != map) {
      permissionMap = (Map<String, Object>) map.get("CRM_AUTH_AUTHORITY");
      if (null != permissionMap) {
        if (permissionMap.get(permission) != null) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * 
   * Title: 调用向crm推送接口<br>
   * Description: 手机号为必输项其他两项可为空<br>
   * CreateDate: 2017年8月11日 下午2:40:05<br>
   * 
   * @category 调用向crm推送接口
   * @author seven.gz
   * @param name
   *          名称
   * @param phone
   *          手机号
   * @param age
   *          年龄
   * @throws UnsupportedEncodingException
   */
  public static Boolean crmPushUser(String phone, String name, String age)
      throws UnsupportedEncodingException {
    // 转化参数，也不知道给crm传null 会不会错，感觉还是不要传了，空字符串反正没啥问题
    if (StringUtils.isEmpty(name) || "null".equals(name)) {
      name = "";
    } else {
      name = URLEncoder.encode(name, "utf-8");
    }
    if (StringUtils.isEmpty(age) || "null".equals(age)) {
      age = "";
    }

    String url = MemcachedUtil.getConfigValue(ConfigConstant.CRM_PUSH_USER_URL) + "&Name="
        + name
        + "&Phone=" + phone + "&Age=" + age;
    String jsonStr = null;

    // 第一次失败就再调用两次
    for (int i = 0; i < 3; i++) {
      try {
        jsonStr = HttpClientUtil.doGetReturnString(url);

        if (!StringUtils.isEmpty(jsonStr)) {
          // 0 成功 3 重复添加
          if (jsonStr.indexOf("\"State\":0") != -1 || jsonStr.indexOf("\"State\":3") != -1) {
            return true;
          } else {
            logger.error("调用crm推送用户接口出错------" + jsonStr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("error:" + e.getMessage(), e);
      }
    }
    return false;
  }

}
