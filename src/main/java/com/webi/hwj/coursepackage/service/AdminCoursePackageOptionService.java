package com.webi.hwj.coursepackage.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursepackage.dao.AdminCoursePackageOptionDao;
import com.webi.hwj.coursepackage.dao.CoursePackageOptionEntityDao;
import com.webi.hwj.coursepackage.param.CoursePackageOptionAndPriceParam;
import com.webi.hwj.coursetype.entity.CourseType;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminCoursePackageOptionService {
  private static Logger logger = Logger.getLogger(AdminCoursePackageOptionService.class);
  @Resource
  AdminCoursePackageOptionDao adminCoursePackageOptionDao;
  @Resource
  CoursePackageOptionEntityDao coursePackageOptionEntityDao;

  /**
   * @category coursePackageOption 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return adminCoursePackageOptionDao.insert(fields);
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
    return adminCoursePackageOptionDao.findListEasyui(paramMap, columnName);
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
    List<Map<String, Object>> tempList = adminCoursePackageOptionDao.findList(paramMap, columnName);
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
   * Title: 查找课程包子表以及相对于的小维度价格<br>
   * Description: 查找课程包子表以及相对于的小维度价格<br>
   * CreateDate: 2016年8月30日 上午11:15:18<br>
   * 
   * @category 查找课程包子表以及相对于的小维度价格
   * @author komi.zsy
   * @param paramMap
   * @param columnName
   * @return
   * @throws Exception
   */
  public List<CoursePackageOptionAndPriceParam> findOptionAndPirceList(String coursePackageId,
      Integer coursePriceVersion) throws Exception {
    List<CoursePackageOptionAndPriceParam> tempList = coursePackageOptionEntityDao
        .findOptionAndPirceList(coursePackageId, coursePriceVersion);
    for (CoursePackageOptionAndPriceParam option : tempList) {
      // 保存原始id
      option.setCourseTypeId(option.getCourseType());
      // 从码表读name
      option.setCourseType(((CourseType) MemcachedUtil.getValue(option.getCourseTypeId()))
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
    return adminCoursePackageOptionDao.findPage(paramMap);
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
    return adminCoursePackageOptionDao.findPageEasyui("*", paramMap);
  }

  /**
   * @category 修改数据
   * @author mingyisoft代码生成工具
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return adminCoursePackageOptionDao.update(fields);
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
    return adminCoursePackageOptionDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return adminCoursePackageOptionDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return adminCoursePackageOptionDao.delete(ids);
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
    return adminCoursePackageOptionDao.findCount(map);
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
    return adminCoursePackageOptionDao.findSum(map, sumField);
  }

  /**
   * Title: 计算English Studio & RSA & 微课，单价之和<br>
   * Description: 调用dao，计算English Studio & RSA & 微课，单价之和<br>
   * CreateDate: 2016年5月20日 上午10:33:15<br>
   * 
   * @category 计算English Studio & RSA & 微课，单价之和
   * @author ivan.mgh
   * @return
   * @throws Exception
   */
  public Integer sumEnglishStudioRsaWeikeUnitPrice() throws Exception {
    return adminCoursePackageOptionDao.sumEnglishStudioRsaWeikeUnitPrice();
  }

}