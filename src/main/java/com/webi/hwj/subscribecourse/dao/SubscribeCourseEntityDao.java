package com.webi.hwj.subscribecourse.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.courseone2many.param.CourseOne2ManySchedulingParam;
import com.webi.hwj.coursetype.constant.CourseTypeConstant;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.subscribecourse.param.CourseSubscribeAndTeacherInfoParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentCountParam;
import com.webi.hwj.subscribecourse.param.CourseTypeCommentListParam;
import com.webi.hwj.subscribecourse.param.FindListfinishLevelParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseCountParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseAndCommentParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseCountParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseListParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseDetailParam;
import com.webi.hwj.subscribecourse.param.SubscribleCourseParam;

@Repository
public class SubscribeCourseEntityDao extends BaseEntityDao<SubscribeCourse> {

  private static final String FIND_ONE_BY_KEY_ID_ON_WEBEX =
      " SELECT key_id,user_phone,user_name,course_type,start_time,teacher_time_id,course_title,user_id FROM t_subscribe_course WHERE key_id = :keyId ";

  // 查询当前用户的每一节课程的最新一条预约记录
  private static final String FIND_LAST_SUBSCRIBE_COURSE_LIST =
      "SELECT sc_demo.course_id, sc_demo.start_time, sc_demo.end_time, "
          + " sc_demo.key_id, sc_demo.subscribe_status"
          + " FROM ( "
          + " SELECT sc.course_id, sc.start_time, sc.end_time, sc.key_id, sc.subscribe_status"
          + " FROM t_subscribe_course sc "
          + " LEFT JOIN t_teacher t ON sc.teacher_id = t.key_id AND t.is_used <> 0 "
          + " WHERE sc.user_id = :userId AND sc.is_used = 1 AND sc.course_type = :courseType "
          + " ORDER BY sc.create_date DESC "
          + " ) AS sc_demo GROUP BY sc_demo.course_id";

  /**
   * 学员首页，课程表中的数据
   */
  private static final String FIND_SUBSCRIBE_COURSE_BY_USERID_AND_ENDTIME =
      " SELECT sc.key_id, sc.user_id, sc.teacher_time_id, "
          + " sc.course_id, sc.course_type, sc.course_title, sc.course_pic, sc.teacher_name, sc.course_courseware, sc.teacher_id, "
          + " sc.subscribe_status, sc.start_time, sc.end_time, t.teacher_photo "
          + " FROM t_subscribe_course sc "
          + " LEFT JOIN t_teacher t ON sc.teacher_id = t.key_id "
          + " WHERE t.is_used <> 0 "
          + " AND sc.is_used <> 0 "
          + " AND sc.end_time > :endTime"
          + " AND sc.user_id = :userId "
          + " ORDER BY sc.start_time ASC ";

  /**
   * 查询课程预约表里的上过课的数据（如果noshow的数据不查出来,老师已删除的课程也查出来） modified by 2016年12月26日
   * 19:24:30 首页的课程回顾需要过滤老师未出席的课程，老师未出席就不显示了
   * 
   * @author komi.zsy
   */
  private static final String FIND_COMPLETE_SUBSCRIBE_COURSE_BY_USERID =
      "SELECT tsc.key_id, tsc.course_type, tsc.course_title,"
          + "tsc.course_pic, tsc.teacher_id,tsc.start_time,tsc.course_courseware,tsc.end_time,tsc.teacher_name,"
          + " tt.teacher_photo"
          + " FROM t_subscribe_course tsc"
          + " LEFT JOIN t_teacher tt"
          + " ON tt.key_id = tsc.teacher_id "
          + " LEFT JOIN t_teacher_time ttt "
          + " ON ttt.key_id = tsc.teacher_time_id "
          + " WHERE tsc.is_used <> 0  AND tsc.subscribe_status = 1 AND ttt.is_attend = 1 "
          + " AND tsc.user_id = :userId"
          + " ORDER BY tsc.start_time DESC ";

  /**
   * 根据学员查找当天还未结束课程
   * 
   * @author komi.zsy
   */
  private static final String FIND_FUTURE_SUBSCRIBE_COURSE_BY_USERID = " SELECT COUNT(1)"
      + " FROM t_subscribe_course"
      + " WHERE user_id = :userId"
      + " AND end_time > :startTime AND end_time <= :endTime"
      + " AND is_used = 1";

  /**
   * 根据用户id查询所有预约信息以及相应的学生评价信息 modified by alex 2016年12月26日 19:46:27
   * 添加查询出老师是否已出席
   * 
   * @author komi.zsy
   */
  private static final String FIND_ALL_SUBSCRIBE_COURSE_AND_COMMENT_BY_USERID =
      "SELECT tsc.`key_id`,"
          + " tsc.`start_time`,tsc.`end_time`,tsc.`course_title`,tsc.`course_type`, "
          + " tcc.`preparation_score`,tsc.subscribe_status,ttt.is_attend "
          + " FROM `t_subscribe_course` tsc"
          + " LEFT JOIN `t_course_comment` tcc"
          + " ON tsc.key_id = tcc.subscribe_course_id AND tcc.`from_user_id` = :userId"
          + " LEFT JOIN `t_teacher_time` ttt"
          + " ON tsc.teacher_time_id = ttt.key_id "
          + " WHERE tsc.is_used = 1 "
          + " AND tsc.`user_id` = :userId"
          + " ORDER BY start_time DESC";

  /**
   * 查找预约课程详情 modified by alex 2016年12月26日 20:13:32 查询出老师是否已出席
   * 
   * @author komi.zsy
   */
  private static final String FIND_SUBSCRIBE_DETAIL_BY_SUBSCRIBEID =
      "SELECT tsc.`key_id`,tsc.`course_type`,"
          + "tsc.`course_title`,tsc.`course_courseware`,tsc.`course_pic`,"
          + "tsc.`start_time`,tsc.`end_time`,tsc.`subscribe_status` AS course_status,tsc.`teacher_time_id`,"
          + "tsc.`teacher_id`,tsc.`teacher_name`,tt.teacher_photo,tt.teacher_nationality,ttt.is_attend "
          + " FROM `t_subscribe_course` tsc"
          + " LEFT JOIN `t_teacher` tt"
          + " ON tt.key_id = tsc.`teacher_id`"
          + " LEFT JOIN `t_teacher_time` ttt"
          + " ON ttt.key_id = tsc.`teacher_time_id`"
          + " WHERE tsc.is_used = 1"
          + " AND tsc.key_id = :keyId ";

