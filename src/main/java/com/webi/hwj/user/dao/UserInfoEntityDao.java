package com.webi.hwj.user.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.user.entity.UserInfo;
import com.webi.hwj.user.param.UserInfoForOrderDetailParam;

@Repository
public class UserInfoEntityDao extends BaseEntityDao<UserInfo> {
  private static Logger logger = Logger.getLogger(UserInfoEntityDao.class);

  /**
   * 查找用户信息
   */
  private static final String FIND_USER_INFO =
      "SELECT tui.english_name,tui.idcard,tui.real_name,contract_func,tu.user_photo,"
          + " tu.phone FROM `t_user_info` tui"
          + " LEFT JOIN t_user tu ON tu.`key_id` = tui.`key_id`"
          + " WHERE tu.`is_used` = 1 AND tui.`is_used` = 1  AND tui.`key_id` = :keyId";

  /**
   * Title: 根据用户手机号或者keyid查找用户信息<br>
   * Description: findUserByUserIdOrPhone<br>
   * CreateDate: 2016年8月8日 下午3:45:30<br>
   * 
   * @category 根据用户手机号或者keyid查找用户信息
   * @author yangmh
   * @param userIdOrPhone
   * @return
   * @throws Exception
   */
  public UserInfo findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId,
        "key_id,idcard,english_name,real_name,gender,province,city,district,address,learn_tool,personal_sign,email,contract_func");
  }

  /**
   * Title: 通过id查询用户信息<br>
   * Description: findUserInfoByKeyId<br>
   * CreateDate: 2016年11月11日 下午6:01:42<br>
   * 
   * @category 通过id查询用户信息
   * @author komi.zsy
   * @param keyId
   *          用户id
   */
  public UserInfoForOrderDetailParam findUserInfoByKeyId(String keyId) throws Exception {
    UserInfoForOrderDetailParam paramObj = new UserInfoForOrderDetailParam();
    paramObj.setKeyId(keyId);
    return super.findOne(FIND_USER_INFO, paramObj);
  }

}