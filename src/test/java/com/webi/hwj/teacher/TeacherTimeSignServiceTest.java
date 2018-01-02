package com.webi.hwj.teacher;

import java.util.ArrayList;
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
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.teacher.entity.TeacherTimeSign;
import com.webi.hwj.teacher.service.TeacherTimeSignService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class TeacherTimeSignServiceTest {
  @Resource
  TeacherTimeSignService teacherTimeSignService;

  @Test
  public void demo() {

  }

  /**
   * Title: 查询教师签课信息测试<br>
   * Description: 查询教师签课信息测试<br>
   * CreateDate: 2016年4月28日 下午1:41:34<br>
   * 
   * @category 查询教师签课信息测试
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findPageByStartTimeTest() throws Exception {
    TeacherTimeSign teacherTimeSign = new TeacherTimeSign();
    teacherTimeSign.setTeacherId("07f171db21c247d49f82284b2dca1a59");
    teacherTimeSign
        .setStartTime(DateUtil.strToDateYYYYMMDDHHMMSS(DateUtil.dateToStrYYMMDD(new Date())));

    Page p = teacherTimeSignService.findPageByStartTime(teacherTimeSign, 1, 20);

    System.out.println(p);
  }

  /**
   * Title: 添加签课时间段测试(成功)<br>
   * Description: 添加签课时间段测试<br>
   * CreateDate: 2016年4月28日 下午1:46:49<br>
   * 
   * @category 添加签课时间段测试
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void insertTeacherTimeSignByAddTimeSuccessTest() throws Exception {
    // TeacherTimeSign teacherTimeSign = new TeacherTimeSign();
    // teacherTimeSign.setStartTime(DateUtil.strToDateYYYYMMDDHHMMSS("2017-06-1
    // 14:13:26"));
    // teacherTimeSign.setEndTime(DateUtil.strToDateYYYYMMDDHHMMSS("2017-06-1
    // 16:13:26"));
    // teacherTimeSign.setTeacherId("07f171db21c247d49f82284b2dca1a59");
    // teacherTimeSign.setTeacherName("8888");
    // teacherTimeSign.setTeacherCourseType("course_type8,course_type2");

    SessionTeacher sessionTeacher = new SessionTeacher();
    sessionTeacher.setKeyId("07f171db21c247d49f82284b2dca1a59");
    sessionTeacher.setTeacherName("8888");
    sessionTeacher.setTeacherCourseType("course_type8,course_type2");

    List<Map<String, Object>> teacherTimeSignList = new ArrayList<Map<String, Object>>();

    Map<String, Object> teacherTimeSign = new HashMap<String, Object>();
    teacherTimeSign.put("startTime", "2017-06-1 14:13:26");
    teacherTimeSign.put("endTime", "2017-06-1 16:13:26");

    teacherTimeSignList.add(teacherTimeSign);

    teacherTimeSignService.insertTeacherTimeSignByAddTime(teacherTimeSignList, sessionTeacher);
  }

  /**
   * Title: 添加签课时间段测试(失败)<br>
   * Description: 添加签课时间段测试<br>
   * CreateDate: 2016年4月28日 下午1:46:49<br>
   * 
   * @category 添加签课时间段测试
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void insertTeacherTimeSignByAddTimeFailTest() throws Exception {
    // TeacherTimeSign teacherTimeSign = new TeacherTimeSign();
    // teacherTimeSign.setStartTime(DateUtil.strToDateYYYYMMDDHHMMSS("2016-06-1
    // 14:13:26"));
    // teacherTimeSign.setEndTime(DateUtil.strToDateYYYYMMDDHHMMSS("2016-06-1
    // 16:13:26"));
    // teacherTimeSign.setTeacherId("07f171db21c247d49f82284b2dca1a59");
    // teacherTimeSign.setTeacherName("8888");
    // teacherTimeSign.setTeacherCourseType("course_type8,course_type2");

    SessionTeacher sessionTeacher = new SessionTeacher();
    sessionTeacher.setKeyId("07f171db21c247d49f82284b2dca1a59");
    sessionTeacher.setTeacherName("8888");
    sessionTeacher.setTeacherCourseType("course_type8,course_type2");

    List<Map<String, Object>> teacherTimeSignList = new ArrayList<Map<String, Object>>();

    Map<String, Object> teacherTimeSign = new HashMap<String, Object>();
    teacherTimeSign.put("startTime", "2016-06-1 14:13:26");
    teacherTimeSign.put("endTime", "2016-06-1 16:13:26");

    teacherTimeSignList.add(teacherTimeSign);

    try {
      teacherTimeSignService.insertTeacherTimeSignByAddTime(teacherTimeSignList, sessionTeacher);
    } catch (Exception e) {
      // 以后再看 抛出自定义错误 的时候才是对的，其他异常是错误的
    }
  }

  /**
   * Title: 删除签课时间段测试(成功)<br>
   * Description: 删除签课时间段测试<br>
   * CreateDate: 2016年4月28日 下午1:49:29<br>
   * 
   * @category 删除签课时间段测试
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void deleteTeacherTimeSignByAddTimeSuccessTest() throws Exception {
    // TeacherTimeSign teacherTimeSign = new TeacherTimeSign();
    // teacherTimeSign.setStartTime(DateUtil.strToDateYYYYMMDDHHMMSS("2016-06-1
    // 14:13:26"));
    // teacherTimeSign.setEndTime(DateUtil.strToDateYYYYMMDDHHMMSS("2016-06-1
    // 16:13:26"));
    // teacherTimeSign.setTeacherId("07f171db21c247d49f82284b2dca1a59");

    List<Map<String, Object>> teacherTimeSignList = new ArrayList<Map<String, Object>>();

    Map<String, Object> teacherTimeSign = new HashMap<String, Object>();
    teacherTimeSign.put("startTime", "2016-06-1 14:13:26");
    teacherTimeSign.put("endTime", "2016-06-1 16:13:26");

    teacherTimeSignList.add(teacherTimeSign);

    teacherTimeSignService.deleteTeacherTimeSignByNewTime(teacherTimeSignList,
        "07f171db21c247d49f82284b2dca1a59");
  }

  /**
   * Title: 删除签课时间段测试(失败)（还需要已经约了1v1课，排了大课等情况需要增加失败情况的测试用例）<br>
   * Description: 删除签课时间段测试<br>
   * CreateDate: 2016年4月28日 下午1:49:29<br>
   * 
   * @category 删除签课时间段测试
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void deleteTeacherTimeSignByAddTimeFailTest() throws Exception {
    // TeacherTimeSign teacherTimeSign = new TeacherTimeSign();
    // teacherTimeSign.setStartTime(DateUtil.strToDateYYYYMMDDHHMMSS("2016-05-21
    // 06:13:26"));
    // teacherTimeSign.setEndTime(DateUtil.strToDateYYYYMMDDHHMMSS("2016-05-21
    // 13:20:26"));
    // teacherTimeSign.setTeacherId("07f171db21c247d49f82284b2dca1a59");

    List<Map<String, Object>> teacherTimeSignList = new ArrayList<Map<String, Object>>();

    Map<String, Object> teacherTimeSign = new HashMap<String, Object>();
    teacherTimeSign.put("startTime", "2016-05-21 06:13:26");
    teacherTimeSign.put("endTime", "2016-05-21 13:20:26");

    teacherTimeSignList.add(teacherTimeSign);

    try {
      teacherTimeSignService.deleteTeacherTimeSignByNewTime(teacherTimeSignList,
          "07f171db21c247d49f82284b2dca1a59");
    } catch (Exception e) {
      // 以后再看 抛出自定义错误 的时候才是对的，其他异常是错误的
    }

  }

}
