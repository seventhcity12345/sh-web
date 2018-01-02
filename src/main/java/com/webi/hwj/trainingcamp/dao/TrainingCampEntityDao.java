package com.webi.hwj.trainingcamp.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.trainingcamp.entity.TrainingCamp;
import com.webi.hwj.trainingcamp.param.AdminTrainingCampParam;
import com.webi.hwj.trainingcamp.param.TrainingCampParam;

@Repository
public class TrainingCampEntityDao extends BaseEntityDao<TrainingCamp> {
  private static Logger logger = Logger.getLogger(TrainingCampEntityDao.class);

  /**
   * 查询所有训练营列表
   * 
   * @author komi.zsy
   */
  private final static String FIND_TRAINING_CAMP_LIST =
      "SELECT ttc.key_id,training_camp_title,training_camp_desc,"
          + "training_camp_num,training_camp_pic,training_camp_start_time,training_camp_end_time,"
          + "training_camp_average_score,account,"
          + "ttc.update_date"
          + " FROM t_training_camp ttc"
          + " LEFT JOIN t_badmin_user tbu"
          + " ON tbu.key_id = ttc.training_camp_learning_coach_id"
          + " WHERE ttc.is_used = 1";

  /**
   * Title: 查询所有训练营列表<br>
   * Description: 查询所有训练营列表<br>
   * CreateDate: 2017年8月8日 下午4:46:12<br>
   * 
   * @category 查询所有训练营列表
   * @author komi.zsy
   * @param cons
   *          组合查询
   * @param sort
   * @param order
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(String cons, String sort, String order, Integer page,
      Integer rows) throws Exception {
    AdminTrainingCampParam trainingCampParam = new AdminTrainingCampParam();
    trainingCampParam.setCons(cons);
    if (StringUtils.isEmpty(sort)) {
      sort = "ttc.update_date";
      order = "DESC";
    }
    return super.findPageEasyui(FIND_TRAINING_CAMP_LIST, trainingCampParam, sort, order, page,
        rows);
  }

  /**
   * 根据训练营id，查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比
   * 
   * @author felix.yl
   */
  private static final String FIND_TRAINING_CAMP_INFO_BY_TRAININGCAMPIDS = " SELECT"
      + " ttc.training_camp_title,"
      + " ttc.training_camp_start_time,"
      + " ttc.training_camp_end_time,"
      + " ttc.training_camp_desc,"
      + " ttc.training_camp_pic,"
      + " tbu.admin_user_name,"
      + " tbu.admin_user_photo,"
      + " ttc.training_camp_average_level,"
      + " ttc.training_camp_yesterday_activity,"
      + " ttc.training_camp_average_age,"
      + " ttc.training_camp_female_present "
      + " FROM t_training_camp ttc"
      + " LEFT JOIN t_badmin_user tbu"
      + " ON ttc.training_camp_learning_coach_id = tbu.key_id"
      + " WHERE ttc.key_id = :trainingCampId"
      + " AND ttc.is_used=1"
      + " AND tbu.is_used=1"
      + " AND DATE(ttc.training_camp_start_time) <= DATE(:currentTime)"
      + " AND DATE(ttc.training_camp_end_time) >= DATE(:currentTime)";

  /**
   * 查询训练营平均分最大的训练营(可能有多个训练营并列第一)
   * 
   * @author felix.yl
   */
  private static final String FIND_TRAINING_CAMP_MAX_AVG_SCORE_INFO = " SELECT"
      + " MAX(ttc.training_camp_average_score) AS trainingCampAverageScore,"
      + " ttc.key_id"
      + " FROM t_training_camp ttc"
      + " WHERE ttc.is_used=1";

  /**
   * 查询出所有在有效期内的训练营id
   * 
   * @author felix.yl
   */
  private static final String FIND_TRAINING_CAMP_ID_EFFECTIVE_INFO = " SELECT"
      + " ttc.key_id"
      + " FROM t_training_camp ttc"
      + " WHERE DATE(ttc.training_camp_end_time)>=DATE(:currentTime)"
      + " AND ttc.is_used=1";

  /**
   * 查询当前训练营人数
   * 
   * @author felix.yl
   */
  private static final String FIND_TRAINING_CAMP_NUM_BY_KEYID = " SELECT"
      + " ttc.training_camp_num"
      + " FROM t_training_camp ttc"
      + " WHERE ttc.key_id= :keyId"
      + " AND DATE(ttc.training_camp_end_time)>=DATE(:currentTime)"
      + " AND ttc.is_used=1";

  /**
   * 
   * Title: 根据训练营id，查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比<br>
   * Description: 根据训练营id，查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比<br>
   * CreateDate: 2017年8月8日 下午2:30:36<br>
   * 
   * @category 根据训练营id，查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比
   * @author felix.yl
   * @param trainingCampIds
   * @param currentTime
   * @return
   * @throws Exception
   */
  public TrainingCampParam findTrainingCampInfoUsingMemcached(
      String trainingCampId, Date currentTime) throws Exception {
    TrainingCampParam paramObj = new TrainingCampParam();
    paramObj.setTrainingCampId(trainingCampId);
    paramObj.setCurrentTime(currentTime);
    return super.findOne(FIND_TRAINING_CAMP_INFO_BY_TRAININGCAMPIDS, paramObj);
  }

  /**
   * 
   * Title: 查询训练营平均分最大的训练营(可能有多个训练营并列第一)<br>
   * Description: 查询训练营平均分最大的训练营(可能有多个训练营并列第一)<br>
   * CreateDate: 2017年8月8日 下午8:01:09<br>
   * 
   * @category 查询训练营平均分最大的训练营(可能有多个训练营并列第一)
   * @author felix.yl
   * @return
   * @throws Exception
   */
  public List<TrainingCamp> findTrainingCampMaxAvgScoreInfo() throws Exception {
    TrainingCamp paramObj = new TrainingCamp();
    return super.findList(FIND_TRAINING_CAMP_MAX_AVG_SCORE_INFO, paramObj);
  }

  /**
   * 
   * Title: 查询出所有在有效期内的训练营id<br>
   * Description: 查询出所有在有效期内的训练营id<br>
   * CreateDate: 2017年8月9日 上午11:37:15<br>
   * 
   * @category 查询出所有在有效期内的训练营id
   * @author felix.yl
   * @param currentTime
   * @return
   * @throws Exception
   */
  public List<TrainingCampParam> findTrainingCampIdEffectiveInfo(Date currentTime)
      throws Exception {
    TrainingCampParam paramObj = new TrainingCampParam();
    paramObj.setCurrentTime(currentTime);
    return super.findList(FIND_TRAINING_CAMP_ID_EFFECTIVE_INFO, paramObj);
  }

  /**
   * 
   * Title: 查询出所有在有效期内的训练营id<br>
   * Description: 查询出所有在有效期内的训练营id<br>
   * CreateDate: 2017年8月9日 上午11:37:15<br>
   * 
   * @category 查询出所有在有效期内的训练营id
   * @author felix.yl
   * @param currentTime
   * @return
   * @throws Exception
   */
  public TrainingCampParam findTrainingCampNumUsingMemcached(String keyId, Date currentTime)
      throws Exception {
    TrainingCampParam paramObj = new TrainingCampParam();
    paramObj.setKeyId(keyId);
    paramObj.setCurrentTime(currentTime);
    return super.findOne(FIND_TRAINING_CAMP_NUM_BY_KEYID, paramObj);
  }

}