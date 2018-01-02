package com.webi.hwj.ordercourse.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.util.MemcachedUtil;
import com.webi.hwj.coursetype.entity.CourseType;
import com.webi.hwj.ordercourse.constant.OrderCourseConstant;
import com.webi.hwj.ordercourse.param.OrderCourseOptionCountParam;
import com.webi.hwj.ordercourse.param.OrderCourseOptionParam;
import com.webi.hwj.user.entity.User;
import com.webi.hwj.user.entity.UserInfo;
import com.webi.hwj.util.CalendarUtil;

/**
 * 
 * Title: 订单状态与合同状态转换的一个工具类<br>
 * Description: OrderStatusUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月9日 上午10:32:21
 * 
 * @author athrun.cw
 */
public class OrderContractStatusUtil {

  /**
   * 
   * Title: 订单状态与合同状态转换： 1.待确认:(1.已拟定,2.已发送,3.已确认,4.支付中) 2.执行中:(5.已支付)
   * 3.已结束:(6.已过期,7.已中止)<br>
   * Description: transOrderAndContractStatus<br>
   * CreateDate: 2016年3月9日 上午10:47:25<br>
   * 
   * @category 订单状态与合同状态转换：
   * @author athrun.cw
   * @param paramMap
   * @return
   */
  public static Map<String, Object> transOrderAndContractStatus(Map<String, Object> paramMap) {
    // 获取订单状态
    Object order_status = paramMap.get("order_status");
    if (order_status == null || "".equals(order_status) || "null".equals(order_status)) {
      return paramMap;
    }
    // 转换合同状态
    switch (order_status.toString()) {
      case "1":
      case "2":
      case "3":
      case "4":
        paramMap.put("contract_status", 1);
        break;
      case "5":
        paramMap.put("contract_status", 2);
        break;
      case "6":
      case "7":
        paramMap.put("contract_status", 3);
        break;
      default:
        break;
    }
    return paramMap;
  }

  /**
   * 
   * Title: 学生来源(0:常规,1:线下售卖,2:线下转线上)<br>
   * Description: transOrderAndContractStatus<br>
   * CreateDate: 2016年3月10日 下午4:39:46<br>
   * 
   * @category 学生来源(0:常规,1:线下售卖,2:线下转线上)
   * @author athrun.cw
   * @param paramMap
   * @return
   */
  public static Map<String, Object> formatUserFromType(Map<String, Object> paramMap) {
    // 获取订单状态
    Object user_from_type = paramMap.get("user_from_type");
    if (user_from_type == null || "".equals(user_from_type) || "null".equals(user_from_type)) {
      return paramMap;
    }

    // modify by komi 2016年6月1日17:38:22 增加四种学生来源
    // 转换合同状态
    switch (user_from_type.toString()) {
      case "0":
        paramMap.put("format_user_from_type", "常规");
        break;
      case "1":
        paramMap.put("format_user_from_type", "线下售卖");
        break;
      case "2":
        paramMap.put("format_user_from_type", "线下转线上");
        break;
      case "3":
        paramMap.put("format_user_from_type", "团训");
        break;
      case "4":
        paramMap.put("format_user_from_type", "学员推荐");
        break;
      case "5":
        paramMap.put("format_user_from_type", "内部员工");
        break;
      case "6":
        paramMap.put("format_user_from_type", "测试");
        break;
      case "7":
        paramMap.put("format_user_from_type", "VIP学员");
        break;
      case "8":
        paramMap.put("format_user_from_type", "QQ会员活动");
        break;
      case "9":
        paramMap.put("format_user_from_type", "美邦会员活动");
        break;
      default:
        break;
    }
    return paramMap;
  }

