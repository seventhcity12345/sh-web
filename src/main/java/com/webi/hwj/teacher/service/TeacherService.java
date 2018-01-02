package com.webi.hwj.teacher.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.mingyisoft.javabase.util.SqlUtil;
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.course.dao.CourseCommentDao;
import com.webi.hwj.course.dao.CourseOne2ManySchedulingDao;
import com.webi.hwj.course.util.CourseUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.index.controller.validateForm.TeacherLoginValidationForm;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.teacher.dao.TeacherDao;
import com.webi.hwj.teacher.dao.TeacherEntityDao;
import com.webi.hwj.teacher.entity.Teacher;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TeacherService {
  @Resource
  TeacherDao teacherDao;
  @Resource
  CourseCommentDao courseCommentDao;
  @Resource
  SubscribeCourseDao subscribeCourseDao;
  @Resource
  CourseOne2ManySchedulingDao courseOne2ManySchedulingDao;

  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;
  @Resource
  TeacherEntityDao teacherEntityDao;
  
  
  /**
   * Title: 初始化session老师.<br>
   * Description: initSessionTeacher<br>
   * CreateDate: 2015年8月21日 下午2:32:33<br>
   * 
   * @category initSessionTeacher
   * @author yangmh
   * @param keyId
   *          教师主键
   */ 
  public SessionTeacher initSessionTeacher(String keyId) throws Exception {
    Teacher teacher = teacherEntityDao.findTeacherByKeyId(keyId);

    if (teacher == null) {
      throw new Exception("session教师不存在,keyId = " + keyId);
    }

    SessionTeacher sessionTeacher = new SessionTeacher();
    BeanUtils.copyProperties(sessionTeacher, teacher);

    // 课程类型
    String[] courseTypes = sessionTeacher.getTeacherCourseType().split(",");
    List<CourseType> courseTypeList = new ArrayList<CourseType>();
    if (courseTypes != null && courseTypes.length != 0 &&
        !"".equals(courseTypes[0])) {
      for (String courseType : courseTypes) {
        courseTypeList.add((CourseType) MemcachedUtil.getValue(courseType));
      }
    }
    sessionTeacher.setTeacherCourseTypeList(courseTypeList);
    String token = SqlUtil.createUUID();
    MemcachedUtil.setValue(token, sessionTeacher, 60 * 60);
    sessionTeacher.setToken(token);
    return sessionTeacher;
  } 

  /**
   * @category teacher 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return teacherDao.insert(fields);
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
    return teacherDao.findList(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @return
   * @throws Exception
   */
  public Page findPage(Map<String, Object> paramMap) throws Exception {
    return teacherDao.findPage(paramMap);
  }

  /**
   * @category 修改数据
   * @author mingyisoft代码生成工具
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return teacherDao.update(fields);
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
    return teacherDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过key,value)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(String key, String value, String columnName) throws Exception {
    return teacherDao.findOne(key, value, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return teacherDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return teacherDao.delete(ids);
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
    return teacherDao.findCount(map);
  }

  /**
   * @category 查询总数
   * @author mingyisoft代码生成工具
   * @param sumField
   *          sum的字段
   * @author vector.mjp
   * @checked
   */
  public int findSum(Map<String, Object> map, String sumField) throws Exception {
    return teacherDao.findSum(map, sumField);
  }

  // 提前5分钟可用！
  private boolean isWork(Date start_time, Date end_time, Integer canGoClassroomBeforeTime) {
    Date now = new Date();
    // 提前5分钟可用
    return (start_time.getTime() - canGoClassroomBeforeTime * 60 * 1000 <= now.getTime()
        && now.getTime() <= end_time.getTime());
  }

  /**
   * @category 更新预约已确认
   * @author vector.mjp
   * @checked
   */
  public boolean confirmSub(Map<String, Object> reqParams) throws Exception {
    return teacherDao.update(reqParams, "t_teacher_time") == 1;
  }

  /**
   * Title: findSmallLessonList<br>
   * Description: findSmallLessonList<br>
   * CreateDate: 2015年8月27日 下午4:53:01<br>
   * 
   * @category findSmallLessonList
   * @author vector.mjp
   * @param params
   * @throws Exception
   */
  // modify by athrun.cw 2015年10月30日16:41:39 改名为findSubscribeCourseDateList
  public Map<String, Object> findSubscribeCourseDateList(Map<String, Object> params)
      throws Exception {
    Map<String, Object> result = new HashMap<String, Object>();
    // 第一步 获取2周内排课的日期数据(可点击的日期)
    // 查询条件为当前时间
    Date curDate = new Date();
    // modify by seven 2016年7月19日16:57:10 开始时间向前推进两天，以便于老师可以评价之前两天的课程
    Date paramDate = new Date(curDate.getTime() - CourseUtil.FORWARD_COMMENT_TIME);
    // params.put("curDate", new Date());
    params.put("curDate", DateUtil.dateToStrYYMMDD(paramDate) + " 00:00:00");

    List<Map<String, Object>> pk_days = teacherDao.findList(TeacherDao.PAIKE_DAYS, params);// 处理为map
    if (pk_days.size() > 0) {

      // modify by seven 2016年7月19日16:57:10 开始时间向前推进两天，以便于老师可以评价之前两天的课程
      // 加上将当前天，为了前天会有点击事件
      Map<String, Object> todayMap = new HashMap<String, Object>();
      todayMap.put("dt", DateUtil.dateToStrYYMMDD(curDate));
      pk_days.add(todayMap);

      Map<String, Object> pk_dates = new HashMap<String, Object>();
      for (Map<String, Object> day : pk_days) {
        pk_dates.put(day.get("dt").toString(), true);// 日期
      }
      result.put("pk_dates", pk_dates);
    }

    // modify by seven 2016年7月19日16:57:10 开始时间向前推进两天，以便于老师可以评价之前两天的课程
    // params.put("curDay", DateUtil.dateToStrYYMMDD(new Date())+" 00:00:00");
    params.put("curDay", DateUtil.dateToStrYYMMDD(paramDate) + " 00:00:00");
    // 第二步 获取2周内排课的绿标+红标 CONFIRM_DAYS_SMALLPACK
    List<Map<String, Object>> days = teacherDao
        .findList(TeacherDao.CONFIRM_DAYS_SMALLPACK_RED_GREEN, params);// 处理为map
    if (days.size() > 0) {
      Map<String, Object> green_dates = new HashMap<String, Object>();
      Map<String, Object> red_dates = new HashMap<String, Object>();

      for (Map<String, Object> day : days) {
        if ((boolean) day.get("is_confirm")) {// 绿色图标数量
          green_dates.put(day.get("dt").toString(), day.get("ct"));// 日期
        } else {// 红色图标数量
          red_dates.put(day.get("dt").toString(), day.get("ct"));// 日期
        }
      }
      result.put("green_dates", green_dates);
      result.put("red_dates", red_dates);
    }

    return result;
  }

  /**
   * Title: 老师端加载预约数据列表<br>
   * Description: 老师端加载预约数据列表<br>
   * CreateDate: 2015年8月28日 上午10:22:14<br>
   * 
   * @category 老师端加载预约数据列表
   * @author vector.mjp
   * @param reqParams
   * @throws Exception
   */
  public List<Map<String, Object>> findSubscribeListByTeacherId(Map<String, Object> reqParams)
      throws Exception {
    reqParams.put("curDate", new Date());
    // 1.获取排课时间信息 :开始上课时间，截止时间，是否确认 iswork
    List<Map<String, Object>> subscribeCourseList = subscribeCourseDao
        .findSubscribeListByTeacherId(reqParams);

    List<Map<String, Object>> tempSubscribeCoursesList = new ArrayList<Map<String, Object>>();

    Map<String, Integer> one2ManySubscribeMap = new HashMap<String, Integer>();

    // 存放已经被预约的课程id
    List<String> subscribeCourseIdList = new ArrayList<String>();

    if (subscribeCourseList != null && subscribeCourseList.size() > 0) {
      for (Map<String, Object> subscribeCourseMap : subscribeCourseList) {
        /**
         * modified by alex 2016年12月5日 18:24:57 需要查询前一个预约过并上过的课 tom批准可以n+1.
         * 注意:只有1v1的课才有这套机制以及查询学员详细信息
         */

        if ("course_type1".equals(subscribeCourseMap.get("course_type").toString())) {
          SubscribeCourse subscribeCourse = subscribeCourseEntityDao
              .findLastSubscribeByKeyIdAndCourseTypeAndUserId(
                  subscribeCourseMap.get("user_id").toString(),
                  subscribeCourseMap.get("lesson_id").toString(),
                  subscribeCourseMap.get("course_type").toString());

          if (subscribeCourse != null) {
            subscribeCourseMap.put("lastSubscribeCourseTitle", subscribeCourse.getCourseTitle());
            subscribeCourseMap.put("lastSubscribeCourseType",
                ((CourseType) MemcachedUtil.getValue(subscribeCourse.getCourseType()))
                    .getCourseTypeEnglishName());
          }
        }

        /**
         * modified by alex.yang 2016年12月5日 16:43:24 前台页面需要显示当前课程类型的英文名字+当前级别+年龄
         */
        subscribeCourseMap.put("course_type_english_name",
            ((CourseType) MemcachedUtil
                .getValue(subscribeCourseMap.get("course_type").toString()))
                    .getCourseTypeEnglishName());

        String courseId = subscribeCourseMap.get("course_id").toString();
        /**
         * modified by alex.yang 2015年12月19日13:21:26，因为1vn需要去重。
         */
        // 如果是1vn课程，需要统计相同课程的学生报名数。
        /**
         * modified by komi 2016年7月5日16:55:02 增加ES课程判定
         */
        if ("course_type2".equals(subscribeCourseMap.get("course_type"))
            || "course_type8".equals(subscribeCourseMap.get("course_type"))
            || "course_type5".equals(subscribeCourseMap.get("course_type"))) {
          // 有重复预约记录，需要把count+1
          if (one2ManySubscribeMap.get(courseId) != null) {
            int subscribeCourseCount = (int) one2ManySubscribeMap.get(courseId);
            subscribeCourseCount++;
            one2ManySubscribeMap.put(courseId, subscribeCourseCount);
          } else {
            // 没有预约重复记录，count为1
            one2ManySubscribeMap.put(courseId, 1);
          }
        }

        // for(Map<String, Object> tempMap : tempSubscribeCoursesList){
        // if(tempMap.get("courseId") != null && subscribeCourseMap.get("flag")
        // != null ){
        // if(!(Boolean)subscribeCourseMap.get("flag")){
        // tempSubscribeCoursesList.add(subscribeCourseMap);
        // subscribeCourseMap.put("flag", true);
        // }
        // }else{
        // subscribeCourseMap.put("flag", false);
        // }
        // tempMap.put("subscribeCourseCount",
        // one2ManySubscribeMap.get(courseId));
        // }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        Date start_time = sdf.parse(subscribeCourseMap.get("start_time").toString());
        Date end_time = sdf.parse(subscribeCourseMap.get("end_time").toString());

        // 老师进教室提前x分钟
        subscribeCourseMap.put("is_work", isWork(start_time, end_time,
            ((CourseType) MemcachedUtil.getValue(subscribeCourseMap.get("course_type").toString()))
                .getCourseTypeBeforeGoclassTime()));

        // modify by ahtrun.cw
        // 添加是否已经评论的判断
        // Map<String, Object> commentParamMap = new HashMap<String, Object>();
        // commentParamMap.put("from_user_id",
        // subscribeCourseMap.get("teacher_id"));
        // commentParamMap.put("subscribe_course_id",
        // subscribeCourseMap.get("lesson_id"));
        // commentParamMap.put("to_user_id", subscribeCourseMap.get("user_id"));
        // 查找 老师教授 课程A && 学员A && 该教师
        // commentParamMap = courseCommentDao
        // .findTeacherCommentByCourseIdAndUserIdAndTeacherId(commentParamMap);
        // 没有评论 && 开课时间 <= 当前时间（已经上课了 || 开始上课，就可以评论了 ）
        // if (commentParamMap == null && start_time.getTime() <=
        // System.currentTimeMillis()) {

        // modify by seven 2016年12月26日20:18:31 允许老师多次修改评论
        if (start_time.getTime() <= System.currentTimeMillis()) {
          subscribeCourseMap.put("can_commented", true);
        }

        // 放入list便于后面查询，没有被预约的lecture课
        subscribeCourseIdList.add(courseId);
      }
      // 如果发现有1vn的课程，下面开始去重
      if (one2ManySubscribeMap.size() != 0) {
        for (Map<String, Object> subscribeCourseMap : subscribeCourseList) {
          String courseId = subscribeCourseMap.get("course_id").toString();

          Integer subscribeCount = one2ManySubscribeMap.get(courseId);

          // 1vn课程
          if (subscribeCount != null) {
            if (subscribeCount.intValue() == -1) {

            } else {
              subscribeCourseMap.put("subscribeCount", subscribeCount);
              // 最大预约人数
              subscribeCourseMap.put("limit_max_count",
                  ((CourseType) MemcachedUtil.getValue("course_type2")).getCourseTypeLimitNumber());
              one2ManySubscribeMap.put(courseId, -1);
              tempSubscribeCoursesList.add(subscribeCourseMap);
            }
          } else {
            // 其他课程
            tempSubscribeCoursesList.add(subscribeCourseMap);
          }
        }
        /**
         * modify by seven 2016年7月21日14:08:30 能显示被排了的但是没有被预约的lecture课
         */
        // 返回加上排了但是没有被预约的lecture
        findOne2ManySchedulingNotSubscribe(tempSubscribeCoursesList, subscribeCourseIdList,
            (String) reqParams.get("dt"), (String) reqParams.get("teacher_id"));
        return tempSubscribeCoursesList;
      }
    }
    /**
     * modify by seven 2016年7月21日14:08:30 能显示被排了的但是没有被预约的lecture课
     */
    // 返回加上排了但是没有被预约的lecture
    findOne2ManySchedulingNotSubscribe(subscribeCourseList, subscribeCourseIdList,
        (String) reqParams.get("dt"), (String) reqParams.get("teacher_id"));
    return subscribeCourseList;
  }

  /**
   * Title: findSmallPackConfirmInfos<br>
   * Description: 加载小包课已确认预约列表信息<br>
   * CreateDate: 2015年8月28日 上午10:22:39<br>
   * 
   * @category findSmallPackConfirmInfos
   * @author vector.mjp
   * @param reqParams
   * @throws Exception
   * 
   * 
   *           modify by athrun.cw 2015年10月30日17:45:10 无用代码，已经删除不用
   *//*
     * public List<Map<String, Object>> findSmallPackConfirmInfos(Map<String,
     * Object> reqParams) throws Exception { //1.获取排课时间信息 :开始上课时间，截止时间，是否确认
     * List<Map<String, Object>> confirminfors =
     * teacherDao.findList(TeacherDao.SMALLPACK_CONFIRMS, reqParams);
     * if(confirminfors==null){ confirminfors = new ArrayList<Map<String,
     * Object>>(); } return confirminfors; }
     */

  /**
   * 
   * Title: 将被拍了但是没有被预约的lecture课加入到返回结果上<br>
   * Description: 将被拍了但是没有被预约的lecture课加入到返回结果上<br>
   * CreateDate: 2016年7月19日 下午1:43:07<br>
   * 
   * @category 将被拍了但是没有被预约的lecture课加入到返回结果上
   * @author seven.gz
   * @param subscribeCoursesList
   * @param keyIds
   * @param selectDay
   * @param teacherId
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findOne2ManySchedulingNotSubscribe(
      List<Map<String, Object>> subscribeCoursesList,
      List<String> keyIds, String selectDay, String teacherId) throws Exception {
    // 查询没有被预约的lecture课程
    List<Map<String, Object>> one2ManySchedulingNotSubscribeList = courseOne2ManySchedulingDao
        .findOne2ManySchedulingNotSubscribe(keyIds, selectDay, teacherId);
    if (one2ManySchedulingNotSubscribeList != null
        && one2ManySchedulingNotSubscribeList.size() > 0) {
      for (Map<String, Object> schedulingNotSubscribe : one2ManySchedulingNotSubscribeList) {
        schedulingNotSubscribe.put("limit_max_count",
            ((CourseType) MemcachedUtil.getValue("course_type2")).getCourseTypeLimitNumber());
        // 预约人数是0
        schedulingNotSubscribe.put("subscribeCount", 0);

        schedulingNotSubscribe.put("course_type_english_name",
            ((CourseType) MemcachedUtil
                .getValue(schedulingNotSubscribe.get("course_type").toString()))
                    .getCourseTypeEnglishName());

        subscribeCoursesList.add(schedulingNotSubscribe);
      }
    }
    return subscribeCoursesList;
  }
  
  /**
   * Title: 老师登录.<br>
   * Description: userLogin<br>
   * CreateDate: 2017年2月20日 下午3:04:07<br>
   * 
   * @category 老师登录
   * @author yangmh
   * @param userLoginValidationForm
   *          用户登录校验对象
   * @param lastLoginIp
   *          最后登录ip
   */
  public SessionTeacher teacherLogin(TeacherLoginValidationForm teacherLoginValidationForm)
      throws Exception {

    // 通过帐号查询老师数据
    Teacher teacher = teacherEntityDao
        .findOneByAccountReturnPwd(teacherLoginValidationForm.getAccount());
    // 对比加密密码
    if (teacher != null
        && SHAUtil.encodeByDate(teacher.getPwd())
            .equals(teacherLoginValidationForm.getPwd())) {
      // 初始化session对象
      SessionTeacher sessionTeacher = initSessionTeacher(teacher.getKeyId());
      return sessionTeacher;
    } else {
      // 登录失败
      return null;
    }
  }
  
}