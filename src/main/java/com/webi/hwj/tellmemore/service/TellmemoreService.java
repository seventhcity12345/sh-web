package com.webi.hwj.tellmemore.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.admin.dao.AdminBdminUserDao;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorConstant;
import com.webi.hwj.course.dao.CourseOne2OneDao;
import com.webi.hwj.statistics.dao.StatisticsMarathonDao;
import com.webi.hwj.tellmemore.constant.TellmemoreConstant;
import com.webi.hwj.tellmemore.dao.TellmemoreInstructionalDao;
import com.webi.hwj.tellmemore.dao.TellmemorePercentDao;
import com.webi.hwj.tellmemore.util.TellmemoreUtil;
import com.webi.hwj.user.dao.UserDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entitydao.AdminUserEntityDao;
import com.webi.hwj.util.TxtUtil;

/**
 * Title: TellMeMoreService<br>
 * Description: TellMeMoreService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月24日 上午11:12:33
 * 
 * @author alex
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TellmemoreService {
  private Logger logger = Logger.getLogger(TellmemoreService.class);

  @Autowired
  private CourseOne2OneDao courseOne2OneDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private TellmemorePercentDao tellmemorePercentDao;

  @Autowired
  private TellmemoreInstructionalDao tellmemoreInstructionalDao;

  @Resource
  private StatisticsMarathonDao statisticsMarathonDao;

  @Resource
  private AdminBdminUserDao adminBdminUserDao;
  @Resource
  AdminUserEntityDao adminUserEntityDao;

  /**
   * Title: 初始化tmm数据<br>
   * Description: 插入百分比表数据，调用tmm接口创建用户，设定组 <br>
   * CreateDate: 2015年10月21日 上午7:45:52<br>
   * 
   * @category 初始化tmm数据
   * @param login
   *          用户登录名(phone)
   * @param userId
   *          用户id
   * @author yangmh
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void initTmmData(SessionUser sessionUser) throws Exception {
    if (sessionUser.getCourseTypes().get("course_type6") == null) {
      // modified by alex.yang 2016年7月12日15:41:47
      // 只有合同包中带1v1的课程才会初始化rsa哟
      logger.info("开通RSA账号失败！因为合同里没有RSA课程！用户id：" + sessionUser.getKeyId());
      return;
    }

    String userId = sessionUser.getKeyId();

    // 查询用户级别
    Map<String, Object> userMap = userDao.findOneByKeyId(userId, "current_level,phone");
    String phone = (String) userMap.get("phone");

    // 判断用户级别。。。
    if (userMap != null && !"".equals(userMap.get("current_level"))
        && !"null".equals(userMap.get("current_level"))) {
      Map<String, Object> repeatParamMap = new HashMap<String, Object>();
      repeatParamMap.put("course_level", userMap.get("current_level"));
      repeatParamMap.put("user_id", userId);
      int percentCount = tellmemorePercentDao.findCount(repeatParamMap);
      logger.info("tellmemore------>percentCount=" + percentCount);

      // 不是重复提交(百分比表里没有当前用户当前级别的数据)，才可以继续初始化数据
      if (percentCount == 0) {
        // 0.查询出级别对应的课程数据列表,并且插入百分比数据表
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("course_level", userMap.get("current_level"));
        List<Map<String, Object>> courseList = courseOne2OneDao.findList(paramMap,
            "key_id,course_title,course_level");
        for (Map<String, Object> courseMap : courseList) {
          Map<String, Object> tellmemorePercentMap = new HashMap<String, Object>();
          tellmemorePercentMap.put("course_level", courseMap.get("course_level"));
          tellmemorePercentMap.put("course_id", courseMap.get("key_id"));
          tellmemorePercentMap.put("user_id", userId);
          tellmemorePercentMap.put("course_title", courseMap.get("course_title"));
          tellmemorePercentDao.insert(tellmemorePercentMap);
          logger.info("tellmemore------>插入percent表,user_id=" + userId + ";course_title="
              + courseMap.get("course_title"));
        }

        // 1.创建tmm用户,用户有可能已存在，因为需要考虑多个合同
        TellmemoreUtil.createTmmUser(phone, userId);

        // 2.分配语言组
        // 通过当前用户的级别来查询用户所属级别的tmm的课程体系的语言组id
        Map<String, Object> instructionalParamMap = new HashMap<String, Object>();
        instructionalParamMap.put("instructional_name", userMap.get("current_level"));
        Map<String, Object> tellmemorePercentMap = tellmemoreInstructionalDao
            .findOne(instructionalParamMap, "instructional_id");
        if (tellmemorePercentMap == null) {
          logger.error("tellmemore------>元数据错误,找不到符合条件的tmm课程体系," + userMap.get("current_level"));
          throw new RuntimeException(
              "tellmemore------>元数据错误,找不到符合条件的tmm课程体系," + userMap.get("current_level"));
        } else {
          TellmemoreUtil.assignGroup(phone, tellmemorePercentMap.get("instructional_id").toString(),
              TellmemoreConstant.GROUP_TYPE_PEDAGOGICAL_INSTRUCTIONAL);
        }

        // 3.分配管理组
        TellmemoreUtil.assignGroup(phone, TellmemoreConstant.PTF_GROUP_NUMBER,
            TellmemoreConstant.GROUP_TYPE_ADMINISTRATIVE);
      }
    } else {
      // logger.error("tellmemore------>用户没有级别！！！"+userId);
      // throw new RuntimeException("tellmemore------>用户没有级别！！！"+userId);
    }
  }

  /**
   * 
   * Title: 抓取单个用户的三位属性的所有课程的总和<br>
   * Description: 总和减去上一个小时的数据，插入数据库<br>
   * CreateDate: 2016年3月24日 下午1:54:56<br>
   * 
   * @category 抓取单个用户的三位属性的所有课程的总和
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE)
  public void fetchTmmUserPercentAndInsertByUserPhone() throws Exception {
    logger.info("开始读取TXT文件---------->马拉松系统Lc旗下学员");

    String loadAddress = "d:\\tmm_marathon_data.txt";
    // 如果是生产环境
    if ("pro".equals(MemcachedUtil.getConfigValue("env"))) {
      loadAddress = File.separator + "usr" + File.separator + "tmm_marathon_data.txt";
    } else if ("test".equals(MemcachedUtil.getConfigValue("env"))) {
      loadAddress = File.separator + "usr" + File.separator + "tmm_marathon_data.txt";
    }

    List<List<String>> userList = TxtUtil.loadTxtFile(loadAddress);

    // txt的格式为：教务账号,rsa账号,学员姓名

    /// *
    for (final List<String> user : userList) {
      // TODO 多线程使用原因：学员数量如果特别大，那么会导致程序串行，性能降低，目前为每个学生一个线程。
      // new Thread(new Runnable() {
      // public void run() {
      try {
        insertMarathonData(user);

      } catch (Exception e) {
        e.printStackTrace();
        logger.error("系统异常，error:" + e.toString());
      }
      // }
      // }).start();

    }
    // */

    logger.debug("抓取该教务所有学员信息结束------>");
  }

  /**
   * Title: 计算并插入马拉松学员统计数据<br>
   * Description: 计算并插入马拉松学员统计数据<br>
   * CreateDate: 2016年6月23日 下午3:08:40<br>
   * 
   * @category 计算并插入马拉松学员统计数据
   * @author komi.zsy
   * @param user
   * @throws Exception
   */
  public void insertMarathonData(final List<String> user) throws Exception {
    logger.debug("抓取tmm数据并更新,当前学员------>" + user);

    // rsa账号
    String phone = user.get(1).trim();

    Map<String, Object> pupilSum = TellmemoreUtil.fetchUserInformationAndSumByUserPhone(phone);

    if (pupilSum == null) {
      logger.debug("tmm学员rsa返回无信息,当前学员------>" + user);
      return;
    }

    // String key_id = UUIDUtil.uuid(32);
    // pupilSum.put("key_id", key_id);
    pupilSum.put("lc_id", "");
    // 教务账号
    pupilSum.put("lc_name", user.get(0).trim());
    pupilSum.put("rsa_account", phone);
    // 学员名称,不为空时存入，为空时默认存入空字符
    if (user.size() > 2) {
      pupilSum.put("user_name", user.get(2).trim());
    } else {
      pupilSum.put("user_name", "");
    }

    // 计算当前时间
    Calendar cal = Calendar.getInstance();
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // System.out.println(sdf.format(cal.getTime()));

    cal.add(Calendar.HOUR_OF_DAY, -1);
    // sdf.format(cal.getTime())+ " " + cal.get(Calendar.HOUR_OF_DAY) +
    // ":59:59";
    String currentTime = DateUtil.formatDate(cal.getTime().getTime(), "yyyy-MM-dd HH:mm:ss");
    // System.out.println(currentTime);

    // 2016-03-08 12:00:01
    pupilSum.put("create_date", currentTime);
    // 计算前一小时时间
    // cal.add(Calendar.HOUR_OF_DAY, -1);
    // String exTime = sdf.format(cal.getTime())+ " " +
    // cal.get(Calendar.HOUR_OF_DAY) + ":59:59";
    // System.out.println(exTime);

    // 差值变量
    int percentdoneChange = Integer.parseInt(pupilSum.get("total_tmm_percent").toString());
    int percentcorrectChange = Integer.parseInt(pupilSum.get("total_tmm_correct").toString());
    List<Integer> timeChange = (List<Integer>) pupilSum.get("workingTimeList");

    // System.out.println("秒"+timeChange.get(2));

    Map<String, Object> pupilParamMap = new HashMap<String, Object>();
    // pupilParamMap.put("create_date",exTime);// "2016-03-25 09:00:00"
    pupilParamMap.put("rsa_account", phone);

    // 用户最近时间一条数据信息
    Map<String, Object> exPupil = statisticsMarathonDao
        .findMarathonByRsaAccountAndTime(pupilParamMap);

    logger.info("抓取sql学员信息------>" + exPupil);

    // 数据库数据转换
    int percentdoneEx = 0;
    int percentcorrectEx = 0;
    List<Integer> timeEx = null;

    if (exPupil != null) {

      percentdoneEx = Integer.parseInt(exPupil.get("total_tmm_percent").toString());
      percentcorrectEx = Integer.parseInt(exPupil.get("total_tmm_correct").toString());
      String workingTime = exPupil.get("total_tmm_workingtime").toString();
      timeEx = TellmemoreUtil.getTimeInInt(workingTime);

      percentdoneChange -= percentdoneEx;
      percentcorrectChange -= percentcorrectEx;
      for (int i = 0; i < 3; i++) {
        timeChange.set(i, timeChange.get(i) - timeEx.get(i));
      }

      // System.out.println(Integer.parseInt(pupilEx.get("total_tmm_percent").toString()));

      pupilSum.put("change_tmm_percent", percentdoneChange);
      pupilSum.put("change_tmm_correct", percentcorrectChange);
      pupilSum.put("change_tmm_workingtime", TellmemoreUtil.getTimeInString(timeChange));
    } else {
      pupilSum.put("change_tmm_percent", 0);
      pupilSum.put("change_tmm_correct", 0);
      pupilSum.put("change_tmm_workingtime", "0:00:00");
    }

    pupilSum.remove("workingTimeList");

    logger.info("抓取tmm学员结束并更新至数据库------>" + pupilSum);

    statisticsMarathonDao.insert(pupilSum);

  }

  /**
   * Title: 抓取用户百分比数据并设置到我们的库里(临时)<br>
   * Description: 抓取用户百分比数据并设置到我们的库里<br>
   * CreateDate: 2015年10月21日 下午7:38:52<br>
   * 
   * @category 抓取用户百分比数据并设置到我们的库里
   * @author yangmh
   * @return
   * @throws Exception
   */
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE)
  public int fetchTmmUserPercentAndUpdate(int pageNumber, int pageSize) throws Exception {
    logger.info("抓取tmm数据并更新,当前页数------>" + pageNumber);
    List<Map<String, Object>> pupilList = TellmemoreUtil.fetchUserInformation(pageNumber, pageSize);
    // 总人数,从rsa接口中获取
    int totalpupilcount = 0;

    /**
     * modified by komi 2016年6月23日17:43:51 如果为null则直接跳出
     */
    if (pupilList == null) {
      return totalpupilcount;
    }

    for (int i = 0; i < pupilList.size(); i++) {

      Map<String, Object> pupil = pupilList.get(i);
      if (i == 0) {
        // 获取summary数据
        totalpupilcount = Integer.valueOf(pupil.get("totalpupilcount") + "");
        continue;
      }

      List<Map<String, Object>> sequenceList = (List) pupil.get("sequenceList");
      // 更新用户rsa数据
      updateTmmPercentByUserId(pupil.get("lastname").toString(), sequenceList);
    }
    return totalpupilcount;
  }

  /**
   * Title: 获取传入用户的所有课程百分比<br>
   * Description: findUserPercentMap<br>
   * CreateDate: 2015年10月21日 下午8:11:21<br>
   * 
   * @category findUserPercentMap
   * @author yangmh
   * @param userId
   *          学员id
   * @parma currentLevel 当前级别
   * @return returnMap key为course_id,value为tmm_percent
   * @throws Exception
   */
  public Map<String, Object> findUserPercentMap(String userId, String currentLevel)
      throws Exception {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("user_id", userId);
    /**
     * modified by komi 2016年8月22日10:16:42 要更新学员全部rsa数据，去除用户级别条件
     */
    if (!StringUtils.isEmpty(currentLevel)) {
      paramMap.put("course_level", currentLevel);
    }
    List<Map<String, Object>> percentList = tellmemorePercentDao.findList(paramMap,
        "key_id,course_id,course_title,tmm_percent,tmm_correct,tmm_workingtime,course_level");
    // 行列转换
    for (Map<String, Object> percentMap : percentList) {
      returnMap.put(percentMap.get("course_title").toString() + ","
          + percentMap.get("course_level").toString(), percentMap);
    }
    return returnMap;
  }

  /**
   * Title: 判断用户是否完成课件<br>
   * Description: 如果没有完成则事务回滚<br>
   * CreateDate: 2015年10月21日 下午9:52:28<br>
   * 
   * @category 判断用户是否完成课件
   * @author yangmh
   * @param courseId
   *          课程id
   * @param userId
   *          用户id
   * @throws Exception
   */
  public JsonMessage judgeUserPercentComplete(String courseId, String userId) throws Exception {
    JsonMessage json = new JsonMessage();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("course_id", courseId);
    paramMap.put("user_id", userId);

    // 检查数据表中的课件完成度
    Map<String, Object> tellMeMoreMap = tellmemorePercentDao.findOne(paramMap, "tmm_percent");
    if (tellMeMoreMap == null) {
      // 没有tellmemore课件的记录，异常
      logger.error(
          ErrorConstant.NO_THIS_TELLMEMORE_PERCENT + "userId=" + userId + ", courseId=" + courseId);
      json.setMsg("请联系工作人员为您开通tellmemore课程体系~");
      json.setSuccess(false);
      return json;
    }

    // 如果实际完成度，小于限定的完成度tmm_limit_percent
    if (Integer.parseInt(tellMeMoreMap.get("tmm_percent").toString()) < Integer
        .parseInt(MemcachedUtil.getConfigValue("tmm_limit_percent").toString())) {
      // 没有达到课件完成度要求，不能预约
      logger.error(ErrorConstant.HAVE_NOT_REQUIRE_PERCENT + "userId=" + userId);
      json.setMsg(ErrorConstant.HAVE_NOT_REQUIRE_PERCENT);
      json.setSuccess(false);
    }
    return json;
  }

  /**
   * 
   * Title: 获取用户学习总时间<br>
   * Description: 获取用户学习总时间<br>
   * CreateDate: 2016年6月23日 下午5:06:21<br>
   * 
   * @category 获取用户学习总时间
   * @author seven.gz
   * @param userId
   * @return List 为时间的列表, 0-小时 1-分钟 2-秒
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = { Exception.class })
  public List<Integer> findTellmemoreTotalTimeByUserId(String userId) throws Exception {
    // 初始化时间为零
    List<Integer> tempList = new ArrayList<Integer>();
    tempList.add(0);
    tempList.add(0);
    tempList.add(0);
    // 获得tellmemore表中的用户数据
    List<Map<String, Object>> tellmemoreUserInfoList = tellmemorePercentDao
        .findTmmUserInfoListByKeyId(userId);
    // 如果没有查到数据则退出
    if (tellmemoreUserInfoList == null || tellmemoreUserInfoList.size() == 0) {
      return tempList;
    }
    // 计算出此用户今天的总学习时间
    for (Map<String, Object> tellmemoreUserInfo : tellmemoreUserInfoList) {
      String tmmWorkingTime = tellmemoreUserInfo.get("tmm_workingtime").toString();
      if (StringUtils.isEmpty(tmmWorkingTime)) {
        tmmWorkingTime = "0:0:0";
      }
      List<Integer> tmmTotalList = TellmemoreUtil.getTimeInInt(tmmWorkingTime);
      tempList.set(0, tempList.get(0) + tmmTotalList.get(0));
      tempList.set(1, tempList.get(1) + tmmTotalList.get(1));
      tempList.set(2, tempList.get(2) + tmmTotalList.get(2));
    }
    return tempList;
  }

  /**
   * 
   * Title: 获取用户总的学习时间<br>
   * Description: 获取用户总的学习时间<br>
   * CreateDate: 2017年8月9日 上午10:22:05<br>
   * 
   * @category 获取用户总的学习时间
   * @author felix.yl
   * @param userId
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = { Exception.class })
  public List<Integer> findTellmemoreAllTimeByUserId(String userId) throws Exception {
    // 初始化时间为零
    List<Integer> tempList = new ArrayList<Integer>();
    tempList.add(0);
    tempList.add(0);
    tempList.add(0);

    // 获得tellmemore表中的用户数据
    List<Map<String, Object>> tellmemoreUserInfoList = tellmemorePercentDao
        .findTmmAllInfoListByKeyId(userId);

    // 如果没有查到数据则退出
    if (tellmemoreUserInfoList == null || tellmemoreUserInfoList.size() == 0) {
      return tempList;
    }

    // 获取用户总的学习时间(查询出的list的多条数据的时间求和)
    for (Map<String, Object> tellmemoreUserInfo : tellmemoreUserInfoList) {
      String tmmWorkingTime = tellmemoreUserInfo.get("tmm_workingtime").toString();
      if (StringUtils.isEmpty(tmmWorkingTime)) {
        tmmWorkingTime = "0:0:0";
      }
      List<Integer> tmmTotalList = TellmemoreUtil.getTimeInInt(tmmWorkingTime);
      tempList.set(0, tempList.get(0) + tmmTotalList.get(0));
      tempList.set(1, tempList.get(1) + tmmTotalList.get(1));
      tempList.set(2, tempList.get(2) + tmmTotalList.get(2));
    }
    return tempList;
  }

  /**
   * 
   * Title: 获取昨天之前用户学习总时间<br>
   * Description: 获取昨天之前用户学习总时间<br>
   * CreateDate: 2017年8月8日 下午9:05:32<br>
   * 
   * @category 获取昨天之前用户学习总时间
   * @author felix.yl
   * @param userId
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = { Exception.class })
  public List<Integer> findTellmemoreTotalTimeYesterdayByUserId(String userId) throws Exception {
    // 初始化时间为零
    List<Integer> tempList = new ArrayList<Integer>();
    tempList.add(0);
    tempList.add(0);
    tempList.add(0);

    // 查询t_statistics_tellmemore_day表(该查询其实只返回一条数据,就是最新的定时任务插入的那条数据)
    List<Map<String, Object>> tellmemoreUserInfoList = tellmemorePercentDao
        .findTmmYesterdayUserInfoListByKeyId(userId);
    // 如果没有查到数据则退出
    if (tellmemoreUserInfoList == null || tellmemoreUserInfoList.size() == 0) {
      return tempList;
    }

    // 获取昨天之前用户学习总时间
    for (Map<String, Object> tellmemoreUserInfo : tellmemoreUserInfoList) {
      String totalTmmWorkingtime = tellmemoreUserInfo.get("total_tmm_workingtime").toString();
      if (StringUtils.isEmpty(totalTmmWorkingtime)) {
        totalTmmWorkingtime = "0:0:0";
      }
      List<Integer> tmmTotalList = TellmemoreUtil.getTimeInInt(totalTmmWorkingtime);
      tempList.set(0, tempList.get(0) + tmmTotalList.get(0));
      tempList.set(1, tempList.get(1) + tmmTotalList.get(1));
      tempList.set(2, tempList.get(2) + tmmTotalList.get(2));
    }
    return tempList;
  }

  /**
   * 
   * Title: 同步更新rsa学习时间和进度<br>
   * Description: 同步更新rsa学习时间和进度<br>
   * CreateDate: 2016年6月24日 下午3:06:38<br>
   * 
   * @category 同步更新rsa学习时间和进度
   * @author seven.gz
   * @param userPhone
   * @param userId
   * @param currentLevel
   * @throws Exception
   */
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE)
  public void updateTellmemorePercent(String userPhone, String userId, String currentLevel)
      throws Exception {
    List<Map<String, Object>> rsaMapList = TellmemoreUtil
        .fetchUserInformationByUserPhone(userPhone);
    // 更新用户rsa数据
    updateTmmPercentByUserId(userId, rsaMapList);
  }

  /**
   * Title: 处理更新用户rsa数据<br>
   * Description: 更新用户rsa数据<br>
   * CreateDate: 2016年9月9日 下午4:05:19<br>
   * 
   * @category 处理更新用户rsa数据
   * @author komi.zsy
   * @param userId
   * @param rsaMapList
   * @throws Exception
   */
  public void updateTmmPercentByUserId(String userId, List<Map<String, Object>> rsaMapList)
      throws Exception {
    if (rsaMapList == null) {
      // modified by seven 2016年8月6日12:08:16 修改日志级别
      logger.info("rsa同步无效,没有此用户!!!!!!!--->userId=" + userId);
    } else {
      // 查询当前用户+级别所在speakhi数据库中的rsa数据
      /**
       * modified by komi 2016年8月22日10:16:42 要更新学员全部rsa数据，去除用户级别条件
       */
      Map<String, Object> speakHiPercentMap = findUserPercentMap(userId, null);

      // 行列转换,key是name
      for (Map<String, Object> rsaMap : rsaMapList) {
        Map<String, Object> updateMap = (Map<String, Object>) speakHiPercentMap
            .get(rsaMap.get("name") + "," + rsaMap.get("courseLevel"));
        if (updateMap != null) {
          String percentcorrect = (String) rsaMap.get("percentcorrect");
          if ("N/A".equals(percentcorrect) || "n/a".equals(percentcorrect)) {
            percentcorrect = "100";
          }

          // 如果进度等数据没有变动则不更新。
          if (updateMap.get("tmm_percent").toString().equals(rsaMap.get("percentdone"))
              && updateMap.get("tmm_correct").toString().equals(percentcorrect)
              && updateMap.get("tmm_workingtime").toString().equals(rsaMap.get("workingTime"))) {
            continue;
          }

          updateMap.put("tmm_percent", rsaMap.get("percentdone"));
          updateMap.put("tmm_correct", percentcorrect);
          updateMap.put("tmm_workingtime", rsaMap.get("workingTime"));
          updateMap.put("course_level", rsaMap.get("courseLevel"));
          tellmemorePercentDao.update(updateMap);
        }
      }
    }
  }

  /**
   * Title: 始删除TMM用户<br>
   * Description: 始删除TMM用户<br>
   * CreateDate: 2016年8月16日 下午2:12:49<br>
   * 
   * @category 始删除TMM用户
   * @author komi.zsy
   * @param userId
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void deleteTmmUser(String userId) throws Exception {
    User user = adminUserEntityDao.findOneByKeyId(userId);
    String userPhone = user.getPhone();
    logger.info("开始删除t_tellmemore_percent用户数据--------->phone = " + userPhone);
    tellmemorePercentDao.deleteAllByUserId(userId);
    logger.info("开始删除TMM用户数据--------->phone = " + userPhone);
    try {
      TellmemoreUtil.userRemoval(userPhone);
    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      SmsUtil.sendAlarmSms(
          "RSA账号删除失败！userId= " + userId + ",userPhone=" + userPhone);
    }
  }

}