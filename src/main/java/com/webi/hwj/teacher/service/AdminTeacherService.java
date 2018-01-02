package com.webi.hwj.teacher.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mingyisoft.javabase.aliyun.ocs.OSSClientUtil;
import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SqlUtil;
import com.webi.hwj.constant.ConfigConstant;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.courseone2many.dao.AdminCourseOne2ManySchedulingDao;
import com.webi.hwj.courseone2many.entity.CourseOne2ManyScheduling;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.subscribecourse.dao.SubscribeCourseDao;
import com.webi.hwj.teacher.constant.AdminTeacherConstant;
import com.webi.hwj.teacher.dao.AdminTeacherDao;
import com.webi.hwj.teacher.dao.TeacherTimeEntityDao;
import com.webi.hwj.teacher.dao.TeacherTimeSignDao;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.entity.TeacherTime;
import com.webi.hwj.teacher.entity.TeacherTimeSign;
import com.webi.hwj.teacher.param.TeacherParam;

/**
 * @category teacher控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminTeacherService {
  private static Logger logger = Logger.getLogger(AdminTeacherService.class);
  @Resource
  private AdminTeacherDao adminTeacherDao;

  @Resource
  private SubscribeCourseDao subscribeCourseDao;

  @Resource
  private AdminCourseOne2ManySchedulingDao adminCourseOne2ManySchedulingDao;

  @Resource
  private TeacherTimeSignDao teacherTimeSignDao;

  @Resource
  private TeacherTimeEntityDao teacherTimeEntityDao;

  /**
   * Title: 查询所有老师信息<br>
   * Description: 查询所有老师信息<br>
   * CreateDate: 2016年4月11日 下午6:08:28<br>
   * 
   * @category 查询所有老师信息
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public Page findPageEasyuiTeachers(String sort, String order, Integer pages, Integer rows)
      throws Exception {
    Page page = adminTeacherDao.findPageEasyui(sort, order, pages, rows);

    for (int i = 0; i < page.getDatas().size(); i++) {
      if (!StringUtils.isEmpty(((TeacherParam) page.getDatas().get(i)).getTeacherCourseType())) {
        ((TeacherParam) page.getDatas().get(i))
            .setTeacherCourseTypeId(((TeacherParam) page.getDatas().get(i)).getTeacherCourseType());
        String[] types = ((TeacherParam) page.getDatas().get(i)).getTeacherCourseType().split(",");
        // 将课程类型从数据库type字段转换成用户显示类型
        StringBuffer result = new StringBuffer();
        for (String type : types) {
          result
              .append(((CourseType) MemcachedUtil.getValue(type)).getCourseTypeChineseName() + ",");
        }

        result.deleteCharAt(result.length() - 1);
        ((TeacherParam) page.getDatas().get(i)).setTeacherCourseType(result.toString());
      }
      logger.debug("获取老师------>" + page.getDatas().get(i));
    }

    return page;
  }

  /**
   * 
   * Title: 修改教師信息<br>
   * Description: 修改教師信息<br>
   * CreateDate: 2016年4月11日 下午6:08:38<br>
   * 
   * @category 修改教師信息
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage editTeacher(Teacher paramObj, MultipartFile teacherPic) throws Exception {
    JsonMessage json = new JsonMessage(true, "修改成功！");

    String teacherId = paramObj.getKeyId();
    String teacherName = paramObj.getTeacherName().trim();

    // 页面中 上传图片时候input 的name属性values
    String fieldName = AdminTeacherConstant.UPLOAD_FIELD_NAME;

    // aliyun上保存图片的路径 images/user/phoneNumber
    String aliyunPath = AdminTeacherConstant.ALIYUN_IMAGE_PATH;
    // String imagePaths = OSSClientUtil.uploadPhoto(request, fieldName,
    // aliyunPath);
    String imagePaths = OSSClientUtil.uploadFile(teacherPic, SqlUtil.createUUID(), aliyunPath);

    // 原来照片路径
    String currentPhoto = paramObj.getTeacherPhoto();

    if (StringUtils.isEmpty(imagePaths)) {
      paramObj.setTeacherPhoto(null);
      json.setMsg("修改成功！(无新图片上传)");
    } else {
      // 处理返回字符串[user_photo_large, user_photo]
      // String[] images = imagePaths.split(",");
      // 大图片的存储路径
      // String user_photo_large = images[0];
      // 小图片的存储路径
      // String user_photo = images[1];

      paramObj.setTeacherPhoto(imagePaths);

      logger.debug("修改图片成功---开始删除原来图片---->" + currentPhoto);
      try {
        String finalOssPath = currentPhoto
            .replace(MemcachedUtil.getConfigValue("aliyun_oss_returnurl"), "");
        OSSClientUtil.deleteFile(finalOssPath);
      } catch (Exception e) {
        logger.error("删除教师原来图片失败------>" + e.toString());
      }

    }

    logger.debug("开始修改教师信息数据库！");
    adminTeacherDao.update(paramObj);
    logger.debug("修改教师信息数据库完毕！");

    logger.debug("开始级联修改预约表老师名字！");
    Map<String, Object> teacherParamMap = new HashMap<String, Object>();
    teacherParamMap.put("teacher_id", teacherId);
    teacherParamMap.put("teacher_name", teacherName);
    subscribeCourseDao.updateTeacherNameByTeacherId(teacherParamMap);
    logger.debug("级联修改预约表老师名字完毕！");

    logger.debug("开始级联修改排课表老师名字和老师头像！");
    CourseOne2ManyScheduling courseOne2ManyScheduling = new CourseOne2ManyScheduling();
    courseOne2ManyScheduling.setTeacherId(teacherId);
    courseOne2ManyScheduling.setTeacherName(teacherName);
    if (paramObj.getTeacherPhoto() == null) {
      courseOne2ManyScheduling.setTeacherPhoto(currentPhoto);
    } else {
      courseOne2ManyScheduling.setTeacherPhoto(paramObj.getTeacherPhoto());
    }
    adminCourseOne2ManySchedulingDao.updateTeacherInfoByTeacherId(courseOne2ManyScheduling);
    logger.debug("级联修改排课表老师名字和老师头像完毕！");

    logger.debug("开始修改教师签课表老师名字和老师上课类型！");
    TeacherTimeSign teacherTimeSign = new TeacherTimeSign();
    teacherTimeSign.setTeacherId(teacherId);
    teacherTimeSign.setTeacherName(teacherName);
    teacherTimeSign.setTeacherCourseType(paramObj.getTeacherCourseType());
    teacherTimeSignDao.updateTeacherInfoByTeacherId(teacherTimeSign);
    logger.debug("修改教师签课表老师名字和老师上课类型完毕！");

    logger.debug("开始修改教师时间表老师名字！");
    // 级联更新t_teacher_time里的老师名称
    TeacherTime teacherTimeParam = new TeacherTime();
    teacherTimeParam.setTeacherId(teacherId);
    teacherTimeParam.setTeacherName(teacherName);
    teacherTimeEntityDao.updateTeacherNameByTeacherId(teacherTimeParam);
    logger.debug("修改教师时间表老师名字完毕！");

    return json;
  }

  /**
   * Title: 删除教师信息（可单可批量）<br>
   * Description: 删除教师信息（可单可批量）<br>
   * CreateDate: 2016年4月11日 下午6:31:31<br>
   * 
   * @category 删除教师信息（可单可批量）
   * @author komi.zsy
   * @param paramMap
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public int batchDeleteTeacher(Map<String, Object> paramMap) throws Exception {
    // 获得后台传递的keys
    String keys = paramMap.get("keys").toString();
    if (keys == null && "".equals(keys)) {
      logger.error("后台传递的参数有误！");
      throw new RuntimeException("后台传递的参数有误！");
    }

    String[] keyIds = keys.split(",");
    List<String> returnList = Arrays.asList(keyIds);

    return adminTeacherDao.delete(returnList);
  }

  /**
   * Title: 新增数据<br>
   * Description: insert<br>
   * CreateDate: 2016年4月11日 上午8:57:05<br>
   * 
   * @category 新增数据
   * @author yangmh
   * @param paramObj
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public JsonMessage insert(Teacher paramObj, MultipartFile teacherPic) throws Exception {
    JsonMessage json = new JsonMessage(true, "新增成功！");
    if (adminTeacherDao.findCountByAccount(paramObj) > 0) {
      json.setMsg("账号已存在！");
      json.setSuccess(false);
      return json;
    }

    // aliyun上保存图片的路径 images/user/phoneNumber
    String aliyunPath = AdminTeacherConstant.ALIYUN_IMAGE_PATH;
    // String imagePaths = OSSClientUtil.uploadPhoto(request, fieldName,
    // aliyunPath);
    String imagePaths = OSSClientUtil.uploadFile(teacherPic, SqlUtil.createUUID(), aliyunPath);

    if (StringUtils.isEmpty(imagePaths)) {
      json.setSuccess(false);
      json.setMsg("上传图像失败！");
      logger.error("新增教师信息，上传图像失败！");
      return json;
    }

    // 处理返回字符串[user_photo_large, user_photo]
    // String[] images = imagePaths.split(",");
    // 大图片的存储路径
    // String user_photo_large = images[0];
    // 小图片的存储路径
    // String user_photo = images[1];

    paramObj.setTeacherPhoto(imagePaths);

    paramObj.setTeacherName(paramObj.getTeacherName().trim());
    paramObj.setAccount(paramObj.getAccount().trim());

    logger.debug("开始插入教师信息数据库！");
    adminTeacherDao.insert(paramObj);
    logger.debug("插入教师信息数据库完毕！");
    return json;
  }

  /**
   * Title: 批量新增数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:04:36<br>
   * 
   * @category 批量新增数据
   * @author yangmh
   * @param paramObj
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchInsert(List<Teacher> paramObjList) throws Exception {
    return adminTeacherDao.batchInsert(paramObjList);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:00<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(String keyIds) throws Exception {
    return adminTeacherDao.delete(keyIds);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:23<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(List<String> keyIds) throws Exception {
    return adminTeacherDao.delete(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(List<String> keyIds) throws Exception {
    return adminTeacherDao.deleteForReal(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(String keyIds) throws Exception {
    return adminTeacherDao.deleteForReal(keyIds);
  }

  /**
   * Title: 批量修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:46:26<br>
   * 
   * @category 批量修改数据
   * @author yangmh
   * @param paramObjList
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchUpdate(List<Teacher> paramObjList) throws Exception {
    return adminTeacherDao.batchUpdate(paramObjList);
  }

  /**
   * Title: 查询数量数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午1:09:45<br>
   * 
   * @category 查询数量数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return
   * @throws Exception
   */
  public int findCount(Teacher paramObj) throws Exception {
    return adminTeacherDao.findCount(paramObj);
  }

  /**
   * 
   * Title: 根据老师来源查找老师<br>
   * Description: 根据老师来源查找老师<br>
   * CreateDate: 2016年8月25日 下午6:54:35<br>
   * 
   * @category 根据老师来源查找老师
   * @author seven.gz
   * @param thirdFrom
   * @return
   * @throws Exception
   */
  public List<Teacher> findTeacherListByThirdFrom(String thirdFrom) throws Exception {
    return adminTeacherDao.findTeacherListByThirdFrom(thirdFrom);
  }

  /**
   * 
   * Title: 后台登录老师账号<br>
   * Description: adminLogin<br>
   * CreateDate: 2017年9月12日 下午4:25:24<br>
   * 
   * @category adminLogin
   * @author seven.gz
   * @param 后台登录老师账号
   * @return
   * @throws Exception
   */
  public CommonJsonObject<String> adminLogin(String teacherId) throws Exception {

    CommonJsonObject<String> json = new CommonJsonObject<String>();

    Teacher teacher = adminTeacherDao.findOneByKeyId(teacherId);

    if (teacher == null) {
      json.setCode(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode());
      json.setMsg("老师不存在");
      return json;
    }

    String url = MemcachedUtil.getConfigValue(ConfigConstant.CONTRACT_OWNER_URL)
        + "/web/teacher/course_center.html?third="
        + teacherId;

    // session超时时间
    /**
     * modify by seven 2017年9月12日15:55:16 这里只需要id就好了，不知道为什么会放一个对象
     */
    MemcachedUtil.setValue("thirdTeacherLogin_" + teacherId, teacherId, 60 * 60);
    json.setData(url);
    return json;
  }
}