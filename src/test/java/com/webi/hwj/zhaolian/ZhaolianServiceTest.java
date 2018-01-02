package com.webi.hwj.zhaolian;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.zhaolian.service.ZhaolianService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class ZhaolianServiceTest {
  @Resource
  ZhaolianService zhaolianService;
  @Resource
  UserService userService;

  @Test
  public void demo() {

  }
  
  /**
   * Title: 提交招联订单<br>
   * Description: 提交招联订单<br>
   * CreateDate: 2017年4月19日 下午3:34:55<br>
   * @category submitZhaolianFormTest 
   * @author komi.zsy
   * @throws Exception 
   */
  @Test
  public void submitZhaolianFormTest() throws Exception {
    String splitOrderId = "06db77b76da74864866fa34cb1142e35";
    String userId = "7fa43cc0e4ae43408247d6df825a84df";
    SessionUser sessionUser = userService.initSessionUser(userId, null);
    CommonJsonObject json = zhaolianService.submitZhaolianForm(splitOrderId, sessionUser);
    System.out.println(json);
  }
  
  /**
   * Title: 获取招联订单状态<br>
   * Description: 获取招联订单状态<br>
   * CreateDate: 2017年4月20日 下午4:59:32<br>
   * @category 获取招联订单状态 
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findOrderSplitStatusTest() throws Exception {
    String splitOrderId = "06db77b76da74864866fa34cb1142e35";
    zhaolianService.findOrderSplitStatus(splitOrderId,"5");
  }
}
