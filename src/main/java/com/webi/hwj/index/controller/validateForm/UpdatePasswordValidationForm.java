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

import org.hibernate.validator.constraints.Length;

/**
 * 
 * Title: 学员修改密码表单校验<br>
 * Description: 学员修改密码表单校验<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月18日 上午10:54:55
 * 
 * @author athrun.cw
 */
public class UpdatePasswordValidationForm {
  @NotNull(message = "新密码不能为空")
  @Length(min = 128, max = 128, message = "只接受128位加密后的密码")
  private String update_newPassword;

  @NotNull(message = "原始密码不能为空")
  @Length(min = 128, max = 128, message = "只接受128位加密后的密码")
  private String update_oldPassword;

  public String getUpdate_newPassword() {
    return update_newPassword;
  }

  public void setUpdate_newPassword(String update_newPassword) {
    this.update_newPassword = update_newPassword;
  }

  public String getUpdate_oldPassword() {
    return update_oldPassword;
  }

  public void setUpdate_oldPassword(String update_oldPassword) {
    this.update_oldPassword = update_oldPassword;
  }

}
