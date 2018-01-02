package com.webi.hwj.baidu.util;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Title: 百度接口测试<br> 
 * Description: Test<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年4月1日 上午10:58:03 
 * @author komi.zsy
 */
public class Test{

    private static final String CORPID = "weibo";// 分配的corpid、tpl
    private static final String SIGNKEY = "GU3vzkCh";// 分配的signkey
    private static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3//sR2tXw0wrC2DySx8vNGlqt3Y7ldU9+LBLI6e1KS5lfc5jlTGF7KBTSkCHBM3ouEHWqp1ZJ85iJe59aF5gIB2klBd6h4wrbbHA2XE1sq21ykja/Gqx7/IRia3zQfxGv/qEkyGOx+XALVoOlZqDwh76o2n1vP1D+tD3amHsK7QIDAQAB";

    public static void main(String[] args) {
        try{
          
//          Map<String, String> paramMap = new HashMap<String, String>();
//          paramMap.put("orderid", "00e78e7e0b9048169cb1aa0684cbb90c");
//          paramMap.put("status", "5");
////          paramMap.put("period", null);
//          //我们自己拿收到的明文参数加密一下
//          String signStr = Sign.createBaseSign(paramMap, SIGNKEY);
          
//          System.out.println(signStr);
          
            Map<String, String> userMap = new HashMap<String, String>();
            userMap.put("username", "张三222");
            userMap.put("mobile", "13321995977");
            String jsonStr = JSON.toJSONString(userMap);
            String rsaStr = Rsa.encrypt(Rsa.getPublicKey(PUBLICKEY), jsonStr);

            System.out.println("rsaStr:  " + rsaStr);

            Map<String, String> map = new HashMap<String, String>();
            map.put("action", "sync_order_info");
            map.put("corpid", CORPID);
            map.put("orderid", "0132232b04174b82a6542b3ee87b812f");
            map.put("money", "11111111");
            map.put("oauthid", "111");
            map.put("courseid", "KAHXS71101029");
            map.put("coursename", "你还是");
            map.put("tpl", CORPID);
            map.put("channel", "channe1");
            map.put("data", rsaStr);

            // 获取sign
            String sign = Sign.createBaseSign(map, SIGNKEY);
            map.put("sign", sign);

            // post 请求
            Set<Entry<String, String>> entries = map.entrySet();
            String postStr = "";
            for (Entry<String, String> entry : entries) {
                postStr += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8") + "&";
            }
            System.out.println("postStr:  " + postStr);

            String url = "http://vipabc.umoney.baidu.com/edu/openapi/post";// 联调地址
            //String url = "https://umoney.baidu.com/edu/openapi/post";// 线上地址

            String result =HttpRequest.sendPost(url, postStr);
            System.out.println(JSONObject.parse(result));
        } catch(Exception e){
          //
        }
    }
}
