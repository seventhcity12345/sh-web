package com.webi.hwj.course.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.webi.hwj.course.param.CourseCommentDetailInfoParam;
import com.webi.hwj.course.param.StudentCommentToTeacherParam;
import com.webi.hwj.course.service.CourseCommentService;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentCountApiParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentCountParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentListApiParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentListParam;
import com.webi.hwj.subscribecourse.param.PageRowNumCourseTypeInfoParam;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @category courseComment控制类
 * @author mingyisoft代码生成工具
 * 
 */
@Api(description = "课程评论相关接口信息")
@Controller
public class CourseCommentController {
  private static Logger logger = Logger.getLogger(CourseCommentController.class);

  @Resource
  private CourseCommentService courseCommentService;

  @Resource
  private SubscribeCourseService subscribeCourseService;

  /**
   * Title: 异步加载用户对别人的评论<br>
   * Description: queryComment<br>
   * CreateDate: 2015年9月1日 下午4:11:35<br>
   * 
   * @category 异步加载用户对别人的评论
   * @author athrun.cw
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/ucenter/courseComment/queryComment")
  public JsonMessage queryComment(HttpServletRequest request) {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    JsonMessage json = new JsonMessage();
    try {
      logger.info("开始异步查询对老师的评价数据...");
      json.setData(courseCommentService.findUserCommentByCourseIdAndUserIdAndTeacherId(paramMap));
      logger.info("对老师的评价:" + json.getData());
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("系统异常！");
      e.printStackTrace();
    }
    return json;
  }

  /**
   * 
   * Title: 评分<br>
   * Description: insertComment<br>
   * CreateDate: 2015年9月2日 下午2:30:49<br>
   * 
   * @category 评分
   * @author athrun.cw
   * @param model
   * @param request
   * @return
   */
  @ResponseBody
  @RequestMapping("/ucenter/courseComment/insertComment")
  public JsonMessage insertComment(Model model, HttpServletRequest request) {
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    logger.debug(paramMap);

    JsonMessage json = new JsonMessage();
    try {
      logger.info("开始添加对老师的评价...");
      json.setData(courseCommentService.insertComment(paramMap));
      logger.info("对老师的评价为：" + json.getData());
      json.setMsg("评论成功！");
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("系统异常！");
      logger.error("error:" + e.getMessage(), e);
      e.printStackTrace();
    }
    return json;
  }

