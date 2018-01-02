package com.webi.hwj.subscribecourse;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseService;

/**
 * 
 * Title: 测试预约的逻辑（1v1）<br>
 * Description: SubscribeCourseServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月30日 下午6:31:22
 * 
 * @author athrun.cw
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
// @Transactional // 事务自动回滚
public class BaseSubscribeCourseServiceTest {
  @Resource
  BaseSubscribeCourseService baseSubscribeCourseService;

  @Test
  public void demo() {

  }

  /**
   * Title: 待评价课程列表<br>
   * Description: 待评价课程类别1v1,预约前置判定<br>
   * CreateDate: 2016年12月12日 下午4:11:55<br>
   * 
   * @category 待评价课程列表
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void subscribeDemoEntryTest() throws Exception {
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setUserId("52b4dc83fc554a79857f5d5ff5ade251");
    subscribeCourse.setTeacherTimeId("3b95ced30a814c6da9b92c73559730ca");
    subscribeCourse.setCourseId("fc7735573d64448f94e5gz2016122001");
    subscribeCourse.setCourseType("course_type4");
    System.out.println(baseSubscribeCourseService.subscribeDemoEntry(subscribeCourse, "", "aaa"));
  }

}
