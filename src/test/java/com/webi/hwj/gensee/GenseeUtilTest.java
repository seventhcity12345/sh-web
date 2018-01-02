package com.webi.hwj.gensee;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.gensee.constant.GenseeConstant;
import com.webi.hwj.gensee.entity.Gensee;
import com.webi.hwj.gensee.util.GenseeUtil;
import com.webi.hwj.util.TxtUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class GenseeUtilTest {
  @Test
  public void demo() {

  }

  // @Resource
  // SubscribeCourseService subscribeCourseService;

  // @Test
  public void createRoomTest() throws Exception {

    String startDateStr = "2016-07-11 15:00:00";
    String endDateStr = "2016-07-11 18:30:00";
    String courseType = "course_type8";
    String teacherName = "teacherName";
    String teacherDesc = "teacherDesc";
    String courseDesc = "courseDesc";

    Gensee gensee = GenseeUtil.createRoom(GenseeConstant.GENSEE_SCENE_BIG, "alex哇咔咔hey,hey",
        courseType,
        DateUtil.strToDateYYYYMMDDHHMMSS(startDateStr),
        DateUtil.strToDateYYYYMMDDHHMMSS(endDateStr),teacherDesc,teacherName,courseDesc);

    System.out.println(gensee + "---");
  }

  // @Test
  public void uploadCoursewareTest() throws Exception {
    String resourceUrl = "http://webi-hwj-test.oss-cn-hangzhou.aliyuncs.com/courseware/one2one/General%20Level%201/Main%20Course%20Level%201_Lesson%201_On%20the%20Phone.pdf";
    String documentId = GenseeUtil.uploadCourseware("aaa", resourceUrl);
    System.out.println(documentId + "---");
  }

  // @Test
  public void attachFileTest() throws Exception {
    String roomId = "uheguUXvR2";
    String docId = "1009411";

    GenseeUtil.attachFile(roomId, docId);

  }

  /**
   * Title: 获取展示互动录像<br>
   * Description: xxTest<br>
   * CreateDate: 2016年9月27日 下午5:01:57<br>
   * @category xxTest 
   * @author komi.zsy
   * @throws Exception
   */
//  @Test
  public void xxTest() throws Exception {
//    String roomId2 = "O3dQVo7EfG";
//    GenseeUtil.watchVideo(roomId2, "alex");
    
    String loadAddress = "d:\\gensee_data.txt";
    List<List<String>> genseeList = TxtUtil.loadTxtFile(loadAddress);
    
    int id = 1;
    int errorNum = 0;
    
    // txt的格式为：房间号，时间
    for (final List<String> gensee : genseeList) {
      String roomId = gensee.get(0);
      try {
        method2("d:\\aa.txt","{\"id\":\""+id+"\",\"time\":\""+gensee.get(1) + "\",\"url\":\"" + GenseeUtil.watchVideo(roomId, "alex")+"\"},");
        id++;
      } catch (Exception e) {
        System.out.println("aaaaaaaaaaaa"+roomId);
        errorNum++;
      }
    }
    System.out.println("错误视频："+errorNum);
  }

  public static void method2(String file, String conent) {
    BufferedWriter out = null;
    try {
      out = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(file, true)));
      out.write(conent + "\r\n");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
