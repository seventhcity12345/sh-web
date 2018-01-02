/** 
 * File: PayOrderCourseLogServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月18日 下午3:49:51
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

import com.webi.hwj.kuaiqian.service.PayOrderCourseLogService;
import com.webi.hwj.util.UUIDUtil;

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
public class PayOrderCourseLogServiceTest {

  @Resource
  private PayOrderCourseLogService payOrderCourseLogService;

  @Test
  public void demo() {

  }

  // @Test
  public void submitKuaiqianFormTest() throws Exception {
    // 开始添加支付 交易记录
    Map<String, Object> logParamMap = new HashMap<String, Object>();
    // trade_status
    logParamMap.put("trade_status", "3");
    logParamMap.put("order_id", "888a9b2e97af473c8f3611fa3f1cbb0d");
    logParamMap.put("user_id", "e0c065ea890940099edd13e7d4d9ce9d");
    logParamMap.put("money", "1");
    logParamMap.put("key_id", UUIDUtil.uuid(30));
    // 再点击支付时候，就开始初始化该交易记录
    payOrderCourseLogService.insertKuaiqianLogByParamMap(logParamMap);

  }

  // @Test
  public void insertKuaiqianLogByParamMapTest() throws Exception {
    // 开始添加支付 交易记录
    Map<String, Object> logParamMap = new HashMap<String, Object>();
    // trade_status
    logParamMap.put("trade_status", "3");
    logParamMap.put("order_id", "888a9b2e97af473c8f3611fa3f1cbb0d");
    logParamMap.put("user_id", "e0c065ea890940099edd13e7d4d9ce9d");
    logParamMap.put("money", "1");
    logParamMap.put("key_id", UUIDUtil.uuid(30));
    // 再点击支付时候，就开始初始化该交易记录
    payOrderCourseLogService.insertKuaiqianLogByParamMap(logParamMap);

  }

  // @Test
  public void findKuaiqianLogByOrderIdTest() throws Exception {
//    Map<String, Object> logParamMap = new HashMap<String, Object>();
    // trade_status
    // logParamMap.put("trade_status", "3");
    // logParamMap.put("order_id", "b92a9b2e97af473c8f3611fa3f1cbb0d");
    // logParamMap.put("user_id", "e0c065ea890940099edd13e7d4d9ce9d");
    // logParamMap.put("money", "100");
    // System.out.println("key_id =
    // "+payOrderCourseLogService.findKuaiqianLogByKeyId(logParamMap).get("key_id"));
  }

  // @Test
  public void updateKuaiqianLogByOrderIdTest() throws Exception {
    Map<String, Object> logParamMap = new HashMap<String, Object>();
    // trade_status
    logParamMap.put("order_id", "b92a9b2e97af473c8f3611fa3f1cbb0d");
    logParamMap.put("trade_status", 10);
    logParamMap.put("deal_id", "123");
    System.out.println("更新数量： " + payOrderCourseLogService.updateKuaiqianLogByKeyId(logParamMap));
  }

}
