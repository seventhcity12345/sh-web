package com.webi.hwj.user.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.admin.service.AdminBdminUserService;
import com.webi.hwj.admin.service.AdminResourceService;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.SessionUtil;

/**
 * Title: 第三方调用我们接口，既不要admin，也不要ucnter前缀<br> 
 * Description: CrmUserController<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年7月5日 上午11:35:55 
 * @author komi.zsy
 */
@Controller
public class CrmUserController {
  private static Logger logger = Logger.getLogger(CrmUserController.class);
  @Resource
  private AdminBdminUserService adminBdminUserService;
  @Resource
  private UserService userService;
  @Resource
  private AdminResourceService adminResourceService;
  
  /**
   * Title: crm查看学员详情<br>
   * Description: crm查看学员详情<br>
   * CreateDate: 2017年7月10日 下午5:00:04<br>
   * @category crm查看学员详情 
   * @author komi.zsy
   * @param request
   * @param phone
   * @param model
   * @return
   * @throws Exception
   */
  @RequestMapping("/api/speakhi/v1/userDetail")
  public String lookUserDetail(HttpServletRequest request, String phone,
      Model model) throws Exception {

    Map<String, Object> user = userService.findOneByPhone(phone);
    //如果没有用户，说明该手机号查不到用户
    if(user!=null){
      //这里跟crm确认了，说不要鉴权，自动登录一下crm账号
      Map<String, Object> adminUserMap = adminBdminUserService.findOne("account", "crm_admin",
          "key_id,account,email,pwd,create_date,admin_user_name,admin_user_type,role_id,role_name");
      SessionAdminUser sessionAdminUser = SessionUtil.initSessionAdminUser(adminUserMap, request.getSession());
      // 初始化权限
      sessionAdminUser.setPermissionMap(adminResourceService.findPermissionMap(sessionAdminUser));
      
      model.addAttribute("userId", user.get("key_id"));
      // modified by seven,2016年6月1日13:09:16
      return "/admin/user/userinfo_detail_crm";
    }
   
    return null;
  }
  
  
  /**
   * Title: 根据手机号查询该学员是否有某种课程类型上课权限<br>
   * Description: 云中心调用我们接口<br>
   * CreateDate: 2017年8月14日 上午11:52:42<br>
   * @category 根据手机号查询该学员是否有某种课程类型上课权限 
   * @author komi.zsy
   * @param phone 学员手机号
   * @param courseType 课程类型
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/userCourse",method = RequestMethod.GET)
  public CommonJsonObject<Object> findUserByCourseTypeAndUserPhone(HttpServletRequest request,
      String phone,String courseType) throws Exception {
    CommonJsonObject<Object> json = new CommonJsonObject<Object>();
    if(StringUtils.isEmpty(phone)){
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("用户手机号不能为空");
    }
    //如果不传，默认是ES
    if(StringUtils.isEmpty(courseType)){
      courseType = "course_type8";
    }
    json = userService.findUserByCourseTypeAndUserPhone(phone, courseType);
    return json;
  }

}