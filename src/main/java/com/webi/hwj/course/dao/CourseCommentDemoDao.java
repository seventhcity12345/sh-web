package com.webi.hwj.course.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mingyisoft.javabase.base.dao.BaseEntityDao;
import com.webi.hwj.course.entity.CourseCommentDemo;

@Repository
public class CourseCommentDemoDao extends BaseEntityDao<CourseCommentDemo> {
  private static Logger logger = Logger.getLogger(CourseCommentDemoDao.class);

  /**
   * Title: 查询demo评论<br>
   * Description: findOneBySubscribeIdAndFromUserId<br>
   * CreateDate: 2017年6月27日 下午3:28:56<br>
   * 
   * @category 查询demo评论
   * @author seven.gz
   * @param subscribeCourseId
   *          预约id
   * @param fromUserId
   *          评论人id
   */
  public CourseCommentDemo findOneBySubscribeIdAndFromUserId(String subscribeCourseId,
      String fromUserId) throws Exception {
    CourseCommentDemo paramObj = new CourseCommentDemo();
    paramObj.setSubscribeCourseId(subscribeCourseId);
    paramObj.setFromUserId(fromUserId);
    return super.findOne(paramObj,
        "key_id,comprehension_level,pronunciation_level,subscribe_status, "
            + " vocabulary_level,grammer_level,fluency_level,good_points,need_to_improve,progress_plan,update_date");
  }

  /**
   * Title: 查询demo评论<br>
   * Description: findOneBySubscribeIdAndFromUserId<br>
   * CreateDate: 2017年6月27日 下午3:28:56<br>
   * 
   * @category 查询demo评论
   * @author seven.gz
   * @param keyId
   *          主键
   */
  public CourseCommentDemo findOneByKeyId(String keyId) throws Exception {
    CourseCommentDemo paramObj = new CourseCommentDemo();
    paramObj.setKeyId(keyId);
    return super.findOne(paramObj,
        "key_id,comprehension_level,pronunciation_level,subscribe_status, "
            + " vocabulary_level,grammer_level,fluency_level,good_points,need_to_improve,progress_plan,update_date");
  }
}