package com.webi.hwj.admin;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.admin.service.AdminBdminUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class AdminBdminUserServiceTest {
  @Resource
  AdminBdminUserService adminBdminUserService;

  @Test
  public void demo() {

  }

  /**
   * Title: 测试老师评价平均分查询<br>
   * Description: 测试老师评价平均分查询<br>
   * CreateDate: 2016年7月21日 下午7:14:06<br>
   * 
   * @category 测试老师评价平均分查询
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findToTeacherAverageScroreTest() throws Exception {

    System.out.println(adminBdminUserService.findLcRota());

  }

}
