package com.webi.hwj.subscribecourse.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.huanxun.exception.HuanxunException;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.StatisticsMonthSubscribeCountParam;
import com.webi.hwj.subscribecourse.param.StatisticsTeacherSubscribeCourseParam;
import com.webi.hwj.subscribecourse.param.SubscribeParam;
import com.webi.hwj.subscribecourse.service.AdminSubscribeCourseService;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseService;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.util.CalendarUtil;
import com.webi.hwj.util.SessionUtil;
import com.webi.hwj.util.TxtUtil;

@Controller
@RequestMapping("/admin/subscribeCourse")
public class AdminSubscribeCourseController {
  private static Logger logger = Logger.getLogger(AdminSubscribeCourseController.class);

  @Resource
  AdminSubscribeCourseService adminSubscribeCourseService;
  @Resource
  SubscribeCourseService subscribeCourseService;
  @Resource
  BaseSubscribeCourseService baseSubscribeCourseService;

  /**
   * 
   * Title: 跳转后台通用英语学员管理界面<br>
   * Description: 跳转后台通用英语学员管理界面<br>
   * CreateDate: 2016年5月31日 下午4:17:22<br>
   * 
   * @category 跳转后台通用英语学员管理界面
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/monthSubscribeCount")
  public String monthSubscribeCount(Model model) {
    return "admin/subscribecourse/month_subscribe_count";
  }

  /**
   * 
   * Title: 跳转后台通用英语学员管理界面<br>
   * Description: 跳转后台通用英语学员管理界面<br>
   * CreateDate: 2016年5月31日 下午4:17:22<br>
   * 
   * @category 跳转后台通用英语学员管理界面
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/teacherCourseCount")
  public String teacherCourseTime(Model model) {
    return "admin/subscribecourse/teacher_course_count";
  }

  /**
   * 
   * Title: 查询预约信息<br>
   * Description: 查询预约信息<br>
   * CreateDate: 2016年8月1日 上午11:35:47<br>
   * 
   * @category 查询预约信息
   * @author seven.gz
   * @param model
   * @return
   */
  @RequestMapping("/subscribeCourseInfo")
  public String subscribeCourseInfo(Model model) {
    return "admin/subscribecourse/subscribe_course_info";
  }

  /**
   * Title: 跳转demo课课程跟踪页面<br>
   * Description: demoSubscribeCourseInfo<br>
   * CreateDate: 2017年6月7日 下午2:15:58<br>
   * 
   * @category 跳转demo课课程跟踪页面
   * @author seven.gz
   */
  @RequestMapping("/demoSubscribeCourseInfo")
  public String demoSubscribeCourseInfo(Model model) {
    return "admin/subscribecourse/demo_subscribe_course_info";
  }

