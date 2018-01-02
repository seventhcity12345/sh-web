/** 
 * File: MapUtil.java<br> 
 * Project: hwj_svn<br> 
 * Package: com.webi.hwj.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月11日 下午2:22:31
 * @author yangmh
 */
package com.webi.hwj.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: MapUtil<br>
 * Description: MapUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月11日 下午2:22:31
 * 
 * @author yangmh
 */
public class MapUtil {
  public static void main(String[] args) {

  }

  /**
   * bean转map Title: bean转map<br>
   * Description: bean转map<br>
   * CreateDate: 2015年8月11日 下午2:29:05<br>
   * 
   * @category bean转map
   * @author yangmh
   * @param bean
   * @return
   * @throws Exception
   */
  public static Map convertBean(Object bean) throws Exception {
    Class type = bean.getClass();
    Map returnMap = new HashMap();
    BeanInfo beanInfo = Introspector.getBeanInfo(type);

    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    for (int i = 0; i < propertyDescriptors.length; i++) {
      PropertyDescriptor descriptor = propertyDescriptors[i];
      String propertyName = descriptor.getName();
      if (!propertyName.equals("class")) {
        Method readMethod = descriptor.getReadMethod();
        Object result = readMethod.invoke(bean, new Object[0]);
        if (result != null) {
          returnMap.put(propertyName, result);
        } else {
          returnMap.put(propertyName, null);
        }
      }
    }
    return returnMap;
  }
}
