package com.webi.hwj.courseone2one.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.service.CourseOne2OneService;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.util.SessionUtil;

/**
 * 一对一课程一步请求数据入口
 * 
 * Title: CourseOne2OneController<br>
 * Description: CourseOne2OneController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月25日 上午9:26:29
 * 
 * @author Woody
 */
@Controller
public class CourseOne2OneController {
  private static Logger logger = Logger.getLogger(CourseOne2OneController.class);
  @Resource
  private CourseOne2OneService courseOne2OneService;
  @Resource
  TellmemoreService tellmemoreService;

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
  @RequestMapping("ucenter/course/findCourseType1List")
  public CommonJsonObject findCourseType1List(HttpServletRequest request) {
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    try {
      logger.info("用户 [" + sessionUser.getKeyId() + "开始查询CourseType1列表...");

      Map<String, Object> returnMap = new HashMap<String, Object>();

      // 查询CourseType1课程列表以及rsa课程进度
      returnMap.put("courseType1List", courseOne2OneService.findCourseType1List(sessionUser));
      // 课件完成进度标准值
      returnMap.put("ratesLimit", MemcachedUtil.getConfigValue("tmm_limit_percent"));

      json.setData(returnMap);

    } catch (Exception e) {
      logger.error("选择CourseType1课程出错", e);
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }
}