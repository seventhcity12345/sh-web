package com.webi.hwj.ordercourse.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.admin.dao.AdminBdminUserDao;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.dao.OrderCourseOptionDao;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseOptionEntityDao;
import com.webi.hwj.ordercourse.param.FindUserEffectiveContractParam;
import com.webi.hwj.ordercourse.param.OrderCourseOptionParam;
import com.webi.hwj.ordercourse.util.OrderContractStatusUtil;
import com.webi.hwj.subscribecourse.dao.SubscribeSupplementEntityDao;
import com.webi.hwj.subscribecourse.param.FindSubscribeSupplementCountParam;
import com.webi.hwj.user.dao.UserDao;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OrderCourseOptionService {
  private static Logger logger = Logger.getLogger(OrderCourseOptionService.class);
  @Resource
  OrderCourseOptionDao orderCourseOptionDao;
  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  UserDao userDao;

  @Resource
  AdminBdminUserDao adminBdminUserDao;

  @Resource
  AdminOrderCourseOptionEntityDao adminOrderCourseOptionEntityDao;

  @Resource
  SubscribeSupplementEntityDao subscribeSupplementEntityDao;

  /**
   * 
   * Title: 查看订单下的 所有具体的服务<br>
   * Description: findDetailsOptionByOrderId<br>
   * CreateDate: 2015年8月14日 上午11:21:16<br>
   * 
   * @category 查看订单下的 所有具体的服务
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findDetailsOptionByOrderId(Map<String, Object> paramMap)
      throws Exception {
    logger.debug("查看订单下的 所有具体的服务");
    return orderCourseOptionDao.findDetailsOptionByOrderId(paramMap);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   */
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return orderCourseOptionDao.findList(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   */
  public Page findPage(Map<String, Object> paramMap) throws Exception {
    return orderCourseOptionDao.findPage(paramMap);
  }

  /**
   * @category 修改数据
   */
  public int update(Map<String, Object> fields) throws Exception {
    return orderCourseOptionDao.update(fields);
  }

  /**
   * @category 查询单条数据(通过参数MAP)
   */
  public Map<String, Object> findOne(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return orderCourseOptionDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return orderCourseOptionDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   */
  public int delete(String ids) throws Exception {
    return orderCourseOptionDao.delete(ids);
  }

  /**
   * @category 查询数量
   */
  public int findCount(Map<String, Object> map) throws Exception {
    return orderCourseOptionDao.findCount(map);
  }

  /**
   * @category 查询总数
   * @param sumField
   *          sum的字段
   */
  public int findSum(Map<String, Object> map, String sumField) throws Exception {
    return orderCourseOptionDao.findSum(map, sumField);
  }

  /**
   * Title: 查询用户当前执行中的合同拥有的课程<br>
   * Description: 查询用户当前执行中的合同拥有的课程<br>
   * CreateDate: 2016年10月13日 下午2:10:21<br>
   * 
   * @category 查询用户当前执行中的合同拥有的课程
   * @author komi.zsy
   * @param userId
   *          用户id
   * @return
   * @throws Exception
   */
  public List<OrderCourseOptionParam> findOrderCourseInfoList(String userId) throws Exception {
    // 用户合同执行中的课程原始数据（sql里有排序，先是is_gift = 0非赠送的课时，再是赠送的课时）
    List<OrderCourseOptionParam> orderCourseOptionList = adminOrderCourseOptionEntityDao
        .findOrderCourseInfoList(userId);
    if (orderCourseOptionList != null && orderCourseOptionList.size() != 0) {
      Date orderStartTime = orderCourseOptionList.get(0).getStartOrderTime();
      // 处理课程剩余有效期
      OrderContractStatusUtil.formatRemainCourseCount(orderCourseOptionList, orderStartTime);
    }
    return orderCourseOptionList;
  }

  /**
   * 
   * Title: 查询学员合同中的课程数<br>
   * Description: findOrderCourseInfoListMergeCourseType<br>
   * CreateDate: 2017年7月21日 下午4:42:46<br>
   * 
   * @category 查询学员合同中的课程数
   * @author seven.gz
   * @param userId
   *          用户id
   */
  public List<FindUserEffectiveContractParam> findOrderCourseInfoListMergeCourseType(String userId)
      throws Exception {
    // 用户合同执行中的课程原始数据（sql里有排序，先是is_gift = 0非赠送的课时，再是赠送的课时）
    List<OrderCourseOptionParam> orderCourseOptionList = findOrderCourseInfoList(userId);

    // 查询各种课程类型的补课数
    List<FindSubscribeSupplementCountParam> supplementCountList = subscribeSupplementEntityDao
        .findCountsByUserId(userId);

    // 存放各类型的补课数
    Map<String, Integer> supplementCountMap = new HashMap<String, Integer>();
    if (supplementCountList != null && supplementCountList.size() > 0) {
      for (FindSubscribeSupplementCountParam supplementCount : supplementCountList) {
        supplementCountMap.put(supplementCount.getCourseType(), supplementCount.getCourseCount());
      }
    }

    // 遍历将相同的课程类型合并
    Map<String, FindUserEffectiveContractParam> returnDataMap =
        new HashMap<String, FindUserEffectiveContractParam>();

    if (orderCourseOptionList != null && orderCourseOptionList.size() > 0) {
      FindUserEffectiveContractParam findUserEffectiveContractParam = null;
      for (OrderCourseOptionParam orderCourseOptionParam : orderCourseOptionList) {
        findUserEffectiveContractParam = returnDataMap.get(orderCourseOptionParam.getCourseType());
        if (findUserEffectiveContractParam == null) {
          findUserEffectiveContractParam = new FindUserEffectiveContractParam();
          PropertyUtils.copyProperties(findUserEffectiveContractParam, orderCourseOptionParam);
          returnDataMap.put(findUserEffectiveContractParam.getCourseType(),
              findUserEffectiveContractParam);
        } else {
          findUserEffectiveContractParam.setRemainCourseCount(findUserEffectiveContractParam
              .getRemainCourseCount() + orderCourseOptionParam.getRemainCourseCount());
          findUserEffectiveContractParam.setShowCourseCount(findUserEffectiveContractParam
              .getShowCourseCount() + orderCourseOptionParam.getShowCourseCount());
        }

        // 如果不是按节算就是天
        if (findUserEffectiveContractParam.getCourseUnitType() == null
            || !OrderCourseConstant.COURSE_UNIT_TYPE_CLASS.equals(findUserEffectiveContractParam
                .getCourseUnitType().toString())) {
          findUserEffectiveContractParam.setCourseUnitType(Integer.valueOf(
              OrderCourseConstant.COURSE_UNIT_TYPE_DAY));
        }

        // 设置补课数
        findUserEffectiveContractParam.setSupplementCount(supplementCountMap.get(
            findUserEffectiveContractParam.getCourseType()));
      }
    }

    List<FindUserEffectiveContractParam> returnList = new ArrayList<FindUserEffectiveContractParam>(
        returnDataMap.values());

    return returnList;
  }
}