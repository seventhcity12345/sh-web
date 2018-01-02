package com.webi.hwj.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class AdminRoleDao extends BaseMysqlDao {
  public AdminRoleDao() {
    super.setTableName("t_admin_role");
  }

  /**
   * @category 通过role_id查询资源列表
   * @param roleId
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findRoleResources(String roleId) throws Exception {
    String sql = "select * from tre_admin_role_resource where role_id = :role_id ";
    Map paramMap = new HashMap();
    paramMap.put("role_id", roleId);
    return super.findList(sql, paramMap);
  }

  /**
   * @category 新增中间表数据
   * @param paramMap
   * @throws Exception
   */
  public void submitRoleResources(Map paramMap) throws Exception {
    String ids = paramMap.get("ids").toString();
    String roleId = paramMap.get("roleId").toString();

    String sql = "delete from tre_admin_role_resource where role_id = :role_id ";
    Map finalMap = new HashMap();
    finalMap.put("role_id", roleId);
    update(sql, finalMap);

    String[] strArray = ids.split(",");
    for (String tempStr : strArray) {
      Map newMap = new HashMap();
      newMap.put("role_id", roleId);
      newMap.put("resource_id", tempStr);

      insert(newMap, "tre_admin_role_resource");
    }
  }
}