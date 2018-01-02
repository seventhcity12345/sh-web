package com.webi.hwj.statistics.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.statistics.entity.TellmemorePercent;
import com.webi.hwj.statistics.param.FindLastWorkTimeParam;

@Repository
public class TellmemorePercentEntityDao extends BaseEntityDao<TellmemorePercent> {
  private static Logger logger = Logger.getLogger(TellmemorePercentEntityDao.class);

  /**
   * 查询最后一次做课件时间
   */
  private static final String FIND_LAST_WORKING_TIME = " SELECT user_id,MAX(update_date) AS last_work_time FROM t_tellmemore_percent "
      + " WHERE user_id IN (:userIds) AND is_used = 1 GROUP BY user_id "
      + " HAVING last_work_time <= :lastWorkTime ";

  /**
   * 查询一次课件达标时间在一周前的记录
   */
  private static final String FIND_COMPELETE_TIME_ONE_WEEK_AGO = " SELECT key_id,user_id,course_id,tmm_percent,first_complete_time FROM t_tellmemore_percent "
      + " WHERE is_used = 1 AND first_complete_time < :firstCompleteTime ";

  /**
   * 更新第一次课件达标时间
   */
  private static final String UPDATE_FIRST_COMPELETE_TIME = " UPDATE t_tellmemore_percent SET first_complete_time = :firstCompleteTime WHERE tmm_percent >= :tmmPercent "
      + " AND first_complete_time IS NULL ";

  /**
   * 
   * Title: 查询一个月未做课件的学员和最后一次做课件的时间<br>
   * Description: 查询一个月未做课件的学员和最后一次做课件的时间<br>
   * CreateDate: 2016年7月12日 下午5:02:13<br>
   * 
   * @category 查询一个月未做课件的学员和最后一次做课件的时间
   * @author seven.gz
   * @param userIds
   * @param lastWorkTime
   * @return
   * @throws Exception
   */
  public List<FindLastWorkTimeParam> findLastWorkTime(List<String> userIds, Date lastWorkTime)
      throws Exception {
    FindLastWorkTimeParam findLastWorkTimeParam = new FindLastWorkTimeParam();
    findLastWorkTimeParam.setUserIds(userIds);
    findLastWorkTimeParam.setLastWorkTime(lastWorkTime);
    return super.findList(FIND_LAST_WORKING_TIME, findLastWorkTimeParam);
  }

  /**
   * 
   * Title: 查询第一次课件达标时间在一周前的记录<br>
   * Description: 查询第一次课件达标时间在一周前的记录<br>
   * CreateDate: 2016年7月13日 下午3:26:06<br>
   * 
   * @category 查询第一次课件达标时间在一周前的记录
   * @author seven.gz
   * @param firstCompleteTime
   * @return
   * @throws Exception
   */
  public List<TellmemorePercent> findCompeleteTime(Date firstCompleteTime) throws Exception {
    TellmemorePercent tellmemorePercent = new TellmemorePercent();
    tellmemorePercent.setFirstCompleteTime(firstCompleteTime);
    return super.findList(FIND_COMPELETE_TIME_ONE_WEEK_AGO, tellmemorePercent);
  }

  /**
   * 
   * Title: 更新第一次达标时间<br>
   * Description: 更新第一次达标时间<br>
   * CreateDate: 2016年7月13日 下午8:10:40<br>
   * 
   * @category 更新第一次达标时间
   * @author seven.gz
   * @param tmmPercent
   * @return
   * @throws Exception
   */
  public int updateFirstCompleteTime(Date firstCompleteTime, Integer tmmPercent) throws Exception {
    TellmemorePercent tellmemorePercent = new TellmemorePercent();
    tellmemorePercent.setFirstCompleteTime(firstCompleteTime);
    tellmemorePercent.setTmmPercent(tmmPercent);
    return super.update(UPDATE_FIRST_COMPELETE_TIME, tellmemorePercent);
  }
}