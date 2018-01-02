package com.webi.hwj.classin.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.classin.param.ClassinAppUrlParam;
import com.webi.hwj.classin.util.ClassinUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.entity.TeacherTime;

/**
 * Title: 展示互动的业务逻辑<br>
 * Description: GenseeService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月7日 下午2:46:59
 * 
 * @author yangmh
 */
@Service
public class ClassinService {
  private static Logger logger = Logger.getLogger(ClassinService.class);

  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;

  /**
   * 
   * Title: 创建房间<br>
   * Description: insertGensee<br>
   * CreateDate: 2017年8月24日 下午4:57:39<br>
   * 
   * @category insertGensee
   * @author seven.gz
   * @param teacherTimeId
   *          老师时间id
   * @param courseTitle
   *          课程标题
   * @param coursePic
   *          课程图片
   * @param schedulingId
   *          排课id
   * @param teacherName
   *          老师名称
   * @param teacherPhoto
   *          老师头像
   * @param startTime
   *          开始时间
   * @param durationMinutes
   *          持续时长
   * @param UserName
   *          学员名称
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE,
      rollbackFor = {
          Exception.class })
  public void insertClassin(String teacherTimeId, String courseTitle, String coursePic,
      String schedulingId,
      String teacherName, String teacherPhoto,
      Date startTime, int durationMinutes, String courseId)
          throws Exception {

    String roomId = ClassinUtil.createRoom(courseTitle, coursePic, schedulingId, teacherName,
        teacherPhoto,
        startTime, durationMinutes, courseId);

    TeacherTime teacherTime = new TeacherTime();
    teacherTime.setKeyId(teacherTimeId);
    teacherTime.setRoomId(roomId);
    teacherTimeEntityDao.update(teacherTime);
  }

  /**
   * 
   * Title: 获取学员进入教室短链接<br>
   * Description: 这个链接短，但是需要调用下接口<br>
   * CreateDate: 2017年8月28日 下午4:24:11<br>
   * 
   * @category 获取学员进入教室短链接
   * @author seven.gz
   * @param roomId
   * @param userId
   * @param userName
   * @param userPhoto
   */
  public static String goToClassinClassStudentShort(String roomId, String userId, String userName,
      String userPhoto)
          throws Exception {

    ClassinAppUrlParam classinAppUrlParam = ClassinUtil.findAppUrl(roomId, userId, userName,
        userPhoto);

    return MemcachedUtil.getConfigValue(ConfigConstant.CLASSIN_URL)
        + "/room/enter?sn=" + classinAppUrlParam.getSn();
  }

  /**
   * 
   * Title: 老师进入房间链接<br>
   * Description: goToClassinClassTeacher<br>
   * CreateDate: 2017年8月24日 下午6:27:38<br>
   * 
   * @category 老师进入房间链接
   * @author seven.gz
   * @param roomId
   *          房间号
   */
  public String goToClassinClassTeacher(String roomId)
      throws Exception {
    return ClassinUtil.findTeacherUrl(roomId);
  }

  /**
   * 
   * Title: 删除教室<br>
   * Description: goToClassinClassTeacher<br>
   * CreateDate: 2017年8月24日 下午6:27:38<br>
   * 
   * @category 删除教室
   * @author seven.gz
   * @param roomId
   *          房间号
   */
  public void deleteRoom(String roomId)
      throws Exception {
    ClassinUtil.deleteRoom(roomId);
  }
}