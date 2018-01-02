package com.webi.hwj.trainingcamp.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.trainingcamp.entity.TrainingMemberOption;
import com.webi.hwj.trainingcamp.param.UserNotInTrainingMemberParam;

@Repository
public class TrainingMemberOptionEntityDao extends BaseEntityDao<TrainingMemberOption> {
  private static Logger logger = Logger.getLogger(TrainingMemberOptionEntityDao.class);
  
  private final static String DELETE_MEMBER_OPTION_BY_CAMP_ID_AND_USERIDS = "UPDATE t_training_member_option"
      + " SET is_used = 0,update_user_id= :updateUserId"
      + " WHERE training_camp_id = :keyId AND training_member_user_id IN (:keyIds)"
      + " AND is_used = 1";
  
  /**
   * Title: 删除训练营成员加分减分项<br>
   * Description: 删除训练营成员加分减分项<br>
   * CreateDate: 2017年9月13日 上午11:22:02<br>
   * @category 删除训练营成员 加分减分项
   * @author komi.zsy
   * @param trainingCampId 训练营id
   * @param userIds 用户ids
   * @return
   * @throws Exception
   */
  public int deleteMemberOptionByCampIsAndUserIds(String trainingCampId,List<String> userIds,String updateUserId) throws Exception{
    UserNotInTrainingMemberParam paramObj = new UserNotInTrainingMemberParam();
    paramObj.setKeyId(trainingCampId);
    paramObj.setKeyIds(userIds);
    paramObj.setUpdateUserId(updateUserId);
    return super.update(DELETE_MEMBER_OPTION_BY_CAMP_ID_AND_USERIDS, paramObj);
  }

}