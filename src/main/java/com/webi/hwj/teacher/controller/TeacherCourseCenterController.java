package com.webi.hwj.teacher.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.course.service.CourseCommentService;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.param.FindTeacherCourseCenterCourseListParam;
import com.webi.hwj.teacher.service.TeacherService;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "老师课程中心相关接口")
@Controller
public class TeacherCourseCenterController {
  private static Logger logger = Logger.getLogger(TeacherCourseCenterController.class);
  @Resource
  private TeacherService teacherService;
  @Resource
  private CourseCommentService courseCommentService;
  @Resource
  private TeacherTimeService teacherTimeService;
  @Resource
  private SubscribeCourseService subscribeCourseService;

  /**
   * Title: 老师端-课程中心头部日期列表查询.<br>
   * Description: findTeacherCourseCenterTopDateList<br>
   * CreateDate: 2017年2月13日 上午10:05:45<br>
   * 
   * @category 老师端-教师中心头部日期列表查询
   * @author yangmh
   */
  @RequestMapping(method = RequestMethod.GET,
      value = "/api/speakhi/v1/tcenter/teacherCourseCenterTopDates")
  @ResponseBody
  public CommonJsonObject findTeacherCourseCenterTopDateList(HttpServletRequest request,
      String queryDate) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);

    // 1.获取14+2天是否排课的记录数据
    json.setData(teacherTimeService
        .findTeacherCourseCenterTopDateList(sessionTeacher.getKeyId(), queryDate));
    return json;
  }

  /**
   * Title: 根据预约id获取备注信息<br>
   * Description: 根据预约id获取备注信息<br>
   * CreateDate: 2017年6月7日 下午7:49:09<br>
   * 
   * @category 根据预约id获取备注信息
   * @author komi.zsy
   * @param request
   * @param subscribeId
   *          预约id
   * @return
   * @throws Exception
   */
  @RequestMapping(method = RequestMethod.GET,
      value = "/api/speakhi/v1/tcenter/demoRemark")
  @ResponseBody
  public CommonJsonObject findDemoRemark(HttpServletRequest request,
      String subscribeId) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 根据预约id获取备注信息
    json.setData(subscribeCourseService.getSubscribeCourseRemark(subscribeId));
    return json;
  }

  /**
   * Title: 老师端-课程中心课程列表查询.<br>
   * Description: 老师端-课程中心课程列表查询.<br>
   * CreateDate: 2017年2月13日 上午11:13:22<br>
   * 
   * @category 老师端-课程中心课程列表查询
   * @author yangmh
   */
  @ApiOperation(value = "老师端-课程中心课程列表查询",
      notes = "老师端-课程中心课程列表查询,查询某天的数据")
  @ResponseBody
  @RequestMapping(method = RequestMethod.GET,
      value = "/api/speakhi/v1/tcenter/teacherCourseCenterCourses")
  public CommonJsonObject<FindTeacherCourseCenterCourseListParam> findTeacherCourseCenterCourseList(
      HttpServletRequest request,
      @ApiParam(name = "queryDate", value = "查询日期",
          required = true,
          example = "2017-07-01") @RequestParam("queryDate") String queryDate,
      @ApiParam(name = "type", value = "查询类型(2:全部数据,1:已确认数据,0:未确认数据)",
          required = true,
          example = "0") @RequestParam("type") String type)
              throws Exception {

    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);
    String teacherId = sessionTeacher.getKeyId();

    FindTeacherCourseCenterCourseListParam findTeacherCourseCenterCourseListParam =
        new FindTeacherCourseCenterCourseListParam();

    // 查询core课程列表
    findTeacherCourseCenterCourseListParam.setTeacherCourseCenterCoreCourseList(teacherTimeService
        .findTeacherCourseCenterCoreCourseList(queryDate, teacherId, type));
    // 查询extra+新概念课程列表（因为两个都是大课因此一起查）
    findTeacherCourseCenterCourseListParam.setTeacherCourseCenterExtraCourseList(teacherTimeService
        .findTeacherCourseCenterExtraCourseList(queryDate, teacherId, type));

    // 给前台返回提前进入教室时间
    findTeacherCourseCenterCourseListParam.setCourseType1BeforeGoclassTime(
        ((CourseType) MemcachedUtil.getValue("course_type1")).getCourseTypeBeforeGoclassTime());
    findTeacherCourseCenterCourseListParam.setCourseType2BeforeGoclassTime(
        ((CourseType) MemcachedUtil.getValue("course_type2")).getCourseTypeBeforeGoclassTime());
    findTeacherCourseCenterCourseListParam.setCourseType4BeforeGoclassTime(
        ((CourseType) MemcachedUtil.getValue("course_type4")).getCourseTypeBeforeGoclassTime());
    findTeacherCourseCenterCourseListParam.setCourseType5BeforeGoclassTime(
        ((CourseType) MemcachedUtil.getValue("course_type5")).getCourseTypeBeforeGoclassTime());
    findTeacherCourseCenterCourseListParam.setCourseType8BeforeGoclassTime(
        ((CourseType) MemcachedUtil.getValue("course_type8")).getCourseTypeBeforeGoclassTime());
    findTeacherCourseCenterCourseListParam.setCourseType9BeforeGoclassTime(
        ((CourseType) MemcachedUtil.getValue("course_type9")).getCourseTypeBeforeGoclassTime());

    CommonJsonObject<FindTeacherCourseCenterCourseListParam> json =
        new CommonJsonObject<FindTeacherCourseCenterCourseListParam>();
    json.setData(findTeacherCourseCenterCourseListParam);
    return json;
  }

  /**
   * Title: 老师确认课程时间.<br>
   * Description: teacherConfirmation<br>
   * CreateDate: 2017年2月14日 上午9:37:47<br>
   * 
   * @category 老师确认课程时间
   * @author yangmh
   * @param keyId
   *          老师时间id
   */
  @ResponseBody
  @RequestMapping(method = RequestMethod.PUT,
      value = "/api/speakhi/v1/tcenter/teacherConfirmation/{keyId}")
  public CommonJsonObject teacherConfirmation(@PathVariable String keyId)
      throws Exception {
    TeacherTime teacherTime = new TeacherTime();
    teacherTime.setKeyId(keyId);
    teacherTime.setIsConfirm(true);
    teacherTimeService.update(teacherTime);
    CommonJsonObject json = new CommonJsonObject();
    return json;
  }

  /**
   * Title: 老师进入教室.<br>
   * Description: goToClassByTeacher<br>
   * CreateDate: 2016年11月18日 上午7:14:26<br>
   * 
   * @category 老师进入教室
   * @author yangmh
   */
  @ResponseBody
  @RequestMapping("/api/speakhi/v1/tcenter/goToClassByTeacher")
  public JsonMessage goToClassByTeacher(HttpServletRequest request, String teacherTimeId)
      throws Exception {
    logger.info("teacherTimeId===" + teacherTimeId);
    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);
    JsonMessage json = new JsonMessage();

    json = subscribeCourseService.goToClassTeacher(teacherTimeId, sessionTeacher.getKeyId(),
        sessionTeacher.getTeacherName(), true);

    return json;
  }

}
