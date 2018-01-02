package com.webi.hwj.course.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mingyisoft.javabase.aliyun.ocs.OSSClientUtil;
import com.mingyisoft.javabase.aliyun.ons.OnsProducerClient;
import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SqlUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.CourseConstant;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.constant.AdminCourseConstant;
import com.webi.hwj.course.dao.CourseOne2ManySchedulingDao;
import com.webi.hwj.course.entity.AdminCourse;
import com.webi.hwj.courseextension1v1.dao.CourseExtension1v1Dao;
import com.webi.hwj.courseextension1v1.entity.CourseExtension1v1;
import com.webi.hwj.courseone2many.dao.AdminCourseOne2ManyDao;
import com.webi.hwj.courseone2many.dao.AdminCourseOne2ManySchedulingDao;
import com.webi.hwj.courseone2many.entity.CourseOne2Many;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.courseone2many.param.SchedulingChangeTeacherParam;
import com.webi.hwj.courseone2one.dao.AdminCourseOne2oneDao;
import com.webi.hwj.courseone2one.entity.CourseOne2One;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.huanxun.exception.HuanxunException;
import com.webi.hwj.huanxun.service.HuanxunService;
import com.webi.hwj.ordercourse.entitydao.AdminOrderCourseEntityDao;
import com.webi.hwj.subscribecourse.dao.AdminSubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseEntityDao;
import com.webi.hwj.subscribecourse.entity.SubscribeCourse;
import com.webi.hwj.teacher.dao.TeacherEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeSignDao;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.entity.TeacherTimeSign;
import com.webi.hwj.teacher.param.TeacherTimeParam;

