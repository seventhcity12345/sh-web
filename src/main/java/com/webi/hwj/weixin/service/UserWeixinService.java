package com.webi.hwj.weixin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.ordercourse.param.OrderCourseAndOptionParam;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.weixin.constant.WeixinConstant;
import com.webi.hwj.weixin.dao.UserWeixinDao;
import com.webi.hwj.weixin.entity.UserWeixin;
import com.webi.hwj.weixin.entity.WeixinMsgOption;
import com.webi.hwj.weixin.param.BindUserParam;
import com.webi.hwj.weixin.util.WeixinUtil;

/**
 * @category userWeixin控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class UserWeixinService {
  private static Logger logger = Logger.getLogger(UserWeixinService.class);
  @Resource
  private UserWeixinDao userWeixinDao;

  @Resource
  private UserEntityDao userEntityDao;

  @Resource
  private WeiXinSubscribeService weiXinSubscribeService;

  @Resource
  private UserService userService;

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
    return userWeixinDao.delete(keyIds);
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
    return userWeixinDao.delete(keyIds);
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
    return userWeixinDao.deleteForReal(keyIds);
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
    return userWeixinDao.deleteForReal(keyIds);
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
  public int update(UserWeixin paramObj) throws Exception {
    return userWeixinDao.update(paramObj);
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
  public int batchUpdate(List<UserWeixin> paramObjList) throws Exception {
    return userWeixinDao.batchUpdate(paramObjList);
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
  public int findCount(UserWeixin paramObj) throws Exception {
    return userWeixinDao.findCount(paramObj);
  }

  public String findUserIdByOpenId(String openId) throws Exception {
    String returnUseId = null;
    List<UserWeixin> userWeixinList = userWeixinDao.findListByOpenId(openId);

    if (userWeixinList != null && userWeixinList.size() > 0) {
      UserWeixin userWeixin = userWeixinList.get(0);
      returnUseId = userWeixin.getUserId();
    }

    return returnUseId;
  }

  public List<UserWeixin> findOpenIdListByUserId(String userId) throws Exception {
    List<UserWeixin> userWeixinList = userWeixinDao.findListByUserId(userId);
    return userWeixinList;
  }

  /**
   * Title: 微信用户绑定.<br>
   * Description: bindUser<br>
   * CreateDate: 2016年10月17日 下午2:41:26<br>
   * 
   * @category 微信用户绑定.
   * @author yangmh
   * @param bindUserParam
   *          参数bean
   * @return 通用json对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject bindUser(BindUserParam bindUserParam)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 先用手机号查询用户信息
    BindUserParam bindUserParamReturn = userEntityDao
        .findUserPhotoAndIdcard(bindUserParam.getPhone());

    if (bindUserParamReturn == null) {
      // 没有用手机号查询到数据，直接返回前端错误
      json.setCode(ErrorCodeEnum.WEIXIN_USER_BIND_NOT_MATCH.getCode());
      return json;
    } else {
      // 不允许多个speakhi账号绑定一个微信号，允许多个微信号绑定多个speakhi账号。
      List<UserWeixin> userWinxinList = userWeixinDao
          .findListByUserIdAndOpenId(bindUserParamReturn.getKeyId(),
              bindUserParam.getWeixinOpenId());
      if (userWinxinList != null && userWinxinList.size() > 0) {
        json.setCode(ErrorCodeEnum.WEIXIN_USER_BIND_JUST_ONE.getCode());
        return json;
      }

      if (!StringUtils.isEmpty(bindUserParam.getIdcard())) {
        // 身份证号后六位的判断，只有参数传进来才表示是走身份证号后六位的逻辑
        if (bindUserParamReturn.getIdcard()
            .indexOf(bindUserParam.getIdcard()) == -1) {
          // 身份证号后六位不匹配
          json.setCode(ErrorCodeEnum.WEIXIN_USER_BIND_NOT_MATCH.getCode());
          return json;
        }
      } else {
        // 短信验证,目前使用前端校验

      }
    }

    // speakhi的用户的默认图片
    String normalPhoto =
        "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images%2Fuser%2Fdefault%2Fuser_default_photo.jpg";

    if (normalPhoto.equals(bindUserParamReturn.getUserPhoto())) {
      // 如果user_photo是默认值，需要把微信的头像地址复制过来
      User user = new User();
      user.setKeyId(bindUserParamReturn.getKeyId());
      user.setUserPhoto(bindUserParam.getWeixinUserPhoto());
      userEntityDao.update(user);
    }

    // 插入绑定表
    UserWeixin userWeixin = new UserWeixin();
    userWeixin.setOpenId(bindUserParam.getWeixinOpenId());
    userWeixin.setUserId(bindUserParamReturn.getKeyId());
    userWeixinDao.insert(userWeixin);

    // 要返回查出的合同进度，找komi要代码
    List<OrderCourseAndOptionParam> orderCourseList = weiXinSubscribeService
        .findUserOnlyCompleteContractList(bindUserParamReturn.getKeyId(),
            bindUserParam.getPhone());

    Map<String, Object> returnMap = new HashMap<String, Object>();
    returnMap.put("orderCourseList", orderCourseList);
    json.setData(returnMap);
    return json;
  }

  /**
   * Title: 微信用户解绑.<br>
   * Description: unbindUser<br>
   * CreateDate: 2016年11月2日 上午2:17:03<br>
   * 
   * @category 微信用户解绑
   * @author yangmh
   * @param weixinOpenId
   *          微信的openid
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject unbindUser(String weixinOpenId)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    int result = userWeixinDao.deleteByOpenId(weixinOpenId);
    if (result == 1) {
      // 删除session信息
      MemcachedUtil.deleteValue(WeixinConstant.WEIXIN_MEMCACHED_SESSION_USER + weixinOpenId);
    } else {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }
    return json;
  }

  /**
   * Title: 覆盖用户的session信息,初始化session<br>
   * . Description: updateWeixinSessionUser<br>
   * CreateDate: 2016年11月3日 上午11:48:08<br>
   * 
   * @category updateWeixinSessionUser
   * @author seven.gz
   * @param session
   *          session对象
   * @param userId
   *          用户id
   * @param weixinOpenId
   *          微信id
   */
  // modify by seven 如果不支持事物会查不到最新更新的东西
  @Transactional(propagation = Propagation.SUPPORTS)
  public SessionUser updateWeixinSessionUser(HttpSession session, String userId,
      String weixinOpenId)
          throws Exception {
    SessionUser sessionUser = userService.initSessionUser(userId, null);
    MemcachedUtil.setValue(WeixinConstant.WEIXIN_MEMCACHED_SESSION_USER + weixinOpenId,
        sessionUser, 3600);
    return sessionUser;
  }

  /**
   * Title: 发送微信推送<br>
   * Description: 发送微信推送<br>
   * CreateDate: 2016年12月20日 下午2:19:52<br>
   * 
   * @category 发送微信推送
   * @author komi.zsy
   * @param dataMap
   *          微信模板信息
   * @param userId
   *          用户id
   * @param subscribeId
   *          预约id
   */
  public void sendWeixinMsg(Map<String, Object> dataMap, String userId, String templateId,
      String subscribeId) throws Exception {
    // TODO 调用这里的地方很多，其中加上定时任务就变成n+1查询了，但是先试试，不行再从外面的list直接联查微信openid
    // TODO 很卡的话要改成消息队列
    // 给谁发,发给当前userid绑定过的oepnid列表(因为很有可能一个speakhi帐号绑定了多个微信号)
    List<UserWeixin> userWeixinList = this.findOpenIdListByUserId(userId);
    if (userWeixinList != null && userWeixinList.size() > 0) {
      // 有预约id的话则跳转查看详情的页面url，否则不需要
      if (!StringUtils.isEmpty(subscribeId)) {
        for (UserWeixin userWeixin : userWeixinList) {
          WeixinUtil.sendWeixinMsg(userWeixin.getOpenId(),
              templateId,
              MemcachedUtil.getConfigValue("speakhi_website_url")
                  + "/wechat_speakhi/views/ucenter/courseDetail.html"
                  + "?subscribeId=" + subscribeId
                  + "&weixinOpenId=" + userWeixin.getOpenId(), dataMap);
        }
      } else {
        for (UserWeixin userWeixin : userWeixinList) {
          WeixinUtil.sendWeixinMsg(userWeixin.getOpenId(),
              templateId, null, dataMap);
        }
      }
    }
  }

  /**
   * Title: 发送微信推送<br>
   * Description: 发送微信推送<br>
   * CreateDate: 2016年12月20日 下午2:19:52<br>
   * 
   * @category 发送微信推送
   * @author alex.ymh
   * @param dataMap
   *          微信模板信息
   * @param userId
   *          用户id
   * @param subscribeId
   *          预约id
   */
  public void sendWeixinMsgAll() throws Exception {
    // TODO 以后要改造成多线程，现在用户量小，先这么写

    String sendUrl = MemcachedUtil.getConfigValue("weixin_msg_all_url");
    String templateId = MemcachedUtil.getConfigValue("weixin_msg_all_template_id");

    Map<String, Object> dataMap = new HashMap<String, Object>();
    // 下发消息的消息体，根据不同的模板，不一样的写法
    dataMap.put("first",
        new WeixinMsgOption(MemcachedUtil.getConfigValue("weixin_msg_all_first")));
    // 课程名称
    dataMap.put("keyword1", new WeixinMsgOption(MemcachedUtil.getConfigValue(
        "weixin_msg_all_keyword1")));

    // 时间
    dataMap.put("keyword2",
        new WeixinMsgOption(MemcachedUtil.getConfigValue("weixin_msg_all_keyword2")));
    // 备注
    dataMap.put("remark", new WeixinMsgOption(MemcachedUtil.getConfigValue(
        "weixin_msg_all_remark")));

    List<UserWeixin> userWeixinList = userWeixinDao.findListAll();
    if (userWeixinList != null && userWeixinList.size() > 0) {
      // 有预约id的话则跳转查看详情的页面url，否则不需要

      for (UserWeixin userWeixin : userWeixinList) {
        logger.info("开始批量下发微信消息,openId=" + userWeixin.getOpenId());
        WeixinUtil.sendWeixinMsg(userWeixin.getOpenId(),
            templateId,
            sendUrl, dataMap);
      }

    }
  }

}