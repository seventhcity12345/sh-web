package com.webi.hwj.statistics.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.statistics.dao.StatisticsMarathonDao;
import com.webi.hwj.statistics.dao.StatisticsMarathonDayDao;
import com.webi.hwj.tellmemore.util.TellmemoreUtil;
import com.webi.hwj.util.TxtUtil;

/**
 * 
 * Title: 马拉松数据统计，每天的变化量统计<br>
 * Description: 马拉松数据统计，每天的变化量统计<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月7日 上午11:04:19
 * 
 * @author komi.zsy
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class StatisticsMarathonService {
  private static Logger logger = Logger.getLogger(StatisticsMarathonService.class);

  @Resource
  private StatisticsMarathonDao statisticsMarathonDao;

  @Resource
  private StatisticsMarathonDayDao statisticsMarathonDayDao;

  // TODO 此处现在是n+1查询，是否需要修改
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void statisticsMarathonChangeEveryday() throws Exception {
    logger.info("开始读取马拉松txt文件----->进行每日统计操作");

    String loadAddress = "d:\\tmm_marathon_data.txt";
    // 如果是生产环境
    if ("pro".equals(MemcachedUtil.getConfigValue("env"))) {
      loadAddress = File.separator + "usr" + File.separator + "tmm_marathon_data.txt";
    } else if ("test".equals(MemcachedUtil.getConfigValue("env"))) {
      loadAddress = File.separator + "usr" + File.separator + "tmm_marathon_data.txt";
    }

    List<List<String>> userList = TxtUtil.loadTxtFile(loadAddress);

    logger.info("读取马拉松txt文件完毕----->开始进行数据库每小时数据相加并插入操作");

    for (List<String> user : userList) {
      try {

        logger.debug("开始计算学员数据库信息-------->" + user);

        // rsa账号
        String phone = user.get(1).trim();

        // 拉取学生每小时数据的条件
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("rsa_account", phone);
        // 计算当前时间
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.HOUR_OF_DAY, -1);
        paramMap.put("create_day", sdf.format(cal.getTime()));

        logger.debug("开始拉取学员数据库信息-------->" + paramMap);

        List<Map<String, Object>> changeDateList = statisticsMarathonDao
            .findMarathonByRsaAccountAndDay(paramMap);

        int percentdoneSum = 0;
        int percentcorrectSum = 0;
        List<Integer> timeSum = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
          timeSum.add(0);
        }

        if (changeDateList != null) {
          for (Map<String, Object> changeDate : changeDateList) {
            percentdoneSum += Integer.parseInt(changeDate.get("change_tmm_percent").toString());
            percentcorrectSum += Integer.parseInt(changeDate.get("change_tmm_correct").toString());
            List<Integer> timeTemp = TellmemoreUtil
                .getTimeInInt(changeDate.get("change_tmm_workingtime").toString());
            for (int i = 0; i < 3; i++) {
              timeSum.set(i, timeSum.get(i) + timeTemp.get(i));
            }
          }
        }

        Map<String, Object> paramMapSum = new HashMap<String, Object>();
        // 教务账号
        paramMapSum.put("lc_name", user.get(0).trim());
        paramMapSum.put("rsa_account", phone);
        // 学员名称,不为空时存入，为空时默认存入空字符
        if (user.size() > 2) {
          paramMapSum.put("user_name", user.get(2).trim());
        } else {
          paramMapSum.put("user_name", "");
        }

        paramMapSum.put("change_tmm_percent", percentdoneSum);
        paramMapSum.put("change_tmm_correct", percentcorrectSum);
        paramMapSum.put("change_tmm_workingtime", TellmemoreUtil.getTimeInString(timeSum));

        paramMapSum.put("create_date", paramMap.get("create_day"));

        logger.debug("开始插入学员数据库信息-------->" + paramMapSum);

        // 查询当天是否已插入过数据（插入过的话则执行更新）
        if (statisticsMarathonDayDao.updateMarathonByRsaAccountAndDay(paramMapSum) == 0) {
          statisticsMarathonDayDao.insert(paramMapSum);
        }
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("系统异常，error:" + e.toString());
      }
    }

    logger.info("数据库每小时数据相加并插入操作完毕-------->");

  }

}