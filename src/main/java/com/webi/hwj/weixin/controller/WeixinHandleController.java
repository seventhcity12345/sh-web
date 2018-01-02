package com.webi.hwj.weixin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.weixin.constant.WeixinConstant;
import com.webi.hwj.weixin.param.BindUserParam;
import com.webi.hwj.weixin.service.UserWeixinService;
import com.webi.hwj.weixin.util.WeixinUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;

/**
 * @category 微信控制类
 * @author mingyisoft代码生成工具
 *
 */
@Api(description = "微信相关接口")
@Controller
@RequestMapping("/")
public class WeixinHandleController {
  private static Logger logger = Logger.getLogger(WeixinHandleController.class);
  @Resource
  private UserWeixinService userWeixinService;

  @Resource
  private UserService userService;

  /**
   * Title: 绑定微信帐号.<br>
   * Description: weixinBindUser<br>
   * CreateDate: 2016年10月14日 下午2:49:02<br>
   * 
   * @category 绑定微信帐号
   * @author yangmh
   */
  @ResponseBody
  @RequestMapping("/weixinBindUser")
  public CommonJsonObject weixinBindUser(@RequestBody @Valid BindUserParam bindUserParam,
      BindingResult result) {
    CommonJsonObject json = new CommonJsonObject();

    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
    } else {
      try {
        json = userWeixinService.bindUser(bindUserParam);
      } catch (Exception ex) {
        ex.printStackTrace();
        logger.error("error:" + ex.getMessage(), ex);
        json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      }
    }

