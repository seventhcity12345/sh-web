package com.webi.hwj.coursetype.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.coursetype.entity.CourseType;

@Repository
public class CourseTypeDao extends BaseEntityDao<CourseType> {

  /**
   * 查询全部课程类型数据
   * 
   * @author komi.zsy
   */
  public final static String FIND_COURSE_TYPE_LIST =
      "SELECT key_id,course_type,course_type_chinese_name,course_type_flag,course_type_is_teacher_comment,"
          + "course_type_english_name,course_type_duration,course_type_limit_number,course_type_before_goclass_time,"
          + "course_type_after_goclass_time,course_type_subscribe_time,course_type_cancel_subscribe_time,course_type_subscribe_rules,"
          + "course_type_cancel_subscribe_rules,course_type_duration_rules"
          + " FROM `t_course_type`"
          + " WHERE is_used <> 0";

  /**
   * Title: 查询列表<br>
   * Description: 查询列表<br>
   * CreateDate: 2016年8月24日 上午11:21:15<br>
   * 
   * @category 查询列表
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<CourseType> findList() throws Exception {
    return super.findList(FIND_COURSE_TYPE_LIST, new CourseType());
  }

  /**
   * 
   * Title: 根据条件查询列表<br>
   * Description: 根据条件查询列表<br>
   * CreateDate: 2016年8月29日 上午10:00:26<br>
   * 
   * @category 根据条件查询列表
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<CourseType> findListByParam(CourseType paramObj) throws Exception {
    return super.findList(paramObj, "course_type,course_type_chinese_name");
  }
}