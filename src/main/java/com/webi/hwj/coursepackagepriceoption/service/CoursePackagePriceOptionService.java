package com.webi.hwj.coursepackagepriceoption.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.webi.hwj.coursepackagepriceoption.entity.CoursePackagePriceOption;
import org.apache.log4j.Logger;
import com.webi.hwj.coursepackagepriceoption.dao.CoursePackagePriceOptionDao;
import java.util.List;

/**
 * @category coursePackagePriceOption控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class CoursePackagePriceOptionService {
  private static Logger logger = Logger.getLogger(CoursePackagePriceOptionService.class);
  @Resource
  CoursePackagePriceOptionDao coursePackagePriceOptionDao;

}