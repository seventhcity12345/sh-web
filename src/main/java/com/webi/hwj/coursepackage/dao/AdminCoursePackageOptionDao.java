package com.webi.hwj.coursepackage.dao;

import org.springframework.stereotype.Repository;
import com.mingyisoft.javabase.dao.BaseMysqlDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

@Repository
public class AdminCoursePackageOptionDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(AdminCoursePackageOptionDao.class);

  public AdminCoursePackageOptionDao() {
    super.setTableName("t_course_package_option");
  }

  /**
   * Title: English Studio & Rsa & 微课单价之和<br>
   * Description: English Studio & Rsa & 微课单价之和<br>
   * CreateDate: 2016年5月18日 下午2:21:51<br>
   * 
   * @category English Studio & Rsa & 微课单价之和
   * @author ivan.mgh
   * @return
   * @throws Exception
   */
  public Integer sumEnglishStudioRsaWeikeUnitPrice() throws Exception {
    // String sql = "SELECT a.course_type course_type,MAX(a.real_price)
    // sum_real_price FROM `t_course_package_option` a WHERE a.course_type IN
    // ('course_type6','course_type7','course_type8') GROUP BY a.course_type";
    // Map<String, Object> paramMap = new HashMap<>();
    // List<Map<String, Object>> list = findList(sql, paramMap);
    //
    //
    // if (null != list && !list.isEmpty()) {
    // for (Map<String, Object> map : list) {
    // sum += Integer.valueOf(map.get("sum_real_price").toString());
    // }
    // }
    int sum = 0;

    return sum;
  }

}