/**
 * @category courseOne2many控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminCourseService {

  private static Logger logger = Logger.getLogger(AdminCourseService.class);
  @Resource
  AdminCourseOne2ManyDao adminCourseOne2ManyDao;
  @Resource
  SubscribeCourseDao subscribeCourseDao;
  @Resource
  AdminCourseOne2oneDao adminCourseOne2oneDao;
  @Resource
  AdminCourseOne2ManySchedulingDao adminCourseOne2ManySchedulingDao;
  @Resource
  AdminOrderCourseEntityDao adminOrderCourseEntityDao;
  @Resource
  AdminSubscribeCourseDao adminSubscribeCourseDao;
  @Resource
  CourseOne2ManySchedulingDao courseOne2ManySchedulingDao;
  @Resource
  CourseExtension1v1Dao courseExtension1v1Dao;
  @Resource
  private TeacherEntityDao teacherEntityDao;
  @Resource
  private TeacherTimeSignDao teacherTimeSignDao;
  @Resource
  private TeacherTimeEntityDao teacherTimeEntityDao;
  @Resource
  private SubscribeCourseEntityDao subscribeCourseEntityDao;
  @Resource
  private HuanxunService huanxunService;

  /**
   * Title: 排课后更换老师<br>
   * Description: 排课后更换老师<br>
   * CreateDate: 2016年10月27日 上午11:31:15<br>
   * 
   * @category 排课后更换老师
   * @author komi.zsy
   * @param schedulingChangeTeacherParam
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public CommonJsonObject changeSchedulingTeacher(
      SchedulingChangeTeacherParam schedulingChangeTeacherParam) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    // 老师id
    String teacherId = schedulingChangeTeacherParam.getTeacherId();
    // 开始时间
    Date startTime = schedulingChangeTeacherParam.getStartTime();
    // 结束时间
    Date endTime = schedulingChangeTeacherParam.getEndTime();
    // 原来的课程id
    String courseId = schedulingChangeTeacherParam.getCourseId();
    // 老师时间id
    String teacherTimeId = schedulingChangeTeacherParam.getTeacherTimeId();

    Teacher teacherObj = teacherEntityDao.findOneByKeyId(teacherId);
    if (teacherObj == null) {
      // 老师不存在
      json.setCode(ErrorCodeEnum.TEACHER_NOT_EXIST.getCode());
      return json;
    }

    if (startTime.getTime() < System.currentTimeMillis()) {
      // 老师不存在
      json.setCode(ErrorCodeEnum.TEACHER_NOT_EXIST.getCode());
      return json;
    }

    // 查询此老师是否有可用时间（是否被老师删除）
    TeacherTimeSign teacherTimeSignPara = new TeacherTimeSign();
    teacherTimeSignPara.setStartTime(startTime);
    teacherTimeSignPara.setEndTime(endTime);
    teacherTimeSignPara.setTeacherId(teacherId);
    TeacherTimeSign teacherTimeSignAvailable = teacherTimeSignDao
        .findTeacherTimeSignByDeleteTime(teacherTimeSignPara);
    if (teacherTimeSignAvailable == null) {
      // 老师时间不存在
      json.setCode(ErrorCodeEnum.TEACHER_TIME_NOT_EXIST.getCode());
      return json;
    }

    // 老师时间查询参数
    TeacherTimeParam teacherTimeParam = new TeacherTimeParam();
    teacherTimeParam.setStartTime(startTime);
    teacherTimeParam.setEndTime(endTime);
    teacherTimeParam.setTeacherId(teacherId);
    // 在签课老师中找出这些老师这个时间段已经被排课的老师
    List<TeacherTimeParam> teacherTimeListToRemove = teacherTimeEntityDao
        .findOverlapTimeTeachers(teacherTimeParam);
    if (teacherTimeListToRemove != null && teacherTimeListToRemove.size() > 0) {
      // 老师已被排课
      json.setCode(ErrorCodeEnum.TEACHER_TIME_NOT_EXIST.getCode());
      return json;
    }

    // 老师名字
    String teacherName = teacherObj.getTeacherName();

    // 如果大课id不为空，则要更新大课排课表
    if (!StringUtils.isEmpty(courseId)) {
      CourseOne2ManyScheduling paramObj = new CourseOne2ManyScheduling();
      paramObj.setKeyId(courseId);
      paramObj.setTeacherId(teacherId);
      paramObj.setTeacherName(teacherName);
      paramObj.setTeacherPhoto(teacherObj.getTeacherPhoto());
      paramObj.setTeacherTimeId(teacherTimeId);
      adminCourseOne2ManySchedulingDao.update(paramObj);
    }

    // 更新老师时间表，将原来的老师时间的老师id和老师名字更新成更换的老师,确认出席状态置为否
    TeacherTime teacherTimeParamObj = new TeacherTime();
    teacherTimeParamObj.setKeyId(teacherTimeId);
    teacherTimeParamObj.setTeacherId(teacherId);
    teacherTimeParamObj.setTeacherName(teacherName);
    teacherTimeParamObj.setIsConfirm(false);
    teacherTimeEntityDao.update(teacherTimeParamObj);

    // 更新 根据老师时间id 排的课 的老师相关信息
    subscribeCourseEntityDao.updateTeacherInfoByTeacherTimeId(teacherTimeId, teacherId,
        teacherName);

    // 如果是环迅老师需要预约、取消预约等操作，先预约，因为预约是必须成功的，取消预约失败也不管。写在最下面，是以防韦博业务逻辑更新失败，环迅无法回滚
    TeacherTime teacherTime = teacherTimeEntityDao.findOneByKeyId(teacherTimeId);
    String courseType = teacherTime.getCourseType();

    // 如果更换的老师是环迅的，要去环迅预约
    if (ConfigConstant.TEACHER_THIRD_FROM_HUANXUN.equals(teacherObj.getThirdFrom())
        && ("course_type1".equals(courseType) || "course_type9".equals(courseType))) {
      // 根据老师时间id查找学员id
      SubscribeCourse subscribeCourse = subscribeCourseEntityDao
          .findUserIdAndTeacherIdByTeacherTimeId(teacherTimeId);
      if (subscribeCourse != null) {
        // 如果是环迅的老师，需要调用环迅的接口
        Map<String, Object> huanxunParamMap = new HashMap<String, Object>();
        huanxunParamMap.put("course_type", courseType);
        huanxunParamMap.put("teacher_id", teacherId);
        huanxunParamMap.put("start_time", startTime);
        huanxunParamMap.put("end_time", endTime);
        huanxunParamMap.put("course_courseware", subscribeCourse.getCourseCourseware());
        huanxunParamMap.put("user_id", subscribeCourse.getUserId());
        huanxunParamMap.put("user_name", subscribeCourse.getUserName());

        String returnCode = huanxunService.huanxunBook(huanxunParamMap);

        // 如果是老师已被占用，则把韦博侧的老师时间等相关排课数据逻辑删除。
        if ("303".equals(returnCode)) {
          throw new HuanxunException("老师已经被占用!");
        } else if (!"200".equals(returnCode)) {
          throw new RuntimeException("预约环迅老师出错!");
        }
      }
    }

    // 如果是环迅老师，需要去调用环迅老师取消预约。但是就算取消环迅老师出错，也继续更换老师操作。
    if (ConfigConstant.TEACHER_THIRD_FROM_HUANXUN.equals(schedulingChangeTeacherParam
        .getThirdFrom())) {
      // 如果是环迅的老师，需要调用环迅的接口
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("start_time", startTime);
      paramMap.put("end_time", endTime);
      String returnCode = "200";
      switch (courseType) {
        case "course_type1":
        case "course_type9":
        case "course_type4":
        case "course_type13":
          // 1v1取消预约
          // 根据老师时间id查找学员id
          SubscribeCourse subscribeCourse = subscribeCourseEntityDao
              .findUserIdAndTeacherIdByTeacherTimeId(teacherTimeId);
          if (subscribeCourse != null) {
            paramMap.put("user_id", subscribeCourse.getUserId());
            returnCode = huanxunService.huanxunCancel(paramMap);
          }
          break;
        case "course_type2":
        case "course_type5":
        case "course_type8":
          // 1vn取消预约
          paramMap.put("teacher_id", teacherTime.getTeacherId());
          returnCode = huanxunService.huanxunCancelOne2Many(paramMap);
          break;
        default:
          break;
      }
      if (!"200".equals(returnCode)) {
        json.setCode(ErrorCodeEnum.SUBSCRIBE_HUANXUN_CANCEL_ERROR.getCode());
      }
    }

    return json;
  }

  /**
   * 
   * Title: 查询1v1和1vN的课程 <br>
   * Description: 查询1v1和1vN的课程 <br>
   * CreateDate: 2016年4月12日 上午9:24:44<br>
   * 
   * @category 查询1v1和1vN的课程
   * @author seven.gz
   * @param param
   * @return
   * @throws Exception
   */
  public Page findAllCourse(Map<String, Object> param) throws Exception {
    Page p = adminCourseOne2ManyDao.findAllCoursePageEasyui(param);
    List<AdminCourse> datas = p.getDatas();
    if (datas != null && datas.size() > 0) {
      for (AdminCourse element : datas) {
        String courseTypeTemp;
        String categoryTypeTemp;
        // 将课程类型格式化为 中文解释
        if (element.getCourseType() != null) {
          courseTypeTemp = element.getCourseType();
          element.setCourseType(((CourseType) MemcachedUtil.getValue(element.getCourseType()))
              .getCourseTypeChineseName());
          element.setCourseTypeId(courseTypeTemp);
        }
        // 将体系类型格式化为 中文解释
        if (element.getCategoryType() != null) {
          categoryTypeTemp = element.getCategoryType();
          element.setCategoryType((String) MemcachedUtil.getValue(element.getCategoryType()));
          element.setCategoryTypeId(categoryTypeTemp);
        }
      }
    }
    return p;
  }

  /**
   * 
   * Title: 根据id和类型删除课程<br>
   * Description: 根据id和类型删除课程<br>
   * CreateDate: 2016年4月13日 上午11:41:34<br>
   * 
   * @category 根据id和类型删除课程
   * @author seven.gz
   * @param keyId
   * @param courseType
   * @return
   * @throws Exception
   */
  public JsonMessage deleteCourseByKeyIdAndType(String keyId, String courseType,
      String updateUserId)
          throws Exception {
    JsonMessage json = new JsonMessage();
    // 删除的是1v1课程
    if ("course_type1".equals(courseType) || "course_type9".equals(courseType)) {
      // //查看此课是否被预约
      // Map<String,Object> paramMap = new HashMap();
      // paramMap.put("course_id",keyId);
      // try {
      // List<Map<String, Object>> subscribeList =
      // subscribeCourseDao.findList(paramMap, " key_id ");
      // //说明这课程已经被预约，则不能删除
      // if(subscribeList != null && subscribeList.size() > 0){
      // json.setSuccess(false);
      // json.setMsg("课程已经被预约，不能删除");
      // //说明这课程没有被预约，可以删除
      // } else {
      // int sc = adminCourseOne2oneDao.delete(keyId);
      // if(1 == sc){
      // json.setSuccess(true);
      // json.setMsg("删除成功");
      // } else {
      // json.setSuccess(true);
      // json.setMsg("删除失败");
      // }
      // }
      // } catch (Exception e) {
      // logger.error("删除1v1课程出错" +" KeyId:" + keyId + " :"+ e);
      // json.setSuccess(false);
      // json.setMsg("删除1v1课程出错");
      // }
      json.setSuccess(false);
      json.setMsg("1v1课程不允许删除");
      // 删除的是1vN课程
    } else {
      // 查看此课是否被排课
      boolean canDelete = true;
      List<CourseOne2ManyScheduling> schedulingList = adminCourseOne2ManySchedulingDao
          .findSchedulingByCourseId(keyId);
      // 当前时间是否在排课时间之后 可以删除 否则不可以
      if (schedulingList != null && schedulingList.size() > 0) {
        Date currentDate = new Date();
        // 遍历排课信息
        for (CourseOne2ManyScheduling scheduling : schedulingList) {
          // 当前时间是否在排课时间之后 可以删除 否则不可以
          if (currentDate.getTime() < scheduling.getEndTime().getTime()) {
            canDelete = false;
            break;
          }
        }
      }
      if (canDelete) {
        adminCourseOne2ManyDao.delete(keyId, updateUserId);
      } else {
        json.setSuccess(false);
        json.setMsg("该节课已经被排入课表，无法删除");
      }
    }
    return json;
  }

  /**
   * Title: 新增课程<br>
   * Description: 新增课程<br>
   * CreateDate: 2016年4月13日 下午5:35:39<br>
   * 
   * @category 新增课程
   * @author seven.gz
   * @param course
   * @return
   * @throws Exception
   */
  @Transactional
  public JsonMessage addCourse(AdminCourse course, MultipartFile courseCoursewareFile,
      MultipartFile coursePicFile)
          throws Exception {
    JsonMessage json = new JsonMessage();
    course.setKeyId(SqlUtil.createUUID());
    // 上传文件
    uploadfile(course, courseCoursewareFile, coursePicFile, false);
    // 说明是1v1课程
    if ("course_type1".equals(course.getCourseType())
        || "course_type9".equals(course.getCourseType())) {
      // AdminCourseOne2one courseOne2one = transformToOne2One(course);
      // try {
      // int sc = adminCourseOne2oneDao.insert(courseOne2one);
      // if(1 == sc){
      // json.setSuccess(true);
      // json.setMsg("添加成功");
      // } else {
      // json.setSuccess(true);
      // json.setMsg("添加失败");
      // }
      // } catch (Exception e) {
      // logger.error("添加1v1课程出错" + " :"+ e);
      // json.setSuccess(false);
      // json.setMsg("添加1v1课程出错");
      // }
      json.setSuccess(false);
      json.setMsg("1v1课程不允许添加");
      // 说明是1vN课程
    } else {
      CourseOne2Many courseOne2Many = transformToOne2Many(course);
      adminCourseOne2ManyDao.insert(courseOne2Many);
    }

    // 如果课件上传成功，需要异步调用vcube设置document_id
    sendVcubeFileMessageQueue(course, courseCoursewareFile);

    return json;
  }

  /**
   * 
   * Title: 修改课程<br>
   * Description: 修改课程<br>
   * CreateDate: 2016年4月13日 下午5:35:39<br>
   * 
   * @category 修改课程
   * @author seven.gz
   * @param course
   * @return
   * @throws Exception
   */
  @Transactional
  public void modifyCourse(AdminCourse course, MultipartFile courseCoursewareFile,
      MultipartFile coursePicFile)
          throws Exception {
    List<String> courseIds = new ArrayList<String>();
    // 上传文件
    uploadfile(course, courseCoursewareFile, coursePicFile, true);

    try {
      if ("course_type1".equals(course.getCourseType())
          || "course_type4".equals(course.getCourseType())) {
        // 更新1v1课程
        CourseOne2One courseOne2One = transformToOne2One(course);
        adminCourseOne2oneDao.update(courseOne2One);
        courseIds.add(courseOne2One.getKeyId());

      } else if ("course_type9".equals(course.getCourseType())) {
        // 更新1v1课程
        CourseExtension1v1 courseExtension1v1 = transformToExtension1v1(course);
        courseExtension1v1Dao.update(courseExtension1v1);
        courseIds.add(courseExtension1v1.getKeyId());

      } else {
        CourseOne2Many courseOne2Many = transformToOne2Many(course);
        // 更新1vn课程
        adminCourseOne2ManyDao.update(courseOne2Many);
        /**
         * modified by komi 2017年1月6日10:09:16
         * 修复bug491，因为ES类型的课程不需要等级，更新排课表是写的sql里一定要更新级别，所以级别不能为NULL
         */
        if (StringUtils.isEmpty(courseOne2Many.getCourseLevel())) {
          // 为null，则设置为空字符串
          courseOne2Many.setCourseLevel("");
        }
        // 级联更新scheduling 表中数据
        adminCourseOne2ManySchedulingDao.updatByCourse(courseOne2Many);
        // 更新预约表中的课程信息
        // 根据课程id查找大课排课信息
        List<CourseOne2ManyScheduling> courseOne2ManySchedulings = adminCourseOne2ManySchedulingDao
            .findSchedulingByCourseId(course.getKeyId());
        if (courseOne2ManySchedulings != null && courseOne2ManySchedulings.size() > 0) {
          for (CourseOne2ManyScheduling scheduling : courseOne2ManySchedulings) {
            courseIds.add(scheduling.getKeyId());
          }
        }
      }

      if (courseIds.size() > 0) {
        // 级联更新预约表中的课程信息
        // modify by seven 级联更新需要加上course_type 因为core1v1 和 extension 1v1 key_id
        // 一样
        course.setCourseIds(courseIds);
        adminSubscribeCourseDao.updateCourseInfo(course);
      }

      // 如果课件上传成功，需要异步调用vcube设置document_id
      sendVcubeFileMessageQueue(course, courseCoursewareFile);

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error:" + e.getMessage(), e);
      // 调用消息队列删除已上传的文件
      sendDeleteFileMessageQueue(course, courseCoursewareFile, coursePicFile);
      throw new RuntimeException("保存数据失败");
    }
  }

  /**
   * Title: 异步生产vcube上传文件消息<br>
   * Description: sendVcubeFileMessageQueue<br>
   * CreateDate: 2016年5月24日 下午10:01:21<br>
   * 
   * @category 异步生产vcube上传文件消息
   * @author seven.gz
   * @param course
   * @param courseCoursewareFile
   * @throws Exception
   */
  private void sendVcubeFileMessageQueue(AdminCourse course, MultipartFile courseCoursewareFile)
      throws Exception {
    if (courseCoursewareFile != null) {
      String onsBody = course.getCourseType() + "," + course.getCourseCourseware() + ","
          + course.getKeyId();
          // 异步上传vcube的文件,无论是新增还是修改

      // modified by komi 2016年7月11日18:28:25 es课程使用展示互动
      if ("course_type8".equals(course.getCourseType())
          || "course_type5".equals(course.getCourseType())) {
        // 1是上传课件
        /**
         * modify by seven 2017年8月23日15:21:31 换掉展示互动后就不要上传图片了
         */
        String ifUpload = MemcachedUtil.getConfigValue(ConfigConstant.GENSEE_NEED_UPLOAD_FILE);
        if (Boolean.valueOf(ifUpload)) {
          OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
              MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_gensee",
              CourseConstant.GENSEESENDFILE + "," + onsBody + "," + course.getCourseTitle());
        }
      } else {
        // modified by alex+komi+seven 2016年8月3日 14:19:47 因为不再使用vcube了
        // OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
        // MemcachedUtil.getConfigValue("aliyun_ons_topicid"),
        // "aliyun_ons_consumerid_vcube_upload", onsBody);
      }

    }
  }

  /**
   * Title: 更新展示互动绑定课件<br>
   * Description: 更新展示互动绑定课件<br>
   * CreateDate: 2016年7月21日 下午5:22:28<br>
   * 
   * @category 更新展示互动绑定课件
   * @author komi.zsy
   * @param courseId
   * @param documentId
   * @throws Exception
   */
  public void updateGenseeAttachFile(String courseId, String documentId) throws Exception {
    List<Map<String, Object>> roomIdList = courseOne2ManySchedulingDao
        .findGenseeRoomIdListByCourseId(courseId);

    if (roomIdList != null && roomIdList.size() != 0) {
      for (Map<String, Object> roomId : roomIdList) {
        // 绑定课件，type，房间号，课件id
        OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
            MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_gensee",
            CourseConstant.GENSEEATTACHFILE + "," + roomId.get("room_id") + "," + documentId);
      }
    }
  }

  /**
   * Title: 异步生产删除OSS文件消息<br>
   * Description: sendDeleteFileMessageQueue<br>
   * CreateDate: 2016年5月24日 下午10:01:51<br>
   * 
   * @category 异步生产删除OSS文件消息
   * @author seven.gz
   * @param course
   * @param courseCoursewareFile
   * @param coursePicFile
   * @throws Exception
   */
  private void sendDeleteFileMessageQueue(AdminCourse course, MultipartFile courseCoursewareFile,
      MultipartFile coursePicFile) throws Exception {
    String onsBody = AdminCourseConstant.ALIYUN_ONS_CONSUMERID_OSS_ONSBODY_TYPE_DELETE + ",";
    if (courseCoursewareFile != null && !StringUtils.isEmpty(course.getCourseCourseware())) {
      OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
          MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_oss",
          onsBody + course.getCourseCourseware());
    }
    if (coursePicFile != null && !StringUtils.isEmpty(course.getCoursePic())) {
      OnsProducerClient.sendMsg(MemcachedUtil.getConfigValue("aliyun_ons_producerid"),
          MemcachedUtil.getConfigValue("aliyun_ons_topicid"), "aliyun_ons_consumerid_oss",
          onsBody + course.getCoursePic());
    }
  }

  /**
   * 
   * Title: 根据AdminCourseAll获得AdminCourseOne2one<br>
   * Description: 根据AdminCourseAll获得AdminCourseOne2one<br>
   * CreateDate: 2016年4月13日 下午5:42:50<br>
   * 
   * @category 根据AdminCourseAll获得AdminCourseOne2one
   * @author seven.gz
   * @param course
   * @return
   */
  private CourseOne2One transformToOne2One(AdminCourse course) {
    CourseOne2One courseOne2one = new CourseOne2One();
    if (course.getCourseTitle() != null) {
      course.setCourseTitle(course.getCourseTitle().trim());
    }
    // 属性复制
    BeanUtils.copyProperties(course, courseOne2one);
    return courseOne2one;
  }

  /**
   * 
   * Title: 根据AdminCourseAll获得AdminCourseOne2one<br>
   * Description: 根据AdminCourseAll获得AdminCourseOne2one<br>
   * CreateDate: 2016年4月13日 下午5:42:50<br>
   * 
   * @category 根据AdminCourseAll获得AdminCourseOne2one
   * @author seven.gz
   * @param course
   * @return
   */
  private CourseExtension1v1 transformToExtension1v1(AdminCourse course) {
    CourseExtension1v1 courseExtension1v1 = new CourseExtension1v1();
    if (course.getCourseTitle() != null) {
      course.setCourseTitle(course.getCourseTitle().trim());
    }
    // 属性复制
    BeanUtils.copyProperties(course, courseExtension1v1);
    return courseExtension1v1;
  }

  /**
   * 
   * Title: 根据AdminCourseAll获得AdminCourseOne2many<br>
   * Description: 根据AdminCourseAll获得AdminCourseOne2many<br>
   * CreateDate: 2016年4月13日 下午5:42:50<br>
   * 
   * @category 根据AdminCourseAll获得AdminCourseOne2many
   * @author seven.gz
   * @param course
   * @return
   */
  private CourseOne2Many transformToOne2Many(AdminCourse course) {
    CourseOne2Many courseOne2Many = new CourseOne2Many();
    if (course.getCourseTitle() != null) {
      course.setCourseTitle(course.getCourseTitle().trim());
    }
    // 属性复制
    BeanUtils.copyProperties(course, courseOne2Many);
    return courseOne2Many;
  }

  /**
   * Title: 课程文件上传<br>
   * Description: uploadfile<br>
   * CreateDate: 2016年5月24日 下午9:30:53<br>
   * 
   * @category 课程文件上传
   * @author seven.gz
   * @param course
   * @param courseCoursewareFile
   * @param coursePicFile
   * @param isModified
   *          是否为更新操作
   * @throws Exception
   */
  public void uploadfile(AdminCourse course, MultipartFile courseCoursewareFile,
      MultipartFile coursePicFile,
      boolean isModified) throws Exception {
    String aliyunPathImage = null;
    String aliyunPathCourse = null;

    if ("course_type1".equals(course.getCourseType())) {
      aliyunPathImage = AdminCourseConstant.ALIYUN_IMAGE_PATH_IMAGES_ONE2ONE
          + course.getCourseLevel() + "/";
      aliyunPathCourse = AdminCourseConstant.ALIYUN_IMAGE_PATH_COURSE_ONE2ONE
          + course.getCourseLevel() + "/";
    } else if ("course_type9".equals(course.getCourseType())) {
      aliyunPathImage = AdminCourseConstant.ALIYUN_IMAGE_PATH_IMAGES_EXTENSION_ONE2ONE
          + course.getCourseLevel() + "/";
      aliyunPathCourse = AdminCourseConstant.ALIYUN_IMAGE_PATH_COURSE_EXTENSION_ONE2ONE
          + course.getCourseLevel() + "/";
    } else if ("course_type4".equals(course.getCourseType())) {
      aliyunPathImage = AdminCourseConstant.ALIYUN_IMAGE_PATH_IMAGES_DEMO_ONE2ONE;
      aliyunPathCourse = AdminCourseConstant.ALIYUN_IMAGE_PATH_COURSE_DEMO_ONE2ONE;
    } else {
      aliyunPathImage = AdminCourseConstant.ALIYUN_IMAGE_PATH_IMAGES_ONE2MANY
          + course.getCourseType() + "/";
      aliyunPathCourse = AdminCourseConstant.ALIYUN_IMAGE_PATH_COURSE_ONE2MANY
          + course.getCourseType() + "/";
    }

    // 文件存在oss上的id
    String imageId = SqlUtil.createUUID();
    String courseId = SqlUtil.createUUID();

    // 上传课程图片
    if (coursePicFile != null) {
      String coursePicUrl = OSSClientUtil.uploadFile(coursePicFile, imageId, aliyunPathImage);
      if (StringUtils.isEmpty(coursePicUrl)) {
        throw new RuntimeException("oss文件上传失败!");
      }

      // 如果是修改则需要跑一个异步的消息队列去删除文件,删除老的
      if (isModified) {
        sendDeleteFileMessageQueue(course, null, coursePicFile);
      }
      // 设置新的
      course.setCoursePic(coursePicUrl);
    }

    // 上传课程课件
    if (courseCoursewareFile != null) {
      String courseCoursewareUrl = OSSClientUtil.uploadFile(courseCoursewareFile, courseId,
          aliyunPathCourse);
      if (StringUtils.isEmpty(courseCoursewareUrl)) {
        throw new RuntimeException("oss文件上传失败!");
      }

      // 如果是修改则需要跑一个异步的消息队列去删除文件,删除老的
      if (isModified) {
        sendDeleteFileMessageQueue(course, courseCoursewareFile, null);
      }
      // 设置新的
      course.setCourseCourseware(courseCoursewareUrl);
    }

  }

  /**
   * 
   * Title: 查询noshow课程信息<br>
   * Description: 查询noshow课程信息,为了组合查询用回之前的dao<br>
   * CreateDate: 2016年7月4日 下午2:33:11<br>
   * 
   * @category 查询noshow课程信息
   * @author seven.gz
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Page findNoShowCoursePage(Map<String, Object> paramMap, String learningCoachId)
      throws Exception {
    // Page page = adminSubscribeCourseDao.findNoShowCoursePage(new
    // Date(),paramMap,learningCoachId);
    Page page = subscribeCourseDao.findNoShowCoursePage(new Date(), paramMap, learningCoachId);
    // 将课程类型格式化为 中文解释
    if (page != null) {
      List<Map<String, Object>> datas = page.getDatas();
      if (datas != null && datas.size() > 0) {
        for (Map<String, Object> element : datas) {
          if (!StringUtils.isEmpty(element.get("course_type"))) {
            element.put("course_type",
                ((CourseType) MemcachedUtil.getValue((String) element.get("course_type")))
                    .getCourseTypeChineseName());
          }
        }
      }
    }
    return page;
  }
}