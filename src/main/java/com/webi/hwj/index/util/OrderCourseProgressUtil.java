/** 
 * File: 课程进度和合同进度的算法工具类
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.index<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月25日 下午6:13:29
 * @author athrun.cw
 */
package com.webi.hwj.index.util;

import java.util.Date;
import java.util.List;

import com.webi.hwj.ordercourse.entity.OrderCourseOption;
import com.webi.hwj.ordercourse.param.ContractLearningProgressParam;

/**
 * Title: 课程进度和合同进度的算法工具类 Description: ProgressUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月25日 下午6:13:29
 * 
 * @author athrun.cw
 */
public class OrderCourseProgressUtil {

  private static final double ONE_DAY_MILLIS = 86400000;

  /**
   * 
   * Title: 已经开始上课的合同的进度算法 && 期望课程进度 && 实际课程进度<br>
   * Description: startingContrartProgress<br>
   * CreateDate: 2015年8月28日 下午6:24:07<br>
   * 
   * @category startingContrartProgress
   * @author athrun.cw
   * @param startingContract
   * @param option_startingContract
   * @return
   */
  public static ContractLearningProgressParam startingContrartProgress(
      ContractLearningProgressParam startingContract,List<OrderCourseOption> optionStartingContract) {
    if (startingContract != null && optionStartingContract != null && optionStartingContract.size() != 0) {
      // -------------------------------合同进度的计算
      // 合同截止日期
      Date orderEndTime = startingContract.getEndOrderTime();
      // 合同开始上课时间
      double startOrderTime = startingContract.getStartOrderTime().getTime();
      // 合同结束时间
      double endOrderTime = startingContract.getEndOrderTime().getTime();
      // 今天日期
      double nowTime = System.currentTimeMillis();
      // 当前与开始时间间隔的毫秒数
      double nowdiffer = nowTime - startOrderTime;
      // 结束时间与开始时间间隔毫秒数
      double enddiffer = endOrderTime - startOrderTime;
      // 当前与开始时间间隔的天数
      double startNowDay = Math.ceil(nowdiffer / ONE_DAY_MILLIS);
      // 结束时间与开始时间间天数
      double startEndDay = enddiffer / ONE_DAY_MILLIS;
      // -------------------------------合同进度的计算

      // -------------------------------课程进度的计算
      // 合同总课时
      int totalCourseCount = 0;

      /**
       * modified by komi 2016年9月12日18:12:53 剩余课时数直接用remain_course_count
       */
      int remainCourseCount = 0;

      /**
       * 将查询出来的已经上过的课程，才定为 已消耗课程
       */
      for (OrderCourseOption option : optionStartingContract) {
        /**
         * modified by komi 2016年7月5日14:39:16
         * 修改为按课程单位类型判定，course_unit_type为0是节，需要算入剩余课时 modified by komi
         * 2016年9月12日18:39:25 修改为只计算core1v1+core1v6+ext1v1+ext1v6课程
         */
        Integer courseUnitType = option.getCourseUnitType();
        String courseType = option.getCourseType();

        if (("course_type1".equals(courseType) || "course_type11".equals(courseType)
            || "course_type2".equals(courseType) || "course_type9".equals(courseType)) && courseUnitType == 0) {

          totalCourseCount += option.getShowCourseCount();

          /**
           * modified by komi 2016年9月12日18:12:53 剩余课时数直接用remain_course_count
           */
          remainCourseCount += option.getRemainCourseCount();
        }
      }

      // 一天毫秒数 24*60*60*1000 = 86400000
      // 结束时间与开始时间间隔天数 enddiffer / 86400000
      // 该合同平均每天的期望课时数(基值1)
      // 平均每天的期望课时数=总课程数/合同有效期天数
      double expectEverageCourse = totalCourseCount / startEndDay;

      /**
       * modify by athrun.cw 2016年5月25日16:58:08 362 拟定仅有RSA课程的合同学员登录生产环境前台报错
       * 总课数为0时（没有1v1、大课、小包课），作为分母会报错
       */

      // 该合同平均每天的期望课程进度(基值2)
      double expectEverageProgress = 0;
      /**
       * 或者期望的课程进度，应该与合同进度保持一致，直接使用当前日期 / 合同总日期，就是了
       */
      // 平均每天的期望进度=平均每天的期望课时数/总课程数
      if (totalCourseCount != 0) {
        expectEverageProgress = expectEverageCourse / totalCourseCount;
      }

      startingContract.setRemainCourseCount(remainCourseCount);
      // -------------------------------课程进度的计算
      if (startNowDay <= startEndDay) {// 没有超过合同结束日期
        // 合同截止日期
        startingContract.setEndOrderTime(orderEndTime);
        // 课程期望进度 基值2 * 已经经过的天数 && 转换为向上取整百分比整数
        int expectCourseProgress = (int)Math.ceil(expectEverageProgress * startNowDay * 100);
        startingContract.setExpectCourseProgress(expectCourseProgress);
        /**
         * modify by athrun.cw 2016年5月25日16:58:08 362 拟定仅有RSA课程的合同学员登录生产环境前台报错
         */
        int courseProgress = 0;
        if (totalCourseCount != 0) {
          // 课程真实进度=已上过的课程（预约表中count）/总课程数， 取最接近百分比整数
          int noUsedCount = totalCourseCount-remainCourseCount;
          if(noUsedCount < 0){
            noUsedCount = 0;
          }
          courseProgress = Math.round(noUsedCount * 100.00f / totalCourseCount);
          startingContract.setCourseProgress(courseProgress);
        } else {
          /**
           * modify by athrun.cw 2015年12月18日10:23:18 2016年5月25日17:03:02
           * greg说和期望进度一致
           */
          courseProgress = expectCourseProgress;
          /**
           * modified by komi 2016年6月23日11:48:28 把进度率（实际进度/期望进度），改为显示实际进度
           */
          startingContract.setCourseProgress(courseProgress);
        }
      } else {// 超过了合同结束结束日期,为100（在正常的业务逻辑中，一旦出现该情况，则认为该合同已经失效，定时器任务？已经置is_student
              // = 0
        //剩余课时
        startingContract.setRemainCourseCount(0);
        // 课程期望进度
        startingContract.setExpectCourseProgress(100);
        // 课程真实进度
        startingContract.setCourseProgress(100);
        // 合同结束时间
        startingContract.setEndOrderTime(orderEndTime);
      }
    }
    return startingContract;
  }

  public static void main(String[] args) {
    System.out.println(24 * 60 * 60 * 1000);
  }
}
