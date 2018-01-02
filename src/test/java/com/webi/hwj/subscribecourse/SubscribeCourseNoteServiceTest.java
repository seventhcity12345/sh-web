package com.webi.hwj.subscribecourse;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.webi.hwj.subscribecourse.entity.SubscribeCourseNote;
import com.webi.hwj.subscribecourse.service.SubscribeCourseNoteService;

/**
 * 
 * Title: 测试预约记录<br>
 * Description: 测试预约记录<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月30日 下午6:31:22
 * 
 * @author athrun.cw
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class SubscribeCourseNoteServiceTest {

  @Resource
  SubscribeCourseNoteService subscribeCourseNoteService;

  /**
   * Title: 添加修改记录测试<br>
   * Description: 添加修改记录测试<br>
   * CreateDate: 2016年9月21日 下午1:40:24<br>
   * 
   * @category 添加修改记录测试
   * @author seven.gz
   * @throws Exception
   *           异常
   */
  @Test
  public void addOrModifyNoteTest() throws Exception {
    SubscribeCourseNote subscribeCourseNote = new SubscribeCourseNote();
    subscribeCourseNote.setKeyId("ceshiceshi");
    subscribeCourseNote.setSubscribeNote("ceshiceshi测试");
    subscribeCourseNote.setSubscribeNoteTaker("ceshiceshi");
    subscribeCourseNote.setUpdateUserId("ceshiceshi");
    JsonMessage json = subscribeCourseNoteService.addOrModifyNote(subscribeCourseNote);
    System.out.println(json);
  }
}
