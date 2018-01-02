package com.webi.hwj.courseextension1v1;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.courseextension1v1.service.CourseExtension1v1Service;
import com.webi.hwj.courseone2one.param.CourseOne2OneParam;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class CourseExtension1v1ServiceTest {
  @Resource
  CourseExtension1v1Service courseExtension1v1Service;

  /**
   * Title: 查找course_type9列表<br>
   * Description: 查找course_type9列表<br>
   * CreateDate: 2016年9月7日 下午2:13:38<br>
   * 
   * @category 查找course_type9列表
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findCourseType9ListTest() throws Exception {
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("72dfe41853e64fef95dcf742de71cfd1");
    sessionUser.setPhone("12345678919");
    sessionUser.setCurrentLevel("General Level 3");

    List<CourseOne2OneParam> courseOne2OneList = courseExtension1v1Service
        .findCourseType9List(sessionUser);

    Assert.assertTrue(courseOne2OneList != null && courseOne2OneList.size() == 9);
  }
}
