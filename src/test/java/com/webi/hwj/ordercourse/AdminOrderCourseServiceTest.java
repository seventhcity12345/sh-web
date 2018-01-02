/** 
 * File: AdminOrderCourseServiceTest.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.ordercourse<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年3月29日 下午2:15:16
 * @author ivan.mgh
 */
package com.webi.hwj.ordercourse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.bean.JsonCodeMessage;
import com.webi.hwj.coursepackagepriceoption.dao.CoursePackagePriceOptionDao;
import com.webi.hwj.ordercourse.dto.AdminOrderCourseDto;
import com.webi.hwj.ordercourse.dto.OrderCourseDto;
import com.webi.hwj.ordercourse.service.AdminOrderCourseSaveService;
import com.webi.hwj.ordercourse.service.AdminOrderCourseService;

import net.sf.json.JSONObject;

/**
 * Title: AdminOrderCourseServiceTest<br>
 * Description: AdminOrderCourseServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月29日 下午2:15:16
 * 
 * @author ivan.mgh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class AdminOrderCourseServiceTest {
  @Resource
  private AdminOrderCourseService adminOrderCourseService;
  @Resource
  AdminOrderCourseSaveService adminOrderCourseSaveService;
  @Resource
  CoursePackagePriceOptionDao coursePackagePriceOptionDao;

  @Test
  public void demo() {

  }
  
  /**
   * Title: 向crm推送变更合同金额<br>
   * Description: 向crm推送变更合同金额<br>
   * CreateDate: 2017年7月4日 下午8:24:58<br>
   * @category 向crm推送变更合同金额 
   * @author komi.zsy
   * @throws Exception
   */
//  @Test
//  public void updateMoneyToCrmTest() throws Exception {
//    Date endTime = new Date();
////    Date startTime = new Date(endTime.getTime() - 7 * 24 * 60 * 60 * 1000);
//    Date startTime = DateUtil.strToDateYYYYMMDD("2015-12-1");
//    adminOrderCourseService.updateMoneyToCrm(startTime, endTime);
//    Thread.sleep(999999999);
//  }
  

  /**
   * Title: 测试：同步SpeakHi合同状态<br>
   * Description: 同步SpeakHi合同状态到CRM<br>
   * CreateDate: 2016年3月28日 下午5:52:08<br>
   * 
   * @category 同步SpeakHi合同状态
   * @author ivan.mgh
   * @throws Exception
   */
  // @Test
  public void testSynchronizeContractStatus() throws Exception {
    OrderCourseDto orderCourseDto = new OrderCourseDto();
    orderCourseDto.setContractGuid("10002");
    orderCourseDto.setContractState("2");
    JSONObject jsonObject = JSONObject.fromObject(orderCourseDto);
    // 消息体
    String JsonMessageBody = jsonObject.toString();

    JsonCodeMessage jsonCodeMessage = adminOrderCourseService
        .synchronizeContractStatus(JsonMessageBody);

    // 断言CODE是否为“200”
    Assert.assertEquals("200", jsonCodeMessage.getCode());
  }
  
  /**
   * Title:  需要赋值参数的确认支付成功接口<br>
   * Description:  需要赋值参数的确认支付成功接口<br>
   * CreateDate: 2017年3月21日 上午11:02:16<br>
   * @category  需要赋值参数的确认支付成功接口 
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void confirmSuccessPayByAutoTest() throws Exception {
    String orderId = "";
    String updateUserId = "";

    adminOrderCourseSaveService.confirmSuccessPayByAuto(orderId, updateUserId,"折后价0元",0);

  }
  
  /**
   * Title: 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的）<br>
   * Description: 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的）<br>
   * CreateDate: 2017年3月21日 上午11:20:50<br>
   * @category 查找学员是否有代付款或正在执行的合同，不允许拟定多合同（现在主要是给crm用的） 
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void findNotOrderCourseByUserId() throws Exception {
    String userId = "";

    int num = adminOrderCourseService.findNotOrderCourseByUserId(userId);

    System.out.println(num);
  }
  
  
  /**
   * Title: 测试：合同信息补充接口<br>
   * Description: 调用合同信息补充接口<br>
   * CreateDate: 2016年3月31日 上午9:40:20<br>
   * 
   * @category 合同信息补充接口
   * @author ivan.mgh
   * @throws Exception
   */
  // @Test
  public void testSynchronizeContractInfo() throws Exception {
    AdminOrderCourseDto adminOrderCourseDto = new AdminOrderCourseDto();
    adminOrderCourseDto.setActualMoney("100");
    adminOrderCourseDto.setContractGuid("100000");
    adminOrderCourseDto.setContractMoney("200");
    adminOrderCourseDto.setContractState("5");
    adminOrderCourseDto.setDiscountMoney("20");
    adminOrderCourseDto.setLeadid("49797FBB-4E9E-E511-8117-000C2957EC4F");

    JSONObject jsonObject = JSONObject.fromObject(adminOrderCourseDto);
    // 消息体
    String JsonMessageBody = jsonObject.toString();

    JsonCodeMessage jsonCodeMessage = adminOrderCourseService
        .synchronizeContractInfo(JsonMessageBody);

    // 断言CODE是否为“200”
    Assert.assertEquals("200", jsonCodeMessage.getCode());
  }

  /**
   * Title: 删除合同<br>
   * Description: 删除合同<br>
   * CreateDate: 2016年8月16日 上午11:23:17<br>
   * 
   * @category 删除合同
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void deleteOrderCourseTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("order_id", "6ce77defb8724c469b45a60cddeea1e9");
    String updateUserId = "";
    CommonJsonObject json = adminOrderCourseService.deleteOrderCourse(paramMap,updateUserId);

    System.out.println(json);

  }

  public void saveOrderCourseTest() throws Exception {

  }

}
