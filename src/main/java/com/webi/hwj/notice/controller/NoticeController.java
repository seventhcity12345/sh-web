package com.webi.hwj.notice.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.CourseConstant;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.notice.constant.NoticeConstant;
import com.webi.hwj.notice.entity.Notice;
import com.webi.hwj.notice.param.RetrunNoticeDataParam;
import com.webi.hwj.notice.service.NoticeService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @category notice控制类
 * @author mingyisoft代码生成工具
 *
 */
@Api(description = "公告相关接口")
@Controller
public class NoticeController {
  private static Logger logger = Logger.getLogger(NoticeController.class);
  @Resource
  private NoticeService noticeService;

  @InitBinder
  protected void initBinder(HttpServletRequest request,
      ServletRequestDataBinder binder) throws Exception {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    // false是不允许字符串为空，true则允许
    CustomDateEditor editor = new CustomDateEditor(df, false);
    binder.registerCustomEditor(Date.class, editor);
  }

  /**
   * Title: 公告栏主页<br>
   * Description: 公告栏主页<br>
   * CreateDate: 2016年7月29日 下午1:39:31<br>
   * 
   * @category 公告栏主页
   * @author komi.zsy
   * @param model
   * @return
   */
  @RequestMapping("/admin/notice/index")
  public String index() {
    return "admin/base/admin_notice";
  }

  /**
   * Title: 查询公告栏信息<br>
   * Description: 查询公告栏信息<br>
   * CreateDate: 2016年7月29日 上午11:00:34<br>
   * 
   * @category 查询公告栏信息
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/admin/notice/pagelist")
  public Map<String, Object> pagelist(HttpServletRequest request) throws Exception {
    logger.debug("查询公告栏信息————————>");

    String sort = request.getParameter("sort");
    String order = request.getParameter("order");
    String pageStr = request.getParameter("page");
    String rowsStr = request.getParameter("rows");
    Integer page = null;
    Integer rows = null;
    if (!StringUtils.isEmpty(pageStr)) {
      page = Integer.parseInt(pageStr);
    }

    if (!StringUtils.isEmpty(rowsStr)) {
      rows = Integer.parseInt(rowsStr);
    }

    String cons = request.getParameter("cons");

    Map<String, Object> responseMap = new HashMap<String, Object>();
    Page p = noticeService.findNoticePageEasyui(cons, sort, order, page, rows);
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());

    logger.debug("查询公告栏信息结束————————>" + p);
    return responseMap;
  }

  /**
   * Title: 新增/编辑公告<br>
   * Description: 新增/编辑公告<br>
   * CreateDate: 2016年7月29日 上午11:16:07<br>
   * 
   * @category 新增/编辑公告
   * @author komi.zsy
   * @param request
   * @param noticeParam
   * @param result
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/admin/notice/updateNotice")
  public CommonJsonObject updateNotice(HttpServletRequest request, @Valid Notice notice,
      BindingResult result) throws Exception {
    CommonJsonObject json = new CommonJsonObject(ErrorCodeEnum.SUCCESS.getCode());

    try {
      // 表单校验框架
      if (result.hasErrors()) {
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg(result.getAllErrors().get(0).getDefaultMessage() + "");
        return json;
      }

      SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

      // 结束时间要存选中日期的23.59.59
      notice.setNoticeEndTime(new Date(notice.getNoticeEndTime().getTime()
          + CourseConstant.ONE_DAY_MILLISECOND - 1000));
      // 开始时间必须小于结束时间
      if (notice.getNoticeStartTime().getTime() >= notice.getNoticeEndTime().getTime()) {
        json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
        json.setMsg("开始生效时间必须小于结束生效时间");
        return json;
      }

      // 新增
      if (StringUtils.isEmpty(notice.getKeyId())) {
        notice.setAdminUserName(sessionAdminUser.getAdminUserName());
        notice.setCreateUserId(sessionAdminUser.getKeyId());
        notice.setUpdateUserId(sessionAdminUser.getKeyId());

        logger.info("开始新增公告信息--->" + notice);

        noticeService.insert(notice);
      }
      // 编辑
      else {
        notice.setUpdateUserId(sessionAdminUser.getKeyId());

        logger.info("开始编辑公告信息--->" + notice);

        noticeService.update(notice);
      }

      // 如果是banner修改，则初始化缓存
      if (notice.getNoticeType() == NoticeConstant.NOTICE_TYPE_BANNER) {
        noticeService.setMemcached(NoticeConstant.NOTICE_TYPE_BANNER);
      } else {
        noticeService.setMemcached(NoticeConstant.NOTICE_TYPE_NOTICE);
      }
    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.UPDATE_ERROR.getCode());
      logger.error("更新公告失败！");
    }

    logger.info("更新公告信息完毕--->" + json);

    return json;
  }

  /**
   * Title: 删除公告<br>
   * Description: 删除公告<br>
   * CreateDate: 2016年8月1日 上午10:17:41<br>
   * 
   * @category 删除公告
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/admin/notice/deleteNotice")
  public CommonJsonObject deleteNotice(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject(ErrorCodeEnum.SUCCESS.getCode());
    Map<String, Object> paramMap = RequestUtil.getParameterMap(request);
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

    logger.info("开始删除公告信息--->" + paramMap);

    try {
      int count = noticeService.batchDeleteNotice(paramMap, sessionAdminUser.getKeyId());
      json.setData(count);
      logger.debug("批量删除成功！");

      noticeService.setMemcached(NoticeConstant.NOTICE_TYPE_BANNER);
      noticeService.setMemcached(NoticeConstant.NOTICE_TYPE_NOTICE);

    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.NOTICE_DELETE_ERROR.getCode());
      logger.error("批量删除失败！");
    }

    logger.info("删除公告信息完毕--->" + json);

    return json;
  }

  /**
   * Title: 查找当前生效中的banner信息<br>
   * Description: 查找当前生效中的banner信息<br>
   * CreateDate: 2016年12月19日 下午5:37:22<br>
   * 
   * @category 查找当前生效中的banner信息
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/banners", method = RequestMethod.GET)
  public CommonJsonObject findBannerInfo(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 从缓存中读取banner信息
    List<Notice> noticeList = (List<Notice>) MemcachedUtil.getValue(ConfigConstant.BANNER_LIST);
    if (noticeList != null && noticeList.size() != 0) {
      List<Notice> returnList = new ArrayList<>();
      for (Notice notice : noticeList) {
        // 因为放入缓存的时候可能还没过期或生效，但是现在可能生效了。
        if (notice.getNoticeStartTime().getTime() <= new Date().getTime()
            && notice.getNoticeEndTime().getTime() >= new Date().getTime()) {
          returnList.add(notice);
        }
      }

      json.setData(returnList);
    }
    return json;
  }

  /**
   * Title: 前端查找公告信息<br>
   * Description: 前端查找公告信息<br>
   * CreateDate: 2016年8月1日 上午10:43:34<br>
   * 
   * @category 前端查找公告信息
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "前端查找公告信息", notes = "前端查找公告信息,包含今日es课程信息")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/noticesInfo", method = RequestMethod.GET)
  public CommonJsonObject<RetrunNoticeDataParam> findNoticeInfo() throws Exception {
    CommonJsonObject<RetrunNoticeDataParam> json = new CommonJsonObject<RetrunNoticeDataParam>();

    RetrunNoticeDataParam retrunNoticeDataParam = new RetrunNoticeDataParam();
    retrunNoticeDataParam.setNoticeList(noticeService.findNoticeListNew(new Date()));
    json.setData(retrunNoticeDataParam);

    return json;
  }
}