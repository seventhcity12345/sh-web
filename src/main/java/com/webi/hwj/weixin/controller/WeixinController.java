package com.webi.hwj.weixin.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.dao.CourseCommentEnityDao;
import com.webi.hwj.course.entity.CourseComment;
import com.webi.hwj.course.param.CourseCommentWeixinParam;
import com.webi.hwj.course.service.CourseOne2OneService;
import com.webi.hwj.courseextension1v1.service.CourseExtension1v1Service;
import com.webi.hwj.courseone2many.service.CourseOneToManySchedulingService;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.index.controller.UserCenterController;
import com.webi.hwj.index.service.OrderCourseProgressService;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.param.OrderCourseAndOptionParam;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.subscribecourse.param.SubscribeCourseAndCommentParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseListParam;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseCheckService;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.user.param.StudentLearningProgressParam;
import com.webi.hwj.user.service.SutdentLearningProgressService;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.webex.util.WebexUtil;
import com.webi.hwj.weixin.service.UserWeixinService;
import com.webi.hwj.weixin.service.WeiXinSubscribeService;
import com.webi.hwj.weixin.util.WeixinUtil;

/**
 * @category userWeixin控制类.
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController {
  private static Logger logger = Logger.getLogger(WeixinController.class);

  @Resource
  private WeiXinSubscribeService weiXinSubscribeService;
  @Resource
  private TeacherTimeService teacherTimeService;
  @Resource
  private CourseOneToManySchedulingService courseOneToManySchedulingService;
  @Resource
  private CourseOne2OneService courseOne2OneService;
  @Resource
  private CourseExtension1v1Service courseExtension1v1Service;
  @Resource
  private OrderCourseService orderCourseService;
  @Resource
  SubscribeCourseService subscribeCourseService;
  @Resource
  CourseCommentEnityDao courseCommentEnityDao;
  @Resource
  UserCenterController userCenterController;
  @Resource
  SutdentLearningProgressService sutdentLearningProgressService;
  @Resource
  UserWeixinService userWeixinService;
  @Resource
  OrderCourseProgressService orderCourseProgressService;
  @Resource
  BaseSubscribeCourseCheckService baseSubscribeCourseCheckService;
  @Resource
  UserService userService;

  /**
   * Title: 根据微信openid解除绑定<br>
   * Description: 根据微信openid删除绑定<br>
   * CreateDate: 2016年10月26日 上午11:48:38<br>
   * 
   * @category 根据微信openid解除绑定
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/unbindUser")
  public CommonJsonObject unbindUser(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    try {
      String weixinOpenId = paramMap.get("weixinOpenId").toString();
      // modified by alex.yang at 2016年11月2日 02:18:03
      userWeixinService.unbindUser(weixinOpenId);
    } catch (Exception e) {
      logger.error("weixin解除绑定出错", e);
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * Title: core1v1课程列表接口<br>
   * Description: core1v1课程列表接口<br>
   * CreateDate: 2016年9月6日 下午2:49:18<br>
   * 
   * @category core1v1课程列表接口
   * @author komi.zsy
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/findCourseType1List")
  public CommonJsonObject findCourseType1List(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    try {
      logger.info("用户 [" + sessionUser.getKeyId() + "开始查询CourseType1列表...");

      Map<String, Object> returnMap = new HashMap<String, Object>();

      // 查询CourseType1课程列表以及rsa课程进度
      returnMap.put("courseType1List", courseOne2OneService.findCourseType1List(sessionUser));
      // 课件完成进度标准值
      returnMap.put("ratesLimit", MemcachedUtil.getConfigValue("tmm_limit_percent"));

      json.setData(returnMap);

    } catch (Exception e) {
      logger.error("weixin选择CourseType1课程出错", e);
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * Title: extension1v1课程列表接口<br>
   * Description: extension1v1课程列表接口<br>
   * CreateDate: 2016年9月6日 下午2:49:18<br>
   * 
   * @category extension1v1课程列表接口
   * @author komi.zsy
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/findCourseType9List")
  public CommonJsonObject findCourseType9List(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    try {
      logger.info("用户 [" + sessionUser.getKeyId() + "开始查询CourseType9列表...");

      Map<String, Object> returnMap = new HashMap<String, Object>();

      // 查询CourseType1课程列表以及rsa课程进度
      returnMap.put("courseType9List", courseExtension1v1Service.findCourseType9List(sessionUser));

      json.setData(returnMap);

    } catch (Exception e) {
      logger.error("选择CourseType9课程出错", e);
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * 
   * Title: core1v1头部日期接口<br>
   * Description: core1v1头部日期接口<br>
   * CreateDate: 2016年9月6日 下午2:29:15<br>
   * 
   * @category core1v1头部日期接口
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/findCourseType1TopDateList")
  public CommonJsonObject findCourseType1TopDateList(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    CommonJsonObject commonJsonObject = new CommonJsonObject();
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("subscribeDateList",
          teacherTimeService.findTopDateListByCourseType(sessionUser.getKeyId(),
              sessionUser.getCurrentOrderEndTime(), "course_type1"));
      commonJsonObject.setData(map);
    } catch (Exception e) {
      commonJsonObject.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      e.printStackTrace();
      logger.error("查询core1v1头部日期 error:" + e.getMessage(), e);
    }
    return commonJsonObject;
  }

  /**
   * 
   * Title: 按天查询预约时的老师时间列表<br>
   * Description: 按天查询预约时的老师时间列表<br>
   * CreateDate: 2016年9月6日 下午4:27:21<br>
   * 
   * @category 按天查询预约时的老师时间列表
   * @author seven.gz
   * @param request
   * @param teacherTimeDate
   * @return
   */
  @ResponseBody
  @RequestMapping("/findCourseType1TeacherTimeList")
  public CommonJsonObject findCourseType1TeacherTimeList(HttpServletRequest request,
      @RequestBody Map<String, Object> reqestJsonMap) {
    CommonJsonObject commonJsonObject = new CommonJsonObject();
    String teacherTimeDateStr = (String) reqestJsonMap.get("teacherTimeDate");
    if (teacherTimeDateStr != null) {
      try {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("teacherTimeList", teacherTimeService
            .findCourseType1TeacherTimeList(new Date(Long.valueOf(teacherTimeDateStr))));
        commonJsonObject.setData(map);
      } catch (Exception e) {
        commonJsonObject.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
        e.printStackTrace();
        logger.error("查询预约老师时间列表 error:" + e.getMessage(), e);
      }
    } else {
      commonJsonObject.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
    }

    return commonJsonObject;
  }

  /**
   * 
   * Title: 加载extension1v6课程信息列表接口<br>
   * Description: 加载extension1v6课程信息列表接口<br>
   * CreateDate: 2016年10月17日 下午5:28:56<br>
   * 
   * @category 加载extension1v6课程信息列表接口
   * @author seven.gz
   * @param request
   * @param paramMap
   * @return
   */
  @ResponseBody
  @RequestMapping("/findCourseType2InfoList")
  public CommonJsonObject findCourseType2InfoList(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    CommonJsonObject json = new CommonJsonObject();
    Long teacherTimeDateStr = (Long) paramMap.get("teacherTimeDate");
    // 获得用户的session
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    if (teacherTimeDateStr != null) {
      try {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("courseList",
            courseOneToManySchedulingService.findCourseInfoList(
                new Date(teacherTimeDateStr), "course_type2",
                sessionUser.getCurrentLevel()));

        json.setData(map);
      } catch (Exception e) {
        json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
        e.printStackTrace();
        logger.error("加载extension1v6课程信息列表接口 error:" + e.getMessage(), e);
      }
    } else {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
    }

    return json;
  }

  /**
   * 
   * Title: 加载extension1v6课程日期列表接口<br>
   * Description: 加载extension1v6课程日期列表接口<br>
   * CreateDate: 2016年10月17日 下午3:48:28<br>
   * 
   * @category 加载extension1v6课程日期列表接口
   * @author seven.gz
   * @param request
   * @param paramMap
   * @return
   */
  @ResponseBody
  @RequestMapping("/findCourseType2TopDateList")
  public CommonJsonObject findCourseType2TopDateList(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    // 获得用户的session
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    CommonJsonObject json = new CommonJsonObject();
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("subscribeDateList",
          courseOneToManySchedulingService.findTopDateListForOneToMany(sessionUser.getKeyId(),
              sessionUser.getCurrentOrderEndTime(), "course_type2",
              sessionUser.getCurrentLevel()));
      json.setData(map);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("加载extension1v6课程日期列表接口出错：" + e.getMessage(), e);
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
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
  @RequestMapping("/findCourseEnglishStidoInfo")
  public CommonJsonObject findCourseEnglishStidoInfo(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMapJson) {
    String weixinOpenId = (String) paramMapJson.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
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
  @RequestMapping("/findCourseOcInfo")
  public CommonJsonObject findCourseOcInfo(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMapJson) throws Exception {
    logger.debug("查找用户第一份合同是否超过30天，并提供oc课课程信息------->");
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = (String) paramMapJson.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    String courseType = "course_type5";

    try {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("isTop",
          orderCourseService.findUserFirstOrderIsInNDay(30, sessionUser.getKeyId()));
      CourseType ct = (CourseType) MemcachedUtil.getValue(courseType);

      Date currentTime = new Date();
      Date startTime = new Date(
          currentTime.getTime() - ct.getCourseTypeBeforeGoclassTime() * 60 * 1000);
      paramMap.put("ocInfoList",
          courseOneToManySchedulingService.findCourseOcInfo(sessionUser, startTime));

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
   * Title: 微信预约extension1v6课程前置条件接口<br>
   * Description: 微信预约extension1v6课程前置条件接口<br>
   * CreateDate: 2016年10月17日 上午10:11:56<br>
   * 
   * @category 微信预约extension1v6课程前置条件接口
   * @author seven.gz
   * @param request
   * @param paramMap
   * @return
   */
  @ResponseBody
  @RequestMapping("/subscribeCourseType2CoursePremise")
  public CommonJsonObject subscribeCourseType2CoursePremise(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    // 课程id
    String courseId = (String) paramMap.get("courseId");
    // 获得用户的session
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    CommonJsonObject json = null;
    try {
      json = weiXinSubscribeService.subscribeCourseType2CoursePremise(courseId,
          sessionUser.getKeyId());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("微信预约extension1v6课程前置条件接口出错：" + e.getMessage(), e);
      json = new CommonJsonObject(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    return json;
  }

  /**
   * Title: 按课程分组查询课程预约信息<br>
   * Description: 按课程分组查询课程预约信息<br>
   * CreateDate: 2016年10月13日 下午2:05:05<br>
   * 
   * @category 按课程分组查询课程预约信息
   * @author seven.gz
   * @return List
   */
  @ResponseBody
  @RequestMapping("/findLearningPath")
  public CommonJsonObject findLearningPath(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {

    CommonJsonObject json = new CommonJsonObject();
    // 获得用户的session
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    try {
      json.setData(weiXinSubscribeService.findLearningPath(sessionUser));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      logger.error("微信分组查询学员课程列表出错" + e.getMessage(), e);
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    return json;
  }

  /**
   * Title: 添加评论（学生给老师）<br>
   * Description: 添加评论（学生给老师）<br>
   * CreateDate: 2016年10月14日 下午5:19:17<br>
   * 
   * @category 添加评论（学生给老师）
   * @author komi.zsy
   * @param request
   * @param paramObj
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/postCommentToTeacher")
  // CourseComment新写一个参数bean，加微信openid
  public CommonJsonObject postCommentToTeacher(HttpServletRequest request,
      @RequestBody CourseCommentWeixinParam paramObj) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = paramObj.getWeixinOpenId();
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    try {
      String userId = sessionUser.getKeyId();
      CourseComment courseComment = new CourseComment();
      BeanUtils.copyProperties(paramObj, courseComment);
      courseComment.setFromUserId(userId);
      courseComment.setUpdateUserId(userId);
      courseComment.setCreateUserId(userId);
      courseCommentEnityDao.insert(courseComment);
    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      logger.error(e.toString());
      e.printStackTrace();
    }
    return json;
  }

  /**
   * Title: 获取合同学习进度<br>
   * Description: 会员中心首页学员学习进度<br>
   * CreateDate: 2016年10月17日 下午2:37:03<br>
   * 
   * @category 获取合同学习进度
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findContractLearningProgress")
  public CommonJsonObject findContractLearningProgress(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMapJson)
          throws Exception {
    // 获得用户的session
    String weixinOpenId = (String) paramMapJson.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);

    CommonJsonObject json = new CommonJsonObject();
    // 微信需要复用 获取合同学习进度
    try {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("isStudent", sessionUser.getIsStudent());

      if (sessionUser.getIsStudent()) {
        // 学员： 合同进度计算
        paramMap.put("contractProgressList",
            orderCourseProgressService.findStartingContractListByUserId(sessionUser.getKeyId()));
      } else {
        // 非学员：找是否有未付款的合同
        /**
         * 在非学员状态的用户中心允许快速找到未付款的订单入口 在非学员状态的用户中心允许快速找到CC已发送状态的合同入口。
         * 查看order表中的status状态，确定查询到是否有未签的合同或者
         */
        List<OrderCourse> orderCourseList = orderCourseService
            .findOrdersByUserId(sessionUser.getKeyId());
        if (orderCourseList != null && orderCourseList.size() > 0) {
          // 如果有多个合同，则取最新的那一个
          paramMap.put("contract", orderCourseList.get(0));
        } else {
          paramMap.put("contract", null);
        }
      }

      json.setData(paramMap);

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    return json;
  }

  /**
   * Title: 查看录像<br>
   * Description: 视频回顾<br>
   * CreateDate: 2016年10月14日 下午5:10:32<br>
   * 
   * @category 查看录像
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/reviewVideoUrl")
  public CommonJsonObject reviewVideoUrl(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);

    try {
      String teacherTimeId = paramMap.get("teacherTimeId").toString();
      json = subscribeCourseService.watchCourseVideo(sessionUser.getKeyId(), teacherTimeId,
          sessionUser.getUserName());
    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.COURSE_VIDEO_NOT_EXIST.getCode());
      logger.error(e.toString());
      e.printStackTrace();
    }
    return json;
  }

  /**
   * Title: 查找预约课程详情及其评价相关信息<br>
   * Description: 查找预约课程详情及其评价相关信息<br>
   * CreateDate: 2016年10月14日 下午4:39:18<br>
   * 
   * @category 查找预约课程详情及其评价相关信息
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findSubscribeDetailInfo")
  public CommonJsonObject findSubscribeDetailInfo(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    try {
      // 预约id
      String subscribeId = paramMap.get("subscribeId").toString();
      // 查找预约课程详情及其评价相关信息
      SubscribeCourseAndCommentParam returnObj = weiXinSubscribeService
          .findASubscribeDetailBySubscribeId(subscribeId, sessionUser.getKeyId());

      if (returnObj == null) {
        // 没有预约信息或预约已取消
        json.setCode(ErrorCodeEnum.SUBSCRIBE_COURSE_NOT_EXIST.getCode());
      } else {
        json.setData(returnObj);
      }

    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      logger.error(e.toString());
      e.printStackTrace();
    }
    return json;
  }

  /**
   * Title: 查询订课记录列表<br>
   * Description: 根据用户id查询所有预约信息以及相应的学生评价信息<br>
   * CreateDate: 2016年10月14日 下午2:37:09<br>
   * 
   * @category 查询订课记录列表
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findSubscribeList")
  public CommonJsonObject findSubscribeList(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    try {
      String pageStr = (String) paramMap.get("page");
      String rowsStr = (String) paramMap.get("rows");
      Integer page = null;
      Integer rows = null;
      if (pageStr != null) {
        page = Integer.parseInt(pageStr);
      }
      if (rowsStr != null) {
        rows = Integer.parseInt(rowsStr);
      }

      // 根据用户id查询所有预约信息以及相应的学生评价信息
      Page pageObj = weiXinSubscribeService.findSubscribeList(sessionUser.getKeyId(), page, rows);

      Map<String, Object> returnMap = new HashMap<String, Object>();
      returnMap.put("subscribeList", pageObj.getDatas());
      json.setData(returnMap);

    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      logger.error(e.toString());
      e.printStackTrace();
    }
    return json;
  }

  /**
   * Title: 查询合同信息接口<br>
   * Description: 查询合同信息接口<br>
   * CreateDate: 2016年10月13日 下午2:25:47<br>
   * 
   * @category 查询合同信息接口
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findUserContractList")
  public CommonJsonObject findUserContractList(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    try {
      // 查询合同信息
      List<OrderCourseAndOptionParam> orderCourseAndOptionParamList = weiXinSubscribeService
          .findUserContractList(sessionUser.getKeyId(), sessionUser.getPhone());

      Map<String, Object> returnMap = new HashMap<String, Object>();
      returnMap.put("orderCourseList", orderCourseAndOptionParamList);
      json.setData(returnMap);

    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      logger.error(e.toString());
      e.printStackTrace();
    }
    return json;
  }

  /**
   * Title: 查询当天预约课程数<br>
   * Description: 查询当天预约课程数<br>
   * CreateDate: 2016年10月13日 下午4:03:05<br>
   * 
   * @category 查询当天预约课程数
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findCurrentDayCourseInfo")
  public CommonJsonObject findCurrentDayCourseInfo(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    try {
      // 当天24点
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      cal.add(Calendar.DATE, 1);
      Date endTime = DateUtil.strToDateYYYYMMDD(DateUtil.dateToStrYYMMDD(cal.getTime()));

      // 查询当天预约课程数
      Map<String, Object> returnMap = new HashMap<>();
      returnMap.put("subscribeCount", weiXinSubscribeService
          .findCurrentDayCourseInfo(sessionUser.getKeyId(), new Date(), endTime));
      json.setData(returnMap);
    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      logger.error(e.toString());
      e.printStackTrace();
    }
    return json;
  }

  /**
   * 
   * Title: 查询学员学习进度<br>
   * Description: 查询学员学习进度<br>
   * CreateDate: 2016年6月12日 下午6:22:47<br>
   * 
   * @category 查询学员学习进度
   * @author seven.gz
   * @param request
   * @param orderId
   * @return
   */
  @ResponseBody
  @RequestMapping("/findUserLearningProgress")
  public StudentLearningProgressParam findUserLearningProgress(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) {
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    String orderId = (String) paramMap.get("orderId");
    if (StringUtils.isEmpty(orderId)) {
      return null;
    }
    try {
      return sutdentLearningProgressService.findStudentLearningProgress(sessionUser.getKeyId(),
          orderId);
    } catch (Exception e) {
      logger.error("error:查询学员学习进度出错" + e.getMessage(), e);
    }
    return null;
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
  @RequestMapping("/subscribeCourse/go2Vcube4Class")
  public JsonMessage go2Vcube4Class(HttpServletRequest request, JsonMessage json,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
    paramMap.put("user_id", sessionUser.getKeyId());
    // FIXME 暂时使用user_name：vcube显示的user_name
    paramMap.put("user_name", sessionUser.getUserName());
    paramMap.put("user_code", sessionUser.getUserCode());
    paramMap.put("key_id", paramMap.get("subscribeId"));
    try {
      // 预约subcribe_id上课，课程预约subscribe
      logger.info("用户 [" + sessionUser.getPhone() + "] 开始进入教室，课程预约subscribe_id [ "
          + paramMap.get("key_id") + "] ");
      /**
       * modified by komi 2016年7月7日17:43:41 修改为威力方和展示互动通用预约逻辑（以后等威力方没了，要改为全展示互动）
       */
      json = subscribeCourseService.goToClassStudent(paramMap);// 上课
      // logger.info("用户 [" + sessionUser.getPhone() + "]
      // 进入Vcube上课成功，课程预约subscribe_id [ " + paramMap.get("key_id") + "] ");
    } catch (Exception e) {
      logger.error("用户 [" + sessionUser.getPhone() + "] 学员去上课失败，课程预约subscribe_id [ "
          + paramMap.get("key_id") + "] ");
      logger.error("error:" + e.toString());
      json.setMsg(e.toString());
      json.setSuccess(false);
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
  @RequestMapping("/api/speakhi/v1/unfinishedComments")
  public CommonJsonObject findNotCommentList(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);

    // 查找用户已上课、未评价的1v1类型的课程列表
    List<SubscribeCourseListParam> subscribeCourseList = baseSubscribeCourseCheckService
        .findNotCommentList(sessionUser.getKeyId());
    Map<String, Object> returnMap = new HashMap<String, Object>();
    if (subscribeCourseList != null) {
      returnMap.put("commentNum", subscribeCourseList.size());
    } else {
      returnMap.put("commentNum", 0);
    }
    json.setData(returnMap);

    return json;
  }

  /**
   * Title: 进入黄海的webex测试教室.<br>
   * Description: enterTestWebexRoom<br>
   * CreateDate: 2016年12月21日 上午10:46:29<br>
   * 
   * @category 进入黄海的webex测试教室
   * @author yangmh
   */
  @ResponseBody
  @RequestMapping("/enterTestWebexRoom")
  public CommonJsonObject enterTestWebexRoom() throws Exception {
    return WebexUtil.enterTestWebexRoom();
  }

  /**
   * 
   * Title: 编辑个人资料<br>
   * Description: 此方法用老的东西，如果全改改动较大，先暂时用，等后来修改service等方法的时候一起改掉<br>
   * CreateDate: 2015年10月16日 上午10:25:13<br>
   * 
   * @category 编辑个人资料
   * @author seven.gz
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/userInfo", method = RequestMethod.PUT)
  public CommonJsonObject updateUserInfo(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap) throws Exception {
    CommonJsonObject commonJsonObject = new CommonJsonObject();

    String weixinOpenId = (String) paramMap.get("weixinOpenId");
    SessionUser sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);

    paramMap.put("key_id", sessionUser.getKeyId());
    paramMap.put("update_user_id", sessionUser.getKeyId());

    try {
      JsonMessage json = userService.updateUserInfoByUserOrAdminUser(paramMap, request);

      // 里面的方法有问题直接抛出异常了，理论上不会走出失败的里狂
      if (!json.isSuccess()) {
        commonJsonObject.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      }
    } catch (Exception e) {
      logger.info("用户 [" + sessionUser.getPhone() + "] 更新资料失败！");
      logger.error("error:" + e.getMessage());
      commonJsonObject.setMsg(e.getMessage());
      commonJsonObject.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    return commonJsonObject;
  }
}