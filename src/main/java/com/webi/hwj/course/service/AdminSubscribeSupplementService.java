package com.webi.hwj.course.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeSupplementDao;
import com.webi.hwj.user.dao.AdminUserDao;

@Service
public class AdminSubscribeSupplementService {
  private static Logger logger = Logger.getLogger(AdminSubscribeSupplementService.class);
  @Resource
  SubscribeSupplementDao subscribeSupplementDao;

  @Resource
  SubscribeCourseDao subscribeCourseDao;

  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  AdminUserDao adminUserDao;

  /**
   * @category subscribeSupplement 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return subscribeSupplementDao.insert(fields);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findListEasyui(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return subscribeSupplementDao.findListEasyui(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return subscribeSupplementDao.findList(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @param pageNumber
   *          当前页数
   * @param pageSize
   *          每页显示多少条数据
   * @return
   * @throws Exception
   */
  public Page findPage(Map<String, Object> paramMap, String columnName) throws Exception {
    return subscribeSupplementDao.findPage(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @param pageNumber
   *          当前页数
   * @param pageSize
   *          每页显示多少条数据
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(Map<String, Object> paramMap, String columnName) throws Exception {
    return subscribeSupplementDao.findPageEasyui(columnName, paramMap);
  }

  /**
   * @category 修改数据
   * @author mingyisoft代码生成工具
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return subscribeSupplementDao.update(fields);
  }

  /**
   * @category 查询单条数据(通过参数MAP)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return subscribeSupplementDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过key,value)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(String key, String value, String columnName) throws Exception {
    return subscribeSupplementDao.findOne(key, value, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return subscribeSupplementDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return subscribeSupplementDao.delete(ids);
  }

  /**
   * @category 查询数量
   * @author mingyisoft代码生成工具
   * @param sql
   * @param map
   * @return
   * @throws Exception
   */
  public int findCount(Map<String, Object> map) throws Exception {
    return subscribeSupplementDao.findCount(map);
  }

  /**
   * @category 查询总数
   * @author mingyisoft代码生成工具
   * @param map
   * @param sumField
   *          sum的字段
   * @return
   * @throws Exception
   */
  public int findSum(Map<String, Object> map, String sumField) throws Exception {
    return subscribeSupplementDao.findSum(map, sumField);
  }

  /**
   * 
   * Title: 为学生补课<br>
   * Description: subscribeCourseSupplement<br>
   * CreateDate: 2015年12月1日 下午6:26:15<br>
   * 
   * @category 为学生补课
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage subscribeCourseSupplement(Map<String, Object> paramMap) throws Exception {
    JsonMessage json = new JsonMessage();
    /**
     * 1.给对应的合同子表的数据里的剩余课时+1
     */
    // 找到预约该课程所消耗的option 的 id信息
    Map<String, Object> subscribeCourseMap = subscribeCourseDao.findOneByKeyId(
        paramMap.get("supplement_subscribe_id"), "order_id, order_option_id, end_time");

    if (subscribeCourseMap != null) {
      Date end_time = DateUtil
          .strToDateYYYYMMDDHHMMSS(subscribeCourseMap.get("end_time").toString());
      // 判断该课程的end_time<当前时间，才是合法的，否则不能补课
      if (end_time.getTime() < System.currentTimeMillis()) {
        Map<String, Object> updateOrderCourseOptionMap = new HashMap<String, Object>();
        updateOrderCourseOptionMap.put("order_course_option_id",
            subscribeCourseMap.get("order_option_id"));
        // 增加剩余课时数
        orderCourseDao.updateCourseCount(updateOrderCourseOptionMap, true);

        /**
         * 2.新增t_subscribe_supplement 补课表
         */
        Map<String, Object> subscribeSupplementMap = new HashMap<String, Object>();
        subscribeSupplementMap.put("user_id", paramMap.get("supplement_user_id"));
        subscribeSupplementMap.put("user_name", paramMap.get("supplement_user_name"));
        subscribeSupplementMap.put("supplement_reason", paramMap.get("supplement_reason"));
        subscribeSupplementMap.put("course_id", paramMap.get("supplement_course_id"));
        subscribeSupplementMap.put("course_title", paramMap.get("supplement_course_title"));
        subscribeSupplementMap.put("from_subscribe_course_id",
            paramMap.get("supplement_subscribe_id"));
        subscribeSupplementMap.put("course_type", paramMap.get("supplement_course_type"));
        subscribeSupplementMap.put("create_user_id", paramMap.get("admin_user_id"));
        subscribeSupplementMap.put("create_user_name", paramMap.get("admin_user_name"));
        subscribeSupplementMap.put("update_user_id", paramMap.get("admin_user_id"));
        adminUserDao.insert(subscribeSupplementMap, "t_subscribe_supplement");
      }
    } else {
      logger.error("该课程不能补课~");
      json.setMsg("该课程不能补课~");
      json.setSuccess(false);
      throw new RuntimeException("该课程不能补课~");
    }
    return json;
  }
}