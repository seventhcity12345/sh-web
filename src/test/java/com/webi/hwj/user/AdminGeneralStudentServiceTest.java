package com.webi.hwj.user;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.user.param.GeneralStudentInfoParam;
import com.webi.hwj.user.service.AdminGeneralStudentService;
import com.webi.hwj.user.util.AdminUserUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class AdminGeneralStudentServiceTest {
  @Resource
  AdminGeneralStudentService adminGeneralStudentService;

  @Test
  public void demo() {

  }

  // @Test
  public void calculationStandardCourseCountTest() {
    System.out.println(
        AdminUserUtil.calculationStandardCourseCount(DateUtil.strToDateYYYYMMDD("2016-05-05"),
            DateUtil.strToDateYYYYMMDD("2016-05-07"), 55,
            DateUtil.strToDateYYYYMMDD("2016-05-06")));
  }

  // @Test
  public void generalStudentInfoTest() throws Exception {
    GeneralStudentInfoParam page = adminGeneralStudentService
        .findGeneralStudentDetailInfo("52b4dc83fc554a79857f5d5ff5ade251");
    System.out.println(page);
  }
}
