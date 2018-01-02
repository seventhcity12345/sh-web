/** 
 * File: CourseCommentServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.course<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年9月1日 上午10:43:12
 * @author athrun.cw
 */
package com.webi.hwj.ons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class OnsProducerTest {
  @Test
  public void demo() {

  }

  /**
   * 
   * Title: zzzTest<br>
   * Description: zzzTest<br>
   * CreateDate: 2016年9月14日 下午6:03:22<br>
   * 
   * @category zzzTest
   * @author yangmh
   * @throws Exception
   */
  // @Test
  public void zzzTest() throws Exception {
    try {
      // OnsProducerClient.sendMsg("PID_2634659682-102-tmm-test","yang_test_public_tmm","user_id,phone");
      OnsProducerClient.sendMsg("PID_DEV_COMMON", "TID_DEV_COMMON", "tag_crm_order_course2",
          "maoguohong.");
      // OnsProducerClient.sendMsg("yang_test_public", "我爱你333");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
