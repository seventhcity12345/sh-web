package com.webi.hwj.weixin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.constant.CourseConstant;
import com.webi.hwj.course.dao.CourseCommentEnityDao;
import com.webi.hwj.course.entity.CourseComment;
import com.webi.hwj.course.service.CourseOne2OneService;
import com.webi.hwj.courseextension1v1.service.CourseExtension1v1Service;
import com.webi.hwj.courseone2many.dao.CourseOneToManySchedulingDao;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.courseone2one.dao.AdminCourseOne2oneDao;
import com.webi.hwj.courseone2one.param.CourseOne2OneParam;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.ordercourse.entitydao.OrderCourseEntityDao;
import com.webi.hwj.ordercourse.entitydao.OrderCourseOptionEntityDao;
import com.webi.hwj.ordercourse.param.OrderCourseAndOptionParam;
import com.webi.hwj.ordercourse.param.OrderCourseOptionParam;
import com.webi.hwj.ordercourse.util.OrderContractStatusUtil;
import com.webi.hwj.subscribecourse.constant.AdminSubscribeCourseConstant;
import com.webi.hwj.subscribecourse.constant.SubscribeCourseConstant;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.SubscribeCourseAndCommentParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseListParam;
import com.webi.hwj.weixin.constant.WeiXinSubscribeConstant;
import com.webi.hwj.weixin.param.LearningPathTypeInfoParm;

/**
 * 
 * Title: 微信预约相关接口<br>
 * Description: 微信预约相关接口<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年10月13日 下午2:16:52
 * 
 * @author seven.gz
 */
@Service
public class WeiXinSubscribeService {

  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;
  @Resource
  AdminCourseOne2oneDao adminCourseOne2oneDao;
  @Resource
  CourseOne2OneService courseOne2OneService;
  @Resource
  CourseExtension1v1Service courseExtension1v1Service;
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  CourseOneToManySchedulingDao courseOneToManySchedulingDao;
  @Resource
  OrderCourseEntityDao orderCourseEntityDao;
  @Resource
  OrderCourseOptionEntityDao orderCourseOptionEntityDao;
  @Resource
  CourseCommentEnityDao courseCommentEnityDao;

  /**
   * 
   * Title: 查询用户是否预约过这个标题的课程<br>
   * Description: 查询用户是否预约过这个标题的课程<br>
   * CreateDate: 2016年10月17日 上午10:05:50<br>
   * 
   * @category 查询用户是否预约过这个标题的课程
   * @author seven.gz
   * @param courseId
   * @param userId
   * @return
   * @throws Exception
   */
  public CommonJsonObject subscribeCourseType2CoursePremise(String courseId, String userId)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();

