package com.webi.hwj.coursepackagepriceoption.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.coursepackage.param.CoursePackageAndPriceParam;
import com.webi.hwj.coursepackagepriceoption.entity.CoursePackagePriceOption;

@Repository
public class CoursePackagePriceOptionDao extends BaseEntityDao<CoursePackagePriceOption> {
  private static Logger logger = Logger.getLogger(CoursePackagePriceOptionDao.class);
  
  /**
   * 逻辑删除价格策略子表数据
   * @author komi.zsy
   */
  private final static String DELETE_BY_COURSE_PACKAGE_PRICE_ID ="UPDATE t_course_package_price_option"
      + " SET is_used = 0"
      + " WHERE course_package_price_id = :coursePackagePriceId AND is_used = 1";

  /**
   * 根据价格策略id查找课程包数据
   * 
   * @author komi.zsy
   */
  private final static String FIND_COURSE_PACKAGE_LIST_BY_PRICEID = "SELECT tcp.key_id,tcp.package_name,tcp.category_type,tcp.limit_show_time,"
      + "tcp.limit_show_time_unit,tcp.course_package_type,"
      + "tcppo.key_id as course_package_price_option_id,tcppo.course_package_show_price,tcppo.course_package_real_price,tcppo.course_price_version"
      + " FROM `t_course_package_price_option` tcppo"
      + " LEFT JOIN `t_course_package` tcp"
      + " ON tcppo.course_package_id = tcp.key_id"
      + " WHERE tcppo.course_package_price_id = :coursePackagePriceId"
      + " AND tcp.is_used <> 0 AND tcppo.is_used <> 0 "
      + " ORDER BY key_id";

  /**
   * 根据价格策略id和课程包id查找课程包数据
   * 
   * @author komi.zsy
   */
  private final static String FIND_COURSE_PACKAGE_BY_PRICEID_AND_COURSEPACKAGEID = "SELECT tcp.key_id,tcp.package_name,"
      + "tcp.category_type,tcp.limit_show_time,tcp.limit_show_time_unit,tcp.course_package_type,"
      + "tcppo.key_id as course_package_price_option_id,"
      + "tcppo.course_package_show_price,tcppo.course_package_real_price,tcppo.course_price_version"
      + " FROM `t_course_package_price_option` tcppo"
      + " LEFT JOIN `t_course_package` tcp"
      + " ON tcppo.course_package_id = tcp.key_id"
      + " WHERE tcppo.course_package_price_id = :coursePackagePriceId"
      + " AND tcp.key_id = :coursePackageId "
      + " AND tcp.is_used <> 0 ";
  
  
  /**
   * Title: 逻辑删除价格策略子表数据<br>
   * Description: 逻辑删除价格策略子表数据<br>
   * CreateDate: 2017年8月15日 上午11:24:17<br>
   * @category 逻辑删除价格策略子表数据 
   * @author komi.zsy
   * @param coursePackagePriceId 价格策略主表id
   * @return
   * @throws Exception
   */
  public int deleteByCoursePackagePriceId(String coursePackagePriceId) throws Exception {
    CoursePackagePriceOption paramObj = new CoursePackagePriceOption();
    paramObj.setCoursePackagePriceId(coursePackagePriceId);
    return super.update(DELETE_BY_COURSE_PACKAGE_PRICE_ID, paramObj);
  }
  
  
  /**
   * Title: 查找价格策略子表数据<br>
   * Description: 查找价格策略子表数据<br>
   * CreateDate: 2016年8月29日 下午5:55:45<br>
   * 
   * @category 查找价格策略子表数据
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<CoursePackageAndPriceParam> findList(String coursePackagePriceId) throws Exception {
    CoursePackageAndPriceParam paramObj = new CoursePackageAndPriceParam();
    paramObj.setCoursePackagePriceId(coursePackagePriceId);
    return super.findList(FIND_COURSE_PACKAGE_LIST_BY_PRICEID, paramObj);
  }

  /**
   * Title: 根据价格策略id和课程包id查找课程包数据<br>
   * Description: 根据价格策略id和课程包id查找课程包数据<br>
   * CreateDate: 2016年8月31日 下午3:31:18<br>
   * 
   * @category 根据价格策略id和课程包id查找课程包数据
   * @author komi.zsy
   * @param coursePackagePriceId
   * @return
   * @throws Exception
   */
  public CoursePackageAndPriceParam findOneByCoursePackageIdAndCoursePackagePriceId(
      String coursePackagePriceId, String coursePackageId) throws Exception {
    CoursePackageAndPriceParam paramObj = new CoursePackageAndPriceParam();
    paramObj.setCoursePackagePriceId(coursePackagePriceId);
    paramObj.setCoursePackageId(coursePackageId);
    return super.findOne(FIND_COURSE_PACKAGE_BY_PRICEID_AND_COURSEPACKAGEID, paramObj);
  }
}