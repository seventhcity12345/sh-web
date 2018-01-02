package com.webi.hwj.user;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.esapp.param.AppJsonObject;
import com.webi.hwj.esapp.param.UserInfoParam;
import com.webi.hwj.user.param.FloatingLayerInfoParam;
import com.webi.hwj.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class UserServiceTest {
  @Resource
  UserService userService;

  /**
   * 
   * Title: queryFloatingLayerInfoTest<br>
   * Description: 青少学员界面浮层接口测试方法<br>
   * CreateDate: 2017年7月4日 下午11:40:00<br>
   * 
   * @category queryFloatingLayerInfoTest
   * @author felix.yl
   * @throws Exception
   */
  @Test
  public void queryFloatingLayerInfoTest() throws Exception {
    FloatingLayerInfoParam floatingLayerInfo = userService.findFloatingLayerInfo(
        "14d8f822b2274a8dac315f9f7be74729");
    System.out.println(floatingLayerInfo);
  }

  /**
   * Title: app用户(学生)登录<br>
   * Description: app用户(学生)登录<br>
   * CreateDate: 2017年8月24日 下午3:29:45<br>
   * 
   * @category app用户(学生)登录
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void userLoginTest() throws Exception {
    AppJsonObject<UserInfoParam> json = new AppJsonObject<UserInfoParam>();

    String phone = "12345678901";
    String validCode = "1234";
    String ip = "10.0.0.1";
    int time = 60*60;

    // 判断是否验证码是否正确
    if (!StringUtils.isEmpty(MemcachedUtil.getConfigValue(phone))
        && MemcachedUtil.getConfigValue(phone).equals(validCode)) {
      // 用户登录并生产token
      SessionUser sessionUser = userService.appUserLogin(phone,
          ip, time);

      if (sessionUser != null) {
        // 登录成功
        UserInfoParam userInfoParam = new UserInfoParam();
        userInfoParam.setApp_userid(sessionUser.getToken());
        userInfoParam.setEnname(sessionUser.getEnglishName());
        userInfoParam.setNick_name(sessionUser.getRealName());
        userInfoParam.setHead_imgurl(sessionUser.getUserPhoto());
        json.setData(userInfoParam);
      } else {
        // 登录失败
        json.setError("该手机号没有正在执行中的合同");
        json.setState(false);
      }
    } else {
      json.setError("验证码错误！");
      json.setState(false);
    }

    System.out.println(json);
  }

}
