package com.webi.hwj.teacher.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.courseone2many.service.CourseOneToManySchedulingService;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category teacherTime控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/")
public class TeacherTimeController {
  private static Logger logger = Logger.getLogger(TeacherTimeController.class);
  @Resource
  private TeacherTimeService teacherTimeService;
  @Resource
  private CourseOneToManySchedulingService courseOneToManySchedulingService;

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
  @RequestMapping("/ucenter/teacherTime/findCourseType1TopDateList")
  public CommonJsonObject findCourseType1TopDateList(HttpServletRequest request) {
    CommonJsonObject commonJsonObject = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    String userId = sessionUser.getKeyId();
    Date endOrderTime = sessionUser.getCurrentOrderEndTime();
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("subscribeDateList",
          teacherTimeService.findCourseType1TopDateList(userId, endOrderTime));
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
  @RequestMapping("/ucenter/teacherTime/findCourseType1TeacherTimeList")
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
   * Title: 加载extension1v6课程日期列表接口<br>
   * Description: findCourseType2TopDateList<br>
   * CreateDate: 2016年12月14日 下午3:43:43<br>
   * 
   * @category 加载extension1v6课程日期列表接口
   * @author seven.gz
   * @param request
   * @param paramMap
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/courseType2TopDates",
      method = RequestMethod.GET)
  public CommonJsonObject findCourseType2TopDateList(HttpServletRequest request) {
    // 获得用户的session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
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
      logger.error("PC加载extension1v6课程日期列表接口出错：" + e.getMessage(), e);
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * 
   * Title: 加载extension1v6课程信息列表接口<br>
   * Description: findCourseType2InfoList<br>
   * CreateDate: 2016年12月14日 下午3:43:55<br>
   * 
   * @category 加载extension1v6课程信息列表接口
   * @author seven.gz
   * @param request
   * @param paramMap
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/courseType2Courses/{teacherTimeDateStr}",
      method = RequestMethod.GET)
  public CommonJsonObject findCourseType2InfoList(HttpServletRequest request,
      @PathVariable(value = "teacherTimeDateStr") String teacherTimeDateStr) {
    CommonJsonObject json = new CommonJsonObject();
    // 获得用户的session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    if (teacherTimeDateStr != null) {
      try {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("courseList",
            courseOneToManySchedulingService.findCourseType2InfoListPc(sessionUser.getKeyId(),
                new Date(Long.valueOf(teacherTimeDateStr)), "course_type2",
                sessionUser.getCurrentLevel()));

        json.setData(map);
      } catch (Exception e) {
        json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
        e.printStackTrace();
        logger.error("PC加载extension1v6课程信息列表接口 error:" + e.getMessage(), e);
      }
    } else {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
    }
    return json;
  }

  /**
   * 
   * Title: core1v1头部日期接口<br>
   * Description: 之前返回的日期是字符串，新写个返回时间戳的<br>
   * CreateDate: 2016年9月6日 下午2:29:15<br>
   * 
   * @category core1v1头部日期接口
   * @author seven.gz
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/courseType1TopDateList",
      method = RequestMethod.GET)
  public CommonJsonObject findCourseType1TopDateListForTimeStamp(HttpServletRequest request) {
    CommonJsonObject commonJsonObject = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    String userId = sessionUser.getKeyId();
    Date endOrderTime = sessionUser.getCurrentOrderEndTime();
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      List<Date> dayList = teacherTimeService.findCourseType1TopDateList(userId, endOrderTime);
      List<Date> returnDayList = new ArrayList<Date>();

      if (dayList != null && dayList.size() > 0) {
        for (Date dayDate : dayList) {
          returnDayList.add(new Date(dayDate.getTime()));
        }
      }
      map.put("subscribeDateList", returnDayList);
      commonJsonObject.setData(map);
    } catch (Exception e) {
      commonJsonObject.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      e.printStackTrace();
      logger.error("查询core1v1头部日期 error:" + e.getMessage(), e);
    }
    return commonJsonObject;
  }
}
