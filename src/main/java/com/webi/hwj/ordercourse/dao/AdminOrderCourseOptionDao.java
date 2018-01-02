package com.webi.hwj.ordercourse.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;

@Repository
public class AdminOrderCourseOptionDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(AdminOrderCourseOptionDao.class);

  public AdminOrderCourseOptionDao() {
    super.setTableName("t_order_course_option");
  }

  /**
   * 查找该用户拥有的所有课程类型
   * 
   * @author komi.zsy
   */
  private static final String FIND_COURSETYPES_BY_USERID = " SELECT"
      + " toco.course_type,toco.show_course_count,toco.course_unit_type,toc.start_order_time,"
      + " toc.key_id AS order_id,toco.key_id AS order_option_id,order_status "
      + " FROM `t_order_course_option` toco"
      + " LEFT JOIN `t_order_course` toc ON toco.order_id = toc.key_id"
      + " WHERE toc.user_id = :userId"
      + " AND toco.is_used <> 0 AND toc.is_used <> 0 AND toc.order_status IN( "
      + OrderCourseConstant.ORDER_HAS_BEEN_PAID + "," + OrderCourseConstant.ORDER_IS_EXPIRED + ")";
  
  
  /**
   * 续约时删除之前的临时数据，这些临时数据统计报表里也不需要用到所以加了个临时字段is_delete
   * 看之后有时间再重新设计优化，先保证修复功能时其他功能不会出错
   * @author komi.zsy
   */
  private static final String DELETE_ORDER_COURSE_OPTION_BY_RENEW = "update t_order_course_option"
      + " set is_used = 0,is_delete = 0,update_user_id = :updateUserId"
      + " where key_id in (:keyIds) ";
  
  /**
   * Title: 续约时删除之前的临时数据<br>
   * Description: 续约时删除之前的临时数据<br>
   * CreateDate: 2017年6月12日 上午11:40:38<br>
   * @category 续约时删除之前的临时数据 
   * @author komi.zsy
   * @param updateUserId 更新人id
   * @param keyIds 删除的数据的keyid
   * @return
   * @throws Exception
   */
  public int deleteOrderCourseOptionByRenew(String updateUserId,List<String> keyIds) throws Exception{
    Map<String, Object> paramMap = new  HashMap<>();
    paramMap.put("updateUserId", updateUserId);
    paramMap.put("keyIds", keyIds);
    return super.update(DELETE_ORDER_COURSE_OPTION_BY_RENEW, paramMap);
  }

  /**
   * Title: 查找该用户拥有的所有课程类型<br>
   * Description: 查找该用户拥有的所有课程类型<br>
   * CreateDate: 2016年7月6日 下午5:27:46<br>
   * 
   * @category 查找该用户拥有的所有课程类型
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findCourseTypesByUserId(String userId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("userId", userId);
    return findList(FIND_COURSETYPES_BY_USERID, paramMap);
  }
}