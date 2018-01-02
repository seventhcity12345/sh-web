/** 
 * File: SignMessageUtil.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月15日 上午10:48:26
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webi.hwj.kuaiqian.bean.KuaiqianParameterConstant;

/**
 * 
 * Title: 虚拟提交快钱表单<br>
 * Description: SubmitPostUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年1月27日 下午5:23:06
 * 
 * @author athrun.cw
 */
public class SubmitPostUtil {

  /**
   * 
   * Title: 组装快钱提交 虚拟表单<br>
   * Description: buildPostRequestForm<br>
   * CreateDate: 2016年1月27日 下午5:32:51<br>
   * 
   * @category 组装快钱提交 虚拟表单
   * @author athrun.cw
   * @param sParaTemp
   * @param formSubmitType
   * @param submitButtonName
   * @return
   */
  public static String buildPostRequestForm(Map<String, String> splitOrderMap,
      String formSubmitType, String submitButtonName) {
    // 待请求参数数组
    Map<String, String> sPara = paraFilter(splitOrderMap);
    List<String> keys = new ArrayList<String>(sPara.keySet());

    StringBuffer sbHtml = new StringBuffer();

    sbHtml.append("<form id=\"kuaiqiansubmit\" name=\"kuaiqiansubmit\" action=\""
        + KuaiqianParameterConstant.KUAIQIAN_PAY_URL_PRO
        + "\" method=\"" + formSubmitType + "\">");

    for (int i = 0; i < keys.size(); i++) {
      String name = (String) keys.get(i);
      String value = (String) sPara.get(name);

      sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    }

    // submit按钮控件请不要含有name属性
    sbHtml.append("<input type=\"submit\" value=\"" + submitButtonName
        + "\" style=\"display:none;\"></form>");
    sbHtml.append("<script>document.forms['kuaiqiansubmit'].submit();</script>");

    return sbHtml.toString();
  }

  /**
   * 
   * Title: 除去数组中的空值和签名参数<br>
   * Description: paraFilter<br>
   * CreateDate: 2016年1月27日 下午5:41:18<br>
   * 
   * @category 除去数组中的空值和签名参数
   * @author athrun.cw
   * @param sArray
   * @return
   */
  public static Map<String, String> paraFilter(Map<String, String> sArray) {

    Map<String, String> result = new HashMap<String, String>();

    if (sArray == null || sArray.size() <= 0) {
      return result;
    }

    for (String key : sArray.keySet()) {
      String value = sArray.get(key);
      if (value == null || value.equals("")) {
        continue;
      }
      result.put(key, value);
    }

    return result;
  }

}
