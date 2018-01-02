package com.webi.hwj.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.user.service.AdminKeyFollowupService;

/**
 * Title: 重点学员跟踪查询类<br>
 * Description: 重点学员跟踪查询类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月6日 上午10:01:22
 * 
 * @author seven.gz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class AdminKeyFollowupServiceTest {
  @Resource
  AdminKeyFollowupService adminKeyFollowupService;

  @Test
  public void demo() {

  }

  /**
   * Title: 新生跟踪测试<br>
   * Description: 新生跟踪测试<br>
   * CreateDate: 2016年7月6日 上午10:01:50<br>
   * 
   * @category 新生跟踪测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findKeyFollowupStudentTest() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = adminKeyFollowupService.findKeyFollowupStudentPage(param,
        "3c7f96dd419e490ca1e36ca2f99716xx", "newStudent");
    System.out.println(p);
  }

  /**
   * 
   * Title: 本月follow次数少于两个查询测试<br>
   * Description: 本月follow次数少于两个查询测试<br>
   * CreateDate: 2016年7月6日 上午10:01:50<br>
   * 
   * @category 本月follow次数少于两个查询测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findFollowupStudentTest2() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = adminKeyFollowupService.findKeyFollowupStudentPage(param,
        "3c7f96dd419e490ca1e36ca2f99716xx", "lessFollowup");
    System.out.println(p);
  }

  /**
   * 
   * Title: 合同两个 月内到期测试<br>
   * Description: 合同两个 月内到期测试<br>
   * CreateDate: 2016年7月6日 上午10:01:50<br>
   * 
   * @category 合同两个 月内到期测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findFollowupStudentTest3() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = adminKeyFollowupService.findKeyFollowupStudentPage(param,
        "3c7f96dd419e490ca1e36ca2f99716xx", "twoMonthEnd");
    System.out.println(p);
  }

  /**
   * 
   * Title: 合同超过一个月但是没有做过课件测试<br>
   * Description: 合同超过一个月但是没有做过课件测试<br>
   * CreateDate: 2016年7月6日 上午10:01:50<br>
   * 
   * @category 合同超过一个月但是没有做过课件测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findFollowupStudentTest4() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = adminKeyFollowupService.findKeyFollowupStudentPage(param, null, "neverDoneRsa");
    System.out.println(p);
  }

  /**
   * 
   * Title: 曾经上过课超过一个月没有做过课件测试<br>
   * Description: 曾经上过课超过一个月没有做过课件测试<br>
   * CreateDate: 2016年7月6日 上午10:01:50<br>
   * 
   * @category 曾经上过课超过一个月没有做过课件测试
   * @author seven.gz
   * @throws Exception
   */
  // @Test
  public void findFollowupStudentTest5() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = adminKeyFollowupService.findKeyFollowupStudentPage(param, null, "oneMonthNotDoRsa");
    System.out.println(p);
  }

  // @Test
  public void findFollowupStudentTest6() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = adminKeyFollowupService.findKeyFollowupStudentPage(param, null, "notSubscribeClass");
    System.out.println(p);
  }

}
