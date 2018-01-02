package com.webi.hwj.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.admin.dao.AdminBdminUserEntityDao;
import com.webi.hwj.admin.dto.BadminUserDto;
import com.webi.hwj.admin.entity.BadminUser;
import com.webi.hwj.admin.service.AdminBdminUserService;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.SmsConstant;
import com.webi.hwj.course.dao.CourseOne2ManySchedulingDao;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.index.param.UserRegisterParam;
import com.webi.hwj.index.service.IndexService;
import com.webi.hwj.ordercourse.dao.AdminOrderCourseDao;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseEntityDao;
import com.webi.hwj.ordercourse.param.FindOrderCourseCountParam;
import com.webi.hwj.ordercourse.service.AdminOrderCourseOptionService;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeSupplementEntityDao;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseAndTimeParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseCountParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeSupplementCountParam;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.trainingcamp.entity.TrainingMember;
import com.webi.hwj.trainingcamp.service.TrainingMemberService;
import com.webi.hwj.user.dao.AdminUserDao;
import com.webi.hwj.user.param.FindUserCourseStatisticsParam;
import com.webi.hwj.user.util.AdminUserDateUtil;
import com.webi.hwj.user.util.AdminUserUtil;
import com.webi.hwj.user.util.UserCategoryTypeUtil;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminUserService {
  private static Logger logger = Logger.getLogger(AdminUserService.class);
  @Resource
  AdminUserDao adminUserDao;

  @Resource
  SubscribeCourseDao subscribeCourseDao;

  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  private TellmemoreService tellmemoreService;

  @Resource
  SubscribeCourseService subscribeCourseService;

  @Resource
  AdminOrderCourseDao adminOrderCourseDao;

  @Resource
  CourseOne2ManySchedulingDao courseOne2ManySchedulingDao;

  @Resource
  UserService userService;

  @Resource
  IndexService indexService;

  @Resource
  AdminOrderCourseOptionService adminOrderCourseOptionService;

  @Resource
  AdminOrderCourseEntityDao adminOrderCourseEntityDao;

  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;

  @Resource
  SubscribeSupplementEntityDao subscribeSupplementEntityDao;

  @Resource
  AdminBdminUserEntityDao adminBdminUserEntityDao;

  @Resource
  AdminBdminUserService adminBdminUserService;
  
  @Resource
  TrainingMemberService trainingMemberService;

  /**
   * 
   * Title: 学员管理中，双击详情页面dialog中的 订课详情 <br>
   * Description: findSubscribedCourseByUserId<br>
   * CreateDate: 2015年11月29日 下午2:58:47<br>
   * 
   * @category 学员管理中，双击详情页面dialog中的 订课详情
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findSubscribedCourseByUserIdEasyui(Map<String, Object> paramMap) throws Exception {
    Page subscribedCoursePage = adminUserDao.findSubscribedCourseByUserIdEasyui(paramMap);
    List<Map<String, Object>> subscribedCourseList = subscribedCoursePage.getDatas();
    // 课程体系&& 课程类别 && 状态 转换为 页面显示的格式内容
    subscribedCoursePage.setDatas(UserCategoryTypeUtil
        .formatCategoryTypeAndCourseTypeAndSubscribeStatus(subscribedCourseList));
    return subscribedCoursePage;
  }

  /**
   * 
   * Title: findTeacherTimePageEasyui<br>
   * Description: findTeacherTimePageEasyui<br>
   * CreateDate: 2015年11月26日 上午11:55:26<br>
   * 
   * @category findTeacherTimePageEasyui
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findTeacherTimePageEasyui(Map<String, Object> paramMap) throws Exception {
    String course_type = paramMap.get("course_type").toString();
    Object date_time = paramMap.get("date_time");
    Page teacherTimePage = new Page();
    switch (course_type) {
      // 1对1或者小包课（实际上也是1对1）
      case "course_type1":
      case "course_type3":
        // 如果后台没有传递时间，则有数据异常，直接返回空数据
        if (date_time == null || "".equals(date_time)) {
          return teacherTimePage;
        }
        /**
         * 查询1对1课程（小包课）的teacher_time
         */
        // 页面查询时间参数转换为Date
        Date date_time2Date = new SimpleDateFormat("yyyy-MM-dd").parse(date_time.toString());
        // 处理选择的时间（毫秒）：需要提前xxx小时预约
        int before_lesson_countdown = ((CourseType) MemcachedUtil.getValue("course_type1"))
            .getCourseTypeSubscribeTime() * 60 * 1000;
        Map<String, Object> thisDayMinAndMaxTimeMap = AdminUserDateUtil
            .addThisDayMinAdnMaxTime(date_time2Date.getTime() + before_lesson_countdown);

        // 将开始时间和结束时间 作为查询参数
        paramMap.put("start_time", thisDayMinAndMaxTimeMap.get("thisDayMinTime"));
        paramMap.put("end_time", thisDayMinAndMaxTimeMap.get("thisDayMaxTime"));

        // 查询结果集
        teacherTimePage = adminUserDao.findTeacherTimePage(paramMap);

        // 处理查询到的结果集
        teacherTimePage.setDatas(
            addUserIdAndCourseIdAndCourseType2TeacherTimePage(teacherTimePage.getDatas(),
                paramMap));
        break;
      case "course_type2":
        // 直接查询1对多表，获取老师信息和上课时间信息
        teacherTimePage = courseOne2ManySchedulingDao
            .findOne2ManyCoursePageEasyuiByCourseId(paramMap);

        // 处理结果集
        teacherTimePage.setDatas(
            addUserIdAndCourseType2One2ManyCoursePage(teacherTimePage.getDatas(), paramMap));
        break;
      default:
        break;
    }
    return teacherTimePage;
  }

  private static List<Map<String, Object>> addUserIdAndCourseType2One2ManyCoursePage(
      List<Map<String, Object>> one2ManyCourseList, Map<String, Object> paramMap) {
    if (one2ManyCourseList == null || one2ManyCourseList.size() == 0) {
      return one2ManyCourseList;
    }
    for (Map<String, Object> one2ManyCourse : one2ManyCourseList) {
      one2ManyCourse.put("user_id", paramMap.get("user_id"));
      one2ManyCourse.put("course_type", paramMap.get("course_type"));
    }
    return one2ManyCourseList;
  }

  /**
   * 
   * Title: 将传递的用户id和课程id绑定 传到前台<br>
   * Description: addUserIdAndCourseId2TeacherTimePage<br>
   * CreateDate: 2015年11月26日 下午5:19:53<br>
   * 
   * @category 将传递的用户id和课程id绑定 传到前台
   * @author athrun.cw
   * @param teacherTimeList
   * @param paramMap
   * @return
   */
  private static List<Map<String, Object>> addUserIdAndCourseIdAndCourseType2TeacherTimePage(
      List<Map<String, Object>> teacherTimeList, Map<String, Object> paramMap) {
    if (teacherTimeList == null || teacherTimeList.size() == 0) {
      return teacherTimeList;
    }
    for (Map<String, Object> teacherTime : teacherTimeList) {
      teacherTime.put("user_id", paramMap.get("user_id"));
      teacherTime.put("course_id", paramMap.get("course_id"));
      teacherTime.put("course_type", paramMap.get("course_type"));
    }
    return teacherTimeList;
  }

  /**
   * Title: 根据用户手机号判断用户是否学员 <br>
   * Description: 根据用户手机号判断用户是否学员 <br>
   * CreateDate: 2016年3月30日 下午5:10:26<br>
   * 
   * @category 根据用户手机号判断用户是否学员
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int fingStudentIsExistByPhone(Map<String, Object> paramMap) throws Exception {

    return adminUserDao.findCount(paramMap);
  }

  /**
   * Title: 根据用户手机号重置用户密码<br>
   * Description: 重置用户密码<br>
   * CreateDate: 2016年3月30日 下午6:26:43<br>
   * 
   * @category 重置用户密码
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int resetUserPasswordByAdmin(Map<String, Object> paramMap) throws Exception {

    return adminUserDao.resetUserPasswordByPhone(paramMap);
  }

  /**
   * 
   * Title: 批量修改用户的learning_coach_id<br>
   * Description: batchUpdateUserLC<br>
   * CreateDate: 2015年11月23日 上午11:27:37<br>
   * 
   * @category 批量修改用户的learning_coach_id
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage batchUpdateUserLC(Map<String, Object> paramMap) throws Exception {
    JsonMessage json = new JsonMessage();
    // 获得后台传递的keys
    String user_ids = paramMap.get("user_id").toString();
    if (user_ids == null && "".equals(user_ids)) {
      logger.error("后台传递的参数有误！");
      throw new RuntimeException("后台传递的参数有误！");
    }

    String[] userIds = user_ids.split(",");
    for (String user_id : userIds) {
      Map<String, Object> updateUserLearnCoachMap = new HashMap<String, Object>();
      updateUserLearnCoachMap.put("key_id", user_id);
      updateUserLearnCoachMap.put("learning_coach_id", paramMap.get("learning_coach_id"));
      adminUserDao.updateUserLearnCoachId(updateUserLearnCoachMap);
    }
    return json;
  }

  /**
   * Title: 查询代订课中的下拉框中的课程列表<br>
   * Description: findUserCourseList<br>
   * CreateDate: 2016年6月28日 下午8:26:09<br>
   * 
   * @category findUserCourseList
   * @author 曹伟
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findUserCourseList(Map<String, Object> paramMap)
      throws Exception {
    // course_type决定课程类别
    String course_type = paramMap.get("course_type").toString();
    /**
     * 判断是查询那种类型的课程 1.当course_type为course_type1时，课程类别：1v1
     * 2.当course_type为course_type2时，是课程类别：1vn
     * 3.当course_type为course_type3时，是课程类别：主题课
     */
    List<Map<String, Object>> courseList = new ArrayList<Map<String, Object>>();
    if ("course_type1".equals(course_type)) {
      // 根据条件category_type current_level，查询学员能够预约的1对1课程列表
      courseList = adminUserDao.findUserOne2OneCourseListByCategoryTypeAndLevel(paramMap);
    } else if ("course_type2".equals(course_type)) {
      // 根据条件category_type，查询学员能够预约的1对多课程列表
      String start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
          .format(new Date(System.currentTimeMillis()));
      paramMap.put("start_time", start_time);
      courseList = courseOne2ManySchedulingDao.findUserOne2ManyCourseListByCategoryType(paramMap);
    } else if ("course_type3".equals(course_type)) {
      // 1.查询到学员当前已经预约了那些小包课，找到最大的course_position
      int smallpackMaxPositionSubscribed = subscribeCourseDao
          .findSmallpackMaxPositionSubscribedByUserIdAndCourseId(paramMap);

      paramMap.put("course_position", smallpackMaxPositionSubscribed);

      // 2.根据条件category_type，查询学员能够预约的主题课程列表
      courseList = adminUserDao.findUserSmallpackCourseListByCategoryType(paramMap);
    } else {
      throw new Exception("没有可用的课程类型！！！！！！");
    }

    return courseList;
  }

  /**
   * Title: 获取所有有效的lc<br>
   * Description: 获取所有有效的lc<br>
   * CreateDate: 2017年3月2日 上午11:51:55<br>
   * 
   * @category 获取所有有效的lc
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<BadminUser> findAdminUserListByLc() throws Exception {
    // 从码表中获取哪些角色id算lc
    String roleIdstr = MemcachedUtil.getConfigValue(ConfigConstant.LC_ROLE_IDS);
    List<BadminUser> adminUserList = adminBdminUserService.findAdminUserListByRoleIds(roleIdstr);
    logger.debug("获取所有有效的教务adminUserList: " + adminUserList);
    return adminUserList;
  }

  /**
   * 
   * Title: 查询学员管理页面中显示的数据 <br>
   * Description: findStudentPageEasyui<br>
   * CreateDate: 2015年11月19日 下午6:55:16<br>
   * 
   * @category 查询学员管理页面中显示的数据
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(readOnly = true)
  public Page findStudentPageEasyui(Map<String, Object> paramMap) throws Exception {
    // modified by alex+athrun 2016年3月2日15:36:24
    // 1.1 只查询学员的详细信息和LC信息（以学员为主表）
    Page userInformationsPage = adminUserDao.findStudentPageEasyui(paramMap);

    Map<String, Map<String, Object>> userStatisticsInfoMap =
        new HashMap<String, Map<String, Object>>();

    // 获得查询出来的用户合同数据
    List<Map<String, Object>> userInformationsList = userInformationsPage.getDatas();
    logger.debug("0.后台管理用户id [" + paramMap.get("learning_coach_id") + "] 查到的基础数据集合为："
        + userInformationsList);

    if (userInformationsList == null || userInformationsList.size() == 0) {
      return userInformationsPage;
    }

    List<String> tempUserIdsList = new ArrayList<String>();
    for (Map<String, Object> userMap : userInformationsList) {
      tempUserIdsList.add(userMap.get("user_id").toString());
      userStatisticsInfoMap.put(userMap.get("user_id").toString(), userMap);
    }

    Map<String, Object> userIdsParamMap = new HashMap<String, Object>();
    userIdsParamMap.put("userIds", tempUserIdsList);

    // 1.2 查询当前用户列表所包含的所有合同列表
    List<Map<String, Object>> tempOrderCourseList = adminOrderCourseDao
        .findOrderCourseByUserIds(userIdsParamMap);

    // 2.查询followup相关数据
    List<Map<String, Object>> lastFollowupUserList = adminUserDao
        .findLastFollowupUser(userIdsParamMap);

    // 3.需要对本月上课数量进行计算
    Map<String, Object> subscribeUserMap = new HashMap<String, Object>();
    subscribeUserMap.put("userIds", tempUserIdsList);
    subscribeUserMap.put("currentMonthMinTime", AdminUserDateUtil.getCurrentMonthMinTime());
    subscribeUserMap.put("currentTime", new Date());
    List<Map<String, Object>> subscribeCountUserList = adminUserDao
        .findSubscribeCountUser(subscribeUserMap);

    // 返回的 已經去重的 用户数据集合(使用有序的map，保持查询出数据 经过排序的有效性)
    Map<String, Object> orderCourseUserMap = new LinkedHashMap<String, Object>();

    // 遍历
    Map<String, Object> tempUserMap = null;
    for (Map<String, Object> tempOrderCourseMap : tempOrderCourseList) {
      if (userStatisticsInfoMap.containsKey(tempOrderCourseMap.get("user_id").toString())) {
        tempUserMap = userStatisticsInfoMap
            .get(tempOrderCourseMap.get("user_id").toString());

        String userId = tempUserMap.get("user_id").toString();
        String categoryType = tempOrderCourseMap.get("category_type").toString();

        tempUserMap.put("key_id", tempOrderCourseMap.get("order_course_id"));
        tempUserMap.put("category_type", categoryType);
        tempUserMap.put("course_package_id", tempOrderCourseMap.get("course_package_id"));
        tempUserMap.put("course_package_name", tempOrderCourseMap.get("course_package_name"));
        tempUserMap.put("start_order_time", tempOrderCourseMap.get("start_order_time"));
        tempUserMap.put("end_order_time", tempOrderCourseMap.get("end_order_time"));
        tempUserMap.put("order_status", tempOrderCourseMap.get("order_status"));

        // modify by seven 2017年3月28日11:11:23 将学员正在执行中的合同全部显示在列表中
        String packageName = (String) tempOrderCourseMap.get("course_package_name");
        String packageNames = (String) tempUserMap.get("course_package_names");
        if (packageNames != null) {
          packageNames += packageName + ";";
        } else {
          packageNames = packageName + ";";
        }
        tempUserMap.put("course_package_names", packageNames);

        // 需求点：开课时间与合同结束时间默认显示常规课合同时间（如学员只购买了主题课则显示主题课合同时间、如学员同时拥有主题课与常规课则优先显示常规课时间）

        // 用来存放缓冲map的，标识 当前user_id用户数据 是否已经放入了orderCourseUserMap集合
        Map<String, Object> tempMap = (Map<String, Object>) orderCourseUserMap.get(userId);
        // 判断： 是否已经放入了orderCourseUserMap集合
        if (tempMap != null
            && (tempMap.get("have_put") != null && (boolean) tempMap.get("have_put"))) {
          // 已经放过了的,跳过
          continue;
        } else {// 没有放入
          // 默认 只要不重复，全部放入
          orderCourseUserMap.put(userId, tempUserMap);
          // 如果重复了：如果是常规课，（并且以前没有放入过该人），则放入到orderCourseUserMap，并且标识 已经放过了
          if ("category_type1".equals(categoryType) || "category_type2".equals(categoryType)) {
            tempUserMap.put("have_put", true);
          }
        }
      }
    }
    // 遍历逻辑2
    for (Map<String, Object> lastFollowupUserInfos : lastFollowupUserList) {
      if (userStatisticsInfoMap.containsKey(lastFollowupUserInfos.get("user_id").toString())) {
        tempUserMap = userStatisticsInfoMap
            .get(lastFollowupUserInfos.get("user_id").toString());
        // 同一个用户，合并最后followup时间
        tempUserMap.put("last_followup_time", lastFollowupUserInfos.get("last_followup_time"));
      }
    }

    // 遍历逻辑3
    for (Map<String, Object> subscribeUserInfos : subscribeCountUserList) {
      if (userStatisticsInfoMap.containsKey(subscribeUserInfos.get("user_id").toString())) {
        tempUserMap = userStatisticsInfoMap
            .get(subscribeUserInfos.get("user_id").toString());
        // 同一个用户，本月上课数量进行计算(如果没有上课记录的话，显示0处理)
        tempUserMap.put("this_month_course_count",
            subscribeUserInfos.get("this_month_course_count"));
        tempUserMap.put("last_access_time", subscribeUserInfos.get("start_time"));
      }
    }

    // 遍历user将没有this_month_course_count的，表示没有上过课（如果没有上课记录的话，显示0处理）
    for (Map<String, Object> userInfoMapTemp : userInformationsList) {
      // 一会需要修改
      // 4.将每条记录中，没有this_month_course_count的，表示没有上过课（如果没有上课记录的话，显示0处理）
      if (userInfoMapTemp.get("this_month_course_count") == null
          || "".equals(userInfoMapTemp.get("this_month_course_count"))) {
        userInfoMapTemp.put("this_month_course_count", 0);
      }
    }

    // 如果没有数据，以下任何 都不做的处理 return
    if (userInformationsList == null || userInformationsList.size() == 0) {
      return userInformationsPage;
    }

    // 5.将用户 合同中保存的体系类别，转换为后台管理页面中需要显示的合同类别
    List<Map<String, Object>> formatedUserInfoAndCategoryTypeList = UserCategoryTypeUtil
        .formatCategoryType2OrderType(userInformationsList);

    // 6. 统计学员是否达标数据
    statisticsCourseCount(tempUserIdsList, userStatisticsInfoMap);

    // 将1-5步 处理后的集合 formatedUserInfoAndCategoryTypeList集合
    // 装载到userInformationsPage对象中
    userInformationsPage.setDatas(formatedUserInfoAndCategoryTypeList);
    return userInformationsPage;
  }

  /**
   * 
   * Title: findPageEasyuiUser<br>
   * Description: findPageEasyuiUser<br>
   * CreateDate: 2015年8月15日 下午3:41:18<br>
   * 
   * @category findPageEasyuiUser
   * @author yangmh
   * @return
   * @throws Exception
   */
  public Page findPageEasyuiUser(Map<String, Object> fields) throws Exception {
    Page page = adminUserDao.findPageEasyuiUser(fields);

    // modify by seven 2016年12月23日21:52:01 潜客管理增加demo课相关字段
    // 存放学员的id
    List<String> userIds = new ArrayList<String>();
    // 用于行转列
    Map<String, Map<String, Object>> usersDataMap = new HashMap<String, Map<String, Object>>();

    List<Map<String, Object>> datas = page.getDatas();
    if (datas != null && datas.size() > 0) {
      for (Map<String, Object> tmpMap : datas) {
        userIds.add((String) tmpMap.get("user_id"));
        usersDataMap.put((String) tmpMap.get("user_id"), tmpMap);
      }
    }

    // modify by seven 2016年12月23日21:52:01 潜客管理增加demo课相关字段
    // 存放学员的id
    // 查询预约信息主要是demo
    if (userIds.size() > 0) {
      List<FindSubscribeCourseAndTimeParam> subscribeList = adminSubscribeCourseDao
          .findListSubscribeByUserIds(userIds);
      if (subscribeList != null && subscribeList.size() > 0) {
        Map<String, Object> userInfoMap = null;
        for (FindSubscribeCourseAndTimeParam findSubscribeCourseAndTimeParam : subscribeList) {
          userInfoMap = usersDataMap.get(findSubscribeCourseAndTimeParam.getUserId());
          // 判断之前map中是否有存放
          if (userInfoMap != null && "course_type4".equals(findSubscribeCourseAndTimeParam
              .getCourseType())) {
            Date startTime = (Date) userInfoMap.get("startTime");
            if (startTime == null || (findSubscribeCourseAndTimeParam
                .getStartTime() != null && startTime.getTime() < findSubscribeCourseAndTimeParam
                    .getStartTime().getTime())) {
              userInfoMap.put("startTime", findSubscribeCourseAndTimeParam
                  .getStartTime());
              userInfoMap.put("webexMeetingKey",
                  findSubscribeCourseAndTimeParam.getWebexMeetingKey());
              userInfoMap.put("subscribeId",
                  findSubscribeCourseAndTimeParam.getKeyId());
              userInfoMap.put("webexRequestUrl",
                  findSubscribeCourseAndTimeParam.getWebexRequestUrl());
              userInfoMap.put("webexRoomHostId",
                  findSubscribeCourseAndTimeParam.getWebexRoomHostId());
            }
          }
        }
      }
    }

    return page;
  }

  /**
   * @category user 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return adminUserDao.insert(fields);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findListEasyui(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminUserDao.findListEasyui(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminUserDao.findList(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @param pageNumber
   *          当前页数
   * @param pageSize
   *          每页显示多少条数据
   * @return
   * @throws Exception
   */
  public Page findPage(Map<String, Object> paramMap) throws Exception {
    return adminUserDao.findPage(paramMap);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @param pageNumber
   *          当前页数
   * @param pageSize
   *          每页显示多少条数据
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(Map<String, Object> paramMap) throws Exception {
    return adminUserDao.findPageEasyui("*", paramMap);
  }

  /**
   * @category 修改数据
   * @author mingyisoft代码生成工具
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return adminUserDao.update(fields);
  }

  /**
   * Title: 保存用户当前级别<br>
   * . Description: 保存用户当前级别<br>
   * CreateDate: 2015年11月9日 上午7:52:53<br>
   * 
   * @category 保存用户当前级别
   * @author yangmh
   * @param paramMap
   * @param adminUserMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage saveCurrentLevel(Map<String, Object> paramMap, String updateUserId)
      throws Exception {
    String currentLevel = paramMap.get("current_level").toString();

    if (currentLevel != null && !"".equals(currentLevel) && !"null".equals(currentLevel)) {
      paramMap.put("update_user_id", updateUserId);

      /**
       * modify by seven 2016-06-01 添加修改级别时间，学员详情信息中的本级已上1vn课程，需要用到
       */
      paramMap.put("level_change_time", new Date());

      adminUserDao.update(paramMap);
      
      //modified by komi 2017年8月11日10:54:00 级联更新训练营表用户当前级别
      TrainingMember paramObj = new TrainingMember();
      paramObj.setTrainingMemberUserId((String)paramMap.get("key_id"));
      paramObj.setTrainingMemberCurrentLevel(currentLevel);
      trainingMemberService.updateMemberUserCurrentLevel(paramObj);
      
      SessionUser sessionUser = new SessionUser();
      sessionUser.setKeyId(paramMap.get("key_id").toString());
      sessionUser.setPhone(paramMap.get("phone").toString());
      sessionUser.setCourseTypes(
          adminOrderCourseOptionService.findCourseTypesByUserId(sessionUser.getKeyId()));

      tellmemoreService.initTmmData(sessionUser);
      return new JsonMessage();
    } else {
      return new JsonMessage(false, "当前用户还没有级别！");
    }
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
    return adminUserDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return adminUserDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return adminUserDao.delete(ids);
  }

  /**
   * @category 查询数量
   * @author mingyisoft代码生成工具
   * @param sql
   * @param map
   * @return
   * @throws Exception
   */
  public int findCount(Map<String, Object> map) throws Exception {
    return adminUserDao.findCount(map);
  }

  /**
   * @category 查询总数
   * @author mingyisoft代码生成工具
   * @param map
   * @param sumField
   *          sum的字段
   * @return
   * @throws Exception
   */
  public int findSum(Map<String, Object> map, String sumField) throws Exception {
    return adminUserDao.findSum(map, sumField);
  }

  /**
   * Title: 学员不存在则创建<br>
   * Description: 通过学员全局GUID（leadId）和学员手机号均为找到学员则创建学员<br>
   * CreateDate: 2016年4月28日 上午11:36:16<br>
   * 
   * @category 学员不存在则创建
   * @author ivan.mgh
   * @param badminUserDto
   * @param createrId
   * @return
   * @throws Exception
   */
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE)
  public Map<String, Object> createUserIfNotExist(BadminUserDto badminUserDto, String createrId)
      throws Exception {
    // 若学员不存在，则创建
    Map<String, Object> userMap = userService.findOneByLeadId(badminUserDto.getLeadId());

    if (null == userMap) {
      Map<String, Object> um = userService.findOneByPhone(badminUserDto.getStudentMobile());
      if (null == um) {
        UserRegisterParam userRegisterParam = new UserRegisterParam();
        userRegisterParam.setPhone(badminUserDto.getStudentMobile());

        // 生成6位随机数
        String random6BitNumber = String.valueOf((Math.random() * 9 + 1) * 100000).substring(0, 6);
        logger.info("随机生成的6位有效密码是：" + random6BitNumber);

        // 默认密码
        String defaultPwd = SHAUtil.encode(random6BitNumber);
        userRegisterParam.setPwd(defaultPwd);
        userRegisterParam.setAdid(badminUserDto.getAdid());

        Map<String, Object> userObj = indexService.saveUser(userRegisterParam);

        userObj.put("lead_id", badminUserDto.getLeadId());
        userObj.put("create_user_id", createrId);
        userService.update(userObj);

        // 密码发送给学员
        SmsUtil.sendSmsToQueue(badminUserDto.getStudentMobile(),
            SmsConstant.PREFIX_SPEAKHI + random6BitNumber + SmsConstant.REGISTER_NEW_USER);
        userMap = userObj;
      } else { // update leadId
        um.put("lead_id", badminUserDto.getLeadId());
        userService.update(um);

        userMap = um;
      }
    }

    return userMap;
  }

  /**
   * 
   * Title: 统计学员课时数（已上、标准、是否达标）<br>
   * Description: 统计学员课时数（已上、标准、是否达标）<br>
   * CreateDate: 2016年10月25日 下午3:27:45<br>
   * 
   * @category 统计学员课时数（已上、标准、是否达标）
   * @author seven.gz
   * @param userIds
   * @param userInfoMap
   * @throws Exception
   */
  public void statisticsCourseCount(List<String> userIds,
      Map<String, Map<String, Object>> userInfoMap)
          throws Exception {
    if (userIds == null || userIds.size() == 0 || userInfoMap == null) {
      return;
    }
    Map<String, FindUserCourseStatisticsParam> userStatisticsInfoMap =
        new HashMap<String, FindUserCourseStatisticsParam>();
    for (String userId : userIds) {
      FindUserCourseStatisticsParam findUserCourseStatisticsParam =
          new FindUserCourseStatisticsParam();
      findUserCourseStatisticsParam.setUserId(userId);
      userStatisticsInfoMap.put(userId, findUserCourseStatisticsParam);
    }

    FindUserCourseStatisticsParam tempUserInfo = null;
    // 查询合同信息
    List<FindOrderCourseCountParam> orderCourseCountList = adminOrderCourseEntityDao
        .findOrderCourseCount(userIds);
    Date currentDate = new Date();
    // 获取要查的用户id同时行转列
    List<String> orderIds = new ArrayList<String>();
    if (orderCourseCountList != null && orderCourseCountList.size() > 0) {
      for (FindOrderCourseCountParam findOrderCourseCountParam : orderCourseCountList) {
        // 获得用户数据
        tempUserInfo = userStatisticsInfoMap.get(findOrderCourseCountParam.getUserId());
        if (tempUserInfo != null) {
          // 判断合同字表中的课程类型
          if ("course_type1".equals(findOrderCourseCountParam.getCourseType())) {
            tempUserInfo.setOne2OneTotalCount(tempUserInfo.getOne2OneTotalCount()
                + AdminUserUtil.findTotalCount(findOrderCourseCountParam));
          } else if ("course_type2".equals(findOrderCourseCountParam.getCourseType())) {
            tempUserInfo.setOne2ManyTotalCount(tempUserInfo.getOne2ManyTotalCount()
                + AdminUserUtil.findTotalCount(findOrderCourseCountParam));
          }
          tempUserInfo.setStartOrderTime(findOrderCourseCountParam.getStartOrderTime());
          tempUserInfo.setEndOrderTime(findOrderCourseCountParam.getEndOrderTime());
          orderIds.add(findOrderCourseCountParam.getOrderId());
        }
      }
    }

    // 查询补课信息
    List<FindSubscribeSupplementCountParam> findSubscribeSupplementCountParams =
        subscribeSupplementEntityDao
            .findCountsByOrderIds(orderIds);
    if (findSubscribeSupplementCountParams != null
        && findSubscribeSupplementCountParams.size() > 0) {
      for (FindSubscribeSupplementCountParam findSubscribeSupplementCountParam : findSubscribeSupplementCountParams) {
        tempUserInfo = userStatisticsInfoMap.get(findSubscribeSupplementCountParam.getUserId());
        if (tempUserInfo != null) {
          // 判断合同字表中的课程类型
          if ("course_type1".equals(findSubscribeSupplementCountParam.getCourseType())) {
            tempUserInfo.setOne2OneTotalCount(tempUserInfo.getOne2OneTotalCount()
                + findSubscribeSupplementCountParam.getCourseCount());
          } else if ("course_type2".equals(findSubscribeSupplementCountParam.getCourseType())) {
            tempUserInfo.setOne2ManyTotalCount(tempUserInfo.getOne2ManyTotalCount()
                + findSubscribeSupplementCountParam.getCourseCount());
          }
        }
      }
    }

    // 根据合同号查询约课信息
    List<FindSubscribeCourseCountParam> subscribeCourseCountList = adminSubscribeCourseDao
        .findSubscribeCourseCountByOrderIds(orderIds);
    if (subscribeCourseCountList != null && subscribeCourseCountList.size() > 0) {
      for (FindSubscribeCourseCountParam subscribeCourseCount : subscribeCourseCountList) {
        // 获得用户数据
        tempUserInfo = userStatisticsInfoMap.get(subscribeCourseCount.getUserId());
        if (tempUserInfo != null) {
          if ("course_type1".equals(subscribeCourseCount.getCourseType())) {
            // 设置1v1出席课程数
            tempUserInfo.setOne2OneShowCount(subscribeCourseCount.getCourseCount());
          } else if ("course_type2".equals(subscribeCourseCount.getCourseType())) {
            // 设置1vN出席课程数
            tempUserInfo.setOne2ManyShowCount(subscribeCourseCount.getCourseCount());
          }
        }
      }
    }

    // 设置是否是活跃学员
    Collection<FindUserCourseStatisticsParam> userInfoList = userStatisticsInfoMap.values();
    for (FindUserCourseStatisticsParam findUserCourseStatisticsParam : userInfoList) {
      // 设置标准课时数
      // 设置1v1标准课时数
      findUserCourseStatisticsParam
          .setOne2OneStandardCount(AdminUserUtil.calculationStandardCourseCount(
              findUserCourseStatisticsParam.getStartOrderTime(),
              findUserCourseStatisticsParam.getEndOrderTime(),
              findUserCourseStatisticsParam.getOne2OneTotalCount(), currentDate));
      // 设置1vN标准课时数
      findUserCourseStatisticsParam
          .setOne2ManyStandardCount(AdminUserUtil.calculationStandardCourseCount(
              findUserCourseStatisticsParam.getStartOrderTime(),
              findUserCourseStatisticsParam.getEndOrderTime(),
              findUserCourseStatisticsParam.getOne2ManyTotalCount(), currentDate));

      // 已上1v1+已上1vn >= 标准1v1+标准1vN*0.5 为达标学员
      findUserCourseStatisticsParam
          .setActivity(
              findUserCourseStatisticsParam.getOne2ManyShowCount() + findUserCourseStatisticsParam
                  .getOne2OneShowCount() >= (findUserCourseStatisticsParam
                      .getOne2ManyStandardCount()
                      + findUserCourseStatisticsParam.getOne2OneStandardCount()) * 0.5);

      // 设回map中返回到前端
      Map<String, Object> tempUserMap = userInfoMap.get(findUserCourseStatisticsParam.getUserId());
      tempUserMap.put("one2OneStandardCount",
          findUserCourseStatisticsParam.getOne2OneShowCount() + "/" + findUserCourseStatisticsParam
              .getOne2OneStandardCount());
      tempUserMap.put("one2ManyStandardCount",
          findUserCourseStatisticsParam.getOne2ManyShowCount() + "/" + findUserCourseStatisticsParam
              .getOne2ManyStandardCount());
      tempUserMap.put("isActivity", findUserCourseStatisticsParam
          .isActivity());

    }
  }
}
