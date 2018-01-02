/** 
 * File: CourseCommentServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.course<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年9月1日 上午10:43:12
 * @author athrun.cw
 */
package com.webi.hwj.courseone2many;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.courseone2many.service.AdminCourseOne2ManySchedulingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class AdminCourseOneToManySchedulingServiceTest {

  @Resource
  private AdminCourseOne2ManySchedulingService adminCourseOne2ManySchedulingService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试查询有时间老师方法<br>
   * Description: 测试查询有时间老师方法<br>
   * CreateDate: 2016年4月26日 下午3:02:40<br>
   * 
   * @category 测试查询有时间老师方法
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void findAvailableTeacherByTimeTest() throws Exception {
    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS("2017-01-14 09:00:00");
    Date endTime = DateUtil.strToDateYYYYMMDDHHMMSS("2017-01-14 09:05:00");
    List<String> list = new ArrayList<String>();
    list.add("course_type2");
    list.add("course_type8");
    System.out
        .println(adminCourseOne2ManySchedulingService.findAvailableTeacherByTime(startTime, endTime,
            list));
  }
}
