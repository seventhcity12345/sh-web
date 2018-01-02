package com.webi.hwj.teacher.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.teacher.entity.TeacherTimeSign;
import com.webi.hwj.teacher.service.TeacherTimeSignService;
import com.webi.hwj.util.SessionUtil;

/**
 * Title: 教师签课管理<br>
 * Description: 教师签课管理<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月26日 下午5:09:08
 * 
 * @author komi.zsy
 */
@Controller
@RequestMapping("/tcenter/timeSign")
public class TeacherTimeSignController {
  private static Logger logger = Logger.getLogger(TeacherTimeSignController.class);
  @Resource
  private TeacherTimeSignService teacherTimeSignService;

  /**
   * Title: 查询教师签课信息<br>
   * Description: 查询教师签课信息<br>
   * CreateDate: 2016年4月27日 上午10:50:18<br>
   * 
   * @category 查询教师签课信息
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/findTeacherTimeSignInfo", method = RequestMethod.POST)
  public JsonMessage findTeacherTimeSignInfo(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap)
      throws Exception {
    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);
    logger.debug("开始查询教师签课信息----->" + sessionTeacher.getKeyId());
    JsonMessage json = new JsonMessage(true, "Success to query！");

    if ("huanxun".equals(sessionTeacher.getThird_from())) {
      logger.debug("第三方来源教师暂时不支持签课----->");
      json.setSuccess(false);
      json.setMsg("Third party source teachers do not support the temporary sign");

      return json;
    }

    String pageStr = (String) paramMap.get("page");
    String rowsStr = (String) paramMap.get("rows");
    Integer page = null;
    Integer rows = null;
    if (!StringUtils.isEmpty(pageStr)) {
      page = Integer.parseInt(pageStr);
    }

    if (!StringUtils.isEmpty(rowsStr)) {
      rows = Integer.parseInt(rowsStr);
    }

    TeacherTimeSign teacherTimeSign = new TeacherTimeSign();
    teacherTimeSign.setTeacherId(sessionTeacher.getKeyId());
    // modified by alex+komi+seven 2016年8月2日 11:55:17
    // 因为查询的时候需要从00:00:00开始，所以出此下策。
    teacherTimeSign.setStartTime(DateUtil.strToDateYYYYMMDD(DateUtil.dateToStrYYMMDD(new Date())));

    try {
      Map<String, Object> timeMap = new HashMap<String, Object>();
      Page returnPage = teacherTimeSignService.findPageByStartTime(teacherTimeSign, page, rows);
      timeMap.put("totalPage", returnPage.getTotalPage());
      timeMap.put("timeList", returnPage.getDatas());
      json.setData(timeMap);
    } catch (Exception e) {
      json.setSuccess(false);
      json.setMsg("Fail to query！");
      logger.error(e.toString());
    }
    logger.debug("查询教师签课信息完毕----->");
    return json;
  }

  /**
   * Title: 添加教师签课信息<br>
   * Description: 添加教师签课信息<br>
   * CreateDate: 2016年4月27日 上午10:50:43<br>
   * 
   * @category 添加教师签课信息
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/insertTeacherTimeSign", method = RequestMethod.POST)
  public CommonJsonObject insertTeacherTimeSign(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap)
      throws Exception {
    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);
    logger.info("开始添加教师签课信息------->" + sessionTeacher.getAccount());
    CommonJsonObject json = new CommonJsonObject();

    if ("huanxun".equals(sessionTeacher.getThird_from())) {
      logger.debug("第三方来源教师暂时不支持签课----->");
      json.setCode(ErrorCodeEnum.TEACHER_TIME_SIGN_THIRD_FROM_ERROR.getCode());
      return json;
    }

    // 签课时间列表
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> teacherSignTimeList = (List<Map<String, Object>>) paramMap
        .get("teacherSignTimeList");
    try {
      // 添加新的签课时间
      teacherTimeSignService.insertTeacherTimeSignByAddTime(teacherSignTimeList, sessionTeacher);
    } catch (Exception e) {
      logger.error(e.toString());
      json.setCode(ErrorCodeEnum.TEACHER_TIME_SIGN_ADD_ERROR.getCode());
      json.setData(e.getMessage());
    }

    logger.info("添加教师签课信息完毕------->" + sessionTeacher.getAccount());
    return json;
  }

  /**
   * Title: 删除教师签课信息<br>
   * Description: 删除教师签课信息<br>
   * CreateDate: 2016年4月27日 上午10:50:59<br>
   * 
   * @category 删除教师签课信息
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/deleteTeacherTimeSign", method = RequestMethod.POST)
  public CommonJsonObject deleteTeacherTimeSign(HttpServletRequest request,
      @RequestBody Map<String, Object> paramMap)
      throws Exception {
    SessionTeacher sessionTeacher = SessionUtil.getSessionTeacher(request);
    logger.info("开始删除教师签课信息------->" + sessionTeacher.getAccount());
    CommonJsonObject json = new CommonJsonObject();

    if ("huanxun".equals(sessionTeacher.getThird_from())) {
      logger.debug("第三方来源教师暂时不支持签课----->");
      json.setCode(ErrorCodeEnum.TEACHER_TIME_SIGN_THIRD_FROM_ERROR.getCode());
      return json;
    }

    // 签课时间列表
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> teacherSignTimeList = (List<Map<String, Object>>) paramMap
        .get("teacherSignTimeList");

    try {
      // 删除教师签课信息
      teacherTimeSignService.deleteTeacherTimeSignByNewTime(teacherSignTimeList,
          sessionTeacher.getKeyId());
    } catch (Exception e) {
      logger.error(e.toString());
      json.setCode(ErrorCodeEnum.TEACHER_TIME_SIGN_DEL_ERROR.getCode());
      json.setData(e.getMessage());
    }

    logger.info("删除教师签课信息完毕------->" + sessionTeacher.getAccount());
    return json;
  }

}
