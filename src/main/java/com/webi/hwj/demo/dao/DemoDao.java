package com.webi.hwj.demo.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.demo.entity.Demo;

@Repository
public class DemoDao extends BaseEntityDao<Demo> {
  private static Logger logger = Logger.getLogger(DemoDao.class);

  private final static String XX_SQL = "select phone,user_name from t_demo where is_student = :isStudent";

  private final static String XX_PAGE_SQL = "select phone,user_name from t_demo where is_student = :isStudent";
  private final static String XX_PAGE_SQL2 = "select phone from t_demo where phone = :phone";

  // 不写sql的例子
  public List<Demo> findList(Demo demo) throws Exception {
    return super.findList(demo, "phone,user_name,learning_coach_id,current_level");
  }

  // 写sql的例子
  public List<Demo> findListWithSql(Demo demo) throws Exception {
    return super.findList(XX_SQL, demo);
  }

  // 分页例子,不写sql
  public Page findPage(Demo demo, Integer page, Integer rows) throws Exception {
    return super.findPage(demo, "key_id,user_name,current_level,create_date", page, rows);
  }

  // 分页例子,不写sql
  public Page findPageWithSql(Demo demo, Integer page, Integer rows) throws Exception {
    return super.findPage(XX_PAGE_SQL, demo, page, rows);
  }

  // 查询一条数据
  public Demo findOne(Demo demo) throws Exception {
    return super.findOne(demo, "phone,user_name");
  }

  public Demo findOneSql(Demo demo) throws Exception {
    return super.findOne(XX_PAGE_SQL, demo);
  }

  // 按主键查询一条数据
  public Demo findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId,
        "key_id,phone,current_level,is_student,user_name,user_code,user_photo,create_date");
  }

  // 按主键查询一条String
  public String findOneStringByKeyId(String keyId) throws Exception {
    return super.findOneStringByKeyId(keyId, "key_id");
  }

  // 按（可以不是本dao的对象）查询一条String
  public String findOneString(Demo demo) throws Exception {
    return super.findOneString(demo, "phone");
  }

  public String findOneString2(Demo demo) throws Exception {
    return super.findOneString(XX_PAGE_SQL2, demo);
  }
}