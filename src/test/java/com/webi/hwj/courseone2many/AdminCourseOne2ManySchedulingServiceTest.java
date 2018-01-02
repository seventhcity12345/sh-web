/** 
 * File: CourseCommentServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.course<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年9月1日 上午10:43:12
 * @author athrun.cw
 */
package com.webi.hwj.courseone2many;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.courseone2many.service.AdminCourseOne2ManySchedulingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class AdminCourseOne2ManySchedulingServiceTest {

  @Resource
  private AdminCourseOne2ManySchedulingService adminCourseOne2ManySchedulingService;

  @Test
  public void demo() {

  }

  /**
   * Title: 查询排课列表测试<br>
   * Description: 查询排课列表测试<br>
   * CreateDate: 2016年7月26日 上午11:14:51<br>
   * 
   * @category 查询排课列表测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findSchedulingListTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("sort", null);
    paramMap.put("order", null);
    paramMap.put("page", "1");
    paramMap.put("rows", "20");
    // 查询排课信息
    Page p = adminCourseOne2ManySchedulingService.findSchedulingList(paramMap);
    System.out.println(p);
  }

}
