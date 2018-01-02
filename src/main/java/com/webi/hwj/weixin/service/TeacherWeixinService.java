package com.webi.hwj.weixin.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.teacher.dao.TeacherEntityDao;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.weixin.dao.TeacherWeixinDao;
import com.webi.hwj.weixin.entity.TeacherWeixin;
import com.webi.hwj.weixin.param.BindTeacherParam;
import com.webi.hwj.weixin.util.WeixinUtil;

/**
 * @category teacherWeixin控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class TeacherWeixinService {
  private static Logger logger = Logger.getLogger(TeacherWeixinService.class);
  @Resource
  private TeacherWeixinDao teacherWeixinDao;
  @Resource
  private TeacherEntityDao teacherEntityDao;

  /**
   * Title: 通过openid查找数据<br>
   * Description: 通过openid查找数据<br>
   * CreateDate: 2017年6月15日 下午4:48:00<br>
   * @category 通过openid查找数据 
   * @author komi.zsy
   * @param openId 微信openid
   * @return
   * @throws Exception
   */
  public String findTeacherIdByOpenId(String openId) throws Exception {
    String returnUseId = null;
    List<TeacherWeixin> teacherWeixinList = teacherWeixinDao.findListByOpenId(openId);

    if (teacherWeixinList != null && teacherWeixinList.size() > 0) {
      TeacherWeixin teacherWeixin = teacherWeixinList.get(0);
      returnUseId = teacherWeixin.getTeacherId();
    }

    return returnUseId;
  }

  /**
   * Title: 微信用户绑定.<br>
   * Description: bindTeacher<br>
   * CreateDate: 2016年10月17日 下午2:41:26<br>
   * 
   * @category 微信用户绑定.
   * @author yangmh
   * @param bindTeacherParam
   *          参数bean
   * @return 通用json对象
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
  public CommonJsonObject bindTeacher(BindTeacherParam bindTeacherParam)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 先用手机号查询用户信息
    Teacher teacherReturn = teacherEntityDao.findTeacherByAccount(bindTeacherParam.getAccount());

    if (teacherReturn == null) {
      // 没有用查询到数据，直接返回前端错误
      json.setCode(ErrorCodeEnum.WEIXIN_TEACHER_BIND_NOT_MATCH.getCode());
      return json;
    } else {
      // 不允许多个speakhi账号绑定一个微信号，允许多个微信号绑定多个speakhi账号。
      int teacherWinxinCount = teacherWeixinDao
          .findConutByOpenId(bindTeacherParam.getWeixinOpenId());
      if (teacherWinxinCount > 0) {
        json.setCode(ErrorCodeEnum.WEIXIN_USER_BIND_JUST_ONE.getCode());
        return json;
      }
    }

    // 插入绑定表
    TeacherWeixin teacherWeixin = new TeacherWeixin();
    teacherWeixin.setOpenId(bindTeacherParam.getWeixinOpenId());
    teacherWeixin.setTeacherId(teacherReturn.getKeyId());
    teacherWeixinDao.insert(teacherWeixin);
    
    //修改教师表，绑定微信标识改为已绑定
    Teacher teacherObject= new Teacher();
    teacherObject.setKeyId(teacherReturn.getKeyId());
    teacherObject.setIsBindWechat(true);
    teacherEntityDao.update(teacherObject);

    return json;
  }
  
  /**
   * Title: 给教师发送微信推送<br>
   * Description: 给教师发送微信推送<br>
   * CreateDate: 2017年6月16日 下午2:58:22<br>
   * @category 给教师发送微信推送 
   * @author komi.zsy
   * @param dataMap 发送内容
   * @param teacherId 教师id
   * @param templateId 模板id
   * @throws Exception
   */
  public void sendWeixinMsgToTeacher(Map<String, Object> dataMap, String teacherId, String templateId) throws Exception {
    // 给谁发,发给当前teacherId绑定过的oepnid列表(因为很有可能一个speakhi帐号绑定了多个微信号)
    List<TeacherWeixin> teacherWeixinList = teacherWeixinDao.findListByTeacherId(teacherId);
    if (teacherWeixinList != null && teacherWeixinList.size() > 0) {
      for (TeacherWeixin teacherWeixin : teacherWeixinList) {
        WeixinUtil.sendWeixinMsg(teacherWeixin.getOpenId(),
            templateId, null, dataMap);
      }
    }
  }
}