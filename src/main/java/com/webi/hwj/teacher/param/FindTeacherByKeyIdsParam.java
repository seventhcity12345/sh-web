package com.webi.hwj.teacher.param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: t_teacher_time实体类<br>
 * Description: t_teacher_time实体类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月27日 下午6:05:05
 * 
 * @author komi.zsy
 */
@TableName("t_teacher")
public class FindTeacherByKeyIdsParam implements Serializable {
  private static final long serialVersionUID = -7170440212260015245L;
  // 主键id
  private String keyId;
  // 老师来源
  private String thirdFrom;
  // 老师ids
  private List<String> teacherIds;

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getThirdFrom() {
    return thirdFrom;
  }

  public void setThirdFrom(String thirdFrom) {
    this.thirdFrom = thirdFrom;
  }

  public List<String> getTeacherIds() {
    return teacherIds;
  }

  public void setTeacherIds(List<String> teacherIds) {
    this.teacherIds = teacherIds;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}