package com.webi.hwj.teacher.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.param.FindTeacherByKeyIdsParam;

@Repository
public class TeacherEntityDao extends BaseEntityDao<Teacher> {

  private static final String FIND_TEACHER_BY_KEYIDS =
      " SELECT key_id,third_from FROM t_teacher WHERE is_used = 1 AND key_id IN (:teacherIds) ";
  
  private static final String FIND_TEACHER_PWD = "SELECT "
      + "key_id,pwd FROM t_teacher WHERE account = :account AND is_used = 1 ";

  /**
   * 
   * Title: 根据keyId查询老师<br>
   * Description: 根据keyId查询老师<br>
   * CreateDate: 2016年4月29日 上午10:36:26<br>
   * 
   * @category 根据keyId查询老师
   * @author seven.gz
   * @param KeyId
   * @return
   * @throws Exception
   */
  public Teacher findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId,
        " key_id,teacher_name,teacher_desc,teacher_photo,third_from,teacher_course_type,teacher_job_type ");
  }
  /**
   * Title: 查询教师对象通过account.<br>
   * Description: findTeacherByAccount<br>
   * CreateDate: 2017年2月21日 下午6:33:26<br>
   * 
   * @category 查询教师对象通过account
   * @author yangmh
   * @param account
   *          老师帐号
   */
  public Teacher findTeacherByKeyId(String account) throws Exception {
    return super.findOneByKeyId(account,
        "key_id,teacher_name,account,teacher_nationality,teacher_gender,teacher_photo,"
            + "teacher_desc,teacher_course_type,teacher_contact_content,teacher_job_type,"
            + "third_from");
  }  /**
   * Title: 根据教师账号查找keyid<br>
   * Description: 根据教师账号查找keyid<br>
   * CreateDate: 2017年6月15日 下午4:59:34<br>
   * @category 根据教师账号查找keyid 
   * @author komi.zsy
   * @param account 教师账号
   * @return
   * @throws Exception
   */
  public Teacher findTeacherByAccount(String account)
      throws Exception {
    Teacher teacher = new Teacher();
    teacher.setAccount(account);
    return super.findOne(teacher, "key_id");
  }
  /**
   * 
   * Title: 根据老师ids查询老师<br>
   * Description: 根据老师ids查询老师<br>
   * CreateDate: 2016年8月1日 上午11:07:18<br>
   * 
   * @category 根据老师ids查询老师
   * @author seven.gz
   * @param teacherIds
   * @return
   * @throws Exception
   */
  public List<FindTeacherByKeyIdsParam> findTeacherByKeyIds(List<String> teacherIds)
      throws Exception {
    FindTeacherByKeyIdsParam findTeacherByKeyIdsParam = new FindTeacherByKeyIdsParam();
    findTeacherByKeyIdsParam.setTeacherIds(teacherIds);
    return super.findListByGen(FIND_TEACHER_BY_KEYIDS, findTeacherByKeyIdsParam);
  }
  
  /**
   * Title: 查询用户密码.<br>
   * Description: 登录专用<br>
   * CreateDate: 2017年2月20日 下午2:47:49<br>
   * 
   * @category 查询用户密码.
   * @author yangmh
   * @param phone
   *          手机号
   */
  public Teacher findOneByAccountReturnPwd(String account) throws Exception {
    Teacher param = new Teacher();
    param.setAccount(account);
    return super.findOne(FIND_TEACHER_PWD, param);
  }
}