  /**
   * 根据课程类型+预约id+用户id查询上一条预约数据.
   * 
   * @author yangmh
   */
  private final static String FIND_LAST_SUBSCRIBE_BY_KEYID_AND_COURSETYPE_AND_USERID =
      "SELECT course_title,course_type "
          + " FROM t_subscribe_course  "
          + " WHERE user_id = :userId "
          + " AND subscribe_status = 1 "
          + " AND key_id <> :keyId "
          + " AND course_type = :courseType "
          + " ORDER BY create_date DESC  "
          + " LIMIT 1 ";

  /**
   * 1v1待评价课程（按tom要求，从此功能上线一天开始计算，所以sql写死了一个开始日期）
   * 
   * @author komi.zsy
   */
  private static final String FIND_SUBSCRIBE_COURSE_BY_NOT_COMMENT =
      "SELECT tsc.key_id,tsc.course_type,tsc.course_title,"
          + "tsc.teacher_name,tsc.start_time,  tsc.end_time"
          + " FROM  `t_subscribe_course` tsc "
          + " LEFT JOIN `t_course_comment` tcc "
          + " ON tcc.subscribe_course_id = tsc.key_id  AND tcc.from_user_id = tsc.user_id "
          + " LEFT JOIN t_teacher_time ttt "
          + " ON ttt.key_id = tsc.teacher_time_id "
          + " LEFT JOIN t_course_type tct"
          + " ON tct.course_type = tsc.course_type"
          + " WHERE tsc.is_used = 1  AND tct.is_used = 1"
          // modify by seven 2017年4月19日10:05:03 修改使用course_type_flag 和 排课地方冲突问题
          // 修改为固定的course_type
          + " AND tsc.course_type IN ('course_type1','course_type9') "
          + " AND tsc.subscribe_status = 1 AND ttt.is_attend = 1 "
          + " AND (tcc.preparation_score IS NULL OR tcc.preparation_score = '' ) "
          + " AND tsc.start_time > '2016-12-16'"
          + " AND tsc.user_id = :userId"
          + " ORDER BY tsc.start_time ASC ";

  /*
   * 根据courseid和userid 查询预约信息
   */
  private static final String FIND_SUBSCRIBE_LIST_BY_USERID_AND_COURSE_IDS_AND_DATE =
      "SELECT b.subscribe_status, b.key_id ,b.course_id "
          + " FROM t_subscribe_course b "
          + " WHERE b.user_id = :userId and DATE(b.start_time) = DATE(:startTime) AND b.is_used = 1 AND b.course_id IN(:courseIds) ";

  /**
   * 
   * 查询这个时间是否有预约
   */
  public static final String FIND_ALLTYPE_USERCOURSE_BY_USERID_ANDSTARTTIME_ANDENDTIME = " SELECT 1"
      + " FROM t_subscribe_course "
      + " WHERE is_used =1 "
      + " AND user_id = :userId AND ( "
      + "(:startTime >= start_time AND :startTime <= end_time) "
      + "OR (:endTime >= start_time AND :endTime <= end_time) "
      + "OR (:startTime <= start_time AND :endTime >= end_time) ) ";

  /**
   * 取消预约
   */
  private final static String UPDATE_SUBSCRIBEUSERID_BY_USERID = "UPDATE t_subscribe_course "
      + " SET update_user_id =:updateUserId , is_used = :isUsed"
      + " WHERE key_id =:keyId ";

  /**
   * 
   * 按日期查询所有预约信息
   * 
   * @author komi.zsy
   */
  private static final String FIND_SUBSCRIBE_COURSE_BY_DATE =
      "SELECT key_id,user_id,course_title,start_time,course_type"
          + " FROM t_subscribe_course"
          + " WHERE is_used = 1 AND"
          + " DATE(start_time) = :startTime ";

  /**
   * 根据时间查询所有课程类型的已完成已出席未评价预约信息 modified by alex 2016年12月26日 20:46:38
   * 老师未出席的课程不被查询出来
   * 
   * @author komi.zsy
   */
  private static final String FIND_ALL_SUBSCRIBE_COURSE_BY_NOT_COMMENT =
      "SELECT tsc.key_id,tsc.user_id,"
          + "tsc.course_title,tsc.start_time,tsc.course_type"
          + " FROM t_subscribe_course tsc"
          + " LEFT JOIN t_course_comment tcc"
          + " ON tcc.subscribe_course_id = tsc.key_id AND tcc.is_used = 1 AND tsc.user_id = tcc.from_user_id"
          + " LEFT JOIN t_teacher_time ttt"
          + " ON ttt.key_id = tsc.teacher_time_id "
          + " WHERE tsc.is_used = 1 AND tsc.end_time = :endTime "
          + " AND (tcc.delivery_score  = '' OR tcc.delivery_score IS NULL) AND tsc.subscribe_status = 1 AND ttt.is_attend = 1 ";

  /**
   * 参数：传入当前用户的userId、本月的开始日期、本月的结束日期;<br>
   * 返回值：查询本月有预约(过)课程的日期以及当天预约的课程数;
   * 
   * @author felix.yl
   */
  private final static String FIND_SUBSCRIBE_COURSE_DATE_AND_NUMBER = " SELECT COUNT(1) AS 'count',"
      + " DATE(tsc.start_time) AS 'subscribleDate'"
      + " FROM t_subscribe_course tsc"
      + " LEFT JOIN t_course_type tct"
      + " ON tsc.course_type=tct.course_type"
      + " WHERE tsc.user_id= :userId"
      + " AND tsc.start_time>= :startTime"
      + " AND tsc.start_time<= :endTime"
      + " AND tsc.is_used <> 0"
      + " AND tct.course_type <> 'course_type4'"
      + " GROUP BY DATE(tsc.start_time)";

