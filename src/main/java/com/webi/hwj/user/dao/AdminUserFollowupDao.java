package com.webi.hwj.user.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class AdminUserFollowupDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(AdminUserFollowupDao.class);

  public AdminUserFollowupDao() {
    super.setTableName("t_user_followup");
  }
}