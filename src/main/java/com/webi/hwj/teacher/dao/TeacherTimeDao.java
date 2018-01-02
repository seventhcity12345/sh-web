package com.webi.hwj.teacher.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class TeacherTimeDao extends BaseMysqlDao {
  public TeacherTimeDao() {
    super.setTableName("t_teacher_time");
  }

  /**
   * 取消预约时，需要把老师时间表的状态还原 modified by alex.yang 2015年9月9日19:57:56
   */
  private static final String CANCEL_SUBSCRIBE_TEACHER = "UPDATE t_teacher_time "
      + "SET is_subscribe=0, is_confirm=0 "
      + "WHERE is_used=1 AND key_id = :teacher_time_id ";

  public static final String TEACHER_SUBSCRIBED = "SELECT count(1) as ct "
      + "FROM t_teacher_time "
      + "WHERE is_subscribe=1 AND key_id= :teacher_time_id AND is_used=1 ";

  public static final String FIND_UNSUBSCRIBED_TEACHER = "SELECT key_id AS teacher_time_id,teacher_id "
      + "FROM t_teacher_time "
      + "WHERE is_subscribe=0 AND start_time=:start_time AND end_time= :end_time AND is_used=1";

  public static final String FIND_SUB_TIME = "SELECT start_time FROM t_teacher_time WHERE key_id = :teacher_time_id";

  /**
   * 查找时间内所有用过的房间号
   * 
   * @author komi.zsy
   */
  private static final String FIND_USED_ROOM_IN_TEACHER_TIME = "SELECT DISTINCT webex_room_host_id FROM t_teacher_time "
      + "WHERE ( "
      + "(:start_time >= start_time AND :start_time < end_time) "
      + "OR (:end_time > start_time AND :end_time <= end_time) "
      + "OR (:start_time <= start_time AND :end_time >= end_time) ) "
      + "AND is_used =1 ";

  /**
   * 查询预约小包课的时间记录
   */
  public static final String FIND_SUB_SMALLPACK_TIME = "SELECT t1.key_id as teacher_time_id,t1.start_time,t1.end_time,t1.teacher_id FROM t_teacher_time t1,("
      + "SELECT t.start_time,t.end_time FROM t_teacher_time t WHERE t.key_id = :teacher_time_id ) t2 "
      + "WHERE t1.is_subscribe=0 AND t1.is_used=1 AND t1.start_time=t2.start_time AND t1.end_time=t2.end_time";

  /**
   * top查看14天课程是否有1v1课 modified by alex 2016年4月27日
   * 16:21:31,现在t_teacher_time表里有type了，需要按type来过滤，加了一个条件
   */
  private static final String FIND_FOURTEEN_ONE2ONE_TEACHER_TIME_LIST = "SELECT date(ttt.start_time) today "
      + " FROM t_teacher_time ttt LEFT JOIN t_teacher tt ON ttt.teacher_id = tt.key_id "
      + " WHERE ttt.is_used = 1 "
      + " AND ttt.is_subscribe = 0 "
      + " AND ttt.start_time >= :start "
      + " AND DATE(ttt.start_time) < :end "
      + " AND ttt.course_type = 'course_type1' "
      // modify by seven 2016年12月27日15:31:16 过滤老师时间需要用老师的权限
      + " AND tt.teacher_course_type LIKE '%course_type1%' "
      + " GROUP BY date(ttt.start_time)";

  /**
   * Title: isTeacherSubscribed<br>
   * Description: 判断教师是否已被预约<br>
   * CreateDate: 2015年8月27日 上午10:25:46<br>
   * 
   * @category isTeacherSubscribed
   * @author vector.mjp
   * @param paramMap
   * @throws Exception
   */
  public boolean isTeacherSubscribed(Map<String, Object> paramMap) throws Exception {
    List<Map<String, Object>> rt = findList(TEACHER_SUBSCRIBED, paramMap);
    if (rt == null) {
      throw new RuntimeException("数据出现错误，在预约时，老师时间表中的id查询不到数据");
    }
    return Integer.parseInt(rt.get(0).get("ct").toString()) == 1;
  }

  /**
   * Title: 查找该时段所有已用的房间号<br>
   * Description: 查找该时段所有已用的房间号<br>
   * CreateDate: 2016年5月6日 上午10:45:36<br>
   * 
   * @category 查找该时段所有已用的房间号
   * @author komi.zsy
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findUsedWebexRoomInTeacherTime(Date startTime, Date endTime)
      throws Exception {
    Map<String, Object> teacherTimeParamMap = new HashMap<String, Object>();
    teacherTimeParamMap.put("start_time", startTime);
    teacherTimeParamMap.put("end_time", endTime);
    // 查询是否有可用的微立方房间
    List<Map<String, Object>> resultMapList = findList(FIND_USED_ROOM_IN_TEACHER_TIME,
        teacherTimeParamMap);
    return resultMapList;
  }

  /**
   * Title: 查询14天内是否有可用的1v1排课数据<br>
   * Description: findFourteenOne2OneTeacherTimeList<br>
   * CreateDate: 2016年4月27日 下午4:55:59<br>
   * 
   * @category 查询14天内是否有可用的1v1排课数据
   * @author yangmh
   * @param start
   *          开始时间
   * @param end
   *          结束时间
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findFourteenOne2OneTeacherTimeList(String start, String end)
      throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("start", start);
    paramMap.put("end", end);
    return super.findList(FIND_FOURTEEN_ONE2ONE_TEACHER_TIME_LIST, paramMap);
  }

  /**
   * Title: 取消预约的时候将teacherTime表中的被预约初始化<br>
   * Description: cancelSubscribeTeacherTime<br>
   * CreateDate: 2016年4月27日 下午5:44:10<br>
   * 
   * @category 取消预约的时候将teacherTime表中的被预约初始化
   * @author yangmh
   * @param teacherTimeId
   * @return
   * @throws Exception
   */
  public int cancelSubscribeTeacherTime(String teacherTimeId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("teacher_time_id", teacherTimeId);
    return super.update(CANCEL_SUBSCRIBE_TEACHER, paramMap);
  }

}