  /**
   * 参数：传入当前用户的userId、当天日期;<br>
   * 返回值：查询展示学员在某天已预约的所有课程以及老师相关信息(大课);
   * 
   * @author felix.yl
   */
  private final static String FIND_SUBSCRIBE_COURSE_DETAIL_ONE_TO_MANY = " SELECT"
      + " tsc.key_id,"
      + " tsc.teacher_time_id,"
      + " tsc.course_type,"
      + " tsc.course_pic,"
      + " tsc.course_title,"
      + " tsc.start_time,"
      + " tsc.end_time,"
      + " tsc.course_courseware,"
      + " tcos.course_desc,"
      + " tt.teacher_photo,"
      + " tt.teacher_name,"
      + " tt.teacher_nationality,"
      + " ttt.teacher_time_platform,"
      + " tct.course_type_limit_number,"
      + " tct.course_type_before_goclass_time,"
      + " tct.course_type_cancel_subscribe_time,"
      + " tct.course_type_chinese_name,"
      + " tsc.subscribe_status"
      + " FROM t_subscribe_course tsc"
      + " LEFT JOIN t_teacher tt"
      + " ON tsc.teacher_id=tt.key_id"
      + " LEFT JOIN t_course_one2many_scheduling tcos"
      + " ON tsc.course_id=tcos.key_id"
      + " LEFT JOIN t_course_type tct"
      + " ON tsc.course_type=tct.course_type"
      + " LEFT JOIN t_teacher_time ttt"
      + " ON ttt.key_id=tsc.teacher_time_id"
      + " WHERE tct.course_type_flag = " + CourseTypeConstant.COURSE_TYPE_FLAG_ONE_TO_MANY
      + " AND tsc.user_id=:userId"
      + " AND tsc.start_time>= :currentStartTime"
      + " AND tsc.start_time<=:currentEndTime"
      + " AND tsc.is_used<>0"
      + " AND tt.is_used<>0"
      + " AND tcos.is_used<>0"
      + " AND tct.is_used<>0"
      + " ORDER BY tsc.start_time ASC";

  /**
   * 参数：传入当前用户的userId、当天日期;<br>
   * 返回值：查询展示学员在某天已预约的所有课程以及老师相关信息(小课)
   * 
   * @author felix.yl
   */
  private final static String FIND_SUBSCRIBE_COURSE_DETAIL_ONE_TO_ONE = " SELECT"
      + " tsc.key_id,"
      + " tsc.teacher_time_id,"
      + " tsc.course_type,"
      + " tsc.course_pic,"
      + " tsc.course_title,"
      + " tsc.start_time,"
      + " tsc.end_time,"
      + " tsc.course_courseware,"
      + " tt.teacher_photo,"
      + " tt.teacher_name,"
      + " tt.teacher_nationality,"
      + " ttt.teacher_time_platform,"
      + " tct.course_type_limit_number,"
      + " tct.course_type_before_goclass_time,"
      + " tct.course_type_cancel_subscribe_time,"
      + " tct.course_type_chinese_name,"
      + " tsc.subscribe_status"
      + " FROM t_subscribe_course tsc"
      + " LEFT JOIN t_teacher tt"
      + " ON tsc.teacher_id=tt.key_id"
      + " LEFT JOIN t_course_type tct"
      + " ON tsc.course_type=tct.course_type"
      + " LEFT JOIN t_teacher_time ttt"
      + " ON ttt.key_id=tsc.teacher_time_id"
      + " WHERE tct.course_type_flag = " + CourseTypeConstant.COURSE_TYPE_FLAG_1V1
      + " AND tsc.user_id=:userId"
      + " AND tsc.start_time>= :currentStartTime"
      + " AND tsc.start_time<=:currentEndTime"
      + " AND tsc.is_used<>0"
      + " AND tt.is_used<>0"
      + " AND tct.is_used<>0"
      + " AND tct.course_type <> 'course_type4'"
      + " ORDER BY tsc.start_time ASC";

  /*
   * 查询学员已完成的课程预约数
   */
  private static final String FIND_COUNT_FINISH_COURSE_TYPE =
      " SELECT course_type,COUNT(1) AS courseCount "
          + " FROM t_subscribe_course WHERE user_id = :userId "
          + " AND is_used = 1 AND end_time < :endTime GROUP BY course_type";

  /**
   * 传入参数：用户userId、courseType;<br>
   * 返回：查询课程预约表,当前用户对应的课程类型,课程结束时间大于等于参数时间的所有预约信息
   * 
   * @author felix.yl
   */
  private static final String FIND_SUBSCRIBE_LIST_BY_USERID_AND_COURSETYPE_AND_DATE =
      " SELECT key_id,course_id"
          + " FROM t_subscribe_course"
          + " WHERE user_id = :userId"
          + " AND course_type = :courseType"
          + " AND end_time >= :endTime"
          + " AND is_used = 1";

  /**
   * 传入参数：当前时间、用户的userId;<br>
   * 返回：查询返回该用户已经完成的所有课程类型以及每种课程类型对应的数目;
   * 
   * @author felix.yl
   */
  public static final String FIND_COURSE_COUNT_BY_COURSE_TYPE = " SELECT"
      + " course_type,"
      + " COUNT(1) AS commentCount"
      + " FROM t_subscribe_course"
      + " WHERE start_time < :currentTime"
      + " AND user_id = :userId"
      + " AND is_used = 1"
      + " AND course_type <> 'course_type4'"
      + " GROUP BY course_type";

  /**
   * 传入参数：当前时间、用户userId、课程类型<br>
   * 返回：分页查询返回课程预约表相关信息
   * 
   * @author felix.yl
   */
  public static final String FIND_COURSE_LIST_BY_COURSE_TYPE = " SELECT"
      + " tsc.key_id,"
      + " tsc.teacher_id,"
      + " tsc.course_title,"
      + " tsc.course_type,"
      + " tsc.subscribe_status,"
      + " tsc.start_time,"
      + " tsc.end_time,"
      + " tsc.teacher_name,"
      + " tsc.user_id"
      + " FROM t_subscribe_course tsc "
      + " WHERE tsc.is_used = 1 "
      + " AND tsc.start_time < :currentTime "
      + " AND tsc.user_id = :userId"
      + " AND tsc.course_type = :courseType"
      + " ORDER BY tsc.start_time DESC";

  /**
   * 根据学员和课程类型查询预约列表
   */
  public static final String FIND_LIST_BY_USER_ID_AND_COURSETYPE =
      " SELECT key_id,course_title,start_time,end_time,teacher_time_id FROM t_subscribe_course "
          + " WHERE user_id = :userId AND course_type = :courseType AND end_time > :endTime AND is_used = 1";

  /**
   * 传入参数：预约Id<br>
   * 返回：查询返回预约相关信息、老师相关信息、上课平台信息
   * 
   * @author felix.yl
   */
  private static final String FIND_SUBSCRIBE_TEACHER_PLATFORM_INFO_BY_SUBSCRIBE_ID = " SELECT"
      + " tsc.teacher_time_id,"
      + " tsc.course_courseware,"
      + " ttt.teacher_time_platform,"
      + " tt.teacher_name,"
      + " tt.teacher_photo"
      + " FROM t_subscribe_course tsc"
      + " LEFT JOIN t_teacher tt"
      + " ON tsc.teacher_id=tt.key_id"
      + " LEFT JOIN t_teacher_time ttt"
      + " ON ttt.key_id=tsc.teacher_time_id"
      + " WHERE tsc.key_id=:subscribeCourseId"
      + " AND tsc.is_used = 1"
      + " AND tt.is_used = 1";

