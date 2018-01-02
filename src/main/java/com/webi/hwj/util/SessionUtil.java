package com.webi.hwj.util;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.bean.SessionUser;

public class SessionUtil {
  public static final String SESSION_ADMIN_USER_NAME = "adminSessionUser";

  /**
   * Title: 获取token.<br>
   * Description: findToken<br>
   * CreateDate: 2017年8月23日 下午2:30:51<br>
   * @category 获取token 
   * @author yangmh
   * @param request
   * @return
   */
  private static String findToken(HttpServletRequest request) {
    //先从http头获取，我们自己的程序都是这个玩法
    String token = request.getHeader("token");
    if (StringUtils.isEmpty(token)) {
      //考虑到韦博的app是使用问号传递token的，那就再试一次问号获取
      token = request.getParameter("token");
    }  
    
    return token;
  }

  /**
   * @category 取得session用户（前台）
   * @param request
   * @return
   */
  public static SessionUser getSessionUser(HttpServletRequest request) {
    return (SessionUser) MemcachedUtil.getValue(findToken(request));
  }

  /**
   * Title: 清空session信息.<br>
   * Description: deleteSessionUser<br>
   * CreateDate: 2017年2月20日 下午4:22:30<br>
   * 
   * @category 清空session信息
   * @author yangmh
   */
  public static void deleteSessionUser(HttpServletRequest request) {
    MemcachedUtil.deleteValue(findToken(request));
    // request.getSession().removeAttribute(SessionUtil.SESSION_USER_NAME);
  }

  /**
   * @category 取得session老师（前台）
   * @param request
   * @return
   */
  public static SessionTeacher getSessionTeacher(HttpServletRequest request) {
    return (SessionTeacher) MemcachedUtil.getValue(findToken(request));
  }

  /**
   * @category 取得session用户（后台管理）
   * @param request
   * @return
   */
  public static SessionAdminUser getSessionAdminUser(HttpServletRequest request) {
    return (SessionAdminUser) request.getSession().getAttribute(SESSION_ADMIN_USER_NAME);
  }

  // /**
  // 目前不使用该方法，代替的方案为userService中的initSessionUser，因为initSessionUser中需要查询数据库相关
  // * @category 初始化session对象
  // * @param userObj
  // * @param session
  // */
  // public static void initSessionUser(Map<String,Object> userObj,HttpSession
  // session){
  // SessionUser sessionUser = new SessionUser();
  // sessionUser.setStudent((Boolean)userObj.get("is_student"));
  // sessionUser.setKeyId(userObj.get("key_id")+"");
  // sessionUser.setPhone(userObj.get("phone")+"");
  // //添加info_complete_percent 课程完成度
  // sessionUser.setInfo_complete_percent(userObj.get("info_complete_percent")+"");
  // /**
  // * 名称 需要特殊的在 会员首页显示，所以不能使用+空字符来显示，不然会出现字符串null
  // * modify by athrun.cw 2015年12月18日14:42:25
  // */
  // //添加current_level 用户当前级别
  // if(userObj.get("current_level") != null){
  // sessionUser.setCurrent_level(userObj.get("current_level").toString());
  // }
  // /**
  // * 名称 需要特殊的在 会员首页显示，所以不能使用+空字符来显示，不然会出现字符串null
  // * modify by athrun.cw 2015年12月18日11:31:50
  // */
  // if(userObj.get("user_name") != null){
  // sessionUser.setUserName(userObj.get("user_name").toString());
  // }
  // sessionUser.setUserName(userObj.get("user_name")+"");
  // sessionUser.setUserCode(userObj.get("user_code")+"");
  // sessionUser.setUserPhoto(userObj.get("user_photo")+"");
  // /**
  // * 名称 需要特殊的在 会员首页显示，所以不能使用+空字符来显示，不然会出现字符串null
  // * modify by athrun.cw 2015年12月18日14:46:27
  // */
  // if(userObj.get("user_photo_large") != null){
  // sessionUser.setUserPhotoLarge(userObj.get("user_photo_large").toString());
  // }
  // sessionUser.setLastLoginIp(userObj.get("last_login_ip")+"");
  // sessionUser.setLastLoginTime(userObj.get("last_login_time")+"");
  // session.setAttribute(SessionUtil.SESSION_USER_NAME, sessionUser);
  // }

  /**
   * @category 初始化session对象
   * @param userObj
   * @param session
   */
  public static SessionAdminUser initSessionAdminUser(Map<String, Object> adminUserObj,
      HttpSession session) {
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

    session.setAttribute(SessionUtil.SESSION_ADMIN_USER_NAME, sessionAdminUser);
    return sessionAdminUser;
  }
}
