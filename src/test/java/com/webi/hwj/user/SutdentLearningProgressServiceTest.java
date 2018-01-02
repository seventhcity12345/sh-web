package com.webi.hwj.user;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.admin.service.AdminConfigService;
import com.webi.hwj.user.service.SutdentLearningProgressService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class SutdentLearningProgressServiceTest {
  @Resource
  SutdentLearningProgressService sutdentLearningProgressService;
  @Resource
  AdminConfigService adminConfigService;

  @Test
  public void demo() {

  }

  /**
   * Title: 学习进度查询测试<br>
   * Description: 学习进度查询测试<br>
   * CreateDate: 2016年6月13日 下午2:40:14<br>
   * 
   * @category 学习进度查询测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findStudentLearningProgressTest() throws Exception {
    System.out.println(sutdentLearningProgressService.findStudentLearningProgress(
        "52b4dc83fc554a79857f5d5ff5ade251", "e7e5985b675042738018aba7529d2c30"));
  }

  // @Test
  public void findRandomSentenceTest() throws Exception {
    System.out.println(adminConfigService.findRandomSentence(-5));
  }

  @Test
  public void findRsaEffectiveWorkTimeTest() throws Exception {
    System.out.println(DateUtil.strToDateYYYYMMDD("2016-06-01").getTime());
    System.out.println(sutdentLearningProgressService.findRsaEffectiveWorkTime(
        DateUtil.strToDateYYYYMMDD("2017-06-01"), "e1083140b01c4cd5b6643b6b88316088"));
  }

  /**
   * 
   * Title: 学习进度测试<br>
   * Description: findLearningProgressTest<br>
   * CreateDate: 2017年7月20日 下午7:55:17<br>
   * 
   * @category 学习进度测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findLearningProgressTest() throws Exception {
    System.out.println(sutdentLearningProgressService.findLearningProgress(
        "52b4dc83fc554a79857f5d5ff5ade251",
        DateUtil.strToDateYYYYMMDD("2017-06-01")));
  }

  /**
   * 
   * Title: rsa时长查询测试<br>
   * Description: findRsaLearningProgress<br>
   * CreateDate: 2017年9月13日 下午2:22:09<br>
   * 
   * @category rsa时长查询测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findRsaLearningProgress() throws Exception {
    System.out.println(sutdentLearningProgressService.findRsaLearningProgress(2, 2,
        "bbbad702ccba4c7fa230365601175bbc"));
  }
}
