package com.webi.hwj.notice.param;

import java.io.Serializable;
import java.util.List;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 公告信息组合查询参数bean<br>
 * Description: NoticeParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年7月28日 下午5:05:08
 * 
 * @author seven.gz
 */
public class RetrunNoticeDataParam implements Serializable {
  private static final long serialVersionUID = -6630172737144250045L;
  // 返回的公告列表
  @ApiModelProperty(value = "返回的公告列表", required = true)
  private List<NoticeParam> noticeList;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public List<NoticeParam> getNoticeList() {
    return noticeList;
  }

  public void setNoticeList(List<NoticeParam> noticeList) {
    this.noticeList = noticeList;
  }

}