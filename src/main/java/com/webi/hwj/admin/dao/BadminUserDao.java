package com.webi.hwj.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.admin.entity.BadminUser;
import com.webi.hwj.admin.param.FindBadminUserParam;

@Repository
public class BadminUserDao extends BaseEntityDao<BadminUser> {
  private static Logger logger = Logger.getLogger(BadminUserDao.class);

  /**
   * 根据keyid查询教务信息.
   */
  private static final String FIND_ADMIN_INFO_BY_KEYIDS = " SELECT key_id,admin_user_name "
      + " FROM t_badmin_user "
      + " WHERE is_used = 1 AND key_id IN (:keyIds) ";

  /**
   * 根据keyid查询教务信息.
   */
  private static final String FIND_LIST_BY_EMPLOYEE_NUMBER = " SELECT DISTINCT key_id,admin_user_name "
      + " FROM t_badmin_user "
      + " WHERE is_used = 1 AND employee_number IN (:employeeNoList) ";

  /**
   * Title: 根据keyid查询教务信息<br>
   * Description: 根据keyid查询教务信息<br>
   * CreateDate: 2016年9月19日 下午4:28:38<br>
   * 
   * @category 根据keyid查询教务信息
   * @author seven.gz
   * @param keyIds
   *          要查询的教务id
   * @return List
   * @throws Exception
   *           异常
   */
  public List<FindBadminUserParam> findAdminInfoByKeyIds(List<String> keyIds) throws Exception {
    if (keyIds == null || keyIds.size() == 0) {
      return null;
    } else {
      FindBadminUserParam paramObj = new FindBadminUserParam();
      paramObj.setKeyIds(keyIds);
      return super.findList(FIND_ADMIN_INFO_BY_KEYIDS, paramObj);
    }
  }

  /**
   * 
   * Title: 根据员工id查找<br>
   * Description: findListByEmployeeNumber<br>
   * CreateDate: 2017年1月17日 下午8:50:10<br>
   * 
   * @category 根据员工id查找
   * @author seven.gz
   * @param employeeNoList
   * @return
   * @throws Exception
   */
  public List<BadminUser> findListByEmployeeNumber(List<String> employeeNoList)
      throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("employeeNoList", employeeNoList);
    return super.findList(FIND_LIST_BY_EMPLOYEE_NUMBER, paramMap);
  }

  /**
   * 
   * Title: 根据keyId查询<br>
   * Description: findOneBykeyId<br>
   * CreateDate: 2017年1月18日 下午1:40:41<br>
   * 
   * @category 根据keyId查询
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public BadminUser findOneBykeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "key_id,account,admin_user_name");
  }

}