package com.webi.hwj.coperation;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.coperation.service.QqService;
import com.webi.hwj.coperation.util.QqUtil;

/**
 * 
 * Title: 兑换码活动测试<br>
 * Description: 兑换码活动测试<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年6月29日 下午4:46:36
 * 
 * @author seven.gz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class QqServiceTest {
  @Resource
  QqService qqService;

  @Test
  public void demo() {

  }

  /**
   * 
   * Title: 测试获取兑换码<br>
   * Description: 测试获取兑换码<br>
   * CreateDate: 2016年6月29日 下午4:48:46<br>
   * 
   * @category 测试获取兑换码
   * @author seven.gz
   * @throws Exception
   */
   @Test
  public void receiveRedeemCodeTest() throws Exception {
     QqUtil.consumeRedeemCode("408","2269008254200");
  }

}
