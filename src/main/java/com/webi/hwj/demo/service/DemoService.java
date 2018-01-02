package com.webi.hwj.demo.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.demo.dao.DemoDao;
import com.webi.hwj.demo.entity.Demo;

/**
 * @category demo控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class DemoService {
  private static Logger logger = Logger.getLogger(DemoService.class);
  @Resource
  DemoDao demoDao;

  /**
   * Title: 新增数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:02:27<br>
   * 
   * @category 新增数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return 执行成功数
   * @throws Exception
   */
  public int insert(Demo demo) throws Exception {
    return demoDao.insert(demo);
  }

  /**
   * Title: 批量新增数据<br>
   * Description: insert<br>
   * CreateDate: 2016年4月27日 下午7:48:37<br>
   * 
   * @category 批量新增数据
   * @author yangmh
   * @param paramObjList
   * @return
   * @throws Exception
   */
  public int insert(List<Demo> paramObjList) throws Exception {
    return demoDao.batchInsert(paramObjList);
  }

  /**
   * Title: 批量新增数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:04:36<br>
   * 
   * @category 批量新增数据
   * @author yangmh
   * @param paramObj
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchInsert(List<Demo> demoList) throws Exception {
    return demoDao.batchInsert(demoList);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:00<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(String keyIds) throws Exception {
    System.out.println("alex======");
    return demoDao.delete(keyIds);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:23<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(List<String> keyIds) throws Exception {
    return demoDao.delete(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(List<String> keyIds) throws Exception {
    return demoDao.deleteForReal(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(String keyIds) throws Exception {
    return demoDao.deleteForReal(keyIds);
  }

  /**
   * Title: 修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:40:18<br>
   * 
   * @category 修改数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return 执行成功数
   * @throws Exception
   */
  public int update(Demo demo) throws Exception {
    return demoDao.update(demo);
  }

  /**
   * Title: 批量修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:46:26<br>
   * 
   * @category 批量修改数据
   * @author yangmh
   * @param paramObj
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchUpdate(List<Demo> demoList) throws Exception {
    return demoDao.batchUpdate(demoList);
  }

  /**
   * Title: 查询数量数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午1:09:45<br>
   * 
   * @category 查询数量数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return
   * @throws Exception
   */
  public int findCount(Demo demo) throws Exception {
    return demoDao.findCount(demo);
  }

  public List<Demo> findList(Demo demo) throws Exception {
    return demoDao.findList(demo);
  }

  public List<Demo> findListWithSql(Demo demo) throws Exception {
    return demoDao.findListWithSql(demo);
  }

  public Page findPage(Demo demo, Integer page, Integer rows) throws Exception {
    return demoDao.findPage(demo, page, rows);
  }

  public Page findPageWithSql(Demo demo, Integer page, Integer rows) throws Exception {
    return demoDao.findPageWithSql(demo, page, rows);
  }

  public Demo findOne(Demo demo) throws Exception {
    return demoDao.findOne(demo);
  }

  public Demo findOneByKeyId(String keyId) throws Exception {
    return demoDao.findOneByKeyId(keyId);
  }

  public Demo findOneSql(Demo demo) throws Exception {
    return demoDao.findOneSql(demo);
  }

  public String findOneStringByKeyId(String keyId) throws Exception {
    return demoDao.findOneStringByKeyId(keyId);
  }

  public String findOneString(Demo demo) throws Exception {
    return demoDao.findOneString(demo);
  }

  public String findOneString2(Demo demo) throws Exception {
    return demoDao.findOneString2(demo);
  }

}