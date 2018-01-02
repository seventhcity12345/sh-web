package com.webi.hwj.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.admin.entity.BadminUser;

@Repository
public class AdminBdminUserEntityDao extends BaseEntityDao<BadminUser> {
  private static Logger logger = Logger.getLogger(AdminBdminUserEntityDao.class);
  /**
   * 根据角色id查找管理员
   * 
   * @author komi.zsy
   */
  private final static String FIND_ADMIN_USER_LIST_BY_ROLEID = "SELECT key_id, account"
      + " FROM t_badmin_user"
      + " WHERE role_id IN (:roleIds)"
      + " AND is_used = 1";

  /**
   * Title: 根据角色id查找管理员<br>
   * Description: 根据角色id查找管理员<br>
   * CreateDate: 2017年3月2日 上午11:37:05<br>
   * 
   * @category 根据角色id查找管理员
   * @author komi.zsy
   * @param roleIds
   *          角色id
   * @return
   * @throws Exception
   */
  public List<BadminUser> findAdminUserListByRoleId(List<String> roleIdList) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("roleIds", roleIdList);
    return super.findList(FIND_ADMIN_USER_LIST_BY_ROLEID, paramMap);
  }

  /**
   * 
   * Title: 根据keyId查询<br>
   * Description: findCcIdByUserId<br>
   * CreateDate: 2017年7月5日 下午1:00:18<br>
   * 
   * @category 根据keyId查询
   * @author felix.yl
   * @param userId
   * @return
   * @throws Exception
   */
  public BadminUser findAdminUserInfoByUserId(String keyId)
      throws Exception {
    return super.findOneByKeyId(keyId, "admin_user_name,telphone,weixin");
  }
}