  /**
   * 
   * 该学员某个级别中含有9个已完成已出席的不同的Core课程
   * 
   * @author seven.gz
   */
  public static final String FIND_LIST_FINISH_LEVEL =
      " SELECT tsc.user_level,MAX(tsc.end_time) AS end_time "
          + " FROM t_subscribe_course tsc "
          + " LEFT JOIN t_tellmemore_instructional tti ON tsc.user_level = tti.instructional_name "
          + " WHERE tsc.user_id = :userId "
          + " AND tsc.end_time < :endTime "
          + " AND tsc.user_level != '' "
          + " AND tsc.is_used =1  "
          + " AND tsc.course_type = 'course_type1'  "
          + " AND tsc.subscribe_status = 1 "
          + " GROUP BY tsc.user_level"
          + " HAVING COUNT(DISTINCT tsc.course_id) >= 9 "
          + " ORDER BY tti.instructional_id DESC";

  /**
   * 参数：传入学员的用户id<br>
   * 返回：查询最近一条没有查看老师评论的预约
   * 
   * @author felix.yl
   */
  private static final String STUDENT_FIND_NO_LOOK_COMMENT =
      " SELECT tsc.key_id,"
          + " tsc.start_time,"
          + " tsc.course_type,"
          + " tsc.user_id "
          + " FROM t_subscribe_course tsc "
          + " LEFT JOIN t_course_comment tcc "
          + " ON tsc.key_id = tcc.subscribe_course_id "
          + " AND tcc.preparation_score = '' "
          + " WHERE tsc.is_used = 1 "
          + " AND user_id = :userId "
          + " ORDER BY start_time DESC ";

  /**
   * 查询指定预约已过上课时间倒叙排名
   * 
   * @author felix.yl
   */
  private static final String FIND_NUMBER_BY_SUBSCRIBEID =
      " SELECT COUNT(1) "
          + " FROM t_subscribe_course "
          + " WHERE is_used = 1 "
          + " AND start_time >= :startTime "
          + " AND start_time < :endTime "
          + " AND user_id = :userId "
          + " AND course_type = :courseType "
          + " ORDER BY start_time DESC ";
  /**
   * 查询已预约且出席的1v1课程节数
   * 
   * @author felix.yl
   */
  private static final String FIND_COUNT_ONE_TO_ONE_COURSE =
      " SELECT COUNT(1) AS oneToOneCountCourse"
          + " FROM t_subscribe_course tsc"
          + " WHERE tsc.user_id=:userId"
          + " AND tsc.subscribe_status=1"
          + " AND tsc.course_type IN ('course_type1','course_type9')"
          + " AND Date(tsc.start_time)>=Date(:currentStartTime)"
          + " AND Date(tsc.start_time)<=Date(:currentEndTime)"
          + " AND tsc.is_used=1";

  /**
   * 查询已预约且出席的1vn课程节数
   * 
   * @author felix.yl
   */
  private static final String FIND_COUNT_ONE_TO_MANY_COURSE =
      " SELECT COUNT(1) AS oneToManyCountCourse"
          + " FROM t_subscribe_course tsc"
          + " WHERE tsc.user_id=:userId"
          + " AND tsc.subscribe_status=1"
          + " AND tsc.course_type IN ('course_type2','course_type5','course_type8')"
          + " AND Date(tsc.start_time)>=Date(:currentStartTime)"
          + " AND Date(tsc.start_time)<=Date(:currentEndTime)"
          + " AND tsc.is_used=1";

  /**
   * 根据预约id查询课程和老师相关信息
   * 
   * @author komi.zsy
   */
  private static final String FIND_COURSE_AND_TEACHER_INFO_BY_SUBSCRIBE_ID =
      "SELECT tsc.key_id AS subscribe_id ,"
          + "tsc.teacher_time_id,tsc.user_id,tsc.invite_url AS student_url,tsc.course_title,tsc.course_type,tsc.start_time,"
          + "tsc.end_time,tsc.course_pic,tsc.course_id,tsc.teacher_name,tt.teacher_photo,ttt.room_id "
          + " FROM t_subscribe_course tsc"
          + " LEFT JOIN t_teacher tt"
          + " ON tt.key_id = tsc.teacher_id"
          + " LEFT JOIN t_teacher_time ttt"
          + " ON tsc.teacher_time_id = ttt.key_id"
          + " WHERE tt.is_used = 1 AND tsc.is_used = 1"
          + " AND tsc.key_id = :subscribeId";

  /**
   * Title: 根据预约id查询课程和老师相关信息<br>
   * Description: 根据预约id查询课程和老师相关信息<br>
   * CreateDate: 2017年8月25日 下午5:39:05<br>
   * 
   * @category 根据预约id查询课程和老师相关信息
   * @author komi.zsy
   * @param subscribeId
   *          预约id
   * @return
   * @throws Exception
   */
  public CourseOne2ManySchedulingParam findCourseAndTeacherInfoBySubscribeId(String subscribeId)
      throws Exception {
    CourseOne2ManySchedulingParam paramObj = new CourseOne2ManySchedulingParam();
    paramObj.setSubscribeId(subscribeId);
    return super.findOne(FIND_COURSE_AND_TEACHER_INFO_BY_SUBSCRIBE_ID, paramObj);
  }

  /**
   * Title: 根据时间查询所有课程类型的未评价预约信息<br>
   * Description: 根据时间查询所有课程类型的未评价预约信息<br>
   * CreateDate: 2016年12月20日 下午6:33:09<br>
   * 
   * @category 根据时间查询所有课程类型的未评价预约信息
   * @author komi.zsy
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findAllSubscribeCourseByNotComment(Date endTime) throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setEndTime(endTime);
    return super.findList(FIND_ALL_SUBSCRIBE_COURSE_BY_NOT_COMMENT, paramObj);
  }

  /**
   * Title: 按日期查询所有预约信息<br>
   * Description: 按日期查询所有预约信息<br>
   * CreateDate: 2016年12月20日 下午4:24:07<br>
   * 
   * @category 按日期查询所有预约信息
   * @author komi.zsy
   * @param startTime
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findSubscribeCourseByDate(Date startTime) throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setStartTime(startTime);
    return super.findList(FIND_SUBSCRIBE_COURSE_BY_DATE, paramObj);
  }

  /**
   * * Title: 待评价列表<br>
   * Description: 待评价列表<br>
   * CreateDate: 2016年12月12日 下午3:34:52<br>
   * 
   * @category 待评价列表
   * @author komi.zsy
   * @param userId
   *          用户id
   * @return
   * @throws Exception
   */
  public List<SubscribeCourseListParam> findNotCommentList(String userId) throws Exception {
    SubscribeCourseListParam paramObj = new SubscribeCourseListParam();
    paramObj.setUserId(userId);
    return super.findList(FIND_SUBSCRIBE_COURSE_BY_NOT_COMMENT, paramObj);
  }

