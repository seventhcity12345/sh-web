/** 
 * File: PayLogService.java<br> 
 * Project: speakhi<br> 
 * Package: com.webi.hwj.alipay.service<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年12月10日 下午3:39:59
 * @author yangmh
 */
package com.webi.hwj.alipay.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.alipay.dao.PayLogDao;

/**
 * Title: PayLogService<br>
 * Description: PayLogService<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年12月10日 下午3:39:59
 * 
 * @author yangmh
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PayLogService {
  @Resource
  PayLogDao payLogDao;

  /**
   * @category courseComment 插入
   * @author mingyisoft代码生成工具
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return payLogDao.insert(fields);
  }
}