  /**
   * 
   * Title: 视频回顾<br>
   * Description: reviewVideoUrl<br>
   * CreateDate: 2015年9月7日 下午5:24:41<br>
   * 
   * @category 视频回顾
   * @author athrun.cw
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/courseComment/reviewVideoUrl")
  public CommonJsonObject reviewVideoUrl(HttpServletRequest request) throws Exception {
    SessionUser sUser = SessionUtil.getSessionUser(request);
    CommonJsonObject json = null;

    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    try {
      json = subscribeCourseService.watchCourseVideo(sessionUser.getKeyId(), request.getParameter(
          "key_id"),
          sUser.getUserName());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setMsg("查看录像出错,请联系技术支持");
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    logger.info("视屏id= " + request.getParameter("key_id") + " " + json.getMsg());
    return json;
  }

  /**
   * 
   * Title: 查询已完成课程总数<br>
   * Description: 查询已完成课程总数<br>
   * CreateDate: 2017年7月21日 下午4:23:34<br>
   * 
   * @category 查询已完成课程总数
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-已完成课程-查询已完成课程总数【接口】",
      notes = "成人-已完成课程-已完成课程总数;<br>"
          + "查询当前学员所有已完成的课程,返回课程类型以及课程类型中文以及对应的已完成课程数.")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/courseCountCompleted",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<CourseTypeCommentCountApiParam> findCourseCountCompleted(
      HttpServletRequest request)
          throws Exception {
    // 构建CommonJsonObject
    CommonJsonObject<CourseTypeCommentCountApiParam> json =
        new CommonJsonObject<CourseTypeCommentCountApiParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 调用Service层
    List<CourseTypeCommentCountParam> courseCountCompletedList = courseCommentService
        .findCourseCountCompleted(sessionUser);

    // 将list放到对象中传到前台
    CourseTypeCommentCountApiParam courseTypeCommentCountApiParam =
        new CourseTypeCommentCountApiParam();
    courseTypeCommentCountApiParam.setCourseTypeCommentCountParamList(courseCountCompletedList);

    // 传给前端
    json.setData(courseTypeCommentCountApiParam);

    return json;
  }

  /**
   * 
   * Title: 查询返回指定课程类型的评价信息列表<br>
   * Description: 返回指定课程类型的评价信息列表<br>
   * CreateDate: 2017年7月21日 下午4:23:34<br>
   * 
   * @category 返回指定课程类型的评价信息列表
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-已完成课程-查询指定课程类型的评价信息列表【接口】",
      notes = "成人-已完成课程-返回指定课程类型的评价信息列表;<br>"
          + "在初始化加载或者点击翻页按钮时,根据前端传入的课程类型、页码、行数(每页想要展示的行数),查询展示指定课程类型的评价信息列表.")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/courseListCompleted",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<CourseTypeCommentListApiParam> findCourseListCompleted(
      HttpServletRequest request,
      @ApiParam(required = true, name = "courseType", value = "课程类型") @RequestParam(
          value = "courseType") String courseType,
      @ApiParam(required = true, name = "page", value = "页码") @RequestParam(
          value = "page") String page,
      @ApiParam(required = true, name = "rows", value = "每页想要显示的行数") @RequestParam(
          value = "rows") String rows)
              throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CourseTypeCommentListApiParam> json =
        new CommonJsonObject<CourseTypeCommentListApiParam>();

    // 获取当前登录用户userId
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    String keyId = sessionUser.getKeyId();

    // 获取参数(前端传过来：课程类型、页码、每页想要展示的条数-转换为int型)
    Integer pageInt = Integer.valueOf(page);
    Integer rowsInt = Integer.valueOf(rows);

    // 调用Service层
    List<CourseTypeCommentListParam> courseListCompletedList = courseCommentService
        .findCourseListCompleted(keyId, courseType, pageInt, rowsInt);

    // 构建CourseTypeCommentListApiParam
    CourseTypeCommentListApiParam courseTypeCommentListApiParam =
        new CourseTypeCommentListApiParam();

    courseTypeCommentListApiParam.setCourseTypeCommentListParamList(courseListCompletedList);

    json.setData(courseTypeCommentListApiParam);

    return json;
  }

  /**
   * 
   * Title: 查询已完成课程评论的详细信息<br>
   * Description: 查询已完成课程评论的详细信息<br>
   * CreateDate: 2017年7月21日 下午4:23:34<br>
   * 
   * @category 查询已完成课程评论的详细信息
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-已完成课程-查询已完成课程评论的详细信息【接口】",
      notes = "成人-已完成课程-查询已完成课程评论的详细信息;<br>"
          + "在点击评论列表中的某行,前端传入的预约id,查询展示课程评论的详细信息.")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/courseCommentDetailInfoCompleted",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<CourseCommentDetailInfoParam> findCourseCommentDetailInfoCompleted(
      HttpServletRequest request,
      @ApiParam(required = true, name = "subscribeCourseId", value = "预约id") @RequestParam(
          value = "subscribeCourseId") String subscribeCourseId)
              throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CourseCommentDetailInfoParam> json =
        new CommonJsonObject<CourseCommentDetailInfoParam>();

    // 获取当前登录用户userId
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    String keyId = sessionUser.getKeyId();

    // 调用Service层
    CourseCommentDetailInfoParam courseCommentDetailInfoParam = courseCommentService
        .findCourseCommentDetailCompleted(subscribeCourseId, keyId);

    json.setData(courseCommentDetailInfoParam);

    return json;
  }

  /**
   * 
   * Title: 已完成课程模块-学员新增或修改对老师的评论<br>
   * Description: 已完成课程模块-学员新增或修改对老师的评论<br>
   * CreateDate: 2017年7月25日 上午10:47:45<br>
   * 
   * @category 已完成课程模块-学员新增或修改对老师的评论
   * @author felix.yl
   * @param request
   * @param updateCommentToTeacherParam
   * @param result
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-已完成课程-学员新增或修改评论信息【接口】",
      notes = "成人-已完成课程-学员新增或修改评论信息;<br>")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/studentToTeacherCommentInfo",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<String> updateCommentToTeacher(
      HttpServletRequest request,
      @ApiParam(required = true, name = "studentCommentToTeacherParam",
          value = "评论信息封装bean") @RequestBody @Valid StudentCommentToTeacherParam studentCommentToTeacherParam,
      BindingResult result) throws Exception {

    // 构建CommonJsonObject对象
    CommonJsonObject<String> json = new CommonJsonObject<String>();

    // 表单校验框架
    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }

    // 获取当前登录用户userId
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    String keyId = sessionUser.getKeyId();

    // 调用Service层
    courseCommentService.updateCommentToTeacher(studentCommentToTeacherParam, keyId);

    return json;
  }

  /**
   * 
   * Title: findPageAndRowsBySubscribeId<br>
   * Description: findPageAndRowsBySubscribeId<br>
   * CreateDate: 2017年7月27日 上午10:54:57<br>
   * 
   * @category findPageAndRowsBySubscribeId
   * @author officer
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   * @throws NumberFormatException
   */
  @ApiOperation(value = "成人-已完成课程-查询指定课程的页码和行号【接口】",
      notes = "成人-已完成课程-成人-已完成课程-查询指定课程的页码和行号;")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/pageAndRowNum",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)

  public CommonJsonObject<PageRowNumCourseTypeInfoParam> findPageAndRowsBySubscribeId(
      HttpServletRequest request,
      @ApiParam(required = true, name = "rows", value = "每页的条数") @RequestParam(
          value = "rows") String rows,
      @ApiParam(name = "subscribeId", value = "预约id") @RequestParam(
          value = "subscribeId") String subscribeId) throws NumberFormatException, Exception {

    // 构建CommonJsonObject对象
    CommonJsonObject<PageRowNumCourseTypeInfoParam> json =
        new CommonJsonObject<PageRowNumCourseTypeInfoParam>();

    // 获取当前登录用户userId
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    String keyId = sessionUser.getKeyId();

    // 调用Service
    PageRowNumCourseTypeInfoParam pageAndRows = courseCommentService.findPageAndRowsBySubscribeId(
        Integer.valueOf(rows), subscribeId, keyId);

    json.setData(pageAndRows);

    return json;
  }

}
