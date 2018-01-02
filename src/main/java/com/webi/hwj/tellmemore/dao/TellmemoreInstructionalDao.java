package com.webi.hwj.tellmemore.dao;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class TellmemoreInstructionalDao extends BaseMysqlDao {
  public TellmemoreInstructionalDao() {
    super.setTableName("t_tellmemore_instructional");
  }
}