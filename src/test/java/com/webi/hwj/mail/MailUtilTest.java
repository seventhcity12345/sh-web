/** 
 * File: PayOrderCourseLogServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月18日 下午3:49:51
 * @author athrun.cw
 */
package com.webi.hwj.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title: PayOrderCourseLogServiceTest<br>
 * Description: PayOrderCourseLogServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月18日 下午3:49:51
 * 
 * @author athrun.cw
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class MailUtilTest {

  @Test
  public void demo() {

  }

  /**
   * 
   * Title: 发送邮件测试<br>
   * Description: updateKuaiqianLogByOrderIdTest<br>
   * CreateDate: 2016年12月22日 下午6:32:41<br>
   * 
   * @category 发送邮件测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void sendMailTest() throws Exception {
    MailUtil.sendMail("", "Test", "Test");
  }

}
