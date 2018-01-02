package com.webi.hwj.user.entitydao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.user.entity.UserInfo;

@Repository
public class AdminUserInfoEntityDao extends BaseEntityDao<UserInfo> {
  private static Logger logger = Logger.getLogger(AdminUserInfoEntityDao.class);

  public UserInfo findOneRealName(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "key_id,real_name,idcard,english_name");
  }
}