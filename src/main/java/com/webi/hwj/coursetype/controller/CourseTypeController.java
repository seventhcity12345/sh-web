package com.webi.hwj.coursetype.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.coursetype.service.CourseTypeService;

/**
 * @category courseType控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/courseType")
public class CourseTypeController {
  private static Logger logger = Logger.getLogger(CourseTypeController.class);
  @Resource
  private CourseTypeService courseTypeService;

  /**
   * Title: 查找课程类型列表<br>
   * Description: 查找课程类型列表<br>
   * CreateDate: 2016年8月24日 下午6:33:13<br>
   * 
   * @category 查找课程类型列表
   * @author komi.zsy
   * @param request
   * @param paramObj
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findCourseType")
  public List<CourseType> findCourseType(HttpServletRequest request) throws Exception {
    List<CourseType> courseTypes = courseTypeService.findList();
    return courseTypes;
  }

  /**
   * Title: 根据条件查找课程类型列表<br>
   * Description: 根据条件查找课程类型列表<br>
   * CreateDate: 2016年8月29日 上午9:58:06<br>
   * 
   * @category 根据条件查找课程类型列表
   * @author komi.zsy
   * @param request
   * @param paramObj
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findCourseTypeByParam")
  public List<CourseType> findCourseTypeByParam(HttpServletRequest request, CourseType paramObj)
      throws Exception {
    List<CourseType> courseTypes = courseTypeService.findListByParam(paramObj);
    return courseTypes;
  }

  /**
   * Title: 后台代订课专用课程类型查询<br>
   * Description: 后台代订课专用课程类型查询<br>
   * CreateDate: 2016年9月1日 上午10:26:29<br>
   * 
   * @category 后台代订课专用课程类型查询
   * @author komi.zsy
   * @param request
   * @param paramObj
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/findCourseTypeByBookingClass")
  public List<CourseType> findCourseTypeByBookingClass(HttpServletRequest request)
      throws Exception {
    // 代订课需要的课程类型
    String[] courseTypes = { "course_type1", "course_type2", "course_type9", "course_type11" };
    List<CourseType> courseTypeList = new ArrayList<CourseType>();
    for (String courseType : courseTypes) {
      courseTypeList.add(((CourseType) MemcachedUtil.getValue(courseType)));
    }

    return courseTypeList;
  }
}