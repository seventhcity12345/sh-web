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
@ApiModel(value = "TrainingCampClanMemberParam(成人-口语训练营-战队成员模型)")
public class TrainingCampClanMemberParam implements Serializable {

  private static final long serialVersionUID = 2822763989638085871L;

  @ApiModelProperty(value = "头像",
      example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images%2Fuser%2Fdefault%2Fuser_default_photo.jpg")
  private String trainingMemberPic;// 成员头像

  @ApiModelProperty(value = "英文名", example = "felix")
  private String trainingMemberEnglishName;// 成员英文名

  @ApiModelProperty(value = "训练营id(查询用,前端不用理睬)")
  private String trainingCampId;

  public String toString() {
    return ReflectUtil.reflectToString(this);
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

  public String getTrainingCampId() {
    return trainingCampId;
  }

  public void setTrainingCampId(String trainingCampId) {
    this.trainingCampId = trainingCampId;
  }

}
