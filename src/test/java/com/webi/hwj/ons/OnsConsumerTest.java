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

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.mingyisoft.javabase.aliyun.ons.OnsConsumerClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class OnsConsumerTest {
  @Test
  public void demo() {

  }

  // @Test
  public void xxxTest() throws Exception {
    new OnsConsumerClient("TID_DEV_COMMON", "CID_DEV_COMMON", "tag_crm_order_course",
        new MessageListener() {
          public Action consume(Message message, ConsumeContext context) {
            try {
              System.out.println(message);
              // System.out.println("hahahah");
              System.out.println(new String(message.getBody(), "UTF-8"));
              // 正常情况，告诉队列已经消费此消息
              return Action.CommitMessage;
            } catch (Exception e) {
              // 异常情况，告诉队列过一会再消费此消息，队列会重发16次，如果16次都没消费成功，则队列杀掉该消息。
              return Action.ReconsumeLater;
            }
          }
        });
    System.out.println("睡觉");
    Thread.sleep(500000);
  }
}
