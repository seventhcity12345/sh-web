package com.webi.hwj.teacher.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.subscribecourse.constant.AdminSubscribeCourseConstant;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseInfoParam;
import com.webi.hwj.teacher.constant.AdminTeacherConstant;
import com.webi.hwj.teacher.dao.TeacherTimeDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.param.FindTeacherCourseCenterCoreCourseParam;
import com.webi.hwj.teacher.param.FindTeacherCourseCenterExtraCourseParam;
import com.webi.hwj.teacher.param.FindTeacherCourseCenterTopGreenRedDateParam;
import com.webi.hwj.teacher.param.FindTeacherTimeAndTeacherParam;
import com.webi.hwj.teacher.param.FindTimesAndTeachersByDayParam;
import com.webi.hwj.teacher.param.TeacherTimeParam;
import com.webi.hwj.util.CalendarUtil;
import com.webi.hwj.webex.dao.WebexRoomDao;
import com.webi.hwj.webex.entity.WebexRoom;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TeacherTimeService {
  private static Logger logger = Logger.getLogger(TeacherTimeService.class);
  @Resource
  TeacherTimeDao teacherTimeDao;
  @Resource
  WebexRoomDao webexRoomDao;
  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  private SubscribeCourseEntityDao subscribeCourseEntityDao;

  /**
   * Title: teacherTime所有的属性更新都走这个方法.<br>
   * Description: 必须要赋值key_id<br>
   * CreateDate: 2017年2月14日 上午9:33:06<br>
   * 
   * @category teacherTime所有的属性更新都走这个方法
   * @author yangmh
   * @param teacherTime
   *          teacherTime对象
   */
  public int update(TeacherTime teacherTime) throws Exception {
    return teacherTimeEntityDao.update(teacherTime);
  }

  /**
   * @category 查询单条数据(通过参数MAP)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return teacherTimeDao.findOne(paramMap, columnName);
  }

  /**
   * Title: 查找可以使用的房间列表<br>
   * Description: 查找可以使用的房间列表<br>
   * CreateDate: 2016年5月6日 下午12:10:26<br>
   * 
   * @category 查找可以使用的房间列表
   * @author komi.zsy
   * @param roomType
   *          房间类型 2为1v1，10为lecture
   * @param startTime
   * @param endTime
   * @return returnList 可用房间号集合
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public List<String> findWebexAvailableRoomList(int webexRoomType, Date startTime, Date endTime)
      throws Exception {
    // modified by alex+komi+seven 2016年8月3日 14:19:47 因为不再使用vcube了

    // 查询出所有房间列表
    List<WebexRoom> roomIdList = webexRoomDao.findAllRoomList(webexRoomType);

    // key为roomid
    Map<String, String> newWebexRoomMap = new HashMap<String, String>();
    for (WebexRoom webexRoomObj : roomIdList) {
      newWebexRoomMap.put(webexRoomObj.getWebexRoomHostId(), webexRoomObj.getWebexRoomHostId());
    }

    // 查询所有已用的房间号
    List<Map<String, Object>> resultMapList = teacherTimeDao
        .findUsedWebexRoomInTeacherTime(startTime, endTime);
    // 去除已用的房间，剩下可用的房间号
    for (Map<String, Object> resultMap : resultMapList) {
      newWebexRoomMap.remove(resultMap.get("webex_room_host_id"));
    }

    List<String> returnList = new ArrayList<String>(newWebexRoomMap.values());
    return returnList;
  }

  /**
   * 
   * Title: 查询老师时间信息<br>
   * Description: 查询老师时间信息<br>
   * CreateDate: 2016年7月25日 下午2:50:58<br>
   * 
   * @category 查询老师时间信息
   * @author seven.gz
   * @param param
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public Page findSubscribeTeacherTimeList(Map<String, Object> param, Date startTime, Date endTime)
      throws Exception {
    // 查询出符合条件的teacherTime信息
    Page page = teacherTimeEntityDao.findTeacherTimeAndTeacherInfo(param, startTime, endTime);
    // 存放teacherTimeid
    List<String> teacherTimeIds = new ArrayList<String>();
    // 行转列存放查出的数据
    Map<String, FindTeacherTimeAndTeacherParam> teacherTimeMap =
        new HashMap<String, FindTeacherTimeAndTeacherParam>();

    if (page != null) {
      List<FindTeacherTimeAndTeacherParam> teacherTimeList = page.getDatas();
      if (teacherTimeList != null && teacherTimeList.size() > 0) {
        for (FindTeacherTimeAndTeacherParam findTeacherTimeAndTeacherParam : teacherTimeList) {

          teacherTimeIds.add(findTeacherTimeAndTeacherParam.getKeyId());
          teacherTimeMap.put(findTeacherTimeAndTeacherParam.getKeyId(),
              findTeacherTimeAndTeacherParam);
          // 将课程类型翻译成码表里的汉字
          findTeacherTimeAndTeacherParam.setCourseType(
              ((CourseType) MemcachedUtil.getValue(findTeacherTimeAndTeacherParam.getCourseType()))
                  .getCourseTypeChineseName());
        }
      }
    }

    // 根据老师时间id查询预约信息
    List<FindSubscribeCourseInfoParam> subscribeCourseList = adminSubscribeCourseDao
        .findSubscribeCourseInfoParam(teacherTimeIds);
    if (subscribeCourseList != null && subscribeCourseList.size() > 0) {
      FindTeacherTimeAndTeacherParam teacherTimeTemp = null;
      for (FindSubscribeCourseInfoParam findSubscribeCourseInfoParam : subscribeCourseList) {
        teacherTimeTemp = teacherTimeMap.get(findSubscribeCourseInfoParam.getTeacherTimeId());
        if (teacherTimeTemp != null) {
          if (StringUtils.isEmpty(teacherTimeTemp.getCourseTitle())) {
            teacherTimeTemp.setCourseTitle(findSubscribeCourseInfoParam.getCourseTitle());
          }
          /**
           * modified by komi 2016年10月25日10:34:01 增加课程等级
           */
          if (teacherTimeTemp.getCourseLevel() == null) {
            // 大课等级，如果这个为空，则用用户等级
            teacherTimeTemp
                .setCourseLevel(findSubscribeCourseInfoParam.getUserLevel());
          }
          teacherTimeTemp.setAlreadyPersonCount(teacherTimeTemp.getAlreadyPersonCount() + 1);
        }
      }
    }

    return page;
  }

  /**
   * 
   * Title: 查询core1v1头部日期<br>
   * Description: 查询core1v1头部日期<br>
   * CreateDate: 2016年9月6日 下午2:28:55<br>
   * 
   * @category 查询core1v1头部日期接口
   * @author seven.gz
   * @param userId
   * @param endOrderTime
   * @return
   * @throws Exception
   */
  public List<Date> findCourseType1TopDateList(String userId, Date endOrderTime) throws Exception {
    List<Date> resultListMap = new ArrayList<Date>(); // 结果集合

    int beforeLessionCanSubscribe = ((CourseType) MemcachedUtil.getValue("course_type1"))
        .getCourseTypeSubscribeTime();
    // 查询的开始时间需要大于提前预约时间
    long startLong = System.currentTimeMillis() + beforeLessionCanSubscribe * 60 * 1000;
    long endLong = startLong + 14 * 24 * 60 * 60 * 1000; // 14天数据

    String start = DateUtil.formatDate(startLong, "yyyy-MM-dd");
    String end = DateUtil.formatDate(endLong, "yyyy-MM-dd");
    // 合同在14天之内失效，则显示的就不是14天了，
    // 获取合同下一天的时间
    endOrderTime = CalendarUtil.getNextNDay(endOrderTime, 1);
    if (endOrderTime.getTime() < endLong) {
      end = DateUtil.dateToStrYYMMDD(endOrderTime);
    }

    List<Map<String, Object>> dataListMap = teacherTimeDao.findFourteenOne2OneTeacherTimeList(start,
        end);
    if (dataListMap != null && dataListMap.size() > 0) {
      for (Map<String, Object> map : dataListMap) {
        // 将日期放入返回结果中
        resultListMap.add((Date) map.get("today"));
      }
    }

    return resultListMap;
  }

  /**
   * 
   * Title: 按天查询预约时的老师时间列表时间<br>
   * Description: 按天查询预约时的老师时间列表<br>
   * CreateDate: 2016年9月6日 下午4:25:39<br>
   * 
   * @category 按天查询预约时的老师时间列表
   * @author seven.gz
   * @param teacherTimeDate
   * @return
   * @throws Exception
   */
  public List<FindTimesAndTeachersByDayParam> findCourseType1TeacherTimeList(Date teacherTimeDate)
      throws Exception {
    int beforeLessionCanSubscribe = ((CourseType) MemcachedUtil.getValue("course_type1"))
        .getCourseTypeSubscribeTime();

    // 查询的开始时间需要大于提前预约时间
    long startTime = System.currentTimeMillis() + beforeLessionCanSubscribe * 60 * 1000;

    Date selectDate = DateUtil.strToDateYYYYMMDDHHMMSS(DateUtil.dateToStrYYMMDD(teacherTimeDate)
        + " 00:00:00");
    // 获取所查日期前一天0点时间
    Long oneDayBefore = selectDate.getTime() - 1 * 24 * 60 * 60 * 1000;

    // 获取所查数据后2天0点时间
    Long twoDayAfter = selectDate.getTime() + 2 * 24 * 60 * 60 * 1000;

    // 取个打的值
    if (oneDayBefore > startTime) {
      startTime = oneDayBefore;
    }

    // 查找当天的老师&时间
    List<FindTimesAndTeachersByDayParam> timeAndTeacherList = teacherTimeEntityDao
        .findTimesAndTeachersByDay(new Date(startTime), new Date(twoDayAfter));

    return timeAndTeacherList;
  }

  /**
   * Title: 专门为预约删除老师时间使用<br>
   * Description: 环讯出错后需要删除掉我们的老师时间，并设置了事务传播级别，为新开一个事务，因为父事务是要回滚的，子事务需要提交<br>
   * CreateDate: 2016年9月21日 下午2:56:58<br>
   * 
   * @category deleteOnlyUseInSubscribe
   * @author yangmh
   * @param ids
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
  public int deleteOnlyUseInSubscribe(String ids) throws Exception {
    return teacherTimeDao.delete(ids);
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
  public List<Date> findTopDateListByCourseType(String userId, Date endOrderTime, String courseType)
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
    // modify by seven 2017年9月12日18:42:49 合同当天的也要查询出来
    // 获取合同下一天的时间
    endOrderTime = CalendarUtil.getNextNDay(endOrderTime, 1);
    if (endOrderTime.getTime() < endLong) {
      endTime = DateUtil.strToDateYYYYMMDD(DateUtil.dateToStrYYMMDD(endOrderTime));
    }

    List<TeacherTimeParam> dataList = teacherTimeEntityDao.findTimeDateList(startTime,
        endTime, courseType);

    if (dataList != null && dataList.size() > 0) {
      for (TeacherTimeParam teacherTimeParam : dataList) {
        // 将日期放入返回结果中
        resultList.add(teacherTimeParam.getStartTime());
      }
    }
    return resultList;
  }

  /**
   * 
   * Title: 查询拥有某些权限的老师时间<br>
   * Description: 用于demo订课<br>
   * CreateDate: 2016年12月22日 下午9:09:07<br>
   * 
   * @category 查询拥有某些权限的老师时间
   * @author seven.gz
   * @param day
   * @param startTime
   * @param courseType
   * @return
   * @throws Exception
   */
  public List<FindTimesAndTeachersByDayParam> findTimesAndTeachersByDayAndCourseType(String day,
      String courseType, String webexRoomHostId)
          throws Exception {
    Date currentTime = new Date();// 系统当前时间
    List<FindTimesAndTeachersByDayParam> returnList = teacherTimeEntityDao
        .findTimesAndTeachersByDayAndCourseType(day, courseType, currentTime);
    if (returnList != null && returnList.size() > 0) {
      // 查出所查当天已经被排入的房间的老师时间
      List<TeacherTime> roomUsedTeacherTimeList = teacherTimeEntityDao.findListTeacherTimeRoomUsed(
          webexRoomHostId, day, currentTime);

      // 过滤不可用时间
      Iterator<FindTimesAndTeachersByDayParam> it = returnList.iterator();
      int beforeLessionCanSubscribe = 0;
      CourseType courseTypeEnity = (CourseType) MemcachedUtil.getValue(courseType);
      while (it.hasNext()) {
        FindTimesAndTeachersByDayParam findTimesAndTeachersByDayParam = it.next();
        // 根据教师来源和教师教师性质判断提前预约的时间，speakhi全职老师提前2小时，其他的按数据库配置走，环迅老师不做限制有环迅来做
        // modify by seven 2017年7月17日 修改为全职和买断都是2小时 其他按数据库走
        if (!AdminTeacherConstant.TEACHER_THIRD_FROM_HUANXUN.equals(findTimesAndTeachersByDayParam
            .getThirdFrom())) {
          if (AdminTeacherConstant.TEACHER_TEACHER_JOB_TYPE_FULL_TIME == findTimesAndTeachersByDayParam
              .getTeacherJobType()
              || AdminTeacherConstant.TEACHER_TEACHER_JOB_TYPE_BUYOUT == findTimesAndTeachersByDayParam
                  .getTeacherJobType()) {
            beforeLessionCanSubscribe =
                AdminSubscribeCourseConstant.DEMO_SUBSCRIBE_TIME_SPEAKHI_FULL_TIME;
          } else {
            beforeLessionCanSubscribe = courseTypeEnity.getCourseTypeSubscribeTime();
          }
        }

        // 判断是否需要移除结果
        if (findTimesAndTeachersByDayParam.getStartTime().getTime() < System.currentTimeMillis()
            + beforeLessionCanSubscribe * 60
                * 1000) {
          it.remove();
          // 进行下一个循环,否则下面的删除动作会报错
          continue;
        }

        // 删除房间没有时间的时段
        if (roomUsedTeacherTimeList != null && roomUsedTeacherTimeList.size() > 0) {
          // 如果是已经被占用的时间则从结果集中移除
          boolean continueFlag = false;
          for (TeacherTime roomTeacherTime : roomUsedTeacherTimeList) {
            // 判断有交集的三中情况

            // 之前判断删除的逻辑 hide By Felix 2017-06-30
            /*
             * if ((roomTeacherTime.getStartTime().getTime() <=
             * findTimesAndTeachersByDayParam .getStartTime().getTime() &&
             * roomTeacherTime.getStartTime() .getTime() >
             * findTimesAndTeachersByDayParam.getEndTime().getTime()) ||
             * (roomTeacherTime.getStartTime().getTime() <
             * findTimesAndTeachersByDayParam .getEndTime().getTime() &&
             * roomTeacherTime.getStartTime() .getTime() >=
             * findTimesAndTeachersByDayParam.getEndTime().getTime()) ||
             * (roomTeacherTime.getStartTime().getTime() >
             * findTimesAndTeachersByDayParam .getStartTime().getTime() &&
             * roomTeacherTime.getEndTime() .getTime() >
             * findTimesAndTeachersByDayParam.getEndTime().getTime()))
             */

            // Modify By Felix 2017-06-30
            // "在弹窗界面展示数据之前删除不符合实际逻辑的数据,在此我修改了'是否删除'的if判断逻辑,原先的判断逻辑见上注释内容";(对于查询出的一堆数据,删除不符合的实际业务的数据)
            if ((roomTeacherTime.getStartTime().getTime() <= findTimesAndTeachersByDayParam
                .getStartTime().getTime() && roomTeacherTime.getEndTime()
                    .getTime() > findTimesAndTeachersByDayParam.getStartTime().getTime())
                || (roomTeacherTime.getStartTime().getTime() < findTimesAndTeachersByDayParam
                    .getEndTime().getTime() && roomTeacherTime.getEndTime()
                        .getTime() >= findTimesAndTeachersByDayParam.getEndTime().getTime())
                || (roomTeacherTime.getStartTime().getTime() >= findTimesAndTeachersByDayParam
                    .getStartTime().getTime() && roomTeacherTime.getEndTime()
                        .getTime() <= findTimesAndTeachersByDayParam.getEndTime().getTime())) {
              it.remove();
              // 进行下一个循环,否则下面的删除动作会报错
              continueFlag = true;
              break;
            }
          }
          if (continueFlag) {
            continue;
          }
        }

        // 查询房间,用时间过滤房间
        WebexRoom webexRoom = webexRoomDao.findWebexRoomByHostId(webexRoomHostId);
        if (webexRoom != null && webexRoom.getRoomTimeFilter() != null) {
          if (webexRoom.getRoomTimeFilter() != CalendarUtil.getMinute(findTimesAndTeachersByDayParam
              .getStartTime())) {
            it.remove();
            // 进行下一个循环,否则下面的删除动作会报错
            continue;
          }

        }

      }
    }
    return returnList;
  }

  /**
   * Title: 教师端-头部日期列表查询.<br>
   * Description: 弄的巨复杂，以后需要重构<br>
   * CreateDate: 2017年2月10日 下午6:24:39<br>
   * 
   * @category 教师端-头部日期列表查询
   * @author yangmh
   * @param teacherId
   *          老师id
   * @param 查询日期
   */
  public List<FindTeacherCourseCenterTopGreenRedDateParam> findTeacherCourseCenterTopDateList(
      String teacherId, String queryDate)
          throws Exception {

    // 第一步 获取2周内排课的日期数据(可点击的日期,不管有没有约的，都可以点击并刷新下面的列表)
    List<TeacherTime> availableDateList = teacherTimeEntityDao
        .findTeacherCourseCenterTopAvailableDateList(teacherId, queryDate);

    // 第二步 获取2周内排课的绿标+红标(小课)
    List<FindTeacherCourseCenterTopGreenRedDateParam> topGreenRedDateSmallList =
        teacherTimeEntityDao
            .findTeacherCourseCenterTopGreenRedDateSmallList(teacherId, queryDate);

    // 获取2周内排课的绿标+红标(大课)
    List<FindTeacherCourseCenterTopGreenRedDateParam> topGreenRedDateBigList = teacherTimeEntityDao
        .findTeacherCourseCenterTopGreenRedDateBigList(teacherId, queryDate);

    // 组合大课列表+小课列表
    List<FindTeacherCourseCenterTopGreenRedDateParam> totalRedDateList =
        new ArrayList<FindTeacherCourseCenterTopGreenRedDateParam>();
    totalRedDateList.addAll(topGreenRedDateSmallList);
    totalRedDateList.addAll(topGreenRedDateBigList);

    Map<Date, FindTeacherCourseCenterTopGreenRedDateParam> returnMap =
        new HashMap<Date, FindTeacherCourseCenterTopGreenRedDateParam>();

    // 遍历可点击日期列表，然后计算红绿图标，为了给前端简单的数据结构
    for (TeacherTime teacherTime : availableDateList) {
      FindTeacherCourseCenterTopGreenRedDateParam initObj =
          new FindTeacherCourseCenterTopGreenRedDateParam();
      initObj.setStartTime(teacherTime.getStartTime());
      initObj.setGreenCount(0);
      initObj.setRedCount(0);
      returnMap.put(teacherTime.getStartTime(), initObj);
      if (totalRedDateList.size() > 0) {
        for (FindTeacherCourseCenterTopGreenRedDateParam totalRedDate : totalRedDateList) {

          if (teacherTime.getStartTime().getTime() == totalRedDate.getStartTime().getTime()) {
            FindTeacherCourseCenterTopGreenRedDateParam tempGreenRedDateParam =
                (FindTeacherCourseCenterTopGreenRedDateParam) returnMap
                    .get(totalRedDate.getStartTime());

            /**
             * 下面的一坨操作是为了累加每个日期的绿点和红点，这块以后在优化，现在比较着急先这么写.
             */

            int tempGreen = 0;
            int tempRed = 0;
            if (tempGreenRedDateParam != null) {
              if (tempGreenRedDateParam.getGreenCount() != null) {
                tempGreen = tempGreenRedDateParam.getGreenCount();
              }
              if (tempGreenRedDateParam.getRedCount() != null) {
                tempRed = tempGreenRedDateParam.getRedCount();
              }
            }

            if (totalRedDate.getIsConfirm()) {
              // 绿色图标数量

              if (tempGreenRedDateParam != null) {
                totalRedDate.setGreenCount(totalRedDate.getCt() + tempGreen);
                totalRedDate.setRedCount(tempRed);
              } else {
                totalRedDate.setGreenCount(totalRedDate.getCt());
              }
            } else {
              // 红色图标数量

              if (tempGreenRedDateParam != null) {
                totalRedDate.setRedCount(totalRedDate.getCt() + tempRed);

                totalRedDate.setGreenCount(tempGreen);
              } else {
                totalRedDate.setRedCount(totalRedDate.getCt());
              }

            }
            totalRedDate.setIsConfirm(null);
            totalRedDate.setCt(null);
            // returnList.add(findTeacherCourseCenterTopGreenRedDateParam);

            returnMap.put(totalRedDate.getStartTime(), totalRedDate);

            // break;
          }
        }
      }
    }

    List<FindTeacherCourseCenterTopGreenRedDateParam> returnList =
        new ArrayList<FindTeacherCourseCenterTopGreenRedDateParam>();

    for (Date date : returnMap.keySet()) {
      returnList.add(returnMap.get(date));
    }

    return returnList;
  }

  /**
   * Title: 查询教师端-课程中心-core课程列表.<br>
   * Description: findTeacherCourseCenterCoreCourseList<br>
   * CreateDate: 2017年2月13日 下午5:17:42<br>
   * 
   * @category 查询教师端-课程中心-core课程列表
   * @author yangmh
   * @param queryDate
   *          查询日期
   * @param teacherId
   *          老师id
   * @param type
   *          查询类型(2:全部数据,1:已确认数据,0:未确认数据)
   */
  public List<FindTeacherCourseCenterCoreCourseParam> findTeacherCourseCenterCoreCourseList(
      String queryDate, String teacherId, String type)
          throws Exception {
    // 1.查询1v1课程列表
    // 注意:只有1v1的课才有这套机制以及查询学员详细信息
    List<FindTeacherCourseCenterCoreCourseParam> coreCourseList = teacherTimeEntityDao
        .findTeacherCourseCenterCoreCourseList(queryDate, teacherId);
    List<FindTeacherCourseCenterCoreCourseParam> returnList = new ArrayList<>();
    for (FindTeacherCourseCenterCoreCourseParam findTeacherCourseCenterCourseParam : coreCourseList) {
      /**
       * 需要查询前一个预约过并上过的课,tom允许的n+1查询，因为数据量不大，否则要写出一个巨复杂的sql，维护成本太高.
       */
      SubscribeCourse subscribeCourse = subscribeCourseEntityDao
          .findLastSubscribeByKeyIdAndCourseTypeAndUserId(
              findTeacherCourseCenterCourseParam.getUserId(),
              findTeacherCourseCenterCourseParam.getSubscribeId(),
              findTeacherCourseCenterCourseParam.getCourseType());

      if (subscribeCourse != null) {

        findTeacherCourseCenterCourseParam
            .setLastSubscribeCourseTitle(subscribeCourse.getCourseTitle());

        // 课程类型对象
        CourseType courseType = (CourseType) MemcachedUtil
            .getValue(subscribeCourse.getCourseType());

        findTeacherCourseCenterCourseParam.setLastSubscribeCourseType(courseType
            .getCourseTypeEnglishName());
      }

      // 课程类型对象
      CourseType courseType = (CourseType) MemcachedUtil
          .getValue(findTeacherCourseCenterCourseParam.getCourseType());

      // tom要求要为课程类型名称+上课形式
      findTeacherCourseCenterCourseParam.setCourseTypeEnglishName(courseType
          .getCourseTypeEnglishName());

      switch (type) {
        case "0":
          // 红色的老师没有确认的课程
          if (!findTeacherCourseCenterCourseParam.getIsConfirm()) {
            returnList.add(findTeacherCourseCenterCourseParam);
          }
          break;
        case "1":
          // 绿色的老师已经确认的课程
          if (findTeacherCourseCenterCourseParam.getIsConfirm()) {
            returnList.add(findTeacherCourseCenterCourseParam);
          }
          break;
        case "2":
          returnList.add(findTeacherCourseCenterCourseParam);
          break;
      }

      /**
       * modified by komi 如果是demo课且没有名字，则显示为demostudent
       */
      if ("course_type4".equals(findTeacherCourseCenterCourseParam.getCourseType())
          && StringUtils.isEmpty(findTeacherCourseCenterCourseParam.getEnglishName())) {
        findTeacherCourseCenterCourseParam.setEnglishName("demoStudent");
      }

    }
    return returnList;
  }

  /**
   * Title: 查询教师端-课程中心-extra课程列表.<br>
   * Description: 包括新概念课程<br>
   * CreateDate: 2017年2月13日 下午5:17:42<br>
   * 
   * @category 查询教师端-课程中心-extra课程列表
   * @author yangmh
   * @param queryDate
   *          查询日期
   * @param teacherId
   *          老师id
   * @param type
   *          查询类型(2:全部数据,1:已确认数据,0:未确认数据)
   */
  public List<FindTeacherCourseCenterExtraCourseParam> findTeacherCourseCenterExtraCourseList(
      String queryDate, String teacherId, String type)
          throws Exception {
    // 1.查询1vn课程列表
    List<FindTeacherCourseCenterExtraCourseParam> extraCourseList = teacherTimeEntityDao
        .findTeacherCourseCenterExtraCourseList(queryDate, teacherId);

    List<FindTeacherCourseCenterExtraCourseParam> returnList =
        new ArrayList<FindTeacherCourseCenterExtraCourseParam>();
    for (FindTeacherCourseCenterExtraCourseParam findTeacherCourseCenterCourseParam : extraCourseList) {

      // 课程类型对象
      CourseType courseType = (CourseType) MemcachedUtil
          .getValue(findTeacherCourseCenterCourseParam.getCourseType());

      // tom要求要为课程类型名称+上课形式
      findTeacherCourseCenterCourseParam.setCourseTypeEnglishName(courseType
          .getCourseTypeEnglishName());

      switch (type) {
        case "0":
          // 红色的老师没有确认的课程
          if (!findTeacherCourseCenterCourseParam.getIsConfirm()) {
            returnList.add(findTeacherCourseCenterCourseParam);
          }
          break;
        case "1":
          // 绿色的老师已经确认的课程
          if (findTeacherCourseCenterCourseParam.getIsConfirm()) {
            returnList.add(findTeacherCourseCenterCourseParam);
          }
          break;
        case "2":
          returnList.add(findTeacherCourseCenterCourseParam);
          break;
      }
    }
    return returnList;
  }
}