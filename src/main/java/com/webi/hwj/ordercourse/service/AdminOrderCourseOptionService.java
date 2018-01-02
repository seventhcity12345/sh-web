package com.webi.hwj.ordercourse.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.coursetype.param.CourseTypeInfo;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseOptionDao;
import com.webi.hwj.util.CalendarUtil;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminOrderCourseOptionService {
  private static Logger logger = Logger.getLogger(AdminOrderCourseOptionService.class);
  @Resource
  AdminOrderCourseOptionDao adminOrderCourseOptionDao;

  /**
   * @category orderCourseOption 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return adminOrderCourseOptionDao.insert(fields);
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
    return adminOrderCourseOptionDao.findListEasyui(paramMap, columnName);
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
    List<Map<String, Object>> tempList = adminOrderCourseOptionDao.findList(paramMap, columnName);
    for (Map<String, Object> m : tempList) {
      // 保存原始id
      m.put("course_type_id", m.get("course_type") + "");
      // 从码表读name
      m.put("course_type", ((CourseType) MemcachedUtil.getValue(m.get("course_type").toString()))
          .getCourseTypeChineseName());

    }
    return tempList;
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
  public Page findPage(Map<String, Object> paramMap) throws Exception {
    return adminOrderCourseOptionDao.findPage(paramMap);
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
  public Page findPageEasyui(Map<String, Object> paramMap) throws Exception {
    return adminOrderCourseOptionDao.findPageEasyui("*", paramMap);
  }

  /**
   * @category 修改数据
   * @author mingyisoft代码生成工具
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return adminOrderCourseOptionDao.update(fields);
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
    return adminOrderCourseOptionDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return adminOrderCourseOptionDao.findOneByKeyId(param, columnName);
  }

  /**
   * Title: 查找该用户拥有的所有课程类型<br>
   * Description: findCourseTypesByUserId<br>
   * CreateDate: 2016年7月12日 下午3:37:51<br>
   * 
   * @category 查找该用户拥有的所有课程类型
   * @author komi.zsy
   * @param userId
   * @return
   * @throws Exception
   */
  public Map<String, Object> findCourseTypesByUserId(String userId) throws Exception {
    Map<String, Object> returnMap = new HashMap<String, Object>();

    List<Map<String, Object>> returnList = adminOrderCourseOptionDao
        .findCourseTypesByUserId(userId);
    CourseTypeInfo courseTypeInfo;
    for (Map<String, Object> returnObj : returnList) {
      // returnMap.put(returnObj.get("course_type").toString(),
      // ((CourseType)MemcachedUtil.getValue(returnObj.get("course_type").toString())).getCourseTypeChineseName());
      // 从map中获取courseType的信息
      courseTypeInfo = (CourseTypeInfo) returnMap.get(returnObj.get("course_type").toString());
      if (courseTypeInfo == null) {
        courseTypeInfo = new CourseTypeInfo();
        returnMap.put(returnObj.get("course_type").toString(), courseTypeInfo);
      }

      // 设置中文名
      courseTypeInfo.setCourseTypeChineseName(
          ((CourseType) MemcachedUtil.getValue(returnObj.get("course_type").toString()))
              .getCourseTypeChineseName());

      // 合同开始时间
      Date startOrderTime = (Date) returnObj.get("start_order_time");
      // 课程单位
      String courseUnitType = returnObj.get("course_unit_type").toString();
      // 课程数
      String showCourseCount = (String) returnObj.get("show_course_count").toString();
      // 这三个值理论上不会为空
      // modify by seven 2016年11月7日20:12:53
      // 只有执行中的合同需要计算时间
      String orderStatus = (String) returnObj.get("order_status").toString();
      if (startOrderTime != null && courseUnitType != null && showCourseCount != null
          && OrderCourseConstant.ORDER_HAS_BEEN_PAID.equals(orderStatus)) {
        // 限制时间
        Date limitTime = courseTypeInfo.getLimitTime();
        // 如果时效单位是月
        if (OrderCourseConstant.COURSE_UNIT_TYPE_MONTH.equals(courseUnitType)) {
          if (limitTime == null) {
            limitTime = CalendarUtil.getNextNMonth(startOrderTime,
                Integer.valueOf(showCourseCount));
          } else {
            limitTime = CalendarUtil.getNextNMonth(limitTime, Integer.valueOf(showCourseCount));
          }
          // 如果时效单位是天
          courseTypeInfo.setOrderId((String) returnObj.get("order_id"));
          courseTypeInfo.setOrderOptionId((String) returnObj.get("order_option_id"));
        } else if (OrderCourseConstant.COURSE_UNIT_TYPE_DAY.equals(courseUnitType)) {
          if (limitTime == null) {
            limitTime = CalendarUtil.getNextNDay(startOrderTime, Integer.valueOf(showCourseCount));
          } else {
            limitTime = CalendarUtil.getNextNDay(limitTime, Integer.valueOf(showCourseCount));
          }
          // 如果时效单位是节
          courseTypeInfo.setOrderId((String) returnObj.get("order_id"));
          courseTypeInfo.setOrderOptionId((String) returnObj.get("order_option_id"));
        } else if (OrderCourseConstant.COURSE_UNIT_TYPE_CLASS.equals(courseUnitType)) {
          // 设置标志这种课程类型有按节上课的
          courseTypeInfo.setCourseUnitHaveClass(true);
        }
        courseTypeInfo.setLimitTime(limitTime);
      }
    }
    return returnMap;
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return adminOrderCourseOptionDao.delete(ids);
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
    return adminOrderCourseOptionDao.findCount(map);
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
    return adminOrderCourseOptionDao.findSum(map, sumField);
  }
}