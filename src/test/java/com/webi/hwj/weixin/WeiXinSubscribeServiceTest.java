package com.webi.hwj.weixin;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.param.OrderCourseAndOptionParam;
import com.webi.hwj.ordercourse.service.AdminOrderCourseOptionService;
import com.webi.hwj.subscribecourse.param.SubscribeCourseAndCommentParam;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.dao.UserInfoEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entity.UserInfo;
import com.webi.hwj.weixin.service.WeiXinSubscribeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class WeiXinSubscribeServiceTest {
  @Resource
  WeiXinSubscribeService weiXinSubscribeService;
  @Resource
  UserEntityDao userEntityDao;
  @Resource
  UserInfoEntityDao userInfoEntityDao;
  @Resource
  AdminOrderCourseOptionService adminOrderCourseOptionService;
  @Resource
  OrderCourseDao orderCourseDao;
  @Resource
  SubscribeCourseService subscribeCourseService;

  @Test
  public void demo() {

  }

  public SessionUser getSessionUser(String userIdOrPhone) throws Exception {
    User user = userEntityDao.findUserByUserIdOrPhone(userIdOrPhone);
    if (user == null) {
      throw new Exception("session用户不存在,userId = " + userIdOrPhone);
    }
    UserInfo userInfo = userInfoEntityDao.findOneByKeyId(user.getKeyId());

    SessionUser sessionUser = new SessionUser();
    BeanUtils.copyProperties(sessionUser, user);
    BeanUtils.copyProperties(sessionUser, userInfo);
    sessionUser.setStudent(sessionUser.getIsStudent());

    // modified by alex.ymh 2016年8月8日 14:58:11 出来混，迟早要换的。
    sessionUser.setCurrentLevel(user.getCurrentLevel());
    sessionUser.setInfoCompletePercent(user.getInfoCompletePercent() + "");

    // modified by alex.yang 如果用户是学员的话，才会查下面跟合同相关的信息

    if (sessionUser.getIsStudent()) {
      /**
       * modified by komi 2016年7月7日10:51:15 增加学员拥有的所有课程类型
       */
      sessionUser.setCourseTypes(
          adminOrderCourseOptionService.findCourseTypesByUserId(sessionUser.getKeyId()));
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("user_id", user.getKeyId());
      Map<String, Object> orderCourseMap = orderCourseDao.findContractByUserId(paramMap);

      // 当前合同开始时间
      sessionUser.setCurrentOrderStartTime((Date) orderCourseMap.get("start_order_time"));
      // 当前合同结束时间
      sessionUser.setCurrentOrderEndTime((Date) orderCourseMap.get("end_order_time"));
      // 当前合同的课程包类型
      sessionUser.setCoursePackageType(orderCourseMap.get("course_package_type").toString());
    }
    return sessionUser;
  }

  /**
   * 
   * Title: 按课程分组查询课程预约信息测试<br>
   * Description: 按课程分组查询课程预约信息测试<br>
   * CreateDate: 2016年10月17日 上午10:13:54<br>
   * 
   * @category 按课程分组查询课程预约信息测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findLearningPathTest() throws Exception {
    String userIdOrPhone = "17000000001";
    SessionUser sessionUser = getSessionUser(userIdOrPhone);
    System.out.println(weiXinSubscribeService.findLearningPath(sessionUser));
  }

  /**
   * 
   * Title: 查询用户是否预约过这个标题的课程测试<br>
   * Description: 查询用户是否预约过这个标题的课程测试<br>
   * CreateDate: 2016年10月17日 上午10:15:24<br>
   * 
   * @category 查询用户是否预约过这个标题的课程测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void subscribeCourseType2CoursePremiseTest() throws Exception {
    CommonJsonObject json = weiXinSubscribeService.subscribeCourseType2CoursePremise(
        "9234ea3a30b34fa2b5edee1c179e22eb", "52b4dc83fc554a79857f5d5ff5ade251");
    System.out.println(json);
  }

  /**
   * Title: 查询合同信息接口<br>
   * Description: 查询合同信息接口<br>
   * CreateDate: 2016年10月13日 下午2:25:47<br>
   * 
   * @category 查询合同信息接口
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void findUserContractList() throws Exception {
    String userId = "ed38eccdda894f4baa5dd3232e7c3789";
    String userPhone = "111111";

    List<OrderCourseAndOptionParam> orderCourseAndOptionParamList = weiXinSubscribeService
        .findUserContractList(userId, userPhone);

    System.out.println(orderCourseAndOptionParamList);
  }

  /**
   * Title: 查询当天预约课程数<br>
   * Description: 查询当天预约课程数<br>
   * CreateDate: 2016年10月13日 下午4:03:05<br>
   * 
   * @category 查询当天预约课程数
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void findCurrentDayCourseInfo() throws Exception {
    String userId = "ed38eccdda894f4baa5dd3232e7c3789";
    Date startTime = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DATE, 1);
    Date endTime = DateUtil.strToDateYYYYMMDD(DateUtil.dateToStrYYMMDD(cal.getTime()));

    System.out.println(weiXinSubscribeService.findCurrentDayCourseInfo(userId, startTime, endTime));
  }

  /**
   * Title: 查找预约课程详情及其评价相关信息<br>
   * Description: 查找预约课程详情及其评价相关信息<br>
   * CreateDate: 2016年10月14日 下午4:39:18<br>
   * 
   * @category 查找预约课程详情及其评价相关信息
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  // @Test
  public void findSubscribeDetailInfoTest() throws Exception {
    // 预约id
    String subscribeId = "392bebdfeac644b8a6e46e5e3d494073";
    String userId = "0af6e18487544543b144c8758827b026";
    // 查找预约课程详情及其评价相关信息
    SubscribeCourseAndCommentParam returnObj = weiXinSubscribeService
        .findASubscribeDetailBySubscribeId(subscribeId, userId);

    System.out.println(returnObj);

  }

  /**
   * Title: 查询订课记录列表<br>
   * Description: 根据用户id查询所有预约信息以及相应的学生评价信息<br>
   * CreateDate: 2016年10月14日 下午2:37:09<br>
   * 
   * @category 查询订课记录列表
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  // @Test
  public void findSubscribeListTest() throws Exception {
    String userId = "0af6e18487544543b144c8758827b026";
    // 根据用户id查询所有预约信息以及相应的学生评价信息
    Page pageObj = weiXinSubscribeService.findSubscribeList(userId, 1, 100);

    System.out.println(pageObj);

  }

}
