package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 简易报关单表头信息
 * @author zhangyx
 *
 */
public class Entry_HeadSimple implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ENTRY_ID;// 报关单号
	private String PRE_ENTRY_ID;// 预录入编号
	private String TRADE_CO;// 经营单位编号
	private String TRADE_NAME;// 经营单位名称
	private String TRAF_MODE;// 运输方式
	private String TRAF_SPEC;// 
	private String TRAF_NAME;// 运输工具名称
	private String VOYAGE_NO;// 运输工具航次(班)号
	private String BILL_NO;// 提运单号
	private String OWNER_CODE;// 收货单位编号
    private String OWNER_NAME;// 收货单位名称
    private String TRADE_MODE;// 贸易方式
    private String ABBR_TRADE;// 
	public String getENTRY_ID() {
		return ENTRY_ID;
	}
	public void setENTRY_ID(String eNTRY_ID) {
		ENTRY_ID = eNTRY_ID;
	}
	public String getPRE_ENTRY_ID() {
		return PRE_ENTRY_ID;
	}
	public void setPRE_ENTRY_ID(String pRE_ENTRY_ID) {
		PRE_ENTRY_ID = pRE_ENTRY_ID;
	}
	public String getTRADE_CO() {
		return TRADE_CO;
	}
	public void setTRADE_CO(String tRADE_CO) {
		TRADE_CO = tRADE_CO;
	}
	public String getTRADE_NAME() {
		return TRADE_NAME;
	}
	public void setTRADE_NAME(String tRADE_NAME) {
		TRADE_NAME = tRADE_NAME;
	}
	public String getTRAF_MODE() {
		return TRAF_MODE;
	}
	public void setTRAF_MODE(String tRAF_MODE) {
		TRAF_MODE = tRAF_MODE;
	}
	public String getTRAF_SPEC() {
		return TRAF_SPEC;
	}
	public void setTRAF_SPEC(String tRAF_SPEC) {
		TRAF_SPEC = tRAF_SPEC;
	}
	public String getTRAF_NAME() {
		return TRAF_NAME;
	}
	public void setTRAF_NAME(String tRAF_NAME) {
		TRAF_NAME = tRAF_NAME;
	}
	public String getVOYAGE_NO() {
		return VOYAGE_NO;
	}
	public void setVOYAGE_NO(String vOYAGE_NO) {
		VOYAGE_NO = vOYAGE_NO;
	}
	public String getBILL_NO() {
		return BILL_NO;
	}
	public void setBILL_NO(String bILL_NO) {
		BILL_NO = bILL_NO;
	}
	public String getOWNER_CODE() {
		return OWNER_CODE;
	}
	public void setOWNER_CODE(String oWNER_CODE) {
		OWNER_CODE = oWNER_CODE;
	}
	public String getOWNER_NAME() {
		return OWNER_NAME;
	}
	public void setOWNER_NAME(String oWNER_NAME) {
		OWNER_NAME = oWNER_NAME;
	}
	public String getTRADE_MODE() {
		return TRADE_MODE;
	}
	public void setTRADE_MODE(String tRADE_MODE) {
		TRADE_MODE = tRADE_MODE;
	}
	public String getABBR_TRADE() {
		return ABBR_TRADE;
	}
	public void setABBR_TRADE(String aBBR_TRADE) {
		ABBR_TRADE = aBBR_TRADE;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
