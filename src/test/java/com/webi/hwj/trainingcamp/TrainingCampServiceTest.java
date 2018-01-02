package com.webi.hwj.trainingcamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.HttpClientUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.param.EasyuiSearchParam;
import com.webi.hwj.trainingcamp.entity.TrainingCamp;
import com.webi.hwj.trainingcamp.param.TrainingCampClanIntegralRankingApiParam;
import com.webi.hwj.trainingcamp.param.TrainingCampClanMemberApiParam;
import com.webi.hwj.trainingcamp.param.TrainingCampIntegralProfileParam;
import com.webi.hwj.trainingcamp.param.TrainingCampParam;
import com.webi.hwj.trainingcamp.service.TrainingCampService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class TrainingCampServiceTest {

  @Resource
  TrainingCampService trainingCampService;

  @Test
  public void findTrainingCampInfoTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<TrainingCampParam> json = new CommonJsonObject<TrainingCampParam>();

    // 构建参数
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("52b4dc83fc554a79857f5d5ff5ade251");

    // 调用Service
    TrainingCampParam trainingCampInfo = trainingCampService.findTrainingCampInfo(sessionUser);

    json.setData(trainingCampInfo);

    Assert.assertEquals(200, json.getCode());
  }

  @Test
  public void findTrainingCampIntegralProfileParmInfoTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<TrainingCampIntegralProfileParam> json =
        new CommonJsonObject<TrainingCampIntegralProfileParam>();

    // 构建参数
    SessionUser sessionUser = new SessionUser();
    // sessionUser.setKeyId("0104dc299a5348f6965c6c37ae49e857");
    sessionUser.setKeyId("0104dc299a5348f6965c6c37ae49e857");

    // 调用Service
    TrainingCampIntegralProfileParam trainingCampIntegralProfileInfo = trainingCampService
        .findTrainingCampIntegralProfileParmInfo(sessionUser);

    json.setData(trainingCampIntegralProfileInfo);

    Assert.assertEquals(200, json.getCode());
  }

  @Test
  public void findTrainingCampClanIntegralRankingInfoTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<TrainingCampClanIntegralRankingApiParam> json =
        new CommonJsonObject<TrainingCampClanIntegralRankingApiParam>();

    // 构建参数
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("0104dc299a5348f6965c6c37ae49e857");
    // sessionUser.setKeyId("123");

    // 调用Service
    TrainingCampClanIntegralRankingApiParam trainingCampClanIntegralRankingInfo =
        trainingCampService.findTrainingCampClanIntegralRankingInfo(sessionUser);

    json.setData(trainingCampClanIntegralRankingInfo);

    Assert.assertEquals(200, json.getCode());
  }

  @Test
  public void findTrainingCampClanMembersInfoTest() throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<TrainingCampClanMemberApiParam> json =
        new CommonJsonObject<TrainingCampClanMemberApiParam>();

    // 构建参数
    SessionUser sessionUser = new SessionUser();
    sessionUser.setKeyId("52b4dc83fc554a79857f5d5ff5ade251");

    // 调用Service
    TrainingCampClanMemberApiParam trainingCampClanMemberApiParam = trainingCampService
        .findTrainingCampClanMembersInfo(sessionUser, "0", "15");

    json.setData(trainingCampClanMemberApiParam);

    Assert.assertEquals(200, json.getCode());
  }
  
  /**
   * Title: 查询所有训练营列表<br>
   * Description: 查询所有训练营列表<br>
   * CreateDate: 2017年8月8日 下午4:55:42<br>
   * @category 查询所有训练营列表 
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void pagelistTest() throws Exception {
    EasyuiSearchParam easyuiSearchParam = new EasyuiSearchParam();
    Map<String, Object> responseMap = new HashMap<String, Object>();
    Page p = trainingCampService.findPageEasyui(easyuiSearchParam.getCons(), easyuiSearchParam
        .getSort(), easyuiSearchParam.getOrder(), easyuiSearchParam.getPage(), easyuiSearchParam.getRows());
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());
    System.out.println(p);
  }
  
  /**
   * Title: 批量删除训练营<br>
   * Description: 批量删除训练营<br>
   * CreateDate: 2017年8月10日 下午4:25:48<br>
   * @category 批量删除训练营 
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void deleteTrainingCamp() throws Exception {
    String keys = "1d634c1bbbdd4ffe8d6a2daa64b6be03,3a0fed7648a7418bba78c64dc41c43aa";
    String updateUserId = "111";
    
    Assert.assertEquals(2, trainingCampService.delete(keys,updateUserId));
  }
  
  
  /**
   * Title: 新增/编辑训练营数据<br>
   * Description: 新增/编辑训练营数据<br>
   * CreateDate: 2017年8月14日 上午10:03:17<br>
   * @category 新增/编辑训练营数据 
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @Test
  public void updateTrainingCamp() throws Exception {
    TrainingCamp trainingCamp = new TrainingCamp();
    trainingCamp.setTrainingCampTitle("测试111");
    trainingCamp.setTrainingCampStartTime(new Date());
    trainingCamp.setTrainingCampEndTime(new Date());
    trainingCamp.setTrainingCampDesc("ceshidescription");
    String updateUserId = "111";
    
    byte[] a = HttpClientUtil.doGetReturnByte(
        "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images/course/englishstudio/0301-0312/Culture%20Shock.jpg");

    MultipartFile trainingCampPic = new MockMultipartFile("alex1", "a.jpg", null, a);
   
    //新增/编辑训练营数据
    trainingCampService.saveTrainingCamp(trainingCamp, trainingCampPic,updateUserId);
  }

}
