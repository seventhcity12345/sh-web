package com.webi.hwj.trainingcamp.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.trainingcamp.entity.TrainingMember;
import com.webi.hwj.trainingcamp.param.TrainingCampIntegralProfileParam;
import com.webi.hwj.trainingcamp.param.TrainingMemberParam;
import com.webi.hwj.trainingcamp.param.TrainingMemberTotalParam;
import com.webi.hwj.trainingcamp.param.UserNotInTrainingMemberParam;

@Repository
public class TrainingMemberEntityDao extends BaseEntityDao<TrainingMember> {

  private static Logger logger = Logger.getLogger(TrainingMemberEntityDao.class);

  /**
   * 根据训练营查询成员列表
   * 
   * @author komi.zsy
   */
  private final static String FIND_TRAINING_MEMBER_List_BY_TRAINING_CAMP_ID =
      "SELECT ttm.key_id,ttm.training_camp_id,"
          + "ttm.training_member_user_id,ttm.training_member_user_code,ttm.training_member_english_name,"
          + "ttm.training_member_real_name,ttm.training_member_phone,ttm.training_member_current_level,"
          + "ttm.training_member_total_score,ttm.training_member_rsa_score,ttm.training_member_course_score,"
          + "ttmo.training_member_option_reason,ttmo.training_member_option_score,ttmo.training_member_option_type"
          + " FROM t_training_member ttm"
          + " LEFT JOIN t_training_member_option ttmo"
          + " ON ttmo.training_camp_id = ttm.training_camp_id AND ttmo.training_member_user_id = ttm.training_member_user_id"
          + " WHERE (ttmo.is_used = 1  OR ttmo.is_used IS NULL )"
          + " AND ttm.is_used = 1 AND TTM.training_camp_id = :trainingCampId";

  /**
   * 更新总分
   * 
   * @author komi.zsy
   */
  private final static String UPDATE_TOTAL_SCORE = "UPDATE t_training_member"
      + " SET training_member_total_score = training_member_total_score + :trainingMemberTotalScore"
      + " WHERE training_member_user_id = :trainingMemberUserId"
      + " AND training_camp_id = :trainingCampId"
      + " AND is_used = 1";

  /**
   * 更新用户信息
   * 
   * @author komi.zsy
   */
  private final static String UPDATE_MEMBER_USER_INFO = "UPDATE t_training_member"
      + " SET training_member_english_name = :trainingMemberEnglishName,"
      + "training_member_real_name = :trainingMemberRealName,"
      + "training_member_gender = :trainingMemberGender"
      + " WHERE training_member_user_id = :trainingMemberUserId";

  /**
   * 更新手机号
   * 
   * @author komi.zsy
   */
  private final static String UPDATE_MEMBER_USER_PHONE = "UPDATE t_training_member"
      + " SET training_member_phone = :trainingMemberPhone"
      + " WHERE training_member_user_id = :trainingMemberUserId";

  /**
   * 更新当前等级
   * 
   * @author komi.zsy
   */
  private final static String UPDATE_MEMBER_USER_CURRENTLEVEL = "UPDATE t_training_member"
      + " SET training_member_current_level = :trainingMemberCurrentLevel"
      + " WHERE training_member_user_id = :trainingMemberUserId";

  /**
   * 更新用户真实姓名
   * 
   * @author komi.zsy
   */
  private final static String UPDATE_MEMBER_USER_REAL_NAME = "UPDATE t_training_member"
      + " SET training_member_real_name = :trainingMemberRealName"
      + " WHERE training_member_user_id = :trainingMemberUserId";

  /**
   * 更新用户头像
   * 
   * @author komi.zsy
   */
  private final static String UPDATE_MEMBER_USER_PIC = "UPDATE t_training_member"
      + " SET training_member_pic = :trainingMemberPic"
      + " WHERE training_member_user_id = :trainingMemberUserId";
  
