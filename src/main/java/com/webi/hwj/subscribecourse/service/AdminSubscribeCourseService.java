package com.webi.hwj.subscribecourse.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.admin.dao.BadminUserDao;
import com.webi.hwj.courseone2one.dao.AdminCourseOne2oneDao;
import com.webi.hwj.coursetype.constant.CourseTypeConstant;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.huanxun.service.HuanxunService;
import com.webi.hwj.mail.MailUtil;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseSplitEntityDao;
import com.webi.hwj.ordercourse.param.AdminOrderAndOrderSplitParam;
import com.webi.hwj.statistics.dao.StatisticsTellmemoreDayDao;
import com.webi.hwj.statistics.param.FindStatisticsTellmemoreParam;
import com.webi.hwj.subscribecourse.constant.AdminSubscribeCourseConstant;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseAndStudentParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseCountParam;
import com.webi.hwj.subscribecourse.param.FindTuanxunCourseCommentParam;
import com.webi.hwj.subscribecourse.param.StatisticsMonthSubscribeCountParam;
import com.webi.hwj.subscribecourse.param.StatisticsSubscribeCourseForMailParam;
import com.webi.hwj.subscribecourse.param.StatisticsTeacherSubscribeCourseParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseForCreateMailParam;
import com.webi.hwj.subscribecourse.util.AdminSubscribeCourseUtil;
import com.webi.hwj.teacher.dao.TeacherEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.param.FindTeacherByKeyIdsParam;
import com.webi.hwj.teacher.param.TeacherTimeParam;
import com.webi.hwj.teacher.service.TeacherTimeService;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.dao.UserInfoEntityDao;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;
import com.webi.hwj.user.param.FindUserOrderInfoParam;
import com.webi.hwj.util.CalendarUtil;

@Service
public class AdminSubscribeCourseService {
  private static Logger logger = Logger.getLogger(AdminSubscribeCourseService.class);
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;
  @Resource
  TeacherEntityDao teacherEntityDao;
  @Resource
  BadminUserDao badminUserDao;
  @Resource
  AdminUserEntityDao adminUserEntityDao;
  @Resource
  StatisticsTellmemoreDayDao StatisticsTellmemoreDayDao;
  @Resource
  UserInfoEntityDao userInfoEntityDao;
  @Resource
  AdminCourseOne2oneDao adminCourseOne2oneDao;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;
  @Resource
  UserEntityDao userEntityDao;
  @Resource
  TeacherTimeService teacherTimeService;
  @Resource
  HuanxunService huanxunService;
  @Resource
  BaseSubscribeCourseService baseSubscribeCourseService;
  @Resource
  AdminOrderCourseSplitEntityDao adminOrderCourseSplitEntityDao;

