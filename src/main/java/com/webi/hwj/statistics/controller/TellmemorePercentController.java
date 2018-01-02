package com.webi.hwj.statistics.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webi.hwj.statistics.service.TellmemorePercentService;

/**
 * @category tellmemorePercent控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/tellmemorePercent")
public class TellmemorePercentController {
  private static Logger logger = Logger.getLogger(TellmemorePercentController.class);
  @Resource
  private TellmemorePercentService tellmemorePercentService;

}