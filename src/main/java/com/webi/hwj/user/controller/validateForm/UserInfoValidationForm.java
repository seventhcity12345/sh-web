package com.webi.hwj.user.controller.validateForm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * 
 * Title: UserInfoValidationForm<br>
 * Description: 用户资料验证<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年10月16日 上午10:11:03
 * 
 * @author athrun.cw
 */
public class UserInfoValidationForm {

  /**
   * 手机号： ^\d{3}-\d{4,10}$|^\d{4}-\d{3,9}$|^1\d{10}$
   * 身份证号：^(\d{18,18}|\d{15,15}|\d{17,17}x)$ 真实姓名： ^[\u4e00-\u9fa5]{0,5}$ email:
   * ^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$
   */
  @NotNull(message = "手机号不能为空")
  @Length(min = 11, max = 11, message = "请输入11位手机号")
  private String phone;

  @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|\\d{17,17}(x|X))$|^\\s{0}$", message = "身份证号是15-18位")
  @Length(min = 0, max = 18, message = "身份证号的范围是15-18位")
  private String idcard;

  @NotNull(message = "英文名不能为空")
  @Length(min = 1, max = 20, message = "英文名的范围是1-20位")
  private String english_name;

  @Length(min = 0, max = 15, message = "真实姓名的范围是0-15位")
  private String real_name;

  // gender一旦没有填写，只能默认值是2，表示没有填写
  @Length(min = 0, max = 2, message = "性别的范围是0-2位")
  private String gender = "2";

  @Length(min = 0, max = 10, message = "省的范围是0-10位")
  private String province;

  @Length(min = 0, max = 10, message = "市的范围是0-10位")
  private String city;

  @Length(min = 0, max = 10, message = "区/县的范围是0-10位")
  private String district;

  @Length(min = 0, max = 40, message = "通讯地址的范围是0-40位")
  private String address;

  @Length(min = 0, max = 50, message = "学习工具的范围是0-50位")
  private String learn_tool;

  @Length(min = 0, max = 100, message = "个性签名的范围是2-6位")
  private String personal_sign;

  @Length(min = 0, max = 50, message = "不是有效邮箱，邮箱示例：webi@webi.com.cn")
  private String email;

  @NotNull(message = "联系方式不能为空")
  @Length(min = 8, max = 14, message = "联系方式由8-14位数字或者区号加'-'加号码组成")
  private String contract_func;

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public String getEnglish_name() {
    return english_name;
  }

  public void setEnglish_name(String english_name) {
    this.english_name = english_name;
  }

  public String getReal_name() {
    return real_name;
  }

  public void setReal_name(String real_name) {
    this.real_name = real_name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLearn_tool() {
    return learn_tool;
  }

  public void setLearn_tool(String learn_tool) {
    this.learn_tool = learn_tool;
  }

  public String getPersonal_sign() {
    return personal_sign;
  }

  public void setPersonal_sign(String personal_sign) {
    this.personal_sign = personal_sign;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContract_func() {
    return contract_func;
  }

  public void setContract_func(String contract_func) {
    this.contract_func = contract_func;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
