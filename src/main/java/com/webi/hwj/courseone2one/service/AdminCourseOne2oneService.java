package com.webi.hwj.courseone2one.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.constant.WebexConstant;
import com.webi.hwj.courseone2many.service.AdminCourseOne2ManySchedulingService;
import com.webi.hwj.courseone2one.constant.CourseOneToOneSchedulingConstant;
import com.webi.hwj.courseone2one.dao.AdminCourseOne2oneDao;
import com.webi.hwj.courseone2one.entity.CourseOne2One;
import com.webi.hwj.coursetype.constant.CourseTypeConstant;
import com.webi.hwj.coursetype.dao.CourseTypeDao;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeSignDao;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.entity.TeacherTimeSign;
import com.webi.hwj.teacher.param.TeacherTimeParam;
import com.webi.hwj.teacher.param.TeacherTimeSignParam;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.util.CalendarUtil;
import com.webi.hwj.webex.service.WebexSubscribeService;

/**
 * @category courseOne2one控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminCourseOne2oneService {
  private static Logger logger = Logger.getLogger(AdminCourseOne2oneService.class);
  @Resource
  AdminCourseOne2oneDao adminCourseOne2oneDao;
  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;
  @Resource
  TeacherTimeService teacherTimeService;
  @Resource
  AdminCourseOne2ManySchedulingService adminCourseOne2ManySchedulingService;
  @Resource
  TeacherTimeSignDao teacherTimeSignDao;
  @Resource
  WebexSubscribeService webexSubscribeService;
  @Resource
  CourseTypeDao courseTypeDao;

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
  public int insert(CourseOne2One paramObj) throws Exception {
    return adminCourseOne2oneDao.insert(paramObj);
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
  public int batchInsert(List<CourseOne2One> paramObjList) throws Exception {
    return adminCourseOne2oneDao.batchInsert(paramObjList);
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
    return adminCourseOne2oneDao.delete(keyIds);
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
    return adminCourseOne2oneDao.delete(keyIds);
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
    return adminCourseOne2oneDao.deleteForReal(keyIds);
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
    return adminCourseOne2oneDao.deleteForReal(keyIds);
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
  public int update(CourseOne2One paramObj) throws Exception {
    return adminCourseOne2oneDao.update(paramObj);
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
  public int batchUpdate(List<CourseOne2One> paramObjList) throws Exception {
    return adminCourseOne2oneDao.batchUpdate(paramObjList);
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
  public int findCount(CourseOne2One paramObj) throws Exception {
    return adminCourseOne2oneDao.findCount(paramObj);
  }

  /**
   * 
   * Title: 查询1v1排课信息<br>
   * Description: 查询1v1排课信息<br>
   * CreateDate: 2016年5月4日 下午5:07:51<br>
   * 
   * @category 查询1v1排课信息
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public Page findCourseOne2oneSchedulingList(Map<String, Object> paramMap) throws Exception {
    TeacherTimeParam paramObj = new TeacherTimeParam();
    Integer page = Integer.valueOf((String) paramMap.get("page"));
    Integer rows = Integer.valueOf((String) paramMap.get("rows"));
    paramObj.setCons((String) paramMap.get("cons"));
    Page returnPage = teacherTimeEntityDao.findOne2OneSchedulingEasyui(paramObj, page, rows);
    List<TeacherTimeParam> teacherTimeParamList = (List<TeacherTimeParam>) returnPage.getDatas();
    if (teacherTimeParamList != null && teacherTimeParamList.size() > 0) {
      for (TeacherTimeParam teacherTimeParam : teacherTimeParamList) {
        String[] types = teacherTimeParam.getTeacherCourseType().split(",");
        // 将课程类型从数据库type字段转换成用户显示类型
        StringBuffer result = new StringBuffer();
        for (String type : types) {
          result
              .append(((CourseType) MemcachedUtil.getValue(type)).getCourseTypeChineseName() + ",");
        }
        result.deleteCharAt(result.length() - 1);
        teacherTimeParam.setTeacherCourseType(result.toString());
      }
    }
    return returnPage;
  }

  /**
   * Title: 一键排课<br>
   * Description: 一键排课<br>
   * CreateDate: 2016年5月5日 下午5:00:04<br>
   * 
   * @category 一键排课
   * @author komi.zsy
   * @param selectDate
   *          选择的日期（yyyy-MM-dd）
   * @param createUserId
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public boolean insertOnekeyCourseOne2oneScheduling(String selectDate, String createUserId)
      throws Exception {
    // 选中日期排课开始时间（也是第一节课的开始时间）
    String schedulingStartDate = selectDate + " "
        + CourseOneToOneSchedulingConstant.CLASS_START_TIME_BY_DAY;
    Calendar calStartClassTime = CalendarUtil.parseCalendar(schedulingStartDate);

    // 选中日期排课结束时间（选中的日期第二天的凌晨）
    Calendar calEndTime = Calendar.getInstance();
    calEndTime.setTime(calStartClassTime.getTime());
    calEndTime.add(Calendar.HOUR_OF_DAY, CourseOneToOneSchedulingConstant.CLASS_HOUR_BY_DAY);

    // 上课结束时间（也是第一节课的结束时间）
    Calendar calEndClassTime = Calendar.getInstance();
    calEndClassTime.setTime(calStartClassTime.getTime());
    calEndClassTime.add(Calendar.MINUTE, CourseOneToOneSchedulingConstant.getVcubeRoomClassTime());

    // 是否有房间（true有）
    boolean roomIsEnough = true;

    // modify by seven
    CourseType paramObj = new CourseType();
    paramObj.setCourseTypeFlag(CourseTypeConstant.COURSE_TYPE_FLAG_1V1);
    List<CourseType> courseType1v1List = courseTypeDao.findListByParam(paramObj);
    List<String> courseTypeList = new ArrayList<String>();
    if (courseType1v1List != null && courseType1v1List.size() > 0) {
      for (CourseType courseTypeTemp : courseType1v1List) {
        courseTypeList.add(courseTypeTemp.getCourseType());
      }
    }

    // 上课结束时间不大于排课结束时间，则循环排课
    while (calEndClassTime.getTimeInMillis() <= calEndTime.getTimeInMillis()) {
      if (!this.insertCourseOne2oneScheduling(calStartClassTime, calEndClassTime, createUserId,
          courseTypeList)) {
        roomIsEnough = false;
      }

      // 上课开始时间置为下一节课开始时间（增加排课间隔时间）
      calStartClassTime.add(Calendar.MINUTE,
          CourseOneToOneSchedulingConstant.getClassMinuteByClass());
      // 上课结束时间置为下一节课结束时间
      calEndClassTime.add(Calendar.MINUTE,
          CourseOneToOneSchedulingConstant.getClassMinuteByClass());
    }
    return roomIsEnough;
  }

  /**
   * Title: 插入一个时间段的1v1自动排课<br>
   * Description: 插入一个时间段的1v1自动排课<br>
   * CreateDate: 2016年5月12日 上午11:02:10<br>
   * 
   * @category 插入一个时间段的1v1自动排课
   * @author komi.zsy
   * @param calStartClassTime
   *          选中日期排课开始时间（也是第一节课的开始时间）
   * @param calEndClassTime
   *          上课结束时间（也是第一节课的结束时间）
   * @param createUserId
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public boolean insertCourseOne2oneScheduling(Calendar calStartClassTime, Calendar calEndClassTime,
      String createUserId, List<String> courseTypeList) throws Exception {

    boolean roomIsEnough = true;

    // 课程类型，这里都是1v1排课
    String courseType = "course_type1";

    // 有签课时间且没有被排课的老师
    List<TeacherTimeSignParam> teacherTimeSignList = adminCourseOne2ManySchedulingService
        .findAvailableTeacherByTime(calStartClassTime.getTime(), calEndClassTime.getTime(),
            courseTypeList);

    if (teacherTimeSignList != null && teacherTimeSignList.size() != 0) {
      // 减去房间预留时间
      calStartClassTime.add(Calendar.MINUTE,
          -CourseOneToOneSchedulingConstant.getVcubeRoomClassFrontTime("course_type1"));
      // 查询房间使用的开始时间（已经减去房间预留时间的时间，比如：上课时间为15:00，这里的时间是14:55，前提是这个预留时间为5分钟）
      Date startTimeParam = calStartClassTime.getTime();
      // 再把刚才减少的时间加回来作为插入t_teacher_time的start_time，具体逻辑问komi
      calStartClassTime.add(Calendar.MINUTE,
          CourseOneToOneSchedulingConstant.getVcubeRoomClassFrontTime("course_type1"));

      // 加上房间延后时间
      calEndClassTime.add(Calendar.MINUTE,
          CourseOneToOneSchedulingConstant.getVcubeRoomClassPostTime("course_type1"));
      // 查询房间使用的结束时间（已经加上房间延后时间的时间，比如：上课时间为15:00，这里的时间是15:05，前提是这个延后时间为5分钟）
      Date endTimeParam = calEndClassTime.getTime();
      // 再把刚才延后的时间减回来作为插入t_teacher_time的end_time，具体逻辑问komi
      calEndClassTime.add(Calendar.MINUTE,
          -CourseOneToOneSchedulingConstant.getVcubeRoomClassPostTime("course_type1"));

      // 查找可以使用的房间列表
      List<String> roomIdList = teacherTimeService
          .findWebexAvailableRoomList(WebexConstant.WEBEX_ROOM_TYPE_ONE2ONE, startTimeParam,
              endTimeParam);

      List<TeacherTime> teacherTimeList = new ArrayList<TeacherTime>();

      for (TeacherTimeSignParam teacherTimeSign : teacherTimeSignList) {
        // 房间号为空，则没有房间，需要通知负责人增加房间
        String roomId = "";
        if (roomIdList.size() > 0) {
          // 如果还有可用房间，则从集合中删除这个房间，并且赋值给roomId
          roomId = roomIdList.get(0);
          roomIdList.remove(0);
        } else {
          roomIsEnough = false;
          break;
        }

        TeacherTime teacherTime = new TeacherTime();
        teacherTime.setWebexRoomHostId(roomId);
        teacherTime.setTeacherId(teacherTimeSign.getTeacherId());
        teacherTime.setTeacherName(teacherTimeSign.getTeacherName());
        teacherTime.setStartTime(calStartClassTime.getTime());
        teacherTime.setEndTime(calEndClassTime.getTime());
        teacherTime.setCourseType(courseType);
        teacherTime.setUpdateUserId(createUserId);
        teacherTime.setCreateUserId(createUserId);

        teacherTimeList.add(teacherTime);
      }
      logger.debug("一键排课数据开始插入----->" + teacherTimeList);
      teacherTimeEntityDao.batchInsert(teacherTimeList);

      // 判断是是否需要发送报警短信信息
      boolean warnFlag = false;

      for (TeacherTime teacherTime : teacherTimeList) {
        // 异步调用生产者,生产webex会议
        // 延迟1分钟消费
        try {
          String createWebexMeetingBodyStr = WebexConstant.WEBEX_CONSUMER_TYPE_CREATE_MEETING + ","
              + teacherTime.getKeyId() + "," + teacherTime.getTeacherName() + " "
              + DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getStartTime()) + " "
              + DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getEndTime()) + " ";
          OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
              MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_webex",
              createWebexMeetingBodyStr, 60 * 1000);
        } catch (Exception e) {
          warnFlag = true;
          e.printStackTrace();
          logger.error("error:" + e.getMessage(), e);
          continue;
        }
      }

      // 报警
      if (warnFlag) {
        SmsUtil.sendAlarmSms(
            "一键排课消息队列报警" + new Date() + ",生产异常");
      }
    }
    return roomIsEnough;
  }

  /**
   * Title: 添加1v1课程<br>
   * Description: 添加1v1课程<br>
   * CreateDate: 2016年5月10日 上午11:03:24<br>
   * 
   * @category 添加1v1课程
   * @author seven.gz
   * @param teacherTimeParam
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage addCourseOne2OneScheduling(TeacherTimeParam teacherTimeParam)
      throws Exception {
    JsonMessage json = new JsonMessage();
    json.setSuccess(true);
    json.setMsg("添加成功");

    // modify by 2016年9月9日10:06:48 seven 去除1v1排课时的结束时间选择
    Date startTime = teacherTimeParam.getStartTime();
    // Date endTime = teacherTimeParam.getEndTime();
    int subscribeTime = ((CourseType) MemcachedUtil.getValue(teacherTimeParam.getCourseType()))
        .getCourseTypeDuration(); // 课程持续时间
    Date endTime = new Date(startTime.getTime() + subscribeTime * 60 * 1000);
    teacherTimeParam.setEndTime(endTime);
    // 判断开始日期是否大于等于结束日期
    // if (endTime.getTime() <= startTime.getTime()) {
    // json.setSuccess(false);
    // json.setMsg("请输入正确日期");
    // return json;
    // }

    // 查询此老师是否有可用时间（是否被老师删除）
    TeacherTimeSign teacherTimeSignPara = new TeacherTimeSign();
    teacherTimeSignPara.setStartTime(startTime);
    teacherTimeSignPara.setEndTime(endTime);
    teacherTimeSignPara.setTeacherId(teacherTimeParam.getTeacherId());
    TeacherTimeSign teacherTimeSignAvailable = teacherTimeSignDao
        .findTeacherTimeSignByDeleteTime(teacherTimeSignPara);
    if (teacherTimeSignAvailable == null) {
      json.setSuccess(false);
      json.setMsg("此教师的时间不可用，请重新选择");
      return json;
    }

    teacherTimeParam.setTeacherName(teacherTimeSignAvailable.getTeacherName());

    // 在签课老师中找出这些老师这个时间段已经被排课的老师
    List<TeacherTimeParam> teacherTimeListToRemove = teacherTimeEntityDao
        .findOverlapTimeTeachers(teacherTimeParam);

    if (teacherTimeListToRemove != null && teacherTimeListToRemove.size() > 0) {
      json.setSuccess(false);
      json.setMsg("此教师的时间已经被排课，请重新选择");
      return json;
    }
    // 因为排课房间要有预留时间所以查询时要再课程时间基础上留出这个时间
    Calendar calStartClassTimeCalendar = Calendar.getInstance();
    calStartClassTimeCalendar.setTime(startTime);
    calStartClassTimeCalendar.add(Calendar.MINUTE,
        -CourseOneToOneSchedulingConstant.getVcubeRoomClassFrontTime("course_type1"));

    Calendar calEndClassTimeCalendar = Calendar.getInstance();
    calEndClassTimeCalendar.setTime(endTime);
    calEndClassTimeCalendar.add(Calendar.MINUTE,
        CourseOneToOneSchedulingConstant.getVcubeRoomClassPostTime("course_type1"));

    // 获得老师时间实体
    List<String> roomIdList = teacherTimeService.findWebexAvailableRoomList(
        WebexConstant.WEBEX_ROOM_TYPE_ONE2ONE,
        calStartClassTimeCalendar.getTime(), calEndClassTimeCalendar.getTime());
    if (roomIdList != null && roomIdList.size() > 0) {
      String roomId = roomIdList.get(0);
      teacherTimeParam.setWebexRoomHostId(roomId);
    } else {
      json.setSuccess(false);
      json.setMsg("当前已排课时已超出可用房间，请联系技术部要求增加房间");
      return json;
    }

    TeacherTime teacherTime = new TeacherTime();
    BeanUtils.copyProperties(teacherTimeParam, teacherTime);

    // 插入老师时间表j
    teacherTimeEntityDao.insert(teacherTime);

    // modify by seven 2016年10月13日18:17:54 给terry用作测试用
    json.setData(teacherTime.getKeyId());

    // 同步生产webex会议

    int resultInt = webexSubscribeService.createWebexMeeting(teacherTime.getKeyId(),
        teacherTime.getTeacherName() + " "
            + DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getStartTime()) + " "
            + DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getEndTime()) + " ");

    if (resultInt == 0) {
      throw new RuntimeException("1v1手动排课同步调用webex接口数据操作异常");
    }

    // String createWebexMeetingBodyStr =
    // WebexConstant.WEBEX_CONSUMER_TYPE_CREATE_MEETING + ","
    // + teacherTime.getKeyId() + "," + teacherTime.getTeacherName()+" "
    // +DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getStartTime())+" "
    // +DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getEndTime())+" ";
    // OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
    // MemcachedUtil.getConfigValue("aliyun_ons_topicid"),
    // "aliyun_ons_consumerid_webex",
    // createWebexMeetingBodyStr, 60 * 1000);

    return json;
  }

  /**
   * Title: 根据课程类型查询课程<br>
   * Description: findListCourseByCourseType<br>
   * CreateDate: 2016年12月22日 下午6:22:15<br>
   * 
   * @category 根据课程类型查询课程
   * @author seven.gz
   * @param courseType
   * @return
   * @throws Exception
   */
  public List<CourseOne2One> findListCourseByCourseType(String courseType) throws Exception {
    return adminCourseOne2oneDao.findListCourseByCourseType(courseType);
  }
}