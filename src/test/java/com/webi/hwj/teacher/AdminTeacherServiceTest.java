package com.webi.hwj.teacher;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.HttpClientUtil;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.service.AdminTeacherService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
@Component
public class AdminTeacherServiceTest {
  @Resource
  AdminTeacherService adminTeacherService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试查询记录每天消课率<br>
   * Description: 以LC划分 查询，每个LC手下的学员 a.每天消耗1V1课程数 b.出勤课程课程数<br>
   * CreateDate: 2016年3月24日 下午2:08:19<br>
   * 
   * @category 测试查询记录每天消课率
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void recordConsumeClassRateTest() throws Exception {
    // String today_date = DateUtil.dateToStrYYMMDD(new Date());
    adminTeacherService.findPageEasyuiTeachers(null, null, 1, 20);
  }

  /**
   * Title: 查询教师信息测试<br>
   * Description: 查询教师信息测试<br>
   * CreateDate: 2016年7月26日 上午10:59:01<br>
   * 
   * @category 查询教师信息测试
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findPageEasyuiTeachersTest() throws Exception {
    String sort = "create_date";
    String order = "desc";
    Integer page = 1;
    Integer rows = 2;

    Page p = adminTeacherService.findPageEasyuiTeachers(sort, order, page, rows);
    System.out.println(p);
  }

  /**
   * Title: 编辑教师信息<br>
   * Description: 编辑教师信息<br>
   * CreateDate: 2016年7月26日 下午2:19:21<br>
   * 
   * @category 编辑教师信息
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  // public void editTeacherTest() throws Exception {
  // Teacher teacher = new Teacher();
  // teacher.setKeyId("050f50b882b84a718aa044956f931fea");
  // teacher.setTeacherName("测试专用老师");
  // teacher.setTeacherGender(0);
  // teacher.setThirdFrom("开发测试");
  // teacher.setTeacherNationality("中国");
  // teacher.setTeacherContactContent("测试电话：666666");
  // teacher.setTeacherJobType(0);
  // teacher.setTeacherCourseType("course_type1,course_type2");
  // teacher.setTeacherDesc("teacherDesc");
  //
  // byte[] a = HttpClientUtil.doGetReturnByte(
  // "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images/course/englishstudio/0301-0312/Culture%20Shock.jpg");
  //
  // MultipartFile coursePicFile = new MockMultipartFile("alex1", "a.jpg", null,
  // a);
  //
  // JsonMessage json = adminTeacherService.editTeacher(teacher, coursePicFile);
  // System.out.println(json);
  // Assert.assertTrue(json.getMsg(), json.isSuccess());
  // }

  /**
   * Title: 新增教师 <br>
   * Description: 新增教师<br>
   * CreateDate: 2016年7月27日 下午2:53:12<br>
   * 
   * @category 新增教师
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public String addTeacherTest() throws Exception {
    Teacher teacher = new Teacher();
    teacher.setAccount("aaaaa1");
    teacher.setTeacherName("测试");
    teacher.setTeacherGender(0);
    teacher.setThirdFrom("开发测试");
    teacher.setTeacherNationality("中国");
    teacher.setTeacherContactContent("测试电话：666666");
    teacher.setTeacherJobType(0);
    teacher
        .setTeacherCourseType("course_type1,course_type2,course_type9,course_type5,course_type8");
    teacher.setTeacherDesc("teacherDesc");

    byte[] a = HttpClientUtil.doGetReturnByte(
        "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images/course/englishstudio/0301-0312/Culture%20Shock.jpg");

    MultipartFile coursePicFile = new MockMultipartFile("alex1", "a.jpg", null, a);

    JsonMessage json = adminTeacherService.insert(teacher, coursePicFile);

    System.out.println(json);

    Assert.assertTrue(json.getMsg(), json.isSuccess());

    return teacher.getKeyId();
  }

  /**
   * Title: 删除教师<br>
   * Description: 删除教师<br>
   * CreateDate: 2016年7月27日 下午3:05:47<br>
   * 
   * @category 删除教师
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void deleteTeacherTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();

    paramMap.put("keys", "050f50b882b84a718aa044956f931fea");

    int num = adminTeacherService.batchDeleteTeacher(paramMap);

    System.out.println(num);

    Assert.assertSame("数据错误", 1, num);
  }
}
