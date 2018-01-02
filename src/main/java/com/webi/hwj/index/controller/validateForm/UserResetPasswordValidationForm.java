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
 * Title: 找回密码：重置密码相关校验<br>
 * Description: FindPasswordValidationForm<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月22日 下午3:30:05
 * 
 * @author athrun.cw
 */
public class UserResetPasswordValidationForm {

  @NotNull(message = "手机号不能为空")
  @Pattern(regexp = "^\\d{3}-\\d{4,10}$|^\\d{4}-\\d{3,9}$|^1\\d{10}$", message = "手机号码是11位数字")
  @Length(min = 11, max = 11, message = "手机号必须为11位")
  private String phone;

  @NotNull(message = "新密码不能为空")
  @Length(min = 128, max = 128, message = "只接受128位加密后的密码")
  private String newPassword;

  @NotNull(message = "确认密码不能为空")
  @Length(min = 128, max = 128, message = "只接受128位加密后的密码")
  private String confirmPassword;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

}
