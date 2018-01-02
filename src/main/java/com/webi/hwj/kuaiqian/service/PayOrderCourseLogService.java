/** 
 * File: PayOrderCourseLogService.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月18日 下午3:39:16
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.kuaiqian.dao.PayOrderCourseDao;
import com.webi.hwj.util.UUIDUtil;

/**
 * Title: PayOrderCourseLogService<br>
 * Description: PayOrderCourseLogService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月18日 下午3:39:16
 * 
 * @author athrun.cw
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PayOrderCourseLogService {

  private static Logger logger = Logger.getLogger(PayOrderCourseLogService.class);
  @Resource
  PayOrderCourseDao payOrderCourseDao;

  /**
   * 
   * Title: 从orderId，更新支付日志信息 Description: updateKuaiqianLogByOrderId<br>
   * CreateDate: 2015年8月18日 下午4:42:17<br>
   * 
   * @category updateKuaiqianLogByOrderId
   * @author athrun.cw
   * @param logParamMap
   * @return
   * @throws Exception
   */
  public int updateKuaiqianLogByKeyId(Map<String, Object> logParamMap) throws Exception {
    logger.debug("更新支付日志信息");
    return payOrderCourseDao.updateKuaiqianLogByKeyId(logParamMap);
  }

  /**
   * 
   * Title: 初始化 一条支付记录 Description: insert<br>
   * 初始化 一条支付记录 CreateDate: 2015年8月18日 下午3:46:10<br>
   * 
   * @category insert
   * @author athrun.cw
   * @param fields
   * @return
   * @throws Exception
   */
  public int insertKuaiqianLogByParamMap(Map<String, Object> paramMap) throws Exception {
    // 生成30位的key_id
    paramMap.put("key_id", UUIDUtil.uuid(30));
    logger.debug("将要支付的基本信息：" + paramMap);
    return payOrderCourseDao.insertKuaiqianLogByParamMap(paramMap);
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
    return payOrderCourseDao.findList(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @return
   * @throws Exception
   */
  public Page findPage(Map<String, Object> paramMap) throws Exception {
    return payOrderCourseDao.findPage(paramMap);
  }

  /**
   * @category update
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public int update(Map<String, Object> fields) throws Exception {
    return payOrderCourseDao.update(fields);
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
    return payOrderCourseDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return payOrderCourseDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return payOrderCourseDao.delete(ids);
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
    return payOrderCourseDao.findCount(map);
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
    return payOrderCourseDao.findSum(map, sumField);
  }

}