  /**
   * 根据用户id，查找不在这些id中的剩余用户
   * 查询不再正在执行中的训练营中的学员
   * @author komi.zsy
   */
  private final static String FIND_PAGE_BY_USER_NOT_IN_TRAINING_CAMP = "SELECT tu.key_id,tu.user_code,tui.real_name,tui.english_name,tu.phone"
      + " FROM t_user tu LEFT JOIN t_user_info tui ON tu.key_id = tui.key_id "
      + " LEFT JOIN t_order_course toc"
      + " ON tu.key_id = toc.user_id "
      + " WHERE tu.is_used = 1 AND tui.is_used = 1 AND toc.is_used = 1"
      + " AND toc.order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " AND tu.key_id NOT IN (:keyIds)";
  
  /**
   * 查找在有效训练营中的学员id
   * @author komi.zsy
   */
  private final static String FIND_TRAINING_MEMBER_IN_TRAINING_CAMP = "SELECT ttm.training_member_user_id"
      + " FROM t_training_camp ttc"
      + " LEFT JOIN t_training_member ttm"
      + " ON ttc.key_id = ttm.training_camp_id"
      + " WHERE ttm.is_used = 1 AND ttc.is_used = 1"
      + " AND (:createDate BETWEEN ttc.training_camp_start_time AND ttc.training_camp_end_time)"
      + " GROUP BY ttm.training_member_user_id";
  
  /**
   * 查询训练营中的成员信息
   * 用来计算平均数
   * @author komi.zsy
   */
  private final static String FIND_MEMBER_INFO_BY_CAMP_ID = "SELECT training_member_user_id,"
      + "training_member_age,training_member_gender,training_member_current_level"
      + " FROM t_training_member"
      + " WHERE is_used = 1"
      + " AND training_camp_id = :trainingCampId";
  
  /**
   * 删除训练营成员
   * @author komi.zsy
   */
  private final static String DELETE_MEMBER_BY_CAMP_IS_AND_USERIDS = "UPDATE t_training_member"
      + " SET is_used = 0,update_user_id= :updateUserId"
      + " WHERE training_camp_id = :keyId AND training_member_user_id IN (:keyIds)"
      + " AND is_used = 1";
  
  /**
   * Title: 删除训练营成员<br>
   * Description: 删除训练营成员<br>
   * CreateDate: 2017年9月13日 上午11:22:02<br>
   * @category 删除训练营成员 
   * @author komi.zsy
   * @param trainingCampId 训练营id
   * @param userIds 用户ids
   * @return
   * @throws Exception
   */
  public int deleteMemberByCampIsAndUserIds(String trainingCampId,List<String> userIds,String updateUserId) throws Exception{
    UserNotInTrainingMemberParam paramObj = new UserNotInTrainingMemberParam();
    paramObj.setKeyId(trainingCampId);
    paramObj.setKeyIds(userIds);
    paramObj.setUpdateUserId(updateUserId);
    return super.update(DELETE_MEMBER_BY_CAMP_IS_AND_USERIDS, paramObj);
  }
  
  /**
   * Title: 查询训练营中的成员信息<br>
   * Description: 用来计算平均数<br>
   * CreateDate: 2017年9月13日 上午9:48:57<br>
   * @category 查询训练营中的成员信息 
   * @author komi.zsy
   * @param trainingCampId 训练营id
   * @return
   * @throws Exception
   */
  public List<TrainingMember> findMemberInfoByCampId(String trainingCampId) throws Exception{
    TrainingMember paramObj = new TrainingMember();
    paramObj.setTrainingCampId(trainingCampId);
    return super.findList(FIND_MEMBER_INFO_BY_CAMP_ID, paramObj);
  }
  
