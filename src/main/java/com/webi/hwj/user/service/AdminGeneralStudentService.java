package com.webi.hwj.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseEntityDao;
import com.webi.hwj.ordercourse.param.FindOrderCourseCountParam;
import com.webi.hwj.statistics.dao.StatisticsTellmemoreDayDao;
import com.webi.hwj.statistics.param.FindStatisticsTellmemoreParam;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeSupplementEntityDao;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseCountParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeSupplementCountParam;
import com.webi.hwj.tellmemore.util.TellmemoreUtil;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;
import com.webi.hwj.user.param.GeneralStudentInfoParam;
import com.webi.hwj.user.util.AdminUserUtil;
import com.webi.hwj.util.CalendarUtil;

/**
 * 
 * Title: 后台统计用户信息类<br>
 * Description: 后台统计用户信息类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月31日 上午11:03:10
 * 
 * @author seven.gz
 */
@Service
public class AdminGeneralStudentService {
  private static Logger logger = Logger.getLogger(AdminGeneralStudentService.class);
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

  /**
   * 
   * Title: 通过学员id获取学员上课数量信息<br>
   * Description: 通过学员id获取学员上课数量信息<br>
   * CreateDate: 2016年6月1日 下午5:18:06<br>
   * 
   * @category 通过学员id获取学员上课数量信息
   * @author seven.gz
   * @param userId
   * @return
   * @throws Exception
   */
  public GeneralStudentInfoParam findGeneralStudentDetailInfo(String userId) throws Exception {
    GeneralStudentInfoParam generalStudentInfoParam = new GeneralStudentInfoParam();
    if (!StringUtils.isEmpty(userId)) {
      Date currentDate = new Date();
      // 获得当前学员1v1，1vn课程数量信息
      findOrderCourseCountInfo(userId, currentDate, generalStudentInfoParam);
      // 获得当前级别1v1，1vn已上课程数信息
      findCurrentLevelCourseCount(userId, currentDate, generalStudentInfoParam);
      // 获得当月RSA学习时间
      findWorkingTimeOfMonth(userId, currentDate, generalStudentInfoParam);
    }
    return generalStudentInfoParam;
  }

  /**
   * 
   * Title: 获得合同课程数信息<br>
   * Description: 获得合同课程数信息<br>
   * CreateDate: 2016年6月2日 上午11:39:06<br>
   * 
   * @category 获得合同课程数信息
   * @author seven.gz
   * @param userId
   * @param currentTime
   * @param generalStudentInfoParam
   * @throws Exception
   */
  public void findOrderCourseCountInfo(String userId, Date currentTime,
      GeneralStudentInfoParam generalStudentInfoParam) throws Exception {

    // 查询此学员的合同中课程数（这里只查询通用合同，默认只有一个有效通用合同）
    List<String> userIds = new ArrayList<String>();
    userIds.add(userId);
    List<FindOrderCourseCountParam> orderCourseCountList = adminOrderCourseEntityDao
        .findOrderCourseCount(userIds,
            "category_type2");
    String orderId = null;
    if (orderCourseCountList != null && orderCourseCountList.size() > 0) {
      orderId = orderCourseCountList.get(0).getOrderId();
    }
    // 查询补课数
    List<FindSubscribeSupplementCountParam> findSubscribeSupplementCountParams =
        subscribeSupplementEntityDao
            .findCountsByUserId(userId, orderId);
    // 查询此学员有效预约并且结束时间小于等于当前时间的课程数
    List<FindSubscribeCourseCountParam> subscribeCourseCountList = adminSubscribeCourseDao
        .findCountByUserIdAndEndTime(userId, currentTime, orderId);
    AdminUserUtil.calculationCourseCount(generalStudentInfoParam, orderCourseCountList,
        findSubscribeSupplementCountParams,
        subscribeCourseCountList);
    generalStudentInfoParam.setOrderId(orderId);
  }