  /**
   * 
   * Title: 查询通用学员信息<br>
   * Description: 查询通用学员信息<br>
   * CreateDate: 2016年5月31日 下午9:11:59<br>
   * 
   * @category 查询通用学员信息
   * @author seven.gz
   * @param request
   * @param findAllFlag
   *          true:查询全部学员,false:查询此lc手下学员
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/statisticsMonthSubscribeCount")
  public Map<String, Object> statisticsMonthSubscribeCount(HttpServletRequest request,
      String startTime,
      String endTime, String studentShow) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询课时时段预约信息...");
    try {
      List<StatisticsMonthSubscribeCountParam> resultList = adminSubscribeCourseService
          .statisticsMonthSubscribeCount(DateUtil.strToDateYYYYMMDDHHMMSS(startTime),
              DateUtil.strToDateYYYYMMDDHHMMSS(endTime), studentShow);
      paramMap.put("total", resultList != null ? resultList.size() : 0);
      paramMap.put("rows", resultList);
    } catch (Exception e) {
      logger.error("查询课时时段预约信息出错:" + e);
    }
    return paramMap;
  }

  /**
   * 
   * Title: 统计老师上课总计<br>
   * Description: 统计老师上课总计<br>
   * CreateDate: 2016年5月31日 下午9:11:59<br>
   * 
   * @category 统计老师上课总计
   * @author seven.gz
   * @param request
   * @param findAllFlag
   *          true:查询全部学员,false:查询此lc手下学员
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/satisticsTeacherCourseCount")
  public Map<String, Object> satisticsTeacherCourseCount(HttpServletRequest request,
      String startTime,
      String endTime, String studentShow) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询老师上课总计信息...");
    try {
      List<StatisticsTeacherSubscribeCourseParam> resultList = adminSubscribeCourseService
          .satisticsTeacherSubscribeCourse(paramMap, DateUtil.strToDateYYYYMMDDHHMMSS(startTime),
              DateUtil.strToDateYYYYMMDDHHMMSS(endTime), studentShow);
      paramMap.put("total", resultList != null ? resultList.size() : 0);
      paramMap.put("rows", resultList);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("查询老师上课总计信息出错:" + e);
    }
    return paramMap;
  }

  /**
   * 
   * Title: 课程跟踪信息<br>
   * Description: 课程跟踪信息<br>
   * CreateDate: 2016年8月1日 上午11:34:09<br>
   * 
   * @category 课程跟踪信息
   * @author seven.gz
   * @param request
   * @param startTime
   * @param endTime
   * @param studentShow
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findSubscribeCourseInfoPage")
  public Map<String, Object> findSubscribeCourseInfoPage(HttpServletRequest request,
      String startTime,
      String endTime, String studentShow) throws Exception {
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);

    // 转化前台的查询条件，这里写法有点弱，等后来看看从根源处理下
    String sort = (String) paramMap.get("sort");
    if (!StringUtils.isEmpty(sort)) {
      sort = sort.replaceAll("courseTypeChineseName", "courseType");
      paramMap.put("sort", sort);
    }

    logger.info("后台管理用户id [" + sessionAdminUser.getKeyId() + "] 查询课表信息...");
    try {
      Page page = adminSubscribeCourseService.findSubscribeCourseInfoPage(paramMap,
          DateUtil.strToDateYYYYMMDDHHMMSS(startTime),
          DateUtil.strToDateYYYYMMDDHHMMSS(endTime));
      paramMap.put("total", page.getTotalCount());
      paramMap.put("rows", page.getDatas());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("查询课表信息出错:" + e);
    }
    return paramMap;
  }

  /**
   * Title: 切换学员出席状态<br>
   * Description: 切换学员出席状态<br>
   * CreateDate: 2016年9月20日 下午2:14:20<br>
   * 
   * @category 切换学员出席状态
   * @author seven.gz
   * @param request
   *          请求
   * @param subscribeId
   *          预约id
   * @param oldStatus
   *          学员show 源状态
   * @return JsonMessage
   */
  @ResponseBody
  @RequestMapping("/changeStudentShowStatus")
  public JsonMessage changeStudentShowStatus(HttpServletRequest request,
      String subscribeId, Boolean oldStatus) {
    JsonMessage json = null;
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    try {
      json = adminSubscribeCourseService.changeStudentShowStatus(subscribeId, oldStatus,
          sessionAdminUser.getKeyId());
    } catch (Exception ec) {
      ec.printStackTrace();
      logger.error("跟新学员出席状态失败 error:" + ec.getMessage(), ec);
      json = new JsonMessage();
      json.setSuccess(false);
      json.setMsg("操作失败");
    }
    return json;
  }

  /**
   * 
   * Title: 管理员进入教室<br>
   * Description: 管理员进入教室<br>
   * CreateDate: 2016年9月21日 下午5:10:29<br>
   * 
   * @category 管理员进入教室
   * @author seven.gz
   * @param teacherTimeId
   *          老师时间id
   * @param request
   *          请求
   */
  @ResponseBody
  @RequestMapping("/goToClassByAdminUser/{teacherTimeId}")
  public JsonMessage goToClassByAdminUser(
      @PathVariable(value = "teacherTimeId") String teacherTimeId, HttpServletRequest request) {
    JsonMessage json = null;

    try {
      json = subscribeCourseService.goToClassTeacher(teacherTimeId, null,
          null, false);
    } catch (Exception e) {
      logger.error("后台进入webex失败，teacherTimeId="
          + teacherTimeId);
      logger.error("error:" + e.toString());
      json = new JsonMessage();
      json.setMsg(e.toString());
      json.setSuccess(false);
    }
    return json;
  }

  /**
   * Title: 查询当天已经预约的core和extension课程数<br>
   * Description: 查询当天已经预约的core和extension课程数<br>
   * CreateDate: 2016年9月21日 下午5:08:47<br>
   * 
   * @category 查询当天已经预约的core和extension课程数
   * @author seven.gz
   * @param request
   *          请求
   * @return int
   */
  @ResponseBody
  @RequestMapping("/findCourseCount")
  public int findCourseCount(HttpServletRequest request) {
    int courseCount = 0;
    try {
      // 今天0点
      Date startTime = DateUtil.strToDateYYYYMMDD(DateUtil.dateToStrYYMMDD(new Date()));
      // 明天0点
      Date endTime = CalendarUtil.getNextNDay(startTime, 1);
      courseCount = adminSubscribeCourseService.findCourseCount(startTime, endTime);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("查询当天课程数出错:" + e);
    }
    return courseCount;
  }

