/** 
 * File: AdminOrderCourse.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.ordercourse.model<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年3月30日 下午3:08:40
 * @author ivan.mgh
 */
package com.webi.hwj.ordercourse.model;

import java.io.Serializable;

/**
 * Title: AdminOrderCourse<br>
 * Description: AdminOrderCourse<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月30日 下午3:08:40
 * 
 * @author ivan.mgh
 */
public class AdminOrderCourse implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = -1253520469223682464L;

  private String leadId;

  public String getLeadId() {
    return leadId;
  }

  public void setLeadId(String leadId) {
    this.leadId = leadId;
  }

}
