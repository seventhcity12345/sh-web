package com.webi.hwj.redeemcode.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * 
 * Title: 兑换码活动<br>
 * Description: RedeemDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年2月23日 下午2:54:47
 * 
 * @author athrun.cw
 */

@Repository
public class RedeemCodeDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(RedeemCodeDao.class);

  public RedeemCodeDao() {
    super.setTableName("t_redeem_code");
  }

  /**
   * 后端通过当前session的用户id去查询兑换表里的数据+需要加活动名称 modify 2016年6月30日14:12:40
   * 为了多个活动并存，去除活动名称的限制
   */
  private static final String FIND_REDEEMCODE_BY_USERID_AND_ACTIVITYNAME = " SELECT redeem_user_id AS user_id, "
      + " redeem_user_phone AS user_phone, redeem_user_real_name AS user_real_name, redeem_start_time, "
      + " redeem_end_time, activity_start_time, activity_end_time, activity_name "
      + " FROM t_redeem_code "
      + " WHERE is_used <> 0 "
      // + " AND activity_name = :activity_name "
      + " AND redeem_user_id = :redeem_user_id ";


  /**
   * 
   * Title: 查询兑换码信息：查询session里的兑换码信息，用于非学员首页 <br>
   * Description: findRedeemCode<br>
   * CreateDate: 2016年2月24日 上午11:02:43<br>
   * 
   * @category 查询兑换码信息：查询session里的兑换码信息，用于非学员首页
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findRedeemCodeByUserIdAndActivityName(Map<String, Object> paramMap)
      throws Exception {
    return super.findOne(FIND_REDEEMCODE_BY_USERID_AND_ACTIVITYNAME, paramMap);
  }


}
