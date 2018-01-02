package com.webi.hwj.user.controller.validateForm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * 
 * Title: 管理员帮助用户注册表单校验<br>
 * Description: UserPhoneValidationForm<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年11月23日 下午5:15:31
 * 
 * @author athrun.cw
 */
public class UserPhoneValidationForm {

  @Pattern(regexp = "^\\d{3}-\\d{4,10}$|^\\d{4}-\\d{3,9}$|^1\\d{10}$", message = "手机号码是11位数字")
  @NotNull(message = "手机号不能为空")
  @Length(min = 11, max = 11, message = "手机号必须为11位")
  private String phone;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

}
