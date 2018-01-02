package com.webi.hwj.courseprice.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.courseprice.dao.CoursePriceDao;
import com.webi.hwj.courseprice.entity.CoursePrice;
import com.webi.hwj.courseprice.param.CoursePriceParam;

/**
 * @category coursePrice控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class CoursePriceService {
  private static Logger logger = Logger.getLogger(CoursePriceService.class);
  @Resource
  CoursePriceDao coursePriceDao;

  /**
   * Title: 根据课程类型和价格版本号查找课程类型类型和课程单价<br>
   * Description: 根据课程类型和价格版本号查找课程类型类型和课程单价<br>
   * CreateDate: 2016年8月30日 下午3:12:16<br>
   * 
   * @category 根据课程类型和价格版本号查找课程类型类型和课程单价
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<CoursePriceParam> findCourseUnitAndPriceListByVersion(CoursePriceParam paramObj)
      throws Exception {
    List<CoursePriceParam> coursePriceList = coursePriceDao
        .findCourseUnitAndPriceListByVersion(paramObj);

    for (CoursePriceParam coursePrice : coursePriceList) {
      // 课程类型类型显示
      String name = "节";
      switch (coursePrice.getCoursePriceUnitType()) {
      case 0:
        name = "节";
        break;
      case 1:
        name = "月";
        break;
      case 2:
        name = "天";
        break;
      default:
        break;
      }
      coursePrice.setCoursePriceUnitTypeName(name);
    }
    return coursePriceList;
  }
}