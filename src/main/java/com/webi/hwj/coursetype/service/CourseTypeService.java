package com.webi.hwj.coursetype.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.webi.hwj.coursetype.entity.CourseType;
import org.apache.log4j.Logger;
import com.webi.hwj.coursetype.dao.CourseTypeDao;
import java.util.List;

/**
 * @category courseType控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class CourseTypeService {
  private static Logger logger = Logger.getLogger(CourseTypeService.class);
  @Resource
  CourseTypeDao courseTypeDao;

  /**
   * Title: 查询列表<br>
   * Description: 查询列表<br>
   * CreateDate: 2016年8月24日 上午11:21:15<br>
   * 
   * @category 查询列表
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<CourseType> findList() throws Exception {
    return courseTypeDao.findList();
  }

  /**
   * Title: 根据条件查询列表<br>
   * Description: 根据条件查询列表<br>
   * CreateDate: 2016年8月29日 上午10:02:00<br>
   * 
   * @category 根据条件查询列表
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public List<CourseType> findListByParam(CourseType paramObj) throws Exception {
    return courseTypeDao.findListByParam(paramObj);
  }
}