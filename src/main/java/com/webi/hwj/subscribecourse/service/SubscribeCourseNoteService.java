package com.webi.hwj.subscribecourse.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseNoteDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourseNote;

/**
 * @category subscribeCourseNote控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
public class SubscribeCourseNoteService {
  private static Logger logger = Logger.getLogger(SubscribeCourseNoteService.class);
  @Resource
  SubscribeCourseNoteDao subscribeCourseNoteDao;

  /**
   * Title: 新增或更新记录<br>
   * Description: 新增或更新记录<br>
   * CreateDate: 2016年9月20日 下午4:51:35<br>
   * 
   * @category 新增或更新记录
   * @author seven.gz
   * @param subscribeCourseNote
   *          记录实体类
   * @return JsonMessage
   * @throws Exception
   *           通用异常
   */
  public JsonMessage addOrModifyNote(SubscribeCourseNote subscribeCourseNote) throws Exception {
    JsonMessage message = new JsonMessage();
    // 根据keyid查询是否有此预约的记录
    SubscribeCourseNote findNote = subscribeCourseNoteDao
        .findOneByKeyId(subscribeCourseNote.getKeyId());

    int resultCount = 0;
    // 如果没有则插入
    if (findNote == null) {
      subscribeCourseNote.setCreateUserId(subscribeCourseNote.getUpdateUserId());
      resultCount = subscribeCourseNoteDao.insert(subscribeCourseNote);
      // 如果有则更新
    } else {
      resultCount = subscribeCourseNoteDao.update(subscribeCourseNote);
    }

    if (resultCount == 0) {
      message.setSuccess(false);
      message.setMsg("操作失败");
    } else {
      message.setSuccess(true);
    }

    return message;
  }
}