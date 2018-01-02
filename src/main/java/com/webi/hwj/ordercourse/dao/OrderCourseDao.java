package com.webi.hwj.ordercourse.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.dao.BaseMysqlDao;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;

@Repository
public class OrderCourseDao extends BaseMysqlDao {
  private static Logger logger = Logger.getLogger(OrderCourseDao.class);

  /**
   * 查合同（已经支付成功 并且是有效的用户订单 order_status = 已支付 ）
   */
  public static final String FIND_CONTRACT_BY_USERID = " SELECT key_id, user_id, course_package_name, course_package_type, total_real_price, "
      + " total_show_price, start_order_time, end_order_time, limit_show_time, order_status "
      + " FROM t_order_course "
      + " WHERE is_used <> 0 "
      + " AND user_id = :user_id "
      + " AND (order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID + " ) "
      + " ORDER BY start_order_time asc ";

  // /**
  // * 通过用户id查询合法合同(在时间范围内)里的子表数据id(必须是已经消耗过的子表数据),用于取消预约前的查询
  // * @author alex.yang
  // */
  // public static final String FIND_NEED_CANCEL_ORDER_COURSE_OPTION_BY_USERID =
  // "SELECT oco.key_id "
  // + "FROM t_order_course_option oco "
  // + "LEFT JOIN t_order_course oc ON oco.order_id = oc.key_id "
  // + "WHERE oco.remain_course_count < oco.course_count "
  // + "AND oc.user_id = :user_id "
  // + "AND oc.start_order_time <= :curDate AND oc.end_order_time >= :curDate "
  // + "AND oco.course_type =:course_type "
  // + "AND oc.order_status = "+OrderStatusConstant.ORDER_STATUS_HAVE_PAID
  // + "AND oco.is_used = 1 AND oc.is_used = 1 "
  // + "ORDER BY oco.create_date asc ";

  /**
   * 通过用户id查询合法合同(在时间范围内)里的子表数据id(必须是不能被消耗完的子表数据),用于预约前的查询
   * 
   * @author alex.yang
   */
  public static final String FIND_NEED_SUBSCRIBE_ORDER_COURSE_OPTION_BY_USERID = " SELECT oco.key_id as order_option_id ,oc.key_id as order_id "
      + " FROM t_order_course_option oco "
      + " LEFT JOIN t_order_course oc ON oco.order_id = oc.key_id "
      + " WHERE oco.remain_course_count > 0 "
      + " AND oc.order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " AND oc.user_id = :user_id "
      + " AND oc.category_type = :category_type "
      + " AND oc.start_order_time <= :curDate AND oc.end_order_time >= :curDate "
      + " AND oco.course_type =:course_type "
      + " AND oco.is_used = 1 AND oc.is_used = 1 "
      // modify by seven 2016年8月31日09:47:34 课时数需要是节的
      + " AND oco.course_unit_type = " + OrderCourseConstant.COURSE_UNIT_TYPE_CLASS
      + " ORDER BY oco.create_date asc ";
  
  /**
   * 通过用户id查询合法合同(在时间范围内)里的子表数据id(必须是不能被消耗完的子表数据),用于预约前的查询
   * 
   * @author alex.yang
   */
  public static final String FIND_NEED_SUBSCRIBE_ORDER_COURSE_OPTION_BY_USERID_FOR_UPDATE = " SELECT oco.key_id as order_option_id ,oc.key_id as order_id "
      + " FROM t_order_course_option oco "
      + " LEFT JOIN t_order_course oc ON oco.order_id = oc.key_id "
      + " WHERE oco.remain_course_count > 0 "
      + " AND oc.order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " AND oc.user_id = :user_id "
      + " AND oc.category_type = :category_type "
      + " AND oc.start_order_time <= :curDate AND oc.end_order_time >= :curDate "
      + " AND oco.course_type =:course_type "
      + " AND oco.is_used = 1 AND oc.is_used = 1 "
      // modify by seven 2016年8月31日09:47:34 课时数需要是节的
      + " AND oco.course_unit_type = " + OrderCourseConstant.COURSE_UNIT_TYPE_CLASS
      + " ORDER BY oco.create_date asc Limit 1 FOR UPDATE ";


  /**
   * 查询合同的学员，销售的信息
   */
  public static final String FIND_CONTRACT_USER_BY_ID = "SELECT toc.key_id,tu.user_name,tu.phone,tbu.admin_user_name,tbu.email as a_email,tbu.phone as a_phone,tui.idcard FROM t_order_course toc "
      + "LEFT JOIN t_user tu ON toc.user_id=tu.key_id	"
      + "LEFT JOIN t_user_info tui ON toc.user_id=tui.key_id	"
      + "LEFT JOIN t_badmin_user tbu ON toc.create_user_id=tbu.key_id "
      + "WHERE toc.key_id= :key_id and toc.is_used=1 and tu.is_used=1 and tbu.is_used=1 and tui.is_used=1 ";

  /**
   * 订单状态的更改
   */
  public static final String UPDATE_ORDER = "UPDATE t_order_course "
      + "SET order_status= :new_status "
      + "WHERE order_status= :old_status";

  /**
   * 课程所剩数量的查询
   */
  public static final String REMAIN_COURSE_COUNT = "SELECT toc.key_id,toco.remain_course_count AS remainCourseCount "
      + "FROM t_order_course toc "
      + "LEFT JOIN t_order_course_option toco "
      + "ON toc.key_id = toco.order_id "
      + "AND toco.is_used = 1 "
      + "AND toco.course_type = :course_type "
      + "AND toco.remain_course_count > 0 "
      + "WHERE toc.user_id= :user_id "
      + "AND toc.is_used=1 "
      + "AND toc.start_order_time <= :curDate "
      + "AND toc.end_order_time >= :curDate "
      + "AND toc.order_status=" + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
      + " ORDER BY toco.create_date ASC ";

  /**
   * 课程所剩数量的增加
   */
  public static final String ADD_COURSE_COUNT = "UPDATE t_order_course_option "
      + "SET remain_course_count = remain_course_count+1 "
      + "WHERE key_id = :order_course_option_id AND course_unit_type = 0";

  /**
   * 课程所剩数量的减少
   */
  public static final String MINUS_COURSE_COUNT = "UPDATE t_order_course_option "
      + "SET remain_course_count = remain_course_count-1 "
      + "WHERE key_id = :order_course_option_id AND course_unit_type = 0";

  public OrderCourseDao() {
    super.setTableName("t_order_course");
  }

  /**
   * 
   * Title: 查询预约的 已经上过的 小包课数量<br>
   * Description: findSubscribeCourseSmallpackCount<br>
   * CreateDate: 2015年9月6日 上午10:24:15<br>
   * 
   * @category findSubscribeCourseSmallpackCount
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  /**
   * modify by athrun.cw 2016年5月31日10:25:22 新版本中，去除补课逻辑， 2.计算课程进度时 去掉相关的补课逻辑
   * 最后和alex+cyndi定的是： remain_course_count + 未上
   * 
   */
  /*
   * public int findSubscribeCourseCount(Map<String, Object> paramMap) throws
   * Exception{ return super.findCount(FIND_SUBSCRIBE_COURSE_COUNT, paramMap); }
   */

  /**
   * 
   * Title: 正式学员登录，由user_id 查看自己的最新合同信息 Description: findContractByUserId<br>
   * CreateDate: 2015年8月20日 下午4:39:37<br>
   * 
   * @category findContractByUserId
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findContractByUserId(Map<String, Object> paramMap) throws Exception {
    return super.findOne(FIND_CONTRACT_BY_USERID, paramMap);

  }

  /**
   * @category 查询合同的人员
   * @param map
   * @return 学员姓名，身份证号，手机号，好外教账户，销售的名字,销售的邮箱，销售的电话
   * @throws Exception
   * @author vector.mjp
   */
  public List<Map<String, Object>> findContractUser(Map<String, Object> params) throws Exception {
    return findList(FIND_CONTRACT_USER_BY_ID, params);
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
   * Title: 更新学员的合同的某个课程的数量<br>
   * Description: 更新学员的合同的某个课程的数量<br>
   * CreateDate: 2015年8月27日 下午1:44:38<br>
   * 
   * @category 更新学员的合同的某个课程的数量
   * @author vector.mjp
   * @param paramMap
   *          只有order_course_option_id，合同子表的主键
   * @param is_add
   *          true:取消预约,false:预约
   * @throws Exception
   */
  public void updateCourseCount(Map<String, Object> paramMap, boolean is_add) throws Exception {
    // modified by alex.yang 2015年9月9日20:00:13
    if (is_add) {
      update(ADD_COURSE_COUNT, paramMap);
    } else {
      update(MINUS_COURSE_COUNT, paramMap);
    }
  }
}