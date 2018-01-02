package com.webi.hwj.baidu;

import javax.annotation.Resource;

import org.junit.Test;

import com.webi.hwj.baidu.service.BaiduService;

public class BaiduServiceTest {
  
  @Resource
  BaiduService baiduService;

  @Test
  public void demo(){
    
  }
  
//  @Test
//  public void syncOrderInfoTest(){
//    String url = baiduService.syncOrderInfo(sessionUser.getKeyId(), sessionUser.getUserName(),
//        sessionUser.getPhone(), orderCourseSplitObj.getKeyId(),
//        orderCourseSplitObj.getSplitPrice().toString(),orderCourseSplitObj.getCoursePackageName());
//  }
  
}
