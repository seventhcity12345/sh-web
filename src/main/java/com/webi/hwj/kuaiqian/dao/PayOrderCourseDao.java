/** 
 * File: PayOrderCourseDao.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian.dao<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月18日 下午3:40:19
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * Title: PayOrderCourseDao<br>
 * Description: PayOrderCourseDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月18日 下午3:40:19
 * 
 * @author athrun.cw
 */
@Repository
public class PayOrderCourseDao extends BaseMysqlDao {
  public PayOrderCourseDao() {
    super.setTableName("t_kuaiqian_pay_log");
  }

  private static Logger logger = Logger.getLogger(PayOrderCourseDao.class);

  /**
   * 支付成功后，修改订单状态为已支付
   */
  private static final String UPDATE_KUAIQIAN_LOG_BY_KEYID = " update t_kuaiqian_pay_log "
      + " SET trade_status = :trade_status, pay_log_desc = :pay_log_desc, "
      + " deal_id = :deal_id "
      + " WHERE is_used <> 0 "
      + " AND key_id = :key_id";

  /**
   * 生成30位的特殊key_id
   */
  public static final String INSERT_KUAIQIAN_LOG = " INSERT INTO t_kuaiqian_pay_log "
      + " ( "
      + " key_id, trade_status, order_id, user_id, money"
      + " ) "
      + " VALUES("
      + " :key_id, :trade_status, :order_id, :user_id, :money "
      + ")";

  /**
   * 
   * Title: 从orderId，更新支付日志信息 Description: updateKuaiqianLogByOrderId<br>
   * CreateDate: 2015年8月18日 下午4:38:06<br>
   * 
   * @category updateKuaiqianLogByOrderId
   * @author athrun.cw
   * @param logParamMap
   * @return
   * @throws Exception
   */
  public int updateKuaiqianLogByKeyId(Map<String, Object> logParamMap) throws Exception {
    return super.update(UPDATE_KUAIQIAN_LOG_BY_KEYID, logParamMap);
  }

  public int insertKuaiqianLogByParamMap(Map<String, Object> paramMap) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    return namedParameterJdbcTemplate.update(INSERT_KUAIQIAN_LOG, new MapSqlParameterSource(
        paramMap), keyHolder);
  }

}
