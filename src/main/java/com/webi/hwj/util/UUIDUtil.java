/** 
 * File: UUIDUtil.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月20日 上午11:29:29
 * @author athrun.cw
 */
package com.webi.hwj.util;

import java.util.UUID;

/**
 * Title: Log日志，需要30位的keyId Description: UUIDUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月20日 上午11:29:29
 * 
 * @author athrun.cw
 */
public class UUIDUtil {

  /**
   * 
   * Title: 截取30位uuid Description: uuid<br>
   * CreateDate: 2015年8月20日 上午11:43:56<br>
   * 
   * @category uuid
   * @author athrun.cw
   * @return
   */
  public static String uuid(int length) {
    String oraUUID = UUID.randomUUID().toString().replaceAll("-", "");
    return oraUUID.substring(0, length);
  }

  public static void main(String[] args) {
    System.out.println(uuid(30));
    System.out.println(uuid(30).length());
  }

}
