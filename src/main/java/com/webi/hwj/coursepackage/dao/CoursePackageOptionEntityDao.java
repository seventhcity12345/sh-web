package com.webi.hwj.coursepackage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.coursepackage.entity.CoursePackageOption;
import com.webi.hwj.coursepackage.param.CoursePackageOptionAndPriceParam;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.param.SaveOrderCourseParam;

@Repository
public class CoursePackageOptionEntityDao extends BaseEntityDao<CoursePackageOption> {
  /**
   * 查找课程包子表以及相对于的小维度价格
   * 
   * @author komi.zsy + seven.gz
   */
  private final static String FIND_COURSE_PACKAGE_OPTION_AND_PRICE_LIST = "SELECT tcpo.course_type,tcpo.course_count,"
      + "tcpo.course_unit_type,tcp.course_price_unit_price"
      + " FROM `t_course_package_option` tcpo"
      + " LEFT JOIN `t_course_price` tcp"
      + " ON tcpo.course_type = tcp.course_type AND tcpo.course_unit_type = tcp.course_price_unit_type"
      + " WHERE tcpo.course_package_id = :coursePackageId"
      + " AND tcp.course_price_version = :coursePriceVersion"
      + " AND tcpo.is_used <> 0  AND tcp.is_used <> 0";

  /**
   * 查找课程包主表和课程包子表以及相对于的小维度价格
   * 
   * @author komi.zsy
   */
  private final static String FIND_COURSE_PACKAGE_LIST = "SELECT tcpo.course_type,tcpo.course_count,tcpo.course_unit_type,"
      + "tcp.course_price_unit_price"
      + " FROM `t_course_package_option` tcpo"
      + " LEFT JOIN `t_course_price` tcp"
      + " ON tcpo.course_type = tcp.course_type AND tcpo.course_unit_type = tcp.course_price_unit_type"
      + " WHERE tcpo.course_package_id = :coursePackageId"
      + " AND tcp.course_price_version = :coursePriceVersion"
      + " AND tcpo.is_used <> 0  AND tcp.is_used <> 0";

  /**
   * Title: 查找课程包子表以及相对于的小维度价格<br>
   * Description: 查找课程包子表以及相对于的小维度价格<br>
   * CreateDate: 2016年8月30日 上午11:17:33<br>
   * 
   * @category 查找课程包子表以及相对于的小维度价格
   * @author komi.zsy
   * @param coursePackageId
   * @return
   * @throws Exception
   */
  public List<CoursePackageOptionAndPriceParam> findOptionAndPirceList(String coursePackageId,
      Integer coursePriceVersion) throws Exception {
    CoursePackageOptionAndPriceParam paramObj = new CoursePackageOptionAndPriceParam();
    paramObj.setCoursePackageId(coursePackageId);
    paramObj.setCoursePriceVersion(coursePriceVersion);
    return super.findList(FIND_COURSE_PACKAGE_OPTION_AND_PRICE_LIST, paramObj);
  }

  /**
   * Title: 查找课程包主表和课程包子表以及相对于的小维度价格<br>
   * Description: 查找课程包主表和课程包子表以及相对于的小维度价格<br>
   * CreateDate: 2016年8月31日 下午3:56:02<br>
   * 
   * @category 查找课程包主表和课程包子表以及相对于的小维度价格
   * @author komi.zsy
   * @param coursePackageId
   * @param coursePriceVersion
   * @return
   * @throws Exception
   */
  public List<CoursePackageOptionAndPriceParam> findCoursePackageList(String coursePackageId,
      Integer coursePriceVersion) throws Exception {
    CoursePackageOptionAndPriceParam paramObj = new CoursePackageOptionAndPriceParam();
    paramObj.setCoursePackageId(coursePackageId);
    paramObj.setCoursePriceVersion(coursePriceVersion);
    return super.findList(FIND_COURSE_PACKAGE_LIST, paramObj);
  }
}