package com.webi.hwj.courseprice.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.coursepackageprice.entity.CoursePackagePrice;
import com.webi.hwj.courseprice.entity.CoursePrice;
import com.webi.hwj.courseprice.param.CoursePriceParam;

@Repository
public class CoursePriceDao extends BaseEntityDao<CoursePrice> {
  private static Logger logger = Logger.getLogger(CoursePriceDao.class);

  private final static String FIND_COURSE_UNIT_AND_PRICE_BY_VERSION = "SELECT course_type,course_price_unit_price,course_price_unit_type"
      + " FROM `t_course_price`"
      + " WHERE course_price_version = :coursePriceVersion AND course_type = :courseType"
      + " AND is_used <> 0";
  
  
  /**
   * 查找当前最大版本号
   * @author komi.zsy
   */
  private final static String FIND_MAX_VERSION = "SELECT MAX(course_price_version) FROM t_course_price";
  
  /**
   * Title: 查找当前最大版本号<br>
   * Description:  查找当前最大版本号<br>
   * CreateDate: 2017年9月7日 下午1:53:05<br>
   * @category  查找当前最大版本号 
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public int findMaxNum()throws Exception{
    return super.findCount(FIND_MAX_VERSION, new CoursePrice());
  }

  /**
   * Title: 根据课程类型和价格版本号查找课程类型类型和课程单价<br>
   * Description: 根据课程类型和价格版本号查找课程类型类型和课程单价<br>
   * CreateDate: 2016年8月30日 下午3:12:16<br>
   * 
   * @category 根据课程类型和价格版本号查找课程类型类型和课程单价
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<CoursePriceParam> findCourseUnitAndPriceListByVersion(CoursePriceParam paramObj)
      throws Exception {
    return super.findList(FIND_COURSE_UNIT_AND_PRICE_BY_VERSION, paramObj);
  }
}