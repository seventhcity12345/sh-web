/** 
 * File: PayLogDao.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.alipay.dao<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年12月10日 下午3:39:18
 * @author yangmh
 */
package com.webi.hwj.alipay.dao;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * Title: PayLogDao<br>
 * Description: PayLogDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月10日 下午3:39:18
 * 
 * @author yangmh
 */
@Repository
public class PayLogDao extends BaseMysqlDao {
  public PayLogDao() {
    super.setTableName("t_pay_log");
  }
}
