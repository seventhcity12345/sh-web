package com.webi.hwj.user;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.ordercourse.service.AdminOrderCourseOptionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class UserSessionTest {
  @Resource
  AdminOrderCourseOptionService adminOrderCourseOptionService;

  @Test
  public void demo() {

  }

  // @Test
  public void test() throws Exception {
    String userId = "b43c0970d1934c96a83c9a1c05f3f8f0";
    Map<String, Object> a = adminOrderCourseOptionService.findCourseTypesByUserId(userId);
    System.out.println(a);

  }
}
