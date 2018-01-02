package com.webi.hwj.statistics.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.statistics.dao.StatisticsTellmemoreDayDao;
import com.webi.hwj.statistics.entity.StatisticsTellmemoreDay;
import com.webi.hwj.statistics.param.CoursewareLearningTimeApiParam;
import com.webi.hwj.statistics.param.CoursewareLearningTimeParam;
import com.webi.hwj.tellmemore.dao.TellmemorePercentDao;
import com.webi.hwj.tellmemore.util.TellmemoreUtil;

/**
 * Title: Tellmemore每日学员三维数据统计，变化量统计<br>
 * Description: Tellmemore每日学员三维数据统计，变化量统计<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月15日 下午1:14:48
 * 
 * @author komi.zsy
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class StatisticsTellmemoreDayService {
  private static Logger logger = Logger.getLogger(StatisticsTellmemoreDayService.class);
  @Resource
  StatisticsTellmemoreDayDao statisticsTellmemoreDayDao;

  @Resource
  TellmemorePercentDao tellmemorePercentDao;

  /**
   * Title: 统计每日tellmemore学员三维数据，并获得与前一日的差额<br>
   * Description: 统计每日tellmemore学员三维数据，并获得与前一日的差额<br>
   * CreateDate: 2016年4月19日 上午9:57:53<br>
   * 
   * @category 统计每日tellmemore学员三维数据，并获得与前一日的差额
   * @author komi.zsy
   * @param paramDate
   *          统计的日期
   * @throws Exception
   */
  // @Transactional(propagation = Propagation.REQUIRED, isolation =
  // Isolation.SERIALIZABLE)
  public void statisticsTellmemoreChangeEveryday(String paramDate) throws Exception {
    logger.info("开始speakhi学员rsa数据每日统计操作-------->");

    logger.debug("开始读取tellmemore学员信息----->进行每日统计操作");
    // key为user_id，方便后面根据user_id查询三维数据
    Map<String, List<Integer>> tmmUserTotalMap = new HashMap<String, List<Integer>>();
    // 查找tmm用户原始数据
    List<Map<String, Object>> tmmUserInfo = tellmemorePercentDao.findAllTmmUserInfoList();

    if (tmmUserInfo != null && tmmUserInfo.size() > 0) {
      // 因为第一条一定是一个新的user，所有单独建map
      // 新建一个list，分别存放：总时间（时，分，秒）,总进度，总准确率
      // tmm_workingtime在数据库默认值为"",所有需要判断一下是否有值
      if (StringUtils.isEmpty(tmmUserInfo.get(0).get("tmm_workingtime"))) {
        tmmUserInfo.get(0).put("tmm_workingtime", "0:00:00");
      }
      List<Integer> tmmTotalListFirst = TellmemoreUtil
          .getTimeInInt(tmmUserInfo.get(0).get("tmm_workingtime").toString());
      tmmTotalListFirst.add(Integer.parseInt(tmmUserInfo.get(0).get("tmm_percent").toString()));
      tmmTotalListFirst.add(Integer.parseInt(tmmUserInfo.get(0).get("tmm_correct").toString()));
      tmmUserTotalMap.put(tmmUserInfo.get(0).get("user_id").toString(), tmmTotalListFirst);

      for (int i = 1; i < tmmUserInfo.size(); i++) {

        Map<String, Object> tempMap = tmmUserInfo.get(i);

        // tmm_workingtime在数据库默认值为"",所有需要判断一下是否有值
        if (StringUtils.isEmpty(tempMap.get("tmm_workingtime"))) {
          tempMap.put("tmm_workingtime", "0:00:00");
        }

        // 将tmm_workingtime字符串转换成时，分，秒
        List<Integer> tmmTotalList = TellmemoreUtil
            .getTimeInInt(tempMap.get("tmm_workingtime").toString());

        // 每条都和前一条的userid对比，一样则相加，不一样则往map里新插入一个user
        if (tempMap.get("user_id").equals(tmmUserInfo.get(i - 1).get("user_id"))) {
          List<Integer> tempList = tmmUserTotalMap.get(tempMap.get("user_id"));
          tempList.set(0, tempList.get(0) + tmmTotalList.get(0));
          tempList.set(1, tempList.get(1) + tmmTotalList.get(1));
          tempList.set(2, tempList.get(2) + tmmTotalList.get(2));
          tempList.set(3,
              tempList.get(3) + Integer.parseInt(tempMap.get("tmm_percent").toString()));
          tempList.set(4,
              tempList.get(4) + Integer.parseInt(tempMap.get("tmm_correct").toString()));
        } else {
          tmmTotalList.add(Integer.parseInt(tmmUserInfo.get(i).get("tmm_percent").toString()));
          tmmTotalList.add(Integer.parseInt(tmmUserInfo.get(i).get("tmm_correct").toString()));
          tmmUserTotalMap.put(tempMap.get("user_id").toString(), tmmTotalList);
        }
      }
    }

    // 查出所有tmmday中的前一天的数据
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate;
    if (paramDate == null) {
      currentDate = new Date();
    } else {
      currentDate = format.parse(paramDate);
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
    String exTime = format.format(calendar.getTime());
    // System.out.println("exTime:"+exTime);

    logger.info("开始读取tellmemoreDay学员信息----->进行计算差额操作");

    StatisticsTellmemoreDay statisticsTellmemoreDay = new StatisticsTellmemoreDay();
    statisticsTellmemoreDay.setCreateDate(format.parse(exTime));
    List<StatisticsTellmemoreDay> tmmExUserInfoList = statisticsTellmemoreDayDao
        .findInfoByCreatDay(statisticsTellmemoreDay);
    Map<String, StatisticsTellmemoreDay> tmmExUserInfoMap =
        new HashMap<String, StatisticsTellmemoreDay>();

    for (StatisticsTellmemoreDay tmmExUserInfo : tmmExUserInfoList) {
      tmmExUserInfoMap.put(tmmExUserInfo.getUserId(), tmmExUserInfo);
    }

    // sqlList，为后面批量插入保存数据
    List<StatisticsTellmemoreDay> statisticsTellmemoreDaySqlList =
        new ArrayList<StatisticsTellmemoreDay>();

    // 遍历，因为有可能tmm表中有新userid，但是tmmday中没有，而且tmmday中学员可能有合同过期了的，所有还是要用tmm的map里的userid为标准
    for (String key : tmmUserTotalMap.keySet()) {
      List<Integer> tempList = tmmUserTotalMap.get(key);
      StatisticsTellmemoreDay statisticsTellmemoreDaySql = new StatisticsTellmemoreDay();
      if (tmmExUserInfoMap.get(key) == null) {
        // statisticsTellmemoreDaySql.setChangeTmmWorkingtime("0:00:00");
        // statisticsTellmemoreDaySql.setChangeTmmPercent(0);
        // statisticsTellmemoreDaySql.setChangeTmmCorrect(0);

        statisticsTellmemoreDaySql
            .setChangeTmmWorkingtime(TellmemoreUtil.getTimeInString(tempList));
        statisticsTellmemoreDaySql.setChangeTmmPercent(tempList.get(3));
        statisticsTellmemoreDaySql.setChangeTmmCorrect(tempList.get(4));
      } else {
        List<Integer> exWorkTime = TellmemoreUtil
            .getTimeInInt(tmmExUserInfoMap.get(key).getTotalTmmWorkingtime());
        exWorkTime.set(0, tempList.get(0) - exWorkTime.get(0));
        exWorkTime.set(1, tempList.get(1) - exWorkTime.get(1));
        exWorkTime.set(2, tempList.get(2) - exWorkTime.get(2));

        statisticsTellmemoreDaySql
            .setChangeTmmWorkingtime(TellmemoreUtil.getTimeInString(exWorkTime));
        statisticsTellmemoreDaySql
            .setChangeTmmPercent(tempList.get(3) - tmmExUserInfoMap.get(key).getTotalTmmPercent());
        statisticsTellmemoreDaySql
            .setChangeTmmCorrect(tempList.get(4) - tmmExUserInfoMap.get(key).getTotalTmmCorrect());
      }

      statisticsTellmemoreDaySql.setCreateDate(currentDate);
      statisticsTellmemoreDaySql.setUserId(key);
      statisticsTellmemoreDaySql.setTotalTmmWorkingtime(TellmemoreUtil.getTimeInString(tempList));
      statisticsTellmemoreDaySql.setTotalTmmPercent(tempList.get(3));
      statisticsTellmemoreDaySql.setTotalTmmCorrect(tempList.get(4));

      statisticsTellmemoreDaySqlList.add(statisticsTellmemoreDaySql);
    }

    logger.debug("批量插入数据库开始-------->" + statisticsTellmemoreDaySqlList.get(0));
    statisticsTellmemoreDayDao.batchInsert(statisticsTellmemoreDaySqlList);
    logger.debug("批量插入数据库完毕-------->");

    logger.info("speakhi学员rsa数据每日统计操作完毕-------->");
  }

  /**
   * Title: 新增数据<br>
   * Description: insert<br>
   * CreateDate: 2016年4月11日 上午8:57:05<br>
   * 
   * @category 新增数据
   * @author yangmh
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int insert(StatisticsTellmemoreDay paramObj) throws Exception {
    return statisticsTellmemoreDayDao.insert(paramObj);
  }

  /**
   * Title: 批量新增数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:04:36<br>
   * 
   * @category 批量新增数据
   * @author yangmh
   * @param paramObj
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchInsert(List<StatisticsTellmemoreDay> paramObjList) throws Exception {
    return statisticsTellmemoreDayDao.batchInsert(paramObjList);
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
  public int delete(String keyIds) throws Exception {
    return statisticsTellmemoreDayDao.delete(keyIds);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:23<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(List<String> keyIds) throws Exception {
    return statisticsTellmemoreDayDao.delete(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(List<String> keyIds) throws Exception {
    return statisticsTellmemoreDayDao.deleteForReal(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(String keyIds) throws Exception {
    return statisticsTellmemoreDayDao.deleteForReal(keyIds);
  }

  /**
   * Title: 修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:40:18<br>
   * 
   * @category 修改数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return 执行成功数
   * @throws Exception
   */
  public int update(StatisticsTellmemoreDay paramObj) throws Exception {
    return statisticsTellmemoreDayDao.update(paramObj);
  }

  /**
   * Title: 批量修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:46:26<br>
   * 
   * @category 批量修改数据
   * @author yangmh
   * @param paramObjList
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchUpdate(List<StatisticsTellmemoreDay> paramObjList) throws Exception {
    return statisticsTellmemoreDayDao.batchUpdate(paramObjList);
  }

  /**
   * Title: 查询数量数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午1:09:45<br>
   * 
   * @category 查询数量数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return
   * @throws Exception
   */
  public int findCount(StatisticsTellmemoreDay paramObj) throws Exception {
    return statisticsTellmemoreDayDao.findCount(paramObj);
  }

  /**
   * 
   * Title: 口语训练营课件学习时长对比<br>
   * Description: 口语训练营课件学习时长对比<br>
   * CreateDate: 2017年8月9日 下午4:33:49<br>
   * 
   * @category 口语训练营课件学习时长对比
   * @author felix.yl
   * @param sessionUser
   * @return
   * @throws Exception
   */
  public CoursewareLearningTimeApiParam findCoursewareLearningTimeInfo(SessionUser sessionUser)
      throws Exception {

    // 获取当前登录用户的userId
    String userId = sessionUser.getKeyId();

    // 今天日期
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
    Date endDate = df.parse(df.format(new Date()));
    // Date endDate = df.parse("2017-05-09");

    // 7天以前的日期
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(endDate);
    calendar.add(Calendar.DATE, -7);
    Date startDate = df.parse(df.format(calendar.getTime()));
    // Date startDate = df.parse("2017-05-02");

    // 查询所有人最近7天平均学习时长
    List<CoursewareLearningTimeParam> avgCoursewareLearningTimeList = statisticsTellmemoreDayDao
        .findAvgCoursewareLearningTimeInfo(startDate, endDate);

    // 查询当前登录学员自己最近7天平均学习时长
    List<CoursewareLearningTimeParam> myCoursewareLearningTimeList = statisticsTellmemoreDayDao
        .findMyCoursewareLearningTimeInfo(userId, startDate, endDate);

    // 构建对象
    CoursewareLearningTimeApiParam coursewareLearningTimeApiParam =
        new CoursewareLearningTimeApiParam();
    coursewareLearningTimeApiParam.setCoursewareLearningTimeAvgList(avgCoursewareLearningTimeList);
    coursewareLearningTimeApiParam.setCoursewareLearningTimeMyList(myCoursewareLearningTimeList);

    return coursewareLearningTimeApiParam;
  }
}