package com.webi.hwj.redeemcode.service;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.constant.SmsConstant;
import com.webi.hwj.ordercourse.entitydao.OrderCourseEntityDao;
import com.webi.hwj.redeemcode.constant.RedeemCodeConstant;
import com.webi.hwj.redeemcode.dao.RedeemCodeDao;
import com.webi.hwj.redeemcode.dao.RedeemCodeEntityDao;
import com.webi.hwj.redeemcode.entity.RedeemCode;
import com.webi.hwj.redeemcode.util.RedeemCodeUtil;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;

/**
 * 
 * Title: 兑换码活动<br>
 * Description: RedeemcodeService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年2月23日 下午2:54:38
 * 
 * @author athrun.cw
 */

@Service
public class RedeemCodeService {
  private static Logger logger = Logger.getLogger(RedeemCodeService.class);

  @Resource
  RedeemCodeDao redeemcodeDao;

  @Resource
  AdminUserEntityDao adminUserEntityDao;

  @Resource
  RedeemCodeEntityDao redeemCodeEntityDao;

  @Resource
  OrderCourseEntityDao orderCourseEntityDao;

  /**
   * Title: 校验是否可以使用兑换码<br>
   * Description: 校验是否可以使用兑换码<br>
   * CreateDate: 2016年7月20日 下午1:47:52<br>
   * 
   * @category 校验是否可以使用兑换码
   * @author komi.zsy
   * @param redeemcodeMap
   * @param redeemUserId
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage checkRedeemCode(String redeemCode, String redeemUserId, Date currentDate)
      throws Exception {
    JsonMessage json = new JsonMessage();

    RedeemCode redeemcodeObj = redeemCodeEntityDao.findRedeemCodeByCode(redeemCode);

    // 0.检查用户是否已经是学员，是则不能使用兑换码
    User user = adminUserEntityDao.findOneByKeyId(redeemUserId);

    if (user == null) {
      json.setSuccess(false);
      json.setMsg("您还不是SpeakHi的成员,请先注册或登陆，谢谢");
      return json;
    }

    if (user.getIsStudent()) {
      json.setSuccess(false);
      json.setMsg("您已是SpeakHi的成员，暂无法兑换课程，谢谢");
      return json;
    }

    /**
     * 4.1 兑换码不存在提示 4.2 兑换码已经使用提示（出现提示：您已兑换过体验课程，谢谢） 4.3 兑换码已过期提示 4.4
     * 活动还未开始，前后2个时间都判断一下 （出现提示：体验课程兑换活动已结束，您可前往会员中心首页体验公开课，感谢您的支持”）
     */
    // 1.根据用户提交的验证码，查询是否合法
    if (redeemcodeObj == null) {
      json.setSuccess(false);
      json.setMsg("您输入的兑换码不存在，请确认后重新输入，谢谢~");
      return json;
    }

    // 2.验证该用户是否已经兑换过，如果已经存在该用户，则return
    // Map<String, Object> findRedeemcodeMap = new HashMap<String, Object>();
    // findRedeemcodeMap.put("redeem_user_id", redeemUserId);
    // findRedeemcodeMap.put("activity_name", redeemcodeObj.getActivityName());
    // Map<String, Object> userRedeemcodeMap = redeemcodeDao
    // .findRedeemCodeByUserIdAndActivityName(findRedeemcodeMap);
    // if (userRedeemcodeMap != null) {
    // json.setSuccess(false);
    // json.setMsg("您已兑换过体验课程套餐，该课程只可兑换一次，建议购买常规课程套餐哦~");
    // return json;
    // }

    // 2.判断有无正在执行总的合同
    // int orderCount =
    // orderCourseEntityDao.findCountExecutoryOrder(redeemUserId);
    // if (orderCount > 0) {
    // json.setSuccess(false);
    // json.setMsg("您已有正在执行中的合同，请在合同结束后再兑换~");
    // return json;
    // }

    // 3.验证兑换码是否已经使用
    if (!StringUtils.isEmpty(redeemcodeObj.getRedeemUserId())) {
      json.setSuccess(false);
      json.setMsg("此兑换码已被使用，谢谢~");
      return json;
    }

    // 4.兑换码未开始|| 已过期提示
    Date activity_start_time = redeemcodeObj.getActivityStartTime();
    Date activity_end_time = redeemcodeObj.getActivityEndTime();
    if (currentDate.getTime() < activity_start_time.getTime()
        || currentDate.getTime() >= activity_end_time.getTime()) {
      json.setSuccess(false);
      json.setMsg("体验课程兑换活动未开始或者已结束，您可前往会员中心首页体验公开课，感谢您的支持~");
      return json;
    }

