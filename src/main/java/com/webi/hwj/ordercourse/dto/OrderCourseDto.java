/** 
 * File: OrderCourseDto.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.ordercourse.cto<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年3月25日 上午11:19:37
 * @author ivan.mgh
 */
package com.webi.hwj.ordercourse.dto;

import java.io.Serializable;

/**
 * Title: 合同数据传输类<br>
 * Description: 合同数据传输类<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月25日 上午11:19:37
 * 
 * @author ivan.mgh
 */
public class OrderCourseDto implements Serializable {
  /** 
  * 
  */
  private static final long serialVersionUID = 7034940084386711709L;

  private String contractGuid;
  private String contractState;

  public String getContractGuid() {
    return contractGuid;
  }

  public void setContractGuid(String contractGuid) {
    this.contractGuid = contractGuid;
  }

  public String getContractState() {
    return contractState;
  }

  public void setContractState(String contractState) {
    this.contractState = contractState;
  }

}
