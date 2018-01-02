/** 
 * File: GenseeConstant.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.gensee.constant<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年7月6日 下午2:12:22
 * @author yangmh
 */
package com.webi.hwj.gensee.constant;

/**
 * Title: GenseeConstant<br>
 * Description: GenseeConstant<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月6日 下午2:12:22
 * 
 * @author yangmh
 */
public class GenseeConstant {
  /**
   * 老师口令
   */
  public static final String GENSEE_TEACHER_TOKEN = "111111";
  /**
   * 助教口令
   */
  public static final String GENSEE_ASSISTANT_TOKEN = "222222";
  /**
   * 学生客户端口令
   */
  public static final String GENSEE_STUDENT_CLIENT_TOKEN = "333333";

  /**
   * 教室类型：大讲堂
   */
  public static final int GENSEE_SCENE_BIG = 0;
  /**
   * 教室类型：小班课
   */
  public static final int GENSEE_SCENE_SMALL = 1;

  /**
   * Web 端学生界面设置(1 是三分屏)
   */
  public static final int GENSEE_UI_MODE_THREE_PART = 1;

  /**
   * 进教室类型：老师进入教室
   */
  public static final int GENSEE_GO_TO_CLASS_TYPE_TEACHER = 1;
  /**
   * 进教室类型：学生进入教室
   */
  public static final int GENSEE_GO_TO_CLASS_TYPE_STUDENT = 2;
  /**
   * 进教室类型：助教进入教室
   */
  public static final int GENSEE_GO_TO_CLASS_TYPE_ASSISTANT = 3;
  /**
   * 进教室类型：非本系统用户进入教室
   * 给总部对接使用，他们的用户没有usercode
   */
  public static final int GENSEE_GO_TO_CLASS_TYPE_NOT_USER = 4;

}
