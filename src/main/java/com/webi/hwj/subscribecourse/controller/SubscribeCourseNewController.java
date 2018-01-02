package com.webi.hwj.subscribecourse.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.huanxun.exception.HuanxunException;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.FindListfinishLevelReturnParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeInfoByUserIdAndCourseTypeParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseListParam;
import com.webi.hwj.subscribecourse.param.SubscribeParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseApiParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseDetailApiParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseDetailParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseParam;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseCheckService;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseService;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Title: 课程预约&&取消预约.<br>
 * Description: SubscribeCourseController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月25日 下午2:46:29
 * 
 * @author athrun.cw
 */
@Api(description = "课程预约相关接口信息")
@Controller
public class SubscribeCourseNewController {
  private static Logger logger = Logger.getLogger(SubscribeCourseNewController.class);

  @Resource
  private BaseSubscribeCourseService baseSubscribeCourseService;

  @Resource
  private BaseSubscribeCourseCheckService baseSubscribeCourseCheckService;

  @Resource
  private TeacherTimeService teacherTimeService;
  @Resource
  private SubscribeCourseService subscribeCourseService;

  /**
   * Title: 新版统一.<br>
   * Description: subscribe<br>
   * CreateDate: 2016年9月19日 下午3:39:19<br>
   * 
   * @category 新版统一
   * @author yangmh
   * @param request
   *          request对象
   */
  @ResponseBody
  @RequestMapping("/ucenter/subscribeCourse")
  public CommonJsonObject subscribeCourse(HttpServletRequest request,
      @RequestBody @Valid SubscribeParam subscribeParam, BindingResult result)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    String userId = sessionUser.getKeyId();

    // 组装subscribeCourse对象
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setUserId(userId);
    subscribeCourse.setUserPhone(sessionUser.getPhone());
    subscribeCourse.setCourseType(subscribeParam.getCourseType());
    subscribeCourse.setUserLevel(sessionUser.getCurrentLevel());
    subscribeCourse.setCourseId(subscribeParam.getCourseId());
    subscribeCourse.setTeacherTimeId(subscribeParam.getTeacherTimeId());
    subscribeCourse.setCreateUserId(userId);
    subscribeCourse.setUpdateUserId(userId);
    subscribeCourse.setSubscribeFrom("pc");

