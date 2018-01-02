package com.webi.hwj.trainingcamp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.RequestUtil;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.param.EasyuiSearchParam;
import com.webi.hwj.teacher.constant.AdminTeacherConstant;
import com.webi.hwj.trainingcamp.entity.TrainingCamp;
import com.webi.hwj.trainingcamp.service.TrainingCampService;
import com.webi.hwj.util.SessionUtil;

/**
 * @category trainingCamp控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
public class AdminTrainingCampController {
  private static Logger logger = Logger.getLogger(AdminTrainingCampController.class);
  @Resource
  private TrainingCampService trainingCampService;
  
  @InitBinder
  protected void initBinder(HttpServletRequest request,
      ServletRequestDataBinder binder) throws Exception {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    // false是不允许字符串为空，true则允许
    CustomDateEditor editor = new CustomDateEditor(df, false);
    binder.registerCustomEditor(Date.class, editor);
  }

  /**
   * Title: 首页<br>
   * Description: 首页<br>
   * CreateDate: 2017年8月8日 下午3:54:02<br>
   * 
   * @category index
   * @author komi.zsy
   * @return
   */
  @RequestMapping("/api/speakhi/v1/admin/trainingCamp/index")
  public String index() {
    return "admin/subscribecourse/admin_training_camp";
  }

  /**
   * Title: 查询所有训练营列表<br>
   * Description: 查询所有训练营列表<br>
   * CreateDate: 2017年8月8日 下午4:55:42<br>
   * @category 查询所有训练营列表 
   * @author komi.zsy
   * @param easyuiSearchParam easyui一些排序参数
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/admin/trainingCampList")
  public Map<String, Object> pagelist(HttpServletRequest request,EasyuiSearchParam easyuiSearchParam) throws Exception {
    Map<String, Object> responseMap = new HashMap<String, Object>();
    Page p = trainingCampService.findPageEasyui(easyuiSearchParam.getCons(), easyuiSearchParam
        .getSort(), easyuiSearchParam.getOrder(), easyuiSearchParam.getPage(), easyuiSearchParam.getRows());
    responseMap.put("total", p.getTotalCount());
    responseMap.put("rows", p.getDatas());
    return responseMap;
  }
  
  /**
   * Title: 批量删除训练营<br>
   * Description: 批量删除训练营<br>
   * CreateDate: 2017年8月10日 下午4:25:48<br>
   * @category 批量删除训练营 
   * @author komi.zsy
   * @param keys 训练营ids
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/admin/deleteTrainingCamp")
  public CommonJsonObject<Object> deleteTrainingCamp(HttpServletRequest request,String keys) throws Exception {
    CommonJsonObject<Object> json = new CommonJsonObject<Object>();
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    // 获得后台传递的keys
    if (StringUtils.isEmpty(keys)) {
      logger.error("后台传递的参数有误！");
      throw new RuntimeException("后台传递的参数有误！");
    }
    json.setData(trainingCampService.delete(keys,sessionAdminUser.getKeyId()));
    return json;
  }
  
  
  /**
   * Title: 新增/编辑训练营数据<br>
   * Description: 新增/编辑训练营数据<br>
   * CreateDate: 2017年8月14日 上午10:03:17<br>
   * @category 新增/编辑训练营数据 
   * @author komi.zsy
   * @param request
   * @param trainingCamp
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/admin/trainingCamp",method = RequestMethod.POST)
  public CommonJsonObject<Object> updateTrainingCamp(HttpServletRequest request,TrainingCamp trainingCamp) throws Exception {
    CommonJsonObject<Object> json = new CommonJsonObject<Object>();;
    
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);
    
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultipartFile trainingCampPic = (MultipartFile) multipartRequest
        .getFiles(AdminTeacherConstant.UPLOAD_FIELD_NAME)
        .get(0);
   
    //新增/编辑训练营数据
    trainingCampService.saveTrainingCamp(trainingCamp, trainingCampPic, sessionAdminUser.getKeyId());
    
    return json;
  }

}