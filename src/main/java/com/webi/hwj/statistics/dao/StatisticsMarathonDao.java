package com.webi.hwj.statistics.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * 
 * Title: 马拉松数据统计从RSA拉取原始数据，和每小时变化量<br>
 * Description: 马拉松数据统计从RSA拉取原始数据，和每小时变化量<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月7日 上午11:03:19
 * 
 * @author komi.zsy
 */
@Repository
public class StatisticsMarathonDao extends BaseMysqlDao {
  public StatisticsMarathonDao() {
    super.setTableName("t_statistics_marathon");
  }

  private static final String SELECT_MARATHON_BY_RSAACCOUNT_AND_TIME = "SELECT total_tmm_percent,total_tmm_correct,"
      + "total_tmm_workingtime,change_tmm_percent,change_tmm_correct,change_tmm_workingtime,create_date "
      + " FROM t_statistics_marathon "
      + " WHERE rsa_account = :rsa_account AND is_used = 1 "
      + " ORDER BY create_date DESC";

  private static final String SELECT_MARATHON_BY_RSAACCOUNT_AND_DAY = "SELECT change_tmm_percent,change_tmm_correct,"
      + "change_tmm_workingtime"
      + " FROM t_statistics_marathon "
      + " WHERE  DATE(create_date) = :create_day AND rsa_account = :rsa_account AND is_used = 1 "
      + " GROUP BY create_date";

  /**
   * Title: 根据用户时间和账号查询三维变化属性<br>
   * Description: findMarathonByRsaAccountAndTime<br>
   * CreateDate: 2016年3月29日 下午6:40:51<br>
   * 
   * @category 根据用户时间和账号查询三维变化属性
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findMarathonByRsaAccountAndTime(Map<String, Object> paramMap)
      throws Exception {
    return super.findOne(SELECT_MARATHON_BY_RSAACCOUNT_AND_TIME, paramMap);
  }

  /**
   * Title: 根据rsa账号和日期（20xx-xx-xx）查询所有数据<br>
   * Description: findMarathonByRsaAccountAndDay<br>
   * CreateDate: 2016年4月7日 上午11:11:05<br>
   * 
   * @category findMarathonByRsaAccountAndDay
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findMarathonByRsaAccountAndDay(Map<String, Object> paramMap)
      throws Exception {
    return super.findList(SELECT_MARATHON_BY_RSAACCOUNT_AND_DAY, paramMap);
  }
}