  /**
   * 
   * Title: 统计每月的预约数<br>
   * Description: 统计每月的预约数<br>
   * CreateDate: 2016年7月27日 下午3:11:06<br>
   * 
   * @category 统计每月的预约数
   * @author seven.gz
   * @param startTime
   * @param endTime
   * @throws Exception
   */
  public List<StatisticsMonthSubscribeCountParam> statisticsMonthSubscribeCount(Date startTime,
      Date endTime,
      String studentShow) throws Exception {
    // 查询出这个时间段内的预约信息
    List<SubscribeCourse> subscribeCourseList = adminSubscribeCourseDao.findSubscribeCourseByTime(
        startTime,
        endTime, studentShow);
    // 存放统计的数据 LinkedHashMap 才能保证迭代的时候取出的顺序和存入的顺序相同
    Map<String, StatisticsMonthSubscribeCountParam> subscribeCourseMap =
        new LinkedHashMap<String, StatisticsMonthSubscribeCountParam>();
    // 哪个月
    String monthString = null;
    int one2OneTotalCount = 0;
    int one2ManyTotalCount = 0;
    int englishStudioTotalCount = 0;
    int extensionOne2OneTotalCount = 0;
    int ocTotalCount = 0;
    int demoTotalCount = 0;

    // 遍历统计各种课程的预约数
    StatisticsMonthSubscribeCountParam statisticsMonthSubscribeCountParam = null;
    for (SubscribeCourse subscribeCourse : subscribeCourseList) {
      monthString = DateUtil.dateToStr(subscribeCourse.getStartTime(), "yyyy-MM");
      if (subscribeCourseMap.containsKey(monthString)) {
        statisticsMonthSubscribeCountParam = subscribeCourseMap.get(monthString);
      } else {
        statisticsMonthSubscribeCountParam = new StatisticsMonthSubscribeCountParam();
        subscribeCourseMap.put(monthString, statisticsMonthSubscribeCountParam);
      }
      if ("course_type1".equals(subscribeCourse.getCourseType())) {
        statisticsMonthSubscribeCountParam
            .setOne2OneCount(statisticsMonthSubscribeCountParam.getOne2OneCount() + 1);
        statisticsMonthSubscribeCountParam
            .setTotal(statisticsMonthSubscribeCountParam.getTotal() + 1);
        one2OneTotalCount++;
      } else if ("course_type2".equals(subscribeCourse.getCourseType())) {
        statisticsMonthSubscribeCountParam
            .setOne2ManyCount(statisticsMonthSubscribeCountParam.getOne2ManyCount() + 1);
        statisticsMonthSubscribeCountParam
            .setTotal(statisticsMonthSubscribeCountParam.getTotal() + 1);
        one2ManyTotalCount++;
      } else if ("course_type5".equals(subscribeCourse.getCourseType())) {
        statisticsMonthSubscribeCountParam
            .setOcCount(statisticsMonthSubscribeCountParam.getOcCount() + 1);
        statisticsMonthSubscribeCountParam
            .setTotal(statisticsMonthSubscribeCountParam.getTotal() + 1);
        ocTotalCount++;
      } else if ("course_type8".equals(subscribeCourse.getCourseType())) {
        statisticsMonthSubscribeCountParam
            .setEnglishStudioCount(statisticsMonthSubscribeCountParam.getEnglishStudioCount() + 1);
        statisticsMonthSubscribeCountParam
            .setTotal(statisticsMonthSubscribeCountParam.getTotal() + 1);
        englishStudioTotalCount++;
      } else if ("course_type9".equals(subscribeCourse.getCourseType())) {
        statisticsMonthSubscribeCountParam
            .setExtensionOne2OneCount(
                statisticsMonthSubscribeCountParam.getExtensionOne2OneCount() + 1);
        statisticsMonthSubscribeCountParam
            .setTotal(statisticsMonthSubscribeCountParam.getTotal() + 1);
        extensionOne2OneTotalCount++;
      } else if ("course_type4".equals(subscribeCourse.getCourseType())) {
        statisticsMonthSubscribeCountParam
            .setDemoOne2OneCount(
                statisticsMonthSubscribeCountParam.getDemoOne2OneCount() + 1);
        statisticsMonthSubscribeCountParam
            .setTotal(statisticsMonthSubscribeCountParam.getTotal() + 1);
        demoTotalCount++;
      }
      statisticsMonthSubscribeCountParam.setClassDate(monthString);
    }

    // 总计
    StatisticsMonthSubscribeCountParam totalCountParam = new StatisticsMonthSubscribeCountParam();
    totalCountParam.setClassDate("合计");
    totalCountParam.setOne2OneCount(one2OneTotalCount);
    totalCountParam.setOne2ManyCount(one2ManyTotalCount);
    totalCountParam.setEnglishStudioCount(englishStudioTotalCount);
    totalCountParam.setExtensionOne2OneCount(extensionOne2OneTotalCount);
    totalCountParam.setOcCount(ocTotalCount);
    totalCountParam.setDemoOne2OneCount(demoTotalCount);
    totalCountParam.setTotal(one2OneTotalCount + one2ManyTotalCount + englishStudioTotalCount
        + extensionOne2OneTotalCount + demoTotalCount);

    List<StatisticsMonthSubscribeCountParam> returnList =
        new ArrayList<StatisticsMonthSubscribeCountParam>(
            subscribeCourseMap.values());
    returnList.add(totalCountParam);

    return returnList;
  }

