package com.webi.hwj.course.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * 
 * Title: 微信端课程dao<br>
 * Description: WeixinCourseDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月3日 上午10:06:27
 * 
 * @author athrun.cw
 */
@Repository
public class WeixinCourseDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(WeixinCourseDao.class);

  private static final String FIND_ORDERCOURSE_OPTION_BY_ORDERID = " SELECT key_id, order_id, "
      + " course_type, real_price, show_course_count, course_count, remain_course_count, is_gift "
      + " FROM t_order_course_option "
      + " WHERE order_id = :order_id "
      + " ORDER BY create_date ";

  public WeixinCourseDao() {
    super.setTableName("t_order_course");
  }

  /**
   * 
   * Title: 查找合同order_id下的子条数<br>
   * Description: findOrderCourseOptionByOrderId<br>
   * CreateDate: 2016年5月3日 上午10:05:20<br>
   * 
   * @category 查找合同order_id下的子条数
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findOrderCourseOptionByOrderId(Map<String, Object> paramMap)
      throws Exception {
    return super.findList(FIND_ORDERCOURSE_OPTION_BY_ORDERID, paramMap);
  }

}
