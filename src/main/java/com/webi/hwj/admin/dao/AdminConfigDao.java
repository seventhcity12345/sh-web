package com.webi.hwj.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;

@Repository
public class AdminConfigDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(BaseMysqlDao.class);

  public AdminConfigDao() {
    System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
    super.setTableName("t_admin_config");
    // AliyunCacheUtil.setConfigAdminDao(this);
  }

  /**
   * @category 初始化config缓存
   * @throws Exception
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @PostConstruct
  public void init() throws Exception {
    try {
      logger.info("begin................init............");
      // List<Map<String, Object>> resultList = super.findList(new
      // HashMap(),"*");
      // 初始化配置信息进缓存
      // for(Map m : resultList){
      // if(m.get("c_value")!=null){
      //// AliyunCacheUtil.setValue(m.get("c_key_en")+"", m.get("c_value"));
      // }
      // }
    } catch (Exception e) {
      // e.printStackTrace();
    }
    // System.out.println(ConfigCache.getValue("checkword"));
  }

  /**
   * 
   * Title: 根据类型查询码表<br>
   * Description: 根据类型查询码表<br>
   * CreateDate: 2016年6月13日 上午10:36:15<br>
   * 
   * @category 根据类型查询码表
   * @author seven.gz
   * @param congfigType
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findListByConfigType(String congfigType) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("config_type", congfigType);
    return super.findList(paramMap, "config_name,config_value");
  }
}