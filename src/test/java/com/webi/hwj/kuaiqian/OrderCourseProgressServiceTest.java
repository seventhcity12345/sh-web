/** 
 * File: OrderCourseProgressServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月26日 上午11:40:36
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.index.service.OrderCourseProgressService;

/**
 * Title: OrderCourseProgressServiceTest<br>
 * Description: OrderCourseProgressServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月26日 上午11:40:36
 * 
 * @author athrun.cw
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class OrderCourseProgressServiceTest {

  @Resource
  private OrderCourseProgressService orderCourseProgressService;

  @Test
  public void demo() {

  }
}
