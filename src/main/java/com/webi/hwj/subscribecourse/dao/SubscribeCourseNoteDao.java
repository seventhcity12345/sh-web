package com.webi.hwj.subscribecourse.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourseNote;

@Repository
public class SubscribeCourseNoteDao extends BaseEntityDao<SubscribeCourseNote> {
  private static Logger logger = Logger.getLogger(SubscribeCourseNoteDao.class);

  /**
   * Title: 根据keyId查询<br>
   * Description: findOneByKeyId<br>
   * CreateDate: 2016年9月20日 下午4:36:49<br>
   * 
   * @category findOneByKeyId
   * @author seven.gz
   * @param keyId
   *          主键
   * @return SubscribeCourseNote
   * @throws Exception
   *           通用异常
   */
  public SubscribeCourseNote findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "key_id");
  }
}