package com.webi.hwj.subscribecourse.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.util.DateUtil;
import com.webi.hwj.subscribecourse.param.FindSubscribeCourseAndStudentParam;
import com.webi.hwj.subscribecourse.param.StatisticsSubscribeCourseForMailParam;
import com.webi.hwj.subscribecourse.param.SubscribeCourseForCreateMailParam;
import com.webi.hwj.user.param.FindUserOrderInfoParam;

public class AdminSubscribeCourseUtil {
  private static Logger logger = Logger.getLogger(AdminSubscribeCourseUtil.class);

  /**
   * 
   * Title: 根据统计数据生成要发的送邮件的html<br>
   * Description: 根据统计数据生成要发的送邮件的html<br>
   * CreateDate: 2016年9月22日 下午3:12:26<br>
   * 
   * @category 根据统计数据生成要发的送邮件的html
   * @author seven.gz
   * @param subscribeCourseForCreateMailParam
   * @return
   */
  public static String creatAbnormalSubscribeMail(
      SubscribeCourseForCreateMailParam subscribeCourseForCreateMailParam) {
    StringBuffer mailBufferString = new StringBuffer();
    // 遍历统计信息
    Map<String, StatisticsSubscribeCourseForMailParam> statisticsMap =
        subscribeCourseForCreateMailParam
            .getStatisticsInofMap();
    if (statisticsMap != null) {
      for (StatisticsSubscribeCourseForMailParam statisticsInfo : statisticsMap.values()) {
        mailBufferString.append(statisticsInfo.getCourseType()
            + "共计<span style=\"font-weight:bold;color:red;\">" + statisticsInfo.getTotalCount()
            + "</span>节,"
            + "有问题<span style=\"font-weight:bold;color:red;\">" + statisticsInfo.getAbnormalCount()
            + "</span>节,"
            + "正常<span style=\"font-weight:bold;color:red;\">" + statisticsInfo.getNormalCount()
            + "</span>节。</br>");
      }
    }
    mailBufferString.append("<br/><br/>");
    mailBufferString.append("<table border=1 cellspacing=0 cellpadding=2>");
    mailBufferString.append("<tr><th>Time</th><th>ID</th><th>Name</th><th>Type</th>"
        + "<th>Course</th><th>Teacher Resource</th><th>Teacher</th><th>LC</th>"
        + "<th>Note Taker</th><th>Note</th><th>Note Time</th></tr>");
    List<FindSubscribeCourseAndStudentParam> abnormalList = subscribeCourseForCreateMailParam
        .getAdnormalList();
    if (abnormalList != null && abnormalList.size() > 0) {
      for (FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam : abnormalList) {
        mailBufferString.append("<tr>");
        if (findSubscribeCourseAndStudentParam.getStartTime() != null) {
          mailBufferString.append("<td>" + DateUtil.dateToStrYYMMDDHHMMSS(
              findSubscribeCourseAndStudentParam.getStartTime()) + "</td>");
        } else {
          mailBufferString.append("<td>" + "" + "</td>");
        }
        mailBufferString
            .append(
                "<td>" + nullTranslate(findSubscribeCourseAndStudentParam.getUserCode()) + "</td>");
        mailBufferString
            .append(
                "<td>" + nullTranslate(findSubscribeCourseAndStudentParam.getUserName()) + "</td>");
        mailBufferString
            .append("<td>" + nullTranslate(findSubscribeCourseAndStudentParam
                .getCourseTypeChineseName())
                + "</td>");
        mailBufferString
            .append("<td>" + nullTranslate(findSubscribeCourseAndStudentParam.getCourseTitle())
                + "</td>");
        mailBufferString
            .append("<td>" + nullTranslate(findSubscribeCourseAndStudentParam.getThirdFrom())
                + "</td>");
        mailBufferString
            .append("<td>" + nullTranslate(findSubscribeCourseAndStudentParam.getTeacherName())
                + "</td>");
        mailBufferString
            .append("<td>" + nullTranslate(findSubscribeCourseAndStudentParam.getAdminUserName())
                + "</td>");
        mailBufferString
            .append(
                "<td>" + nullTranslate(findSubscribeCourseAndStudentParam.getSubscribeNoteTaker())
                    + "</td>");
        mailBufferString
            .append("<td>" + nullTranslate(findSubscribeCourseAndStudentParam.getSubscribeNote())
                + "</td>");
        if (findSubscribeCourseAndStudentParam.getSubscribeNoteDate() != null) {
          mailBufferString.append("<td>" + DateUtil.dateToStrYYMMDDHHMMSS(
              findSubscribeCourseAndStudentParam.getSubscribeNoteDate()) + "</td>");
        } else {
          mailBufferString.append("<td>" + "" + "</td>");
        }
        mailBufferString.append("</tr>");
      }
    }
    mailBufferString.append("</table>");
    return mailBufferString.toString();
  }

