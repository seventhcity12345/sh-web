package com.webi.hwj.notice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.notice.entity.Notice;
import com.webi.hwj.notice.service.NoticeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class NoticeServiceTest {

  @Resource
  private NoticeService noticeService;

  @Test
  public void demo() {

  }

  /**
   * Title: 查找公告信息列表 <br>
   * Description: 查找公告信息列表 <br>
   * CreateDate: 2016年7月29日 上午10:43:14<br>
   * 
   * @category 查找公告信息列表
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findNoticePageEasyuiTest() throws Exception {

    String sort = null;
    String order = "desc";
    Integer page = 1;
    Integer rows = 10;

    Page p = noticeService.findNoticePageEasyui(null, sort, order, page, rows);

    System.out.println(p);
  }

  /**
   * Title: 新增公告<br>
   * Description: 新增公告<br>
   * CreateDate: 2016年7月29日 上午11:41:13<br>
   * 
   * @category 新增公告
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void insertNoticeTest() throws Exception {
    Notice notice = new Notice();

    notice.setAdminUserName("测试管理员");
    notice.setCreateUserId("3935209efe4c47249c97e77d79e177f8");
    notice.setUpdateUserId("3935209efe4c47249c97e77d79e177f8");

    notice.setNoticeTitle("测试111");
    notice.setNoticeContent("ceshi");
    notice.setNoticeStartTime(new Date());
    notice.setNoticeEndTime(new Date(new Date().getTime() + 1 * 24 * 60 * 60 * 1000));

    int num = noticeService.insert(notice);

    System.out.println(num);

    Assert.assertEquals(1, num);
  }

  /**
   * Title: 编辑公告<br>
   * Description: 编辑公告<br>
   * CreateDate: 2016年7月29日 上午11:41:55<br>
   * 
   * @category 编辑公告
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void updateNoticeTest() throws Exception {

    Notice notice = new Notice();

    notice.setUpdateUserId("3935209efe4c47249c97e77d79e177f8");

    notice.setKeyId("00034f2c314c4128917391309594616f");
    notice.setNoticeTitle("测试222");
    notice.setNoticeContent("ceshi222");
    notice.setNoticeStartTime(new Date());
    notice.setNoticeEndTime(new Date(new Date().getTime() + 1 * 24 * 60 * 60 * 1000));

    int num = noticeService.update(notice);

    System.out.println(num);

    Assert.assertEquals(1, num);
  }

  /**
   * Title: 删除公告信息<br>
   * Description: 删除公告信息<br>
   * CreateDate: 2016年7月29日 上午11:57:55<br>
   * 
   * @category 删除公告信息
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void deleteNoticeTest() throws Exception {

    Map<String, Object> paramMap = new HashMap<String, Object>();

    paramMap.put("keys", "3a89bd76c7a24bffb3d7a2a2136e35d4,853d8e5ecf5d4804b3794fb1d3d5f83f");
    String updateUserId = "";

    int num = noticeService.batchDeleteNotice(paramMap, updateUserId);

    System.out.println(num);

    // Assert.assertEquals(2, num);
  }

  /**
   * Title: 前端查找公告信息接口<br>
   * Description: 前端查找公告信息接口<br>
   * CreateDate: 2016年8月1日 上午10:46:59<br>
   * 
   * @category 前端查找公告信息接口
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findNoticeInfoTest() throws Exception {
    List<Notice> noticeList = noticeService.findNoticeListByUsed();

    System.out.println(noticeList);
  }

  /**
   * Title: 查找当前生效中的banner信息<br>
   * Description: 查找当前生效中的banner信息<br>
   * CreateDate: 2016年12月19日 下午2:43:13<br>
   * 
   * @category 查找当前生效中的banner信息
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void findBannerListByUsed() throws Exception {
    List<Notice> noticeList = noticeService.findBannerListByUsed();

    System.out.println(noticeList);
  }

  /**
   * 
   * Title: 查询公告测试<br>
   * Description: findNoticeListNewTest<br>
   * CreateDate: 2017年7月20日 下午4:18:16<br>
   * 
   * @category 查询公告测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findNoticeListNewTest() throws Exception {
    System.out.println(noticeService.findNoticeListNew(DateUtil.strToDateYYYYMMDD(
        "2017-07-20 15:00:00")));
  }

}
