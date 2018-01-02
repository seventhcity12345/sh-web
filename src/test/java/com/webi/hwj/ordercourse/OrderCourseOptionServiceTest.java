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

import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.ordercourse.param.FindUserEffectiveContractParam;
import com.webi.hwj.ordercourse.param.OrderCourseOptionParam;
import com.webi.hwj.ordercourse.service.OrderCourseOptionService;

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
public class OrderCourseOptionServiceTest {

  @Resource
  private OrderCourseOptionService orderCourseOptionService;

  @Test
  public void demo() {

  }

  @Test
  public void findDetailsOptionByOrderIdTest() throws Exception {

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("order_id", "71599806334541bf8c7ea6333f66de63");

    System.out.println("111111111111111111111111111111----------------------->"
        + orderCourseOptionService.findDetailsOptionByOrderId(paramMap).size());

  }

  /**
   * Title: 查询用户当前执行中的合同拥有的课程<br>
   * Description: 查询用户当前执行中的合同拥有的课程<br>
   * CreateDate: 2016年9月20日 下午3:00:06<br>
   * 
   * @category 查询用户当前执行中的合同拥有的课程
   * @author komi.zsy
   * @param request
   * @throws Exception
   */
  // @Test
  public void findOrderCourseInfoListTest() throws Exception {
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("72dfe41853e64fef95dcf742de71cfd1");
    // 查询合同课时信息
    List<OrderCourseOptionParam> orderCourseOptionList = orderCourseOptionService
        .findOrderCourseInfoList(sessionUser.getKeyId());

    Assert.assertNotNull(orderCourseOptionList);

    for (OrderCourseOptionParam orderCourseOptionParam : orderCourseOptionList) {
      System.out.println(orderCourseOptionParam.getRemainCourseCount() + ","
          + orderCourseOptionParam.getShowCourseCount() + "," + orderCourseOptionParam
              .getStartOrderTime());
    }

  }

  /**
   * 
   * Title: 查询学员合同中的课程数<br>
   * Description: findOrderCourseInfoListMergeCourseTypeTest<br>
   * CreateDate: 2017年7月21日 下午4:49:38<br>
   * 
   * @category 查询学员合同中的课程数
   * @author seven.gz
   */
  @Test
  public void findOrderCourseInfoListMergeCourseTypeTest() throws Exception {
    // 查询合同课时信息
    List<FindUserEffectiveContractParam> orderCourseOptionList = orderCourseOptionService
        .findOrderCourseInfoListMergeCourseType("7fa43cc0e4ae43408247d6df825a84df");
    System.out.println(orderCourseOptionList);

  }
}
