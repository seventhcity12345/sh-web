package com.webi.hwj.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.teacher.service.TeacherService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class TeacherServiceTest {
  @Resource
  TeacherService teacherService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试老师端的course center数据查询.<br>
   * Description: <br>
   * CreateDate: 2016年7月23日 上午10:27:35<br>
   * 
   * @category 测试老师端的comments center数据查询
   * @author alex
   */
  // @Test
  public void findSubscribeListByTeacherIdTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("teacher_id", "166f92ec49974597ab652d003753af54");
    paramMap.put("dt", "2016-12-26");
    List<Map<String, Object>> result = teacherService.findSubscribeListByTeacherId(paramMap);
    System.out.println(result);

  }
  
 
}
