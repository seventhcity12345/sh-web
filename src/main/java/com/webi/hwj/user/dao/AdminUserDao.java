package com.webi.hwj.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class AdminUserDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(AdminUserDao.class);

  public AdminUserDao() {
    super.setTableName("t_user");
  }

  /**
   * (后台管理平台)根据日期，查询符合条件的teacher_time和老师等 modified by alex 2016年4月27日
   * 16:21:31,现在t_teacher_time表里有type了，需要按type来过滤，加了一个条件
   */
  private static final String FIND_TEACHERTIME_BY_DATE =
      " SELECT tt.key_id as teacher_time_id, tt.teacher_id, "
          + " tt.start_time, tt.end_time, t.teacher_name "
          + " FROM t_teacher_time tt "
          + " LEFT JOIN t_teacher t ON tt.teacher_id = t.key_id "
          + " WHERE tt.is_used <> 0 "
          + " AND t.is_used <> 0 "
          + " AND tt.start_time >= :start_time "
          + " AND tt.end_time <= :end_time "
          + " AND tt.is_subscribe = 0 "
          + " AND tt.course_type = 'course_type1' "
          // modify by seven 2016年12月27日15:31:16 过滤老师时间需要用老师的权限
          + " AND t.teacher_course_type LIKE '%course_type1%' "
          + " AND tt.webex_meeting_key <> '' "
          + " ORDER BY tt.start_time DESC ";

  /**
   * 根据条件category_type current_level，查询学员能够预约的1对1课程列表
   */
  private static final String FIND_ONE2ONE_COURSE_BY_CATEGORYTYPE_AND_LEVEL =
      " SELECT key_id AS course_id, course_title, course_level "
          + " FROM t_course_one2one "
          + " WHERE is_used <> 0 "
          + " AND category_type = :category_type "
          + " AND course_level = :current_level ";

  /**
   * 根据条件category_type，查询学员能够预约的主题课程列表
   */
  private static final String FIND_SMALLPACK_COURSE_BY_CATEGORYTYPE =
      " SELECT key_id AS course_id, course_title "
          + " FROM t_course_smallpack "
          + " WHERE is_used <> 0 "
          + " AND category_type = :category_type "
          + " AND course_position > :course_position ";

  /**
   * 更新用户的教务
   */
  private static final String UPDATE_USER_LEARNCOACHID = "update t_user "
      + " SET learning_coach_id = :learning_coach_id "
      + " WHERE key_id = :key_id "
      + " AND is_used <> 0 ";

  /**
   * 根据userIds字符串，查询本月中用户上了多少课(已经上的)
   */
  private static final String FIND_SUBSCRIBE_COURSE_COUNT_BY_USERIDS =
      " SELECT user_id, MAX(start_time) AS start_time, COUNT(user_id) as this_month_course_count "
          + " FROM t_subscribe_course "
          + " WHERE is_used <> 0 "
          + " AND subscribe_status = 1 "
          + " AND start_time >= :currentMonthMinTime"
          + " AND end_time <= :currentTime "
          + " AND user_id in ( :userIds ) "
          // modify by seven 2016年12月2日19:27:40 学员管理列表本月上课统计去除es
          + " AND (course_type = 'course_type1' OR course_type = 'course_type2' OR course_type = 'course_type9') "
          + " GROUP BY user_id "
          + " ORDER BY start_time DESC";

  /**
   * 根据userIds字符串，查询到followup
   */
  private static final String FIND_LAST_CREATETIME_BY_USERIDS =
      " SELECT user_id, MAX(create_date) as last_followup_time "
          + " FROM t_user_followup "
          + " WHERE is_used <> 0 "
          + " AND user_id in ( :userIds) "
          + " GROUP BY create_date ";

  /**
   * 查询学员管理页面中默认显示的数据（当前LC下的用户）
   */
  private static final String FIND_USER_INFORMATION_BY_LC = " SELECT u.key_id as user_id, "
      + " u.phone, u.current_level, u.user_code, u.learning_coach_id, bu.account, ui.real_name, ui.english_name "
      + " FROM t_user u "
      + " LEFT JOIN t_badmin_user bu ON bu.key_id = u.learning_coach_id "
      + " LEFT JOIN t_user_info ui ON ui.key_id = u.key_id "
      + " WHERE u.is_used <> 0 "
      + " AND u.is_student = 1 "
      + " AND bu.is_used <> 0 "
      + " AND ui.is_used <> 0 "
      + " AND u.learning_coach_id = :learning_coach_id ";

  /**
   * 查询学员管理页面全部学员数据
   */
  private static final String FIND_USER_INFORMATION = " SELECT u.key_id as user_id, "
      + " u.phone, u.current_level, u.user_code, u.learning_coach_id, bu.account, ui.real_name, ui.english_name "
      + " FROM t_user u "
      + " LEFT JOIN t_badmin_user bu ON bu.key_id = u.learning_coach_id AND bu.is_used <> 0 "
      + " LEFT JOIN t_user_info ui ON ui.key_id = u.key_id AND ui.is_used <> 0 "
      + " WHERE u.is_used <> 0 "
      + " AND u.is_student = 1 ";

  /**
   * 学员管理中，双击详情页面dialog中的 订课详情
   */
  private static final String FIND_SUBSCRIBED_COURSE_BY_USERID =
      " SELECT tsc.key_id AS subscribe_id,tsc.user_id, "
          + " tsc.course_id,tsc.course_type,tsc.course_title, "
          + " tsc.teacher_name,tsc.create_date,tsc.start_time, "
          + " tsc.end_time,tsc.subscribe_status,tsc.category_type, "
          + " tbu.admin_user_name "
          + " FROM  t_subscribe_course tsc  LEFT JOIN t_badmin_user tbu  "
          + " ON tsc.create_user_id = tbu.key_id "
          + " WHERE tsc.is_used <> 0  "
          + " AND tsc.user_id = :user_id  "
          + " ORDER BY tsc.start_time DESC ";

  /**
   * alex写的，潜客列表查询sql
   */
  private static final String FIND_USER_JOIN_ADMINUSER_SQL =
      "SELECT u.key_id as user_id, u.init_level, u.current_level, "
          + "u.test_level, u.phone, u.user_name, u.user_code, u.is_student, u.create_date,ui.idcard "
          + "FROM t_user u "
          + "LEFT JOIN t_user_info ui "
          + "ON u.key_id = ui.key_id "
          + "WHERE u.is_used = 1 "
          + "AND u.is_student = 0 ";// 只显示 非学员

  /**
   * komi 重置用户密码
   */
  private static final String RESET_USER_PASSWORD = "update t_user "
      + " SET pwd = :pwd "
      + " WHERE phone = :phone "
      + " AND is_used <> 0 AND is_student <> 0";

  /**
   * Title:根据用户手机号重置用户密码<br>
   * Description: 重置用户密码<br>
   * CreateDate: 2016年3月30日 下午6:33:10<br>
   * 
   * @category 重置用户密码
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int resetUserPasswordByPhone(Map<String, Object> paramMap) throws Exception {
    return super.update(RESET_USER_PASSWORD, paramMap);
  }

  /**
   * 
   * Title: 学员管理中，双击详情页面dialog中的 订课详情<br>
   * Description: findSubscribedCourseByUserIdEasyui<br>
   * CreateDate: 2015年11月29日 下午3:28:10<br>
   * 
   * @category 学员管理中，双击详情页面dialog中的 订课详情
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findSubscribedCourseByUserIdEasyui(Map<String, Object> paramMap) throws Exception {
    return super.findPage(FIND_SUBSCRIBED_COURSE_BY_USERID, paramMap);
  }

  /**
   * 
   * Title: 当选择的是1对1或者是小包课时候，需要选择时间段（无需sortable，所以使用findPage方法）<br>
   * Description: findTeacherTimePageEasyui<br>
   * CreateDate: 2015年11月26日 下午1:57:30<br>
   * 
   * @category 当选择的是1对1或者是小包课时候，需要选择时间段
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findTeacherTimePage(Map<String, Object> paramMap) throws Exception {
    return super.findPage(FIND_TEACHERTIME_BY_DATE, paramMap);
  }

  /**
   * 
   * Title: 根据条件category_type current_level，查询学员能够预约的1对1课程列表<br>
   * Description: findUserOne2OneCourseListByCategoryTypeAndLevel<br>
   * CreateDate: 2015年11月25日 下午5:42:04<br>
   * 
   * @category 根据条件category_type current_level，查询学员能够预约的1对1课程列表
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findUserOne2OneCourseListByCategoryTypeAndLevel(
      Map<String, Object> paramMap) throws Exception {
    return super.findList(FIND_ONE2ONE_COURSE_BY_CATEGORYTYPE_AND_LEVEL, paramMap);
  }

  /**
   * 
   * Title: 根据条件category_type，查询学员能够预约的主题课程列表<br>
   * Description: findUserSmallpackCourseListByCategoryType<br>
   * CreateDate: 2015年11月25日 下午5:48:43<br>
   * 
   * @category 根据条件category_type，查询学员能够预约的主题课程列表
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findUserSmallpackCourseListByCategoryType(
      Map<String, Object> paramMap) throws Exception {
    return super.findList(FIND_SMALLPACK_COURSE_BY_CATEGORYTYPE, paramMap);
  }

  /**
   * 
   * Title: 更新用户的教务<br>
   * Description: updateUserLearnCoachId<br>
   * CreateDate: 2015年11月25日 上午10:33:28<br>
   * 
   * @category 更新用户的教务
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int updateUserLearnCoachId(Map<String, Object> paramMap) throws Exception {
    return super.update(UPDATE_USER_LEARNCOACHID, paramMap);
  }

  /**
   * 
   * Title: 根据userIds字符串，查询到user_id用户上了多少课程<br>
   * Description: findSubscribeCountUser<br>
   * CreateDate: 2015年11月19日 下午8:51:13<br>
   * 
   * @category 根据userIds字符串，查询到user_id用户上了多少课程
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findSubscribeCountUser(Map<String, Object> paramMap)
      throws Exception {
    return super.findList(FIND_SUBSCRIBE_COURSE_COUNT_BY_USERIDS, paramMap);
  }

  /**
   * 
   * Title: 根据userIds字符串，查询到followup<br>
   * Description: findLastFollowupUser<br>
   * CreateDate: 2015年11月19日 下午8:20:47<br>
   * 
   * @category 根据userIds字符串，查询到followup
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findLastFollowupUser(Map<String, Object> paramMap)
      throws Exception {
    return super.findList(FIND_LAST_CREATETIME_BY_USERIDS, paramMap);
  }

  /**
   * 
   * Title: 查询学员管理页面中显示的数据 （默认是当前LC ， 当点击全部学员时候，没有LC限制） <br>
   * Description: findStudentPageEasyui<br>
   * CreateDate: 2015年11月19日 下午8:14:50<br>
   * 
   * @category 查询学员管理页面中显示的数据（默认是当前LC ， 当点击全部学员时候，没有LC限制）
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findStudentPageEasyui(Map<String, Object> paramMap) throws Exception {
    if (paramMap.get("sort") == null) {
      paramMap.put("sort", "u.create_date");
      paramMap.put("order", "asc");
    }
    // 默认页面显示的是：当前LC下的所有学员
    if ("studentPageList".equals(paramMap.get("studentPageList"))) {
      logger.debug("默认页面显示当前LC下的所有学员...");
      return findPageEasyui(paramMap, FIND_USER_INFORMATION_BY_LC);
    }
    // 如果是查看全部学员时候，则查询出没有LC限制的学员
    if ("findAllStudent".equals(paramMap.get("findAllStudent"))) {
      logger.debug("查询所有学员...");
      return findPageEasyui(paramMap, FIND_USER_INFORMATION);
    }
    logger.debug("页面请求的Controller有异常，没有收到需要请求的查询要求，返回空数据...");
    return new Page();
  }

  /**
   * @category easyui查询数据方法有分页无sql.
   * @param paramMap
   *          参数map
   * @return
   * @throws Exception
   */
  public Page findPageEasyuiUser(Map<String, Object> fields) throws Exception {
    return findPageEasyui(fields, FIND_USER_JOIN_ADMINUSER_SQL);
  }
}