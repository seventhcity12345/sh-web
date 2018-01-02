package com.webi.hwj.user.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: FloatingLayerInfoParam<br>
 * Description: 青少-学员端页面浮层接口数据封装parm类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年7月4日 下午5:48:13
 * 
 * @author felix.yl
 */
@ApiModel(value = "FloatingLayerInfoParam(学员浮层信息模型)")
public class FloatingLayerInfoParam implements Serializable {
  private static final long serialVersionUID = -1084667315124629686L;
  @ApiModelProperty(value = "LC名称", example = "mockLc")
  private String lcName;
  @ApiModelProperty(value = "LC手机号", example = "00000000000")
  private String lcTel;
  @ApiModelProperty(value = "LC微信号", example = "weixin")
  private String lcWeixin;
  @ApiModelProperty(value = "CC名称", example = "mockCC")
  private String ccName;
  @ApiModelProperty(value = "CC手机号", example = "11111111111")
  private String ccTel;
  @ApiModelProperty(value = "CC微信号", example = "lisi8899")
  private String ccWeixin;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getLcName() {
    return lcName;
  }

  public void setLcName(String lcName) {
    this.lcName = lcName;
  }

  public String getLcTel() {
    return lcTel;
  }

  public void setLcTel(String lcTel) {
    this.lcTel = lcTel;
  }

  public String getLcWeixin() {
    return lcWeixin;
  }

  public void setLcWeixin(String lcWeixin) {
    this.lcWeixin = lcWeixin;
  }

  public String getCcName() {
    return ccName;
  }

  public void setCcName(String ccName) {
    this.ccName = ccName;
  }

  public String getCcTel() {
    return ccTel;
  }

  public void setCcTel(String ccTel) {
    this.ccTel = ccTel;
  }

  public String getCcWeixin() {
    return ccWeixin;
  }

  public void setCcWeixin(String ccWeixin) {
    this.ccWeixin = ccWeixin;
  }

}
