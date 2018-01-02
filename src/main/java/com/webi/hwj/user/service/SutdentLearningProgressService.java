package com.webi.hwj.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.admin.service.AdminConfigService;
import com.webi.hwj.coursetype.constant.CourseTypeConstant;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.index.param.FindLearningProgressParam;
import com.webi.hwj.index.util.OrderCourseProgressUtil;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseEntityDao;
import com.webi.hwj.ordercourse.entitydao.OrderCourseEntityDao;
import com.webi.hwj.ordercourse.entitydao.OrderCourseOptionEntityDao;
import com.webi.hwj.ordercourse.param.ContractLearningProgressParam;
import com.webi.hwj.ordercourse.param.FindOrderCourseCountParam;
import com.webi.hwj.statistics.dao.StatisticsTellmemoreDayDao;
import com.webi.hwj.statistics.entity.StatisticsTellmemoreDay;
import com.webi.hwj.statistics.param.FindStatisticsTellmemoreParam;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.dao.SubscribeSupplementEntityDao;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseCountParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeSupplementCountParam;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.tellmemore.util.TellmemoreUtil;
import com.webi.hwj.user.constant.UserConstant;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;
import com.webi.hwj.user.param.FindRsaEffectiveWorkTimeParam;
import com.webi.hwj.user.param.GeneralStudentInfoParam;
import com.webi.hwj.user.param.StudentLearningProgressParam;
import com.webi.hwj.user.util.AdminUserDateUtil;
import com.webi.hwj.user.util.AdminUserUtil;
import com.webi.hwj.user.util.CompletePercentUtil;
import com.webi.hwj.util.CalendarUtil;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SutdentLearningProgressService {
  private static Logger logger = Logger.getLogger(SutdentLearningProgressService.class);
  @Resource
  AdminUserEntityDao adminUserEntityDao;
  @Resource
  AdminOrderCourseEntityDao adminOrderCourseEntityDao;
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  StatisticsTellmemoreDayDao statisticsTellmemoreDayDao;
  @Resource
  SubscribeSupplementEntityDao subscribeSupplementEntityDao;
  @Resource
  AdminConfigService adminConfigService;
  @Resource
  TellmemoreService tellmemoreService;
  @Resource
  OrderCourseEntityDao orderCourseEntityDao;
  @Resource
  OrderCourseOptionEntityDao orderCourseOptionEntityDao;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;

  /**
   * 
   * Title: 查询学员学习进度<br>
   * Description: 查询学员学习进度<br>
   * CreateDate: 2016年6月8日 下午4:42:24<br>
   * 
   * @category 查询学员学习进度
   * @author seven.gz
   * @return
   * @throws Exception
   */
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE,rollbackFor={Exception.class})
  public StudentLearningProgressParam findStudentLearningProgress(String userId, String orderId)
      throws Exception {

    // 查询学员信息
    User user = adminUserEntityDao.findOneByKeyId(userId);

    // 获取当前时间
    Date currentTime = new Date();
    // 查询合同信息
    List<FindOrderCourseCountParam> findOrderCourseCountParamList = adminOrderCourseEntityDao
        .findOrderCourseCount(orderId);
    // 如果没有合法合同就返回null
    if (findOrderCourseCountParamList == null || findOrderCourseCountParamList.size() < 1) {
      return null;
    }
    // 存放返回结果
    StudentLearningProgressParam studentLearningProgressParam = new StudentLearningProgressParam();
    // 获得合同信息（这里只是拿主表信息，通过合同id联查，并且通过id筛选所以拿第一个就行）
    FindOrderCourseCountParam findOrderCourseCountParam = findOrderCourseCountParamList.get(0);
    // 计算合同天数信息和期望学习进度
    calculationOrderDays(studentLearningProgressParam, findOrderCourseCountParam, currentTime);
    // 计算上课数信息和时间学习进度
    calculationCourseCount(studentLearningProgressParam, userId, orderId, currentTime,
        findOrderCourseCountParamList);
    // 计算当前级别上课数
    calculationCurrentLevelCourseCount(studentLearningProgressParam, userId, user, currentTime);
    // 计算RSA课件信息
    findRsaEffectiveExerciseDays(studentLearningProgressParam, userId, currentTime);
    // 获得小嗨要说的话
    int percentageDifference = studentLearningProgressParam.getActualLearningProgress()
        - studentLearningProgressParam.getExpectedLearningProgress();

    if (studentLearningProgressParam.getConsumeOrderDays() < UserConstant.NEW_STUDENT_DAYS) {
      percentageDifference = UserConstant.NEW_STUDENT_PERCENTAGE;
    }
    studentLearningProgressParam
        .setEncouragementWords(adminConfigService.findRandomSentence(percentageDifference));
    return studentLearningProgressParam;
  }

  /**
   * 
   * Title: 计算合同天数和合同期望进度<br>
   * Description: 计算进行天数、剩余天数<br>
   * CreateDate: 2016年6月8日 下午5:41:00<br>
   * 
   * @category 计算合同天数和合同期望进度
   * @author seven.gz
   * @param studentLearningProgressParam
   * @param findOrderCourseCountParam
   * @param currentTime
   */
  private void calculationOrderDays(StudentLearningProgressParam studentLearningProgressParam,
      FindOrderCourseCountParam findOrderCourseCountParam, Date currentTime) {
    // 计算合同进行天数
    int consumeOrderDays = AdminUserDateUtil
        .calculationDaysBetweenTwoDate(findOrderCourseCountParam.getStartOrderTime(), currentTime)
        + 1;
    // 计算合同总天数
    int totalDays = AdminUserDateUtil.calculationDaysBetweenTwoDate(
        findOrderCourseCountParam.getStartOrderTime(),
        findOrderCourseCountParam.getEndOrderTime());

    studentLearningProgressParam.setConsumeOrderDays(consumeOrderDays);
    // 合同剩余天数 = 合同总天数-合同进行天数
    studentLearningProgressParam.setRemainOrderDays(totalDays - consumeOrderDays);
    // 合同进度 按百分比
    // studentLearningProgressParam.setExpectedLearningProgress(consumeOrderDays
    // * 100 / totalDays);
    // 向上取整
    studentLearningProgressParam
        .setExpectedLearningProgress(
            AdminUserUtil.calculatePercentage(consumeOrderDays, totalDays));
  }

  /**
   * 
   * Title: 计算课时数信息<br>
   * Description: 计算课时数信息<br>
   * CreateDate: 2016年6月8日 下午6:05:57<br>
   * 
   * @category 计算课时数信息
   * @author seven.gz
   * @param studentLearningProgressParam
   * @param userId
   * @param orderId
   * @param currentTime
   * @param findOrderCourseCountParamList
   * @throws Exception
   */
  private void calculationCourseCount(StudentLearningProgressParam studentLearningProgressParam,
      String userId,
      String orderId, Date currentTime,
      List<FindOrderCourseCountParam> findOrderCourseCountParamList)
          throws Exception {
    // 查询补课数
    List<FindSubscribeSupplementCountParam> findSubscribeSupplementCountParams =
        subscribeSupplementEntityDao
            .findCountsByUserId(userId, orderId);
    // 查询此学员有效预约并且结束时间小于等于当前时间的课程数
    List<FindSubscribeCourseCountParam> subscribeCourseCountList = adminSubscribeCourseDao
        .findCountByUserIdAndEndTime(userId, currentTime, orderId);
    // 将统计出的数设置到 GeneralStudentInfoParam 中
    GeneralStudentInfoParam generalStudentInfoParam = new GeneralStudentInfoParam();
    AdminUserUtil.calculationCourseCount(generalStudentInfoParam, findOrderCourseCountParamList,
        findSubscribeSupplementCountParams, subscribeCourseCountList);
    // 总课时数 = 1v1课时数 + 1vn课时数 （含补课）
    int totalCourseCount = generalStudentInfoParam.getCoreCourseCount()
        + generalStudentInfoParam.getExtensionCourseCount();
    // 总剩余课程数 = 1v1 剩余课程数 + 1vN剩余课程数
    int remainCourseCount = generalStudentInfoParam.getCoreRemainCourseCount()
        + generalStudentInfoParam.getExtensionRemainCourseCount();
    // 消耗课程数 = 总课程数 - 剩余课程数
    int consumeCourseCount = totalCourseCount - remainCourseCount;
    // 计算onshow课程数
    int noShowCourseCount = consumeCourseCount - generalStudentInfoParam.getCoreShowCourseCount()
        - generalStudentInfoParam.getExtensionShowCourseCount();

    studentLearningProgressParam.setConsumeCourseCount(consumeCourseCount);
    studentLearningProgressParam.setTotalCourseCount(totalCourseCount);
    studentLearningProgressParam.setRemainCourseCount(remainCourseCount);
    studentLearningProgressParam.setNoShowCourseCount(noShowCourseCount);
    // 计算实际学习进度
    // studentLearningProgressParam.setActualLearningProgress(consumeCourseCount
    // * 100 / totalCourseCount);
    // 向上取整
    studentLearningProgressParam
        .setActualLearningProgress(
            AdminUserUtil.calculatePercentage(consumeCourseCount, totalCourseCount));
  }

  /**
   * 
   * Title: 计算本级课程数、升级需要的课程数<br>
   * Description: 计算本级课程数、升级需要的课程数<br>
   * CreateDate: 2016年6月12日 上午10:14:52<br>
   * 
   * @category 计算本级课程数、升级需要的课程数
   * @author seven.gz
   * @param userId
   * @param user
   * @param currentTime
   * @throws Exception
   */
  private FindLearningProgressParam calculationCurrentLevelCourseCount(
      StudentLearningProgressParam studentLearningProgressParam,
      String userId, User user, Date currentTime) throws Exception {
    FindLearningProgressParam findLearningProgressParam = new FindLearningProgressParam();

    // 当前级别core已上课程数
    int currentLevelOne2OneCourseCount = adminSubscribeCourseDao.findCountByUserIdAndLevel(userId,
        user.getCurrentLevel(), currentTime, null, true);
    // 当前级别extension已上课程数
    int currentLevelOne2ManyCourseCount = adminSubscribeCourseDao.findCountByUserIdAndLevel(userId,
        user.getCurrentLevel(), currentTime, null, false);
    // 升级需要的core课程总数
    Integer one2oneLevelupCourseCount = Integer
        .valueOf(MemcachedUtil.getConfigValue("one2one_levelup_course_count"));
    // 升级需要的extension课程总数
    Integer one2ManyLevelupCourseCount = Integer
        .valueOf(MemcachedUtil.getConfigValue("one2many_levelup_course_count"));
    // 到达下一级还需要的1v1课程数
    int toNextLevelOne2OneCourseCount = 0;
    // 到达下一级还需要的1vN课程数
    int toNextLevelOne2ManyCourseCount = 0;
    // 当消耗的1v1课程数 >= 需要的1v1课程数
    if (currentLevelOne2OneCourseCount >= one2oneLevelupCourseCount) {
      currentLevelOne2OneCourseCount = one2oneLevelupCourseCount;
    } else {
      // 到达下一级需要的课程数 = 需要上的课程数 - 已上的课程数
      toNextLevelOne2OneCourseCount = one2oneLevelupCourseCount - currentLevelOne2OneCourseCount;
    }
    // 当消耗的1vN课程数 >= 需要的1vN课程数
    if (currentLevelOne2ManyCourseCount >= one2ManyLevelupCourseCount) {
      currentLevelOne2ManyCourseCount = one2ManyLevelupCourseCount;
    } else {
      // 到达下一级需要的课程数 = 需要上的课程数 - 已上的课程数
      toNextLevelOne2ManyCourseCount = one2ManyLevelupCourseCount - currentLevelOne2ManyCourseCount;
    }

    // 升级需要的课程总数 = 升级需要的1v1课程总数 + 升级需要的1vN课程总数
    studentLearningProgressParam
        .setLevelupTotalCourseCount(one2oneLevelupCourseCount + one2ManyLevelupCourseCount);
    // 到达下一级还需要的1v1课程数
    studentLearningProgressParam.setToNextLevelOne2OneCourseCount(toNextLevelOne2OneCourseCount);
    // 到达下一级还需要的1vN课程数
    studentLearningProgressParam.setToNextLevelOne2ManyCourseCount(toNextLevelOne2ManyCourseCount);
    // 当前级别1v1已上课程数
    studentLearningProgressParam.setCurrentLevelOne2OneCourseCount(currentLevelOne2OneCourseCount);
    // 当前级别1vn已上课程数
    studentLearningProgressParam
        .setCurrentLevelOne2ManyCourseCount(currentLevelOne2ManyCourseCount);
    // 学员当前等级
    studentLearningProgressParam.setCurrentLevel(user.getCurrentLevel());

    return findLearningProgressParam;
  }

  /**
   * 
   * Title: weixin查找当月RSA有效学习的日期<br>
   * Description: weixin查找当月RSA有效学习的日期<br>
   * CreateDate: 2016年6月12日 下午5:21:29<br>
   * 
   * @category weixin查找当月RSA有效学习的日期
   * @author seven.gz
   * @param studentLearningProgressParam
   * @param userId
   * @param currentTime
   * @throws Exception
   */
  public void findRsaEffectiveExerciseDays(
      StudentLearningProgressParam studentLearningProgressParam, String userId,
      Date currentTime) throws Exception {
    // 有效的RSA学习的时间长度
    Integer effectiveExerciseTime = Integer
        .valueOf(MemcachedUtil.getConfigValue("rsa_effective_exercise_time"));
    studentLearningProgressParam
        .setEffectiveLearningDays(findEffectiveDays(effectiveExerciseTime, currentTime, userId));
    studentLearningProgressParam.setEffectiveExerciseTime(effectiveExerciseTime);
  }

  /**
   * 
   * Title: 按月查找RSA有效学习的日期<br>
   * Description: 按月查找RSA有效学习的日期<br>
   * CreateDate: 2016年9月7日 下午5:24:03<br>
   * 
   * @category 按月查找RSA有效学习的日期
   * @author seven.gz
   * @param effectiveExerciseTime
   * @param currentTime
   * @param userId
   * @return
   * @throws Exception
   */
  public List<String> findEffectiveDays(int effectiveExerciseTime, Date queryDate, String userId)
      throws Exception {
    // 获得当月第一天0点
    Date firstDayOfMonth = CalendarUtil.getFirstDayOfMonth(queryDate);
    // 昨天的凌晨时间
    Date yesterday = CalendarUtil.getNextNDay(firstDayOfMonth, -1);
    // 获得下个月第一天0点
    Date firstDayOfNextMonth = CalendarUtil.getFirstDayOfNextMonth(queryDate);
    // 获得当月学习时间
    List<FindStatisticsTellmemoreParam> findStatisticsTellmemoreParams = statisticsTellmemoreDayDao
        .findInfoByTime(userId, yesterday, firstDayOfNextMonth);

    // 根据抓取目前用户学习总时间，用来计算今天的学习时间,percent表中查询
    List<Integer> studentLearnTimeList = tellmemoreService.findTellmemoreTotalTimeByUserId(userId);

    List<String> effectiveLearningDays = new ArrayList<String>();
    if (findStatisticsTellmemoreParams != null && findStatisticsTellmemoreParams.size() > 0) {
      // 有效课件学习时间的天
      for (FindStatisticsTellmemoreParam findStatisticsTellmemoreParam : findStatisticsTellmemoreParams) {
        // 判断此天是否是有效学习
        if (TellmemoreUtil.compareWithMinute(
            findStatisticsTellmemoreParam.getChangeTmmWorkingtime(),
            effectiveExerciseTime) && findStatisticsTellmemoreParam.getCreateDate()
                .getTime() > firstDayOfMonth.getTime()) {
          effectiveLearningDays
              .add(DateUtil.dateToStrYYMMDD(findStatisticsTellmemoreParam.getCreateDate()));
        }
      }

      // 获得昨天学习总时间
      List<Integer> yesterdayTotalTime = TellmemoreUtil
          .getTimeInInt(findStatisticsTellmemoreParams.get(0).getTotalTmmWorkingtime());
      // 获取今天和昨天学习时间差值
      studentLearnTimeList.set(0, studentLearnTimeList.get(0) - yesterdayTotalTime.get(0));
      studentLearnTimeList.set(1, studentLearnTimeList.get(1) - yesterdayTotalTime.get(1));
      studentLearnTimeList.set(2, studentLearnTimeList.get(2) - yesterdayTotalTime.get(2));
    }
    Date currentDate = new Date();
    // 如果给定时间的下个月时间大于当前时间 &&当天没有统计过
    if (firstDayOfNextMonth.getTime() > currentDate.getTime()) {
      if (findStatisticsTellmemoreParams == null || findStatisticsTellmemoreParams.size() == 0
          || !DateUtil.dateToStrYYMMDD(currentDate)
              .equals(DateUtil
                  .dateToStrYYMMDD(findStatisticsTellmemoreParams.get(0).getCreateDate()))) {
        // 判断今天是否为有效学习
        if (TellmemoreUtil.compareWithMinute(TellmemoreUtil.getTimeInString(studentLearnTimeList),
            effectiveExerciseTime)) {
          effectiveLearningDays.add(DateUtil.dateToStrYYMMDD(currentDate));
        }
      }
    }
    return effectiveLearningDays;
  }

  /**
   * 
   * Title: PC按月查找RSA有效学习的日期<br>
   * Description: PC按月查找RSA有效学习的日期<br>
   * CreateDate: 2016年9月7日 下午5:35:04<br>
   * 
   * @category PC按月查找RSA有效学习的日期
   * @author seven.gz
   * @param currentTime
   * @param userId
   * @return
   * @throws Exception
   */
  public FindRsaEffectiveWorkTimeParam findRsaEffectiveWorkTime(Date queryDate, String userId)
      throws Exception {
    FindRsaEffectiveWorkTimeParam findRsaEffectiveWorkTimeParam =
        new FindRsaEffectiveWorkTimeParam();
    Integer effectiveExerciseTime = Integer
        .valueOf(MemcachedUtil.getConfigValue("rsa_effective_exercise_time"));
    findRsaEffectiveWorkTimeParam
        .setEffectiveLearningDays(findEffectiveDays(effectiveExerciseTime, queryDate, userId));
    findRsaEffectiveWorkTimeParam.setRsaEffectiveExerciseTime(effectiveExerciseTime.toString());
    return findRsaEffectiveWorkTimeParam;
  }

  /**
   * 
   * Title: 查询学员学习进度<br>
   * Description: findLearningProgress<br>
   * CreateDate: 2017年7月20日 下午7:50:51<br>
   * 
   * @category 查询学员学习进度
   * @author seven.gz
   * @param userId
   *          学员ID
   * @param currentTime
   *          查询时间
   */
  public FindLearningProgressParam findLearningProgress(String userId, Date currentTime)
      throws Exception {
    User user = adminUserEntityDao.findOneByKeyId(userId);

    FindLearningProgressParam findLearningProgressParam = new FindLearningProgressParam();

    // 查询升级课时信息
    StudentLearningProgressParam studentLearningProgressParam = new StudentLearningProgressParam();
    calculationCurrentLevelCourseCount(studentLearningProgressParam, userId, user, currentTime);
    PropertyUtils.copyProperties(findLearningProgressParam, studentLearningProgressParam);

    // 查询上课信息
    int count1v1 = 0;
    int count1vn = 0;
    List<FindSubscribeCourseCountParam> courseCountList = subscribeCourseEntityDao
        .findCountFinishCourseType(userId, currentTime);
    if (courseCountList != null && courseCountList.size() > 0) {
      for (FindSubscribeCourseCountParam findSubscribeCourseCountParam : courseCountList) {
        if (((CourseType) MemcachedUtil.getValue(findSubscribeCourseCountParam.getCourseType()))
            .getCourseTypeFlag() == CourseTypeConstant.COURSE_TYPE_FLAG_1V1) {
          count1v1 += findSubscribeCourseCountParam.getCourseCount();
        } else {
          count1vn += findSubscribeCourseCountParam.getCourseCount();
        }
      }
    }
    findLearningProgressParam.setUserFinishCourseCount(count1v1 + count1vn);
    findLearningProgressParam.setUserTalkTime(count1v1 * 0.5 + count1vn);

    // 查询实际学习进度
    List<ContractLearningProgressParam> startingContractList =
        new ArrayList<ContractLearningProgressParam>();
    startingContractList = orderCourseEntityDao.findStartingContractListByUserId(userId,
        currentTime);
    // 当前时间下，有正在开课的合同时候
    if (startingContractList != null && startingContractList.size() != 0) {
      ContractLearningProgressParam startingContract = startingContractList.get(0);
      /**
       * 使用码表中的category_type1 2 3等 来显示 合同类型
       */
      startingContract.setCategoryTypeChineseName(MemcachedUtil.getConfigValue(startingContract
          .getCategoryType()));

      // 课程option数据课时
      List<OrderCourseOption> optionStartingContract = orderCourseOptionEntityDao
          .findDetailsOptionByOrderId(startingContract.getKeyId());

      startingContract = OrderCourseProgressUtil
          .startingContrartProgress(startingContract, optionStartingContract);
      findLearningProgressParam.setActualLearningProgress(startingContract.getCourseProgress());
    }
    return findLearningProgressParam;
  }

  /**
   * Title: 查询学员rsa学习进度<br>
   * Description: findRsaLearningProgress<br>
   * CreateDate: 2017年9月13日 上午11:44:50<br>
   * 
   * @category 查询学员rsa学习进度
   * @author seven.gz
   * @param page
   * @param rows
   * @param userId
   */
  public Page findRsaLearningProgress(Integer page, Integer rows, String userId)
      throws Exception {
    Page returnPage = statisticsTellmemoreDayDao.findPageRsaLearningProgress(page, rows, userId);

    List<StatisticsTellmemoreDay> datas = returnPage.getDatas();

    if (datas != null && datas.size() > 0) {
      for (StatisticsTellmemoreDay statisticsTellmemoreDay : datas) {
        // 将学习时长转化为分钟
        statisticsTellmemoreDay.setChangeTmmWorkingtime(CompletePercentUtil.translateMinute(
            statisticsTellmemoreDay
                .getChangeTmmWorkingtime()) + "");
        statisticsTellmemoreDay.setTotalTmmWorkingtime(CompletePercentUtil.translateMinute(
            statisticsTellmemoreDay
                .getTotalTmmWorkingtime()) + "");
      }
    }

    // 如果第一页
    if (page == 1) {
      // 根据抓取目前用户学习总时间，用来计算今天的学习时间,percent表中查询
      List<Integer> studentLearnTimeList = tellmemoreService.findTellmemoreTotalTimeByUserId(
          userId);
      int todayTotalLearnMinute = CompletePercentUtil.translateMinute(studentLearnTimeList);

      // 今天的学习时间
      int todayLearnMinute = todayTotalLearnMinute;

      if (datas != null && datas.size() > 0) {
        todayLearnMinute = todayTotalLearnMinute - Integer.valueOf(datas.get(0)
            .getTotalTmmWorkingtime());

        // 如果小于0说明是第二个合同的第一天
        if (todayLearnMinute < 0) {
          todayLearnMinute = todayTotalLearnMinute;
        }
      }

      // 今天的学习数据
      StatisticsTellmemoreDay todayLearnInfo = new StatisticsTellmemoreDay();
      todayLearnInfo.setCreateDate(new Date());
      todayLearnInfo.setChangeTmmWorkingtime(todayLearnMinute + "");

      // 将今天你的插入
      datas.add(0, todayLearnInfo);
      // 删除最后一天的
      // datas.remove(datas.size() - 1);
    }
    return returnPage;
  }
}