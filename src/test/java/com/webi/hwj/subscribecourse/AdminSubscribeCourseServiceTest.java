package com.webi.hwj.subscribecourse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.subscribecourse.param.SubscribeCourseForCreateMailParam;
import com.webi.hwj.subscribecourse.service.AdminSubscribeCourseService;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;

/**
 * @category tellmemorePercent控制类.
 * @author mingyisoft代码生成工具
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class AdminSubscribeCourseServiceTest {
  @Resource
  AdminSubscribeCourseService adminSubscribeCourseService;
  @Resource
  SubscribeCourseService subscribeCourseService;

  @Test
  public void demo() {

  }

  /**
   * Title: 统计老师课时数测试<br>
   * Description: 统计老师课时数测试<br>
   * CreateDate: 2016年7月13日 下午9:22:23<br>
   * 
   * @category 统计老师课时数测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void statisticsMonthSubscribeCountTest() throws Exception {
    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS("2016-05-28 00:00:00");
    Date endTime = DateUtil.strToDateYYYYMMDDHHMMSS("2016-07-28 00:00:00");
    System.out.println(
        adminSubscribeCourseService.statisticsMonthSubscribeCount(startTime, endTime, null));
  }

  /**
   * Title: 老师课表测试<br>
   * Description: 老师课表测试<br>
   * CreateDate: 2016年7月13日 下午9:22:23<br>
   * 
   * @category 老师课表测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void satisticsTeacherSubscribeCourseTest() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", null);
    param.put("rows", null);

    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS("2016-05-28 00:00:00");
    Date endTime = DateUtil.strToDateYYYYMMDDHHMMSS("2016-07-28 00:00:00");

    System.out.println(adminSubscribeCourseService.satisticsTeacherSubscribeCourse(param, startTime,
        endTime, null));
  }

  /**
   * Title: 查询预约课表信息测试<br>
   * Description: 查询预约课表信息测试<br>
   * CreateDate: 2016年8月1日 上午11:46:01<br>
   * 
   * @category 查询预约课表信息测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findSubscribeCourseInfoPage() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");

    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS("2016-05-28 00:00:00");
    Date endTime = DateUtil.strToDateYYYYMMDDHHMMSS("2016-07-28 00:00:00");

    System.out.println(
        adminSubscribeCourseService.findSubscribeCourseInfoPage(param, startTime, endTime));
  }

  /**
   * Title: 预约1vn课程前置条件<br>
   * Description: 预约1vn课程前置条件<br>
   * CreateDate: 2016年8月16日 下午5:10:07<br>
   * 
   * @category 预约1vn课程前置条件
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void checkSubscribeOne2ManyCoursePremiseTest() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", "72dfe41853e64fef95dcf742de71cfd1");
    paramMap.put("course_id", "028a49ba20ab462b93a0dbcd8ec1282a");

    JsonMessage json = subscribeCourseService.checkSubscribeOne2ManyCoursePremise(paramMap);
    System.out.println(json);
    // Assert.assertTrue(json.isSuccess());
  }

  /**
   * 
   * Title: 测试切换学员出席状态<br>
   * Description: 测试切换学员出席状态<br>
   * CreateDate: 2016年9月20日 下午2:43:51<br>
   * 
   * @category 测试切换学员出席状态
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void changeStudentShowStatusTest() throws Exception {
    String subscribeId = "0199fc74402b4b98a867e7cc5ead0d9b";
    JsonMessage json = adminSubscribeCourseService.changeStudentShowStatus(subscribeId, false,
        "sevenTest");
    System.out.println(json);
    // Assert.assertTrue(json.isSuccess());
  }

  /**
   * 
   * Title: 测试异常预约信息统计<br>
   * Description: 测试异常预约信息统计<br>
   * CreateDate: 2016年9月22日 上午11:37:52<br>
   * 
   * @category 测试异常预约信息统计
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void statisticsAbnormalSubscribeInfoTest() throws Exception {
    SubscribeCourseForCreateMailParam res = adminSubscribeCourseService
        .statisticsAbnormalSubscribeInfo(DateUtil.strToDateYYYYMMDDHHMMSS("2017-03-21 00:00:00"),
            DateUtil.strToDateYYYYMMDDHHMMSS("2017-03-22 00:00:00"));
    System.out.println(res);
  }

  /**
   * Title: 测试异常预约数据发送邮件<br>
   * Description: 测试异常预约数据发送邮件<br>
   * CreateDate: 2016年9月22日 下午3:15:18<br>
   * 
   * @category 测试异常预约数据发送邮件
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void sendAbnormalSubscribeTest() throws Exception {
    adminSubscribeCourseService
        .sendAbnormalSubscribe();
  }

  /**
   * 
   * Title: 生成团训学员信息的csv文件内容测试<br>
   * Description: createDownloadTuanxunInfoTest<br>
   * CreateDate: 2016年12月12日 下午5:03:23<br>
   * 
   * @category 生成团训学员信息的csv文件内容测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void createDownloadTuanxunInfoTest() throws Exception {
    System.out.println(adminSubscribeCourseService
        .createDownloadTuanxunInfo("2016-01-01 00:00:00", "2017-01-31 00:00:00"));
  }

  /**
   * Title: 测试demo课跟踪<br>
   * Description: findDemoSubscribeCourseInfoPage<br>
   * CreateDate: 2017年4月30日 下午5:12:13<br>
   * 
   * @category 测试demo课跟踪
   * @author seven.gz
   */
  @Test
  public void findDemoSubscribeCourseInfoPage() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");

    Date startTime = DateUtil.strToDateYYYYMMDDHHMMSS("2014-05-28 00:00:00");
    Date endTime = DateUtil.strToDateYYYYMMDDHHMMSS("2017-07-28 00:00:00");

    System.out.println(
        adminSubscribeCourseService.findDemoSubscribeCourseInfoPage(param, startTime, endTime,
            "course_type4"));
  }
}