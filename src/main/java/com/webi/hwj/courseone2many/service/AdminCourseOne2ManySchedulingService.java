package com.webi.hwj.courseone2many.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.classin.service.ClassinService;
import com.webi.hwj.classin.util.ClassinUtil;
import com.webi.hwj.constant.WebexConstant;
import com.webi.hwj.courseone2many.dao.AdminCourseOne2ManyDao;
import com.webi.hwj.courseone2many.dao.AdminCourseOne2ManySchedulingDao;
import com.webi.hwj.courseone2many.entity.CourseOne2Many;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.courseone2many.param.CourseOne2ManySchedulingParam;
import com.webi.hwj.courseone2one.constant.CourseOneToOneSchedulingConstant;
import com.webi.hwj.coursetype.constant.CourseTypeConstant;
import com.webi.hwj.coursetype.dao.CourseTypeDao;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.gensee.constant.GenseeConstant;
import com.webi.hwj.gensee.entity.Gensee;
import com.webi.hwj.gensee.service.GenseeService;
import com.webi.hwj.gensee.util.GenseeUtil;
import com.webi.hwj.huanxun.service.HuanxunService;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.teacher.constant.TeacherTimeConstant;
import com.webi.hwj.teacher.dao.TeacherEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeSignDao;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.entity.TeacherTimeSign;
import com.webi.hwj.teacher.param.TeacherTimeParam;
import com.webi.hwj.teacher.param.TeacherTimeSignParam;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.util.UUIDUtil;
import com.webi.hwj.webex.service.WebexSubscribeService;

