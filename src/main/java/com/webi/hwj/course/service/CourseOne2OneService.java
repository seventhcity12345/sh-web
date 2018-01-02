package com.webi.hwj.course.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.course.dao.CourseOne2OneDao;
import com.webi.hwj.courseone2one.dao.AdminCourseOne2oneDao;
import com.webi.hwj.courseone2one.param.CourseOne2OneParam;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CourseOne2OneService {
  @Resource
  private CourseOne2OneDao courseOne2OneDao;
  @Resource
  AdminCourseOne2oneDao adminCourseOne2oneDao;
  @Resource
  CourseService courseService;

  /**
   * Title: 查询course_type1列表<br>
   * Description: 查询course_type1列表 <br>
   * CreateDate: 2016年9月6日14:56:46<br>
   * 
   * @category 查询course_type1列表
   * @author komi.zsy
   * @param sessionUser
   * @return
   * @throws Exception
   */
  public List<CourseOne2OneParam> findCourseType1List(SessionUser sessionUser) throws Exception {
    // 查询学员当前等级的课程列表
    List<CourseOne2OneParam> one2OneCourseList = adminCourseOne2oneDao
        .findCourseType1List(sessionUser.getCurrentLevel());

    // 根据rsa进度和预约情况，组装1v1类型课程数据
    courseService.findCourseOne2OneList(sessionUser, one2OneCourseList);

    return one2OneCourseList;
  }

  /**
   * 原来使用MemcachedUtil缓存数据，不再使用、
   * 
   * 获取一对一头部 如果还存有 从缓存读 Title: queryTopData<br>
   * Description: queryTopData<br>
   * CreateDate: 2015年9月25日 下午4:53:17<br>
   * 
   * @category 获取一对一头部
   * @author Woody
   * @return
   * @throws Exception
   */
  /*
   * public List<Map<String, Object>> queryTopData() throws Exception { long
   * startLong = System.currentTimeMillis(); long endLong = startLong + 14 * 24
   * * 60 * 60 * 1000; // 14天数据 String start = DateUtil.formatDate(startLong,
   * "yyyy-MM-dd"); String end = DateUtil.formatDate(endLong, "yyyy-MM-dd");
   * 
   * @SuppressWarnings("unchecked") List<Map<String, Object>> resultListMap =
   * (List<Map<String, Object>>) MemcachedUtil.getValue(TOP_DATA_CACHE_KEY_1V1 +
   * start);
   * 
   * if (resultListMap == null) { resultListMap = new
   * ArrayList<Map<String,Object>>(); Calendar cal = Calendar.getInstance(); //
   * 时间处理
   * 
   * logger.info(">!-------------查询一对一顶部信息------------!<");
   * 
   * Map<String, Object> paramMap = new HashMap<String, Object>();
   * paramMap.put("start", start); paramMap.put("end", end);
   * 
   * List<Map<String, Object>> dataListMap =
   * courseOne2OneDao.findList(CourseOne2OneDao.SQL_HAS_DATA_TODAY, paramMap);
   * 
   * if (dataListMap != null && dataListMap.size() > 0) { Date date = null; long
   * todayLong = DateUtil.strToDateYYYYMMDD(start).getTime(); Map<String,
   * Object> dataMap = null; long day = 0L; int index = 0;
   * 
   * for (int i = 0; i < 14; i++) { // 放入14天的模板 day = todayLong + (i * 24 * 60 *
   * 60 * 1000); date = DateUtils.getDate(day);
   * 
   * dataMap = new HashMap<String, Object>(2); cal.setTime(date);
   * dataMap.put("weekStr", weekDays[cal.get(Calendar.DAY_OF_WEEK)]); // 放入周几
   * dataMap.put("MMdd", DateUtil.formatDate(date.getTime(), "MM/dd")); // 放入月/日
   * 
   * resultListMap.add(dataMap); } // 通过索引 获取相印位置的data 然后塞入有数据的标记 for
   * (Map<String, Object> map : dataListMap) { date =
   * DateUtil.strToDateYYYYMMDD(String.valueOf(map.get("today"))); // 获取有数据的日期
   * 
   * index = (int) ((date.getTime() - todayLong) / (24 * 60 * 60 * 1000)); //
   * 计算index 0 1 2 3 4 5 6 7 8 9 10 11 12 13
   * 
   * dataMap = resultListMap.get(index); dataMap.put("today", map.get("today"));
   * // 塞入日期 用于对每天数据的检索 dataMap.put("flag", true); // 塞入有数据的标记 } }
   * MemcachedUtil.setValue(TOP_DATA_CACHE_KEY_1V1 + start, resultListMap, 24 *
   * 60 * 60); } return resultListMap; }
   */
}