package cn.tydic.mobile.pdarequery.entity;

import java.io.Serializable;

import android.content.Context;

/***
 * 用户实体类
 * @author zhangyx
 *
 */
@SuppressWarnings("serial")
public class UserInfo implements Serializable{

	private String userName;//用户名称
	private String pwd;//密码
	private String phoneId;//手机Id标示
	private String xm;
	private String serverAddress;//服务器地址
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

}
