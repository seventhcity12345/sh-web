package com.webi.hwj.teacher;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.course.param.CourseCommentParam;
import com.webi.hwj.teacher.service.TeacherCourseCommentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class TeacherCourseCommentServiceTest {
  @Resource
  TeacherCourseCommentService teacherCourseCommentService;

  @Test
  public void demo() {

  }

  /**
   * Title: 老师中心的评价页面数据加载<br>
   * Description: 老师中心的评价页面数据加载<br>
   * CreateDate: 2017年6月7日 下午2:31:08<br>
   * @category 老师中心的评价页面数据加载 
   * @author komi.zsy
   */
  @Test
  public void findTeacherCommentPageTest() throws Exception{
    String teacherId = "7c707d0b45374be38fd5c3b82c15ee5b";
    String courseTypeCheckBox = "course_type1";

    System.out.println(teacherCourseCommentService.findTeacherCommentPage(teacherId,
        courseTypeCheckBox, 1, 5));
  }

  /**
   * Title: 老师查看自己给学生的评论<br>
   * Description: 老师查看自己给学生的评论<br>
   * CreateDate: 2017年6月7日 下午2:24:28<br>
   * 
   * @category 老师查看自己给学生的评论
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findTeacherCommentTest() throws Exception {
    String teacherId = "";
    String subscribeCourseId = "";
    System.out.println(teacherCourseCommentService.findTeacherComment(teacherId,
        subscribeCourseId));
  }
  
  /**
   * Title: 保存评论信息<br>
   * Description: 保存评论信息<br>
   * CreateDate: 2017年6月7日 下午2:38:33<br>
   * @category 保存评论信息 
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void saveCommentTest() throws Exception {
    CourseCommentParam courseComment = new CourseCommentParam();
    courseComment.setSubscribeCourseId("f2228fa71d394b90903c53d361c678ac");
    courseComment.setFromUserId("7fa43cc0e4ae43408247d6df825a84df");
    courseComment.setToUserId("166f92ec49974597ab652d003753af54");
    courseComment.setGrammerScore("5");
    courseComment.setIsDemo(true);
    courseComment.setStudentIsShow(1);
    System.out.println(teacherCourseCommentService.saveComment(courseComment));
  }
  
  
  

}
