package com.webi.hwj.trainingcamp.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webi.hwj.trainingcamp.service.TrainingMemberOptionService;

/**
 * @category trainingMemberOption控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/trainingMemberOption")
public class TrainingMemberOptionController {
  private static Logger logger = Logger.getLogger(TrainingMemberOptionController.class);
  @Resource
  private TrainingMemberOptionService trainingMemberOptionService;

}