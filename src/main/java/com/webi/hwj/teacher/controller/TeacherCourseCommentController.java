package com.webi.hwj.teacher.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.course.entity.CourseComment;
import com.webi.hwj.course.param.CourseCommentParam;
import com.webi.hwj.teacher.service.TeacherCourseCommentService;
import com.webi.hwj.util.SessionUtil;

/**
 * 
 * Title: 老师评价<br>
 * Description: TeacherCourseComment<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月24日 上午10:42:53
 * 
 * @author athrun.cw
 */
@Controller
public class TeacherCourseCommentController {
  private static Logger logger = Logger.getLogger(TeacherCourseCommentController.class);

  @Resource
  private TeacherCourseCommentService teacherCourseCommentService;
  
  
  /**
   * Title: 保存老师评论.<br>
   * Description: 有keyId就是修改，否则为新增<br>
   * CreateDate: 2017年2月14日 下午10:00:11<br>
   * 
   * @category 添加老师评论
   * @author yangmh
   * @param courseComment
   *          评论对象
   */
  @ResponseBody
  @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT },
      value = "/api/speakhi/v1/tcenter/teacherComment")
  public CommonJsonObject insertTeacherComment(HttpServletRequest request,
      @RequestBody CourseCommentParam courseCommentParam) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    json.setData(teacherCourseCommentService.saveComment(courseCommentParam));
    return json;
  }
  

  /**
   * Title: 老师查看自己给学生的评论.<br>
   * Description: findTeacherComment<br>
   * CreateDate: 2017年2月14日 下午9:25:04<br>
   * 
   * @category 老师查看自己给学生的评论
   * @author yangmh
   * @param subscribeCourseId
   *          预约id
   */
  @ResponseBody
  @RequestMapping(method = RequestMethod.GET,
      value = "/api/speakhi/v1/tcenter/teacherComment")
  public CommonJsonObject findTeacherComment(HttpServletRequest request, String subscribeCourseId)
      throws Exception {
    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);

    CommonJsonObject json = new CommonJsonObject();
    json.setData(
        teacherCourseCommentService.findTeacherComment(sessionTeacher.getKeyId(),
            subscribeCourseId));

    return json;
  }
  
  
  /**
   * Title: 老师中心的评价页面数据加载.<br>
   * Description: findTeacherCourseCommentPage<br>
   * CreateDate: 2017年2月16日 下午3:14:11<br>
   * 
   * @category 老师中心的评价页面数据加载.
   * @author yangmh
   * @param courseTypeCheckBox
   *          课程类型
   * @param rows
   *          每页显示数据个数，可为空
   * @param page
   *          当前为第几页
   */
  @ResponseBody
  @RequestMapping(method = RequestMethod.GET,
      value = "/api/speakhi/v1/tcenter/teacherCourseCommentPage")
  public CommonJsonObject findTeacherCourseCommentPage(HttpServletRequest request,
      String courseTypeCheckBox,
      Integer rows, Integer page)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);
    /**
     * modified by komi 2016年12月5日17:45:01
     * 增加课程类型判断，如果前端没有传课程类型，如果一个课程类型都没有，则返回空.
     */
    if (courseTypeCheckBox != null) {
      json.setData(teacherCourseCommentService.findTeacherCommentPage(sessionTeacher.getKeyId(),
          courseTypeCheckBox, page, rows));

    }

    return json;
  }

}