  /**
   * Title: 查找在有效训练营中的学员id<br>
   * Description: 查找在有效训练营中的学员id<br>
   * CreateDate: 2017年9月12日 下午7:42:54<br>
   * @category 查找在有效训练营中的学员id 
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<TrainingMember> findTrainingMemberUnTrainingCamp() throws Exception{
    TrainingMember paramObj = new TrainingMember();
    paramObj.setCreateDate(new Date());
    return super.findList(FIND_TRAINING_MEMBER_IN_TRAINING_CAMP, paramObj);
  }
  
  /**
   * Title: 查询不再正在执行中的训练营中的学员<br>
   * Description: 根据用户id，查找不在这些id中的剩余用户<br>
   * CreateDate: 2017年9月12日 下午6:27:54<br>
   * @category 查询不再正在执行中的训练营中的学员 
   * @author komi.zsy
   * @param cons
   * @param sort
   * @param order
   * @param page
   * @param rows
   * @param keyIds 在训练营中的学员id
   * @return
   * @throws Exception
   */
  public Page findPageByUserNotInTrainingCamp(String cons, String sort, String order, Integer page,
      Integer rows, List<String> keyIds) throws Exception {
    UserNotInTrainingMemberParam userNotInTrainingMemberParam = new UserNotInTrainingMemberParam();
    userNotInTrainingMemberParam.setCons(cons);
    userNotInTrainingMemberParam.setKeyIds(keyIds);
    if (StringUtils.isEmpty(sort)) {
      sort = "tu.user_code";
      order = "DESC";
    }
    return super.findPageEasyui(FIND_PAGE_BY_USER_NOT_IN_TRAINING_CAMP, userNotInTrainingMemberParam,
        sort, order, page, rows);
  }


  /**
   * Title: 更新用户真实姓名<br>
   * Description: 更新用户真实姓名<br>
   * CreateDate: 2017年8月10日 下午6:48:32<br>
   * 
   * @category 更新用户真实姓名
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateMemberUserRealName(TrainingMember paramObj) throws Exception {
    return super.update(UPDATE_MEMBER_USER_REAL_NAME, paramObj);
  }

  /**
   * Title: 更新用户头像<br>
   * Description: 更新用户头像<br>
   * CreateDate: 2017年8月10日 下午6:48:32<br>
   * 
   * @category 更新用户头像
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateMemberUserPic(TrainingMember paramObj) throws Exception {
    return super.update(UPDATE_MEMBER_USER_PIC, paramObj);
  }

  /**
   * Title: 更新用户信息<br>
   * Description: 更新用户信息<br>
   * CreateDate: 2017年8月10日 下午6:48:32<br>
   * 
   * @category 更新用户信息
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateMemberUserInfo(TrainingMember paramObj) throws Exception {
    return super.update(UPDATE_MEMBER_USER_INFO, paramObj);
  }

  /**
   * Title: 更新手机号<br>
   * Description: 更新手机号<br>
   * CreateDate: 2017年8月10日 下午6:48:35<br>
   * 
   * @category 更新手机号
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateMemberUserPhone(TrainingMember paramObj) throws Exception {
    return super.update(UPDATE_MEMBER_USER_PHONE, paramObj);
  }

  /**
   * Title: 更新当前等级<br>
   * Description: 更新当前等级<br>
   * CreateDate: 2017年8月10日 下午6:48:39<br>
   * 
   * @category 更新当前等级
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateMemberUserCurrentLevel(TrainingMember paramObj) throws Exception {
    return super.update(UPDATE_MEMBER_USER_CURRENTLEVEL, paramObj);
  }

  /**
   * Title: 更新总分<br>
   * Description: 更新总分<br>
   * CreateDate: 2017年8月10日 下午4:11:25<br>
   * 
   * @category 更新总分
   * @author komi.zsy
   * @param trainingCampId
   *          训练营id
   * @param trainingMemberUserId
   *          用户id
   * @param trainingMemberTotalScore
   *          需要加减的分数
   * @return
   * @throws Exception
   */
  public int updateTotalScore(String trainingCampId, String trainingMemberUserId,
      int trainingMemberTotalScore) throws Exception {
    TrainingMember paramObj = new TrainingMember();
    paramObj.setTrainingCampId(trainingCampId);
    paramObj.setTrainingMemberUserId(trainingMemberUserId);
    paramObj.setTrainingMemberTotalScore(trainingMemberTotalScore);
    return super.update(UPDATE_TOTAL_SCORE, paramObj);
  }