    try {
      json = baseSubscribeCourseService.subscribeEntry(subscribeCourse, sessionUser);
    } catch (HuanxunException ex) {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_HUANXUN_ERROR.getCode());
    } catch (Exception e) {
      logger.error("error:" + e.getMessage(), e);
      json.setMsg(e.getMessage());
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * Title: 新版统一取消预约.<br>
   * Description: cancelSubscribeCourse<br>
   * CreateDate: 2016年9月23日 下午5:06:14<br>
   * 
   * @category cancelSubscribeCourse
   * @author yangmh
   * @param request
   * @param paramMap
   * @return
   */
  @ResponseBody
  @RequestMapping("/ucenter/cancelSubscribeCourse")
  public CommonJsonObject cancelSubscribeCourse(HttpServletRequest request,
      @RequestBody Map<String, String> paramMap)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    String subscribeId = paramMap.get("subscribeId");

    if (subscribeId == null) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      return json;
    }

    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 组装subscribeCourse对象
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setKeyId(subscribeId);
    subscribeCourse.setUpdateUserId(sessionUser.getKeyId());
    subscribeCourse.setUserPhone(sessionUser.getPhone());

    try {
      json = baseSubscribeCourseService.cancelSubscribeEntry(subscribeCourse);
    } catch (HuanxunException ex) {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_HUANXUN_CANCEL_ERROR.getCode());
    } catch (Exception ex) {
      logger.error("error:" + ex.getMessage(), ex);
      json.setMsg(ex.getMessage());
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * Title: 待评价列表<br>
   * Description: 待评价列表<br>
   * CreateDate: 2016年12月12日 下午3:18:31<br>
   * 
   * @category 待评价列表
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/ucenter/unfinishedComments")
  public CommonJsonObject findNotCommentList(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    try {
      // 查找用户已上课、未评价的1v1类型的课程列表
      List<SubscribeCourseListParam> subscribeCourseList = baseSubscribeCourseCheckService
          .findNotCommentList(sessionUser.getKeyId());
      Map<String, Object> returnMap = new HashMap<String, Object>();
      returnMap.put("comments", subscribeCourseList);
      json.setData(returnMap);

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    return json;
  }

  /**
   * Title: 1对多课程预约 前置判断<br>
   * Description: 1对多课程预约 前置判断<br>
   * CreateDate: 2016年8月16日 下午3:45:11<br>
   * 
   * @category 1对多课程预约 前置判断
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/subscribeCourse/subscribeOne2ManyCoursePremise")
  public JsonMessage subscribeOne2ManyCoursePremise(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    paramMap.put("user_id", sessionUser.getKeyId());
    try {
      json = subscribeCourseService.checkSubscribeOne2ManyCoursePremise(paramMap);
      if (json.isSuccess()) {
        // 没有上过重复的课程，直接走预约逻辑
        // json = this.subscribeCourse(request);
      } else {
        // 有上过重复的课程，返回提示
      }

    } catch (Exception e) {
      logger.info("用户 [" + sessionUser.getPhone() + "] 预约1对多课程 前置条件 失败，课程id [ "
          + paramMap.get("course_id") + "] ");
      logger.error("error:" + e.getMessage(), e);
      json.setMsg(e.getMessage());
      json.setSuccess(false);
    }
    return json;
  }

  /**
   * 
   * Title: go2Vcube4Class<br>
   * Description: go2Vcube4Class<br>
   * CreateDate: 2015年10月12日 下午6:13:43<br>
   * 
   * @category go2Vcube4Class
   * @author athrun.cw
   * @param request
   *          key_id : subcribe_id
   * @param json
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/subscribeCourse/go2Vcube4Class")
  public JsonMessage go2Vcube4Class(HttpServletRequest request, JsonMessage json) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    paramMap.put("user_id", sessionUser.getKeyId());
    // FIXME 暂时使用user_name：vcube显示的user_name
    paramMap.put("user_name", sessionUser.getUserName());
    paramMap.put("user_code", sessionUser.getUserCode());
    try {
      // 预约subcribe_id上课，课程预约subscribe
      logger.info("用户 [" + sessionUser.getPhone() + "] 开始进入Vcube，课程预约subscribe_id [ "
          + paramMap.get("key_id") + "] ");
      /**
       * modified by komi 2016年7月7日17:43:41 修改为威力方和展示互动通用预约逻辑（以后等威力方没了，要改为全展示互动）
       */
      json = subscribeCourseService.goToClassStudent(paramMap);// 上课
      // logger.info("用户 [" + sessionUser.getPhone() + "]
      // 进入Vcube上课成功，课程预约subscribe_id [ " + paramMap.get("key_id") + "] ");
    } catch (Exception e) {
      logger.error("用户 [" + sessionUser.getPhone() + "] 进入Vcube上课失败，课程预约subscribe_id [ "
          + paramMap.get("key_id") + "] ");
      logger.error("error:" + e.toString());
      json.setMsg(e.toString());
      json.setSuccess(false);
    }
    return json;
  }

  /**
   * 
   * Title: 查询本月有预约(过)课程的日期以及当天预约的课程数<br>
   * Description: 查询本月有预约(过)课程的日期以及当天预约的课程数<br>
   * CreateDate: 2017年7月19日 上午11:22:03<br>
   * 
   * @category 查询本月有预约(过)课程的日期以及当天预约的课程数
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-会员首页-我的主页-我的课表-日历展示奖杯/闹钟【接口】",
      notes = "成人-会员首页-我的主页-我的课表-查询本月有预约(过)课程的日期以及当天预约的课程数;<br>"
          + "今天之前有预约过课程的那天,对应在日历课表上那天展示奖杯以及当天的课程预约数;<br>"
          + "今天以及之后,在有课程预约的那天日历上显示闹钟小图标.")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/subscribeCourseDateAndNumber",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<SubscribleCourseApiParam> findSubscribeCourseDateAndNumber(
      HttpServletRequest request,
      @ApiParam(
          name = "yearMonth", value = "yearMonth",
          required = true,
          example = "201707") @RequestParam("yearMonth") String yearMonth)
              throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<SubscribleCourseApiParam> json =
        new CommonJsonObject<SubscribleCourseApiParam>();

    // 判断传过来的日期格式是否合法(和前端Sam约定日期格式为201707)
    if (yearMonth == null || yearMonth.length() != 6) {
      json.setMsg("前端传入的日期参数格式不符合约定！");
      return json;
    }

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 调用Service层
    List<SubscribleCourseParam> courseSubscribleList = subscribeCourseService
        .findSubscribeCourseDateAndNumber(sessionUser, yearMonth);

    // 将list放到对象中传到前台
    SubscribleCourseApiParam courseSubscribleParamApi = new SubscribleCourseApiParam();
    courseSubscribleParamApi.setSubscribleCourseParamList(courseSubscribleList);

    // 传给前端
    json.setData(courseSubscribleParamApi);

    return json;
  }

  /**
   * 
   * Title: 查询展示学员在某天已预约的所有课程(包括大课、小课)<br>
   * Description: 查询展示学员在某天已预约的所有课程(包括大课、小课)<br>
   * CreateDate: 2017年7月21日 上午10:26:16<br>
   * 
   * @category 查询展示学员在某天已预约的所有课程(包括大课、小课)
   * @author felix.yl
   * @param request
   * @param yearMonthDay
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-会员首页-我的主页-我的课表-点击日历上具体的某天【接口】",
      notes = "成人-会员首页-我的主页-我的课表-点击日历上具体某天时调用该接口;<br>"
          + "查询展示出该学员在对应那天已预约的所有课程(包括大课、小课)")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/subscribeCourseDetail",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<SubscribleCourseDetailApiParam> findSubscribeCourseDetail(
      HttpServletRequest request,
      @ApiParam(
          name = "yearMonthDay", value = "yearMonthDay",
          required = true,
          example = "2017-07-20") @RequestParam("yearMonthDay") String yearMonthDay)
              throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<SubscribleCourseDetailApiParam> json =
        new CommonJsonObject<SubscribleCourseDetailApiParam>();

    // 判断传过来的日期格式是否合法(和前端Sam约定日期格式为2017-07-20)
    if (yearMonthDay == null || yearMonthDay.length() != 10) {
      json.setMsg("前端传入的日期参数格式不符合约定！");
      return json;
    }

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 调用Service层
    List<SubscribleCourseDetailParam> subscribeCourseDetail = subscribeCourseService
        .findSubscribeCourseDetail(sessionUser, yearMonthDay);

    // 将list放到对象中传到前台
    SubscribleCourseDetailApiParam subscribleCourseDetailParamApi =
        new SubscribleCourseDetailApiParam();
    subscribleCourseDetailParamApi.setSubscribleCourseDetailList(subscribeCourseDetail);

    // 传给前端
    json.setData(subscribleCourseDetailParamApi);

    return json;
  }

  /**
   * 
   * Title: 查询demo预约列表<br>
   * Description: findListDemoSubscribe<br>
   * CreateDate: 2017年7月21日 下午6:26:19<br>
   * 
   * @category 查询demo预约列表
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "查询学员demo课列表",
      notes = "查询学员demo课列表")
  @RequestMapping(value = "/api/speakhi/v1/ucenter/demoSubscribeList",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<FindSubscribeInfoByUserIdAndCourseTypeParam> findListDemoSubscribe(
      HttpServletRequest request)
          throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<FindSubscribeInfoByUserIdAndCourseTypeParam> json =
        new CommonJsonObject<FindSubscribeInfoByUserIdAndCourseTypeParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 传给前端
    json.setData(subscribeCourseService.findSubscribeInfoByUserIdAndCourseType(sessionUser
        .getKeyId(), "course_type4", new Date()));

    return json;
  }

  /**
   * 
   * Title: 查询学员完成的级别列表<br>
   * Description: findListfinishLevel<br>
   * CreateDate: 2017年7月24日 下午5:09:42<br>
   * 
   * @category 查询学员完成的级别列表
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "查询学员已获得证书列表",
      notes = "查询学员已获得证书列表")
  @RequestMapping(value = "/api/speakhi/v1/ucenter/finishLevels",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<FindListfinishLevelReturnParam> findListfinishLevel(
      HttpServletRequest request)
          throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<FindListfinishLevelReturnParam> json =
        new CommonJsonObject<FindListfinishLevelReturnParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 传给前端
    json.setData(subscribeCourseService.findListfinishLevel(sessionUser
        .getKeyId(), new Date()));

    return json;
  }
}
