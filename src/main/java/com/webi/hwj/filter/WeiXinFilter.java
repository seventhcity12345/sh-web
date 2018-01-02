package com.webi.hwj.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.weixin.service.UserWeixinService;
import com.webi.hwj.weixin.util.WeixinUtil;

/**
 * Title: 微信拦截器<br>
 * Description: WeiXinFilter<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月25日 上午10:49:06
 * 
 * @author athrun.cw
 */
public class WeiXinFilter implements Filter {
  private Logger logger = Logger.getLogger(this.getClass().getName());

  private UserWeixinService userWeixinService;

  private UserService userService;

  public void init(FilterConfig filterConfig) throws ServletException {
    // System.out.println("这里是初始化....");
    ServletContext context = filterConfig.getServletContext();
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
    userWeixinService = (UserWeixinService) ctx.getBean("userWeixinService");
    userService = (UserService) ctx.getBean("userService");
  }

  public void destroy() {

  }

  /**
   * 前缀带/weixin的接口全部拦截.
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String weixinOpenId = request.getParameter("weixinOpenId");

    if (StringUtils.isEmpty(weixinOpenId)) {
      // 被微信拦截器拦截的接口都必须传递weixinOpenId
      httpResponse.getWriter().write("weixinOpenId is null");
      logger.error("weixinOpenId is null");
      return;
    }

    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);

    if (sessionUser == null) {
      // 没有session了，需要自动登录
      try {
        String userId = userWeixinService.findUserIdByOpenId(weixinOpenId);
        if (StringUtils.isEmpty(userId)) {
          // 没有绑定用户，理论上不会存在这种情况...
          httpResponse.getWriter().write("user is not binded");
          logger.error("user is not binded");
          return;
        } else {
          // 已经是绑定用户，自动登录
          userWeixinService.updateWeixinSessionUser(httpRequest.getSession(), userId, weixinOpenId);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
        httpResponse.getWriter().write("system error");
        logger.error("system error");
        return;
      }
    }

    // httpResponse.sendRedirect("/ucenter");
    // 异步请求响应结果：
    // httpResponse.getWriter().write("您的操作已超时，请登录后操作");
    chain.doFilter(request, response);
  }

}
