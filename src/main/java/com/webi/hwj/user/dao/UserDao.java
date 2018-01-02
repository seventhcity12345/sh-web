package com.webi.hwj.user.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;
import com.webi.hwj.user.util.CompletePercentUtil;

@Repository
public class UserDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(UserDao.class);

  public UserDao() {
    super.setTableName("t_user");
  }

  // 页面中 上传图片时候input 的name属性values
  public static final String UPLOAD_FIELD_NAME = "file";

  // 保存图片到aliyun文件服务器上的根路径
  public static final String ALIYUN_IMAGE_PATH = "images/user/";

  public static final String UPDATE_USER_PHOTO_AND_LARGEPHOTO = " UPDATE t_user "
      + " SET user_photo = :user_photo, user_photo_large = :user_photo_large "
      + " WHERE is_used <> 0 "
      + " AND key_id = :key_id ";

  /**
   * 更新用户测试级别
   */
  public static final String UPDATE_USER_TESTLEVEL = " UPDATE t_user "
      + " SET test_level = :test_level "
      + " WHERE is_used <> 0 "
      + " AND key_id = :user_id ";

  /**
   * 更新用户的info信息
   */
  public static final String UPDATE_USERINFO_BY_USERID = " UPDATE t_user_info "
      + " SET idcard = :idcard, english_name = :english_name, real_name = :real_name, "
      + " gender = :gender, province = :province, city = :city, district = :district, "
      + " address = :address, learn_tool = :learn_tool, personal_sign = :personal_sign, "
      + " email = :email, contract_func = :contract_func "
      + " WHERE is_used <> 0 "
      + " AND key_id = :user_id ";

  /**
   * 更新use主表中的user_name
   */
  public static final String UPDATE_USERNAME_BY_USERID = " UPDATE t_user "
      + " SET user_name = :english_name "
      + " WHERE is_used <> 0 "
      + " AND key_id = :user_id ";

  /**
   * t_user_info表中user_id记录
   */
  private static final String FIND_USERINFO_BY_USERID = " SELECT key_id, idcard, english_name, real_name, "
      + " gender, province, city, district, address, learn_tool, personal_sign, email, contract_func "
      + " FROM t_user_info"
      + " WHERE is_used <> 0"
      + " AND key_id = :user_id ";

  /**
   * 任务模块 判断用户是否完成子 资料完成度任务：条件 info_complete_percent = 100
   * 
   * athrun.cw
   */
  public static final String FIND_USER_COMPLETE_PERCENT_100_EXIST_BY_USERID = " SELECT count(1) "
      + " FROM t_user "
      + " WHERE is_used <> 0 "
      + " AND key_id = :user_id "
      + " AND info_complete_percent = 100";

  /**
   * 
   * Title: findUserInfoByUserId<br>
   * Description: t_user_info表中user_id记录<br>
   * CreateDate: 2015年10月21日 上午10:51:23<br>
   * 
   * @category t_user_info表中user_id记录
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findUserInfoByUserId(Map<String, Object> paramMap) throws Exception {
    return super.findOne(FIND_USERINFO_BY_USERID, paramMap);
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
  public void updateCompletePercent(Map<String, Object> userInfoMap) throws Exception {
    Map<String, Object> userMap = new HashMap<String, Object>();
    userMap.put("key_id", userInfoMap.get("key_id"));
    userMap.put("info_complete_percent", CompletePercentUtil.getCompletePercent(userInfoMap));
    super.update(userMap);
  }
}