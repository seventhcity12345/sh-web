/** 
 * File: LALALAController.java<br> 
 * Project: hwj_svn<br> 
 * Package: com.webi.hwj.demo.controller<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年9月22日 下午10:42:42
 * @author yangmh
 */
package com.webi.hwj.locktest.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webi.hwj.locktest.service.LockTestService;

/**
 * Title: 锁表测试类<br>
 * Description: LALALAController<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月22日 下午10:42:42
 * 
 * @author yangmh
 */
@Controller
@RequestMapping("/yangmh")
public class LockTestController {
  @Resource
  private LockTestService lockTestService;

  @RequestMapping("/aaa")
  public void aaa() throws Exception {
    lockTestService.aaa();
  }

  @RequestMapping("/bbb")
  public void bbb() throws Exception {
    lockTestService.bbb();
  }
}
