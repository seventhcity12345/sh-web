/** 
 * File: 课程进度（实际进度、期望进度）、合同进度service
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.index.service<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月25日 下午6:30:54
 * @author athrun.cw
 */
package com.webi.hwj.index.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.index.util.OrderCourseProgressUtil;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.dao.OrderCourseOptionDao;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.entitydao.OrderCourseEntityDao;
import com.webi.hwj.ordercourse.entitydao.OrderCourseOptionEntityDao;
import com.webi.hwj.ordercourse.param.ContractLearningProgressParam;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeSupplementDao;

/**
 * Title: 课程进度（实际进度、期望进度）、合同进度service Description:
 * OrderCourseProgressService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月25日 下午6:30:54
 * 
 * @author athrun.cw
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OrderCourseProgressService {
  private static Logger logger = Logger.getLogger(OrderCourseProgressService.class);
  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  OrderCourseOptionDao orderCourseOptionDao;

  @Resource
  SubscribeSupplementDao subscribeSupplementDao;

  @Resource
  SubscribeCourseDao subscribeCourseDao;
  
  @Resource
  OrderCourseEntityDao orderCourseEntityDao;
  
  @Resource
  OrderCourseOptionEntityDao orderCourseOptionEntityDao;

  /**
   * 单表查询，无需事务 Title: 查找到已经签订的 && 已经开始上课合同信息 Description:
   * findStartingContractByUserId<br>
   * 查找到已经签订的 && 已经开始上课合同信息 CreateDate: 2015年8月26日 上午11:36:49<br>
   * @category findStartingContractByUserId
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<ContractLearningProgressParam> findStartingContractListByUserId(String userId) throws Exception {
    logger.debug("用户id：[" + userId + "] 查找到已经签订的 && 已经开始上课合同信息");
    // 找到已经开始的合同
    List<ContractLearningProgressParam> startingContractList = new ArrayList<ContractLearningProgressParam>();
    startingContractList = orderCourseEntityDao.findStartingContractListByUserId(userId,new Date());
    
    // 当前时间下，有正在开课的合同时候
    if (startingContractList != null && startingContractList.size() != 0) {
      logger.info("用户id：[" + userId + "] 合同信息集合为：" + startingContractList);
      for (ContractLearningProgressParam startingContract : startingContractList) {
        /**
         * 使用码表中的category_type1 2 3等 来显示 合同类型
         */
        startingContract.setCategoryTypeChineseName(MemcachedUtil.getConfigValue(startingContract.getCategoryType()));

        // 课程option数据课时
        List<OrderCourseOption> optionStartingContract = orderCourseOptionEntityDao.findDetailsOptionByOrderId(startingContract.getKeyId());

        startingContract = OrderCourseProgressUtil
            .startingContrartProgress(startingContract,optionStartingContract);
      }
      
    } else {// 没有正在开课的合同时候 进度全部显示为0
            // 1.合同进度显示为0
      logger.debug("用户id：[" + userId + "] 暂时无有效的合同！");
      ContractLearningProgressParam startingContract = new ContractLearningProgressParam();
      //剩余课时
      startingContract.setRemainCourseCount(0);
      // 课程期望进度
      startingContract.setExpectCourseProgress(0);
      // 课程真实进度
      startingContract.setCourseProgress(0);
      // 合同结束时间
      startingContract.setEndOrderTime(new Date());
      // 合同类型
      startingContract.setCategoryTypeChineseName("暂无合同");
      
      startingContractList.add(startingContract);
    }
    
    return startingContractList;
  }
}
