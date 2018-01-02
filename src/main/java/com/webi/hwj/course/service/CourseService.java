package com.webi.hwj.course.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.course.util.CourseUtil;
import com.webi.hwj.courseone2one.param.CourseOne2OneParam;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.tellmemore.service.TellmemoreService;

/**
 * 课程入口实现
 * 
 * Title: CourseService<br>
 * Description: CourseService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年9月23日 下午1:02:24
 * 
 * @author Woody
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CourseService {
  private Logger logger = Logger.getLogger(CourseService.class);
  @Resource
  private TellmemoreService tellmemoreService;
  @Resource
  SubscribeCourseEntityDao subscribeCourseEntityDao;

  /**
   * Title: 组装1v1类型课程列表<br>
   * Description: 组装1v1类型课程列表<br>
   * CreateDate: 2016年9月7日 上午10:06:31<br>
   * 
   * @category 组装1v1类型课程列表
   * @author komi.zsy
   * @param userPhone
   *          用户手机号（用于更新rsa数据）
   * @param userId
   *          用户id
   * @param currentLevel
   *          当前级别
   * @return
   * @throws Exception
   */
  public List<CourseOne2OneParam> findCourseOne2OneList(SessionUser sessionUser,
      List<CourseOne2OneParam> one2OneCourseList) throws Exception {
    String userId = sessionUser.getKeyId();
    String currentLevel = sessionUser.getCurrentLevel();

    logger.info(">!-------------查询一对一参数为：currentLevel=" + currentLevel + "    userId=" + userId
        + "------------!<");

    if (one2OneCourseList != null && one2OneCourseList.size() > 0) {
      // 从缓存中读取课程类型相关信息
      CourseType courseType = (CourseType) MemcachedUtil
          .getValue(one2OneCourseList.get(0).getCourseType());

      // 查询当前用户的每一节课程的最新一条预约记录
      List<SubscribeCourse> lastSubscribeCourseList = subscribeCourseEntityDao
          .findLastSubscribeCourseByUserIdAndCourseId(userId, courseType.getCourseType());

      // tmm课件的完成情况，确定是否符合预约的前提
      // 获取我的课件的全部进度 key是课件ID value当前进度
      Map<String, Object> rates = tellmemoreService.findUserPercentMap(userId, currentLevel);

      // 统计各个课程的学习情况
      CourseUtil.statisticalStatus(lastSubscribeCourseList, one2OneCourseList, courseType, rates);
    }

    return one2OneCourseList;
  }
}