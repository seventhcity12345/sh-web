package com.webi.hwj.course;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.coursepackageprice.constant.CoursePackagePriceConstant;
import com.webi.hwj.coursepackageprice.entity.CoursePackagePrice;
import com.webi.hwj.coursepackageprice.service.CoursePackagePriceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class CoursePackagePriceTest {
  @Resource
  CoursePackagePriceService coursePackagePriceService;

  @Test
  public void demo() {

  }
  
  /**
   * Title: 查找当前时间生效的价格政策<br>
   * Description: 查找当前时间生效的价格政策<br>
   * CreateDate: 2016年8月29日 下午4:31:17<br>
   * 
   * @category 查找当前时间生效的价格政策
   * @author komi.zsy
   * @param currentTime
   * @return
   * @throws Exception
   */
  @Test
  public void findListByTimeTest() throws Exception {
    System.out.println(coursePackagePriceService.findListByTime(new Date(), "%" + CoursePackagePriceConstant.PACKAGE_PRICE_ONLINE_TYPE + "%"));
  }

  
  /**
   * Title: 增加价格策略<br>
   * Description: 增加价格策略<br>
   * CreateDate: 2017年8月16日 下午4:26:57<br>
   * @category 增加价格策略 
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void addCoursePackagePriceTest() throws Exception {
    String loadAddress = "E:\\CoursePackagePriceData.txt";
    
    coursePackagePriceService.addCoursePackagePrice(loadAddress);
  }

}