/** 
 * File: TellMeMoreController.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.tellmemore.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年10月10日 下午4:19:16
 * @author yangmh
 */
package com.webi.hwj.tellmemore.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.util.CommentUtil;
import com.webi.hwj.coursetype.param.CourseTypeInfo;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.tellmemore.util.TellmemoreUtil;
import com.webi.hwj.user.service.SutdentLearningProgressService;
import com.webi.hwj.util.SessionUtil;

/**
 * Title: TellMeMoreController<br>
 * Description: TellMeMoreController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年10月10日 下午4:19:16
 * 
 * @author yangmh
 */
@Controller
@RequestMapping("/ucenter/tellmemore")
public class TellmemoreController {
  private static Logger logger = Logger.getLogger(TellmemoreController.class);
  @Resource
  private TellmemoreService tellmemoreService;
  @Resource
  private SutdentLearningProgressService sutdentLearningProgressService;

  /**
   * Title: 初始化tmm元数据<br>
   * Description: initTmmData<br>
   * CreateDate: 2015年10月23日 上午11:09:49<br>
   * 
   * @category 初始化tmm元数据
   * @author yangmh
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/initTmmData")
  @ResponseBody
  public JsonMessage initTmmData(HttpServletRequest request) throws Exception {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    logger.info("tellmemore------>初始化开始" + sessionUser.getPhone());
    JsonMessage json = new JsonMessage();

    try {
      tellmemoreService.initTmmData(sessionUser);
      json.setSuccess(true);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setMsg("请联系销售人员为您开通tellmemore课程体系,谢谢~");
      json.setSuccess(false);
    }

    logger.info("tellmemore------>初始化结束" + sessionUser.getPhone() + ",结果:" + json.isSuccess());
    return json;
  }

  /**
   * Title: 去做课件<br>
   * Description: 去做课件<br>
   * CreateDate: 2015年10月23日 上午11:06:47<br>
   * 
   * @category goTmm
   * @author yangmh
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/goTmm")
  @ResponseBody
  public JsonMessage goTmm(HttpServletRequest request) throws Exception {
    JsonMessage json = new JsonMessage();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    CourseTypeInfo courseTypeInfo = (CourseTypeInfo) sessionUser.getCourseTypes()
        .get("course_type6");
    try {
      if (courseTypeInfo != null) {
        // modify by seven 2016年8月31日13:58:59 判断课件是否到期
        if (CommentUtil.checkCourseTypeLimitTime(courseTypeInfo)) {
          json.setMsg(TellmemoreUtil.generateConnectionPortalUrl(sessionUser.getPhone()));
          json.setSuccess(true);
        } else {
          json.setMsg("当前" + courseTypeInfo.getCourseTypeChineseName() + "课程已经过期，到期时间为:"
              + DateUtil.dateToStrYYMMDDHHMMSS(courseTypeInfo.getLimitTime()));
          json.setSuccess(false);
        }

      } else {
        json.setMsg("您未购买核心课件,暂无法进入!");
        json.setSuccess(false);
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      json.setMsg("系统内部出现异常!");
      json.setSuccess(false);
    }
    return json;
  }

  /**
   * Title: 初始化并刷新rsa数据<br>
   * Description: 初始化并刷新rsa数据<br>
   * CreateDate: 2016年9月7日 下午12:04:29<br>
   * 
   * @category 初始化并刷新rsa数据
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/refreshTmm")
  public CommonJsonObject refreshTmm(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 初始化tmm
    tellmemoreService.initTmmData(sessionUser);

    /**
     * modified by alex.yang 2016年8月10日14:01:50 同步更新rsa课程进度封装成一个通用方法
     */
    tellmemoreService.updateTellmemorePercent(sessionUser.getPhone(), sessionUser.getKeyId(),
        sessionUser.getCurrentLevel());

    return json;
  }

  /**
   * 
   * Title: 查询核心课件日历<br>
   * Description: 查询核心课件日历<br>
   * CreateDate: 2016年9月7日 下午6:03:06<br>
   * 
   * @category 查询核心课件日历
   * @author seven.gz
   * @param request
   * @param reqestJsonMap
   * @return
   */
  @ResponseBody
  @RequestMapping("/findRsaEffectiveWorkTime")
  public CommonJsonObject findRsaEffectiveWorkTime(HttpServletRequest request,
      @RequestBody Map<String, Object> reqestJsonMap) {
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    CommonJsonObject commonJsonObject = new CommonJsonObject();
    String queryDate = (String) reqestJsonMap.get("queryDate");
    if (queryDate != null) {
      try {
        commonJsonObject.setData(sutdentLearningProgressService
            .findRsaEffectiveWorkTime(new Date(Long.valueOf(queryDate)), sessionUser.getKeyId()));
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
}
