package com.webi.hwj.user.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.statistics.dao.TellmemorePercentEntityDao;
import com.webi.hwj.statistics.entity.TellmemorePercent;
import com.webi.hwj.statistics.param.FindLastWorkTimeParam;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.tellmemore.dao.TellmemorePercentDao;
import com.webi.hwj.user.constant.UserConstant;
import com.webi.hwj.user.dao.UserFollowupDao;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;
import com.webi.hwj.user.param.FindUserFollowupParam;
import com.webi.hwj.user.param.KeyFollowupStudentParam;
import com.webi.hwj.util.CalendarUtil;

@Service
public class AdminKeyFollowupService {
  @Resource
  AdminUserEntityDao adminUserEntityDao;
  @Resource
  TellmemorePercentDao tellmemorePercentDao;
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  TellmemorePercentEntityDao tellmemorePercentEntityDao;
  @Resource
  UserFollowupDao userFollowupDao;

  /**
   * 
   * Title: 查询重点跟进学员<br>
   * Description: 查询重点跟进学员<br>
   * CreateDate: 2016年7月5日 下午6:51:22<br>
   * 
   * @category 查询重点跟进学员
   * @author seven.gz
   * @param paramMap
   * @param learningCoachId
   * @param findType
   * @return
   * @throws Exception
   */
  public Page findKeyFollowupStudentPage(Map<String, Object> paramMap, String learningCoachId,
      String findType)
      throws Exception {
    Page page = null;
    // 新生跟进
    if ("newStudent".equals(findType)) {
      // 获得当前日期向前推31天的日期
      Calendar newStudentmilli = Calendar.getInstance();
      newStudentmilli.setTime(new Date());
      newStudentmilli.add(Calendar.DAY_OF_YEAR, -UserConstant.DAYS_OF_MONTH);
      page = adminUserEntityDao.findNewStudentPage(paramMap, newStudentmilli.getTime(),
          learningCoachId);
    }

    // 本月follow次数少于2次跟进
    if ("lessFollowup".equals(findType)) {
      Date firstDayOfMonth = CalendarUtil.getFirstDayOfMonth();
      page = adminUserEntityDao.findLessFollowupStudentPage(paramMap, firstDayOfMonth,
          learningCoachId,
          UserConstant.FOLLOWUP_COUNT);
    }

    // 两个月内合同结束人员
    if ("twoMonthEnd".equals(findType)) {
      Calendar endTime = Calendar.getInstance();
      endTime.setTime(new Date());
      // 从现在向后加 62 天
      endTime.add(Calendar.DAY_OF_YEAR, UserConstant.DAYS_OF_MONTH * 2);
      page = adminUserEntityDao.findTwoMonthEndStudentPage(paramMap, endTime.getTime(),
          learningCoachId);
    }

    // 合同超过一个月但是没有做过课件
    if ("neverDoneRsa".equals(findType)) {
      page = findNeverDoneRsa(paramMap, learningCoachId);
    }

    // 超过一个月未做课件
    if ("oneMonthNotDoRsa".equals(findType)) {
      page = findOneMonthNotDoRsa(paramMap, learningCoachId);
    }

    // 完成课件一周内未上课
    if ("notSubscribeClass".equals(findType)) {
      page = findNotSubscribeClass(paramMap, learningCoachId);
    }
    return page;
  }