  /**
   * Title: 根据训练营查询成员列表<br>
   * Description: 根据训练营查询成员列表<br>
   * CreateDate: 2017年8月8日 下午4:46:12<br>
   * 
   * @category 根据训练营查询成员列表
   * @author komi.zsy
   * @param cons
   *          组合查询
   * @param sort
   * @param order
   * @param page
   * @param rows
   * @param trainingCampId
   *          训练营id
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(String cons, String sort, String order, Integer page,
      Integer rows, String trainingCampId) throws Exception {
    TrainingMemberParam trainingMemberParam = new TrainingMemberParam();
    trainingMemberParam.setCons(cons);
    trainingMemberParam.setTrainingCampId(trainingCampId);
    if (StringUtils.isEmpty(sort)) {
      sort = "ttm.training_member_user_code";
      order = "DESC";
    }
    return super.findPageEasyui(FIND_TRAINING_MEMBER_List_BY_TRAINING_CAMP_ID, trainingMemberParam,
        sort, order, page, rows);
  }
  
  /**
   * 根据当前登录学员的userId去查询训练营成员表t_training_member里面的训练营id
   * 
   * @author felix.yl
   */
  public static final String FIND_TRAINING_MEMBER_LIST_BY_USER_ID = " SELECT"
      + " ttm.training_camp_id"
      + " FROM t_training_member ttm"
      + " LEFT JOIN t_training_camp ttc"
      + " ON ttm.training_camp_id=ttc.key_id"
      + " WHERE ttm.training_member_user_id = :trainingMemberUserId"
      + " AND ttc.is_used=1"
      + " AND ttm.is_used=1"
      + " AND DATE(ttc.training_camp_end_time)>=DATE(:currentDate)";

  /**
   * 查询个人总积分、课件学习分、预约上课分、战队活动分(即该学员的加分总分)
   * 
   * @author felix.yl
   */
  public static final String FIND_TRAINING_MEMBER_SCORE_BY_USER_ID = " SELECT"
      + " ttc.key_id,"
      + " ttm.training_member_total_score,"
      + " ttm.training_member_rsa_score,"
      + " ttm.training_member_course_score,"
      + " IFNULL(SUM(ttmo.training_member_option_score),0) AS trainingMemberOptionScore"
      + " FROM t_training_member ttm"
      + " LEFT JOIN t_training_member_option ttmo"
      + " ON ttm.training_camp_id = ttmo.training_camp_id"
      + " AND ttmo.is_used = 1"
      + " AND ttmo.training_member_option_type = 1"
      + " AND ttm.training_member_user_id = ttmo.training_member_user_id"
      + " LEFT JOIN t_training_camp ttc"
      + " ON ttc.key_id = ttm.training_camp_id"
      + " WHERE ttm.is_used = 1"
      + " AND ttc.is_used = 1"
      + " AND ttm.training_member_user_id = :trainingMemberUserId"
      + " AND DATE(ttc.training_camp_start_time) <= DATE(:currentTime)"
      + " AND DATE(ttc.training_camp_end_time) >= DATE(:currentTime)";

  /**
   * 查询个人积分排行榜在前15名的学员信息
   * 
   * @author felix.yl
   */
  public static final String FIND_MEMBER_INFO_RANKING = " SELECT"
      + " ttm.key_id,"
      + " ttm.training_member_user_id,"
      + " ttm.training_member_pic,"
      + " ttm.training_member_english_name,"
      + " ttm.training_member_total_score"
      + " FROM t_training_member ttm"
      + " WHERE ttm.training_camp_id IN (:keyIds)"
      + " ORDER BY ttm.training_member_total_score DESC"
      + " LIMIT 15";

  /**
   * 查询当前登录学员信息
   * 
   * @author felix.yl
   */
  public static final String FIND_MEMBER_INFO_RANKING_ONESELF = " SELECT"
      + " ttm.key_id,"
      + " ttm.training_member_pic,"
      + " ttm.training_member_english_name,"
      + " ttm.training_member_total_score"
      + " FROM t_training_member ttm"
      + " WHERE ttm.training_camp_id IN (:keyIds)"
      + " AND ttm.training_member_user_id=:user_id";

  /**
   * 查询个人积分大于当前学员个人积分的人数
   * 
   * @author felix.yl
   */
  public static final String FIND_STUDENT_RANKING_HIGE_ONESELF = " SELECT"
      + " COUNT(1) AS 'highNum'"
      + " FROM t_training_member ttm"
      + " WHERE ttm.training_camp_id IN (:keyIds)"
      + " AND ttm.training_member_total_score > :trainingMemberTotalScore";