  /**
   * Title: 处理合同剩余有效期<br>
   * Description: 处理合同剩余有效期<br>
   * CreateDate: 2016年10月13日 下午2:10:21<br>
   * 
   * @category 处理合同剩余有效期
   * @author komi.zsy
   * @param orderCourseOptionList
   *          用户合同执行中的课程原始数据（sql里有排序，先是is_gift = 0非赠送的课时，再是赠送的课时）
   * @param orderStartTime
   *          合同开始时间
   * @return
   * @throws Exception
   */
  public static void formatRemainCourseCount(List<OrderCourseOptionParam> orderCourseOptionList,
      Date orderStartTime) throws Exception {
    if (orderCourseOptionList != null && orderCourseOptionList.size() != 0) {
      // 用于判断原合同有效期是否用完，用完则开始扣除赠送课程的有效期（Tom要求，先扣除合同原来的课程，再扣除赠送的课程时间）
      Map<String, Calendar> courseTimeMap = new HashMap<String, Calendar>();
      // 处理数据
      for (OrderCourseOptionParam ocp : orderCourseOptionList) {
        // 处理课程名字
        StringBuffer courseTypeChineseName = new StringBuffer();
        courseTypeChineseName.append(
            ((CourseType) MemcachedUtil.getValue(ocp.getCourseType())).getCourseTypeChineseName());
        if (ocp.getIsGift()) {
          courseTypeChineseName.append("(赠)");
        }
        ocp.setCourseTypeChineseName(courseTypeChineseName.toString());

        // 处理剩余课时(课程单位类型（0:节，1:月，2：天）)
        // 按节算的直接使用remainCourseCount，按时效算的需要处理
        if (ocp.getCourseUnitType() != 0) {
          Calendar startCal = Calendar.getInstance();
          Calendar endCal = Calendar.getInstance();

          // 先扣除非赠送的时效，再扣除赠送的时效
          if (courseTimeMap.get(ocp.getCourseType()) != null) {
            // 合同开始时间
            startCal.setTime(courseTimeMap.get(ocp.getCourseType()).getTime());
            // 合同结束时间
            endCal.setTime(courseTimeMap.get(ocp.getCourseType()).getTime());
          } else {
            // 合同开始时间
            startCal.setTime(orderStartTime);
            // 合同结束时间
            endCal.setTime(orderStartTime);
          }

          // 计算有效期结束时间
          if (ocp.getCourseUnitType() == 1) {
            // 按月
            endCal.add(Calendar.MONTH, ocp.getShowCourseCount());
          } else if (ocp.getCourseUnitType() == 2) {
            // 按天
            endCal.add(Calendar.DATE, ocp.getShowCourseCount());
          }

          // 计算剩余天数
          int remainDay = 0;

          // 如果当前日期大于课程开始日期，则开始计算剩余天数，否则只把有效期改为天数即可
          if (startCal.getTimeInMillis() < new Date().getTime()) {
            // 计算剩余天数
            remainDay = CalendarUtil.getMinusDay(endCal.getTime(), new Date());
          } else {
            // 计算课程有效期天数
            remainDay = CalendarUtil.getMinusDay(endCal.getTime(), startCal.getTime());
          }

          // 更新标识map里的课程结束日期，防止有多个相同的赠送课时需要处理
          if (courseTimeMap.get(ocp.getCourseType()) == null) {
            courseTimeMap.put(ocp.getCourseType(), endCal);
          } else {
            if (endCal.getTimeInMillis() > courseTimeMap.get(ocp.getCourseType())
                .getTimeInMillis()) {
              courseTimeMap.put(ocp.getCourseType(), endCal);
            }
          }

          // 给剩余课时数赋值
          if (remainDay < 0) {
            ocp.setRemainCourseCount(0);
          } else {
            ocp.setRemainCourseCount(remainDay);
          }
        }
      }
    }
  }

  /**
   * 
   * Title: 生成给lc发送的邮件<br>
   * Description: createChangeLcMail<br>
   * CreateDate: 2017年1月18日 上午11:45:09<br>
   * 
   * @category 生成给lc发送的邮件
   * @author seven.gz
   * @param user
   * @param userInfo
   * @param ccName
   * @param lcName
   * @return
   */
  public static String createChangeLcMail(User user, UserInfo userInfo, String ccName,
      String lcName) {
    StringBuffer mailBufferString = new StringBuffer();
    mailBufferString.append("Dear All,</br>");
    mailBufferString.append("&nbsp;&nbsp;有以下新学员合同生成/老学员合同变更,请LC团队尽快联系:</br>");
    if (!StringUtils.isEmpty(userInfo.getRealName())) {
      mailBufferString.append("&nbsp;&nbsp;学员姓名:" + userInfo.getRealName());
      if (!StringUtils.isEmpty(userInfo.getEnglishName())) {
        mailBufferString.append("(" + userInfo.getEnglishName() + ")");
      }
      mailBufferString.append("</br>");
    }
    if (!StringUtils.isEmpty(user.getCurrentLevel())) {
      mailBufferString.append("&nbsp;&nbsp;级别:" + user.getCurrentLevel() + "</br>");
    }
    if (!StringUtils.isEmpty(user.getPhone())) {
      mailBufferString.append("&nbsp;&nbsp;(家长)手机号:" + user.getPhone() + "</br>");
    }
    if (!StringUtils.isEmpty(ccName)) {
      mailBufferString.append("&nbsp;&nbsp;合同生成CC:" + ccName + "</br>");
    }
    if (!StringUtils.isEmpty(lcName)) {
      mailBufferString.append("&nbsp;&nbsp;合同指派LC:" + lcName + "</br>");
    }
    mailBufferString.append("</br>&nbsp;&nbsp;任何疑问请找CC当面沟通</br>");
    return mailBufferString.toString();
  }

