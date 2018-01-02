package com.webi.hwj.subscribecourse.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeSupplement;
import com.webi.hwj.subscribecourse.param.FindSubscribeSupplementCountParam;

@Repository
public class SubscribeSupplementEntityDao extends BaseEntityDao<SubscribeSupplement> {
  private static Logger logger = Logger.getLogger(SubscribeSupplementEntityDao.class);

  private static final String FIND_COUNTS_BY_USERID =
      " SELECT tss.course_type,COUNT(tss.course_type) AS course_count "
          + " FROM t_subscribe_supplement tss LEFT JOIN t_subscribe_course tsc "
          + " ON tss.from_subscribe_course_id = tsc.key_id "
          + " WHERE tss.is_used <> 0 AND tss.user_id = :userId AND tsc.order_id = :orderId "
          + " GROUP BY tss.course_type ";

  private static final String FIND_COUNTS_BY_ORDERIDS =
      " SELECT COUNT(tsc.course_type) AS course_count,tsc.course_type,tss.user_id "
          + " FROM t_subscribe_supplement tss LEFT JOIN t_subscribe_course tsc "
          + " ON tss.from_subscribe_course_id = tsc.key_id "
          + " WHERE tsc.order_id IN (:orderIds) AND tss.is_used = 1 GROUP BY  tss.user_id,tsc.course_type ";

  /**
   * 查询学员个课程类型的补课数
   */
  private static final String FIND_COUNTS_BY_USER_ID =
      " SELECT course_type,COUNT(1) AS course_count "
          + " FROM t_subscribe_supplement WHERE user_id = :userId AND is_used = 1  GROUP BY course_type";

  /**
   * 
   * Title: 通过用户和合同查询补课数信息<br>
   * Description: 通过用户和合同查询补课数信息<br>
   * CreateDate: 2016年6月13日 下午2:48:09<br>
   * 
   * @category 通过用户和合同查询补课数信息
   * @author seven.gz
   * @param userId
   * @param orderId
   * @return
   * @throws Exception
   */
  public List<FindSubscribeSupplementCountParam> findCountsByUserId(String userId, String orderId)
      throws Exception {
    FindSubscribeSupplementCountParam findSubscribeSupplementCountParam =
        new FindSubscribeSupplementCountParam();
    findSubscribeSupplementCountParam.setUserId(userId);
    findSubscribeSupplementCountParam.setOrderId(orderId);
    return super.findList(FIND_COUNTS_BY_USERID, findSubscribeSupplementCountParam);
  }

  /**
   * 
   * Title: 合同查询补课数信息<br>
   * Description: 合同查询补课数信息<br>
   * CreateDate: 2016年6月13日 下午2:49:02<br>
   * 
   * @category 合同查询补课数信息
   * @author seven.gz
   * @param orderIds
   * @return
   * @throws Exception
   */
  public List<FindSubscribeSupplementCountParam> findCountsByOrderIds(List<String> orderIds)
      throws Exception {
    FindSubscribeSupplementCountParam findSubscribeSupplementCountParam =
        new FindSubscribeSupplementCountParam();
    findSubscribeSupplementCountParam.setOrderIds(orderIds);
    return super.findList(FIND_COUNTS_BY_ORDERIDS, findSubscribeSupplementCountParam);
  }

  /**
   * 
   * Title: 查询学员各个课程类型的补课数<br>
   * Description: findCountsByUserId<br>
   * CreateDate: 2017年7月21日 下午1:58:10<br>
   * 
   * @category 查询学员各个课程类型的补课数
   * @author seven.gz
   * @param orderIds
   * @return
   * @throws Exception
   */
  public List<FindSubscribeSupplementCountParam> findCountsByUserId(String userId)
      throws Exception {
    FindSubscribeSupplementCountParam findSubscribeSupplementCountParam =
        new FindSubscribeSupplementCountParam();
    findSubscribeSupplementCountParam.setUserId(userId);
    return super.findList(FIND_COUNTS_BY_USER_ID, findSubscribeSupplementCountParam);
  }
}