  /**
   * 查询个人积分小于当前学员个人积分的人数
   * 
   * @author felix.yl
   */
  public static final String FIND_STUDENT_RANKING_LOW_ONESELF = " SELECT"
      + " COUNT(1) AS 'lowNum'"
      + " FROM t_training_member ttm"
      + " WHERE ttm.training_camp_id IN (:keyIds)"
      + " AND ttm.training_member_total_score < :trainingMemberTotalScore";

  /**
   * 查询有效学员的总数
   * 
   * @author felix.yl
   */
  public static final String FIND_STUDENT_RANKING_ALL_EFFECTIVE = " SELECT"
      + " COUNT(1) AS 'totalNum'"
      + " FROM t_training_member ttm"
      + " WHERE ttm.training_camp_id IN (:keyIds)";

  /**
   * 根据训练营id查询成员信息
   * 
   * @author felix.yl
   */
  public static final String FIND_TRAINING_MEMBER_BY_TRAINING_CAMP_ID = " SELECT"
      + " ttm.training_member_pic,"
      + " ttm.training_member_english_name"
      + " FROM t_training_member ttm"
      + " WHERE ttm.training_camp_id = :trainingCampId"
      + " AND ttm.is_used = 1"
      + " LIMIT :start,:end";

  /**
   * 
   * Title: 根据当前登录学员的userId去查询训练营成员表t_training_member里面的训练营id<br>
   * Description: 根据当前登录学员的userId去查询训练营成员表t_training_member里面的训练营id<br>
   * CreateDate: 2017年8月8日 下午2:12:08<br>
   * 
   * @category 根据当前登录学员的userId去查询训练营成员表t_training_member里面的训练营id
   * @author felix.yl
   * @param userId
   * @return
   * @throws Exception
   */
  public TrainingMember findTrainingMemberListByUserIdUsingMemcached(
      String trainingMemberUserId,
      Date currentDate)
          throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("trainingMemberUserId", trainingMemberUserId);
    paramMap.put("currentDate", currentDate);

