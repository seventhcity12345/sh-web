/** 
 * File: UserForm.java<br> 
 * Project: hwj_svn<br> 
 * Package: com.webi.hwj.index.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月11日 下午1:22:45
 * @author yangmh
 */
package com.webi.hwj.index.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 学员用户注册表单校验<br>
 * Description: 用户注册表单校验<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月11日 下午1:22:45
 * 
 * @author yangmh
 */
@ApiModel(value = "UserRegisterParam(用户注册模型)")
public class UserRegisterParam {
  @ApiModelProperty(value = "手机号码", required = true, example = "18600609747")
  @NotNull(message = "手机号不能为空")
  @Pattern(regexp = "^\\d{3}-\\d{4,10}$|^\\d{4}-\\d{3,9}$|^1\\d{10}$", message = "手机号码是11位数字")
  @Length(min = 11, max = 11, message = "手机号必须为11位")
  private String phone;

  @ApiModelProperty(value = "128位加密后的密码", required = true,
      example = "b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d")
  @NotNull(message = "密码不能为空")
  @Length(min = 128, max = 128, message = "只接受128位加密后的密码")
  private String pwd;

  @ApiModelProperty(value = "验证码", required = true, example = "1234")
  @NotNull(message = "验证码不能为空")
  @Length(min = 4, max = 4, message = "验证码必须为4位")
  private String code;

  @ApiModelProperty(value = "adid", required = true, example = "QGHQFN06NI")
  @NotNull(message = "adid不能为空")
  private String adid;

  private String createUserId;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getAdid() {
    return adid;
  }

  public void setAdid(String adid) {
    this.adid = adid;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }
}