  /**
   * 
   * Title: 统计老师课时数<br>
   * Description: 统计老师课时数<br>
   * CreateDate: 2016年7月29日 上午11:11:30<br>
   * 
   * @category 统计老师课时数
   * @author seven.gz
   * @param paramMap
   * @param startTime
   * @param endTime
   * @param studentShow
   * @return
   * @throws Exception
   */
  public List<StatisticsTeacherSubscribeCourseParam> satisticsTeacherSubscribeCourse(
      Map<String, Object> paramMap,
      Date startTime, Date endTime, String studentShow) throws Exception {
    // 查询出预约信息
    Page page = adminSubscribeCourseDao.findSubscribeCourseAndTeacherPage(paramMap, startTime,
        endTime,
        studentShow);
    List<StatisticsTeacherSubscribeCourseParam> subscribeCourseList = null;
    if (page != null) {
      subscribeCourseList = page.getDatas();
    }

    // 存储统计数据map
    Map<String, StatisticsTeacherSubscribeCourseParam> statisticsMap =
        new LinkedHashMap<String, StatisticsTeacherSubscribeCourseParam>();
    // 存放已经出现过的teahcerTimeId,避免重复统计
    List<String> teacherTimeList = new ArrayList<String>();

    // 总课程数
    int totalCourseCount = 0;
    // 总课时数
    double totalCourseTime = 0;

    if (subscribeCourseList != null && subscribeCourseList.size() > 0) {
      String mapKey = null;
      StatisticsTeacherSubscribeCourseParam statisticsParam = null;

      // 遍历统计预约信息
      for (StatisticsTeacherSubscribeCourseParam statisticsTeacherSubscribeCourseParam : subscribeCourseList) {
        // 如果这个teacherTime已经出现过则跳过
        if (!teacherTimeList.contains(statisticsTeacherSubscribeCourseParam.getTeacherTimeId())) {
          teacherTimeList.add(statisticsTeacherSubscribeCourseParam.getTeacherTimeId());
          // 用 上课日期 + 老师id + 课程类型作为key
          mapKey = DateUtil.dateToStrYYMMDD(statisticsTeacherSubscribeCourseParam.getStartTime())
              + statisticsTeacherSubscribeCourseParam.getTeacherId()
              + statisticsTeacherSubscribeCourseParam.getCourseType();

          CourseType courseTypeEntity = (CourseType) MemcachedUtil
              .getValue(statisticsTeacherSubscribeCourseParam.getCourseType());

          if (statisticsMap.containsKey(mapKey)) {
            statisticsParam = statisticsMap.get(mapKey);
          } else {
            statisticsParam = new StatisticsTeacherSubscribeCourseParam();
            statisticsParam.setCourseType(courseTypeEntity
                .getCourseTypeChineseName());
            statisticsParam.setClassDate(
                DateUtil.dateToStrYYMMDD(statisticsTeacherSubscribeCourseParam.getStartTime()));
            statisticsParam.setTeacherName(statisticsTeacherSubscribeCourseParam.getTeacherName());
            statisticsParam.setThirdFrom(statisticsTeacherSubscribeCourseParam.getThirdFrom());

            statisticsMap.put(mapKey, statisticsParam);
          }

          statisticsParam.setCourseCount(statisticsParam.getCourseCount() + 1);
          // 不同课程类型计算不同的课时数l
          if (courseTypeEntity != null &&
              courseTypeEntity.getCourseTypeFlag() != null
              && courseTypeEntity.getCourseTypeFlag() == CourseTypeConstant.COURSE_TYPE_FLAG_1V1) {

            statisticsParam.setCourseTime(
                statisticsParam.getCourseTime() + AdminSubscribeCourseConstant.COURSE_1V1_TIME);
            totalCourseTime += AdminSubscribeCourseConstant.COURSE_1V1_TIME;
          } else {
            statisticsParam.setCourseTime(
                statisticsParam.getCourseTime() + AdminSubscribeCourseConstant.COURSE_LECTURE_TIME);
            totalCourseTime += AdminSubscribeCourseConstant.COURSE_LECTURE_TIME;
          }
          totalCourseCount++;
        }
      }

    }
    // 获得统计出的数据
    List<StatisticsTeacherSubscribeCourseParam> resultList =
        new ArrayList<StatisticsTeacherSubscribeCourseParam>(
            statisticsMap.values());

    // 合计数据
    StatisticsTeacherSubscribeCourseParam totalStatistics =
        new StatisticsTeacherSubscribeCourseParam();
    totalStatistics.setClassDate("合计");
    totalStatistics.setCourseCount(totalCourseCount);
    totalStatistics.setCourseTime(totalCourseTime);
    // 将总数加入返回值中
    resultList.add(totalStatistics);

    return resultList;
  }

