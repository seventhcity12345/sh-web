package com.webi.hwj.statistics.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * 
 * Title: 马拉松数据统计每天变化量<br>
 * Description: 马拉松数据统计每天变化量<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月7日 上午11:50:19
 * 
 * @author komi.zsy
 */
@Repository
public class StatisticsMarathonDayDao extends BaseMysqlDao {
  public StatisticsMarathonDayDao() {
    super.setTableName("t_statistics_marathon_day");
  }

  /**
   * 统计马拉松数据每天变化量
   * 
   * @author komi.zsy
   */
  private final String UPDATE_MARATHON_BY_RSAACCOUNT_AND_DAY = "update t_statistics_marathon_day set "
      + " change_tmm_percent = :change_tmm_percent , "
      + " change_tmm_workingtime = :change_tmm_workingtime , "
      + " change_tmm_correct = :change_tmm_correct "
      + " where rsa_account = :rsa_account and DATE(create_date) = :create_date ";

  /**
   * Title: 统计马拉松数据每天变化量<br>
   * Description: 统计马拉松数据每天变化量<br>
   * CreateDate: 2016年7月26日 下午5:18:56<br>
   * 
   * @category 统计马拉松数据每天变化量
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int updateMarathonByRsaAccountAndDay(Map<String, Object> paramMap)
      throws Exception {
    return super.update(UPDATE_MARATHON_BY_RSAACCOUNT_AND_DAY, paramMap);
  }
}
