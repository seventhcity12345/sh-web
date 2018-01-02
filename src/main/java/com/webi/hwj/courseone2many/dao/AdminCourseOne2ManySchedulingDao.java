package com.webi.hwj.courseone2many.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.courseone2many.entity.CourseOne2Many;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.courseone2many.param.CourseOne2ManySchedulingParam;

@Repository
public class AdminCourseOne2ManySchedulingDao extends BaseEntityDao<CourseOne2ManyScheduling> {
  private static Logger logger = Logger.getLogger(AdminCourseOne2ManySchedulingDao.class);

  private static final String UPDATE_SCHEDULING_BY_COURSE = " UPDATE t_course_one2many_scheduling SET course_title = :courseTitle, "
      + " course_level=:courseLevel,course_courseware = :courseCourseware,course_pic= :coursePic, "
      + " category_type=:categoryType,course_desc=:courseDesc WHERE course_id = :keyId ";

  private static final String UPDATE_SCHEDULING_DOCUMENT_ID_BY_COURSE = " UPDATE t_course_one2many_scheduling SET document_id= :documentId "
      + " WHERE course_id = :keyId ";

  /**
   * 根据教师id修改教师相关信息
   * 
   * @author komi.zsy
   */
  private final static String UPDATE_TEACHER_INFO_BY_TEACHERID = "UPDATE t_course_one2many_scheduling "
      + " SET teacher_name =:teacherName,teacher_photo = :teacherPhoto "
      + " WHERE teacher_id =:teacherId ";

  /**
   * 查询排课列表信息
   * 
   * @author seven
   */
  private final static String FIND_SCHEDULING_LIST = " SELECT tcos.key_id, tcos.teacher_time_id, tcos.start_time,tcos.end_time,    "
      + "   tcos.course_type,tcos.teacher_name,tcos.course_level,tcos.course_title,   "
      + "   tcos.create_date,ttt.is_subscribe, ttt.is_confirm,tcos.teacher_url,tcos.student_url,tt.`third_from`  "
      + " FROM t_course_one2many_scheduling tcos "
      + "   LEFT JOIN t_teacher_time ttt  ON tcos.teacher_time_id = ttt.key_id    "
      + " LEFT JOIN `t_teacher` tt ON tt.`key_id` = ttt.`teacher_id`    "
      + " WHERE tcos.is_used = 1 ORDER BY tcos.start_time DESC                        ";

  /**
   * 查询排课列表信息
   * 
   * @author seven
   */
  private final static String FIND_SCHEDULING_BY_KEY_ID_WITH_NO_ISUESD = " SELECT start_time,end_time,teacher_id FROM t_course_one2many_scheduling WHERE key_id = :keyId ";

  /**
   * 
   * Title: 根据课程id查询排课信息<br>
   * Description: 根据课程id查询排课信息<br>
   * CreateDate: 2016年4月13日 上午11:24:20<br>
   * 
   * @category 根据课程id查询排课信息
   * @author seven.gz
   * @param courseId
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManyScheduling> findSchedulingByCourseId(String courseId) throws Exception {
    CourseOne2ManyScheduling paramObj = new CourseOne2ManyScheduling();
    paramObj.setCourseId(courseId);
    return super.findList(paramObj, " key_id,course_id,end_time ");
  }

  /**
   * 
   * Title: 根据课程跟新排课<br>
   * Description: 根据课程跟新排课<br>
   * CreateDate: 2016年4月13日 下午9:39:17<br>
   * 
   * @category 根据课程跟新排课
   * @author seven.gz
   * @param course
   * @return
   * @throws Exception
   */
  public int updatByCourse(CourseOne2Many course) throws Exception {
    return super.update(UPDATE_SCHEDULING_BY_COURSE, course);
  }

  /**
   * Title: 批量更新大课排课表的documentId<br>
   * Description: updatByCourseWithDocumentId<br>
   * CreateDate: 2016年5月25日 下午2:06:47<br>
   * 
   * @category 批量更新大课排课表的documentId
   * @author seven.gz
   * @param keyId
   * @param documentId
   * @return
   * @throws Exception
   */
  public int updatByCourseWithDocumentId(String keyId, String documentId) throws Exception {
    CourseOne2Many course = new CourseOne2Many();
    course.setKeyId(keyId);
    course.setDocumentId(documentId);
    return super.update(UPDATE_SCHEDULING_DOCUMENT_ID_BY_COURSE, course);
  }

  /**
   * Title: 根据教师id修改教师相关信息<br>
   * Description: 根据教师id修改教师相关信息<br>
   * CreateDate: 2016年4月21日 下午3:15:27<br>
   * 
   * @category 根据教师id修改教师相关信息
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int updateTeacherInfoByTeacherId(CourseOne2ManyScheduling courseOne2ManyScheduling)
      throws Exception {
    return super.update(UPDATE_TEACHER_INFO_BY_TEACHERID, courseOne2ManyScheduling);

  }

  /**
   * 
   * Title: 查询排课列表<br>
   * Description: 查询排课列表<br>
   * CreateDate: 2016年4月26日 上午9:42:32<br>
   * 
   * @category 查询排课列表
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public Page findSchedulingList(Map<String, Object> param) throws Exception {
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    return super.findPage(FIND_SCHEDULING_LIST, new CourseOne2ManySchedulingParam(), page, rows);
  }

  /**
   * 
   * Title: 根据keyid查询排课信息<br>
   * Description: 根据keyid查询排课信息<br>
   * CreateDate: 2016年8月26日 上午11:16:14<br>
   * 
   * @category 根据keyid查询排课信息
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public CourseOne2ManyScheduling findOneByKeyIdWithNoIsUsed(String keyId) throws Exception {
    CourseOne2ManyScheduling paramObj = new CourseOne2ManyScheduling();
    paramObj.setKeyId(keyId);
    return super.findOne(FIND_SCHEDULING_BY_KEY_ID_WITH_NO_ISUESD, paramObj);
  }

}