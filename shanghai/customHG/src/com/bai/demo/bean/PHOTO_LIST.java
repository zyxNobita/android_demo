package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 
 * @author zhangyx
 *
 */
public class PHOTO_LIST implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;
	private String G_NO;
	private String PHOTO_CODE;
	private String PHOTO_ID;
	private String LONG;
	private String LAT;
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public String getG_NO() {
		return G_NO;
	}
	public void setG_NO(String g_NO) {
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
