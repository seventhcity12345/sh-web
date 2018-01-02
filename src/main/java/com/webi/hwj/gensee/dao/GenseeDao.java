/** 
 * File: GenseeDao.java<br> 
 * Project: SpeakHi<br> 
 * Package: com.webi.hwj.gensee.dao<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2016年7月7日 下午2:34:12
 * @author yangmh
 */
package com.webi.hwj.gensee.dao;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.gensee.entity.Gensee;

/**
 * Title: GenseeDao<br>
 * Description: GenseeDao<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月7日 下午2:34:12
 * 
 * @author yangmh
 */
@Repository
public class GenseeDao extends BaseEntityDao<Gensee> {
  public Gensee findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "student_join_url,teacher_join_url,room_id");
  }
}
