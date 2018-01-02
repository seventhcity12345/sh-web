package com.webi.hwj.ordercourse.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;
import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.admin.service.AdminBdminUserService;
import com.webi.hwj.bean.JsonCodeMessage;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.coursepackageprice.dao.CoursePackagePriceDao;
import com.webi.hwj.coursepackageprice.entity.CoursePackagePrice;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseDao;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseOptionDao;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseSplitDao;
import com.webi.hwj.ordercourse.dto.AdminOrderCourseDto;
import com.webi.hwj.ordercourse.dto.OrderCourseDto;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseEntityDao;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseOptionEntityDao;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseSplitEntityDao;
import com.webi.hwj.ordercourse.param.CrmUpdateOrderMoneyParam;
import com.webi.hwj.ordercourse.param.OrderCourseOptionCountParam;
import com.webi.hwj.ordercourse.util.OrderContractStatusUtil;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseService;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.user.dao.AdminUserDao;
import com.webi.hwj.user.dao.AdminUserInfoDao;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.util.CrmUtil;

import net.sf.json.JSONObject;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminOrderCourseService {
  private static Logger logger = Logger.getLogger(AdminOrderCourseService.class);
  @Resource
  AdminOrderCourseDao adminOrderCourseDao;

  @Resource
  AdminOrderCourseOptionDao adminOrderCourseOptionDao;

  @Resource
  AdminUserDao adminUserDao;

  @Resource
  AdminUserInfoDao adminUserInfoDao;

  @Resource
  UserDao userDao;

  @Resource
  AdminOrderCourseSplitDao adminOrderCourseSplitDao;

  @Resource
  SubscribeCourseService subscribeCourseService;

  @Resource
  BaseSubscribeCourseService baseSubscribeCourseService;

  @Resource
  AdminBdminUserService adminBdminUserService;

  @Resource
  TellmemoreService tellmemoreService;

  @Resource
  CoursePackagePriceDao coursePackagePriceDao;

  @Resource
  UserEntityDao userEntityDao;

  @Resource
  private AdminOrderCourseEntityDao adminOrderCourseEntityDao;

  @Resource
  private AdminOrderCourseOptionEntityDao adminOrderCourseOptionEntityDao;

  @Resource
  private AdminOrderCourseSplitEntityDao adminOrderCourseSplitEntityDao;

  /**
   * Title: 合同延期<br>
   * Description: 合同延期<br>
   * CreateDate: 2017年3月9日 下午5:07:45<br>
   * 
   * @category 合同延期
   * @author komi.zsy
   * @param orderId
   *          合同id
   * @param limitShowTime
   *          延期时长
   * @param limitShowTimeUnit
   *          延期时效单位
   * @param updateUserId
   *          更新人id
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public CommonJsonObject updateContractExtension(String orderId, int limitShowTime,
      int limitShowTimeUnit, String updateUserId) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 根据合同id查询合同信息
    OrderCourse orderCourseObj = adminOrderCourseEntityDao.findOneByKeyIdWithExtension(orderId);
    if (orderCourseObj == null
        || !OrderStatusConstant.ORDER_STATUS_HAVE_PAID.equals(orderCourseObj
            .getOrderStatus() + "")) {
      // 如果没有合同数据，或者合同不是正在执行中，则返回错误
      json.setCode(ErrorCodeEnum.ORDER_NOT_EXIST.getCode());
      json.setMsg("只有正在执行中的合同可以延期！");
    } else {
      // 有正在执行中的合同，计算延期后的合同结束日期
      Calendar endCal = Calendar.getInstance();
      endCal.setTime(orderCourseObj.getEndOrderTime());
      switch (limitShowTimeUnit) {
        case OrderCourseConstant.LIMIT_SHOW_TIME_UNIT_MONTH:
          endCal.add(Calendar.MONTH, limitShowTime);
          // 更新合同子表课程数量
          adminOrderCourseOptionEntityDao.updateOrderCourseOptionByExtension(limitShowTime,
              orderId);
          break;
        case OrderCourseConstant.LIMIT_SHOW_TIME_UNIT_DAY:
          endCal.add(Calendar.DATE, limitShowTime);
          break;
        default:
          json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
          json.setMsg("合同时效单位错误！！！");
          return json;
      }

      // 更新合同结束日期
      OrderCourse orderCourseParam = new OrderCourse();
      orderCourseParam.setKeyId(orderId);
      orderCourseParam.setEndOrderTime(endCal.getTime());
      orderCourseParam.setUpdateUserId(updateUserId);
      orderCourseParam.setDataDesc(orderCourseObj.getDataDesc()
          + "合同延期:"
          + DateUtil.dateToStrYYMMDD(orderCourseObj.getEndOrderTime()) + ","
          + limitShowTime + "," + limitShowTimeUnit + ";");
      int num = adminOrderCourseEntityDao.update(orderCourseParam);
      if (num == 0) {
        throw new RuntimeException("合同延期失败！！！");
      }
    }
    return json;
  }

  /**
   * 
   * Title: 找到 需要（能够）续约的全部合同<br>
   * Description: findNeedRenewalOrderCourseListByUserId<br>
   * CreateDate: 2016年3月29日 下午2:52:23<br>
   * 
   * @category 找到 需要（能够）续约的全部合同
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findRenewalOrderCourseListByUserId(Map<String, Object> paramMap)
      throws Exception {
    return adminOrderCourseDao.findRenewalOrderCourseListByUserId(paramMap);
  }

  /**
   * 
   * Title: 找是否有未支付的合同（拟定合同的时候需要判断当前用户是否有没支付完成的订单，如果有的话，不能让管理员拟定合同。）<br>
   * Description: findNotPaidOrderCourseByUserId<br>
   * CreateDate: 2016年1月11日 下午4:38:40<br>
   * 
   * @category 找是否有未支付的合同（拟定合同的时候需要判断当前用户是否有没支付完成的订单，如果有的话，不能让管理员拟定合同。）
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findNotPaidOrderCourseByUserId(Map<String, Object> paramMap)
      throws Exception {
    return adminOrderCourseDao.findNotPaidOrderCourseByUserId(paramMap);
  }

  /**
   * Title: 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的）<br>
   * Description: 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的）<br>
   * CreateDate: 2017年3月20日 下午3:47:52<br>
   * 
   * @category 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的）
   * @author komi.zsy
   * @param userId
   *          用户id
   * @return
   * @throws Exception
   */
  public int findNotOrderCourseByUserId(String userId)
      throws Exception {
    return adminOrderCourseEntityDao.findNotOrderCourseByUserId(userId);
  }

  /**
   * @category orderCourse 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return adminOrderCourseDao.insert(fields);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findListEasyui(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminOrderCourseDao.findListEasyui(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminOrderCourseDao.findList(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @param pageNumber
   *          当前页数
   * @param pageSize
   *          每页显示多少条数据
   * @return
   * @throws Exception
   */
  public Page findPage(Map<String, Object> paramMap) throws Exception {
    return adminOrderCourseDao.findPage(paramMap);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
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
    return adminOrderCourseDao.findPageEasyui("*", paramMap);
  }

  /**
   * Title: 查询合同列表<br>
   * Description: findOrderCourseList<br>
   * CreateDate: 2016年1月13日 上午12:40:54<br>
   * 
   * @category 查询合同列表
   * @author yangmh
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findOrderCourseList(Map<String, Object> paramMap) throws Exception {
    /**
     * modify by athrun.cw 2016年3月9日16:20:54
     * 
     * 2.在后台合同列表中，添加一列：合同状态，要显示订单状态，合同状态2列。 需要统一调用前台合同&订单状态的工具类
     * 
     * 3.合同列表添加学生来源。 学生来源(0:常规,1:线下售卖,2:线下转线上)
     */

    // modify by seven 2017年4月20日15:21:34 cc只能看自己的合同
    Page orderCoursePage;
    if (StringUtils.isEmpty((String) paramMap.get("ccAdminUserId"))) {
      orderCoursePage = adminOrderCourseDao.findOrderCourseList(paramMap);
    } else {
      orderCoursePage = adminOrderCourseDao.findOrderCourseListForCc(paramMap);
    }

    List<Map<String, Object>> orderCourseList = orderCoursePage.getDatas();
    logger.debug("1.合同状态处理前orderCourseList：" + orderCourseList);
    for (Map<String, Object> orderCourseMap : orderCourseList) {
      // 把订单状态1-7格式化为合同状态1-3
      orderCourseMap = OrderContractStatusUtil.transOrderAndContractStatus(orderCourseMap);
      /**
       * modify by athrun.cw 2016年3月10日16:48:23
       * 
       * 合同列表中添加学生来源
       */
      orderCourseMap = OrderContractStatusUtil.formatUserFromType(orderCourseMap);

      /**
       * modefied by komi 2016年8月31日11:52:28 增加查询价格策略过期时间
       * 老数据没有这个值，需要判断,没有价格政策的合同不能修改（同价格政策过期处理，给过期时间赋一个早于当前时间的值）
       */
      if (orderCourseMap.get("course_package_price_option_id") != null) {
        CoursePackagePrice coursePackagePrice = coursePackagePriceDao
            .findEndTimeByOptionId(orderCourseMap.get("course_package_price_option_id").toString());
        if (coursePackagePrice != null) {
          orderCourseMap.put("course_package_price_id", coursePackagePrice.getKeyId());
          orderCourseMap.put("package_price_end_time",
              coursePackagePrice.getPackagePriceEndTime().getTime());

        } else {
          orderCourseMap.put("package_price_end_time", new Date().getTime() - 24 * 60 * 60 * 1000);
        }
      } else {
        orderCourseMap.put("package_price_end_time", new Date().getTime() - 24 * 60 * 60 * 1000);
      }

      /**
       * modify by athrun.cw 2016年4月8日16:47:37 续约
       */
      Object from_path = orderCourseMap.get("from_path");
      if (from_path != null) {
        String[] fromPathArr = from_path.toString().split(",");
        int lengthArr = fromPathArr.length;
        // 有续约的 才找
        if (lengthArr >= 2) {
          // 找该续约合同的源合同
          // modify by seven 2017年5月11日 修改删除续约的源合同查询会报错的问题
          Map<String, Object> resourceRenewalOrderCourseMap = adminOrderCourseDao
              .findOneByKeyIdWithNoIsUsed(
                  fromPathArr[lengthArr - 2]);
          orderCourseMap.put("renewaled_limit_show_time",
              resourceRenewalOrderCourseMap.get("limit_show_time"));
          orderCourseMap.put("renewaled_limit_show_time_unit",
              resourceRenewalOrderCourseMap.get("limit_show_time_unit"));
          orderCourseMap.put("renewaled_total_final_price",
              resourceRenewalOrderCourseMap.get("total_final_price"));
          orderCourseMap.put("renewaled_total_real_price",
              resourceRenewalOrderCourseMap.get("total_real_price"));
        }

      }
    }
    logger.debug("2.合同&&订单状态处理后orderCourseList：" + orderCourseList);
    return orderCoursePage;
  }

  /**
   * @category 修改数据
   * @author mingyisoft代码生成工具
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return adminOrderCourseDao.update(fields);
  }

  /**
   * @category 查询单条数据(通过参数MAP)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminOrderCourseDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return adminOrderCourseDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return adminOrderCourseDao.delete(ids);
  }

  /**
   * @category 查询数量
   * @author mingyisoft代码生成工具
   * @param sql
   * @param map
   * @return
   * @throws Exception
   */
  public int findCount(Map<String, Object> map) throws Exception {
    return adminOrderCourseDao.findCount(map);
  }

  /**
   * @category 查询总数
   * @author mingyisoft代码生成工具
   * @param map
   * @param sumField
   *          sum的字段
   * @return
   * @throws Exception
   */
  public int findSum(Map<String, Object> map, String sumField) throws Exception {
    return adminOrderCourseDao.findSum(map, sumField);
  }

  /**
   * 
   * Title: 通用：处理续约的option课程数<br>
   * Description: commonOperateRnewalOrderCourseOption<br>
   * CreateDate: 2016年4月1日 下午2:49:55<br>
   * 
   * @category 处理续约的option课程数
   * @author athrun.cw
   * @param operateMap
   * @param orderCourseOptionMapList
   * @return
   */
  public Map<String, Object> commonOperateRnewalOrderCourseOption(Map<String, Object> operateMap,
      List<Map<String, Object>> orderCourseOptionMapList) {

    return operateMap;
  }

  /**
   * @category 查询合同所有信息
   * @param key_id
   *          合同ID
   * @author vector.mjp
   */
  public Map<String, Object> findContractByKeyId(String keyId) throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("key_id", keyId);

    Map<String, Object> result = new HashMap<String, Object>();
    // 查询合同的人员相关信息：学员+销售
    List<Map<String, Object>> userInfos = adminOrderCourseDao.findContractUser(map);
    if (userInfos != null && userInfos.size() > 0) {
      result.put("contract", adminOrderCourseDao.findContractUser(map).get(0));
    }

    // 查询合同的相关信息：
    List<Map<String, Object>> packageInfos = adminOrderCourseDao.findOrderPackeageInfo(map);
    if (packageInfos != null && packageInfos.size() > 0) {
      result.put("pkg", adminOrderCourseDao.findOrderPackeageInfo(map).get(0));
    }
    // 查询合同的课程相关信息
    List<Map<String, Object>> courseInfos = adminOrderCourseDao.findOrderCourses(map);

    // 取一下课程名
    for (Map<String, Object> course : courseInfos) {
      course.put("course_type",
          ((CourseType) MemcachedUtil.getValue(course.get("course_type").toString()))
              .getCourseTypeChineseName());
    }
    result.put("courses", courseInfos);

    return result;
  }

  /**
   * 
   * Title: 删除合同（order_id）<br>
   * Description: deleteOrderCourse<br>
   * CreateDate: 2016年1月20日 下午3:32:08<br>
   * 
   * @category 删除合同（order_id）
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public CommonJsonObject deleteOrderCourse(Map<String, Object> paramMap, String updateUserId)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 找到该合同信息
    Map<String, Object> orderCourseMap = adminOrderCourseDao
        .findOneByKeyId(paramMap.get("order_id"), "*");
    if (orderCourseMap == null) {
      logger.error("合同id：" + paramMap.get("order_id") + "不存在！");
      throw new RuntimeException("合同id：" + paramMap.get("order_id") + "不存在！");
    }

    logger.info("将要删除的合同详细信息：" + orderCourseMap);

    String userId = orderCourseMap.get("user_id").toString();

    // 1.删除合同主表
    // modify by seven 2017年5月23日14:48:45 删除合同
    adminOrderCourseDao.deleteByKeyIdWithUpdateUserId(orderCourseMap.get("key_id").toString(),
        updateUserId);

    // 2.删除关联的split表
    Map<String, Object> deleteOrderCourseSplitMap = new HashMap<String, Object>();
    deleteOrderCourseSplitMap.put("order_id", paramMap.get("order_id"));
    adminOrderCourseSplitDao.deleteOrderCourseSplitByOrderId(deleteOrderCourseSplitMap);

    /**
     * 3.更新学员表 需要判断该学员是否还有其他的有效的合同，没有，才置为非学员
     */
    Map<String, Object> findOrderCourseMap = new HashMap<String, Object>();
    findOrderCourseMap.put("user_id", userId);
    findOrderCourseMap.put("order_status", OrderStatusConstant.ORDER_STATUS_HAVE_PAID);
    List<Map<String, Object>> orderCourseMapList = adminOrderCourseDao
        .findOrderCourseByUserId(findOrderCourseMap);

    // rsa是否删除学员flag
    boolean isDeleteTmmUser = false;

    if (orderCourseMapList == null || orderCourseMapList.size() == 0) {
      // 没有其他的有效的合同了
      Map<String, Object> updateUserMap = new HashMap<String, Object>();
      updateUserMap.put("key_id", userId);
      updateUserMap.put("is_student", 0);
      userDao.update(updateUserMap);

      // modified by komi 2016年8月16日10:59:28 用户已不是学员，则删除rsa数据
      isDeleteTmmUser = true;

      logger.info("学员id：" + userId + " 无其他的有效合同，仍然是学员~");
    } else {
      logger.info("学员id：" + userId + " 还有其他的有效合同，仍然是学员~");
    }

    /**
     * 4.找是否order_id 还有就已经预约的课程，取消预约
     */
    Map<String, Object> findSubecribeCourseMap = new HashMap<String, Object>();
    findSubecribeCourseMap.put("order_id", orderCourseMap.get("key_id"));
    List<Map<String, Object>> subecribeCourseMapList = subscribeCourseService.findList(paramMap,
        "*");

    if (subecribeCourseMapList == null || subecribeCourseMapList.size() == 0) {
      logger.info("学员id：" + userId + "当前没有预约的课程~");
    } else {
      for (Map<String, Object> subecribeCourseMap : subecribeCourseMapList) {
        // 只有未上课 && 还未到上课时间的，才走取消预约流程
        String subscribe_status = subecribeCourseMap.get("subscribe_status").toString();
        Date start_time = (Date) subecribeCourseMap.get("start_time");
        logger.debug("用户将要取消预约的课程subscribeId=" + subecribeCourseMap.get("key_id")
            + "需要取消预约，预约记录是subecribeCourseMap=" + subecribeCourseMap);
        if ("0".equals(subscribe_status) && System.currentTimeMillis() < start_time.getTime()) {
          logger.debug("用户取消预约的课程subscribeId=" + subecribeCourseMap.get("key_id")
              + "需要取消预约，预约记录是subecribeCourseMap=" + subecribeCourseMap);

          String course_type = subecribeCourseMap.get("course_type").toString();
          Map<String, Object> cancelSubecribeCourseMap = new HashMap<String, Object>();
          cancelSubecribeCourseMap.put("key_id", subecribeCourseMap.get("key_id"));
          cancelSubecribeCourseMap.put("user_id", userId);

          /**
           * modified by komi 2016年12月14日11:21:30 改为通用取消预约
           */
          // 组装subscribeCourse对象
          SubscribeCourse subscribeCourse = new SubscribeCourse();
          subscribeCourse.setKeyId(subecribeCourseMap.get("key_id").toString());
          subscribeCourse.setUpdateUserId(updateUserId);
          User user = userEntityDao.findUserByUserIdOrPhone(orderCourseMap.get("user_id")
              .toString());
          if (user == null) {
            json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
            json.setMsg("请选择一名用户");
            throw new RuntimeException("用户id：" + paramMap.get("user_id") + "不存在！");
          }
          subscribeCourse.setUserPhone(user.getPhone());
          subscribeCourse.setSubscribeFrom("admin");

          json = baseSubscribeCourseService.cancelSubscribeEntry(subscribeCourse);

          logger.info("取消预约课程id" + subecribeCourseMap.get("key_id") + "成功！");
        } else {
          logger.info("用户预约的课程subscribeId=" + subecribeCourseMap.get("key_id") + "不需要取消预约...");
        }
      }
    }

    /**
     * modified by komi 2016年8月16日11:06:00 需求287，增加删除rsa学员账号功能 在学员没有其他合同时才删除
     * 必须写在最后，防止数据库回滚
     */
    if (isDeleteTmmUser) {
      logger.info("开始查询要删除的TMM用户数据--------->userId = " + userId);
      tellmemoreService.deleteTmmUser(userId);
      logger.info("删除TMM用户数据成功--------->userId = " + userId);
    }

    json.setMsg("删除订单成功！");

    // modified by ivan.mgh，2016年5月11日17:24:44
    // ---------------start-----------------
    // 调用“同步合同状态”CRM接口，通知CRM系统，该合同已被删除
    // speakhi中并没有“已删除”状态，此处不需要调用公用更新合同状态方法
    sendSynchronizeContractStatusMessage(orderCourseMap.get("key_id").toString());
    // ---------------end-----------------

    return json;
  }

  /**
   * 
   * Title: 撤回合同<br>
   * Description: withdrawOrderCourse<br>
   * CreateDate: 2016年1月22日 下午3:24:19<br>
   * 
   * @category 撤回合同
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public JsonMessage withdrawOrderCourse(Map<String, Object> paramMap) throws Exception {
    JsonMessage json = new JsonMessage();
    // 找到该合同信息
    Map<String, Object> orderCourseMap = adminOrderCourseDao
        .findOneByKeyId(paramMap.get("order_id"), "*");
    if (orderCourseMap == null) {
      json.setSuccess(false);
      json.setMsg("合同id：" + paramMap.get("order_id") + "不存在！");
      logger.error("合同id：" + paramMap.get("order_id") + "不存在！");
      throw new RuntimeException("合同id：" + paramMap.get("order_id") + "不存在！");
    }

    // 查找合同拆分订单申请中的数据（招联百度专用！！！）为0则代表没有申请中的数据，可以修改合同
    int isApplySplitNum = adminOrderCourseSplitEntityDao.findOneByOrderIdAndStatus(paramMap.get(
        "order_id").toString());

    String order_status = orderCourseMap.get("order_status").toString();
    if (isApplySplitNum == 0
        && (OrderStatusConstant.ORDER_STATUS_HAVE_PLANNED.equals(order_status)
            || OrderStatusConstant.ORDER_STATUS_HAVE_SENT.equals(order_status)
            || OrderStatusConstant.ORDER_STATUS_HAVE_CONFIRMED.equals(order_status))) {

      // modified by ivan.mgh，2016年5月24日10:34:40
      // 合同状态变更为“已拟定”，生产消息
      updateOrderStatus(orderCourseMap.get("key_id").toString(),
          OrderStatusConstant.ORDER_STATUS_HAVE_PLANNED);

      json.setMsg("撤回订单成功！");
    } else {
      json.setSuccess(false);
      json.setMsg("此合同已产生付款记录，不可撤回！");
      logger.error("合同id：" + paramMap.get("order_id") + "此合同已产生付款记录，不可撤回！");
      throw new RuntimeException("合同id：" + paramMap.get("order_id") + "此合同已产生付款记录，不可撤回！");
    }

    return json;
  }

  /**
   * 
   * Title: 后台 合同详情分页查询<br>
   * Description: findOrderCourseByUserIdEasyui<br>
   * CreateDate: 2016年3月28日 下午4:04:56<br>
   * 
   * @category 后台 合同详情分页查询
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findOrderCourseByUserIdEasyui(Map<String, Object> paramMap) throws Exception {
    Page page = adminOrderCourseDao.findOrderCourseByUserIdEasyui(paramMap);

    List<Map<String, Object>> orderCourseList = page.getDatas();
    List<String> orderIds = new ArrayList<String>();
    if (orderCourseList != null && orderCourseList.size() != 0) {
      for (Map<String, Object> orderCourseMap : orderCourseList) {
        orderIds.add(orderCourseMap.get("key_id").toString());
      }
      // 处理合同的课时数数据
      Map<String, Object> courseMap = formatOrderCourseOptionInfo(orderIds);

      for (Map<String, Object> orderCourseMap : orderCourseList) {
        orderCourseMap.put("courseCountStr", courseMap.get(orderCourseMap.get("key_id")));
        // orderCourseMap.put("courseTypeGroupName",
        // MemcachedUtil.getConfigValue(orderCourseMap.get("course_type_group").toString()));
      }
    }
    return page;
  }

  /**
   * 
   * Title: 合同续约时，拟定好合同后管理员可以点击续约合同按钮调用接口 将续约合同的父类（根）迭代 全部查询出来<br>
   * Description: findRenewalOrderCourseDetailByOrderId<br>
   * CreateDate: 2016年3月28日 下午5:12:36<br>
   * 
   * @category 合同续约时，拟定好合同后管理员可以点击续约合同按钮调用接口 将续约合同的父类（根）迭代 全部查询出来
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findRenewalOrderCourseDetailByOrderId(Map<String, Object> paramMap)
      throws Exception {
    Map<String, Object> renewalOrderCourseMap = new HashMap<String, Object>();

    // 找到续约的合同记录，子类、父类...的path
    Map<String, Object> orderCourseMap = adminOrderCourseDao
        .findOneByKeyId(paramMap.get("order_id"), "from_path");
    if (orderCourseMap == null) {
      renewalOrderCourseMap.put("code", "500");
      renewalOrderCourseMap.put("msg", "合同续约明细接口失败：找不到该续约合同信息！");
      logger.error("合同续约明细接口失败：找不到该续约合同信息！");
      return renewalOrderCourseMap;
    }

    List<String> keyIds = Arrays.asList(orderCourseMap.get("from_path").toString().split(","));
    logger.debug("该续约合同以及父级合同共有：" + keyIds.size() + "个，keyIds = " + keyIds);

    Map<String, Object> keyIdsMap = new HashMap<String, Object>();
    keyIdsMap.put("keyIds", keyIds);
    List<Map<String, Object>> orderCourseMapList = adminOrderCourseDao
        .findOrderCourseByKeyIds(keyIdsMap);
    if (orderCourseMapList == null || orderCourseMapList.size() == 0) {
      renewalOrderCourseMap.put("code", "500");
      renewalOrderCourseMap.put("msg", "合同续约明细接口失败：数据错误，找不到续约信息！");
      logger.error("合同续约明细接口失败：数据错误，找不到续约信息！");
      return renewalOrderCourseMap;
    }

    renewalOrderCourseMap.put("order_list", orderCourseMapList);
    renewalOrderCourseMap.put("code", "200");
    renewalOrderCourseMap.put("msg", "合同续约明细接口调用成功~~~");
    return renewalOrderCourseMap;
  }

  /**
   * Title: 同步CRM合同状态，生产消息<br>
   * Description: 同步CRM合同状态，生产消息，加入消息队列<br>
   * CreateDate: 2016年5月12日 下午1:37:05<br>
   * 
   * @category 同步CRM合同状态，生产消息
   * @author ivan.mgh
   * @param contractGuid
   *          合同号
   * @param contractState
   *          合同状态
   * @throws Exception
   */
  public void sendSynchronizeContractStatusMessage(String contractGuid) throws Exception {
    // 如果是CRM中创建中创建的合同，才需要加入到消息队列同步合同状态
    if (isCrmContract(contractGuid)) {
      logger.info("同步合同状态，生产者开始生产消息，合同号：" + contractGuid);

      try {
        // 生产消息，加入消息队列
        OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
            MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "tag_crm_contract_status",
            contractGuid, 60 * 1000);
      } catch (Exception e) {
        // 异常时发短信通知
        SmsUtil.sendAlarmSms(
            "消息队列报警,同步CRM合同状态时无法生产消息,sendSynchronizeContractStatusMessage,"
                + contractGuid);

        e.printStackTrace();
        logger.error("error:" + e.toString());
      }
    }
  }

  /**
   * Title: 同步SpeakHi合同状态<br>
   * Description: 同步SpeakHi合同状态到CRM<br>
   * CreateDate: 2016年3月28日 下午4:50:30<br>
   * 
   * @category 同步SpeakHi合同状态
   * @author ivan.mgh
   * @param orderCourseDto
   *          同步合同状态数据传输对象（包括合同号，合同状态）
   * @return
   * @throws Exception
   */
  public JsonCodeMessage synchronizeContractStatus(String contractGuid) throws Exception {
    OrderCourseDto orderCourseDto = new OrderCourseDto();
    orderCourseDto.setContractGuid(contractGuid);

    // 从数据库中查到订单当前状态
    Map<String, Object> orderCourseMap = findOneByKeyId(orderCourseDto.getContractGuid(),
        "order_status,is_used");
    logger.info("-------------------order_status-----------------" + contractGuid + ","
        + orderCourseMap.get("order_status").toString());

    if (((Boolean) orderCourseMap.get("is_used")).booleanValue()) {
      orderCourseDto.setContractState(orderCourseMap.get("order_status").toString());
    } else {
      // 合同被删除
      orderCourseDto.setContractState(OrderStatusConstant.ORDER_STATUS_HAVE_DELETED);
    }

    logger.info("同步合同状态，消费者开始消费消息，合同号：" + orderCourseDto.getContractGuid() + "，合同状态："
        + orderCourseDto.getContractState());

    JsonCodeMessage jsonCodeMessage = null;

    // 合同号和合同状态均不能为空
    if (StringUtils.isNotBlank(orderCourseDto.getContractGuid())
        && StringUtils.isNotBlank(orderCourseDto.getContractState())) {
      String url = MemcachedUtil.getConfigValue("crm_synchronize_contract_status_url");
      logger.info("---------3------------" + url);
      String privateKey = MemcachedUtil.getConfigValue("crm_private_key");
      logger.info("---------4------------" + privateKey);

      JSONObject jsonObject = JSONObject.fromObject(orderCourseDto);
      // 消息体
      String jsonMessageBody = jsonObject.toString();

      // 签名
      String token = SHAUtil.encode(jsonMessageBody + privateKey);

      Map<String, String> headerMap = new HashMap<>();
      headerMap.put("token", token);

      String result = HttpClientUtil.doPostByJson(url, jsonMessageBody, headerMap);
      logger.info("同步speakhi合同状态响应----------------------------------------->" + result);

      JSONObject resultJSONObject = JSONObject.fromObject(result);
      jsonCodeMessage = (JsonCodeMessage) JSONObject.toBean(resultJSONObject,
          JsonCodeMessage.class);

      if (!"200".equals(jsonCodeMessage.getCode())) {
        logger.error(
            "同步合同状态返回错误,code=" + jsonCodeMessage.getCode() + ",msg=" + jsonCodeMessage.getMsg());
        throw new RuntimeException(
            "同步合同状态返回错误,code=" + jsonCodeMessage.getCode() + ",msg=" + jsonCodeMessage.getMsg());
      }
    } else {
      logger.error("合同号和合同状态均不能为空");
      // 抛异常
      throw new RuntimeException("合同号和合同状态均不能为空");
    }
    return jsonCodeMessage;
  }

  /**
   * Title: 补充合同信息，生产消息<br>
   * Description: 补充合同信息，生产消息，加入消息队列<br>
   * CreateDate: 2016年5月12日 下午1:47:37<br>
   * 
   * @category 补充合同信息，生产消息
   * @author ivan.mgh
   * @param orderCourseMap
   * @throws Exception
   */
  public void sendSynchronizeContractInfoMessage(Map<String, Object> orderCourseMap)
      throws Exception {
    AdminOrderCourseDto adminOrderCourseDto = new AdminOrderCourseDto();
    adminOrderCourseDto.setLeadid(orderCourseMap.get("lead_id").toString());
    adminOrderCourseDto.setContractGuid(orderCourseMap.get("key_id").toString());
    adminOrderCourseDto.setContractState(orderCourseMap.get("order_status").toString());
    adminOrderCourseDto.setContractMoney(orderCourseMap.get("total_show_price").toString());
    adminOrderCourseDto.setDiscountMoney(orderCourseMap.get("total_real_price").toString());
    adminOrderCourseDto.setActualMoney(orderCourseMap.get("total_final_price").toString());

    JSONObject jsonObject = JSONObject.fromObject(adminOrderCourseDto);
    // 消息体
    String JsonMessageBody = jsonObject.toString();

    logger.info("同步合同信息，生产者开始生产消息，合同号：" + adminOrderCourseDto.getContractGuid());

    try {
      // 生产消息，加入消息队列
      OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
          MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "tag_crm_contract_info",
          JsonMessageBody);
    } catch (Exception e) {
      // 异常时发短信通知
      SmsUtil.sendAlarmSms(
          "消息队列报警,补充CRM合同信息时无法生产消息,sendSynchronizeContractInfoMessage," + adminOrderCourseDto
              .getContractGuid());

      e.printStackTrace();
      logger.error("error:" + e.toString());
    }
  }

  /**
   * Title: 合同信息补充<br>
   * Description: 调用合同信息补充接口<br>
   * CreateDate: 2016年5月12日 上午11:31:10<br>
   * 
   * @category 合同信息补充
   * @author ivan.mgh
   * @param JsonMessageBody
   *          消息体（JSON串）
   * @return
   * @throws Exception
   */
  public JsonCodeMessage synchronizeContractInfo(String jsonMessageBody) throws Exception {
    JSONObject messageBodyJSONObject = JSONObject.fromObject(jsonMessageBody);
    AdminOrderCourseDto adminOrderCourseDto = (AdminOrderCourseDto) JSONObject.toBean(
        messageBodyJSONObject,
        AdminOrderCourseDto.class);

    logger.info("同步合同信息，消费者开始消费消息，合同号：" + adminOrderCourseDto.getContractGuid());

    JsonCodeMessage jsonCodeMessage = null;

    // SpeakHi合同号、合同总金额、优惠金额、实收金额、合同状态、CRM客户唯一GUID均不能为空
    if (StringUtils.isNotBlank(adminOrderCourseDto.getContractGuid())
        && StringUtils.isNotBlank(adminOrderCourseDto.getActualMoney())
        && StringUtils.isNotBlank(adminOrderCourseDto.getContractMoney())
        && StringUtils.isNotBlank(adminOrderCourseDto.getDiscountMoney())
        && StringUtils.isNotBlank(adminOrderCourseDto.getLeadid())
        && StringUtils.isNotBlank(adminOrderCourseDto.getContractState())) {

      String url = MemcachedUtil.getConfigValue("crm_synchronize_contract_info_url");
      logger.info("---------1------------" + url);

      String privateKey = MemcachedUtil.getConfigValue("crm_private_key");
      logger.info("---------2------------" + privateKey);

      // 签名
      String token = SHAUtil.encode(jsonMessageBody + privateKey);

      Map<String, String> headerMap = new HashMap<>();
      headerMap.put("token", token);

      String result = HttpClientUtil.doPostByJson(url, jsonMessageBody, headerMap);
      logger.info("合同信息补充接口----------------------------------------->" + result);

      JSONObject resultJSONObject = JSONObject.fromObject(result);
      jsonCodeMessage = (JsonCodeMessage) JSONObject.toBean(resultJSONObject,
          JsonCodeMessage.class);

      if (!"200".equals(jsonCodeMessage.getCode())) {
        logger.error(
            "同步合同信息返回错误,code=" + jsonCodeMessage.getCode() + ",msg=" + jsonCodeMessage.getMsg());
        throw new RuntimeException(
            "同步合同信息返回错误,code=" + jsonCodeMessage.getCode() + ",msg=" + jsonCodeMessage.getMsg());
      }
    } else {
      // 抛异常
      throw new RuntimeException("Speakhi合同号、合同总金额、优惠金额、实收金额、合同状态、CRM客户唯一GUID均不能为空");
    }
    return jsonCodeMessage;
  }

  /**
   * Title: 是否为CRM中创建的合同<br>
   * Description: 通过is_crm字段判断是否crm中创建的合同<br>
   * CreateDate: 2016年5月16日20:48:27<br>
   * 
   * @category 是否为CRM中创建的合同
   * @author ivan.mgh
   * @param contractGuid
   *          合同号
   * @return
   * @throws Exception
   */
  private boolean isCrmContract(String contractGuid) throws Exception {
    boolean isCrmContract = false;
    Map<String, Object> orderCourseMap = findOneByKeyId(contractGuid, "is_crm");
    if (null != orderCourseMap) {
      if (((Boolean) orderCourseMap.get("is_crm")).booleanValue()) {
        logger.info("合同：" + contractGuid + "，是否为CRM中创建的合同？TRUE");
        isCrmContract = true;
      }
    }
    return isCrmContract;
  }

  /**
   * Title: 更新合同状态通用方法<br>
   * Description: 更新合同状态通用方法，先更新合同状态到数据库，然后合同状态变更信息加入消息队列<br>
   * CreateDate: 2016年5月24日 上午10:16:41<br>
   * 
   * @category 更新合同状态通用方法
   * @author ivan.mgh
   * @param orderId
   * @param status
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void updateOrderStatus(String orderId, String status) throws Exception {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("key_id", orderId);

    /**
     * modified by komi 2016年6月28日11:39:31 查询合同原状态，如果是5，则不能再变回1，2，3，4
     */
    Map<String, Object> orderMap = adminOrderCourseDao.findOne(paramMap, "order_status");

    if (orderMap != null && OrderStatusConstant.ORDER_STATUS_HAVE_PAID
        .equals(orderMap.get("order_status").toString())) {
      if (OrderStatusConstant.ORDER_STATUS_HAVE_PLANNED.equals(status)
          || OrderStatusConstant.ORDER_STATUS_HAVE_SENT.equals(status)
          || OrderStatusConstant.ORDER_STATUS_HAVE_CONFIRMED.equals(status)
          || OrderStatusConstant.ORDER_STATUS_PAYING.equals(status)) {
        logger.error("合同企图把状态从5更改回" + status + "!!!");
        return;
      }
    }

    paramMap.put("order_status", status);

    // 更新合同状态
    adminOrderCourseDao.updateOrderStatusByOrderId(paramMap);

    // 同步合同状态
    sendSynchronizeContractStatusMessage(orderId);
  }

  /**
   * Title: 处理合同的课时数数据<br>
   * Description: 处理合同的课时数数据<br>
   * CreateDate: 2017年2月15日 下午3:32:40<br>
   * 
   * @category 处理合同的课时数数据
   * @author seven 青少拷贝的komi的方法
   * @param orderIds
   *          合同的id列表
   * @return 返回一个key为合同id，value为处理后的课时数字符串的map
   * @throws Exception
   */
  public Map<String, Object> formatOrderCourseOptionInfo(List<String> orderIds) throws Exception {
    List<OrderCourseOptionCountParam> orderCourseOptionParamList =
        adminOrderCourseOptionEntityDao
            .findListByOrderId(orderIds);
    // 用来放处理过后的课时数相关数据（为了不用n+1查询）
    Map<String, Object> courseMap = new HashMap<String, Object>();
    if (orderCourseOptionParamList != null && orderCourseOptionParamList.size() != 0) {
      // 用以排序，判断是否是新的合同

      // modify by seven 2017年3月31日10:18:54 修改显示不准确的问题
      // 存放合同的子项list
      Map<String, List<OrderCourseOptionCountParam>> optionListMap =
          new HashMap<String, List<OrderCourseOptionCountParam>>();
      // 遍历按合同放入list
      List<OrderCourseOptionCountParam> paramList = null;
      for (OrderCourseOptionCountParam orderCourseOptionParam : orderCourseOptionParamList) {
        String orderId = "";
        orderId = orderCourseOptionParam.getOrderId();
        paramList = optionListMap.get(orderId);
        if (paramList == null) {
          // 不同的合同id，需要新建一个list继续处理
          paramList = new ArrayList<OrderCourseOptionCountParam>();
          optionListMap.put(orderId, paramList);
        }
        paramList.add(orderCourseOptionParam);
      }

      // 遍历map
      if (optionListMap.size() > 0) {
        for (String key : optionListMap.keySet()) {
          paramList = optionListMap.get(key);
          // 处理list，
          List<OrderCourseOptionCountParam> formatList = OrderContractStatusUtil
              .formatRemainCourseCount(paramList);
          if (formatList != null && formatList.size() != 0) {
            StringBuffer courseStr = new StringBuffer();
            for (OrderCourseOptionCountParam mapFormatParam : formatList) {
              if ("course_type1".equals(mapFormatParam.getCourseType()) ||
                  "course_type2".equals(mapFormatParam.getCourseType())
                  || "course_type9".equals(mapFormatParam.getCourseType())) {

                // 组装字符串
                courseStr.append(((CourseType) MemcachedUtil.getValue(mapFormatParam
                    .getCourseType()))
                        .getCourseTypeChineseName());
                courseStr.append("(总");
                courseStr.append(mapFormatParam.getShowCourseCount());

                switch (mapFormatParam.getCourseUnitType().toString()) {
                  case OrderCourseConstant.COURSE_UNIT_TYPE_CLASS: {
                    courseStr.append("节/剩");
                    courseStr.append(mapFormatParam.getRemainCourseCount());
                    courseStr.append("节);");
                  }
                    break;
                  case OrderCourseConstant.COURSE_UNIT_TYPE_MONTH: {
                    courseStr.append("个月/");
                    courseStr.append(DateUtil.dateToStrYYMMDD(mapFormatParam.getCourseEndTime()));
                    courseStr.append(");");
                  }
                    break;
                  case OrderCourseConstant.COURSE_UNIT_TYPE_DAY: {
                    courseStr.append("天/");
                    courseStr.append(DateUtil.dateToStrYYMMDD(mapFormatParam.getCourseEndTime()));
                    courseStr.append(");");
                  }
                    break;

                  default:
                    break;
                }
              }
            }
            courseMap.put(key, courseStr);
          }

        }
      }
    }
    return courseMap;

  }

  /**
   * Title: 更换cc<br>
   * Description: changeCc<br>
   * CreateDate: 2017年3月28日 下午5:43:19<br>
   * 
   * @category 更换cc
   * @author seven.gz
   * @param orderId
   *          合同id
   * @param ccId
   *          ccid
   */
  public CommonJsonObject changeCc(String orderId, String ccId) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 将前台传过来的id列表分隔开
    String[] orderIds = orderId.split(",");
    List<OrderCourse> orderCourseList = new ArrayList<OrderCourse>();
    /**
     * modified by komi 2017年5月4日13:58:17 修改为批量修改
     */
    for (String orderIdStr : orderIds) {
      OrderCourse orderCourse = new OrderCourse();
      orderCourse.setKeyId(orderIdStr);
      orderCourse.setCreateUserId(ccId);
      orderCourseList.add(orderCourse);
    }
    adminOrderCourseEntityDao.batchUpdate(orderCourseList);
    return json;
  }

  /**
   * Title: 向crm推送变更合同金额<br>
   * Description: 向crm推送变更合同金额<br>
   * CreateDate: 2017年7月4日 下午6:11:29<br>
   * 
   * @category 向crm推送变更合同金额
   * @author komi.zsy
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @throws Exception
   */
  public void updateMoneyToCrm(Date startTime, Date endTime) throws Exception {
    // crm专用，查询一段时间内，支付过的用户的支付总额(最近一周)
    // Date curDate = new Date();
    // new Date(curDate.getTime() - 7 * 24 * 60 * 60 * 1000)
    final List<CrmUpdateOrderMoneyParam> crmUpdateOrderMoneyParamList = adminOrderCourseEntityDao
        .findUpdateMoneyToCrm(startTime, endTime);
    if (crmUpdateOrderMoneyParamList != null && crmUpdateOrderMoneyParamList.size() > 20) {
      /**
       *  因为crm接口反应很慢，所以用多线程同时跑，但是我觉得定时任务里跑多线程不是很好，因为定时任务本身就是多线程，而且之前也发生过线程被占满导致其他任务跑步起来的问题
       *  试验过后发现如果用多进程，crm那边不支持，可能会导致crm的文件进程被占用，导致失败，还是改为使用for循环
       */
      int i = 0;
      //一次几条
      int threadNum = 20;
      while (true) {
        if (i + threadNum >= crmUpdateOrderMoneyParamList.size()) {
          CrmUtil.updateMoneyToCrm(crmUpdateOrderMoneyParamList.subList(i,
              crmUpdateOrderMoneyParamList.size() - 1));
          break;
        } else {
          CrmUtil.updateMoneyToCrm(crmUpdateOrderMoneyParamList.subList(i, i
              + threadNum - 1));
        }
        i = i + threadNum;
      }

    } else {
      CrmUtil.updateMoneyToCrm(crmUpdateOrderMoneyParamList);
    }
    logger.info("同步金额结束");
  }

}