  /**
   * Title: 查询学生预约课表<br>
   * Description: 查询学生预约课表<br>
   * CreateDate: 2016年8月1日 上午11:26:45<br>
   * 
   * @category 查询学生预约课表
   * @author seven.gz
   * @param paramMap
   *          esayui传入参数
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @return page
   * @throws Exception
   *           异常
   */
  public Page findSubscribeCourseInfoPage(Map<String, Object> paramMap, Date startTime,
      Date endTime) throws Exception {
    // 查询出学生预约信息
    Page page = adminSubscribeCourseDao.findSubscribeCourseAndStudentPage(paramMap, startTime,
        endTime);

    List<FindSubscribeCourseAndStudentParam> subscribeCourseAndStudentList = page.getDatas();
    // 老师时间id-为查询老师时间
    List<String> teacherTimeIds = new ArrayList<String>();
    // 老师id-为查询老师信息
    List<String> teacherIds = new ArrayList<String>();
    // 教务id
    List<String> learningCoachIds = new ArrayList<String>();

    if (subscribeCourseAndStudentList != null && subscribeCourseAndStudentList.size() > 0) {
      for (FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam : subscribeCourseAndStudentList) {
        if (!teacherTimeIds.contains(findSubscribeCourseAndStudentParam.getTeacherTimeId())) {
          teacherTimeIds.add(findSubscribeCourseAndStudentParam.getTeacherTimeId());
        }
        if (!teacherIds.contains(findSubscribeCourseAndStudentParam.getTeacherId())) {
          teacherIds.add(findSubscribeCourseAndStudentParam.getTeacherId());
        }
        if (!learningCoachIds.contains(findSubscribeCourseAndStudentParam.getLearningCoachId())) {
          learningCoachIds.add(findSubscribeCourseAndStudentParam.getLearningCoachId());
        }
      }
    } else {
      return page;
    }
    // 查询老师时间
    List<TeacherTimeParam> teacherTimeList = teacherTimeEntityDao
        .findTeacherTimeByKeyids(teacherTimeIds);
    Map<String, String> teacherTimeMap = new HashMap<String, String>();
    Map<String, Boolean> teacherTimeAttendMap = new HashMap<String, Boolean>();
    // 行专列
    if (teacherTimeList != null && teacherTimeList.size() > 0) {
      for (TeacherTimeParam findTeacherTimeParam : teacherTimeList) {
        // modified by alex+komi+seven 2016年8月3日 14:19:47 因为不再使用vcube了
        teacherTimeMap.put(findTeacherTimeParam.getKeyId(),
            findTeacherTimeParam.getWebexRoomHostId());

        teacherTimeAttendMap.put(findTeacherTimeParam.getKeyId(),
            findTeacherTimeParam.getIsAttend());
      }
    }

    // 查询老师
    // List<FindTeacherByKeyIdsParam> teacherList =
    // teacherEntityDao.findTeacherByKeyIds(teacherIds);
    // Map<String, String> teacherMap = new HashMap<String, String>();
    // // 行专列
    // if (teacherList != null && teacherList.size() > 0) {
    // for (FindTeacherByKeyIdsParam findTeacherByKeyIdsParam : teacherList) {
    // teacherMap.put(findTeacherByKeyIdsParam.getKeyId(),
    // findTeacherByKeyIdsParam.getThirdFrom());
    // }
    // }

    // modify by seven 增加lc名称组合查询
    // 查询教务
    // Map<String, String> learningCoachMap = new HashMap<String, String>();
    // List<FindBadminUserParam> learningCoachList = badminUserDao
    // .findAdminInfoByKeyIds(learningCoachIds);
    // // 行专列
    // if (learningCoachList != null && learningCoachList.size() > 0) {
    // for (FindBadminUserParam findBadminUserParam : learningCoachList) {
    // learningCoachMap.put(findBadminUserParam.getKeyId(),
    // findBadminUserParam.getAdminUserName());
    // }
    // }

    // 将信息拼入返回值
    if (subscribeCourseAndStudentList != null && subscribeCourseAndStudentList.size() > 0) {
      for (FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam : subscribeCourseAndStudentList) {
        findSubscribeCourseAndStudentParam.setRoomId(
            teacherTimeMap.get(findSubscribeCourseAndStudentParam.getTeacherTimeId()));
        findSubscribeCourseAndStudentParam.setIsAttend(
            teacherTimeAttendMap.get(findSubscribeCourseAndStudentParam.getTeacherTimeId()));

        // 课程类型转化为汉字
        findSubscribeCourseAndStudentParam.setCourseTypeChineseName(((CourseType) MemcachedUtil
            .getValue((String) findSubscribeCourseAndStudentParam.getCourseType()))
                .getCourseTypeChineseName());

        // modify by seven 增加lc名称组合查询
        // 教务名称
        // findSubscribeCourseAndStudentParam.setAdminUserName(
        // learningCoachMap.get(findSubscribeCourseAndStudentParam.getLearningCoachId()));
      }
    }
    return page;
  }

  /**
   * 
   * Title: 切换学员出席状态 <br>
   * Description: 切换学员出席状态 <br>
   * CreateDate: 2016年9月20日 下午2:05:29<br>
   * 
   * @category 切换学员出席状态
   * @author seven.gz
   * @param subscribeId
   *          预约id
   * @param oldStatus
   *          学员show 源状态
   * @return boolean
   * @throws Exception
   *           通用异常
   */
  public JsonMessage changeStudentShowStatus(String subscribeId, boolean oldStatus,
      String updateUserId) throws Exception {
    JsonMessage message = new JsonMessage();
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setKeyId(subscribeId);
    paramObj.setUpdateUserId(updateUserId);
    // 如果源状态为show 则修改为noshow 如果源状态为noshow则修改成show
    if (oldStatus) {
      paramObj.setSubscribeStatus(AdminSubscribeCourseConstant.SUBSCRIBE_STATUS_NO_SHOW);
    } else {
      paramObj.setSubscribeStatus(AdminSubscribeCourseConstant.SUBSCRIBE_STATUS_SHOW);
    }
    int resultCount = adminSubscribeCourseDao.update(paramObj);
    if (resultCount != 1) {
      message.setSuccess(false);
      message.setMsg("操作失败");
    } else {
      message.setSuccess(true);
    }
    return message;
  }

  /**
   * Title: 据时间查询已经预约的core和extension课程数<br>
   * Description: 据时间查询已经预约的core和extension课程数<br>
   * CreateDate: 2016年9月21日 下午5:04:18<br>
   * 
   * @category 据时间查询已经预约的core和extension课程数
   * @author seven.gz
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @return int
   * @throws Exception
   *           通用异常
   */
  public int findCourseCount(Date startTime, Date endTime) throws Exception {
    return teacherTimeEntityDao.findCountByStartTimeAndEndTime(startTime, endTime);
  }

