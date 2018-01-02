package com.webi.hwj.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.user.param.FloatingLayerInfoParam;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @category user控制类
 * @author mingyisoft代码生成工具
 *
 */
@Api(description = "成人学员相关接口信息")
@Controller
public class UserControllerForUserApi {
  private static Logger logger = Logger.getLogger(UserControllerForUserApi.class);
  @Resource
  private UserService userService;

  /**
   * 
   * Title: 成人浮层接口<br>
   * Description: 成人学员界面浮层接口<br>
   * CreateDate: 2017年7月4日 下午5:34:56<br>
   * 
   * @category 成人浮层接口
   * @author felix.yl
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "成人学员界面浮层接口",
      notes = "在speakHi(成人)学员端页面右侧添加浮层,浮层上显示\"学员LC、学员CC\"相关信息.<br>"
          + "LC相关信息:LC名称(没有显示\"暂无\")、LC手机号(没有手机号显示微信号，都没有显示\"暂无\");<br>"
          + "最新一份合同的CC相关信息：CC名称(没有显示\"暂无\")、CC手机号(没有手机号显示微信号，都没有显示\"暂无\");若学员未登录,以上信心均显示\"暂无\"。")
  @RequestMapping(value = "/api/speakhi/v1/ucenter/floatingLayerInfo",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<FloatingLayerInfoParam> floatingLayerInfo(HttpServletRequest request)
      throws Exception {

    CommonJsonObject<FloatingLayerInfoParam> json = new CommonJsonObject<FloatingLayerInfoParam>();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    String keyId = sessionUser.getKeyId();
    FloatingLayerInfoParam param = userService.findFloatingLayerInfo(keyId);
    json.setData(param);
    return json;
  }

}
