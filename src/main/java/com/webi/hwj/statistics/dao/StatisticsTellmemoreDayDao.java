package com.webi.hwj.statistics.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.statistics.entity.StatisticsTellmemoreDay;
import com.webi.hwj.statistics.param.CoursewareLearningTimeParam;
import com.webi.hwj.statistics.param.FindStatisticsTellmemoreParam;

@Repository
public class StatisticsTellmemoreDayDao extends BaseEntityDao<StatisticsTellmemoreDay> {
  private static Logger logger = Logger.getLogger(StatisticsTellmemoreDayDao.class);

  /*
   * 按日期查找所有数据
   */
  private static final String SELECT_TELLMEMORE_DAY_USER_INFO = "SELECT user_id,"
      + " total_tmm_percent,total_tmm_correct,total_tmm_workingtime,"
      + " change_tmm_percent,change_tmm_correct,change_tmm_workingtime "
      + " FROM t_statistics_tellmemore_day "
      + " WHERE DATE(create_date) = :createDate AND is_used <> 0";

  /**
   * 查询用户一定时间段内的学习时间
   */
  private static final String SELECT_TELLMEMORE_DAY_USER_INFO_BY_TIME =
      " SELECT total_tmm_workingtime,change_tmm_workingtime,create_date "
          + " FROM t_statistics_tellmemore_day "
          + " WHERE create_date >= :startTime AND create_date <= :endTime AND "
          + " user_id = :userId AND is_used <> 0 ORDER BY create_date DESC ";

  /*
   * 按日期查找所有数据
   */
  private static final String FIND_INFO_BY_DAY_AND_USERS = "SELECT user_id,"
      + " total_tmm_percent,total_tmm_correct,total_tmm_workingtime,"
      + " change_tmm_percent,change_tmm_correct,change_tmm_workingtime,create_date "
      + " FROM t_statistics_tellmemore_day "
      + " WHERE is_used <> 0 AND user_id IN(:userIds) ";

  /**
   * 查询最近7天每天学员平均学习课件时长
   * 
   * @author felix.yl
   */
  private static final String FIND_AVG_COURSEWARE_LEARNING_TIME = " SELECT"
      + " create_date AS 'learningDate',"
      + " TRUNCATE((SUM(SUBSTRING_INDEX(change_tmm_workingtime,':',1)) *60 *60 +"
      + " SUM(SUBSTRING_INDEX(SUBSTRING_INDEX(change_tmm_workingtime,':',2),':',-1)) *60 +"
      + " SUM(SUBSTRING_INDEX(change_tmm_workingtime,':',-1))) /COUNT(1)/60 +25  ,0)"
      + " AS 'learningTime'"
      + " FROM t_statistics_tellmemore_day"
      + " WHERE create_date BETWEEN :startDate AND :endDate"
      + " AND is_used=1"
      + " GROUP BY Date(create_date)";

  /**
   * 查询某个学员最近7天每天学员平均学习课件时长
   * 
   * @author felix.yl
   */
  private static final String FIND_MY_COURSEWARE_LEARNING_TIME = " SELECT"
      + " create_date AS 'learningDate',"
      + " ((SUBSTRING_INDEX(change_tmm_workingtime,':',1) *60 *60 +"
      + " SUBSTRING_INDEX(SUBSTRING_INDEX(change_tmm_workingtime,':',2),':',-1) *60 +"
      + " SUBSTRING_INDEX(change_tmm_workingtime,':',-1))/60) AS 'learningTime'"
      + " FROM t_statistics_tellmemore_day"
      + " WHERE create_date BETWEEN :startDate AND :endDate"
      + " AND user_id= :userId"
      + " AND is_used=1"
      + " GROUP BY Date(create_date)";