  /**
   * Title: 统计异常预约数据信息<br>
   * Description: 统计异常预约数据信息<br>
   * CreateDate: 2016年9月22日 下午2:06:27<br>
   * 
   * @category 统计异常预约数据信息
   * @author seven.gz
   * @return SubscribeCourseForCreateMailParam
   * @throws Exception
   *           通用异常
   */
  public SubscribeCourseForCreateMailParam statisticsAbnormalSubscribeInfo(Date startTime,
      Date endTime) throws Exception {
    // 返回统计结果
    SubscribeCourseForCreateMailParam result = new SubscribeCourseForCreateMailParam();

    // 为统一使用esayui的方法在这里设置 row的值 为了获得全部数据 这里设置为最大的int
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("rows", Integer.MAX_VALUE + "");
    paramMap.put("page", "1");
    // 1. 查询数据
    Page page = findSubscribeCourseInfoPage(paramMap, startTime, endTime);
    List<FindSubscribeCourseAndStudentParam> subscribeCourseAndStudentList = page.getDatas();

    // 2. 统计数据
    // 记录课程类型的数目
    Map<String, StatisticsSubscribeCourseForMailParam> statisticsMap =
        new HashMap<String, StatisticsSubscribeCourseForMailParam>();
    StatisticsSubscribeCourseForMailParam statisticsSubscribeCourseForMailParam = null;
    // 异常列表
    List<FindSubscribeCourseAndStudentParam> adnormalList =
        new ArrayList<FindSubscribeCourseAndStudentParam>();
    if (subscribeCourseAndStudentList != null && subscribeCourseAndStudentList.size() > 0) {
      // 遍历结果查找出异常的学员 并统计数目
      for (FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam : subscribeCourseAndStudentList) {
        // 这里courseType 是已经翻译过的courseType
        // modify by seven 2017年3月23日11:51:48 不统计demo课数据
        if (!"course_type4".equals(findSubscribeCourseAndStudentParam.getCourseType())) {
          String courseTypeChineseName = findSubscribeCourseAndStudentParam
              .getCourseTypeChineseName();
          if (statisticsMap.containsKey(courseTypeChineseName)) {
            statisticsSubscribeCourseForMailParam =
                (StatisticsSubscribeCourseForMailParam) statisticsMap
                    .get(courseTypeChineseName);
          } else {
            statisticsSubscribeCourseForMailParam = new StatisticsSubscribeCourseForMailParam();
            statisticsSubscribeCourseForMailParam.setCourseType(courseTypeChineseName);
            statisticsMap.put(courseTypeChineseName, statisticsSubscribeCourseForMailParam);

          }
          // 总数加一
          statisticsSubscribeCourseForMailParam.addTotalCount();
          // 如果学员出席，并且没有记录视为正常
          if (!findSubscribeCourseAndStudentParam.getSubscribeStatus()
              || (!StringUtils.isEmpty(findSubscribeCourseAndStudentParam.getSubscribeNote())
                  && !StringUtils
                      .isEmpty(findSubscribeCourseAndStudentParam.getSubscribeNote().trim()))) {
            // 异常加一
            statisticsSubscribeCourseForMailParam.addAbnormalCount();
            adnormalList.add(findSubscribeCourseAndStudentParam);
          } else {
            // 正常加一
            statisticsSubscribeCourseForMailParam.addNormalCount();
          }
        }
      }

      result.setAdnormalList(adnormalList);
      result.setStatisticsInofMap(statisticsMap);
    }
    return result;
  }

