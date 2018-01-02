package com.webi.hwj.index.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;
import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.constant.CrmConstant;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.index.param.UserRegisterParam;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.service.UserService;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class IndexService {
  private static Logger logger = Logger.getLogger(IndexService.class);

  @Resource
  UserDao userDao;

  @Resource
  UserEntityDao userEntityDao;

  @Resource
  UserService userService;

  /**
   * Title: 学员注册.<br>
   * Description: studentRegister<br>
   * CreateDate: 2016年3月19日 下午4:40:58<br>
   * 
   * @category 学员注册
   * @author yangmh
   * @param paramForm
   *          参数bean
   * @param 学生的ip地址
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,
      rollbackFor = Exception.class)
  public CommonJsonObject<User> studentRegister(final UserRegisterParam paramForm,
      String requestIp)
      throws Exception {
    final CommonJsonObject<User> json = new CommonJsonObject<User>();
    /**
     * 手机号不允许重复判断.
     */
    final User repeatUser = userEntityDao.findOneByPhoneReturnPwd(paramForm.getPhone());

    if (repeatUser != null) {
      json.setCode(ErrorCodeEnum.USER_PHONE_EXISTS.getCode());
      return json;
    }

    // 保存用户信息
    final User returnUser = userService.insertUser(paramForm);
    // 初始化session对象
    userService.initSessionUser(returnUser.getKeyId(), null);
    json.setData(returnUser);

    /**
     * modify by komi 2016年5月26日14:26:22 crm注册异步化
     */
    try {
      logger.info("学生注册CRM异步消息开始生产------>");
      if (requestIp == null) {
        requestIp = "null";
      }
      // "&Phone=reg_phone","&ADID=ADID",ip地址（若空则默认为上海）
      String bodyStr = CrmConstant.CRMREGISTERTYPE + "," + "&Phone="
          + paramForm.getPhone() + "&ADID="
          + paramForm.getAdid() + "," + requestIp;
      OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
          MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_crm",
          bodyStr);
    } catch (Exception e) {
      SmsUtil.sendAlarmSms(
          "crm学员注册消息队列报警,无法生产消息,register,"
              + paramForm.getPhone());
      logger.error("error:" + e.toString(), e);
    }
    return json;
  }

  /**
   * 查询预约用户列表
   * 
   * @category 查询预约用户列表
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findSubscribeUserList(Map<String, Object> paramMap)
      throws Exception {
    return userDao.findList(paramMap, "t_user_subscribe");
  }
  
  /**
   * Title: 保存用户信息<br>
   * Description: 保存用户信息<br>
   * CreateDate: 2015年8月29日 上午11:18:31<br>
   * 
   * @category 保存用户信息
   * @author yangmh
   * @param userRegisterValidationForm
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public Map<String, Object> saveUser(UserRegisterParam userRegisterParam)
      throws Exception {
    Map<String, Object> userObj = new HashMap<String, Object>();

    if (userRegisterParam.getAdid() == null) {
      userRegisterParam.setAdid("QGHQFN06NI");
    }

    userObj.put("phone", userRegisterParam.getPhone());
    userObj.put("pwd", userRegisterParam.getPwd());
    userObj.put("adid", userRegisterParam.getAdid());
    userObj.put("last_login_time", new Date());

    /**
     * modify by athrun.cw 2016年5月23日15:38:52 bug 342
     * LC后台帮潜客注册，`t_user`表中create_user_id为空
     */
    if (userRegisterParam.getCreateUserId() != null) {
      userObj.put("create_user_id", userRegisterParam.getCreateUserId());
      userObj.put("update_user_id", userRegisterParam.getCreateUserId());
    }

    // 保存数据info数据
    userDao.insert(userObj);
    Map<String, Object> userInfoMap = new HashMap<String, Object>();
    userInfoMap.put("key_id", userObj.get("key_id"));
    userDao.insert(userInfoMap, "t_user_info");

    return userObj;
  }
}
