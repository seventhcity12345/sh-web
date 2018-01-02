package com.webi.hwj.trainingcamp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.trainingcamp.dao.TrainingMemberEntityDao;
import com.webi.hwj.trainingcamp.dao.TrainingMemberOptionEntityDao;
import com.webi.hwj.trainingcamp.entity.TrainingMemberOption;

/**
 * @category trainingMemberOption控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class TrainingMemberOptionService {
  private static Logger logger = Logger.getLogger(TrainingMemberOptionService.class);
  @Resource
  TrainingMemberOptionEntityDao trainingMemberOptionEntityDao;
  @Resource
  TrainingMemberEntityDao trainingMemberEntityDao;

  /**
   * Title: 新增数据<br>
   * Description: insert<br>
   * CreateDate: 2016年4月11日 上午8:57:05<br>
   * 
   * @category 新增数据
   * @author yangmh
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int insert(TrainingMemberOption paramObj) throws Exception {
    return trainingMemberOptionEntityDao.insert(paramObj);
  }
  
  /**
   * Title: 加分/扣分<br>
   * Description: 加分/扣分<br>
   * CreateDate: 2017年8月10日 下午4:12:59<br>
   * @category 加分/扣分 
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  @Transactional(isolation = Isolation.SERIALIZABLE,propagation = Propagation.REQUIRED)
  public int addScore(TrainingMemberOption paramObj) throws Exception {
    //更新个人积分总分
    int num = trainingMemberEntityDao.updateTotalScore(paramObj.getTrainingCampId(), paramObj.getTrainingMemberUserId(), paramObj.getTrainingMemberOptionScore());
    if(num == 0){
      return 0;
    }
    
    return trainingMemberOptionEntityDao.insert(paramObj);
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
  public int batchInsert(List<TrainingMemberOption> paramObjList) throws Exception {
    return trainingMemberOptionEntityDao.batchInsert(paramObjList);
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
    return trainingMemberOptionEntityDao.delete(keyIds);
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
    return trainingMemberOptionEntityDao.delete(keyIds);
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
    return trainingMemberOptionEntityDao.deleteForReal(keyIds);
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
    return trainingMemberOptionEntityDao.deleteForReal(keyIds);
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
  public int update(TrainingMemberOption paramObj) throws Exception {
    return trainingMemberOptionEntityDao.update(paramObj);
  }

  /**
   * Title: 批量修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:46:26<br>
   * 
   * @category 批量修改数据
   * @author yangmh
   * @param paramObjList
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchUpdate(List<TrainingMemberOption> paramObjList) throws Exception {
    return trainingMemberOptionEntityDao.batchUpdate(paramObjList);
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
  public int findCount(TrainingMemberOption paramObj) throws Exception {
    return trainingMemberOptionEntityDao.findCount(paramObj);
  }
}