  /**
   * 
   * Title: 查询合同超过一个月，但没有做过课件的学员信息<br>
   * Description: findNeverDoneRsa<br>
   * CreateDate: 2016年7月11日 下午5:23:37<br>
   * 
   * @category findNeverDoneRsa
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public Page findNeverDoneRsa(Map<String, Object> paramMap, String learningCoachId)
      throws Exception {
    // 查询出t_tellmemore_percent 中的数据
    List<Map<String, Object>> tellmemorePercentList = tellmemorePercentDao
        .findNeverDoneRsaStudent();
    List<String> userIds = new ArrayList<String>();
    if (tellmemorePercentList != null && tellmemorePercentList.size() > 0) {
      for (Map<String, Object> tellmemorePercent : tellmemorePercentList) {
        userIds.add((String) tellmemorePercent.get("user_id"));
      }
    }
    Calendar startTime = Calendar.getInstance();
    startTime.setTime(new Date());
    // 从现在向前减 31 天
    startTime.add(Calendar.DAY_OF_YEAR, -UserConstant.DAYS_OF_MONTH);
    return adminUserEntityDao.findNeverDoneRsaPage(paramMap, startTime.getTime(), learningCoachId,
        userIds);
  }

  /**
   * 
   * Title: 一个月未做课件<br>
   * Description: 一个月未做课件<br>
   * CreateDate: 2016年7月12日 下午8:18:55<br>
   * 
   * @category 一个月未做课件
   * @author seven.gz
   * @param paramMap
   * @param learningCoachId
   * @return
   * @throws Exception
   */
  public Page findOneMonthNotDoRsa(Map<String, Object> paramMap, String learningCoachId)
      throws Exception {
    Date currentDate = new Date();
    // 查出上过 1v1 课的学员和最后一次上课时间
    List<SubscribeCourse> subscribeList = adminSubscribeCourseDao.findWentClassStudents(
        "course_type1",
        currentDate);
    // 上过1v1课的学员
    List<String> userIds = new ArrayList<String>();
    // 上过课的学员和最后一次上课时间
    Map<String, Date> lastClassMap = new HashMap<String, Date>();
    if (subscribeList != null && subscribeList.size() > 0) {
      for (SubscribeCourse subscribeCourse : subscribeList) {
        userIds.add(subscribeCourse.getUserId());
        lastClassMap.put(subscribeCourse.getUserId(), subscribeCourse.getStartTime());
      }
    }

    // 查询出上次做课件时间在一个月之前的学员和做课件时间
    Long newStudentmilli = currentDate.getTime()
        - (1000L * 60 * 60 * 24 * UserConstant.DAYS_OF_MONTH);
    List<FindLastWorkTimeParam> lastWorkTimes = tellmemorePercentEntityDao.findLastWorkTime(userIds,
        new Date(newStudentmilli));
    // 一个月未做课件的学员
    List<String> workLastMonthUserIds = new ArrayList<String>();
    // 学员的做课件时间
    Map<String, Date> lastCoursewareDateMap = new HashMap<String, Date>();
    if (lastWorkTimes != null && lastWorkTimes.size() > 0) {
      for (FindLastWorkTimeParam lastWorkTime : lastWorkTimes) {
        workLastMonthUserIds.add(lastWorkTime.getUserId());
        lastCoursewareDateMap.put(lastWorkTime.getUserId(), lastWorkTime.getLastWorkTime());
      }
    } else {
      return new Page();
    }
    // 查询出follow数据
    List<FindUserFollowupParam> userFollowups = userFollowupDao
        .findFollowupByUserIds(workLastMonthUserIds);
    // 统计最后一次做课件开始follow的次数
    Map<String, Integer> followupCountMap = new HashMap<String, Integer>();
    if (userFollowups != null && userFollowups.size() > 0) {
      for (FindUserFollowupParam findUserFollowupParam : userFollowups) {
        if (lastCoursewareDateMap.containsKey(findUserFollowupParam.getUserId())) {
          // 判断在最后一次做课件后的follow
          if (lastCoursewareDateMap.get(findUserFollowupParam.getUserId())
              .getTime() < findUserFollowupParam
                  .getCreateDate().getTime()) {
            if (followupCountMap.containsKey(findUserFollowupParam.getUserId())
                && lastCoursewareDateMap.containsKey(findUserFollowupParam.getUserId())) {
              // 计数加一
              followupCountMap.put(findUserFollowupParam.getUserId(),
                  followupCountMap.get(findUserFollowupParam.getUserId()) + 1);
            } else {
              followupCountMap.put(findUserFollowupParam.getUserId(), 1);
            }
          }
        }
      }
    }
    // 查询出学员的信息
    Page page = adminUserEntityDao.findStudentInfoPage(paramMap, learningCoachId,
        workLastMonthUserIds);
    // 拼入数据
    List<KeyFollowupStudentParam> dates = page.getDatas();
    if (dates != null && dates.size() > 0) {
      for (KeyFollowupStudentParam keyFollowupStudentParam : dates) {
        keyFollowupStudentParam
            .setLastClassDate(lastClassMap.get(keyFollowupStudentParam.getUserId()));
        keyFollowupStudentParam
            .setLastCoursewareDate(lastCoursewareDateMap.get(keyFollowupStudentParam.getUserId()));
        keyFollowupStudentParam
            .setFollowupCount(followupCountMap.get(keyFollowupStudentParam.getUserId()));
      }
    }

    return page;
  }