  /**
   * 
   * Title: 将null转为空字符串<br>
   * Description: 将null转为空字符串<br>
   * CreateDate: 2016年9月22日 下午3:29:20<br>
   * 
   * @category 将null转为空字符串
   * @author seven.gz
   * @param obj
   * @return
   */
  public static Object nullTranslate(Object obj) {
    if (obj == null) {
      return "";
    } else {
      return obj;
    }
  }

  /**
   * 
   * Title: 根据统计数据生成要发的送邮件的附件字符串 .csv<br>
   * Description: 根据统计数据生成要发的送邮件的附件 .csv<br>
   * CreateDate: 2016年9月22日 下午3:12:26<br>
   * 
   * @category 据统计数据生成要发的送邮件的附件字符串 .csv
   * @author seven.gz
   * @param subscribeCourseForCreateMailParam
   * @return
   */
  public static String creatAbnormalMailAttachmentsString(
      SubscribeCourseForCreateMailParam subscribeCourseForCreateMailParam) {
    StringBuffer mailBufferString = new StringBuffer();
    // 遍历统计信息
    // 获取回车符号
    String enterString = System.getProperty("line.separator");
    mailBufferString
        .append("Time,ID,Name,Type,Course,Teacher Resource,Teacher,LC,Note Taker,Note,Note Time"
            + enterString);
    List<FindSubscribeCourseAndStudentParam> abnormalList = subscribeCourseForCreateMailParam
        .getAdnormalList();
    if (abnormalList != null && abnormalList.size() > 0) {
      for (FindSubscribeCourseAndStudentParam findSubscribeCourseAndStudentParam : abnormalList) {
        if (findSubscribeCourseAndStudentParam.getStartTime() != null) {
          mailBufferString.append(DateUtil.dateToStrYYMMDDHHMMSS(
              findSubscribeCourseAndStudentParam.getStartTime()) + ",");
        } else {
          mailBufferString.append(",");
        }
        mailBufferString
            .append(nullTranslate(findSubscribeCourseAndStudentParam.getUserCode()) + ",");
        mailBufferString
            .append(nullTranslate(findSubscribeCourseAndStudentParam.getUserName()) + ",");
        mailBufferString
            .append(nullTranslate(findSubscribeCourseAndStudentParam.getCourseType()) + ",");
        mailBufferString
            .append(nullTranslate(findSubscribeCourseAndStudentParam.getCourseTitle()) + ",");
        mailBufferString
            .append(nullTranslate(findSubscribeCourseAndStudentParam.getThirdFrom()) + ",");
        mailBufferString
            .append(nullTranslate(findSubscribeCourseAndStudentParam.getTeacherName()) + ",");
        mailBufferString
            .append(nullTranslate(findSubscribeCourseAndStudentParam.getAdminUserName()) + ",");
        mailBufferString
            .append(
                nullTranslate(findSubscribeCourseAndStudentParam.getSubscribeNoteTaker()) + ",");
        mailBufferString
            .append(nullTranslate(findSubscribeCourseAndStudentParam.getSubscribeNote()) + ",");
        if (findSubscribeCourseAndStudentParam.getSubscribeNoteDate() != null) {
          mailBufferString.append(DateUtil.dateToStrYYMMDDHHMMSS(
              findSubscribeCourseAndStudentParam.getSubscribeNoteDate()) + ",");
        }
        mailBufferString
            .append(enterString);
      }
    }
    return mailBufferString.toString();
  }

