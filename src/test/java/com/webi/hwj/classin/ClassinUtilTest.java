package com.webi.hwj.classin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.classin.util.ClassinUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class ClassinUtilTest {

  @Test
  public void demo() {

  }

  /**
   * 
   * Title: classin创建房间测试<br>
   * Description: pushUserDateToCrmTest<br>
   * CreateDate: 2017年8月24日 下午3:54:31<br>
   * 
   * @category classin创建房间测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void createRoomTest() throws Exception {
    // ConvertlabUtil.obtainCustomers(DateUtil.dateToStr(new Date(),
    // "yyyy-MM-dd'T'hh:mm:ss'Z'"));
    ClassinUtil.createRoom("sevenTest",
        "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images/course/one2many/course_type8/77dd9fbf5ed34034b09df10eefc89df2.jpg",
        "0977fd7411894acfbb8181f3e5fafaba", "teacherSeven",
        "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images/course/one2many/course_type8/77dd9fbf5ed34034b09df10eefc89df2.jpg",
        DateUtil.strToDateYYYYMMDDHHMMSS("2017-08-24 16:00:00"), 5, "sdfsdfdsf");
  }

  /**
   * 
   * Title: 测试获取appurl<br>
   * Description: findAppUrlTest<br>
   * CreateDate: 2017年8月25日 下午8:26:23<br>
   * 
   * @category 测试获取appurl
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findAppUrlTest() throws Exception {
    System.out.println(ClassinUtil.findAppUrl("100200", "100001", "teacherSeven",
        "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images/course/one2many/course_type8/77dd9fbf5ed34034b09df10eefc89df2.jpg"));
  }

  /**
   * 
   * Title: 老师进入聊天室测试<br>
   * Description: goToClassinClassChatTeacher<br>
   * CreateDate: 2017年8月28日 下午8:37:27<br>
   * 
   * @category 老师进入聊天室测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void goToClassinClassChatTeacher() throws Exception {
    System.out.println(ClassinUtil.goToClassinClassChatTeacher("100106"));
  }

}
