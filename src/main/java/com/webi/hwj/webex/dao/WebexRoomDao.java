package com.webi.hwj.webex.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.webex.entity.WebexRoom;

@Repository
public class WebexRoomDao extends BaseEntityDao<WebexRoom> {
  private static Logger logger = Logger.getLogger(WebexRoomDao.class);

  /**
   * modify by seven demo需要查询不是可以排课的房间
   */
  private static final String FIND_ONE_BY_HOSTID_WITH_NO_IS_USED =
      " SELECT webex_room_host_id,webex_room_host_password,webex_room_host_email,"
          + " webex_site_id,webex_partner_id,webex_request_url "
          + " FROM t_webex_room WHERE webex_room_host_id = :WebexRoomHostId ";

  /**
   * Title: 通过房间host帐号查询房间<br>
   * Description: findWebexRoomByHostId<br>
   * CreateDate: 2016年7月28日 下午3:21:20<br>
   * 
   * @category 通过房间host帐号查询房间
   * @author yangmh
   * @param hostId
   * @return
   * @throws Exception
   */
  public WebexRoom findWebexRoomByHostId(String hostId) throws Exception {
    WebexRoom paramObj = new WebexRoom();
    paramObj.setWebexRoomHostId(hostId);
    return super.findOne(paramObj,
        "webex_room_host_id,webex_room_host_password,webex_room_host_email,webex_site_id,webex_partner_id,webex_request_url,room_time_filter");
  }

  /**
   * Title: 查询房间列表<br>
   * Description: findAllRoomList<br>
   * CreateDate: 2016年8月3日 下午3:15:21<br>
   * 
   * @category 查询房间列表
   * @author yangmh
   * @param webexRoomType
   *          房间类型(0:1v1,1:1v6)
   * @return
   * @throws Exception
   */
  public List<WebexRoom> findAllRoomList(int webexRoomType) throws Exception {
    WebexRoom paramObj = new WebexRoom();
    paramObj.setWebexRoomType(webexRoomType);
    return super.findList(paramObj, "webex_room_host_id");
  }

  /**
   * Title: 通过房间host帐号查询房间.<br>
   * Description: findWebexRoomByHostId<br>
   * CreateDate: 2016年7月28日 下午3:21:20<br>
   * 
   * @category 通过房间host帐号查询房间
   * @author yangmh
   * @param hostId
   *          房间号
   */
  public WebexRoom findWebexRoomByHostIdWithNoIsUsed(String hostId) throws Exception {
    WebexRoom paramObj = new WebexRoom();
    paramObj.setWebexRoomHostId(hostId);
    return super.findOne(FIND_ONE_BY_HOSTID_WITH_NO_IS_USED, paramObj);
  }
}