  /**
   * 
   * Title: 查询做完课件一周内没有预约<br>
   * Description: 查询做完课件一周内没有预约<br>
   * CreateDate: 2016年7月13日 下午6:40:34<br>
   * 
   * @category 查询做完课件一周内没有预约
   * @author seven.gz
   * @param paramMap
   * @param learningCoachId
   * @return
   * @throws Exception
   */
  public Page findNotSubscribeClass(Map<String, Object> paramMap, String learningCoachId)
      throws Exception {

    Calendar startTime = Calendar.getInstance();
    startTime.setTime(new Date());
    // 从现在向前减 7 天
    startTime.add(Calendar.DAY_OF_YEAR, -UserConstant.DAYS_OF_WEEK);

    // 查询出 课件完成在一周前的课程
    List<TellmemorePercent> tellmemorePercentList = tellmemorePercentEntityDao
        .findCompeleteTime(startTime.getTime());
    Map<String, Map<String, String>> tellmemorePercentMap = new HashMap<String, Map<String, String>>();
    if (tellmemorePercentList != null && tellmemorePercentList.size() > 0) {
      Map<String, String> userCourseMap = null;
      for (TellmemorePercent tellmemorePercent : tellmemorePercentList) {
        // 判断是否有这个人
        if (tellmemorePercentMap.containsKey(tellmemorePercent.getUserId())) {
          userCourseMap = tellmemorePercentMap.get(tellmemorePercent.getUserId());
          // 判断此人是否有这节课
          if (!userCourseMap.containsKey(tellmemorePercent.getCourseId())) {
            userCourseMap.put(tellmemorePercent.getCourseId(), tellmemorePercent.getKeyId());
          }
        } else {
          userCourseMap = new HashMap<String, String>();
          tellmemorePercentMap.put(tellmemorePercent.getUserId(), userCourseMap);
          userCourseMap.put(tellmemorePercent.getCourseId(), tellmemorePercent.getKeyId());
        }
      }
      // 如果没有数据则返回空
    } else {
      return new Page();
    }

    // 查询出预约过的人和课程
    List<SubscribeCourse> subscribeCourseList = adminSubscribeCourseDao
        .findUserAndCourse("course_type1");
    if (subscribeCourseList != null && subscribeCourseList.size() > 0) {
      Map<String, String> userCourseMap = null;
      for (SubscribeCourse subscribeCourse : subscribeCourseList) {
        // 此人预约过课
        if (tellmemorePercentMap.containsKey(subscribeCourse.getUserId())) {
          userCourseMap = tellmemorePercentMap.get(subscribeCourse.getUserId());
          // 这个人预约过这个课（预约过课就代表是在完成课件之后，所以不用判断时间）
          if (userCourseMap.containsKey(subscribeCourse.getCourseId())) {
            userCourseMap.remove(subscribeCourse.getCourseId());
          }
        }
      }
    }

    // 遍历map获得完成课件一周没有预约课的keyid
    List<String> tellmemorePercentKeyIdList = new ArrayList<String>();
    if (tellmemorePercentMap.values() != null && tellmemorePercentMap.values().size() > 0) {
      for (Map<String, String> userCourseMap : tellmemorePercentMap.values()) {
        if (userCourseMap.values() != null && userCourseMap.values().size() > 0) {
          for (String tellmemorePercentkeyId : userCourseMap.values()) {
            tellmemorePercentKeyIdList.add(tellmemorePercentkeyId);
          }
        }
      }
    }

    if (tellmemorePercentKeyIdList == null || tellmemorePercentKeyIdList.size() == 0) {
      return new Page();
    }
    return adminUserEntityDao.findNotSbuscribeClass(paramMap, learningCoachId,
        tellmemorePercentKeyIdList);
  }

}
