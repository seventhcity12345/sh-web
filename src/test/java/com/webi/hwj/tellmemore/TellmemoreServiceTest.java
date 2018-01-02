package com.webi.hwj.tellmemore;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.statistics.service.StatisticsMarathonService;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.tellmemore.util.TellmemoreUtil;

/**
 * Title: TellmemorePercentServiceTest<br>
 * Description: TellmemorePercentServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年10月21日 下午8:18:42
 * 
 * @author yangmh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class TellmemoreServiceTest {
  @Resource
  private TellmemoreService tellmemoreService;

  @Resource
  private StatisticsMarathonService statisticsMarathonService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试初始化tmm数据<br>
   * . Description: initTmmDataTest<br>
   * CreateDate: 2015年10月21日 下午8:28:54<br>
   * 
   * @category initTmmDataTest
   * @author yangmh
   * @throws Exception
   */
  // //@Test
  public void initTmmDataTest() throws Exception {
    // tellmemoreService.initTmmData("19999999999",
    // "6a43b24f41d542c88716f08d8fd380df");
    // TellmemoreUtil.createTmmUser("19999999999","6a43b24f41d542c88716f08d8fd380df");
  }

  /**
   * Title: 统计马拉松每天学员数据变化测试<br>
   * Description: 统计马拉松每天学员数据变化测试<br>
   * CreateDate: 2016年4月7日 下午1:34:17<br>
   * 
   * @category 统计马拉松每天学员数据变化测试
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void statisticsMarathonChangeEverydayTest() throws Exception {
    statisticsMarathonService.statisticsMarathonChangeEveryday();
  }

  /**
   * Title: 根据用户RSA账号调用接口查询用户数据<br>
   * Description: fetchTmmUserPercentByUserPhoneTest<br>
   * CreateDate: 2016年3月28日 上午11:11:26<br>
   * 
   * @category fetchTmmUserPercentByUserPhoneTest
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void fetchTmmUserPercentByUserPhoneTest() throws Exception {
    try {
      TellmemoreUtil.fetchUserInformationAndSumByUserPhone("12345678919");
    } catch (IOException e) {
      System.out.println("111111test:" + e.toString());
    } finally {

    }
  }

  /**
   * Title: 测试抓取根据txt文档，扫描的一个教员下所有用户的三围总属性并插入到我们自己的库<br>
   * Description: 测试抓取根据txt文档，扫描的一个教员下所有用户的三围总属性并插入到我们自己的库<br>
   * CreateDate: 2016年3月24日 下午2:12:31<br>
   * 
   * @category 测试抓取根据txt文档，扫描的一个教员下所有用户的三围总属性并插入到我们自己的库
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void fetchTmmUserPercentAndInsertByUserPhoneTest() throws Exception {
    tellmemoreService.fetchTmmUserPercentAndInsertByUserPhone();

    // try {
    // Thread.sleep(Long.valueOf("9999999"));
    // } catch (NumberFormatException e) {
    // e.printStackTrace();
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
  }

  /**
   * Title: 测试抓取用户的百分比并更新到我们自己的库<br>
   * Description: 测试抓取用户的百分比并更新到我们自己的库<br>
   * CreateDate: 2015年10月21日 下午9:06:40<br>
   * 
   * @category 测试抓取用户的百分比并更新到我们自己的库
   * @author yangmh
   * @throws Exception
   */
  // @Test
  public void fetchTmmUserPercentAndUpdateTest() throws Exception {
    System.out.println("111" + tellmemoreService);

    int totalPupilCount = 0;
    int currentPage = 8;
    int pageSize = 20;

    tellmemoreService.fetchTmmUserPercentAndUpdate(currentPage, pageSize);

    // do{
    // int temp =
    // tellmemoreService.fetchTmmUserPercentAndUpdate(currentPage,pageSize);
    // if(totalPupilCount==0){
    // totalPupilCount = temp;
    // }
    // currentPage++;
    // totalPupilCount = totalPupilCount - pageSize;
    // }while(totalPupilCount>0);
  }

  /**
   * Title: RSA删除用户测试<br>
   * Description: RSA删除用户测试<br>
   * CreateDate: 2016年8月16日 下午2:16:30<br>
   * 
   * @category RSA删除用户测试
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void deleteTmmUserTest() throws Exception {
    String userId = "f0558acc3cf84400a9232af20488894a";
    tellmemoreService.deleteTmmUser(userId);
  }

  /**
   * Title: 更新rsa用户数据<br>
   * Description: 更新rsa用户数据<br>
   * CreateDate: 2016年9月7日 下午2:50:03<br>
   * 
   * @category 更新rsa用户数据
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void updateTellmemorePercentTest() throws Exception {
    tellmemoreService.updateTellmemorePercent("12345678919", "72dfe41853e64fef95dcf742de71cfd1",
        "General Level 3");
  }

  /**
   * Title: 获取用户的课件完成度Map<br>
   * Description: findUserPercentMapTest<br>
   * CreateDate: 2015年10月21日 下午9:55:11<br>
   * 
   * @category findUserPercentMapTest
   * @author yangmh
   * @throws Exception
   */
  // //@Test
  // public void findUserPercentMapTest() throws Exception {
  // Map<String,Object> xx =
  // tellmemoreService.findUserPercentMap("c5cd6c5774c24bc68224ba2f7ecf9f43");
  // for (String key : xx.keySet()) {
  // System.out.println("key= "+ key + " and value= " + xx.get(key));
  // }
  // }

  /**
   * Title: 判断学员是否完成课件<br>
   * Description: judgeUserPercentCompleteTest<br>
   * CreateDate: 2015年10月21日 下午9:55:59<br>
   * 
   * @category judgeUserPercentCompleteTest
   * @author yangmh
   * @throws Exception
   */
  // //@Test
  // public void judgeUserPercentCompleteTest() throws Exception {
  // tellmemoreService.judgeUserPercentComplete("4d48d6caca714364afc9abacda27c2x7","c5cd6c5774c24bc68224ba2f7ecf9f43");
  // tellmemoreService.judgeUserPercentComplete("4d48d6caca714364afc9abacda27c2x9","c5cd6c5774c24bc68224ba2f7ecf9f43");
  // }

}
