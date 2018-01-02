
package com.webi.hwj.esapp.param;

import com.mingyisoft.javabase.util.ReflectUtil;

/**
 * Title: speakhi-es app的返回格式<br>
 * Description: AppJsonConstant<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月23日 下午3:11:42
 * 
 * @author komi.zsy
 */
public class AppJsonObject<T> {
  // 状态是否成功
  private boolean state = true;
  // 返回数据
  private T data;
  // 错误信息
  private String error;
  // 不知道干啥的可能是错误码吧（跟着总部的数据结构）
  private String error_key;

  public AppJsonObject() {

  }

  public AppJsonObject(boolean state, T data, String error) {
    this.data = data;
    this.state = state;
    this.error = error;
  }

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }

  public boolean isState() {
    return state;
  }

  public void setState(boolean state) {
    this.state = state;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getError_key() {
    return error_key;
  }

  public void setError_key(String error_key) {
    this.error_key = error_key;
  }

}