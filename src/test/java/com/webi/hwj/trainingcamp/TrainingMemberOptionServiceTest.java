package com.webi.hwj.trainingcamp;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.trainingcamp.entity.TrainingMemberOption;
import com.webi.hwj.trainingcamp.service.TrainingMemberOptionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class TrainingMemberOptionServiceTest {
  
  @Resource
  TrainingMemberOptionService trainingMemberOptionService;
  
  /**
   * Title: 新增加分/扣分<br>
   * Description: 新增加分/扣分<br>
   * CreateDate: 2017年8月9日 下午4:47:45<br>
   * @category 新增加分/扣分 
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @Test
  public void insertScoreInfoTest() throws Exception{
    TrainingMemberOption trainingMemberOption = new TrainingMemberOption();
    trainingMemberOption.setTrainingMemberOptionReason("ceshi1");
    trainingMemberOption.setTrainingMemberOptionScore(10);
    trainingMemberOption.setTrainingMemberOptionType(true);
    trainingMemberOption.setTrainingCampId("");
    trainingMemberOption.setTrainingMemberUserId("");
    //直接在数据库中存-100至100分，方便计算
    if(!trainingMemberOption.getTrainingMemberOptionType()){
      trainingMemberOption.setTrainingMemberOptionScore(-trainingMemberOption.getTrainingMemberOptionScore());
    }
    int num = trainingMemberOptionService.addScore(trainingMemberOption);
      Assert.assertEquals(0, num);
  }

}