    // Tom说一个学员只能同时在一个有效的训练营中
    List<TrainingMember> trainingMemberList = super.findList(FIND_TRAINING_MEMBER_LIST_BY_USER_ID,
        paramMap);
    if (trainingMemberList.size() > 0) {
      return trainingMemberList.get(0);
    } else {
      return null;
    }

  }

  /**
   * 
   * Title: 查询个人总积分、课件学习分、预约上课分、战队活动分(即该学员的加分总分)<br>
   * Description: 查询个人总积分、课件学习分、预约上课分、战队活动分(即该学员的加分总分)<br>
   * CreateDate: 2017年8月8日 下午7:55:07<br>
   * 
   * @category 查询个人总积分、课件学习分、预约上课分、战队活动分(即该学员的加分总分)
   * @author felix.yl
   * @param trainingMemberUserId
   * @param currentTime
   * @return
   * @throws Exception
   */
  public TrainingCampIntegralProfileParam findTrainingMemberScoreByUserId(
      String trainingMemberUserId, Date currentTime)
          throws Exception {
    TrainingCampIntegralProfileParam paramObj = new TrainingCampIntegralProfileParam();
    paramObj.setTrainingMemberUserId(trainingMemberUserId);
    paramObj.setCurrentTime(currentTime);
    return super.findOne(FIND_TRAINING_MEMBER_SCORE_BY_USER_ID, paramObj);
  }

  /**
   * 
   * Title: 查询个人积分排行榜在前15名的学员信息<br>
   * Description: 查询个人积分排行榜在前15名的学员信息<br>
   * CreateDate: 2017年8月9日 上午11:49:28<br>
   * 
   * @category 查询个人积分排行榜在前15名的学员信息
   * @author felix.yl
   * @param keyIds
   * @return
   * @throws Exception
   */
  public List<TrainingMember> findTrainingMemberRankingBykeyId(List<String> keyIds)
      throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("keyIds", keyIds);
    return super.findList(FIND_MEMBER_INFO_RANKING, paramMap);
  }

  /**
   * 
   * Title: 查询当前登录学员信息(个人积分排行榜用)<br>
   * Description: 查询当前登录学员信息(个人积分排行榜用)<br>
   * CreateDate: 2017年8月9日 上午11:49:28<br>
   * 
   * @category 查询当前登录学员信息(个人积分排行榜用)
   * @author felix.yl
   * @param keyIds
   * @return
   * @throws Exception
   */
  public List<TrainingMember> findTrainingMemberRankingOneselfBykeyId(List<String> keyIds,
      String user_id)
          throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("keyIds", keyIds);
    paramMap.put("user_id", user_id);
    return super.findList(FIND_MEMBER_INFO_RANKING_ONESELF, paramMap);
  }

  /**
   * 
   * Title: 查询个人积分大于当前学员个人积分的人数<br>
   * Description: 查询个人积分大于当前学员个人积分的人数<br>
   * CreateDate: 2017年8月9日 上午11:49:28<br>
   * 
   * @category 查询个人积分大于当前学员个人积分的人数
   * @author felix.yl
   * @param keyIds
   * @return
   * @throws Exception
   */
  public TrainingMemberTotalParam findStudentHighRanking(List<String> keyIds,
      Integer trainingMemberTotalScore)
          throws Exception {
    TrainingMemberTotalParam paramObj = new TrainingMemberTotalParam();
    paramObj.setKeyIds(keyIds);
    paramObj.setTrainingMemberTotalScore(trainingMemberTotalScore);
    return super.findOne(FIND_STUDENT_RANKING_HIGE_ONESELF, paramObj);
  }

  /**
   * 
   * Title: 查询个人积分小于当前学员个人积分的人数<br>
   * Description: 查询个人积分小于当前学员个人积分的人数<br>
   * CreateDate: 2017年8月9日 上午11:49:28<br>
   * 
   * @category 查询个人积分小于当前学员个人积分的人数
   * @author felix.yl
   * @param keyIds
   * @return
   * @throws Exception
   */
  public TrainingMemberTotalParam findStudentLowRanking(List<String> keyIds,
      Integer trainingMemberTotalScore)
          throws Exception {
    TrainingMemberTotalParam paramObj = new TrainingMemberTotalParam();
    paramObj.setKeyIds(keyIds);
    paramObj.setTrainingMemberTotalScore(trainingMemberTotalScore);
    return super.findOne(FIND_STUDENT_RANKING_LOW_ONESELF, paramObj);
  }

  /**
   * 
   * Title: 查询个人积分小于当前学员个人积分的人数<br>
   * Description: 查询个人积分小于当前学员个人积分的人数<br>
   * CreateDate: 2017年8月9日 上午11:49:28<br>
   * 
   * @category 查询个人积分小于当前学员个人积分的人数
   * @author felix.yl
   * @param keyIds
   * @return
   * @throws Exception
   */
  public TrainingMemberTotalParam findStudentAllRanking(List<String> keyIds)
      throws Exception {
    TrainingMemberTotalParam paramObj = new TrainingMemberTotalParam();
    paramObj.setKeyIds(keyIds);
    return super.findOne(FIND_STUDENT_RANKING_ALL_EFFECTIVE, paramObj);
  }

  /**
   * 
   * Title: 根据训练营id查询成员信息<br>
   * Description: 根据训练营id查询成员信息<br>
   * CreateDate: 2017年8月9日 下午5:28:40<br>
   * 
   * @category 根据训练营id查询成员信息
   * @author felix.yl
   * @param trainingCampId
   * @return
   * @throws Exception
   */
  public List<TrainingMember> findTrainingMemberByTrainingCampIdUsingMemcached(
      String trainingCampId,
      int start, int end)
          throws Exception {
    Map paramMap = new HashMap();
    paramMap.put("trainingCampId", trainingCampId);
    paramMap.put("start", start);
    paramMap.put("end", end);
    return super.findList(FIND_TRAINING_MEMBER_BY_TRAINING_CAMP_ID, paramMap);
  }
}