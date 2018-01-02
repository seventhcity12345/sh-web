package com.webi.hwj.trainingcamp.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: 战队积分模型<br>
 * Description: 战队积分模型<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月9日 上午11:26:53
 * 
 * @author felix.yl
 */
@ApiModel(value = "TrainingCampClanIntegralRankingParam(成人-口语训练营-战队积分模型)")
public class TrainingCampClanIntegralRankingParam implements Serializable {

  private static final long serialVersionUID = -3351084559909720593L;

  @ApiModelProperty(value = "userId", example = "0606d341bb0342cf88f1903d19110635")
  private String userId;// userId

  @ApiModelProperty(value = "名次", example = "1")
  private Integer ranking;// 名次

  @ApiModelProperty(value = "头像",
      example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images%2Fuser%2Fdefault%2Fuser_default_photo.jpg")
  private String trainingMemberPic;// 头像

  @ApiModelProperty(value = "英文名", example = "felix")
  private String trainingMemberEnglishName;// 英文名

  @ApiModelProperty(value = "个人总积分", example = "210")
  private Integer trainingMemberTotalScore;// 个人总积分

  @ApiModelProperty(value = "是否为本人(当前登录学员.Y:是;N:不是;)", example = "Y")
  private String isOwnOneself;// 是否为本人(当前登录学员.Y:是;N:不是;)

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getRanking() {
    return ranking;
  }

  public void setRanking(Integer ranking) {
    this.ranking = ranking;
  }

  public String getTrainingMemberPic() {
    return trainingMemberPic;
  }

  public void setTrainingMemberPic(String trainingMemberPic) {
    this.trainingMemberPic = trainingMemberPic;
  }

  public String getTrainingMemberEnglishName() {
    return trainingMemberEnglishName;
  }

  public void setTrainingMemberEnglishName(String trainingMemberEnglishName) {
    this.trainingMemberEnglishName = trainingMemberEnglishName;
  }

  public Integer getTrainingMemberTotalScore() {
    return trainingMemberTotalScore;
  }

  public void setTrainingMemberTotalScore(Integer trainingMemberTotalScore) {
    this.trainingMemberTotalScore = trainingMemberTotalScore;
  }

  public String getIsOwnOneself() {
    return isOwnOneself;
  }

  public void setIsOwnOneself(String isOwnOneself) {
    this.isOwnOneself = isOwnOneself;
  }

}
