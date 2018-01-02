package com.webi.hwj.course.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.webi.hwj.course.dao.WeixinCourseDao;
import com.webi.hwj.course.util.WeixinCourseUtil;

/**
 * 
 * Title: 微信端课程相关service<br>
 * Description: WeixinCourseService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年5月3日 上午9:58:39
 * 
 * @author athrun.cw
 */
@Service
public class WeixinCourseService {
  private static Logger logger = Logger.getLogger(WeixinCourseService.class);

  @Resource
  WeixinCourseDao weixinCourseDao;

  /**
   * Title: 加载课程信息列表接口<br>
   * Description: findCourseTypeList<br>
   * CreateDate: 2016年5月3日 上午10:02:11<br>
   * 
   * @category 加载课程信息列表接口
   * @author athrun.cw
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findCourseTypeList(Map<String, Object> paramMap)
      throws Exception {
    List<Map<String, Object>> orderCourseOptionMapList = weixinCourseDao
        .findOrderCourseOptionByOrderId(paramMap);
    return WeixinCourseUtil.formatCourseType(orderCourseOptionMapList);
  }

}
