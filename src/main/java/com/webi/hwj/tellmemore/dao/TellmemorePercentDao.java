package com.webi.hwj.tellmemore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class TellmemorePercentDao extends BaseMysqlDao {
  public TellmemorePercentDao() {
    super.setTableName("t_tellmemore_percent");
  }

  private final String UPDAE_TMM_PERCENT = "update t_tellmemore_percent set "
      + " tmm_percent = :tmm_percent , tmm_workingtime = :tmm_workingtime , tmm_correct = :tmm_correct "
      + " where user_id = :user_id and course_title = :course_title ";

  /*
   * 查找所有是学员的tmm课程信息
   */
  private final String SELECT_ALL_TMM_USER_INFO =
      "SELECT user_id ,tmm_percent,tmm_correct,tmm_workingtime"
          + " FROM t_tellmemore_percent tmm LEFT JOIN t_user u ON u.key_id = tmm.user_id "
          + " WHERE u.is_used <>0 AND tmm.is_used <>0  AND u.is_student <>0 "
          + " ORDER BY user_id";

  /*
   * 查找所有是学员的tmm课程信息
   */
  private static final String SELECT_ALL_TMM_USER_INFO_BY_USERID =
      " SELECT user_id ,tmm_percent,tmm_correct,tmm_workingtime "
          + " FROM t_tellmemore_percent tmm LEFT JOIN t_user u ON u.key_id = tmm.user_id "
          + " WHERE u.is_used <>0 AND tmm.is_used <>0  AND u.is_student <>0  AND tmm.user_id = :user_id ";

  /**
   * 查询出所有没做过课件的学员
   */

  private static final String FIND_NEVER_DONE_RSA_STUDENT =
      " SELECT user_id,SUM(tmm_percent) AS total_percent "
          + " FROM t_tellmemore_percent WHERE is_used = 1 "
          + " GROUP BY user_id HAVING total_percent = 0 ";

  /**
   * 删除该学员所有数据
   */
  private final String DELETE_ALL_BY_USERID =
      "update t_tellmemore_percent set is_used = 0 where user_id = :userId";

  /**
   * 查询所有课件学习总时长(基本上可以认为是即时的,学员端刷新一次,就会从RSA那边拉一次数据)
   * 
   * @author felix.yl
   */
  private final String SELECT_TMM_USER_INFO_BY_USERID = " SELECT"
      + " ttp.tmm_workingtime "
      + " FROM t_tellmemore_percent ttp"
      + " WHERE ttp.user_id =:user_id"
      + " AND ttp.is_used=1";

  /**
   * 查询昨天以前的课件学习总时长(每天凌晨定时任务刷新,所以说可以认为今天白天的学习时间还没有加进去)
   * 
   * @author felix.yl
   */
  private final String SELECT_YESTERDAY_TMM_USER_INFO_BY_USERID = " SELECT"
      + " tstd.total_tmm_workingtime"
      + " FROM t_statistics_tellmemore_day tstd"
      + " WHERE tstd.user_id=:user_id"
      + " AND tstd.is_used=1"
      + " ORDER BY tstd.create_date DESC"
      + " LIMIT 1";

  /**
   * Title: 删除该学员所有数据<br>
   * Description: 删除该学员所有数据<br>
   * CreateDate: 2016年8月23日 下午6:24:42<br>
   * 
   * @category 删除该学员所有数据
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public int deleteAllByUserId(String userId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("userId", userId);
    return super.update(DELETE_ALL_BY_USERID, paramMap);
  }

  /**
   * Title: 更新tmm用户百分比<br>
   * Description: 更新tmm用户百分比<br>
   * CreateDate: 2015年10月21日 下午7:31:54<br>
   * 
   * @category 更新tmm用户百分比
   * @author yangmh
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int updatePercent(Map<String, Object> paramMap) throws Exception {
    return super.update(UPDAE_TMM_PERCENT, paramMap);
  }

  /**
   * Title: 查找所有tmm学员课程三维信息<br>
   * Description: findAllTmmUserInfoList<br>
   * CreateDate: 2016年4月15日 下午1:21:54<br>
   * 
   * @category findAllTmmUserInfoList
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findAllTmmUserInfoList() throws Exception {
    return super.findList(SELECT_ALL_TMM_USER_INFO, null);
  }

  /**
   * Title: 根据userId查找tmm学员课程三维信息<br>
   * Description: 根据userId查找tmm学员课程三维信息<br>
   * 
   * @category 根据userId查找tmm学员课程三维信息 CreateDate: 2016年4月15日 下午1:21:54<br>
   * @author seven.gz
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findTmmUserInfoListByKeyId(String userId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", userId);
    return super.findList(SELECT_ALL_TMM_USER_INFO_BY_USERID, paramMap);
  }

  /**
   * 
   * Title: 查询从没有做过课件的学生<br>
   * Description: 查询从没有做过课件的学生<br>
   * CreateDate: 2016年7月11日 下午5:20:28<br>
   * 
   * @category 查询从没有做过课件的学生
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findNeverDoneRsaStudent() throws Exception {
    return super.findList(FIND_NEVER_DONE_RSA_STUDENT, null);
  }

  /**
   * 
   * Title: 获取用户总的学习时间<br>
   * Description: 获取用户总的学习时间<br>
   * CreateDate: 2017年8月9日 上午10:22:51<br>
   * 
   * @category 获取用户总的学习时间
   * @author felix.yl
   * @param userId
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findTmmAllInfoListByKeyId(String userId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", userId);
    return super.findList(SELECT_TMM_USER_INFO_BY_USERID, paramMap);
  }

  /**
   * 
   * Title: 查询昨天以前的课件学习总时长<br>
   * Description: 查询昨天以前的课件学习总时长<br>
   * CreateDate: 2017年8月8日 下午9:02:14<br>
   * 
   * @category 查询昨天以前的课件学习总时长
   * @author felix.yl
   * @param userId
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findTmmYesterdayUserInfoListByKeyId(String userId)
      throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", userId);
    return super.findList(SELECT_YESTERDAY_TMM_USER_INFO_BY_USERID, paramMap);
  }

}