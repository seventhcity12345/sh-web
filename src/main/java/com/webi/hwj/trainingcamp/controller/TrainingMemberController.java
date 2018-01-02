package com.webi.hwj.trainingcamp.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webi.hwj.trainingcamp.service.TrainingMemberService;

/**
 * @category trainingMember控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/trainingMember")
public class TrainingMemberController {
  private static Logger logger = Logger.getLogger(TrainingMemberController.class);
  @Resource
  private TrainingMemberService trainingMemberService;

}