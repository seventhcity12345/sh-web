/** 
 * File: CourseCommentServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.course<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年9月1日 上午10:43:12
 * @author athrun.cw
 */
package com.webi.hwj.courseone2many;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.course.service.CourseOne2ManyService;

/**
 * Title: 一对多课程测试用例<br>
 * Description: CourseOne2ManyServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月13日 下午6:37:03
 * 
 * @author yangmh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class CourseOne2ManyServiceTest {

  @Resource
  CourseOne2ManyService courseOne2ManyService;

  @Test
  public void demo() {

  }
}
