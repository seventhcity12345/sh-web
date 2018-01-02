/** 
 * File: SmsApiTest.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.sms<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年12月29日 下午5:01:23
 * @author yangmh
 */
package com.webi.hwj.sms;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.SmsUtil;

/**
 * Title: 发短信接口测试用例<br>
 * Description: SmsApiTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月29日 下午5:01:23
 * 
 * @author yangmh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class SmsApiTest {
  
  @Test
  public void demo() {

  }
  
  /**
   * Title: 不走消息队列直接下发短信<br>
   * Description: 王导的最新版<br>
   * CreateDate: 2015年12月29日 下午5:31:07<br>
   * 
   * @category 不走消息队列直接下发短信
   * @author yangmh
   * @throws Exception
   */
  // @Test
  public void speakhiSmsSend() throws Exception {
    int result = SmsUtil.sendSms("18600609747,15021298915,13585642900", "鲜血与雷鸣!");
    Assert.assertEquals(result, 200);
  }
  
  
  /**
   * Title: 报警短信测试<br>
   * Description: 报警短信测试<br>
   * CreateDate: 2016年11月30日 下午5:43:09<br>
   * @category 报警短信测试 
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void sendAlarmSmsSend() throws Exception {
    int result = SmsUtil.sendAlarmSms("报警短信测试");
    Assert.assertEquals(result, 200);
  }
}
