package com.webi.hwj.esapp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.courseone2many.service.CourseOneToManySchedulingService;
import com.webi.hwj.esapp.param.AppJsonObject;
import com.webi.hwj.esapp.param.CourseListParam;
import com.webi.hwj.esapp.param.FindCourseListParam;
import com.webi.hwj.esapp.param.LoginParam;
import com.webi.hwj.esapp.param.UserInfoParam;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Title: speakhi-es app相关接口<br>
 * Description: speakhi-es app相关接口<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月23日 下午3:04:45
 * 
 * @author komi.zsy
 */
@Api(description = "speakhi-es app相关接口")
@Controller
public class SpeakHiEsAppController {
  private static Logger logger = Logger.getLogger(SpeakHiEsAppController.class);

  @Resource
  UserService userService;
  @Resource
  CourseOneToManySchedulingService courseOneToManySchedulingService;
  @Resource
  SubscribeCourseService subscribeCourseService;

  /**
   * Title: 检查token是否失效<br>
   * Description: 检查token是否失效<br>
   * CreateDate: 2017年9月1日 下午6:27:30<br>
   * 
   * @category 检查token是否失效
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "app验证token", notes = "看看token是否有效")
  @RequestMapping(value = "/api/speakhi/v1/app/token", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AppJsonObject<Object> checkToken(HttpServletRequest request)
      throws Exception {
    AppJsonObject<Object> json = new AppJsonObject<Object>();
    try {
      SessionUser sessionUser = SessionUtil.getSessionUser(request);
      
      logger.info("sessionUser:"+sessionUser);

      if (sessionUser == null) {
        json.setError("token已失效");
        json.setState(false);
        return json;
      }

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
      json.setError("系统内部错误");
      json.setState(false);
    }

    return json;

  }

  /**
   * Title: app学生进入教室<br>
   * Description: app学生进入教室<br>
   * CreateDate: 2017年8月25日 下午6:31:58<br>
   * 
   * @category app学生进入教室
   * @author komi.zsy
   * @param request
   * @param courseListParam
   *          预约id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "app进入教室", notes = "进入教室接口，返回url")
  @RequestMapping(value = "/api/speakhi/v1/app/courseUrl", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AppJsonObject<CourseListParam> goToClassByStudent(HttpServletRequest request,
      @ApiParam(required = true,
          value = "预约id(只用live_id)") @RequestBody CourseListParam courseListParam)
      throws Exception {
    AppJsonObject<CourseListParam> json = new AppJsonObject<CourseListParam>();
    try {
      SessionUser sessionUser = SessionUtil.getSessionUser(request);

      if (sessionUser == null) {
        json.setError("token已失效");
        json.setState(false);
        return json;
      }

      // 上课
      json = subscribeCourseService.goToClassAppStudent(courseListParam.getLive_id(),
          sessionUser.getKeyId(), sessionUser.getUserName(), sessionUser.getUserCode(), sessionUser
              .getUserPhoto());

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
      json.setError("系统内部错误");
      json.setState(false);
    }

    return json;

  }

  /**
   * Title: 根据课程类型获取最近一节要上的课程<br>
   * Description: 根据课程类型获取最近一节要上的课程<br>
   * CreateDate: 2017年8月24日 下午5:11:46<br>
   * 
   * @category 根据课程类型获取最近一节要上的课程
   * @author komi.zsy
   * @param request
   * @param findCourseListParam
   *          课程类型
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "app头部课程", notes = "根据课程类型获取最近一节要上的课程")
  @RequestMapping(value = "/api/speakhi/v1/app/courseHead", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AppJsonObject<CourseListParam> findCourseHeadByCourseType(HttpServletRequest request,
      @ApiParam(required = true,
          value = "课程类型") @RequestBody FindCourseListParam findCourseListParam) throws Exception {
    AppJsonObject<CourseListParam> json = new AppJsonObject<CourseListParam>();
    try {
      SessionUser sessionUser = SessionUtil.getSessionUser(request);

      if (sessionUser == null) {
        json.setError("token已失效");
        json.setState(false);
        return json;
      }

      String courseType = findCourseListParam.getCourseType();
      // 默认es课程
      if (StringUtils.isEmpty(courseType)) {
        courseType = "course_type8";
      } else {
        courseType = "course_type" + courseType;
      }

      // 根据课程类型查找课程列表信息
      CourseListParam courseListParam =
          courseOneToManySchedulingService.findCourseHeadByCourseTypeAndUserId(courseType,
              sessionUser.getKeyId());
      if (courseListParam == null) {
        json.setState(false);
      }
      json.setData(courseListParam);

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
      json.setError("系统内部错误");
      json.setState(false);
    }

    return json;

  }

  /**
   * Title: 根据课程类型查找课程列表信息<br>
   * Description: 根据课程类型查找课程列表信息<br>
   * CreateDate: 2017年8月24日 下午4:43:25<br>
   * 
   * @category 根据课程类型查找课程列表信息
   * @author komi.zsy
   * @param request
   * @param findCourseListParam
   *          课程类型
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "app课程列表", notes = "根据课程类型获取课程列表")
  @RequestMapping(value = "/api/speakhi/v1/app/courseList", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AppJsonObject<List<CourseListParam>> findCourseListByCourseType(HttpServletRequest request,
      @ApiParam(
          required = true,
          value = "课程类型") @RequestBody FindCourseListParam findCourseListParam) throws Exception {
    AppJsonObject<List<CourseListParam>> json = new AppJsonObject<List<CourseListParam>>();
    try {
      SessionUser sessionUser = SessionUtil.getSessionUser(request);

      if (sessionUser == null) {
        json.setError("token已失效");
        json.setState(false);
        return json;
      }

      String courseType = findCourseListParam.getCourseType();
      Integer page = findCourseListParam.getCurrent_page();
      Integer rows = findCourseListParam.getPage_size();
      // 默认es课程
      if (StringUtils.isEmpty(courseType)) {
        courseType = "course_type8";
      } else {
        courseType = "course_type" + courseType;
      }

      // 根据课程类型查找课程列表信息
      List<CourseListParam> returnList =
          courseOneToManySchedulingService.findCourseListByCourseTypeAndUserId(courseType,
              sessionUser.getKeyId(), page, rows);

      json.setData(returnList);

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
      json.setError("系统内部错误");
      json.setState(false);
    }

    return json;

  }

  /**
   * Title: app用户(学生)登录<br>
   * Description: app用户(学生)登录<br>
   * CreateDate: 2017年8月24日 下午3:29:45<br>
   * 
   * @category app用户(学生)登录
   * @author komi.zsy
   * @param request
   * @param loginParam
   *          手机号+验证码
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "app用户(学生)登录", notes = "")
  @RequestMapping(value = "/api/speakhi/v1/app/login", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AppJsonObject<UserInfoParam> userLogin(HttpServletRequest request,
      @Valid @ApiParam(required = true, value = "手机号+验证码") @RequestBody LoginParam loginParam,
      BindingResult result)
      throws Exception {
    AppJsonObject<UserInfoParam> json = new AppJsonObject<UserInfoParam>();
    try {
      // 表单校验框架
      if (result.hasErrors()) {
        json.setError(result.getAllErrors().get(0).getDefaultMessage() + "");
        json.setState(false);
        return json;
      }

      String phone = loginParam.getMobile();
      // 30天
      int time = 30 * 24 * 60 * 60;

      // 判断验证码是否正确
      if ((!StringUtils.isEmpty(MemcachedUtil.getConfigValue(phone))
          && MemcachedUtil.getConfigValue(phone).equals(loginParam.getValid_code()))
          // 应app组要求，设置一个万用验证码，给app store审核用
          || "1993".equals(loginParam.getValid_code())) {
        // 用户登录并生产token
        SessionUser sessionUser = userService.appUserLogin(phone,
            RequestUtil.getAddr(request), time);

        if (sessionUser != null) {
          // 登录成功
          UserInfoParam userInfoParam = new UserInfoParam();
          userInfoParam.setApp_userid(sessionUser.getToken());
          userInfoParam.setEnname(sessionUser.getEnglishName());
          userInfoParam.setNick_name(sessionUser.getRealName());
          userInfoParam.setHead_imgurl(sessionUser.getUserPhoto());
          json.setData(userInfoParam);
        } else {
          // 登录失败
          json.setError("该手机号没有正在执行中的合同");
          json.setState(false);
        }
      } else {
        json.setError("验证码错误！");
        json.setState(false);
      }

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
      json.setError("系统内部错误");
      json.setState(false);
    }

    return json;
  }

  /**
   * Title: 发送验证码<br>
   * Description: 发送验证码<br>
   * CreateDate: 2017年8月23日 下午3:06:49<br>
   * 
   * @category 发送验证码
   * @author komi.zsy
   * @param loginParam
   *          手机号
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "app发送验证码", notes = "缓存保存验证码，key是手机号")
  @RequestMapping(value = "/api/speakhi/v1/app/sms", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AppJsonObject<Object> sendSms(@Valid @ApiParam(required = true,
      value = "手机号") @RequestBody LoginParam loginParam, BindingResult result) throws Exception {
    AppJsonObject<Object> json = new AppJsonObject<Object>();
    try {
      // 表单校验框架
      if (result.hasErrors()) {
        json.setError(result.getAllErrors().get(0).getDefaultMessage() + "");
        json.setState(false);
        return json;
      }

      // 生产验证码
      int ckno = (int) ((Math.random() * 9 + 1) * 1000);
      System.out.println("ckno===================================>" + ckno);

      // 手机号
      String mobile = loginParam.getMobile();

      // 缓存保存验证码，key是手机号,保留10分钟
      MemcachedUtil.setValue(mobile, ckno, 60 * 10);

      // 向消息队列里推发短信消息
      // SmsUtil.sendSmsToQueueRocketMq(mobile, "您注册的手机验证码为" + ckno +
      // "，请在注册页面填写验证码完成注册");
      // FIXME javacommon的依赖修复后，需要改成自建mq
      SmsUtil.sendSmsToQueue(mobile, "手机验证码为" + ckno + "，请在登录页面填写验证码完成登录，验证码有效时间十分钟。");

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
      json.setError("系统内部错误");
      json.setState(false);
    }

    return json;

  }

}