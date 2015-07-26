package com.bai.demo.utils;

import java.io.Serializable;

/************************************************************
 *  内容摘要	：
 *
 *  作者	：adminstrator
 *  创建时间	：2013-7-23 下午05:19:47 
 *  当前版本号：v1.0
 *  历史记录	:
 *  	日期	: 2013-7-23 下午05:19:47 	修改人：adminstrator
 *  	描述	:
 *  上传图片的信息类
 ************************************************************/
public class Image implements Serializable {
	private String ID;
	private String ENTRY_ID;
	private Integer G_NO;
	private String PHOTO_CODE;
	private String PHOTO_ID;
	private String LONG;
	private String LAT;
	private String base64String;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public Integer getG_NO() {
		return G_NO;
	}
	public void setG_NO(Integer g_NO) {
		G_NO = g_NO;
	}
	public String getPHOTO_CODE() {
		return PHOTO_CODE;
	}
	public void setPHOTO_CODE(String pHOTO_CODE) {
		PHOTO_CODE = pHOTO_CODE;
	}
	public String getPHOTO_ID() {
		return PHOTO_ID;
	}
	public void setPHOTO_ID(String pHOTO_ID) {
		PHOTO_ID = pHOTO_ID;
	}
	public String getLONG() {
		return LONG;
	}
	public void setLONG(String lONG) {
		LONG = lONG;
	}
	public String getLAT() {
		return LAT;
	}
	public void setLAT(String lAT) {
		LAT = lAT;
	}
	public String getBase64String() {
		return base64String;
	}
	public void setBase64String(String base64String) {
		this.base64String = base64String;
	}
	
}
