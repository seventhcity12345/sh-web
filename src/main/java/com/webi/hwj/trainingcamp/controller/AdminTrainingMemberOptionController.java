package com.webi.hwj.trainingcamp.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.trainingcamp.entity.TrainingMemberOption;
import com.webi.hwj.trainingcamp.service.TrainingMemberOptionService;

/**
 * @category trainingMemberOption控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
public class AdminTrainingMemberOptionController {
  private static Logger logger = Logger.getLogger(AdminTrainingMemberOptionController.class);
  @Resource
  private TrainingMemberOptionService trainingMemberOptionService;
  
  /**
   * Title: 新增加分/扣分<br>
   * Description: 新增加分/扣分<br>
   * CreateDate: 2017年8月9日 下午4:47:45<br>
   * @category 新增加分/扣分 
   * @author komi.zsy
   * @param trainingMemberOption
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/admin/trainingMemberOption",method = RequestMethod.POST)
  public CommonJsonObject<Object> insertScoreInfo(TrainingMemberOption trainingMemberOption) throws Exception{
    CommonJsonObject<Object> json = new CommonJsonObject<Object>();
    //直接在数据库中存-100至100分，方便计算
    if(!trainingMemberOption.getTrainingMemberOptionType()){
      trainingMemberOption.setTrainingMemberOptionScore(-trainingMemberOption.getTrainingMemberOptionScore());
    }
    int num = trainingMemberOptionService.addScore(trainingMemberOption);
    if(num == 0){
      json.setCode(ErrorCodeEnum.UPDATE_ERROR.getCode());
    }
    return json;
  }

}