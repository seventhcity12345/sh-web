package com.webi.hwj.coperation;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.coperation.param.RedeemCodeRegisterParam;
import com.webi.hwj.coperation.service.MeibangService;
import com.webi.hwj.coperation.util.MeibangUtil;

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
public class MeibangServiceTest {
  @Resource
  MeibangService MeibangService;

  @Test
  public void demo() {

  }

  /**
   * Title: 校验兑换码<br>
   * Description: 校验兑换码<br>
   * CreateDate: 2016年11月9日 下午2:35:17<br>
   * 
   * @category 校验兑换码
   * @author seven.gz
   * @throws Exception
   */
  //@Test
  public void cehckRedeemCodeTest() throws Exception {
    System.out.println(MeibangService.cehckRedeemCode("1111111111111111"));
  }

  /**
   * Title: 兑换码注册<br>
   * Description: 校验兑换码注册<br>
   * CreateDate: 2016年11月9日 下午2:35:17<br>
   * 
   * @category 校验兑换码注册
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void redeemCodeRegisterTest() throws Exception {
    RedeemCodeRegisterParam redeemCodeRegisterParam = new RedeemCodeRegisterParam();
    redeemCodeRegisterParam.setCity("测试");
    redeemCodeRegisterParam.setPhone("15874696589");
    redeemCodeRegisterParam.setRedeemCode("5555555555555555");
    redeemCodeRegisterParam.setUserName("测试");
    System.out.println(MeibangService.redeemCodeRegister(redeemCodeRegisterParam));
  }

  /**
   * 
   * Title: 美邦校验兑换码接口测试<br>
   * Description: 美邦校验兑换码接口测试<br>
   * CreateDate: 2016年11月9日 下午4:18:09<br>
   * 
   * @category 美邦校验兑换码接口测试
   * @author seven.gz
   * @throws Exception
   */
  //@Test
  public void apiCheckCardNoTest() throws Exception {
    MeibangUtil.checkCardNo("1111111111111111");
  }

  /**
   * 
   * Title: 美邦核销结果接收接口测试<br>
   * Description: 美邦核销结果接收接口测试<br>
   * CreateDate: 2016年11月9日 下午4:19:28<br>
   * 
   * @category 美邦核销结果接收接口测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void apiRecExchangeResultTest() throws Exception {
    MeibangUtil.recExchangeResult("0123456789111111", "12344569875");
  }

}
