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
@ApiModel(value = "SubscribleCourseDetailApiParam(成人-我的课表-某天所有已预约课程详情模型(list对象))")
public class SubscribleCourseDetailApiParam implements Serializable {

  private static final long serialVersionUID = -8380885452530103279L;

  @ApiModelProperty(value = "某天所有已预约课程详情模型(list对象)")
  private List<SubscribleCourseDetailParam> subscribleCourseDetailList;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<SubscribleCourseDetailParam> getSubscribleCourseDetailList() {
    return subscribleCourseDetailList;
  }

  public void setSubscribleCourseDetailList(
      List<SubscribleCourseDetailParam> subscribleCourseDetailList) {
    this.subscribleCourseDetailList = subscribleCourseDetailList;
  }

}
