package com.webi.hwj.admin.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * @category badminUser Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_badmin_user")
public class FindBadminUserParam implements Serializable {
  private static final long serialVersionUID = 4322481555685385192L;
  // 主键
  private String keyId;
  // 真实名称
  private String adminUserName;
  // 查询参数
  private List<String> keyIds;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getAdminUserName() {
    return adminUserName;
  }

  public void setAdminUserName(String adminUserName) {
    this.adminUserName = adminUserName;
  }

  public List<String> getKeyIds() {
    return keyIds;
  }

  public void setKeyIds(List<String> keyIds) {
    this.keyIds = keyIds;
  }

}