  /**
   * 
   * Title: 查询当前级别已上课程数<br>
   * Description: 查询当前级别已上课程数<br>
   * CreateDate: 2016年6月1日 下午9:24:16<br>
   * 
   * @category 查询当前级别已上课程数
   * @author seven.gz
   * @param userId
   * @param currentTime
   * @throws Exception
   */
  private void findCurrentLevelCourseCount(String userId, Date currentTime,
      GeneralStudentInfoParam generalStudentInfoParam) throws Exception {
    User user = adminUserEntityDao.findOneByKeyId(userId);
    if (user == null) {
      return;
    }
    int currentLevelCoreCourseCount = adminSubscribeCourseDao.findCountByUserIdAndLevel(userId,
        // 当前级别core已上课程数
        user.getCurrentLevel(), currentTime, generalStudentInfoParam.getOrderId(), true);
    // 当前级别extension已上课程数
    int currentLevelExtensionCourseCount = adminSubscribeCourseDao.findCountByUserIdAndLevel(userId,
        // 当前级别core已上课程数
        user.getCurrentLevel(), currentTime, generalStudentInfoParam.getOrderId(), false);

    generalStudentInfoParam.setCurrentLevelCoreCourseCount(currentLevelCoreCourseCount);
    generalStudentInfoParam.setCurrentLevelExtensionCourseCount(currentLevelExtensionCourseCount);

  }

  /**
   * 
   * Title: 获取RSA课件的学习时间<br>
   * Description: 获取RSA课件的学习时间<br>
   * CreateDate: 2016年6月2日 上午9:35:48<br>
   * 
   * @category 获取RSA课件的学习时间
   * @author seven.gz
   * @param userId
   * @param currentTime
   * @param generalStudentInfoParam
   * @throws Exception
   */
  private void findWorkingTimeOfMonth(String userId, Date currentTime,
      GeneralStudentInfoParam generalStudentInfoParam) throws Exception {
    // 获得当月第一天0点
    Date firstDayOfMonth = CalendarUtil.getFirstDayOfMonth();
    // 获得当月学习时间
    List<FindStatisticsTellmemoreParam> findStatisticsTellmemoreParams = statisticsTellmemoreDayDao
        .findInfoByTime(userId, firstDayOfMonth, currentTime);
    if (findStatisticsTellmemoreParams != null && findStatisticsTellmemoreParams.size() > 0) {
      // 当月第一天的学习时间
      String workingTimeFirstDayOfMonth = findStatisticsTellmemoreParams
          .get(findStatisticsTellmemoreParams.size() - 1).getTotalTmmWorkingtime();
      // 当天的学习时间
      String changeTimeFirstDayOfMonth = findStatisticsTellmemoreParams
          .get(findStatisticsTellmemoreParams.size() - 1).getChangeTmmWorkingtime();
      // 前一天的学习时间
      String currentWorkingTime = findStatisticsTellmemoreParams.get(0).getTotalTmmWorkingtime();
      // 格式化学习时间
      List<Integer> workingTimeFirstDayOfMonths = TellmemoreUtil
          .getTimeInInt(workingTimeFirstDayOfMonth);
      List<Integer> currentWorkingTimes = TellmemoreUtil.getTimeInInt(currentWorkingTime);
      List<Integer> changeWorkingTimes = TellmemoreUtil.getTimeInInt(changeTimeFirstDayOfMonth);
      // 最后一次记录的学习时间 - 当月第一天的学习时间
      if (currentWorkingTimes != null && currentWorkingTimes.size() > 0) {
        int temp = 0;
        for (int i = 0; i < currentWorkingTimes.size(); i++) {
          // modify by seven 2017年4月24日11:09:51 如果结果是负数设置为0
          temp = currentWorkingTimes.get(i) - workingTimeFirstDayOfMonths.get(i)
              + changeWorkingTimes.get(i);
          currentWorkingTimes.set(i, temp >= 0 ? temp
              : currentWorkingTimes.get(i) + changeWorkingTimes.get(
                  i));
        }
      }
      // 转化计算结果
      String workingTimeString = TellmemoreUtil.getTimeInString(currentWorkingTimes);
      // 格式化前台输出
      String[] workingTimeStringStrs = workingTimeString.split(":");
      if (workingTimeStringStrs != null && workingTimeStringStrs.length > 2) {
        workingTimeString = workingTimeStringStrs[0] + "小时" + workingTimeStringStrs[1] + "分钟";
      }
      generalStudentInfoParam.setCurrentMonthTmmWorkingTime(workingTimeString);
    }
  }
}
