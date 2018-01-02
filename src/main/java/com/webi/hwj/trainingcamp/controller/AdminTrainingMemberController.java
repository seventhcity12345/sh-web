package com.webi.hwj.trainingcamp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.param.EasyuiSearchParam;
import com.webi.hwj.trainingcamp.service.TrainingMemberService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category trainingMember控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
public class AdminTrainingMemberController {
  private static Logger logger = Logger.getLogger(AdminTrainingMemberController.class);
  @Resource
  private TrainingMemberService trainingMemberService;

  /**
   * Title: 根据训练营查询成员列表<br>
   * Description: 根据训练营查询成员列表<br>
   * CreateDate: 2017年8月9日 下午4:12:11<br>
   * 
   * @category 根据训练营查询成员列表
   * @author komi.zsy
   * @param easyuiSearchParam
   *          easyui一些排序参数
   * @param trainingCampId
   *          训练营id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/admin/trainingMemberList/{trainingCampId}")
  public Map<String, Object> pagelist(EasyuiSearchParam easyuiSearchParam, @PathVariable(
      value = "trainingCampId") String trainingCampId) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    Page p = trainingMemberService.findPageEasyui(easyuiSearchParam.getCons(), easyuiSearchParam
        .getSort(), easyuiSearchParam.getOrder(), easyuiSearchParam.getPage(), easyuiSearchParam
            .getRows(), trainingCampId);
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());
    return responseMap;
  }

  /**
   * Title: 查询所有不在有效训练营中的学员<br>
   * Description: 查询所有不在有效训练营中的学员<br>
   * CreateDate: 2017年9月12日 下午7:47:17<br>
   * 
   * @category 查询所有不在有效训练营中的学员
   * @author komi.zsy
   * @param easyuiSearchParam
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/admin/notTrainingMemberList")
  public Map<String, Object> pagelistByNotInTrainingCamp(EasyuiSearchParam easyuiSearchParam)
      throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    Page p = trainingMemberService.pagelistByNotInTrainingCamp(easyuiSearchParam.getCons(),
        easyuiSearchParam
            .getSort(), easyuiSearchParam.getOrder(), easyuiSearchParam.getPage(), easyuiSearchParam
                .getRows());
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());
    return responseMap;
  }

  /**
   * Title: 新增训练营成员<br>
   * Description: 新增训练营成员<br>
   * CreateDate: 2017年9月13日 上午11:15:56<br>
   * @category 新增训练营成员 
   * @author komi.zsy
   * @param request
   * @param keys
   * @param trainingCampId 训练营id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/admin/trainingMember", method = RequestMethod.POST)
  public CommonJsonObject<Object> addTrainingMember(HttpServletRequest request, String keys,String trainingCampId)
      throws Exception {
    CommonJsonObject<Object> json = new CommonJsonObject<Object>();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    //新增训练营成员
    trainingMemberService.addTrainingMember(keys,trainingCampId,sessionAdminUser.getKeyId());
    
    return json;
  }
  
  /**
   * Title: 删除训练营成员<br>
   * Description: 删除训练营成员<br>
   * CreateDate: 2017年9月13日 上午11:34:17<br>
   * @category 删除训练营成员 
   * @author komi.zsy
   * @param request
   * @param keys
   * @param trainingCampId 训练营id
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/admin/trainingMemberDeletion", method = RequestMethod.POST)
  public CommonJsonObject<Object> deleteTrainingMember(HttpServletRequest request, String keys,String trainingCampId)
      throws Exception {
    CommonJsonObject<Object> json = new CommonJsonObject<Object>();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    //删除训练营成员
    trainingMemberService.deleteTrainingMember(keys,trainingCampId,sessionAdminUser.getKeyId());
    
    return json;
  }

}