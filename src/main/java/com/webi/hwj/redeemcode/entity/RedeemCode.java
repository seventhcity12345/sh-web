package com.webi.hwj.redeemcode.entity;

import java.io.Serializable;
import com.mingyisoft.javabase.annotation.TableName;
import com.mingyisoft.javabase.util.ReflectUtil;
import java.util.Date;

import javax.validation.constraints.NotNull;


/**
 * @category redeemCode Entity
 * @author mingyisoft代码生成工具
 */
@TableName("t_redeem_code")
public class RedeemCode implements Serializable{
  private static final long serialVersionUID = 5507038245318591434L;
  //主键
	private String keyId;
	//课程包id
	@NotNull(message = "课程包不能为空")
	private String coursePackageId;
	//价格策略主表id
	@NotNull(message = "价格策略不能为空")
    private String coursePackagePriceId;
    //批次id（暂时只有qq用）
    private String cpid;
	//兑换码
	private String redeemCode;
	//用户id(没人用就是空)
	private String redeemUserId;
	//用户手机号(没人用就是空)
	private String redeemUserPhone;
	//用户真实姓名(没人用就是空)
	private String redeemUserRealName;
	//兑换码使用时间
	private Date redeemStartTime;
	//兑换码截止时间(一个月)
	private Date redeemEndTime;
	//活动开始时间（3.16-4.19）
	@NotNull(message = "兑换码开始时间不能为空")
	private Date activityStartTime;
	//活动结束时间
	@NotNull(message = "兑换码截止时间不能为空")
	private Date activityEndTime;
	//活动名称
	@NotNull(message = "活动名称不能为空")
	private String activityName;
	//活动中是否已经被发送（用于在线领取活动）0-未发送， 1-已发送
	private Integer isSent;
	//创建时间
	private Date createDate;
	//修改时间
	private Date updateDate;
	//创建人
	private String createUserId;
	//修改人
	private String updateUserId;
	//是否启用
	private Boolean isUsed;

	public String toString(){
		return ReflectUtil.reflectToString(this);
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getCoursePackageId() {
		return coursePackageId;
	}

	public void setCoursePackageId(String coursePackageId) {
		this.coursePackageId = coursePackageId;
	}

	public String getRedeemCode() {
		return redeemCode;
	}

	public void setRedeemCode(String redeemCode) {
		this.redeemCode = redeemCode;
	}

	public String getRedeemUserId() {
		return redeemUserId;
	}

	public void setRedeemUserId(String redeemUserId) {
		this.redeemUserId = redeemUserId;
	}

	public String getRedeemUserPhone() {
		return redeemUserPhone;
	}

	public void setRedeemUserPhone(String redeemUserPhone) {
		this.redeemUserPhone = redeemUserPhone;
	}

	public String getRedeemUserRealName() {
		return redeemUserRealName;
	}

	public void setRedeemUserRealName(String redeemUserRealName) {
		this.redeemUserRealName = redeemUserRealName;
	}

	public Date getRedeemStartTime() {
		return redeemStartTime;
	}

	public void setRedeemStartTime(Date redeemStartTime) {
		this.redeemStartTime = redeemStartTime;
	}

	public Date getRedeemEndTime() {
		return redeemEndTime;
	}

	public void setRedeemEndTime(Date redeemEndTime) {
		this.redeemEndTime = redeemEndTime;
	}

	public Date getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(Date activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public Date getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getIsSent() {
		return isSent;
	}

	public void setIsSent(Integer isSent) {
		this.isSent = isSent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

  public String getCoursePackagePriceId() {
    return coursePackagePriceId;
  }

  public void setCoursePackagePriceId(String coursePackagePriceId) {
    this.coursePackagePriceId = coursePackagePriceId;
  }

  public String getCpid() {
    return cpid;
  }

  public void setCpid(String cpid) {
    this.cpid = cpid;
  }


}