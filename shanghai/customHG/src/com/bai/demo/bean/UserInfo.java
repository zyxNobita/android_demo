package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 用户信息
 * @author zhangyx
 *
 */
public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String LogOnName="";// 登录名
	private String LongJobNumber="";// 
	private String LobNumber="";//登录关员工号
	private String UserName="";// 用户名
	private int CustomsStatus;//海关状态
	private String CustomsCode="";// 海关编号
	private String CustomsName="";// 海关编码
	private String SectionCode="";// 科室编码
	private String SectionName="";// 科室名称
	private String UserRoles="";///用户角色字符串
	public String getLogOnName() {
		return LogOnName;
	}
	public void setLogOnName(String logOnName) {
		LogOnName = logOnName;
	}
	public String getLongJobNumber() {
		return LongJobNumber;
	}
	public void setLongJobNumber(String longJobNumber) {
		LongJobNumber = longJobNumber;
	}
	public String getLobNumber() {
		return LobNumber;
	}
	public void setLobNumber(String lobNumber) {
		LobNumber = lobNumber;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public int getCustomsStatus() {
		return CustomsStatus;
	}
	public void setCustomsStatus(int customsStatus) {
		CustomsStatus = customsStatus;
	}
	public String getCustomsCode() {
		return CustomsCode;
	}
	public void setCustomsCode(String customsCode) {
		CustomsCode = customsCode;
	}
	public String getCustomsName() {
		return CustomsName;
	}
	public void setCustomsName(String customsName) {
		CustomsName = customsName;
	}
	public String getSectionCode() {
		return SectionCode;
	}
	public void setSectionCode(String sectionCode) {
		SectionCode = sectionCode;
	}
	public String getSectionName() {
		return SectionName;
	}
	public void setSectionName(String sectionName) {
		SectionName = sectionName;
	}
	public String getUserRoles() {
		return UserRoles;
	}
	public void setUserRoles(String userRoles) {
		UserRoles = userRoles;
	}
	
}
