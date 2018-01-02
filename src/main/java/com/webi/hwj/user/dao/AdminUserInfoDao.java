package com.webi.hwj.user.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class AdminUserInfoDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(AdminUserInfoDao.class);

  /**
   * 更新用户的info信息
   */
  public static final String UPDATE_USERINFO_BY_USERID_OR_ADMIN = " UPDATE t_user_info "
      + " SET idcard = :idcard, english_name = :english_name, real_name = :real_name, "
      + " gender = :gender, province = :province, city = :city, district = :district, "
      + " address = :address, learn_tool = :learn_tool, personal_sign = :personal_sign, "
      + " email = :email, contract_func = :contract_func , update_user_id = :update_user_id"
      + " WHERE is_used <> 0 "
      + " AND key_id = :key_id ";

  /**
   * 更新use主表中的user_name
   */
  public static final String UPDATE_USERNAME_BY_USERID_OR_ADMIN = " UPDATE t_user "
      + " SET user_name = :english_name "
      + " WHERE is_used <> 0 "
      + " AND key_id = :key_id ";

  /**
   * 通过主键联查t_user和t_user_info表
   */
  public static final String FIND_USER_AND_USERINFO_BY_KEY_ID = "SELECT ui.* ,u.`info_complete_percent`,u.phone "
      + "FROM t_user_info ui "
      + "LEFT JOIN  t_user u ON u.key_id = ui.key_id "
      + "WHERE ui.key_id = :key_id";

  public AdminUserInfoDao() {
    super.setTableName("t_user_info");
  }

  public Map<String, Object> findUserInfoAndUserByUserId(Map<String, Object> paramMap)
      throws Exception {
    return super.findOne(FIND_USER_AND_USERINFO_BY_KEY_ID, paramMap);
  }
}