  /**
   * Title: 查找预约课程详情<br>
   * Description: 查找预约课程详情<br>
   * CreateDate: 2016年10月14日 下午3:11:26<br>
   * 
   * @category 查找预约课程详情
   * @author komi.zsy
   * @param subscribeId
   *          预约表id
   * @return
   * @throws Exception
   */
  public SubscribeCourseAndCommentParam findASubscribeDetailBySubscribeId(String subscribeId)
      throws Exception {
    SubscribeCourseAndCommentParam paramObj = new SubscribeCourseAndCommentParam();
    paramObj.setKeyId(subscribeId);
    return super.findOne(FIND_SUBSCRIBE_DETAIL_BY_SUBSCRIBEID, paramObj);
  }

  /**
   * Title: 根据用户id查询所有预约信息以及相应的学生评价信息<br>
   * Description: 根据用户id查询所有预约信息以及相应的学生评价信息<br>
   * CreateDate: 2016年10月14日 上午11:43:09<br>
   * 
   * @category 根据用户id查询所有预约信息以及相应的学生评价信息
   * @author komi.zsy
   * @param userId
   * @return
   * @throws Exception
   */
  public Page findAllSubscribeCourseAndCommentByUserId(String userId, Integer page, Integer rows)
      throws Exception {
    SubscribeCourseListParam paramObj = new SubscribeCourseListParam();
    paramObj.setUserId(userId);
    return super.findPage(FIND_ALL_SUBSCRIBE_COURSE_AND_COMMENT_BY_USERID, paramObj, page, rows);
  }