/**
 * @category courseOne2manyScheduling控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AdminCourseOne2ManySchedulingService {
  private static Logger logger = Logger.getLogger(AdminCourseOne2ManySchedulingService.class);
  @Resource
  AdminCourseOne2ManySchedulingDao adminCourseOne2ManySchedulingDao;
  @Resource
  TeacherTimeDao teacherTimeDao;
  @Resource
  TeacherTimeSignDao teacherTimeSignDao;
  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;
  @Resource
  TeacherEntityDao teacherEntityDao;
  @Resource
  AdminCourseOne2ManyDao adminCourseOne2ManyDao;
  @Resource
  TeacherTimeService teacherTimeService;
  @Resource
  GenseeService genseeService;
  @Resource
  WebexSubscribeService webexSubscribeService;
  @Resource
  HuanxunService huanxunService;
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  CourseTypeDao courseTypeDao;
  @Resource
  ClassinService classinService;

  /**
   * key_id的长度
   */
  public final static int KEY_ID_SIZE = 32;

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
  public int insert(CourseOne2ManyScheduling paramObj) throws Exception {
    return adminCourseOne2ManySchedulingDao.insert(paramObj);
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
  public int batchInsert(List<CourseOne2ManyScheduling> paramObjList) throws Exception {
    return adminCourseOne2ManySchedulingDao.batchInsert(paramObjList);
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
    return adminCourseOne2ManySchedulingDao.delete(keyIds);
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
    return adminCourseOne2ManySchedulingDao.delete(keyIds);
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
    return adminCourseOne2ManySchedulingDao.deleteForReal(keyIds);
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
    return adminCourseOne2ManySchedulingDao.deleteForReal(keyIds);
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
  public int update(CourseOne2ManyScheduling paramObj) throws Exception {
    return adminCourseOne2ManySchedulingDao.update(paramObj);
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
  public int batchUpdate(List<CourseOne2ManyScheduling> paramObjList) throws Exception {
    return adminCourseOne2ManySchedulingDao.batchUpdate(paramObjList);
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
  public int findCount(CourseOne2ManyScheduling paramObj) throws Exception {
    return adminCourseOne2ManySchedulingDao.findCount(paramObj);
  }

  /**
   * 
   * Title: 查询排课列表<br>
   * Description: 查询排课列表<br>
   * CreateDate: 2016年4月26日 上午9:36:56<br>
   * 
   * @category 查询排课列表
   * @author seven.gz
   * @param param
   * @return
   * @throws Exception
   */
  public Page findSchedulingList(Map<String, Object> param) throws Exception {
    // 查询排课信息
    Page p = adminCourseOne2ManySchedulingDao.findSchedulingList(param);
    List<CourseOne2ManySchedulingParam> datas = p.getDatas();
    if (datas != null) {
      for (CourseOne2ManySchedulingParam element : datas) {
        // 将课程类型格式化为 中文解释
        if (element.getCourseType() != null) {
          element.setCourseTypeName(((CourseType) MemcachedUtil.getValue(element.getCourseType()))
              .getCourseTypeChineseName());
        }
      }
    }
    return p;
  }

  /**
   * 
   * Title: 删除大课排课信息<br>
   * Description: 删除大课排课信息<br>
   * CreateDate: 2016年4月26日 下午3:02:09<br>
   * 
   * @category 删除大课排课信息
   * @author seven.gz
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = {
          Exception.class })
  public JsonMessage deleteScheduling(String schedulingId, String teacherTimeId, String courseType)
      throws Exception {
    JsonMessage json = new JsonMessage();
    // 根据teacherTimeId判断该教师时间是否已经预约
    Map<String, Object> teacherTimeMap = new HashMap<String, Object>();
    teacherTimeMap.put("teacher_time_id", teacherTimeId);

    // 查询老师时间
    TeacherTime teacherTime = teacherTimeEntityDao.findOneByKeyId(teacherTimeId);

    // 如果已经被预约
    if (teacherTime.getIsSubscribe()) {
      json.setSuccess(false);
      json.setMsg("该节课已被预约，无法删除课程，请先取消预约");
      // 没有被预约
    } else {
      // 删除大课排课表和老师时间表的数据
      adminCourseOne2ManySchedulingDao.delete(schedulingId);
      teacherTimeDao.delete(teacherTimeId);
      json.setSuccess(true);
      json.setMsg("删除成功");

      // 如果是classin则需要调用接口删除
      if (TeacherTimeConstant.TEACHER_TIME_PLATFORM_CLASSIN.equals(teacherTime
          .getTeacherTimePlatform())) {
        ClassinUtil.deleteRoom(teacherTime.getRoomId());
      }
    }

    CourseOne2ManyScheduling courseOne2ManyScheduling = adminCourseOne2ManySchedulingDao
        .findOneByKeyIdWithNoIsUsed(schedulingId);
    Teacher teacher = teacherEntityDao.findOneByKeyId(courseOne2ManyScheduling.getTeacherId());
    // 如果是环迅老师 则需要调用环迅接口取消1vn时间
    if ("huanxun".equals(teacher.getThirdFrom())) {

      // 如果是环迅的老师，需要调用环迅的接口
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("start_time",
          DateUtil.dateToStrYYMMDDHHMMSS(courseOne2ManyScheduling.getStartTime()));
      paramMap.put("end_time",
          DateUtil.dateToStrYYMMDDHHMMSS(courseOne2ManyScheduling.getEndTime()));
      paramMap.put("teacher_id", courseOne2ManyScheduling.getTeacherId());

      String returnCode = huanxunService.huanxunCancelOne2Many(paramMap);
      if (!"200".equals(returnCode)) {
        throw new RuntimeException("预约环迅老师1vn出错!");
      }
    }
    return json;
  }

  /**
   * 
   * Title: 根据时间找出签过这个时间的且没有没排课的老师<br>
   * Description: 根据时间找出签过这个时间的且没有没排课的老师<br>
   * CreateDate: 2016年4月28日 上午11:44:53<br>
   * 
   * @category 根据时间找出签过这个时间的且没有没排课的老师
   * @author seven.gz
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public List<TeacherTimeSignParam> findAvailableTeacherByTime(Date startTime, Date endTime,
      List<String> courseTypeList)
          throws Exception {
    // modified by alex+seven+komi 2016年8月2日 10:36:35 三人优化过这里的date转string
    // 判断开始日期是否大于等于结束日期
    if (endTime.getTime() <= startTime.getTime()) {
      return null;
    }

    // 老师签课时间查询参数
    TeacherTimeSignParam teacherTimeSignParam = new TeacherTimeSignParam();
    teacherTimeSignParam.setStartTime(startTime);
    teacherTimeSignParam.setEndTime(endTime);

    // // 用于模糊查询
    // /**
    // * modified by komi 2016年6月27日14:55:42
    // * 如果courseType=course_type9，Lecture1v1课程时，改为查找course_type1
    // */
    // if ("course_type9".equals(courseType)) {
    // teacherTimeSignParam.setTeacherCourseType("course_type1");
    // } else {
    // teacherTimeSignParam.setTeacherCourseType(courseType);
    // }

    // modify by seven 2016年12月29日10:01:09
    // core1v1，ext1v1，demo1v1的类型单独出来，都可以在1v1排课的下拉框中过滤出来。
    List<TeacherTimeSignParam> teacherTimeSignList = null;
    if (courseTypeList != null && courseTypeList.size() > 0) {
      // 找出签过参数时间段的有此类型上课权限的老师
      teacherTimeSignList = teacherTimeSignDao
          .findTeacherSignByTime(teacherTimeSignParam);
      // 过滤老师 需要的老师
      Iterator<TeacherTimeSignParam> it = teacherTimeSignList.iterator();
      Boolean removeFlag = true;
      while (it.hasNext()) {
        TeacherTimeSignParam teacherTimeSignParamTemp = it.next();
        removeFlag = true;
        for (String courseTypeStr : courseTypeList) {
          if (teacherTimeSignParamTemp.getTeacherCourseType().indexOf(courseTypeStr) != -1) {
            removeFlag = false;
            break;
          }
        }
        if (removeFlag) {
          it.remove();
        }
      }
    }

    // 存放老师时间
    Map<String, TeacherTimeSignParam> teacherTimeSignMap =
        new LinkedHashMap<String, TeacherTimeSignParam>();
    List<String> teacherIds = new ArrayList<String>();
    if (teacherTimeSignList != null) {
      for (TeacherTimeSignParam e : teacherTimeSignList) {
        teacherTimeSignMap.put(e.getTeacherId(), e);
        teacherIds.add(e.getTeacherId());
      }
    }
    // 老师时间查询参数
    TeacherTimeParam teacherTimeParam = new TeacherTimeParam();
    teacherTimeParam.setStartTime(startTime);
    teacherTimeParam.setEndTime(endTime);
    teacherTimeParam.setTeacherIds(teacherIds);
    // 在签课老师中找出这些老师这个时间段已经被排课的老师
    if (teacherTimeSignList != null && teacherTimeSignList.size() != 0) {
      List<TeacherTimeParam> teacherTimeListToRemove = teacherTimeEntityDao
          .findOverlapTimeTeachers(teacherTimeParam);
      // 将被排课的老师删除
      if (teacherTimeListToRemove != null) {
        for (TeacherTimeParam t : teacherTimeListToRemove) {
          // 移除已经被签课的老师
          teacherTimeSignMap.remove(t.getTeacherId());
        }
      }
    }
    // 获得map值得list
    List<TeacherTimeSignParam> res = new ArrayList<TeacherTimeSignParam>(
        teacherTimeSignMap.values());
    return res;
  }

  /**
   * 
   * Title: 新增排课<br>
   * Description: 新增排课<br>
   * CreateDate: 2016年4月28日 下午6:01:11<br>
   * 
   * @category 新增排课
   * @author seven.gz
   * @param 新增排课
   * @param startTime
   *          排课课程开始时间
   * @param endTime
   *          排课课程结束时间
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = {
          Exception.class })
  public JsonMessage addCourseOne2ManyScheduling(
      CourseOne2ManyScheduling adminCourseOne2ManySchedulingParam,
      boolean isHuanxun, String platform) throws Exception {
    JsonMessage json = new JsonMessage();
    json.setSuccess(true);
    json.setMsg("添加成功");

    // modify by 2016年9月9日10:06:48 seven 去除1v1排课时的结束时间选择
    Date startTime = adminCourseOne2ManySchedulingParam.getStartTime();
    // Date endTime = adminCourseOne2ManySchedulingParam.getEndTime();
    int subscribeTime = ((CourseType) MemcachedUtil
        .getValue(adminCourseOne2ManySchedulingParam.getCourseType()))
            .getCourseTypeDuration(); // 课程持续时间
    Date endTime = new Date(startTime.getTime() + subscribeTime * 60 * 1000);
    adminCourseOne2ManySchedulingParam.setEndTime(endTime);

    // 判断开始日期是否大于等于结束日期
    // if (endTime.getTime() <= startTime.getTime()) {
    // json.setSuccess(false);
    // json.setMsg("请输入正确日期");
    // return json;
    // }
    // 日期格式错误

    // 环迅老师不去校验老师签课时间
    if (!isHuanxun) {
      // 查询此老师是否有可用时间（是否被老师删除）
      TeacherTimeSign teacherTimeSignPara = new TeacherTimeSign();
      teacherTimeSignPara.setStartTime(startTime);
      teacherTimeSignPara.setEndTime(endTime);
      teacherTimeSignPara.setTeacherId(adminCourseOne2ManySchedulingParam.getTeacherId());

      // 查找要删除时间段是否在已签课时间段内
      TeacherTimeSign teacherTimeSignAvailable = teacherTimeSignDao
          .findTeacherTimeSignByDeleteTime(teacherTimeSignPara);
      if (teacherTimeSignAvailable == null) {
        json.setSuccess(false);
        json.setMsg("此教师的时间不可用，请重新选择");
        return json;
      }
    }

    // 老师时间查询参数
    TeacherTimeParam teacherTimeParam = new TeacherTimeParam();
    teacherTimeParam.setStartTime(startTime);
    teacherTimeParam.setEndTime(endTime);
    teacherTimeParam.setTeacherId(adminCourseOne2ManySchedulingParam.getTeacherId());
    // 在签课老师中找出这些老师这个时间段已经被排课的老师
    List<TeacherTimeParam> teacherTimeListToRemove = teacherTimeEntityDao
        .findOverlapTimeTeachers(teacherTimeParam);

    if (teacherTimeListToRemove != null && teacherTimeListToRemove.size() > 0) {
      json.setSuccess(false);
      json.setMsg("此教师的时间已经被排课，请重新选择");
      return json;
    }

    // 根据课程id查询课程信息
    CourseOne2Many courseOne2Many = adminCourseOne2ManyDao
        .findOneByKeyId(adminCourseOne2ManySchedulingParam.getCourseId());
    // 根据教师id查询教师信息
    Teacher teacher = teacherEntityDao
        .findOneByKeyId(adminCourseOne2ManySchedulingParam.getTeacherId());

    // 设置正确的开始结束时间
    adminCourseOne2ManySchedulingParam.setStartTime(startTime);
    adminCourseOne2ManySchedulingParam.setEndTime(endTime);

    // 获得要插入的排课实体
    CourseOne2ManyScheduling schedulingInsert = transformToScheduling(courseOne2Many, teacher,
        adminCourseOne2ManySchedulingParam);

    // 因为排课房间要有预留时间所以查询时要再课程时间基础上留出这个时间
    Calendar calStartClassTimeCalendar = Calendar.getInstance();
    calStartClassTimeCalendar.setTime(startTime);
    calStartClassTimeCalendar.add(Calendar.MINUTE, -CourseOneToOneSchedulingConstant
        .getVcubeRoomClassFrontTime(schedulingInsert.getCourseType()));

    Calendar calEndClassTimeCalendar = Calendar.getInstance();
    calEndClassTimeCalendar.setTime(endTime);
    calEndClassTimeCalendar.add(Calendar.MINUTE, CourseOneToOneSchedulingConstant
        .getVcubeRoomClassPostTime(schedulingInsert.getCourseType()));

    /**
     * modified by komi 2016年6月27日15:29:05 房间类型2-1v1 10-lecture
     * course_type2是lecture房间，限制人数为x
     */
    String roomId = "";
    if ("course_type2".equals(schedulingInsert.getCourseType())) {
      List<String> roomIdList = teacherTimeService.findWebexAvailableRoomList(
          WebexConstant.WEBEX_ROOM_TYPE_ONE2MANY, calStartClassTimeCalendar.getTime(),
          calEndClassTimeCalendar.getTime());
      if (roomIdList != null && roomIdList.size() > 0) {
        roomId = roomIdList.get(0);
      } else {
        // modified by alex+komi+seven 2016年8月3日 14:19:47 如果没有房间则不再创建数据

        json.setSuccess(false);
        json.setMsg("当前排课失败，webex房间已满，请联系技术部要求增加房间");
        return json;
      }
      schedulingInsert
          .setLimitNumber(
              ((CourseType) MemcachedUtil.getValue("course_type2")).getCourseTypeLimitNumber());
    }

    // 获得老师时间实体
    TeacherTime teacherTime = transformToTeacherTime(schedulingInsert, roomId);

    // 设置平台
    if (!StringUtils.isEmpty(platform)) {
      teacherTime.setTeacherTimePlatform(Integer.valueOf(platform));
    }

    // 插入排课表
    adminCourseOne2ManySchedulingDao.insert(schedulingInsert);
    // 插入老师时间表j
    teacherTimeEntityDao.insert(teacherTime);
    /**
     * modify by seven 2016年10月13日18:17:54 给terry用作测试用 modified by komi
     * 2016年10月19日15:45:51 增加返回teacherTimeId，给terry用作测试用
     */
    List<String> terryTestList = new ArrayList<String>();
    terryTestList.add(schedulingInsert.getKeyId());
    terryTestList.add(schedulingInsert.getTeacherTimeId());
    json.setData(terryTestList);

    /**
     * modified by komi 2016年7月7日16:40:41 ES课程增加展示互动排课房间 modified by komi
     * 2016年9月21日16:41:58 oc课增加展示互动排课房间
     */
    String courseType = schedulingInsert.getCourseType();
    if ("course_type8".equals(courseType) || "course_type5".equals(courseType)) {
      /**
       * modified by komi 2017年1月4日14:15:04 增加老师名字、老师简介、课程简介，需求472
       */
      if (TeacherTimeConstant.TEACHER_TIME_PLATFORM_GENSEE.toString().equals(platform)) {
        Gensee gensee = genseeService.insertGensee(teacherTime.getKeyId(), courseType,
            GenseeConstant.GENSEE_SCENE_BIG,
            schedulingInsert.getCourseTitle(), teacherTime.getStartTime(), teacherTime.getEndTime(),
            teacher.getTeacherDesc(), teacher.getTeacherName(), courseOne2Many.getCourseDesc());
        GenseeUtil.attachFile(gensee.getRoomId(), courseOne2Many.getDocumentId());
      } else {
        classinService.insertClassin(teacherTime.getKeyId(), schedulingInsert.getCourseTitle(),
            schedulingInsert.getCoursePic(),
            schedulingInsert.getKeyId(),
            schedulingInsert.getTeacherName(), schedulingInsert.getTeacherPhoto(),
            startTime, subscribeTime, schedulingInsert.getCourseId());
      }
    }

    // 只有lecture的时候才需要创建webex的教室
    if ("course_type2".equals(schedulingInsert.getCourseType())) {

      // 同步生产webex会议

      int resultInt = webexSubscribeService.createWebexMeeting(teacherTime.getKeyId(),
          schedulingInsert.getTeacherName() + " "
              + DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getStartTime())
              + " " + DateUtil.dateToStrYYMMDDHHMMSS(teacherTime.getEndTime()) + " ");

      if (resultInt == 0) {
        throw new RuntimeException("lecture手动排课同步调用webex接口数据操作异常");
      }

      // 异步调用生产者,生产webex会议
      // 延迟1分钟消费
      // String createWebexMeetingBodyStr =
      // WebexConstant.WEBEX_CONSUMER_TYPE_CREATE_MEETING + ","
      // + teacherTime.getKeyId() + "," +
      // schedulingInsert.getCourseTitle();
      // OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
      // MemcachedUtil.getConfigValue("aliyun_ons_topicid"),
      // "aliyun_ons_consumerid_webex",
      // createWebexMeetingBodyStr, 60 * 1000);
    }

    // 如果是环迅排课 就调用环迅的预约接口
    if (isHuanxun) {
      // 如果是环迅的老师，需要调用环迅的接口
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("course_type", schedulingInsert.getCourseType());
      paramMap.put("start_time", DateUtil.dateToStrYYMMDDHHMMSS(schedulingInsert.getStartTime()));
      paramMap.put("end_time", DateUtil.dateToStrYYMMDDHHMMSS(schedulingInsert.getEndTime()));
      paramMap.put("teacher_id", schedulingInsert.getTeacherId());
      paramMap.put("course_courseware", schedulingInsert.getCourseCourseware());

      String returnCode = huanxunService.huanxunBookOne2Many(paramMap);
      if (!"200".equals(returnCode)) {
        throw new RuntimeException("预约环迅老师1vn出错!");
      }
    }

    return json;
  }

  /**
   * 
   * Title: 转化为排课对象<br>
   * Description: 转化为排课对象<br>
   * CreateDate: 2016年4月29日 上午11:18:27<br>
   * 
   * @category 转化为排课对象
   * @author seven.gz
   * @param AdminCourseOne2Many
   * @param Teacher
   * @param SchedulingChangeTeacherParam
   * @return
   */
  private CourseOne2ManyScheduling transformToScheduling(CourseOne2Many courseOne2Many,
      Teacher teacher,
      CourseOne2ManyScheduling paramObj) {
    CourseOne2ManyScheduling adminCourseOne2ManyScheduling = new CourseOne2ManyScheduling();
    // 体系类别
    adminCourseOne2ManyScheduling.setCategoryType(courseOne2Many.getCategoryType());
    // 课程类别
    adminCourseOne2ManyScheduling.setCourseType(courseOne2Many.getCourseType());
    // 所属课程id
    adminCourseOne2ManyScheduling.setCourseId(courseOne2Many.getKeyId());
    // 课程标题
    adminCourseOne2ManyScheduling.setCourseTitle(courseOne2Many.getCourseTitle());
    // 课程级别
    adminCourseOne2ManyScheduling.setCourseLevel(courseOne2Many.getCourseLevel());
    // 课程图片
    adminCourseOne2ManyScheduling.setCoursePic(courseOne2Many.getCoursePic());
    // 老师id
    adminCourseOne2ManyScheduling.setTeacherId(teacher.getKeyId());
    // 老师名称
    adminCourseOne2ManyScheduling.setTeacherName(teacher.getTeacherName());
    // 老师头像
    adminCourseOne2ManyScheduling.setTeacherPhoto(teacher.getTeacherPhoto());
    // ppt课件
    adminCourseOne2ManyScheduling.setCourseCourseware(courseOne2Many.getCourseCourseware());
    // 微立方课件id
    adminCourseOne2ManyScheduling.setDocumentId(courseOne2Many.getDocumentId());
    // 课程简介
    adminCourseOne2ManyScheduling.setCourseDesc(courseOne2Many.getCourseDesc());
    // 上课时间
    adminCourseOne2ManyScheduling.setStartTime(paramObj.getStartTime());
    // 下课时间
    adminCourseOne2ManyScheduling.setEndTime(paramObj.getEndTime());
    // 教师url
    if (paramObj.getTeacherUrl() != null) {
      adminCourseOne2ManyScheduling.setTeacherUrl(paramObj.getTeacherUrl().trim());
    }
    // 学生url
    if (paramObj.getStudentUrl() != null) {
      adminCourseOne2ManyScheduling.setStudentUrl(paramObj.getStudentUrl().trim());
    }
    // 创建人id
    adminCourseOne2ManyScheduling.setCreateUserId(paramObj.getCreateUserId());
    // 最后更新人id
    adminCourseOne2ManyScheduling.setUpdateUserId(paramObj.getUpdateUserId());
    // 老师时间id (因为要一起插入所以在这里生成)
    adminCourseOne2ManyScheduling.setTeacherTimeId(UUIDUtil.uuid(KEY_ID_SIZE));

    return adminCourseOne2ManyScheduling;
  }

  /**
   * 
   * Title: 转化为老师时间实体<br>
   * Description: 转化为老师时间实体<br>
   * CreateDate: 2016年4月29日 上午11:47:58<br>
   * 
   * @category 转化为老师时间实体
   * @author seven.gz
   * @param scheduling
   * @param webexRoomHostId
   * @return
   */
  private TeacherTime transformToTeacherTime(CourseOne2ManyScheduling scheduling,
      String webexRoomHostId) {
    TeacherTime teacherTime = new TeacherTime();
    // 主键id (因为要一起插入在前一步生成)
    teacherTime.setKeyId(scheduling.getTeacherTimeId());
    // 老师id(逻辑外键)
    teacherTime.setTeacherId(scheduling.getTeacherId());
    // 课程类别
    teacherTime.setCourseType(scheduling.getCourseType());
    // webex房间ID
    teacherTime.setWebexRoomHostId(webexRoomHostId);
    // 老师名字
    teacherTime.setTeacherName(scheduling.getTeacherName());
    // 允许上课开始时间
    teacherTime.setStartTime(scheduling.getStartTime());
    // 允许上课结束时间
    teacherTime.setEndTime(scheduling.getEndTime());
    // 创建人id
    teacherTime.setCreateUserId(scheduling.getCreateUserId());
    // 最后更新人id
    teacherTime.setUpdateUserId(scheduling.getUpdateUserId());
    return teacherTime;
  }

  /**
   * 
   * Title: 根据老师时间id查找是否有可用的老师（用于更换老师）<br>
   * Description: 根据预约这个老师时间的course_type来更换<br>
   * CreateDate: 2016年12月21日 上午11:20:18<br>
   * 
   * @category 根据老师时间id查找是否有可用的老师（用于更换老师
   * @author seven.gz
   * @param startTime
   * @param courseType
   * @param teacherTimeId
   * @return
   * @throws Exception
   */
  public List<TeacherTimeSignParam> findAvailableTeacherByTeacherTime(Date startTime,
      String courseType, String teacherTimeId) throws Exception {
    SubscribeCourse subscribeCourse = adminSubscribeCourseDao.findOneByTeacherTimeId(teacherTimeId);
    if (subscribeCourse != null) {
      courseType = subscribeCourse.getCourseType();
    }
    int subscribeTime = ((CourseType) MemcachedUtil.getValue(courseType))
        .getCourseTypeDuration(); // 课程持续时间
    Date endTime = new Date(startTime.getTime() + subscribeTime * 60 * 1000);
    List<String> courseTypeList = new ArrayList<String>();
    courseTypeList.add(courseType);
    return findAvailableTeacherByTime(startTime, endTime, courseTypeList);
  }

  /**
   * 
   * Title: 根据时间找出签过这个时间的且没有没排课的老师<br>
   * Description: 根据时间找出签过这个时间的且没有没排课的老师<br>
   * CreateDate: 2016年12月29日 上午10:53:35<br>
   * 
   * @category 根据时间找出签过这个时间的且没有没排课的老师
   * @author seven.gz
   * @param startTime
   * @param endTime
   * @param courseType
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public List<TeacherTimeSignParam> findAvailableTeacherByTime(Date startTime, Date endTime,
      String courseType)
          throws Exception {
    // modify by seven 2016年12月29日10:01:09
    // core1v1，ext1v1，demo1v1的类型单独出来，都可以在1v1排课的下拉框中过滤出来。
    List<String> courseTypeList = new ArrayList<String>();
    if (!"course_type1".equals(courseType)) {
      courseTypeList.add(courseType);
    } else {
      // modify by seven
      CourseType paramObj = new CourseType();
      paramObj.setCourseTypeFlag(CourseTypeConstant.COURSE_TYPE_FLAG_1V1);
      List<CourseType> courseType1v1List = courseTypeDao.findListByParam(paramObj);
      if (courseType1v1List != null && courseType1v1List.size() > 0) {
        for (CourseType courseTypeTemp : courseType1v1List) {
          courseTypeList.add(courseTypeTemp.getCourseType());
        }
      }
    }
    return findAvailableTeacherByTime(startTime, endTime,
        courseTypeList);
  }

}