  /**
   * 
   * Title: 发送异常预约信息邮件<br>
   * Description: 发送异常预约信息邮件<br>
   * CreateDate: 2016年9月22日 下午3:39:24<br>
   * 
   * @category 发送异常预约信息邮件
   * @author seven.gz
   */
  public void sendAbnormalSubscribe() {
    // 获得今天六点
    String todayString = DateUtil.dateToStrYYMMDD(new Date());
    Date endTime = DateUtil
        .strToDateYYYYMMDDHHMMSS(todayString + " 06:00:00");
    // 获得昨天六点
    Date startTime = CalendarUtil.getNextNDay(endTime, -1);
    // 1.获得昨天早上6点到今天6点的异样和统计数据
    try {
      // 查找昨天到今天的数据
      SubscribeCourseForCreateMailParam subscribeCourseForCreateMailParam =
          statisticsAbnormalSubscribeInfo(
              startTime, endTime);
      // 判断是否有邮件内容
      if (subscribeCourseForCreateMailParam.canSendMail()) {
        // 2. 生成发送邮件的html数据
        String mailString = AdminSubscribeCourseUtil
            .creatAbnormalSubscribeMail(subscribeCourseForCreateMailParam);
        // 3. 发送邮件
        String attachmentsString = AdminSubscribeCourseUtil
            .creatAbnormalMailAttachmentsString(subscribeCourseForCreateMailParam);
        MailUtil.sendMail(MemcachedUtil.getConfigValue("mail_abnormal_subscribe_to_addr"),
            AdminSubscribeCourseConstant.ABNORMAL_SUBSCRIBE_MAIL_SUBJECT + todayString, mailString,
            AdminSubscribeCourseConstant.ABNORMAL_SUBSCRIBE_MAIL_ATTACHMENT_NAME + todayString
                + ".csv",
            attachmentsString);
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("发送异常统计邮件出错 error:" + e.getMessage(), e);
    }
  }

  /**
   * 
   * Title: 生成团训学员信息的csv文件内容<br>
   * Description: createDownloadTuanxunInfo<br>
   * CreateDate: 2016年12月12日 下午5:01:58<br>
   * 
   * @category 生成团训学员信息的csv文件内容
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public String createDownloadTuanxunInfo(String startOrderTime, String endOrderTime)
      throws Exception {
    // 查询团训学员数据
    List<FindUserOrderInfoParam> userOrderInfoList = statisticsUserInfo(startOrderTime,
        endOrderTime);
    // 判断是否有邮件内容
    String mailString = "";
    if (userOrderInfoList != null && userOrderInfoList.size() > 0) {
      // 2. 生成发送邮件的html数据
      mailString = AdminSubscribeCourseUtil
          .creatTuanxunSubscribeCsvString(userOrderInfoList);
    }
    return mailString;
  }

  /**
   * 
   * Title: 统计要发送的学员数据<br>
   * Description: 统计要发送的学员数据<br>
   * CreateDate: 2016年11月15日 下午3:25:05<br>
   * 
   * @category 统计要发送的学员数据
   * @author seven.gz
   * @throws Exception
   */
  public List<FindUserOrderInfoParam> statisticsUserInfo(String startOrderTime, String endOrderTime)
      throws Exception {
    // 获取当前时间的前一天
    Date yesterday = DateUtil.strToDateYYYYMMDD(DateUtil.dateToStrYYMMDD(new Date()) + " 00:00:00");
    // 查询团训学员数据
    List<FindUserOrderInfoParam> userOrderInfoList = adminUserEntityDao
        .findUserOrderInfoByType(OrderCourseConstant.USER_FROM_TYPE_GROUP_TRAINING, startOrderTime,
            endOrderTime);
    // 行转列
    if (userOrderInfoList != null && userOrderInfoList.size() > 0) {
      List<String> userIds = new ArrayList<String>();
      Map<String, FindUserOrderInfoParam> userInfoMap =
          new HashMap<String, FindUserOrderInfoParam>();
      for (FindUserOrderInfoParam findUserOrderInfoParam : userOrderInfoList) {
        userInfoMap.put(findUserOrderInfoParam.getKeyId(), findUserOrderInfoParam);
        userIds.add(findUserOrderInfoParam.getKeyId());
      }

      // 查询团训学员消耗课程数据
      List<FindSubscribeCourseCountParam> subscribeCountList = adminSubscribeCourseDao
          .findSubscribeCourseCount(userIds, yesterday);
      FindUserOrderInfoParam userInfoTemp = null;
      if (subscribeCountList != null && subscribeCountList.size() > 0) {
        for (FindSubscribeCourseCountParam findSubscribeCourseCountParam : subscribeCountList) {
          userInfoTemp = userInfoMap.get(findSubscribeCourseCountParam.getUserId());
          if (userInfoTemp != null) {
            if ("course_type1".equals(findSubscribeCourseCountParam.getCourseType())) {
              userInfoTemp
                  .setConsumeCourseType1Count(findSubscribeCourseCountParam.getCourseCount());
            } else if ("course_type2".equals(findSubscribeCourseCountParam.getCourseType())) {
              userInfoTemp
                  .setConsumeCourseType2Count(findSubscribeCourseCountParam.getCourseCount());
            } else if ("course_type8".equals(findSubscribeCourseCountParam.getCourseType())) {
              userInfoTemp
                  .setConsumeCourseType8Count(findSubscribeCourseCountParam.getCourseCount());
            }
          }
        }
      }
      // 获取回车符号
      String enterString = System.getProperty("line.separator");
      // 查询团训学员已上课程教师名称、教师评论、开始上课时间
      List<FindTuanxunCourseCommentParam> tuanxunCourseCommentList = adminSubscribeCourseDao
          .findTuanxunCourseComment(userIds, yesterday);
      if (tuanxunCourseCommentList != null && tuanxunCourseCommentList.size() > 0) {
        // stringBuffer = new StringBuffer();
        for (FindTuanxunCourseCommentParam findTuanxunCourseCommentParam : tuanxunCourseCommentList) {
          userInfoTemp = userInfoMap.get(findTuanxunCourseCommentParam.getUserId());
          if (userInfoTemp != null) {
            if (userInfoTemp.getCommentContent() == null) {
              String content = DateUtil.dateToStrYYMMDDHHMMSS(findTuanxunCourseCommentParam
                  .getStartTime()) + ";" +
                  findTuanxunCourseCommentParam.getTeacherName() + enterString +
                  findTuanxunCourseCommentParam.getCommentContent().replace("\"", "\"\"")
                  + enterString;
              userInfoTemp.appendCommentContent(content);
            } else {
              String content = DateUtil.dateToStrYYMMDDHHMMSS(findTuanxunCourseCommentParam
                  .getStartTime()) + ";" +
                  findTuanxunCourseCommentParam.getTeacherName() + enterString +
                  findTuanxunCourseCommentParam.getCommentContent().replace("\"", "\"\"")
                  + enterString;
              userInfoTemp.appendCommentContent(content);
            }
          }
        }
      }

      // 查询团训学员rsa时长数据
      // 获得前一天的日期
      List<FindStatisticsTellmemoreParam> tellmemoreInfos = StatisticsTellmemoreDayDao
          .findInfoByCreatDayAndUserIds(userIds);
      if (tellmemoreInfos != null && tellmemoreInfos.size() > 0) {
        for (FindStatisticsTellmemoreParam findStatisticsTellmemoreParam : tellmemoreInfos) {
          userInfoTemp = userInfoMap.get(findStatisticsTellmemoreParam.getUserId());
          if (userInfoTemp != null) {
            if (userInfoTemp.getCreateDate() == null) {
              userInfoTemp
                  .setTotalTmmWorkingTime(findStatisticsTellmemoreParam.getTotalTmmWorkingtime());
              userInfoTemp.setCreateDate(findStatisticsTellmemoreParam.getCreateDate());
            } else {
              if (userInfoTemp.getCreateDate().getTime() < findStatisticsTellmemoreParam
                  .getCreateDate().getTime()) {
                userInfoTemp
                    .setTotalTmmWorkingTime(findStatisticsTellmemoreParam.getTotalTmmWorkingtime());
                userInfoTemp.setCreateDate(findStatisticsTellmemoreParam.getCreateDate());
              }
            }
          }
        }
      }
    }
    return userOrderInfoList;
  }

  /**
   * Title: 根据课程类型时间查询预约信息<br>
   * Description: findDemoSubscribeCourseInfoPage<br>
   * CreateDate: 2017年4月30日 下午5:09:38<br>
   * 
   * @category 根据课程类型时间查询预约信息
   * @author seven.gz
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @param courseType
   *          课程类型
   */
  public Page findDemoSubscribeCourseInfoPage(Map<String, Object> paramMap, Date startTime,
      Date endTime, String courseType) throws Exception {
    // 查询出学生预约信息
    Page page = adminSubscribeCourseDao.findSubscribeCourseAndStudentPageByCourseType(paramMap,
        startTime,
        endTime, courseType);

    List<FindSubscribeCourseAndStudentParam> subscribeCourseAndStudentList = page.getDatas();
    // 老师时间id-为查询老师时间
    List<String> teacherTimeIds = new ArrayList<String>();
    // 老师id-为查询老师信息
    List<String> teacherIds = new ArrayList<String>();
    // 学员ID
    List<String> userIds = new ArrayList<String>();

    if (subscribeCourseAndStudentList != null && subscribeCourseAndStudentList.size() > 0) {
      for (FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam : subscribeCourseAndStudentList) {
        if (!teacherTimeIds.contains(findSubscribeCourseAndStudentParam.getTeacherTimeId())) {
          teacherTimeIds.add(findSubscribeCourseAndStudentParam.getTeacherTimeId());
        }
        if (!teacherIds.contains(findSubscribeCourseAndStudentParam.getTeacherId())) {
          teacherIds.add(findSubscribeCourseAndStudentParam.getTeacherId());
        }
        if (!userIds.contains(findSubscribeCourseAndStudentParam.getUserId())) {
          userIds.add(findSubscribeCourseAndStudentParam.getUserId());
        }

        // 将demo课地址拼入返回值
        findSubscribeCourseAndStudentParam.setReportUrl(MemcachedUtil.getConfigValue(
            "contract_owner_url") + "/web/teacher/demo_report.html?subscribeCourseId="
            + findSubscribeCourseAndStudentParam.getKeyId() + "&subscribeType="
            + findSubscribeCourseAndStudentParam.getSubscribeType());
        findSubscribeCourseAndStudentParam.setCommentUrl(MemcachedUtil.getConfigValue(
            "contract_owner_url") + "/web/teacher/demo_comment.html?subscribeCourseId="
            + findSubscribeCourseAndStudentParam.getKeyId() + "&subscribeType="
            + findSubscribeCourseAndStudentParam.getSubscribeType());

      }
    } else {
      return page;
    }
    // 查询老师时间和房间信息
    List<TeacherTimeParam> teacherTimeList = teacherTimeEntityDao
        .findTeacherTimeAndRoomByKeyIds(teacherTimeIds);
    Map<String, String> teacherTimeMap = new HashMap<String, String>();
    Map<String, Boolean> teacherTimeAttendMap = new HashMap<String, Boolean>();
    Map<String, String> roomUrlMap = new HashMap<String, String>();
    Map<String, String> meetingKeyMap = new HashMap<String, String>();
    Map<String, String> hostIdKeyMap = new HashMap<String, String>();

    // 行专列
    if (teacherTimeList != null && teacherTimeList.size() > 0) {
      for (TeacherTimeParam findTeacherTimeParam : teacherTimeList) {
        teacherTimeMap.put(findTeacherTimeParam.getKeyId(),
            findTeacherTimeParam.getWebexRoomHostId());
        teacherTimeAttendMap.put(findTeacherTimeParam.getKeyId(),
            findTeacherTimeParam.getIsAttend());
        roomUrlMap.put(findTeacherTimeParam.getKeyId(), findTeacherTimeParam.getWebexRequestUrl());
        meetingKeyMap.put(findTeacherTimeParam.getKeyId(), findTeacherTimeParam
            .getWebexMeetingKey());
        hostIdKeyMap.put(findTeacherTimeParam.getKeyId(), findTeacherTimeParam
            .getWebexRoomHostId());
      }
    }

    // 查询老师
    List<FindTeacherByKeyIdsParam> teacherList = teacherEntityDao.findTeacherByKeyIds(teacherIds);
    Map<String, String> teacherMap = new HashMap<String, String>();
    // 行专列
    if (teacherList != null && teacherList.size() > 0) {
      for (FindTeacherByKeyIdsParam findTeacherByKeyIdsParam : teacherList) {
        teacherMap.put(findTeacherByKeyIdsParam.getKeyId(),
            findTeacherByKeyIdsParam.getThirdFrom());
      }
    }

    // 查询学员付费信息
    List<AdminOrderAndOrderSplitParam> orderSplitList = adminOrderCourseSplitEntityDao
        .findListOrderAndOrderSplit(userIds);
    Map<String, List<AdminOrderAndOrderSplitParam>> userSplitMap =
        new HashMap<String, List<AdminOrderAndOrderSplitParam>>();
    // 将结果根据用户分成不同的组
    if (orderSplitList != null && orderSplitList.size() > 0) {
      List<AdminOrderAndOrderSplitParam> userSplitList = null;
      for (AdminOrderAndOrderSplitParam adminOrderAndOrderSplitParam : orderSplitList) {
        userSplitList = userSplitMap.get(adminOrderAndOrderSplitParam.getUserId());
        if (userSplitList == null) {
          userSplitList = new ArrayList<AdminOrderAndOrderSplitParam>();
          userSplitList.add(adminOrderAndOrderSplitParam);
          userSplitMap.put(adminOrderAndOrderSplitParam.getUserId(), userSplitList);
        } else {
          userSplitList.add(adminOrderAndOrderSplitParam);
        }
      }
    }

    // 将信息拼入返回值
    if (subscribeCourseAndStudentList != null && subscribeCourseAndStudentList.size() > 0) {
      for (FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam : subscribeCourseAndStudentList) {
        findSubscribeCourseAndStudentParam
            .setThirdFrom(teacherMap.get(findSubscribeCourseAndStudentParam.getTeacherId()));
        findSubscribeCourseAndStudentParam.setRoomId(
            teacherTimeMap.get(findSubscribeCourseAndStudentParam.getTeacherTimeId()));
        findSubscribeCourseAndStudentParam.setIsAttend(
            teacherTimeAttendMap.get(findSubscribeCourseAndStudentParam.getTeacherTimeId()));
        findSubscribeCourseAndStudentParam.setWebexRequestUrl(roomUrlMap.get(
            findSubscribeCourseAndStudentParam
                .getTeacherTimeId()));
        findSubscribeCourseAndStudentParam.setWebexMeetingKey(meetingKeyMap.get(
            findSubscribeCourseAndStudentParam
                .getTeacherTimeId()));
        findSubscribeCourseAndStudentParam.setWebexRoomHostId(hostIdKeyMap.get(
            findSubscribeCourseAndStudentParam
                .getTeacherTimeId()));

        // 课程类型转化为汉字
        findSubscribeCourseAndStudentParam.setCourseTypeChineseName(((CourseType) MemcachedUtil
            .getValue((String) findSubscribeCourseAndStudentParam.getCourseType()))
                .getCourseTypeChineseName());

        // 拼入成交金额返回值
        mosaicAmount(findSubscribeCourseAndStudentParam, userSplitMap.get(
            findSubscribeCourseAndStudentParam.getUserId()));
      }
    }
    return page;
  }

  /**
   * Title: 拼入成交金额<br>
   * Description: mosaicAmount<br>
   * CreateDate: 2017年6月8日 下午1:58:38<br>
   * 
   * @category 拼入成交金额
   * @author seven.gz
   */
  private void mosaicAmount(FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam,
      List<AdminOrderAndOrderSplitParam> userSplitList) {
    if (userSplitList == null || userSplitList.size() == 0) {
      return;
    }
    int payAmount = 0;
    for (AdminOrderAndOrderSplitParam adminOrderAndOrderSplitParam : userSplitList) {
      if (adminOrderAndOrderSplitParam.getUpdateDate()
          .getTime() >= findSubscribeCourseAndStudentParam.getEndTime().getTime()) {
        payAmount += adminOrderAndOrderSplitParam.getSplitPrice();
      }
    }
    findSubscribeCourseAndStudentParam.setTransactionAmount(payAmount);
  }

}
