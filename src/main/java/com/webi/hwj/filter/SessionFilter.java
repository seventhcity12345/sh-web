package com.webi.hwj.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.util.SessionUtil;

import net.sf.json.JSONObject;

/**
 * @category session过滤器，用于校验用户是否合法
 * @author yangmh
 *
 */
public class SessionFilter implements Filter {
  private static Logger logger = Logger.getLogger(SessionFilter.class);

  // private UserService userService;

  public void init(FilterConfig filterConfig) throws ServletException {
    // //System.out.println("这里是初始化....");
    // ServletContext context = filterConfig.getServletContext();
    // ApplicationContext ctx =
    // WebApplicationContextUtils.getWebApplicationContext(context);
    // userService = (UserService) ctx.getBean("userService");
  }

  public void destroy() {

  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // String requestType = httpRequest.getHeader("X-Requested-With");// 异步请求头属性
    //
    // if (requestType == null) {
    // modify by seven 2017年7月10日15:27:55 同异步请求改为和青少一样的判断
    String requestType = httpRequest.getHeader("Accept");

    // 同步请求
    if (requestType == null || requestType.indexOf("application/json") == -1) {

      if (httpRequest.getRequestURL().toString().toLowerCase().indexOf("ucenter") != -1) {// 学员访问访问类
        SessionUser sessionUser = SessionUtil.getSessionUser(httpRequest);
        if (sessionUser == null) {
          // 非法访问
          httpResponse.sendRedirect("/");
          return;
        }
      } else if (httpRequest.getRequestURL().toString().toLowerCase().indexOf("tcenter") != -1) {// 老师访问
        SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(httpRequest);
        if (sessionTeacher == null) {
          // 非法访问
          httpResponse.sendRedirect("/t");
          return;
        }

      } else if (httpRequest.getRequestURL().toString().toLowerCase().indexOf("/admin") != -1) {// 管理员访问
        // 校验从CRM过来的请求
        if (!judgeCrmRequest(httpRequest)) {
          SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(httpRequest);
          if (sessionAdminUser == null) {
            // 非法访问
            httpResponse.sendRedirect("/");
            return;
          }
        }
      } /*
         * else if
         * (httpRequest.getRequestURL().toString().toLowerCase().indexOf(
         * "weixin") != -1) {// 管理员访问 SessionUser sessionUser =
         * SessionUtil.getSessionUserMap(httpRequest); if (sessionUser == null)
         * { // 非法访问 httpResponse.sendRedirect("/"); return; } }
         */
    } else {

      CommonJsonObject commonJsonObject = new CommonJsonObject();
      commonJsonObject.setCode(ErrorCodeEnum.SESSION_NOT_EXIST.getCode());
      JSONObject jsonObject = JSONObject.fromObject(commonJsonObject);

      // 异步请求
      if (httpRequest.getRequestURL().toString().toLowerCase().indexOf("ucenter") != -1) {// 学员访问访问类
        SessionUser sessionUser = SessionUtil.getSessionUser(httpRequest);
        if (sessionUser == null) {
          // 异步请求响应结果：
          // httpResponse.getWriter().write("您的操作已超时，请登录后操作");
          httpResponse.getWriter().write(jsonObject.toString());
          return;
        }

      } else if (httpRequest.getRequestURL().toString().toLowerCase().indexOf("tcenter") != -1) {// 老师访问
        SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(httpRequest);
        if (sessionTeacher == null) {
          // 异步请求响应结果：
          // httpResponse.getWriter().write("您的操作已超时，请登录后操作");
          httpResponse.getWriter().write(jsonObject.toString());

          return;
        }
      } else if (httpRequest.getRequestURL().toString().toLowerCase().indexOf("/admin") != -1) {// 管理员访问
        // 校验从CRM过来的请求
        if (!judgeCrmRequest(httpRequest)) {
          SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(httpRequest);
          if (sessionAdminUser == null) {
            JsonMessage json = new JsonMessage();
            json.setSuccess(false);
            json.setMsg("会话已超时，请重新登录！");

            JSONObject jo = JSONObject.fromObject(json);
            System.out.println(jo.toString());
            // 非法访问
            httpResponse.getWriter().write(jo.toString());
            return;
          }
        }
      } /*
         * else if
         * (httpRequest.getRequestURL().toString().toLowerCase().indexOf(
         * "weixin") != -1) {// 管理员访问 SessionUser sessionUser =
         * SessionUtil.getSessionUserMap(httpRequest); if (sessionUser == null)
         * { // 异步请求响应结果： httpResponse.getWriter().write("您的操作已超时，请登录后操作");
         * 
         * return; } }
         */
    }
    chain.doFilter(request, response);
  }

  /**
   * Title: 校验CRM请求<br>
   * Description: 校验CRM请求，若已经登录，则可以访问目标资源<br>
   * CreateDate: 2016年3月30日 下午2:12:24<br>
   * 
   * @category 校验CRM请求
   * @author ivan.mgh
   * @param httpRequest
   *          请求
   * @return
   * @throws UnsupportedEncodingException
   */
  @SuppressWarnings("unchecked")
  private boolean judgeCrmRequest(HttpServletRequest httpRequest)
      throws UnsupportedEncodingException {
    // URL中包含crm
    if (httpRequest.getRequestURL().toString().toLowerCase().indexOf("crm") != -1) {
      // 请求中的授权参数
      String authParam = httpRequest.getParameter("auth");

      if (StringUtils.isNotBlank(authParam)) {
        // Base64解码
        String auth = new String(Base64.decodeBase64(authParam), "UTF-8");

        // 缓存的授权凭证
        Map<String, Object> authMap = (Map<String, Object>) MemcachedUtil
            .getValue("CRM_AUTH_" + auth);

        if (null != authMap) {
          String leadId = (String) authMap.get("CRM_AUTH_KEY");
          if (authParam.equals(leadId)) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
