package com.webi.hwj.huanxun.service;

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

import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SmsUtil;
import com.webi.hwj.constant.WebexConstant;
import com.webi.hwj.huanxun.util.HuanXunUtil;
import com.webi.hwj.teacher.dao.TeacherDao;
import com.webi.hwj.teacher.dao.TeacherTimeDao;
import com.webi.hwj.teacher.service.TeacherTimeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class HuanXunApiService {
  private static Logger logger = Logger.getLogger(HuanXunApiService.class);
  @Resource
  TeacherDao teacherDao;

  @Resource
  TeacherTimeDao teacherTimeDao;

  @Resource
  TeacherTimeService teacherTimeService;

  /**
   * Title: 批量插入老师数据<br>
   * Description: batchInsertTeacherData<br>
   * CreateDate: 2015年12月21日 上午7:36:58<br>
   * 
   * @category batchInsertTeacherData
   * @author yangmh
   * @param json
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = Exception.class)
  public Map<String, String> pullTeacherData(String json) throws Exception {
    Map<String, String> returnMap = new HashMap<String, String>();

    // 解析json串

    JSONObject ja = JSONObject.fromObject(json);
    JSONArray data = ja.getJSONArray("data");
    for (int i = 0; i < data.size(); i++) {
      JSONObject teacherData = data.getJSONObject(i);
      Map<String, Object> teacherMap = new HashMap<String, Object>();
      String keyId = teacherData.getString("key_id");
      teacherMap.put("key_id", keyId);
      teacherMap.put("account", teacherData.getString("teacher_name"));
      // 默认密码111111
      teacherMap.put("pwd",
          "b0412597dcea813655574dc54a5b74967cf85317f0332a2591be7953a016f8de56200eb37d5ba593b1e4aa27cea5ca27100f94dccd5b04bae5cadd4454dba67d");
      teacherMap.put("teacher_name", teacherData.getString("teacher_name"));
      teacherMap.put("teacher_desc", teacherData.getString("teacher_desc"));
      teacherMap.put("teacher_photo", teacherData.getString("teacher_photo"));
      // modify by seven 2016年9月8日10:44:44 与环迅统一course_type
      teacherMap.put("teacher_course_type", teacherData.getString("teacher_course_type"));

      teacherMap.put("third_from", "huanxun");
      teacherMap.put("teacher_job_type", "3");

      Map<String, Object> tempTeacherMap = teacherDao.findOneByKeyId(keyId, "key_id");

      // 根据账号查询
      Map<String, Object> queryMap = new HashMap<String, Object>();
      queryMap.put("account", teacherData.getString("teacher_name"));
      Map<String, Object> accountTeacher = teacherDao.findOne(queryMap, "key_id");
      // 说明账号存在 在这个账号后加4位随机数
      if (accountTeacher != null) {
        teacherMap.put("account",
            teacherData.getString("teacher_name") + (int) ((Math.random() * 9 + 1) * 1000));
      }

      if (tempTeacherMap != null) {
        logger.info("更新数据------>" + keyId);
        teacherDao.update(teacherMap);
      } else {
        logger.info("插入数据------>" + keyId);
        teacherDao.insert(teacherMap);
      }

    }

    returnMap.put("code", "200");
    returnMap.put("msg", "");

    return returnMap;

  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = Exception.class)
  public Map<String, String> pullTeacherTimeData(String json) throws Exception {
    JSONObject ja = JSONObject.fromObject(json);
    String action = ja.getString("action");
    JSONArray data = ja.getJSONArray("data");

    Map<String, String> returnMap = new HashMap<String, String>();

    if (!"del".equals(action) && !"add".equals(action)) {
      returnMap.put("code", "304");
      returnMap.put("msg", "action不合法,action=" + action);
      return returnMap;
    }

    logger.info("action------>" + action);

    for (int i = 0; i < data.size(); i++) {
      JSONObject teacherData = data.getJSONObject(i);

      String teacherId = teacherData.getString("teacher_id");
      String startTime = teacherData.getString("start_time");
      String endTime = teacherData.getString("end_time");

      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("teacher_id", teacherId);
      paramMap.put("start_time", startTime);
      paramMap.put("end_time", endTime);

      Map<String, Object> teacherTimeMap = teacherTimeDao.findOne(paramMap, "key_id,is_subscribe");

      // 新增操作
      if ("add".equals(action)) {
        // 判断当前老师的时间是否重复了？如果重复了，则给环迅返回失败，否则入库。
        if (teacherTimeMap != null) {
          // throw new
          // Exception("老师时间数据已存在,teacher_id="+teacherId+",start_time="+startTime+",end_time="+endTime);
          logger.error(
              "老师时间数据已存在,teacher_id=" + teacherId + ",start_time=" + startTime + ",end_time="
                  + endTime);
          // modify by seven 2017年1月6日09:54:37 依照环迅要求忽略308错误
          // throw new Exception("308," + teacherId + "," + startTime + "," +
          // endTime);
        } else {
          // modify by seven 环迅在节假日不能排课
          // banDateString 格式 开始禁用时间,结束禁用时间 时间格式yyyy-MM-dd
          String banDateString = MemcachedUtil.getConfigValue("huanxun_ban_date");
          // 数据库里没有设置则跳过
          if (!StringUtils.isEmpty(banDateString)) {
            String[] banDateStringArray = banDateString.split(",");
            Date startBanDate = DateUtil.strToDateYYYYMMDDHHMMSS(banDateStringArray[0]);
            Date endBanDate = DateUtil.strToDateYYYYMMDDHHMMSS(banDateStringArray[1]);
            // 如果时间有交集 则跳过
            if (HuanXunUtil.timeHaveIntersection(startBanDate, endBanDate,
                DateUtil.strToDateYYYYMMDDHHMMSS(startTime),
                DateUtil.strToDateYYYYMMDDHHMMSS(endTime))) {
              continue;
            }
          }

          Map<String, Object> teacherMap = teacherDao.findOneByKeyId(teacherId,
              "teacher_course_type,teacher_name");
          if (teacherMap == null) {
            logger.error(
                "老师不存在,teacher_id=" + teacherId + ",start_time=" + startTime + ",end_time="
                    + endTime);
            throw new Exception("302," + teacherId + "," + startTime + "," + endTime);
          }

          // TODO 目前是只有1v1老师的课
          if (teacherMap.get("teacher_course_type").toString().indexOf("course_type1") != -1) {
            // 分配微立方房间
            List<String> roomList = teacherTimeService.findWebexAvailableRoomList(
                WebexConstant.WEBEX_ROOM_TYPE_ONE2ONE, DateUtil.strToDateYYYYMMDDHHMMSS(startTime),
                DateUtil.strToDateYYYYMMDDHHMMSS(endTime));
            // 只有在有房间的时候才进行排课
            if (roomList != null && roomList.size() > 0) {
              paramMap.put("webex_room_host_id", roomList.get(0));
              paramMap.put("teacher_name", teacherMap.get("teacher_name"));
              teacherTimeDao.insert(paramMap);

              // 异步调用生产者,生产webex会议
              // 延迟1分钟消费
              String createWebexMeetingBodyStr = WebexConstant.WEBEX_CONSUMER_TYPE_CREATE_MEETING
                  + ","
                  + paramMap.get("key_id") + "," + teacherMap.get("teacher_name") + " "
                  + DateUtil.strToDateYYYYMMDDHHMMSS(startTime) + " "
                  + DateUtil.strToDateYYYYMMDDHHMMSS(endTime) + " ";
              OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
                  MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_webex",
                  createWebexMeetingBodyStr, 60 * 1000);
            } else {
              logger.error("环讯推送排课数据但是我们没有房间对应,teacher_id=" + teacherId + ",start_time=" + startTime
                  + ",end_time=" + endTime);
              // 如果环讯排课的时候房间不够用，则短信报警!!!
              SmsUtil.sendAlarmSms(
                  "环讯推送排课数据但是我们没有房间对应,teacher_id=" + teacherId + ",start_time=" + startTime
                      + ",end_time=" + endTime);
            }
          }
        }
      }

      // 删除操作
      if ("del".equals(action)) {
        if (teacherTimeMap == null) {
          // 已经被删掉了，就继续往下走吧
          continue;
        }

        // 如果当前老师时间已经被学生预约走了
        if ((boolean) teacherTimeMap.get("is_subscribe")) {
          logger.error(
              "当前老师时间已经被韦博学员预约走了，环迅需要check问题！,teacher_id=" + teacherId + ",start_time=" + startTime
                  + ",end_time=" + endTime);
          throw new Exception("309," + teacherId + "," + startTime + "," + endTime);
        }

        teacherTimeDao.delete(teacherTimeMap.get("key_id").toString());
      }

    }
    returnMap.put("code", "200");
    returnMap.put("msg", "");
    return returnMap;
  }
}
