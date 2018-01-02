package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 成人会员首页我的课表<br>
 * Description: 成人会员首页我的课表<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年7月19日 下午1:41:40
 * 
 * @author felix.yl
 */
@ApiModel(value = "CourseSubscribleParamApi(成人-我的课表-奖杯/闹钟图标展示-信息模型(list))")
public class SubscribleCourseApiParam implements Serializable {

  private static final long serialVersionUID = 5306786881663984823L;

  @ApiModelProperty(value = "有预约(过)课程的日期及课程数信息(list对象)")
  private List<SubscribleCourseParam> subscribleCourseParamList;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<SubscribleCourseParam> getSubscribleCourseParamList() {
    return subscribleCourseParamList;
  }

  public void setSubscribleCourseParamList(List<SubscribleCourseParam> subscribleCourseParamList) {
    this.subscribleCourseParamList = subscribleCourseParamList;
  }

}
