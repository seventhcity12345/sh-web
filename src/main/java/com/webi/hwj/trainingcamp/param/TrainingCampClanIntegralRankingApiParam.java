package com.webi.hwj.trainingcamp.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 战队积分模型(含list对象)<br>
 * Description: 战队积分模型(含list对象)<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月9日 上午11:27:21
 * 
 * @author felix.yl
 */
@ApiModel(value = "TrainingCampClanIntegralRankingApiParam(成人-口语训练营-战队积分模型(list对象)))")
public class TrainingCampClanIntegralRankingApiParam implements Serializable {

  private static final long serialVersionUID = 1124772854735544129L;

  @ApiModelProperty(value = "战队积分模型(list对象)")
  private List<TrainingCampClanIntegralRankingParam> trainingCampClanIntegralRankingParamList;

  @ApiModelProperty(value = "高于平台百分比", example = "92")
  private Integer highPercentage;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<TrainingCampClanIntegralRankingParam> getTrainingCampClanIntegralRankingParamList() {
    return trainingCampClanIntegralRankingParamList;
  }

  public void setTrainingCampClanIntegralRankingParamList(
      List<TrainingCampClanIntegralRankingParam> trainingCampClanIntegralRankingParamList) {
    this.trainingCampClanIntegralRankingParamList = trainingCampClanIntegralRankingParamList;
  }

  public Integer getHighPercentage() {
    return highPercentage;
  }

  public void setHighPercentage(Integer highPercentage) {
    this.highPercentage = highPercentage;
  }

}
