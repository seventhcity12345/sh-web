package com.webi.hwj.weixin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.weixin.entity.TeacherWeixin;
import com.webi.hwj.weixin.entity.UserWeixin;
import com.webi.hwj.weixin.service.ObtainOpenIdByNoBindUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class ObtainOpenIdByNoBindUserServiceTest {

  private static Logger logger = Logger.getLogger(ObtainOpenIdByNoBindUserServiceTest.class);

  @Resource
  ObtainOpenIdByNoBindUserService obtainOpenIdByNoBindUserService;

  /**
   * 
   * Title: obtaionAllOpenIdTest<br>
   * Description: obtaionAllOpenIdTest:获取所有关注公众号用户的openId测试<br>
   * CreateDate: 2017年7月4日 上午9:11:22<br>
   * 
   * @category obtaionAllOpenIdTest
   * @author felix.yl
   */
  @Test
  public void obtaionOpenIdTest() {
    try {
      List<String> openIdList = obtainOpenIdByNoBindUserService.findOpenId();
      System.out.println("---------------------获取到所有关注公众号用户的openId个数为：" + openIdList.size());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
    }
  }

  /**
   * 
   * Title: obtainAlreadyBindOpenIdTest<br>
   * Description: 测试获取所有"已关注且绑定"微信服务号的用户的openId;(成人)<br>
   * CreateDate: 2017年7月6日 下午2:07:22<br>
   * 
   * @category obtainAlreadyBindOpenIdTest
   * @author felix.yl
   * @return
   * @throws Exception
   */
  @Test
  public void obtainAlreadyBindOpenIdTest() throws Exception {

    List<UserWeixin> openIdListBySpeakHiUser = obtainOpenIdByNoBindUserService
        .findAlreadyBindOpenIdUser();

    System.out.println("(成人)已绑定的学员List：" + openIdListBySpeakHiUser);
    System.out.println("(成人)已绑定的学员list大小：" + openIdListBySpeakHiUser.size());

    List<TeacherWeixin> openIdListBySpeakHiTeacher = obtainOpenIdByNoBindUserService
        .findAlreadyBindOpenIdTeacher();

    System.out.println("(成人)已绑定的教师List：" + openIdListBySpeakHiTeacher);
    System.out.println("(成人)已绑定的教师List大小：" + openIdListBySpeakHiTeacher.size());
  }

  /**
   * 
   * Title: obtainAlreadyBindOpenIdByTeenagerTest<br>
   * Description: 测试调用青少接口-查询青少数据库中所有的openID<br>
   * CreateDate: 2017年7月6日 下午4:02:15<br>
   * 
   * @category obtainAlreadyBindOpenIdByTeenagerTest
   * @author officer
   * @throws Exception
   */
  @Test
  public void obtainAlreadyBindOpenIdByTeenagerTest() throws Exception {

    List<UserWeixin> list = obtainOpenIdByNoBindUserService.findAlreadyBindOpenIdByTeenager();

    System.out.println(list);
    System.out.println(list.size());
  }

  /**
   * 
   * Title: 整合出需要推送消息的所有openId测试<br>
   * Description: listOperationsendMessageTest<br>
   * CreateDate: 2017年7月6日 下午6:48:48<br>
   * 
   * @category 整合出需要推送消息的所有openId测试
   * @author officer
   * @throws Exception
   */
  @Test
  public void listOperationsendMessageTest() throws Exception {

    List<String> list = obtainOpenIdByNoBindUserService.findListOperationsendMessage();

    System.out.println(list);
    System.out.println(list.size());
  }

  /**
   * 
   * Title: 微信推送-提醒用户绑定微信公众号<br>
   * Description: sendMessageRemindBindTest<br>
   * CreateDate: 2017年7月7日 下午1:49:22<br>
   * 
   * @category 微信推送-提醒用户绑定微信公众号
   * @author felix.yl
   */
  @Test
  public void sendMessageRemindBindTest() {
    obtainOpenIdByNoBindUserService.sendMessageRemindBind();
  }

  /**
   * 
   * Title: 微信推送-提醒用户绑定微信公众号【多线程】<br>
   * Description: sendMessageRemindBindTest<br>
   * CreateDate: 2017年7月7日 下午1:49:22<br>
   * 
   * @category 微信推送-提醒用户绑定微信公众号【多线程】
   * @author felix.yl
   * @throws InterruptedException
   */
  @Test
  public void sendMessageRemindBindThreadTest() throws InterruptedException {
    obtainOpenIdByNoBindUserService.sendMessageRemindBindThread();
  }

}
