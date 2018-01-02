package com.webi.hwj.coursepackage.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.coursepackage.dao.AdminCoursePackageDao;
import com.webi.hwj.coursepackage.param.CoursePackageAndPriceParam;
import com.webi.hwj.coursepackagepriceoption.dao.CoursePackagePriceOptionDao;
import com.webi.hwj.coursepackagepriceoption.entity.CoursePackagePriceOption;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminCoursePackageService {
  private static Logger logger = Logger.getLogger(AdminCoursePackageService.class);
  @Resource
  AdminCoursePackageDao adminCoursePackageDao;
  @Resource
  CoursePackagePriceOptionDao coursePackagePriceOptionDao;

  /**
   * Title: 根据价格策略查询可用课程包<br>
   * Description: 根据价格策略查询可用课程包<br>
   * CreateDate: 2016年8月29日 下午5:57:26<br>
   * 
   * @category 根据价格策略查询可用课程包
   * @author komi.zsy
   * @param paramMap
   * @param columnName
   * @return
   * @throws Exception
   */
  public List<CoursePackageAndPriceParam> findList(String keyId) throws Exception {
    return coursePackagePriceOptionDao.findList(keyId);
  }

  /**
   * @category coursePackage 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return adminCoursePackageDao.insert(fields);
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
    return adminCoursePackageDao.findPage(paramMap);
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
    return adminCoursePackageDao.findPageEasyui("*", paramMap);
  }

  /**
   * @category 修改数据
   * @author mingyisoft代码生成工具
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return adminCoursePackageDao.update(fields);
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
    return adminCoursePackageDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return adminCoursePackageDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return adminCoursePackageDao.delete(ids);
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
    return adminCoursePackageDao.findCount(map);
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
    return adminCoursePackageDao.findSum(map, sumField);
  }
}