    return json;
  }

  /**
   * Title: 微信入口.<br>
   * Description: 负责拼装获取授权信息的url<br>
   * CreateDate: 2016年10月14日 下午3:35:35<br>
   * 
   * @category 微信入口
   * @author yangmh
   * @param pageId
   *          页面id
   * @return 重定向到微信的获取授权url
   */
  @RequestMapping("/weixinEntry")
  public String weixinEntry(String pageId, HttpServletResponse response) throws Exception {
    String redirectUri = java.net.URLEncoder.encode(
        MemcachedUtil.getConfigValue("speakhi_website_url") + "/weixinHandle",
        "UTF-8");

    String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
        + "appid=" + WeixinConstant.getWeixinAppid()
        + "&redirect_uri=" + redirectUri
        + "&response_type=code&scope=snsapi_base&state=" + pageId + "#wechat_redirect";

    // return "redirect:" + url;
    // modify by seven 2016年11月9日16:43:02 解决重定向问题
    response.sendRedirect(url);
    return null;
  }

  /**
   * Title: 微信入口.获取用户信息需要用户授权<br>
   * Description: 负责拼装获取授权信息的url<br>
   * CreateDate: 2016年10月14日 下午3:35:35<br>
   * 
   * @category 微信入口
   * @author yangmh
   * @param pageId
   *          页面id
   * @return 重定向到微信的获取授权url
   */
  @RequestMapping("/weixinEntryFindUserInfo")
  public String weixinEntryFindUserInfo(String pageId, HttpServletResponse response)
      throws Exception {
    String redirectUri = java.net.URLEncoder.encode(
        MemcachedUtil.getConfigValue("speakhi_website_url") + "/weixinHandle",
        "UTF-8");

    String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
        + "appid=" + WeixinConstant.getWeixinAppid()
        + "&redirect_uri=" + redirectUri
        + "&response_type=code&scope=snsapi_userinfo&state=" + pageId + "#wechat_redirect";
    // modify by seven 2016年11月9日16:43:02 解决重定向问题
    response.sendRedirect(url);
    // return "redirect:" + url;
    return null;
  }

  /**
   * Title: 微信扫码登录轮询.<br>
   * Description: weixinScanLogin<br>
   * CreateDate: 2016年10月20日 上午11:20:07<br>
   * 
   * @category weixinScanLogin
   * @author yangmh
   * @param paramMap
   *          uid,前端发送过来,并存储在缓存中的标识
   * @return data
   */
  @ResponseBody
  @ApiOperation(value = "微信扫码登录",
      notes = "用于网站首页的登录功能，参数通过问号传递,msg返回0,1,2；data返回sessionUser对象；0:返回前端：继续轮询,用户还没有扫码,1:返回前端：请绑定微信帐号,2:登录成功，刷新页面即可")
  @RequestMapping(value = "/weixinScanLogin", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<SessionUser> weixinScanLogin(@ApiParam(name = "uid", required = true,
      value = "前端发送过来,并存储在缓存中的标识") @RequestParam("uid") String uid)
      throws Exception {
    CommonJsonObject<SessionUser> json = new CommonJsonObject<SessionUser>();
    if (!StringUtils.isEmpty(uid)) {
      String openId = MemcachedUtil.getConfigValue(WeixinConstant.WEIXIN_MEMCACHED_SCAN_UID + uid);
      // 通过openid去查询是否有绑定我们的userid
      if (StringUtils.isEmpty(openId)) {
        // 返回前端：继续轮询,用户还没有扫码
        json.setMsg("0");
      } else {
        String userId = userWeixinService.findUserIdByOpenId(openId);
        if (StringUtils.isEmpty(userId)) {
          // 返回前端：请绑定微信帐号
          json.setMsg("1");
        } else {
          // 登录成功，刷新页面即可
          json.setMsg("2");

          // 初始化session,自动登录
          json.setData(userService.initSessionUser(userId, null));
        }
      }
    } else {
      // 返回前端：继续轮询,用户还没有扫码
      json.setMsg("0");
    }
    return json;
  }

  /**
   * Title: 微信页面路由.<br>
   * Description: 核心接口,被微信服务器回调<br>
   * CreateDate: 2016年10月14日 下午5:56:12<br>
   * 
   * @category 微信页面路由
   * @author yangmh
   * @param request
   * @param code
   * @param state
   * @return
   * @throws Exception
   */
  @RequestMapping("/weixinHandle")
  public String weixinHandle(HttpServletRequest request, String code, String state,
      HttpServletResponse response)
      throws Exception {

    if (StringUtils.isEmpty(code)) {
      logger.error("weixin code is null");
      throw new Exception("weixin code is null");
    }

    String returnAddress = null;

    JSONObject jsonObj = WeixinUtil.findOpenIdAndSpecialAccessToken(code);

    if (jsonObj.get("openid") != null) {
      String openId = jsonObj.get("openid").toString();
      String accessToken = jsonObj.get("access_token").toString();

      if (state.indexOf("_") != -1) {
        // 如果pageid中有下划线则表示是走微信扫码的逻辑
        String[] stateArray = state.split("_");
        String uid = stateArray[1];

        // 每个uid只缓存2分钟,用于前端的轮询查状态
        MemcachedUtil.setValue(WeixinConstant.WEIXIN_MEMCACHED_SCAN_UID + uid, openId, 120);
        // returnAddress = "redirect:" +
        // modify by seven 2016年11月9日16:43:02 解决重定向问题
        // MemcachedUtil.getConfigValue("speakhi_website_url")
        // + "//wechat/views/bindSuccess.html";
        returnAddress = MemcachedUtil.getConfigValue("speakhi_website_url")
            + "//wechat/views/bindSuccess.html";
      } else {
        // 通过openid去查询是否有绑定我们的userid
        String userId = userWeixinService.findUserIdByOpenId(openId);

        if (StringUtils.isEmpty(userId)) {
          // 路由到绑定页面，调用微信用户的名称和头像
          String[] userInfoArray = WeixinUtil.findUserInfo(openId, accessToken);

          // 如果获取不到则重定向到获取用户信息的入口(以防万一)
          if (StringUtils.isEmpty(userInfoArray[0])) {
            // modify by seven 2016年11月9日16:43:02 解决重定向问题
            // return "redirect:" +
            // MemcachedUtil.getConfigValue("speakhi_website_url")
            // + "/weixinEntryFindUserInfo?pageId=" + state;
            response.sendRedirect(MemcachedUtil.getConfigValue("speakhi_website_url")
                + "/weixinEntryFindUserInfo?pageId=" + state);
            return null;
          }

          // 把微信的用户名和头像放到缓存里，在绑定页面让前端工程师获得,缓存1小时
          // 缓存微信用户昵称
          MemcachedUtil.setValue(WeixinConstant.WEIXIN_MEMCACHED_NICK_NAME + openId,
              userInfoArray[0], 60 * 60);
          // 缓存微信用户头像
          MemcachedUtil.setValue(WeixinConstant.WEIXIN_MEMCACHED_USER_PHOTO + openId,
              userInfoArray[1], 60 * 60);

          // 用户还没有绑定微信，路由到绑定页面
          // modify by seven 2016年11月9日16:43:02 解决重定向问题
          // returnAddress = "redirect:" +
          // MemcachedUtil.getConfigValue("speakhi_website_url")
          // + "/wechat/views/?weixinOpenId=" + openId;
          returnAddress = MemcachedUtil.getConfigValue("speakhi_website_url")
              + "/wechat/views/?weixinOpenId=" + openId;
        } else {
          // 路由到正常业务逻辑相关页面

          // 初始化session,自动登录
          userWeixinService.updateWeixinSessionUser(request.getSession(), userId, openId);

          // 注意，约定大于配置，以后微信新增入口页面的话，格式也要这么写
          // modify by seven 2016年11月9日16:43:02 解决重定向问题
          // returnAddress = "redirect:" +
          // MemcachedUtil.getConfigValue("speakhi_website_url")
          // + "/wechat/views/" + state + "/?weixinOpenId=" + openId;
          returnAddress = MemcachedUtil.getConfigValue("speakhi_website_url")
              + "/wechat/views/" + state + "/?weixinOpenId=" + openId;

        }
      }
    }
    // modify by seven 2016年11月9日16:43:02 解决重定向问题
    // return returnAddress;
    response.sendRedirect(returnAddress);
    return null;
  }
}