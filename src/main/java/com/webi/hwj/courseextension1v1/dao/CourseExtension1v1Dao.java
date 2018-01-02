package com.webi.hwj.courseextension1v1.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.courseextension1v1.entity.CourseExtension1v1;
import com.webi.hwj.courseone2one.param.CourseOne2OneParam;

@Repository
public class CourseExtension1v1Dao extends BaseEntityDao<CourseExtension1v1> {
  private static Logger logger = Logger.getLogger(CourseExtension1v1Dao.class);

  /**
   * 查询course_type9列表：条件当前用户级别
   * 
   * @author komi.zsy
   */
  private static final String FIND_COURSETYPE9_LIST = "SELECT tce.key_id, tce.course_title,tco.course_title AS course_rsa_title,"
      + " tce.course_pic,'course_type9' AS course_type,tce.course_level "
      + " FROM `t_course_extension_1v1` tce"
      + " LEFT JOIN `t_course_one2one` tco"
      + " ON tce.key_id = tco.key_id AND tco.is_used <> 0 "
      + " WHERE tce.course_level = :courseLevel AND tce.is_used <> 0"
      + " ORDER BY tce.course_order";

  /**
   * Title: 查找course_type9列表<br>
   * Description: 查找course_type9列表<br>
   * CreateDate: 2016年9月6日 下午3:16:01<br>
   * 
   * @category 查找course_type9列表
   * @author komi.zsy
   * @param courseLevel
   *          需要查询的课程等级（学员当前等级）
   * @return
   * @throws Exception
   */
  public List<CourseOne2OneParam> findCourseType9List(String courseLevel) throws Exception {
    CourseOne2OneParam paramObj = new CourseOne2OneParam();
    paramObj.setCourseLevel(courseLevel);
    return super.findList(FIND_COURSETYPE9_LIST, paramObj);
  }

  /**
   * 
   * Title: 根据keyid查询<br>
   * Description: 根据keyid查询<br>
   * CreateDate: 2016年9月7日 下午2:34:29<br>
   * 
   * @category 根据keyid查询
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public CourseExtension1v1 findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId,
        "key_id,category_type,course_order,course_title,course_pic,course_courseware,course_level");
  }
}