package com.webi.hwj.statistics;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.statistics.param.CoursewareLearningTimeApiParam;
import com.webi.hwj.statistics.service.StatisticsTellmemoreDayService;

/**
 * Title: 测试speakhi学员rsa数据每日统计<br>
 * Description: 测试speakhi学员rsa数据每日统计<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月18日 上午11:34:25
 * 
 * @author komi.zsy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class StatisticsTellmemoreDayServiceTest {

  @Resource
  StatisticsTellmemoreDayService statisticsTellmemoreDayService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试speakhi学员rsa数据每日统计<br>
   * Description: 测试speakhi学员rsa数据每日统计<br>
   * CreateDate: 2016年4月18日 上午11:34:44<br>
   * 
   * @category 测试speakhi学员rsa数据每日统计
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void statisticsTellmemoreChangeEverydayTest() throws Exception {
    statisticsTellmemoreDayService.statisticsTellmemoreChangeEveryday(null);
  }

  /**
   * 
   * Title: 测试课件学习时长对比<br>
   * Description: 测试课件学习时长对比<br>
   * CreateDate: 2017年8月10日 上午10:53:02<br>
   * 
   * @category 测试课件学习时长对比
   * @author felix.yl
   * @throws Exception
   */
  @Test
  public void findCoursewareLearningTimeInfoTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CoursewareLearningTimeApiParam> json =
        new CommonJsonObject<CoursewareLearningTimeApiParam>();

    // 构建参数
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("52b4dc83fc554a79857f5d5ff5ade251");

    // 调用Service
    CoursewareLearningTimeApiParam coursewareLearningTimeApiParam = statisticsTellmemoreDayService
        .findCoursewareLearningTimeInfo(sessionUser);

    json.setData(coursewareLearningTimeApiParam);

    Assert.assertEquals(200, json.getCode());
  }

}
