/** 
 * File: CourseCommentServiceTest.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.course<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年9月1日 上午10:43:12
 * @author athrun.cw
 */
package com.webi.hwj.courseone2many;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.course.dao.CourseOne2ManySchedulingDao;
import com.webi.hwj.courseone2many.param.CourseOne2ManySchedulingParam;
import com.webi.hwj.courseone2many.param.CourseSchedulingApiParam;
import com.webi.hwj.courseone2many.param.CourseSchedulingParam;
import com.webi.hwj.courseone2many.service.CourseOneToManySchedulingService;
import com.webi.hwj.esapp.param.AppJsonObject;
import com.webi.hwj.esapp.param.CourseListParam;
import com.webi.hwj.esapp.param.FindCourseListParam;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class CourseOneToManySchedulingServiceTest {

  @Resource
  private CourseOneToManySchedulingService courseOneToManySchedulingService;

  @Resource
  private OrderCourseService orderCourseService;

  @Resource
  CourseOne2ManySchedulingDao courseOne2ManySchedulingDao;

  @Test
  public void demo() {

  }

  /**
   * Title: 根据课程类型获取最近一节要上的课程<br>
   * Description: 根据课程类型获取最近一节要上的课程<br>
   * CreateDate: 2017年8月24日 下午5:11:46<br>
   * 
   * @category 根据课程类型获取最近一节要上的课程
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void findCourseHeadByCourseTypeTest() throws Exception {
    AppJsonObject<CourseListParam> json = new AppJsonObject<CourseListParam>();
    String userId = "";

    String courseType = "course_type8";

    // 根据课程类型查找课程列表信息
    CourseListParam courseListParam =
        courseOneToManySchedulingService.findCourseHeadByCourseTypeAndUserId(courseType,
            userId);

    json.setData(courseListParam);

    System.out.println(json);

  }
  
  

  /**
   * Title: 根据课程类型查找课程列表信息<br>
   * Description: 根据课程类型查找课程列表信息<br>
   * CreateDate: 2017年8月24日 下午4:43:25<br>
   * 
   * @category 根据课程类型查找课程列表信息
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void findCourseListByCourseTypeTest() throws Exception {
    AppJsonObject<List<CourseListParam>> json = new AppJsonObject<List<CourseListParam>>();
      String courseType = "course_type8";
      Integer page = 1;
      Integer rows = 5;
      String userId = "ab1373d4eb50406ab1e59cfda2fe5d9f";

      // 根据课程类型查找课程列表信息
      List<CourseListParam> returnList =
          courseOneToManySchedulingService.findCourseListByCourseTypeAndUserId(courseType,
              userId, page, rows);

      json.setData(returnList);
      System.out.println(returnList.size());
      System.out.println(json);
  }

  /**
   * Title: 查询oc课课程信息和第一份合同是否超过30天测试<br>
   * Description: 查询oc课课程信息和第一份合同是否超过30天测试<br>
   * CreateDate: 2016年4月26日 下午3:02:40<br>
   * 
   * @category 查询oc课课程信息和第一份合同是否超过30天测试
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  // @Test
  public void findCourseOcInfoTest() throws Exception {
    JsonMessage json = new JsonMessage(true, "查询成功");
    Map<String, Object> paramMap = new HashMap<String, Object>();

    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("72dfe41853e64fef95dcf742de71cfd1");
    sessionUser.setUserName("十九");
    sessionUser.setUserCode("221");

    paramMap.put("isTop",
        orderCourseService.findUserFirstOrderIsInNDay(30, "fd25a88292014076b2ee2f477b403348"));
    paramMap.put("ocInfo",
        courseOneToManySchedulingService.findCourseOcInfo(sessionUser, new Date()));

    json.setData(paramMap);

    System.out.println(json);

    // Assert.assertTrue(json.isSuccess());
  }

  /**
   * Title: 查询EnglishStido课课程信息结束日期大于当前日期<br>
   * Description: 查询EnglishStido课课程信息结束日期大于当前日期<br>
   * CreateDate: 2016年7月28日 上午11:33:03<br>
   * 
   * @category 查询EnglishStido课课程信息结束日期大于当前日期
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void findCourseEnglishStidoInfoTest() throws Exception {
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("72dfe41853e64fef95dcf742de71cfd1");
    sessionUser.setUserName("十九");
    sessionUser.setUserCode("221");

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("ESInfo",
        courseOneToManySchedulingService.findCourseEnglishStidoInfo(sessionUser));

    JsonMessage json = new JsonMessage(true, "查询成功");
    json.setData(paramMap);
    System.out.println(json);

    // Assert.assertTrue(json.isSuccess());
  }

  // @Test
  public void findGenseeRoomIdListByCourseIdTest() throws Exception {
    courseOne2ManySchedulingDao.findGenseeRoomIdListByCourseId("11111111");

  }

  /**
   * Title: 根据keyid查询相关课程信息<br>
   * Description: 根据keyid查询相关课程信息<br>
   * CreateDate: 2017年4月10日 下午4:49:10<br>
   * 
   * @category 根据keyid查询相关课程信息
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findCourseInfoByKeyIdTest() throws Exception {
    String keyId = "013fc90cc8fd4db8b82f8d48fcc3a27d";
    CourseOne2ManySchedulingParam courseOne2ManySchedulingParam = courseOneToManySchedulingService
        .findCourseInfoByKeyId(keyId);
    System.out.println(courseOne2ManySchedulingParam);
  }

  /**
   * Title: 查找还未开课的ES课程列表<br>
   * Description: 查找还未开课的ES课程列表<br>
   * CreateDate: 2017年4月10日 下午5:00:29<br>
   * 
   * @category 查找还未开课的ES课程列表
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findEnglishStidoListTest() throws Exception {
    List<CourseOne2ManySchedulingParam> courseOne2ManySchedulingParam =
        courseOneToManySchedulingService.findEnglishStidoList();
    System.out.println(courseOne2ManySchedulingParam);
  }

  /**
   * 
   * Title: 测试查询课程列表<br>
   * Description: 测试查询课程列表<br>
   * CreateDate: 2016年10月17日 下午5:11:31<br>
   * 
   * @category 测试查询课程列表
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findCourseInfoListTest() throws Exception {
    System.out.println(courseOneToManySchedulingService.findCourseInfoList(
        DateUtil.strToDateYYYYMMDD("2016-10-19"),
        "course_type2", "General Level 1"));
  }

  /**
   * 
   * Title: findEnglishStidoOnFunBigClassInfo<br>
   * Description: findEnglishStidoOnFunBigClassInfo<br>
   * CreateDate: 2017年7月5日 下午8:51:55<br>
   * 
   * @category findEnglishStidoOnFunBigClassInfo
   * @author felix.yl
   * @throws Exception
   */
  @Test
  public void findEnglishStidoOnFunBigClassInfo() throws Exception {
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("1dcac11cf80e4bb0a92bccb6c5be9c85");
    sessionUser.setUserName("赛文");
    sessionUser.setUserCode("1214151");

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("ESInfo",
        courseOneToManySchedulingService.findEnglishStidoOnFunBigClassInfo(sessionUser));

    JsonMessage json = new JsonMessage(true, "查询成功");
    json.setData(paramMap);
    System.out.println(json);
  }

  /**
   * 
   * Title: findCourseAndTeacherInfoListTest<br>
   * Description: findCourseAndTeacherInfoListTest<br>
   * CreateDate: 2017年7月20日 下午5:36:42<br>
   * 
   * @category findCourseAndTeacherInfoListTest
   * @author felix.yl
   * @throws Exception
   */
  @Test
  public void findCourseAndTeacherInfoListTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CourseSchedulingApiParam> json =
        new CommonJsonObject<CourseSchedulingApiParam>();

    // 构建参数
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("52b4dc83fc554a79857f5d5ff5ade251");

    // 调用Service
    List<CourseSchedulingParam> courseAndTeacherInfoList = courseOneToManySchedulingService
        .findCourseAndTeacherInfoList(sessionUser, "course_type8",
            new Date());// 课程结束时间大于等于当前时间

    CourseSchedulingApiParam courseSchedulingApiParam =
        new CourseSchedulingApiParam();
    courseSchedulingApiParam.setCourseInfoList(courseAndTeacherInfoList);

    json.setData(courseSchedulingApiParam);

    Assert.assertEquals(200, json.getCode());

  }

}