  /**
   * 
   * Title: noteTypeToStr<br>
   * Description: noteTypeToStr<br>
   * CreateDate: 2016年10月26日 下午5:01:41<br>
   * 
   * @category noteTypeToStr
   * @author seven.gz
   * @return
   */
  public static String noteTypeToStr(Integer noteType) {
    String noteTypeStr = "";
    if (noteType != null) {
      switch (noteType) {
        case 1:
          noteTypeStr = "NS-学员失联";
          break;
        case 2:
          noteTypeStr = "NS-学员个人原因";
          break;
        case 3:
          noteTypeStr = "NS-学员设备故障";
          break;
        case 4:
          noteTypeStr = "NS-老师设备故障";
          break;
        case 5:
          noteTypeStr = "NS-老师未出席";
          break;
        case 6:
          noteTypeStr = "老师迟到";
          break;
        case 7:
          noteTypeStr = "平台故障";
          break;
        case 8:
          noteTypeStr = "首课";
          break;
      }
    }
    return noteTypeStr;
  }

  /**
   * 
   * Title: 生成团训学员报表定时要发的送邮件的html<br>
   * Description:生成团训学员报表定时要发的送邮件的html<br>
   * CreateDate: 2016年9月22日 下午3:12:26<br>
   * 
   * @category 生成团训学员报表定时要发的送邮件的html
   * @author seven.gz
   * @param subscribeCourseForCreateMailParam
   * @return
   */
  public static String creatTuanxunSubscribeMail(List<FindUserOrderInfoParam> tuanxunUserInfoList) {
    StringBuffer mailBufferString = new StringBuffer();
    Date currentDate = new Date();
    mailBufferString.append("<br/><br/>");
    mailBufferString.append("<table border=1 cellspacing=0 cellpadding=2>");
    mailBufferString
        .append("<tr><th>姓名</th><th>手机号</th><th>开始级别</th><th>当前级别</th><th>开始日期</th><th>合同结束日期</th>"
            + "<th>报告日期</th><th>1v1</th><th>1v6</th><th>RSA累计时间</th></tr>");
    if (tuanxunUserInfoList != null && tuanxunUserInfoList.size() > 0) {
      for (FindUserOrderInfoParam findUserOrderInfoParam : tuanxunUserInfoList) {
        mailBufferString.append("<tr>");

        mailBufferString
            .append(
                "<td>" + nullTranslate(findUserOrderInfoParam.getUserName()) + "</td>");
        mailBufferString
            .append(
                "<td>" + nullTranslate(findUserOrderInfoParam.getPhone()) + "</td>");
        mailBufferString
            .append(
                "<td>" + nullTranslate(findUserOrderInfoParam.getInitLevel()) + "</td>");
        mailBufferString
            .append("<td>" + nullTranslate(findUserOrderInfoParam.getCurrentLevel())
                + "</td>");

        if (findUserOrderInfoParam.getStartOrderTime() != null) {
          mailBufferString.append("<td>" + DateUtil.dateToStrYYMMDDHHMMSS(
              findUserOrderInfoParam.getStartOrderTime()) + "</td>");
        } else {
          mailBufferString.append("<td>" + "" + "</td>");
        }

        if (findUserOrderInfoParam.getEndOrderTime() != null) {
          mailBufferString.append("<td>" + DateUtil.dateToStrYYMMDDHHMMSS(
              findUserOrderInfoParam.getEndOrderTime()) + "</td>");
        } else {
          mailBufferString.append("<td>" + "" + "</td>");
        }
        mailBufferString.append("<td>" + DateUtil.dateToStrYYMMDDHHMMSS(currentDate) + "</td>");

        mailBufferString
            .append("<td>" + nullTranslate(findUserOrderInfoParam.getConsumeCourseType1Count())
                + "</td>");
        mailBufferString
            .append("<td>" + nullTranslate(findUserOrderInfoParam.getConsumeCourseType2Count())
                + "</td>");
        if (findUserOrderInfoParam.getTotalTmmWorkingTime() == null) {
          mailBufferString
              .append(
                  "<td>" + nullTranslate("00:00:00")
                      + "</td>");
        } else {
          mailBufferString
              .append(
                  "<td>" + nullTranslate(findUserOrderInfoParam.getTotalTmmWorkingTime())
                      + "</td>");
        }
        mailBufferString.append("</tr>");
      }
    }
    mailBufferString.append("</table>");
    return mailBufferString.toString();
  }

