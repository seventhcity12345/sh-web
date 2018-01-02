package com.webi.hwj.admin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webi.hwj.admin.service.AdminConfigService;

/**
 * Title: 主从数据库读写分离测试<br> 
 * Description: 测试之后要删掉<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年4月12日 上午11:38:21 
 * @author komi.zsy
 */
@Controller
public class AdminMasterAndSalveDatabase {
  private static Logger logger = Logger.getLogger(AdminMasterAndSalveDatabase.class);
  @Resource
  private AdminConfigService adminConfigService;

  /**
   * Title: 查找老师来源<br>
   * Description: 根据码表type查找老师来源<br>
   * CreateDate: 2016年12月14日 下午4:17:54<br>
   * 
   * @category 查找老师来源
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/masterAndSalveDatabase/findTeacherThirdFrom")
  public List<Map<String, Object>> findTeacherThirdFrom(HttpServletRequest request)
      throws Exception {
    return adminConfigService.findTeacherThirdFrom();
  }
}
