package com.webi.hwj.redeemcode;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.redeemcode.entity.RedeemCode;
import com.webi.hwj.redeemcode.service.RedeemCodeService;

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
public class RedeemcodeServiceTest {
  @Resource
  RedeemCodeService redeemcodeService;

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
  // @Test
  public void receiveRedeemCodeTest() throws Exception {
    redeemcodeService.sendSms("RECEIVE_ONLINE", "15021298915");
  }
  
  
  /**
   * Title: 生成兑换码和下载的文件<br>
   * Description: 生成兑换码<br>
   * CreateDate: 2017年1月16日 下午5:42:11<br>
   * @category 生成兑换码 
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void betchCreateRedeemcodeBySentTest() throws Exception {
     RedeemCode paramObj = new RedeemCode();
     paramObj.setRedeemCode("00111111");
     int redeemCodeNum = 1;
    System.out.println(redeemcodeService.betchCreateRedeemcodeBySent(paramObj,redeemCodeNum));
  }
  
}
