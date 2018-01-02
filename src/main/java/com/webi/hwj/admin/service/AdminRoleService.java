package com.webi.hwj.admin.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.admin.dao.AdminRoleDao;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminRoleService {
  @Resource
  AdminRoleDao roleDao;

  /**
   * @category tRole 插入
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return roleDao.insert(fields);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return roleDao.findList(paramMap, columnName);
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
    return roleDao.findPageEasyui("*", paramMap);
  }

  /**
   * @category 修改数据
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return roleDao.update(fields);
  }

  /**
   * @category 查询单条数据(通过参数MAP)
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return roleDao.findOne(paramMap, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return roleDao.delete(ids);
  }

  /**
   * @category 通过roleId查询对应的resource的keyid 用逗号分割。
   * @param roleId
   * @return
   * @throws Exception
   */
  public String findRoleResources(String roleId) throws Exception {
    StringBuffer returnStr = new StringBuffer(1024);
    List<Map<String, Object>> resultList = roleDao.findRoleResources(roleId);

    for (Map<String, Object> tempMap : resultList) {
      if (returnStr.length() != 0)
        returnStr.append(",");
      returnStr.append(tempMap.get("resource_id"));
    }
    return returnStr.toString();
  }

  /**
   * @category 保存中间表数据
   * @param paramMap
   * @throws Exception
   */
  public void submitRoleResources(Map<String, Object> paramMap) throws Exception {
    roleDao.submitRoleResources(paramMap);
  }

}