  /**
   * Title: 根据日期查找所有数据<br>
   * Description: 根据日期查找所有数据<br>
   * CreateDate: 2016年4月15日 下午1:31:56<br>
   * 
   * @category 根据日期查找所有数据
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<StatisticsTellmemoreDay> findInfoByCreatDay(StatisticsTellmemoreDay paramObj)
      throws Exception {
    return super.findList(SELECT_TELLMEMORE_DAY_USER_INFO, paramObj);
  }

  /**
   * 
   * Title: 查询用户一定时间段内的学习时间<br>
   * Description: 查询用户一定时间段内的学习时间<br>
   * CreateDate: 2016年6月1日 下午10:01:36<br>
   * 
   * @category 查询用户一定时间段内的学习时间
   * @author seven.gz
   * @param userId
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<FindStatisticsTellmemoreParam> findInfoByTime(String userId, Date startTime,
      Date endTime) throws Exception {
    FindStatisticsTellmemoreParam findStatisticsTellmemoreParam =
        new FindStatisticsTellmemoreParam();
    findStatisticsTellmemoreParam.setUserId(userId);
    findStatisticsTellmemoreParam.setStartTime(startTime);
    findStatisticsTellmemoreParam.setEndTime(endTime);
    return super.findList(SELECT_TELLMEMORE_DAY_USER_INFO_BY_TIME, findStatisticsTellmemoreParam);
  }

  /**
   * 
   * Title: 根据日期、学员查找所有数据<br>
   * Description: 根据日期、学员查找所有数据<br>
   * CreateDate: 2016年11月15日 下午3:13:22<br>
   * 
   * @category 根据日期、学员查找所有数据
   * @author seven.gz
   * @param createDate
   * @param userIds
   * @return
   * @throws Exception
   */
  public List<FindStatisticsTellmemoreParam> findInfoByCreatDayAndUserIds(List<String> userIds)
      throws Exception {
    FindStatisticsTellmemoreParam paramObj = new FindStatisticsTellmemoreParam();
    paramObj.setUserIds(userIds);
    return super.findList(FIND_INFO_BY_DAY_AND_USERS, paramObj);
  }

  /**
   * 
   * Title: 查询最近7天每天学员平均学习课件时长<br>
   * Description: 查询最近7天每天学员平均学习课件时长<br>
   * CreateDate: 2017年8月10日 上午10:09:13<br>
   * 
   * @category 查询最近7天每天学员平均学习课件时长
   * @author felix.yl
   * @param startDate
   * @param endDate
   * @return
   * @throws Exception
   */
  public List<CoursewareLearningTimeParam> findAvgCoursewareLearningTimeInfo(Date startDate,
      Date endDate)
          throws Exception {
    CoursewareLearningTimeParam paramObj = new CoursewareLearningTimeParam();
    paramObj.setStartDate(startDate);
    paramObj.setEndDate(endDate);
    return super.findList(FIND_AVG_COURSEWARE_LEARNING_TIME, paramObj);
  }

  /**
   * 
   * Title: 查询某个学员最近7天每天学员平均学习课件时长<br>
   * Description: 查询某个学员最近7天每天学员平均学习课件时长<br>
   * CreateDate: 2017年8月10日 上午10:09:13<br>
   * 
   * @category 查询某个学员最近7天每天学员平均学习课件时长
   * @author felix.yl
   * @param startDate
   * @param endDate
   * @return
   * @throws Exception
   */
  public List<CoursewareLearningTimeParam> findMyCoursewareLearningTimeInfo(String userId,
      Date startDate, Date endDate) throws Exception {
    CoursewareLearningTimeParam paramObj = new CoursewareLearningTimeParam();
    paramObj.setStartDate(startDate);
    paramObj.setEndDate(endDate);
    paramObj.setUserId(userId);
    return super.findList(FIND_MY_COURSEWARE_LEARNING_TIME, paramObj);
  }

  /**
   * 
   * Title: 查询学员学习时长<br>
   * Description: findNoShowCoursePage<br>
   * CreateDate: 2017年9月13日 上午11:34:02<br>
   * 
   * @category 查询学员学习时长
   * @author seven.gz
   * @param paramMap
   * @param userId
   * @return
   * @throws Exception
   */
  public Page findPageRsaLearningProgress(Integer page, Integer rows, String userId)
      throws Exception {
    StatisticsTellmemoreDay paramObj = new StatisticsTellmemoreDay();
    paramObj.setUserId(userId);
    return super.findPage(paramObj, "total_tmm_workingtime,change_tmm_workingtime,create_date",
        page, rows);
  }
}