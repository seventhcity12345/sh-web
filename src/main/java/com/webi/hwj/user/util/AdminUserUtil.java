package com.webi.hwj.user.util;

import java.util.Date;
import java.util.List;

import com.webi.hwj.constant.OrderStatusConstant;
import com.webi.hwj.ordercourse.param.FindOrderCourseCountParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseCountParam;
import com.webi.hwj.subscribecourse.param.FindSubscribeSupplementCountParam;
import com.webi.hwj.user.param.GeneralStudentInfoParam;

public class AdminUserUtil {

  /**
   * 
   * Title: 计算标准课时数<br>
   * Description: 计算标准课时数 总课时数/总天数 * 现在天数<br>
   * CreateDate: 2016年5月31日 下午3:02:30<br>
   * 
   * @category 计算标准课时数
   * @author seven.gz
   * @param startOrderTime
   * @param endOrderTime
   * @param totalCount
   *          课程总数
   * @param currentTime
   *          当前时间
   * @return
   */
  public static int calculationStandardCourseCount(Date startOrderTime, Date endOrderTime,
      int totalCount,
      Date currentTime) {
    if (startOrderTime == null || endOrderTime == null || currentTime == null) {
      return 0;
    }
    return totalCount * AdminUserDateUtil.calculationDaysBetweenTwoDate(startOrderTime, currentTime)
        / AdminUserDateUtil.calculationDaysBetweenTwoDate(startOrderTime, endOrderTime);
  }

  /**
   * 
   * Title: 计算课时数（总课时数、剩余课时数）<br>
   * Description: 计算课时数（总课时数、剩余课时数）<br>
   * CreateDate: 2016年6月7日 下午8:39:35<br>
   * 
   * @category 计算课时数（总课时数、剩余课时数）
   * @author seven.gz
   * @param generalStudentInfoParam
   * @param orderCourseCountList
   * @param findSubscribeSupplementCountParams
   * @param subscribeCourseCountList
   */
  public static void calculationCourseCount(GeneralStudentInfoParam generalStudentInfoParam,
      List<FindOrderCourseCountParam> orderCourseCountList,
      List<FindSubscribeSupplementCountParam> findSubscribeSupplementCountParams,
      List<FindSubscribeCourseCountParam> subscribeCourseCountList) {

    // 合同包名称
    String coursePackageName = null;
    // core课程总数
    int coreCourseTotalCount = 0;
    // extension课程总数
    int extensionCourseTotalCount = 0;
    // core出席数
    int coreShowCourseCount = 0;
    // extension出席数
    int extensionShowCourseCount = 0;

    // core剩余课程
    int coreRemainCourseCount = 0;
    // extension剩余课程
    int extensionRemainCourseCount = 0;
    // core补课数
    int coreSupplementCount = 0;
    // extension补课数
    int extensionSupplementCount = 0;

    // 得到各个类型合同中的课时数
    if (orderCourseCountList != null && orderCourseCountList.size() > 0) {
      // 将课程数进行赋值
      for (FindOrderCourseCountParam findOrderCourseCountParam : orderCourseCountList) {
        // 设置合同包名称
        if (coursePackageName == null) {
          coursePackageName = findOrderCourseCountParam.getCoursePackageName();
        }
        // 判断课程类型
        if ("course_type1".equals(findOrderCourseCountParam.getCourseType())
            || "course_type11".equals(findOrderCourseCountParam.getCourseType())) {
          coreCourseTotalCount += findTotalCount(findOrderCourseCountParam);
          coreRemainCourseCount += findOrderCourseCountParam.getRemainCourseCount();
        } else if ("course_type2".equals(findOrderCourseCountParam.getCourseType())
            || "course_type9".equals(findOrderCourseCountParam.getCourseType())) {
          extensionCourseTotalCount += findTotalCount(findOrderCourseCountParam);
          extensionRemainCourseCount += findOrderCourseCountParam.getRemainCourseCount();
        }
      }
    }
    // 得到各个类型消课数
    if (subscribeCourseCountList != null && subscribeCourseCountList.size() > 0) {
      for (FindSubscribeCourseCountParam findSubscribeCourseCountParam : subscribeCourseCountList) {
        // 判断课程类型
        if ("course_type1".equals(findSubscribeCourseCountParam.getCourseType())
            || "course_type11".equals(findSubscribeCourseCountParam.getCourseType())) {
          // 设置1v1课程数
          coreShowCourseCount = findSubscribeCourseCountParam.getShowCourseCount();
        } else if ("course_type2".equals(findSubscribeCourseCountParam.getCourseType())
            || "course_type9".equals(findSubscribeCourseCountParam.getCourseType())) {
          // 设置1vN课程数
          extensionShowCourseCount = findSubscribeCourseCountParam.getShowCourseCount();
        }
      }
    }

    // 得到各个类型补课数
    if (findSubscribeSupplementCountParams != null
        && findSubscribeSupplementCountParams.size() > 0) {
      for (FindSubscribeSupplementCountParam findSubscribeSupplementCountParam : findSubscribeSupplementCountParams) {
        if ("course_type1".equals(findSubscribeSupplementCountParam.getCourseType())
            || "course_type11".equals(findSubscribeSupplementCountParam.getCourseType())) {
          coreSupplementCount = findSubscribeSupplementCountParam.getCourseCount();
        } else if ("course_type2".equals(findSubscribeSupplementCountParam.getCourseType())
            || "course_type9".equals(findSubscribeSupplementCountParam.getCourseType())) {
          extensionSupplementCount = findSubscribeSupplementCountParam.getCourseCount();
        }
      }
    }
    generalStudentInfoParam.setCoursePackageName(coursePackageName);
    // 总课时数需要加上补课数
    generalStudentInfoParam
        .setExtensionCourseCount(extensionCourseTotalCount + extensionSupplementCount);
    generalStudentInfoParam.setCoreCourseCount(coreCourseTotalCount + coreSupplementCount);
    generalStudentInfoParam.setExtensionRemainCourseCount(extensionRemainCourseCount);
    generalStudentInfoParam.setCoreRemainCourseCount(coreRemainCourseCount);
    generalStudentInfoParam.setCoreShowCourseCount(coreShowCourseCount);
    generalStudentInfoParam.setExtensionShowCourseCount(extensionShowCourseCount);
  }

