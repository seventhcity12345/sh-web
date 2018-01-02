package com.webi.hwj.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class AdminBdminUserDao extends BaseMysqlDao {

  public AdminBdminUserDao() {
    super.setTableName("t_badmin_user");
  }

  /**
   * @category 通过user_id查询角色列表
   * @param roleId
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findUserRoles(String user_id) throws Exception {
    String sql = "select * from tre_user_role where user_id = :user_id ";
    Map paramMap = new HashMap();
    paramMap.put("user_id", user_id);
    return super.findList(sql, paramMap);
  }
  
  /**
   * @author komi.zsy
   * 根据keyid更新管理员信息
   */
  private static final String UPDATE_ADMIN_INFO_BY_KEYID = "UPDATE t_badmin_user"
      + " SET account = :account ,email = :email,admin_user_photo = :admin_user_photo,"
      + "admin_user_name = :admin_user_name,employee_number = :employee_number,"
      + "telphone = :telphone,weixin = :weixin"
      + " WHERE is_used = 1 AND key_id = :key_id ";
  
  /**
   * @author komi.zsy
   * 根据keyid更新管理员信息
   * 密码也改
   */
  private static final String UPDATE_ADMIN_INFO_AND_PWD_BY_KEYID = "UPDATE t_badmin_user"
      + " SET account = :account ,email = :email,pwd= :pwd,admin_user_photo = :admin_user_photo,"
      + "admin_user_name = :admin_user_name,employee_number = :employee_number,"
      + "telphone = :telphone,weixin = :weixin"
      + " WHERE is_used = 1 AND key_id = :key_id ";
  
  /**
   * Title: 根据keyid更新管理员信息<br>
   * Description: 因为pwd要求<br>
   * CreateDate: 2017年7月11日 下午1:57:29<br>
   * @category 根据keyid更新管理员信息 
   * @author komi.zsy
   * @param badminUser 管理员信息
   * @param isUpdatePwd 是否更新密码，true更新
   * @return
   * @throws Exception
   */
  public int updateAdminInfoByKeyId(Map<String, Object> badminUser,boolean isUpdatePwd) throws Exception{
    if(isUpdatePwd){
      return super.update(UPDATE_ADMIN_INFO_AND_PWD_BY_KEYID, badminUser);
    }
    else{
      return super.update(UPDATE_ADMIN_INFO_BY_KEYID, badminUser);
    }
  }

  /**
   * 
   * Title: 根据id查找管理员名字<br>
   * Description: 根据id查找管理员名字<br>
   * CreateDate: 2016年3月25日 下午2:10:54<br>
   * 
   * @category 根据id查找管理员名字
   * @author komi.zsy
   * @param key_id
   * @return
   * @throws Exception
   */
  public Map<String, Object> findBAdminName(String key_id) throws Exception {
    String sql = "select admin_user_name from t_badmin_user where key_id = :key_id ";
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("key_id", key_id);
    return super.findOne(sql, paramMap);
  }

  /**
   * @category 新增中间表数据
   * @param paramMap
   * @throws Exception
   */
  public void submitUserRoles(Map paramMap) throws Exception {
    super.update(paramMap);
  }

  /**
   * Title: 根据账号查询<br>
   * Description: findOneByAccount<br>
   * CreateDate: 2017年5月23日 下午5:32:10<br>
   * 
   * @category 根据账号查询
   * @author seven.gz
   * @param account
   *          账号
   */
  public Map<String, Object> findOneByAccount(String account) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("account", account);
    return super.findOne(paramMap, "key_id");
  }
}