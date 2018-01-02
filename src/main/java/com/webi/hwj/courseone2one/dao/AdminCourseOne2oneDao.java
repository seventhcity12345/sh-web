package com.webi.hwj.courseone2one.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.courseone2one.entity.CourseOne2One;
import com.webi.hwj.courseone2one.param.CourseOne2OneParam;

@Repository
public class AdminCourseOne2oneDao extends BaseEntityDao<CourseOne2One> {

  /**
   * 查询course_type1列表：条件当前用户级别
   * 
   * @author komi.zsy
   */
  private static final String FIND_COURSETYPE1_LIST = " SELECT key_id, course_title,course_title AS course_rsa_title, "
      + "course_pic,'course_type1' AS course_type ,course_level"
      + " FROM t_course_one2one "
      + " WHERE course_level = :courseLevel "
      + " AND is_used <> 0 "
      + " ORDER BY course_order";

  /**
   * Title: 查找course_type1列表<br>
   * Description: 查找course_type1列表<br>
   * CreateDate: 2016年9月6日 下午3:16:01<br>
   * 
   * @category 查找course_type1列表
   * @author komi.zsy
   * @param courseLevel
   *          需要查询的课程等级（学员当前等级）
   * @return
   * @throws Exception
   */
  public List<CourseOne2OneParam> findCourseType1List(String courseLevel) throws Exception {
    CourseOne2OneParam paramObj = new CourseOne2OneParam();
    paramObj.setCourseLevel(courseLevel);
    return super.findList(FIND_COURSETYPE1_LIST, paramObj);
  }

  /**
   * Title: 根据课程类型查询课程<br>
   * Description: findListCourseByCourseType<br>
   * CreateDate: 2016年12月22日 下午6:22:15<br>
   * 
   * @category 根据课程类型查询课程
   * @author seven.gz
   * @param courseType
   * @return
   * @throws Exception
   */
  public List<CourseOne2One> findListCourseByCourseType(String courseType) throws Exception {
    CourseOne2One paramObj = new CourseOne2One();
    paramObj.setCourseType(courseType);
    return super.findList(paramObj, "key_id,course_title");
  }

  /**
   * 
   * Title: 根据keyid查询<br>
   * Description: findOneByKeyId<br>
   * CreateDate: 2016年12月23日 上午10:51:57<br>
   * 
   * @category 根据keyid查询
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public CourseOne2One findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId,
        "key_id,category_type,course_type,course_order,course_title,course_courseware,course_level,course_pic");
  }
}