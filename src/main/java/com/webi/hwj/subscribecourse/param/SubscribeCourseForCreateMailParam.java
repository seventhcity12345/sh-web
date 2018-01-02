package com.webi.hwj.subscribecourse.param;

import java.util.List;
import java.util.Map;

import com.mingyisoft.javabase.util.ReflectUtil;

public class SubscribeCourseForCreateMailParam {

  // 统计信息
  private Map<String, StatisticsSubscribeCourseForMailParam> statisticsInofMap;

  // 异常的预约信息
  private List<FindSubscribeCourseAndStudentParam> adnormalList;

  public Map<String, StatisticsSubscribeCourseForMailParam> getStatisticsInofMap() {
    return statisticsInofMap;
  }

  public void setStatisticsInofMap(
      Map<String, StatisticsSubscribeCourseForMailParam> statisticsInofMap) {
    this.statisticsInofMap = statisticsInofMap;
  }

  public List<FindSubscribeCourseAndStudentParam> getAdnormalList() {
    return adnormalList;
  }

  public void setAdnormalList(List<FindSubscribeCourseAndStudentParam> adnormalList) {
    this.adnormalList = adnormalList;
  }

  /**
   * Title: 信息不为空的情况下才发送邮件<br>
   * Description: canSendMail<br>
   * CreateDate: 2016年9月25日 上午10:24:17<br>
   * 
   * @category canSendMail
   * @author seven.gz
   * @return
   */
  public boolean canSendMail() {
    if ((adnormalList != null && adnormalList.size() > 0)
        || (statisticsInofMap != null && statisticsInofMap.size() > 0)) {
      return true;
    } else {
      return false;
    }
  }
  
  
  
  

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }
}
