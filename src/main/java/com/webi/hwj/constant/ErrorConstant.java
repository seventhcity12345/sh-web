package com.webi.hwj.constant;

/**
 * 
 * Title: ErrorConstant<br>
 * Description: 预约 & 取消预约的错误提示常量类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年10月27日 下午6:28:34
 * 
 * @author athrun.cw
 */
public class ErrorConstant {
  // 一对多预约 预约的人数已经超过最大限度了
  public static final String SUBSCRIBE_ONE2MANY_MAX_PERSONCOUNT_LIMIT = "抱歉，该节课预约人数已满，请预约其他课程！";

  // 数据出现错误，没有已经预约 好并且可以去上课的课程！
  public static final String SUBSCRIBE_COURSE_NODATA = "数据出现错误，没有已经预约或者可以去上课的课程！";

  // 该时间段已经被其他人预约了！
  public static final String HAVE_SUBSCRIBED_BY_OTHER_USER = "T_T 该老师已被预约！";

  // 该时间段已经被其他人预约了！
  public static final String TEACHERTIMEID_NOT_EXIST = "该时间段不存在！";

  // 当前没有可预约的老师！
  public static final String NO_TEACHER_EXIST_NOW = "当前没有可预约的老师！";

  // 你没有可以使用的合同或者购买的课程类别与预约的不一致！
  public static final String NO_ORDERCOURSE_OR_CATEGORYTYPE_NOT_LEGAL = "你没有可以使用的合法合同或者购买的课程类别与预约的不一致！";

  // 当前主题课不可预约，请按主题课先后顺序预约
  public static final String SMALLPACK_CURRENT_POSITION_NOT_LEGAL = "当前主题课不可预约，请按主题课先后顺序预约";

  // 课程数据不存在！
  public static final String COURSE_NOT_EXIST = "课程数据不存在！";

  // 没有可用合同或者查询不到合法合同下的子表数据！
  public static final String ORDERCOURSE_NOT_LEGAL_OR_OPTINES_COUNT_HAVE_DONE = "您没有该课程的可用课时数，无法预约！";

  // 你在该时间段已经有预约的课程了！
  public static final String HAVE_SUBSCRIBE_COURSE_AT_THIS_TIME = "您已在该时段预约了其他课程，请重新选择时间！";

  // 教室预约调用出错！
  public static final String VCUBE_SUBSCRIBE_ERROR = "Vcube房间预约调用出错！";

  // 老师数据错误，没有这个老师！NO_THIS_TEACHER
  public static final String NO_THIS_TEACHER = "老师数据错误，没有这个老师！";

  // 课件数据错误，没有课件完成记录！NO_THIS_TELLMEMORE_PERCENT
  public static final String NO_THIS_TELLMEMORE_PERCENT = "课件数据错误，没有课件完成记录！";

  // 课件完成度没有达到限定的条件，不能预约！ HAVE_NOT_REQUIRE_PERCENT
  public static final String HAVE_NOT_REQUIRE_PERCENT = "课件完成度没有达到限定的条件，不能预约！";

  // 当前用户等级和预约课程等级不一致，不能预约！ COURSE_LEVEL_NOT_AVAIABLE2_THIS_USER
  public static final String COURSE_LEVEL_NOT_AVAIABLE2_THIS_USER = "当前用户等级和预约课程等级不一致，不能预约！";

  // 当前用户等级和预约课程等级不一致，不能预约！ COURSE_LEVEL_NOT_AVAIABLE2_THIS_USER
  public static final String EXCEED_MAX_SUBSCRIBE_COURSE_COUNT = "您当前可预约课程数量已满，请完成课程后再进行预约!";

  /*
   * //一对多预约 预约的人数已经超过最大限度了 public static final String
   * SUBSCRIBE_ONE2MANY_MAX_PERSONCOUNT_LIMIT = "抱歉，该节课预约人数已满，请预约其他课程！";
   * 
   * //数据出现错误，没有已经预约好并且可以去上课的课程！ public static final String
   * SUBSCRIBE_COURSE_NODATA = "数据出现错误，没有已经预约或者可以去上课的课程！";
   * 
   * //该时间段已经被其他人预约了！ public static final String HAVE_SUBSCRIBED_BY_OTHER_USER =
   * "T_T 该老师已被预约！";
   * 
   * //该时间段已经被其他人预约了！ public static final String TEACHERTIMEID_NOT_EXIST =
   * "该时间段不存在！";
   * 
   * //当前没有可预约的老师！ public static final String NO_TEACHER_EXIST_NOW =
   * "当前没有可预约的老师！";
   * 
   * //你没有可以使用的合同或者购买的课程类别与预约的不一致！ public static final String
   * NO_ORDERCOURSE_OR_CATEGORYTYPE_NOT_LEGAL = "你没有可以使用的合法合同或者购买的课程类别与预约的不一致！";
   * 
   * //课程数据不存在！ public static final String COURSE_NOT_EXIST = "课程数据不存在！";
   * 
   * //没有可用合同或者查询不到合法合同下的子表数据！ public static final String
   * ORDERCOURSE_NOT_LEGAL_OR_OPTINES_COUNT_HAVE_DONE = "您预约的课程数已达到购买的课程总数上限！";
   * 
   * //你在该时间段已经有预约的课程了！ public static final String
   * HAVE_SUBSCRIBE_COURSE_AT_THIS_TIME = "您已在该时段预约了其他课程，请重新选择时间！";
   * 
   * //v立方房间预约调用出错！ public static final String VCUBE_SUBSCRIBE_ERROR =
   * "Vcube房间预约调用出错！";
   * 
   * //老师数据错误，没有这个老师！NO_THIS_TEACHER public static final String NO_THIS_TEACHER
   * = "老师数据错误，没有这个老师！";
   * 
   * //课件数据错误，没有课件完成记录！NO_THIS_TELLMEMORE_PERCENT public static final String
   * NO_THIS_TELLMEMORE_PERCENT = "课件数据错误，没有课件完成记录！";
   * 
   * //可见完成度没有达到限定的条件，不能预约！ HAVE_NOT_REQUIRE_PERCENT public static final String
   * HAVE_NOT_REQUIRE_PERCENT = "可见完成度没有达到限定的条件，不能预约！";
   * 
   * //当前用户等级和预约课程等级不一致，不能预约！ COURSE_LEVEL_NOT_AVAIABLE2_THIS_USER public static
   * final String COURSE_LEVEL_NOT_AVAIABLE2_THIS_USER =
   * "当前用户等级和预约课程等级不一致，不能预约！";
   */
}
