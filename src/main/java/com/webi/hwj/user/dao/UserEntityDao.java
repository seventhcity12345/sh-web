package com.webi.hwj.user.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.param.UserInfoByTrainingMemberParam;
import com.webi.hwj.user.param.UserInfoOrderCourseParam;
import com.webi.hwj.weixin.param.BindUserParam;

@Repository
public class UserEntityDao extends BaseEntityDao<User> {
  private static Logger logger = Logger.getLogger(UserEntityDao.class);

  private static final String FIND_USER_PWD = "SELECT "
      + "key_id,pwd FROM t_user WHERE phone = :phone AND is_used = 1 ";
  
  /**
   * 根据手机号查找是学员的用户
   * @author komi.zsy
   */
  private static final String FIND_USER_INFO_BY_PHONE_AND_STUDENT = "SELECT tui.key_id,tui.english_name,"
      + "tui.real_name,tu.user_photo"
      + " FROM t_user tu"
      + " LEFT JOIN t_user_info tui"
      + " ON tu.key_id = tui.key_id"
      + " WHERE tu.is_used = 1 AND tui.is_used = 1"
      + " AND tu.is_student = 1"
      + " AND tu.phone = :phone";

  /**
   * 根据用户手机号或者keyid查找用户信息
   * 
   * @author komi.zsy
   */
  public static final String FIND_USER_BY_USERID_OR_PHONE =
      " SELECT  key_id,phone,pwd,current_level,user_name,user_code,user_photo,"
          // modified by allen.chang 2017年1月9日 前台需要查詢用戶資料百分比
          + " user_photo_large,last_login_ip,last_login_time,is_student,info_complete_percent "
          + " FROM t_user WHERE key_id = :keyId OR phone = :keyId limit 1 ";

  private static final String FIND_USER_PHOTO_AND_IDCARD =
      "SELECT tu.key_id,tui.idcard,tu.user_photo "
          + " FROM t_user tu "
          + " LEFT JOIN t_user_info tui ON tu.key_id = tui.key_id "
          + " WHERE tu.phone = :phone AND "
          + " tu.is_student = 1 AND tu.is_used = 1 ";

  /**
   * 根据learning_coach_id 查出其名下的所有开始时间和结束时间在合同中课程有效期期间内的学员
   * 
   * @author komi.zsy
   */
  private final static String FIND_USER_BY_ORDER_COURSE_IS_USED_AND_LEARNING_COACH_ID =
      "SELECT tu.key_id,tu.user_code,tui.idcard,"
          + "tu.phone,tu.current_level,tu.user_photo,tui.english_name,tui.real_name,tui.gender,"
          + "toco.course_type,toco.course_unit_type,toco.remain_course_count AS course_count,toc.start_order_time"
          + " FROM t_user tu LEFT JOIN t_user_info tui ON tu.key_id = tui.key_id"
          + " LEFT JOIN t_order_course toc"
          + " ON tu.key_id = toc.user_id"
          + " LEFT JOIN t_order_course_option toco"
          + " ON toc.key_id = toco.order_id"
          + " WHERE tu.learning_coach_id = :learningCoachId"
          + " AND tu.is_used = 1 AND tui.is_used = 1 AND toc.is_used = 1 AND toco.is_used = 1"
          + " AND toco.course_type = :courseType"
          + " AND toc.start_order_time <= :startOrderTime AND toc.end_order_time >= :endOrderTime"
          + " AND toc.order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID
          // 理论上成人不会有多合同，这句是防止有错误数据时，影响训练成员数据
          + " GROUP BY toc.user_id";

  /**
   * 根据手机号查询该学员是否有某种课程类型上课权限
   * 
   * @author komi.zsy
   */
  private final static String FIND_USER_BY_COURSE_TYPE_AND_USER_PHONE =
      "SELECT toco.course_type,toco.course_unit_type,"
          + "toco.course_count,toc.start_order_time,toc.end_order_time"
          + " FROM t_user tu"
          + " LEFT JOIN t_order_course toc"
          + " ON tu.key_id = toc.user_id"
          + " LEFT JOIN t_order_course_option toco"
          + " ON toc.key_id = toco.order_id"
          + " WHERE tu.phone = :phone"
          + " AND tu.is_used = 1 AND toc.is_used = 1 AND toco.is_used = 1"
          + " AND toco.course_type = :courseType"
          + " AND toc.order_status = " + OrderStatusConstant.ORDER_STATUS_HAVE_PAID;
  
