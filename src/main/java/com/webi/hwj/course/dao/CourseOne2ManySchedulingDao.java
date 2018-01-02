package com.webi.hwj.course.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * Title: 大课排课dao<br>
 * Description: 大课排课dao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月13日 下午8:04:44
 * 
 * @author yangmh
 */
@Repository
public class CourseOne2ManySchedulingDao extends BaseMysqlDao {
  public CourseOne2ManySchedulingDao() {
    super.setTableName("t_course_one2many_scheduling");
  }

  private static final String UPDATE_ONE2MANY_ALREADY_PERSON_COUNT_BY_COURSEID = " UPDATE t_course_one2many_scheduling "
      + " SET already_person_count = already_person_count - 1 "
      + " WHERE is_used = 1 "
      + " AND key_id = :keyId ";
  /**
   * 更新already_person_count+1
   */
  private static final String UPDATE_ONE2MANY_ALREADYCOUNT = " UPDATE t_course_one2many_scheduling "
      + " SET already_person_count = already_person_count + 1 "
      + " WHERE is_used = 1 AND key_id = :keyId ";
  /**
   * 查询一段范围内的排好课的大课(lecture/oc/es)的排课数据
   * 
   * @author yangmh
   */
  /**
   * modify by athrun.cw 2016年5月5日14:18:12 添加了老师国籍字段 teacher_nationality
   */
  private static final String FIND_ONE2MANY_SCHEDULING_LIST_BY_BETWEEN_TIME = "SELECT a.limit_number,a.course_type,"
      + "t.teacher_photo,t.teacher_name,t.teacher_nationality, a.key_id, a.course_title, a.course_pic,"
      + "a.teacher_id, a.start_time, a.end_time, a.already_person_count ,a.teacher_time_id "
      + "FROM t_course_one2many_scheduling a "
      + "LEFT JOIN t_teacher t ON a.teacher_id = t.key_id AND t.is_used = 1 "
      + "WHERE a.category_type = :categoryType AND DATE(a.start_time) = :startTime "
      + "AND a.end_time > :endTime AND a.course_type in (:courseType)  "
      + "AND a.course_level REGEXP :currentLevel AND a.is_used = 1 "
      + "ORDER BY a.start_time asc";

  /**
   * 根据course_id查询1对多 课程数据 course_id只能查询一条数据，因此无需排序
   */
  private static final String FIND_ONE2MANY_COURSE_BY_COURSEID = " SELECT key_id as course_id, category_type, "
      + " course_title, course_pic, teacher_id, teacher_name, teacher_time_id, start_time, end_time, already_person_count "
      + " FROM t_course_one2many_scheduling "
      + " WHERE is_used = 1 "
      + " AND key_id = :course_id ";

  /**
   * 根据条件category_type 和start_time，查询学员能够预约的1对多课程列表
   */
  private static final String FIND_ONE2MANY_COURSE_BY_CATEGORYTYPE = " SELECT key_id AS course_id, course_title "
      + " FROM t_course_one2many_scheduling "
      + " WHERE is_used <> 0 "
      + " AND category_type = :category_type"
      + " AND course_type = :course_type "
      + " AND start_time >= :start_time ";

  /**
   * 查找没有被预约但是被排课的数据
   * 
   * @author seven.gz
   */
  private static final String FIND_ONE2MANY_SCHEDULING_NOT_SUBSCRIBE = " SELECT teacher_id,course_title,course_id,course_type,course_pic,course_courseware, "
      + " teacher_time_id AS time_id,start_time,end_time "
      + " FROM t_course_one2many_scheduling "
      + " WHERE DATE(start_time) = :selectDay AND is_used = 1 AND teacher_id = :teacherId "
      + " AND (course_type = 'course_type2' OR course_type = 'course_type8' OR course_type = 'course_type5') ";

  /**
   * 根据课程id查找大于当前时间的排课的展示互动房间
   * 
   * @author komi.zsy
   */
  private static final String FIND_GENSEE_ROOMID_LIST_BY_COURSEID = "SELECT tg.room_id"
      + " FROM `t_course_one2many_scheduling` tcos"
      + " LEFT JOIN `t_gensee` tg"
      + " ON tcos.teacher_time_id = tg.key_id"
      + " WHERE tcos.course_id = :courseId"
      + " AND tcos.start_time > :currentDate"
      + " AND tcos.is_used <> 0 AND tg.is_used <> 0 ";

  /**
   * 锁表查询
   */
  private static final String FIND_BY_KEY_ID_LOCK = " SELECT key_id,category_type,course_courseware,course_pic,course_title, "
      + " already_person_count,limit_number,course_type "
      + " FROM t_course_one2many_scheduling WHERE key_id = :keyId FOR UPDATE ";

