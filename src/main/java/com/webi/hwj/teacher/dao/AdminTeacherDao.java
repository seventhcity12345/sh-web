package com.webi.hwj.teacher.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.teacher.entity.Teacher;
import com.webi.hwj.teacher.param.TeacherParam;

@Repository
public class AdminTeacherDao extends BaseEntityDao<Teacher> {
  private static Logger logger = Logger.getLogger(AdminTeacherDao.class);

  /*
   * 查询教师信息（列）'huanxun'不显示
   */
  private static final String SELECT_TEACHER_INFO_BY_THIRDFROM =
      "SELECT key_id, teacher_name, account, pwd, is_bind_wechat,"
          + " teacher_nationality,teacher_gender,teacher_photo, teacher_course_type, teacher_desc,teacher_contact_content,"
          + " teacher_job_type,third_from , create_date,update_date "
          + " FROM t_teacher"
          + " WHERE third_from !='huanxun' AND is_used <> 0";

  /*
   * 查询教师账号是否存在(无论is_used是否为1)
   */
  private static final String SELECT_TEACHER_IS_EXIST_BY_ACCOUNT =
      "SELECT count(1) FROM t_teacher WHERE "
          + " account = :account";

  private static final String FIND_TEACHER_BY_THRID_FROM = "SELECT key_id,account FROM t_teacher"
      + " WHERE third_from = :thirdFrom AND is_used = 1 ORDER BY account ASC";

  /**
   * Title: 查找third_from不等于huanxun的所有老师信息<br>
   * Description: 查找third_from不等于huanxun的所有老师信息<br>
   * CreateDate: 2016年4月21日 下午3:52:58<br>
   * 
   * @category 查找third_from不等于huanxun的所有老师信息
   * @author komi.zsy
   * @param paramObj
   * @param sort
   * @param order
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(String sort, String order, Integer page, Integer rows)
      throws Exception {
    return super.findPageEasyui(SELECT_TEACHER_INFO_BY_THIRDFROM, new TeacherParam(), sort, order,
        page, rows);
  }

  /**
   * Title: 查询教师账号是否存在(无论is_used是否为1)<br>
   * Description: 查询教师账号是否存在(无论is_used是否为1)<br>
   * CreateDate: 2016年4月21日 下午3:53:33<br>
   * 
   * @category 查询教师账号是否存在(无论is_used是否为1)
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int findCountByAccount(Teacher paramObj) throws Exception {
    return super.findCount(SELECT_TEACHER_IS_EXIST_BY_ACCOUNT, paramObj);
  }

  /**
   * 
   * Title: 根据老师来源查找老师<br>
   * Description: 根据老师来源查找老师<br>
   * CreateDate: 2016年8月25日 下午6:53:57<br>
   * 
   * @category 根据老师来源查找老师
   * @author seven.gz
   * @param thirdFrom
   * @return
   * @throws Exception
   */
  public List<Teacher> findTeacherListByThirdFrom(String thirdFrom) throws Exception {
    Teacher teacher = new Teacher();
    teacher.setThirdFrom(thirdFrom);
    return super.findList(FIND_TEACHER_BY_THRID_FROM, teacher);
  }

  /**
   * 
   * Title: 根据keyid查询<br>
   * Description: findOneByKeyId<br>
   * CreateDate: 2017年9月12日 下午4:17:52<br>
   * 
   * @category 根据keyid查询
   * @author seven.gz
   * @param keyId
   * @return
   * @throws Exception
   */
  public Teacher findOneByKeyId(String keyId) throws Exception {
    return super.findOneByKeyId(keyId, "key_id");
  }
}