  /**
   * Title: 根据学员查找当天还未结束课程<br>
   * Description: 根据学员查找当天还未结束课程<br>
   * CreateDate: 2016年10月13日 下午4:39:04<br>
   * 
   * @category 根据学员查找当天还未结束课程
   * @author komi.zsy
   * @param userId
   *          用户id
   * @param startTime
   *          当前时间
   * @param endTime
   *          当天24点
   * @return
   * @throws Exception
   */
  public int findFutureSubscribeCourseNumByUserId(String userId, Date startTime, Date endTime)
      throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setUserId(userId);
    paramObj.setStartTime(startTime);
    paramObj.setEndTime(endTime);
    return super.findCount(FIND_FUTURE_SUBSCRIBE_COURSE_BY_USERID, paramObj);

  }

  /**
   * 查询学员已完成的课程数
   */
  private static final String FIND_COMPLETE_COUNT_BY_COURSE_TYPE =
      " SELECT COUNT(1) FROM t_subscribe_course "
          + " WHERE end_time < :endTime AND is_used = 1 AND user_id = :userId AND course_type = :courseType ";

  /**
   * 根据老师时间id，更新老师信息
   * 
   * @author komi.zsy
   */
  private static final String UPDATE_TEACHER_INFO_BY_TEACHER_TIME_ID = "UPDATE `t_subscribe_course`"
      + " SET teacher_id = :teacherId,teacher_name = :teacherName"
      + " WHERE teacher_time_id = :teacherTimeId AND is_used = 1";

  public int updateTeacherInfoByTeacherTimeId(String teacherTimeId, String teacherId,
      String teacherName) throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setTeacherTimeId(teacherTimeId);
    paramObj.setTeacherId(teacherId);
    paramObj.setTeacherName(teacherName);
    return super.update(UPDATE_TEACHER_INFO_BY_TEACHER_TIME_ID, paramObj);
  }

  /**
   * Title: 查询课程预约表里的上过课的数据<br>
   * Description: 查询课程预约表里的上过课的数据（如果noshow的数据不查出来,老师已删除的课程也查出来）<br>
   * CreateDate: 2016年9月21日 上午11:03:47<br>
   * 
   * @category 查询课程预约表里的上过课的数据
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
  public Page findCompleteSubscribeCourseByUserId(String userId, Integer page, Integer rows)
      throws Exception {
    SubscribeCourseParam paramObj = new SubscribeCourseParam();
    paramObj.setUserId(userId);
    return super.findPage(FIND_COMPLETE_SUBSCRIBE_COURSE_BY_USERID, paramObj, page, rows);
  }

  /**
   * Title: 学员还未上课的预约课程列表<br>
   * Description: 学员还未上课的预约课程列表<br>
   * CreateDate: 2016年9月20日 下午4:02:07<br>
   * 
   * @category 学员还未上课的预约课程列表
   * @author komi.zsy
   * @param userId
   * @param currentDate
   * @return
   * @throws Exception
   */
  public List<SubscribeCourseParam> findSubscribeCourseByUserIdAndCurrentDate(String userId,
      Date currentDate) throws Exception {
    SubscribeCourseParam paramObj = new SubscribeCourseParam();
    paramObj.setUserId(userId);
    paramObj.setEndTime(currentDate);
    return super.findList(FIND_SUBSCRIBE_COURSE_BY_USERID_AND_ENDTIME, paramObj);
  }

  /**
   * Title: 查询当前用户的每一节课程的最新一条预约记录<br>
   * Description: 查询当前用户的每一节课程的最新一条预约记录<br>
   * CreateDate: 2016年9月6日 下午3:43:18<br>
   * 
   * @category 查询当前用户的每一节课程的最新一条预约记录
   * @author komi.zsy
   * @param userId
   *          用户id
   * @param courseType
   *          课程类型
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findLastSubscribeCourseByUserIdAndCourseId(String userId,
      String courseType) throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setUserId(userId);
    paramObj.setCourseType(courseType);
    return super.findList(FIND_LAST_SUBSCRIBE_COURSE_LIST, paramObj);
  }

  /**
   * Title: 给webex的消费者使用<br>
   * Description: 不管is_used 是否为1,都查出来,防止预约立即取消预约报错<br>
   * CreateDate: 2016年8月4日 上午11:09:27<br>
   * 
   * @category 给webex的消费者使用
   * @author yangmh
   * @param keyId
   * @return
   * @throws Exception
   */
  public SubscribeCourse findOneByKeyIdOnWebex(String keyId)
      throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setKeyId(keyId);
    return super.findOne(FIND_ONE_BY_KEY_ID_ON_WEBEX, paramObj);
  }

  /**
   * Title: 根据预约id获取备注信息<br>
   * Description: 根据预约id获取备注信息<br>
   * CreateDate: 2017年6月7日 下午5:01:35<br>
   * 
   * @category 根据预约id获取备注信息
   * @author komi.zsy
   * @param keyId
   *          预约id
   * @return
   * @throws Exception
   */
  public SubscribeCourse findRemarkByKeyId(String keyId)
      throws Exception {
    return super.findOneByKeyId(keyId, "subscribe_remark");
  }

  /**
   * Title: 给取消预约使用<br>
   * Description: findOneByKeyOnSubscribe<br>
   * CreateDate: 2016年9月22日 上午8:54:18<br>
   * 
   * @category findOneByKeyOnSubscribe
   * @author yangmh
   * @param keyId
   * @return
   * @throws Exception
   */
  public SubscribeCourse findOneByKeyIdOnSubscribe(String keyId)
      throws Exception {
    return super.findOneByKeyId(keyId,
        "key_id,teacher_time_id,teacher_id,course_type,course_id,"
            + "start_time,end_time,order_option_id,user_id,user_name,teacher_name,user_id,subscribe_status");
  }

  public SubscribeCourse findOneByKeyIdForUpdate(String keyId)
      throws Exception {
    String querySql =
        "SELECT key_id,course_title,teacher_time_id,teacher_id,course_type,course_id,start_time,end_time,order_option_id,user_id,user_name "
            + "FROM t_subscribe_course where key_id = :keyId AND is_used = 1 FOR UPDATE ";
    Map paramMap = new HashMap();
    paramMap.put("keyId", keyId);

    List<SubscribeCourse> returnList = this.namedParameterJdbcTemplate.query(querySql,
        paramMap, new BeanPropertyRowMapper(SubscribeCourse.class));
    if (returnList != null && returnList.size() != 0) {
      return returnList.get(0);
    }

    return null;
  }

  /**
   * Title: 按照courseType查询学员的完成的课程数<br>
   * Description: 按照courseType查询学员的完成的课程数<br>
   * CreateDate: 2016年10月13日 下午3:55:22<br>
   * 
   * @category 按照courseType查询学员的完成的课程数
   * @author seven.gz
   * @return int
   */
  public int findCompleteCountByCourseType(String userId, String courseType, Date currentTime)
      throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setUserId(userId);
    paramObj.setCourseType(courseType);
    paramObj.setEndTime(currentTime);
    return super.findCount(FIND_COMPLETE_COUNT_BY_COURSE_TYPE, paramObj);
  }

  /**
   * Title: 根据课程类型+预约id+用户id查询上一条预约数据.<br>
   * Description: findLastSubscribeByKeyIdAndCourseTypeAndUserId<br>
   * CreateDate: 2016年12月5日 下午6:57:49<br>
   * 
   * @category 根据课程类型+预约id+用户id查询上一条预约数据.
   * @author yangmh
   * @param userId
   *          用户id
   * @param keyId
   *          当前预约id
   * @param courseType
   *          课程类型
   */
  public SubscribeCourse findLastSubscribeByKeyIdAndCourseTypeAndUserId(
      String userId, String keyId, String courseType) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("userId", userId);
    paramMap.put("keyId", keyId);
    paramMap.put("courseType", courseType);
    List<SubscribeCourse> subscribeCourseList = super.findList(
        FIND_LAST_SUBSCRIBE_BY_KEYID_AND_COURSETYPE_AND_USERID, paramMap);

    if (subscribeCourseList != null && subscribeCourseList.size() > 0) {
      return subscribeCourseList.get(0);
    } else {
      return null;
    }

  }

  /**
   * 
   * Title: 根据courseid和userid 查询预约信息<br>
   * Description: findSubscribeListByUserIdAndCourseIdsAndDate<br>
   * CreateDate: 2016年12月15日 下午1:58:14<br>
   * 
   * @category 根据courseid和userid 查询预约信息
   * @author seven.gz
   * @param userId
   * @param startTime
   * @param courseIds
   * @return
   * @throws Exception
   */
  public List<SubscribeCourse> findSubscribeListByUserIdAndCourseIdsAndDate(String userId,
      Date startTime, List<String> courseIds) throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("userId", userId);
    paramMap.put("startTime", startTime);
    paramMap.put("courseIds", courseIds);
    return super.findList(FIND_SUBSCRIBE_LIST_BY_USERID_AND_COURSE_IDS_AND_DATE, paramMap);
  }

  /**
   * 
   * Title: 查询用户user_id在相同时间段start_time && end_time 内，不能重复预约<br>
   * Description: findAllTypeUserCourseByUserIdAndStartTimeAndEndTime<br>
   * CreateDate: 2016年12月23日 上午11:43:49<br>
   * 
   * @category 查询用户user_id在相同时间段start_time && end_time 内，不能重复预约
   * @author seven.gz
   * @param userId
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public SubscribeCourse findAllTypeUserCourseByUserIdAndStartTimeAndEndTime(String userId,
      Date startTime,
      Date endTime) throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setUserId(userId);
    paramObj.setStartTime(startTime);
    paramObj.setEndTime(endTime);
    return super.findOne(FIND_ALLTYPE_USERCOURSE_BY_USERID_ANDSTARTTIME_ANDENDTIME, paramObj);
  }

  /**
   * 
   * Title: 更新人<br>
   * Description: updateSubscribeUserIdByUserId<br>
   * CreateDate: 2016年5月25日 下午6:04:09<br>
   * 
   * @category updateSubscribeUserIdByUserId
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int updateSubscribeUserIdByUserId(String keyId, String updateUserId, Boolean isUsed)
      throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setKeyId(keyId);
    paramObj.setUpdateUserId(updateUserId);
    paramObj.setIsUsed(isUsed);
    return super.update(UPDATE_SUBSCRIBEUSERID_BY_USERID, paramObj);
  }

  /**
   * Title: 根据老师时间id查找学员id和老师id<br>
   * Description: 用于更换老师时取消环迅老师时需要使用的数据<br>
   * CreateDate: 2017年5月3日 下午4:18:13<br>
   * 
   * @category 根据老师时间id查找学员id和老师id
   * @author komi.zsy
   * @param teacherTimeId
   *          老师时间id
   * @return
   * @throws Exception
   */
  public SubscribeCourse findUserIdAndTeacherIdByTeacherTimeId(String teacherTimeId)
      throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setTeacherTimeId(teacherTimeId);
    List<SubscribeCourse> list = super.findList(paramObj,
        "user_id,user_name,teacher_id,course_type,course_courseware");
    if (list == null || list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  /**
   * 
   * Title: 查询本月有预约(过)课程的日期以及当天预约的课程数<br>
   * Description: 查询本月有预约(过)课程的日期以及当天预约的课程数<br>
   * CreateDate: 2017年7月19日 下午4:22:12<br>
   * 
   * @category 查询本月有预约(过)课程的日期以及当天预约的课程数
   * @author felix.yl
   * @param userId
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<SubscribleCourseParam> findSubscribeCourseDateAndNumber(String userId,
      Date startTime,
      Date endTime) throws Exception {
    SubscribleCourseParam paramObj = new SubscribleCourseParam();
    paramObj.setUserId(userId);// 学员的userId
    paramObj.setStartTime(startTime);// 本月第一天
    paramObj.setEndTime(endTime);// 本月最后一天
    return super.findList(FIND_SUBSCRIBE_COURSE_DATE_AND_NUMBER, paramObj);
  }

  /**
   * 
   * Title: 查询展示学员在某天已预约的所有课程以及老师相关信息(大课)<br>
   * Description: 查询展示学员在某天已预约的所有课程以及老师相关信息(大课)<br>
   * CreateDate: 2017年7月20日 上午10:47:43<br>
   * 
   * @category 查询展示学员在某天已预约的所有课程以及老师相关信息(大课)
   * @author felix.yl
   * @param userId
   * @param currentStartTime
   * @param currentEndTime
   * @return
   * @throws Exception
   */
  public List<SubscribleCourseDetailParam> findSubscribeCourseDetailOneToMany(String userId,
      Date currentStartTime,
      Date currentEndTime) throws Exception {
    SubscribleCourseDetailParam paramObj = new SubscribleCourseDetailParam();
    paramObj.setUserId(userId);// 学员的userId
    paramObj.setCurrentStartTime(currentStartTime);// 当天的开始时间
    paramObj.setCurrentEndTime(currentEndTime);// 当天的结束时间
    return super.findList(FIND_SUBSCRIBE_COURSE_DETAIL_ONE_TO_MANY, paramObj);
  }

  /**
   * 
   * Title: 查询展示学员在某天已预约的所有课程以及老师相关信息(小课)<br>
   * Description: 查询展示学员在某天已预约的所有课程以及老师相关信息(小课)<br>
   * CreateDate: 2017年7月20日 上午10:47:43<br>
   * 
   * @category 查询展示学员在某天已预约的所有课程以及老师相关信息(小课)
   * @author felix.yl
   * @param userId
   * @param currentStartTime
   * @param currentEndTime
   * @return
   * @throws Exception
   */
  public List<SubscribleCourseDetailParam> findSubscribeCourseDetailOneToOne(String userId,
      Date currentStartTime,
      Date currentEndTime) throws Exception {
    SubscribleCourseDetailParam paramObj = new SubscribleCourseDetailParam();
    paramObj.setUserId(userId);// 学员的userId
    paramObj.setCurrentStartTime(currentStartTime);// 当天的开始时间
    paramObj.setCurrentEndTime(currentEndTime);// 当天的结束时间
    return super.findList(FIND_SUBSCRIBE_COURSE_DETAIL_ONE_TO_ONE, paramObj);
  }

  /**
   * 
   * Title: 查询学员各个课程类型完成的课程数<br>
   * Description: findCountFinishCourseType<br>
   * CreateDate: 2017年7月20日 下午6:11:20<br>
   * 
   * @category 查询学员各个课程类型完成的课程数
   * @author seven.gz
   * @param userId
   * @param endTime
   * @return
   * @throws Exception
   */
  public List<FindSubscribeCourseCountParam> findCountFinishCourseType(String userId, Date endTime)
      throws Exception {
    FindSubscribeCourseCountParam paramObj = new FindSubscribeCourseCountParam();
    paramObj.setUserId(userId);
    paramObj.setEndTime(endTime);
    return super.findList(FIND_COUNT_FINISH_COURSE_TYPE, paramObj);
  }

  /**
   * 
   * Title: 查找当前用户、课程结束时间大于等于参数时间的所有预约信息<br>
   * Description: 查找当前用户、课程结束时间大于等于参数时间的所有预约信息<br>
   * CreateDate: 2017年7月20日 下午4:47:08<br>
   * 
   * @category 查找当前用户、课程结束时间大于等于参数时间的所有预约信息
   * @author felix.yl
   * @param userId
   * @param endTime
   * @param courseType
   * @return
   * @throws Exception
   */
  public List<SubscribeCourseParam> findSubscribeCourseList(String userId,
      String courseType, Date endTime) throws Exception {
    SubscribeCourseParam paramObj = new SubscribeCourseParam();
    paramObj.setUserId(userId);
    paramObj.setCourseType(courseType);
    paramObj.setEndTime(endTime);

    return super.findList(FIND_SUBSCRIBE_LIST_BY_USERID_AND_COURSETYPE_AND_DATE, paramObj);
  }

  /**
   * 
   * Title: 查询返回该用户已经完成的所有课程类型以及每种课程类型对应的数目<br>
   * Description: 查询返回该用户已经完成的所有课程类型以及每种课程类型对应的数目<br>
   * CreateDate: 2017年7月21日 下午3:42:20<br>
   * 
   * @category 查询返回该用户已经完成的所有课程类型以及每种课程类型对应的数目
   * @author felix.yl
   * @param userId
   * @param startTime
   * @return
   * @throws Exception
   */
  public List<CourseTypeCommentCountParam> findCourseCountCompleted(String userId, Date currentTime)
      throws Exception {
    CourseTypeCommentCountParam paramObj = new CourseTypeCommentCountParam();
    paramObj.setUserId(userId);
    paramObj.setCurrentTime(currentTime);
    return super.findList(FIND_COURSE_COUNT_BY_COURSE_TYPE, paramObj);
  }

  /**
   * 
   * Title: 分页查询返回课程预约相关信息<br>
   * Description: 查询返回课程预约相关信息<br>
   * CreateDate: 2017年7月21日 下午3:42:20<br>
   * 
   * @category 分页查询返回课程预约相关信息
   * @author felix.yl
   * @param userId
   * @param startTime
   * @return
   * @throws Exception
   */
  public Page findCourseListCompleted(String userId, String courseType,
      Date currentTime, int page, int rows)
          throws Exception {
    CourseTypeCommentListParam paramObj = new CourseTypeCommentListParam();
    paramObj.setUserId(userId);
    paramObj.setCourseType(courseType);
    paramObj.setCurrentTime(currentTime);
    return super.findPage(FIND_COURSE_LIST_BY_COURSE_TYPE, paramObj, page, rows);
  }

  /**
   * 
   * Title: 根据课程类型查询学员预约列表<br>
   * Description: findListByUserIdAndCourseType<br>
   * CreateDate: 2017年7月21日 下午6:07:18<br>
   * 
   * @category 根据课程类型查询学员预约列表
   * @author seven.gz
   * @param userId
   *          学员id
   * @param courseType
   *          课程类型
   */
  public List<SubscribeCourse> findListByUserIdAndCourseType(String userId,
      String courseType, Date endTime)
          throws Exception {
    SubscribeCourse paramObj = new SubscribeCourse();
    paramObj.setUserId(userId);
    paramObj.setCourseType(courseType);
    paramObj.setEndTime(endTime);
    return super.findList(FIND_LIST_BY_USER_ID_AND_COURSETYPE, paramObj);
  }

  /**
   * 
   * Title: 根据预约id查询预约、老师、上课平台(是否有回放)相关的信息<br>
   * Description: 根据预约id查询预约、老师、上课平台(是否有回放)相关的信息<br>
   * CreateDate: 2017年7月24日 下午4:46:13<br>
   * 
   * @category 根据预约id查询预约、老师、上课平台(是否有回放)相关的信息
   * @author felix.yl
   * @param subscribeCourseId
   * @return
   * @throws Exception
   */
  public CourseSubscribeAndTeacherInfoParam findSubscribeTeacherInfoBySubscribeId(
      String subscribeCourseId)
          throws Exception {
    CourseSubscribeAndTeacherInfoParam paramObj = new CourseSubscribeAndTeacherInfoParam();
    paramObj.setSubscribeCourseId(subscribeCourseId);
    return super.findOne(FIND_SUBSCRIBE_TEACHER_PLATFORM_INFO_BY_SUBSCRIBE_ID, paramObj);
  }

  /**
   * 
   * Title: 查询该学员某个级别中含有9个已完成已出席的不同的Core课程<br>
   * Description: findListFinishLevel<br>
   * CreateDate: 2017年7月24日 下午5:05:32<br>
   * 
   * @category 查询该学员某个级别中含有9个已完成已出席的不同的Core课程
   * @author seven.gz
   * @param userId
   *          用户id
   * @param endTime
   *          结束时间
   */
  public List<FindListfinishLevelParam> findListFinishLevel(String userId, Date endTime)
      throws Exception {
    FindListfinishLevelParam paramObj = new FindListfinishLevelParam();
    paramObj.setUserId(userId);
    paramObj.setEndTime(endTime);
    return super.findList(FIND_LIST_FINISH_LEVEL, paramObj);
  }

  /**
   * 
   * Title: 查找最近一条学员没有查看评论的预约信息<br>
   * Description: 查找最近一条学员没有查看评论的预约信息<br>
   * CreateDate: 2017年7月27日 上午10:28:27<br>
   * 
   * @category 查找最近一条学员没有查看评论的预约信息
   * @author felix.yl
   * @param userId
   * @return
   * @throws Exception
   */
  public SubscribeCourse findOneStudentNoLookComment(String userId) throws Exception {
    SubscribeCourse SubscribeCourse = new SubscribeCourse();
    SubscribeCourse.setUserId(userId);
    return super.findOne(STUDENT_FIND_NO_LOOK_COMMENT, SubscribeCourse);
  }

  /**
   * 
   * Title: 查询指定开始时间的排名<br>
   * Description: 查询指定开始时间的排名<br>
   * CreateDate: 2017年7月27日 上午10:32:45<br>
   * 
   * @category 查询指定开始时间的排名
   * @author felix.yl
   * @param startTime
   * @param userId
   * @param courseType
   * @return
   * @throws Exception
   */
  public int findOrderNumber(Date startTime, String userId, String courseType) throws Exception {
    SubscribeCourse subscribeCourse = new SubscribeCourse();
    subscribeCourse.setStartTime(startTime);
    subscribeCourse.setEndTime(new Date());
    subscribeCourse.setUserId(userId);
    subscribeCourse.setCourseType(courseType);
    return super.findCount(FIND_NUMBER_BY_SUBSCRIBEID, subscribeCourse);
  }

  /**
   * 
   * Title: findRemarkByKeyId<br>
   * Description: findRemarkByKeyId<br>
   * CreateDate: 2017年7月27日 上午10:49:51<br>
   * 
   * @category findRemarkByKeyId
   * @author officer
   * @param keyId
   * @return
   * @throws Exception
   */
  public SubscribeCourse findSubscribeCourseByKeyId(String keyId)
      throws Exception {
    return super.findOneByKeyId(keyId, "course_type,start_time");
  }

  /**
   * 
   * Title: 查询已预约且出席的1v1课程节数<br>
   * Description: 查询已预约且出席的1v1课程节数<br>
   * CreateDate: 2017年8月8日 下午8:20:37<br>
   * 
   * @category 查询已预约且出席的1v1课程节数
   * @author felix.yl
   * @param userId
   * @param currentStartTime
   * @param currentEndTime
   * @return
   * @throws Exception
   */
  public SubscribeCourseCountParam findCountOneToOneCourse(String userId, Date currentStartTime,
      Date currentEndTime)
          throws Exception {
    SubscribeCourseCountParam paramObj = new SubscribeCourseCountParam();
    paramObj.setUserId(userId);
    paramObj.setCurrentStartTime(currentStartTime);
    paramObj.setCurrentEndTime(currentEndTime);
    return super.findOne(FIND_COUNT_ONE_TO_ONE_COURSE, paramObj);
  }

  /**
   * 
   * Title: 查询已预约且出席的1vn课程节数<br>
   * Description: 查询已预约且出席的1vn课程节数<br>
   * CreateDate: 2017年8月8日 下午8:20:37<br>
   * 
   * @category 查询已预约且出席的1vn课程节数
   * @author felix.yl
   * @param userId
   * @param currentStartTime
   * @param currentEndTime
   * @return
   * @throws Exception
   */
  public SubscribeCourseCountParam findCountOneToManyCourse(String userId, Date currentStartTime,
      Date currentEndTime)
          throws Exception {
    SubscribeCourseCountParam paramObj = new SubscribeCourseCountParam();
    paramObj.setUserId(userId);
    paramObj.setCurrentStartTime(currentStartTime);
    paramObj.setCurrentEndTime(currentEndTime);
    return super.findOne(FIND_COUNT_ONE_TO_MANY_COURSE, paramObj);
  }

}
