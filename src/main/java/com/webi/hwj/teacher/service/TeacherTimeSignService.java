package com.webi.hwj.teacher.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.bean.SessionTeacher;
import com.webi.hwj.teacher.dao.TeacherEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeSignDao;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.entity.TeacherTimeSign;
import com.webi.hwj.teacher.util.TeacherUtil;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TeacherTimeSignService {
  private static Logger logger = Logger.getLogger(TeacherTimeSignService.class);
  @Resource
  TeacherTimeSignDao teacherTimeSignDao;

  @Resource
  TeacherTimeEntityDao teacherTimeEntityDao;

  @Resource
  TeacherEntityDao teacherEntityDao;

  /**
   * Title: 查询教师签课信息<br>
   * Description: 分页查询按start_time排正序的教师签课信息<br>
   * CreateDate: 2016年4月27日 上午11:09:17<br>
   * 
   * @category 分页查询按start_time排正序的教师签课信息
   * @author komi.zsy
   * @param paramObj
   * @param page
   *          页数
   * @param rows
   *          行数
   * @return
   * @throws Exception
   */
  public Page findPageByStartTime(TeacherTimeSign paramObj, Integer page, Integer rows)
      throws Exception {
    return teacherTimeSignDao.findPage(paramObj, "start_time", "ASC", page, rows);
  }

  /**
   * Title: 添加签课时间段<br>
   * Description: 查询是否已有教师签课信息，没有则添加新数据<br>
   * CreateDate: 2016年4月27日 下午5:44:31<br>
   * 
   * @category 查询是否已有教师签课信息，没有则添加新数据
   * @author komi.zsy
   * @param teacherTimeSign
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void insertTeacherTimeSignByAddTime(List<Map<String, Object>> teacherSignTimeList,
      SessionTeacher sessionTeacher) throws Exception {
    // modify by seven 2016年12月28日20:46:52 老师信息不能从session中拿要查数据库
    Teacher teacher = teacherEntityDao.findOneByKeyId(sessionTeacher.getKeyId());

    // 是否需要回滚
    boolean isRollBack = false;
    // 错误的日期
    StringBuffer errorDateList = new StringBuffer();
    // 遍历所有签课时间
    for (Map<String, Object> teacherSignTimeMap : teacherSignTimeList) {
      String startTime = (String) teacherSignTimeMap.get("startTime");
      String endTime = (String) teacherSignTimeMap.get("endTime");
      // 用于前端返回YYMMDD
      String strStartTime = DateUtil.dateToStrYYMMDD(DateUtil.strToDateYYYYMMDD(startTime));
      try {
        // 效验时间是否合法
        if (TeacherUtil.checkTimeIsInvalid(startTime, endTime)) {
          TeacherTimeSign teacherTimeSign = new TeacherTimeSign();

          teacherTimeSign.setStartTime(DateUtil.strToDateYYYYMMDDHHMMSS(startTime));
          teacherTimeSign.setEndTime(DateUtil.strToDateYYYYMMDDHHMMSS(endTime));
          teacherTimeSign.setTeacherId(teacher.getKeyId());
          teacherTimeSign.setTeacherName(teacher.getTeacherName());
          teacherTimeSign.setTeacherCourseType(teacher.getTeacherCourseType());
          teacherTimeSign.setCreateUserId(teacher.getKeyId());

          // 添加新的签课时间
          // 是否可以添加新的签课时间,为0则代表，没有任何相交、重复的已有签课时间
          if (teacherTimeSignDao.findTeacherTimeSignByAddTime(teacherTimeSign) == 0) {
            teacherTimeSignDao.insert(teacherTimeSign);
          } else {
            if (isRollBack) {
              errorDateList.append(",");
            }
            errorDateList.append(strStartTime);
            isRollBack = true;
          }
        } else {
          logger.info("时间不合法！----->");
          if (isRollBack) {
            errorDateList.append(",");
          }
          errorDateList.append(strStartTime);
          isRollBack = true;

        }
      } catch (Exception e) {
        logger.error("教师签课出错---->" + e.toString());
        e.printStackTrace();
        if (isRollBack) {
          errorDateList.append(",");
        }
        errorDateList.append(strStartTime);
        isRollBack = true;
      }
    }

    // 需要回滚
    if (isRollBack) {
      logger.info("老师签课时间不合法！----->");
      throw new RuntimeException(errorDateList.toString());
    }
  }

  /**
   * Title: 删除签课时间段<br>
   * Description: 查询是否存在删除时间段，并且该时间段没有已预约1v1课程或已排课大课课程，存在则删除<br>
   * CreateDate: 2016年4月28日 上午11:50:03<br>
   * 
   * @category 查询是否存在删除时间段，并且该时间段没有已预约1v1课程或已排课大课课程，存在则删除
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void deleteTeacherTimeSignByNewTime(List<Map<String, Object>> teacherSignTimeList,
      String TeacherId) throws Exception {
    String updateUserId = TeacherId;
    // 是否需要回滚
    boolean isRollBack = false;
    // 错误的日期
    StringBuffer errorDateList = new StringBuffer();
    // 遍历所有签课时间
    for (Map<String, Object> teacherSignTimeMap : teacherSignTimeList) {
      String startTime = (String) teacherSignTimeMap.get("startTime");
      String endTime = (String) teacherSignTimeMap.get("endTime");
      // 用于前端返回YYMMDD
      String strStartTime = DateUtil.dateToStrYYMMDD(DateUtil.strToDateYYYYMMDD(startTime));
      try {
        // 效验时间是否合法
        if (TeacherUtil.checkTimeIsInvalid(startTime, endTime)) {
          TeacherTimeSign paramObj = new TeacherTimeSign();
          paramObj.setStartTime(DateUtil.strToDateYYYYMMDDHHMMSS(startTime));
          paramObj.setEndTime(DateUtil.strToDateYYYYMMDDHHMMSS(endTime));
          paramObj.setTeacherId(TeacherId);

          // 删除老师签课
          TeacherTimeSign teacherTimeSign = teacherTimeSignDao
              .findTeacherTimeSignByDeleteTime(paramObj);

          if (teacherTimeSign == null) {
            // 没有签课时间
            if (isRollBack) {
              errorDateList.append(",");
            }
            errorDateList.append(strStartTime);
            isRollBack = true;
          } else {
            TeacherTime paramTeacherTime = new TeacherTime();
            paramTeacherTime.setTeacherId(teacherTimeSign.getTeacherId());
            paramTeacherTime.setStartTime(paramObj.getStartTime());
            paramTeacherTime.setEndTime(paramObj.getEndTime());
            List<TeacherTime> teacherTimeList = teacherTimeSignDao
                .findTeacherTimeByTeacherTimeSign(paramTeacherTime);

            // 是否能删除,true能删除
            boolean isCanDelete = true;

            // teacher_time表需要删除的list<bean>
            List<String> timeBeanList = new ArrayList<String>();

            for (TeacherTime teacherTime : teacherTimeList) {
              if ("course_type1".equals(teacherTime.getCourseType())
                  || "course_type9".equals(teacherTime.getCourseType())) {
                // 1v1课程，被预约则不能删除，没有预约仅排课可以删除
                if (teacherTime.getIsSubscribe()) {
                  isCanDelete = false;
                  // json.setMsg("Time chosen has been booked by student");
                }
              } else {
                // 大课类型，排了课则不能删除
                isCanDelete = false;
                // json.setMsg("Time chosen has been arranged, please contact
                // duty teacher");
              }

              // 既不是1v1已预约课程，也不是大课已排课课程，则可以删除
              timeBeanList.add(teacherTime.getKeyId());
            }

            if (isCanDelete) {
              // 更新人id
              teacherTimeSign.setUpdateUserId(updateUserId);

              long startTimeLong = teacherTimeSign.getStartTime().getTime();
              long endTimeLong = teacherTimeSign.getEndTime().getTime();
              long paramStartTimeLong = paramObj.getStartTime().getTime();
              long paramEndTimeLong = paramObj.getEndTime().getTime();
              // 可以在t_teacher_time_sign表中删除需要删除的时间段
              if (paramStartTimeLong == startTimeLong) {
                if (paramEndTimeLong == endTimeLong) {
                  // 直接删除签课数据
                  teacherTimeSignDao.delete(teacherTimeSign.getKeyId(), updateUserId);
                } else if (paramEndTimeLong < endTimeLong) {
                  // 更新签课数据，把原签课.start_time修改为参数endTime
                  teacherTimeSign.setStartTime(paramObj.getEndTime());
                  teacherTimeSignDao.update(teacherTimeSign);
                }
              } else if (paramEndTimeLong == endTimeLong && paramStartTimeLong > startTimeLong) {
                // 更新签课数据，把原签课.end_time修改为参数start_time
                teacherTimeSign.setEndTime(paramObj.getStartTime());
                teacherTimeSignDao.update(teacherTimeSign);
              } else {
                // 直接删除签课数据
                teacherTimeSignDao.delete(teacherTimeSign.getKeyId(), updateUserId);

                // 增加新数据
                TeacherTimeSign teacherTimeSignNew = new TeacherTimeSign();
                teacherTimeSignNew.setTeacherId(teacherTimeSign.getTeacherId());
                teacherTimeSignNew.setTeacherName(teacherTimeSign.getTeacherName());
                teacherTimeSignNew.setTeacherCourseType(teacherTimeSign.getTeacherCourseType());
                teacherTimeSignNew.setCreateUserId(updateUserId);
                // 增加一条原签课.start_time,参数startTime
                teacherTimeSignNew.setStartTime(teacherTimeSign.getStartTime());
                teacherTimeSignNew.setEndTime(paramObj.getStartTime());
                teacherTimeSignDao.insert(teacherTimeSignNew);

                // 增加一条参数endTime，原签课.end_time
                teacherTimeSignNew.setKeyId(null);
                teacherTimeSignNew.setStartTime(paramObj.getEndTime());
                teacherTimeSignNew.setEndTime(teacherTimeSign.getEndTime());
                teacherTimeSignDao.insert(teacherTimeSignNew);
              }

              if (timeBeanList.size() > 0) {
                // 批量删除timeBeanList中在teacher_time表中的数据
                teacherTimeEntityDao.delete(timeBeanList, updateUserId);
              }
            } else {
              if (isRollBack) {
                errorDateList.append(",");
              }
              errorDateList.append(strStartTime);
              isRollBack = true;
            }
          }
        } else {
          logger.info("时间不合法！----->");
          // json.setMsg("Please select your available time first");
          if (isRollBack) {
            errorDateList.append(",");
          }
          errorDateList.append(strStartTime);
          isRollBack = true;
        }
      } catch (Exception e) {
        logger.error("教师删除签课出错---->" + e.toString());
        e.printStackTrace();
        if (isRollBack) {
          errorDateList.append(",");
        }
        errorDateList.append(strStartTime);
        isRollBack = true;
      }
    }

    // 需要回滚
    if (isRollBack) {
      logger.info("老师删除签课时间不合法！----->");
      throw new RuntimeException(errorDateList.toString());
    }
  }
}