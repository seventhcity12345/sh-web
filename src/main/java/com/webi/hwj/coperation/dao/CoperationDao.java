package com.webi.hwj.coperation.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.coperation.entity.Coperation;

@Repository
public class CoperationDao extends BaseEntityDao<Coperation> {
  private static Logger logger = Logger.getLogger(CoperationDao.class);

  private static final String FIND_COPERATION_BY_REDEEMCODE = "SELECT user_phone"
      + " FROM `t_coperation`"
      + " WHERE redeem_code = :redeemCode AND is_used = 1";
  
  /**
   * Title: 根据兑换码查找数据<br>
   * Description: 根据兑换码查找数据<br>
   * CreateDate: 2016年11月8日 下午3:15:02<br>
   * @category 根据兑换码查找数据 
   * @author komi.zsy
   * @param keyId
   * @return
   * @throws Exception
   */
  public Coperation findOneByRedeemCode(String redeemCode) throws Exception {
    Coperation paramObj = new Coperation();
    paramObj.setRedeemCode(redeemCode);
    return super.findOne(FIND_COPERATION_BY_REDEEMCODE, paramObj);
  }
}