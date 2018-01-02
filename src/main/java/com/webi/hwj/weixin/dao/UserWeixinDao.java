package com.webi.hwj.weixin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.weixin.entity.UserWeixin;

@Repository
public class UserWeixinDao extends BaseEntityDao<UserWeixin> {
  private static Logger logger = Logger.getLogger(UserWeixinDao.class);

  /**
   * 根据微信openid删除绑定
   * 
   * @author komi.zsy
   */
  private static final String DELETE_BY_OPENID = "UPDATE `t_user_weixin` SET is_used = 0"
      + " WHERE open_id = :openId AND is_used = 1";

  /**
   * 
   * Title: 根据微信openid删除绑定<br>
   * Description: 根据微信openid删除绑定<br>
   * CreateDate: 2016年10月26日 上午11:46:14<br>
   * 
   * @category 根据微信openid删除绑定
   * @author komi.zsy
   * @param weixinOpenId
   *          微信openid
   * @return
   * @throws Exception
   */
  public int deleteByOpenId(String weixinOpenId) throws Exception {
    UserWeixin paramObj = new UserWeixin();
    paramObj.setOpenId(weixinOpenId);
    return super.update(DELETE_BY_OPENID, paramObj);
  }

  /**
   * Title: 通过openid查找userweixin数据.<br>
   * Description: find<br>
   * CreateDate: 2016年10月14日 下午2:13:00<br>
   * 
   * @category 通过openid查找userweixin数据.
   * @author yangmh
   */
  public List<UserWeixin> findListByOpenId(String openId) throws Exception {
    UserWeixin userWeixin = new UserWeixin();
    userWeixin.setOpenId(openId);

    List<UserWeixin> userWeixinList = super.findList(userWeixin, "key_id,user_id,open_id");
    return userWeixinList;
  }

  /**
   * Title: 查找所有userweixin数据.<br>
   * Description: <br>
   * CreateDate: 2016年10月14日 下午2:13:00<br>
   * 
   * @category 查找所有userweixin数据.
   * @author yangmh
   */
  public List<UserWeixin> findListAll() throws Exception {
    return super.findList(new UserWeixin(), "open_id");
  }

  /**
   * Title: 通过user_id查找userweixin数据.<br>
   * Description: find<br>
   * CreateDate: 2016年10月14日 下午2:13:00<br>
   * 
   * @category 通过openid查找userweixin数据.
   * @author yangmh
   */
  public List<UserWeixin> findListByUserId(String userId) throws Exception {
    UserWeixin userWeixin = new UserWeixin();
    userWeixin.setUserId(userId);

    List<UserWeixin> userWeixinList = super.findList(userWeixin, "key_id,user_id,open_id");
    return userWeixinList;
  }

  /**
   * Title: 通过user_id+open_id查找userweixin数据.<br>
   * Description: find<br>
   * CreateDate: 2016年10月14日 下午2:13:00<br>
   * 
   * @category 通过openid查找userweixin数据.
   * @author yangmh
   */
  public List<UserWeixin> findListByUserIdAndOpenId(String userId, String openId) throws Exception {
    UserWeixin userWeixin = new UserWeixin();
    userWeixin.setUserId(userId);
    userWeixin.setOpenId(openId);

    List<UserWeixin> userWeixinList = super.findList(userWeixin, "key_id,user_id,open_id");
    return userWeixinList;
  }

  /**
   * 
   * Title: 查询成人所有已绑定微信服务号的openId<br>
   * Description: 查询成人所有已绑定微信服务号的openId<br>
   * CreateDate: 2017年7月6日 下午2:38:15<br>
   * 
   * @category 查询成人所有已绑定微信服务号的openId
   * @author felix.yl
   * @return
   * @throws Exception
   */
  public List<UserWeixin> findOpenIdAll() throws Exception {
    return super.findList(new UserWeixin(), "open_id");
  }

}