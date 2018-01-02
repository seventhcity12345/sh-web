package com.webi.hwj.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.courseone2one.service.AdminCourseOne2oneService;
import com.webi.hwj.coursepackage.param.CoursePackageAndPriceParam;
import com.webi.hwj.coursepackagepriceoption.dao.CoursePackagePriceOptionDao;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.index.param.UserRegisterParam;
import com.webi.hwj.index.service.IndexService;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseDao;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseSplitDao;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.param.SaveOrderCourseParam;
import com.webi.hwj.ordercourse.service.AdminOrderCourseOptionService;
import com.webi.hwj.ordercourse.service.AdminOrderCourseSaveService;
import com.webi.hwj.ordercourse.service.OrderCourseSplitService;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseService;
import com.webi.hwj.teacher.param.TeacherTimeParam;
import com.webi.hwj.teacher.service.TeacherTimeSignService;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.dao.UserInfoEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entity.UserInfo;
import com.webi.hwj.util.CalendarUtil;

/**
 * 
 * Title: 合同流程测试<br>
 * Description: 合同流程测试<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月21日 上午10:41:07
 * 
 * @author seven.gz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class ProcessTest {
  @Resource
  AdminOrderCourseSaveService adminOrderCourseSaveService;
  @Resource
  CoursePackagePriceOptionDao coursePackagePriceOptionDao;
  @Resource
  IndexService indexService;
  @Resource
  OrderCourseSplitService orderCourseSplitService;
  @Resource
  AdminOrderCourseDao adminOrderCourseDao;
  @Resource
  AdminOrderCourseSplitDao adminOrderCourseSplitDao;
  @Resource
  TeacherTimeSignService teacherTimeSignService;
  @Resource
  AdminCourseOne2oneService adminCourseOne2oneService;
  @Resource
  BaseSubscribeCourseService baseSubscribeCourseService;
  @Resource
  UserEntityDao userEntityDao;
  @Resource
  UserInfoEntityDao userInfoEntityDao;
  @Resource
  AdminOrderCourseOptionService adminOrderCourseOptionService;
  @Resource
  OrderCourseDao orderCourseDao;

  @Test
  public void demo() throws Exception {

  }

  /**
   * 
   * Title: 测试合同流程<br>
   * Description: 测试合同流程<br>
   * CreateDate: 2016年10月21日 下午1:40:49<br>
   * 
   * @category 测试合同流程
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void orderCourseProcessTest() throws Exception {
    // 1.新增潜客
    String random6BitNumber = "111111";
    String phone = "1399999999";

    UserRegisterParam userRegisterParam = new UserRegisterParam();
    userRegisterParam.setPhone(phone);
    userRegisterParam.setPwd(SHAUtil.encode(random6BitNumber));
    userRegisterParam.setCreateUserId("JunitTest");
    Map<String, Object> userObj = indexService.saveUser(userRegisterParam);
    Assert.assertNotNull(userObj.get("key_id"));

    // 校验学生是否有未支付的合同,一会看看是否有必要添加

    // 2.开通合同
    // 2.1 拟定合同
    String sessionAdminUserKeyId = "JunitTest";

    // 初始化参数，默认合同等级为3
    SaveOrderCourseParam saveOrderCourseParam = new SaveOrderCourseParam();
    // 合同等级
    saveOrderCourseParam.setCurrentLevel("General Level 3");
    // 用户id
    saveOrderCourseParam.setUserId((String) userObj.get("key_id"));
    // 用户中文名字
    saveOrderCourseParam.setUserName("单元测试");
    // 用户身份证号 paramMap.get("idcard") + ""
    // 332需求迭代，不需要身份证号
    saveOrderCourseParam.setIdcard(null);

    // 查询价格政策
    CoursePackageAndPriceParam coursePackageAndPriceParam = coursePackagePriceOptionDao
        .findOneByCoursePackageIdAndCoursePackagePriceId("4eb73582e31b4f7680e2c1808dddddd1",
            "8a75fa2cdf054df1a6a565f287vip003");

    Assert.assertNotNull(coursePackageAndPriceParam);

    // 学生来源
    saveOrderCourseParam.setUserFromType(0);
    // 课程体系
    saveOrderCourseParam.setCategoryType(coursePackageAndPriceParam.getCategoryType());
    // 课程包id
    saveOrderCourseParam.setCoursePackageId(coursePackageAndPriceParam.getKeyId());
    // 课程包名字
    saveOrderCourseParam.setCoursePackageName(coursePackageAndPriceParam.getPackageName());
    // 时效性(只用于展示)
    saveOrderCourseParam.setLimitShowTime(coursePackageAndPriceParam.getLimitShowTime());
    // 时效性(单位)
    saveOrderCourseParam.setLimitShowTimeUnit(coursePackageAndPriceParam.getLimitShowTimeUnit());
    // 优惠价
    saveOrderCourseParam.setTotalRealPrice(coursePackageAndPriceParam.getCoursePackageRealPrice());
    // 支付价（这里等于优惠价）
    saveOrderCourseParam.setTotalFinalPrice(coursePackageAndPriceParam.getCoursePackageRealPrice());
    // 价格政策子表id
    saveOrderCourseParam
        .setCoursePackagePriceOptionId(coursePackageAndPriceParam.getCoursePackagePriceOptionId());
    // 课程包类型
    saveOrderCourseParam.setCoursePackageType(coursePackageAndPriceParam.getCoursePackageType());

    Map<String, OrderCourseOption> normalOptionsMap = new HashMap<String, OrderCourseOption>();
    Map<String, OrderCourseOption> giftOptionsMap = new HashMap<String, OrderCourseOption>();

    OrderCourseOption orderCourseOption = new OrderCourseOption();
    orderCourseOption.setCourseType("course_type1");
    orderCourseOption.setCourseCount(3);
    orderCourseOption.setCourseUnitType(1);
    orderCourseOption.setRealPrice(200);
    orderCourseOption.setShowCourseCount(3);
    orderCourseOption.setRemainCourseCount(3);
    orderCourseOption.setIsGift(false);
    normalOptionsMap.put(orderCourseOption.getCourseType()
        +","+orderCourseOption.getCourseUnitType(), orderCourseOption);

    OrderCourseOption orderCourseOption2 = new OrderCourseOption();
    orderCourseOption2.setCourseType("course_type2");
    orderCourseOption2.setCourseCount(3);
    orderCourseOption2.setCourseUnitType(1);
    orderCourseOption2.setRealPrice(200);
    orderCourseOption2.setShowCourseCount(3);
    orderCourseOption2.setRemainCourseCount(3);
    orderCourseOption2.setIsGift(false);
    normalOptionsMap.put(orderCourseOption2.getCourseType()
        +","+orderCourseOption2.getCourseUnitType(), orderCourseOption2);

    OrderCourseOption orderCourseOptionGift = new OrderCourseOption();
    orderCourseOptionGift.setCourseType("course_type1");
    orderCourseOptionGift.setCourseCount(3);
    orderCourseOptionGift.setCourseUnitType(1);
    orderCourseOptionGift.setRealPrice(200);
    orderCourseOptionGift.setShowCourseCount(3);
    orderCourseOptionGift.setRemainCourseCount(3);
    orderCourseOptionGift.setIsGift(true);
    giftOptionsMap.put(orderCourseOptionGift.getCourseType()
        +","+orderCourseOptionGift.getCourseUnitType(), orderCourseOptionGift);

    
    List<OrderCourseOption> giftOptionsList = new ArrayList<>(giftOptionsMap.values());
    List<OrderCourseOption> normalOptionsList = new ArrayList<>(normalOptionsMap.values());
    String orderId = adminOrderCourseSaveService.saveOrderCourseAndOption(saveOrderCourseParam,
        sessionAdminUserKeyId, sessionAdminUserKeyId, giftOptionsList,
        normalOptionsList);
    
    // 2.2 修改合同

    // 2.3 拆分订单
    Map<String, Object> courseSplitparamMap = new HashMap<String, Object>();
    courseSplitparamMap.put("order_id", orderId);
    List<Map<String, Object>> courseSplitMapList = new ArrayList<Map<String, Object>>();
    courseSplitparamMap.put("order_split_list", courseSplitMapList);

    Map<String, Object> insertOrderCourseSplitMap = new HashMap<String, Object>();
    insertOrderCourseSplitMap.put("order_id", orderId);
    insertOrderCourseSplitMap.put("split_price",
        coursePackageAndPriceParam.getCoursePackageRealPrice() / 2);
    insertOrderCourseSplitMap.put("split_status", "2");
    insertOrderCourseSplitMap.put("split_pay_type", "2");
    insertOrderCourseSplitMap.put("pay_bank", "");
    insertOrderCourseSplitMap.put("pay_success_sequence", "");
    insertOrderCourseSplitMap.put("pay_cc_name", "");
    insertOrderCourseSplitMap.put("pay_center_name", "");
    insertOrderCourseSplitMap.put("pay_city_name", "");
    insertOrderCourseSplitMap.put("create_user_id", "JunitTest");
    insertOrderCourseSplitMap.put("update_user_id", "JunitTest");

    Map<String, Object> insertOrderCourseSplitMap2 = new HashMap<String, Object>();
    insertOrderCourseSplitMap2.put("order_id", orderId);
    insertOrderCourseSplitMap2.put("split_price",
        coursePackageAndPriceParam.getCoursePackageRealPrice()
            - coursePackageAndPriceParam.getCoursePackageRealPrice() / 2);
    insertOrderCourseSplitMap2.put("split_status", "2");
    insertOrderCourseSplitMap2.put("split_pay_type", "2");
    insertOrderCourseSplitMap2.put("pay_bank", "");
    insertOrderCourseSplitMap2.put("pay_success_sequence", "");
    insertOrderCourseSplitMap2.put("pay_cc_name", "");
    insertOrderCourseSplitMap2.put("pay_center_name", "");
    insertOrderCourseSplitMap2.put("pay_city_name", "");
    insertOrderCourseSplitMap2.put("create_user_id", "JunitTest");
    insertOrderCourseSplitMap2.put("update_user_id", "JunitTest");

    courseSplitMapList.add(insertOrderCourseSplitMap);
    courseSplitMapList.add(insertOrderCourseSplitMap2);

    Map<String, Object> orderCourseSplitMap = orderCourseSplitService
        .saveOrderCourseSplit(courseSplitparamMap);

    // 2.4 修改合同

    // 2.5 学员确认
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("key_id", orderId);
    paramMap.put("order_status", OrderStatusConstant.ORDER_STATUS_HAVE_CONFIRMED);
    // 更新合同状态
    adminOrderCourseDao.updateOrderStatusByOrderId(paramMap);

    // 2.6 后台确认 线下确认流程
    paramMap.put("order_id", orderId);
    List<Map<String, Object>> orderCourseSplitMapList = adminOrderCourseSplitDao
        .findOrderCourseSplitByOrderId(paramMap);

    Assert.assertNotNull(orderCourseSplitMapList);
    Assert.assertNotEquals(0, orderCourseSplitMapList.size());

    for (Map<String, Object> orderCourseSplitMapObj : orderCourseSplitMapList) {
      orderCourseSplitMapObj.put("order_id", orderId);
      orderCourseSplitMapObj.put("split_order_id", orderCourseSplitMapObj.get("key_id"));
      Map<String, Object> confirmSuccessPayMap = orderCourseSplitService
          .confirmSuccessPay(orderCourseSplitMapObj);

      Assert.assertEquals("200", confirmSuccessPayMap.get("code"));
    }

    // 3 排课 1v1
    String oneToOneTeacherTimeId = schedulingTest();

    // 4 初始化 SessionUser
    SessionUser sessionUser = initSessionUser(phone);

    // 5 预约 1v1
    CommonJsonObject subscribeEntryJson = subscribeEntryTest(sessionUser.getKeyId(), sessionUser,
        "course_type1",
        "0977fd7411894acfbb8181f3e5fafaba",
        oneToOneTeacherTimeId);

    Assert.assertEquals(200, subscribeEntryJson.getCode());
    // 这里有问题一会再看 报错 该时间段不存在！userId=c28c91dcfa684afe9bd2e2cac9eb4921

  }

  /**
   * 
   * Title: 排课测试<br>
   * Description: 排课测试<br>
   * CreateDate: 2016年10月21日 下午5:00:33<br>
   * 
   * @category 排课测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public String schedulingTest() throws Exception {
    // 3 新增老师
    String teacherId = "";//adminTeacherServiceTest.addTeacherTest();

    // 4 老师签课
    SessionTeacher sessionTeacher = new SessionTeacher();
    sessionTeacher.setKeyId(teacherId);
    sessionTeacher.setTeacherName("测试");
    sessionTeacher
        .setTeacherCourseType("course_type1,course_type2,course_type9,course_type5,course_type8");

    List<Map<String, Object>> teacherTimeSignList = new ArrayList<Map<String, Object>>();

    Map<String, Object> teacherTimeSign = new HashMap<String, Object>();
    Date currentDate = new Date();
    teacherTimeSign.put("startTime", DateUtil.dateToStrYYMMDD(currentDate) + " 06:00:00");
    teacherTimeSign.put("endTime",
        DateUtil.dateToStrYYMMDD(CalendarUtil.getNextNDay(currentDate, 1)) + " 00:00:00");
    teacherTimeSignList.add(teacherTimeSign);
    teacherTimeSignService.insertTeacherTimeSignByAddTime(teacherTimeSignList, sessionTeacher);

    // 5 新增课程 （1vn）

    // 5 排课
    // 5.1 排1v1
    TeacherTimeParam teacherTimeParam = new TeacherTimeParam();
    teacherTimeParam.setTeacherId(teacherId);
    teacherTimeParam.setTeacherName("测试");
    teacherTimeParam.setCourseType("course_type1");

    int subscribeTime = ((CourseType) MemcachedUtil.getValue(teacherTimeParam.getCourseType()))
        .getCourseTypeDuration(); // 课程持续时间

    teacherTimeParam.setStartTime(new Date(DateUtil
        .strToDateYYYYMMDDHHMMSS((String) teacherTimeSign.get("endTime")).getTime()
        - subscribeTime * 60 * 1000));
    teacherTimeParam.setCourseType("course_type1");

    JsonMessage json = adminCourseOne2oneService.addCourseOne2OneScheduling(teacherTimeParam);
    Assert.assertTrue(json.isSuccess());

    return (String) json.getData();
  }

  /**
   * 
   * Title: 预约测试方法<br>
   * Description: 预约测试方法<br>
   * CreateDate: 2016年10月21日 下午5:30:17<br>
   * 
   * @category 预约测试方法
   * @author seven.gz
   * @param userId
   * @param sessionUser
   * @param courseType
   * @param courseId
   * @param teacherTimeId
   * @return
   * @throws Exception
   */
  public CommonJsonObject subscribeEntryTest(String userId, SessionUser sessionUser,
      String courseType,
      String courseId,
      String teacherTimeId) throws Exception {
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setUserId(userId);
    subscribeCourse.setUserPhone(sessionUser.getPhone());
    subscribeCourse.setCourseType(courseType);
    subscribeCourse.setUserLevel(sessionUser.getCurrentLevel());
    subscribeCourse.setCourseId(courseId);
    subscribeCourse.setTeacherTimeId(teacherTimeId);
    subscribeCourse.setCreateUserId(userId);
    subscribeCourse.setUpdateUserId(userId);
    subscribeCourse.setSubscribeFrom("pc");
    CommonJsonObject subscribeJson = baseSubscribeCourseService.subscribeEntry(subscribeCourse,
        sessionUser);
    return subscribeJson;
  }

  /**
   * 
   * Title: 获得SessionUser对象<br>
   * Description: 获得SessionUser对象<br>
   * CreateDate: 2016年10月21日 下午5:33:18<br>
   * 
   * @category 获得SessionUser对象
   * @author seven.gz
   * @param userIdOrPhone
   * @return
   * @throws Exception
   */
  public SessionUser initSessionUser(String userIdOrPhone) throws Exception {
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

    sessionUser.setCourseTypes(
        adminOrderCourseOptionService.findCourseTypesByUserId(sessionUser.getKeyId()));
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", user.getKeyId());
    // modified by alex.yang 如果用户是学员的话，才会查下面跟合同相关的信息
    if (sessionUser.getIsStudent()) {
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

}
