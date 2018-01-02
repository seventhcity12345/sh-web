package com.webi.hwj.coursepackageprice.dao;

import org.springframework.stereotype.Repository;
import com.mingyisoft.javabase.base.dao.BaseEntityDao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import com.webi.hwj.coursepackageprice.entity.CoursePackagePrice;

@Repository
public class CoursePackagePriceDao extends BaseEntityDao<CoursePackagePrice> {
  private static Logger logger = Logger.getLogger(CoursePackagePriceDao.class);
  
  /**
   * 根据价格策略名字查找是否有已存在的价格策略
   * @author komi.zsy
   */
  private final static String FIND_COURSE_PACKAGE_PRICE_BY_NAME = "SELECT key_id"
      + " FROM t_course_package_price"
      + " WHERE package_price_name = :packagePriceName AND is_used = 1";

  /**
   * 查找当前时间生效的价格政策
   * 
   * @author komi.zsy
   */
  public final static String FIND_LIST_BY_TIME = "SELECT key_id,package_price_name"
      + " FROM t_course_package_price "
      + " WHERE package_price_start_time <= :packagePriceStartTime "
      + " AND package_price_end_time >= :packagePriceEndTime "
      + " AND package_price_online_type LIKE :packagePriceOnlineType"
      + " AND is_used <> 0";

  /**
   * 根据价格策略子表id查询主表id和过期时间
   * 
   * @author komi.zsy
   */
  public final static String FIND_END_TIME_BY_OPTIONID = "SELECT tcpp.key_id,tcpp.package_price_end_time"
      + " FROM `t_course_package_price` tcpp"
      + " LEFT JOIN `t_course_package_price_option` tcppo"
      + " ON tcpp.key_id = tcppo.course_package_price_id"
      + " WHERE tcppo.key_id = :keyId"
      + " AND tcppo.is_used <> 0 AND tcpp.is_used <> 0 ";
  
  /**
   * Title: 根据价格策略名字查找是否有已存在的价格策略<br>
   * Description: 根据价格策略名字查找是否有已存在的价格策略<br>
   * CreateDate: 2017年8月14日 下午6:07:00<br>
   * @category 根据价格策略名字查找是否有已存在的价格策略 
   * @author komi.zsy
   * @param packagePriceName 价格策略名字
   * @return
   * @throws Exception
   */
  public CoursePackagePrice findCoursePackagePriceByName(String coursePackagePriceName)
      throws Exception {
    CoursePackagePrice paramObj = new CoursePackagePrice();
    paramObj.setPackagePriceName(coursePackagePriceName);
    return super.findOne(FIND_COURSE_PACKAGE_PRICE_BY_NAME, paramObj);
  }

  /**
   * Title: 查找当前时间生效的价格政策<br>
   * Description: 查找当前时间生效的价格政策<br>
   * CreateDate: 2016年8月29日 下午4:27:48<br>
   * 
   * @category 查找当前时间生效的价格政策
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<CoursePackagePrice> findListByTime(Date currentTime,String packagePriceOnlineType) throws Exception {
    CoursePackagePrice paramObj = new CoursePackagePrice();
    paramObj.setPackagePriceStartTime(currentTime);
    paramObj.setPackagePriceEndTime(currentTime);
    paramObj.setPackagePriceOnlineType(packagePriceOnlineType);
    return super.findList(FIND_LIST_BY_TIME, paramObj);
  }

  /**
   * Title: 根据价格策略子表id查询主表id和过期时间<br>
   * Description: 根据价格策略子表id查询主表id和过期时间<br>
   * CreateDate: 2016年8月31日 上午11:59:04<br>
   * 
   * @category 根据价格策略子表id查询主表id和过期时间
   * @author komi.zsy
   * @param course_package_price_option_id
   * @return
   * @throws Exception
   */
  public CoursePackagePrice findEndTimeByOptionId(String course_package_price_option_id)
      throws Exception {
    CoursePackagePrice paramObj = new CoursePackagePrice();
    paramObj.setKeyId(course_package_price_option_id);
    return super.findOne(FIND_END_TIME_BY_OPTIONID, paramObj);
  }
}