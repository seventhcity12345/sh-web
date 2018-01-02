package com.webi.hwj.gensee.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.gensee.constant.GenseeConstant;
import com.webi.hwj.gensee.dao.GenseeDao;
import com.webi.hwj.gensee.entity.Gensee;
import com.webi.hwj.gensee.util.GenseeUtil;
import com.webi.hwj.user.dao.UserDao;

/**
 * Title: 展示互动的业务逻辑<br>
 * Description: GenseeService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月7日 下午2:46:59
 * 
 * @author yangmh
 */
@Service
public class GenseeService {
  private static Logger logger = Logger.getLogger(GenseeService.class);

  @Resource
  private GenseeDao genseeDao;

  @Resource
  private UserDao userDao;

  /**
   * Title: 创建展示互动数据+房间<br>
   * Description: insertGensee<br>
   * CreateDate: 2016年7月7日 下午2:56:18<br>
   * 
   * @category 创建展示互动数据+房间
   * @author yangmh
   * @param teacherTimeId
   *          老师时间ID
   * @param roomType
   *          房间类型
   * @param subject
   *          房间名称
   * @param startTime
   *          开始时间
   * @param endTime
   *          结束时间
   * @throws Exception
   *           需要接异常，直接抛给用户。
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = {
          Exception.class })
  public Gensee insertGensee(String teacherTimeId, String courseType,int roomType, String subject, Date startTime,
      Date endTime,String teacherDesc,String teacherName,String courseDesc)
      throws Exception {
    /**
     * modified by komi 2017年1月4日14:21:53
     * 创建房间是增加课程、教师简介信息，需求472
     */
    Gensee gensee = GenseeUtil.createRoom(roomType, courseType,subject, startTime, endTime,
        teacherDesc, teacherName, courseDesc);
    gensee.setKeyId(teacherTimeId);
    genseeDao.insert(gensee);
    return gensee;
  }

  /**
   * Title: 进入展示互动教室<br>
   * Description: goToGenseeClass<br>
   * CreateDate: 2016年7月7日 下午4:03:15<br>
   * 
   * @category 进入展示互动教室
   * @author yangmh
   * @param teacherTimeId
   * @param goType
   *          1：老师进入教室，2：学生进入教室，3：助教进入教室
   * @param userName
   *          用户名称
   * @param userCode
   *          如果是学生进入教室才需要此参数
   * @return
   * @throws Exception
   *           需要接异常，直接抛给用户。
   */
  public String goToGenseeClass(String teacherTimeId, int goType, String userName, Integer userCode)
      throws Exception {
    Gensee gensee = genseeDao.findOneByKeyId(teacherTimeId);
    if (gensee == null) {
      throw new RuntimeException("gensee is null");
    }
    String roomUrl = null;

    switch (goType) {
    case GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_TEACHER:
      // 老师进教室
      roomUrl = gensee.getTeacherJoinUrl()
          + "?nickname=" + userName
          + "&token=" + GenseeConstant.GENSEE_TEACHER_TOKEN;
      break;
    case GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_STUDENT:
      // 考虑潜客，userName为空时，userName使用userCode
      if (StringUtils.isEmpty(userName)) {
        userName = userCode.toString();
      }
      // 学生进教室
      roomUrl = gensee.getStudentJoinUrl()
          + "?uid=" + (1000000000 + userCode)
          + "&nickname=" + userName;
      // modified by alex.ymh 2016年8月1日16:28:55,学生不再通过客户端进入展视互动的教室。
      // +"&token="+GenseeConstant.GENSEE_STUDENT_CLIENT_TOKEN;

      break;
    case GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_NOT_USER:
      // 非用户进教室
      roomUrl = gensee.getStudentJoinUrl()
          + "?nickname=" + userName;
      break;
    case GenseeConstant.GENSEE_GO_TO_CLASS_TYPE_ASSISTANT:
      // 助教进教室
      roomUrl = gensee.getTeacherJoinUrl()
          + "?nickname=" + userName
          + "&token=" + GenseeConstant.GENSEE_ASSISTANT_TOKEN;
      break;
    }
    return roomUrl;
  }

  /**
   * Title: 查看展示互动录像<br>
   * Description: watchGenseeVideo<br>
   * CreateDate: 2016年7月10日 上午11:31:39<br>
   * 
   * @category 查看展示互动录像
   * @author yangmh
   * @param teacherTimeId
   * @param userName
   *          用户名
   * @return
   * @throws Exception
   */
  public String watchGenseeVideo(String teacherTimeId, String userName) throws Exception {
    Gensee gensee = genseeDao.findOneByKeyId(teacherTimeId);
    if (gensee == null) {
      throw new RuntimeException("gensee is null");
    }
    return GenseeUtil.watchVideo(gensee.getRoomId(), userName);
  }
}