package com.webi.hwj.coperation.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.coperation.constant.QqConstant;
import com.webi.hwj.coperation.dao.CoperationDao;
import com.webi.hwj.coperation.entity.Coperation;
import com.webi.hwj.coperation.param.RedeemCodeRegisterParam;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.dao.UserInfoEntityDao;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entity.UserInfo;

/**
 * @category qqCoperation控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
public class CoperationService {
  private static Logger logger = Logger.getLogger(CoperationService.class);
  @Resource
  CoperationDao coperationDao;
  
  @Resource
  UserEntityDao userEntityDao;

  @Resource
  UserInfoEntityDao userInfoEntityDao;

  /**
   * 
   * Title: 保存合作方兑换数据表<br>
   * Description: 保存合作方兑换数据表<br>
   * CreateDate: 2016年10月9日 下午5:07:00<br>
   * @category 保存合作方兑换数据表 
   * @author seven.gz
   * @param user
   * @param redeemCodeRegisterParam
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      timeout = 15)
  public void saveCoperation(String userKeyId, RedeemCodeRegisterParam redeemCodeRegisterParam,Integer coperationType) throws Exception{
 
    Coperation coperation = new Coperation();
    coperation.setUserId(userKeyId);
    coperation.setRedeemCode(redeemCodeRegisterParam.getRedeemCode());
    coperation.setUserName(redeemCodeRegisterParam.getUserName());
    coperation.setUserPhone(redeemCodeRegisterParam.getPhone());
    coperation.setUserProvince(redeemCodeRegisterParam.getProvince());
    coperation.setUserCity(redeemCodeRegisterParam.getCity());
    coperation.setCoperationType(coperationType);
    coperationDao.insert(coperation);
  }
  
  /**
   * Title: 保存用户信息<br>
   * Description: 保存用户信息<br>
   * CreateDate: 2016年9月26日 下午4:31:08<br>
   * 
   * @category 保存用户信息
   * @author seven.gz
   * @param redeemCodeRegisterParam
   *          参数bean
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      timeout = 15)
  public void saveUser(RedeemCodeRegisterParam redeemCodeRegisterParam,Integer coperationType) throws Exception {
    // 保存用户表
    User user = new User();
    BeanUtils.copyProperties(redeemCodeRegisterParam, user);
    userEntityDao.insert(user);
    // 保存用户信息表
    UserInfo userInfo = new UserInfo();
    BeanUtils.copyProperties(redeemCodeRegisterParam, userInfo);
    userInfo.setKeyId(user.getKeyId());
    userInfoEntityDao.insert(userInfo);
    // 保存合作方兑换数据表
    saveCoperation(user.getKeyId(), redeemCodeRegisterParam,coperationType);
    
    redeemCodeRegisterParam.setUserId(user.getKeyId());
  }
}