    json.setData(redeemcodeObj);

    return json;
  }

  /**
   * Title: 更新兑换码表<br>
   * Description: 更新兑换码表<br>
   * CreateDate: 2016年7月21日 上午10:27:41<br>
   * 
   * @category 更新兑换码表
   * @author komi.zsy
   * @param paramMap
   * @param redeemcodeMap
   *          兑换码信息
   * @param addMonth
   *          增加的月份
   * @param currentDate
   *          当前日期
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public RedeemCode updateRedeemCode(String userId, String userPhone, String userName,
      RedeemCode redeemcodeObj, int addMonth, Date currentDate) throws Exception {
    redeemcodeObj.setRedeemUserId(userId);
    redeemcodeObj.setRedeemUserPhone(userPhone);
    redeemcodeObj.setRedeemUserRealName(userName);
    redeemcodeObj.setRedeemStartTime(currentDate);

    // 计算redeem_end_time（redeem_start_time + addMonth个月）
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + addMonth,
        calendar.get(Calendar.DAY_OF_MONTH) + 1, 0, 0, 0);
    redeemcodeObj.setRedeemEndTime(calendar.getTime());

    redeemcodeObj.setUpdateDate(currentDate);
    redeemcodeObj.setUpdateUserId(userId);

    redeemCodeEntityDao.update(redeemcodeObj);

    return redeemcodeObj;
  }

  /**
   * 
   * Title: 提交兑换码信息接口:提交兑换码信息，用于兑换码页面<br>
   * Description: submitRedeemCode<br>
   * CreateDate: 2016年2月25日 下午2:34:10<br>
   * 
   * @category 提交兑换码信息接口:提交兑换码信息，用于兑换码页面
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public JsonMessage submitRedeemCode(Map<String, Object> paramMap) throws Exception {
    JsonMessage json = new JsonMessage(false, "提交兑换码失败~");

    Date currentDate = new Date();

    // 校验是否可以使用兑换码
    json = checkRedeemCode(paramMap.get("redeem_code") + "", paramMap.get("redeem_user_id") + "",
        currentDate);
    if (!json.isSuccess()) {
      return json;
    }

    RedeemCode redeemcodeObj = (RedeemCode) json.getData();

    // 全部验证通过，正常情况下，开始提交兑换码
    updateRedeemCode(paramMap.get("redeem_user_id").toString(), paramMap.get("redeem_user_phone")
        .toString(),
        paramMap.get("redeem_user_real_name").toString(), redeemcodeObj, 1, currentDate);

    // 更新成功
    json.setSuccess(true);
    json.setMsg("恭喜您，已经成功兑换~");

    return json;
  }

  /**
   * 
   * Title: 查询兑换码信息：查询session里的兑换码信息，用于非学员首页<br>
   * Description: findRedeemCode<br>
   * CreateDate: 2016年2月24日 上午10:50:44<br>
   * 
   * @category 查询兑换码信息：查询session里的兑换码信息，用于非学员首页
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(readOnly = true)
  public JsonMessage findRedeemCode(Map<String, Object> paramMap) throws Exception {
    JsonMessage json = new JsonMessage(false, "获取兑换码失败~");

    // 获得兑换码信息
    Map<String, Object> redeemcode = redeemcodeDao.findRedeemCodeByUserIdAndActivityName(paramMap);
    if (redeemcode == null) {
      json.setSuccess(false);
      json.setMsg("该用户还未参与兑换码活动~");
      return json;
    }
    json.setSuccess(true);
    json.setMsg("获取兑换码信息成功~");
    json.setData(redeemcode);

    return json;
  }

  /**
   * 
   * Title: 生成新的 兑换码（多线程）<br>
   * Description: createRedeemcode<br>
   * CreateDate: 2016年2月23日 下午3:03:52<br>
   * 
   * @category 生成新的 兑换码 （多线程）
   * @author athrun.cw
   * @return
   * @throws Exception
   */
  public void createRedeemcode(final String createUserId, final String chooseActivity)
      throws Exception {
    logger.debug("RedeemcodeService开始生产");

    /**
     * 启动20个线程执行
     */
    for (int threadNum = 0; threadNum < RedeemCodeConstant.THREAD_NUM; threadNum++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            /**
             * modify by athrun.cw 2016年4月13日14:50:13 新的兑换码体验活动
             */
            if (RedeemCodeConstant.ACTIVITY_NAME_A.equals(chooseActivity)) {
              // A兑换码体验活动
              betchCreateARedeemcode(
                  RedeemCodeConstant.REDEEMCODE_A_NUM / RedeemCodeConstant.THREAD_NUM,
                  createUserId, chooseActivity);
            }

            /**
             * 原来的兑换码活动
             */
            if (RedeemCodeConstant.ACTIVITY_NAME.equals(chooseActivity)) {
              // 一套以M开头，表示是M引来的流量 M开头的10W个 betchCreateMRedeemcode
              betchCreateMRedeemcode(
                  RedeemCodeConstant.REDEEMCODE_M_NUM / RedeemCodeConstant.THREAD_NUM,
                  createUserId, chooseActivity);

              // 一套以W开头，表示是W引来的流量 M开头的1000个 betchCreateWRedeemcode
              betchCreateWRedeemcode(
                  RedeemCodeConstant.REDEEMCODE_W_NUM / RedeemCodeConstant.THREAD_NUM,
                  createUserId, chooseActivity);
            }
          } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常，error:" + e.getMessage());
          }
        }
      }).start();
    }
    logger.debug("RedeemcodeService生产结束。");
  }

  /**
   * 
   * Title: 批量生产A开头的验证码<br>
   * Description: betchCreateARedeemcode<br>
   * CreateDate: 2016年4月13日 下午2:40:46<br>
   * 
   * @category 批量生产A开头的验证码
   * @author athrun.cw
   * @param aLength
   * @param createUserId
   * @throws Exception
   */
  public void betchCreateARedeemcode(int aLength, String createUserId, String chooseActivity)
      throws Exception {
    for (int i = 0; i < aLength; i++) {
      logger.debug("开始第" + (i + 1) + "次生产A开头的兑换码...");
      // redeemcodeDao.insert(RedeemcodeUtil.createRedeemcodeMap("A",
      // createUserId,
      // RedeemcodeConstant.ACTIVITY_START_TIME_A,
      // RedeemcodeConstant.ACTIVITY_END_TIME_A, chooseActivity));
    }
  }

  /**
   * 
   * Title: 批量生产M开头的验证码<br>
   * Description: betchCreateM<br>
   * CreateDate: 2016年2月23日 下午4:09:50<br>
   * 
   * @category 批量生产M开头的验证码
   * @author athrun.cw
   * @param mLength
   * @throws Exception
   */
  public void betchCreateMRedeemcode(int mLength, String createUserId, String chooseActivity)
      throws Exception {
    for (int i = 0; i < mLength; i++) {
      logger.debug("开始第" + (i + 1) + "次生产M（M引来的流量）开头的兑换码...");
      // redeemcodeDao.insert(RedeemcodeUtil.createRedeemcodeMap("M",
      // createUserId,
      // RedeemcodeConstant.ACTIVITY_START_TIME_M,
      // RedeemcodeConstant.ACTIVITY_END_TIME_M, chooseActivity));
    }
  }

  /**
   * 
   * Title: 批量生产W开头的验证码<br>
   * Description: betchCreateW<br>
   * CreateDate: 2016年2月23日 下午4:10:08<br>
   * 
   * @category 批量生产W开头的验证码
   * @author athrun.cw
   * @param wLength
   * @throws Exception
   */
  private void betchCreateWRedeemcode(int wLength, String createUserId, String chooseActivity)
      throws Exception {
    for (int i = 0; i < wLength; i++) {
      logger.debug("开始第" + (i + 1) + "次生产W（W引来的流量）开头的兑换码...");
      // redeemcodeDao.insert(RedeemcodeUtil.createRedeemcodeMap("W",
      // createUserId,
      // RedeemcodeConstant.ACTIVITY_START_TIME_W,
      // RedeemcodeConstant.ACTIVITY_END_TIME_W, chooseActivity));
    }
  }

  /**
   * 
   * Title: 生成新的 兑换码（多线程）<br>
   * Description: createRedeemcode<br>
   * CreateDate: 2016年2月23日 下午3:03:52<br>
   * 
   * @category 生成新的 兑换码 （多线程）
   * @author athrun.cw
   * @return
   * @throws Exception
   */
  public void createRedeemcodeNew(final RedeemCode paramObj, final int redeemcodeNum)
      throws Exception {
    logger.debug("RedeemcodeService开始生产");

    /**
     * 启动20个线程执行
     */
    for (int threadNum = 0; threadNum < RedeemCodeConstant.THREAD_NUM; threadNum++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            betchCreateARedeemcodeNew(paramObj, redeemcodeNum / RedeemCodeConstant.THREAD_NUM);
          } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常，error:" + e.getMessage());
          }
        }
      }).start();
    }
    logger.debug("RedeemcodeService生产结束。");
  }

  /**
   * Title: 批量生产已发送的兑换码<br>
   * Description: 生产兑换码给市场部，由他们来发送，所以默认兑换码生产出来就已经发送了<br>
   * CreateDate: 2017年1月16日 下午4:47:12<br>
   * 
   * @category 批量生产已发送的兑换码
   * @author komi.zsy
   * @param paramObj
   * @param redeemCodeNum
   * @throws Exception
   */
  public String betchCreateRedeemcodeBySent(RedeemCode paramObj, int redeemCodeNum)
      throws Exception {
    logger.debug("betchCreateRedeemcodeBySent开始生产");

    List<RedeemCode> redeemCodeList = new ArrayList<RedeemCode>();
    for (int i = 0; i < redeemCodeNum; i++) {
      logger.debug("开始第" + (i + 1) + "次生产" + paramObj.getActivityName() + "活动的兑换码...");
      redeemCodeList.add(RedeemCodeUtil.createRedeemcodeObj(paramObj));
    }
    // 批量插入兑换码
    int successNum = redeemCodeEntityDao.batchInsert(redeemCodeList);

    // 所有兑换码插入成功
    if (redeemCodeNum == successNum) {
      // 生成兑换码文件
      return RedeemCodeUtil.createRedeemcodeFile(redeemCodeList);
    }

    return null;
  }

  /**
   * 
   * Title: 批量生产X开头的验证码<br>
   * Description: 批量生产X开头的验证码<br>
   * CreateDate: 2016年6月29日 下午2:50:39<br>
   * 
   * @category 批量生产X开头的验证码
   * @author seven.gz
   * @param redeemcodeHeader
   *          用哪个字母开头
   * @param aLength
   *          生成多少个
   * @param createUserId
   *          生成人
   * @param chooseActivity
   *          活动名称
   * @param activityStartTime
   *          活动开始时间
   * @param activityEndTime
   *          活动结束时间
   * @throws Exception
   */
  public void betchCreateARedeemcodeNew(RedeemCode paramObj, int aLength)
      throws Exception {
    for (int i = 0; i < aLength; i++) {
      logger.debug("开始第" + (i + 1) + "次生产" + paramObj.getActivityName() + "活动的兑换码...");
      redeemCodeEntityDao.insert(RedeemCodeUtil.createRedeemcodeObj(paramObj));
    }
  }

  /**
   * 
   * Title: 向用户发送兑换码<br>
   * Description: 向用户发送兑换码<br>
   * CreateDate: 2016年6月29日 下午4:21:32<br>
   * 
   * @category 向用户发送兑换码
   * @author seven.gz
   * @param activityName
   * @param userId
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      timeout = 15)
  public JsonMessage sendSms(String activityName, String phone) throws Exception {
    JsonMessage msg = new JsonMessage();
    // 活动名称为空则什么也不做
    if (!StringUtils.isEmpty(activityName)) {
      Map<String, Object> mapParam = new HashMap<String, Object>();
      // 活动名称
      mapParam.put("activity_name", activityName);
      // 没有被发送
      mapParam.put("is_sent", RedeemCodeConstant.REDEEM_CODE_UNSENT);
      // 没有使用过
      mapParam.put("redeem_user_id", "");
      // 查找一个没有发送出去的兑换码
      Map<String, Object> redeemCode = redeemcodeDao.findOne(mapParam, "key_id,redeem_code");
      if (redeemCode != null) {
        mapParam.clear();
        mapParam.put("key_id", redeemCode.get("key_id"));
        mapParam.put("is_sent", RedeemCodeConstant.REDEEM_CODE_SENT);
        // 更新这个对换码为已发送
        redeemcodeDao.update(mapParam);
        logger.info("向:" + phone + ", 发送兑换码:" + (String) redeemCode.get("redeem_code"));
        // “恭喜您领取成功！您的课程兑换码是：XXXXX 兑换登录网址是：XXXXX”
        // 修改短信方法
        SmsUtil.sendSmsToQueue(phone,
            SmsConstant.SEND_REDEEM_CODE_HEAD + (String) redeemCode.get("redeem_code")
                + SmsConstant.SEND_REDEEM_CODE_TAIL
                + MemcachedUtil.getValue("use_redeem_code_url"));
      } else {
        logger.error("此活动兑换码已用光...");
        msg.setSuccess(false);
        msg.setMsg("此活动兑换码已用光...");
      }
    } else {
      msg.setSuccess(false);
      msg.setMsg("活动名为空");
    }
    return msg;
  }
}
