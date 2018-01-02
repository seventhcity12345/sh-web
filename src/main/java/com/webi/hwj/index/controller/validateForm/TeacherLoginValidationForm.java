package com.webi.hwj.index.controller.validateForm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 老师登录表单校验<br>
 * Description: 老师登录表单校验<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月11日 下午1:22:45
 * 
 * @author yangmh
 */
@ApiModel(value = "TeacherLoginValidationForm(老师登录模型)")
public class TeacherLoginValidationForm {

  @ApiModelProperty(value = "老师帐号", required = true, example = "john")
  @NotNull(message = "账号不能为空")
  @Length(min = 3, max = 20, message = "账号长度为3-20位")
  private String account;

  @ApiModelProperty(value = "老师密码(128位加密)", required = true,
      example = "b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d")
  @NotNull(message = "密码不能为空")
  @Length(min = 128, max = 128, message = "只接受128位加密后的密码")
  private String pwd;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }
}
