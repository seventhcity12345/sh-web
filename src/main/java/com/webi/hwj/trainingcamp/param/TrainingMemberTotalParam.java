package com.webi.hwj.trainingcamp.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * 
 * Title: 口语训练营个人积分人数统计<br>
 * Description: 口语训练营个人积分人数统计<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月9日 下午2:02:05
 * 
 * @author felix.yl
 */
public class TrainingMemberTotalParam implements Serializable {

  private static final long serialVersionUID = 273068110191789066L;

  // 有效学员总人数
  private Integer totalNum;

  // 个人积分大于当前学员的人数
  private Integer highNum;

  // 个人积分小于当前学员的人数
  private Integer lowNum;

  // 当前学员的个人积分
  private Integer trainingMemberTotalScore;

  // 有效训练营的keyIds
  private List<String> keyIds;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public Integer getTotalNum() {
    return totalNum;
  }

  public void setTotalNum(Integer totalNum) {
    this.totalNum = totalNum;
  }

  public Integer getHighNum() {
    return highNum;
  }

  public void setHighNum(Integer highNum) {
    this.highNum = highNum;
  }

  public Integer getLowNum() {
    return lowNum;
  }

  public void setLowNum(Integer lowNum) {
    this.lowNum = lowNum;
  }

  public Integer getTrainingMemberTotalScore() {
    return trainingMemberTotalScore;
  }

  public void setTrainingMemberTotalScore(Integer trainingMemberTotalScore) {
    this.trainingMemberTotalScore = trainingMemberTotalScore;
  }

  public List<String> getKeyIds() {
    return keyIds;
  }

  public void setKeyIds(List<String> keyIds) {
    this.keyIds = keyIds;
  }

}