  /**
   * 
   * Title: 计算剩余课时数<br>
   * Description: 总课时数+补课数-已上课数<br>
   * CreateDate: 2016年6月2日 上午11:37:00<br>
   * 
   * @category 计算剩余课时数
   * @author seven.gz
   * @param courseTotalCount
   * @param subscribeCourseCount
   * @param supplementCount
   * @return
   */
  public static int calculationRemianCourseCount(int courseTotalCount, int subscribeCourseCount,
      int supplementCount) {
    return courseTotalCount + supplementCount - subscribeCourseCount;
  }

  /**
   * 
   * Title: 根据同类型获得课程数<br>
   * Description: 根据同类型获得课程数<br>
   * CreateDate: 2016年6月2日 上午11:20:10<br>
   * 
   * @category 根据同类型获得总课程数
   * @author seven.gz
   * @param findOrderCourseCountParam
   * @return
   */
  public static int findTotalCount(FindOrderCourseCountParam findOrderCourseCountParam) {
    // 判断是否是正常合同
    if (OrderStatusConstant.ORDER_ORIGINAL_TYPE_NORMAL
        .equals(findOrderCourseCountParam.getCategoryType())) {
      // 正常合同取CourseCount(总课时数)
      return findOrderCourseCountParam.getCourseCount();
    } else {
      // 续约合同取ShowCourseCount(用于续约的合同展示)
      return findOrderCourseCountParam.getShowCourseCount();
    }
  }

  /**
   * 
   * Title: 向上取整计算百分比<br>
   * Description: 向上取整计算百分比<br>
   * CreateDate: 2016年6月17日 上午11:41:27<br>
   * 
   * @category 向上取整计算百分比
   * @author seven.gz
   * @return
   */
  public static int calculatePercentage(int denominator, int molecule) {
    if (denominator == 0 || molecule == 0) {
      return 0;
    }
    return (int) Math.ceil(((double) denominator * 100) / molecule);
  }
}
