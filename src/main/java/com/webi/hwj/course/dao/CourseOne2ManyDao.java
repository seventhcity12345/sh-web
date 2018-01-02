package com.webi.hwj.course.dao;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

/**
 * 一对N DAO
 * 
 * Title: CourseOne2ManyDao<br>
 * Description: CourseOne2ManyDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月24日 上午11:13:01
 * 
 * @author Woody
 */
@Repository
public class CourseOne2ManyDao extends BaseMysqlDao {
  public CourseOne2ManyDao() {
    super.setTableName("t_course_one2many");
  }
}