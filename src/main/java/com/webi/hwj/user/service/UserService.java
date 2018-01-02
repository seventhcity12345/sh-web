package com.webi.hwj.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.aliyun.ocs.OSSClientUtil;
import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.exception.StrangeAlarmException;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SHAUtil;
import com.mingyisoft.javabase.util.SqlUtil;
import com.webi.hwj.admin.dao.AdminBdminUserEntityDao;
import com.webi.hwj.admin.entity.BadminUser;
import com.webi.hwj.bean.SessionAdminUser;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.index.param.UserLoginValidationForm;
import com.webi.hwj.index.param.UserRegisterParam;
import com.webi.hwj.ordercourse.dao.OrderCourseDao;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseEntityDao;
import com.webi.hwj.ordercourse.entitydao.OrderCourseEntityDao;
import com.webi.hwj.ordercourse.service.AdminOrderCourseOptionService;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.tellmemore.util.TellmemoreUtil;
import com.webi.hwj.trainingcamp.entity.TrainingMember;
import com.webi.hwj.trainingcamp.service.TrainingMemberService;
import com.webi.hwj.user.dao.AdminUserInfoDao;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.dao.UserInfoEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entity.UserInfo;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;
import com.webi.hwj.user.param.FloatingLayerInfoParam;
import com.webi.hwj.user.util.CompletePercentUtil;
import com.webi.hwj.util.SessionUtil;
import com.webi.hwj.weixin.dao.UserWeixinDao;
import com.webi.hwj.weixin.entity.UserWeixin;
import com.webi.hwj.weixin.service.UserWeixinService;
import com.webi.hwj.weixin.util.WeixinUtil;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserService {
  private static Logger logger = Logger.getLogger(UserService.class);
  @Resource
  UserDao userDao;

  @Resource
  AdminUserInfoDao adminUserInfoDao;

  @Resource
  AdminOrderCourseOptionService adminOrderCourseOptionService;

  @Resource
  UserService userService;

  @Resource
  AdminUserEntityDao adminUserEntityDao;

  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;

  @Resource
  UserEntityDao userEntityDao;

  @Resource
  UserInfoEntityDao userInfoEntityDao;

  @Resource
  OrderCourseDao orderCourseDao;

  @Resource
  AdminOrderCourseEntityDao adminOrderCourseEntityDao;

  @Resource
  UserWeixinDao userWeixinDao;

  @Resource
  UserWeixinService userWeixinService;

  @Resource
  AdminBdminUserEntityDao adminBdminUserEntityDao;

  @Resource
  OrderCourseEntityDao orderCourseEntityDao;
  @Resource
  TrainingMemberService trainingMemberService;

  /**
   * 
   * Title: 成人浮层接口Service<br>
   * Description: 浮层接口Service<br>
   * CreateDate: 2017年7月4日 下午6:45:53<br>
   * 
   * @category 成人浮层接口Service
   * @author felix.yl
   * @param request
   * @param sessionUser
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public FloatingLayerInfoParam findFloatingLayerInfo(String keyId)
      throws Exception {// keyId代表当前登录的学生的keyId
    FloatingLayerInfoParam floatingLayerInfoParam = new FloatingLayerInfoParam();

    // 根据keyId去查询对应LC的id
    User user = userEntityDao.findOneLcId(keyId);

    if (user != null) {
      String lcId = user.getLearningCoachId();// LC的id

      BadminUser bdUserLc = adminBdminUserEntityDao.findAdminUserInfoByUserId(lcId);// 根据LC的id去查询LC的信息
      if (bdUserLc != null) {
        floatingLayerInfoParam.setLcName(bdUserLc.getAdminUserName());
        floatingLayerInfoParam.setLcTel(bdUserLc.getTelphone());
        floatingLayerInfoParam.setLcWeixin(bdUserLc.getWeixin());
      } else {
        floatingLayerInfoParam.setLcName("");
        floatingLayerInfoParam.setLcTel("");
        floatingLayerInfoParam.setLcWeixin("");
      }
    } else {
      floatingLayerInfoParam.setLcName("");
      floatingLayerInfoParam.setLcTel("");
      floatingLayerInfoParam.setLcWeixin("");
    }

    // 根据keyId去查询对应CC的id
    List<OrderCourse> ccList = orderCourseEntityDao.findCcId(keyId);

    if (ccList != null && ccList.size() != 0) {
      OrderCourse orderCourse = ccList.get(0);
      String ccId = orderCourse.getCreateUserId();// 获取到CC的id

      BadminUser bdUserCc = adminBdminUserEntityDao.findAdminUserInfoByUserId(ccId);// 根据CC的id去查询CC的信息
      if (bdUserCc != null) {
        floatingLayerInfoParam.setCcName(bdUserCc.getAdminUserName());
        floatingLayerInfoParam.setCcTel(bdUserCc.getTelphone());
        floatingLayerInfoParam.setCcWeixin(bdUserCc.getWeixin());
      } else {
        floatingLayerInfoParam.setCcName("");
        floatingLayerInfoParam.setCcTel("");
        floatingLayerInfoParam.setCcWeixin("");
      }
    } else {
      floatingLayerInfoParam.setCcName("");
      floatingLayerInfoParam.setCcTel("");
      floatingLayerInfoParam.setCcWeixin("");
    }
    return floatingLayerInfoParam;
  }

  /**
   * 
   * Title: updateUserPhoto<br>
   * Description: 更新用户图像<br>
   * CreateDate: 2015年11月5日 上午11:39:42<br>
   * 
   * @category 更新用户图像
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public CommonJsonObject<SessionUser> updateUserPhoto(HttpServletRequest request, SessionUser sessionUser)
      throws Exception {
    CommonJsonObject<SessionUser> json = new CommonJsonObject<SessionUser>();

    // 页面中 上传图片时候input 的name属性values
    String fieldName = UserDao.UPLOAD_FIELD_NAME;

    // aliyun上保存图片的路径 images/user/phoneNumber
    String aliyunPath = UserDao.ALIYUN_IMAGE_PATH + sessionUser.getPhone() + "/";
    String imagePaths = null;

    /**
     * 1.开始调用uploadPhoto 上传图像到aliyun服务器， 并且获取存储路径字符串
     */
    try {
      logger.info(
          "用户 [" + sessionUser.getPhone() + "] 开始调用OSSClientUtil，图片将存储于" + aliyunPath + "路径下...");

      // 返回字符串"user_photo_large,user_photo"
      imagePaths = OSSClientUtil.uploadPhoto(request, fieldName, aliyunPath);
      json.setMsg("上传图像成功！");
    } catch (Exception e) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      json.setMsg("上传图像失败！");
      logger.info("用户 [" + sessionUser.getPhone() + "] 调用OSSClientUtil错误，图片存储失败！");
      logger.error(
          "用户 [" + sessionUser.getPhone() + "] 调用OSSClientUtil错误，图片存储失败 error:" + e.getMessage());
    }
    if (imagePaths == null || "".equals(imagePaths)) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      json.setMsg("上传图像失败！");
      logger.error("用户 [" + sessionUser.getPhone() + "] 上传图像失败！");
      throw new RuntimeException("上传图像失败！");
    }

    // 处理返回字符串[user_photo_large, user_photo]
    String[] images = imagePaths.split(",");
    // 大图片的存储路径
    String user_photo_large = images[0];
    // 小图片的存储路径
    String user_photo = images[1];

    /**
     * 2.更新用户的大图像& 小图像
     */
    Map<String, Object> updateUserPhotosMap = new HashMap<String, Object>();
    updateUserPhotosMap.put("user_photo", user_photo);
    updateUserPhotosMap.put("user_photo_large", user_photo_large);
    updateUserPhotosMap.put("key_id", sessionUser.getKeyId());
    if (userDao.update(UserDao.UPDATE_USER_PHOTO_AND_LARGEPHOTO, updateUserPhotosMap) != 1) {
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
      json.setMsg("上传图像失败！");
      throw new RuntimeException("上传图像失败！");
    }

    // modified by komi 2017年8月11日10:54:00 级联更新训练营表用户头像
    TrainingMember paramObj = new TrainingMember();
    paramObj.setTrainingMemberUserId(sessionUser.getKeyId());
    paramObj.setTrainingMemberPic(user_photo);
    trainingMemberService.updateMemberUserPic(paramObj);

    /**
     * 3.更新session
     */
    /**
     * modify by athrun.cw 2015年12月24日11:19:17 初始化session对象
     */
    json.setData(initSessionUser(sessionUser.getKeyId(), null));
    return json;
  }

  /**
   * 
   * Title: initUserTestLevel<br>
   * Description: 初始化用户的级别 <br>
   * CreateDate: 2015年11月4日 下午4:16:19<br>
   * 
   * @category 初始化用户的级别
   * @author athrun.cw
   * @param paramMap
   *          test_level key_id
   * @return
   * @throws Exception
   */
  public JsonMessage initUserTestLevel(Map<String, Object> paramMap) throws Exception {
    JsonMessage json = new JsonMessage();
    Map<String, Object> userMap = new HashMap<String, Object>();
    userMap.put("test_level", paramMap.get("test_level"));
    userMap.put("user_id", paramMap.get("user_id"));

    if (userDao.update(UserDao.UPDATE_USER_TESTLEVEL, userMap) == 1) {
      logger.info("用户id [" + paramMap.get("user_id") + "] 级别初始化成功！");
      json.setMsg("用户级别初始化成功！");
    } else {
      logger.error("用户id [" + paramMap.get("user_id") + "] 级别初始化失败！");
      json.setMsg("用户级别初始化失败！");
      json.setSuccess(false);
    }
    return json;
  }

  /**
   * Title: 初始化用户Session.<br>
   * Description: 将t_user表,t_user_info表以及合同类型列表全部放到session中<br>
   * CreateDate: 2017年2月20日 下午1:52:37<br>
   * 
   * @category initSessionUser
   * @author yangmh
   * @param userIdOrPhone
   *          用户id或手机号
   * @param time
   *          超时时间,单位:秒,如果为null则默认1小时
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,
      rollbackFor = Exception.class)
  public SessionUser initSessionUser(String userIdOrPhone, Integer time) throws Exception {

    SessionUser sessionUser = findSessionUser(userIdOrPhone);

    String token = SqlUtil.createUUID();

    if (time == null) {
      // 默认时间为1小时
      time = 60 * 60;
    }

    MemcachedUtil.setValue(token, sessionUser, time);
    sessionUser.setToken(token);

    return sessionUser;
  }

  /**
   * Title: 根据用户id或手机号查询用户信息<br>
   * Description: 根据用户id或手机号查询用户信息<br>
   * CreateDate: 2017年5月11日 下午4:55:15<br>
   * 
   * @category 根据用户id或手机号查询用户信息
   * @author komi.zsy
   * @param userIdOrPhone
   *          用户id或手机号
   * @return
   * @throws Exception
   */
  public SessionUser findSessionUser(String userIdOrPhone) throws Exception {
    // modified by alex.ymh 2016年8月8日 14:58:11
    // 优化该方法，在本方法内自己查询t_user_info和t_user的信息。

    User user = userEntityDao.findUserByUserIdOrPhone(userIdOrPhone);
    if (user == null) {
      throw new Exception("session用户不存在,userId = " + userIdOrPhone);
    }
    UserInfo userInfo = userInfoEntityDao.findOneByKeyId(user.getKeyId());

    SessionUser sessionUser = new SessionUser();
    // modified by alex 2016年11月20日 17:51:49
    // birthday为null会报错

    BeanUtils.copyProperties(sessionUser, user);
    BeanUtils.copyProperties(sessionUser, userInfo);
    // session对象中不允许出现密码
    sessionUser.setPwd(null);
    sessionUser.setUserName(sessionUser.getRealName());

    sessionUser.setStudent(sessionUser.getIsStudent());

    if (!StringUtils.isEmpty(userInfo.getEnglishName())) {
      sessionUser.setUserName(userInfo.getEnglishName());
    }

    // modified by alex.ymh 2016年8月8日 14:58:11 出来混，迟早要换的。
    sessionUser.setCurrentLevel(user.getCurrentLevel());
    sessionUser.setInfoCompletePercent(user.getInfoCompletePercent() + "");

    /**
     * modified by komi 2016年7月7日10:51:15 增加学员拥有的所有课程类型
     */
    sessionUser.setCourseTypes(
        adminOrderCourseOptionService.findCourseTypesByUserId(sessionUser.getKeyId()));
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", user.getKeyId());
    // modified by alex.yang 如果用户是学员的话，才会查下面跟合同相关的信息

    if (sessionUser.getIsStudent()) {
      Map<String, Object> orderCourseMap = orderCourseDao.findContractByUserId(paramMap);

      // 当前合同开始时间
      sessionUser.setCurrentOrderStartTime((Date) orderCourseMap.get("start_order_time"));
      // 当前合同结束时间
      sessionUser.setCurrentOrderEndTime((Date) orderCourseMap.get("end_order_time"));
      // 当前合同的课程包类型
      sessionUser.setCoursePackageType(orderCourseMap.get("course_package_type").toString());

    }

    return sessionUser;
  }

  /**
   * 
   * Title: 修改用户详细信息<br>
   * Description: updateUserInfoByUserId<br>
   * CreateDate: 2016年3月18日 上午10:20:32<br>
   * 
   * @category 修改用户详细信息
   * @author komi.zsy
   * @param paramMap
   * @param request
   * @return
   * @throws Exception
   */
  /*
   * @Transactional(propagation = Propagation.REQUIRED, isolation =
   * Isolation.SERIALIZABLE)//有多条数据库操作，使用事物 public JsonMessage
   * updateUserInfoByUserId(Map<String, Object> paramMap, HttpServletRequest
   * request) throws Exception { JsonMessage json = new JsonMessage();
   * SessionAdminUser sessionAdminUser =
   * SessionUtil.getSessionAdminUser(request);
   * 
   * //如果province city district不是城市值(null或者没有选择)，变成""
   * if(paramMap.get("province") != null &&
   * "省份".equals(paramMap.get("province").toString())){ paramMap.put("province",
   * ""); } if(paramMap.get("city") != null &&
   * "城市".equals(paramMap.get("city").toString())){ paramMap.put("city", ""); }
   * if(paramMap.get("district") != null &&
   * "县区".equals(paramMap.get("district").toString())){ paramMap.put("district",
   * ""); }
   * 
   * //更新之前，需要判断：real_name(user_name) 和idcard是第一次更新 Map<String, Object>
   * userInfoMap = new HashMap<String, Object>(); userInfoMap.put("key_id",
   * paramMap.get("key_id")); userInfoMap =
   * adminUserInfoDao.findUserInfoAndUserByUserId(userInfoMap);//查找用户信息，
   * t_user和t_user_info表联查（t_user表中只需要info_complete_percent属性） if(userInfoMap ==
   * null ){ json.setSuccess(false); json.setMsg("对不起，该用户不存在或者数据错误~");
   * logger.error("管理员 [" + sessionAdminUser.getAdminUserName() + "操作用户" +
   * paramMap.get("phone") + "] 该用户不存在或者数据错误~..."); throw new
   * RuntimeException("对不起，该用户不存在或者数据错误~"); }
   * 
   * //不是第一次更新real_name if(userInfoMap.get("real_name") != null &&
   * !"".equals(userInfoMap.get("real_name"))){ paramMap.put("real_name",
   * userInfoMap.get("real_name")); }
   * 
   * //不是第一次更新idcard if(userInfoMap.get("idcard") != null &&
   * !"".equals(userInfoMap.get("idcard"))){ paramMap.put("idcard",
   * userInfoMap.get("idcard")); }
   * 
   * //更行条数必须是1，否则异常 if
   * (userDao.update(AdminUserInfoDao.UPDATE_USERINFO_BY_USERID_BY_ADMIN,
   * paramMap) == 1 &&
   * userDao.update(AdminUserInfoDao.UPDATE_USERNAME_BY_USERID_BY_ADMIN,
   * paramMap) == 1) { json.setSuccess(true); json.setMsg("修改资料成功");
   * logger.info("管理员 [" + sessionAdminUser.getAdminUserName() + "操作用户" +
   * paramMap.get("phone") + "] 更新个人资料成功..."); }else{ json.setSuccess(false);
   * json.setMsg("对不起，您输入的数据有误，请重新修改资料"); logger.error("管理员 [" +
   * sessionAdminUser.getAdminUserName() + "操作用户" + paramMap.get("phone") +
   * "] 输入的数据有误，更新失败..."); throw new RuntimeException("对不起，您输入的数据有误，请重新修改资料"); }
   * 
   * logger.info("管理员 [" + sessionAdminUser.getAdminUserName() + "操作用户" +
   * paramMap.get("phone") + "] 更新userInfo成功，开始更新info_complete_percent进度...");
   *//**
     * 2.判断资料完成度有变更 2.1计算新的资料完成度 2.2完成度改变的逻辑
     *//*
       * int info_complete_percent =
       * CompletePercentUtil.getCompletePercent(paramMap); //int
       * info_complete_percent_old =
       * CompletePercentUtil.getCompletePercent(userInfoMap);//
       * 使用数据库原始数据计算原始完成度，但是曹伟说不建议再次计算
       * 
       * //3.完成度 有变更 if (info_complete_percent !=
       * (int)userInfoMap.get("info_complete_percent")) {
       * //3.更新t_user表中的info_complete_percent Map<String, Object> userMap = new
       * HashMap<String, Object>(); userMap.put("key_id",
       * paramMap.get("key_id")); userMap.put("info_complete_percent",
       * info_complete_percent); userDao.update(userMap); logger.info("管理员 [" +
       * sessionAdminUser.getAdminUserName() + "操作用户" + paramMap.get("phone") +
       * "] 更新info_complete_percent进度成功"); }
       * 
       * return json; }
       */

  /**
   * 
   * Title: updateUserInfoByUserId<br>
   * Description: 更新用户资料<br>
   * CreateDate: 2015年10月16日 下午3:35:33<br>
   * 
   * @category 更新用户资料
   * @author athrun.cw
   * @param paramMap
   * @param request
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage updateUserInfoByUserOrAdminUser(Map<String, Object> paramMap,
      HttpServletRequest request)
      throws Exception {
    JsonMessage json = new JsonMessage();

    /**
     * midify by athrun.cw+komi.zsy 前後台（用戶+管理員） 修改用戶的資料，引用相同的service
     */
    // 如果province city district不是城市值(null或者没有选择)，变成""
    if (paramMap.get("province") != null && "省份".equals(paramMap.get("province").toString())) {
      paramMap.put("province", "");
    }
    if (paramMap.get("city") != null && "城市".equals(paramMap.get("city").toString())) {
      paramMap.put("city", "");
    }
    if (paramMap.get("district") != null && "县区".equals(paramMap.get("district").toString())) {
      paramMap.put("district", "");
    }

    String loginfo = "";
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    SessionAdminUser sessionAdminUser = SessionUtil.getSessionAdminUser(request);

    if (sessionUser != null) {
      loginfo = "前台用戶修改：用户id [" + sessionUser.getKeyId() + "] ";
    } else if (sessionAdminUser != null) {
      loginfo = "后台管理员id [" + sessionAdminUser.getKeyId() + "] 修改：用户id [" + paramMap.get("key_id")
          + "] ";
    }

    // 更新之前，需要判断：real_name(user_name) 和idcard是第一次更新
    Map<String, Object> userInfoMap = new HashMap<String, Object>();
    userInfoMap.put("key_id", paramMap.get("key_id"));
    // userInfoMap = userDao.findOne(userInfoMap, "t_user_info", "*");

    // 查找用户信息，t_user和t_user_info表联查（t_user表中只需要info_complete_percent属性）
    userInfoMap = adminUserInfoDao.findUserInfoAndUserByUserId(userInfoMap);
    if (userInfoMap == null) {
      json.setSuccess(false);
      json.setMsg("对不起，该用户不存在或者数据错误~");
      logger.error(loginfo + "该用户不存在或者数据错误~...");
      throw new RuntimeException("对不起，该用户不存在或者数据错误~");
    }

    /**
     * modified by komi 2017年3月10日11:08:10 按tom要求，真实姓名和身份证号都可以更改
     */
    // // 不是第一次更新real_name
    // if (userInfoMap.get("real_name") != null &&
    // !"".equals(userInfoMap.get("real_name"))) {
    // paramMap.put("real_name", userInfoMap.get("real_name"));
    // }
    //
    // // 不是第一次更新idcard
    // if (userInfoMap.get("idcard") != null &&
    // !"".equals(userInfoMap.get("idcard"))) {
    // paramMap.put("idcard", userInfoMap.get("idcard"));
    // }

    // 更行条数必须是1，否则异常
    if (userDao.update(AdminUserInfoDao.UPDATE_USERINFO_BY_USERID_OR_ADMIN, paramMap) == 1
        && userDao.update(AdminUserInfoDao.UPDATE_USERNAME_BY_USERID_OR_ADMIN, paramMap) == 1) {
      // modified by komi 2017年8月10日18:02:12 级联更新训练营成员表
      TrainingMember paramObj = new TrainingMember();
      paramObj.setTrainingMemberGender(Integer.parseInt((String) paramMap.get("gender")));
      paramObj.setTrainingMemberEnglishName((String) paramMap.get("english_name"));
      paramObj.setTrainingMemberRealName((String) paramMap.get("real_name"));
      paramObj.setTrainingMemberUserId((String) paramMap.get("key_id"));
      trainingMemberService.updateUserInfo(paramObj);

      json.setSuccess(true);
      json.setMsg("修改资料成功");
      logger.info(loginfo + " 更新个人资料成功...");
    } else {
      json.setSuccess(false);
      json.setMsg("对不起，您输入的数据有误，请重新修改资料");
      logger.error(loginfo + " 输入的数据有误，更新失败...");
      throw new RuntimeException("对不起，您输入的数据有误，请重新修改资料");
    }

    // modify by seven 2017年7月4日14:32:22 级联修改预约数据库
    // 如果英文名改变了
    if (paramMap.get("english_name") != null && userInfoMap.get("english_name") != null && !paramMap
        .get("english_name").equals(userInfoMap.get("english_name"))) {
      adminSubscribeCourseDao.updateUserNameByUserId((String) paramMap.get("key_id"),
          (String) paramMap
              .get("english_name"));
    }

    logger.info(loginfo + " 更新userInfo成功，开始更新info_complete_percent进度...");
    /**
     * 2.判断资料完成度有变更 2.1计算新的资料完成度 2.2完成度改变的逻辑
     */
    int info_complete_percent = CompletePercentUtil.getCompletePercent(paramMap);

    // 3.完成度 有变更
    // athrun//Integer.parseInt(sessionUser.getInfo_complete_percent())
    if (info_complete_percent != (int) userInfoMap.get("info_complete_percent")) {
      // 3.更新t_user表中的info_complete_percent
      Map<String, Object> userMap = new HashMap<String, Object>();
      userMap.put("key_id", paramMap.get("key_id"));
      userMap.put("info_complete_percent", info_complete_percent);
      userDao.update(userMap);
      logger.info(loginfo + " 更新info_complete_percent进度成功");

    }

    if (sessionUser != null) {
      // 4.更新session

      /**
       * modify by athrun.cw 2015年12月25日15:09:10 初始化session对象：因为可能会影响到资料完成度 &&
       * 用户名
       */
      initSessionUser(paramMap.get("key_id") + "", null);

      logger.info(loginfo + " 更新session完成，index中用户名同步更新成功...");
    }

    /**
     * modify by seven 2017年4月10日20:02:00 更新微信端session
     */
    List<UserWeixin> userWeixinList = userWeixinDao.findListByUserId((String) paramMap.get(
        "key_id"));
    // 获取openid参数
    if (userWeixinList != null && userWeixinList.size() > 0) {
      for (UserWeixin userWeixin : userWeixinList) {
        String weixinOpenId = userWeixin.getOpenId();
        // 从缓存中获取sessionUser
        sessionUser = WeixinUtil.getWeixinSessionUser(weixinOpenId);
        if (sessionUser != null) {
          // 初始化session,自动登录,并缓存1个小时session信息
          userWeixinService.updateWeixinSessionUser(null, userWeixin.getUserId(),
              weixinOpenId);
        }
      }
    }

    // 管理员可以更换手机号
    if (sessionAdminUser != null) {
      // 有更换学员手机号权限的
      if (sessionAdminUser.isHavePermisson("contract:updatePhone")) {
        /**
         * modified by komi by2016年8月1日18:51:01 手机号有修改
         * 写在最后，防止RSA成功后，数据库回滚，导致RSA数据错误的问题
         */
        // 原来的手机号
        String phone = (String) userInfoMap.get("phone");
        // 新的手机号
        String newPhone = (String) paramMap.get("phone");

        if (!StringUtils.isEmpty(newPhone) && !newPhone.equals(phone)) {
          // 验证手机号是否重复
          Map<String, Object> newPhoneMap = new HashMap<String, Object>();
          newPhoneMap.put("phone", newPhone);
          List<Map<String, Object>> userList = userService.findList(newPhoneMap, "key_id");
          if (userList != null && userList.size() > 0) {
            json.setSuccess(false);
            throw new RuntimeException("该手机号已经注册！");
          }

          // 更新手机号（学员登陆账号）
          User user = new User();
          user.setKeyId(paramMap.get("key_id") + "");
          user.setPhone(newPhone);
          adminUserEntityDao.update(user);

          // 更新预约表手机号
          SubscribeCourse subscribeCourse = new SubscribeCourse();
          subscribeCourse.setUserPhone(user.getPhone());
          subscribeCourse.setUserId(user.getKeyId());
          adminSubscribeCourseDao.updateUserPhoneByUserId(subscribeCourse);

          // modified by komi 2017年8月10日18:02:12 级联更新训练营成员表
          TrainingMember paramObj = new TrainingMember();
          paramObj.setTrainingMemberPhone(user.getPhone());
          paramObj.setTrainingMemberUserId(user.getKeyId());
          trainingMemberService.updateUserPhone(paramObj);

          // 判断正在执行中的合同是否有RSA课程
          int rsaCount = adminOrderCourseEntityDao.findCountUserCourseType(user.getKeyId(),
              "course_type6");
          if (rsaCount > 0) {
            // rsa更新用户账号为新手机号
            TellmemoreUtil.updateTmmUserAccount(phone, newPhone);
          }
        }
      }
    }

    return json;
  }

  /**
   * 
   * Title: findUserInfoByUserId<br>
   * Description: indexController中login使用 && 后台查看合同详情中使用2015年11月24日11:03:03 <br>
   * CreateDate: 2015年10月21日 上午10:53:53<br>
   * 
   * @category indexController中login使用 && 后台查看合同详情中使用2015年11月24日11:03:08
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findUserInfoByUserId(Map<String, Object> paramMap) throws Exception {
    return userDao.findUserInfoByUserId(paramMap);

  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return userDao.findList(paramMap, columnName);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @return
   * @throws Exception
   */
  public Page findPage(Map<String, Object> paramMap) throws Exception {
    return userDao.findPage(paramMap);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @author mingyisoft代码生成工具
   * @param paramMap
   *          参数map
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(Map<String, Object> paramMap) throws Exception {
    return userDao.findPageEasyui("*", paramMap);
  }

  /**
   * @category 修改数据
   * @author mingyisoft代码生成工具
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return userDao.update(fields);
  }

  /**
   * @category 查询单条数据(通过参数MAP)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return userDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过key,value)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(String key, String value, String columnName) throws Exception {
    return userDao.findOne(key, value, columnName);
  }

  /**
   * @category 查询单条数据(通过主键)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOneByKeyId(Object param, String columnName) throws Exception {
    return userDao.findOneByKeyId(param, columnName);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @author mingyisoft代码生成工具
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return userDao.delete(ids);
  }

  /**
   * @category 查询数量
   * @author mingyisoft代码生成工具
   * @param sql
   * @param map
   * @return
   * @throws Exception
   */
  public int findCount(Map<String, Object> map) throws Exception {
    return userDao.findCount(map);
  }

  /**
   * @category 查询总数
   * @author mingyisoft代码生成工具
   * @param map
   * @param sumField
   *          sum的字段
   * @return
   * @throws Exception
   */
  public int findSum(Map<String, Object> map, String sumField) throws Exception {
    return userDao.findSum(map, sumField);
  }

  public Map<String, Object> findOneByLeadId(String leadId) throws Exception {
    return userDao.findOne("lead_id", leadId, "key_id,phone,user_name,current_level");
  }

  public Map<String, Object> findOneByPhone(String studentMobile) throws Exception {
    return userDao.findOne("phone", studentMobile, "key_id,phone,user_name,current_level");
  }

  /**
   * Title: 根据手机号查询该学员是否有某种课程类型上课权限<br>
   * Description: 根据手机号查询该学员是否有某种课程类型上课权限<br>
   * CreateDate: 2017年8月14日 上午11:52:42<br>
   * 
   * @category 根据手机号查询该学员是否有某种课程类型上课权限
   * @author komi.zsy
   * @param phone
   *          学员手机号
   * @param courseType
   *          课程类型
   * @return
   * @throws Exception
   */
  public CommonJsonObject<Object> findUserByCourseTypeAndUserPhone(String phone, String courseType)
      throws Exception {
    CommonJsonObject<Object> json = new CommonJsonObject<Object>();
    int num = userEntityDao.findUserByCourseTypeAndUserPhone(phone, courseType);
    if (num > 0) {
      // 大于0说明该学员的这个课程在有效期内
      json.setData(true);
    } else {
      json.setData(false);
    }
    return json;
  }

  /**
   * Title: 用户登录.<br>
   * Description: userLogin<br>
   * CreateDate: 2017年2月20日 下午3:04:07<br>
   * 
   * @category 用户登录
   * @author yangmh
   * @param userLoginValidationForm
   *          用户登录校验对象
   * @param lastLoginIp
   *          最后登录ip
   */
  public SessionUser userLogin(UserLoginValidationForm userLoginValidationForm,
      String lastLoginIp) throws Exception {

    // 通过手机号查询用户数据
    User user = userEntityDao.findOneByPhoneReturnPwd(userLoginValidationForm.getPhone());
    // 对比加密密码
    if (user != null
        && SHAUtil.encodeByDate(user.getPwd())
            .equals(userLoginValidationForm.getPwd())) {
      // 更新用户的最后登录时间和ip
      user.setLastLoginTime(new Date());
      user.setLastLoginIp(lastLoginIp);
      userEntityDao.update(user);

      // 如果是true则表示需要缓存30天
      String rememberMe = userLoginValidationForm.getRememberMe();
      // 默认1小时
      int time = 60 * 60;
      if ("true".equals(rememberMe)) {
        // 30天
        time = 30 * 24 * 60 * 60;
      }

      // 初始化session对象
      SessionUser sessionUser = userService.initSessionUser(user.getKeyId(),
          time);

      return sessionUser;
    } else {
      // 登录失败
      return null;
    }
  }

  /**
   * Title: 根据手机号查找是学员的用户<br>
   * Description: 根据手机号查找是学员的用户<br>
   * CreateDate: 2017年8月24日 上午11:46:36<br>
   * 
   * @category 根据手机号查找是学员的用户
   * @author komi.zsy
   * @param phone
   *          手机号
   * @param lastLoginIp
   *          最后登录ip
   * @return
   * @throws Exception
   */
  public SessionUser appUserLogin(String phone, String lastLoginIp,int time) throws Exception {

    // 通过手机号查询用户数据
    User user = userEntityDao.findOneByPhoneAndStudent(phone);
    if (user != null) {
      // 更新用户的最后登录时间和ip
      user.setLastLoginTime(new Date());
      user.setLastLoginIp(lastLoginIp);
      userEntityDao.update(user);

      // 初始化session对象
      SessionUser sessionUser = userService.initSessionUser(user.getKeyId(),
          time);

      return sessionUser;
    }

    return null;

  }

  /**
   * Title: 新增用户(学生).<br>
   * Description: insertUser<br>
   * CreateDate: 2017年6月19日 下午5:04:18<br>
   * 
   * @category 新增用户(学生)
   * @author yangmh
   * @param userRegisterParam
   *          学生参数对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public User insertUser(UserRegisterParam userRegisterParam)
      throws Exception {
    // 赋值默认的ADID，市场营销用的一个码，具体可以问tina。
    if (userRegisterParam.getAdid() == null) {
      userRegisterParam.setAdid("QGHQFN06NI");
    }

    User user = new User();
    // 赋值属性
    BeanUtils.copyProperties(user, userRegisterParam);

    int userReturnCode = userEntityDao.insert(user);

    UserInfo userInfo = new UserInfo();
    userInfo.setKeyId(user.getKeyId());

    int userInfoReturnCode = userInfoEntityDao.insert(userInfo);

    // 同时保存user表与user_info表
    if (userReturnCode == 1 && userInfoReturnCode == 1) {
      return user;
    } else {
      throw new StrangeAlarmException("userReturnCode == 1 && userInfoReturnCode == 1应该一样的,纳尼?");
    }
  }

  /**
   * Title: 查询用户密码.<br>
   * Description: 同时用于查询用户是否存在用于判断重复注册<br>
   * CreateDate: 2017年8月9日 下午4:03:56<br>
   * 
   * @category findOneByPhoneReturnPwd
   * @author yangmh
   * @param phone
   *          手机号
   */
  public User findOneByPhoneReturnPwd(String phone) throws Exception {
    return userEntityDao.findOneByPhoneReturnPwd(phone);
  }
}