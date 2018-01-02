package com.webi.hwj.coursepackage.dao;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.coursepackage.entity.CoursePackage;
import com.webi.hwj.ordercourse.param.SaveOrderCourseParam;

@Repository
public class CoursePackageEntityDao extends BaseEntityDao<CoursePackage> {
  /**
   * 根据价格策略子表id查找课程包相关信息及其价格
   * 
   * @author komi.zsy
   */
  private final static String FIND_COURSE_PACKAGE_BY_COURSE_PACKAGE_PRICE_OPTION_ID =
      "SELECT tcp.key_id AS course_package_id,tcppo.course_package_show_price AS total_show_price,tcppo.course_package_real_price,"
          + "tcppo.course_price_version,tcp.package_name AS course_package_name,tcp.limit_show_time,"
          + "tcp.limit_show_time_unit,tcp.category_type,tcp.course_package_type"
          + " FROM t_course_package_price_option tcppo"
          + " LEFT JOIN t_course_package tcp"
          + " ON tcp.key_id = tcppo.course_package_id"
          + " WHERE tcppo.is_used = 1 AND tcp.is_used = 1"
          + " AND tcppo.key_id = :coursePackagePriceOptionId";

  /**
   * Title: 根据价格策略子表id查找课程包相关信息及其价格<br>
   * Description: 根据价格策略子表id查找课程包相关信息及其价格<br>
   * CreateDate: 2017年3月21日 上午10:34:24<br>
   * 
   * @category 根据价格策略子表id查找课程包相关信息及其价格
   * @author komi.zsy
   * @param coursePackagePriceOptionId
   *          价格策略子表id
   * @return
   * @throws Exception
   */
  public SaveOrderCourseParam findCoursePackageByCoursePackagePriceOptionId(
      String coursePackagePriceOptionId) throws Exception {
    SaveOrderCourseParam paramObj = new SaveOrderCourseParam();
    paramObj.setCoursePackagePriceOptionId(coursePackagePriceOptionId);
    return super.findOne(FIND_COURSE_PACKAGE_BY_COURSE_PACKAGE_PRICE_OPTION_ID, paramObj);
  }
}