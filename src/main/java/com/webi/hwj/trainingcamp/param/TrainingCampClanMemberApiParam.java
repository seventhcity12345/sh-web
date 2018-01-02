package com.webi.hwj.trainingcamp.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 战队成员模型<br>
 * Description: 战队成员模型<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月9日 上午11:26:53
 * 
 * @author felix.yl
 */
@ApiModel(value = "TrainingCampClanMemberApiParam(成人-口语训练营-战队成员模型)")
public class TrainingCampClanMemberApiParam implements Serializable {

  private static final long serialVersionUID = -1223172661024269129L;

  @ApiModelProperty(value = "战队成员模型(list对象)")
  private List<TrainingCampClanMemberParam> trainingCampClanMemberParamList;

  @ApiModelProperty(value = "战队总人数", example = "120")
  private Integer countNum;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<TrainingCampClanMemberParam> getTrainingCampClanMemberParamList() {
    return trainingCampClanMemberParamList;
  }

  public void setTrainingCampClanMemberParamList(
      List<TrainingCampClanMemberParam> trainingCampClanMemberParamList) {
    this.trainingCampClanMemberParamList = trainingCampClanMemberParamList;
  }

  public Integer getCountNum() {
    return countNum;
  }

  public void setCountNum(Integer countNum) {
    this.countNum = countNum;
  }

}
