package com.webi.hwj.courseone2many.dao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.course.entity.AdminCourse;
import com.webi.hwj.courseone2many.entity.CourseOne2Many;

@Repository
public class AdminCourseOne2ManyDao extends BaseEntityDao<CourseOne2Many> {
  private static Logger logger = Logger.getLogger(AdminCourseOne2ManyDao.class);

  /**
   * 查询1v1 和 1vn 的课程
   */
  private static final String FIND_ALL_COURSE = " SELECT key_id,category_type,course_type,course_title,course_level,  "
      + " course_pic,course_courseware,document_id,create_date,course_desc FROM "
      + " ( SELECT key_id,category_type,course_type,course_title,course_level, "
      + " course_pic,course_courseware,document_id,create_date,course_desc "
      + " FROM t_course_one2many "
      + " WHERE is_used = 1 "
      + " UNION ALL "
      + " SELECT key_id,category_type,course_type,course_title,course_level, "
      + " course_pic,course_courseware,document_id,create_date,'' as course_desc "
      + " FROM t_course_one2one "
      + " WHERE is_used = 1 "
      + " UNION ALL SELECT  key_id,category_type,'course_type9' AS course_type, course_title,course_level, "
      + " course_pic,course_courseware,document_id,create_date,'' AS course_desc "
      + " FROM  t_course_extension_1v1  WHERE is_used = 1 "
      + ") temp where 1=1 ";

  /**
   * 按条件查询课程
   */
  private static final String FIND_ONE2MANY_COURSE_LIST = " SELECT key_id,course_title,course_level "
      + " FROM t_course_one2many "
      + " WHERE category_type = :categoryType AND course_type=:courseType AND is_used=1 ";

  /**
   * 
   * Title: 查询1v1和1vN的课程<br>
   * Description: 查询1v1和1vN的课程<br>
   * CreateDate: 2016年4月12日 上午9:24:02<br>
   * 
   * @category 查询1v1和1vN的课程
   * @author seven.gz
   * @param paramObj
   * @param param
   * @return
   * @throws Exception
   */
  /**
   * Title: findAllCoursePageEasyui<br>
   * Description: findAllCoursePageEasyui<br>
   * CreateDate: 2016年6月28日 下午4:31:02<br>
   * 
   * @category findAllCoursePageEasyui
   * @author seven.gz
   * @param param
   * @return
   * @throws Exception
   */
  public Page findAllCoursePageEasyui(Map<String, Object> param) throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    AdminCourse adminCourse = new AdminCourse();
    adminCourse.setCons((String) param.get("cons"));
    return super.findPageEasyui(FIND_ALL_COURSE, adminCourse, sort, order, page, rows);
  }

  /**
   * 
   * Title: 查询课程(页面条件筛选)<br>
   * Description: 查询课程(页面条件筛选)<br>
   * CreateDate: 2016年4月27日 下午5:13:22<br>
   * 
   * @category 查询课程(页面条件筛选)
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public List<CourseOne2Many> findOne2ManyCourseList(CourseOne2Many paramObj) throws Exception {
    String sql = FIND_ONE2MANY_COURSE_LIST;
    boolean useLevelFlag = false;
    if (!"%%".equals(paramObj.getCourseLevel())) {
      sql += " AND course_level LIKE :courseLevel ";
      useLevelFlag = true;
    }
    sql += " ORDER BY course_title ASC";

    List<CourseOne2Many> resList = super.findList(sql, paramObj);

    if (useLevelFlag) {
      Iterator<CourseOne2Many> iter = resList.iterator();
      // 去除leve1时 查出的 leve1N
      while (iter.hasNext()) {
        CourseOne2Many adminCourseOne2Many = iter.next();
        String courseLevel = adminCourseOne2Many.getCourseLevel();
        if (courseLevel != null) {
          String[] courseLevels = courseLevel.split(",");
          List<String> courseLevelsList = Arrays.asList(courseLevels);
          if (!courseLevelsList.contains(paramObj.getCourseLevel().replaceAll("%", ""))) {
            iter.remove();
          }
        }
      }
    }
    return resList;
  }

  /**
   * 
   * Title: 根据keyId查询课程信息<br>
   * Description: 根据keyId查询课程信息<br>
   * CreateDate: 2016年4月29日 上午10:46:57<br>
   * 
   * @category findOneByKeyId
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public CourseOne2Many findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId,
        " key_id,category_type,course_Type,course_title,course_level,course_pic,course_courseware,document_id,course_desc ");
  }

}