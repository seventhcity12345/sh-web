package com.webi.hwj.ordercourse.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * 
 * Title: 微信端课程dao<br>
 * Description: WeixinOrderCourseDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月3日 上午10:06:27
 * 
 * @author athrun.cw
 */
@Repository
public class WeixinOrderCourseDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(WeixinOrderCourseDao.class);

  public WeixinOrderCourseDao() {
    super.setTableName("t_order_course");
  }

}
