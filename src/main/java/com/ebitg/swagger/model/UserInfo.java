package com.ebitg.swagger.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ��Ա��Ϣ�� ע�⣺@ApiModel �� @ApiModelProperty ������ͨ��������ղ���ʱ��API�ĵ�����ʾ�ֶε�˵��
 * ע�⣺@DateTimeFormat �� @JsonFormat �����ڽ��պͷ������ڸ�ʽʱ�����ʽ�� ʵ�����Ӧ�����ݱ�Ϊ�� user_info
 * 
 * @author Wangbo
 * @date: 2017-07-14 16:45:29
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
@ApiModel(value = "UserInfo")
public class UserInfo {
	@ApiModelProperty(value = "ID")
	private Integer id;

	@ApiModelProperty(value = "�û���¼�˺�", required = true)
	private String userNo;

	@ApiModelProperty(value = "����", required = true)
	private String userName;

	@ApiModelProperty(value = "����ƴ��")
	private String spellName;

	@ApiModelProperty(value = "����", required = true)
	private String password;

	@ApiModelProperty(value = "�ֻ���", required = true)
	private String userPhone;

	@ApiModelProperty(value = "�Ա�")
	private Integer userGender;

	@ApiModelProperty(value = "��¼����ʱ��")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@ApiModelProperty(value = "��¼�޸�ʱ��")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo == null ? null : userNo.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getSpellName() {
		return spellName;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone == null ? null : userPhone.trim();
	}

	public Integer getUserGender() {
		return userGender;
	}

	public void setUserGender(Integer userGender) {
		this.userGender = userGender;
	}

	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", userNo=").append(userNo);
		sb.append(", userName=").append(userName);
		sb.append(", spellName=").append(spellName);
		sb.append(", password=").append(password);
		sb.append(", userPhone=").append(userPhone);
		sb.append(", userGender=").append(userGender);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateTime=").append(updateTime);
		sb.append("]");
		return sb.toString();
	}
}
