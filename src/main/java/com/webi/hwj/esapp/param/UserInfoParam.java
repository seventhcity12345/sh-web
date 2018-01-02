
package com.webi.hwj.esapp.param;

import com.mingyisoft.javabase.util.ReflectUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Title: 用户登录参数<br>
 * Description: LoginParam<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月23日 下午3:20:26
 * 
 * @author komi.zsy
 */
@ApiModel(value = "UserInfoParam(用户信息模型)")
public class UserInfoParam {
  // token
  @ApiModelProperty(value = "token", required = true, example = "csafuhiwhfqwancfjiowqj")
  private String app_userid;
  // 昵称（中文名字）
  @ApiModelProperty(value = "昵称（中文名字）", required = true, example = "张三")
  private String nick_name;
  
  @ApiModelProperty(value = "英文名", required = true, example = "seven")
  private String enname;
  
  @ApiModelProperty(value = "头像", required = true, example = "http://webi-hwj.oss-cn-hangzhou.aliyuncs.com/images%2Fuser%2Fdefault%2Fuser_default_photo.jpg")
  private String head_imgurl;
  

  public String toString() {
    return ReflectUtil.reflectToString(this);
  }


  public String getApp_userid() {
    return app_userid;
  }


  public void setApp_userid(String app_userid) {
    this.app_userid = app_userid;
  }


  public String getNick_name() {
    return nick_name;
  }


  public void setNick_name(String nick_name) {
    this.nick_name = nick_name;
  }


  public String getEnname() {
    return enname;
  }


  public void setEnname(String enname) {
    this.enname = enname;
  }


  public String getHead_imgurl() {
    return head_imgurl;
  }


  public void setHead_imgurl(String head_imgurl) {
    this.head_imgurl = head_imgurl;
  }
  
}