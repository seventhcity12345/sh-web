package com.webi.hwj.course;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.course.service.AdminCourseCommentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class AdminCourseCommentServiceTest {
  @Resource
  AdminCourseCommentService adminCourseCommentService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试老师评价平均分查询<br>
   * Description: 测试老师评价平均分查询<br>
   * CreateDate: 2016年7月21日 下午7:14:06<br>
   * 
   * @category 测试老师评价平均分查询
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findToTeacherAverageScroreTest() throws Exception {

    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = adminCourseCommentService.findTeacherAverageScrore(param,
        DateUtil.strToDateYYYYMMDDHHMMSS("2016-01-22 00:00:00"),
        DateUtil.strToDateYYYYMMDDHHMMSS("2016-07-22 00:00:00"));
    System.out.println(p);

  }

  /**
   * Title: 测试老师评论查询<br>
   * Description: 测试老师评论查询<br>
   * CreateDate: 2016年7月23日 上午10:27:35<br>
   * 
   * @category 测试老师评论查询
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findfindTeacherCourseCommentListTest() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = adminCourseCommentService.findTeacherCourseCommentList(param,
        DateUtil.strToDateYYYYMMDDHHMMSS("2016-01-22 00:00:00"),
        DateUtil.strToDateYYYYMMDDHHMMSS("2016-07-22 00:00:00"));
    System.out.println(p);

  }

}
