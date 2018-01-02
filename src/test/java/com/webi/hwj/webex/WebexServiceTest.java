package com.webi.hwj.webex;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.webex.service.WebexSubscribeService;
import com.webi.hwj.webex.util.WebexUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class WebexServiceTest {
  @Resource
  WebexSubscribeService webexSubscribeService;

  @Test
  public void demo(){
  }
  
  //@Test
  public void enterWebexTestRoomTest() throws Exception {
    WebexUtil.createWebexJoinUrl("https://webiedu.webex.com.cn/WBXService/XMLService", 
        "room01", "web01", "webexedu01@webi.com.cn", "22277", "P0qzM8pSGsJuExDhmTc1IQ", "518865634", "Test");
  }

  // @Test
  public void createWebexMeetingTest() throws Exception {
    String teacherTimeId = "358c91553843464a8e4f28d76cbb4415";
    String meetingTitle = "alex7777";
    webexSubscribeService.createWebexMeeting(teacherTimeId, meetingTitle);
  }

  // @Test
  public void createWebexMeetingHostUrlTest() throws Exception {
    String teacherTimeId = "358c91553843464a8e4f28d76cbb4415";
    webexSubscribeService.createWebexMeetingHostUrl(teacherTimeId);
  }

  // @Test
  public void createWebexMeetingJoinUrlTest() throws Exception {
    String subscribeId = "0ab33529497545c28e39f243f069865e";
    webexSubscribeService.createWebexMeetingJoinUrl(subscribeId, "seven(22222)");
  }

  // @Test
  // public void deleteWebexMeetingTest() throws Exception {
  // String teacherTimeId = "358c91553843464a8e4f28d76cbb4415";
  // webexSubscribeService.deleteWebexMeeting(teacherTimeId);
  // }
}
