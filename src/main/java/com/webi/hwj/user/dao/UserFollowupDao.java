package com.webi.hwj.user.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.user.entity.UserFollowup;
import com.webi.hwj.user.param.FindUserFollowupParam;

@Repository
public class UserFollowupDao extends BaseEntityDao<UserFollowup> {
  private static Logger logger = Logger.getLogger(UserFollowupDao.class);

  /**
   * 查找follow信息根据学员信息
   */
  private static final String FIND_FOLLOWUP_BY_USER_IDS = " SELECT user_id,create_date FROM t_user_followup WHERE user_id IN (:userIds) AND is_used = 1 ";

  /**
   * 
   * Title: 查找follow信息根据学员信息<br>
   * Description: 查找follow信息根据学员信息<br>
   * CreateDate: 2016年7月12日 下午7:49:51<br>
   * 
   * @category 查找follow信息根据学员信息
   * @author seven.gz
   * @param userIds
   * @return
   * @throws Exception
   */
  public List<FindUserFollowupParam> findFollowupByUserIds(List<String> userIds) throws Exception {
    FindUserFollowupParam findUserFollowupParam = new FindUserFollowupParam();
    findUserFollowupParam.setUserIds(userIds);
    return super.findList(FIND_FOLLOWUP_BY_USER_IDS, findUserFollowupParam);
  }

}