package com.webi.hwj.ordercourse.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.dao.BaseMysqlDao;
import com.webi.hwj.constant.OrderStatusConstant;

@Repository
public class AdminOrderCourseDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(AdminOrderCourseDao.class);

  public AdminOrderCourseDao() {
    super.setTableName("t_order_course");
  }

  // modify by seven 2017年3月28日14:33:26 查询cc
  public static final String FIND_ORDERCOURSE_BY_USERID_EASYUI =
      " SELECT  toc.key_id,toc.user_id,toc.course_package_name,toc.order_remark, "
          + " toc.total_real_price,toc.total_show_price,"
          + " toc.total_final_price,toc.start_order_time, "
          + " toc.end_order_time,toc.order_status,toc.user_name,tbu.admin_user_name"
          + " FROM t_order_course toc LEFT JOIN t_badmin_user tbu "
          + " ON toc.create_user_id = tbu.key_id "
          + " WHERE toc.is_used <> 0 AND toc.user_id = :user_id ORDER BY toc.create_date DESC ";

  /**
   * 通过user_id找合同信息
   */
  public static final String FIND_ORDERCOURSE_BY_USERID = " SELECT key_id  "
      + " FROM t_order_course "
      + " WHERE is_used <> 0 "
      + " AND user_id = :user_id "
      + " AND order_status = :order_status ";

  /**
   * 查询合同列表
   */
  private static final String FIND_ORDER_COURSE_LIST =
      "SELECT core.key_id,u.`user_code`,core.user_name AS 'core.user_name',u.phone AS 'u.phone',bu.`admin_user_name`,"
          /**
           * modify by athrun.cw 2016年1月21日16:46:59 修改合同需要以下参数 course_package_id
           * & course_package_name & current_level & user_name & category_type
           */
          + "core.course_package_price_option_id, core.course_package_id, core.category_type,core.order_remark, "
          + "core.course_package_name, u.current_level, u.user_name, u.phone, "
          /**
           * modify by athrun.cw 2016年3月10日16:28:15
           * 
           * 合同列表中添加学生来源。 user_from_type
           */
          + " tu.idcard,  u.key_id as user_id, core.user_from_type, "
          /**
           * modify by athrun.cw 2016年4月1日16:05:51 order_original_type,
           * total_final_price
           */
          + " core.limit_show_time, core.limit_show_time_unit,core.from_path, "
          + " core.order_original_type, core.total_final_price, "
          + " core.total_show_price,core.key_id AS 'core.key_id', "
          + " core.create_date AS 'core.create_date',core.`start_order_time`,core.`end_order_time`,"
          + " core.order_status,core.gift_time, "
          + " u.learning_coach_id,lcbu.admin_user_name AS learning_coach_name "
          + " FROM t_order_course core "
          + " LEFT JOIN t_user u ON core.user_id = u.`key_id` AND u.is_used = 1 "
          + " LEFT JOIN t_user_info tu ON tu.key_id = u.key_id AND tu.is_used = 1 "
          + " LEFT JOIN t_badmin_user bu ON bu.`key_id` = core.`create_user_id` "
          + " LEFT JOIN t_badmin_user lcbu ON lcbu.`key_id` = u.learning_coach_id "
          + " WHERE core.is_used =1 ";

  /**
   * 找是否有未支付的合同（拟定合同的时候需要判断当前用户是否有没支付完成的订单，如果有的话，不能让管理员拟定合同。）
   */
  public static final String FIND_NOTPAID_ORDERS_BY_USERID = " SELECT key_id "
      + " FROM t_order_course "
      + " WHERE is_used <> 0 "
      + " AND user_id = :user_id "
      + " AND (order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PLANNED
      + " OR order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_SENT
      + " OR order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_CONFIRMED
      + " OR order_status = " + OrderStatusConstant.ORDER_STATUS_PAYING + " ) ";

  /**
   * 查询合同的课程信息
   */
  private static final String FIND_ORDER_COURSES = "SELECT course_type,course_count,is_gift "
      + "FROM t_order_course_option toco "
      + "LEFT JOIN t_order_course toc ON toc.key_id = toco.order_id "
      + "WHERE toco.order_id = :key_id  ";

  /**
   * 查询合同的基本信息
   */
  private static final String FIND_ORDER_PACKEAGE_INFO =
      "SELECT toc.total_real_price,toc.start_order_time "
          + "FROM t_order_course toc "
          + "WHERE toc.key_id = :key_id  and toc.is_used=1 ";

  /**
   * 查询合同的学员，销售的信息
   */
  private static final String FIND_CONTRACT_USER_BY_ID =
      "SELECT toc.key_id,tu.user_name,tu.phone,tbu.admin_user_name,tbu.email as a_email,tbu.phone as a_phone,tui.idcard FROM t_order_course toc "
          + "LEFT JOIN t_user tu ON toc.user_id=tu.key_id	"
          + "LEFT JOIN t_user_info tui ON toc.user_id=tui.key_id	"
          + "LEFT JOIN t_badmin_user tbu ON toc.create_user_id=tbu.key_id "
          + "WHERE toc.key_id= :key_id and toc.is_used=1 and tu.is_used=1 and tbu.is_used=1 and tui.is_used=1 ";

  /**
   * 由userids 查询合同信息
   */
  private static final String FIND_ORDERCOURSE_BY_USERIDS =
      " SELECT oc.key_id as order_course_id, oc.user_id , "
          + " oc.category_type, oc.course_package_id, oc.course_package_name, "
          + " oc.start_order_time, oc.end_order_time, oc.order_status "
          + " FROM t_order_course oc "
          + " WHERE oc.is_used = 1 AND oc.order_status = 5 "
          + " AND oc.user_id in ( :userIds) "
          + " ORDER BY oc.user_id ";

  /**
   * 由keyIds查询出合同信息
   */
  private static final String FIND_ORDERCOURSE_BY_KEYIDS = " SELECT key_id, course_package_name, "
      + " start_order_time, end_order_time, total_show_price, total_real_price, total_final_price "
      + " FROM t_order_course "
      + " WHERE is_used <> 0 "
      + " AND key_id in ( :keyIds) "
      + " ORDER BY create_date ASC ";

  /**
   * 加载后台选择 可续约的合同 modify by komi 2016年6月1日17:38:22
   * 增加total_real_price，不再使用total_discount_price
   */
  public static final String FIND_RENEWALORDERCOURSE_LIST_BY_USERID =
      " SELECT total_real_price,key_id, category_type, course_package_name, limit_show_time, limit_show_time_unit,total_final_price "
          + " FROM t_order_course "
          + " WHERE is_used <> 0 "
          + " AND user_id = :user_id "
          + " AND (order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID + " ) "
          + " ORDER BY create_date desc ";

  /**
   * 根据key_id 查询合同不带is_used
   */
  public static final String FIND_ONE_BY_KEY_ID_WITH_NO_ISUSED =
      " SELECT limit_show_time,limit_show_time_unit, total_final_price, total_real_price "
          + " FROM t_order_course "
          + " WHERE key_id = :keyId ";

  /**
   * 删除合同时更新updateuserid
   */
  public static final String DELETE_BY_KEY_ID_WITH_UPDATE_USER_ID =
      " UPDATE t_order_course SET is_used = 0, update_user_id = :updateUserId WHERE key_id = :keyId ";

  /**
   * 
   * Title: 加载后台选择 可续约的合同<br>
   * Description: findRenewalOrderCourseListByUserId<br>
   * CreateDate: 2016年3月29日 下午5:46:45<br>
   * 
   * @category 加载后台选择 可续约的合同
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findRenewalOrderCourseListByUserId(Map<String, Object> paramMap)
      throws Exception {
    return super.findList(FIND_RENEWALORDERCOURSE_LIST_BY_USERID, paramMap);

  }

  /**
   * 
   * Title: 后台 合同详情分页查询<br>
   * Description: findOrderCourseByUserIdEasyui<br>
   * CreateDate: 2016年3月28日 下午4:00:48<br>
   * 
   * @category 后台 合同详情分页查询
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findOrderCourseByUserIdEasyui(Map<String, Object> paramMap) throws Exception {
    return super.findPage(FIND_ORDERCOURSE_BY_USERID_EASYUI, paramMap);
  }

  /**
   * 
   * Title: 由keyIds 找到合同的列表<br>
   * Description: findOrderCourseByKeyIds<br>
   * CreateDate: 2016年3月28日 下午5:38:32<br>
   * 
   * @category 由keyIds 找到合同的列表
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findOrderCourseByKeyIds(Map<String, Object> paramMap)
      throws Exception {
    return super.findList(FIND_ORDERCOURSE_BY_KEYIDS, paramMap);
  }

  /**
   * 
   * Title: 由userids 查询合同信息<br>
   * Description: findOrderCourseByUserIds<br>
   * CreateDate: 2016年3月2日 下午5:05:13<br>
   * 
   * @category 由userids 查询合同信息
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findOrderCourseByUserIds(Map<String, Object> paramMap)
      throws Exception {
    return super.findList(FIND_ORDERCOURSE_BY_USERIDS, paramMap);
  }

  /**
   * 
   * Title: 删除主表数据<br>
   * Description: 删除主表数据<br>
   * CreateDate: 2015年8月18日 上午4:22:28<br>
   * 
   * @category 删除主表数据
   * @author yangmh
   * @param id
   * @return
   * @throws Exception
   */
  public int deleteForRealOption(String id) throws Exception {
    String sqlStr = "delete from t_order_course_option where order_id = '" + id + "'";
    return namedParameterJdbcTemplate.update(sqlStr, new HashMap());
  }

  private static final String UPDATE_ORDER_STATUS_BY_ORDERID = " update t_order_course "
      + " SET order_status = :order_status " + " WHERE is_used <> 0 " + " AND key_id = :key_id";

  // 2.在非学员状态的用户中心允许快速找到未付款的订单入口
  // 3.在非学员状态的用户中心允许快速找到CC已发送状态的合同入口。
  /**
   * 查找CC已经发送的&& 用户已经确认但是没有付款的订单
   */
  private static final String FIND_ORDERS_BY_USERID = " SELECT * "
      + " FROM t_order_course "
      + " WHERE is_used <> 0 "
      + " AND user_id = :user_id "
      + " AND (order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_SENT
      + " OR order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_CONFIRMED
      + " OR order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID + " )";

  /**
   * 查询用户的合同
   */
  private static final String FIND_USER_ORDER_COURSES =
      " SELECT course_package_name,total_real_price,total_show_price,start_order_time,end_order_time "
          + " FROM t_order_course "
          + " WHERE user_id= :user_id AND NOW() "
          + " BETWEEN start_order_time AND end_order_time "
          + " AND order_status=" + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
          + " AND is_used=1 ";

  /**
   * 订单状态的更改
   */
  private static final String UPDATE_ORDER = "UPDATE t_order_course "
      + "SET order_status= :new_status WHERE order_status= :old_status";

  /**
   * 1v1课程所剩数量的查询
   */
  private static final String COUNT_1V1 =
      "SELECT toco.remain_course_count as count FROM t_order_course toc LEFT JOIN t_order_course_option toco ON toc.key_id = toco.order_id WHERE user_id= :user_id AND toc.is_used=1 AND toco.is_used = 1 AND toco.course_type = :course_type";

  /**
   * 1v1课程所剩数量的增加
   */
  private static final String ADD_COURSE_COUNT =
      "UPDATE t_order_course_option SET remain_course_count = remain_course_count+1 WHERE order_id=(SELECT key_id FROM t_order_course WHERE user_id = :user_id AND is_used=1) AND course_type = :course_type ";

  /**
   * 1v1课程所剩数量的减少
   */
  private static final String MINUS_COURSE_COUNT =
      "UPDATE t_order_course_option SET remain_course_count = remain_course_count-1 WHERE order_id=(SELECT key_id FROM t_order_course WHERE user_id = :user_id AND is_used=1) AND course_type = :course_type";

  /**
   * 
   * Title: 找是否有未支付的合同（拟定合同的时候需要判断当前用户是否有没支付完成的订单， 如果有的话，不能让管理员拟定合同。）<br>
   * Description: findNotPaidOrderCourseByUserId<br>
   * CreateDate: 2016年1月11日 下午4:33:21<br>
   * 
   * @category 找是否有未支付的合同（拟定合同的时候需要判断当前用户是否有没支付完成的订单， 如果有的话，不能让管理员拟定合同。）
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findNotPaidOrderCourseByUserId(Map<String, Object> paramMap)
      throws Exception {
    return findOne(FIND_NOTPAID_ORDERS_BY_USERID, paramMap);
  }

  /**
   * 
   * Title: 根据order_id将order_status更新为已支付 Description:<br>
   * CreateDate: 2015年8月17日 下午4:50:26<br>
   * 
   * @category 根据order_id将order_status更新为已支付
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int updateOrderStatusByOrderId(Map<String, Object> paramMap) throws Exception {
    return super.update(UPDATE_ORDER_STATUS_BY_ORDERID, paramMap);
  }

  /**
   * 
   * Title: 找到未付款 && CC已发送状态 的订单 Description: findOrdersByUserIdAndStatus<br>
   * CreateDate: 2015年8月14日 上午10:15:12<br>
   * 
   * @category findOrdersByUserIdAndStatus
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findOrdersByUserId(Map<String, Object> paramMap)
      throws Exception {
    return findList(FIND_ORDERS_BY_USERID, paramMap);
  }

  /**
   * @category 关闭所有未支付的课程包订单
   * @param pms
   * @throws Exception
   */
  public void closeOrder(Map<String, Object> pms) throws Exception {
    update(UPDATE_ORDER, pms);
  }

  /**
   * @category 判断学员的合同是否有效
   * @param pms
   * @return true：有效； false：无效
   * @throws Exception
   */
  public List<Map<String, Object>> findUserOrderCourses(Map paramMap) throws Exception {
    // 查询学员有效的合同数据
    return findList(FIND_USER_ORDER_COURSES, paramMap);
  }

  public boolean has1v1Course(Map paramMap) throws Exception {

    List<Map<String, Object>> list = findList(COUNT_1V1, paramMap);

    return Integer.parseInt(list.get(0).get("count").toString()) > 0;
  }

  public void updateCourseCount(Map<String, Object> pms, boolean is_add) throws Exception {
    pms.put("course_type", "course_type1");
    if (is_add) {
      update(ADD_COURSE_COUNT, pms);
    } else {
      update(MINUS_COURSE_COUNT, pms);
    }
  }

  /**
   * @category 查询合同的人员
   * @param map
   * @return 学员姓名，身份证号，手机号，speakhi账户，销售的名字,销售的邮箱，销售的电话
   * @throws Exception
   * @author vector.mjp
   */
  public List<Map<String, Object>> findContractUser(Map<String, Object> params) throws Exception {
    return findList(FIND_CONTRACT_USER_BY_ID, params);
  }

  /**
   * @category 查询合同的基本信息
   * @param map
   * @return 合同交易时间，合同交易价格，合同生效时间【上课开始时间】
   * @throws Exception
   * @author vector.mjp
   */
  public List<Map<String, Object>> findOrderPackeageInfo(Map<String, Object> map) throws Exception {
    return findList(FIND_ORDER_PACKEAGE_INFO, map);
  }

  /**
   * @category 查询合同的课时数
   * @param map
   * @return 课程类型，节数，是否赠送【是1 否0】
   * @throws Exception
   * @author vector.mjp
   */
  public List<Map<String, Object>> findOrderCourses(Map<String, Object> map) throws Exception {
    return findList(FIND_ORDER_COURSES, map);
  }

  /**
   * Title: 查询后台管理平台的合同列表<br>
   * Description: findOrderCourseList<br>
   * CreateDate: 2016年1月13日 上午12:11:51<br>
   * 
   * @category 查询后台管理平台的合同列表
   * @author yangmh
   * @param columnName
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findOrderCourseList(Map<String, Object> paramMap) throws Exception {
    return findPageEasyui(paramMap, FIND_ORDER_COURSE_LIST, " order by core.create_date desc ");
  }

  /**
   * 
   * Title: 通过user_id找合同信息<br>
   * Description: findOrderCourseByUserId<br>
   * CreateDate: 2016年1月20日 下午3:57:01<br>
   * 
   * @category 通过user_id找合同信息
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findOrderCourseByUserId(Map<String, Object> paramMap)
      throws Exception {
    return findList(FIND_ORDERCOURSE_BY_USERID, paramMap);
  }

  /**
   * Title: 查询后台管理平台的合同列表.<br>
   * Description: findOrderCourseList<br>
   * CreateDate: 2016年1月13日 上午12:11:51<br>
   * 
   * @category 查询后台管理平台的合同列表
   * @author seven.gz
   */
  public Page findOrderCourseListForCc(Map<String, Object> paramMap) throws Exception {
    String sql = FIND_ORDER_COURSE_LIST + " AND core.create_user_id = :ccAdminUserId ";
    return findPageEasyui(paramMap, sql, " order by core.create_date desc ");
  }

  /**
   * Title: 根据keyId查询合同不带isused<br>
   * Description: findOrderCourseListForCc<br>
   * CreateDate: 2017年5月11日 下午2:55:45<br>
   * 
   * @category 根据keyId查询合同不带isused
   * @author seven.gz
   * @param keyId
   *          keyid
   * 
   */
  public Map<String, Object> findOneByKeyIdWithNoIsUsed(String keyId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("keyId", keyId);
    return findOne(FIND_ONE_BY_KEY_ID_WITH_NO_ISUSED, paramMap);
  }

  /**
   * Title: 删除合同更新更新人<br>
   * Description: deleteByKeyIdWithUpdateUserId<br>
   * CreateDate: 2017年5月23日 下午2:47:23<br>
   * 
   * @category 删除合同更新更新人
   * @author seven.gz
   * @param keyId
   *          合同id
   */
  public int deleteByKeyIdWithUpdateUserId(String keyId, String updateUserId) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("keyId", keyId);
    paramMap.put("updateUserId", updateUserId);
    return update(DELETE_BY_KEY_ID_WITH_UPDATE_USER_ID, paramMap);
  }
}