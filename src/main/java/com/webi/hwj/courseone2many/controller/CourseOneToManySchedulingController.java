package com.webi.hwj.courseone2many.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.courseone2many.param.CourseOne2ManySchedulingForUserApiParam;
import com.webi.hwj.courseone2many.param.CourseSchedulingApiParam;
import com.webi.hwj.courseone2many.param.CourseSchedulingParam;
import com.webi.hwj.courseone2many.service.CourseOneToManySchedulingService;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.coursetype.param.CourseTypeInfo;
import com.webi.hwj.gensee.constant.GenseeConstant;
import com.webi.hwj.gensee.service.GenseeService;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.util.CalendarUtil;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Title: 查找排课信息<br>
 * Description: 查找排课信息<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月25日 下午6:12:42
 * 
 * @author komi.zsy
 */
@Api(description = "课程相关接口信息")
@Controller
public class CourseOneToManySchedulingController {
  private static Logger logger = Logger.getLogger(CourseOneToManySchedulingController.class);
  @Resource
  private CourseOneToManySchedulingService courseOneToManySchedulingService;

  @Resource
  private OrderCourseService orderCourseService;

  @Resource
  private GenseeService genseeService;

  /**
   * Title: 查询ES课程列表<br>
   * Description: 查询ES课程列表<br>
   * CreateDate: 2017年4月10日 下午3:45:22<br>
   * 
   * @category 查询ES课程列表
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/englishStidoList")
  public CommonJsonObject findEnglishStidoList(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("esInfoList", courseOneToManySchedulingService.findEnglishStidoList());

    json.setData(paramMap);

    return json;
  }

  /**
   * 
   * Title: 根据keyid查询相关课程信息<br>
   * Description: 根据keyid查询相关课程信息<br>
   * CreateDate: 2017年4月10日 下午4:42:18<br>
   * 
   * @category 根据keyid查询相关课程信息
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/englishStidoInfo")
  public CommonJsonObject findEnglishStidoInfoByKeyId(HttpServletRequest request,
      @RequestBody CourseOne2ManyScheduling courseOneToManyScheduling) throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("esInfo", courseOneToManySchedulingService.findCourseInfoByKeyId(
        courseOneToManyScheduling.getKeyId()));

    json.setData(paramMap);

    return json;
  }

  /**
   * Title: es学生进教室<br>
   * Description: 提供给总部使用，只有用户名在我们系统没有userCode，可能也没有账号<br>
   * CreateDate: 2017年4月10日 下午7:37:52<br>
   * 
   * @category es学生进教室
   * @author komi.zsy
   * @param request
   * @param subscribeCourse
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/goClass")
  public CommonJsonObject englishStidoGoToClass(HttpServletRequest request,
      @RequestBody SubscribeCourse subscribeCourse) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 进教室url
    String url = genseeService.goToGenseeClass(subscribeCourse.getTeacherTimeId(),
        GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_NOT_USER, subscribeCourse.getUserName(), null);
    json.setData(url);
    return json;
  }

  /**
   * Title: 查询oc课课程信息和第一份合同是否超过30天<br>
   * Description: 查询oc课课程信息和第一份合同是否超过30天<br>
   * CreateDate: 2016年4月25日 下午6:15:00<br>
   * 
   * @category 查询oc课课程信息和第一份合同是否超过30天
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/scheduling/findCourseOcInfo")
  public CommonJsonObject findCourseOcInfo(HttpServletRequest request) throws Exception {
    logger.debug("查找用户第一份合同是否超过30天，并提供oc课课程信息------->");
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    String courseType = "course_type5";

    try {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("isTop",
          orderCourseService.findUserFirstOrderIsInNDay(30, sessionUser.getKeyId()));
      paramMap.put("ocInfoList",
          courseOneToManySchedulingService.findCourseOcInfo(sessionUser, null));
      CourseType ct = (CourseType) MemcachedUtil.getValue(courseType);
      paramMap.put("beforeGoclassTime", ct.getCourseTypeBeforeGoclassTime());
      paramMap.put("cancelSubscribeTime", ct.getCourseTypeCancelSubscribeTime());
      paramMap.put("subscribeTime", ct.getCourseTypeSubscribeTime());

      json.setData(paramMap);
    } catch (Exception e) {
      logger.error("oc课课程查询--->" + e.toString());
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    logger.debug("查找oc课课程信息完毕------->" + json);
    return json;
  }

  /**
   * 
   * Title: 查询EnglishStido课课程信息结束日期大于当前日期<br>
   * Description: findCourseEnglishStidoInfo<br>
   * CreateDate: 2016年5月16日 上午11:44:20<br>
   * 
   * @category 查询EnglishStido课课程信息结束日期大于当前日期
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/ucenter/scheduling/findCourseEnglishStidoInfo")
  public CommonJsonObject findCourseEnglishStidoInfo(HttpServletRequest request) {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    logger.info("前台用户id [" + sessionUser.getKeyId() + "] 查询ES排课信息...");
    CommonJsonObject json = new CommonJsonObject();
    String courseType = "course_type8";

    try {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("esInfoList",
          courseOneToManySchedulingService.findCourseEnglishStidoInfo(sessionUser));
      CourseType ct = (CourseType) MemcachedUtil.getValue(courseType);
      paramMap.put("beforeGoclassTime", ct.getCourseTypeBeforeGoclassTime());
      paramMap.put("cancelSubscribeTime", ct.getCourseTypeCancelSubscribeTime());
      paramMap.put("subscribeTime", ct.getCourseTypeSubscribeTime());

      json.setData(paramMap);
    } catch (Exception e) {
      logger.error("EnglishStdio课课程查询出错--->:" + e.getMessage(), e);
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    return json;
  }

  /**
   * 
   * Title: 学员去上oc课<br>
   * Description: 学员去上oc课<br>
   * CreateDate: 2016年8月31日 下午4:38:26<br>
   * 
   * @category 学员去上oc课
   * @author seven.gz
   * @param request
   * @param paramMap
   * @return
   */
  @ResponseBody
  @RequestMapping("/ucenter/scheduling/courseOc/goToClass")
  public CommonJsonObject ocGoToClass(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    String courseId = (String) paramMap.get("courseId");

    CommonJsonObject json = new CommonJsonObject(ErrorCodeEnum.SUCCESS.getCode());
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    CourseTypeInfo courseTypeInfo = (CourseTypeInfo) sessionUser.getCourseTypes()
        .get("course_type5");
    try {
      if (courseTypeInfo != null) {
        // modify by seven 2016年8月31日13:58:59 判断课件是否到期 日后再加这个判断 因为,现在合同都是3个月的oc
        // if(CommentUtil.checkCourseTypeLimitTime(courseTypeInfo)){
        Map<String, String> map = new HashMap<String, String>();
        map.put("url", courseOneToManySchedulingService.ocGoToClass(courseId));
        json.setData(map);
        // } else {
        // json.setMsg("当前" + courseTypeInfo.getCourseTypeChineseName() +
        // "课程已经过期，到期时间为:" +
        // DateUtil.dateToStrYYMMDDHHMMSS(courseTypeInfo.getLimitTime()));
        // json.setCode(ErrorCodeConstant.HAVE_NO_COURSE_TYPE);
        // }
      } else {
        json.setMsg("您未购买核心课件,暂无法进入!");
        json.setCode(ErrorCodeEnum.HAVE_NO_COURSE_TYPE.getCode());
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setMsg("系统内部出现异常!");
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * 
   * Title: 趣味大课堂即将开课课程查询展示-接口<br>
   * Description: findCourseEnglishStidoInfo<br>
   * CreateDate: 2017年7月5日 下午6:14:46<br>
   * 
   * @category 趣味大课堂即将开课课程查询展示-接口
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "趣味大课堂即将开课课程查询展示【接口】",
      notes = "成人新增趣味大课堂模块.<br>"
          + "按时间顺序查询展示离现在最近的5节课程;<br>")
  @RequestMapping(value = "/api/speakhi/v1/funBigClass",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<CourseOne2ManySchedulingForUserApiParam> findEnglishStidoOnFunBigClass(
      HttpServletRequest request) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    CommonJsonObject<CourseOne2ManySchedulingForUserApiParam> json =
        new CommonJsonObject<CourseOne2ManySchedulingForUserApiParam>();
    String courseType = "course_type8";

    CourseOne2ManySchedulingForUserApiParam courseOne2ManyForUserApiParam =
        new CourseOne2ManySchedulingForUserApiParam();
    CourseType couType = (CourseType) MemcachedUtil.getValue(courseType);
    courseOne2ManyForUserApiParam.setEsInfoList(courseOneToManySchedulingService
        .findEnglishStidoOnFunBigClassInfo(sessionUser));
    courseOne2ManyForUserApiParam.setBeforeGoclassTime(couType.getCourseTypeBeforeGoclassTime());
    courseOne2ManyForUserApiParam.setCancelSubscribeTime(couType
        .getCourseTypeCancelSubscribeTime());
    courseOne2ManyForUserApiParam.setSubscribeTime(couType.getCourseTypeSubscribeTime());

    json.setData(courseOne2ManyForUserApiParam);

    return json;
  }

  /**
   * 
   * Title: 会员中心改版后ES课程接口<br>
   * Description: 会员中心改版后ES课程接口<br>
   * CreateDate: 2017年7月20日 下午6:27:40<br>
   * 
   * @category 会员中心改版后ES课程接口
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "会员中心-点击左侧导航栏ES-调用接口【接口】",
      notes = "会员中心改版后ES课程接口;<br>"
          + "按时间顺序查询展示预约的ES课程相关信息.<br>")
  @RequestMapping(value = "/api/speakhi/v1/ucenter/esInfo",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<CourseSchedulingApiParam> findEsInfo(
      HttpServletRequest request) throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CourseSchedulingApiParam> json =
        new CommonJsonObject<CourseSchedulingApiParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 调用Service层(经过service层的处理,此处返回的结果中已经包含了是否已预约标志)
    List<CourseSchedulingParam> courseAndTeacherInfoList = courseOneToManySchedulingService
        .findCourseAndTeacherInfoList(sessionUser, "course_type8", new Date());// 查询ES课程(查询ES课程结束时间大于等于当前时间的ES课程,传入参数为当前时间)

    // 构建对象,返回给前端
    CourseSchedulingApiParam courseSchedulingApiParam = new CourseSchedulingApiParam();

    // 缓存中获取课程类型相关信息
    CourseType courseType = (CourseType) MemcachedUtil.getValue("course_type8");
    // Integer courseTypePlatform = courseType.getCourseTypePlatform();//
    // 上课平台(上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放))
    Integer courseTypeSubscribeTime = courseType.getCourseTypeSubscribeTime();// 提前预约时间(分钟)
    Integer courseTypeCancelSubscribeTime = courseType.getCourseTypeCancelSubscribeTime();// 提前取消预约时间(分钟)
    Integer courseTypeBeforeGoclassTime = courseType.getCourseTypeBeforeGoclassTime();// 提前上课时间(分钟)
    String courseTypeChineseName = courseType.getCourseTypeChineseName();// 课程类型中文名称

    // 属性赋值
    courseSchedulingApiParam.setCourseInfoList(courseAndTeacherInfoList);
    // courseSchedulingApiParam.setCourseTypePlatform(courseTypePlatform);
    courseSchedulingApiParam.setCourseTypeSubscribeTime(courseTypeSubscribeTime);
    courseSchedulingApiParam.setCourseTypeCancelSubscribeTime(courseTypeCancelSubscribeTime);
    courseSchedulingApiParam.setCourseTypeBeforeGoclassTime(courseTypeBeforeGoclassTime);
    courseSchedulingApiParam.setCourseTypeChineseName(courseTypeChineseName);

    json.setData(courseSchedulingApiParam);

    return json;
  }

  /**
   * 
   * Title: 会员中心改版后OC课程接口<br>
   * Description: 会员中心改版后OC课程接口<br>
   * CreateDate: 2017年7月20日 下午6:27:40<br>
   * 
   * @category 会员中心改版后OC课程接口
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @ApiOperation(value = "会员中心改版-点击左侧导航栏OC-调用接口【接口】",
      notes = "会员中心改版后OC课程接口;<br>"
          + "按时间顺序查询展示预约的OC课程相关信息.<br>")
  @RequestMapping(value = "/api/speakhi/v1/ucenter/ocInfo",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<CourseSchedulingApiParam> findOcInfo(
      HttpServletRequest request) throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CourseSchedulingApiParam> json =
        new CommonJsonObject<CourseSchedulingApiParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 获取当月第一天日期
    Date firstDayOfMonth = CalendarUtil.getFirstDayOfMonth();

    // 调用Service层(经过service层的处理,此处返回的结果中已经包含了是否已预约标志)
    List<CourseSchedulingParam> courseAndTeacherInfoList = courseOneToManySchedulingService
        .findCourseAndTeacherInfoList(sessionUser, "course_type5", firstDayOfMonth);// 查询OC课程

    // 构建对象,返回给前端
    CourseSchedulingApiParam courseSchedulingApiParam = new CourseSchedulingApiParam();

    // 缓存中获取课程类型相关信息
    CourseType courseType = (CourseType) MemcachedUtil.getValue("course_type5");
    // Integer courseTypePlatform = courseType.getCourseTypePlatform();//
    // 上课平台(上课平台直接决定是否有课程回放;0:webex(无课程回放);1:展示互动(有课程回放))
    Integer courseTypeSubscribeTime = courseType.getCourseTypeSubscribeTime();// 提前预约时间(分钟)
    Integer courseTypeCancelSubscribeTime = courseType.getCourseTypeCancelSubscribeTime();// 提前取消预约时间(分钟)
    Integer courseTypeBeforeGoclassTime = courseType.getCourseTypeBeforeGoclassTime();// 提前上课时间(分钟)
    String courseTypeChineseName = courseType.getCourseTypeChineseName();// 课程类型中文名称

    // 属性赋值
    courseSchedulingApiParam.setCourseInfoList(courseAndTeacherInfoList);
    // courseSchedulingApiParam.setCourseTypePlatform(courseTypePlatform);
    courseSchedulingApiParam.setCourseTypeSubscribeTime(courseTypeSubscribeTime);
    courseSchedulingApiParam.setCourseTypeCancelSubscribeTime(courseTypeCancelSubscribeTime);
    courseSchedulingApiParam.setCourseTypeBeforeGoclassTime(courseTypeBeforeGoclassTime);
    courseSchedulingApiParam.setCourseTypeChineseName(courseTypeChineseName);

    json.setData(courseSchedulingApiParam);

    return json;
  }

}