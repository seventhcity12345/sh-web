package com.webi.hwj.subscribecourse;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.esapp.param.AppJsonObject;
import com.webi.hwj.esapp.param.CourseListParam;
import com.webi.hwj.ordercourse.service.AdminOrderCourseOptionService;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.SubscribeCourseListParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseApiParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseDetailApiParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseDetailParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseParam;
import com.webi.hwj.subscribecourse.service.BaseSubscribeCourseCheckService;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;

/**
 * 
 * Title: 测试预约的逻辑（1v1）<br>
 * Description: SubscribeCourseServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月30日 下午6:31:22
 * 
 * @author athrun.cw
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class SubscribeCourseServiceTest {

  @Resource
  SubscribeCourseService subscribeCourseService;
  @Resource
  AdminOrderCourseOptionService adminOrderCourseOptionService;

  @Resource
  BaseSubscribeCourseCheckService baseSubscribeCourseCheckService;

  @Test
  public void demo() {

  }

  /**
   * Title: app学生进入教室<br>
   * Description: app学生进入教室<br>
   * CreateDate: 2017年8月25日 下午6:31:58<br>
   * 
   * @category app学生进入教室
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void goToClassByStudentTest()
      throws Exception {

    String userId = "f5ac0e704e1e4b4792ca986b41ea393f";
    String userName = "123";
    String userCode = "123";
    String userPhoto = "ddd";

    String subscribeId = "f4af22573cc140b8b360c25dc22bd4c8";

    // 上课
    AppJsonObject<CourseListParam> json = subscribeCourseService.goToClassAppStudent(subscribeId,
        userId, userName, userCode, userPhoto);

    System.out.println(json);

  }

  /**
   * Title: 根据预约id获取备注信息<br>
   * Description: 根据预约id获取备注信息<br>
   * CreateDate: 2017年6月7日 下午7:50:19<br>
   * 
   * @category 根据预约id获取备注信息
   * @author komi.zsy
   */
  @Test
  public void getSubscribeCourseRemarkTest() throws Exception {
    String subscribeId = "0199fc74402b4b98a867e7cc5ead0d9b";
    System.out.println(subscribeCourseService.getSubscribeCourseRemark(subscribeId));
  }

  /**
   * Title: 老师进入教室<br>
   * Description: 老师进入教室<br>
   * CreateDate: 2017年6月7日 下午2:23:02<br>
   * 
   * @category 老师进入教室
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void goToClassTeacherTest() throws Exception {
    String teacherTimeId = "";
    String teacherId = "";
    String teacherName = "";
    boolean isFront = false;
    System.out.println(subscribeCourseService.goToClassTeacher(teacherTimeId, teacherId,
        teacherName, isFront));
  }

  /**
   * Title: 查看录像<br>
   * Description: 视频回顾<br>
   * CreateDate: 2016年10月14日 下午5:10:32<br>
   * 
   * @category 查看录像
   * @author komi.zsy
   * @param request
   * @param paramMap
   * @return
   * @throws Exception
   */
  // @Test
  public void reviewVideoUrlTest() throws Exception {
    String teacherTimeId = "fa43053823a34cfb8e2cbc520ae1cc42";
    String userName = "test";
    String userId = "";
    CommonJsonObject json = subscribeCourseService.watchCourseVideo(userId, teacherTimeId,
        userName);

    System.out.println(json);
  }

  /**
   * Title: 查找即将上的课程<br>
   * Description: 查找即将上的课程<br>
   * CreateDate: 2016年9月20日 下午3:55:20<br>
   * 
   * @category 查找即将上的课程
   * @author komi.zsy
   * @param request
   * @throws Exception
   */
  // @Test
  public void findClassTablesByUserIdTest() throws Exception {
    String userId = "66a01d13be1a4bab8eb137b104d21826";

    List<SubscribeCourseParam> paramMapList = subscribeCourseService
        .findClassTablesByUserId(userId);

    System.out.println(paramMapList);
  }

  /**
   * Title: 查询课程预约表里的上过课的数据<br>
   * Description: 查询课程预约表里的上过课的数据<br>
   * CreateDate: 2016年9月21日 下午2:09:44<br>
   * 
   * @category 查询课程预约表里的上过课的数据
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void findCompleteSubscribeCourseByUserIdTest() throws Exception {
    String userId = "0af6e18487544543b144c8758827b026";

    List<SubscribeCourseParam> paramMapList = subscribeCourseService
        .findCompleteSubscribeCourseByUserId(userId, 1, 3);

    System.out.println(paramMapList);
  }

  /**
   * Title: 待评价课程列表<br>
   * Description: 待评价课程类别1v1,预约前置判定<br>
   * CreateDate: 2016年12月12日 下午4:11:55<br>
   * 
   * @category 待评价课程列表
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void findNotCommentListTest() throws Exception {
    String userId = "0af6e18487544543b144c8758827b026";
    // 查找用户已上课、未评价的1v1类型的课程列表
    List<SubscribeCourseListParam> subscribeCourseList = baseSubscribeCourseCheckService
        .findNotCommentList(userId);
    System.out.println(subscribeCourseList);
  }

  /**
   * Title: 按日期查询所有预约信息<br>
   * Description: 按日期查询所有预约信息<br>
   * CreateDate: 2016年12月20日 下午4:24:07<br>
   * 
   * @category 按日期查询所有预约信息
   * @author komi.zsy
   * @param startTime
   * @return
   * @throws Exception
   */
  // @Test
  public void findSubscribeCourseByDateTest(Date startTime) throws Exception {
    List<SubscribeCourse> list = subscribeCourseService.findSubscribeCourseByDate(startTime);
  }

  /**
   * Title: 根据时间查询所有课程类型的未评价预约信息<br>
   * Description: 根据时间查询所有课程类型的未评价预约信息<br>
   * CreateDate: 2016年12月20日 下午6:33:54<br>
   * 
   * @category 根据时间查询所有课程类型的未评价预约信息
   * @author komi.zsy
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  @Test
  public void findAllSubscribeCourseByNotCommentTest() throws Exception {
    List<SubscribeCourse> list = subscribeCourseService
        .findAllSubscribeCourseByNotComment(
            DateUtil.strToDateYYYYMMDDHHMMSS("2016-12-26 17:45:00"));
    System.out.println(list);
  }

  /**
   * 
   * Title: 查询本月有预约(过)课程的日期以及当天预约的课程数<br>
   * Description: 查询本月有预约(过)课程的日期以及当天预约的课程数<br>
   * CreateDate: 2017年7月19日 下午4:33:16<br>
   * 
   * @category 查询本月有预约(过)课程的日期以及当天预约的课程数
   * @author felix.yl
   * @throws Exception
   */
  @Test
  public void findSubscribeCourseDateAndNumberTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<SubscribleCourseApiParam> json =
        new CommonJsonObject<SubscribleCourseApiParam>();

    // 构建参数
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("604d544e65464965b0d1a2efd4188b94");
    String yearMonth = "201708";
    // String yearMonth = "20170205";//错误测试

    // 调用Service
    List<SubscribleCourseParam> subscribeCourseList = subscribeCourseService
        .findSubscribeCourseDateAndNumber(sessionUser, yearMonth);

    SubscribleCourseApiParam subscribleCourseApiParam =
        new SubscribleCourseApiParam();
    subscribleCourseApiParam.setSubscribleCourseParamList(subscribeCourseList);

    json.setData(subscribleCourseApiParam);

    Assert.assertEquals(200, json.getCode());

  }

  /**
   * 
   * Title: 查询展示学员在某天已预约的所有课程(包括大课、小课)<br>
   * Description: 查询展示学员在某天已预约的所有课程(包括大课、小课)<br>
   * CreateDate: 2017年7月19日 下午4:33:16<br>
   * 
   * @category 查询展示学员在某天已预约的所有课程(包括大课、小课)
   * @author felix.yl
   * @throws Exception
   */
  @Test
  public void findSubscribeCourseDetailTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<SubscribleCourseDetailApiParam> json =
        new CommonJsonObject<SubscribleCourseDetailApiParam>();

    // 构建参数
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("604d544e65464965b0d1a2efd4188b94");
    String yearMonth = "2017-08-02";
    // String yearMonth = "20170503";//错误测试

    // 调用Service
    List<SubscribleCourseDetailParam> subscribeCourseDetail = subscribeCourseService
        .findSubscribeCourseDetail(sessionUser, yearMonth);

    SubscribleCourseDetailApiParam subscribleCourseDetailApiParam =
        new SubscribleCourseDetailApiParam();
    subscribleCourseDetailApiParam.setSubscribleCourseDetailList(subscribeCourseDetail);

    json.setData(subscribleCourseDetailApiParam);

    Assert.assertEquals(200, json.getCode());
  }

}
