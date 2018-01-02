package com.webi.hwj.course.dao;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class CourseDao extends BaseMysqlDao {
  /**
   * 用户与课程类型的关联关系表
   */
  public static final String TABLE_TRE_CATRGORY_USER = "tre_category_user";
}