package com.webi.hwj.webex.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webi.hwj.constant.WebexConstant;
import com.webi.hwj.webex.param.DemoRoomParam;
import com.webi.hwj.webex.service.WebexRoomService;

/**
 * 
 * Title: AdminWebexSubscribeController<br>
 * Description: AdminWebexSubscribeController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年6月26日 下午5:24:46
 * 
 * @author seven.gz
 */
@Controller
public class AdminWebexSubscribeController {
  @Resource
  WebexRoomService webexRoomService;

  /**
   * 
   * Title: 查询demo房间<br>
   * Description: findListDemoRoom<br>
   * CreateDate: 2017年6月26日 下午5:31:36<br>
   * 
   * @category 查询demo房间
   * @author seven.gz
   */
  @ResponseBody
  @RequestMapping(value = "admin/webex/findListDemoRoom")
  public List<DemoRoomParam> findListDemoRoom(HttpServletRequest request) throws Exception {
    return webexRoomService.findAllDemoRoomList(WebexConstant.WEBEX_ROOM_TYPE_DEMO);
  }

  /**
   * 
   * Title: 查询房间指定时间时间对应的房间号<br>
   * Description: findMeetingKey<br>
   * CreateDate: 2017年8月9日 下午2:44:19<br>
   * 
   * @category 查询房间指定时间时间对应的房间号
   * @author seven.gz
   * @param teacherTimeId
   *          老师时间id
   * @param webexRoomHostId
   *          webex房间号
   */
  @ResponseBody
  @RequestMapping(value = "admin/webex/findMeetingKey")
  public String findMeetingKey(String teacherTimeId,
      String webexRoomHostId) throws Exception {
    return webexRoomService.findMeetingKey(teacherTimeId, webexRoomHostId);
  }

}
