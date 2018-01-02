/** 
 * File: Gensee.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.gensee.entity<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年7月7日 上午8:14:42
 * @author yangmh
 */
package com.webi.hwj.gensee.entity;

import java.io.Serializable;
import java.util.Date;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: GenseeRoom<br>
 * Description: GenseeRoom<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月7日 上午8:14:42
 * 
 * @author yangmh
 */
@TableName("t_gensee")
public class Gensee implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -8339441253819157690L;

  private String keyId;

  private String roomId;

  private String studentJoinUrl;

  private String teacherJoinUrl;

  private Date createDate;

  private Date updateDate;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public String getStudentJoinUrl() {
    return studentJoinUrl;
  }

  public void setStudentJoinUrl(String studentJoinUrl) {
    this.studentJoinUrl = studentJoinUrl;
  }

  public String getTeacherJoinUrl() {
    return teacherJoinUrl;
  }

  public void setTeacherJoinUrl(String teacherJoinUrl) {
    this.teacherJoinUrl = teacherJoinUrl;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
}
