package com.webi.hwj.ordercourse.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 合同子表课程类型<br>
 * Description: 合同子表课程类型<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年9月20日 上午11:00:34
 * 
 * @author komi.zsy
 */
public class FindUserEffectiveContractReturnParam implements Serializable {
  private static final long serialVersionUID = 3055153650064488081L;
  // 跟码表里的课程类别走
  @ApiModelProperty(value = "课程类型对应的课程数")
  private List<FindUserEffectiveContractParam> corseCountList;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<FindUserEffectiveContractParam> getCorseCountList() {
    return corseCountList;
  }

  public void setCorseCountList(List<FindUserEffectiveContractParam> corseCountList) {
    this.corseCountList = corseCountList;
  }

}