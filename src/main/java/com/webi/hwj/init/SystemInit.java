/** 
 * File: SystemInit.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.init<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年10月21日 下午4:44:35
 * @author yangmh
 */
package com.webi.hwj.init;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.admin.service.AdminConfigService;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.coursetype.service.CourseTypeService;
import com.webi.hwj.notice.constant.NoticeConstant;
import com.webi.hwj.notice.service.NoticeService;

/**
 * Title: 系统初始化<br>
 * Description: 系统初始化<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年10月21日 下午4:44:35
 * 
 * @author yangmh
 */
public class SystemInit implements ApplicationListener<ContextRefreshedEvent> {
  private static Logger logger = Logger.getLogger(SystemInit.class);

  @Resource
  private AdminConfigService adminConfigService;

  @Resource
  CourseTypeService courseTypeService;

  @Resource
  NoticeService noticeService;

  private String memcachedHost;
  private String memcachedPort;

  private String memcachedUserName;
  private String memcachedPortPassword;

  private String env;

  public void onApplicationEvent(ContextRefreshedEvent event) {
    // 防止初始化两次
    if (event.getApplicationContext().getParent() == null) {
      // root application context 没有parent，他就是老大.
      // 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。23
      logger.info("speakhi项目初始化");

      // 初始化缓存
      try {

        MemcachedUtil.env = env;
        MemcachedUtil.host = memcachedHost;
        MemcachedUtil.port = memcachedPort;
        MemcachedUtil.username = memcachedUserName;
        MemcachedUtil.password = memcachedPortPassword;

        // 初始化缓存加判断，容器启动时，如果发现码表中的数据与缓存中的数据不一致，则更新缓存数据。
        List<Map<String, Object>> adminConfigList = adminConfigService.findList(null,
            "config_name,config_value");
        for (Map<String, Object> configMap : adminConfigList) {
          String configName = configMap.get("config_name").toString();
          String memcacheValue = MemcachedUtil.getConfigValue(configName);
          String configValue = configMap.get("config_value") + "";
          if (!configValue.equals(memcacheValue)) {
            MemcachedUtil.setValue(configName, configValue);
          }
        }

        /**
         * modified by komi 2016年8月24日11:28:09
         * courseType从码表中移除，修改至从course_type表放入缓存
         */
        List<CourseType> courseTypes = courseTypeService.findList();
        if (courseTypes != null && courseTypes.size() != 0) {
          for (CourseType CourseType : courseTypes) {
            MemcachedUtil.setValue(CourseType.getCourseType(), CourseType);
          }
        } else {
          throw new Exception("没有有效的课程类型！");
        }

        /**
         * modified by komi 2016年12月19日17:15:27 增加banner缓存
         */
        noticeService.setMemcached(NoticeConstant.NOTICE_TYPE_BANNER);
        noticeService.setMemcached(NoticeConstant.NOTICE_TYPE_NOTICE);

      } catch (Exception e) {
        logger.error("初始化缓存失败!!!!!!");
        e.printStackTrace();
      }
    }
  }

  public String getMemcachedHost() {
    return memcachedHost;
  }

  public void setMemcachedHost(String memcachedHost) {
    this.memcachedHost = memcachedHost;
  }

  public String getMemcachedPort() {
    return memcachedPort;
  }

  public void setMemcachedPort(String memcachedPort) {
    this.memcachedPort = memcachedPort;
  }

  public String getMemcachedUserName() {
    return memcachedUserName;
  }

  public void setMemcachedUserName(String memcachedUserName) {
    this.memcachedUserName = memcachedUserName;
  }

  public String getMemcachedPortPassword() {
    return memcachedPortPassword;
  }

  public void setMemcachedPortPassword(String memcachedPortPassword) {
    this.memcachedPortPassword = memcachedPortPassword;
  }

  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }
}
