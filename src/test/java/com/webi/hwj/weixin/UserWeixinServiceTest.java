package com.webi.hwj.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.weixin.constant.WeixinConstant;
import com.webi.hwj.weixin.entity.UserWeixin;
import com.webi.hwj.weixin.entity.WeixinMsgOption;
import com.webi.hwj.weixin.param.BindUserParam;
import com.webi.hwj.weixin.service.UserWeixinService;
import com.webi.hwj.weixin.util.WeixinUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class UserWeixinServiceTest {
  @Resource
  UserWeixinService userWeixinService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试微信下发消息.<br>
   * Description: sendWeixinMsgTest<br>
   * CreateDate: 2016年10月18日 下午7:15:40<br>
   * 
   * @category 测试微信下发消息
   * @author yangmh
   */
  // @Test
  public void sendWeixinMsgTest() throws Exception {
    Map<String, Object> dataMap = new HashMap<String, Object>();
    // 下发消息的消息体，根据不同的模板，不一样的写法
    dataMap.put("first", new WeixinMsgOption("亲爱的学员，您已成功预定以下课程，我们不见不散哦~"));
    // 课程名称
    dataMap.put("keyword1", new WeixinMsgOption("飒飒1"));

    // 时间
    dataMap.put("keyword2",
        new WeixinMsgOption(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")));
    // 取消预约的时候没有这个
    // dataMap.put("remark", new WeixinMsgOption("点击详情,查看课程详细情况"));

    List<UserWeixin> userWeixinList = userWeixinService
        .findOpenIdListByUserId("037ebaca39224d0aa930485a032b7192");
    if (userWeixinList != null && userWeixinList.size() > 0) {
      for (UserWeixin userWeixin : userWeixinList) {

        WeixinUtil.sendWeixinMsg(userWeixin.getOpenId(),
            WeixinConstant.getWeixinMsgTemplateIdSubscribe(), null, dataMap);

      }
    }
  }

  /**
   * Title: 查询微信用户信息测试用例<br>
   * Description: findUserInfoTest<br>
   * CreateDate: 2016年10月20日 下午5:17:09<br>
   * 
   * @category 查询微信用户信息测试用例
   * @author yangmh
   * @throws Exception
   */
  // @Test
  public void findUserInfoTest() throws Exception {
    WeixinUtil.findUserInfo("ocTLXjohO6LNgRBHvx7R2irISnoU",
        "a3AWesWniB17HqeVEAQH7Qu9CwctA-txFmiCFCr9wROyAHkuYXBTHcqXt-yn7dB2wMQICCVXcRkY7JSoJggCmcgMgqygRsEcuM28vatHNds");
  }

  /**
   * Title: 微信用户绑定测试用例.<br>
   * Description: bindUserTest<br>
   * CreateDate: 2016年10月20日 下午5:21:56<br>
   * 
   * @category 微信用户绑定测试用例
   * @author yangmh
   */
  // @Test
  public void bindUserTest() throws Exception {
    BindUserParam bindUserParam = new BindUserParam();
    bindUserParam.setPhone("18888888887");
    bindUserParam.setWeixinNickName("偏头痛杨洋");
    bindUserParam.setWeixinOpenId("ocTLXjohO6LNgRBHvx7R2irYYYYY");
    bindUserParam.setWeixinUserPhoto("http://speakhi.com/web/dist/images/common/sh_logo.gif");
    userWeixinService.bindUser(bindUserParam);
  }
}
