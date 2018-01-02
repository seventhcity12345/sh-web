package com.webi.hwj.webex.service;

import java.text.MessageFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseService;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.webex.dao.WebexRoomDao;
import com.webi.hwj.webex.entity.WebexRoom;
import com.webi.hwj.webex.util.WebexUtil;

/**
 * @category webexSubscribe控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
public class WebexSubscribeService {
  private static Logger logger = Logger.getLogger(WebexSubscribeService.class);
  @Resource
  WebexRoomDao webexRoomDao;
  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;

  @Resource
  BaseSubscribeCourseService baseSubscribeCourseService;

  /**
   * Title: 创建webex会议（结合韦博的逻辑）<br>
   * Description: 需要异步调用，需要失败轮询报警机制<br>
   * CreateDate: 2016年7月28日 下午3:31:59<br>
   * 
   * @category 创建webex会议（结合韦博的逻辑）
   * @author yangmh
   * @param teacherTimeId
   *          老师时间id
   * @param meetingTitle
   *          房间名称
   * @param
   * @throws Exception
   * @return 1成功,0失败
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public int createWebexMeeting(String teacherTimeId, String meetingTitle) throws Exception {
    TeacherTime teacherTime = teacherTimeEntityDao.findTeacherTimeByKeyIdReturnWebex(teacherTimeId);
    if (teacherTime == null) {
      throw new RuntimeException("老师时间不存在！！！,teacherTimeId=" + teacherTimeId);
    }

    WebexRoom webexRoom = webexRoomDao.findWebexRoomByHostId(teacherTime.getWebexRoomHostId());
    if (webexRoom == null) {
      throw new RuntimeException("房间不存在！！！,webexRoomHostId=" + teacherTime.getWebexRoomHostId());
    }

    // 会议开始前多久可以进入房间，单位：秒
    int openTime = ((CourseType) MemcachedUtil.getValue(teacherTime.getCourseType()))
        .getCourseTypeBeforeGoclassTime() * 60;

    long durationLong = teacherTime.getEndTime().getTime() - teacherTime.getStartTime().getTime();
    // 会议持续时间，单位：分钟
    int duration = (int) (durationLong / (60 * 1000));

    // 1.发送请求给webex服务器，获取meetingKey（会议号）
    String meetingKey = WebexUtil.createWebexMeeting(webexRoom.getWebexRequestUrl(),
        webexRoom.getWebexRoomHostId(),
        webexRoom.getWebexRoomHostPassword(), webexRoom.getWebexRoomHostEmail(),
        webexRoom.getWebexSiteId(),
        webexRoom.getWebexPartnerId(), meetingTitle, teacherTime.getStartTime(), openTime,
        duration);

    // 2.在韦博的数据库中保存数据
    teacherTime.setWebexMeetingKey(meetingKey);
    return teacherTimeEntityDao.update(teacherTime);
  }

  /**
   * Title: 创建webex会议室的host_url<br>
   * Description: createWebexMeetingHostUrl<br>
   * CreateDate: 2016年8月3日 下午5:12:59<br>
   * 
   * @category 创建webex会议室的host_url
   * @author yangmh
   * @param teacherTimeId
   * @return 1成功,0失败
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public String createWebexMeetingHostUrl(String teacherTimeId) throws Exception {
    TeacherTime teacherTime = teacherTimeEntityDao.findTeacherTimeByKeyIdReturnWebex(teacherTimeId);
    if (teacherTime == null) {
      throw new RuntimeException("teacherTime数据不存在！！！,teacherTimeId=" + teacherTimeId);
    }

    // modify by seven 修改查询房间不带isused
    WebexRoom webexRoom = webexRoomDao.findWebexRoomByHostIdWithNoIsUsed(teacherTime
        .getWebexRoomHostId());
    if (webexRoom == null) {
      throw new RuntimeException("房间不存在！！！,webexRoomHostId=" + teacherTime.getWebexRoomHostId());
    }

    // 1.发送请求给webex服务器，获取host_url（老师url）
    String hostUrl = WebexUtil.createWebexHostUrl(webexRoom.getWebexRequestUrl(),
        webexRoom.getWebexRoomHostId(),
        webexRoom.getWebexRoomHostPassword(), webexRoom.getWebexRoomHostEmail(),
        webexRoom.getWebexSiteId(),
        webexRoom.getWebexPartnerId(), teacherTime.getWebexMeetingKey());

    return hostUrl;
  }

  /**
   * Title: 创建webex会议室的join_url<br>
   * Description: createWebexMeetingJoinUrl<br>
   * CreateDate: 2016年8月4日 上午11:12:24<br>
   * 
   * @category 创建webex会议室的join_url
   * @author yangmh
   * @param subscribeId
   *          预约id
   * @param attendeeName
   *          参会者名称:名字+userCode
   * @return 1成功,0失败
   * @throws Exception
   */
  // modify by seven+alex 为解决此处和取消预约的死锁问题，去除事物
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE)
  public int createWebexMeetingJoinUrl(String subscribeId, String attendeeName) throws Exception {
    logger.info(
        "webex创建joinurl--->start,subscribeId=" + subscribeId + "&attendeeName=" + attendeeName);
    SubscribeCourse subscribeCourse = subscribeCourseEntityDao.findOneByKeyIdOnWebex(subscribeId);

    TeacherTime teacherTime = teacherTimeEntityDao
        .findTeacherTimeByKeyIdReturnWebex(subscribeCourse.getTeacherTimeId());
    if (teacherTime == null) {
      throw new RuntimeException(
          "teacherTime数据不存在！！！,teacherTimeId=" + subscribeCourse.getTeacherTimeId());
    }

    WebexRoom webexRoom = webexRoomDao.findWebexRoomByHostId(teacherTime.getWebexRoomHostId());
    if (webexRoom == null) {
      throw new RuntimeException("房间不存在！！！,webexRoomHostId=" + teacherTime.getWebexRoomHostId());
    }

    // 1.发送请求给webex服务器，获取host_url（老师url）
    String joinUrl = WebexUtil.createWebexJoinUrl(webexRoom.getWebexRequestUrl(),
        webexRoom.getWebexRoomHostId(),
        webexRoom.getWebexRoomHostPassword(), webexRoom.getWebexRoomHostEmail(),
        webexRoom.getWebexSiteId(),
        webexRoom.getWebexPartnerId(), teacherTime.getWebexMeetingKey(), attendeeName);

    // 2.给预约表设置invite_url
    subscribeCourse.setInviteUrl(joinUrl);

    int returnInt = subscribeCourseEntityDao.update(subscribeCourse);

    if (returnInt == 1) {

      CourseType courseTypeObj = (CourseType) MemcachedUtil
          .getValue(subscribeCourse.getCourseType());
      // 发送短信{0}同学,您预订了外教{1},开课时间为{2},请提前{3}分钟通过PC端登录学习。
      SmsUtil.sendSmsToQueue(subscribeCourse.getUserPhone(),
          MessageFormat.format(MemcachedUtil.getConfigValue("subscribe_success_message"),
              new String[] { subscribeCourse.getUserName(),
                  courseTypeObj.getCourseTypeChineseName(),
                  DateUtil.dateToStrYYMMDDHHMM((Date) subscribeCourse.getStartTime()),
                  courseTypeObj.getCourseTypeBeforeGoclassTime().toString() }));

      // 下发微信消息
      baseSubscribeCourseService.sendSubscribeWeixinMsg(subscribeCourse);
    }

    return returnInt;
  }

  /**
   * Title: 删除webex会议<br>
   * Description: deleteWebexMeeting<br>
   * CreateDate: 2016年8月1日 下午12:15:35<br>
   * 
   * @category 删除webex会议
   * @author yangmh
   * @param teacherTimeId
   *          老师时间id
   * @throws Exception
   */
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE)
  // public int deleteWebexMeeting(String teacherTimeId) throws Exception {
  // // 1.查询出webex预约数据
  // WebexSubscribe webexSubscribe =
  // webexSubscribeDao.findOneByKeyId(teacherTimeId);
  // if (webexSubscribe == null) {
  // throw new RuntimeException("webex预约房间删除失败！！！,teacherTimeId=" +
  // teacherTimeId);
  // }
  //
  // // 2.删除韦博自己的webex数据（必须要物理删除，因为取消后再预约就会导致主键重复）
  // int returnInt = webexSubscribeDao.deleteForReal(teacherTimeId);
  //
  // // 3.发送请求给webex服务器，删除会议（老师url）
  // WebexUtil.deleteWebexMeeting(webexSubscribe.getWebexRoomHostId(),
  // webexSubscribe.getWebexRoomHostPassword(),
  // webexSubscribe.getWebexRoomHostEmail(),
  // webexSubscribe.getWebexMeetingKey());
  // return returnInt;
  // }
}