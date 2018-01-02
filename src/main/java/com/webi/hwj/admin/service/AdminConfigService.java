package com.webi.hwj.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.admin.dao.AdminConfigDao;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminConfigService {
  @Resource
  AdminConfigDao adminConfigDao;

  /**
   * @category config 插入
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    Map<String, Object> resultMap = adminConfigDao.insert(fields);
    adminConfigDao.init();// 初始化缓存
    return resultMap;
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findListEasyui(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminConfigDao.findListEasyui(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminConfigDao.findList(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
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
    return adminConfigDao.findPage(paramMap);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
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
    return adminConfigDao.findPageEasyui("*", paramMap);
  }

  /**
   * @category 修改数据
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    int resultCount = adminConfigDao.update(fields);
    adminConfigDao.init();// 初始化缓存
    return resultCount;
  }

  /**
   * @category 查询单条数据(通过参数MAP)
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminConfigDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过key,value)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(String key, String value, String columnName) throws Exception {
    return adminConfigDao.findOne(key, value, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return adminConfigDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return adminConfigDao.delete(ids);
  }

  /**
   * @category 查询数量
   * @param sql
   * @param map
   * @return
   * @throws Exception
   */
  public int findCount(Map<String, Object> map) throws Exception {
    return adminConfigDao.findCount(map);
  }

  /**
   * @category 查询总数
   * @param map
   * @param sumField
   *          sum的字段
   * @return
   * @throws Exception
   */
  public int findSum(Map<String, Object> map, String sumField) throws Exception {
    return adminConfigDao.findSum(map, sumField);
  }

  /**
   * 
   * Title: 根据百分比获得随机的句子<br>
   * Description: 根据百分比获得随机的句子<br>
   * CreateDate: 2016年6月13日 上午11:21:45<br>
   * 
   * @category 根据百分比获得随机的句子
   * @author seven.gz
   * @param percentageDifference
   * @return
   * @throws Exception
   */
  public String findRandomSentence(int percentageDifference) throws Exception {
    // 查出码表中的所有句子
    List<String> sentenceList = new ArrayList<String>();
    List<Map<String, Object>> configList = adminConfigDao.findListByConfigType("random_sentence");
    if (configList != null && configList.size() > 0) {
      for (Map<String, Object> sentence : configList) {
        String name = (String) sentence.get("config_name");
        String[] nameSplit = name.split("_");
        Integer startPercentage = Integer.valueOf(nameSplit[nameSplit.length - 2]);
        Integer endPercentage = Integer.valueOf(nameSplit[nameSplit.length - 1]);
        if (startPercentage <= percentageDifference && percentageDifference < endPercentage) {
          sentenceList.add((String) sentence.get("config_value"));
        }
      }
    }
    if (sentenceList.size() > 0) {
      return sentenceList.get((int) (Math.random() * (sentenceList.size())));
    }
    return null;
  }
  
  /**
   * Title: 查找老师来源<br>
   * Description: 根据码表type查找老师来源<br>
   * CreateDate: 2016年12月14日 下午4:17:54<br>
   * @category 查找老师来源 
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findTeacherThirdFrom() throws Exception {
    return adminConfigDao.findListByConfigType("teacher_third_from");
  }
}