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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.HttpClientUtil;
import com.webi.hwj.course.entity.AdminCourse;
import com.webi.hwj.course.service.AdminCourseService;

/**
 * Title: AdminCourseAllServiceTest.<br>
 * Description: AdminCourseAllServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年9月14日 下午3:45:22
 * 
 * @author yangmh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class AdminCourseAllServiceTest {
  @Resource
  private AdminCourseService adminCourseAllService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试查询1v1和1vN的课程 <br>
   * Description: 测试查询1v1和1vN的课程<br>
   * CreateDate: 2016年4月12日 上午9:25:30<br>
   * 
   * @category 测试查询1v1和1vN的课程
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findAllCourseTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("page", "1");
    paramMap.put("rows", "20");
    Page page = adminCourseAllService.findAllCourse(paramMap);
    System.out.println("查询所有课程" + page);
  }

  /**
   * Title: 新增课程测试<br>
   * Description: 新增课程测试<br>
   * CreateDate: 2016年7月26日 下午3:18:17<br>
   * 
   * @category 新增课程测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void addCourseTest() throws Exception {

    AdminCourse course = new AdminCourse();

    course.setCategoryType("category_type2");
    course.setCourseType("course_type2");
    course.setCourseTitle("SevenJunitTest");
    course.setCourseLevel("General Level 1,General Level 14,General Level 15,General Level 16");
    course.setCourseDesc("单元测试！！！");

    byte[] a = HttpClientUtil.doGetReturnByte(
        "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images/course/englishstudio/0301-0312/Culture%20Shock.jpg");
    byte[] b = HttpClientUtil.doGetReturnByte(
        "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/courseware/one2one/Business%20Level%201/78bff34e38064e379b101433490a0f7c.pptx");

    MultipartFile coursePicFile = new MockMultipartFile("alex1", "a.jpg", null, a);
    MultipartFile courseCoursewareFile = new MockMultipartFile("alex2",
        "a.pptx", null, b);

    JsonMessage json = adminCourseAllService.addCourse(course, courseCoursewareFile, coursePicFile);
    Assert.assertTrue(json.isSuccess());

  }
}
