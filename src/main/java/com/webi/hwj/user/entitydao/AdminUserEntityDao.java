package com.webi.hwj.user.entitydao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entity.UserInfo;
import com.webi.hwj.user.param.FindUserCourseStatisticsParam;
import com.webi.hwj.user.param.FindUserOrderInfoParam;
import com.webi.hwj.user.param.KeyFollowupStudentParam;
import com.webi.hwj.user.util.CompletePercentUtil;

@Repository
public class AdminUserEntityDao extends BaseEntityDao<User> {
  private static Logger logger = Logger.getLogger(AdminUserEntityDao.class);

  /**
   * 查询用户基本信息 seven
   */
  private final static String FIND_GENERAL_ENGLISH_USER_LIST = " SELECT tu.user_code,tu.key_id AS user_id,tu.phone,tu.learning_coach_id,tbu.admin_user_name, "
      + " tui.real_name,tui.english_name "
      + " FROM  t_user tu  "
      + " LEFT JOIN t_badmin_user tbu ON tbu.key_id = tu.learning_coach_id  AND tbu.is_used <> 0 "
      + " LEFT JOIN t_user_info tui ON tui.key_id = tu.key_id  AND tui.is_used <> 0 "
      + " WHERE tu.is_used <> 0  AND tu.is_student = 1  AND tu.current_level LIKE :currentLevel ";

  /**
   * 查找重点跟进学员
   */
  private final static String FIND_KEY_FOLLOWUP_STUDENTS = " SELECT tu.key_id AS user_id,tu.user_code,tui.real_name,tui.english_name,tbu.admin_user_name,tu.phone,"
      + " toc.start_order_time,toc.end_order_time,tuf.followup_count,tuf.last_follow_time,tbucc.admin_user_name AS cc_name "
      + " FROM t_user tu "
      + " LEFT JOIN t_user_info tui ON tu.key_id = tui.key_id AND tui.is_used = 1 "
      + " LEFT JOIN t_badmin_user tbu ON tu.learning_coach_id = tbu.key_id AND tbu.is_used = 1 "
      + " LEFT JOIN t_order_course toc ON toc.user_id = tu.key_id AND toc.is_used = 1 AND toc.order_status = "
      + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " LEFT JOIN t_badmin_user tbucc ON toc.create_user_id = tbucc.key_id "
      + " LEFT JOIN (SELECT user_id,MAX(create_date) last_follow_time,COUNT(user_id) followup_count FROM t_user_followup WHERE is_used = 1 "
      + " GROUP BY user_id)  tuf ON tuf.user_id = tu.key_id "
      + " WHERE tu.is_used = 1 AND tu.is_student = 1 ";

  /**
   * 查找重点跟进学员
   * 
   * Modify By YuanLong[Felix] 2017.6.14
   * 需求描述：Tom提出需求 ,speakHi后台管理系统的'本月FollowUp情况',查询时'follow次数'一列统计显示本月所有follow次数,取消小于2的限制;
   *        而'最后一次Follow时间',取消原先本月最后一次Follow时间的限制,查询展示最后一次Follow时间,不做本月约束。
   * 修改记录：将原先 AND (followup_count < :followupCount OR followup_count IS NULL)约束剔除,且新增left join子查询。
   */
  private final static String FIND_LESS_FOLLOWUP_STUDENTS = " SELECT tu.key_id AS user_id,tu.user_code,tui.real_name,tui.english_name,tbu.admin_user_name,tu.phone,"
      + " toc.start_order_time,tuf.followup_count,tufcopy.last_follow_time "
      + " FROM t_user tu "
      + " LEFT JOIN t_user_info tui ON tu.key_id = tui.key_id AND tui.is_used = 1 "
      + " LEFT JOIN t_badmin_user tbu ON tu.learning_coach_id = tbu.key_id AND tbu.is_used = 1 "
      + " LEFT JOIN t_order_course toc ON toc.user_id = tu.key_id AND toc.is_used = 1 AND toc.order_status = "
      + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " LEFT JOIN (SELECT user_id,COUNT(user_id) followup_count FROM t_user_followup "
      + " WHERE is_used = 1 AND create_date >= :filterTime "
      + " GROUP BY user_id)  tuf ON tuf.user_id = tu.key_id "
      + " LEFT JOIN (SELECT user_id,MAX(create_date) last_follow_time FROM t_user_followup "
      + " WHERE is_used = 1"
      + " GROUP BY user_id)  tufcopy ON tufcopy.user_id = tu.key_id "
      + " WHERE tu.is_used = 1 AND tu.is_student = 1";
  
