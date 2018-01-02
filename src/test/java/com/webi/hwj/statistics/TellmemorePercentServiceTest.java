package com.webi.hwj.statistics;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.statistics.service.TellmemorePercentService;

/**
 * @category tellmemorePercent控制类
 * @author mingyisoft代码生成工具
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class TellmemorePercentServiceTest {
  private static Logger logger = Logger.getLogger(TellmemorePercentServiceTest.class);
  @Resource
  TellmemorePercentService TellmemorePercentService;

  @Test
  public void demo() {

  }

  /**
   * Title: 更新第一次完成时间测试<br>
   * Description: 更新第一次完成时间测试<br>
   * CreateDate: 2016年7月13日 下午9:22:23<br>
   * 
   * @category 更新第一次完成时间测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void updateFirstCompleteTimeTest() throws Exception {
    TellmemorePercentService.updateFirstCompleteTime();
  }
}