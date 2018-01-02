package com.webi.hwj.subscribecourse.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class SubscribeSupplementDao extends BaseMysqlDao {
  public SubscribeSupplementDao() {
    super.setTableName("t_subscribe_supplement");
  }

  private static final String UPDATE_SUBSCRIBESUPPLEMENT = " UPDATE t_subscribe_supplement "
      + " SET is_supplement= :is_supplement, "
      + " to_subscribe_course_id = :to_subscribe_course_id "
      + " WHERE is_used <> 0 "
      + " AND key_id = :key_id ";

  /**
   * 找到 最新的 已经补过课的 补课记录，然后 在小包课取消预约的时候，重新标识 ：没有补过课
   */
  private static final String FIND_LAST_SUBSCRIBESUPPLEMENT = " SELECT key_id "
      + " FROM t_subscribe_supplement "
      + " WHERE is_used <> 0 "
      + " AND to_subscribe_course_id = :to_subscribe_course_id"
      + " ORDER BY create_date ";

  /**
   * 找学员 有多少有效的补课
   */
  public static final String FIND_SUPPELMENT_COURSE_COUNT = " SELECT count(1)"
      + " FROM t_subscribe_supplement "
      + " WHERE is_used <> 0 "
      + " AND user_id = :user_id";

  /**
   * 
   * Title: 找学员 有多少有效的补课<br>
   * Description: findSupplementCourseCount<br>
   * CreateDate: 2016年2月25日 下午2:12:22<br>
   * 
   * @category 找学员 有多少有效的补课
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int findSupplementCourseCount(Map<String, Object> paramMap) throws Exception {
    return super.findCount(FIND_SUPPELMENT_COURSE_COUNT, paramMap);
  }

  /**
   * .
   * 
   * Title: （1对1取消预约时候）更新用户补课表数据<br>
   * Description: updateSubscribeSupplement<br>
   * CreateDate: 2015年12月7日 下午5:20:09<br>
   * 
   * @category （1对1取消预约时候）更新用户补课表数据
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int updateSubscribeSupplement(Map<String, Object> paramMap) throws Exception {
    return super.update(UPDATE_SUBSCRIBESUPPLEMENT, paramMap);
  }

  /**
   * Title: 找到 最新的 已经补过课的 补课记录，然后 在小包课取消预约的时候，重新标识 ：没有补过课<br>
   * Description: findSubscribeSupplement<br>
   * CreateDate: 2015年12月7日 下午4:36:34<br>
   * 
   * @category 找到 最新的 已经补过课的 补课记录，然后 在小包课取消预约的时候，重新标识 ：没有补过课
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findLastSubscribeSupplement(Map<String, Object> paramMap)
      throws Exception {
    return super.findOne(FIND_LAST_SUBSCRIBESUPPLEMENT, paramMap);
  }
}