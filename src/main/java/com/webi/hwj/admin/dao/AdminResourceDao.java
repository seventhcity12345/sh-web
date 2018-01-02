package com.webi.hwj.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;
import com.webi.hwj.bean.SessionAdminUser;

@Repository
public class AdminResourceDao extends BaseMysqlDao {
  public AdminResourceDao() {
    super.setTableName("t_admin_resource");
  }

  /**
   * @category 查询所有资源数据
   * @param key_id
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> tree(String key_id) throws Exception {
    StringBuffer sql = new StringBuffer(
        "select r1.*,r2.r_name as pname from t_admin_resource r1 left join t_admin_resource r2 on r1.pid = r2.key_id where r1.is_used = 1 ");
    if (key_id != null && !"".equals(key_id)) {
      sql.append(" and r1.pid = '" + key_id + "'");
    } else {
      sql.append(" and r1.pid = 0");
    }
    sql.append(" order by r1.sort asc ");

    return super.findList(sql.toString(), new HashMap());
  }

  public List<Map<String, Object>> findPermissionList(SessionAdminUser sessionAdminUser)
      throws Exception {
    StringBuffer sqlStr = new StringBuffer("select r.permission from t_admin_resource r ");
    Map<String, Object> paramMap = new HashMap<String, Object>();

    if ("2".equals(sessionAdminUser.getAdminUserType())) {
      // 超级管理员
      sqlStr.append(" where r.is_used = 1 ");
    } else {
      // 普通管理员
      sqlStr.append(" left join tre_admin_role_resource rr on r.key_id = rr.resource_id "
          + " left join t_badmin_user tu on tu.role_id = rr.role_id "
          + " where r.is_used = 1 and tu.key_id = :key_id ");
      paramMap.put("key_id", sessionAdminUser.getKeyId());
    }
    // 只查询功能
    sqlStr.append(" and r.r_type = 1 ");
    sqlStr.append(" order by r.sort asc ");

    return super.findList(sqlStr.toString(), paramMap);
  }

  /**
   * @category 左侧管理员菜单
   * @param userId
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> leftMenuTree(String keyId, SessionAdminUser sessionAdminUser)
      throws Exception {
    StringBuffer sqlStr = new StringBuffer(
        "select r.key_id,r.r_name,r.pid,r.sort,r.url from t_admin_resource r ");
    Map<String, Object> paramMap = new HashMap<String, Object>();

    if ("2".equals(sessionAdminUser.getAdminUserType())) {
      // 超级管理员
      sqlStr.append(" where r.is_used = 1 ");
    } else {
      // 普通管理员
      sqlStr.append(" left join tre_admin_role_resource rr on r.key_id = rr.resource_id "
          + " left join t_badmin_user tu on tu.role_id = rr.role_id "
          + " where r.is_used = 1 and tu.key_id = :key_id ");
      paramMap.put("key_id", sessionAdminUser.getKeyId());
    }
    // 只查询菜单
    sqlStr.append(" and r.r_type = 0 ");

    if (keyId != null && !"".equals(keyId)) {
      sqlStr.append(" and r.pid = '" + keyId + "'");
    } else {
      sqlStr.append(" and r.pid = '0' ");
    }
    sqlStr.append(" order by r.sort asc ");

    return super.findList(sqlStr.toString(), paramMap);
  }

  /**
   * @category 查询最大sort
   * @param pid
   * @return
   * @throws Exception
   */
  public Integer findMaxSort(String pid) throws Exception {
    String sql = "select max(sort) from t_admin_resource where pid = " + pid;
    // Integer maxSort = super.getNamedParameterJdbcTemplate().queryForInt(sql,
    // new HashMap());

    Integer maxSort = super.getNamedParameterJdbcTemplate().queryForObject(sql, new HashMap(),
        Integer.class);

    return maxSort != null ? maxSort + 1 : 1;
  }

  /**
   * @category 向上排序
   * @param pid
   * @return
   * @throws Exception
   */
  public void upSort(Map<String, Object> paramMap) throws Exception {
    String point = paramMap.get("point").toString();
    String sortStr = paramMap.get("target").toString();
    if ("bottom".equals(point)) {// 在目标下方
      sortStr = (Integer.valueOf(sortStr) + 1) + "";
    }

    String sql1 = "update t_admin_resource set sort = sort + 1 where sort >= " + sortStr
        + " and sort < "
        + paramMap.get("source") + " and pid = " + paramMap.get("pid");// 自己新位置往下的所有sort自增1
    String sql2 = "update t_admin_resource set sort = " + sortStr + " where key_id = "
        + paramMap.get("key_id");// 改变自己的sort

    super.getNamedParameterJdbcTemplate().update(sql1, new HashMap());
    super.getNamedParameterJdbcTemplate().update(sql2, new HashMap());
  }

  /**
   * @category 向下排序
   * @param pid
   * @return
   * @throws Exception
   */
  public void downSort(Map<String, Object> paramMap) throws Exception {
    String point = paramMap.get("point").toString();
    String sortStr = paramMap.get("target").toString();
    if ("top".equals(point)) {// 在目标上方
      sortStr = (Integer.valueOf(sortStr) - 1) + "";
    }

    String sql1 = "update t_admin_resource set sort = sort - 1 where sort > "
        + paramMap.get("source")
        + " and sort <= " + sortStr + " and pid = " + paramMap.get("pid");// 自己新位置往下的所有sort自减1
    String sql2 = "update t_admin_resource set sort = " + sortStr + " where key_id = "
        + paramMap.get("key_id");// 改变自己的sort

    super.getNamedParameterJdbcTemplate().update(sql1, new HashMap());
    super.getNamedParameterJdbcTemplate().update(sql2, new HashMap());
  }
}