    // 查找该课程的课程名字
    CourseOne2ManyScheduling courseOne2ManyScheduling = courseOneToManySchedulingDao
        .findOneByKeyId(courseId);
    if (courseOne2ManyScheduling != null) {
      // 根据userid和课程名字和已出席的课程 的条件，查处是否有数据
      SubscribeCourse subscribeCourse = new SubscribeCourse();
      subscribeCourse.setUserId(userId);
      subscribeCourse.setCourseTitle(courseOne2ManyScheduling.getCourseTitle());
      subscribeCourse.setSubscribeStatus(1);
      int num = adminSubscribeCourseDao.findCount(subscribeCourse);
      // 有数据，则弹出提示
      if (num > 0) {
        json.setCode(ErrorCodeEnum.SUBSCRIBE_LECTURE_REPEAT.getCode());
      }
    }
    return json;
  }

  /**
   * 
   * Title: 将pc的七种课程状态转换为微信列表中的三种<br>
   * Description:将pc的七种课程状态转换为微信列表中的三种<br>
   * CreateDate: 2016年10月14日 下午4:09:04<br>
   * 
   * @category 将pc的七种课程状态转换为微信列表中的三种
   * @author seven.gz
   * @param courseStatus
   * @return
   */
  private int translateToWeiXinListCourseStatus(int courseStatus) {
    int result = 0;
    // 不能预约说明课程进度小于20
    if (courseStatus == CourseConstant.COURSE_STATUS_CAN_NOT_SUBSCRIBE) {
      result = WeiXinSubscribeConstant.WEIXIN_COURSE_LIST_STATUS_LOCK;
    } else if (courseStatus == CourseConstant.COURSE_STATUS_SHOW
        || courseStatus == CourseConstant.COURSE_STATUS_NO_SHOW) {
      result = WeiXinSubscribeConstant.WEIXIN_COURSE_LIST_STATUS_COMPLETE;
    } else {
      result = WeiXinSubscribeConstant.WEIXIN_COURSE_LIST_STATUS_UNLOCK;
    }
    return result;
  }

  /**
   * Title: 按课程分组查询课程预约信息<br>
   * Description: 按课程分组查询课程预约信息<br>
   * CreateDate: 2016年10月13日 下午2:41:28<br>
   * 
   * @category 按课程分组查询课程预约信息
   * @author seven.gz
   * @param courseTypeMap
   *          用户合同有的课程类型
   * @return
   * @throws Exception
   */
  public List<LearningPathTypeInfoParm> findLearningPath(SessionUser sessionUser) throws Exception {
    // 用户拥有的课程类型
    Map<String, Object> courseTypeMap = sessionUser.getCourseTypes();
    // 用户id
    String userId = sessionUser.getKeyId();
    // 当前时间
    Date currentTime = new Date();

    List<LearningPathTypeInfoParm> courseTypeList = new ArrayList<LearningPathTypeInfoParm>();
    // 判断用户是否有oc课
    if (courseTypeMap.containsKey("course_type5")) {
      LearningPathTypeInfoParm courseTypeOc = new LearningPathTypeInfoParm();
      courseTypeOc.setCourseType("course_type5");
      // 升级所需要的课程数
      courseTypeOc.setLevelUpCourseCount(
          Integer.valueOf(MemcachedUtil.getConfigValue("oc_levelup_course_count")));
      // 已经上的课程数
      courseTypeOc.setCompleteCourseCount(
          subscribeCourseEntityDao.findCompleteCountByCourseType(userId, "course_type5",
              currentTime));
      courseTypeList.add(courseTypeOc);
    }

    // rsa进度列表
    List<Integer> rsaUnitList = new ArrayList<Integer>();
    // rsa完成数
    int rsaCompleteCount = 0;
    // core1v1进度列表
    List<Integer> core1v1UnitList = new ArrayList<Integer>();
    // core1v1完成数
    int core1v1CompleteCount = 0;
    // 查询学员1v1课程信息包含rsa信息
    List<CourseOne2OneParam> core1v1CourseList = courseOne2OneService
        .findCourseType1List(sessionUser);
    if (core1v1CourseList != null && core1v1CourseList.size() > 0) {
      for (CourseOne2OneParam courseOne2OneParam : core1v1CourseList) {
        // 判断rsa 状态
        // 不能预约说明课程进度小于20
        if (courseOne2OneParam
            .getCourseStatus() == CourseConstant.COURSE_STATUS_CAN_NOT_SUBSCRIBE) {
          rsaUnitList.add(WeiXinSubscribeConstant.WEIXIN_COURSE_LIST_STATUS_UNLOCK);
          core1v1UnitList.add(WeiXinSubscribeConstant.WEIXIN_COURSE_LIST_STATUS_LOCK);
        } else {
          // 不能预约说明课程进度大于20
          rsaUnitList.add(WeiXinSubscribeConstant.WEIXIN_COURSE_LIST_STATUS_COMPLETE);
          rsaCompleteCount++;
        }

        // 判断core1v1 状态
        int core1v1Status = translateToWeiXinListCourseStatus(courseOne2OneParam
            .getCourseStatus());
        core1v1UnitList.add(core1v1Status);
        if (WeiXinSubscribeConstant.WEIXIN_COURSE_LIST_STATUS_COMPLETE == core1v1Status) {
          core1v1CompleteCount++;
        }

      }
    }

    // 判断用户是否有rsa课件
    if (courseTypeMap.containsKey("course_type6")) {
      LearningPathTypeInfoParm courseTypeRsa = new LearningPathTypeInfoParm();
      courseTypeRsa.setCourseType("course_type6");
      // 升级所需要的课程数(和1v1一致,不做完rsa没法做1v1)
      courseTypeRsa.setLevelUpCourseCount(
          Integer.valueOf(MemcachedUtil.getConfigValue("one2one_levelup_course_count")));
      courseTypeRsa.setCompleteCourseCount(rsaCompleteCount);
      courseTypeRsa.setCourseUnitList(rsaUnitList);
      courseTypeList.add(courseTypeRsa);
    }

    // 判断用户是否有core1v1
    if (courseTypeMap.containsKey("course_type1")) {
      LearningPathTypeInfoParm courseTypeCore1v1 = new LearningPathTypeInfoParm();
      courseTypeCore1v1.setCourseType("course_type1");
      // 升级所需要的课程数
      courseTypeCore1v1.setLevelUpCourseCount(
          Integer.valueOf(MemcachedUtil.getConfigValue("one2one_levelup_course_count")));
      courseTypeCore1v1.setCompleteCourseCount(core1v1CompleteCount);
      courseTypeCore1v1.setCourseUnitList(core1v1UnitList);
      courseTypeList.add(courseTypeCore1v1);
    }

    // 判断用户是否有extension1v1
    if (courseTypeMap.containsKey("course_type9")) {
      // extension1v1进度列表
      List<Integer> extension1v1UnitList = new ArrayList<Integer>();
      // extension1v1完成数
      int extension1v1CompleteCount = 0;
      // 查询学员1v1课程信息包含rsa信息
      List<CourseOne2OneParam> extension1v1CourseList = courseExtension1v1Service
          .findCourseType9List(sessionUser);
      if (extension1v1CourseList != null && extension1v1CourseList.size() > 0) {
        for (CourseOne2OneParam courseOne2OneParam : extension1v1CourseList) {
          // 判断extension1v1 状态
          int extension1v1Status = translateToWeiXinListCourseStatus(courseOne2OneParam
              .getCourseStatus());
          extension1v1UnitList.add(extension1v1Status);
          if (WeiXinSubscribeConstant.WEIXIN_COURSE_LIST_STATUS_COMPLETE == extension1v1Status) {
            extension1v1CompleteCount++;
          }
        }

        LearningPathTypeInfoParm courseTypeExtension1v1 = new LearningPathTypeInfoParm();
        courseTypeExtension1v1.setCourseType("course_type9");
        // 升级所需要的课程数
        courseTypeExtension1v1.setLevelUpCourseCount(
            Integer.valueOf(MemcachedUtil.getConfigValue("one2one_levelup_course_count")));
        courseTypeExtension1v1.setCompleteCourseCount(extension1v1CompleteCount);
        courseTypeExtension1v1.setCourseUnitList(extension1v1UnitList);
        courseTypeList.add(courseTypeExtension1v1);
      }
    }

    // 判断用户是否有extension1v6
    if (courseTypeMap.containsKey("course_type2")) {
      LearningPathTypeInfoParm courseTypeExtension1v1 = new LearningPathTypeInfoParm();
      courseTypeExtension1v1.setCourseType("course_type2");
      // 升级所需要的课程数
      courseTypeExtension1v1.setLevelUpCourseCount(
          Integer.valueOf(MemcachedUtil.getConfigValue("one2many_levelup_course_count")));
      // 当前级别extension已上课程数
      int currentLevelOne2ManyCourseCount = adminSubscribeCourseDao.findCountByUserIdAndLevel(
          userId, sessionUser.getCurrentLevel(), currentTime, null, false);
      courseTypeExtension1v1.setCompleteCourseCount(currentLevelOne2ManyCourseCount);
      courseTypeList.add(courseTypeExtension1v1);

    }
    return courseTypeList;
  }

  /**
   * Title: 查找预约课程详情 <br>
   * Description: 查找预约课程详情及其评价相关信息 <br>
   * CreateDate: 2016年10月14日 下午3:15:06<br>
   * 
   * @category 查找预约课程详情
   * @author komi.zsy
   * @param subscribeId
   *          预约id
   * @return
   * @throws Exception
   */
  public SubscribeCourseAndCommentParam findASubscribeDetailBySubscribeId(String subscribeId,
      String userId) throws Exception {
    // 查找预约课程详情
    SubscribeCourseAndCommentParam returnObj = subscribeCourseEntityDao
        .findASubscribeDetailBySubscribeId(subscribeId);

    if (returnObj == null) {
      // 没有预约信息或预约已取消
      return null;
    }

    CourseType courseTypeObj = (CourseType) MemcachedUtil.getValue(returnObj.getCourseType());
    // 课程类型中文名
    returnObj.setCourseTypeChineseName(courseTypeObj.getCourseTypeChineseName());

    // 判断课程是否已上完
    if (returnObj.getEndTime().getTime() <= new Date().getTime()) {
      // 课程已上完
      if (returnObj.getCourseStatus() == SubscribeCourseConstant.WEIXIN_SUBSCRIBE_DETAIL_SHOW
          && returnObj.getIsAttend() == SubscribeCourseConstant.WEIXIN_SUBSCRIBE_DETAIL_SHOW) {
        // 课程出席，加载学生、老师评价
        List<CourseComment> courseCommentObjList = courseCommentEnityDao
            .findCommentBySubscribeid(subscribeId);
        if (courseCommentObjList != null && courseCommentObjList.size() != 0) {
          for (CourseComment CourseCommentObj : courseCommentObjList) {
            // 来源id为学生id的话，是学生评价，否则为老师评价
            if (userId.equals(CourseCommentObj.getFromUserId())) {
              // 学生评价
              // 专业度分数
              returnObj.setDeliveryScore(CourseCommentObj.getDeliveryScore());
              // 准备度分数
              returnObj.setPreparationScore(CourseCommentObj.getPreparationScore());
              // 互动性分数
              returnObj.setInteractionScore(CourseCommentObj.getInteractionScore());
              // 学生给老师评论平均分
              returnObj.setStudentShowScore(CourseCommentObj.getShowScore());
              // 学生的评论
              returnObj.setStudentCommentContent(CourseCommentObj.getCommentContent());
            } else {
              // 老师评价
              // 老师给学生评论平均分
              returnObj.setTeacherShowScore(CourseCommentObj.getShowScore());
              // 发音分数
              returnObj.setPronouncationScore(CourseCommentObj.getPronouncationScore());
              // 语法分数
              returnObj.setGrammerScore(CourseCommentObj.getGrammerScore());
              // 词汇量分数
              returnObj.setVocabularyScore(CourseCommentObj.getVocabularyScore());
              // 听力分数
              returnObj.setListeningScore(CourseCommentObj.getListeningScore());
              // 老师的评论
              returnObj.setTeacherCommentContent(CourseCommentObj.getCommentContent());
            }
          }
        }
      } else {
        // 已预约，未完成
        returnObj.setCourseStatus(SubscribeCourseConstant.WEIXIN_SUBSCRIBE_DETAIL_NOT_SHOW);
      }
    } else {
      // 课程未上完
      returnObj.setCourseStatus(SubscribeCourseConstant.WEIXIN_SUBSCRIBE_DETAIL_NOT_START);
    }

    return returnObj;
  }

  /**
   * Title: 根据用户id查询所有预约信息以及相应的学生评价信息<br>
   * Description: 根据用户id查询所有预约信息以及相应的学生评价信息<br>
   * CreateDate: 2016年10月14日 上午11:48:56<br>
   * 
   * @category 根据用户id查询所有预约信息以及相应的学生评价信息
   * @author komi.zsy
   * @param userId
   *          用户id
   * @param page
   *          页数
   * @param rows
   *          行数
   * @return
   * @throws Exception
   */
  public Page findSubscribeList(String userId, Integer page, Integer rows) throws Exception {
    // 根据用户id查询所有预约信息以及相应的学生评价信息
    Page pageObj = subscribeCourseEntityDao.findAllSubscribeCourseAndCommentByUserId(userId, page,
        rows);
    List<SubscribeCourseListParam> subscribeCourseListParamList = pageObj.getDatas();
    if (subscribeCourseListParamList != null && subscribeCourseListParamList.size() != 0) {
      // modified by alex+seven 2016年11月5日 16:58:57
      // 注意!类似于这种情况不要放到for循环里,会浪费内存.
      // 当前时间
      Date currentTime = new Date();

      // 处理数据
      for (SubscribeCourseListParam subscribeCourseListParam : subscribeCourseListParamList) {
        CourseType courseTypeObj = (CourseType) MemcachedUtil
            .getValue(subscribeCourseListParam.getCourseType());
        // 课程类型中文名
        subscribeCourseListParam.setCourseTypeChineseName(courseTypeObj.getCourseTypeChineseName());
        // 最大上课人数
        subscribeCourseListParam.setCourseTypeLimitNumber(courseTypeObj.getCourseTypeLimitNumber());
        long endTime = subscribeCourseListParam.getEndTime().getTime();
        // 处理预约状态（0.已完成已评价、1.已完成未评价（显示待评价标签）、2.已预约且当天开课（显示即将开课标签（当前时间到当天24点内））、3.已预约且非当天开课）
        if (endTime <= currentTime.getTime()) {
          if (subscribeCourseListParam
              .getSubscribeStatus() == AdminSubscribeCourseConstant.SUBSCRIBE_STATUS_SHOW
              && subscribeCourseListParam
                  .getIsAttend() == AdminSubscribeCourseConstant.SUBSCRIBE_STATUS_SHOW) {
            // 课程结束时间小于当前时间，课程已完成状态
            if (StringUtils.isEmpty(subscribeCourseListParam.getPreparationScore())) {
              // 评价为空
              subscribeCourseListParam
                  .setCourseStatus(SubscribeCourseConstant.WEIXIN_SUBSCRIBE_NOT_COMMENT);
            } else {
              // 已评价
              subscribeCourseListParam
                  .setCourseStatus(SubscribeCourseConstant.WEIXIN_SUBSCRIBE_COMMENT);
            }
          } else {
            // 未出席
            subscribeCourseListParam
                .setCourseStatus(SubscribeCourseConstant.WEIXIN_SUBSCRIBE_NO_SHOW);
          }

        } else {
          // 课程结束时间大于当前时间，还未上完课，课程已预约状态
          if (endTime <= (DateUtil.strToDateYYYYMMDD(DateUtil.dateToStrYYMMDD(currentTime))
              .getTime() + CourseConstant.ONE_DAY_MILLISECOND)) {
            // 结束时间且小于当日24点，为当天开课状态
            subscribeCourseListParam
                .setCourseStatus(SubscribeCourseConstant.WEIXIN_SUBSCRIBE_TODAY);
          } else {
            // 结束时间大于当天24点，为非当天开课状态
            subscribeCourseListParam
                .setCourseStatus(SubscribeCourseConstant.WEIXIN_SUBSCRIBE_AFTER_TODAY);
          }
        }
      }
    }
    return pageObj;

  }

  /**
   * Title: 查询合同信息接口<br>
   * Description: 查询合同信息接口<br>
   * CreateDate: 2016年10月13日 下午3:11:07<br>
   * 
   * @category 查询合同信息接口
   * @author komi.zsy
   * @param userId
   *          用户id
   * @param userPhone
   *          手机号
   * @return
   * @throws Exception
   */
  public List<OrderCourseAndOptionParam> findUserContractList(String userId, String userPhone)
      throws Exception {
    // 查找用户执行中和已过期的话合同
    List<OrderCourseAndOptionParam> orderCourseList = orderCourseEntityDao
        .findCompleteContractListByUserId(userId);
    if (orderCourseList != null && orderCourseList.size() != 0) {
      for (OrderCourseAndOptionParam orderCourse : orderCourseList) {
        // 根据合同id查询课程信息
        List<OrderCourseOptionParam> orderCourseOptionList = orderCourseOptionEntityDao
            .findDetailsOptionParamByOrderId(orderCourse.getKeyId());
        // 处理课程剩余有效期
        OrderContractStatusUtil.formatRemainCourseCount(orderCourseOptionList,
            orderCourse.getStartOrderTime());
        // 将课程信息list放入合同信息
        orderCourse.setOrderCourseOptionList(orderCourseOptionList);
        orderCourse.setUserPhone(userPhone);
      }
    }
    return orderCourseList;
  }

  /**
   * Title: 查询执行中合同信息接口<br>
   * Description: <br>
   * CreateDate: 2016年10月13日 下午3:11:07<br>
   * 
   * @category 查询执行中合同信息接口
   * @author alex
   * @param userId
   *          用户id
   * @param userPhone
   *          手机号
   * @return
   * @throws Exception
   */
  public List<OrderCourseAndOptionParam> findUserOnlyCompleteContractList(String userId,
      String userPhone) throws Exception {
    // 查找用户执行中和已过期的话合同
    List<OrderCourseAndOptionParam> orderCourseList = orderCourseEntityDao
        .findOnlyCompleteContractListByUserId(userId);
    if (orderCourseList != null && orderCourseList.size() != 0) {
      for (OrderCourseAndOptionParam orderCourse : orderCourseList) {
        // 根据合同id查询课程信息
        List<OrderCourseOptionParam> orderCourseOptionList = orderCourseOptionEntityDao
            .findDetailsOptionParamByOrderId(orderCourse.getKeyId());
        // 处理课程剩余有效期
        OrderContractStatusUtil.formatRemainCourseCount(orderCourseOptionList,
            orderCourse.getStartOrderTime());
        // 将课程信息list放入合同信息
        orderCourse.setOrderCourseOptionList(orderCourseOptionList);
        orderCourse.setUserPhone(userPhone);
      }
    }
    return orderCourseList;
  }

  /**
   * Title: 查询当天预约课程数<br>
   * Description: 查询当天预约课程数<br>
   * CreateDate: 2016年10月13日 下午4:39:54<br>
   * 
   * @category 查询当天预约课程数
   * @author komi.zsy
   * @param userId
   * @return
   * @throws Exception
   */
  public int findCurrentDayCourseInfo(String userId, Date startTime, Date endTime)
      throws Exception {
    // 根据学员查找当天还未结束课程
    return subscribeCourseEntityDao.findFutureSubscribeCourseNumByUserId(userId, startTime,
        endTime);
  }
}
