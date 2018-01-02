package com.webi.hwj.course.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.param.CourseCommentDemoParam;
import com.webi.hwj.course.service.CourseCommentDemoService;

/**
 * @category courseCommentDemo控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
public class CourseCommentDemoController {
  private static Logger logger = Logger.getLogger(CourseCommentDemoController.class);
  @Resource
  private CourseCommentDemoService courseCommentDemoService;

  /**
   * 
   * Title: demo课保存评论<br>
   * Description: 管理员也能通过连接评论，所以不加tcenter<br>
   * CreateDate: 2017年6月27日 下午4:29:52<br>
   * 
   * @category demo课保存评论
   * @author seven.gz
   * @param request
   * @param courseCommentDemoParam
   * @param result
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/teacherCommentDemo", method = RequestMethod.POST)
  public CommonJsonObject saveTeacherCommentDemo(HttpServletRequest request,
      @RequestBody @Valid CourseCommentDemoParam courseCommentDemoParam,
      BindingResult result) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
      return json;
    }
    json = courseCommentDemoService.saveCommentDemo(courseCommentDemoParam);
    return json;
  }

  /**
   * 
   * Title: 查询评论内容<br>
   * Description: findTeacherCommentDemo<br>
   * CreateDate: 2017年6月27日 下午5:30:24<br>
   * 
   * @category 查询评论内容
   * @author seven.gz
   * @param request
   * @param subscribeCourseId
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/teacherCommentDemo/{subscribeCourseId}",
      method = RequestMethod.GET)
  public CommonJsonObject findTeacherCommentDemo(HttpServletRequest request,
      @PathVariable(value = "subscribeCourseId") String subscribeCourseId) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    json.setData(courseCommentDemoService.findCommentDemo(subscribeCourseId));;
    return json;
  }
}