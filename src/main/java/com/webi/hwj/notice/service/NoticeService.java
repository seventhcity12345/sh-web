package com.webi.hwj.notice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.courseone2many.dao.CourseOneToManySchedulingDao;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.notice.constant.NoticeConstant;
import com.webi.hwj.notice.dao.NoticeDao;
import com.webi.hwj.notice.entity.Notice;
import com.webi.hwj.notice.param.NoticeParam;
import com.webi.hwj.util.CalendarUtil;

/**
 * @category notice控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class NoticeService {
  private static Logger logger = Logger.getLogger(NoticeService.class);
  @Resource
  NoticeDao noticeDao;
  @Resource
  CourseOneToManySchedulingDao courseOneToManySchedulingDao;

  /**
   * Title: 查找公告信息列表<br>
   * Description: 查找公告信息列表<br>
   * CreateDate: 2016年7月29日 上午9:40:35<br>
   * 
   * @category 查找公告信息列表
   * @author komi.zsy
   * @param sort
   * @param order
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  public Page findNoticePageEasyui(String cons, String sort, String order, Integer page,
      Integer rows) throws Exception {
    Page p = noticeDao.findNoticePageEasyui(cons, sort, order, page, rows);

    if (p.getDatas() != null && p.getDatas().size() != 0) {
      Date currentDate = new Date();
      for (NoticeParam np : (List<NoticeParam>) p.getDatas()) {
        // 当前时间大于等于开始时间且小于等于结束时间，为生效中的公告
        if (np.getNoticeStartTime().getTime() <= currentDate.getTime()
            && np.getNoticeEndTime().getTime() >= currentDate.getTime()) {
          np.setIsUsed(true);
        } else {
          np.setIsUsed(false);
        }
        // 如果类型是banner，则内容为"无"
        if (np.getNoticeType() == NoticeConstant.NOTICE_TYPE_BANNER) {
          np.setNoticeContent("banner:" + np.getNoticeContent());
        }
      }
    }

    return p;
  }

  /**
   * Title: 批量删除公告<br>
   * Description: 批量删除公告<br>
   * CreateDate: 2016年7月29日 上午11:51:51<br>
   * 
   * @category 批量删除公告
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  public int batchDeleteNotice(Map<String, Object> paramMap, String updateUserId) throws Exception {
    // 获得后台传递的keys
    String keys = paramMap.get("keys").toString();
    if (keys == null && "".equals(keys)) {
      logger.error("后台传递的参数有误！");
      throw new RuntimeException("后台传递的参数有误！");
    }

    String[] keyIds = keys.split(",");
    List<String> returnList = Arrays.asList(keyIds);

    return noticeDao.delete(returnList, updateUserId);
  }

  /**
   * Title: 查找当前生效中的最新x条公告信息<br>
   * Description: 查找当前生效中的最新x条公告信息<br>
   * CreateDate: 2016年8月1日 上午10:39:11<br>
   * 
   * @category 查找当前生效中的最新x条公告信息
   * @author komi.zsy
   * @param num
   *          查找num条数据
   * @param noticeStartTime
   *          开始生效时间
   * @param noticeEndTime
   *          结束生效时间
   * @return
   * @throws Exception
   */
  public List<Notice> findNoticeListByUsed() throws Exception {
    return noticeDao.findNoticeListByUsed();
  }

  /**
   * Title: 初始化缓存<br>
   * Description: 初始化缓存<br>
   * CreateDate: 2016年12月19日 下午5:21:13<br>
   * 
   * @category 初始化缓存
   * @author komi.zsy
   * @param type
   *          类型：0 公告，1 banner
   * @throws Exception
   */
  public void setMemcached(int type) throws Exception {
    if (type == NoticeConstant.NOTICE_TYPE_BANNER) {
      MemcachedUtil.setValue(ConfigConstant.BANNER_LIST, this.findBannerListByUsed());
    } else if (type == NoticeConstant.NOTICE_TYPE_NOTICE) {
      MemcachedUtil.setValue(ConfigConstant.NOTICE_LIST, this.findNoticeListByUsed());
    }
  }

  /**
   * Title: 查找当前还未过期的banner信息<br>
   * Description: 查找当前还未过期的banner信息<br>
   * CreateDate: 2016年12月19日 下午2:40:50<br>
   * 
   * @category 查找当前还未过期的banner信息
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<Notice> findBannerListByUsed() throws Exception {
    return noticeDao.findBannerListByUsed();
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
  public int insert(Notice paramObj) throws Exception {
    return noticeDao.insert(paramObj);
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
  public int update(Notice paramObj) throws Exception {
    return noticeDao.update(paramObj);
  }

  /**
   * Title: 查找当前生效中的最新x条公告信息<br>
   * Description: 查找当前生效中的最新x条公告信息<br>
   * CreateDate: 2016年8月1日 上午10:39:11<br>
   * 
   * @category 查找当前生效中的最新x条公告信息
   * @author komi.zsy
   * @param num
   *          查找num条数据
   * @param noticeStartTime
   *          开始生效时间
   * @param noticeEndTime
   *          结束生效时间
   * @return
   * @throws Exception
   */
  public List<NoticeParam> findNoticeListNew(Date currentTime) throws Exception {
    List<NoticeParam> returnNoticeList = new ArrayList<NoticeParam>();
    // 查询公告
    List<Notice> noticeList = (List<Notice>) MemcachedUtil.getValue(ConfigConstant.NOTICE_LIST);
    NoticeParam noticeTemp = null;
    if (noticeList != null && noticeList.size() != 0) {
      for (Notice notice : noticeList) {
        // 因为放入缓存的时候可能还没过期或生效，但是现在可能生效了。
        if (notice.getNoticeStartTime().getTime() <= currentTime.getTime()
            && notice.getNoticeEndTime().getTime() >= currentTime.getTime()) {
          noticeTemp = new NoticeParam();
          PropertyUtils.copyProperties(noticeTemp, notice);
          returnNoticeList.add(noticeTemp);
        }
      }
    }
    // 查询es课程
    List<CourseOne2ManyScheduling> todayEsList = courseOneToManySchedulingDao
        .findListByCourseTypeAndEndTime("course_type8", currentTime,
            CalendarUtil.getNextNDay(currentTime, 1));
    // 拼入返回值
    if (todayEsList != null && todayEsList.size() > 0) {
      for (CourseOne2ManyScheduling courseOne2ManyScheduling : todayEsList) {
        noticeTemp = new NoticeParam();
        noticeTemp.setNoticeTitle("即将直播");
        noticeTemp.setNoticeContent("【" + ((CourseType) MemcachedUtil.getValue("course_type8"))
            .getCourseTypeChineseName()
            + "】《"
            + courseOne2ManyScheduling.getCourseTitle()
            + "》"
            + "【今天】"
            + DateUtil.dateToStr(courseOne2ManyScheduling.getStartTime(), "HH:mm") + "~" + DateUtil
                .dateToStr(courseOne2ManyScheduling.getEndTime(), "HH:mm"));
        returnNoticeList.add(noticeTemp);
      }
    }

    return returnNoticeList;
  }
}