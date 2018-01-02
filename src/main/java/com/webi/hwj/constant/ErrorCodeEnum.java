package com.webi.hwj.constant;

/**
 * Title: BaseErrorCodeEnum.<br>
 * Description: YangEnum<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年7月24日 下午4:45:52
 * 
 * @author yangmh
 */
public enum ErrorCodeEnum {
  SUCCESS(200, "默认成功"),
  SOURCE_NOT_EXIST(404, "默认资源无法找到"),
  SYSTEM_ERROR(500, "默认失败"),
  PARAM_CHECK_ERROR(10001, "参数校验错误"),
  UPLOAD_FILE_FAIL(10002, "上传文件失败"),
  UPDATE_ERROR(11001, "通用更新失败"),
  NOTICE_DELETE_ERROR(11002, "公告删除失败"),
  NOTICE_FIND_ERROR(11003, "公告查询失败"),
  SUBSCRIBE_LECTURE_REPEAT(20001, "已上过该topic的lecture课程，是否再次出席"),
  SUBSCRIBE_ERROR_COURSE_TYPE(20002, "预约课程:类型不正确出错"),
  SUBSCRIBE_ALREADY_EXSIT(20003, "预约课程:您已在该时段预约了其他课程，请重新选择时间"),
  SUBSCRIBE_COUNT_NOT_ENOUGH(20004, "预约课程:您当前可预约课程数量已满，请完成课程后再进行预约!"),
  SUBSCRIBE_NO_ENGLISH_NAME(20005, "预约课程:没有英文名"),
  SUBSCRIBE_ERROR_TIME(20006, "预约课程:必须在预约时间范围内才能预约"),
  SUBSCRIBE_ONE2MANY_MAX_PERSONCOUNT_LIMIT(20008, "抱歉，该节课预约人数已满，请预约其他课程！"),
  SUBSCRIBE_HUANXUN_ERROR(20009, "预约课程：环讯老师被占用"),
  SUBSCRIBE_COURSE_NOT_EXIST(20010, "取消预约：预约数据不存在"),
  SUBSCRIBE_COURSE_CANCEL_WRONG_TIME(20011, "取消预约：非法取消预约,取消预约时间不正确!"),
  SUBSCRIBE_HUANXUN_CANCEL_ERROR(20012, "取消预约课程：取消环讯老师出错"),
  SUBSCRIBE_WEBEX_ROOM_NOT_EXIST(20013, "webex房间不存在(用于后台预约demo课)"),
  HAVE_NO_COURSE_TYPE(21001, "预约课程:未购买课程类型,暂无法进入"),
  SUBSCRIBE_NEED_SAME_LEVLE(21002, "预约课程:必须预约当前级别的课程"),
  COURSE_VIDEO_NOT_EXIST(22001, "课程回顾:当前课程视频回顾不存在！"),
  COURSE_VIDEO_WEBEX_NOT_EXIST(22002, "课程回顾:当前课程为webex房间,暂时不支持视频回顾！"),
  TEACHER_NOT_EXIST(30001, "老师:老师数据不存在"),
  TEACHER_TIME_NOT_EXIST(31001, "老师时间:老师时间数据不存在"),
  TEACHER_TIME_ALREADY_SUBSCRIBE(31002, "老师时间:老师已经被预约"),
  TEACHER_TIME_SIGN_ADD_ERROR(32001, "老师签课时间：新增老师签课时间错误"),
  TEACHER_TIME_SIGN_DEL_ERROR(32002, "老师签课时间：删除老师签课时间错误"),
  TEACHER_TIME_SIGN_THIRD_FROM_ERROR(32003, "老师签课时间：第三方来源教师暂时不支持签课"),
  COURSE_NOT_EXIST(40001, "课程数据不存在！"),
  RSA_NOT_COMPLETE_COURSE_PERCENT(41001, "课件数据错误，没有课件完成记录！"),
  ORDER_NOT_EXIST(50001, "合同：合同相关信息不存在"),
  ORDER_COURSE_COUNT_NOT_ENOUGH(50002, "合同：您没有该课程的可用课时数，无法预约！"),
  ORDER_IS_EXIST(50003, "合同：合同已存在！"),
  WEIXIN_USER_BIND_NOT_MATCH(60001, "微信(绑定帐号)：此手机未找到合同"),
  WEIXIN_USER_BIND_JUST_ONE(60002, "不允许多个speakhi账号绑定一个微信号"),
  WEIXIN_TEACHER_BIND_NOT_MATCH(60003, "微信(绑定帐号)：未找到此教师账号"),
  PHONE_IS_EXIST(70002, "用户:手机号已注册"),
  USER_NEXT_LEVEL_NOT_EXIST(70003, "用户:已升到最高级别"),
  SESSION_NOT_EXIST(70004, "用户:session对象不存在"),
  CCODE_NOT_CORRECT(70008,"验证码错误"),
  USER_PHONE_EXISTS(70007,"用户:手机号已被使用"),
  REDEEM_CODE_IS_USED(80001, "兑换码：兑换码已使用"),
  REDEEM_CODE_ILLEGAL(80002, "兑换码：兑换码不正确");

  private ErrorCodeEnum(int code, String description) {
    this.code = code;
    this.description = description;
  }

  private String description; // 错误码描述
  private int code; // 错误码

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
