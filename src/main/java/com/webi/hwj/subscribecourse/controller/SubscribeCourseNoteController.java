package com.webi.hwj.subscribecourse.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.subscribecourse.entity.SubscribeCourseNote;
import com.webi.hwj.subscribecourse.service.SubscribeCourseNoteService;
import com.webi.hwj.subscribecourse.util.AdminSubscribeCourseUtil;
import com.webi.hwj.util.SessionUtil;

/**
 * @category subscribeCourseNote控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/subscribeCourseNote")
public class SubscribeCourseNoteController {
  private static Logger logger = Logger.getLogger(SubscribeCourseNoteController.class);
  @Resource
  private SubscribeCourseNoteService subscribeCourseNoteService;

  /**
   * 
   * Title: 新增或修改预约记录<br>
   * Description: 新增或修改预约记录<br>
   * CreateDate: 2016年9月20日 下午4:49:40<br>
   * 
   * @category 新增或修改预约记录
   * @author seven.gz
   * @param request
   *          请求
   * @param subscribeCourseNote
   *          预约记录实体
   * @param result
   *          校验结果
   * @return JsonMessage
   */
  @ResponseBody
  @RequestMapping("/addOrModifyNote")
  public JsonMessage addOrModifyNote(HttpServletRequest request,
      @Valid SubscribeCourseNote subscribeCourseNote, BindingResult result) {
    JsonMessage json = new JsonMessage();
    // 表单校验框架
    if (result.hasErrors()) {
      json.setSuccess(false);
      json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
    } else {
      SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
      subscribeCourseNote.setUpdateUserId(sessionAdminUser.getKeyId());
      ;
      subscribeCourseNote.setSubscribeNoteTaker(sessionAdminUser.getAdminUserName());

      if (StringUtils.isEmpty(subscribeCourseNote.getSubscribeNote())) {
        subscribeCourseNote
            .setSubscribeNote(
                AdminSubscribeCourseUtil.noteTypeToStr(subscribeCourseNote.getSubscribeNoteType()));
      }
      try {
        json = subscribeCourseNoteService.addOrModifyNote(subscribeCourseNote);
      } catch (Exception ec) {
        ec.printStackTrace();
        logger.error("编辑记录失败 error:" + ec.getMessage(), ec);
        json.setSuccess(false);
        json.setMsg("操作失败");
      }
    }
    return json;
  }
}