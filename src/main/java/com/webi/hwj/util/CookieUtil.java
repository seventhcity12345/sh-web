package com.webi.hwj.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mingyisoft.javabase.util.SHAUtil;

/**
 * 
 * Title: 客户端保存用户信息的cookie工具类<br>
 * Description: CookieUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月16日 上午10:37:24
 * 
 * @author athrun.cw
 */
public class CookieUtil {

  // 学生登录时候
  public static final String COOKIE_USER_NAME = "cookieUser";

  public static final String SSH512_WEBI_SPEAKHI = "webi.speakhi.caowei123";

  // cookie最大存在时间30天
  public static final int COOKIE_MAX_EXIST_SECONDS = 30 * 24 * 60 * 60;

  // cookie最大存在时间永久（手机用户10年足矣）
  public static final int COOKIE_MAX_EXIST_FOREVER = 10 * 365 * 24 * 60 * 60;

  public static void main(String[] args) {
    Map<String, Object> cookieMap = new HashMap<String, Object>();
    cookieMap.put("user_name", "caowei");
    cookieMap.put("pwd", "123");
    System.out.println(cookieMap.toString());
  }

  /**
   * 
   * Title: 根据用户的cookie的绑定name，获取cookie信息<br>
   * Description: getCookieByName<br>
   * CreateDate: 2016年3月16日 上午11:47:51<br>
   * 
   * @category getCookieByName
   * @author athrun.cw
   * @param request
   * @param name
   * @return
   */
  public static Cookie getCookieByName(HttpServletRequest request, String name) {
    Map<String, Cookie> cookieMap = readCookieMap(request);
    if (cookieMap.containsKey(name)) {
      Cookie cookie = (Cookie) cookieMap.get(name);
      return cookie;
    } else {
      return null;
    }
  }

  /**
   * 
   * Title: 读取用户的缓存cookie<br>
   * Description: readCookieMap<br>
   * CreateDate: 2016年3月16日 上午11:47:14<br>
   * 
   * @category 读取用户的缓存cookie
   * @author athrun.cw
   * @param request
   * @return
   */
  private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
    Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
    Cookie[] cookies = request.getCookies();
    if (null != cookies) {
      for (Cookie cookie : cookies) {
        cookieMap.put(cookie.getName(), cookie);
      }
    }
    return cookieMap;
  }

  /**
   * 
   * Title: 清除cookie<br>
   * Description: destroyCookieUser<br>
   * CreateDate: 2016年5月24日 下午6:14:09<br>
   * 
   * @category 清除cookie
   * @author athrun.cw
   * @param request
   * @param response
   */
  public static void destroyCookieUser(HttpServletRequest request, HttpServletResponse response) {
    // 开始保存Cookie
    Cookie cookie = new Cookie(SHAUtil.encode(COOKIE_USER_NAME), null);
    // 设置cookie存储路径为项目跟路径
    cookie.setPath(request.getContextPath());
    response.addCookie(cookie);

  }

  /**
   * 
   * Title: 初始化用户的登录cookie信息<br>
   * Description: initCookieUser<br>
   * CreateDate: 2016年3月16日 上午11:47:39<br>
   * 
   * @category initCookieUser
   * @author athrun.cw
   * @param paramMap
   * @param response
   */
  public static void initCookieUser(String value, HttpServletRequest request,
      HttpServletResponse response) {
    // 开始保存Cookie
    Cookie cookie = new Cookie(SHAUtil.encode(COOKIE_USER_NAME), value);
    // 设置失效时间是30天
    cookie.setMaxAge(COOKIE_MAX_EXIST_SECONDS);

    // 设置cookie存储路径为项目跟路径
    cookie.setPath(request.getContextPath());
    response.addCookie(cookie);

  }

  /**
   * 
   * Title: 创建cookie字符串<br>
   * Description: createCookieValue<br>
   * CreateDate: 2016年4月28日 下午4:02:03<br>
   * 
   * @category 创建cookie字符串
   * @author athrun.cw
   * @param userMap
   * @return
   * @throws Exception
   */
  public static String createCookieValue(Map<String, Object> userMap) throws Exception {
    return userMap.get("phone").toString() + "," + SSH512_WEBI_SPEAKHI + ","
        + SHAUtil.encode(userMap.get("pwd").toString()) /*
                                                         * + "," + userMap
                                                         */;
  }

}