  /**
   * 根据用户id列表查找用户信息
   * @author komi.zsy
   */
  private final static String FIND_USER_INFO_BY_USERIDS = "SELECT tu.key_id,tu.user_code,tui.english_name,tui.real_name,"
      + "tui.idcard,tui.gender,tu.user_photo,tu.phone,tu.current_level"
      + " FROM t_user tu"
      + " LEFT JOIN t_user_info tui"
      + " ON tu.key_id = tui.key_id"
      + " WHERE tu.is_used = 1 AND tui.is_used = 1"
      + " AND tu.key_id IN (:keyIds)";
  
  /**
   * Title: 根据用户id列表查找用户信息<br>
   * Description: 根据用户id列表查找用户信息<br>
   * CreateDate: 2017年9月12日 下午8:17:55<br>
   * @category 根据用户id列表查找用户信息 
   * @author komi.zsy
   * @param keyIds 用户ids
   * @return
   * @throws Exception
   */
  public List<UserInfoByTrainingMemberParam> findUserInfoByUserIds(List<String> keyIds) throws Exception {
    UserInfoByTrainingMemberParam paramObj = new UserInfoByTrainingMemberParam();
    paramObj.setKeyIds(keyIds);
    return super.findList(FIND_USER_INFO_BY_USERIDS,paramObj);
  }

  /**
   * Title: 根据手机号查询该学员是否有某种课程类型上课权限<br>
   * Description: 根据手机号查询该学员是否有某种课程类型上课权限<br>
   * CreateDate: 2017年8月14日 上午11:52:42<br>
   * 
   * @category 根据手机号查询该学员是否有某种课程类型上课权限
   * @author komi.zsy
   * @param phone
   *          学员手机号
   * @param courseType
   *          课程类型
   * @return
   * @throws Exception
   */
  public int findUserByCourseTypeAndUserPhone(
      String phone, String courseType) throws Exception {
    UserInfoOrderCourseParam paramObj = new UserInfoOrderCourseParam();
    paramObj.setPhone(phone);
    paramObj.setCourseType(courseType);
    List<UserInfoOrderCourseParam> userInfoOrderCourseParamList = super.findList(
        FIND_USER_BY_COURSE_TYPE_AND_USER_PHONE,
        paramObj);
    // 本来不应该在dao层处理的，但是这个理论上应该查出来就是有效的课程类型时间，但是现在没法查，等tom以后改版成了每个课程类型都有自己的生效时间就可以查了，所以在这里统一处理一下先
    if (userInfoOrderCourseParamList != null && userInfoOrderCourseParamList.size() != 0) {
      List<UserInfoOrderCourseParam> paramList = new ArrayList<UserInfoOrderCourseParam>();
      for (UserInfoOrderCourseParam userInfoOrderCourseParam : userInfoOrderCourseParamList) {
        // 处理一下课程的有效期，看是否还在有效期内
        Calendar cal = Calendar.getInstance();
        cal.setTime(userInfoOrderCourseParam.getStartOrderTime());
        // 课程单位类型（0:节，1:月，2：天）
        switch (userInfoOrderCourseParam.getCourseUnitType()) {
          case 0:
            // 按节卖就一直算有效，就算扣到0也无所谓
            cal.setTime(userInfoOrderCourseParam.getEndOrderTime());
            break;
          case 1:
            cal.add(Calendar.MONTH, userInfoOrderCourseParam.getCourseCount());
            break;
          case 2:
            cal.add(Calendar.DATE, userInfoOrderCourseParam.getCourseCount());
            break;
          default:
            break;
        }
        if (cal.getTimeInMillis() >= new Date().getTime()) {
          // 有效
          paramList.add(userInfoOrderCourseParam);
        }
      }
      return paramList.size();
    }
    return 0;
  }

