package com.webi.hwj.gensee;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.gensee.constant.GenseeConstant;
import com.webi.hwj.gensee.entity.Gensee;
import com.webi.hwj.gensee.service.GenseeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class GenseeServiceTest {

  @Resource
  GenseeService genseeService;

  @Test
  public void createRoomTest() throws Exception {
    String startDateStr = "2016-07-11 16:00:00";
    String endDateStr = "2016-07-11 18:30:00";
    String teacherTimeId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx5";
    String courseType = "course_type8";
    String teacherName = "teacherName";
    String teacherDesc = "teacherDesc";
    String courseDesc = "courseDesc";

    Gensee gensee = genseeService.insertGensee(teacherTimeId,courseType,
        GenseeConstant.GENSEE_SCENE_BIG,
        "我爱你柯南",
        DateUtil.strToDateYYYYMMDDHHMMSS(startDateStr),
        DateUtil.strToDateYYYYMMDDHHMMSS(endDateStr),teacherDesc,teacherName,courseDesc);

    System.out.println(gensee + "---");
  }

  // @Test
  public void gotoGenseeClassTest() throws Exception {
    String teacherTimeId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx3";

    String t = genseeService.goToGenseeClass(teacherTimeId,
        GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_TEACHER, "teacher-alex", null);
    String s = genseeService.goToGenseeClass(teacherTimeId,
        GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_STUDENT, "student-komi", 19555);
    String a = genseeService.goToGenseeClass(teacherTimeId,
        GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_ASSISTANT, "ass-greg", null);

    System.out.println("t===" + t);
    System.out.println("s===" + s);
    System.out.println("a===" + a);
  }

  // @Test
  public void watchGenseeVideoTest() throws Exception {
    String teacherTimeId = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1";
    String userName = "gggg";
    System.out.println(genseeService.watchGenseeVideo(teacherTimeId, userName));
  }
}
