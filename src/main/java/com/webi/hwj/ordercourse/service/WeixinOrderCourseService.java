package com.webi.hwj.ordercourse.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.webi.hwj.ordercourse.dao.WeixinOrderCourseDao;

/**
 * 
 * Title: 微信端课程相关service<br>
 * Description: WeixinOrderCourseService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月3日 上午9:58:39
 * 
 * @author athrun.cw
 */
@Service
public class WeixinOrderCourseService {
  private static Logger logger = Logger.getLogger(WeixinOrderCourseService.class);

  @Resource
  WeixinOrderCourseDao weixinOrderCourseDao;

}
