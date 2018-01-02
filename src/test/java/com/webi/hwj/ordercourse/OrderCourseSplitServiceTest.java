/** 
 * File: CourseCommentServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.course<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年9月1日 上午10:43:12
 * @author athrun.cw
 */
package com.webi.hwj.ordercourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.ordercourse.service.OrderCourseSplitService;

/**
 * 
 * Title: 拆分订单预加载接口测试<br>
 * Description: OrderCourseSplitServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年1月12日 上午11:22:14
 * 
 * @author athrun.cw
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class OrderCourseSplitServiceTest {

  @Resource
  private OrderCourseSplitService orderCourseSplitService;

  @Test
  public void demo() {

  }

  /**
   * 
   * Title: 拆分订单预加载接口:销售拟定好的合同，有可能被拆分成多个订单。 <br>
   * Description: preLoadOrderCourseSplitTest<br>
   * CreateDate: 2016年1月12日 下午4:58:39<br>
   * 
   * @category 拆分订单预加载接口:销售拟定好的合同，有可能被拆分成多个订单。
   * @author athrun.cw
   * @throws Exception
   */
   @Test
  public void preLoadOrderCourseSplitTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("order_id", "015b693b7761470f97b2879fc4090d48");

    Map<String, Object> pre = orderCourseSplitService.preLoadOrderCourseSplitByOrderId(paramMap);
    System.out.println("拆分订单预加载接口应答参数:--------------------------->>>" + pre);
  }

  /**
   * 
   * Title: 订单拆分提交:将拆分好的订单提交至服务器 <br>
   * Description: saveOrderCourseSplitTest<br>
   * CreateDate: 2016年1月12日 下午4:58:25<br>
   * 
   * @category 订单拆分提交:将拆分好的订单提交至服务器
   * @author athrun.cw
   * @throws Exception
   */
  // @Test
  public void saveOrderCourseSplitTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("order_id", "e9fa2451bb8f4d498ce11695890d1bf5");

    List<Map<String, Object>> order_split_list = new ArrayList<Map<String, Object>>();

    Map<String, Object> splitMap = new HashMap<String, Object>();
    splitMap.put("key_id", "");
    splitMap.put("split_price", "1000");
    splitMap.put("split_status", "0");
    order_split_list.add(splitMap);

    Map<String, Object> splitMap2 = new HashMap<String, Object>();
    splitMap2.put("key_id", "123");
    splitMap2.put("split_price", "7999");
    splitMap2.put("split_status", "0");
    order_split_list.add(splitMap2);

    paramMap.put("order_split_list", order_split_list);

    Map<String, Object> save = orderCourseSplitService.saveOrderCourseSplit(paramMap);
    System.out.println("拆分订单预加载接口应答参数:--------------------------->>>" + save);
  }

}