  /**
   * Title: 处理合同剩余有效期<br>
   * Description: 处理合同剩余有效期,从青少拷贝过来的<br>
   * CreateDate: 2016年10月13日 下午2:10:21<br>
   * 
   * @category 处理合同剩余有效期
   * @author seven
   * @param orderCourseOptionList
   *          用户合同执行中的课程原始数据（sql里有排序，先是is_gift = 0非赠送的课时，再是赠送的课时）
   * @return
   * @throws Exception
   */
  public static List<OrderCourseOptionCountParam> formatRemainCourseCount(
      List<OrderCourseOptionCountParam> orderCourseOptionList) throws Exception {
    Map<String, OrderCourseOptionCountParam> orderCourseOptionMap =
        new HashMap<String, OrderCourseOptionCountParam>();
    if (orderCourseOptionList != null && orderCourseOptionList.size() != 0) {
      // 行列转换
      for (OrderCourseOptionCountParam ocp : orderCourseOptionList) {
        OrderCourseOptionCountParam obj = orderCourseOptionMap.get(ocp.getCourseType() + ","
            + ocp.getCourseUnitType());
        if (obj != null) {
          // 累加课时数
          obj.setShowCourseCount(obj.getShowCourseCount() + ocp.getShowCourseCount());
          obj.setRemainCourseCount(obj.getRemainCourseCount() + ocp.getRemainCourseCount());
        } else {
          orderCourseOptionMap.put(ocp.getCourseType() + "," + ocp.getCourseUnitType(), ocp);
        }

        obj = orderCourseOptionMap.get(ocp.getCourseType() + "," + ocp.getCourseUnitType());

        // 处理课程名字
        ocp.setCourseTypeChineseName(((CourseType) MemcachedUtil.getValue(ocp.getCourseType()))
            .getCourseTypeChineseName());

        // 处理剩余课时(课程单位类型（0:节，1:月，2：天）)
        // 按节算的直接使用remainCourseCount，按时效算的需要处理
        if (ocp.getStartOrderTime() != null && ocp.getCourseUnitType() != Integer.valueOf(
            OrderCourseConstant.COURSE_UNIT_TYPE_CLASS)) {
          // 课程开始时间
          Calendar startCal = Calendar.getInstance();
          startCal.setTime(ocp.getStartOrderTime());
          // 课程结束时间
          Calendar endCal = Calendar.getInstance();
          endCal.setTime(startCal.getTime());
          // 计算有效期结束时间
          if (ocp.getCourseUnitType() == Integer.valueOf(
              OrderCourseConstant.COURSE_UNIT_TYPE_MONTH)) {
            // 按月
            endCal.add(Calendar.MONTH, ocp.getShowCourseCount());
          } else if (ocp.getCourseUnitType() == Integer.valueOf(
              OrderCourseConstant.COURSE_UNIT_TYPE_DAY)) {
            // 按天
            endCal.add(Calendar.DATE, ocp.getShowCourseCount());
          }

          // 如果之前已经有该课程，判断哪个结束日期更晚，使用最后截止日期
          if (obj.getCourseEndTime() != null) {
            if (obj.getCourseEndTime().getTime() < endCal.getTimeInMillis()) {
              obj.setCourseEndTime(endCal.getTime());
            }
          } else {
            obj.setCourseEndTime(endCal.getTime());
          }
        }

      }
    }
    return new ArrayList<>(orderCourseOptionMap.values());
  }

  public static void main(String[] args) {
    Map<String, Object> paramMap = new HashMap<>();
    // transOrderAndContractStatus(paramMap);
    // paramMap.put("from_path",
    // "8a75fa2cdf054df1a6a565f287f02xxa,8a75fa2cdf054df1a6a565f287f02xxx,8a75fa2cdf054df1a6a565f287f02xxy");
    // String[] fromPaths = paramMap.get("from_path").toString().split(",");
    // String fromPath = fromPaths[fromPaths.length - 2];

    paramMap.put("order_original_type", 1);
    int order_original_type = Integer.parseInt(paramMap.get("order_original_type").toString());

    System.out.println(1 == order_original_type);

    String order_original_type1 = paramMap.get("order_original_type").toString();
    System.out.println("1".equals(order_original_type1));
  }

}
