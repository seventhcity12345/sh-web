/** 
 * File: UserForm.java<br> 
 * Project: hwj_svn<br> 
 * Package: com.webi.hwj.index.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月11日 下午1:22:45
 * @author yangmh
 */
package com.webi.hwj.index.controller.validateForm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * 
 * Title: 找回密码：手机&验证码校验<br>
 * Description: FindPasswordValidationForm<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月22日 下午3:30:05
 * 
 * @author athrun.cw
 */
public class UserFindPasswordValidationForm {

  @NotNull(message = "手机号不能为空")
  @Pattern(regexp = "^\\d{3}-\\d{4,10}$|^\\d{4}-\\d{3,9}$|^1\\d{10}$", message = "手机号码是11位数字")
  @Length(min = 11, max = 11, message = "手机号必须为11位")
  private String phone;

  @NotNull(message = "验证码不能为空")
  @Length(min = 4, max = 4, message = "验证码必须为4位")
  private String validateCode;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getValidateCode() {
    return validateCode;
  }

  public void setValidateCode(String validateCode) {
    this.validateCode = validateCode;
  }

}