  /**
   * 查询做完课件一周内没有上课
   */
  private final static String FIND_NOT_SBUSCRIBE_CLASS = " SELECT  tu.key_id AS user_id,tu.user_code,tu.phone,tui.real_name,tui.english_name,tbu.admin_user_name,ttp.course_title,ttp.first_complete_time,ttp.tmm_percent,tuf.last_follow_time,tuf.followup_count "
      + " FROM  t_tellmemore_percent ttp LEFT JOIN t_user tu ON ttp.user_id = tu.key_id "
      + " LEFT JOIN t_user_info tui ON tu.key_id = tui.key_id  AND tui.is_used = 1 "
      + " LEFT JOIN t_badmin_user tbu ON tu.learning_coach_id = tbu.key_id AND tbu.is_used = 1 "
      + " LEFT JOIN t_order_course toc ON toc.user_id = tu.key_id AND toc.is_used = 1 "
      + " LEFT JOIN(SELECT order_id,SUM(remain_course_count) AS remain_course_count FROM t_order_course_option WHERE course_type = 'course_type1' AND is_used = 1 GROUP BY order_id) toco "
      + " ON toco.order_id = toc.key_id "
      + " LEFT JOIN (SELECT user_id,MAX(create_date) last_follow_time,COUNT(user_id) followup_count FROM t_user_followup WHERE is_used = 1 GROUP BY user_id) tuf "
      + " ON tuf.user_id = tu.key_id "
      + " WHERE tu.is_used = 1 AND tu.is_student = 1 AND toc.order_status = "
      + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " AND toco.remain_course_count > 0  AND ttp.key_id IN (:keyIds) ";

  /**
   * 根据学生来源查询学员合同信息
   */
  private final static String FIND_USER_ORDER_INFO_BY_TYPE = " SELECT tu.user_code,tu.key_id,toc.user_id,toc.user_name,tu.phone, "
      + " tu.init_level,tu.current_level,toc.start_order_time,toc.end_order_time,tu.phone "
      + " FROM t_user tu LEFT JOIN t_order_course toc ON tu.key_id = toc.user_id "
      // modify by seven 过期的学员也要
      + " WHERE tu.is_used = 1 AND toc.is_used = 1 AND (toc.order_status = "
      + OrderStatusConstant.ORDER_STATUS_HAVE_PAID + " OR toc.order_status = "
      + OrderStatusConstant.ORDER_STATUS_HAVE_EXPIRED+")"
      + " AND toc.start_order_time >= :startOrderTime AND toc.start_order_time < :endOrderTime"
      + " AND toc.user_from_type = :userFromType ";