  /**
   * Title: 根据learning_coach_id 查出其名下的所有开始时间和结束时间在合同中课程有效期期间内的学员<br>
   * Description: 根据learning_coach_id 查出其名下的所有开始时间和结束时间在合同中课程有效期期间内的学员<br>
   * CreateDate: 2017年8月11日 下午5:20:50<br>
   * 
   * @category 根据learning_coach_id 查出其名下的所有开始时间和结束时间在合同中课程有效期期间内的学员
   * @author komi.zsy
   * @param learningCoachId
   * @param startOrderTime
   * @param endOrderTime
   * @return
   * @throws Exception
   */
  public List<UserInfoOrderCourseParam> findUserByOrderCourseIsUsedAndLearningCoachId(
      String learningCoachId, Date startOrderTime, Date endOrderTime) throws Exception {
    UserInfoOrderCourseParam userInfoOrderCourseParam = new UserInfoOrderCourseParam();
    userInfoOrderCourseParam.setLearningCoachId(learningCoachId);
    userInfoOrderCourseParam.setCourseType("course_type10");
    userInfoOrderCourseParam.setStartOrderTime(startOrderTime);
    userInfoOrderCourseParam.setEndOrderTime(endOrderTime);
    return super.findList(FIND_USER_BY_ORDER_COURSE_IS_USED_AND_LEARNING_COACH_ID,
        userInfoOrderCourseParam);
  }

  /**
   * Title: 根据用户手机号或者keyid查找用户信息.<br>
   * Description: findUserByUserIdOrPhone<br>
   * CreateDate: 2016年8月8日 下午3:45:30<br>
   * 
   * @category 根据用户手机号或者keyid查找用户信息
   * @author yangmh
   * @param userIdOrPhone
   *          用户手机号或用户ID
   * @return
   * @throws Exception
   */
  public User findUserByUserIdOrPhone(String userIdOrPhone) throws Exception {
    User user = new User();
    user.setKeyId(userIdOrPhone);
    List<User> userList = super.findList(FIND_USER_BY_USERID_OR_PHONE, user);
    if (userList != null && userList.size() > 0) {
      return userList.get(0);
    } else {
      return null;
    }
  }

  /**
   * Title: 查询用户的头像和身份证.<br>
   * Description: findUserPhotoAndIdcard<br>
   * CreateDate: 2016年10月17日 下午1:47:32<br>
   * 
   * @category 查询用户的头像和身份证
   * @author yangmh
   * @param phone
   *          手机号
   */
  public BindUserParam findUserPhotoAndIdcard(String phone) throws Exception {
    BindUserParam bindUserParam = new BindUserParam();
    bindUserParam.setPhone(phone);
    return super.findOne(FIND_USER_PHOTO_AND_IDCARD, bindUserParam);
  }

  /**
   * 
   * Title: 根据keyid查找对象<br>
   * Description: findOneByKeyId<br>
   * CreateDate: 2016年12月23日 上午10:23:11<br>
   * 
   * @category 根据keyid查找对象
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public User findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "phone,user_code,current_level");
  }

  /**
   * 
   * Title: 根据学员keyId去查询对应CC的id<br>
   * Description: 根据keyId去查询LC的id<br>
   * CreateDate: 2017年7月5日 上午11:58:31<br>
   * 
   * @category 根据学员keyId去查询对应LC的id
   * @author felix.yl
   * @param keyId
   * @return
   * @throws Exception
   */
  public User findOneLcId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "learning_coach_id");
  }

  /**
   * Title: 查询用户密码.<br>
   * Description: 登录专用<br>
   * CreateDate: 2017年2月20日 下午2:47:49<br>
   * 
   * @category 查询用户密码.
   * @author yangmh
   * @param phone
   *          手机号
   */
  public User findOneByPhoneReturnPwd(String phone) throws Exception {
    User param = new User();
    param.setPhone(phone);
    return super.findOne(FIND_USER_PWD, param);
  }
  
  /**
   * Title: 根据手机号查找是学员的用户<br>
   * Description: 根据手机号查找是学员的用户<br>
   * CreateDate: 2017年8月24日 上午11:44:50<br>
   * @category 根据手机号查找是学员的用户 
   * @author komi.zsy
   * @param phone 手机号
   * @return
   * @throws Exception
   */
  public User findOneByPhoneAndStudent(String phone) throws Exception {
    User param = new User();
    param.setPhone(phone);
    return super.findOne(FIND_USER_INFO_BY_PHONE_AND_STUDENT, param);
  }
  
}