  /**
   * 
   * Title: 生成团训学员信息的csv文件内容<br>
   * Description: creatTuanxunSubscribeCsvString<br>
   * CreateDate: 2016年12月12日 下午5:00:10<br>
   * 
   * @category 生成团训学员信息的csv文件内容
   * @author seven.gz
   * @param tuanxunUserInfoList
   * @return
   */
  public static String creatTuanxunSubscribeCsvString(
      List<FindUserOrderInfoParam> tuanxunUserInfoList) {
    StringBuffer mailBufferString = new StringBuffer();
    // 遍历统计信息
    // 获取回车符号
    String enterString = System.getProperty("line.separator");
    // 添加English Studio,1v1comment
    mailBufferString
        .append("姓名,学员ID,手机号,开始级别,当前级别,开始日期,合同截至日期,报告日期,1v1,1v6,English Studio,RSA累计时间,1v1comment"
            + enterString);
    if (tuanxunUserInfoList != null && tuanxunUserInfoList.size() > 0) {
      Date currentDate = new Date();
      for (FindUserOrderInfoParam findUserOrderInfoParam : tuanxunUserInfoList) {
        mailBufferString
            .append(nullTranslate(findUserOrderInfoParam.getUserName()) + ",");
        // 添加用户id
        mailBufferString
            .append(nullTranslate(findUserOrderInfoParam.getUserCode()) + ",");
        mailBufferString
            .append(nullTranslate(findUserOrderInfoParam.getPhone()) + ",");
        mailBufferString
            .append(nullTranslate(findUserOrderInfoParam.getInitLevel()) + ",");
        mailBufferString
            .append(nullTranslate(findUserOrderInfoParam.getCurrentLevel()) + ",");
        if (findUserOrderInfoParam.getStartOrderTime() != null) {
          mailBufferString.append(DateUtil.dateToStrYYMMDDHHMMSS(
              findUserOrderInfoParam.getStartOrderTime()) + ",");
        } else {
          mailBufferString.append(",");
        }
        if (findUserOrderInfoParam.getEndOrderTime() != null) {
          mailBufferString.append(DateUtil.dateToStrYYMMDDHHMMSS(
              findUserOrderInfoParam.getEndOrderTime()) + ",");
        } else {
          mailBufferString.append(",");
        }
        mailBufferString.append(DateUtil.dateToStrYYMMDDHHMMSS(currentDate) + ",");
        mailBufferString
            .append(nullTranslate(findUserOrderInfoParam.getConsumeCourseType1Count()) + ",");
        mailBufferString
            .append(nullTranslate(findUserOrderInfoParam.getConsumeCourseType2Count()) + ",");
        // 添加English Studio（出席的ES课程数）
        mailBufferString
            .append(nullTranslate(findUserOrderInfoParam.getConsumeCourseType8Count()) + ",");
        if (StringUtils.isEmpty(findUserOrderInfoParam.getTotalTmmWorkingTime())) {
          mailBufferString
              .append("0:0:0" + ",");
        } else {
          mailBufferString
              .append(
                  nullTranslate(findUserOrderInfoParam.getTotalTmmWorkingTime()) + ",");
        }

        // 添加课程评论
        if (StringUtils.isEmpty(findUserOrderInfoParam.getCommentContent())) {
          mailBufferString
              .append("");
        } else {
          mailBufferString
              .append("\"" +
                  nullTranslate(findUserOrderInfoParam.getCommentContent()) + "\"");
        }
        mailBufferString
            .append(enterString);
      }
    }
    return mailBufferString.toString();
  }
}
