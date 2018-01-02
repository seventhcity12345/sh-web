/** 
 * File: OrderCourseServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: OrderCourseOptionService<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月14日 上午11:51:12
 * @author athrun.cw
 */
package com.webi.hwj.ordercourse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.webi.hwj.coperation.constant.MeibangConstant;
import com.webi.hwj.index.service.OrderCourseProgressService;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.param.ContractLearningProgressParam;
import com.webi.hwj.ordercourse.param.SaveOrderCourseParam;
import com.webi.hwj.ordercourse.service.OrderCourseService;

/**
 * Title: OrderCourseServiceTest<br>
 * Description: OrderCourseServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月14日 上午11:51:12
 * 
 * @author athrun.cw
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class OrderCourseServiceTest {

  @Resource
  private OrderCourseService orderCourseService;
  @Resource
  private OrderCourseDao orderCourseDao;
  @Resource
  OrderCourseProgressService orderCourseProgressService;

  @Test
  public void demo() {

  }

  /**
   * Title: 百度同步状态的业务逻辑<br>
   * Description: 百度同步状态的业务逻辑<br>
   * CreateDate: 2017年3月31日 上午11:19:51<br>
   * 
   * @category 百度同步状态的业务逻辑
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void afterBaiduPaySuccessLogicTest() throws Exception {
    String orderSplitId = "00e78e7e0b9048169cb1aa0684cbb90c";
    String status = "6";
    orderCourseService.afterBaiduPaySuccessLogic(orderSplitId, "百度分期", status, null, null, null);
  }

  /**
   * 
   * Title: 测试：直接去查预约表，然后把count查出来。预约时间的数量<br>
   * Description: findSubscribeCourseSmallpackCountTest<br>
   * CreateDate: 2015年9月6日 上午10:34:00<br>
   * 
   * @category findSubscribeCourseSmallpackCountTest
   * @author athrun.cw
   * @throws Exception
   */
  // @Test
  public void findSubscribeCourseSmallpackCountTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", "40b778b309b842cc9ba7916b0781b57f");
    // System.out.println("----------------------------"+orderCourseDao.findSubscribeCourseSmallpackCount(paramMap));
  }

  /**
   * Title: crm线下拟定合同<br>
   * Description: crm线下拟定合同<br>
   * CreateDate: 2017年3月21日 上午11:26:46<br>
   * 
   * @category crm线下拟定合同
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void insertContractWithCrmTest() throws Exception {
    SaveOrderCourseParam saveOrderCourseParam = new SaveOrderCourseParam();
    saveOrderCourseParam.setIdcard("123456789011111");
    saveOrderCourseParam.setUserPhone("12345678901");
    saveOrderCourseParam.setTotalRealPrice(1234);
    saveOrderCourseParam.setIsCrm(true);
    saveOrderCourseParam.setUserName("crm测试");
    saveOrderCourseParam.setCoursePackagePriceOptionId("01941f6cbeda44b88cae12690142fce3");
    saveOrderCourseParam.setGiftTime(null);
    saveOrderCourseParam.setUserFromType(0);
    saveOrderCourseParam.setCurrentLevel("General Level 1");
    saveOrderCourseParam.setCreateUserId("crm");
    CommonJsonObject json = orderCourseService.insertContractWithCrm(saveOrderCourseParam);

    System.out.println(json);
  }

  /**
   * Title: 一键兑换合同测试<br>
   * Description: 一键兑换合同测试<br>
   * CreateDate: 2016年11月9日14:35:25<br>
   * 
   * @category 一键兑换合同测试
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void oneKeyCreateOrderTest() throws Exception {
    String updateUserId = "04ca5a3056144546bf5427793a91df90";

    String userPhone = "12335678919";
    String userName = "测试号";
    String redeemCode = "K4eccb3f9b8f";
    String idcard = "";

    // 开通合同
    JsonMessage json = orderCourseService.oneKeyCreateOrder(
        MeibangConstant.MEIBANG_COURSE_PACKAGE_ID,
        MeibangConstant.MEIBANG_COURSE_PACKAGE_PRICE_ID, redeemCode,
        userPhone,
        userName, OrderCourseConstant.USER_FROM_TYPE_MBVIP,
        updateUserId);

    System.out.println(json);

    Assert.assertTrue(json.isSuccess());
  }

  /**
   * Title: 兑换码一键兑换合同测试<br>
   * Description: 兑换码一键兑换合同测试<br>
   * CreateDate: 2016年7月21日 下午2:21:51<br>
   * 
   * @category 兑换码一键兑换合同测试
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void saveOrderCourseAndOptionByRedeemCodeTest() throws Exception {
    String updateUserId = "04ca5a3056144546bf5427793a91df90";

    String userPhone = "12335678919";
    String userName = "测试号";
    String redeemCode = "K4eccb3f9b8f";
    String idcard = "";

    JsonMessage json = orderCourseService.saveOrderCourseAndOptionByRedeemCode(
        redeemCode, userPhone, userName, OrderCourseConstant.USER_FROM_TYPE_NORMAL, updateUserId);

    System.out.println(json);

    Assert.assertTrue(json.isSuccess());
  }

  /**
   * Title: 查找到已经签订的 && 已经开始上课合同信息<br>
   * Description: 查找到已经签订的 && 已经开始上课合同信息<br>
   * CreateDate: 2016年9月21日 下午4:05:18<br>
   * 
   * @category 查找到已经签订的 && 已经开始上课合同信息
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void findStartingContractByUserIdTest() throws Exception {
    String userId = "72dfe41853e64fef95dcf742de71cfd1";
    List<ContractLearningProgressParam> ContractLearningProgressParamList =
        orderCourseProgressService.findStartingContractListByUserId(userId);
    System.out.println(ContractLearningProgressParamList);
  }

  /**
   * Title: 找到未付款 && CC已发送状态 的合同<br>
   * Description: 找到未付款 && CC已发送状态 的合同<br>
   * CreateDate: 2016年9月21日 下午4:24:36<br>
   * 
   * @category 找到未付款 && CC已发送状态 的合同
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void findOrdersByUserIdTest() throws Exception {
    String userId = "8a9f9120abc8483da29f857fa535d3f8";
    List<OrderCourse> OrderCourseList = orderCourseService.findOrdersByUserId(userId);
    System.out.println(OrderCourseList);
  }

}
