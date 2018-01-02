package com.webi.hwj.redeemcode.dao;

import org.springframework.stereotype.Repository;
import com.mingyisoft.javabase.base.dao.BaseEntityDao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import com.webi.hwj.redeemcode.entity.RedeemCode;

@Repository
public class RedeemCodeEntityDao extends BaseEntityDao<RedeemCode>{ 
	private static Logger logger = Logger.getLogger(RedeemCodeEntityDao.class);
	
	  /**
	   * 根据redeem code查询兑换码信息 是否存在
	   */
	  private static final String FIND_REDEEMCODE_BY_CODE = " SELECT key_id, redeem_code, course_package_id,course_package_price_id, "
	      + " redeem_user_id, redeem_user_phone, redeem_user_real_name, redeem_start_time, redeem_end_time,cpid, "
	      + " activity_start_time, activity_end_time, activity_name "
	      + " FROM t_redeem_code "
	      + " WHERE is_used <> 0 "
	      + " AND redeem_code = :redeemCode ";
	  
	  
	  
	  /**
	   * Title: 根据redeem code查询兑换码信息 是否存在<br>
	   * Description: findRedeemCodeByCode<br>
	   * CreateDate: 2016年2月24日 下午2:41:00<br>
	   * @category 根据redeem code查询兑换码信息 是否存在
	   * @author athrun.cw
	   * @param paramMap
	   * @return
	   * @throws Exception
	   */
	  public RedeemCode findRedeemCodeByCode(String redeemCode) throws Exception {
	    RedeemCode paramObj = new RedeemCode();
	    paramObj.setRedeemCode(redeemCode);
	    return super.findOne(FIND_REDEEMCODE_BY_CODE, paramObj);
	  }
	
}