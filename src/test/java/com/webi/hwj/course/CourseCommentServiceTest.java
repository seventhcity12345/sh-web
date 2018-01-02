package com.webi.hwj.course;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.course.param.CourseCommentDetailInfoParam;
import com.webi.hwj.course.param.StudentCommentToTeacherParam;
import com.webi.hwj.course.service.CourseCommentService;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentCountApiParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentCountParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentListApiParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentListParam;
import com.webi.hwj.subscribecourse.param.PageRowNumCourseTypeInfoParam;

/**
 * @category CourseCommentServiceTest测试类
 * @author alex
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class CourseCommentServiceTest {
  @Resource
  CourseCommentService courseCommentService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试前端学员的已完成课程数据查询.<br>
   * Description: 排课后更换老师<br>
   * CreateDate: 2016年10月27日 上午11:41:10<br>
   * 
   * @category 测试前端学员的已完成课程数据查询
   * @author alex
   */
  @Test
  public void testFindCoursesAndCommentsByUserId() throws Exception {
    String toUserId = "72dfe41853e64fef95dcf742de71cfd1";
    Integer page = 1;
    Integer rows = 5;

    Page p = courseCommentService.findCoursesAndCommentsByUserId(toUserId, page, rows);
    System.out.println(p);
  }

  /**
   * 
   * Title: findCourseCountCompletedTest<br>
   * Description: findCourseCountCompletedTest<br>
   * CreateDate: 2017年7月21日 下午4:28:54<br>
   * 
   * @category findCourseCountCompletedTest
   * @author felix.yl
   * @throws Exception
   */
  @Test
  public void findCourseCountCompletedTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CourseTypeCommentCountApiParam> json =
        new CommonJsonObject<CourseTypeCommentCountApiParam>();

    // 构建参数
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("52b4dc83fc554a79857f5d5ff5ade251");

    // 调用Service
    List<CourseTypeCommentCountParam> courseCountCompletedList = courseCommentService
        .findCourseCountCompleted(sessionUser);

    CourseTypeCommentCountApiParam courseTypeCommentCountApiParam =
        new CourseTypeCommentCountApiParam();
    courseTypeCommentCountApiParam.setCourseTypeCommentCountParamList(courseCountCompletedList);

    json.setData(courseTypeCommentCountApiParam);

    Assert.assertEquals(200, json.getCode());

  }

  /**
   * 
   * Title: findCourseListCompletedTest<br>
   * Description: findCourseListCompletedTest<br>
   * CreateDate: 2017年7月24日 上午11:47:03<br>
   * 
   * @category findCourseListCompletedTest
   * @author felix.yl
   * @throws Exception
   */
  @Test
  public void findCourseListCompletedTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CourseTypeCommentListApiParam> json =
        new CommonJsonObject<CourseTypeCommentListApiParam>();

    // 构建参数
    String userId = "0af6e18487544543b144c8758827b026";
    String courseType = "course_type1";
    int page = 1;
    int rows = 5;

    // 调用Service
    List<CourseTypeCommentListParam> courseTypeCommentListParam = courseCommentService
        .findCourseListCompleted(userId, courseType, page, rows);

    CourseTypeCommentListApiParam courseTypeCommentListApiParam =
        new CourseTypeCommentListApiParam();
    courseTypeCommentListApiParam.setCourseTypeCommentListParamList(courseTypeCommentListParam);

    json.setData(courseTypeCommentListApiParam);

    Assert.assertEquals(200, json.getCode());
  }

  /**
   * 
   * Title: findCourseCommentDetailCompletedTest<br>
   * Description: findCourseCommentDetailCompletedTest<br>
   * CreateDate: 2017年7月24日 下午6:34:39<br>
   * 
   * @category findCourseCommentDetailCompletedTest
   * @author officer
   * @throws Exception
   */
  @Test
  public void findCourseCommentDetailCompletedTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CourseCommentDetailInfoParam> json =
        new CommonJsonObject<CourseCommentDetailInfoParam>();

    // 构建参数
    String subscribeCourseId = "0199fc74402b4b98a867e7cc5ead0d9b";
    String userId = "fd25a88292014076b2ee2f477b403348";

    // 调用Service
    CourseCommentDetailInfoParam courseCommentDetailCompleted = courseCommentService
        .findCourseCommentDetailCompleted(subscribeCourseId, userId);

    json.setData(courseCommentDetailCompleted);

    Assert.assertEquals(200, json.getCode());

  }

  /**
   * 
   * Title: updateCommentToTeacherTest<br>
   * Description: updateCommentToTeacherTest<br>
   * CreateDate: 2017年7月25日 上午11:37:47<br>
   * 
   * @category updateCommentToTeacherTest
   * @author officer
   * @throws Exception
   */
  @Test
  public void updateCommentToTeacherTest() throws Exception {
    // 构建对象(构建对象传参的时候frmoUserId字段不是必须的,这个字段是为了查后台传参)
    StudentCommentToTeacherParam objParam = new StudentCommentToTeacherParam();
    objParam.setSubscribeCourseId("55ff7c23e42143c7ba5d7a2905cd5cf7");// 预约课程id
    objParam.setToUserId("7fa43cc0e4ae43408247d6df825a84df");// 被评论人id(老师id)
    objParam.setDeliveryScore("5");
    objParam.setInteractionScore("5");
    objParam.setPreparationScore("5");
    objParam.setShowScore("5");
    objParam.setCommentContent("这个老师还不错,挺好的,但是希望不要骄傲,我就是随便测试测试.");

    // 获取用户id(评论人id)
    String userId = "166f92ec49974597ab652d003753af54";

    // 调用Service
    courseCommentService.updateCommentToTeacher(objParam, userId);
  }

  /**
   * 
   * Title: findPageAndRowsBySubscribeIdTest<br>
   * Description: findPageAndRowsBySubscribeIdTest<br>
   * CreateDate: 2017年7月27日 上午11:10:51<br>
   * 
   * @category findPageAndRowsBySubscribeIdTest
   * @author officer
   * @throws Exception
   */
  @Test
  public void findPageAndRowsBySubscribeIdTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<PageRowNumCourseTypeInfoParam> json =
        new CommonJsonObject<PageRowNumCourseTypeInfoParam>();

    // 构建参数
    int rows = 3;
    String subscribeId = "f2228fa71d394b90903c53d361c678ac";
    String userId = "7fa43cc0e4ae43408247d6df825a84df";

    // 调用Service
    PageRowNumCourseTypeInfoParam pageAndRows = courseCommentService.findPageAndRowsBySubscribeId(
        rows, subscribeId, userId);

    json.setData(pageAndRows);

    Assert.assertEquals(200, json.getCode());
  }

}