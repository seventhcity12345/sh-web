package com.webi.hwj.courseone2one;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.course.service.CourseOne2OneService;
import com.webi.hwj.courseone2one.entity.CourseOne2One;
import com.webi.hwj.courseone2one.param.CourseOne2OneParam;
import com.webi.hwj.courseone2one.service.AdminCourseOne2oneService;
import com.webi.hwj.teacher.param.TeacherTimeParam;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class AdminCourseOne2oneServiceTest {
  @Resource
  AdminCourseOne2oneService adminCourseOne2oneService;
  @Resource
  CourseOne2OneService courseOne2OneService;

  @Test
  public void demo() {

  }

  /**
   * Title: 1v1一键排课<br>
   * Description: 1v1一键排课<br>
   * CreateDate: 2016年7月27日 下午5:13:23<br>
   * 
   * @category 1v1一键排课
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void insertOnekeyCourseOne2oneSchedulingTest() throws Exception {
    String selectDate = "2016-7-27";
    String updateUserId = "3935209efe4c47249c97e77d79e177f8";

    boolean isRoomEnough = adminCourseOne2oneService.insertOnekeyCourseOne2oneScheduling(selectDate,
        updateUserId);

    System.out.println(isRoomEnough);
  }

  /**
   * Title: 查找course_type1列表<br>
   * Description: 查找course_type1列表<br>
   * CreateDate: 2016年9月7日 下午2:13:38<br>
   * 
   * @category 查找course_type1列表
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findCourseType1ListTest() throws Exception {
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("72dfe41853e64fef95dcf742de71cfd1");
    sessionUser.setPhone("12345678919");
    sessionUser.setCurrentLevel("General Level 3");

    List<CourseOne2OneParam> courseOne2OneList = courseOne2OneService
        .findCourseType1List(sessionUser);

    Assert.assertTrue(courseOne2OneList != null && courseOne2OneList.size() == 9);

  }

  /**
   * 
   * Title: 1v1排课测试<br>
   * Description: 1v1排课测试<br>
   * CreateDate: 2016年10月21日 下午4:26:34<br>
   * 
   * @category 1v1排课测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void addCourseOne2OneSchedulingTest() throws Exception {
    TeacherTimeParam teacherTimeParam = new TeacherTimeParam();
    teacherTimeParam.setTeacherId("");
    teacherTimeParam.setTeacherName("");
    teacherTimeParam.setStartTime(DateUtil.strToDateYYYYMMDDHHMMSS("2017-1-23 00:00:00"));
    teacherTimeParam.setCourseType("course_type1");

    JsonMessage json = adminCourseOne2oneService.addCourseOne2OneScheduling(teacherTimeParam);
//    Assert.assertTrue(json.isSuccess());

  }

  /**
   * 
   * Title: 查询demo课测试<br>
   * Description: findListCourseByCourseTypeTest<br>
   * CreateDate: 2016年12月22日 下午8:05:14<br>
   * 
   * @category 查询demo课测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findListCourseByCourseTypeTest() throws Exception {
    List<CourseOne2One> list = adminCourseOne2oneService.findListCourseByCourseType("course_type4");

    System.out.println(list);
  }

}
