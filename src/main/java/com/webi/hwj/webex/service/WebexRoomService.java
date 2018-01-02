package com.webi.hwj.webex.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mucfc.fep.sdk.util.StringUtil;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseService;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.webex.dao.WebexRoomDao;
import com.webi.hwj.webex.entity.WebexRoom;
import com.webi.hwj.webex.param.DemoRoomParam;

import net.sf.json.JSONObject;

/**
 * @category webexRoom控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class WebexRoomService {
  private static Logger logger = Logger.getLogger(WebexRoomService.class);
  @Resource
  WebexRoomDao webexRoomDao;
  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;

  /**
   * Title: 新增数据<br>
   * Description: insert<br>
   * CreateDate: 2016年4月11日 上午8:57:05<br>
   * 
   * @category 新增数据
   * @author yangmh
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int insert(WebexRoom paramObj) throws Exception {
    return webexRoomDao.insert(paramObj);
  }

  /**
   * Title: 批量新增数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:04:36<br>
   * 
   * @category 批量新增数据
   * @author yangmh
   * @param paramObj
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchInsert(List<WebexRoom> paramObjList) throws Exception {
    return webexRoomDao.batchInsert(paramObjList);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:00<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(String keyIds) throws Exception {
    return webexRoomDao.delete(keyIds);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:23<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(List<String> keyIds) throws Exception {
    return webexRoomDao.delete(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(List<String> keyIds) throws Exception {
    return webexRoomDao.deleteForReal(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(String keyIds) throws Exception {
    return webexRoomDao.deleteForReal(keyIds);
  }

  /**
   * Title: 修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:40:18<br>
   * 
   * @category 修改数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return 执行成功数
   * @throws Exception
   */
  public int update(WebexRoom paramObj) throws Exception {
    return webexRoomDao.update(paramObj);
  }

  /**
   * Title: 批量修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:46:26<br>
   * 
   * @category 批量修改数据
   * @author yangmh
   * @param paramObjList
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchUpdate(List<WebexRoom> paramObjList) throws Exception {
    return webexRoomDao.batchUpdate(paramObjList);
  }

  /**
   * Title: 查询数量数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午1:09:45<br>
   * 
   * @category 查询数量数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return
   * @throws Exception
   */
  public int findCount(WebexRoom paramObj) throws Exception {
    return webexRoomDao.findCount(paramObj);
  }

  /**
   * 
   * Title: 按类型查询demo房间<br>
   * Description: findAllDemoRoomList<br>
   * CreateDate: 2017年6月26日 下午5:28:35<br>
   * 
   * @category 按类型查询demo房间
   * @author seven.gz
   * @param webexRoomType
   *          房间类型
   */
  public List<DemoRoomParam> findAllDemoRoomList(int webexRoomType) throws Exception {
    List<DemoRoomParam> roomReturnList = new ArrayList<DemoRoomParam>();
    List<WebexRoom> webexReturnList = webexRoomDao.findAllRoomList(webexRoomType);
    if (webexReturnList != null && webexReturnList.size() > 0) {
      DemoRoomParam demoRoomParam = null;
      String roomSet = "";
      JSONObject returnJson = null;
      for (WebexRoom webexRoom : webexReturnList) {
        // 获取缓存中demoWebex房间配置，格式为
        // {"webexRoomName":"Online-整点-01","courseLevelTestOptionList":[{"6:00-12:00":"string","webexMeetingKey":"123123123"}]}
        roomSet = MemcachedUtil.getConfigValue(webexRoom.getWebexRoomHostId());
        if (!StringUtil.isEmpty(roomSet)) {
          returnJson = JSONObject.fromObject(roomSet);

          demoRoomParam = new DemoRoomParam();
          demoRoomParam.setWebexRoomHostId(webexRoom.getWebexRoomHostId());
          demoRoomParam.setWebexRoomName(returnJson.getString("webexRoomName"));
          roomReturnList.add(demoRoomParam);
        }
      }

    }
    return roomReturnList;
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
  public String findMeetingKey(String teacherTimeId, String webexRoomHostId) throws Exception {
    TeacherTime teacherTime = teacherTimeEntityDao.findOneByKeyId(teacherTimeId);
    // 根据配置获取房间对应时间的会议号
    String webexMeetingKey = BaseSubscribeCourseService.findWebexMeetingKey(webexRoomHostId,
        teacherTime);
    return webexMeetingKey;
  }
}