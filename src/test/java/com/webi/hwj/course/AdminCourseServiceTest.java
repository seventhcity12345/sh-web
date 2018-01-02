package com.webi.hwj.course;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.course.service.AdminCourseService;
import com.webi.hwj.courseone2many.param.SchedulingChangeTeacherParam;

/**
 * @category AdminCourseService测试类
 * @author seven
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class AdminCourseServiceTest {
  @Resource
  AdminCourseService adminCourseService;

  @Test
  public void demo() {

  }
  
  /**
   * Title: 排课后更换老师<br>
   * Description: 排课后更换老师<br>
   * CreateDate: 2016年10月27日 上午11:41:10<br>
   * @category 排课后更换老师 
   * @author komi.zsy
   * @throws Exception
   */
  //@Test
  public void changeTeacherTest() throws Exception {
    SchedulingChangeTeacherParam schedulingChangeTeacherParam = new SchedulingChangeTeacherParam();
    schedulingChangeTeacherParam.setTeacherId("");
    schedulingChangeTeacherParam.setEndTime(new Date());
    schedulingChangeTeacherParam.setStartTime(new Date());
    schedulingChangeTeacherParam.setCourseId("");
    schedulingChangeTeacherParam.setTeacherTimeId("");
    //更换老师
    CommonJsonObject json = adminCourseService.changeSchedulingTeacher(schedulingChangeTeacherParam);
    
    System.out.println(json);
    Assert.assertEquals(200, json.getCode());
  }


  /**
   * 
   * Title: 测试查询noshow课程方法<br>
   * Description: 测试查询noshow课程方法<br>
   * CreateDate: 2016年7月6日 上午9:56:28<br>
   * 
   * @category 测试查询noshow课程方法
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findNoShowCoursePageTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("page", "1");
    paramMap.put("findAllFlag", false);
    paramMap.put("rows", 100);
    String learningCoachId = "3c7f96dd419e490ca1e36ca2f99716xx";
    System.out.println(adminCourseService.findNoShowCoursePage(paramMap, learningCoachId));

    paramMap.put("findAllFlag", true);
    System.out.println(adminCourseService.findNoShowCoursePage(paramMap, learningCoachId));
  }
}