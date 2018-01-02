package com.webi.hwj.subscribecourse.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

public class FindListfinishLevelReturnParam implements Serializable {

  private static final long serialVersionUID = 8627603557890576704L;

  // 完成级别列表
  @ApiModelProperty(value = "完成级别列表")
  private List<FindListfinishLevelParam> finishLevelList;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<FindListfinishLevelParam> getFinishLevelList() {
    return finishLevelList;
  }

  public void setFinishLevelList(List<FindListfinishLevelParam> finishLevelList) {
    this.finishLevelList = finishLevelList;
  }

}
