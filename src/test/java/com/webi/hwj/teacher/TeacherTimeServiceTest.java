package com.webi.hwj.teacher;

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

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.teacher.param.FindTimesAndTeachersByDayParam;
import com.webi.hwj.teacher.service.TeacherTimeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class TeacherTimeServiceTest {
  @Resource
  TeacherTimeService teacherTimeService;

  @Test
  public void demo() {

  }

  /**
   * Title: 查询教师端-课程中心-core课程列表<br>
   * Description: 查询教师端-课程中心-core课程列表<br>
   * CreateDate: 2017年6月7日 下午2:19:48<br>
   * 
   * @category 查询教师端-课程中心-core课程列表
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findTeacherCourseCenterCoreCourseListTest() throws Exception {
    String queryDate = "2017-6-5";
    String teacherId = "6eaba5476f694be69b44169071d64c19";
    String type = "2";
    System.out.println(teacherTimeService
        .findTeacherCourseCenterCoreCourseList(queryDate, teacherId, type));
  }

  /**
   * Title: 查询教师端-课程中心-extra课程列表.<br>
   * Description: 查询教师端-课程中心-extra课程列表.<br>
   * CreateDate: 2017年6月7日 下午2:20:26<br>
   * 
   * @category 查询教师端-课程中心-extra课程列表.
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findTeacherCourseCenterExtraCourseListTest() throws Exception {
    String queryDate = "2017-02-27";
    String teacherId = "7c707d0b45374be38fd5c3b82c15ee5b";
    String type = "0";
    System.out.println(teacherTimeService
        .findTeacherCourseCenterExtraCourseList(queryDate, teacherId, type));
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
  public void findTeacherTimeAndTeacherInfoTest() throws Exception {

    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = teacherTimeService.findSubscribeTeacherTimeList(param,
        DateUtil.strToDateYYYYMMDDHHMMSS("2016-01-22 00:00:00"),
        DateUtil.strToDateYYYYMMDDHHMMSS("2016-07-22 00:00:00"));
    System.out.println(p);

  }

  /**
   * Title: 查询core1v1头部日期测试<br>
   * Description: findCourseType1TopDateListTest<br>
   * CreateDate: 2016年9月6日 下午2:40:50<br>
   * 
   * @category 查询core1v1头部日期测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findCourseType1TopDateListTest() throws Exception {
    String userId = "52b4dc83fc554a79857f5d5ff5ade251";
    Date endOrderTime = DateUtil.strToDateYYYYMMDD("2017-09-13 15:55:00");
    List<Date> list = teacherTimeService.findCourseType1TopDateList(userId, endOrderTime);
    System.out.println("XX" + list);
  }

  /**
   * Title: 按天查询老师时间测试<br>
   * Description: 按天查询老师时间测试<br>
   * CreateDate: 2016年9月6日 下午4:31:00<br>
   * 
   * @category 按天查询老师时间测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findCourseType1TeacherTimeListTest() throws Exception {
    Date startTime = DateUtil.strToDateYYYYMMDD("2016-09-12");
    System.out.println(startTime.getTime());
    List<FindTimesAndTeachersByDayParam> list = teacherTimeService
        .findCourseType1TeacherTimeList(startTime);
    System.out.println("teacherTimes:" + list);
  }

  /**
   * 
   * Title: 查询头部日期测试<br>
   * Description: findTopDateListByCourseTypeTest<br>
   * CreateDate: 2016年10月17日 下午3:50:52<br>
   * 
   * @category 查询头部日期 测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findTopDateListByCourseTypeTest() throws Exception {
    String userId = "52b4dc83fc554a79857f5d5ff5ade251";
    Date endOrderTime = DateUtil.strToDateYYYYMMDDHHMMSS("2017-09-13 15:06:00");
    List<Date> list = teacherTimeService.findTopDateListByCourseType(userId, endOrderTime,
        "course_type1");
    System.out.println("XX" + list);
  }

  /**
   * 
   * Title: 查询拥有某些权限的老师时间<br>
   * Description: findTimesAndTeachersByDayAndCourseTypeTest<br>
   * CreateDate: 2016年12月22日 下午9:29:24<br>
   * 
   * @category 查询拥有某些权限的老师时间
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findTimesAndTeachersByDayAndCourseTypeTest() throws Exception {
    System.out
        .println(teacherTimeService.findTimesAndTeachersByDayAndCourseType("2017-06-06",
            "course_type1", ""));
  }

  /**
   * Title: 教师端-头部日期列表查询<br>
   * Description: 教师端-头部日期列表查询<br>
   * CreateDate: 2017年6月7日 下午2:07:02<br>
   * 
   * @category 教师端-头部日期列表查询
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findTeacherCourseCenterTopDateListTest() throws Exception {
    String teacherId = "8346726d6768461e996e2c63f4d779a6";
    String queryDate = "2017-6-6";
    System.out.println(teacherTimeService
        .findTeacherCourseCenterTopDateList(teacherId, queryDate));
  }
}
