package com.webi.hwj.baidu.constant;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.constant.ConfigConstant;

/**
 * Title: 百度分期常量类<br> 
 * Description: BaiduConstant<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年4月1日 上午9:40:31 
 * @author komi.zsy
 */
public class BaiduConstant {
  
  /**
   * action：同步订单数据
   */
  public static final String SYNC_ORDER_INFO = "sync_order_info";
  
  /**
   * action：同步订单数据
   */
  public static final String GET_ORDER_STATUS = "get_order_status";
  
  /**
   * 状态码 ： 0 成功 
   */
  public static final String BAIDU_STATUS_SUCCESS = "0";
  
  /**
   * 状态码 ： 1 订单号不存在
   */
  public static final String BAIDU_STATUS_ORDER_NOT_EXIST = "1";
  
  /**
   * 状态码 ： 2 参数错误
   */
  public static final String BAIDU_STATUS_PARAM_ERROR = "2";
  
  /**
   * Title: 获取 机构ID<br>
   * Description: 机构ID（corpid）和   产品编码（tpl） 分配的corpid、tpl<br>
   * CreateDate: 2017年3月31日 下午1:54:17<br>
   * @category getCorpid 
   * @author komi.zsy
   * @return
   */
  public static final String getCorpid(){
    return MemcachedUtil.getConfigValue(ConfigConstant.BAIDU_CORPID);
  }
  
  /**
   * Title: 获取SignKey<br>
   * Description: 分配的signkey<br>
   * CreateDate: 2017年3月31日 下午1:55:01<br>
   * @category getSignKey 
   * @author komi.zsy
   * @return
   */
  public static final String getSignKey(){
    return MemcachedUtil.getConfigValue(ConfigConstant.BAIDU_SIGN_KEY);
  }

  /**
   * Title: 获取RSA公钥<br>
   * Description: 分配的RSA公钥<br>
   * CreateDate: 2017年3月31日 下午1:55:55<br>
   * @category getPublicKey 
   * @author komi.zsy
   * @return
   */
  public static final String getPublicKey(){
    return MemcachedUtil.getConfigValue(ConfigConstant.BAIDU_PUBLICKEY);
  }

  /**
   * Title: 获取百度接口Url<br>
   * Description: getUrl<br>
   * CreateDate: 2017年3月31日 下午2:00:12<br>
   * @category getUrl 
   * @author komi.zsy
   * @return
   */
  public static final String getUrl(){
    return MemcachedUtil.getConfigValue(ConfigConstant.BAIDU_URL);
  }
  
  /**
   * Title: 获取课程id<br>
   * Description: 获取课程id<br>
   * CreateDate: 2017年7月24日 下午6:30:25<br>
   * @category 获取课程id 
   * @author komi.zsy
   * @return
   */
  public static final String getCourseId(){
    return MemcachedUtil.getConfigValue(ConfigConstant.BAIDU_COURSE_ID);
  }

}
