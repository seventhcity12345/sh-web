package com.webi.hwj.notice.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.notice.entity.Notice;
import com.webi.hwj.notice.param.NoticeParam;

@Repository
public class NoticeDao extends BaseEntityDao<Notice> {

  /**
   * 查找全部公告信息
   * 
   * @author komi.zsy
   */
  public static final String FIND_NOTICE_LIST =
      "SELECT key_id,notice_title,notice_content,notice_type,"
          + "admin_user_name,notice_start_time,notice_end_time,create_date"
          + " FROM `t_notice`"
          + " WHERE is_used <> 0";

  /**
   * 查找当前生效中的最新x条公告信息
   * 
   * @author komi.zsy
   */
  public static final String FIND_NOTICE_LIST_BY_USED =
      "SELECT key_id,notice_title,notice_content,admin_user_name,"
          + "notice_start_time,notice_end_time,create_date"
          + " FROM `t_notice`"
          + " WHERE notice_end_time > :noticeEndTime"
          + " AND is_used <> 0 AND notice_type = 0 "
          + " ORDER BY notice_start_time DESC ";

  /**
   * 查找当前生效中的banner信息
   * 
   * @author komi.zsy
   */
  public static final String FIND_BANNER_LIST_BY_USED =
      "SELECT key_id,notice_title,notice_content,admin_user_name,"
          + "notice_start_time,notice_end_time,create_date"
          + " FROM `t_notice`"
          + " WHERE notice_end_time > :noticeEndTime"
          + " AND is_used <> 0 AND notice_type = 1 "
          + " ORDER BY notice_start_time DESC ";

  /**
   * Title: 查找公告信息列表<br>
   * Description: 查找公告信息列表<br>
   * CreateDate: 2016年7月29日 上午9:39:18<br>
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
    NoticeParam noticeParam = new NoticeParam();
    noticeParam.setCons(cons);
    return super.findPageEasyui(FIND_NOTICE_LIST, noticeParam, sort, order, page, rows);
  }

  /**
   * Title: 查找当前生效中的最新x条公告信息<br>
   * Description: 查找当前生效中的最新x条公告信息<br>
   * CreateDate: 2016年8月1日 上午10:37:42<br>
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
    Notice notice = new Notice();
    // modify by seven 2017年7月20日13:46:10 不再只查询三条了
    notice.setNoticeEndTime(new Date());
    return super.findList(FIND_NOTICE_LIST_BY_USED, notice);
  }

  /**
   * 
   * Title: 查找当前还未过期的banner信息<br>
   * Description: 查找当前还未过期的banner信息<br>
   * CreateDate: 2016年12月19日 下午2:37:45<br>
   * 
   * @category 查找当前还未过期的banner信息
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<Notice> findBannerListByUsed() throws Exception {
    Notice noticeParam = new Notice();
    noticeParam.setNoticeEndTime(new Date());
    return super.findList(FIND_BANNER_LIST_BY_USED, noticeParam);
  }
}