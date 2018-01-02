package com.webi.hwj.courseone2many.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.course.constant.CourseConstant;
import com.webi.hwj.courseone2many.dao.CourseOneToManySchedulingDao;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.courseone2many.param.CourseOne2ManySchedulingParam;
import com.webi.hwj.courseone2many.param.CourseSchedulingParam;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.esapp.param.CourseListParam;
import com.webi.hwj.gensee.constant.GenseeConstant;
import com.webi.hwj.gensee.service.GenseeService;
import com.webi.hwj.subscribecourse.constant.SubscribeCourseConstant;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.SubscribeCourseParam;
import com.webi.hwj.util.CalendarUtil;

/**
 * Title: 查找排课信息<br>
 * Description: 查找排课信息<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月25日 下午6:18:15
 * 
 * @author komi.zsy
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class CourseOneToManySchedulingService {
  private static Logger logger = Logger.getLogger(CourseOneToManySchedulingService.class);
  @Resource
  CourseOneToManySchedulingDao courseOneToManySchedulingDao;
  @Resource
  private SubscribeCourseDao subscribeCourseDao;

  @Resource
  private GenseeService genseeService;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;

  /**
   * Title: 查询当月oc课程信息<br>
   * Description: 查询当月oc课程信息<br>
   * CreateDate: 2016年4月26日 下午1:48:48<br>
   * 
   * @category 查询当月oc课程信息
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findCourseOcInfo(SessionUser sessionUser,
      Date paramTime)
      throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    // 这里课程类型是oc课
    paramObj.setCourseType("course_type5");

    // 求出本月月初和本月月末（就是下月月初）
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String startTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1)
        + "-01";
    calendar.add(Calendar.MONTH, 1);
    String endTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-01";

    // 如果有参数就用传的参数时间查询
    if (paramTime != null) {
      paramObj.setStartTime(paramTime);
    } else {
      paramObj.setStartTime(format.parse(startTime));
    }
    paramObj.setEndTime(format.parse(endTime));

    // 查找OC课程信息
    List<CourseOne2ManySchedulingParam> returnList = courseOneToManySchedulingDao
        .findSchedulingByCourseTypeAndDate(paramObj);

    // 设置为当前时间（查询预约信息）
    paramObj.setEndTime(new Date());
    // 获取课程相关预约信息
    findCourseInfoByCourseType(sessionUser, paramObj, returnList);

    return returnList;
  }

  /**
   * 
   * Title: 查询EnglishStido课课程信息结束日期大于当前日期<br>
   * Description: 查询EnglishStido课课程信息结束日期大于当前日期<br>
   * CreateDate: 2016年5月16日 上午11:46:54<br>
   * 
   * @category 查询EnglishStido课课程信息结束日期大于当前日期
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findCourseEnglishStidoInfo(SessionUser sessionUser)
      throws Exception {
    // 查找结束日期大于等于当前日期的ES课程信息
    List<CourseOne2ManySchedulingParam> returnList = courseOneToManySchedulingDao
        .findSchedulingAndTeacherInfo("course_type8", new Date());

    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    // 这里课程类型是EnglishStdio课
    paramObj.setCourseType("course_type8");
    // 设置为当前时间
    paramObj.setEndTime(new Date());

    // 获取课程相关预约信息
    findCourseInfoByCourseType(sessionUser, paramObj, returnList);

    return returnList;
  }

  /**
   * Title: 查找还未开课的ES课程列表<br>
   * Description: 查找还未开课的ES课程列表<br>
   * CreateDate: 2017年4月10日 下午3:27:57<br>
   * 
   * @category 查找还未开课的ES课程列表
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findEnglishStidoList() throws Exception {
    // 查找结束日期大于等于当前日期的ES课程信息
    List<CourseOne2ManySchedulingParam> returnList = courseOneToManySchedulingDao
        .findSchedulingAndTeacherInfo("course_type8", new Date());

    return returnList;
  }

  /**
   * Title: 根据课程类型查找课程列表信息<br>
   * Description: 根据课程类型查找课程列表信息<br>
   * CreateDate: 2017年8月24日 下午4:30:16<br>
   * 
   * @category 根据课程类型查找课程列表信息
   * @author komi.zsy
   * @param courseType
   *          课程类型
   * @param userId
   *          用户id
   * @return
   * @throws Exception
   */
  public List<CourseListParam> findCourseListByCourseTypeAndUserId(String courseType,
      String userId, Integer page, Integer rows) throws Exception {
    // 查找结束日期大于等于当前日期的课程信息
    List<CourseOne2ManySchedulingParam> courseOne2ManySchedulingParamList =
        courseOneToManySchedulingDao
            .findCourseListByCourseTypeAndUserId(courseType, userId, new Date(), page, rows)
            .getDatas();

    // 转换成app要用的数据结构
    List<CourseListParam> returnList = new ArrayList<CourseListParam>();
    if (courseOne2ManySchedulingParamList != null && courseOne2ManySchedulingParamList
        .size() != 0) {
      int courseTypeBeforeGoclassTime = ((CourseType)MemcachedUtil.getValue(courseType)).getCourseTypeBeforeGoclassTime();
      for (CourseOne2ManySchedulingParam courseOne2ManySchedulingParam : courseOne2ManySchedulingParamList) {
        CourseListParam courseListParam = new CourseListParam();
        courseListParam.setId(courseOne2ManySchedulingParam.getKeyId());
        courseListParam.setVideo_id(courseOne2ManySchedulingParam.getCourseId());
        courseListParam.setLive_id(courseOne2ManySchedulingParam.getSubscribeId());
        //如果预约id为空，则没有预约
        if(StringUtils.isEmpty(courseOne2ManySchedulingParam.getSubscribeId())){
          courseListParam.setSubscribeStatus(false);
        }
        else{
          courseListParam.setSubscribeStatus(true);
        }
        courseListParam.setCourseTypeBeforeGoclassTime(courseTypeBeforeGoclassTime);
        courseListParam.setName(courseOne2ManySchedulingParam.getCourseTitle());
        courseListParam.setTeacher_name(courseOne2ManySchedulingParam.getTeacherName());
        courseListParam.setBanner(courseOne2ManySchedulingParam.getCoursePic());
        courseListParam.setStart_time(courseOne2ManySchedulingParam.getStartTime());
        courseListParam.setEnd_time(courseOne2ManySchedulingParam.getEndTime());
        courseListParam.setTeacher_img(courseOne2ManySchedulingParam.getTeacherPhoto());
        returnList.add(courseListParam);
      }
    }

    return returnList;
  }

  /**
   * Title: 根据课程类型查找最近一节课程信息<br>
   * Description: 根据课程类型查找最近一节课程信息<br>
   * CreateDate: 2017年8月24日 下午4:30:16<br>
   * 
   * @category 根据课程类型查找最近一节课程信息
   * @author komi.zsy
   * @param courseType
   *          课程类型
   * @param userId
   *          用户id
   * @return
   * @throws Exception
   */
  public CourseListParam findCourseHeadByCourseTypeAndUserId(String courseType,
      String userId) throws Exception {
    // 查找结束日期大于等于当前日期的最近一节课程信息
    CourseOne2ManySchedulingParam courseOne2ManySchedulingParam = courseOneToManySchedulingDao
        .findCourseHeadByCourseTypeAndUserId(courseType, userId, new Date());

    // 转换成app要用的数据结构
    if(courseOne2ManySchedulingParam != null){
      CourseListParam courseListParam = new CourseListParam();
      courseListParam.setId(courseOne2ManySchedulingParam.getKeyId());
      courseListParam.setVideo_id(courseOne2ManySchedulingParam.getCourseId());
      courseListParam.setLive_id(courseOne2ManySchedulingParam.getSubscribeId());
      //如果预约id为空，则没有预约
      if(StringUtils.isEmpty(courseOne2ManySchedulingParam.getSubscribeId())){
        courseListParam.setSubscribeStatus(false);
      }
      else{
        courseListParam.setSubscribeStatus(true);
      }
      courseListParam.setCourseTypeBeforeGoclassTime(((CourseType)MemcachedUtil.getValue(courseType)).getCourseTypeBeforeGoclassTime());
      courseListParam.setName(courseOne2ManySchedulingParam.getCourseTitle());
      courseListParam.setTeacher_name(courseOne2ManySchedulingParam.getTeacherName());
      courseListParam.setBanner(courseOne2ManySchedulingParam.getCoursePic());
      courseListParam.setStart_time(courseOne2ManySchedulingParam.getStartTime());
      courseListParam.setEnd_time(courseOne2ManySchedulingParam.getEndTime());
      courseListParam.setTeacher_img(courseOne2ManySchedulingParam.getTeacherPhoto());
      courseListParam.setServerTime(new Date());
      return courseListParam;
    }

    return null;
  }

  /**
   * Title: 根据keyid查询相关课程信息<br>
   * Description: 根据keyid查询相关课程信息<br>
   * CreateDate: 2017年4月10日 下午4:12:33<br>
   * 
   * @category 根据keyid查询相关课程信息
   * @author komi.zsy
   * @param keyId
   *          大课排课表id
   * @return
   * @throws Exception
   */
  public CourseOne2ManySchedulingParam findCourseInfoByKeyId(String keyId) throws Exception {
    return courseOneToManySchedulingDao.findCourseInfoByKeyId(keyId);
  }

  /**
   * Title: 获取课程相关预约信息<br>
   * Description: 获取课程相关预约信息，以及学生是否预约、上课链接等<br>
   * CreateDate: 2016年9月21日 下午5:13:19<br>
   * 
   * @category 获取课程相关预约信息
   * @author komi.zsy
   * @param sessionUser
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findCourseInfoByCourseType(
      SessionUser sessionUser, CourseOne2ManySchedulingParam paramObj,
      List<CourseOne2ManySchedulingParam> returnList) throws Exception {
    /**
     * modified by komi 2016年7月6日09:56:16 查询结束日期大于当前日期的课程预约信息
     */
    List<Map<String, Object>> subscribeMapList = subscribeCourseDao
        .findSubscribeListByUserIdAndCourseTypeAndDate(sessionUser.getKeyId(),
            paramObj.getEndTime(), paramObj.getCourseType());

    // 行列转换后的是否预约参数list
    Map<String, Object> subscribeMapParamList = new HashMap<String, Object>();

    if (subscribeMapList != null && subscribeMapList.size() != 0) {
      for (Map<String, Object> subscribeMap : subscribeMapList) {
        subscribeMapParamList.put((String) subscribeMap.get("course_id"), subscribeMap);
      }
    }

    if (returnList != null && returnList.size() != 0) {
      // 遍历课程列表，把预约信息置入，0(false)是没预约，1(true)是已预约
      for (CourseOne2ManySchedulingParam returnObj : returnList) {
        // 如果不为空，则代表预约表中有该课程记录，则已预约
        if (subscribeMapParamList.get(returnObj.getKeyId()) != null) {
          // 传入预约id
          returnObj.setSubscribeId(
              (String) ((Map<String, Object>) subscribeMapParamList.get(returnObj.getKeyId()))
                  .get("key_id"));
        }

        // 传入学生教室URL
        returnObj.setStudentUrl(genseeService.goToGenseeClass(returnObj.getTeacherTimeId(),
            GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_STUDENT,
            sessionUser.getUserName(), Integer.valueOf((String) sessionUser.getUserCode())));
      }
    }
    return returnList;
  }

  /**
   * 
   * Title: 获得oc课学员url<br>
   * Description: 获得oc课学员url<br>
   * CreateDate: 2016年8月31日 下午4:33:14<br>
   * 
   * @category 获得oc课学员url
   * @author seven.gz
   * @param courseId
   * @return
   * @throws Exception
   */
  public String ocGoToClass(String courseId) throws Exception {
    String url = null;
    CourseOne2ManyScheduling courseOne2ManyScheduling = courseOneToManySchedulingDao
        .findOneByKeyId(courseId);
    if (courseOne2ManyScheduling != null) {
      url = courseOne2ManyScheduling.getStudentUrl();
    }
    return url;
  }

  /**
   * 
   * Title: 查询课程列表<br>
   * Description: 查询课程列表<br>
   * CreateDate: 2016年10月17日 下午5:09:07<br>
   * 
   * @category 查询课程列表
   * @author seven.gz
   * @param teacherTimeDate
   * @param courseType
   * @param currentLevel
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findCourseInfoList(Date teacherTimeDate,
      String courseType, String currentLevel) throws Exception {
    // 提前预约时间
    int beforeLessionCanSubscribe = ((CourseType) MemcachedUtil.getValue(courseType))
        .getCourseTypeSubscribeTime();

    // 查询的开始时间需要大于提前预约时间
    long startTime = System.currentTimeMillis() + beforeLessionCanSubscribe * 60 * 1000;

    // 查找当天的老师&时间
    List<CourseOne2ManySchedulingParam> timeAndTeacherList = courseOneToManySchedulingDao
        .findCourseInfoList(teacherTimeDate, new Date(startTime), courseType,
            currentLevel + "([^0-9]|$)");

    return timeAndTeacherList;
  }

  /**
   * 
   * Title: 查询头部日期<br>
   * Description: 查询头部日期<br>
   * CreateDate: 2016年10月17日15:13:35<br>
   * 
   * @category 查询头部日期
   * @author seven.gz
   * @param userId
   * @param endOrderTime
   * @return
   * @throws Exception
   */
  public List<Date> findTopDateListForOneToMany(String userId, Date endOrderTime, String courseType,
      String currentLevel)
      throws Exception {
    List<Date> resultList = new ArrayList<Date>(); // 结果集合

    int beforeLessionCanSubscribe = ((CourseType) MemcachedUtil.getValue(courseType))
        .getCourseTypeSubscribeTime();
    // 查询的开始时间需要大于提前预约时间
    long startLong = System.currentTimeMillis() + beforeLessionCanSubscribe * 60 * 1000;
    long endLong = startLong + 14 * 24 * 60 * 60 * 1000; // 14天数据

    // modify by seven 2016年11月8日15:14:30 按照tom的要求点进日期没有课的就不要显示日期了
    Date startTime = new Date(startLong);
    Date endTime = DateUtil.strToDateYYYYMMDD(DateUtil.formatDate(endLong, "yyyy-MM-dd"));

    // 合同在14天之内失效，则显示的就不是14天了，
    // modify by seven 2017年9月12日18:49:27 合同结束哪天也要能查询出来
    // 获取合同下一天的时间
    endOrderTime = CalendarUtil.getNextNDay(endOrderTime, 1);

    if (endOrderTime.getTime() < endLong) {
      endTime = DateUtil.strToDateYYYYMMDD(DateUtil.dateToStrYYMMDD(endOrderTime));
    }

    List<CourseOne2ManySchedulingParam> dataList = courseOneToManySchedulingDao.findTimeDateList(
        startTime,
        endTime, courseType, currentLevel + "([^0-9]|$)");

    if (dataList != null && dataList.size() > 0) {
      for (CourseOne2ManySchedulingParam courseOne2ManySchedulingParam : dataList) {
        // 将日期放入返回结果中
        resultList.add(courseOne2ManySchedulingParam.getStartTime());
      }
    }
    return resultList;
  }

  /**
   * 
   * Title: 根据日期查询当天1v6的课程信息<br>
   * Description: findCourseType2InfoListPc<br>
   * CreateDate: 2016年12月15日 下午3:44:41<br>
   * 
   * @category 根据日期查询当天1v6的课程信息
   * @author seven.gz
   * @param userId
   * @param teacherTimeDate
   * @param courseType
   * @param currentLevel
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findCourseType2InfoListPc(String userId,
      Date teacherTimeDate,
      String courseType, String currentLevel) throws Exception {

    CourseType courseTypeEnity = (CourseType) MemcachedUtil.getValue("course_type2");

    int subscribeTime = courseTypeEnity
        .getCourseTypeSubscribeTime(); // 可预约时间
    int beforeGoclassTime = courseTypeEnity
        .getCourseTypeBeforeGoclassTime(); // 课程开始时间
    int cancelSubscribeTime = courseTypeEnity
        .getCourseTypeCancelSubscribeTime(); // 可取消预约时间

    // 查找当天的老师&时间
    List<CourseOne2ManySchedulingParam> schedulingList = courseOneToManySchedulingDao
        .findCourseInfoListPc(teacherTimeDate, new Date(), courseType, currentLevel + "([^0-9]|$)");

    if (schedulingList != null && schedulingList.size() > 0) {
      // 存放排课id
      List<String> schedulingIds = new ArrayList<String>();

      // 行转列存放列表信息
      Map<String, CourseOne2ManySchedulingParam> schedulingMap =
          new HashMap<String, CourseOne2ManySchedulingParam>();

      for (CourseOne2ManySchedulingParam courseOne2ManySchedulingParam : schedulingList) {
        schedulingMap.put(courseOne2ManySchedulingParam.getKeyId(), courseOne2ManySchedulingParam);
        schedulingIds.add(courseOne2ManySchedulingParam.getKeyId());
      }

      // 查询当天自己预约过的课程
      List<SubscribeCourse> subscribeMapList = subscribeCourseEntityDao
          .findSubscribeListByUserIdAndCourseIdsAndDate(userId, teacherTimeDate, schedulingIds);

      // 设置预约信息
      if (subscribeMapList != null && subscribeMapList.size() > 0) {
        CourseOne2ManySchedulingParam schedulingTemp = null;
        for (SubscribeCourse subscribeCourse : subscribeMapList) {
          schedulingTemp = schedulingMap.get(subscribeCourse.getCourseId());
          if (schedulingTemp != null) {
            schedulingTemp.setSubscribeId(subscribeCourse.getKeyId());
          }
        }
      }

      // 设置课程状态
      Date start = null;
      Date end = null;
      CourseOne2ManySchedulingParam courseOne2ManySchedulingParam = null;
      Iterator<CourseOne2ManySchedulingParam> iter = schedulingList.iterator();
      while (iter.hasNext()) {
        courseOne2ManySchedulingParam = iter.next();

        start = courseOne2ManySchedulingParam.getStartTime();
        end = courseOne2ManySchedulingParam.getEndTime();

        long time = start.getTime() - System.currentTimeMillis();

        int status = CourseConstant.COURSE_STATUS_CAN_NOT_SUBSCRIBE;
        if (null == courseOne2ManySchedulingParam.getSubscribeId()) { // 没被预约
          if (time <= subscribeTime * 60 * 1000) { // 如果在不能预约了 不展示
            iter.remove(); // 删除不用显示的课
            continue;
          } else {
            if (courseOne2ManySchedulingParam.getAlreadyPersonCount() < courseTypeEnity
                .getCourseTypeLimitNumber()) { // 已预约数小于可预约数
              status = CourseConstant.COURSE_STATUS_CAN_SUBSCRIBE; // 可预约
                                                                   // 当前预约数小于最大预约数
                                                                   // 预约时间在可预约范围内
                                                                   // 上课开始时间大于
              // 可预约最大时间
            } else {
              status = CourseConstant.COURSE_STATUS_CAN_NOT_SUBSCRIBE; // 不可预约
                                                                       // 针对预约已满
            }
          }
        } else { // 已被预约
          if (time > cancelSubscribeTime * 60 * 1000) {
            // 大于12个小时 可取消预约
            status = CourseConstant.COURSE_STATUS_CAN_CANCEL_SUBSCRIBE;
          } else if (time <= cancelSubscribeTime * 60 * 1000
              && time > beforeGoclassTime * 60 * 1000) {
            // 倒计时 12小时

            status = CourseConstant.COURSE_STATUS_COUNT_DOWN;
          } else if (time < beforeGoclassTime * 60 * 1000
              && System.currentTimeMillis() < end.getTime()) {
            // 上课开始前15分钟一直到课程结束 都能 进入教室
            status = CourseConstant.COURSE_STATUS_GO_TO_CLASS;
          }
        }
        courseOne2ManySchedulingParam.setStatus(status);
        courseOne2ManySchedulingParam
            .setCourseTypeCancelSubscribeTime(courseTypeEnity.getCourseTypeCancelSubscribeTime());
        courseOne2ManySchedulingParam
            .setCourseTypeBeforeGoclassTime(courseTypeEnity.getCourseTypeBeforeGoclassTime());
        courseOne2ManySchedulingParam.setMaxCount(courseTypeEnity.getCourseTypeLimitNumber());
      }
      return schedulingList;
    } else {
      return null;
    }
  }

  /**
   * 
   * Title: 趣味大讲堂<br>
   * Description: findCourseEnglishStidoInfo<br>
   * CreateDate: 2017年7月5日 下午6:24:14<br>
   * 
   * @category 趣味大讲堂-查询课程以及课程对应的老师信息
   * @author felix.yl
   * @param sessionUser
   * @return
   * @throws Exception
   */
  public List<CourseOne2ManySchedulingParam> findEnglishStidoOnFunBigClassInfo(
      SessionUser sessionUser)
      throws Exception {
    // 查找结束日期大于等于当前日期的ES课程信息
    List<CourseOne2ManySchedulingParam> returnList = courseOneToManySchedulingDao
        .findEnglishStidoOnFunBigClassAndTeacherInfo("course_type8", new Date());

    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    paramObj.setCourseType("course_type8");// 课程类型是EnglishStdio课
    paramObj.setEndTime(new Date());// 当前时间

    /**
     * 获取课程相关预约信息【此处直接调用重用Komi写的方法 modify by felix】 如果未登录就不需要调用该方法了
     */
    if (sessionUser != null) {
      findCourseInfoByCourseType(sessionUser, paramObj, returnList);
    }
    return returnList;
  }

  /**
   * 
   * Title: 会员中心改版-查询课程和老师相关信息<br>
   * Description: 会员中心改版-传入课程类型和当前时间,查询对应课程类型的、课程结束时间大于等于当前时间的课程以及相对应的老师信息<br>
   * CreateDate: 2017年7月20日 下午4:41:03<br>
   * 
   * @category 会员中心改版-传入课程类型和当前时间,查询对应课程类型的、课程结束时间大于等于当前时间的课程以及相对应的老师信息
   * @author felix.yl
   * @param sessionUser
   * @param courseType
   * @param paramTime
   * @return
   * @throws Exception
   */
  public List<CourseSchedulingParam> findCourseAndTeacherInfoList(SessionUser sessionUser,
      String courseType, Date paramTime)
      throws Exception {
    // 调用Dao层,查找对应课程类型,课程结束时间大于等于'参数时间'的课程以及老师相关信息
    List<CourseSchedulingParam> courseAndTeacherInfoList = courseOneToManySchedulingDao
        .findCourseAndTeacherInfoList(courseType, paramTime);

    // 获取该课程类型对应的"可提前取消预约的时间"和"提前进入教室的时间"
    CourseType ct = (CourseType) MemcachedUtil.getValue(courseType);
    Integer cancelSubscribeTime = ct.getCourseTypeCancelSubscribeTime() * 60 * 1000;// 提前取消预约时间(分钟)
    Integer beforeGoclassTime = ct.getCourseTypeBeforeGoclassTime() * 60 * 1000;// 提前上课时间(分钟)

    // 1-可取消预约;2-倒计时;3-可进入教室
    for (CourseSchedulingParam returnObj : courseAndTeacherInfoList) {
      Long courseStartTime = returnObj.getStartTime().getTime();// 课程开始时间
      Long courseEndTime = returnObj.getEndTime().getTime();// 课程结束时间
      Long currentTime = System.currentTimeMillis();// 当前时间
      Long time = courseStartTime - currentTime;// 现在时间距离上课时间间隔

      // 刷新状态
      if (time > cancelSubscribeTime) {
        returnObj.setStatus(SubscribeCourseConstant.CAN_CANCEL_SUBSCRIBE);// 1-可取消预约
      }
      if (time <= cancelSubscribeTime && time > beforeGoclassTime) {
        returnObj.setStatus(SubscribeCourseConstant.CAN_COUNT_DOWN);// 2-倒计时
      }
      if (time <= beforeGoclassTime && currentTime < courseEndTime) {
        returnObj.setStatus(SubscribeCourseConstant.CAN_INTO_CLASSROOM);// 3-可进入教室
      }
    }

    // 构建对象,向对象中的属性赋值
    CourseSchedulingParam paramObj = new CourseSchedulingParam();
    paramObj.setCourseType(courseType);
    paramObj.setParamTime(paramTime);

    // 调用方法,获取预约相关信息
    findCourseAndTeacherAndSchedulingInfoList(sessionUser, paramObj, courseAndTeacherInfoList);

    return courseAndTeacherInfoList;
  }

  /**
   * 
   * Title: 会员中心改版-结合(课程+老师)相关信息以及预约表相关信息,获取课程的预约状态<br>
   * Description: 结合(课程+老师)相关信息以及预约表相关信息,获取课程的预约状态<br>
   * CreateDate: 2017年7月20日 下午5:05:28<br>
   * 
   * @category 结合(课程+老师)相关信息以及预约表相关信息,获取课程的预约状态
   * @author felix.yl
   * @param sessionUser
   * @param paramObj
   * @param courseAndTeacherInfoList
   * @return
   * @throws Exception
   */
  public List<CourseSchedulingParam> findCourseAndTeacherAndSchedulingInfoList(
      SessionUser sessionUser, CourseSchedulingParam paramObj,
      List<CourseSchedulingParam> courseAndTeacherInfoList) throws Exception {

    // 根据userId、courseType、参数时间,查询课程预约表相关信息
    List<SubscribeCourseParam> subscribeInfoList = subscribeCourseEntityDao
        .findSubscribeCourseList(sessionUser.getKeyId(), paramObj
            .getCourseType(), paramObj.getParamTime());

    Map<String, SubscribeCourseParam> subscribeMapParamList =
        new HashMap<String, SubscribeCourseParam>();

    if (subscribeInfoList != null && subscribeInfoList.size() != 0) {
      for (SubscribeCourseParam subscribeObj : subscribeInfoList) {
        subscribeMapParamList.put(subscribeObj.getCourseId(), subscribeObj);
      }
    }

    // 关键逻辑点：课程预约表的courseId就是大课排课表的keyId
    if (courseAndTeacherInfoList != null && courseAndTeacherInfoList.size() != 0) {
      for (CourseSchedulingParam returnObj : courseAndTeacherInfoList) {
        // 如果不为空，则代表预约表中有该课程记录，则已预约
        if (subscribeMapParamList.get(returnObj.getKeyId()) != null) {

          SubscribeCourseParam subscribeCourse = subscribeMapParamList.get(returnObj.getKeyId());

          returnObj.setSubscribeFlag("1");// 是否已预约标志(0:未预约;1:已预约)

          String keyId = subscribeCourse.getKeyId();
          returnObj.setSubscribeId(keyId);// 传入预约id
        } else {
          // 是否已预约标志(0:未预约;1:已预约)
          returnObj.setSubscribeFlag("0");
        }
      }
    }
    return courseAndTeacherInfoList;
  }

}