  /**
   * Title: 根据课程id查找大于当前时间的排课的展示互动房间<br>
   * Description: 根据课程id查找大于当前时间的排课的展示互动房间<br>
   * CreateDate: 2016年7月21日 下午5:49:52<br>
   * 
   * @category 根据课程id查找大于当前时间的排课的展示互动房间
   * @author komi.zsy
   * @param courseId
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findGenseeRoomIdListByCourseId(String courseId)
      throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("courseId", courseId);
    paramMap.put("currentDate", new Date());
    return super.findList(FIND_GENSEE_ROOMID_LIST_BY_COURSEID, paramMap);
  }

  /**
   * Title: 更新already_person_count+1<br>
   * Description: updateAlreadyPersonCount<br>
   * CreateDate: 2016年4月13日 下午8:00:04<br>
   * 
   * @category 更新already_person_count+1
   * @author yangmh
   * @param keyId
   *          课程id
   * @return
   * @throws Exception
   */
  public int updateAlreadyPersonCount(String keyId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("keyId", keyId);
    return super.update(UPDATE_ONE2MANY_ALREADYCOUNT, paramMap);
  }

  /**
   * Title: 更新already_person_count-1<br>
   * Description: updateOne2ManyAlreadyPersonCountByCourseId<br>
   * CreateDate: 2016年4月13日 下午8:00:17<br>
   * 
   * @category 更新already_person_count-1
   * @author yangmh
   * @param keyId
   *          课程id
   * @return
   * @throws Exception
   */
  public int updateOne2ManyAlreadyPersonCountByCourseId(String keyId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("keyId", keyId);
    return super.update(UPDATE_ONE2MANY_ALREADY_PERSON_COUNT_BY_COURSEID, paramMap);
  }

  /**
   * Title: 查询一段范围内的排好课的大课(lecture/oc/es)的排课数据<br>
   * Description: findOne2ManyListByBetweenTime<br>
   * CreateDate: 2016年4月13日 下午5:59:54<br>
   * 
   * @category 查询一段范围内的排好课的大课(lecture/oc/es)数据
   * @author yangmh
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @param categoryType
   *          体系类型
   * @param courseTypeList
   *          课程类型集合，可能会出现过滤多个课程类型的情况
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findOne2ManySchedulingListByBetweenTime(
      String startTime, String endTime, String categoryType, List<String> courseTypeList,
      String currentLevel)
      throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("categoryType", categoryType);
    paramMap.put("startTime", startTime);
    paramMap.put("endTime", endTime);
    paramMap.put("courseType", courseTypeList);
    paramMap.put("currentLevel", currentLevel + "([^0-9]|$)");

    return super.findList(FIND_ONE2MANY_SCHEDULING_LIST_BY_BETWEEN_TIME, paramMap);
  }

  /**
   * 
   * Title: 根据course_id查询1对多 课程数据（无需sortable，所以使用findPage方法）<br>
   * Description: findOne2ManyCoursePageEasyuiByCourseId<br>
   * CreateDate: 2015年11月27日 上午11:30:10<br>
   * 
   * @category 根据course_id查询1对多 课程数据
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findOne2ManyCoursePageEasyuiByCourseId(Map<String, Object> paramMap)
      throws Exception {
    return super.findPage(FIND_ONE2MANY_COURSE_BY_COURSEID, paramMap);
  }

  /**
   * Title: 根据条件category_type，查询学员能够预约的1对1课程列表<br>
   * Description: findUserOne2ManyCourseListByCategoryTypeAndLevel<br>
   * CreateDate: 2015年11月25日 下午5:43:08<br>
   * 
   * @category 根据条件category_type，查询学员能够预约的1对1课程列表
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findUserOne2ManyCourseListByCategoryType(
      Map<String, Object> paramMap) throws Exception {
    return super.findList(FIND_ONE2MANY_COURSE_BY_CATEGORYTYPE, paramMap);
  }

  /**
   * 
   * Title: 查询当天被排课但是没有被预约的课程<br>
   * Description: 查询当天被排课但是没有被预约的课程<br>
   * CreateDate: 2016年7月21日 上午11:09:08<br>
   * 
   * @category 查询当天被排课但是没有被预约的课程
   * @author seven.gz
   * @param keyIds
   * @param today
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findOne2ManySchedulingNotSubscribe(List<String> keyIds,
      String selectDay, String teacherId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("keyIds", keyIds);
    paramMap.put("selectDay", selectDay);
    paramMap.put("teacherId", teacherId);
    String sql = FIND_ONE2MANY_SCHEDULING_NOT_SUBSCRIBE;
    if (keyIds != null && keyIds.size() > 0) {
      sql += " AND key_id NOT IN (:keyIds) ";
    }

    return super.findList(sql, paramMap);
  }

  /**
   * 
   * Title: 带锁按keyid查询<br>
   * Description: 带锁按keyid查询<br>
   * CreateDate: 2016年10月20日 上午10:19:26<br>
   * 
   * @category 带锁按keyid查询
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyIdLock(String keyId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("keyId", keyId);
    List<Map<String, Object>> resultList = super.findList(FIND_BY_KEY_ID_LOCK, paramMap);
    if (resultList != null && resultList.size() > 0) {
      return resultList.get(0);
    } else {
      return null;
    }
  }
}