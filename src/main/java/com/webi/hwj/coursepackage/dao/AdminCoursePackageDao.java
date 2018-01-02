package com.webi.hwj.coursepackage.dao;

import org.springframework.stereotype.Repository;
import com.mingyisoft.javabase.dao.BaseMysqlDao;
import org.apache.log4j.Logger;

@Repository
public class AdminCoursePackageDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(AdminCoursePackageDao.class);

  public AdminCoursePackageDao() {
    super.setTableName("t_course_package");
  }
}