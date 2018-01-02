package com.webi.hwj.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.admin.dao.AdminResourceDao;
import com.webi.hwj.bean.SessionAdminUser;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminResourceService {
  @Resource
  AdminResourceDao resourceDao;

  /**
   * @category tResource 插入
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return resourceDao.insert(fields);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return resourceDao.findList(paramMap, columnName);
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
    return resourceDao.findPageEasyui("*", paramMap);
  }

  /**
   * @category 修改数据
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return resourceDao.update(fields);
  }

  /**
   * @category 查询单个数据
   * @param id
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(Map paramMap, String columnName) throws Exception {
    return resourceDao.findOne(paramMap, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return resourceDao.delete(ids);
  }

  /**
   * @category 资源tree
   * @param key_id
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> tree(String key_id) throws Exception {
    List<Map<String, Object>> list = resourceDao.tree(key_id);
    for (Map<String, Object> obj : list) {
      obj.put("id", obj.get("key_id"));
      obj.put("text", obj.get("r_name"));
      if (key_id == null || "".equals(key_id) || "0".equals(key_id)) {
        obj.put("state", "closed");// 非叶子节点
      } else {
        obj.put("state", "open");// 叶子节点
      }
    }

    return list;
  }

  /**
   * @category 左侧管理员菜单
   * @param userId
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> leftMenuTree(String key_id, SessionAdminUser sessionAdminUser)
      throws Exception {
    List<Map<String, Object>> list = resourceDao.leftMenuTree(key_id, sessionAdminUser);
    for (Map<String, Object> obj : list) {
      obj.put("id", obj.get("key_id"));
      obj.put("text", obj.get("r_name"));
      if (key_id == null || "".equals(key_id) || "0".equals(key_id)) {
        obj.put("state", "closed");// 非叶子节点
      } else {
        obj.put("state", "open");// 叶子节点
      }
    }

    return list;
  }

  /**
   * Title: 查询当前用户的功能权限Map<br>
   * Description: findPermissionMap<br>
   * CreateDate: 2016年2月14日 下午1:39:10<br>
   * 
   * @category 查询当前用户的功能权限Map
   * @author yangmh
   * @param sessionAdminUser
   * @return
   * @throws Exception
   */
  public Map<String, Object> findPermissionMap(SessionAdminUser sessionAdminUser) throws Exception {
    List<Map<String, Object>> list = resourceDao.findPermissionList(sessionAdminUser);
    Map<String, Object> returmMap = new HashMap<String, Object>();
    for (Map<String, Object> obj : list) {
      returmMap.put(obj.get("permission").toString(), obj.get("permission"));
    }
    return returmMap;
  }

  /**
   * @category 向上排序
   * @param pid
   * @return
   * @throws Exception
   */
  public void sort(Map<String, Object> paramMap) throws Exception {
    int source = Integer.valueOf(paramMap.get("source") + "");// 原
    int target = Integer.valueOf(paramMap.get("target") + "");// 目标

    if (source - target > 0) {// 向上排序
      resourceDao.upSort(paramMap);
    } else {// 向下排序
      resourceDao.downSort(paramMap);
    }
  }

}