  /**
   * Title: 通过主键查找用户名<br>
   * Description: findUserNameByKeyId<br>
   * CreateDate: 2016年4月5日 下午7:07:18<br>
   * 
   * @category findUserNameByKeyId
   * @author athrun.cw
   * @param keyId
   * @return
   * @throws Exception
   */
  public User findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId,
        "key_id,phone,user_name,current_level,level_change_time,is_student,learning_coach_id");
  }

  /**
   * Title: 更新完成百分比<br>
   * Description: user_info表的字段发生变更后，需要重新计算一下百分比并且保存<br>
   * CreateDate: 2016年2月17日 下午2:47:18<br>
   * 
   * @category 更新完成百分比
   * @author yangmh
   * @throws Exception
   */
  /**
   * Title: updateCompletePercent<br>
   * Description: updateCompletePercent<br>
   * CreateDate: 2016年4月5日 下午8:03:39<br>
   * 
   * @category updateCompletePercent
   * @author athrun.cw
   * @param userInfoParam
   * @throws Exception
   */
  public int updateCompletePercent(UserInfo userInfo) throws Exception {
    User user = new User();
    user.setKeyId(userInfo.getKeyId());
    user.setInfoCompletePercent(CompletePercentUtil.getCompletePercent(userInfo));
    return super.update(user);
  }

  /**
   * 
   * Title: 查询学员基本信息<br>
   * Description: 用于查询学员基本信息，便于后面统计这些数据的其他数据<br>
   * CreateDate: 2016年5月31日 上午11:40:57<br>
   * 
   * @category findPageEasyui
   * @author seven.gz
   * @param param
   * @param categoryType
   *          - General 或 Business 用于筛选通用还是商务
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(Map<String, Object> param, String currentLevel, String learningCoachId)
      throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      sort = " tu.user_code ";
      order = " ASC ";
    }

    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));

    FindUserCourseStatisticsParam findUserStatisticsInfoParam = new FindUserCourseStatisticsParam();
    findUserStatisticsInfoParam.setCons((String) param.get("cons"));
    findUserStatisticsInfoParam.setCurrentLevel(currentLevel);
    String sql = FIND_GENERAL_ENGLISH_USER_LIST;
    if (!StringUtils.isEmpty(learningCoachId)) {
      sql += " and learning_coach_id = :learningCoachId ";
      findUserStatisticsInfoParam.setLearningCoachId(learningCoachId);
    }

    return super.findPageEasyui(sql, findUserStatisticsInfoParam, sort, order, page, rows);
  }

  /**
   * 
   * Title: 查询新生信息<br>
   * Description: 查询新生信息<br>
   * CreateDate: 2016年7月5日 下午6:11:31<br>
   * 
   * @category 查询新生信息
   * @author seven.gz
   * @param param
   * @param newStudentDate
   * @param learningCoachId
   * @return
   * @throws Exception
   */
  public Page findNewStudentPage(Map<String, Object> param, Date newStudentDate,
      String learningCoachId) throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      // 同时用两个排序条件 只能用小写
      sort = " last_follow_time asc,start_order_time ";
      order = " ASC ";
    }
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    KeyFollowupStudentParam keyFollowupStudentParam = new KeyFollowupStudentParam();
    keyFollowupStudentParam.setCons((String) param.get("cons"));
    keyFollowupStudentParam.setStartOrderTime(newStudentDate);
    // 为了共用一段sql 所以在这里拼上不同的地方
    String sql = FIND_KEY_FOLLOWUP_STUDENTS + " AND toc.start_order_time  >= :startOrderTime ";
    if (!StringUtils.isEmpty(learningCoachId)) {
      sql += " and learning_coach_id = :learningCoachId ";
      keyFollowupStudentParam.setLearningCoachId(learningCoachId);
    }
    return findPageEasyui(sql, keyFollowupStudentParam, sort, order, page, rows);
  }

  /**
   * 
   * Title: 查询两个月内合同到期学员<br>
   * Description: 查询两个月内合同到期学员<br>
   * CreateDate: 2016年7月5日 下午6:11:31<br>
   * 
   * @category 查询两个月内合同到期学员
   * @author seven.gz
   * @param param
   * @param newStudentDate
   * @param learningCoachId
   * @return
   * @throws Exception
   */
  public Page findTwoMonthEndStudentPage(Map<String, Object> param, Date endOrderTime,
      String learningCoachId) throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      sort = " end_order_time ";
      order = " ASC ";
    }
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    KeyFollowupStudentParam keyFollowupStudentParam = new KeyFollowupStudentParam();
    keyFollowupStudentParam.setCons((String) param.get("cons"));
    keyFollowupStudentParam.setEndOrderTime(endOrderTime);
    // 为了共用一段sql 所以在这里拼上不同的地方
    String sql = FIND_KEY_FOLLOWUP_STUDENTS + " AND toc.end_order_time  <= :endOrderTime ";
    if (!StringUtils.isEmpty(learningCoachId)) {
      sql += " and learning_coach_id = :learningCoachId ";
      keyFollowupStudentParam.setLearningCoachId(learningCoachId);
    }
    return findPageEasyui(sql, keyFollowupStudentParam, sort, order, page, rows);
  }

  /**
   * 
   * Title: 本月follow少于2次的学员<br>
   * Description: 本月follow少于2次的学员<br>
   * CreateDate: 2016年7月5日 下午6:11:31<br>
   * 
   * @category 本月follow少于2次的学员
   * @author seven.gz
   * @param param
   * @param newStudentDate
   * @param learningCoachId
   * @return
   * @throws Exception
   */
  public Page findLessFollowupStudentPage(Map<String, Object> param, Date filterTime,
      String learningCoachId, int followupCount) throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      sort = " last_follow_time ";
      order = " ASC ";
    }
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    KeyFollowupStudentParam keyFollowupStudentParam = new KeyFollowupStudentParam();
    keyFollowupStudentParam.setCons((String) param.get("cons"));
    keyFollowupStudentParam.setFilterTime(filterTime);
    // 本月
    keyFollowupStudentParam.setFollowupCount(followupCount);
    String sql = FIND_LESS_FOLLOWUP_STUDENTS;
    if (!StringUtils.isEmpty(learningCoachId)) {
      sql += " and learning_coach_id = :learningCoachId ";
      keyFollowupStudentParam.setLearningCoachId(learningCoachId);
    }
    return findPageEasyui(sql, keyFollowupStudentParam, sort, order, page, rows);
  }

  /**
   * 
   * Title: 查询合同超过一个月但没有做过课件的学员信息<br>
   * Description: 查询合同超过一个月但没有做过课件的学员信息<br>
   * CreateDate: 2016年7月5日 下午6:11:31<br>
   * 
   * @category 查询合同超过一个月但没有做过课件的学员信息
   * @author seven.gz
   * @param param
   * @param startOrderTime
   *          一个月前的日期
   * @param learningCoachId
   * @return
   * @throws Exception
   */
  public Page findNeverDoneRsaPage(Map<String, Object> param, Date startOrderTime,
      String learningCoachId, List<String> userIds) throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      // 同时用两个排序条件 只能用小写
      sort = " last_follow_time";
      order = " ASC ";
    }
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    KeyFollowupStudentParam keyFollowupStudentParam = new KeyFollowupStudentParam();
    keyFollowupStudentParam.setCons((String) param.get("cons"));
    keyFollowupStudentParam.setStartOrderTime(startOrderTime);
    keyFollowupStudentParam.setUserIds(userIds);
    // 为了共用一段sql 所以在这里拼上不同的地方
    String sql = FIND_KEY_FOLLOWUP_STUDENTS
        + " AND toc.start_order_time  <= :startOrderTime AND tu.key_id IN (:userIds) ";
    if (!StringUtils.isEmpty(learningCoachId)) {
      sql += " and learning_coach_id = :learningCoachId ";
      keyFollowupStudentParam.setLearningCoachId(learningCoachId);
    }
    return findPageEasyui(sql, keyFollowupStudentParam, sort, order, page, rows);
  }

  /**
   * 
   * Title: 查询学员信息<br>
   * Description: 查询学员信息<br>
   * CreateDate: 2016年7月12日 下午3:56:05<br>
   * 
   * @category 查询学员信息
   * @author seven.gz
   * @param param
   * @param startOrderTime
   * @param learningCoachId
   * @param userIds
   * @return
   * @throws Exception
   */
  public Page findStudentInfoPage(Map<String, Object> param, String learningCoachId,
      List<String> userIds) throws Exception {
    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      // 同时用两个排序条件 只能用小写
      sort = " last_follow_time";
      order = " ASC ";
    }
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    KeyFollowupStudentParam keyFollowupStudentParam = new KeyFollowupStudentParam();
    keyFollowupStudentParam.setCons((String) param.get("cons"));
    keyFollowupStudentParam.setUserIds(userIds);
    String sql = FIND_KEY_FOLLOWUP_STUDENTS + " AND tu.key_id IN (:userIds) ";
    if (!StringUtils.isEmpty(learningCoachId)) {
      sql += " and learning_coach_id = :learningCoachId ";
      keyFollowupStudentParam.setLearningCoachId(learningCoachId);
    }
    return findPageEasyui(sql, keyFollowupStudentParam, sort, order, page, rows);
  }

  /**
   * 
   * Title: 做完课件一周内没有上课<br>
   * Description: 做完课件一周内没有上课<br>
   * CreateDate: 2016年7月13日 下午6:34:02<br>
   * 
   * @category 做完课件一周内没有上课
   * @author seven.gz
   * @param param
   * @param learningCoachId
   * @param userIds
   * @return
   * @throws Exception
   */
  public Page findNotSbuscribeClass(Map<String, Object> param, String learningCoachId,
      List<String> keyIds) throws Exception {

    String sort = (String) param.get("sort");
    String order = (String) param.get("order");
    if (sort == null) {
      // 同时用两个排序条件 只能用小写
      sort = " last_follow_time";
      order = " ASC ";
    }
    Integer page = Integer.valueOf((String) param.get("page"));
    Integer rows = Integer.valueOf((String) param.get("rows"));
    KeyFollowupStudentParam keyFollowupStudentParam = new KeyFollowupStudentParam();
    keyFollowupStudentParam.setCons((String) param.get("cons"));
    keyFollowupStudentParam.setKeyIds(keyIds);
    String sql = FIND_NOT_SBUSCRIBE_CLASS;
    if (!StringUtils.isEmpty(learningCoachId)) {
      sql += " and learning_coach_id = :learningCoachId ";
      keyFollowupStudentParam.setLearningCoachId(learningCoachId);
    }
    return findPageEasyui(sql, keyFollowupStudentParam, sort, order, page, rows);
  }

  /**
   * 
   * Title: 根据学员来源查询学员合同信息<br>
   * Description: 根据学员来源查询学员合同信息<br>
   * CreateDate: 2016年11月15日 下午2:20:15<br>
   * 
   * @category 根据学员来源查询学员合同信息
   * @author seven.gz
   * @param userFromType
   * @return
   * @throws Exception
   */
  public List<FindUserOrderInfoParam> findUserOrderInfoByType(int userFromType,String startOrderTime,String endOrderTime) throws Exception {
    FindUserOrderInfoParam paramObj = new FindUserOrderInfoParam();
//    Date currentDate = new Date();
    paramObj.setUserFromType(userFromType);
//    paramObj.setStartOrderTime(CalendarUtil.getFirstDayOfMonth(startOrderTime));
//    paramObj.setEndOrderTime(CalendarUtil.getFirstDayOfNextMonth(endOrderTime));
    paramObj.setStartOrderTime(DateUtil.strToDateYYYYMMDDHHMMSS(startOrderTime));
    paramObj.setEndOrderTime(DateUtil.strToDateYYYYMMDDHHMMSS(endOrderTime));
    return super.findList(FIND_USER_ORDER_INFO_BY_TYPE, paramObj);
  }
  
  /**
   * Title: 根据手机号查找用户<br>
   * Description: 根据手机号查找用户<br>
   * CreateDate: 2017年3月20日 下午5:29:20<br>
   * @category 根据手机号查找用户 
   * @author komi.zsy
   * @param phone 手机号
   * @return
   * @throws Exception
   */
  public User findOneByPhone(String phone) throws Exception {
    User paramObj = new User();
    paramObj.setPhone(phone);
    return super.findOne(paramObj, "key_id");
  }
}