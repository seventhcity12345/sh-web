package com.webi.hwj.convertlab;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.convertlab.service.ConvertlabService;

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
public class ConvertlabServiceTest {

  @Resource
  ConvertlabService convertlabService;

  @Test
  public void demo() {

  }

  /**
   * 
   * Title: 推送crm数据测试<br>
   * Description: crmPushUserTest<br>
   * CreateDate: 2017年8月11日 下午3:05:46<br>
   * 
   * @category 推送crm数据测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void pushUserDateToCrmTest() throws Exception {
    // ConvertlabUtil.obtainCustomers(DateUtil.dateToStr(new Date(),
    // "yyyy-MM-dd'T'hh:mm:ss'Z'"));
    convertlabService.pushUserDateToCrm(DateUtil.strToDateYYYYMMDD(
        "2017-08-10"));
  }

}
