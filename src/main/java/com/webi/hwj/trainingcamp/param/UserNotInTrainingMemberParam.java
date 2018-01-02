package com.webi.hwj.trainingcamp.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: 不在有效训练营中的学员<br> 
 * Description: UserNotInTrainingMemberParam<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年9月12日 下午6:20:06 
 * @author komi.zsy
 */
public class UserNotInTrainingMemberParam implements Serializable {
  /** 
  * 
  */ 
  private static final long serialVersionUID = -6463566730076522017L;
  // 主键id
  private String keyId;
  // 用户编码
  private Integer userCode;
  // 英文名
  private String englishName;
  // 真实姓名
  private String realName;
  // 手机号
  private String phone;
  
  // 在训练营中的学员id
  private List<String> keyIds;
  //更新人id
  private String updateUserId;
  // 组合查询条件
  public String cons;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public Integer getUserCode() {
    return userCode;
  }

  public void setUserCode(Integer userCode) {
    this.userCode = userCode;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<String> getKeyIds() {
    return keyIds;
  }

  public void setKeyIds(List<String> keyIds) {
    this.keyIds = keyIds;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }

}