/** 
 * File: HuanxunApiTest.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.huanxun<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年12月29日 下午4:36:46
 * @author yangmh
 */
package com.webi.hwj.huanxun;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.webi.hwj.huanxun.service.HuanXunApiService;

import net.sf.json.JSONObject;

/**
 * Title: 环迅API测试用例<br>
 * Description: HuanxunApiTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月29日 下午4:36:46
 * 
 * @author yangmh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class HuanxunApiTest {
  @Resource
  HuanXunApiService huanXunApiService;

  @Test
  public void demo() {

  }

  /**
   * Title: 预约课程<br>
   * Description: book<br>
   * CreateDate: 2015年12月29日 下午4:46:11<br>
   * 
   * @category 预约课程
   * @author yangmh
   * @throws Exception
   */
  // @Test
  public void book() throws Exception {
    String url = "http://apitest.121learn.com/api/v1/wb/book";
    String paramJsonStr = "{\"course_type\":\"1v1\","
        + "\"timestamp\":\"" + (System.currentTimeMillis() + "").substring(0, 11) + "\","
        + "\"partner\":\"8884f8892b41411c92d8c01788cac547\","
        + "\"teacher_id\":\"d40a97bc70fb4e65b1a3ed03da987905\","
        + "\"start_time\":\"2016-01-02 11:00:00\","
        + "\"end_time\":\"2016-01-02 11:25:00\","
        + "\"course_courseware\":\"http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/courseware%2Fone2one%2FGeneral_Level_5%2FMain%20Course%20Level%205_Lesson%205_The%20Bill.pptx\","
        + "\"user_id\":\"3217f5d667c34380a12ce7651bc2zzzz\","
        + "\"nick_name\":\"啊啊啊啊阿里巴巴\""
        + "}";
    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("Authorization", "Token cf1df1d83da12f7dbf9e94c6183f90fe28c8c5aa");
    String returnStr = HttpClientUtil.doPostByJson(url, paramJsonStr, headerMap);
    JSONObject returnJson = JSONObject.fromObject(returnStr);
    System.out.println("post请求------>msg=" + returnJson.getString("msg") + ",code="
        + returnJson.getString("code"));
    System.out.println(returnJson.getString("code"));
    System.out.println("--------" + returnStr);
  }

  /**
   * Title: 取消预约<br>
   * Description: cancel<br>
   * CreateDate: 2015年12月29日 下午4:49:31<br>
   * 
   * @category 取消预约
   * @author yangmh
   * @throws Exception
   */
  // @Test
  public void cancel() throws Exception {
    String url = "http://apitest.121learn.com/api/v1/classes/partner/wb/cancel";
    String paramJsonStr = "{\"timestamp\":\"" + (System.currentTimeMillis() + "").substring(0, 11)
        + "\","
        + "\"partner\":\"8884f8892b41411c92d8c01788cac547\","
        + "\"start_time\":\"2015-12-28 13:00:00\","
        + "\"end_time\":\"2015-12-28 13:25:00\","
        + "\"user_id\":\"3217f5d667c34380a12ce7651bc2715d\""
        + "}";

    Map<String, String> headerMap = new HashMap<String, String>();
    headerMap.put("Authorization", "Token cf1df1d83da12f7dbf9e94c6183f90fe28c8c5aa");
    String returnStr = HttpClientUtil.doPostByJson(url, paramJsonStr, headerMap);
    JSONObject returnJson = JSONObject.fromObject(returnStr);
    System.out.println("post请求------>msg=" + returnJson.getString("msg") + ",code="
        + returnJson.getString("code"));
    System.out.println(returnJson.getString("code"));
    System.out.println("--------" + returnStr);
  }

  /**
   * Title: 生成环迅校验码<br>
   * Description: 生成环迅校验码<br>
   * CreateDate: 2016年8月8日 下午4:53:26<br>
   * 
   * @category 生成环迅校验码
   * @author seven.gz
   */
  // @Test
  public void createCheckCode() {
    String json = "{\"data\":[{\"key_id\":\"4735bc8df4fa406a840bc9816cca9da1\",\"teacher_name\":\"yang\",\"teacher_desc\":\"johnwasabestfriendofmineandsomethinglikethat\",\"teacher_photo\":\"http://finance.gucheng.com/UploadFiles_7830/201506/2015063021483874.jpg\",\"teacher_course_type\":\"1v1,1vn\"},{\"key_id\":\"4735bc8df4fa406a840bc9816cca9daa\",\"teacher_name\":\"johnholland\",\"teacher_desc\":\"johnwasabestfriendofmineandsomethinglikethat\",\"teacher_photo\":\"http://finance.gucheng.com/UploadFiles_7830/201506/2015063021483874.jpg\",\"teacher_course_type\":\"course_type1,course_type2\"}]}";
    String PRIVATE_KEY = "d066fbcd244e4f888481a50ed74ad842";

    System.out.println(SHAUtil.encode(json + PRIVATE_KEY));
  }

  // @Test
  public void huanXunApiServiceTest() throws Exception {
    String json = "{\"data\":[{\"key_id\":\"4735bc8df4fa406a840bc9816cca9daB\",\"teacher_name\":\"yang\",\"teacher_desc\":\"johnwasabestfriendofmineandsomethinglikethat\",\"teacher_photo\":\"http://finance.gucheng.com/UploadFiles_7830/201506/2015063021483874.jpg\",\"teacher_course_type\":\"1v1,1vn\"},{\"key_id\":\"4735bc8df4fa406a840bc9816cca9daA\",\"teacher_name\":\"yang\",\"teacher_desc\":\"johnwasabestfriendofmineandsomethinglikethat\",\"teacher_photo\":\"http://finance.gucheng.com/UploadFiles_7830/201506/2015063021483874.jpg\",\"teacher_course_type\":\"course_type1,course_type2\"}]}";

    System.out.println(huanXunApiService.pullTeacherData(json));
  }
}
