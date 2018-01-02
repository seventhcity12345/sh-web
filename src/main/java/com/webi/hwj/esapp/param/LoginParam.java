
package com.webi.hwj.esapp.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 用户登录参数<br>
 * Description: LoginParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月23日 下午3:20:26
 * 
 * @author komi.zsy
 */
@ApiModel(value = "UserLoginValidationForm(用户登录模型)")
public class LoginParam {
  // 手机号
  @ApiModelProperty(value = "手机号", required = true, example = "12345678901")
  @NotNull(message = "手机号不能为空")
  @Pattern(regexp = "^\\d{3}-\\d{4,10}$|^\\d{4}-\\d{3,9}$|^1\\d{10}$", message = "手机号码是11位数字")
  @Length(min = 11, max = 11, message = "手机号必须为11位")
  private String mobile;
  // 验证码
  @ApiModelProperty(value = "验证码", required = true, example = "1234")
  private String valid_code;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getValid_code() {
    return valid_code;
  }

  public void setValid_code(String valid_code) {
    this.valid_code = valid_code;
  }

}