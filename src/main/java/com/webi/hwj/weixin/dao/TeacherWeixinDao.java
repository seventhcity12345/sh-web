package com.webi.hwj.weixin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.weixin.entity.TeacherWeixin;

@Repository
public class TeacherWeixinDao extends BaseEntityDao<TeacherWeixin> {
  private static Logger logger = Logger.getLogger(TeacherWeixinDao.class);

  /**
   * Title: 通过openid查找数据<br>
   * Description: 通过openid查找数据<br>
   * CreateDate: 2017年6月15日 下午4:42:55<br>
   * 
   * @category 通过openid查找数据
   * @author komi.zsy
   * @param openId
   *          微信openid
   * @return
   * @throws Exception
   */
  public List<TeacherWeixin> findListByOpenId(String openId) throws Exception {
    TeacherWeixin teacherWeixin = new TeacherWeixin();
    teacherWeixin.setOpenId(openId);

    List<TeacherWeixin> teacherWeixinList = super.findList(teacherWeixin,
        "key_id,teacher_id,open_id");
    return teacherWeixinList;
  }

  /**
   * Title: 查找所有teacherweixin数据.<br>
   * Description: <br>
   * CreateDate: 2016年10月14日 下午2:13:00<br>
   * 
   * @category 查找所有teacherweixin数据.
   * @author yangmh
   */
  public List<TeacherWeixin> findListAll() throws Exception {
    return super.findList(new TeacherWeixin(), "open_id");
  }

  /**
   * Title: 通过teacher_id查找teacherweixin数据.<br>
   * Description: find<br>
   * CreateDate: 2016年10月14日 下午2:13:00<br>
   * 
   * @category 通过openid查找teacherweixin数据.
   * @author yangmh
   */
  public List<TeacherWeixin> findListByTeacherId(String teacherId) throws Exception {
    TeacherWeixin teacherWeixin = new TeacherWeixin();
    teacherWeixin.setTeacherId(teacherId);

    List<TeacherWeixin> teacherWeixinList = super.findList(teacherWeixin,
        "key_id,teacher_id,open_id");
    return teacherWeixinList;
  }

  /**
   * Title: 通过open_id查找是否有绑定数据<br>
   * Description: 通过open_id查找是否有绑定数据<br>
   * CreateDate: 2017年6月15日 下午5:04:18<br>
   * 
   * @category 通过open_id查找是否有绑定数据
   * @author komi.zsy
   * @param openId
   *          微信openid
   * @return
   * @throws Exception
   */
  public int findConutByOpenId(String openId) throws Exception {
    TeacherWeixin teacherWeixin = new TeacherWeixin();
    teacherWeixin.setOpenId(openId);
    return super.findCount(teacherWeixin);
  }

  /**
   * 
   * Title: findOpenIdAll<br>
   * Description: 查询成人微信数据库中所有已绑定微信服务号的openId<br>
   * CreateDate: 2017年7月6日 下午2:38:15<br>
   * 
   * @category findOpenIdAll
   * @author felix.yl
   * @return
   * @throws Exception
   */
  public List<TeacherWeixin> findOpenIdAll() throws Exception {
    return super.findList(new TeacherWeixin(), "open_id");
  }

}