package com.webi.hwj.trainingcamp.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mingyisoft.javabase.aliyun.ocs.OSSClientUtil;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SqlUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.param.SubscribeCourseCountParam;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.trainingcamp.dao.TrainingCampEntityDao;
import com.webi.hwj.trainingcamp.dao.TrainingMemberEntityDao;
import com.webi.hwj.trainingcamp.entity.TrainingCamp;
import com.webi.hwj.trainingcamp.entity.TrainingMember;
import com.webi.hwj.trainingcamp.param.TrainingCampClanIntegralRankingApiParam;
import com.webi.hwj.trainingcamp.param.TrainingCampClanIntegralRankingParam;
import com.webi.hwj.trainingcamp.param.TrainingCampClanMemberApiParam;
import com.webi.hwj.trainingcamp.param.TrainingCampClanMemberParam;
import com.webi.hwj.trainingcamp.param.TrainingCampIntegralProfileParam;
import com.webi.hwj.trainingcamp.param.TrainingCampParam;
import com.webi.hwj.trainingcamp.param.TrainingMemberTotalParam;

/**
 * 
 * Title: 口语训练营相关<br>
 * Description: 口语训练营相关<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月8日 下午3:03:44
 * 
 * @author felix.yl
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class TrainingCampService {

  private static Logger logger = Logger.getLogger(TrainingCampService.class);

  @Resource
  TrainingCampEntityDao trainingCampEntityDao;

  @Resource
  TrainingMemberEntityDao trainingMemberEntityDao;

  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;

  @Resource
  TellmemoreService tellmemoreService;

  @Resource
  TrainingMemberService trainingMemberService;

  /**
   * Title: 新增/编辑训练营数据<br>
   * Description: 新增/编辑训练营数据<br>
   * CreateDate: 2017年8月11日 下午6:46:40<br>
   * 
   * @category 新增/编辑训练营数据
   * @author komi.zsy
   * @param trainingCamp
   *          训练营数据
   * @param trainingCampPic
   *          训练营封面
   * @param updateUserId
   *          更新人id
   * @throws Exception
   */
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public void saveTrainingCamp(TrainingCamp trainingCamp,
      MultipartFile trainingCampPic, String updateUserId) throws Exception {
    trainingCamp.setUpdateUserId(updateUserId);

    // aliyun上保存图片的路径 images/user/phoneNumber
    String aliyunPath = "images/trainingCamp/";
    // String imagePaths = OSSClientUtil.uploadPhoto(request, fieldName,
    // aliyunPath);
    String imagePaths = OSSClientUtil.uploadFile(trainingCampPic, SqlUtil.createUUID(), aliyunPath);

    // 如果没有keyid就是新增，有就是编辑
    if (StringUtils.isEmpty(trainingCamp.getKeyId())) {
      trainingCamp.setTrainingCampPic(null);
      if (!StringUtils.isEmpty(imagePaths)) {
        trainingCamp.setTrainingCampPic(imagePaths);
      }
      trainingCamp.setCreateUserId(updateUserId);
      trainingCamp.setTrainingCampLearningCoachId(updateUserId);

      // 先插入，获得训练营id
      trainingCampEntityDao.insert(trainingCamp);
      // 插入训练营成员，并获得下标0为平均年龄，1为平均级别，2为女性占百分比,3为当前训练营人数
      List<Integer> avg = trainingMemberService.insertTrainingMember(trainingCamp.getKeyId(),
          trainingCamp.getTrainingCampLearningCoachId(),
          trainingCamp.getTrainingCampStartTime(), trainingCamp.getTrainingCampEndTime(),
          updateUserId);
      trainingCamp.setTrainingCampAverageAge(avg.get(0));
      trainingCamp.setTrainingCampAverageLevel(avg.get(1));
      trainingCamp.setTrainingCampFemalePresent(avg.get(2));
      trainingCamp.setTrainingCampNum(avg.get(3));
      trainingCampEntityDao.update(trainingCamp);
    } else {
      // 原来照片路径
      String currentPhoto = trainingCamp.getTrainingCampPic();
      trainingCamp.setTrainingCampPic(null);
      if (!StringUtils.isEmpty(imagePaths)) {
        trainingCamp.setTrainingCampPic(imagePaths);
        // 如果原来不是默认图片，删除原来图片
        if (!"http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images/trainingCamp/default/trainingCamp_default_photo.png"
            .equals(currentPhoto)) {
          try {
            String finalOssPath = currentPhoto
                .replace(MemcachedUtil.getConfigValue("aliyun_oss_returnurl"), "");
            OSSClientUtil.deleteFile(finalOssPath);
          } catch (Exception e) {
            logger.error("删除训练营原来图片失败------>" + e.toString());
          }
        }
      }

      // 其实前端已经把这两个字段屏蔽掉了，这里做个保险，不改时间
      trainingCamp.setTrainingCampStartTime(null);
      trainingCamp.setTrainingCampEndTime(null);
      trainingCampEntityDao.update(trainingCamp);
    }
  }

  /**
   * Title: 查询所有训练营列表<br>
   * Description: 查询所有训练营列表<br>
   * CreateDate: 2017年8月8日 下午4:47:24<br>
   * 
   * @category 查询所有训练营列表
   * @author komi.zsy
   * @param cons
   *          组合查询
   * @param sort
   * @param order
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(String cons, String sort, String order, Integer page,
      Integer rows) throws Exception {
    return trainingCampEntityDao.findPageEasyui(cons, sort, order, page, rows);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:00<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(String keyIds, String updateUserId) throws Exception {
    return trainingCampEntityDao.delete(keyIds, updateUserId);
  }

  /**
   * 
   * Title: 查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比<br>
   * Description: 查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比<br>
   * CreateDate: 2017年8月8日 下午3:08:10<br>
   * 
   * @category 查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比
   * @author felix.yl
   * @param sessionUser
   * @return
   * @throws Exception
   */
  public TrainingCampParam findTrainingCampInfo(SessionUser sessionUser)
      throws Exception {

    // 获取当前登录用户的userId
    String userId = sessionUser.getKeyId();

    // 构建当前时间(不直接在参数里面new Date()的原因是防止缓存失效)
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
    String currentTime = df.format(new Date());
    Date date = df.parse(currentTime);

    // 根据用户的userId去查询训练营成员表,返回训练营id
    TrainingMember trainingMember = trainingMemberEntityDao
        .findTrainingMemberListByUserIdUsingMemcached(userId, date);
    if (trainingMember != null) {

      String trainingCampId = trainingMember.getTrainingCampId();

      // 根据训练营id,查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比
      TrainingCampParam trainingCampInfo = trainingCampEntityDao
          .findTrainingCampInfoUsingMemcached(
              trainingCampId, date);
      return trainingCampInfo;
    } else {
      return null;
    }
  }

  /**
   * 
   * Title: 查询积分概况接口信息<br>
   * Description: 查询积分概况接口信息<br>
   * CreateDate: 2017年8月8日 下午8:28:47<br>
   * 
   * @category 查询积分概况接口信息
   * @author felix.yl
   * @param sessionUser
   * @return
   * @throws Exception
   */
  public TrainingCampIntegralProfileParam findTrainingCampIntegralProfileParmInfo(
      SessionUser sessionUser)
          throws Exception {
    // 构建传给前端的对象
    TrainingCampIntegralProfileParam trainingCampIntegralProfileParm =
        new TrainingCampIntegralProfileParam();

    // 获取当前登录用户的userId
    String userId = sessionUser.getKeyId();

    // 查询个人总积分、课件学习分、预约上课分、战队活动分(即该学员的加分总分)
    TrainingCampIntegralProfileParam trainingMemberScore = trainingMemberEntityDao
        .findTrainingMemberScoreByUserId(userId, new Date());
    if (trainingMemberScore.getKeyId() != null) {
      String keyId = trainingMemberScore.getKeyId();// 训练营表的主键(确定团队奖励分用)

      trainingCampIntegralProfileParm.setTrainingMemberTotalScore(trainingMemberScore
          .getTrainingMemberTotalScore());// 个人总积分
      trainingCampIntegralProfileParm.setTrainingMemberRsaScore(trainingMemberScore
          .getTrainingMemberRsaScore());// RSA课件学习分
      trainingCampIntegralProfileParm.setTrainingMemberCourseScore(trainingMemberScore
          .getTrainingMemberCourseScore());// 预约上课分
      trainingCampIntegralProfileParm.setTrainingMemberOptionScore(trainingMemberScore
          .getTrainingMemberOptionScore());// 战队活动分

      // 查询团队奖励分
      List<TrainingCamp> trainingCampMaxAvgScoreList = trainingCampEntityDao
          .findTrainingCampMaxAvgScoreInfo();

      // 确定团队奖励分
      trainingCampIntegralProfileParm.setTrainingMemberBonusScore(0);// 默认0分
      for (int i = 0; i < trainingCampMaxAvgScoreList.size(); i++) {
        String maxAvgScorekeyId = trainingCampMaxAvgScoreList.get(i).getKeyId();
        if (keyId.equals(maxAvgScorekeyId)) {
          trainingCampIntegralProfileParm.setTrainingMemberBonusScore(5);// 团队奖励分
        }
      }

      // 格式化当前时间:开始计算今日学习时间
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
      String currentTime = df.format(new Date());
      String currentStartTimeStr = currentTime + " 00:00:00";
      String currentEndTimeStr = currentTime + " 23:59:59";
      Date currentStartTime = df.parse(currentStartTimeStr);
      Date currentEndTime = df.parse(currentEndTimeStr);

      // 获取预约表中1v1课节数
      SubscribeCourseCountParam countOneToOneCourse = subscribeCourseEntityDao
          .findCountOneToOneCourse(userId, currentStartTime, currentEndTime);
      Integer oneToOneCount = countOneToOneCourse.getOneToOneCountCourse();// 1v1课程节数(*30)

      // 获取预约表中1vn课节数
      SubscribeCourseCountParam countOneToManyCourse = subscribeCourseEntityDao
          .findCountOneToManyCourse(userId, currentStartTime, currentEndTime);
      Integer oneToManyCount = countOneToManyCourse.getOneToManyCountCourse();// 1vn课程节数(*60)

      // RSA学习时长(下面获取到的时间格式是时:分:秒格式的,转换为分钟再做下面两个参数的减法运算得出今日RSA学习时长)
      List<Integer> studentLearnTimeList = tellmemoreService.findTellmemoreAllTimeByUserId(
          userId);// 总的学习时长(查询出的多条list数据求和的,可以认为是即时的)
      List<Integer> studentYesterdayLearnTimeList = tellmemoreService
          .findTellmemoreTotalTimeYesterdayByUserId(userId);// 昨天之前的学习时长(获取到定时任务插入的最新的一条数据)

      // 现在这里算一下被减数的值(因为下面就要被更新了)
      Integer initHour = studentLearnTimeList.get(0);
      Integer initMinute = studentLearnTimeList.get(1);
      Integer initSecond = studentLearnTimeList.get(2);
      Integer initTodayRsaStudyTime = initHour * 60 * 60 + initMinute * 60 + initSecond;// 被减数(换算成秒,先算出来考虑合同过期学员的情况)

      studentLearnTimeList.set(0, studentLearnTimeList.get(0) - studentYesterdayLearnTimeList.get(
          0));
      studentLearnTimeList.set(1, studentLearnTimeList.get(1) - studentYesterdayLearnTimeList.get(
          1));
      studentLearnTimeList.set(2, studentLearnTimeList.get(2) - studentYesterdayLearnTimeList.get(
          2));
      Integer hour = studentLearnTimeList.get(0);
      Integer minute = studentLearnTimeList.get(1);
      Integer second = studentLearnTimeList.get(2);
      Integer todayRsaStudyTime = hour * 60 * 60 + minute * 60 + second;// 今日RSA学习时长(换算成秒)
      Integer todayRsaStudyTimeSencond = todayRsaStudyTime / 60;

      // 如果计算结果小于0时,除了数据错乱,可能的原因就是合同过期的学员
      if (todayRsaStudyTime < 0) {
        todayRsaStudyTimeSencond = initTodayRsaStudyTime / 60;
      }

      // 今日学习时间
      Integer todayStudyTime = oneToOneCount * 30 + oneToManyCount * 60 + todayRsaStudyTimeSencond;
      trainingCampIntegralProfileParm.setTodayStudyTime(todayStudyTime + "");// 今日学习时间

      return trainingCampIntegralProfileParm;
    } else {
      return null;
    }
  }

  /**
   * 
   * Title: 战队积分排名接口<br>
   * Description: 战队积分排名接口<br>
   * CreateDate: 2017年8月9日 上午11:30:18<br>
   * 
   * @category 战队积分排名接口
   * @author felix.yl
   * @param sessionUser
   * @return
   * @throws Exception
   */
  public TrainingCampClanIntegralRankingApiParam findTrainingCampClanIntegralRankingInfo(
      SessionUser sessionUser)
          throws Exception {

    // 获取当前登录用户的userId
    String userId = sessionUser.getKeyId();

    // 查询出有效的训练营
    List<TrainingCampParam> trainingCampParamList = trainingCampEntityDao
        .findTrainingCampIdEffectiveInfo(new Date());
    // 将有效训练营的keyId存储在一个list集合中
    List<String> keyIdList = new ArrayList<String>();
    for (int i = 0; i < trainingCampParamList.size(); i++) {
      String keyId = trainingCampParamList.get(i).getKeyId();
      keyIdList.add(keyId);
    }

    if (keyIdList.size() == 0) {
      return null;
    }

    // 根据有效的训练营id查询所有在有效训练营里按照个人积分从高到底排列的前15名(不考虑当前学员是否在前15名)
    List<TrainingMember> rankingTopFifteenList = trainingMemberEntityDao
        .findTrainingMemberRankingBykeyId(keyIdList);

    /**
     * 整合对象
     */
    // 构建list集合
    List<TrainingCampClanIntegralRankingParam> trainingCampClanIntegralRankingParamList =
        new ArrayList<TrainingCampClanIntegralRankingParam>();

    int beforeRanking = 0;
    for (int j = 0; j < rankingTopFifteenList.size(); j++) {
      // 获取对象
      TrainingMember trainingMember = rankingTopFifteenList.get(j);

      // 新建对象
      TrainingCampClanIntegralRankingParam paramObj = new TrainingCampClanIntegralRankingParam();

      // 名词
      if (j == 0) {
        paramObj.setRanking(j + 1);
      } else {
        int totalScore1 = rankingTopFifteenList.get(j).getTrainingMemberTotalScore();// 当前的总积分
        int totalScore2 = rankingTopFifteenList.get(j - 1).getTrainingMemberTotalScore();// 上一个总积分
        if (totalScore1 == totalScore2) {
          paramObj.setRanking(beforeRanking);
        } else {
          paramObj.setRanking(j + 1);
        }
      }
      paramObj.setTrainingMemberPic(trainingMember.getTrainingMemberPic());// 头像
      paramObj.setTrainingMemberEnglishName(trainingMember.getTrainingMemberEnglishName());// 英文名
      paramObj.setTrainingMemberTotalScore(trainingMember.getTrainingMemberTotalScore());// 个人总积分
      paramObj.setUserId(trainingMember.getTrainingMemberUserId());
      paramObj.setIsOwnOneself("N");

      // 每次将对象都添加到list集合中
      trainingCampClanIntegralRankingParamList.add(paramObj);
      beforeRanking = paramObj.getRanking();
    }

    // 查询当前登录学员信息
    List<TrainingMember> rankingOneselfList = trainingMemberEntityDao
        .findTrainingMemberRankingOneselfBykeyId(keyIdList, userId);
    if (rankingOneselfList.size() > 0) {

      TrainingMember trainingMember = rankingOneselfList.get(0);
      // 当前学员的个人总积分
      Integer trainingMemberTotalScore = trainingMember.getTrainingMemberTotalScore();
      // 计算当前学员的名次
      TrainingMemberTotalParam studentHighRanking = trainingMemberEntityDao.findStudentHighRanking(
          keyIdList, trainingMemberTotalScore);
      Integer ranking = studentHighRanking.getHighNum() + 1;// 当前学员的名次

      TrainingCampClanIntegralRankingParam studentParamObj =
          new TrainingCampClanIntegralRankingParam();
      studentParamObj.setRanking(ranking);
      studentParamObj.setTrainingMemberPic(trainingMember.getTrainingMemberPic());
      studentParamObj.setTrainingMemberEnglishName(trainingMember.getTrainingMemberEnglishName());
      studentParamObj.setTrainingMemberTotalScore(trainingMemberTotalScore);
      studentParamObj.setUserId(userId);
      studentParamObj.setIsOwnOneself("Y");

      trainingCampClanIntegralRankingParamList.add(studentParamObj);

      /**
       * 下面开始计算高于平台百分比
       */
      // 查询当前有效学员的总人数
      TrainingMemberTotalParam studentAllRanking = trainingMemberEntityDao.findStudentAllRanking(
          keyIdList);
      Integer totalNum = studentAllRanking.getTotalNum();

      // 查询个人总积分小于当前学员的人数
      TrainingMemberTotalParam studentLowRanking = trainingMemberEntityDao.findStudentLowRanking(
          keyIdList, trainingMemberTotalScore);
      Integer lowNum = studentLowRanking.getLowNum();

      // 计算高于平台百分比
      double highPercentageDouble = new BigDecimal((float) lowNum / totalNum).setScale(2,
          BigDecimal.ROUND_HALF_UP).doubleValue();
      Integer highPercentage = (int) (highPercentageDouble * 100);

      // 最终传给前端的对象
      TrainingCampClanIntegralRankingApiParam trainingCampClanIntegralRankingApiParam =
          new TrainingCampClanIntegralRankingApiParam();
      trainingCampClanIntegralRankingApiParam.setTrainingCampClanIntegralRankingParamList(
          trainingCampClanIntegralRankingParamList);
      trainingCampClanIntegralRankingApiParam.setHighPercentage(highPercentage);

      return trainingCampClanIntegralRankingApiParam;
    } else {
      return null;
    }
  }

  /**
   * 
   * Title: 查询战队成员<br>
   * Description: 查询战队成员<br>
   * CreateDate: 2017年8月9日 下午4:33:49<br>
   * 
   * @category 查询战队成员
   * @author felix.yl
   * @param sessionUser
   * @return
   * @throws Exception
   */
  public TrainingCampClanMemberApiParam findTrainingCampClanMembersInfo(SessionUser sessionUser,
      String start, String end)
          throws Exception {

    // 获取当前登录用户的userId
    String userId = sessionUser.getKeyId();

    // 战队积分对象(传给前端)
    TrainingCampClanMemberApiParam trainingCampClanMemberApiParam =
        new TrainingCampClanMemberApiParam();

    // 新建list,存储成员对象
    List<TrainingCampClanMemberParam> trainingCampClanMemberParamList =
        new ArrayList<TrainingCampClanMemberParam>();

    // 构建当前时间(不直接在参数里面new Date()的原因是防止缓存失效)
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
    String currentTime = df.format(new Date());
    Date date = df.parse(currentTime);

    // 根据用户的userId去查询训练营成员表,返回训练营id
    TrainingMember trainingMember = trainingMemberEntityDao
        .findTrainingMemberListByUserIdUsingMemcached(userId, date);
    if (trainingMember != null) {
      String trainingCampId = trainingMember.getTrainingCampId();// 训练营id

      // 查询该训练营的总人数(这一步还有一个目的是判断该训练营是否过期,在SQL里面加了生效结束时间判断,只要此处能查到结果就说明没过期)
      TrainingCampParam trainingCamp = trainingCampEntityDao.findTrainingCampNumUsingMemcached(
          trainingCampId,
          new Date());
      if (trainingCamp == null) {
        return null;
      } else {
        Integer trainingCampNum = trainingCamp.getTrainingCampNum();
        trainingCampClanMemberApiParam.setCountNum(trainingCampNum);// 训练营人数

        // 查询战队成员
        if (end.length() == 0) {
          end = trainingCampNum + "";
        }
        List<TrainingMember> trainingMemberList = trainingMemberEntityDao
            .findTrainingMemberByTrainingCampIdUsingMemcached(trainingCampId, Integer.parseInt(
                start), Integer
                    .parseInt(end));
        for (int k = 0; k < trainingMemberList.size(); k++) {

          String trainingMemberPic = trainingMemberList.get(k).getTrainingMemberPic();
          String trainingMemberEnglishName = trainingMemberList.get(k)
              .getTrainingMemberEnglishName();
          // 战队积分对象(保存战队成员的英文名称和头像)
          TrainingCampClanMemberParam trainingCampClanMemberParam =
              new TrainingCampClanMemberParam();
          trainingCampClanMemberParam.setTrainingMemberPic(trainingMemberPic);// 头像
          trainingCampClanMemberParam.setTrainingMemberEnglishName(trainingMemberEnglishName);// 英文名称

          trainingCampClanMemberParamList.add(trainingCampClanMemberParam);
        }
        trainingCampClanMemberApiParam.setTrainingCampClanMemberParamList(
            trainingCampClanMemberParamList);
        return trainingCampClanMemberApiParam;
      }
    } else {
      return null;
    }
  }

}