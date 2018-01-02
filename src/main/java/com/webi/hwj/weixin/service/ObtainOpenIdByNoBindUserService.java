package com.webi.hwj.weixin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.util.ThreadPoolUtil;
import com.webi.hwj.weixin.constant.WeixinConstant;
import com.webi.hwj.weixin.dao.TeacherWeixinDao;
import com.webi.hwj.weixin.dao.UserWeixinDao;
import com.webi.hwj.weixin.entity.TeacherWeixin;
import com.webi.hwj.weixin.entity.UserWeixin;
import com.webi.hwj.weixin.entity.WeixinMsgOption;
import com.webi.hwj.weixin.util.WeixinUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Title: 向已关注但未绑定微信公众号的用户推送绑定提示消息<br>
 * Description: 向已关注但未绑定微信公众号的用户推送绑定提示消息<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年7月3日 下午5:29:18
 * 
 * @author felix.yl
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ObtainOpenIdByNoBindUserService {

  private static Logger logger = Logger.getLogger(ObtainOpenIdByNoBindUserService.class);

  @Resource
  UserWeixinDao userWeixinDao;

  @Resource
  TeacherWeixinDao teacherWeixinDao;

  @Resource
  UserWeixinService userWeixinService;

  /**
   * 
   * Title: 获取所有已关注微信服务号的用户<br>
   * Description: 该方法用来获取所有已关注微信服务号的用户的openId; <br>
   * 本次需求：<br>
   * 向已经关注了服务号,但尚未绑定服务号的用户推送提示绑定消息;<br>
   * 从所有已关注的用户中除去已经绑定的用户(包含青少和成人), 剩余的即为已关注但尚未绑定公众号的用户; <br>
   * CreateDate: 2017年7月4日 上午8:52:16<br>
   * 
   * @category 获取所有已关注微信服务号的用户
   * @author felix.yl
   * @return
   * @throws Exception
   */
  public List<String> findOpenId() throws Exception {

    String accessToken = WeixinUtil.findNormalAccessToken();// 获取用于API的accessToken(微信接口)
    List<String> openIds = new ArrayList<String>();

    String next_openid = "";// 初始化next_openid
    int total = 0;// 总关注者人数
    int count = 0;// 每次查询出的openId个数
    int flag = 0;// 循环查询openId的次数
    String action = "";

    do {
      if (flag == 0) {
        action = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;// 第一次查询时候不传next_openid
      } else {
        action = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken
            + "&next_openid=" + next_openid;
      }

      // https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
      // 参数说明：
      // 参数access_token:调用接口凭证;
      // 参数next_openid: 第一个拉取的openId,不填默认从头开始拉取;
      // 一次调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求;
      // 当公众号关注者数量超过10000时，可通过填写next_openid的值，从而多次拉取列表的方式来满足需求。具体而言，就是在调用接口时，将上一次调用得到的返回中的next_openid值，作为下一次调用中的next_openid值。
      String result = HttpClientUtil.doGetReturnString(action);
      JSONObject jsonObj = new JSONObject();
      jsonObj = jsonObj.fromObject(result);
      JSONObject json1 = new JSONObject();
      json1 = json1.fromObject(jsonObj.get("data").toString());
      JSONArray json2 = new JSONArray();
      json2 = json2.fromObject(json1.get("openid").toString());

      for (int i = 0; i < json2.size(); i++) {
        openIds.add(i, json2.getString(i));
      }
      next_openid = jsonObj.get("next_openid").toString();// 获取上次调用返回的next_openid
      total = (int) jsonObj.get("total");// 获取总关注者人数
      count = (int) jsonObj.get("count");// 本次查询出的openId个数
      flag++;
    } while (count == 10000 && total > flag * 10000);

    return openIds;
  }

  /**
   * 
   * Title: 已关注且绑定的成人学员<br>
   * Description: 该方法用来获取所有"已关注且绑定"微信服务号的用户的openId;(成人-学员)<br>
   * 说明：<br>
   * 该项目为成人项目,和speakHi成人项目使用同一套数据库,所以成人这边绑定了微信服务号的所有openId可以直接在该项目中获取;<br>
   * 查询t_user_weixin、t_user_teacher表,获取该表中所有is_used=1的记录得openId<br>
   * (注意： 已关注但未绑定用户的openId不会保存到我们数据库中来)<br>
   * CreateDate: 2017年7月6日 下午2:07:22<br>
   * 
   * @category 已关注且绑定的成人学员
   * @author felix.yl
   * @return
   * @throws Exception
   */
  public List<UserWeixin> findAlreadyBindOpenIdUser() throws Exception {
    List<UserWeixin> openIdListBySpeakHiUser = userWeixinDao.findOpenIdAll();// 成人绑定微信服务号的openId(学员)
    return openIdListBySpeakHiUser;
  }

  /**
   * 
   * Title: 已关注且绑定的成人教师<br>
   * Description: 该方法用来获取所有"已关注且绑定"微信服务号的用户的openId;(成人-教师)<br>
   * CreateDate: 2017年7月6日 下午2:07:22<br>
   * 
   * @category 已关注且绑定的成人教师
   * @author felix.yl
   * @return
   * @throws Exception
   */
  public List<TeacherWeixin> findAlreadyBindOpenIdTeacher() throws Exception {
    List<TeacherWeixin> openIdListBySpeakHiTeacher = teacherWeixinDao.findOpenIdAll();
    return openIdListBySpeakHiTeacher;
  }

  /**
   * 
   * Title: 已关注且绑定的学员(青少)<br>
   * Description: 该方法用来获取所有"已关注且绑定"微信服务号的用户的openId;(青少-只有学员-接口调用)<br>
   * 说明：<br>
   * 该项目为成人微信项目,而我们现在要想获取的是青少数据库中所有已经绑定微信服务号的openId,<br>
   * 所以此处我们需要做一个接口,去调用青少项目中的方法, 从而去查询青少数据库中已绑定微信服务号的openId;<br>
   * 调用青少微信接口 CreateDate: 2017年7月6日 下午2:33:33<br>
   * 
   * @category 已关注且绑定的学员(青少)
   * @author officer
   * @return
   * @throws Exception
   */
  public List<UserWeixin> findAlreadyBindOpenIdByTeenager() throws Exception {

    List<UserWeixin> openIdListByTeenager = new ArrayList<UserWeixin>();

    // 查询码表,获取调用青少接口时的URL前缀
    String configValue = MemcachedUtil.getConfigValue("find_teenager_openid_interface_url");
    // String configValue = "http://localhost:8899";//本地测试用

    // 调用青少接口查询所有的openId,返回String
    String resultJsonStr = HttpClientUtil.doGetReturnString(configValue
        + "/teenager/openId/findAllOpenId");

    // 将String转换为JSON数组
    JSONArray jsonArray = JSONArray.fromObject(resultJsonStr);

    // 将JSON数组转换为list集合
    openIdListByTeenager = (List) JSONArray.toList(jsonArray, new UserWeixin(), new JsonConfig());

    return openIdListByTeenager;
  }

  /**
   * 
   * Title: 整合出需要推送消息的所有openId<br>
   * Description:<br>
   * 该方法逻辑：<br>
   * 从查询到所有关注微信服务号的openId中,除去已经绑定了微信公众号的openId,<br>
   * 给剩余的用户(关注但未绑定微信服务号的用户)发送提示消息,让绑定微信服务号;<br>
   * 此方法即整合出需要推送消息的所有openId,储存在list集合中;<br>
   * CreateDate: 2017年7月6日 下午6:08:28<br>
   * 
   * @category 整合出需要推送消息的所有openId
   * @author officer
   * @return
   * @throws Exception
   */
  public List<String> findListOperationsendMessage() throws Exception {

    // 所有关注微信服务号的openId集合(集合中存String型)
    List<String> openIdAllList = findOpenId();

    // 绑定的成人-学员(集合中存UserWeixin对象)
    List<UserWeixin> openIdBindUserList = findAlreadyBindOpenIdUser();
    Map<String, String> openIdBindUserMap = new HashMap<String, String>();// 1
    for (int i = 0; i < openIdBindUserList.size(); i++) {
      openIdBindUserMap.put(openIdBindUserList.get(i).getOpenId(), openIdBindUserList.get(i)
          .getOpenId());
    }

    // 绑定的成人-教师(集合中存TeacherWeixin对象)
    List<TeacherWeixin> openIdBindTeacherList = findAlreadyBindOpenIdTeacher();
    Map<String, String> openIdBindTeacherMap = new HashMap<String, String>();// 2
    for (int i = 0; i < openIdBindTeacherList.size(); i++) {
      openIdBindTeacherMap.put(openIdBindTeacherList.get(i).getOpenId(), openIdBindTeacherList.get(
          i).getOpenId());
    }

    // 绑定的青少-只有学员-接口调用(集合中存TeacherWeixin对象)
    List<UserWeixin> openIdBindTeenagerUserList = findAlreadyBindOpenIdByTeenager();
    Map<String, String> openIdBindUserTeenagerMap = new HashMap<String, String>();// 3
    for (int i = 0; i < openIdBindTeenagerUserList.size(); i++) {
      openIdBindUserTeenagerMap.put(openIdBindTeenagerUserList.get(i).getOpenId(),
          openIdBindTeenagerUserList.get(i).getOpenId());
    }

    /**
     * 移除操作
     */
    for (int i = 0; i < openIdAllList.size(); i++) {
      if (null != openIdBindUserMap.get(openIdAllList.get(i)) || null != openIdBindTeacherMap.get(
          openIdAllList.get(i)) || null != openIdBindUserTeenagerMap.get(openIdAllList.get(i))) {
        openIdAllList.remove(i);
      }
    }
    return openIdAllList;
  }

  /**
   * 
   * Title: 微信推送-提醒用户绑定微信公众号<br>
   * Description: sendMessageRemindBind<br>
   * CreateDate: 2017年7月7日 上午10:33:04<br>
   * 
   * @category 微信推送-提醒用户绑定微信公众号
   * @author felix.yl
   */
  public void sendMessageRemindBind() {
    try {
      // 所有需要推送消息的用户的openId
      List<String> sendMessagList = findListOperationsendMessage();

      // 构建dataMap(下发消息的消息体,与消息模板对应:此处复用了"课程提醒"这个模板)
      Map<String, Object> dataMap = new HashMap<String, Object>();
      dataMap.put("first", new WeixinMsgOption("你好，请各位学员绑定此公众号"));// 标题
      dataMap.put("keyword1", new WeixinMsgOption("韦博嗨英语成人/青少年课程"));// 关键字1:课程
      dataMap.put("keyword2", new WeixinMsgOption("所有学员"));// 关键字2:参加人
      dataMap.put("remark", new WeixinMsgOption("一键绑定，便能享受更加及时、便捷的微信端订课等课程服务"));

      // 获取模板Id(从码表中获取)
      String weixinMsgTemplateId = WeixinConstant
          .getWeixinMsgTemplateIdBindWarn();

      for (int i = 0; i < sendMessagList.size(); i++) {
        // 调用公共微信推送方法-推送消息(暂时写成单线程)
        WeixinUtil.sendWeixinMsg(sendMessagList.get(i),
            weixinMsgTemplateId, null, dataMap);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("error:" + ex.toString(), ex);
    }
  }

  /**
   * 
   * Title: 微信推送-提醒用户绑定微信公众号【多线程】<br>
   * Description: sendMessageRemindBind<br>
   * CreateDate: 2017年7月7日 上午10:33:04<br>
   * 
   * @category 微信推送-提醒用户绑定微信公众号【多线程】
   * @author felix.yl
   */
  public void sendMessageRemindBindThread() {
    try {
      // 所有需要推送消息的用户的openId
      final List<String> sendMessagList = findListOperationsendMessage();

      // 构建dataMap(下发消息的消息体,与消息模板对应:此处复用了"课程提醒"这个模板)
      final Map<String, Object> dataMap = new HashMap<String, Object>();
      dataMap.put("first", new WeixinMsgOption("你好，请各位学员绑定此公众号"));// 标题
      dataMap.put("keyword1", new WeixinMsgOption("韦博嗨英语成人/青少年课程"));// 关键字1:课程
      dataMap.put("keyword2", new WeixinMsgOption("所有学员"));// 关键字2:参加人
      dataMap.put("remark", new WeixinMsgOption("一键绑定，便能享受更加及时、便捷的微信端订课等课程服务"));

      // 获取模板Id(从码表中获取)
      final String weixinMsgTemplateId = WeixinConstant
          .getWeixinMsgTemplateIdBindWarn();

      // 线程池设置为最大线程数为20,此处我设置线程数为10,创建10个线程
      Integer threadNum = WeixinConstant.THREAD_SUMNUM_BY_WEIXIN;
      for (int i = 0; i < threadNum; i++) {
        final int ii = i; // 放中间，每次给final赋值
        final int threadNumF = threadNum; // 放中间，每次给final赋值
        ThreadPoolUtil.getThreadPool().execute(new Runnable() {
          public void run() {
            try {
              int k = ii * sendMessagList.size() / 10;
              int size = sendMessagList.size() / 10 * (ii + 1);
              if (ii == threadNumF - 1) {
                size = sendMessagList.size();
              }
              for (int j = k; j < size; j++) {
                // 调用公共微信推送方法-推送消息(多线程)
                WeixinUtil.sendWeixinMsg(sendMessagList.get(j),
                    weixinMsgTemplateId, null, dataMap);
              }
            } catch (Exception e) {
              e.printStackTrace();
              logger.error("error:" + e.toString());
            }
          }
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.toString());
    }

  }

}
