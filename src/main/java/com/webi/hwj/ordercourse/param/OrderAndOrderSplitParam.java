package com.webi.hwj.ordercourse.param;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

/**
 * Title: 拆分订单表和合同表信息<br> 
 * Description: 用于招联参数<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年1月19日 上午10:58:52 
 * @author komi.zsy
 */
@TableName("t_order_course_split")
public class OrderAndOrderSplitParam implements Serializable {
  private static final long serialVersionUID = -4851652953612240491L;
  // 主键id
  private String keyId;
  // 合同ID
  private String orderId;
  // 拆分金额
  private Integer splitPrice;
  // 支付状态(0:未支付-线上,1:已支付-线上,2:已支付,待确认-线下,3:已支付,已确认-线下)
  private Integer splitStatus;
  // 课程包名称
  private String coursePackageName;
  
  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getKeyId() {
    return keyId;
  }

  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Integer getSplitPrice() {
    return splitPrice;
  }

  public void setSplitPrice(Integer splitPrice) {
    this.splitPrice = splitPrice;
  }

  public Integer getSplitStatus() {
    return splitStatus;
  }

  public void setSplitStatus(Integer splitStatus) {
    this.splitStatus = splitStatus;
  }

  public String getCoursePackageName() {
    return coursePackageName;
  }

  public void setCoursePackageName(String coursePackageName) {
    this.coursePackageName = coursePackageName;
  }


}