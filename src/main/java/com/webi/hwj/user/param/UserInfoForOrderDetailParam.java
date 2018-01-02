package com.webi.hwj.user.param;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 合同详情使用<br>
 * Description: UserInfoParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年11月11日 下午6:05:00
 * 
 * @author komi.zsy
 */
public class UserInfoForOrderDetailParam implements Serializable {
  // 主键id
  private String keyId;
  // 身份证号
  private String idcard;
  // 手机号
  private String phone;
  // 英文名
  private String englishName;
  // 真实姓名
  private String realName;
  // 学生生日
  private Date birthday;
  // 联系方式
  private String contractFunc;
  // 学员头像
  private String userPhoto;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getContractFunc() {
    return contractFunc;
  }

  public void setContractFunc(String contractFunc) {
    this.contractFunc = contractFunc;
  }

  public String getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }

}