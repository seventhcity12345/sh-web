package com.webi.hwj.param;

import java.io.Serializable;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: Easyui查询参数bean，有排序、分页、组合查询等参数<br> 
 * Description: EasyuiSearchParam<br> 
 * Company: 韦博英语在线教育部<br> 
 * CreateDate:2017年8月8日 下午4:52:32 
 * @author komi.zsy
 */
public class EasyuiSearchParam implements Serializable {
  private static final long serialVersionUID = 1273362446487103615L;
  private String sort;
  private String order;
  private Integer page;
  private Integer rows;
  private String cons;

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getRows() {
    return rows;
  }

  public void setRows(Integer rows) {
    this.rows = rows;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

}
