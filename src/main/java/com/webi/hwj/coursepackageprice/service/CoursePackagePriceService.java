package com.webi.hwj.coursepackageprice.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.coursepackage.dao.CoursePackageEntityDao;
import com.webi.hwj.coursepackage.dao.CoursePackageOptionEntityDao;
import com.webi.hwj.coursepackage.entity.CoursePackage;
import com.webi.hwj.coursepackage.entity.CoursePackageOption;
import com.webi.hwj.coursepackageprice.dao.CoursePackagePriceDao;
import com.webi.hwj.coursepackageprice.entity.CoursePackagePrice;
import com.webi.hwj.coursepackagepriceoption.dao.CoursePackagePriceOptionDao;
import com.webi.hwj.coursepackagepriceoption.entity.CoursePackagePriceOption;
import com.webi.hwj.courseprice.dao.CoursePriceDao;
import com.webi.hwj.courseprice.entity.CoursePrice;
import com.webi.hwj.util.TxtUtil;

/**
 * @category coursePackagePrice控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class CoursePackagePriceService {
  private static Logger logger = Logger.getLogger(CoursePackagePriceService.class);
  @Resource
  CoursePackagePriceDao coursePackagePriceDao;
  
  @Resource
  CoursePackagePriceOptionDao coursePackagePriceOptionDao;
  @Resource
  CoursePriceDao coursePriceDao;
  @Resource
  CoursePackageEntityDao coursePackageEntityDao;
  @Resource
  CoursePackageOptionEntityDao coursePackageOptionEntityDao;
  
  // FIXME 这里临时写一下
  public static java.util.Date strToDateYYYYMMDD(String strd,String format) {
    try {
        SimpleDateFormat dateSdf = new SimpleDateFormat(format);
        return dateSdf.parse(strd);
    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }
}

  /**
   * Title: 增加价格策略<br>
   * Description: 增加价格策略<br>
   * CreateDate: 2017年8月16日 下午4:26:57<br>
   * @category 增加价格策略 
   * @author komi.zsy
   * @throws Exception
   */
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void addCoursePackagePrice(String loadAddress)
      throws Exception {

    String updateUserId = "";
    
    List<List<String>> list = TxtUtil.loadTxtFile(loadAddress);
    System.out.println(list);

    // 小维度价格策略，key是courseType+setCoursePriceUnitType，因为会有很多重复的课程不能插入多遍
    Map<String, CoursePrice> coursePriceMap = new HashMap<String, CoursePrice>();
    // 先查出当前价格策略小维度价格策略版本号到几了，然后+1存一下
    int coursePriceVersion = coursePriceDao.findMaxNum() + 1;
    // 价格策略表id
    String coursePackagePriceId = null;
    for (int i = 0; i < list.size(); i++) {
      List<String> rowList = list.get(i);
      System.out.println("222:" + rowList);
      if (rowList.size() != 0) {
       

        if ("价格策略名字".equals(rowList.get(0))) {
          i++;
          rowList = list.get(i);
          String coursePackagePriceName = rowList.get(0);
          System.out.println("333:" + coursePackagePriceName);
          // 价格策略表
          CoursePackagePrice coursePackagePriceparam = new CoursePackagePrice();
          coursePackagePriceparam.setPackagePriceName(coursePackagePriceName);
          coursePackagePriceparam.setPackagePriceStartTime(CoursePackagePriceService.strToDateYYYYMMDD(rowList.get(
              1), "yyyy/MM/dd"));
          coursePackagePriceparam.setPackagePriceEndTime(CoursePackagePriceService.strToDateYYYYMMDD(rowList.get(2),
              "yyyy/MM/dd"));
//          coursePackagePriceparam.setCourseTypeGroup(rowList.get(3));
          coursePackagePriceparam.setUpdateUserId(updateUserId);
          // 根据名字查找是否已有价格策略，如果已有则更新价格策略信息，没有则新增一条
          CoursePackagePrice returnObj = coursePackagePriceDao.findCoursePackagePriceByName(
              coursePackagePriceName);
          if (returnObj != null) {
            coursePackagePriceparam.setKeyId(returnObj.getKeyId());
            coursePackagePriceDao.update(coursePackagePriceparam);
          } else {
            coursePackagePriceDao.insert(coursePackagePriceparam);
          }
          coursePackagePriceId = coursePackagePriceparam.getKeyId();
        } else if ("课程包".equals(rowList.get(0))) {
          i++;
          rowList = list.get(i);
          String coursePackageName = rowList.get(0);
          System.out.println("444:" + coursePackageName);
          // 课程包表
          CoursePackage coursePackageParam = new CoursePackage();
          coursePackageParam.setPackageName(coursePackageName);
          coursePackageParam.setCategoryType(rowList.get(1));
          coursePackageParam.setLimitShowTime(Integer.parseInt(rowList.get(2)));
          coursePackageParam.setLimitShowTimeUnit(Integer.parseInt(rowList.get(3)));
          coursePackageParam.setCoursePackageType(Integer.parseInt(rowList.get(4)));
          coursePackageParam.setCreateUserId(updateUserId);
          coursePackageParam.setUpdateUserId(updateUserId);
          coursePackageEntityDao.insert(coursePackageParam);
          i++;
          rowList = list.get(i);
          if ("课程类型".equals(rowList.get(0))) {
            while (true) {
              i++;
              rowList = list.get(i);
              if ("课程包原价".equals(rowList.get(0))) {
                i++;
                rowList = list.get(i);
                // 要先删除原来的子表数据，全部重新插入新的,tom说先不删除，直接新增
//                coursePackagePriceOptionDao.deleteByCoursePackagePriceId(coursePackagePriceId);
                // 价格策略子表
                CoursePackagePriceOption coursePackagePriceOptionParam =
                    new CoursePackagePriceOption();
                coursePackagePriceOptionParam.setCoursePackagePriceId(coursePackagePriceId);
                coursePackagePriceOptionParam.setCoursePackageId(coursePackageParam.getKeyId());
                coursePackagePriceOptionParam.setCoursePackageShowPrice(Integer.parseInt(rowList
                    .get(0)));
                coursePackagePriceOptionParam.setCoursePackageRealPrice(Integer.parseInt(rowList
                    .get(1)));
                coursePackagePriceOptionParam.setCoursePriceVersion(coursePriceVersion);
                coursePackagePriceOptionDao.insert(coursePackagePriceOptionParam);
                break;
              }
              String courseType = rowList.get(0);
              System.out.println("555:" + courseType);
              // 课程包子表
              CoursePackageOption coursePackageOptionParam = new CoursePackageOption();
              coursePackageOptionParam.setCoursePackageId(coursePackageParam.getKeyId());
              coursePackageOptionParam.setCourseType(courseType);
              coursePackageOptionParam.setCourseCount(Integer.parseInt(rowList.get(1)));
              coursePackageOptionParam.setCourseUnitType(Integer.parseInt(rowList.get(2)));
              coursePackageOptionParam.setCreateUserId(updateUserId);
              coursePackageOptionParam.setUpdateUserId(updateUserId);
              coursePackageOptionEntityDao.insert(coursePackageOptionParam);

              // 小维度价格策略
              if (coursePriceMap.get(courseType + "," + Integer.parseInt(rowList.get(2))) == null) {
                CoursePrice coursePriceParam = new CoursePrice();
                coursePriceParam.setCoursePriceVersion(coursePriceVersion);
                coursePriceParam.setCourseType(courseType);
                coursePriceParam.setCoursePriceUnitPrice(Integer.parseInt(rowList.get(3)));
                coursePriceParam.setCoursePriceUnitType(Integer.parseInt(rowList.get(2)));
                coursePriceParam.setCreateUserId(updateUserId);
                coursePriceParam.setUpdateUserId(updateUserId);
                coursePriceMap.put(courseType + "," + Integer.parseInt(rowList.get(2)),
                    coursePriceParam);
              }

            }

          }
        }
      }
    }

    List<CoursePrice> coursePriceList = new ArrayList<CoursePrice>(coursePriceMap.values());
    coursePriceDao.batchInsert(coursePriceList);

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
  public List<CoursePackagePrice> findListByTime(Date currentTime,String packagePriceOnlineType) throws Exception {
    return coursePackagePriceDao.findListByTime(currentTime,packagePriceOnlineType);
  }
}