  /**
   * 
   * Title: 下载团训数据<br>
   * Description: downloadTuanxunInfo<br>
   * CreateDate: 2016年12月12日 下午5:13:36<br>
   * 
   * @category 下载团训数据
   * @author seven.gz
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/downloadTuanxunInfo")
  public ResponseEntity<byte[]> downloadTuanxunInfo(HttpServletRequest request) throws Exception {
    String startOrderTime = request.getParameter("startOrderTime");
    String endOrderTime = request.getParameter("endOrderTime");
    String csvFileString = adminSubscribeCourseService.createDownloadTuanxunInfo(startOrderTime,
        endOrderTime);
    /**
     * modified by komi 2017年1月16日17:16:14 将导出部分封装起来
     */
    return TxtUtil.exportTxtFile("团训数据.csv", csvFileString);
  }

  /**
   * 
   * Title: 后台预约demo课<br>
   * Description: subscribeCourseDemo<br>
   * CreateDate: 2016年12月23日 下午6:43:35<br>
   * 
   * @category 后台预约demo课
   * @author seven.gz
   * @param request
   * @param subscribeParam
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/subscribeCourseDemo")
  public CommonJsonObject subscribeCourseDemo(HttpServletRequest request,
      @RequestBody SubscribeParam subscribeParam)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // ----以上都是临时的
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

    // 组装subscribeCourse对象
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setCourseType(subscribeParam.getCourseType());
    subscribeCourse.setCourseId(subscribeParam.getCourseId());
    subscribeCourse.setUserId(subscribeParam.getUserId());
    // modify by seven 2017年6月6日21:15:44 增加预约备注
    subscribeCourse.setSubscribeRemark(subscribeParam.getSubscribeRemark());
    subscribeCourse.setTeacherTimeId(subscribeParam.getTeacherTimeId());
    subscribeCourse.setCreateUserId(sessionAdminUser.getKeyId());
    subscribeCourse.setUpdateUserId(sessionAdminUser.getKeyId());
    subscribeCourse.setSubscribeFrom("admin");
    subscribeCourse.setSubscribeType(subscribeParam.getSubscribeType());

    try {
      json = baseSubscribeCourseService.subscribeDemoEntry(subscribeCourse, subscribeParam
          .getWebexRoomHostId(), sessionAdminUser
              .getAccount());
    } catch (HuanxunException ex) {
      // 如果环迅报306说明预约时间不正确
      if (ex.getMessage() != null && ex.getMessage().indexOf("306") != -1) {
        json.setCode(ErrorCodeEnum.SUBSCRIBE_ERROR_TIME.getCode());
      } else {
        json.setCode(ErrorCodeEnum.SUBSCRIBE_HUANXUN_ERROR.getCode());
      }
    } catch (Exception e) {
      logger.error("error:" + e.getMessage(), e);
      json.setMsg(e.getMessage());
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * 
   * Title: demo取消预约<br>
   * Description: cancelSubscribeCourseDemo<br>
   * CreateDate: 2016年12月27日 上午11:47:51<br>
   * 
   * @category demo取消预约
   * @author seven.gz
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/cancelSubscribeCourseDemo")
  public CommonJsonObject cancelSubscribeCourseDemo(HttpServletRequest request,
      @RequestBody Map<String, String> paramMap)
          throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    String subscribeId = paramMap.get("subscribeId");

    if (subscribeId == null) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      return json;
    }

    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

    // 组装subscribeCourse对象
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setKeyId(subscribeId);
    subscribeCourse.setUpdateUserId(sessionAdminUser.getKeyId());
    subscribeCourse.setSubscribeFrom("admin");

    try {
      json = baseSubscribeCourseService.cancelDemoSubscribeEntry(subscribeCourse);
    } catch (HuanxunException ex) {
      json.setCode(ErrorCodeEnum.SUBSCRIBE_HUANXUN_CANCEL_ERROR.getCode());
    } catch (Exception e) {
      logger.error("error:" + e.getMessage(), e);
      json.setMsg(e.getMessage());
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * Title: 查询demo课程跟踪数据<br>
   * Description: findDemoSubscribeCourseInfoPage<br>
   * CreateDate: 2017年4月30日 下午3:39:30<br>
   * 
   * @category 查询demo课程跟踪数据
   * @author seven.gz
   */
  @ResponseBody
  @RequestMapping("/findDemoSubscribeCourseInfoPage")
  public Map<String, Object> findDemoSubscribeCourseInfoPage(HttpServletRequest request,
      String startTime,
      String endTime, String studentShow) throws Exception {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    Page page = adminSubscribeCourseService.findDemoSubscribeCourseInfoPage(paramMap,
        DateUtil.strToDateYYYYMMDDHHMMSS(startTime),
        DateUtil.strToDateYYYYMMDDHHMMSS(endTime), "course_type4");
    paramMap.put("total", page.getTotalCount());
    paramMap.put("rows", page.getDatas());
    return paramMap;
  }
}
