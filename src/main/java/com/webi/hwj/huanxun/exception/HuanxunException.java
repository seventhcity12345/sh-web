/** 
 * File: HuanxunException.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.huanxun.exception<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年1月26日 下午6:55:06
 * @author yangmh
 */
package com.webi.hwj.huanxun.exception;

/**
 * Title: HuanxunException<br>
 * Description: HuanxunException<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年1月26日 下午6:55:06
 * 
 * @author yangmh
 */
// modify by seven 这样会发生死锁 将删除的方法放到预约事物外面 用于传递返回值
// 为了能回滚将huanxun的类修改为RuntimeException
public class HuanxunException extends RuntimeException {
  public HuanxunException(String msg) {
    super(msg);
  }
}